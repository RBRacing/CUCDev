package com.enav.cazaunchollo.cazaunchollo;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.IconRoundCornerProgressBar;
import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class MyProfile extends AppCompatActivity {

    private static final String TAG = "ViewDatabase";
    private static final int GALLERY_INTENT = 1;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private  String userID;
    private TextView user_profile_name;
    private TextView user_profile_email;
    private ProgressDialog progressDialog;
    private CircleImageView user_profile_photo;
    private StorageReference mStorage;
    private Uri descargarFoto;
    private String urifoto = "http://";
    private RoundCornerProgressBar level_progressBar;
    private TextView level_indicator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        user_profile_name= (TextView) findViewById(R.id.user_profile_name);
        user_profile_email = (TextView) findViewById(R.id.user_profile_email);
        user_profile_photo = (CircleImageView) findViewById(R.id.user_profile_photo);
        level_progressBar = (RoundCornerProgressBar) findViewById(R.id.level_progressBar);
        level_indicator = (TextView) findViewById(R.id.level_indicator);
        mStorage = FirebaseStorage.getInstance().getReference();
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference().child("users");
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void showData(DataSnapshot dataSnapshot) {

        String name = (String) dataSnapshot.child(userID).child("name").getValue();
        String email = (String) dataSnapshot.child(userID).child("email").getValue();
        String userPhoto = (String) dataSnapshot.child(userID).child("image").getValue();
        Long points = (Long) dataSnapshot.child(userID).child("points").getValue();
        user_profile_name.setText(name);
        user_profile_email.setText(email);
        Glide.with(getApplicationContext()).load(userPhoto).fitCenter().into(user_profile_photo);
        level_indicator.setText(String.valueOf(points)+"/100");
        level_progressBar.setProgress(points.intValue());
        level_progressBar.setProgressColor(Color.parseColor("#4abe5b"));
        level_progressBar.setProgressBackgroundColor(Color.parseColor("#7dd28a"));
        level_progressBar.setMax(100);
        level_progressBar.setRadius(0);

    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    public void changeUserPhoto(View view) {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_INTENT);

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
            StorageReference filePath = mStorage.child("UsserPhotos").child(uri.getLastPathSegment());

            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    progressDialog.dismiss();
                    descargarFoto = taskSnapshot.getDownloadUrl();
                    urifoto = descargarFoto.toString();
                    Glide.with(MyProfile.this).load(descargarFoto).fitCenter().centerCrop().into(user_profile_photo);
                    Toast.makeText(getApplicationContext(), "Imagen cargada correctamente", Toast.LENGTH_SHORT).show();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    final DatabaseReference offerReference = database.getReference("users").child(userID).child("image");
                    offerReference.setValue(urifoto);
                }
            });
        }

    }

    public void changeUsername(View view) {

        View view_change_username = LayoutInflater.from(MyProfile.this).inflate(R.layout.change_username, null);
        final EditText input_new_username = (EditText) view_change_username.findViewById(R.id.input_new_username);

        AlertDialog.Builder builder = new AlertDialog.Builder(MyProfile.this);

        builder.setMessage("Cambiar nombre de Usuario")
                .setView(view_change_username)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(!input_new_username.equals("")){
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            final DatabaseReference offerReference = database.getReference("users").child(userID).child("name");
                            offerReference.setValue(input_new_username.getText().toString());
                        }

                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

    }

    public void showHelpHowToGetMorePoints(View view) {

        View view_dialog_how_to = LayoutInflater.from(MyProfile.this).inflate(R.layout.dialog_how_to_get_more_points, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(MyProfile.this);

        builder.setMessage("AYUDA")
                .setView(view_dialog_how_to)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();

    }






}