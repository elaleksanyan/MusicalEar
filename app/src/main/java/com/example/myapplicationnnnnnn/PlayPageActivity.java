package com.example.myapplicationnnnnnn;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;




import java.io.IOException;
import java.util.ArrayList;

public class PlayPageActivity extends AppCompatActivity {
    private Button option1Button;
    private Button option2Button;
    private Button option3Button;
    private Button option4Button;
    private Button playAudioButton,ok;

    private MediaPlayer mediaPlayer;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private ArrayList<String> audioNames;
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_page);

        option1Button = findViewById(R.id.ans_a);
        option2Button = findViewById(R.id.ans_b);
        option3Button = findViewById(R.id.ans_c);
        option4Button = findViewById(R.id.ans_d);
        playAudioButton = findViewById(R.id.hearing);
        ok = findViewById(R.id.ok);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference("Intervals/IntervalsMp3");

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

        playAudioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playNextAudio();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex++;
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        MediaPlayer mediaPlayer = new MediaPlayer();
                        try {
                            mediaPlayer.setDataSource(getApplicationContext(), uri);
                            mediaPlayer.prepare();
                            mediaPlayer.start();
                            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mediaPlayer) {
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getApplicationContext(), "Failed to download audio: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }

        });




    }


    private void playNextAudio() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        mediaPlayer = new MediaPlayer();

        StorageReference audioFileRef = storageReference.child(audioNames.get(currentIndex));
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
}
