package com.example.myapplicationnnnnnn;

import android.media.MediaPlayer;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IntervalsLearn extends AppCompatActivity {

    ImageView onPiano,onNotes;
    TextView textView;
    Button next_int, listen;
    StorageReference storageReference1,storageReference2,storageReference3;
    FirebaseFirestore db;
    List<String> names;
    List<String> images;
    List<String> sheets;
    List<String> audioNames;

    private MediaPlayer mediaPlayer;
    int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learn_intervals);

        getSupportActionBar().hide();

        onPiano = findViewById(R.id.piano);
        onNotes = findViewById(R.id.sheets);
        textView = findViewById(R.id.textView);
        next_int = findViewById(R.id.next_interval);

        // Initialize Firebase instances
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference1 = storage.getReference("Intervals/OnPiano");
        db = FirebaseFirestore.getInstance();

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


        storageReference2 = storage.getReference("Intervals/IntervalsExamples");
        sheets = new ArrayList<>();


        sheets.add("prima.png");
        sheets.add("poqr_sekunda.png");
        sheets.add("mec_sekunda.png");
        sheets.add("poqr_tercia.png");
        sheets.add("mec_tercia.png");
        sheets.add("maqur_kvarta.png");
        sheets.add("maqur_kvinta.png");
        sheets.add("poqr_seksta.png");
        sheets.add("mec_seksta.png");
        sheets.add("poqr_septima.png");
        sheets.add("mec_septima.png");
        sheets.add("oktava.png");

        listen = findViewById(R.id.listen_interval);
        storageReference3 = storage.getReference("Intervals/IntervalsMp3");

        audioNames = new ArrayList<>();

        audioNames.add("prima.mp3");
        audioNames.add("poqr_sekunda.mp3");
        audioNames.add("mec_sekunda.mp3");
        audioNames.add("poqr_tercia.mp3");
        audioNames.add("mec_tercia.mp3");
        audioNames.add("maqur_kvarta.mp3");
        audioNames.add("maqur_kvinta.mp3");
        audioNames.add("poqr_seksta.mp3");
        audioNames.add("mec_seksta.mp3");
        audioNames.add("poqr_septima.mp3");
        audioNames.add("mec_septima.mp3");
        audioNames.add("oktava.mp3");

        // Set initial image and string
        loadNextImage();
        fetchNextStringFromFirestore();

        mediaPlayer = new MediaPlayer();


        listen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playNextAudio();
            }
        });


        next_int.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex++;
                if (currentIndex >= images.size()) {
                    currentIndex = 0;
                }
                loadNextImage();
                fetchNextStringFromFirestore();
            }

        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
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

    private void playNextAudio() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        mediaPlayer = new MediaPlayer();

        StorageReference audioFileRef = storageReference3.child(audioNames.get(currentIndex));
        audioFileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                try {
                    mediaPlayer.setDataSource(getApplicationContext(), uri);
                    mediaPlayer.prepareAsync();
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mediaPlayer.start();
                        }
                    });
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            mediaPlayer.release();
                        }
                    });
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), "Failed to prepare audio", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Failed to download audio", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadNextImage() {
        String imageName = images.get(currentIndex);
        String sheetsView = sheets.get(currentIndex);

        StorageReference imageRef = storageReference1.child(imageName);
        StorageReference sheetsRef = storageReference2.child(sheetsView);

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

        sheetsRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getApplicationContext())
                        .load(uri)
                        .into(onNotes);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Failed to load image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}
