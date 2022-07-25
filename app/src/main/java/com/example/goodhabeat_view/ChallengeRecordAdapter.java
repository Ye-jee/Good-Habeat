package com.example.goodhabeat_view;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ChallengeRecordAdapter extends RecyclerView.Adapter<ChallengeRecordAdapter.ViewHolder> {
    ArrayList<ChallengeRecordData> data;

    public ChallengeRecordAdapter(ArrayList<ChallengeRecordData> data) { this.data = data; }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_challenge_record, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getPast_challenge_name().setText(data.get(position).getChallenge_name());
        holder.getPast_challenge_period().setText(data.get(position).getChallenge_period());
        holder.getPast_challenge_percent().setText(data.get(position).getProgress()+"\u0025");
        holder.getPast_challenge_progress().setProgress(data.get(position).getProgress());
        if(data.get(position).getProgress() == 100)
            holder.getIvTrophy().setVisibility(View.VISIBLE);
        else
            holder.getIvTrophy().setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView past_challenge_name;
        private TextView past_challenge_period;
        private TextView past_challenge_percent;
        private ImageView ivTrophy;
        private ProgressBar past_challenge_progress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            past_challenge_name = (TextView) itemView.findViewById(R.id.past_challenge_name);
            past_challenge_period = (TextView) itemView.findViewById(R.id.past_challenge_period);
            past_challenge_percent = (TextView) itemView.findViewById(R.id.past_challenge_percent);
            past_challenge_progress = (ProgressBar) itemView.findViewById(R.id.past_challenge_progress);
            ivTrophy = (ImageView) itemView.findViewById(R.id.ivTrophy);
        }

        public TextView getPast_challenge_name() { return past_challenge_name; }
        public TextView getPast_challenge_period() { return past_challenge_period; }
        public TextView getPast_challenge_percent() { return past_challenge_percent; }
        public ImageView getIvTrophy() { return ivTrophy; }
        public ProgressBar getPast_challenge_progress() { return past_challenge_progress; }


    }

}
