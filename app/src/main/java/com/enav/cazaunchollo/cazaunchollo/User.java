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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.enav.cazaunchollo.cazaunchollo.FirebaseReferences.FAVORITES_REFERENCE;
import static com.enav.cazaunchollo.cazaunchollo.FirebaseReferences.LEVEL_REFERENCE;
import static com.enav.cazaunchollo.cazaunchollo.FirebaseReferences.POINTS_REFERENCE;
import static com.enav.cazaunchollo.cazaunchollo.FirebaseReferences.USERS_REFERENCE;

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

    public static void addLikesToList(final String uid, final String idOffer){

        final DatabaseReference databaseReference =
                FirebaseDatabase.getInstance().getReference();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                favorites = (List) dataSnapshot.child(USERS_REFERENCE).child(uid).child(FAVORITES_REFERENCE).getValue();

                if(favorites !=null){
                    if(!favorites.contains(idOffer)){
                        favorites.add(idOffer);
                        databaseReference.child(USERS_REFERENCE).child(uid).child(FAVORITES_REFERENCE).setValue(favorites);

                        Long points = ((Long) dataSnapshot.child(USERS_REFERENCE).child(uid).child(POINTS_REFERENCE).getValue())+1;
                        databaseReference.child(USERS_REFERENCE).child(uid).child(POINTS_REFERENCE).setValue(points);

                    }
                }else{
                    List<String> favorites = new ArrayList<String>();
                    favorites.add(idOffer);
                    databaseReference.child(USERS_REFERENCE).child(uid).child(FAVORITES_REFERENCE).setValue(favorites);

                    Long points = ((Long) dataSnapshot.child(USERS_REFERENCE).child(uid).child(POINTS_REFERENCE).getValue())+1;
                    databaseReference.child(USERS_REFERENCE).child(uid).child(POINTS_REFERENCE).setValue(points);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void levelUp(final String uid){

        final DatabaseReference databaseReference =
                FirebaseDatabase.getInstance().getReference().child(USERS_REFERENCE).child(uid);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long points = (Long) dataSnapshot.child(POINTS_REFERENCE).getValue();
                Long level = (Long) dataSnapshot.child(LEVEL_REFERENCE).getValue();


                if(points !=null && points>=100){
                    databaseReference.child(POINTS_REFERENCE).setValue(points-100);
                    databaseReference.child(LEVEL_REFERENCE).setValue(level+1);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });



    }

    public static List<String> getFavorites() {
        return favorites;
    }

    public static void setFavorites(List<String> favorites) {
        User.favorites = favorites;
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