package com.example.rxmindapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        //NotificationHelper notificationHelper = new NotificationHelper(context);
        //notificationHelper.createNotification();

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "1001");
        mBuilder.setSmallIcon(R.drawable.common_google_signin_btn_icon_dark);
        mBuilder.setContentTitle("Time to take your medication!");
        mBuilder.setContentText("Open the app and see which medications to take!");
        mBuilder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);

        notificationManagerCompat.notify(200, mBuilder.build());
        Log.d("NOTIFICATIONRECIEVER", "MADE IT TO END OF onReceive");

    }

    // temporary class, will be deleting in the future
    class NotificationHelper
    {
        private Context mContext;
        private static final String NOTIFCATION_CHANNEL_ID = "1001";

        NotificationHelper(Context context)
        {
            mContext = context;
        }

        void createNotification()
        {
            Intent intent = new Intent(mContext, MainActivity.class);

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            PendingIntent resultPendingIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext, NOTIFCATION_CHANNEL_ID);
            mBuilder.setContentTitle("Time to take your medication!");
            mBuilder.setContentText("Open the app and see which medications to take!");
            mBuilder.setAutoCancel(false);
            mBuilder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
            mBuilder.setContentIntent(resultPendingIntent);

            NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            {
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel notificationChannel = new NotificationChannel(NOTIFCATION_CHANNEL_ID, "NotifyUser", importance);
                notificationChannel.enableLights(true);
                notificationChannel.setLightColor(Color.BLUE);
                notificationChannel.enableVibration(true);
                notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                assert notificationManager != null;
                mBuilder.setChannelId(NOTIFCATION_CHANNEL_ID);
                notificationManager.createNotificationChannel(notificationChannel);
            }
            assert notificationManager != null;
            notificationManager.notify(0, mBuilder.build());
        }

    }
}
