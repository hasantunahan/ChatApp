package com.anonsgroup.anons.Notifications;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationBuilderWithBuilderAccessor;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.anonsgroup.anons.AnaEkran;
import com.anonsgroup.anons.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.concurrent.ConcurrentHashMap;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
   final String  channelID ="com.anonsgoup.anons";
   final String channelName="anons";
    NotificationManager notificationManager;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d("Remote Message tag::", remoteMessage.getFrom());
        Intent intent = new Intent(this, AnaEkran.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setContentTitle("Deneme Title")
                .setContentText(remoteMessage.getNotification().getBody())
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentIntent(pendingIntent);

        Log.d("Mesaj Geldiiiii::::::" , remoteMessage.getNotification().getBody());
        String channelId = getString(R.string.default_notification_channel_id);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

     /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("0",
                    "Denedik Title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }*/

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel;
            channel = new NotificationChannel(channelID,channelName,
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(false);
            channel.enableVibration(true);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            getManager().createNotificationChannel(channel);

            Notification.Builder notification = new  Notification.Builder(this,channelID)
                    .setContentIntent(pendingIntent)
                    .setContentTitle("yeni anons")
                    .setSmallIcon(R.drawable.ic_stat_name)
                    .setContentText(remoteMessage.getNotification().getBody())
                    .setAutoCancel(true);
            notificationManager.notify(0 /* ID of notification */, notification.build());
return;
        }



        notificationManager.notify(0 /* ID of notification */, builder.build());

    }


    public NotificationManager getManager(){
        if(notificationManager == null){
       //     Toast.makeText(this, "get Managera giridi", Toast.LENGTH_SHORT).show();
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        }
        return notificationManager;
    }
}
