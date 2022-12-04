package com.example.goodhabeat_view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class MenuSelectActivity extends AppCompatActivity {

    ViewPager viewPager;
    MenuSelect_ViewPagerAdapter menuSelect_viewPagerAdapter;

    TabLayout tabLayout;


    //Button selectCompleteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_select);

        //타이틀바 없애는 코드
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        viewPager = findViewById(R.id.viewPager);
        menuSelect_viewPagerAdapter = new MenuSelect_ViewPagerAdapter(getSupportFragmentManager());

        tabLayout = findViewById(R.id.tabLayout);
        viewPager.setAdapter(menuSelect_viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);


        /*selectCompleteBtn = (Button) findViewById(R.id.select_completeBtn);

        selectCompleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //메뉴 선택을 완료하고 버튼을 클릭해 다시 이전 페이지(식단 설정/추가 페이지)로 이동하는 코드
                finish();
            }
        });*/




    }
}