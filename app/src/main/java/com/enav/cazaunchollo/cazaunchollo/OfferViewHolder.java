package com.enav.cazaunchollo.cazaunchollo;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Edgar on 25/03/2017.
 */

public class OfferViewHolder extends RecyclerView.ViewHolder{
    public Toolbar toolbarCard;
    public TextView comentarios;
    public TextView likeTV;
    public ImageView imagen;
    public ImageView likeIV;
    DatabaseReference dbRef;


    public OfferViewHolder(View v){

        super(v);
        toolbarCard = (Toolbar)itemView.findViewById(R.id.toolbarCard);
        comentarios = (TextView)itemView.findViewById(R.id.comentarios);
        likeTV = (TextView)itemView.findViewById(R.id.likeTV);
        imagen = (ImageView)itemView.findViewById(R.id.imagen);
        likeIV = (ImageView)itemView.findViewById(R.id.likeIV);

        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.recogerIDyLanzarActivity(v, getPosition());
            }
        });
        likeIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int suma = Integer.parseInt(likeTV.getText().toString()) + 1;
                //likeTV.setText(String.valueOf(suma));


                //DatabaseReference ref =  FirebaseDatabase.getInstance().getReference().child("offers");
                //String referencia = ref.getRef('2').getKey().toString();
                //getRef(id).getKey().toString()
                // Log.d("KEY", ref.getRef(getPosition()).getKey().toString());



                MainActivity.like(v, getPosition());
            }
        });

        /*
        likeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.like(v, getPosition());
            }
        });*/
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

    public Toolbar getToolbarCard() {
        return toolbarCard;
    }

    public void setToolbarCard(Toolbar toolbarCard) {
        this.toolbarCard = toolbarCard;
    }

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

}
