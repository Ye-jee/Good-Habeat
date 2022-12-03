package com.example.goodhabeat_view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CategoryDietRecyclerViewAdapter extends RecyclerView.Adapter<CategoryDietRecyclerViewAdapter.ViewHolder>{
    Context itemContext;
    ArrayList<com.example.goodhabeat_view.CategoryDietData> data;

    public CategoryDietRecyclerViewAdapter(Context itemContext, ArrayList<com.example.goodhabeat_view.CategoryDietData> data) {
        this.itemContext = itemContext;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_diet, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final com.example.goodhabeat_view.CategoryDietData item = data.get(position);

        holder.diet_title.setText(item.getDiet_Title());
        holder.diet_food.setText(item.getDiet_Food());
        holder.diet_carbo.setText(item.getDiet_Carbo());
        holder.diet_protein.setText(item.getDiet_Protein());
        holder.diet_fat.setText(item.getDiet_Fat());
        holder.diet_calories.setText(item.getDiet_Calories());
        holder.diet_disease.setText(item.getDiet_Disease());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView diet_title;
        private TextView diet_food;
        private TextView diet_carbo;
        private TextView diet_protein;
        private TextView diet_fat;
        private TextView diet_calories;
        private TextView diet_disease;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            diet_title = (TextView) itemView.findViewById(R.id.diet_title);
            diet_food = (TextView) itemView.findViewById(R.id.diet_food);
            diet_carbo = (TextView) itemView.findViewById(R.id.diet_carbo);
            diet_protein = (TextView) itemView.findViewById(R.id.diet_protein);
            diet_fat = (TextView) itemView.findViewById(R.id.diet_fat);
            diet_calories = (TextView) itemView.findViewById(R.id.diet_calories);
            diet_disease = (TextView) itemView.findViewById(R.id.diet_disease);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    Intent intent = new Intent(itemView.getContext(), CategoryDiet_DetailsMenuActivity.class);
                    intent.putExtra("diet_title", data.get(position).getDiet_Title());
                    String diet_info = data.get(position).getDiet_Carbo()
                            + " | " + data.get(position).getDiet_Protein()
                            + " | " + data.get(position).getDiet_Fat();
                    intent.putExtra("diet_info", diet_info);
                    intent.putExtra("diet_calorie", data.get(position).getDiet_Calories());
                    intent.putExtra("diet_category", data.get(position).getDiet_category()); // Volley에 사용
                    intent.putExtra("diet_bind_id", data.get(position).getDiet_bind_id()); // Volley에 사용
                    itemContext.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                }
            });
        }
    }
}
