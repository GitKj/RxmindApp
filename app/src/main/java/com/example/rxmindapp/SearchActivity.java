package com.example.rxmindapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.common.RequestBuilder;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity {

    private String currUser;

    EditText name;
    EditText imprint;
    EditText shape;
    EditText color;
    Button searchButton;

    LinearLayout buttonTray;
    Button back;
    Button skip;


    private boolean creating;

    APIResponse subject;
    public ArrayList<NlmRxImage> searchResults;
    ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        currUser = (String) getIntent().getStringExtra("currUser");

        AndroidNetworking.initialize(getApplicationContext());

        name = (EditText) findViewById(R.id.et_drugname);
        imprint = (EditText) findViewById(R.id.et_drugimprint);
        color = (EditText) findViewById(R.id.et_drugcolor);
        shape = (EditText) findViewById(R.id.et_drugshape);

        searchButton = (Button) findViewById(R.id.btn_search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //gather search parameters
                String pillName = name.getText().toString();
                String pillColor = color.getText().toString();
                String pillImprint = imprint.getText().toString();

                //input validation
                if (pillColor.equals("") || pillName.equals("") || pillImprint.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Please complete all fields.", Toast.LENGTH_SHORT).show();
                    return;
                }

                //building request string from parameter
                StringBuilder sb = new StringBuilder();
                sb.append("name=");
                sb.append(name.getText());
                sb.append("&");
                sb.append("color=");
                sb.append(color.getText());
                sb.append("&");
                sb.append("imprint=");
                sb.append(imprint.getText());
                String requestString = sb.toString();

                Log.i("req", "requestString = " + requestString);

                //make API request
                makeRequest(requestString);

                Log.i("res", "Size: " + searchResults.size());
                //set search results to display adapter on listview lv
                ResultAdapter resultAdapter = new ResultAdapter(searchResults, SearchActivity.this);
                lv.setAdapter(resultAdapter);
            }
        });

        buttonTray = (LinearLayout) findViewById(R.id.ll_ButtonTray);
        skip = findViewById(R.id.btn_Skip);
        //enables dual use of SearchActivity by hiding unnecessary UI elements when not creating
        //new UserReminder
        creating = (Boolean) getIntent().getBooleanExtra("create", false);
        if(creating){
            //if creating new UserReminder, enable skip button
            skip.setText("Skip Search");
            skip.setId(View.generateViewId());
            skip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SkipToCreateReminder();
                }
            });
        } else skip.setVisibility(View.INVISIBLE); //else disable

        back = (Button) findViewById(R.id.btn_Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainActivity();
            }
        });

        lv = (ListView) findViewById(R.id.list_possible);
        searchResults = new ArrayList<>();
        ResultAdapter resultAdapter = new ResultAdapter(searchResults, this);
        lv.setAdapter(resultAdapter);
        lv.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                //menu.setHeaderTitle("Choose an option");
                menu.add(Menu.NONE, 0, 0, "Choose");
            }
        });


    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        AdapterView.AdapterContextMenuInfo menuInfo;
        menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position;
        switch (item.getItemId()) {

            case 0:

                position = (int) menuInfo.id;

                NlmRxImage result = searchResults.get(position);

                Intent goToCreateActivity = new Intent(this, CreateReminder.class);
                goToCreateActivity.putExtra("name", result.getName());
                goToCreateActivity.putExtra("imprint", imprint.getText());
                goToCreateActivity.putExtra("color", color.getText());
                goToCreateActivity.putExtra("url", result.getImageUrl());

                goToCreateActivity.putExtra("currUser", currUser);
                startActivity(goToCreateActivity);


                break;
        }
        return true;
    }

    private void makeRequest(String params){
        // builds request URL
        String input = "rxnav?" + params;

        Log.i("req", "full request = " + input);

        //uses Fast Android Networking Library to send request
        ANRequest req = AndroidNetworking.get("https://rximage.nlm.nih.gov/api/rximage/1/{resource}")
                .addPathParameter("resource", input)
                .setPriority(Priority.LOW)
                .build();
        //returns request as java library JSONObject
        req.getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                //convert from java library JSONObject to string
                String jobj = response.toString();
                //convert string into Gson library JsonObject, and parses using custom APIResponse deserializer
                final Gson gson = new GsonBuilder().registerTypeAdapter(APIResponse.class, new APIResponseDeserializer()).create();
                subject = gson.fromJson(jobj, APIResponse.class);

                //set searchResults equal to feedback
                searchResults = new ArrayList<>(subject.getNlmRxImages());
                //if no results found then annouce failure
                if(searchResults.size() == 0) Toast.makeText(getApplicationContext(), "No results found", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(ANError error) {
                if (error.getErrorCode() != 0) {
                    // received error from server
                    // error.getErrorCode() - the error code from server
                    // error.getErrorBody() - the error body from server
                    // error.getErrorDetail() - just an error detail
                    Log.i("err", "onError errorCode : " + error.getErrorCode());
                    Log.i("err", "onError errorBody : " + error.getErrorBody());
                    Log.i("err", "onError errorDetail : " + error.getErrorDetail());

                } else {
                    // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                    Log.i("err", "onError errorDetail : " + error.getErrorDetail());
                }
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