package com.enav.cazaunchollo.cazaunchollo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class FullScreenImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);


        ImageView fullScreenImageView = (ImageView) findViewById(R.id.fullScreenImageView);

        Intent callingActivityIntent = getIntent();

        if(callingActivityIntent != null){

            Bundle extras = getIntent().getExtras();
            if(extras != null && fullScreenImageView !=null) {
                Bitmap bmp = (Bitmap) extras.getParcelable("imagebitmap");

                //fullScreenImageView.setImageBitmap(bmp);
                Glide.with(this)
                        .load(bmp)
                        .into(fullScreenImageView);
            }

        }

    }
}
