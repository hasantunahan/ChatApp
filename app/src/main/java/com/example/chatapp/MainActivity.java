package com.example.chatapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    Button kaydol,login;

    FirebaseUser fUser;


    @Override
    protected void onStart() {
        super.onStart();

        fUser=FirebaseAuth.getInstance().getCurrentUser();
        if(fUser != null){
            Intent intent=new Intent(MainActivity.this,Main2Activity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        kaydol=findViewById(R.id.kayitOlButton);
        login=findViewById(R.id.loginButton);
        fUser= FirebaseAuth.getInstance().getCurrentUser();
        if(fUser != null){
            Intent intent=new Intent(MainActivity.this,Main2Activity.class);
            startActivity(intent);
            finish();
        }


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Login.class));
            }
        });

        kaydol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,kayitOl.class));
            }
        });
    }
}
