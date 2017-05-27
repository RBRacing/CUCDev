/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.enav.cazaunchollo.cazaunchollo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OfferCommentsFragment extends Fragment {
    private CoordinatorLayout mRootView;

    private RecyclerView recycler;
    private RecyclerView.LayoutManager lManager;
    private DatabaseReference ref;
    private static DatabaseReference ref2;
    private RecyclerView mRoomRecyclerView;
    private static DatabaseReference mFirebaseDatabaseReference;
    private static FirebaseRecyclerAdapter<Comment, CommentViewHolder> mFirebaseAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    private EditText editText_comment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = (CoordinatorLayout) inflater.inflate(R.layout.app_bar_main2, container, false);


        String comentario = "";

        String referencia = OfferScrollingActivity.getReferencia();
        // Obtener el Recycler
        recycler = (RecyclerView) mRootView.findViewById(R.id.reciclador2);
        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(getContext());
        recycler.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        mRoomRecyclerView = (RecyclerView) mRootView.findViewById(R.id.reciclador2);
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mLinearLayoutManager = new LinearLayoutManager(getContext());

        //Muestra las tarjetas de arriba a abajo
        mLinearLayoutManager.setStackFromEnd(false);

        mFirebaseAdapter = new FirebaseRecyclerAdapter<Comment, CommentViewHolder>(
                Comment.class,
                R.layout.comment_card,
                CommentViewHolder.class,
                mFirebaseDatabaseReference.child("offers").child(referencia).child("comments")) {

            @Override
            protected void populateViewHolder(CommentViewHolder viewHolder, Comment model, int position) {
                viewHolder.person_name.setText(model.getAutor());
            }
        };

        mFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int roomCount = mFirebaseAdapter.getItemCount();
                int lastVisiblePosition = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                if (lastVisiblePosition == -1 || (positionStart >= (roomCount - 1) && lastVisiblePosition == (positionStart - 1))) {
                    mRoomRecyclerView.scrollToPosition(positionStart);
                }
            }
        });
        mRoomRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRoomRecyclerView.setAdapter(mFirebaseAdapter);

        editText_comment = (EditText) mRootView.findViewById(R.id.editText_comment);

        return mRootView;
    }

    public static Fragment newInstance() {
        return new OfferCommentsFragment();
    }


}
