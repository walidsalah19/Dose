package com.example.dose.User.Alarm;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.dose.R;

public class AlarmNotification extends Worker {
    private static final String CHANNEL_ID = "alarm";
    private static final String CHANNEL_NAME = "alarm channel";
    public AlarmNotification(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }


    private void sendNotification(String title, String content, Uri sound) {

        NotificationManager manager = (NotificationManager) getApplicationContext()
                .getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder notificationBuilder
                = createNotificationBuilder(title, content, sound);

        createNotificationChannel(manager, sound);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
           // getApplicationContext().startForegroundService(new Intent(getApplicationContext(), ForegroundService.class));
        }

        manager.notify(0, notificationBuilder.build());
    }


    private NotificationCompat.Builder createNotificationBuilder(String title, String content, Uri sound) {
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);

        notificationBuilder
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(title)
                .setSound(sound)
                .setDefaults(NotificationCompat.DEFAULT_SOUND)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        return notificationBuilder;

    }

    private void createNotificationChannel(NotificationManager manager, Uri sound) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel notificationChannel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH);

            MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), sound);
            mediaPlayer.start();

            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();
            notificationChannel.setSound(sound, audioAttributes);
            manager.createNotificationChannel(notificationChannel);
        }
    }

    @NonNull
    @Override
    public Result doWork() {
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Data input = getInputData();
        String title = input.getString("title");
        String content = input.getString("content");
        sendNotification(title, content, alarmSound);
        return Result.success();
    }
}
