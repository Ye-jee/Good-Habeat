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


public class DietFragment_highProtein extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_diet_high_protein, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.highproteinDiet_container);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ArrayList<CategoryDietData> category_diet_data = new ArrayList<>();

        String diet_title[] = {"삼치간장조림 식단", "닭가슴살양배추찜 식단", "차조밥 식단"};
        String diet_food[] = {"수수밥, 근대국, 삼치간장조림, 취나물볶음, 총각김치", "닭가슴살양배추찜, 무국, 멸치깻잎찜, 겨자곤약무침", "차조밥, 콩나물국, 달걀채소말이, 마늘쫑건새우볶음, 감자조림, 배추김치"};
        //참조한 식단 정보에서는 탄수화물 정보 대신 당 정보를 제공해서, 탄수화물 정보를 그냥 아무 값이나 입력한 것임
        String diet_carbo[] = {"탄수화물 20g", "탄수화물 14g", "탄수화물 74g"};
        String diet_protein[] = {"단백질 23g", "단백질 29g", "단백질 29g"};
        String diet_fat[] = {"지방 6g", "지방 14g", "지방 12g"};
        String diet_calories[] = {"619kcal", "540kcal", "645kcal"};
        String diet_disease[] = {"치매 ", "위장병", "골다공증"};

        for(int i = 0; i<diet_title.length; i++) {
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

        recyclerView.setAdapter(categoryDietRecyclerViewAdapter);

        return view;
    }
}