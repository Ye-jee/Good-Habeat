package com.example.goodhabeat_view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class DietFragment_highProtein extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_diet_high_protein, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.highproteinDiet_container);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ArrayList<CategoryDietData> category_menu_data = new ArrayList<>();

        int menu_img[] = {R.drawable.healthy_diet_ex01};
        String menu_title[] = {"삼치간장조림 식단"};
        String menu_food[] = {"수수밥, 근대국, 삼치간장조림, 취나물볶음, 총각김치"};
        String menu_information[] = {"단백질 23g 지방 6g 당 120g"};
        String menu_calories[] = {"619kcal"};
        String menu_disease[] = {"질병 예방 : 치매 "};

        for(int i = 0; i<menu_img.length; i++) {
            CategoryDietData dataSet = new CategoryDietData(menu_img[i], menu_title[i], menu_food[i], menu_information[i], menu_calories[i], menu_disease[i]);
            category_menu_data.add(dataSet);
        }

        recyclerView.setAdapter(new CategoryDietRecyclerViewAdapter(category_menu_data));

        return view;
    }
}