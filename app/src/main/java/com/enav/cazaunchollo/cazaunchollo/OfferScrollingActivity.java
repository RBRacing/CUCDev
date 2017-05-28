package com.enav.cazaunchollo.cazaunchollo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.zip.Inflater;

public class OfferScrollingActivity extends AppCompatActivity
implements AppBarLayout.OnOffsetChangedListener {

        private int mMaxScrollSize;
        private TextView estado;
        private static String referencia;
        private EditText editText_comment;
        private FirebaseAuth auth;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_offer_scrolling);

            TabLayout tabLayout = (TabLayout) findViewById(R.id.materialup_tabs);
            ViewPager viewPager  = (ViewPager) findViewById(R.id.materialup_viewpager);
            AppBarLayout appbarLayout = (AppBarLayout) findViewById(R.id.materialup_appbar);


            Toolbar toolbar = (Toolbar) findViewById(R.id.materialup_toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    onBackPressed();
                }
            });

            toolbar.setTitle("Caza un Chollo");

            appbarLayout.addOnOffsetChangedListener(this);
            mMaxScrollSize = appbarLayout.getTotalScrollRange();

            viewPager.setAdapter(new TabsAdapter(getSupportFragmentManager()));
            tabLayout.setupWithViewPager(viewPager);

            estado = (TextView) findViewById(R.id.estado);

            referencia = getIntent().getStringExtra("referencia");






        }

    public static void start(Context c) {
        c.startActivity(new Intent(c, OfferScrollingActivity.class));
    }




    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        appBarLayout.setExpanded(true, false);


    }

    public void createComment(View view) {
       // LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        //inflater.inflate(R.layout.app_bar_main2, null);


        android.app.Fragment frag = getFragmentManager().findFragmentById(R.id.reciclador2);
        String co = (String) ((TextView) frag.getView().findViewById(R.id.textView_comment)).getText();

        OfferCommentsFragment offerCommentsFragment = new OfferCommentsFragment();




        // Instancía de Firebase
        auth = FirebaseAuth.getInstance();

        // Obtener usuario actual Firebase
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        String comentario =

             comentario = editText_comment.getText().toString();


        Comment c = new Comment(user.getEmail().toString(), comentario);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("offers").child(referencia).child("comments").push().setValue(c);

    }

    class TabsAdapter extends FragmentPagerAdapter {
        public TabsAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Fragment getItem(int i) {
            switch(i) {
                case 0: return OfferDescriptionFragment.newInstance();
                case 1: return OfferCommentsFragment.newInstance();
            }
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch(position) {
                case 0: return "Descripcion";
                case 1: return "Comentarios";
            }
            return "";
        }
    }

    public static String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }


}

