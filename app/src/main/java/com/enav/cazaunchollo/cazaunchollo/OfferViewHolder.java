package com.enav.cazaunchollo.cazaunchollo;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

import static android.R.attr.id;
import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;


public class OfferViewHolder extends RecyclerView.ViewHolder{

    //public Toolbar toolbarCard;
    public TextView titleCard;
    public TextView subTitleCard;
    public TextView comentarios;
    public TextView likeTV;
    public ImageView imagen;
    public ImageView likeIV;
    public TextView fecha;
    private ArrayList <String>  arrayKeyOffers = new ArrayList<>();


    public OfferViewHolder(View v){

        super(v);
        //toolbarCard = (Toolbar)itemView.findViewById(R.id.toolbarCard);
        titleCard = (TextView) itemView.findViewById(R.id.titleCard);
        subTitleCard = (TextView) itemView.findViewById(R.id.subTitleCard);
        comentarios = (TextView)itemView.findViewById(R.id.comentarios);
        likeTV = (TextView)itemView.findViewById(R.id.likeTV);
        imagen = (ImageView)itemView.findViewById(R.id.imagen);
        likeIV = (ImageView)itemView.findViewById(R.id.likeIV);
        fecha = (TextView) itemView.findViewById(R.id.publication_date);


        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();

        DatabaseReference myRef1 = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());


        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.recogerIDyLanzarActivity(v, getPosition());
            }
        });
        likeIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // DatabaseReference ref =  FirebaseDatabase.getInstance().getReference().child("offers").child("-KiyHgH-j5F7QCQIUU8E").child("likes");
               // String likes = ref.getKey()..toString();
                //Log.d("KEY", likes.toString());
               // int suma = Integer.parseInt(likeTV.getText().toString()) + 1;
               // likeTV.setText(String.valueOf(suma));
               // DatabaseReference ref =  FirebaseDatabase.getInstance().getReference().child("offers");
               // String referencia = ref.getRef('2').getKey().toString();
                //getRef(id).getKey().toString()
                // Log.d("KEY", ref.getRef(getPosition()).getKey().toString());


               // MainActivity.like(v, getPosition());
                int likes = Integer.parseInt(likeTV.getText().toString())+1;
                likeTV.setText(String.valueOf(likes));


                DatabaseReference ref3 =  FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).child("likes");


                //OfferScrollingActivity offerScrollingActivity = new OfferScrollingActivity();
                String referencia = MainActivity.dameREFOffer(getPosition());
                arrayKeyOffers.add(referencia);
                DatabaseReference ref =  FirebaseDatabase.getInstance().getReference().child("offers").child(referencia).child("likes");


                DatabaseReference ref2 =  FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).child("likes");
               // ref2.setValue(arrayKeyOffers);
                ref.setValue(String.valueOf(likes));
                likeIV.setEnabled(false);

            }
        });


        likeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.like(v, getPosition());
            }
        });
        /*
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("RecyclerView", "onClick：" + getPosition());
                // DatabaseReference ref =  FirebaseDatabase.getInstance().getReference().child("offers");
                //Log.d("KEY", String.valueOf(ref.getRef(String.valueOf(getPosition())).getKey()));
                MainActivity.recogerIDyLanzarActivity(v, getPosition());
            }
        });*/

    }



    /*public Toolbar getToolbarCard() {
        return toolbarCard;
    }*/

    //public void setToolbarCard(Toolbar toolbarCard) {
        //this.toolbarCard = toolbarCard;
   // }

    public TextView getComentarios() {
        return comentarios;
    }

    public void setComentarios(TextView comentarios) {
        this.comentarios = comentarios;
    }

    public TextView getLikes() {
        return likeTV;
    }

    public void setLikes(TextView likes) {
        this.likeTV = likes;
    }

    public ImageView getImagen() {
        return imagen;
    }

    public void setImagen(ImageView imagen) {
        this.imagen = imagen;
    }

    public TextView getFecha() {
        return fecha;
    }

    public void setFecha(TextView fecha) {
        this.fecha = fecha;
    }





}
