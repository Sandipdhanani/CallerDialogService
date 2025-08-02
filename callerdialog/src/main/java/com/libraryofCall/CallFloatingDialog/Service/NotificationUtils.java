package com.libraryofCall.CallFloatingDialog.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;

import androidx.core.app.NotificationCompat;

import com.libraryofCall.CallFloatingDialog.R;


public class NotificationUtils {
    public static Notification createNotification(Context context) {
        String CHANNEL_ID = "call_monitor_channel";
        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID, "Call Monitor", NotificationManager.IMPORTANCE_LOW);
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.createNotificationChannel(channel);

        return new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("Call Monitor Active")
                .setSmallIcon(R.drawable.ic_call)
                .build();
    }
}
