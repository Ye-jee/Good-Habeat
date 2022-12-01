package com.example.goodhabeat_view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import android.os.Handler;

public class DietChangeActivity extends AppCompatActivity {

    //음식추가 버튼
    Button menuPlus_change;

    //수정완료 버튼
    Button change_completeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_change);

        getSupportActionBar().hide();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.dietAddList_container);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<DietChangeListData> diet_change_list_data = new ArrayList<>();

        /*String dietChange_menu_name[] = {"옥수수밥", "조기구이", "된장찌개"};
        String dietChange_menu_kcal[] = {"300kcal", "318kcal", "145kcal"};*/

        String dietChange_menu_name[] = {"현미밥"/*, "아욱된장국", "계란말이"*/};
        String dietChange_menu_kcal[] = {"153kcal"/*, "34kcal", "134kcal"*/};

        for(int i = 0; i< dietChange_menu_name.length; i++){
            DietChangeListData dataSet = new DietChangeListData(dietChange_menu_name[i], dietChange_menu_kcal[i]);
            diet_change_list_data.add(dataSet);
        }

        //recyclerView.setAdapter(new DietChangeListRecyclerViewAdapter(diet_change_list_data));
        //5초 뒤에 나타나게 함
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerView.setAdapter(new DietChangeListRecyclerViewAdapter(diet_change_list_data));
            }
        }, 5000); //딜레이 타임 조절


        //음식 추가 버튼 관련
        menuPlus_change = (Button) findViewById(R.id.menu_plus_change);

        menuPlus_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MenuSelectActivity.class);
                startActivity(intent);
            }
        });


        //(식단) 수정 완료 버튼 관련 - 메뉴가 바뀌게 된 오늘의 식단 페이지로 이동하게 함!!
        change_completeBtn = (Button) findViewById(R.id.menu_change_complete);
        change_completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MenuDayActivity_forThesis.class);
                startActivity(intent);
            }
        });

    }
}