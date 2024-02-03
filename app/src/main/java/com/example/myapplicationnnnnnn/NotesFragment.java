package com.example.myapplicationnnnnnn;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class NotesFragment extends Fragment {

//    private Button next_note;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_notes, container, false);

        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        Button next_note = (Button) view.findViewById(R.id.next_note);

        return view;

    }}