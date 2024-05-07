// LeaderboardAdapter.java
package com.example.myapplicationnnnnnn;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.ViewHolder> {
    private ArrayList<String> userEmails;
    private ArrayList<Integer> correctScores;

    public LeaderboardAdapter(ArrayList<String> userEmails, ArrayList<Integer> correctScores) {
        this.userEmails = userEmails;
        this.correctScores = correctScores;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.leaderboard_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.userEmailTextView.setText(userEmails.get(position));
        holder.correctScoreTextView.setText(String.valueOf(correctScores.get(position)));
    }

    @Override
    public int getItemCount() {
        return userEmails.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView userEmailTextView;
        TextView correctScoreTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userEmailTextView = itemView.findViewById(R.id.playerNameTextView);
            correctScoreTextView = itemView.findViewById(R.id.scoreTextView);
        }
    }
}