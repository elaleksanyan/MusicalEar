package com.example.myapplicationnnnnnn;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class PlayPageActivity extends AppCompatActivity {
    private Button option1Button;
    private Button option2Button;
    private Button option3Button;
    private Button option4Button;
    private Button playAudioButton;
    private Button okButton;
    private MediaPlayer mediaPlayer;
    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int correctScores = 0;
    private int incorrectAnswers = 0;
    private int selectedAnswerIndex = -1;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    int initialScore;

    LinearLayout linearLayout;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_page);

        option1Button = findViewById(R.id.ans_a);
        option2Button = findViewById(R.id.ans_b);
        option3Button = findViewById(R.id.ans_c);
        option4Button = findViewById(R.id.ans_d);
        playAudioButton = findViewById(R.id.hearing);
        okButton = findViewById(R.id.ok);
        progressBar = findViewById(R.id.progressBar3);
        linearLayout = findViewById(R.id.linearLayout);

        questions = new ArrayList<>();

        loadQuestions();

        playAudioButton.setOnClickListener(v -> playAudio());


        okButton.setOnClickListener(v -> {
            if (selectedAnswerIndex != -1) {
                checkAnswer();
                loadNextQuestion();
            } else {
                Toast.makeText(getApplicationContext(), "Please select an answer", Toast.LENGTH_SHORT).show();
            }
        });

        option1Button.setOnClickListener(v -> setAnswerSelected(0));
        option2Button.setOnClickListener(v -> setAnswerSelected(1));
        option3Button.setOnClickListener(v -> setAnswerSelected(2));
        option4Button.setOnClickListener(v -> setAnswerSelected(3));

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser != null) {
            initialScore = correctScores * 10 - incorrectAnswers * 5;
        }

    }

    private void setAnswerSelected(int selectedAnswerIndex) {
        this.selectedAnswerIndex = selectedAnswerIndex;
    }

    private void loadQuestions() {
        progressBar.setVisibility(View.VISIBLE);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("questions")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    questions.clear();
                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        Question question = documentSnapshot.toObject(Question.class);
                        questions.add(question);
                    }

                    Collections.shuffle(questions);
                    loadQuestionOptions();
                })
                .addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "Failed to load questions", Toast.LENGTH_SHORT).show());
    }

    private void playAudio() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }

        if (currentQuestionIndex >= questions.size()) {
            Toast.makeText(getApplicationContext(), "All questions played", Toast.LENGTH_SHORT).show();
            return;
        }

        String audioUrl = questions.get(currentQuestionIndex).getAudioUrl();

        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(audioUrl);
            mediaPlayer.prepare();
            mediaPlayer.start();

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Failed to play audio", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadQuestionOptions() {
        if (currentQuestionIndex == 0) {
            Question currentQuestion = questions.get(currentQuestionIndex);
            option1Button.setText(currentQuestion.getAnswers().get(0));
            option2Button.setText(currentQuestion.getAnswers().get(1));
            option3Button.setText(currentQuestion.getAnswers().get(2));
            option4Button.setText(currentQuestion.getAnswers().get(3));
            progressBar.setVisibility(View.GONE);
        } else {
            new Handler().postDelayed(() -> {
                Question currentQuestion = questions.get(currentQuestionIndex);

                option1Button.setText(currentQuestion.getAnswers().get(0));
                option2Button.setText(currentQuestion.getAnswers().get(1));
                option3Button.setText(currentQuestion.getAnswers().get(2));
                option4Button.setText(currentQuestion.getAnswers().get(3));

                progressBar.setVisibility(View.GONE);
            }, 1000); // Change the delay time here if needed
        }
    }




