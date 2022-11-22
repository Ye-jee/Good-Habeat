package com.example.goodhabeat_view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MenuFragment_sideDish extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_side_dish, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.sideDishMenu_container);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ArrayList<MenuSelectData> menu_select_data = new ArrayList<>();

        int menu_img[] = {R.drawable.menu_select_img_rolled_omelet, R.drawable.menu_select_img_chive_pancake, R.drawable.menu_select_img_grilled_mackerel};
        String menu_name[] = {"계란말이", "부추전", "고등어구이"};
        String menu_information[] = {"탄수화물 2g 단백질 10g 지방 8g", "탄수화물 20g 단백질 10g 지방 5g", "탄수화물 0g 단백질 17g 지방 12g"};
        String menu_kcal[] = {"134kcal", "175kcal", "190kcal"};

        for(int i=0; i<menu_img.length; i++) {
            MenuSelectData dataSet = new MenuSelectData(menu_img[i], menu_name[i], menu_information[i], menu_kcal[i]);
            menu_select_data.add(dataSet);
        }

        recyclerView.setAdapter(new MenuSelectRecyclerViewAdapter(menu_select_data));

        return view;
    }
}