package com.example.rxmindapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateReminder extends AppCompatActivity {

    EditText et_Time;
    TimePickerDialog timePickerDialog;
    String amPM;
    User user;
    String timePicked;

    FirebaseDatabase database;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reminder);

        // Uncomment the next 2 lines when firebase is set up.
        // database = FirebaseDatabase.getInstance();
        // ref = database.getReference().child("Users");


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
    }

    public void saveReminder(View v)
    {

        UserReminder userReminder = new UserReminder();
        userReminder.setPillName(findViewById(R.id.et_pillName).toString());
        userReminder.setPillQuantity(findViewById(R.id.et_pillAmount).toString());
        userReminder.setPillTime(findViewById(R.id.et_Time).toString());

        int numDaysSelected = 0;
        if (findViewById(R.id.cb_Monday).isSelected())
        {
            numDaysSelected++;
            userReminder.getPillDays().add("Monday");
        }
        if (findViewById(R.id.cb_Tuesday).isSelected())
        {
            numDaysSelected++;
            userReminder.getPillDays().add("Tuesday");
        }
        if (findViewById(R.id.cb_Wednesday).isSelected())
        {
            numDaysSelected++;
            userReminder.getPillDays().add("Wednesday");
        }
        if (findViewById(R.id.cb_Thursday).isSelected())
        {
            numDaysSelected++;
            userReminder.getPillDays().add("Thursday");
        }
        if (findViewById(R.id.cb_Friday).isSelected())
        {
            numDaysSelected++;
            userReminder.getPillDays().add("Friday");
        }
        if (findViewById(R.id.cb_Saturday).isSelected())
        {
            numDaysSelected++;
            userReminder.getPillDays().add("Saturday");
        }
        if (findViewById(R.id.cb_Sunday).isSelected())
        {
            numDaysSelected++;
            userReminder.getPillDays().add("Sunday");
        }
        if (findViewById(R.id.cb_Everyday).isSelected())
        {
            numDaysSelected++;
            userReminder.getPillDays().add("Monday");
            userReminder.getPillDays().add("Tuesday");
            userReminder.getPillDays().add("Wednesday");
            userReminder.getPillDays().add("Thursday");
            userReminder.getPillDays().add("Friday");
            userReminder.getPillDays().add("Saturday");
            userReminder.getPillDays().add("Sunday");
        }

        user.getUserReminders().add(userReminder);


        //Toast.makeText(CreateReminder.this, "Time: " + timePicked, Toast.LENGTH_SHORT).show();


        // line 114 adds the data to the firebase under the corresponding user.
        // Uncomment below line when firebase is setup
        // ref.child(user.getUsername()).setValue(userReminder);

    }

}