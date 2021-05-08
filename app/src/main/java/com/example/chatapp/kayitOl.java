package com.example.chatapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class kayitOl extends AppCompatActivity {
    EditText username,email,password;
    Button kaydol;
    FirebaseAuth auth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_ol);
        username=findViewById(R.id.usernameEditText);
        email=findViewById(R.id.emailEdittext);
        password=findViewById(R.id.parolaEdittext);
        kaydol=findViewById(R.id.kaydolButton);

        auth=FirebaseAuth.getInstance();
        kaydol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtUsername=username.getText().toString();
                String txtEmail=email.getText().toString();
                String txtPassword=password.getText().toString();
                if (TextUtils.isEmpty(txtUsername) || TextUtils.isEmpty(txtEmail) || TextUtils.isEmpty(txtPassword)){
                    Toast.makeText(kayitOl.this,"Tüm alanlari doldurunuz",Toast.LENGTH_SHORT).show();
                }else if(txtPassword.length() < 8){
                    Toast.makeText(kayitOl.this,"Parola 8 karakterli olmalıdır.",Toast.LENGTH_SHORT).show();
                }else{
                    kaydol(txtUsername,txtEmail,txtPassword);
                }

            }
        });

    }

    private void kaydol(final String username, String email, String password){
         auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
             @Override
             public void onComplete(@NonNull Task<AuthResult> task) {
                 if(task.isSuccessful()){
                     FirebaseUser fuser=auth.getCurrentUser();
                     assert fuser != null;
                     String userid=fuser.getUid();
                     reference= FirebaseDatabase.getInstance().getReference("Users").child(userid);

                     HashMap<String,String> hashMap=new HashMap<>();
                     hashMap.put("id",userid);
                     hashMap.put("name",username);
                     hashMap.put("imageURL","default");
                     hashMap.put("status","offline");
                     hashMap.put("ara",username.toLowerCase());

                     reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                         @Override
                         public void onComplete(@NonNull Task<Void> task) {
                           Intent intent =new Intent(kayitOl.this,Main2Activity.class);
                           intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                           startActivity(intent);
                           finish();
                         }
                     });

                 }else{
                     Toast.makeText(kayitOl.this,"you cant this",Toast.LENGTH_SHORT).show();
                 }
             }
         });

    }
}
