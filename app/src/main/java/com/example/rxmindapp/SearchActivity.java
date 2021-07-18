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
import com.androidnetworking.interfaces.ParsedRequestListener;

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
    Button add;


    private boolean creating = false;

    public ArrayList<Result> searchResults;
    ListView lv;
    UserReminder user;

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
                Boolean nameEmpty = name.getText().equals("");
                Boolean colorEmpty = color.getText().equals("");
                Boolean shapeEmpty = shape.getText().equals("");
                Boolean imprintEmpty = imprint.getText().equals("");
                if(nameEmpty && colorEmpty && shapeEmpty && colorEmpty && imprintEmpty) {
                    return;
                }
                StringBuilder sb = new StringBuilder();
                if(!nameEmpty) sb.append("name=");
                sb.append(name.getText());
                if(!nameEmpty && !colorEmpty) sb.append("&");
                if(!colorEmpty) sb.append("color=");
                sb.append(color.getText());
                //if(!nameEmpty && !shapeEmpty) sb.append("&");
                //if(!shapeEmpty) sb.append("shape=");
                //sb.append(shape.getText());
                if(!nameEmpty && !imprintEmpty) sb.append("&");
                if(!imprintEmpty) sb.append("imprint=");
                sb.append(imprint.getText());
                String requestString = sb.toString();

                Log.i("req", "requestString = " + requestString);
                makeRequest(requestString);
            }
        });

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

                Result result = searchResults.get(position);


                //Intent goToCreateActivity = new Intent(this, CreateReminder.class);
                //goToCreateActivity.putExtra("update", reminder);
                //goToCreateActivity.putExtra("currUser", currUsername);
                //startActivity(goToCreateActivity);

                Toast.makeText(this, result.name, Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    private void makeRequest(String params){
        // https://financialmodelingprep.com/api/v3/quote/AAPL?apikey=demo
        String input = "rxnav?" + params;

        Log.i("req", "full request = " + input);

        ANRequest req = AndroidNetworking.get("https://rximage.nlm.nih.gov/api/rximage/1/{resource}")
                .addPathParameter("resource", input)
                .setPriority(Priority.LOW)
                .build();
        req.getAsObject(APIResponse.class, new ParsedRequestListener<APIResponse>() {
            @Override
            public void onResponse(APIResponse response) {
                searchResults = response.nlmRxImages;
                Log.i("response", "Success = " + response.received.success.toString());
                Log.i("response", "Imagecount = " + response.received.imageCount);

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