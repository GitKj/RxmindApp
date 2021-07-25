package com.example.rxmindapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.Calendar;

public class calendarView extends AppCompatActivity {

    CalendarView cv;
    Button back;
    String currUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_view);

        back = findViewById(R.id.btn_calBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent goToMainActivity = new Intent(calendarView.this, MainActivity.class);
                goToMainActivity.putExtra("currUser", currUser);
                startActivity(goToMainActivity);
                finish();
            }
        });

        currUser = (String) getIntent().getStringExtra("currUser");

        cv = (CalendarView) findViewById(R.id.calendar);

        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                // When a date is selected, we can take to a list view with all of the pills on that day
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                getPillOnDay(dayOfWeek);
            }
        });
    }

    public void getPillOnDay(int day)
    {
        String dayOfWeek ="";

        if (day == 1)
        {
            dayOfWeek = "Sun";
        }
        else if (day == 2)
        {
            dayOfWeek = "Monday";
        }
        else if (day == 3)
        {
            dayOfWeek = "Tuesday";
        }
        else if (day == 4)
        {
            dayOfWeek = "Wednesday";
        }
        else if (day == 5)
        {
            dayOfWeek = "Thursday";
        }
        else if (day == 6)
        {
            dayOfWeek = "Friday";
        }
        else if (day == 7)
        {
            dayOfWeek = "Sat";
        }

        //Toast.makeText(this, "Day clicked: " + day, Toast.LENGTH_SHORT).show();
        Intent switchToListView = new Intent(calendarView.this, calendarList.class);
        switchToListView.putExtra("day", dayOfWeek);
        switchToListView.putExtra("currUser", currUser);
        startActivity(switchToListView);
    }

}