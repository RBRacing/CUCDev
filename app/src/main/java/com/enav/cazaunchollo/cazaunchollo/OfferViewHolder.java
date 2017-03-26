package com.enav.cazaunchollo.cazaunchollo;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Edgar on 25/03/2017.
 */

public class OfferViewHolder extends RecyclerView.ViewHolder {
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

    }
}
