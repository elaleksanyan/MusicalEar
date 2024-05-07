package com.example.myapplicationnnnnnn;// LeaderboardPageActivity.java
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.ArrayList;
import com.example.myapplicationnnnnnn.LeaderboardAdapter;
import com.example.myapplicationnnnnnn.R; // Make sure to import R

public class LeaderboardPageActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LeaderboardAdapter adapter;
    private ArrayList<String> userEmails = new ArrayList<>();
    private ArrayList<Integer> correctScores = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard_activity);

        recyclerView = findViewById(R.id.leaderboardRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userEmail = currentUser.getEmail();
            userEmails.add(userEmail);
        }

        correctScores.add(100);
        correctScores.add(90);

        adapter = new LeaderboardAdapter(userEmails, correctScores);
        recyclerView.setAdapter(adapter);
    }
}