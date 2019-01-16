package com.kubo.kubo_fix.Helper;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.net.Uri;
import android.os.Build;

import com.kubo.kubo_fix.R;

public class NotificationHelper extends ContextWrapper {

    private static final String kubo_CHANEL_ID= "com.kubo.kubo_fix";
    private static final String kubo_CHANEL_Name= "Kubo";

    private NotificationManager manager;

    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) // Only working this function if API is 26 or higher
            createChannel();

    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel expreshoesChannel = new NotificationChannel(kubo_CHANEL_ID,kubo_CHANEL_Name, NotificationManager.IMPORTANCE_DEFAULT);
        expreshoesChannel.enableLights(false);
        expreshoesChannel.enableVibration(true);
        expreshoesChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(expreshoesChannel);
    }

    public NotificationManager getManager() {
        if (manager == null)
            manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        return manager;
    }

    @TargetApi(Build.VERSION_CODES.O)
    public android.app.Notification.Builder getExpreshoesChannelNotification(String title, String body, PendingIntent contentIntent, Uri soundUri)
    {
        return new android.app.Notification.Builder(getApplicationContext(),kubo_CHANEL_ID)
                .setContentIntent(contentIntent)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setSound(soundUri)
                .setAutoCancel(false);
    }
}
