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

import java.util.Calendar;
import java.util.HashSet;

// Class needed to send out notifications
public class NotificationReceiver extends BroadcastReceiver {

    HashSet<Integer> daysOfWeek;

    private boolean monday;
    private boolean tuesday;
    private boolean wednesday;
    private boolean thursday;
    private boolean friday;
    private boolean sat;
    private boolean sun;


    @Override
    public void onReceive(Context context, Intent intent) {


        daysOfWeek = new HashSet<>();

        monday = intent.getBooleanExtra("monday", false);
        tuesday = intent.getBooleanExtra("tuesday", false);
        wednesday = intent.getBooleanExtra("wednesday", false);
        thursday = intent.getBooleanExtra("thursday", false);
        friday = intent.getBooleanExtra("friday", false);
        sat = intent.getBooleanExtra("sat", false);
        sun = intent.getBooleanExtra("sun", false);

        Log.d("ALARMSCHEDULE", "SCHEDULED MONDAY: " + monday);
        Log.d("ALARMSCHEDULE", "SCHEDULED TUESDAY: " + tuesday);
        Log.d("ALARMSCHEDULE", "SCHEDULED WED: " + wednesday);
        Log.d("ALARMSCHEDULE", "SCHEDULED THURS: " + thursday);
        Log.d("ALARMSCHEDULE", "SCHEDULED FRI: " + friday);
        Log.d("ALARMSCHEDULE", "SCHEDULED SAT: " + sat);
        Log.d("ALARMSCHEDULE", "SCHEDULED SUN: " + sun);


        if (sun)
        {
            daysOfWeek.add(1);
        }

        if (monday)
        {
            daysOfWeek.add(2);
        }
        if (tuesday)
        {
            daysOfWeek.add(3);
        }
        if (wednesday)
        {
            daysOfWeek.add(4);
        }
        if (thursday)
        {
            daysOfWeek.add(5);
        }
        if (friday)
        {
            daysOfWeek.add(6);
        }
        if (sat)
        {
            daysOfWeek.add(7);
        }



        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        if (daysOfWeek.contains(day))
        {
            // Building the notification, the notification only triggers if its for the scheduled days
            Log.d("NOTIFICATIONRECIEVER", "ALARM SCHEDULED FOR TODAY");
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "1001");
            mBuilder.setSmallIcon(R.drawable.common_google_signin_btn_icon_dark);
            mBuilder.setContentTitle("Time to take your medication!");
            mBuilder.setContentText("Open the app and see which medications to take!");
            mBuilder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);

            notificationManagerCompat.notify(200, mBuilder.build());
        }
        else
        {
            Log.d("NOTIFICATIONRECIEVER", "DAY NOT SCHEDULED FOR ALARM");
        }

        Log.d("NOTIFICATIONRECIEVER", "MADE IT TO END OF onReceive");

    }

}
