package com.example.goodhabeat_view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class CategoryDietActivity extends AppCompatActivity {

    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;

    TabLayout tabLayout;


    //화면 터치시 레시피 페이지로 이동 관련 코드
    LinearLayout viewPagerOfLayout;


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
        viewPagerAdapter = new com.example.goodhabeat_view.ViewPagerAdapter(getSupportFragmentManager(), tab_index);

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



        //탭 아래 화면 터치시 레시피 페이지로 이동 관련 코드
        viewPagerOfLayout = (LinearLayout) findViewById(R.id.viewPager_ofLayout);
        viewPagerOfLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //새로 만든 레시피 페이지로 이동하게 하는 코드
                Intent intent = new Intent(getApplicationContext(), RecipeActivity.class);
                startActivity(intent);
           }
        });

    }
}