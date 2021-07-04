package com.example.rxmindapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/*
@TODO:
    1. Work on registration feature
    2. Work on ability to update reminders
        a. Possible way to implement this is when user long clicks a reminder,
            have an option to update. When this option is pressed,
            it takes them to createReminder activity, however, fields
            will be autopopulated with the reminder information
   3. Work on ability to delete reminders
        a. Possible way to implement this is by doing a long click option
   4. Work on calendar view
        a. There is a calendar widget
   5. Work on calling FDC API to get simple pill information.
   6. [IMPORTANT] Check to see if days and times are properly saved using a toast or something.


 */
public class MainActivity extends AppCompatActivity {

    private ListView lv;
    ArrayList<String> testObjects = new ArrayList<String>();
    FloatingActionButton fab;
    BottomAppBar bottomAppBar;
    private User currentUser;

    private String currUserName;
    FirebaseDatabase database;
    DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.list_medication);
        fab = findViewById(R.id.fab);

        bottomAppBar = findViewById(R.id.bottomAppBar);

        // What are we doing here?
        // Well, to know which user is logged into the application,
        // We needed to pass a "User" object from login screen, to this activity.
        // Passing the entire object allows us to access all other information in the "User"
        // class, such as the arraylist.
        currentUser = (User) getIntent().getSerializableExtra("currUser");

        // Uncomment the next 2 lines when firebase is set up.
        // database = FirebaseDatabase.getInstance();
        // ref = database.getReference().child("Users");



        // Down here is where we need to populate currentUser array with reminders
        // from firebase for that specific user.

        // We can use this tutorial for help: https://www.youtube.com/watch?v=a6dkWmYo_Qc




        // STEP 1: Get the information of the logged in user, using firebase.
        // Uncomment below segments when firebase is ready.
        // This segment is checking for correct login information.
        // In this case, we are grabbing the information associated with the corresponding user.
        // With this information, we are loading the user reminders.
        // This also makes sure the list is always up to date.

        /*

        ref.child(currentUser.getUsername()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user.getUserReminders() != null) {
                    currentUser.setUserReminders(user.getUserReminders());
                } else {
                    // Do nothing if there are no reminders to get.
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        */


        // STEP 2: Populate the Listview with the current users reminders

        /*
        ArrayAdapter<UserReminder> arrayAdapter = new ArrayAdapter<>(
        this,
        android.R.layout.simple_list_item_1,
        currentUser.getUserReminders()
        )

        lv.setAdapter(arrayAdapter);

         */

        // STEP 3: The pill tab should now hold all current reminders for current logged in user.



        // Test options, we can delete this once we get user medications and things working.
        testObjects.add("Object 1");
        testObjects.add("Object 2");
        testObjects.add("Object 3");

        // This is the array adapter that is NEEDED to populate the list view with user medications.
        // When ready, change ArrayAdapter<String> to ArrayAdapter<UserReminder> and
        // testObjects to userReminders
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                testObjects
        );

        // Populating the list view.
        lv.setAdapter(arrayAdapter);

    }

    public void createItem(View view)
    {
        // When ready, we can change this toast so that when user clicks the FAB,
        // it opens some sort of activity/dialog to add a new medication to the database.
        Toast.makeText(this, "FAB clicked.", Toast.LENGTH_SHORT).show();
        Intent goToCreateActivity = new Intent(this, CreateReminder.class);
        startActivity(goToCreateActivity);
    }

    public void onItemSelected(MenuItem item)
    {
        switch(item.getItemId()) {

            // When ready, we can change these toasts so that when user clicks corresponding
            // menu items, it switches to that activity.
            case R.id.bnb_list:
                Toast.makeText(this, "List tab checked.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bnb_calendar:
                Toast.makeText(this, "Calendar tab checked.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bnb_Search:
                Toast.makeText(this, "Search tab checked.", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}