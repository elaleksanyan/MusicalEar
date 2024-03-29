package com.example.myapplicationnnnnnn;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList; // Add this line to import ArrayList
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IntervalsLearn extends AppCompatActivity {
    ImageView onPiano;
    TextView textView;
    Button next_int;
    StorageReference storageReference;
    FirebaseFirestore db;
    List<String> images;
    int currentIndex = 0;
    DatabaseReference imageNamesRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learn_intervals);

        onPiano = findViewById(R.id.piano);
        next_int = findViewById(R.id.next_interval);
        textView = findViewById(R.id.textView);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference("Intervals/OnPiano");

        images = new ArrayList<>();
        images.add("prima.png");
        images.add("poqr_sekunda.png");
        images.add("mec_sekunda.png");
        images.add("poqr_tercia.png");
        images.add("mec_tercia.png");
        images.add("maqur_kvarta.png");
        images.add("maqur_kvinta.png");
        images.add("poqr_seksta.png");
        images.add("mec_seksta.png");
        images.add("poqr_septima.png");
        images.add("mec_septima.png");
        images.add("oktava.png");

        loadNextImage();

        next_int.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex++;
                if (currentIndex >= images.size()) {
                    currentIndex = 0; // Loop back to the first image
                }
                loadNextImage();

            }
        });

    }

    private void loadNextImage() {
        String imageName = images.get(currentIndex);
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
