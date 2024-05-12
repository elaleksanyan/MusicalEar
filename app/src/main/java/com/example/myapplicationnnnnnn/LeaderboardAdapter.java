// LeaderboardAdapter.java
package com.example.myapplicationnnnnnn;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.ViewHolder> {
    List<LeaderboardItem> leaderboardItems;

    public LeaderboardAdapter(List<LeaderboardItem> leaderboardItems) {
        this.leaderboardItems = leaderboardItems;

        leaderboardItems.sort((item1, item2) ->  Integer.compare(item2.getScore(), item1.getScore()));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.leaderboard_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LeaderboardItem currentItem = leaderboardItems.get(position);

        holder.userEmailTextView.setText(currentItem.getPlayerEmail());
        holder.correctScoreTextView.setText(String.valueOf(currentItem.getScore()));
    }

    @Override
    public int getItemCount() {
        return leaderboardItems.size();
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