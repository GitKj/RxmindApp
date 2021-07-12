package com.example.rxmindapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

    //ArrayList<UserReminder> reminders;
    ArrayList<String> pillNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_list);

        currUser = (String) getIntent().getStringExtra("currUser");
        day = getIntent().getStringExtra("day");

        database = FirebaseDatabase.getInstance();
        ref = database.getReference().child("Users").child(currUser).child(day);

        lv = (ListView) findViewById(R.id.lv_pills);

        pillNames = new ArrayList<>();

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds : snapshot.getChildren())
                {
                    // Should add the list of pills to the array
                    pillNames.add(ds.getValue(String.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, pillNames);

        lv.setAdapter(adapter);



    }
}