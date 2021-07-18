package com.example.rxmindapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class SearchActivity extends AppCompatActivity {

    private String currUser;

    LinearLayout buttonTray;
    Button back;
    Button skip;
    Button add;


    private boolean creating = false;

    UserReminder user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        currUser = (String) getIntent().getStringExtra("currUser");

        buttonTray = (LinearLayout) findViewById(R.id.ll_ButtonTray);


        creating = (Boolean) getIntent().getBooleanExtra("create", false);
        if(creating){
            //set the properties for button

            skip = new Button(this);
            skip.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            skip.setText("Skip Search");
            skip.setId(View.generateViewId());
            skip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SkipToCreateReminder();
                }
            });

            //add button to the layout
            buttonTray.addView(skip);

            add = new Button(this);
            add.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            add.setText("Add Pill");
            add.setId(View.generateViewId());
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SkipToCreateReminder();
                }
            });

            //add button to the layout
            buttonTray.addView(add);

        }

        back = (Button) findViewById(R.id.btn_Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainActivity();
            }
        });
    }

    public void SkipToCreateReminder()
    {
        Intent goToCreateActivity = new Intent(this, CreateReminder.class);
        goToCreateActivity.putExtra("currUser", currUser);
        startActivity(goToCreateActivity);
        finish();
    }

    public void goToMainActivity()
    {
        Intent goToMainActivity = new Intent(this, MainActivity.class);
        goToMainActivity.putExtra("currUser", currUser);
        startActivity(goToMainActivity);
        finish();
    }
}