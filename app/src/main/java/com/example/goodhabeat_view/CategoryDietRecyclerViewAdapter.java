package com.example.goodhabeat_view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoryDietRecyclerViewAdapter extends RecyclerView.Adapter<CategoryDietRecyclerViewAdapter.ViewHolder>{
    ArrayList<CategoryDietData> data;

    public CategoryDietRecyclerViewAdapter(ArrayList<CategoryDietData> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_diet, parent, false);

        /*ImageView menu_img = (ImageView) view.findViewById(R.id.menu_img);
        TextView menu_title = (TextView) view.findViewById(R.id.menu_title);
        TextView menu_food = (TextView) view.findViewById(R.id.menu_food);
        TextView menu_information = (TextView) view.findViewById(R.id.menu_information);
        TextView menu_calories = (TextView) view.findViewById(R.id.menu_calories);*/


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final CategoryDietData item = data.get(position);
        holder.diet_img.setImageResource(item.getDiet_Image());
        holder.diet_title.setText(item.getDiet_Title());
        holder.diet_food.setText(item.getDiet_Food());
        holder.diet_information.setText(item.getDiet_Information());
        holder.diet_calories.setText(item.getDiet_Calories());
        holder.diet_disease.setText(item.getDiet_Disease());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView diet_img;
        private TextView diet_title;
        private TextView diet_food;
        private TextView diet_information;
        private TextView diet_calories;
        private TextView diet_disease;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            diet_img = (ImageView) itemView.findViewById(R.id.menu_pic_day);
            diet_title = (TextView) itemView.findViewById(R.id.diet_title);
            diet_food = (TextView) itemView.findViewById(R.id.diet_food);
            diet_information = (TextView) itemView.findViewById(R.id.diet_information);
            diet_calories = (TextView) itemView.findViewById(R.id.diet_calories);
            diet_disease = (TextView) itemView.findViewById(R.id.diet_disease);

        }
    }
}
