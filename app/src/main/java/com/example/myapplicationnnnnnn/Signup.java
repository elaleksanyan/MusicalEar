package com.example.myapplicationnnnnnn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import kotlin.OverloadResolutionByLambdaReturnType;

public class Signup extends AppCompatActivity {

    EditText signup_username;
    EditText signup_email;
    EditText signup_pass;
    Button signup_btn;
    TextView signedup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signup_username = findViewById(R.id.signup_username);
        signup_email = findViewById(R.id.signup_email);
        signup_pass = findViewById(R.id.signup_pass);
        signup_btn = findViewById(R.id.signup_btn);
        signedup = findViewById(R.id.signedup);
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Signup", "Signup button clicked");
                setContentView(R.layout.login);
            }
        });

    }
}