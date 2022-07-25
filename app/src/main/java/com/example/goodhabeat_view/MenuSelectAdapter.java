package com.example.goodhabeat_view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MenuSelectAdapter extends RecyclerView.Adapter<MenuSelectAdapter.ViewHolder>{
    Context c;
    String [] foodnames;
    String [] foodkcals;

    public MenuSelectAdapter(Context c, String[] foodnames, String[] foodkcals) {
        this.c = c;
        this.foodnames = foodnames;
        this.foodkcals = foodkcals;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_plus_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.plus_fname.setText(foodnames[position]);
        holder.plus_kcal.setText(foodkcals[position]+"kcal");

    }

    @Override
    public int getItemCount() {
        return foodnames.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView plus_fname;
        private TextView plus_kcal;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            plus_fname = (TextView) itemView.findViewById(R.id.plus_fname);
            plus_kcal = (TextView) itemView.findViewById(R.id.plus_kcal);
        }

        public TextView getPlus_fname() {
            return plus_fname;
        }

        public TextView getPlus_kcal() {
            return plus_kcal;
        }
    }
}
