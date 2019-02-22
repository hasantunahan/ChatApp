package com.anonsgoup.anons;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class EmailDogrulamaEkran extends AppCompatActivity {

    private TextView emailTextView;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_dogrulama);
        emailTextView = findViewById(R.id.mailTextView);
        mAuth = FirebaseAuth.getInstance();
        emailTextView.setText(mAuth.getCurrentUser().getEmail());
        Log.d("maildogrulamacreate","buraya girdi");
        onResume();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("maildogrulamacreate","resume buraya girdi");

        if(mAuth.getCurrentUser().isEmailVerified()){
            startActivity(new Intent(getApplicationContext(),AnaEkran.class));
            finish();
        }
    }
}
