package com.example.goodhabeat_view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoryDietRecyclerViewAdapter extends RecyclerView.Adapter<CategoryDietRecyclerViewAdapter.ViewHolder>{
    ArrayList<com.example.goodhabeat_view.CategoryDietData> data;

    public CategoryDietRecyclerViewAdapter(ArrayList<com.example.goodhabeat_view.CategoryDietData> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_diet, parent, false);

        CategoryDietRecyclerViewAdapter.ViewHolder categoryDiet_viewHolder = new CategoryDietRecyclerViewAdapter.ViewHolder(view);

        //클릭 이벤트 구현을 위해 추가된 코드
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data1 = "";
                String data2 = "";
                int position = categoryDiet_viewHolder.getAdapterPosition();

                if(position != RecyclerView.NO_POSITION) {  //순서가 없지 않을 때 즉 그냥 아이템을 클릭할 시에
                    data1 = categoryDiet_viewHolder.getDiet_title().getText().toString();
                    data2 = categoryDiet_viewHolder.getDiet_food().getText().toString();
                }
                itemClickListener.onItemClicked(position, data1, data2);
            }
        });

        return categoryDiet_viewHolder;
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



    //클릭 이벤트 구현을 위해 추가된 코드
    //OnItemClickListener 인터페이스 선언
    public interface OnItemClickListener {
        void onItemClicked(int postion, String data1, String data2);
    }

    //OnItemClickListener 참조 변수 선언
    private OnItemClickListener itemClickListener;

    //OnItemClickListener 전달 메소드
    public void setOnItemClickListener (OnItemClickListener listener) {
        itemClickListener = listener;
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

        }

        public TextView getDiet_title() {
            return diet_title;
        }

        public TextView getDiet_food() {
            return diet_food;
        }
    }
}
