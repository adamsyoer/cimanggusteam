package com.example.cimanggusteam;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import com.example.cimanggusteam.model.Antrian;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@RequiresApi(api = Build.VERSION_CODES.N)
public class AntrianActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private Button jama,jamb,jamc,jamd,jame,jamf,jamg,jamh,jami;
    private FirebaseAuth auth;
    private DatabaseReference database ,reference;
    private ProgressDialog mBokingProgress;
    RadioButton Motor, Moge, Mobil;
    private DatabaseReference mDatabase;
    Long maxid = Long.valueOf(0);
    public static final String inputFormat = "HH:mm";
    private String GetUserID;

    private Date dateCompareOne;
    private Date dateCompareTwo;

    private String jam9 = "9:00";
    private String compareStringTwo = "1:45";
    private Handler handler = new Handler();
    private FirebaseDatabase getDatabase;
    private DatabaseReference getRefenence;
    private ArrayList<Antrian> antrians;
    private ProgressDialog Progress;
    private ArrayList<Antrian> listAntrian;
    private Context context;

    private String getWaktu() {
        DateFormat dateFormat = new SimpleDateFormat("HHmm");
        Date date = new Date();
        return dateFormat.format(date);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_antrian);

        Progress = new ProgressDialog(this);
        mToolbar = (Toolbar) findViewById(R.id.antrian_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Antrian");
        jama = (Button) findViewById(R.id.jama);
        jamb = (Button) findViewById(R.id.jamb);
        jamc = (Button) findViewById(R.id.jamc);
        jamd = (Button) findViewById(R.id.jamd);
        jame = (Button) findViewById(R.id.jame);
        jamf = (Button) findViewById(R.id.jamf);
        jamg = (Button) findViewById(R.id.jamg);
        jamh = (Button) findViewById(R.id.jamh);
        jami = (Button) findViewById(R.id.jami);
        auth = FirebaseAuth.getInstance();
        final EditText Catatan = (EditText) findViewById(R.id.editText_catatan);
        final EditText Platkendaraan = (EditText) findViewById(R.id.editText_platkendaraan);
        RadioGroup rg = (RadioGroup) findViewById(R.id.stateRadioGroup);
        Motor = (RadioButton)findViewById(R.id.radioButton);
        Moge = (RadioButton)findViewById(R.id.radioButton2);
        Mobil = (RadioButton)findViewById(R.id.radioButton3);
        mBokingProgress = new ProgressDialog(this);
        database = FirebaseDatabase.getInstance().getReference().child("Antrian");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                    maxid=(dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        int jam9 = 900;
        int jam10 = 1000;
        int jam11 = 1100;
        int jam12 = 1200;
        int jam13 = 1300;
        int jam14 = 1400;
        int jam15 = 1500;
        int jam16 = 1600;
        int jam17 = 1700;
        int waktusekarang = Integer.parseInt(getWaktu());
        if (jam9<waktusekarang){
            jama.setEnabled(false);
        } if (jam10<waktusekarang){
            jamb.setEnabled(false);
        } if (jam11<waktusekarang){
            jamc.setEnabled(false);
        } if (jam12<waktusekarang){
            jamd.setEnabled(false);
        } if (jam13<waktusekarang){
            jame.setEnabled(false);
        } if (jam14<waktusekarang){
            jamf.setEnabled(false);
        } if (jam15<waktusekarang){
            jamg.setEnabled(false);
        } if (jam16<waktusekarang){
            jamh.setEnabled(false);
        } if (jam17<waktusekarang){
            jami.setEnabled(false);
        }
        Log.e("Waktu Sekarang", String.valueOf(waktusekarang));

        database = FirebaseDatabase.getInstance().getReference();



        final FirebaseUser user = auth.getCurrentUser();
        GetUserID = user.getUid();

        getDatabase = FirebaseDatabase.getInstance();
        getRefenence = getDatabase.getReference();

        final String[] selectedText = {new String()};

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radioButton:
                        selectedText[0] = "motor";
                        break;
                    case R.id.radioButton2:
                        selectedText[0] = "moge";
                        break;
                    case R.id.radioButton3:
                        selectedText[0] = "mobil";
                        // do operations specific to this selection
                        break;
                }
            }
        });

        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Antrian")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Inisialisasi ArrayList
                        antrians = new ArrayList<>();

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            //Mapping data pada DataSnapshot ke dalam objek mahasiswa
                            Antrian antrian = snapshot.getValue(Antrian.class);

                            if (antrian.getWaktusteam().equals("09.00")){
                                jama.setEnabled(false);
                            }else if (antrian.getWaktusteam().equals("10.00")){
                                jamb.setEnabled(false);
                            }else if (antrian.getWaktusteam().equals("11.00")){
                                jamc.setEnabled(false);
                            }else if (antrian.getWaktusteam().equals("12.00")){
                                jamd.setEnabled(false);
                            }else if (antrian.getWaktusteam().equals("13.00")){
                                jame.setEnabled(false);
                            }else if (antrian.getWaktusteam().equals("14.00")){
                                jamf.setEnabled(false);
                            }else if (antrian.getWaktusteam().equals("15.00")){
                                jamg.setEnabled(false);
                            }else if (antrian.getWaktusteam().equals("16.00")){
                                jamh.setEnabled(false);
                            }else if (antrian.getWaktusteam().equals("17.00")){
                                jami.setEnabled(false);
                            }

                            Log.e("AntrianActivity", antrian.getWaktusteam());
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
              /*
                Kode ini akan dijalankan ketika ada error dan
                pengambilan data error tersebut lalu memprint error nya
                ke LogCat
               */
                        Toast.makeText(getApplicationContext(),"Data Gagal Dimuat", Toast.LENGTH_LONG).show();
                        Log.e("AntrianActivity", databaseError.getDetails()+" "+databaseError.getMessage());
                    }
                });




        jama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String jeniskendaraaan = selectedText[0];
                if(!isEmpty(jeniskendaraaan) && !isEmpty(Catatan.getText().toString()) && !isEmpty(Platkendaraan.getText().toString()) ) {
                    Progress.setTitle("Memasukkan Data");
                    Progress.setMessage("Tunggu Yaa");
                    Progress.setCanceledOnTouchOutside(false);
                    Progress.show();
                    antriana(user.getUid(),Catatan.getText().toString(),jeniskendaraaan,Platkendaraan.getText().toString(),user.getEmail(),"09.00");
                }else {
                    Snackbar.make(findViewById(R.id.jama), "Data Antrian tidak boleh kosong", Snackbar.LENGTH_LONG).show();

                    InputMethodManager imm = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(
                            Catatan.getWindowToken(), 0);
                }

            }
        });

        jamb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String jeniskendaraaan = selectedText[0];
                if(!isEmpty(jeniskendaraaan) && !isEmpty(Catatan.getText().toString()) && !isEmpty(Platkendaraan.getText().toString()) ) {
                    Progress.setTitle("Memasukkan Data");
                    Progress.setMessage("Tunggu Yaa");
                    Progress.setCanceledOnTouchOutside(false);
                    Progress.show();
                    antriana(user.getUid(),Catatan.getText().toString(),jeniskendaraaan,Platkendaraan.getText().toString(),user.getEmail(),"10.00");
                }else {
                    Snackbar.make(findViewById(R.id.jama), "Data Antrian tidak boleh kosong", Snackbar.LENGTH_LONG).show();

                    InputMethodManager imm = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(
                            Catatan.getWindowToken(), 0);
                }

            }
        });

        jamc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String jeniskendaraaan = selectedText[0];
                if(!isEmpty(jeniskendaraaan) && !isEmpty(Catatan.getText().toString()) && !isEmpty(Platkendaraan.getText().toString()) ) {
                    Progress.setTitle("Memasukkan Data");
                    Progress.setMessage("Tunggu Yaa");
                    Progress.setCanceledOnTouchOutside(false);
                    Progress.show();
                    antriana(user.getUid(),Catatan.getText().toString(),jeniskendaraaan,Platkendaraan.getText().toString(),user.getEmail(),"11.00");
                }else {
                    Snackbar.make(findViewById(R.id.jama), "Data Antrian tidak boleh kosong", Snackbar.LENGTH_LONG).show();

                    InputMethodManager imm = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(
                            Catatan.getWindowToken(), 0);
                }

            }
        });

        jamd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String jeniskendaraaan = selectedText[0];
                if(!isEmpty(jeniskendaraaan) && !isEmpty(Catatan.getText().toString()) && !isEmpty(Platkendaraan.getText().toString()) ) {
                    Progress.setTitle("Memasukkan Data");
                    Progress.setMessage("Tunggu Yaa");
                    Progress.setCanceledOnTouchOutside(false);
                    Progress.show();
                    antriana(user.getUid(),Catatan.getText().toString(),jeniskendaraaan,Platkendaraan.getText().toString(),user.getEmail(),"12.00");
                }else {
                    Snackbar.make(findViewById(R.id.jama), "Data Antrian tidak boleh kosong", Snackbar.LENGTH_LONG).show();

                    InputMethodManager imm = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(
                            Catatan.getWindowToken(), 0);
                }

            }
        });

        jame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String jeniskendaraaan = selectedText[0];
                if(!isEmpty(jeniskendaraaan) && !isEmpty(Catatan.getText().toString()) && !isEmpty(Platkendaraan.getText().toString()) ) {
                    Progress.setTitle("Memasukkan Data");
                    Progress.setMessage("Tunggu Yaa");
                    Progress.setCanceledOnTouchOutside(false);
                    Progress.show();
                    antriana(user.getUid(),Catatan.getText().toString(),jeniskendaraaan,Platkendaraan.getText().toString(),user.getEmail(),"13.00");
                }else {
                    Snackbar.make(findViewById(R.id.jama), "Data Antrian tidak boleh kosong", Snackbar.LENGTH_LONG).show();

                    InputMethodManager imm = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(
                            Catatan.getWindowToken(), 0);
                }

            }
        });

        jamf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String jeniskendaraaan = selectedText[0];
                if(!isEmpty(jeniskendaraaan) && !isEmpty(Catatan.getText().toString()) && !isEmpty(Platkendaraan.getText().toString()) ) {
                    Progress.setTitle("Memasukkan Data");
                    Progress.setMessage("Tunggu Yaa");
                    Progress.setCanceledOnTouchOutside(false);
                    Progress.show();
                    antriana(user.getUid(),Catatan.getText().toString(),jeniskendaraaan,Platkendaraan.getText().toString(),user.getEmail(),"14.00");
                }else {
                    Snackbar.make(findViewById(R.id.jama), "Data Antrian tidak boleh kosong", Snackbar.LENGTH_LONG).show();

                    InputMethodManager imm = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(
                            Catatan.getWindowToken(), 0);
                }

            }
        });

        jamg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String jeniskendaraaan = selectedText[0];
                if(!isEmpty(jeniskendaraaan) && !isEmpty(Catatan.getText().toString()) && !isEmpty(Platkendaraan.getText().toString()) ) {
                    Progress.setTitle("Memasukkan Data");
                    Progress.setMessage("Tunggu Yaa");
                    Progress.setCanceledOnTouchOutside(false);
                    Progress.show();
                    antriana(user.getUid(),Catatan.getText().toString(),jeniskendaraaan,Platkendaraan.getText().toString(),user.getEmail(),"15.00");
                }else {
                    Snackbar.make(findViewById(R.id.jama), "Data Antrian tidak boleh kosong", Snackbar.LENGTH_LONG).show();

                    InputMethodManager imm = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(
                            Catatan.getWindowToken(), 0);
                }

            }
        });

        jamh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String jeniskendaraaan = selectedText[0];
                if(!isEmpty(jeniskendaraaan) && !isEmpty(Catatan.getText().toString()) && !isEmpty(Platkendaraan.getText().toString()) ) {
                    Progress.setTitle("Memasukkan Data");
                    Progress.setMessage("Tunggu Yaa");
                    Progress.setCanceledOnTouchOutside(false);
                    Progress.show();
                    antriana(user.getUid(),Catatan.getText().toString(),jeniskendaraaan,Platkendaraan.getText().toString(),user.getEmail(),"16.00");
                }else {
                    Snackbar.make(findViewById(R.id.jama), "Data Antrian tidak boleh kosong", Snackbar.LENGTH_LONG).show();

                    InputMethodManager imm = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(
                            Catatan.getWindowToken(), 0);
                }

            }
        });

        jami.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String jeniskendaraaan = selectedText[0];
                if(!isEmpty(jeniskendaraaan) && !isEmpty(Catatan.getText().toString()) && !isEmpty(Platkendaraan.getText().toString()) ) {
                    Progress.setTitle("Memasukkan Data");
                    Progress.setMessage("Tunggu Yaa");
                    Progress.setCanceledOnTouchOutside(false);
                    Progress.show();
                    antriana(user.getUid(),Catatan.getText().toString(),jeniskendaraaan,Platkendaraan.getText().toString(),user.getEmail(),"17.00");
                }else {
                    Snackbar.make(findViewById(R.id.jama), "Data Antrian tidak boleh kosong", Snackbar.LENGTH_LONG).show();

                    InputMethodManager imm = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(
                            Catatan.getWindowToken(), 0);
                }

            }
        });


    }



        //if ( dateCompareOne.before( date ) && dateCompareTwo.after(date)) {
            //yada yada
        //}


    private boolean isEmpty(String s) {
        // Cek apakah ada fields yang kosong, sebelum disubmit
        return TextUtils.isEmpty(s);
    }


    private void antriana(final String userid,final String catatan,final String jeniskendaraan,final String platkendaraan, final String email , final  String waktusteam ) {



                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Antrian").child(String.valueOf(maxid+1));

                    HashMap<String, String> userMap = new HashMap<>();
                    userMap.put("nourut" ,String.valueOf(maxid+1));
                    userMap.put("userid", userid);
                    userMap.put("email", email);
                    userMap.put("catatan", catatan);
                    userMap.put("jeniskendaraan", jeniskendaraan);
                    userMap.put("platkendaraan", platkendaraan);
                    userMap.put("waktusteam", waktusteam);


                    mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Progress.dismiss();
                                showdialog();

                            }
                        }
                    });

            }

            private void showdialog(){
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        this);

                // set title dialog
                alertDialogBuilder.setTitle("Berhasil Memasukkan Data");

                // set pesan dari dialog
                alertDialogBuilder
                        .setMessage("Ok")
                        .setIcon(R.mipmap.ic_launcher)
                        .setCancelable(false)
                        .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                Intent mainIntent = new Intent(AntrianActivity.this, RiwayatActivity.class);
                                startActivity(mainIntent);
                                finish();

                            }
                        });

                // membuat alert dialog dari builder
                AlertDialog alertDialog = alertDialogBuilder.create();

                // menampilkan alert dialog
                alertDialog.show();
            }


    }

