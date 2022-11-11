package com.example.goodhabeat_view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MenuDayActivity extends AppCompatActivity {

    RadioGroup group_dayMeal;
    RadioButton today_bf, today_lc, today_dn;

    SharedPreferences preferences;

    //오늘 날짜
    TextView todayDate;

    //식단 수정, 추가 버튼
    Button diet_modifyBtn;
    Button diet_addBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_day);

        getSupportActionBar().hide();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.menuDay_container);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        ArrayList<MenuDayData> menu_day_data = new ArrayList<>();


        todayDate = (TextView) findViewById(R.id.todayDate);

        group_dayMeal = (RadioGroup) findViewById(R.id.group_dayMeal);
        today_bf = (RadioButton) findViewById(R.id.someday_breakfast);
        today_lc = (RadioButton) findViewById(R.id.someday_lunch);
        today_dn = (RadioButton) findViewById(R.id.someday_dinner);

        diet_modifyBtn = (Button) findViewById(R.id.diet_modifyBtn);    // 식단 수정 버튼
        diet_addBtn = (Button) findViewById(R.id.diet_addBtn);          //식단 추가 버튼

        // 오늘의 요일 구하기 (임시 데이터용 코드 -> 나중에 MySQL로 DB 연결)
        // 이번 주 식단 페이지 처음 들어갔을 때, 요일 버튼 클릭 전 '오늘'에 해당하는 식단을 먼저 보여줄 수 없을까 해서 일단 넣어봄
        // Fragment를 사용할지, Activity에 직접 RecyclerView를 넣을지에 따라 필요없을 수도 있음
        /*long now_time = System.currentTimeMillis(); // 현재 시간
        Date date = new Date(now_time); // Date 형식으로 Convert(변환)

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);*/

        /*todayWeek = cal.get(Calendar.DAY_OF_WEEK);

        switch (todayWeek) {
            case 1 :
                today_week = "sunday";
                break;
            case 2 :
                today_week = "monday";
                break;
            case 3 :
                today_week = "tuesday";
                break;
            case 4 :
                today_week = "wednesday";
                break;
            case 5 :
                today_week = "monday";
                break;
            case 6 :
                today_week = "tuesday";
                break;
            case 7 :
                today_week = "wednesday";
                break;
        }*/


        int menu_pic_id[] = {R.drawable.berry_yogurt, R.drawable.tomato, R.drawable.orange};
        String menu_name[] = {"딸기 요거트", "토마토 샐러드", "오렌지 주스"};
        String menu_text[] = {"딸기 요거트 입니다. 딸기를 넣은 요거트 입니다.",
                    "토마토 샐러드 입니다. 토마토를 넣은 샐러드 입니다.",
                    "오렌지 주스 입니다. 오렌지로 만든 주스 입니다."};

        for (int i = 0; i <menu_pic_id.length; i++) {
            MenuDayData dataSet = new MenuDayData(menu_pic_id[i], menu_name[i], menu_text[i]);
            menu_day_data.add(dataSet);
        }

        recyclerView.setAdapter(new MenuDayRecyclerViewAdapter(menu_day_data));



        // 식단 수정 페이지로 이동
        diet_modifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DietChangeActivity.class);
                startActivity(intent);
            }
        });

        //식단 추가 페이지로 이동
        diet_addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DietAddActivity.class);
                startActivity(intent);
            }
        });

    }
}