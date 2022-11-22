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
        ArrayList<CategoryDietData> category_menu_data = new ArrayList<>();

        int menu_img[] = {R.drawable.healthy_diet_tomato_tteokbokki, R.drawable.healthy_diet_konjac_nodles, R.drawable.healthy_diet_mushroom_salad};
        String menu_title[] = {"토마토 떡볶이", "곤약 콩국수", "모듬버섯 샐러드"};
        String menu_food[] = {"토마토 떡볶이", "곤약 콩국수", "모듬버섯 샐러드"};
        String menu_information[] = {"단백질 8g 지방 1g 당 76g", "단백질 16g 지방 6g 당 14g", "단백질 6g 지방 4g 당 16g"};
        String menu_calories[] = {"340kcal", "178kcal", "110kcal"};
        String menu_disease[] = {"질병 예방 : 뇌졸중", "질병 예방 : 유방암", "질병 예방 : 당뇨병"};

        for(int i = 0; i< menu_img.length; i++) {
            CategoryDietData dataSet = new CategoryDietData(menu_img[i], menu_title[i], menu_food[i], menu_information[i], menu_calories[i], menu_disease[i]);
            category_menu_data.add(dataSet);
        }


        recyclerView.setAdapter(new CategoryDietRecyclerViewAdapter(category_menu_data));

        return view;
    }

}

