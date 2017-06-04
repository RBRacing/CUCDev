package com.enav.cazaunchollo.cazaunchollo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OfferFormActivity extends AppCompatActivity {

    private Button button_post_offer;
    private Button mUploadBtn;
    private StorageReference mStorage;
    private ImageView mImageView;
    private ProgressDialog progressDialog;
    private static final int GALLERY_INTENT = 1;
    private Uri descargarFoto;
    private String urifoto = "http://";
    private FirebaseAuth mAuth;


    private EditText input_title;
    private EditText input_shop;
    private EditText input_description;
    private CheckBox checkBox_status;
    private EditText input_link;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_form);

        mUploadBtn = (Button) findViewById(R.id.btnSubir);
        button_post_offer = (Button) findViewById(R.id.button_post_offer);
        mImageView = (ImageView) findViewById(R.id.imageLoad);
        mStorage = FirebaseStorage.getInstance().getReference();
        progressDialog = new ProgressDialog(this);

        input_title = (EditText) findViewById(R.id.input_title);
        input_shop = (EditText) findViewById(R.id.input_shop);
        input_description = (EditText) findViewById(R.id.input_description);
        checkBox_status = (CheckBox) findViewById(R.id.checkBox_status);
        input_link = (EditText) findViewById(R.id.input_link);


       mUploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);
            }
        });

        final Comment comment = new Comment();


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference offerReference = database.getReference(FirebaseReferences.OFFERS_REFERENCE);


        button_post_offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DateFormat dateFormat = new DateFormat();
                String fecha = dateFormat.devolverFecha();
                List<String> usersLikeToThisOffer = new ArrayList<String>();
                usersLikeToThisOffer.add("");

                //SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
                //String fecha = formateador.format(new Date());
                //Log.d("FECHA", fecha);

                mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();

                Offer offer = new Offer(input_title.getText().toString(), input_shop.getText().toString(), "0", "0", urifoto, input_description.getText().toString(), checkBox_status.isChecked(), comment, fecha, input_link.getText().toString() ,user.getUid(),usersLikeToThisOffer);
                offerReference.push().setValue(offer);

                Toast.makeText(getApplicationContext(), "Oferta publicada", Toast.LENGTH_SHORT).show();
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

                    Glide.with(OfferFormActivity.this).load(descargarFoto).fitCenter().centerCrop().into(mImageView);

                    Toast.makeText(getApplicationContext(), "Imagen cargada correctamente", Toast.LENGTH_SHORT).show();
                    mUploadBtn.setText("Imagen Cargada");
                }
            });
        }

    }
}
