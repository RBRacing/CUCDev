package com.enav.cazaunchollo.cazaunchollo;

import android.net.Uri;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class Offer {

    private String nombre;
    private String hashtag;
    private String comentarios;
    private String likes;
    private String imagen;
    private String descripcion;
    private String estado;
    private Comment comment;
    private String fecha;
    private static List<String> usersLikeToThisOffer;
    private String color;


    public Offer() {
    }

    public Offer(String nombre, String hashtag, String comentarios, String likes, String imagen, String descripcion, String estado, Comment comment, String fecha, List<String> usersLikeToThisOffer) {
        this.nombre = nombre;
        this.hashtag = hashtag;
        this.comentarios = comentarios;
        this.likes = likes;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.estado = estado;
        this.comment = comment;
        this.fecha = fecha;
        this.usersLikeToThisOffer = usersLikeToThisOffer;
        ;
    }

    public static void addUIDToThisOffer(final String uid, final String idOffer){

        final DatabaseReference databaseReference =
                FirebaseDatabase.getInstance().getReference()
                        .child("offers")
                        .child(idOffer).child("usersLikeToThisOffer");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                usersLikeToThisOffer = (List) dataSnapshot.getValue();

                if(usersLikeToThisOffer !=null){
                    if(!usersLikeToThisOffer.contains(uid)){
                        usersLikeToThisOffer.add(uid);
                        databaseReference.setValue(usersLikeToThisOffer);

                    }
                }else{
                    List<String> usersLikeToThisOffer = new ArrayList<String>();
                    usersLikeToThisOffer.add(uid);
                    databaseReference.setValue(usersLikeToThisOffer);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Log.e(TAGLOG, "Error!", databaseError.toException());
            }
        });
    }

    public boolean usersLikeToThisOffer(String uid, final String idOffer){

        boolean likethis = false;

        final DatabaseReference databaseReference =
                FirebaseDatabase.getInstance().getReference()
                        .child("offers")
                        .child(idOffer).child("usersLikeToThisOffer");



        if(usersLikeToThisOffer !=null){
            if(usersLikeToThisOffer.contains(uid)){
                likethis=true;
            }
        }

        return likethis;

    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public List<String> getUsersLikeToThisOffer() {
        return usersLikeToThisOffer;
    }

    public void setUsersLikeToThisOffer(List<String> usersLikeToThisOffer) {
        this.usersLikeToThisOffer = usersLikeToThisOffer;
    }
}
