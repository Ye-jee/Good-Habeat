package com.example.goodhabeat_view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MenuSelectActivity extends AppCompatActivity {

    ViewPager viewPager;
    MenuSelect_ViewPagerAdapter menuSelect_viewPagerAdapter;

    TabLayout tabLayout;

    TextView aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_select);

        //타이틀바 없애는 코드
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //특정 탭 이동
        int tab_index = getIntent().getIntExtra("TabIndex", 0);

        viewPager = findViewById(R.id.viewPager);
        menuSelect_viewPagerAdapter = new MenuSelect_ViewPagerAdapter(getSupportFragmentManager(), tab_index);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager.setAdapter(menuSelect_viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.setOffscreenPageLimit(100); // Fragment 전환 > ViewPager 상태 유지

        aa = (TextView) findViewById(R.id.tv_title);
        aa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new
                        Intent(getApplicationContext(), DietAddActivity.class);
                startActivity(intent);
            }
        });

        if(tab_index == 0) {
            viewPager.setCurrentItem(0);
        }
        else if(tab_index == 1) {
            viewPager.setCurrentItem(1);
        }
        else if(tab_index == 2) {
            viewPager.setCurrentItem(2);
        }
        else if(tab_index == 3) {
            viewPager.setCurrentItem(3);
        }
        else if(tab_index == 4) {
            viewPager.setCurrentItem(4);
        }
        else if(tab_index == 5) {
            viewPager.setCurrentItem(5);
        }


    }
}