package com.example.goodhabeat_view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CommunityRecyclerViewAdapter extends RecyclerView.Adapter<CommunityRecyclerViewAdapter.ViewHolder>{

    ArrayList<com.example.goodhabeat_view.CommunityData> data;

    public CommunityRecyclerViewAdapter(ArrayList<com.example.goodhabeat_view.CommunityData> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_community, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final com.example.goodhabeat_view.CommunityData item = data.get(position);
        holder.user_pic.setImageResource(item.getCommunity_user_pic());
        holder.user_nikName.setText(item.getCommunity_user_nikName());
        holder.create_date.setText(item.getCommunity_create_date());
        holder.create_time.setText(item.getCommunity_create_time());
        holder.content_text.setText(item.getCommunity_content_text());
        holder.content_img.setImageResource(item.getCommunity_content_img());
        holder.heart_img.setImageResource(item.getCommunity_heart_img());
        holder.heart_number.setText(item.getCommunity_heart_number());
        holder.delete_text.setText(item.getCommunity_delete_text());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView user_pic;
        private TextView user_nikName;
        private TextView create_date;
        private TextView create_time;
        private TextView content_text;
        private ImageView content_img;
        private ImageView heart_img;
        private TextView heart_number;
        private TextView delete_text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            user_pic = (ImageView) itemView.findViewById(R.id.commu_user_pic);
            user_nikName = (TextView) itemView.findViewById(R.id.commu_user_nicName);
            create_date = (TextView) itemView.findViewById(R.id.commu_create_date);
            create_time = (TextView) itemView.findViewById(R.id.commu_create_time);
            content_text = (TextView) itemView.findViewById(R.id.commu_content_text);
            content_img = (ImageView) itemView.findViewById(R.id.commu_content_img);
            heart_img = (ImageView) itemView.findViewById(R.id.commu_heart_img);
            heart_number = (TextView) itemView.findViewById(R.id.commu_heart_number);
            delete_text = (TextView) itemView.findViewById(R.id.commu_delete_text);
        }
    }
}
