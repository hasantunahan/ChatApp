package com.anonsgroup.anons;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
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

import java.util.regex.Pattern;

public class SifremiDegistir extends AppCompatActivity {
        EditText yeniET1,yeniET2;
        Button sifreDegisButton;
        private FirebaseUser firebaseUser;
        Editable sifre;
        TextInputLayout passwordWrapper;
        boolean kontrol = true;

        private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[0-9])" +
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*['*!@#$%^&+=.,_-])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{6,}" +               //at least 6 characters
                    "$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sifremi_degistir);
        yeniET1 = findViewById(R.id.yeniET1);
        yeniET2 = findViewById(R.id.yeniET2);
        sifreDegisButton = findViewById(R.id.sifreDegisButton);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        sifreDegisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sifre= yeniET1.getText();
                firebaseUser.updatePassword(String.valueOf(sifre));
            }
        });
        }


        private boolean kontroller() {
            String passwordInput = yeniET1.getText().toString().trim();
            if (passwordInput.isEmpty()) {
                passwordWrapper.setError(getResources().getString(R.string.empty_warning));
                kontrol = false;
            } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
                passwordWrapper.setError(getResources().getString(R.string.password_warning));
                kontrol = false;
            } else {
                passwordWrapper.setError(null);
            }
            return kontrol;
        }
}
