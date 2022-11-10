package com.example.goodhabeat_view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MenuSelectRecyclerViewAdapter extends RecyclerView.Adapter<MenuSelectRecyclerViewAdapter.ViewHolder>{

    ArrayList<com.example.goodhabeat_view.MenuSelectData> data;

    public MenuSelectRecyclerViewAdapter(ArrayList<com.example.goodhabeat_view.MenuSelectData> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu_select, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final com.example.goodhabeat_view.MenuSelectData item = data.get(position);
        holder.menu_img.setImageResource(item.getMenu_img());
        holder.menu_name.setText(item.getMenu_name());
        holder.menu_information.setText(item.getMenu_information());
        holder.menu_kcal.setText(item.getMenu_kcal());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView menu_img;
        private TextView menu_name;
        private TextView menu_information;
        private TextView menu_kcal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            menu_img = (ImageView) itemView.findViewById(R.id.menu_img);
            menu_name = (TextView) itemView.findViewById(R.id.menu_name);
            menu_information = (TextView) itemView.findViewById(R.id.menu_information);
            menu_kcal = (TextView) itemView.findViewById(R.id.menu_calories);
        }
    }
}
