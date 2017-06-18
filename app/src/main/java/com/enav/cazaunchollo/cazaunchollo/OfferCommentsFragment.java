package com.enav.cazaunchollo.cazaunchollo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.enav.cazaunchollo.cazaunchollo.FirebaseReferences.COMMENTS_REFERENCE;
import static com.enav.cazaunchollo.cazaunchollo.FirebaseReferences.OFFERS_REFERENCE;

public class OfferCommentsFragment extends Fragment {

    /* Variables */
    private RelativeLayout mRootView;
    private RecyclerView recycler;
    private RecyclerView.LayoutManager lManager;
    private RecyclerView mRoomRecyclerView;
    private static DatabaseReference mFirebaseDatabaseReference;
    private static FirebaseRecyclerAdapter<Comment, CommentViewHolder> mFirebaseAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    public String texto;
    public ImageButton imageButton;
    public EditText pruebaEditText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /* Inicialización de variables */
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
                mFirebaseDatabaseReference.child(OFFERS_REFERENCE).child(referencia).child(COMMENTS_REFERENCE)) {

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
                    // Obtener usuario actual Firebase
                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    Comment c = new Comment(user.getEmail().toString(), pruebaEditText.getText().toString(), returnDate());
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                    ref.child(OFFERS_REFERENCE).child(OfferScrollingActivity.getReferencia()).child(COMMENTS_REFERENCE).push().setValue(c);
                    pruebaEditText.setText("");
                    CallFirebaseDatabase.devolverNumDeComentariosParaUnaOferta(OfferScrollingActivity.getReferencia());
                }
                else{
                    Toast.makeText(getContext(), "Debes escribir algo antes de enviarlo", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return mRootView;
    }

    // Devolver fecha actual
    public String returnDate(){
        DateFormat dateFormat = new DateFormat();
        String fecha = dateFormat.devolverFecha();
        return fecha;
    }

    public static Fragment newInstance() {
        return new OfferCommentsFragment();
    }

}
