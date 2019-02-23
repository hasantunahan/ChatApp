package com.anonsgoup.anons;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EmailDogrulamaEkran extends AppCompatActivity {

    private TextView emailTextView;
    private FirebaseAuth mAuth;
    private Button tekrarGonderBtn;
    private FirebaseUser fUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_dogrulama);
        emailTextView = findViewById(R.id.mailTextView);
        mAuth = FirebaseAuth.getInstance();
        emailTextView.setText(mAuth.getCurrentUser().getEmail());
        Log.d("maildogrulamacreate","buraya girdi");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("maildogrulamacreate","resume buraya girdi");
        tekrarGonderBtn = findViewById(R.id.tekrarGonderButton);
        fUser = mAuth.getCurrentUser();
        fUser.reload();
        if(fUser.isEmailVerified()){
            startActivity(new Intent(getApplicationContext(),AnaEkran.class));
            finish();
        }

        tekrarGonderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            //TODO: string.xml den alınacak yazı
                            Toast.makeText(EmailDogrulamaEkran.this, "Email Gönderildi", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            //TODO: string.xml den alınacak yazı
                            Toast.makeText(EmailDogrulamaEkran.this, "Bir Hata Oluştu Lütfen Bizimle İletişime Geçiniz.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                
            }
        });
    }

}
