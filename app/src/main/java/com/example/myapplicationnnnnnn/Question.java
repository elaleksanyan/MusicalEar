package com.example.myapplicationnnnnnn;

import com.google.firebase.firestore.PropertyName;

import java.util.List;

public class Question {
    @PropertyName("answers")
    private List<String> answers;

    @PropertyName("audioUrl")
    private String audioUrl;

    @PropertyName("correctAnswerIndex")
    private int correctAnswerIndex;

    public Question() {
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    public void setCorrectAnswerIndex(int correctAnswerIndex) {
        this.correctAnswerIndex = correctAnswerIndex;
    }
}
