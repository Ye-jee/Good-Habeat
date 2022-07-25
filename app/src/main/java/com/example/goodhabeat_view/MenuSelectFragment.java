package com.example.goodhabeat_view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MenuSelectFragment extends Fragment {

    Button menu_plus;
    View menuplusView;
    LinearLayout menu1_layout, menu2_layout, menu3_layout;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_menu_select, container, false);

        menu_plus = (Button) view.findViewById(R.id.menu_plus);
        menu1_layout = (LinearLayout) view.findViewById(R.id.menu1_layout);
        menu2_layout = (LinearLayout) view.findViewById(R.id.menu2_layout);
        menu3_layout = (LinearLayout) view.findViewById(R.id.menu3_layout);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar2);

        menu1_layout.setVisibility(View.INVISIBLE);
        menu2_layout.setVisibility(View.INVISIBLE);
        menu3_layout.setVisibility(View.INVISIBLE);


        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        //menuselectFragment = new MenuSelectFragment();
        SelectDianlogFragment selectDianlogFragment = new SelectDianlogFragment();


        menu_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectDianlogFragment.show(fragmentManager,"food");

                new Handler().postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        menu1_layout.setVisibility(View.VISIBLE);
                        progressBar.setProgress(60);
                    }
                }, 1000);// 0.6초 정도 딜레이를 준 후 시작

            }
        });

        return view;
    }

}