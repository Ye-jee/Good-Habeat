package com.example.goodhabeat_view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class MenuSelectActivity extends AppCompatActivity {
    FragmentManager fragmentManager;
    MenuSelectFragment menuselectFragment;

    RadioButton mon_select, tue_select, wed_select, thr_select, fri_select, sun_select, sat_select;

    Button select_menu_select;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_select);
        getSupportActionBar().hide();

        mon_select = (RadioButton) findViewById(R.id.mon_select);
        tue_select = (RadioButton) findViewById(R.id.tue_select);
        wed_select = (RadioButton) findViewById(R.id.wed_select);
        thr_select = (RadioButton) findViewById(R.id.thr_select);
        fri_select = (RadioButton) findViewById(R.id.fri_select);
        sat_select = (RadioButton) findViewById(R.id.sat_select);
        sun_select = (RadioButton) findViewById(R.id.sun_select);

        select_menu_select = (Button) findViewById(R.id.select_menu_select);

        fragmentManager = getSupportFragmentManager();
        menuselectFragment = new MenuSelectFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.select_container,  menuselectFragment, null);
        fragmentTransaction.commit();

        mon_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.addToBackStack(null);
                ft.replace(R.id.select_container, menuselectFragment);
                ft.commit();
            }
        });
        tue_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.addToBackStack(null);
                ft.replace(R.id.select_container, menuselectFragment);
                ft.commit();
            }
        });

        wed_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.addToBackStack(null);
                ft.replace(R.id.select_container, menuselectFragment);
                ft.commit();
            }
        });

        select_menu_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });


    }
}