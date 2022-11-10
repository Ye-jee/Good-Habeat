package com.example.goodhabeat_view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class MenuFragment_kimchi extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu_kimchi, container, false);

        //가짜 데이터 부분만 변경해서 쓰면 됨!

        /*RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.riceMenu_container);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ArrayList<MenuSeletData> menu_select_data = new ArrayList<>();

        int menu_img[] = {};
        String menu_name[] = {};
        String menu_information[] = {};
        String menu_kcal[] = {};

        for(int i=0; i<menu_img.length; i++) {
            MenuSeletData dataSet = new MenuSeletData(menu_img[i],menu_name[i], menu_information[i], menu_kcal[i]);
            menu_select_data.add(dataSet);
        }

        recyclerView.setAdapter(new MenuSelectRecyclerViewAdapter(menu_select_data));*/


    }
}