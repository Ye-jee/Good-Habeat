package com.example.goodhabeat_view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MenuDayActivity_forThesis extends AppCompatActivity {

    //네비게이션 관련 코드
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;

    //네비게이션 드로우어 헤더
    View navHeader;

    SharedPreferences preferences;
    TextView tvUserName;


    RadioGroup group_dayMeal;
    RadioButton today_bf, today_lc, today_dn;

    //SharedPreferences preferences;

    //오늘 날짜
    TextView todayDate;

    //식단 수정, 추가 버튼
    Button diet_modifyBtn;
    Button diet_addBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_day_for_thesis);

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


        int menu_pic_id[] = {R.drawable.menu_select_img_brown_rice/*, R.drawable.menu_select_img_augh_soup, R.drawable.menu_select_img_rolled_omelet*/};
        String menu_name[] = {"현미밥"/*, "아욱된장국", "계란말이"*/};
        String menu_text[] = {"현미밥은 소화에 좋습니다",
                    /*"아욱된장국은 혈액순환에 좋습니다.",
                    "계란말이에는 단백질이 풍부합니다. "*/};

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



        //네비게이션 관련 코드
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);

        //액션바 토글 만들기 (세줄로 표시되어 있는 것)
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        toggle.syncState();

        //네비게이션 뷰
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        //네비게이션 헤더
        navHeader = navigationView.getHeaderView(0);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                if(id == R.id.menu_home) {
                    //Toast.makeText(getApplicationContext(), "메인 화면", Toast.LENGTH_SHORT).show();
                    //메인화면으로 이동하는 코드
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
                else if(id == R.id.menu_myrecord) {
                    //Toast.makeText(getApplicationContext(), "내 기록", Toast.LENGTH_SHORT).show();
                    //내기록으로 이동하는 코드
                    Intent intent = new Intent(getApplicationContext(), MyRecordActivity.class);
                    startActivity(intent);

                }
                /*else if(id == R.id.menu_challenge) {
                    //Toast.makeText(getApplicationContext(), "챌린지", Toast.LENGTH_SHORT).show();

                    //if문으로 챌린지가 설정되어 있지 않은 액티비티/프레그먼트 또는 챌린지가 설정되어 있는 액티비티/프레그먼트로 이동하게 하는 코드 입력 예정
                    //우선 설정되어 있지 않은 화면부터
                    Intent intent = new Intent(getApplicationContext(), ChallengeActivity_Not.class);
                    startActivity(intent);

                }*/
                else if(id == R.id.menu_community) {
                    //Toast.makeText(getApplicationContext(), "커뮤니티", Toast.LENGTH_SHORT).show();

                    //커뮤니티 화면으로 이동하는 페이지 코드 입력 예정
                    //커뮤니티 페이지로 이동하는 코드
                    Intent intent = new Intent(getApplicationContext(), CommunityActivity.class);
                    startActivity(intent);

                }
                else if(id == R.id.menu_setting) {
                    //설정 페이지로 이동하는 코드
                    Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                    startActivity(intent);
                }

                return false;
            }
        });

    }
}