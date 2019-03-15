package com.anonsgroup.anons;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.anonsgroup.anons.models.SenderUsers;

import java.util.ArrayList;
import java.util.List;

public class ArkadasListesiEkran extends AppCompatActivity {
        ImageView kapat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arkadas_listesi_ekran);
        kapat=findViewById(R.id.arkadasListesiKapat);
        kapat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        RecyclerView recyclerView=findViewById(R.id.arkadasListesiRecylerView);
        List<SenderUsers> nlist=new ArrayList<>();
        //TODO: Veri tabanıyla tekrardan yapılacak
        nlist.add(new SenderUsers("hasantunahan123","HasanTunahan","AK",R.mipmap.hsn));
        nlist.add(new SenderUsers("hasantunahan123","Tarık","Umutlu",R.mipmap.trk));
        nlist.add(new SenderUsers("hasantunahan123","HasanTunahan","AK",R.mipmap.hsn));
        nlist.add(new SenderUsers("hasantunahan123","Tarık","Umutlu",R.mipmap.trk));
        nlist.add(new SenderUsers("hasantunahan123","HasanTunahan","AK",R.mipmap.hsn));
        nlist.add(new SenderUsers("hasantunahan123","Tarık","Umutlu",R.mipmap.trk));
        nlist.add(new SenderUsers("hasantunahan123","HasanTunahan","AK",R.mipmap.hsn));
        nlist.add(new SenderUsers("hasantunahan123","Tarık","Umutlu",R.mipmap.trk));
        nlist.add(new SenderUsers("hasantunahan123","HasanTunahan","AK",R.mipmap.hsn));
        nlist.add(new SenderUsers("hasantunahan123","Tarık","Umutlu",R.mipmap.trk));
        nlist.add(new SenderUsers("hasantunahan123","HasanTunahan","AK",R.mipmap.hsn));
        nlist.add(new SenderUsers("hasantunahan123","Tarık","Umutlu",R.mipmap.trk));
        nlist.add(new SenderUsers("hasantunahan123","HasanTunahan","AK",R.mipmap.hsn));
        nlist.add(new SenderUsers("hasantunahan123","Tarık","Umutlu",R.mipmap.trk));
        ArkadasListesiAdapter arkadasListesiAdapter=new ArkadasListesiAdapter(this,nlist);
        recyclerView.setAdapter(arkadasListesiAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
