package com.example.myapplicationnnnnnn;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.ArrayList;
import java.util.List;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class LeaderboardPageActivity extends AppCompatActivity {
    FirebaseFirestore db;
    FirebaseUser currentUser;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    private LeaderboardAdapter adapter;
    private List<LeaderboardItem> leaderBoardItems;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard_activity);

        initGlobalFields();
    }

    private void initGlobalFields() {
        db = FirebaseFirestore.getInstance();
        leaderBoardItems = new ArrayList<>();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        recyclerView = findViewById(R.id.leaderboardRecyclerView);
        progressBar = findViewById(R.id.progressBar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LeaderboardAdapter(leaderBoardItems);
        recyclerView.setAdapter(adapter);

        fetchLeaderboardItems();
    }

    private void fetchLeaderboardItems() {
        progressBar.setVisibility(View.VISIBLE);
        db.collection("LeaderBoard")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            LeaderboardItem leaderboardItem = document.toObject(LeaderboardItem.class);
                            leaderBoardItems.add(leaderboardItem);
                            adapter.notifyItemInserted(leaderBoardItems.size());
                        }
                        progressBar.setVisibility(View.GONE);
                    } else {
                        Log.e("Leader Board", "Error getting documents: ", task.getException());
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

}