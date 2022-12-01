package com.example.goodhabeat_view;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class CategoryDietActivity extends AppCompatActivity {

    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;

    TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_diet);

        //타이틀바 없애는 코드
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //특정 탭 이동
        int tab_index = getIntent().getIntExtra("TabIndex", 0);


        viewPager = findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), tab_index);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);


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