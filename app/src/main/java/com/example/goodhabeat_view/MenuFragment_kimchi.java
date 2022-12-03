package com.example.goodhabeat_view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MenuFragment_kimchi extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_kimchi, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.kimchiMenu_container);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ArrayList<MenuSelectData> menu_select_data = new ArrayList<>();

        //가짜 데이터 넣을 공간

        //DB 데이터 연결 후에 활성화해야 하는 코드
        /*for(int i=0; i<menu_img.length; i++) {
            MenuSelectData dataSet = new MenuSelectData(menu_img[i], menu_name[i], menu_information[i], menu_kcal[i]);
            menu_select_data.add(dataSet);
        }

        //클릭 이벤트를 구현을 위한 추가코드
        MenuSelectRecyclerViewAdapter menuSelectRecyclerViewAdapter = new MenuSelectRecyclerViewAdapter(menu_select_data);
        menuSelectRecyclerViewAdapter.setOnItemClickListener(new MenuSelectRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int postion, String data) {
                Toast.makeText(getContext(), data, Toast.LENGTH_SHORT).show();

                //아이템 클릭시 식단 수정 페이지로 이동하는 코드
                Intent intent = new Intent(getActivity(), DietChangeActivity.class);
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(menuSelectRecyclerViewAdapter);*/



        return view;


    }
}