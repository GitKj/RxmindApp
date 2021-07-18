package com.example.rxmindapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

        AndroidNetworking.initialize(getApplicationContext());

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

    private void makeRequest(String ticker){
        // https://financialmodelingprep.com/api/v3/quote/AAPL?apikey=demo
        ANRequest req = AndroidNetworking.get("https://financialmodelingprep.com/api/v3/quote/{ticker}")
                .addPathParameter("ticker", ticker)
                .addQueryParameter("apikey", "demo")
                .setPriority(Priority.LOW)
                .build();
        req.getAsObjectList(Quote.class, new ParsedRequestListener<List<Quote>>() {
            @Override
            public void onResponse(List<Quote> quotes) {
                String TAG = "FINANCIAL";
                Log.i(TAG, "userList size : " + quotes.size());
                for (Quote quote : quotes) {
                    Log.i(TAG, "symbol : " + quote.getSymbol());
                    Log.i(TAG, "name : " + quote.getName());
                    Log.i(TAG, "price : " + quote.getPrice());
                    String dayRange = "[" + String.format(Locale.US,"%.2f", quote.getDayLow()) + " - " + quote.getDayHigh() + "]";
                    String yearRange = "[" + String.format(Locale.US,"%.2f", quote.getYearLow()) + " - " + quote.getYearHigh() + "]";

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.ENGLISH);
                    LocalDate localDate = LocalDate.parse("2020-12-20T00:00:00.000Z", formatter);
                    Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                    ((TextView) findViewById(R.id.tickerText)).setText(quote.getSymbol());
                    ((TextView) findViewById(R.id.companyNameText)).setText(quote.getName());
                    ((TextView) findViewById(R.id.exchangeText)).setText(quote.getExchange());
                    ((TextView) findViewById(R.id.currentPriceText)).setText(String.format(Locale.US,"%.2f", quote.getPrice()));
                    ((TextView) findViewById(R.id.changeText)).setText(String.format(Locale.US,"%.2f", quote.getChange()));
                    ((TextView) findViewById(R.id.changePercentText)).setText(String.format(Locale.US,"%.2f %%", quote.getChangesPercentage()));
                    ((TextView) findViewById(R.id.priceAvg50Text)).setText(String.format(Locale.US,"%.2f", quote.getPriceAvg50()));
                    ((TextView) findViewById(R.id.dayRangeText)).setText(dayRange);
                    ((TextView) findViewById(R.id.yearRangeText)).setText(yearRange);
                    ((TextView) findViewById(R.id.epsText)).setText(String.format(Locale.US,"%.3f", quote.getEps()));
                    ((TextView) findViewById(R.id.sharesText)).setText(quote.getSharesOutstanding());
                    ((TextView) findViewById(R.id.earningsText)).setText(date.toString());
                    String toastText = "A share of " + quote.getName() + "is currently at $" + quote.getPrice();
                    Toast.makeText(getApplicationContext(),toastText, Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onError(ANError anError) {
                // handle error
                Toast.makeText(getApplicationContext(),"Error on getting data ", Toast.LENGTH_LONG).show();
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