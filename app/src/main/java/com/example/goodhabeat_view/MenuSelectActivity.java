package com.example.goodhabeat_view;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class MenuSelectActivity extends AppCompatActivity {

    ViewPager viewPager;
    com.example.goodhabeat_view.MenuSelect_ViewPagerAdapter menuSelect_viewPagerAdapter;

    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_select);

        //타이틀바 없애는 코드
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        viewPager = findViewById(R.id.viewPager);
        menuSelect_viewPagerAdapter = new com.example.goodhabeat_view.MenuSelect_ViewPagerAdapter(getSupportFragmentManager());

        tabLayout = findViewById(R.id.tabLayout);
        viewPager.setAdapter(menuSelect_viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);





    }
}