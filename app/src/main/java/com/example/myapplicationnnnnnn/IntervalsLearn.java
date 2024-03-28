package com.example.myapplicationnnnnnn;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList; // Add this line to import ArrayList
import java.util.List;

public class IntervalsLearn extends AppCompatActivity {
    ImageView onPiano;
    Button next_int;
    StorageReference storageReference;
    List<String> imageNames;
    int currentIndex = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learn_intervals);

        onPiano = findViewById(R.id.piano);
        next_int = findViewById(R.id.next_interval);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference("Intervals/OnPiano");

        imageNames = new ArrayList<>();
        imageNames.add("prima.png");
        imageNames.add("poqr_sekunda.png");
        imageNames.add("mec_sekunda.png");
        imageNames.add("poqr_tercia.png");
        imageNames.add("mec_tercia.png");
        imageNames.add("maqur_kvarta.png");
        imageNames.add("maqur_kvinta.png");
        imageNames.add("poqr_seksta.png");
        imageNames.add("mec_seksta.png");
        imageNames.add("poqr_septima.png");
        imageNames.add("mec_septima.png");
        imageNames.add("oktava.png");

        loadNextImage();

        // Button click listener to load next image
        next_int.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex++;
                if (currentIndex >= imageNames.size()) {
                    currentIndex = 0; // Loop back to the first image
                }
                loadNextImage();
            }
        });
    }

    private void loadNextImage() {
        String imageName = imageNames.get(currentIndex);
        StorageReference imageRef = storageReference.child(imageName);
        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getApplicationContext())
                        .load(uri)
                        .into(onPiano);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Failed to load image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
