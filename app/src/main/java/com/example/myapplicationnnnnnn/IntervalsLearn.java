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
import com.example.myapplicationnnnnnn.R;
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

<<<<<<< HEAD
        getSupportActionBar().hide();

        onPiano = findViewById(R.id.piano);
        textView = findViewById(R.id.textView);
        next_int = findViewById(R.id.next_interval);
=======
        // Hide action bar
        getSupportActionBar().hide();
>>>>>>> ecfa3069ed2c7379a6c6cf913b8d8b488291eb3a

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

<<<<<<< HEAD
=======
        // Populate image list
>>>>>>> ecfa3069ed2c7379a6c6cf913b8d8b488291eb3a
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
        // Add more images as needed

        // Set initial image and string
        loadNextImage();
        fetchNextStringFromFirestore();

        // Set click listener for Next button
        next_int.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex++;
                if (currentIndex >= images.size()) {
<<<<<<< HEAD
                    currentIndex = 0;
=======
                    currentIndex = 0; // Reset index when reaching end of images
>>>>>>> ecfa3069ed2c7379a6c6cf913b8d8b488291eb3a
                }
                loadNextImage();
                fetchNextStringFromFirestore();
            }
        });
    }

    private void fetchNextStringFromFirestore() {
<<<<<<< HEAD
=======
        // Get document from Firestore collection
>>>>>>> ecfa3069ed2c7379a6c6cf913b8d8b488291eb3a
        db.collection("yourCollection").document("yourDocument")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
<<<<<<< HEAD
                        String fieldName = "string" + (currentIndex + 1);
                        if (documentSnapshot.contains(fieldName)) {
                            String fetchedString = documentSnapshot.getString(fieldName);
                            textView.setText(fetchedString);
                        } else {
=======
                        // Construct field name based on currentIndex
                        String fieldName = "string" + (currentIndex + 1);
                        // Check if the field exists in the document
                        if (documentSnapshot.contains(fieldName)) {
                            // Update TextView with fetched string
                            String fetchedString = documentSnapshot.getString(fieldName);
                            textView.setText(fetchedString);
                        } else {
                            // Handle the case when the field does not exist
>>>>>>> ecfa3069ed2c7379a6c6cf913b8d8b488291eb3a
                            Toast.makeText(getApplicationContext(), "String not found in Firestore", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
<<<<<<< HEAD
=======
                        // Handle failures
>>>>>>> ecfa3069ed2c7379a6c6cf913b8d8b488291eb3a
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
