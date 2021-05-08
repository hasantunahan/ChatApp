package com.example.chatapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText email,parola;
    Button giris;
    TextView sifreunuttum;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email=findViewById(R.id.EmaileditText);
        parola=findViewById(R.id.ParolaeditText);
        giris=findViewById(R.id.girisbutton);
        sifreunuttum=findViewById(R.id.sifremiUnuttum);
        auth= FirebaseAuth.getInstance();


        sifreunuttum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,SifremiUnuttumEkran.class));
            }
        });


        giris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtEmail=email.getEditableText().toString();
                String txtParola=parola.getEditableText().toString();
                if(TextUtils.isEmpty(txtEmail) || TextUtils.isEmpty(txtParola)){
                    Toast.makeText(Login.this,"Email ve parolayı giriniz",Toast.LENGTH_SHORT).show();
                }else{

                    auth.signInWithEmailAndPassword(txtEmail,txtParola)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                     if(task.isSuccessful()){
                                         Intent intent =new Intent(Login.this,Main2Activity.class);
                                         intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                         startActivity(intent);
                                         finish();
                                    }else{
                                         Toast.makeText(Login.this,"Giriş yapılamadı",Toast.LENGTH_SHORT).show();
                                     }

                                }
                            });
                }


            }
        });


    }
}
