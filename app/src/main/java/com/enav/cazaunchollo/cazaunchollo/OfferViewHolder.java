package com.enav.cazaunchollo.cazaunchollo;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static com.enav.cazaunchollo.cazaunchollo.MainActivity.devolverReferencia;


public class OfferViewHolder extends RecyclerView.ViewHolder{

    // Variables
    public TextView titleCard;
    public TextView subTitleCard;
    public TextView comentarios;
    public TextView likeTV;
    public ImageView imagen;
    public ImageView likeIV;
    public ImageView comentariosico;
    public TextView fecha;
    public CardView idcardview;

    public OfferViewHolder(View v){
        super(v);

        // Inicializacion de las variables
        titleCard = (TextView) itemView.findViewById(R.id.titleCard);
        subTitleCard = (TextView) itemView.findViewById(R.id.subTitleCard);
        comentarios = (TextView)itemView.findViewById(R.id.comentarios);
        likeTV = (TextView)itemView.findViewById(R.id.likeTV);
        imagen = (ImageView)itemView.findViewById(R.id.imagen);
        likeIV = (ImageView)itemView.findViewById(R.id.likeIV);
        fecha = (TextView) itemView.findViewById(R.id.publication_date);
        comentariosico = (ImageView) itemView.findViewById(R.id.comentariosico);
        idcardview = (CardView)itemView.findViewById(R.id.idcardview);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();

        // Al hacer clic sobre el titulo de la tarjeta nos abrira la pantalla de Detalle
        titleCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.recogerIDyLanzarActivity(v, getPosition());
            }
        });

        // Al hacer click sobre la imagen de la tarjeta nos abrira la pantalla de Detalle
        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.recogerIDyLanzarActivity(v, getPosition());
            }
        });

        // Al hacer click sobre el corazon de la tarjeta
        likeIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String referencia = devolverReferencia(getPosition());
                User.addLikesToList(user.getUid(),referencia);
                Offer.addUIDToThisOffer(user.getUid(), referencia);
                Offer.plusLike(referencia);

            }
        });

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

    public TextView getFecha() {
        return fecha;
    }

    public void setFecha(TextView fecha) {
        this.fecha = fecha;
    }





}
