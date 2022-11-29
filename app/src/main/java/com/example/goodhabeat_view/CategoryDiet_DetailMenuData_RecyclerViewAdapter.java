package com.example.goodhabeat_view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoryDiet_DetailMenuData_RecyclerViewAdapter extends RecyclerView.Adapter<CategoryDiet_DetailMenuData_RecyclerViewAdapter.ViewHolder>{

    ArrayList<CategoryDiet_DetailMenuData> data;

    public CategoryDiet_DetailMenuData_RecyclerViewAdapter(ArrayList<CategoryDiet_DetailMenuData> data) {
        this.data = data;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_diet_details_menu, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final CategoryDiet_DetailMenuData item = data.get(position);
        holder.menuDetail_img.setImageResource(item.getMenu_detail_img());
        holder.menuDetail_title.setText(item.getMenu_detail_Title());
        holder.menuDetail_information.setText(item.getMenu_detail_Information());
        holder.menuDetail_calories.setText(item.getMenu_detail_Calories());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView menuDetail_img;
        private TextView menuDetail_title;
        private TextView menuDetail_information;
        private TextView menuDetail_calories;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            menuDetail_img = (ImageView) itemView.findViewById(R.id.menu_detail_img);
            menuDetail_title = (TextView) itemView.findViewById(R.id.menu_detail_title);
            menuDetail_information = (TextView) itemView.findViewById(R.id.menu_detail_information);
            menuDetail_calories = (TextView) itemView.findViewById(R.id.menu_detail_calories);

        }
    }


}
