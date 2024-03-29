package com.example.myapplicationnnnnnn;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class IntervalsLearn extends AppCompatActivity {

    ImageView onPiano;
    TextView textView;
    Button next_int;
    StorageReference storageReference;
    FirebaseFirestore db;
    List<String> names;
    List<String> images;
    int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learn_intervals);

        getSupportActionBar().hide();

        onPiano = findViewById(R.id.piano);
        textView = findViewById(R.id.textView);
        next_int = findViewById(R.id.next_interval);

        // Initialize Firebase instances
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference("Intervals/OnPiano");
        db = FirebaseFirestore.getInstance();

        // Initialize lists
        images = new ArrayList<>();
        names = new ArrayList<>();

        // Populate image list
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

        // Set initial image and string
        loadNextImage();
        fetchNextStringFromFirestore();

        // Set click listener for Next button
        next_int.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex++;
                if (currentIndex >= images.size()) {
                    currentIndex = 0; // Reset index when reaching end of images
                }
                loadNextImage();
                fetchNextStringFromFirestore();
            }
        });
    }

    private void fetchNextStringFromFirestore() {
        // Get document from Firestore collection
        db.collection("yourCollection").document("yourDocument")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String fieldName = "string" + (currentIndex + 1);
                        if (documentSnapshot.contains(fieldName)) {
                            String fetchedString = documentSnapshot.getString(fieldName);
                            textView.setText(fetchedString);
                        } else {
                            // Handle the case when the field does not exist
                            Toast.makeText(getApplicationContext(), "String not found in Firestore", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle failures
                        Toast.makeText(getApplicationContext(), "Failed to fetch string from Firestore: " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
