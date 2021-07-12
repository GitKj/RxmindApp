package com.example.rxmindapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;

import java.util.Calendar;

public class calendarView extends AppCompatActivity {

    CalendarView cv;

    String currUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_view);

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
        if (day == 0)
        {
            dayOfWeek = "sunday";
        }
        else if (day == 1)
        {
            dayOfWeek = "monday";
        }
        else if (day == 2)
        {
            dayOfWeek = "tuesday";
        }
        else if (day == 3)
        {
            dayOfWeek = "wednesday";
        }
        else if (day == 4)
        {
            dayOfWeek = "thursday";
        }
        else if (day == 5)
        {
            dayOfWeek = "friday";
        }
        else if (day == 6)
        {
            dayOfWeek = "saturday";
        }

        Intent switchToListView = new Intent(calendarView.this, calendarList.class);
        switchToListView.putExtra("day", dayOfWeek);
        switchToListView.putExtra("currUser", currUser);
        startActivity(switchToListView);
    }

}