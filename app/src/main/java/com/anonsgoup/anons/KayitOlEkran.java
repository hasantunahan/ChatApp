package com.anonsgoup.anons;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.anonsgoup.anons.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class KayitOlEkran extends AppCompatActivity {

    private Spinner genderSpinner;
    private TextView dobEditText;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    Button signUpOkeyButton;
    EditText emailEditText;
    EditText passwordEditText;
    EditText usernameEditText;
    TextInputLayout passwordWrapper;
    TextInputLayout emailWrapper;
    TextInputLayout usernameWrapper;
    TextInputLayout nameWrapper;
    TextInputLayout surnameWrapper;
    EditText nameEditText;
    EditText surnameEditText;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private ProgressDialog progressDialog;

    private static final Pattern DATE_PATTERN = Pattern.compile("\\d{1,2}/\\d{1,2}/\\d{4}");
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
        setContentView(R.layout.activity_kayit_ol_ekran);

        usernameWrapper = findViewById(R.id.usernameWrapper);
        dobEditText = findViewById(R.id.dobEditText);
        emailEditText = findViewById(R.id.emailEditText);
        signUpOkeyButton = findViewById(R.id.signUpOkeyButton);
        passwordEditText = findViewById(R.id.passwordEditText);
        emailWrapper = findViewById(R.id.emailWrapper);
        passwordWrapper = findViewById(R.id.passwordWrappper);
        genderSpinner = findViewById(R.id.genderSpinner);
        nameEditText = findViewById(R.id.nameEditText);
        surnameEditText = findViewById(R.id.surnameEditText);
        nameWrapper = findViewById(R.id.nameWrapper);
        surnameWrapper = findViewById(R.id.surnameWrapper);
        usernameEditText = findViewById(R.id.usernameEditText);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.sex, R.layout.custom_spinner_item);
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter);
        progressDialog = new ProgressDialog(getApplicationContext());

        mAuth = FirebaseAuth.getInstance();

        signUpOkeyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validatePassword() || !emailkontrol() || !usernamekontrol() || !dobKontrol()
                        || !nameKontrol() || !surnameKontrol())
                    return;
                String name = nameEditText.getText().toString().trim();
                String surname = surnameEditText.getText().toString().trim();
                String username = usernameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String dob = dobEditText.getText().toString().trim();
                String gender = genderSpinner.getSelectedItem().toString();
                User user = new User(email,username,name,surname,dob,gender,new Date().getTime());
                registerNewUser(user,password);

            }
        });
    }

    private void registerNewUser(final User user, String password){
        mAuth.createUserWithEmailAndPassword(user.getEmail(), password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(user.getName()).build();
                            mAuth.getCurrentUser().updateProfile(userProfileChangeRequest);
                            Log.d("İşlem: ", "createUserWithEmail:success");
                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(mAuth.getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        progressDialog.dismiss();
                                        mAuth.getCurrentUser().sendEmailVerification();
                                        //TODO: verification Ekranına Gidilecek
                                        if(!mAuth.getCurrentUser().isEmailVerified()) {
                                            startActivity(new Intent(getApplicationContext(), EmailDogrulamaEkran.class));
                                            finish();
                                        }
                                        else {
                                            startActivity(new Intent(getApplicationContext(),AnaEkran.class));
                                            finish();
                                        }
                                    }
                                    else{
                                        mAuth.getCurrentUser().delete();
                                        Log.d("userhata:",task.getException().toString());
                                    }
                                }
                            });

                        } else {
                            progressDialog.hide();
                            // If sign in fails, display a message to the user.
                            Log.w("İşlem:","createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.user_registering_fail),
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });

    }


    // viewların tıklanma olayları
    public void kayitOlClicks(View view) {
        switch (view.getId()) {
            case R.id.dobEditText:
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                onDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        dobEditText.setText(dayOfMonth + "/" + month + "/" + year);
                    }
                };
                DatePickerDialog datePickerDialog = new DatePickerDialog(KayitOlEkran.this,
                        android.R.style.Theme_Holo_Light_Dialog, onDateSetListener, year, month, day);
                datePickerDialog.getDatePicker().setMaxDate((new Date().getTime()) - Long.parseLong("31556926000") * 13);

                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();

                break;
        }
    }


    private boolean emailkontrol() {
        String emailInput = emailEditText.getEditableText().toString().trim();

        if (emailInput.isEmpty()) {
            emailWrapper.setErrorEnabled(true);
            emailWrapper.setError(getResources().getString(R.string.empty_warning));
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            emailWrapper.setErrorEnabled(true);
            emailWrapper.setError(getResources().getString(R.string.email_warning));
            return false;
        } else {
            emailWrapper.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String passwordInput = passwordEditText.getText().toString().trim();

        if (passwordInput.isEmpty()) {
            passwordWrapper.setError(getResources().getString(R.string.empty_warning));
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            passwordWrapper.setError(getResources().getString(R.string.password_warning));
            return false;
        } else {
            passwordWrapper.setError(null);
            return true;
        }
    }

    private boolean usernamekontrol() {
        String usernameInput = usernameEditText.getEditableText().toString().trim();

        if (usernameInput.isEmpty()) {
            usernameWrapper.setError(getResources().getString(R.string.empty_warning));
            return false;
        } else if (usernameInput.length() > 15) {
            usernameWrapper.setError(getResources().getString(R.string.username_warning));
            return false;
        } else {
            usernameWrapper.setError(null);
            return true;
        }
    }

    private boolean dobKontrol() {
        if (!DATE_PATTERN.matcher(dobEditText.getText()).matches()) {
            dobEditText.setText(getResources().getString(R.string.dob_warning));
            return false;
        }
        return true;
    }

    private boolean nameKontrol() {
        String nameInput = nameEditText.getEditableText().toString().trim();
        if (nameInput.isEmpty()) {
            nameWrapper.setError(getResources().getString(R.string.empty_warning));
            return false;
        } else {
            nameWrapper.setError(null);
            return true;
        }
    }

    private boolean surnameKontrol() {
        String surnameInput = surnameEditText.getEditableText().toString().trim();
        if (surnameInput.isEmpty()) {
            surnameWrapper.setError(getResources().getString(R.string.empty_warning));
            return false;
        } else {
            nameWrapper.setError(null);
            return true;
        }
    }
}