//    private void checkAnswer() {
//        int correctAnswerIndex = questions.get(currentQuestionIndex).getCorrectAnswerIndex();
//
//        if (selectedAnswerIndex == correctAnswerIndex) {
//            correctAnswers++;
//        } else {
//            incorrectAnswers++;
//        }
//
//        selectedAnswerIndex = -1;
//    }

    private void checkAnswer() {
        int correctAnswerIndex = questions.get(currentQuestionIndex).getCorrectAnswerIndex();

        switch (selectedAnswerIndex) {
            case 0:
                if (selectedAnswerIndex == correctAnswerIndex) {
                    option1Button.setBackgroundColor(Color.GREEN);
                } else {
                    option1Button.setBackgroundColor(Color.RED);
                    switch (correctAnswerIndex) {
                        case 0:
                            option1Button.setBackgroundColor(Color.GREEN);
                            break;
                        case 1:
                            option2Button.setBackgroundColor(Color.GREEN);
                            break;
                        case 2:
                            option3Button.setBackgroundColor(Color.GREEN);
                            break;
                        case 3:
                            option4Button.setBackgroundColor(Color.GREEN);
                            break;
                    }
                }
                break;
            case 1:
                if (selectedAnswerIndex == correctAnswerIndex) {
                    option2Button.setBackgroundColor(Color.GREEN);
                } else {
                    option2Button.setBackgroundColor(Color.RED);
                    switch (correctAnswerIndex) {
                        case 0:
                            option1Button.setBackgroundColor(Color.GREEN);
                            break;
                        case 1:
                            option2Button.setBackgroundColor(Color.GREEN);
                            break;
                        case 2:
                            option3Button.setBackgroundColor(Color.GREEN);
                            break;
                        case 3:
                            option4Button.setBackgroundColor(Color.GREEN);
                            break;
                    }
                }
                break;
            case 2:
                if (selectedAnswerIndex == correctAnswerIndex) {
                    option3Button.setBackgroundColor(Color.GREEN);
                } else {
                    option3Button.setBackgroundColor(Color.RED);
                    switch (correctAnswerIndex) {
                        case 0:
                            option1Button.setBackgroundColor(Color.GREEN);
                            break;
                        case 1:
                            option2Button.setBackgroundColor(Color.GREEN);
                            break;
                        case 2:
                            option3Button.setBackgroundColor(Color.GREEN);
                            break;
                        case 3:
                            option4Button.setBackgroundColor(Color.GREEN);
                            break;
                    }
                }
                break;
            case 3:
                if (selectedAnswerIndex == correctAnswerIndex) {
                    option4Button.setBackgroundColor(Color.GREEN);
                } else {
                    option4Button.setBackgroundColor(Color.RED);
                    switch (correctAnswerIndex) {
                        case 0:
                            option1Button.setBackgroundColor(Color.GREEN);
                            break;
                        case 1:
                            option2Button.setBackgroundColor(Color.GREEN);
                            break;
                        case 2:
                            option3Button.setBackgroundColor(Color.GREEN);
                            break;
                        case 3:
                            option4Button.setBackgroundColor(Color.GREEN);
                            break;
                    }
                }
                break;
        }

        new Handler().postDelayed(() -> {
            option1Button.setBackgroundColor(Color.WHITE);
            option2Button.setBackgroundColor(Color.WHITE);
            option3Button.setBackgroundColor(Color.WHITE);
            option4Button.setBackgroundColor(Color.WHITE);
        }, 1000);

        if (selectedAnswerIndex == correctAnswerIndex) {
            correctScores++;
        } else {
            incorrectAnswers++;
        }

        selectedAnswerIndex = -1;
    }


    private void loadNextQuestion() {
        if (currentQuestionIndex < questions.size()) {
            currentQuestionIndex++;
            if (currentQuestionIndex < questions.size()) {
                loadQuestionOptions();
            } else {
                showQuizResult();
                okButton.setEnabled(false);
            }
        }
    }

    private void showQuizResult() {
        StringBuilder resultMessage = new StringBuilder();
        resultMessage.append("Correct: ").append(correctScores).append("\n");
        resultMessage.append("Incorrect: ").append(incorrectAnswers);
        uploadToLeadership(correctScores);
        Toast.makeText(getApplicationContext(), resultMessage.toString(), Toast.LENGTH_LONG).show();

        // Spasum a 5 vayrkyan u verjacnum activityn
        // Kpoxes ete petq a
        new Handler().postDelayed(this::finish, 4000);
    }

    private void uploadToLeadership(int correctScore) {
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String currentUserEmail = mAuth.getCurrentUser().getEmail();

            System.out.println(currentUserEmail);
            db.collection("LeaderBoard")
                    .whereEqualTo("playerEmail", currentUserEmail)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            if (!task.getResult().isEmpty()) {
                                DocumentSnapshot document = task.getResult().getDocuments().get(0);
                                LeaderboardItem leaderboardItem = document.toObject(LeaderboardItem.class);
                                leaderboardItem.setScore(leaderboardItem.getScore() + correctScore);
                                document.getReference()
                                        .set(leaderboardItem)
                                        .addOnSuccessListener(aVoid -> Log.d("Leader Board", "LeaderboardItem updated successfully!"))
                                        .addOnFailureListener(e -> Log.e("Leader Board", "Error updating leaderboardItem", e));

                            } else {
                                LeaderboardItem newLeaderboardItem = new LeaderboardItem();
                                newLeaderboardItem.setPlayerEmail(currentUserEmail);
                                newLeaderboardItem.setScore(correctScore);
                                db.collection("LeaderBoard")
                                        .add(newLeaderboardItem)
                                        .addOnSuccessListener(documentReference -> Log.d("Leader Board", "New leaderboardItem added with ID: " + documentReference.getId()))
                                        .addOnFailureListener(e -> Log.e("Leader Board", "Error adding document", e));
                            }
                        } else {
                            Log.e("Leader Board", "Error getting documents: ", task.getException());
                        }
                    });
        }
    }
}