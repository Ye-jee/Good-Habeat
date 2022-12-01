package com.example.goodhabeat_view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class DietFragment_lowCalories extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_diet_low_calories, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.lowCaloriesDiet_container);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ArrayList<CategoryDietData> category_diet_data = new ArrayList<>();

        //가짜 데이터 넣을 공간

        //DB 데이터 연결 후에 활성화해야 하는 코드
        /*for(int i = 0; i<diet_title.length; i++) {
            CategoryDietData dataSet = new CategoryDietData(diet_title[i], diet_food[i], diet_carbo[i], diet_protein[i], diet_fat[i], diet_calories[i], diet_disease[i]);
            category_diet_data.add(dataSet);
        }

        //클릭 이벤트를 구현을 위한 추가코드
        CategoryDietRecyclerViewAdapter categoryDietRecyclerViewAdapter = new CategoryDietRecyclerViewAdapter(category_diet_data);
        categoryDietRecyclerViewAdapter.setOnItemClickListener(new CategoryDietRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int postion, String data1, String data2) {
                Toast.makeText(getContext(), data1 + "\n" + data2, Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setAdapter(categoryDietRecyclerViewAdapter);*/



        return view;
    }
}