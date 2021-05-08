package com.example.chatapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class SifremiUnuttumEkran extends AppCompatActivity {
      EditText email;
      Button reset;
      FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sifremi_unuttum_ekran);
        email=findViewById(R.id.resetEdittext);
        reset=findViewById(R.id.resetButton);

        firebaseAuth=FirebaseAuth.getInstance();

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email2=email.getText().toString();
                if(email.equals("")){
                    Toast.makeText(SifremiUnuttumEkran.this, "email giriniz", Toast.LENGTH_SHORT).show();
                }else{
                    firebaseAuth.sendPasswordResetEmail(email2).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                         if( task.isSuccessful()){
                             Toast.makeText(SifremiUnuttumEkran.this, "Email'inizi kontrol ediniz", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SifremiUnuttumEkran.this,Login.class));
                         }else{
                             String error=task.getException().getMessage();
                             Toast.makeText(SifremiUnuttumEkran.this, error, Toast.LENGTH_SHORT).show();
                         }
                        }
                    });
                }
            }
        });

    }
}
