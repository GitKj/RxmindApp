package com.example.rxmindapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/*
@TODO:

   1. Just clean up code and add comments

@CURRENT BUGS:



 */
public class MainActivity extends AppCompatActivity {

    private ListView lv;
    FloatingActionButton fab;
    BottomAppBar bottomAppBar;

    private String currUsername;
    FirebaseDatabase database;
    DatabaseReference ref;

    ArrayList<UserReminder> reminders;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.list_medication);
        fab = findViewById(R.id.fab);

        bottomAppBar = findViewById(R.id.bottomAppBar);

        // What are we doing here?
        // Well, to know which user is logged into the application,
        // we can access the current user information by getting child element of
        // the current email, seen in the next few lines

        currUsername = (String) getIntent().getStringExtra("currUser");

        database = FirebaseDatabase.getInstance();
        ref = database.getReference().child("Users").child(currUsername);


        reminders = new ArrayList<>();
        ReminderAdapter reminderAdapter = new ReminderAdapter(reminders, this);
        lv.setAdapter(reminderAdapter);

        // Below, we're getting an updated list of all the reminders
        // for the current logged in user
        ref.child("Reminders").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                reminders.clear();
                for (DataSnapshot ds : snapshot.getChildren())
                {
                    UserReminder reminder = ds.getValue(UserReminder.class);
                    reminders.add(reminder);
                    reminderAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        // When we long click a list view item, a menu pops up
        // Then we can use "itemID" to see if update or delete was clicked,
        // and do corresponding actions
        lv.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                //menu.setHeaderTitle("Choose an option");
                menu.add(Menu.NONE, 0, 0, "Update");
                menu.add(Menu.NONE, 1, 0, "Delete");

            }
        });

    }

    // We need this to gather information on which items were clicked from the menu above.
    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        AdapterView.AdapterContextMenuInfo menuInfo;
        menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position;
        switch (item.getItemId()) {

            case 0:

                position = (int) menuInfo.id;

                UserReminder reminder = reminders.get(position);

                Intent goToCreateActivity = new Intent(this, CreateReminder.class);
                goToCreateActivity.putExtra("update", reminder);
                goToCreateActivity.putExtra("currUser", currUsername);
                startActivity(goToCreateActivity);

                break;
            case 1:

                position = (int) menuInfo.id;
                UserReminder deleting = reminders.get(position);
                String tempNickname = deleting.getPillNickname();

                //Delete functionality for Firebase:
                ref.child("Reminders").child(tempNickname).setValue(null);
                Toast.makeText(this, "Successfully deleted!", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this, "Error occurred when selecting.", Toast.LENGTH_SHORT).show();
        }
        return true;
    }


    public void createItem(View view)
    {
        Intent goToSearchActivity = new Intent(this, SearchActivity.class);
        goToSearchActivity.putExtra("currUser", currUsername);
        goToSearchActivity.putExtra("create", true);
        startActivity(goToSearchActivity);
    }

    public void onItemSelected(MenuItem item)
    {
        switch(item.getItemId()) {

            // when user clicks corresponding menu items, it switches to that activity.
            case R.id.bnb_list:
                //Toast.makeText(this, "List tab checked.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bnb_calendar:

                Intent goToCalendarView = new Intent(this, calendarView.class);
                goToCalendarView.putExtra("currUser", currUsername);
                startActivity(goToCalendarView);
                finish();
                break;
            case R.id.bnb_Search:

                Intent goToSearchActivity = new Intent(this, SearchActivity.class);
                goToSearchActivity.putExtra("currUser", currUsername);
                goToSearchActivity.putExtra("create", false);
                startActivity(goToSearchActivity);
                finish();

                break;
            default:
                break;
        }
    }
}