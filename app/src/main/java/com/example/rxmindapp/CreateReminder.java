package com.example.rxmindapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateReminder extends AppCompatActivity {

    EditText et_Time;
    TimePickerDialog timePickerDialog;
    String amPM;
    User user;

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
                    }
                }, 0, 0, false);

                timePickerDialog.show();
            }
        });
    }

    public void saveReminder(View view)
    {
        UserReminder userReminder = new UserReminder();
        userReminder.setPillName(view.findViewById(R.id.et_pillName).toString());
        userReminder.setPillQuantity(view.findViewById(R.id.et_pillAmount).toString());
        userReminder.setPillTime(view.findViewById(R.id.et_Time).toString());


        if (view.findViewById(R.id.cb_Monday).isSelected())
        {
            userReminder.getPillDays().add("Monday");
        }
        if (view.findViewById(R.id.cb_Tuesday).isSelected())
        {
            userReminder.getPillDays().add("Tuesday");
        }
        if (view.findViewById(R.id.cb_Wednesday).isSelected())
        {
            userReminder.getPillDays().add("Wednesday");
        }
        if (view.findViewById(R.id.cb_Thursday).isSelected())
        {
            userReminder.getPillDays().add("Thursday");
        }
        if (view.findViewById(R.id.cb_Friday).isSelected())
        {
            userReminder.getPillDays().add("Friday");
        }
        if (view.findViewById(R.id.cb_Saturday).isSelected())
        {
            userReminder.getPillDays().add("Saturday");
        }
        if (view.findViewById(R.id.cb_Sunday).isSelected())
        {
            userReminder.getPillDays().add("Sunday");
        }
        if (view.findViewById(R.id.cb_Everyday).isSelected())
        {
            userReminder.getPillDays().add("Monday");
            userReminder.getPillDays().add("Tuesday");
            userReminder.getPillDays().add("Wednesday");
            userReminder.getPillDays().add("Thursday");
            userReminder.getPillDays().add("Friday");
            userReminder.getPillDays().add("Saturday");
            userReminder.getPillDays().add("Sunday");
        }
        user.getUserReminders().add(userReminder);

        // line 114 adds the data to the firebase under the corresponding user.
        // Uncomment below line when firebase is setup
        // ref.child(user.getUsername()).setValue(userReminder);

    }

}