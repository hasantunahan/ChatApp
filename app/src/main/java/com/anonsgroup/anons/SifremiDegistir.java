package com.anonsgroup.anons;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SifremiDegistir extends AppCompatActivity {
        EditText yeniET1,yeniET2;
        Button sifreDegisButton;
        DatabaseReference ref;
        private String str;
    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    private FirebaseAuth.AuthStateListener authListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sifremi_degistir);
        yeniET1 = findViewById(R.id.yeniET1);
        yeniET2 = findViewById(R.id.yeniET2);
        sifreDegisButton = findViewById(R.id.sifreDegisButton);

        sifreDegisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str = "Lütfen yeni şifreyi giriniz.";
                changeEmailOrPasswordFunc(str,false);
            }
        });


        }
    private void changeEmailOrPasswordFunc(String title, final boolean option) {

        if(!option){  // password type
            yeniET1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }

            }

            private void changePassword() {

                firebaseUser.updatePassword(yeniET1.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful())
                                    Toast.makeText(getApplicationContext(), "Şifre değiştirildi.", Toast.LENGTH_LONG).show();
                            }});
}
}
