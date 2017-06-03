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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OfferCommentsFragment extends Fragment {
    private RelativeLayout mRootView;

    private RecyclerView recycler;
    private RecyclerView.LayoutManager lManager;
    private DatabaseReference ref;
    private static DatabaseReference ref2;
    private RecyclerView mRoomRecyclerView;
    private static DatabaseReference mFirebaseDatabaseReference;
    private static FirebaseRecyclerAdapter<Comment, CommentViewHolder> mFirebaseAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    public String texto;
    public ImageButton imageButton;
    public EditText pruebaEditText;


    public EditText editText_comment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = (RelativeLayout) inflater.inflate(R.layout.app_bar_main2, container, false);

        imageButton = (ImageButton) mRootView.findViewById(R.id.imageButton);
        pruebaEditText = (EditText) mRootView.findViewById(R.id.editText_comment);

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
                viewHolder.textView_comment.setText(model.getMensaje());
                viewHolder.comment_date.setText(model.getDate());
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

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String comentario = pruebaEditText.getText().toString();
                if(!comentario.equals("")){
                    FirebaseAuth auth;
                    // Instancía de Firebase
                    auth = FirebaseAuth.getInstance();

                    // Obtener usuario actual Firebase
                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    Comment c = new Comment(user.getEmail().toString(), pruebaEditText.getText().toString(), returnDate());


                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                    ref.child("offers").child(OfferScrollingActivity.getReferencia()).child("comments").push().setValue(c);

                    pruebaEditText.setText("");
                }
                else{
                    Toast.makeText(getContext(), "Debes escribir algo antes de enviarlo", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return mRootView;
    }

    public String returnDate(){
        DateFormat dateFormat = new DateFormat();
        String fecha = dateFormat.devolverFecha();
        return fecha;
    }

    public static Fragment newInstance() {
        return new OfferCommentsFragment();
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
