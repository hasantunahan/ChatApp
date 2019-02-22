package com.anonsgoup.anons;



import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Scanner;

public class LoginEkran extends Activity {
    private EditText usernameEditText;
    private EditText passwordEditText;
    TextView uyariTextView;
    Button signUp;
    Button login;
    String kullaniciAdi;
    String sifre;
    String  seroad="1";
    String serosifre="1";
    int sero;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_ekran);
        login = findViewById(R.id.loginButton);
        signUp = findViewById(R.id.signUpButton);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        uyariTextView = findViewById(R.id.uyariTextView);

        progressDialog = new ProgressDialog(LoginEkran.this);
        mAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //şimdilik email ile giriyoruz.
                // TODO: Stringe Geçirilecek
                progressDialog.setTitle("Girişiniz Kontrol ediliyor.");
                progressDialog.setMessage("Lütfen Bekleyiniz.");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                kullaniciAdi = usernameEditText.getText().toString().trim();
                sifre = passwordEditText.getText().toString().trim();

                mAuth.signInWithEmailAndPassword(kullaniciAdi,sifre).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            Intent intent = new Intent(getApplicationContext(), AnaEkran.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            // TODO: Stringe Geçirilecek
                            progressDialog.hide();
                            Toast.makeText(LoginEkran.this, "Bir hata Oluştu", Toast.LENGTH_SHORT).show();
                            Log.d("girisHata",task.getException().toString());
                        }
                    }
                });

            }
        });


      signUp.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), KayitOlEkran.class));

            }
        });

    }

}
