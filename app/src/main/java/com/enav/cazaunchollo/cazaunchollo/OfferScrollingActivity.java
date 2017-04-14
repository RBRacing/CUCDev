package com.enav.cazaunchollo.cazaunchollo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OfferScrollingActivity extends AppCompatActivity
implements AppBarLayout.OnOffsetChangedListener {

        private int mMaxScrollSize;
        private TextView estado;
        private static String referencia;

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
        if (mMaxScrollSize == 0)
            mMaxScrollSize = appBarLayout.getTotalScrollRange();

        int percentage = (Math.abs(i)) * 100 / mMaxScrollSize;

    }

    public void createComment(View view) {

        Log.d("CREATE", "COMMENT");
        Comment c = new Comment("RBRacing", "Pole");

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
