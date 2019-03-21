package com.anonsgroup.anons.Notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.anonsgroup.anons.AnaEkran;
import com.anonsgroup.anons.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import java.util.Map;
import java.util.Objects;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    String  channelID ="com.anonsgoup.anons";
    String channelName="anons";
    NotificationManager notificationManager;
    public static String username;
    @Override

    public void onMessageReceived(RemoteMessage remoteMessage) {
        Map<String, String> veriler = remoteMessage.getData();
        Log.d("GELEN USERNAME:: ", veriler.get("senderUsername"));
        String gelenUsername =veriler.get("senderUsername");
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            if (gelenUsername != null && gelenUsername.equals(username))
                return;
        }
        int i= (int) System.currentTimeMillis();
        Log.d("Remote Message tag::", remoteMessage.getFrom());
        Intent intent = new Intent(this, AnaEkran.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,i,intent,PendingIntent.FLAG_ONE_SHOT);
        Notification.Builder builder = new Notification.Builder(this)
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setContentText(remoteMessage.getNotification().getBody())
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentIntent(pendingIntent);

        Log.d("Mesaj Geldiiiii::::::" , remoteMessage.getNotification().getBody());
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
            channel.canShowBadge();
            getManager().createNotificationChannel(channel);

            NotificationCompat.Builder notification = new  NotificationCompat.Builder(this,channelID)
                    .setCategory(Notification.CATEGORY_MESSAGE)
                    .setContentIntent(pendingIntent)
                    .setContentTitle(remoteMessage.getNotification().getTitle())
                    .setSmallIcon(R.drawable.ic_stat_name)
                    .setContentText(remoteMessage.getNotification().getBody())
                    .setAutoCancel(true);

            notificationManager.notify(i/* ID of notification */, notification.build());
            return;
        }




        notificationManager.notify(i/* ID of notification */, builder.build());

    }


    public NotificationManager getManager(){
        if(notificationManager == null){
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        }
        return notificationManager;
    }
}
