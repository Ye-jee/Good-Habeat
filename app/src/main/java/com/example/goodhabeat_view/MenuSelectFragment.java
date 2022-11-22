package com.example.goodhabeat_view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MenuSelectFragment extends Fragment {

    Button menu_plus;
    View menuplusView;
    LinearLayout menu1_layout, menu2_layout, menu3_layout;
    TextView calrories_sum, carbohydrtes_sum, protein_sum, fat_sum, calrories_warning;
    ImageView warning;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_menu_select, container, false);

        menu_plus = (Button) view.findViewById(R.id.menu_plus_change);
        menu1_layout = (LinearLayout) view.findViewById(R.id.menu1_layout);
        menu2_layout = (LinearLayout) view.findViewById(R.id.menu2_layout);
        menu3_layout = (LinearLayout) view.findViewById(R.id.menu3_layout);

        calrories_sum = (TextView) view.findViewById(R.id.calrories_sum);
        carbohydrtes_sum = (TextView) view.findViewById(R.id.carbohydrtes_sum );
        protein_sum = (TextView) view.findViewById(R.id.protein_sum);
        fat_sum = (TextView) view.findViewById(R.id.fat_sum);
        calrories_warning = (TextView) view.findViewById(R.id.calories_warning);

        warning = (ImageView) view.findViewById(R.id.warning);


        menu1_layout.setVisibility(View.INVISIBLE);
        menu2_layout.setVisibility(View.INVISIBLE);
        menu3_layout.setVisibility(View.INVISIBLE);

        calrories_warning.setVisibility(View.INVISIBLE);
        warning.setVisibility(View.INVISIBLE);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        //menuselectFragment = new MenuSelectFragment();
        SelectDianlogFragment selectDianlogFragment = new SelectDianlogFragment();

        //칼로리 경고문
       // Integer calrories =  Integer.parseInt(String.valueOf(calrories_sum.getText()));
        Integer calrories = 1000;
        if(calrories >= 2000){
            calrories_warning.setVisibility(View.VISIBLE);
            warning.setVisibility(View.VISIBLE);
        }

        //선택 메뉴
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
                        Integer calrories = 2500;
                        if(calrories >= 2000){
                            calrories_warning.setVisibility(View.VISIBLE);
                            warning.setVisibility(View.VISIBLE);
                        }

                        calrories_sum.setText("1234"+"kcal");
                        carbohydrtes_sum.setText("123g");
                        protein_sum.setText("234g");
                        fat_sum.setText("456g");

                    }
                }, 1000);// 0.6초 정도 딜레이를 준 후 시작

            }
        });

        //영양정보



        return view;
    }

}