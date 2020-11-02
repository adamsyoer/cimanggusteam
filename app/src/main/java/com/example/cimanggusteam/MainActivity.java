package com.example.cimanggusteam;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Toolbar mToolbar;
    private ImageButton mInformasibtn;
    private ImageButton mAntrianbtn;
    private ImageButton mFasilitasbtn;
    private ImageButton mPelayananbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        mToolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Cimanggu Steam");



        mInformasibtn = (ImageButton) findViewById(R.id.imagebtn1);

        mInformasibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent info_intent = new Intent(MainActivity.this, InformasiActivity.class);
                startActivity(info_intent);

            }
        });

        mAntrianbtn = (ImageButton) findViewById(R.id.imagebtn4);
        mAntrianbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent antri_intent = new Intent(MainActivity.this, RiwayatActivity.class);
                startActivity(antri_intent);
            }
        });

        mFasilitasbtn = (ImageButton) findViewById(R.id.imagebtn2);
        mFasilitasbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fasili_intent = new Intent(MainActivity.this, FasilitasActivity.class);
                startActivity(fasili_intent);
            }
        });

        mPelayananbtn = (ImageButton) findViewById(R.id.imagebtn3);
        mPelayananbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pela_intent = new Intent(MainActivity.this, AntrianActivity.class);
                startActivity(pela_intent);
            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null){

            sendToStart();
        }

    }


    private void sendToStart() {

        Intent startIntent = new Intent(MainActivity.this, StarActivity.class);
        startActivity(startIntent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId()== R.id.main_aboutbtn){
            Intent aboutintent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(aboutintent);
        }

        if (item.getItemId()== R.id.main_logoutbtn){

            FirebaseAuth.getInstance().signOut();
            sendToStart();
        }

        return  true;
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder =new AlertDialog.Builder(this);

        builder.setMessage("Apakah anda yakin ingin keluar")
                .setCancelable(false)
                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        MainActivity.super.onBackPressed();

                    }


                })


                .setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}
