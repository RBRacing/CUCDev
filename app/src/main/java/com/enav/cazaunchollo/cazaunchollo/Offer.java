package com.enav.cazaunchollo.cazaunchollo;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

import static com.enav.cazaunchollo.cazaunchollo.FirebaseReferences.LIKES_REFERENCE;
import static com.enav.cazaunchollo.cazaunchollo.FirebaseReferences.OFFERS_REFERENCE;
import static com.enav.cazaunchollo.cazaunchollo.FirebaseReferences.POINTS_REFERENCE;
import static com.enav.cazaunchollo.cazaunchollo.FirebaseReferences.UID_CREATOR_REFERENCE;
import static com.enav.cazaunchollo.cazaunchollo.FirebaseReferences.USERS_REFERENCE;
import static com.enav.cazaunchollo.cazaunchollo.FirebaseReferences.USERS_TO_LIKE_THIS_OFFER;


public class Offer {

    // Variables
    private String nombre;
    private String hashtag;
    private String comentarios;
    private String likes;
    private String imagen;
    private String descripcion;
    private Boolean disponible;
    private Comment comment;
    private String fecha;
    static List<String> usersLikeToThisOffer;
    private String color;
    private static Boolean Pasar = true;
    private String enlace;
    private String uid_creator;
    private Boolean visible;

    // Constructor vacio de Oferta
    public Offer() {
    }

    // Constructor de Oferta
    public Offer(String nombre, String hashtag, String comentarios, String likes, String imagen, String descripcion, Boolean disponible, Comment comment, String fecha, String enlace, String uid_creator, List<String> usersLikeToThisOffer, Boolean visible) {
        this.nombre = nombre;
        this.hashtag = hashtag;
        this.comentarios = comentarios;
        this.likes = likes;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.disponible = disponible;
        this.comment = comment;
        this.fecha = fecha;
        this.enlace = enlace;
        this.usersLikeToThisOffer = usersLikeToThisOffer;
        this.uid_creator = uid_creator;
        this.visible = visible;
    }

    // Metodo para añadir el User ID a la oferta
    public static void addUIDToThisOffer(final String uid, final String idOffer){

        final DatabaseReference databaseReference =
                FirebaseDatabase.getInstance().getReference()
                        .child(OFFERS_REFERENCE)
                        .child(idOffer).child(USERS_TO_LIKE_THIS_OFFER);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                usersLikeToThisOffer = (List) dataSnapshot.getValue();

                if(usersLikeToThisOffer !=null){
                    if(!usersLikeToThisOffer.contains(uid)){
                        usersLikeToThisOffer.add(uid);
                        databaseReference.setValue(usersLikeToThisOffer);
                        Pasar = false;

                    }
                }else{
                    List<String> usersLikeToThisOffer = new ArrayList<String>();
                    usersLikeToThisOffer.add(uid);
                    databaseReference.setValue(usersLikeToThisOffer);
                    Pasar = false;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    // Método para sumar un ME GUSTA a la oferta
    public static void plusLike(final String idOffer){

        final DatabaseReference databaseReference =
                FirebaseDatabase.getInstance().getReference();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String likes = (String) dataSnapshot.child(OFFERS_REFERENCE).child(idOffer).child(LIKES_REFERENCE).getValue();

                String uid_creator = (String) dataSnapshot.child(OFFERS_REFERENCE).child(idOffer).child(UID_CREATOR_REFERENCE).getValue();

                Long points_uid_creator = (Long) dataSnapshot.child(USERS_REFERENCE).child(uid_creator).child(POINTS_REFERENCE).getValue();

                if(likes !=null && !Pasar){
                    Pasar=true;
                    int likesOfString = Integer.valueOf(likes);
                    int calcular = likesOfString+1;
                    databaseReference.child(OFFERS_REFERENCE).child(idOffer).child(LIKES_REFERENCE).setValue(String.valueOf(calcular));
                    calcular = calcular*5;
                    points_uid_creator = points_uid_creator + calcular;

                    databaseReference.child(USERS_REFERENCE).child(uid_creator).child(POINTS_REFERENCE).setValue(points_uid_creator);
                    User.levelUp(uid_creator);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });



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

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
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

    public String getEnlace() {
        return enlace;
    }

    public void setEnlace(String enlace) {
        this.enlace = enlace;
    }

    public String getUid_creator() {
        return uid_creator;
    }

    public void setUid_creator(String uid_creator) {
        this.uid_creator = uid_creator;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
}
