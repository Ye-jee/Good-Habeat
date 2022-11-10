package com.example.goodhabeat_view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MenuFragment_soup extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_soup, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.soupMenu_container);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ArrayList<com.example.goodhabeat_view.MenuSelectData> menu_select_data = new ArrayList<>();

        int menu_img[] = {R.drawable.menu_select_img_augh_soup, R.drawable.menu_select_img_sausage_stew, R.drawable.menu_select_img_kimchi_stew};
        String menu_name[] = {"아욱된장국", "부대찌개", "김치찌개"};
        String menu_information[] = {"탄수화물 4g 단백질 2g 지방 1g", "탄수화물 10g 단백질 6g 지방 9g", "탄수화물 3g 단백질 3g 지방 3g"};
        String menu_kcal[] = {"34kcal", "156kcal", "60kcal"};

        for(int i=0; i<menu_img.length; i++) {
            com.example.goodhabeat_view.MenuSelectData dataSet = new com.example.goodhabeat_view.MenuSelectData(menu_img[i], menu_name[i], menu_information[i], menu_kcal[i]);
            menu_select_data.add(dataSet);
        }

        recyclerView.setAdapter(new MenuSelectRecyclerViewAdapter(menu_select_data));

        return view;
    }
}