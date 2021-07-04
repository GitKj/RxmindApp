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
        // database = FirebaseDatabase.getInstance();
        // ref = database.getReference().child("Users");


        btnLogin = findViewById(R.id.btn_login);
        et_user = findViewById(R.id.et_username);
        et_pass = findViewById(R.id.et_password);

        tv_errorMessage = findViewById(R.id.tv_loginError);
    }

    public void loginSuccess(View view)
    {

        String user = et_user.getText().toString();
        String pass = et_pass.getText().toString();

        // Uncomment below segments when firebase is ready.
        // This segment is checking for correct login information.
        // In this case, we are grabbing the information associated with the corresponding user.
        // With this information, we are checking the password to see if it is currently in the DB

        /*
        if (ref.child(user) != null) {
            ref.child(user).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User tempUser = snapshot.getValue(User.class);
                    if (pass.equals(tempUser.getPassword())) {
                        Toast.makeText(loginScreen.this, "Login Successful!", Toast.LENGTH_SHORT).show();


                        Intent goToMainActivity = new Intent(loginScreen.this, MainActivity.class);
                        goToMainActivity.putExtra("currUser", tempUser);
                        startActivity(goToMainActivity);
                    } else {
                        Toast.makeText(loginScreen.this, "Username or password incorrect.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else
        {
            Toast.makeText(loginScreen.this, "Created a new account.", Toast.LENGTH_SHORT).show();
        }
        */

        // default login until we get firebase up and going
        if (et_user.getText().toString().equals("admin") && et_pass.getText().toString().equals("admin"))
        {
            Intent goToMainActivity = new Intent(this, MainActivity.class);
            startActivity(goToMainActivity);
        }
        else
        {
            tv_errorMessage.setText("Username or password is incorrect. Try again.");
            tv_errorMessage.setTextColor(Color.RED);
        }
    }
}
