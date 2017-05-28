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


    public Offer() {
    }

    public Offer(String nombre, String hashtag, String likes, String comentarios, String imagen, String descripcion, String estado, Comment comment, String fecha) {
        this.nombre = nombre;
        this.hashtag = hashtag;
        this.likes = likes;
        this.comentarios = comentarios;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.estado = estado;
        this.comment = comment;
        this.fecha = fecha;
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
}
