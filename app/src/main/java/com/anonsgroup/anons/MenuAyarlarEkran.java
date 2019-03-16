package com.anonsgroup.anons;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;

import br.com.goncalves.pugnotification.notification.PugNotification;

public class MenuAyarlarEkran extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    SwitchCompat hesapGizliligiSwitchCompat,bildirimTitresimleriSwitchCompat,bildirimSesleriSwitchCompat,bildirimlerSwitchCompat;
    Button bildirimGonder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_ayarlar_ekran);
        hesapGizliligiSwitchCompat = findViewById(R.id.hesapGizliligiSwitchCompat);
        bildirimSesleriSwitchCompat = findViewById(R.id.bildirimSesleriSwitchCompat);
        bildirimTitresimleriSwitchCompat = findViewById(R.id.bildirimTitresimleriSwitchCompat);
        bildirimlerSwitchCompat= findViewById(R.id.bildirimlerSwitchCompat);
        bildirimGonder = findViewById(R.id.bildirimGonder);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Ayarlar");
        actionBar.setDisplayHomeAsUpEnabled(true);

        setupSwitchCompatEventListener();

        bildirimGonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"sero",Toast.LENGTH_LONG).show();
                getnotification();            }
        });

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
                    bildirimYolla();
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
                    if (isChecked)
                    break;
            }
        }


        private void bildirimYolla(){
            PugNotification.with(this)
                    .load()
                    .title("Anons Deneme Bildirimi")
                    .message("Serocan38 sunar")
                    .bigTextStyle("hemen teşekkür et")
                    .smallIcon(R.drawable.mesaj_sag_baloncuk)
                    .largeIcon(R.drawable.mesaj_sol_baloncuk)
                    .flags(Notification.DEFAULT_ALL)
                    .click(MenuAyarlarEkran.class)
                    .autoCancel(true)
                    .simple()
                    .build();

        }
        private void addNotification(){
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.mesaj_sag_baloncuk)
                    .setContentTitle("sero")
                    .setContentText("naber");

            Intent notificationIntent =new Intent(getApplicationContext(),ProfilEkran.class);
            PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(),0,notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(contentIntent);

            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(0,builder.build());
        }
    public void getnotification(){

        NotificationManager notificationmgr = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(getApplicationContext(),MenuAyarlarEkran.class);
        PendingIntent pintent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

        //   PendingIntent pintent = PendingIntent.getActivities(this,(int)System.currentTimeMillis(),intent, 0);

        Notification notif = new Notification.Builder(this)
                .setSmallIcon(R.drawable.mesaj_sag_baloncuk)
                .setContentTitle("Hello Android Hari")
                .setContentText("Welcome to Notification Service")
                .setContentIntent(pintent)
                .build();
        notificationmgr.notify(0,notif);
    }
}
