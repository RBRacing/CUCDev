package com.enav.cazaunchollo.cazaunchollo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener {

    private static final String SENDER_ID = "000";
    /* Variables */
    private RecyclerView recycler;
    private RecyclerView.LayoutManager lManager;
    private RecyclerView mRoomRecyclerView;
    private static DatabaseReference mFirebaseDatabaseReference;
    private static FirebaseRecyclerAdapter<Offer, OfferViewHolder> mFirebaseAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private FirebaseAuth.AuthStateListener authListener;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private TextView username;
    private TextView level;
    private ImageView userPhoto;
    private Boolean borrar;
    private String referencia;
    private int user_level;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user_level = 0;
        borrar = false;
        // Instancía de Firebase
        auth = FirebaseAuth.getInstance();

        // Obtener usuario actual Firebase
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }else{
                    setUserData(user);
                }
            }
        };

        /* Referencias de variables */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        username = (TextView) findViewById(R.id.username);
        level = (TextView) findViewById(R.id.level);

        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Obtener el Recycler
        recycler = (RecyclerView) findViewById(R.id.reciclador);
        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        mRoomRecyclerView = (RecyclerView) findViewById(R.id.reciclador);
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mLinearLayoutManager = new LinearLayoutManager(this);

        //Muestra las tarjetas de arriba a abajo
        mLinearLayoutManager.setStackFromEnd(true);

        mFirebaseAdapter = new FirebaseRecyclerAdapter<Offer, OfferViewHolder>(
                Offer.class,
                R.layout.offer_card,
                OfferViewHolder.class,
                mFirebaseDatabaseReference.child("offers")) {

            @Override
            protected void populateViewHolder(OfferViewHolder viewHolder, Offer model, int position) {
                if(model.getUsersLikeToThisOffer().contains(user.getUid())){
                    viewHolder.likeIV.setColorFilter(Color.BLUE);
                }
                viewHolder.titleCard.setText(model.getNombre());
                viewHolder.subTitleCard.setText("#" + model.getHashtag());
                viewHolder.likeTV.setText(model.getLikes());
                viewHolder.comentarios.setText(model.getComentarios());
                Glide.with(getApplicationContext()).load(model.getImagen()).fitCenter().into(viewHolder.getImagen());
                viewHolder.fecha.setText(model.getFecha());
            }
        };

        mFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int roomCount = mFirebaseAdapter.getItemCount();
                int lastVisiblePosition = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                if (lastVisiblePosition == -1 || (positionStart >= (roomCount - 1) && lastVisiblePosition == (positionStart - 1))) {
                    mRoomRecyclerView.scrollToPosition(positionStart);
                }
            }


        });
        mRoomRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRoomRecyclerView.setAdapter(mFirebaseAdapter);

        Bundle datos = this.getIntent().getExtras();

        if(datos != null){
            referencia = datos.getString("referencia");
            borrar = datos.getBoolean("confirmarBorrado");

            if(referencia != null && borrar){
                removeOffer();
            }
        }


    }

    private void setUserData(FirebaseUser user) {

        // Get a reference to our posts
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child("users").child(user.getUid());

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        final View headerView = navigationView.getHeaderView(0);


        username = (TextView) headerView.findViewById(R.id.username);
        username.setText(user.getEmail());
        // Attach a listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                if(user.getBan() == true){
                    signOut();
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    Toast.makeText(getApplicationContext(), "Esta cuenta se encuentra baneada", Toast.LENGTH_SHORT).show();

                }

                CircleImageView userPhoto;
                userPhoto = (CircleImageView) findViewById(R.id.userPhoto);

                level = (TextView) headerView.findViewById(R.id.level);
                level.setText("Nivel "+String.valueOf(user.getLevel()));
                setUser_level(user.getLevel());

                Glide.with(getApplicationContext()).load(user.getImage()).fitCenter().into(userPhoto);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
        if (id == R.id.action_logout) {
            signOut();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.myProfile) {
            Intent intent = new Intent(this, MyProfile.class);
            startActivity(intent);

        } else if (id == R.id.favorites) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "CAZA UN CHOLLO! Proximamente en Google Play.");
            sendIntent.setType("text/plain");
            startActivity(Intent.createChooser(sendIntent, "Compartir en"));

        } else if (id == R.id.addOffer) {
            if(getUser_level()>1){
                Intent intent = new Intent(this, OfferFormActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(getApplicationContext(), "Necesitas ser mínimo nivel 2 para poder realizar una publicación", Toast.LENGTH_SHORT).show();
            }

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static void recogerIDyLanzarActivity(View v, int id) {
        String referencia = mFirebaseAdapter.getRef(id).getKey().toString();
        Intent intent = new Intent(v.getContext(), OfferScrollingActivity.class);
        intent.putExtra("referencia", referencia);
        v.getContext().startActivity(intent);
    }

    public void removeOffer(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference offerReference = database.getReference(FirebaseReferences.OFFERS_REFERENCE);

        offerReference.child(referencia).removeValue();
    }

    public static String devolverReferencia(int id) {
        String referencia = mFirebaseAdapter.getRef(id).getKey().toString();
       return referencia;
    }

    public static String dameREFOffer(int id){
        String referencia = mFirebaseAdapter.getRef(id).getKey().toString();
        Log.d("referenica", referencia.toString());

    return referencia;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    //sign out method
    public void signOut() {
        auth.signOut();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }

    public int getUser_level() {
        return user_level;
    }

    public void setUser_level(int user_level) {
        this.user_level = user_level;
    }
}
