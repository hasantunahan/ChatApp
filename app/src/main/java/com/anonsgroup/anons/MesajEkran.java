package com.anonsgroup.anons;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.circularreveal.CircularRevealWidget;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.anonsgroup.anons.models.FirebaseUserModel;
import com.anonsgroup.anons.models.SenderUsers;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class MesajEkran extends AppCompatActivity {
    CircleImageView photo;
    TextView username;
    Intent intent;
    ImageView arkadas;
    FirebaseUser fuser;
    DatabaseReference reference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesaj_ekran);
      arkadas=findViewById(R.id.mesajEkranArkadasMi);
        arkadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arkadas.setImageResource(R.drawable.ic_checkfriends);
            }
        });
        photo=findViewById(R.id.mesajEkranPhoto);
        username=findViewById(R.id.mesajEkranIsimSoyisim);
        intent=getIntent();
        String userid=intent.getStringExtra("username");
        fuser= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("users").child(userid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                FirebaseUserModel user=dataSnapshot.getValue(FirebaseUserModel.class);
                username.setText(user.getUsername());
                if(user.getProfilUrl().equals("default")){
                    photo.setImageResource(R.drawable.kullaniciprofildefault);
                }else
                {
                    Glide.with(MesajEkran.this).load(user.getProfilUrl()).into(photo);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





/*
        photo=findViewById(R.id.mesajEkranPhoto);
        adsoyad=findViewById(R.id.mesajEkranIsimSoyisim);
        intent=getIntent();
        String ad=intent.getStringExtra("name");
        String soyad=intent.getStringExtra("surname");
        Integer sendphoto=intent.getIntExtra("photo",0);
        SenderUsers user=new SenderUsers();
        adsoyad.setText(ad+" "+soyad);
        photo.setImageResource(sendphoto);*/







    }

}
