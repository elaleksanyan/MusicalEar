package com.example.myapplicationnnnnnn;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LearnFragmentActivity extends AppCompatActivity {

    private Button notes_btn, intervals_btn;
    private NotesFragment firstFragment = new NotesFragment();
    @Override    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_fragment);
        notes_btn = findViewById(R.id.notes);
        intervals_btn = findViewById(R.id.intervals);
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
        });    }

    private void setNewFragment() {
    }

    private void setNewFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_layout, fragment);
        ft.commit();    }
}