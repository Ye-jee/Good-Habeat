package com.example.goodhabeat_view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class MenuFragment_rice extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_rice, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.riceMenu_container);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ArrayList<MenuSelectData> menu_select_data = new ArrayList<>();

        int menu_img[] = {R.drawable.menu_select_img_brown_rice, R.drawable.menu_select_img_ogokbap, R.drawable.menu_select_img_susu_rice};
        String menu_name[] = {"현미밥", "오곡밥", "수수밥"};
        String menu_information[] = {"탄수화물 32g 단백질 3g 지방 1g", "탄수화물 32g 단백질 3g 지방 0g", "탄수화물 36g 단백질 3g 지방 1g"};
        String menu_kcal[] = {"153kcal", "147kcal", "168kcal"};

        for(int i=0; i<menu_img.length; i++) {
            MenuSelectData dataSet = new MenuSelectData(menu_img[i], menu_name[i], menu_information[i], menu_kcal[i]);
            menu_select_data.add(dataSet);
        }

        recyclerView.setAdapter(new MenuSelectRecyclerViewAdapter(menu_select_data));

        return view;
    }
}