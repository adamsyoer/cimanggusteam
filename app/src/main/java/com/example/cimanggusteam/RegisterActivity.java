package com.example.cimanggusteam;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout mNama;
    private TextInputLayout mEmail;
    private TextInputLayout mPassword;
    private TextInputLayout mTelepon;
    private Button mBuatBaru;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private Toolbar mToolbar;
    //
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        //toolbar
        mToolbar = (Toolbar) findViewById(R.id.register_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Daftar");


        mProgress = new ProgressDialog(this);

        mNama = (TextInputLayout) findViewById(R.id.regnama);
        mEmail = (TextInputLayout) findViewById(R.id.regemail);
        mPassword = (TextInputLayout) findViewById(R.id.regpass);
        mTelepon = (TextInputLayout) findViewById(R.id.regtelepon);
        mBuatBaru = (Button) findViewById(R.id.btn2);

        mBuatBaru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nama = mNama.getEditText().getText().toString();
                String email = mEmail.getEditText().getText().toString();
                String password = mPassword.getEditText().getText().toString();
                String notelepon = mTelepon.getEditText().getText().toString();

                if(TextUtils.isEmpty(nama)){
                    Toast.makeText(getApplicationContext(),"masukkan nama!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(),"masukkan emal!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(),"masukkan password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(notelepon)){
                    Toast.makeText(getApplicationContext(),"masukkan no telp!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!TextUtils.isEmpty(nama)||!TextUtils.isEmpty(email)||!TextUtils.isEmpty(password)||!TextUtils.isEmpty(notelepon)){


                    register_user(nama,email,password,notelepon);

                    mProgress.setTitle("Membuat Akun");
                    mProgress.setMessage("Mohon Tunggu");
                    mProgress.setCanceledOnTouchOutside(false);
                    mProgress.show();



                }

            }
        });

    }

    private void register_user(final String nama,final String email,final String password, final String notelepon) {
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                    String uid = current_user.getUid();

                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                    HashMap<String, String> userMap = new HashMap<>();
                    userMap.put("name", nama);
                    userMap.put("email", email);
                    userMap.put("password", password);
                    userMap.put("notelepon", notelepon);

                    mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                mProgress.dismiss();
                                Intent mainIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(mainIntent);
                                finish();
                            }
                        }
                    });

                } else {
                    mProgress.hide();
                    Toast.makeText(RegisterActivity.this, "Tidak bisa daftar.", Toast.LENGTH_LONG).show();

                }
            }
        });

    }
}
