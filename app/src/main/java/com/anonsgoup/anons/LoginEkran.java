package com.anonsgoup.anons;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_ekran);
        login = findViewById(R.id.loginButton);
        signUp = findViewById(R.id.signUpButton);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        uyariTextView = findViewById(R.id.uyariTextView);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                kullaniciAdi = usernameEditText.getText().toString();
                sifre = passwordEditText.getText().toString();
                if (seroad.equals(kullaniciAdi) && serosifre.equals(sifre)){
                    Intent intent = new Intent(LoginEkran.this,AnaEkran.class);
                    startActivity(intent);}
                else {
                    uyariTextView.setVisibility(View.VISIBLE);
                }
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
