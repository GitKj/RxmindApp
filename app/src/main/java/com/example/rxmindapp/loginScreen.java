package com.example.rxmindapp;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class loginScreen extends AppCompatActivity  {

    private String username;
    private String password;

    Button btnLogin;
    EditText et_user;
    EditText et_pass;

    TextView tv_errorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        btnLogin = findViewById(R.id.btn_login);
        et_user = findViewById(R.id.et_username);
        et_pass = findViewById(R.id.et_password);

        tv_errorMessage = findViewById(R.id.tv_loginError);
    }

    public void loginSuccess(View view)
    {
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
