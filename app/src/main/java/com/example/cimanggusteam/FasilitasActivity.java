package com.example.cimanggusteam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class FasilitasActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    ViewFlipper vi_flipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fasilitas);

        mToolbar = (Toolbar) findViewById(R.id.fasi_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Fasilitas");

        int images[]= {R.drawable.slide1, R.drawable.slide2, R.drawable.slide3, R.drawable.slide4, R.drawable.slide5};
        vi_flipper = findViewById(R.id.vi_flipper);

        for(int image: images){
            flipperImager(image);
        }
    }

    public void flipperImager(int image){
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);

        vi_flipper.addView(imageView);
        vi_flipper.setFlipInterval(2000);
        vi_flipper.setAutoStart(true);

        //animasi

        vi_flipper.setInAnimation(this, android.R.anim.slide_in_left);
        vi_flipper.setOutAnimation(this, android.R.anim.slide_out_right);


    }
}
