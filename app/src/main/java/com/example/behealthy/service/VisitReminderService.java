package com.example.behealthy.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.behealthy.R;

public class VisitReminderService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String doctorName = intent.getStringExtra("DOCTOR_NAME");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("2", "visit_channel", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "2");
            builder.setContentTitle("УВАГА! ЧАС ВІЗИТУ ДО ЛІКАРЯ");
            builder.setContentText("Запланований візит до " + doctorName);
            builder.setSmallIcon(R.drawable.notification_icon);
            Notification notification = builder.build();
            notificationManager.createNotificationChannel(channel);
            notificationManager.notify(1, notification);
        }
        return START_NOT_STICKY;
    }
}
