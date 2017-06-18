package com.enav.cazaunchollo.cazaunchollo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.enav.cazaunchollo.cazaunchollo.FirebaseReferences.OFFERS_REFERENCE;

public class FullScreenImageActivity extends AppCompatActivity {

    /* Variables */
    private DatabaseReference ref;
    private ImageView fullScreenImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);
        // Inicializacion de las variables
        fullScreenImageView = (ImageView) findViewById(R.id.fullScreenImageView);

        // Recogemos el intent con la referencia de la oferta
        Intent callingActivityIntent = getIntent();
        Bundle datos = this.getIntent().getExtras();
        final String referencia = datos.getString("referencia");

        if(callingActivityIntent != null){
            // Conexión Firebase
            ref = FirebaseDatabase.getInstance().getReference().child(OFFERS_REFERENCE);
            ref.child(referencia).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Offer offer = dataSnapshot.getValue(Offer.class);
                    try{
                        if(getApplicationContext() !=null){
                            // Aplicamos al imageView la imagen
                            Glide.with(getApplicationContext()).load(offer.getImagen()).fitCenter().into(fullScreenImageView);

                        }
                    }catch (Exception e){}
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });
        }

    }
}
