package com.enav.cazaunchollo.cazaunchollo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.zip.Inflater;

public class OfferScrollingActivity extends AppCompatActivity
implements AppBarLayout.OnOffsetChangedListener {

        private int mMaxScrollSize;
        private TextView estado;
        public static String referencia;
        private EditText editText_comment;
        private FirebaseAuth auth;
        ImageView imageView;

        boolean isImageFitToScreen;


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

            LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
            View item = inflater.inflate(R.layout.fragment_offer_description, null);


            imageView = (ImageView) item.findViewById(R.id.imageView3);

            LayoutInflater inflater2 = LayoutInflater.from(getApplicationContext());
            View item2 = inflater.inflate(R.layout.app_bar_main2, null);

            editText_comment = (EditText) item2.findViewById(R.id.editText_comment);

        }

    public static void start(Context c) {
        c.startActivity(new Intent(c, OfferScrollingActivity.class));
    }




    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        appBarLayout.setExpanded(true, false);


    }

    public void modOffer(View view) {
        OfferDescriptionFragment offerDescriptionFragment = new OfferDescriptionFragment();
        Intent intent = new Intent(this, OfferModActivity.class);
        intent.putExtra("referencia", referencia.toString());

        startActivity(intent);

    }

    public void removeOffer(View view) {



        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("referencia", referencia.toString());
        intent.putExtra("confirmarBorrado", true);
        startActivity(intent);




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

