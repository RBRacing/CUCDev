package com.enav.cazaunchollo.cazaunchollo;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Edgar on 04/06/2017.
 */

public class CallFirebaseDatabase {


    public static void devolverNumDeComentariosParaUnaOferta(final String idOffer){

        final DatabaseReference databaseReference =
                FirebaseDatabase.getInstance().getReference().child("offers").child(idOffer);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap idComments = (HashMap) dataSnapshot.child("comments").getValue();
                int comentarios = (Integer.parseInt((String)dataSnapshot.child("comentarios").getValue()));

                if(idComments.size()>comentarios){
                    comentarios = (Integer.parseInt((String)dataSnapshot.child("comentarios").getValue()))+1;
                    databaseReference.child("comentarios").setValue(String.valueOf(comentarios));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Log.e(TAGLOG, "Error!", databaseError.toException());
            }
        });


    }


}
