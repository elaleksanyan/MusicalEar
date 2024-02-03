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

    private Button notes_btn, intervals_btn, back_btn;
    private NotesFragment firstFragment = new NotesFragment();
    private IntervalsFragment secondFragment = new IntervalsFragment();
    @Override    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getSupportActionBar().hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_page);
        notes_btn = findViewById(R.id.notes);
        intervals_btn = findViewById(R.id.intervals);
        back_btn = findViewById(R.id.button_back);
        setNewFragment();

        notes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewFragment(firstFragment);            }
        });
        intervals_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntervalsFragment secondFragment = new IntervalsFragment();
                setNewFragment(secondFragment);            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LearnPageActivity.this, MainPageActivity.class);
                startActivity(intent);
            }
        });

    }
    private void setNewFragment() {}

    private void setNewFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_layout, fragment);
        ft.commit();    }






}