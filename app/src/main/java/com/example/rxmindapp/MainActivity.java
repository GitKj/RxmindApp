package com.example.rxmindapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lv;
    ArrayList<String> testObjects = new ArrayList<String>();
    FloatingActionButton fab;
    BottomAppBar bottomAppBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.list_medication);
        fab = findViewById(R.id.fab);

        bottomAppBar = findViewById(R.id.bottomAppBar);

        // Test options, we can delete this once we get user medications and things working.
        testObjects.add("Object 1");
        testObjects.add("Object 2");
        testObjects.add("Object 3");

        // This is the array adapter that is NEEDED to populate the list view with user medications.
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