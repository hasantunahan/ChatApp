package com.anonsgroup.anons;

import android.content.Intent;
import android.support.design.circularreveal.CircularRevealWidget;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.anonsgroup.anons.models.SenderUsers;

import de.hdodenhof.circleimageview.CircleImageView;

public class MesajEkran extends AppCompatActivity {
    CircleImageView photo;
    TextView adsoyad;
    Intent intent;
    ImageView arkadas;



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
        adsoyad=findViewById(R.id.mesajEkranIsimSoyisim);
        intent=getIntent();
        String ad=intent.getStringExtra("name");
        String soyad=intent.getStringExtra("surname");
        Integer sendphoto=intent.getIntExtra("photo",0);
        SenderUsers user=new SenderUsers();
        adsoyad.setText(ad+" "+soyad);
        photo.setImageResource(sendphoto);

    }

}
