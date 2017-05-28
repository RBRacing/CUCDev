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
    static List<String> favorites = new ArrayList<String>();

    public User(String name, String email, String image, String registrationDate, int points, List<String> favorites, int level) {
        this.name = name;
        this.email = email;
        this.image = image;
        this.registrationDate = registrationDate;
        this.points = points;
        this.favorites = favorites;
        this.level = level;
    }

    public User() {

    }

    public static void addLikesToList(final String uid, final String idOffer){

        final DatabaseReference databaseReference =
                FirebaseDatabase.getInstance().getReference()
                        .child("users")
                        .child(uid).child("favorites");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                favorites = (List) dataSnapshot.getValue();

                if(favorites !=null){
                    if(!favorites.contains(idOffer)){
                        favorites.add(idOffer);
                        databaseReference.setValue(favorites);

                    }
                }else{
                    List<String> favorites = new ArrayList<String>();
                    favorites.add(idOffer);
                    databaseReference.setValue(favorites);
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