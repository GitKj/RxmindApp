package com.example.rxmindapp;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class loginScreen extends AppCompatActivity  {

    private String username;
    private String password;

    Button btnLogin;
    EditText et_user;
    EditText et_pass;

    TextView tv_errorMessage;

    FirebaseDatabase database;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        // Uncomment the next 2 lines when firebase is set up.
        database = FirebaseDatabase.getInstance();
        ref = database.getReference().child("Users");


        btnLogin = (Button) findViewById(R.id.btn_login);
        et_user = findViewById(R.id.et_username);
        et_pass = findViewById(R.id.et_password);

        tv_errorMessage = findViewById(R.id.tv_loginError);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginSuccess();
            }
        });
    }

    public void loginSuccess()
    {

        String user = et_user.getText().toString();

        // we replace since firebase does not allow periods in paths
        username = user.replace(".", ",");

        password = et_pass.getText().toString();


        // This segment is checking for correct login information.
        // In this case, we are grabbing the information associated with the corresponding user.
        // With this information, we are checking the password to see if it is currently in the DB


        // We use single value because we aren't going to consistently need this information.
        // Data will only be used once for this particular event (aka logging in).
        ref.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // the user doesn't exist in the database already, so we need to create them an account
                if (snapshot.getValue() == null)
                {
                    // adding a new user to the database with the corresponding password
                    ref.child(username).child("Password").setValue(password);


                    Toast.makeText(getApplicationContext(), "Created account successfully!", Toast.LENGTH_SHORT).show();

                    // Switching to main activity once the account is created
                    Intent goToMainActivity = new Intent(loginScreen.this, MainActivity.class);
                    goToMainActivity.putExtra("currUser", username);
                    startActivity(goToMainActivity);
                }
                else
                {
                    // If the email exists (so the user exists), we need to check to see if the password
                    // entered is the correct one matching the one in FB, if it is,
                    // then we need to go to main activity
                    String fbPass = snapshot.child("Password").getValue(String.class);

                    if (password.equals(fbPass))
                    {
                        Toast.makeText(getApplicationContext(), "Logged in successfully!", Toast.LENGTH_SHORT).show();
                        Intent goToMainActivity = new Intent(loginScreen.this, MainActivity.class);
                        goToMainActivity.putExtra("currUser", username);
                        startActivity(goToMainActivity);
                    }
                    else
                    {
                        tv_errorMessage.setText("Username or password is incorrect. Try again.");
                        tv_errorMessage.setTextColor(Color.RED);
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        // test login pre-firebase
        if (et_user.getText().toString().equals("admin") && et_pass.getText().toString().equals("admin"))
        {
            Intent goToMainActivity = new Intent(this, MainActivity.class);
            startActivity(goToMainActivity);
        }
    }
}
