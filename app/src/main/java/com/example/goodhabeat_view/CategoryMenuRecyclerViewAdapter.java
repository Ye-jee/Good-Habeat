package com.example.goodhabeat_view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoryMenuRecyclerViewAdapter extends RecyclerView.Adapter<CategoryMenuRecyclerViewAdapter.ViewHolder>{
    ArrayList<CategoryMenuData> data;

    public CategoryMenuRecyclerViewAdapter(ArrayList<CategoryMenuData> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_menu, parent, false);

        /*ImageView menu_img = (ImageView) view.findViewById(R.id.menu_img);
        TextView menu_title = (TextView) view.findViewById(R.id.menu_title);
        TextView menu_food = (TextView) view.findViewById(R.id.menu_food);
        TextView menu_information = (TextView) view.findViewById(R.id.menu_information);
        TextView menu_calories = (TextView) view.findViewById(R.id.menu_calories);*/


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final CategoryMenuData item = data.get(position);
        holder.menu_img.setImageResource(item.getMenu_Image());
        holder.menu_title.setText(item.getMenu_Title());
        holder.menu_food.setText(item.getMenu_Food());
        holder.menu_information.setText(item.getMenu_Information());
        holder.menu_calories.setText(item.getMenu_Calories());
        holder.menu_disease.setText(item.getMenu_Disease());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView menu_img;
        private TextView menu_title;
        private TextView menu_food;
        private TextView menu_information;
        private TextView menu_calories;
        private TextView menu_disease;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            menu_img = (ImageView) itemView.findViewById(R.id.menu_img);
            menu_title = (TextView) itemView.findViewById(R.id.menu_title);
            menu_food = (TextView) itemView.findViewById(R.id.menu_food);
            menu_information = (TextView) itemView.findViewById(R.id.menu_information);
            menu_calories = (TextView) itemView.findViewById(R.id.menu_calories);
            menu_disease = (TextView) itemView.findViewById(R.id.menu_disease);

        }
    }
}
