package com.example.myapplicationnnnnnn;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class NotesLearn extends AppCompatActivity {
    ImageView notes,notesOnPiano;
    Button next_note;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getSupportActionBar().hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.learn_notes);
        notes = findViewById(R.id.note_c);
        notesOnPiano = findViewById(R.id.notesOnPiano);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference notesSheets = storage.getReference("NotesOnPiano").child("c_piano.gif");
        StorageReference notesPiano = storage.getReference("Notes").child("notes.gif");

        notesSheets.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getApplicationContext())
                        .load(uri)
                        .into(notes);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Invalid load image", Toast.LENGTH_SHORT).show();
            }
        });

        notesPiano.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getApplicationContext())
                        .load(uri)
                        .into(notesOnPiano);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Invalid load image", Toast.LENGTH_SHORT).show();
            }
        });


    }
}