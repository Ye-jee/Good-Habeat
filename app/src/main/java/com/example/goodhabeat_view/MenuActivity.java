package com.example.goodhabeat_view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {
    FragmentManager fragmentManager;
    MenuWeekFragment menuweekFragment;
    Button mon_week, tue_week, wed_week, thr_week, fri_week, sun_week, sat_week;
    Button week_menu_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getSupportActionBar().hide();

        mon_week = (Button) findViewById(R.id.mon_week);
        tue_week = (Button) findViewById(R.id.tue_week);
        wed_week = (Button) findViewById(R.id.wed_week);
        thr_week = (Button) findViewById(R.id.thr_week);
        fri_week = (Button) findViewById(R.id.fri_week);
        sat_week = (Button) findViewById(R.id.sat_week);
        sun_week = (Button) findViewById(R.id.sun_week);

        week_menu_btn = (Button) findViewById(R.id.week_menu_btn);


        fragmentManager = getSupportFragmentManager();
        menuweekFragment = new MenuWeekFragment();
        /*
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.menu_container,  menuweekFragment, null);
        fragmentTransaction.commit();

         */

        mon_week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.addToBackStack(null);
                ft.replace(R.id.menu_container, menuweekFragment);
                ft.commit();
            }
        });

        tue_week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.addToBackStack(null);
                ft.replace(R.id.menu_container, menuweekFragment);
                ft.commit();
            }
        });

        wed_week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.addToBackStack(null);
                ft.replace(R.id.menu_container, menuweekFragment);
                ft.commit();
            }
        });
        week_menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MenuSelectActivity.class);
                startActivity(intent);
            }
        });
    }
}