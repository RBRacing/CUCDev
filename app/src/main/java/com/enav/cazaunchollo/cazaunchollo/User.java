package com.enav.cazaunchollo.cazaunchollo;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

@IgnoreExtraProperties
public class User {

    private String name;
    private String email;
    private String image;
    private String registrationDate;
    private int points;
    private int level;
    private Boolean ban;
    static List<String> favorites = new ArrayList<String>();
    static List<String> offerLikeToThisOffer = new ArrayList<String>();


    public User(String name, String email, String image, String registrationDate, int points, List<String> favorites, int level, boolean ban) {
        this.name = name;
        this.email = email;
        this.image = image;
        this.registrationDate = registrationDate;
        this.points = points;
        this.favorites = favorites;
        this.level = level;
        this.ban = ban;
    }

    public User() {

    }

    // Tengo que pasarle el uid del creador de la oferta
    public static void addLikesToList(final String uid, final String idOffer){

        final DatabaseReference databaseReference =
                FirebaseDatabase.getInstance().getReference();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                favorites = (List) dataSnapshot.child("users").child(uid).child("favorites").getValue();

                //offerLikeToThisOffer = (List) dataSnapshot.child("offers").child(idOffer).child("usersLikeToThisOffer").getValue();

                if(favorites !=null){
                    if(!favorites.contains(idOffer)){
                        favorites.add(idOffer);
                        databaseReference.child("users").child(uid).child("favorites").setValue(favorites);

                        Long points = ((Long) dataSnapshot.child("users").child(uid).child("points").getValue())+1;
                        databaseReference.child("users").child(uid).child("points").setValue(points);

                    }
                }else{
                    List<String> favorites = new ArrayList<String>();
                    favorites.add(idOffer);
                    databaseReference.child("users").child(uid).child("favorites").setValue(favorites);

                    Long points = ((Long) dataSnapshot.child("users").child(uid).child("points").getValue())+1;
                    databaseReference.child("users").child(uid).child("points").setValue(points);
                }

                /*
                if(offerLikeToThisOffer !=null){
                    if(!offerLikeToThisOffer.contains(uid)){
                        offerLikeToThisOffer.add(uid);
                        databaseReference.child("offers").child(idOffer).setValue(offerLikeToThisOffer);
                    }
                }else{
                    List<String> offerLikeToThisOffer = new ArrayList<String>();
                    offerLikeToThisOffer.add(uid);
                    databaseReference.child("offers").child(idOffer).setValue(offerLikeToThisOffer);
                }*/


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Log.e(TAGLOG, "Error!", databaseError.toException());
            }
        });
    }

    public static void addPointsToCreator(final String uid){

        final DatabaseReference databaseReference =
                FirebaseDatabase.getInstance().getReference()
                        .child("users")
                        .child(uid).child("points");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long points = (Long) dataSnapshot.getValue();

                if(points !=null){
                    points = ((Long) dataSnapshot.child("points").getValue())+20;
                    databaseReference.child("points").setValue(points);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Log.e(TAGLOG, "Error!", databaseError.toException());
            }
        });
    }



    public static boolean likeThis(String uid, final String idOffer){

        boolean likethis = false;

        final DatabaseReference databaseReference =
                FirebaseDatabase.getInstance().getReference()
                        .child("users")
                        .child(uid).child("favorites");



                if(favorites !=null){
                    if(favorites.contains(idOffer)){
                        likethis=true;
                    }
                }

        return likethis;

    }


    public Boolean getBan() {
        return ban;
    }

    public void setBan(Boolean ban) {
        this.ban = ban;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public List<String> getList() {
        return favorites;
    }

    public void setList(List<String> list) {
        this.favorites = list;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}