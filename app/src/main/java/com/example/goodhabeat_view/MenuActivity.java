package com.example.goodhabeat_view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MenuActivity extends AppCompatActivity {
    //FragmentManager fragmentManager;
    //MenuWeekFragment menuweekFragment;

    Button mon_week, tue_week, wed_week, thr_week, fri_week, sun_week, sat_week;
    Button week_menu_btn;

    RadioGroup group_week;
    RadioButton week_bf, week_lc, week_dn;

    SharedPreferences preferences;

    String today_week = "";
    int todayWeek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getSupportActionBar().hide();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.menu_container);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<MenuWeekData> menu_week_data = new ArrayList<>();

        //fragmentManager = getSupportFragmentManager();
        //menuweekFragment = new MenuWeekFragment();

        mon_week = (Button) findViewById(R.id.mon_week);
        tue_week = (Button) findViewById(R.id.tue_week);
        wed_week = (Button) findViewById(R.id.wed_week);
        thr_week = (Button) findViewById(R.id.thr_week);
        fri_week = (Button) findViewById(R.id.fri_week);
        sat_week = (Button) findViewById(R.id.sat_week);
        sun_week = (Button) findViewById(R.id.sun_week);

        group_week = (RadioGroup) findViewById(R.id.group_week);
        week_bf = (RadioButton) findViewById(R.id.week_bf);
        week_lc = (RadioButton) findViewById(R.id.week_lc);
        week_dn = (RadioButton) findViewById(R.id.week_dn);

        week_menu_btn = (Button) findViewById(R.id.week_menu_btn); // 식단 수정 버튼

        /*
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.menu_container, menuweekFragment);
        ft.commit();
         */

        /*
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.menu_container,  menuweekFragment, null);
        fragmentTransaction.commit();
         */

        // 오늘의 요일 구하기 (임시 데이터용 코드 -> 나중에 MySQL로 DB 연결)
        // 이번 주 식단 페이지 처음 들어갔을 때, 요일 버튼 클릭 전 '오늘'에 해당하는 식단을 먼저 보여줄 수 없을까 해서 일단 넣어봄
        // Fragment를 사용할지, Activity에 직접 RecyclerView를 넣을지에 따라 필요없을 수도 있음
        long now_time = System.currentTimeMillis(); // 현재 시간
        Date date = new Date(now_time); // Date 형식으로 Convert(변환)

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        todayWeek = cal.get(Calendar.DAY_OF_WEEK);

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
        }

        if (today_week.equals("monday") || today_week.equals("tuesday") || today_week.equals("wednesday") || today_week.equals("sunday")) {
            int[] menu_pic_id = {R.drawable.berry_yogurt, R.drawable.tomato, R.drawable.orange};
            String[] menu_name = {"딸기 요거트", "토마토 샐러드", "오렌지 주스"};
            String[] menu_text = {"딸기 요거트 입니다. 딸기를 넣은 요거트 입니다.",
                    "토마토 샐러드 입니다. 토마토를 넣은 샐러드 입니다.",
                    "오렌지 주스 입니다. 오렌지로 만든 주스 입니다."};

            for (int i = 0; i < 3; i++) {
                MenuWeekData dataSet = new MenuWeekData(menu_pic_id[i], menu_name[i], menu_text[i]);
                menu_week_data.add(dataSet);
            }

        }

        // 요일 정보 -> SharedPreferences 저장 (Fragment 사용 시 필요, 현재는 Fragment 사용 안 함)
        // 22.08.19 : 요일 버튼과 시간대 버튼을 눌러도 변화 없음. 다시 코드 작성해야 할 거 같음. DB, 서버 연결이 될 때 다시 작성해 시도할 예정.
        mon_week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*
                preferences = getApplicationContext().getSharedPreferences("weeklyMenu", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("dayOfWeek", "monday");
                editor.commit();
*/
                if (week_bf.isChecked()) {
                    menu_week_data.clear();
                    int[] menu_pic_id = {R.drawable.salmon, R.drawable.salad};
                    String[] menu_name = {"월요일 아침", "두부 샐러드"};
                    String[] menu_text = {"연어 샐러드 입니다. 연어를 넣은 샐러드 입니다.",
                            "두부 샐러드 입니다. 두부를 넣은 샐러드 입니다."};

                    for(int i=0; i<2; i++) {
                        MenuWeekData dataSet = new MenuWeekData(menu_pic_id[i], menu_name[i], menu_text[i]);
                        menu_week_data.add(dataSet);
                    }
                } else if (week_lc.isChecked()) {
                    menu_week_data.clear();
                    int[] menu_pic_id = {R.drawable.salmon, R.drawable.salad};
                    String[] menu_name = {"월요일 점심", "두부 샐러드"};
                    String[] menu_text = {"연어 샐러드 입니다. 연어를 넣은 샐러드 입니다.",
                            "두부 샐러드 입니다. 두부를 넣은 샐러드 입니다."};

                    for(int i=0; i<2; i++) {
                        MenuWeekData dataSet = new MenuWeekData(menu_pic_id[i], menu_name[i], menu_text[i]);
                        menu_week_data.add(dataSet);
                    }
                } else if (week_dn.isChecked()) {
                    menu_week_data.clear();
                    int[] menu_pic_id = {R.drawable.salmon, R.drawable.salad};
                    String[] menu_name = {"월요일 아침", "두부 샐러드"};
                    String[] menu_text = {"연어 샐러드 입니다. 연어를 넣은 샐러드 입니다.",
                            "두부 샐러드 입니다. 두부를 넣은 샐러드 입니다."};

                    for(int i=0; i<2; i++) {
                        MenuWeekData dataSet = new MenuWeekData(menu_pic_id[i], menu_name[i], menu_text[i]);
                        menu_week_data.add(dataSet);
                    }
                }

/*
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.addToBackStack(null);
                ft.replace(R.id.menu_container, menuweekFragment);
                ft.commit();
 */
            }
        });

        tue_week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferences = getApplicationContext().getSharedPreferences("weeklyMenu", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("dayOfWeek", "tuesday");
                editor.commit();

                menu_week_data.clear();
                int[] menu_pic_id = {R.drawable.seapasta, R.drawable.tomato, R.drawable.orange};
                String[] menu_name = {"해물 파스타", "토마토 샐러드", "오렌지 주스"};
                String[] menu_text = {"해물 파스타 입니다. 해물을 넣은 파스타 입니다.",
                        "토마토 샐러드 입니다. 토마토를 넣은 샐러드 입니다.",
                        "오렌지 주스 입니다. 오렌지를 넣은 주스 입니다."};

                for(int i=0; i<3; i++) {
                    MenuWeekData dataSet = new MenuWeekData(menu_pic_id[i], menu_name[i], menu_text[i]);
                    menu_week_data.add(dataSet);
                }
            }
        });

        wed_week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferences = getApplicationContext().getSharedPreferences("weeklyMenu", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("dayOfWeek", "wednesday");
                editor.commit();

                menu_week_data.clear();
                int[] menu_pic_id = {R.drawable.stake, R.drawable.brocoll, R.drawable.banana};
                String[] menu_name = {"안심 스테이크", "브로콜리 샐러드", "바나나 주스"};
                String[] menu_text = {"안심 스테이크 입니다. 대박 맛있겠다.",
                        "브로콜리 샐러드 입니다. 초장...",
                        "바나나 주스 입니다. 바나나를 넣은 주스 입니다."};

                for(int i=0; i<3; i++) {
                    MenuWeekData dataSet = new MenuWeekData(menu_pic_id[i], menu_name[i], menu_text[i]);
                    menu_week_data.add(dataSet);
                }
            }
        });

        recyclerView.setAdapter(new MenuWeekAdapter(menu_week_data));

        // 식단 수정 페이지로 이동
        week_menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MenuSelectActivity.class);
                startActivity(intent);
            }
        });

    }
}