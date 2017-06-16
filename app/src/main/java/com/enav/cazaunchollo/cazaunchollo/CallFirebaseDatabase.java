package com.enav.cazaunchollo.cazaunchollo;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.HashMap;

import static com.enav.cazaunchollo.cazaunchollo.FirebaseReferences.COMENTARIOS_REFERENCE;
import static com.enav.cazaunchollo.cazaunchollo.FirebaseReferences.COMMENTS_REFERENCE;
import static com.enav.cazaunchollo.cazaunchollo.FirebaseReferences.LEVEL_REFERENCE;
import static com.enav.cazaunchollo.cazaunchollo.FirebaseReferences.OFFERS_REFERENCE;
import static com.enav.cazaunchollo.cazaunchollo.FirebaseReferences.USERS_REFERENCE;


public class CallFirebaseDatabase {


    // Método para sumar los comentarios a una oferta
    public static void devolverNumDeComentariosParaUnaOferta(final String idOffer){

        final DatabaseReference databaseReference =
                FirebaseDatabase.getInstance().getReference().child(OFFERS_REFERENCE).child(idOffer);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap idComments = (HashMap) dataSnapshot.child(COMMENTS_REFERENCE).getValue();
                int comentarios = (Integer.parseInt((String)dataSnapshot.child(COMENTARIOS_REFERENCE).getValue()));

                if(idComments.size()>comentarios){
                    comentarios = (Integer.parseInt((String)dataSnapshot.child(COMENTARIOS_REFERENCE).getValue()))+1;
                    databaseReference.child(COMENTARIOS_REFERENCE).setValue(String.valueOf(comentarios));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void getRewardNewUserInBeta(final String uid){

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(USERS_REFERENCE).child(uid);
        databaseReference.child(LEVEL_REFERENCE).setValue(2);


    }

}
