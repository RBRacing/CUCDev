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
    public TextView likes;
    public ImageView imagen;


    public OfferViewHolder(View v){
        super(v);
        toolbarCard = (Toolbar)itemView.findViewById(R.id.toolbarCard);
        comentarios = (TextView)itemView.findViewById(R.id.comentarios);
        likes = (TextView)itemView.findViewById(R.id.likes);
        imagen = (ImageView)itemView.findViewById(R.id.imagen);


        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("RecyclerView", "onClick：" + getPosition());
                // DatabaseReference ref =  FirebaseDatabase.getInstance().getReference().child("offers");
                //Log.d("KEY", String.valueOf(ref.getRef(String.valueOf(getPosition())).getKey()));
                MainActivity.recogerIDyLanzarActivity(v, getPosition());
            }
        });


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
        return likes;
    }

    public void setLikes(TextView likes) {
        this.likes = likes;
    }

    public ImageView getImagen() {
        return imagen;
    }

    public void setImagen(ImageView imagen) {
        this.imagen = imagen;
    }
}
