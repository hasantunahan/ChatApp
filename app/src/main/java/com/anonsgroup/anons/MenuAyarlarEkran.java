package com.anonsgroup.anons;

import android.annotation.SuppressLint;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Toast;

public class MenuAyarlarEkran extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    SwitchCompat hesapGizliligiSwitchCompat,bildirimTitresimleriSwitchCompat,bildirimSesleriSwitchCompat,bildirimlerSwitchCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_ayarlar_ekran);
        hesapGizliligiSwitchCompat = findViewById(R.id.hesapGizliligiSwitchCompat);
        bildirimSesleriSwitchCompat = findViewById(R.id.bildirimSesleriSwitchCompat);
        bildirimTitresimleriSwitchCompat = findViewById(R.id.bildirimTitresimleriSwitchCompat);
        bildirimlerSwitchCompat= findViewById(R.id.bildirimlerSwitchCompat);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Ayarlar");
        actionBar.setDisplayHomeAsUpEnabled(true);

        setupSwitchCompatEventListener();
    }

    private void setupSwitchCompatEventListener() {
        hesapGizliligiSwitchCompat.setOnCheckedChangeListener(this);
        bildirimSesleriSwitchCompat.setOnCheckedChangeListener(this);
        bildirimTitresimleriSwitchCompat.setOnCheckedChangeListener(this);
        bildirimlerSwitchCompat.setOnCheckedChangeListener(this);

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
                        Toast.makeText(getBaseContext(),"açık",Toast.LENGTH_LONG).show();
                    break;
                case (R.id.bildirimlerSwitchCompat):

                    if (isChecked){
                        bildirimTitresimleriSwitchCompat.setEnabled(true);
                        bildirimSesleriSwitchCompat.setEnabled(true);
                    }
                        else{
                        bildirimTitresimleriSwitchCompat.setEnabled(false);
                        bildirimSesleriSwitchCompat.setEnabled(false);
                        bildirimSesleriSwitchCompat.setChecked(false);
                        bildirimTitresimleriSwitchCompat.setChecked(false);}
                    break;
                case (R.id.bildirimSesleriSwitchCompat):
                    break;
                case (R.id.bildirimTitresimleriSwitchCompat):
                    break;
            }
        }

}
