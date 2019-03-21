package com.anonsgroup.anons;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Toast;

public class MenuAyarlarEkran extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    SwitchCompat hesapGizliligiSwitchCompat,AnonsBildirimTitresimleriSwitchCompat,AnonsBildirimSesleriSwitchCompat,AnonsBildirimlerSwitchCompat;
    SwitchCompat MesajBildirimlerSwitchCompat,MesajBildirimSesleriSwitchCompat,MesajBildirimTitresimleriSwitchCompat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_ayarlar_ekran);
        hesapGizliligiSwitchCompat = findViewById(R.id.hesapGizliligiSwitchCompat);
        AnonsBildirimTitresimleriSwitchCompat = findViewById(R.id.AnonsBildirimSesleriSwitchCompat);
        AnonsBildirimSesleriSwitchCompat = findViewById(R.id.AnonsBildirimTitresimleriSwitchCompat);
        AnonsBildirimlerSwitchCompat= findViewById(R.id.AnonsBildirimlerSwitchCompat);
        MesajBildirimlerSwitchCompat = findViewById(R.id.MesajBildirimlerSwitchCompat);
        MesajBildirimSesleriSwitchCompat = findViewById(R.id.MesajBildirimSesleriSwitchCompat);
        MesajBildirimTitresimleriSwitchCompat= findViewById(R.id.MesajBildirimTitresimleriSwitchCompat);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Ayarlar");
        actionBar.setDisplayHomeAsUpEnabled(true);

        setupSwitchCompatEventListener();

    }

    private void setupSwitchCompatEventListener() {
        hesapGizliligiSwitchCompat.setOnCheckedChangeListener(this);
        AnonsBildirimlerSwitchCompat.setOnCheckedChangeListener(this);
        AnonsBildirimSesleriSwitchCompat.setOnCheckedChangeListener(this);
        AnonsBildirimTitresimleriSwitchCompat.setOnCheckedChangeListener(this);
        MesajBildirimlerSwitchCompat.setOnCheckedChangeListener(this);
        MesajBildirimSesleriSwitchCompat.setOnCheckedChangeListener(this);
        MesajBildirimTitresimleriSwitchCompat.setOnCheckedChangeListener(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (android.R.id.home==item.getItemId()){
            finish();}
    return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case (R.id.hesapGizliligiSwitchCompat):
                if (isChecked)
                    Toast.makeText(getBaseContext(), "açık", Toast.LENGTH_LONG).show();
                break;
            case (R.id.AnonsBildirimlerSwitchCompat):

                if (isChecked) {
                    AnonsBildirimTitresimleriSwitchCompat.setEnabled(true);
                    AnonsBildirimSesleriSwitchCompat.setEnabled(true);
                } else {
                    AnonsBildirimSesleriSwitchCompat.setEnabled(false);
                    AnonsBildirimSesleriSwitchCompat.setChecked(false);
                    AnonsBildirimTitresimleriSwitchCompat.setEnabled(false);
                    AnonsBildirimTitresimleriSwitchCompat.setChecked(false);
                }
                break;

            case (R.id.AnonsBildirimSesleriSwitchCompat):
                if (isChecked) {
                } else {
                    Toast.makeText(this, "sero", Toast.LENGTH_SHORT).show();
                }
                break;

            case (R.id.AnonsBildirimTitresimleriSwitchCompat):
                if (isChecked) {
                } else {

                }
                break;

            case (R.id.MesajBildirimlerSwitchCompat):

                if (isChecked) {
                    MesajBildirimSesleriSwitchCompat.setEnabled(true);
                    MesajBildirimTitresimleriSwitchCompat.setEnabled(true);
                } else {
                    MesajBildirimSesleriSwitchCompat.setEnabled(false);
                    MesajBildirimSesleriSwitchCompat.setChecked(false);
                    MesajBildirimTitresimleriSwitchCompat.setEnabled(false);
                    MesajBildirimTitresimleriSwitchCompat.setChecked(false);
                }

                break;

            case (R.id.MesajBildirimSesleriSwitchCompat):
                if (isChecked) {
                } else {
                    Toast.makeText(this, "sero", Toast.LENGTH_SHORT).show();
                }
                break;
            case (R.id.MesajBildirimTitresimleriSwitchCompat):
                if (isChecked) {
                } else {

                }
                break;
        }
    }
}
