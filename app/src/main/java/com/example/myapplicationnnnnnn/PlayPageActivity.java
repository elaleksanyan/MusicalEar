package com.example.myapplicationnnnnnn;



import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.StorageReference;

public class PlayPageActivity extends AppCompatActivity {


    TextView totalQuestionsTextView;
    TextView questionTextView;
    Button ansAButton;
    Button ansBButton;
    Button ansCButton;
    Button ansDButton;
    Button okButton;
    String selectedAnswer = "";
    int currentQuestionIndex = 0;
    int score = 0;
    int totalQuestions = QuestionAnswers.question.length;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_page);

        FirebaseApp.initializeApp(this);



        totalQuestionsTextView = findViewById(R.id.totalQuestions);
        questionTextView = findViewById(R.id.question);
        ansAButton = findViewById(R.id.ans_a);
        ansBButton = findViewById(R.id.ans_b);
        ansCButton = findViewById(R.id.ans_c);
        ansDButton = findViewById(R.id.ans_d);
        okButton = findViewById(R.id.ok);

        loadNewQuestion();

    }

    public void onClick(View view) {
        ansAButton.setBackgroundColor(Color.WHITE);
        ansBButton.setBackgroundColor(Color.WHITE);
        ansCButton.setBackgroundColor(Color.WHITE);
        ansDButton.setBackgroundColor(Color.WHITE);

        Button button = (Button) view;


            selectedAnswer = button.getText().toString();
            if (selectedAnswer.equals(QuestionAnswers.answers[currentQuestionIndex])) {
                button.setBackgroundColor(Color.GREEN);
                score++;
            }else {
                button.setBackgroundColor(Color.RED);
            };
            ++currentQuestionIndex;
            loadNewQuestion();

    }
    private void loadNewQuestion() {
        if (currentQuestionIndex == totalQuestions) {
            okButton.setEnabled(false);
            FinishQuiz();
            return;
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                questionTextView.setText(QuestionAnswers.question[currentQuestionIndex]);
                ansAButton.setText(QuestionAnswers.choices[currentQuestionIndex][0]);
                ansBButton.setText(QuestionAnswers.choices[currentQuestionIndex][1]);
                ansCButton.setText(QuestionAnswers.choices[currentQuestionIndex][2]);
                ansDButton.setText(QuestionAnswers.choices[currentQuestionIndex][3]);
            }
        }, 2000);
    }
    private void FinishQuiz() {
        String passStatus = "";
        if (score > totalQuestions * 0.6) {
            passStatus = "Passed";
        } else {
            passStatus = "Failed";
        }

        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage("Score is " + score + " out of " + totalQuestions)
                .setPositiveButton("Restart", ((dialogInterface, i) -> restartQuiz()))
                .setCancelable(false)
                .show();
    }
    private void restartQuiz() {
        score = 0;
        currentQuestionIndex = 0;
        selectedAnswer = "";
        okButton.setEnabled(true);
        loadNewQuestion();
    }
}