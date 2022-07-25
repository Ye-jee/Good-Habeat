package com.example.goodhabeat_view;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.media.Image;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;


public class MenuWeekFragment extends Fragment {
    String url;
    TextView menu_bfex, menu_bfname;
    TextView menu_lcex, menu_lcname;
    TextView menu_dnex, menu_dnname;
    TextView bf_donteat, lc_donteat, dn_donteat;
    RequestQueue requestQueue;
    ConstraintLayout layout;
    RadioGroup group_week;
    RadioButton week_bf, week_lc, week_dn;

    ImageView bf_pic, lc_pic, dn_pic;

    View dialogView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_menu_week, container, false);

        menu_bfex= (TextView) view.findViewById(R.id.menu_bfex);
        menu_bfname= (TextView) view.findViewById(R.id.menu_bfname);

        menu_lcex= (TextView) view.findViewById(R.id.menu_lcex);
        menu_lcname= (TextView) view.findViewById(R.id.menu_lcname);

        menu_dnex= (TextView) view.findViewById(R.id.menu_dnex);
        menu_dnname= (TextView) view.findViewById(R.id.menu_dnname);

        bf_donteat = (TextView) view.findViewById(R.id.bf_donteat);
        lc_donteat = (TextView) view.findViewById(R.id.lc_donteat);
        dn_donteat = (TextView) view.findViewById(R.id.dn_donteat);

        bf_pic = (ImageView) view.findViewById(R.id.bf_pic);
        lc_pic = (ImageView) view.findViewById(R.id.lc_pic);
        dn_pic = (ImageView) view.findViewById(R.id.dn_pic);

        bf_pic.setImageResource(R.drawable.salmon);
        lc_pic.setImageResource(R.drawable.salad);
        dn_pic.setImageResource(R.drawable.broccoli);

        layout =(ConstraintLayout) view.findViewById(R.id.week_layout);

        group_week = (RadioGroup) view.findViewById(R.id.group_week);
        week_bf = (RadioButton) view.findViewById(R.id.week_bf);
        week_lc = (RadioButton) view.findViewById(R.id.week_lc);
        week_dn = (RadioButton) view.findViewById(R.id.week_dn);

        layout.setVisibility(View.INVISIBLE);

        week_bf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.setVisibility(View.VISIBLE);
            }
        });
        week_lc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.setVisibility(View.VISIBLE);
                bf_pic.setImageResource(R.drawable.seapasta);
                menu_bfname.setText("해물 파스타");
                menu_bfex.setText("해물이 가득 들어간 해물 파스타");
                lc_pic.setImageResource(R.drawable.tomato);
                menu_lcname.setText("토마토 샐러드");
                menu_lcex.setText("건강에 좋은 토마토를 넣어 먹어보세요");
                dn_pic.setImageResource(R.drawable.banana);
                menu_dnname.setText("바나나 쥬스");
                menu_dnex.setText("달콤한 바나나 쥬스");
            }

        });
        week_dn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.setVisibility(View.VISIBLE);
                bf_pic.setImageResource(R.drawable.stake);
                menu_bfname.setText("스테이크");
                menu_bfex.setText("육즙이 흐르는 스테이크");
                lc_pic.setImageResource(R.drawable.brocoll);
                menu_lcname.setText("브로콜리 샐러드");
                menu_lcex.setText("맛있는 브로콜리, 샐러드로 먹어도 맛있습니다");
                dn_pic.setImageResource(R.drawable.orange);
                menu_dnname.setText("오렌지 쥬스");
                menu_dnex.setText("상큼한 오렌지 쥬스");


            }
        });


        bf_donteat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogView = (View) View.inflate(getContext(), R.layout.dont_eat, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(getContext());
              //  dlg.setTitle("CHECK IN");
                dlg.setView(dialogView);
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        bf_donteat.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
                    }
                });
                dlg.show();
            }
        });

        lc_donteat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogView = (View) View.inflate(getContext(), R.layout.dont_eat, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(getContext());
                //dlg.setTitle("CHECK IN");
                dlg.setView(dialogView);
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        lc_donteat.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
                    }
                });
                dlg.show();
            }
        });


        dn_donteat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogView = (View) View.inflate(getContext(), R.layout.dont_eat, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(getContext());
               // dlg.setTitle("CHECK IN");
                dlg.setView(dialogView);
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dn_donteat.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
                    }
                });
                dlg.show();
            }
        });


        return view;
    }
}