package com.example.myapplicationnnnnnn;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class QuestionAnswers extends AppCompatActivity {

    private ArrayList<String> audioNames;

//    public static String question[] = {
//            "what note does an octave start on?",
//            "How much is 2 + 2 ?",
//            "How much is 3 + 3 ?"
//    };
//
//    public static String choices[][] = {
//            { "C", "E", "F", "D" },
//            { "1", "2", "3", "4" },
//            { "1", "2", "3", "6" }
//    };
//
//        public static String answers[] = {
//                "C",
//                "4",
//                "6"
//        };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


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
    }


}

