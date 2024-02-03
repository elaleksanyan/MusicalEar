package com.example.myapplicationnnnnnn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class PlayPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_page);
    }
}