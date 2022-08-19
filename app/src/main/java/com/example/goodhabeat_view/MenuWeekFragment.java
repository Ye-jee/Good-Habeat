package com.example.goodhabeat_view;
// ~ 22.08.19 : MenuActivity.java에 RecyclerView 사용 => 현재 MenuWeekFragment.java 불필요 (사용 안 함)
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.Image;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MenuWeekFragment extends Fragment {
    String url;

    String today_week = "";
    int todayWeek;

    SharedPreferences preferences;

    ////////// 기존 변수 (RecyclerView 이전)
    /*
    TextView menu_bfex, menu_bfname;
    TextView menu_lcex, menu_lcname;
    TextView menu_dnex, menu_dnname;
    TextView bf_eat, lc_eat, dn_eat;
    TextView bf_donteat, lc_donteat, dn_donteat;
    RequestQueue requestQueue;
    ScrollView layout;
    RadioGroup group_week;
    RadioButton week_bf, week_lc, week_dn;

    ImageView bf_pic, lc_pic, dn_pic;

    View dialogView;

     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_menu_week, container, false);

        preferences = getActivity().getSharedPreferences("dayOfWeek", Context.MODE_PRIVATE);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.menu_week_container);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ArrayList<MenuWeekData> menu_week_data = new ArrayList<>();

        // 임시 데이터
        /*
        int[] menu_pic_id = {R.drawable.broccoli, R.drawable.salad, R.drawable.banana};
        String[] menu_name = {"연어 샐러드", "두부 샐러드", "바나나 주스"};
        String[] menu_text = {"연어 샐러드 입니다. 연어를 넣은 샐러드 입니다.",
                              "두부 샐러드 입니다. 두부를 넣은 샐러드 입니다.",
                              "바나나 주스 입니다. 바나나를 넣은 주스 입니다."};

        for(int i=0; i<3; i++) {
            MenuWeekData dataSet = new MenuWeekData(menu_pic_id[i], menu_name[i], menu_text[i]);
            menu_week_data.add(dataSet);
        }
         */


        // 임시 데이터
        if (preferences.equals("monday")) {
            menu_week_data.clear();
            int[] menu_pic_id = {R.drawable.salmon, R.drawable.salad};
            String[] menu_name = {"연어 샐러드", "두부 샐러드"};
            String[] menu_text = {"연어 샐러드 입니다. 연어를 넣은 샐러드 입니다.",
                    "두부 샐러드 입니다. 두부를 넣은 샐러드 입니다."};

            for(int i=0; i<2; i++) {
                MenuWeekData dataSet = new MenuWeekData(menu_pic_id[i], menu_name[i], menu_text[i]);
                menu_week_data.add(dataSet);
            }
        }

        if (preferences.equals("tuesday")) {
            menu_week_data.clear();
            int[] menu_pic_id = {R.drawable.seapasta, R.drawable.tomato, R.drawable.orange};
            String[] menu_name = {"해물 파스타", "토마토 샐러드", "오렌지 주스"};
            String[] menu_text = {"해물 파스타 입니다. 해물을 넣은 파스타 입니다.",
                    "토마토 샐러드 입니다. 토마토를 넣은 샐러드 입니다.",
                    "오렌지 주스 입니다. 오렌지를 넣은 주스 입니다."};

            for(int i=0; i<3; i++) {
                MenuWeekData dataSet = new MenuWeekData(menu_pic_id[i], menu_name[i], menu_text[i]);
                menu_week_data.add(dataSet);
            }
        }

        if (preferences.equals("wednesday")) {
            menu_week_data.clear();
            int[] menu_pic_id = {R.drawable.stake, R.drawable.brocoll, R.drawable.banana};
            String[] menu_name = {"안심 스테이크", "브로콜리 샐러드", "바나나 주스"};
            String[] menu_text = {"안심 스테이크 입니다. 대박 맛있겠다.",
                    "브로콜리 샐러드 입니다. 초장...",
                    "바나나 주스 입니다. 바나나를 넣은 주스 입니다."};

            for(int i=0; i<3; i++) {
                MenuWeekData dataSet = new MenuWeekData(menu_pic_id[i], menu_name[i], menu_text[i]);
                menu_week_data.add(dataSet);
            }
        }

        if (preferences.equals("sunday")) {
            menu_week_data.clear();
            int[] menu_pic_id = {R.drawable.kimchijjigae, R.drawable.eggdolldoll};
            String[] menu_name = {"김치찌개", "계란말이"};
            String[] menu_text = {"김치찌개 입니다. 맛있겠네요",
                    "계란말이 입니다. 밥이랑 같이 드세요."};

            for(int i=0; i<2; i++) {
                MenuWeekData dataSet = new MenuWeekData(menu_pic_id[i], menu_name[i], menu_text[i]);
                menu_week_data.add(dataSet);
            }
        }

        recyclerView.setAdapter(new MenuWeekAdapter(menu_week_data));

        ///////// 기존 코드 (RecyclerView 이전)
        /*
        menu_bfex= (TextView) view.findViewById(R.id.menu_bfex);
        menu_bfname= (TextView) view.findViewById(R.id.menu_bfname);

        menu_lcex= (TextView) view.findViewById(R.id.menu_lcex);
        menu_lcname= (TextView) view.findViewById(R.id.menu_lcname);

        menu_dnex= (TextView) view.findViewById(R.id.menu_dnex);
        menu_dnname= (TextView) view.findViewById(R.id.menu_dnname);

        bf_eat = (TextView) view.findViewById(R.id.bf_eat);
        lc_eat = (TextView) view.findViewById(R.id.lc_eat);
        dn_eat = (TextView) view.findViewById(R.id.dn_eat);

        bf_donteat = (TextView) view.findViewById(R.id.bf_donteat);
        lc_donteat = (TextView) view.findViewById(R.id.lc_donteat);
        dn_donteat = (TextView) view.findViewById(R.id.dn_donteat);

        bf_pic = (ImageView) view.findViewById(R.id.bf_pic);
        lc_pic = (ImageView) view.findViewById(R.id.lc_pic);
        dn_pic = (ImageView) view.findViewById(R.id.dn_pic);

        bf_pic.setImageResource(R.drawable.salmon);
        lc_pic.setImageResource(R.drawable.salad);
        dn_pic.setImageResource(R.drawable.broccoli);

        layout =(ScrollView) view.findViewById(R.id.week_layout);

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

        bf_eat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bf_eat.setTextColor(Color.parseColor("#2E8B57"));
                if (bf_donteat.getCurrentTextColor() == Color.parseColor("#2E8B57")) {
                    bf_donteat.setTextColor(Color.parseColor("#A5A5A5"));
                }
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
                        bf_donteat.setTextColor(Color.parseColor("#2E8B57"));
                        //bf_donteat.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
                        if (bf_eat.getCurrentTextColor() == Color.parseColor("#2E8B57")) {
                            bf_eat.setTextColor(Color.parseColor("#A5A5A5"));
                        }
                    }
                });
                dlg.show();
            }
        });

        lc_eat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lc_eat.setTextColor(Color.parseColor("#2E8B57"));
                if (lc_donteat.getCurrentTextColor() == Color.parseColor("#2E8B57")) {
                    lc_donteat.setTextColor(Color.parseColor("#A5A5A5"));
                }
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
                        lc_donteat.setTextColor(Color.parseColor("#2E8B57"));
                        if (lc_eat.getCurrentTextColor() == Color.parseColor("#2E8B57")) {
                            lc_eat.setTextColor(Color.parseColor("#A5A5A5"));
                        }
                    }
                });
                dlg.show();
            }
        });

        dn_eat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dn_eat.setTextColor(Color.parseColor("#2E8B57"));
                if (dn_donteat.getCurrentTextColor() == Color.parseColor("#2E8B57")) {
                    dn_donteat.setTextColor(Color.parseColor("#A5A5A5"));
                }
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
                        dn_donteat.setTextColor(Color.parseColor("#2E8B57"));
                        if (dn_eat.getCurrentTextColor() == Color.parseColor("#2E8B57")) {
                            dn_eat.setTextColor(Color.parseColor("#A5A5A5"));
                        }
                    }
                });
                dlg.show();
            }
        });

         */

        return view;
    }


}