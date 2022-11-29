package com.example.goodhabeat_view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class DietFragment_convenience extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_diet_convenience, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.convenienceDiet_container);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ArrayList<com.example.goodhabeat_view.CategoryDietData> category_menu_data = new ArrayList<>();

        //int menu_img[] = {R.drawable.healthy_diet_tomato_tteokbokki, R.drawable.healthy_diet_konjac_nodles, R.drawable.healthy_diet_mushroom_salad};
        String diet_title[] = {"토마토 떡볶이", "곤약 콩국수", "모듬버섯 샐러드"};
        String diet_food[] = {"토마토 떡볶이", "곤약 콩국수", "모듬버섯 샐러드"};
        //참조한 식단 정보에서는 탄수화물 정보 대신 당 정보를 제공해서, 탄수화물 정보를 그냥 아무 값이나 입력한 것임
        String diet_carbo[] = {"탄수화물 20g", "탄수화물 4g", "탄수화물 6g"};
        String diet_protein[] = {"단백질 8g", "단백질 16g", "단백질 6g"};
        String diet_fat[] = {"지방 1g", "지방 6g", "지방 4g"};
        String diet_calories[] = {"340kcal", "178kcal", "110kcal"};
        String diet_disease[] = {"뇌졸중", "유방암", "당뇨병"};

        for(int i = 0; i<diet_title.length; i++) {
            com.example.goodhabeat_view.CategoryDietData dataSet = new com.example.goodhabeat_view.CategoryDietData(diet_title[i], diet_food[i], diet_carbo[i], diet_protein[i], diet_fat[i], diet_calories[i], diet_disease[i]);
            category_menu_data.add(dataSet);
        }


        recyclerView.setAdapter(new com.example.goodhabeat_view.CategoryDietRecyclerViewAdapter(category_menu_data));

        return view;
    }

}

