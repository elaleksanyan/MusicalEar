package com.example.myapplicationnnnnnn;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LearnPageActivity extends AppCompatActivity {

    private Button notes_btn, intervals_btn;
    private NotesFragment firstFragment = new NotesFragment();
    private IntervalsFragment secondFragment = new IntervalsFragment();
    @Override    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getSupportActionBar().hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_page);
        notes_btn = findViewById(R.id.notes);
        intervals_btn = findViewById(R.id.intervals);

        notes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.fragment_notes);
            }
        });

        intervals_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.fragment_intervals);
            }
        });

    }






}