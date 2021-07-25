package com.example.rxmindapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CreateReminder extends AppCompatActivity {

    EditText et_nickname;
    EditText et_Time;
    EditText et_pill;
    EditText et_numPills;
    EditText et_description;

    CheckBox mon;
    CheckBox tues;
    CheckBox wed;
    CheckBox thurs;
    CheckBox fri;
    CheckBox satur;
    CheckBox sund;
    CheckBox everyday;


    TimePickerDialog timePickerDialog;
    String amPM;

    String timePicked;
    int hourPicked;
    int minutePicked;


    String currUser;

    FirebaseDatabase database;
    DatabaseReference ref;

    Button save;
    Button back;

    UserReminder user;


    private boolean updating = false;
    private boolean monday = false;
    private boolean tuesday = false;
    private boolean wednesday = false;
    private boolean thursday = false;
    private boolean friday = false;
    private boolean sat = false;
    private boolean sun = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reminder);

        createNotificationChannel();

        user = (UserReminder) getIntent().getSerializableExtra("update");
        currUser = (String) getIntent().getStringExtra("currUser");

        // if this is not null, then we know we're updating an already existing reminder

        database = FirebaseDatabase.getInstance();
        ref = database.getReference().child("Users").child(currUser).child("Reminders");


        et_nickname = findViewById(R.id.et_nickname);
        et_pill = findViewById(R.id.et_pillName);
        et_numPills = findViewById(R.id.et_pillAmount);
        et_description = findViewById(R.id.et_Description);
        mon = findViewById(R.id.cb_Monday);
        tues = findViewById(R.id.cb_Tuesday);
        wed = findViewById(R.id.cb_Wednesday);
        thurs = findViewById(R.id.cb_Thursday);
        fri =  findViewById(R.id.cb_Friday);
        satur = findViewById(R.id.cb_Saturday);
        sund = findViewById(R.id.cb_Sunday);
        everyday = findViewById(R.id.cb_Everyday);


        back = (Button) findViewById(R.id.btn_Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainActivity();
            }
        });

        save = (Button) findViewById(R.id.btn_Save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveReminder();
            }
        });


        et_Time = findViewById(R.id.et_Time);
        et_Time.setClickable(true);
        et_Time.setLongClickable(true);
        et_Time.setInputType(InputType.TYPE_NULL);
        et_Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                timePickerDialog = new TimePickerDialog(CreateReminder.this, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        hourPicked = hourOfDay;
                        minutePicked = minute;
                        if (hourOfDay >= 12)
                        {
                            hourOfDay -= 12;
                            amPM = "PM";
                        }
                        else
                        {
                            amPM = "AM";
                        }
                        et_Time.setText(String.format("%02d:%02d", hourOfDay, minute) + amPM);
                        timePicked = String.format("%02d:%02d", hourOfDay, minute) + amPM;
                    }
                }, 0, 0, false);

                timePickerDialog.show();
            }
        });

        if (user != null)
        {
            updating = true;
            updateReminder();
        }

        if(getIntent().getStringExtra("name") != null){
            et_pill.setText(getIntent().getStringExtra("name"));
        }
        StringBuilder sb = new StringBuilder();

        if(getIntent().getStringExtra("imprint") != null){
            sb.append(" Imprint: " + getIntent().getStringExtra("imprint"));
        }
        if(getIntent().getStringExtra("size") != null){
            sb.append(" Size: " + getIntent().getStringExtra("size"));
        }
        if(getIntent().getStringExtra("color") != null){
            sb.append(" Color: " + getIntent().getStringExtra("color"));
        }
        et_description.setText(sb.toString());


    }

    public void onCheckboxClicked(View view)
    {
        boolean checked = ((CheckBox) view).isChecked();

        if (view.getId() == R.id.cb_Monday)
        {
            monday = checked;
        }
        if (view.getId() == R.id.cb_Tuesday)
        {
            tuesday = checked;
        }
        if (view.getId() == R.id.cb_Wednesday)
        {
            wednesday = checked;
        }
        if (view.getId() == R.id.cb_Thursday){

            thursday = checked;
        }
        if (view.getId() == R.id.cb_Friday)
        {
            friday = checked;
        }
        if (view.getId() == R.id.cb_Saturday)
        {
            sat = checked;
        }
        if (view.getId() == R.id.cb_Sunday)
        {
            sun = checked;
        }
        if (view.getId() == R.id.cb_Everyday)
        {
            if (checked)
            {
                monday = true;
                tuesday = true;
                wednesday = true;
                thursday = true;
                friday = true;
                sat = true;
                sun = true;
            }
            else
            {
                monday = false;
                tuesday = false;
                wednesday = false;
                thursday = false;
                friday = false;
                sat = false;
                sun = false;
            }
        }
    }

    public void saveReminder()
    {

        UserReminder userReminder = new UserReminder();
        String pillAmnt = et_numPills.getText().toString();
        String pillName = et_pill.getText().toString();
        String nickname = et_nickname.getText().toString();
        String description = et_description.getText().toString();

        if (pillAmnt.equals("") || pillName.equals("") || nickname.equals("") || timePicked == null || description.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Please complete all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        ref.child(nickname).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                // if a pill with that name already exists in the DB, tell them to enter something else
                if (snapshot.getValue() != null && !updating) {
                    Toast.makeText(getApplicationContext(), "You've already picked that for a nickname!", Toast.LENGTH_SHORT).show();
                } else if (snapshot.getValue() == null || updating)
                {

                    userReminder.setPillName(pillName);
                    userReminder.setPillQuantity(pillAmnt);
                    userReminder.setPillTime(timePicked);
                    userReminder.setPillDescription(description);
                    userReminder.setPillNickname(nickname);
                    if(getIntent().getStringExtra("url") != null) userReminder.setPillImageURL(getIntent().getStringExtra("url"));


                    if (monday) {
                        userReminder.setTakeOnMonday(true);
                        ref.child(nickname).setValue(userReminder);

                    }
                    if (tuesday) {
                        userReminder.setTakeOnTuesday(true);
                        ref.child(nickname).setValue(userReminder);
                    }
                    if (wednesday) {
                        userReminder.setTakeOnWednesday(true);
                        ref.child(nickname).setValue(userReminder);
                    }
                    if (thursday) {
                        userReminder.setTakeOnThursday(true);
                        ref.child(nickname).setValue(userReminder);
                    }
                    if (friday) {
                        userReminder.setTakeOnFriday(true);
                        ref.child(nickname).setValue(userReminder);
                    }
                    if (sat) {
                        userReminder.setTakeOnSat(true);
                        ref.child(nickname).setValue(userReminder);
                    }
                    if (sun) {
                        userReminder.setTakeOnSun(true);
                        ref.child(nickname).setValue(userReminder);
                    }
                    Toast.makeText(getApplicationContext(), "Saved reminder successfully!", Toast.LENGTH_SHORT).show();


                    // --------------------- Settings for notifications ----------------------------------

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, hourPicked);
                    calendar.set(Calendar.MINUTE, minutePicked - 1);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MILLISECOND, 0);

                    if (calendar.getTime().compareTo(new Date()) < 0)
                    {
                        Log.d("CALENDAR", "incremented day");
                        calendar.add(Calendar.DAY_OF_MONTH, 1);
                    }

                    Log.d("CALENDAR", "hour picked: " + hourPicked);
                    Log.d("CALENDAR", "minute picked: " + minutePicked);
                    Log.d("CALENDAR", "day scheduled: " + calendar.get(Calendar.DAY_OF_WEEK));
                    Intent intent = new Intent(CreateReminder.this, NotificationReceiver.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(CreateReminder.this, 0, intent, 0);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                    if (alarmManager != null)
                    {
                        Log.d("CALENDAR", "ALARM NOT NULL");
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
                    }
                    else
                    {
                        String piNull = "false";
                        if (pendingIntent == null) {piNull = "true";}
                        Log.d("CALENDAR", "ALARM MANAGER IS NULL");
                        Log.d("CALENDAR", "PENDING INTENT: " + piNull);
                    }



                    // -------------------- END notification settings ------------------------------------





                    Intent goToMainActivity = new Intent(CreateReminder.this, MainActivity.class);
                    goToMainActivity.putExtra("currUser", currUser);
                    startActivity(goToMainActivity);
                    finish();
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void updateReminder()
    {
        // When user wants to update a reminder, instead of creating an entirely different activity
        // just for that, we can pre populate the CreateReminder.java fields
        // with the information from the clicked reminder, which is what we do below.


        // Made the nickname uneditable because yeah that would mess up the database
        // could probably change this functionality in the future by
        // deleting and creating a new reminder under the new nickname but it isnt of
        // high priority right now.
        et_nickname.setText(user.getPillNickname());
        et_nickname.setEnabled(false);

        et_pill.setText(user.getPillName());
        et_description.setText(user.getPillDescription());
        et_numPills.setText(user.getPillQuantity());
        et_Time.setHint(user.getPillTime());

        mon.setChecked(user.isTakeOnMonday());
        tues.setChecked(user.isTakeOnTuesday());
        wed.setChecked(user.isTakeOnWednesday());
        thurs.setChecked(user.isTakeOnThursday());
        fri.setChecked(user.isTakeOnFriday());
        satur.setChecked(user.isTakeOnSat());
        sund.setChecked(user.isTakeOnSun());


        // Repurposing the Save button so it saves the information and things without doing the error checks
        // that we do when creating a new reminder

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserReminder userReminder = new UserReminder();

                String pillAmnt = et_numPills.getText().toString();
                String pillName = et_pill.getText().toString();
                String description = et_description.getText().toString();

                if (pillAmnt.equals("") || pillName.equals("") ||  timePicked == null || description.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Please complete all fields.", Toast.LENGTH_SHORT).show();
                    return;
                }



                ref.child(user.getPillNickname()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        ref.child(user.getPillNickname()).child("pillDescription").setValue(description);
                        ref.child(user.getPillNickname()).child("pillName").setValue(pillName);
                        ref.child(user.getPillNickname()).child("pillQuantity").setValue(pillAmnt);
                        ref.child(user.getPillNickname()).child("pillTime").setValue(timePicked);

                        if (monday) {
                            userReminder.setTakeOnMonday(true);
                            ref.child(user.getPillNickname()).child("takeOnMonday").setValue(true);

                        }
                        if (tuesday) {
                            userReminder.setTakeOnTuesday(true);
                            ref.child(user.getPillNickname()).child("takeOnTuesday").setValue(true);
                        }
                        if (wednesday) {
                            userReminder.setTakeOnWednesday(true);
                            ref.child(user.getPillNickname()).child("takeOnWednesday").setValue(true);
                        }
                        if (thursday) {
                            userReminder.setTakeOnThursday(true);
                            ref.child(user.getPillNickname()).child("takeOnThursday").setValue(true);
                        }
                        if (friday) {
                            userReminder.setTakeOnFriday(true);
                            ref.child(user.getPillNickname()).child("takeOnFriday").setValue(true);
                        }
                        if (sat) {
                            userReminder.setTakeOnSat(true);
                            ref.child(user.getPillNickname()).child("takeOnSat").setValue(true);                        }
                        if (sun) {
                            userReminder.setTakeOnSun(true);
                            ref.child(user.getPillNickname()).child("takeOnSun").setValue(true);
                        }
                        Toast.makeText(getApplicationContext(), "Saved successfully!", Toast.LENGTH_SHORT).show();
                        Intent goToMainActivity = new Intent(CreateReminder.this, MainActivity.class);
                        goToMainActivity.putExtra("currUser", currUser);
                        startActivity(goToMainActivity);
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }

    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            CharSequence name = "RxmindChannel";
            String description = "Channel for Rxmind notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = new NotificationChannel("1001", name, importance);
            notificationChannel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
            Log.d("CALENDAR", "NOTIFICATION CHANNEL");
        }

    }

    public void goToMainActivity()
    {
        Intent goToMainActivity = new Intent(this, MainActivity.class);
        goToMainActivity.putExtra("currUser", currUser);
        startActivity(goToMainActivity);
        finish();
    }

}