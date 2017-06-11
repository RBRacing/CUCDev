package com.enav.cazaunchollo.cazaunchollo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

import static com.enav.cazaunchollo.cazaunchollo.FirebaseReferences.AVAILABLE_REFERENCE;
import static com.enav.cazaunchollo.cazaunchollo.FirebaseReferences.DESCRIPTION_REFERENCE;
import static com.enav.cazaunchollo.cazaunchollo.FirebaseReferences.HASHTAG_REFERENCE;
import static com.enav.cazaunchollo.cazaunchollo.FirebaseReferences.IMAGE_REFERENCE;
import static com.enav.cazaunchollo.cazaunchollo.FirebaseReferences.LINK_REFERENCE;
import static com.enav.cazaunchollo.cazaunchollo.FirebaseReferences.NAME_REFERENCE;
import static com.enav.cazaunchollo.cazaunchollo.FirebaseReferences.OFFERS_REFERENCE;

public class OfferModActivity extends AppCompatActivity {
    private DatabaseReference ref;
    private EditText title;
    private TextView id_offer;
    private EditText shop;
    private EditText description;
    private EditText link;
    private CheckBox status;
    private ImageView imageLoad;
    private static final int GALLERY_INTENT = 1;
    private Uri descargarFoto;
    private String urifoto = "http://";
    private ProgressDialog progressDialog;
    private StorageReference mStorage;
    private Button button_mod_offer;
    private Button subirImagen;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_mod);

        title = (EditText) findViewById(R.id.title) ;
        shop = (EditText) findViewById(R.id.shop);
        description = (EditText) findViewById(R.id.description);
        link = (EditText) findViewById(R.id.link);
        status = (CheckBox) findViewById(R.id.status);
        imageLoad = (ImageView)findViewById(R.id.imageLoadMod);
        button_mod_offer = (Button) findViewById(R.id.button_mod_offer);
        subirImagen = (Button) findViewById(R.id.subirImagen);

        progressDialog = new ProgressDialog(this);
        mStorage = FirebaseStorage.getInstance().getReference();

        id_offer = (TextView) findViewById(R.id.id_offer);

        Bundle datos = this.getIntent().getExtras();
        final String referencia = datos.getString("referencia");

        id_offer.setText("ID: "+ referencia.toString());


        // Conexión Firebase
        ref = FirebaseDatabase.getInstance().getReference().child(OFFERS_REFERENCE);

        ref.child(referencia).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Offer offer = dataSnapshot.getValue(Offer.class);
                title.setText(offer.getNombre());
                shop.setText(offer.getHashtag());
                description.setText(offer.getDescripcion());
                link.setText(offer.getEnlace());
                status.setChecked(offer.getDisponible());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        subirImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);
            }
        });

        final Comment comment = new Comment();


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference offerReference = database.getReference(OFFERS_REFERENCE);



        button_mod_offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                offerReference.child(referencia).child(NAME_REFERENCE).setValue(title.getText().toString());
                offerReference.child(referencia).child(HASHTAG_REFERENCE).setValue(shop.getText().toString());
                offerReference.child(referencia).child(DESCRIPTION_REFERENCE).setValue(description.getText().toString());
                offerReference.child(referencia).child(AVAILABLE_REFERENCE).setValue(status.isChecked());
                offerReference.child(referencia).child(LINK_REFERENCE).setValue(link.getText().toString());
                if(!urifoto.equals("http://")){
                    offerReference.child(referencia).child(IMAGE_REFERENCE).setValue(urifoto);
                }


                Toast.makeText(getApplicationContext(), "Oferta modificada", Toast.LENGTH_SHORT).show();
            }
        });

    }
    @SuppressWarnings("VisibleForTests")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_INTENT && resultCode == RESULT_OK){

            progressDialog.setTitle("Subiendo foto");
            progressDialog.setMessage("Subiendo foto a Firebase");
            progressDialog.setCancelable(false);
            progressDialog.show();

            Uri uri = data.getData();
            StorageReference filePath = mStorage.child("OfferPhotos").child(uri.getLastPathSegment());

            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    progressDialog.dismiss();


                    descargarFoto = taskSnapshot.getDownloadUrl();
                    urifoto = descargarFoto.toString();

                    Glide.with(OfferModActivity.this).load(descargarFoto).fitCenter().centerCrop().into(imageLoad);

                    Toast.makeText(getApplicationContext(), "Imagen cargada correctamente", Toast.LENGTH_SHORT).show();
                    subirImagen.setText("Imagen Cargada");
                }
            });
        }

    }
}
