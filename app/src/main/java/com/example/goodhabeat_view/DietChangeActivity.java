package com.example.goodhabeat_view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DietChangeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_change);

        getSupportActionBar().hide();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.dietChangeList_container);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<DietChangeListData> diet_change_list_data = new ArrayList<>();

        String dietChange_menu_name[] = {"옥수수밥", "조기구이", "된장찌개"};
        String dietChange_menu_kcal[] = {"300kcal", "318kcal", "145kcal"};

        for(int i = 0; i< dietChange_menu_name.length; i++){
            DietChangeListData dataSet = new DietChangeListData(dietChange_menu_name[i], dietChange_menu_kcal[i]);
            diet_change_list_data.add(dataSet);
        }

        recyclerView.setAdapter(new DietChangeListRecyclerViewAdapter(diet_change_list_data));

    }
}