package com.example.rxmindapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class calendarList extends AppCompatActivity {

    String day;
    String currUser;

    FirebaseDatabase database;
    DatabaseReference ref;

    ListView lv;

    TextView tv_Title;

    ArrayList<UserReminder> reminders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_list);

        currUser = (String) getIntent().getStringExtra("currUser");
        day = getIntent().getStringExtra("day");

        database = FirebaseDatabase.getInstance();
        ref = database.getReference().child("Users").child(currUser).child("Reminders");

        lv = (ListView) findViewById(R.id.lv_pills);

        tv_Title = findViewById(R.id.tv_CLTitle);
        tv_Title.setText("The pills you take on " + day);

        reminders = new ArrayList<>();
        ReminderAdapter reminderAdapter = new ReminderAdapter(reminders, this);
        lv.setAdapter(reminderAdapter);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                reminders.clear();
                for (DataSnapshot ds : snapshot.getChildren())
                {
                    String days = "takeOn" + day;
                    boolean clickedDay = ds.child(days).getValue(Boolean.class);

                    if (clickedDay)
                    {
                        UserReminder reminder = ds.getValue(UserReminder.class);
                        reminders.add(reminder);
                        reminderAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}