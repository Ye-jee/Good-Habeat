package com.example.goodhabeat_view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dinuscxj.progressbar.CircleProgressBar;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class MyRecordActivity extends AppCompatActivity implements CircleProgressBar.ProgressFormatter {
    RequestQueue requestQueue;
    SharedPreferences preferences;
    String nick_confirm = "0";

    private LineChart lineChart;
    private static final String DEFAULT_PATTERN = "%d%%";

    ImageView bmi_img;


    LinearLayout challenge_record;

    CircleProgressBar circleProgressBar;
    CustomCalendarView customCalendarView;

    //----------------------------
    //출석률
    TextView txt_percent2;
    String detail_date_string, detail_date_Bstring;
    int detail_date_count, detail_date_Bcount;
    int before_date_attend, date_attend;
    //----------------------------

    //BMI -----------------------
    TextView bmi_status, bmi_num, info_height, info_weight, btn_set_info;
    double bmi;
    //---------------------------

    //user custom ---------------
    TextView user_custom_myrecode, test_result_myrecode, replay_custom_myrecode;
    String id_get, nick_get, email_get, birth_get, height_get, weight_get;
    String con_get, hp_get, v_get, lc_get, lsa_get, lsu_get;
    String con_string, hp_string, v_string, lc_string, lsa_string, lsu_string;
    StringBuilder String_sum = new StringBuilder("");
    //----------------------------



    //네비게이션 관련 코드
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;

    //네비게이션 드로우어 헤더
    View navHeader;

    TextView tvUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myrecord);
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        // 캘린더
        customCalendarView = (CustomCalendarView) findViewById(R.id.custom_calendar_view);
        //GridView gridView = (GridView) customCalendarView.findViewById(R.id.gridView);

        /*
        // 챌린지 기록
        challenge_record = (LinearLayout) findViewById(R.id.challenge_record);
        challenge_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChallengeRecordActivity.class);
                startActivity(intent);
            }
        });
         */

        //--------------------------------------------------------------------------------------------------------------------------------------------------------
        //프리퍼런스로 로그인한 닉네임 가져오기
        preferences = getApplicationContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String nickname_get = preferences.getString("nickname", "nickname 오류" );
        System.out.println(nickname_get);


        //--------------------------------------------------------------------------------------------------------------------------------------------------------
        //BMI

        bmi_status=(TextView)findViewById(R.id.bmi_status);
        bmi_num=(TextView)findViewById(R.id.bmi_num);
        info_height=(TextView)findViewById(R.id.info_height);
        info_weight=(TextView)findViewById(R.id.info_weight);
        btn_set_info=(TextView)findViewById(R.id.btn_set_info);


        //닉네임 확인
        String url_nickCheck = "http://10.0.2.2:3000/nick_check";
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url_nickCheck,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            nick_confirm = "ok";

                            System.out.println("응답완료: "+response);
                            JSONArray jsonarray = new JSONArray(response);
                            //JSONArray jArray = mainObj.getJSONArray("");
                            JSONObject setting_json = jsonarray.getJSONObject(0);
                            height_get = setting_json.getString("height");
                            weight_get = setting_json.getString("weight");

                            double Dweight = Double.parseDouble(weight_get);
                            double Dheight = Double.parseDouble(height_get);
                            bmi = (Dweight/(Dheight * Dheight))*10000 ;
                            info_height.setText(height_get);
                            info_weight.setText(weight_get);
                            System.out.println(bmi);
                            double bmi_result = Math.round(bmi * 100) / 100.0;
                            bmi_num.setText(Double.toString(bmi_result));

                            if (bmi_result <= 18.5){
                                bmi_status.setText("저체중으로 ");
                            }
                            if(18.6 <= bmi_result){
                                if(bmi_result<23){
                                    bmi_status.setText("정상으로 ");
                                }
                            }
                            if(23<= bmi_result){
                                if(bmi_result<25){
                                    bmi_status.setText("과체중으로 ");
                                }
                            }
                            if(25<= bmi_result){
                                if(bmi_result<30){
                                    bmi_status.setText("비만으로 ");
                                }
                            }
                            if(35<= bmi_result){
                                bmi_status.setText("과체중으로 ");
                            }

                        }catch (Exception e){ e.printStackTrace(); }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("실패 이유: "+error.getMessage());
                    }
                }
        ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("nick_check", nickname_get);

                return params;
            }
        };
        requestQueue.add(request);


        // 키, 몸무게 다시 하기
        btn_set_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });

        //기존 BMI 코드 (주석처리 함)
        /*
        // bmi
        bmi_img = (ImageView) findViewById(R.id.img_bmi);
        //bmi_img.setColorFilter(Color.argb(30,000,000,000));       //사진 어둡게 함

        bmi_num = (TextView) findViewById(R.id.bmi_num);
        bmi = 21.8;
        if (bmi >= 18 && bmi <= 23 )
            bmi_num.setTextColor(Color.parseColor("#8FBC8B"));
            //bmi_img.setColorFilter(Color.parseColor("#778FBC8B"));

         */




        //--------------------------------------------------------------------------------------------------------------------------------------------------------
        //user custom
        user_custom_myrecode = (TextView)findViewById(R.id.user_custom_myrecode);
        test_result_myrecode = (TextView)findViewById(R.id.test_result_myrecode);
        replay_custom_myrecode = (TextView)findViewById(R.id.replay_custom_myrecode);

        user_custom_myrecode.setText(nickname_get+"님에게 추천하는 식단");

        //추천식단 보여주기 (user_custom)
        String url_custom = "http://10.0.2.2:3000/user_id";
        StringRequest request1 = new StringRequest(
                Request.Method.POST,
                url_custom,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println("응답완료: "+response);
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject custom_json = jsonArray.getJSONObject(0);

                            con_get = custom_json.getString("convenience");
                            if(con_get.equals("1"))
                            {con_string = "간편식 | ";
                                String_sum.append(con_string);
                                System.out.println("1"+String_sum);}


                            hp_get = custom_json.getString("high_protein");
                            if(hp_get.equals("1"))
                            {hp_string = "고단백질 | ";
                                String_sum.append(hp_string);
                                System.out.println("2"+String_sum);}


                            v_get = custom_json.getString("vitamin");
                            if(v_get.equals("1"))
                            {v_string = "비타민/무기질 | ";
                                String_sum.append(v_string);
                                System.out.println("3"+String_sum);}


                            lc_get = custom_json.getString("low_calorie");
                            if(lc_get.equals("1"))
                            {lc_string = "저칼로리 | ";
                                String_sum.append(lc_string);
                                System.out.println("4"+String_sum);}


                            lsa_get = custom_json.getString("low_salt");
                            if(lsa_get.equals("1"))
                            {lsa_string = "저염 | ";
                                String_sum.append(lsa_string);
                                System.out.println("5"+String_sum);}


                            lsu_get = custom_json.getString("low_sugar");
                            if(lsu_get.equals("1"))
                            {lsu_string = "저당 | ";
                                String_sum.append(lsu_string);
                                System.out.println("6"+String_sum);}

                            test_result_myrecode.setText(String_sum.substring(0, String_sum.length()-2));


                        }catch (Exception e){ e.printStackTrace(); }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.getMessage());
                    }
                }
        ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nick_check", nickname_get);

                return params;
            }
        };
        requestQueue.add(request1);


        //식단 설문조사 다시 하기
        replay_custom_myrecode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RecommendedDietSurveyActivity.class);
                startActivity(intent);
            }
        });
        //--------------------------------------------------------------------------------------------------------------------------------------------------------

        // Action Bar ///////////////////////////////////////////////////////////////////
        //drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.d_open, R.string.d_close);
        //toggle.syncState();


        //--------------------------------------------------------------------------------------------------------------------------------------------------------
        // Progress Circle /////////////////////////////////////////////////////////////
        Calendar calendar = Calendar.getInstance();
        Calendar b_calendar = Calendar.getInstance();
        b_calendar.add(Calendar.MONTH , -1);

        int beforeOfMonth = b_calendar.getActualMaximum(calendar.DAY_OF_MONTH);
        System.out.println("저번달 월 일수: "+beforeOfMonth);
        int daysOfMonth = calendar.getActualMaximum(calendar.DAY_OF_MONTH);
        System.out.println("해당 월 일수: "+daysOfMonth);

        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat timeFormat = new SimpleDateFormat("MM");
        String month_num = timeFormat.format(currentTime);
        System.out.println("month num: "+month_num);

        String beforeMonth = new java.text.SimpleDateFormat("MM").format(b_calendar.getTime());


        circleProgressBar=findViewById(R.id.cpb_circlebar);
        txt_percent2=(TextView)findViewById(R.id.txt_percent2);

        //유저 해당 월 출석일 수 가져오기
        String url_myrecord_month = "http://10.0.2.2:3000/myrecord_month";
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        request = new StringRequest(
                Request.Method.POST,
                url_myrecord_month,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println("응답완료: "+response);
                            JSONArray jsonarray = new JSONArray(response);
                            //JSONArray jArray = mainObj.getJSONArray("");
                            JSONObject attend_json  = jsonarray.getJSONObject(0);
                            detail_date_string = attend_json.getString("detail_date_count");
                            detail_date_count = Integer.parseInt(detail_date_string);
                            System.out.println("해당 출석률: "+detail_date_count);

                            circleProgressBar.setMax(daysOfMonth);
                            circleProgressBar.setProgress(detail_date_count);  // 해당 퍼센트를 적용

                            date_attend = (int) Math.round(detail_date_count*(Math.pow(daysOfMonth, -1))*100);
                            System.out.println("date_attend: "+date_attend);



                        }catch (Exception e){ e.printStackTrace(); }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("실패 이유: "+error.getMessage());
                    }
                }
        ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("nickname", nickname_get);
                params.put("system_month", month_num);

                return params;
            }
        };
        requestQueue.add(request);


        //유저 저번달 월 출석일 수 가져오기
        String url_before_month = "http://10.0.2.2:3000/myrecord_Bmonth";
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        request = new StringRequest(
                Request.Method.POST,
                url_before_month,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println("응답완료: "+response);
                            JSONArray jsonarray = new JSONArray(response);
                            //JSONArray jArray = mainObj.getJSONArray("");
                            JSONObject attend_json  = jsonarray.getJSONObject(0);
                            detail_date_Bstring = attend_json.getString("detail_date_Bcount");
                            detail_date_Bcount = Integer.parseInt(detail_date_Bstring);
                            System.out.println("저번달 출석률: "+detail_date_Bcount);
                            System.out.println("before_date_attend: "+before_date_attend);
                            before_date_attend = (int) Math.round(detail_date_Bcount*(Math.pow(beforeOfMonth, -1))*100);;

                            int attend_result = date_attend - before_date_attend;
                            System.out.println("출석률 결과 : "+attend_result);

                            if(attend_result>0){
                                txt_percent2.setText(beforeMonth+"월 보다 " +attend_result+"% 더 잘지켰어요!");
                            } else {
                                txt_percent2.setText(beforeMonth+"월 보다 " +Math.abs(attend_result)+"% 덜 잘지켰어요.");
                            }




                        }catch (Exception e){ e.printStackTrace(); }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("실패 이유: "+error.getMessage());
                    }
                }
        ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("nickname", nickname_get);
                params.put("before_month", beforeMonth);

                return params;
            }
        };
        requestQueue.add(request);


        //--------------------------------------------------------------------------------------------------------------------------------------------------------

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);

        getSupportActionBar().setDisplayShowTitleEnabled(false);    //액션바에 타이틀이 보이지 않게 설정
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //액션바 토글 만들기 (세줄로 표시되어 있는 것)
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        toggle.syncState();

        //네비게이션 뷰
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        //네비게이션 헤더
        navHeader = navigationView.getHeaderView(0);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                if(id == R.id.menu_home) {
                    //Toast.makeText(getApplicationContext(), "메인 화면", Toast.LENGTH_SHORT).show();
                    //메인화면으로 이동하는 코드
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
                else if(id == R.id.menu_myrecord) {
                    //Toast.makeText(getApplicationContext(), "내 기록", Toast.LENGTH_SHORT).show();
                    //내기록으로 이동하는 코드
                    Intent intent = new Intent(getApplicationContext(), MyRecordActivity.class);
                    startActivity(intent);

                }
                /*else if(id == R.id.menu_challenge) {
                    //Toast.makeText(getApplicationContext(), "챌린지", Toast.LENGTH_SHORT).show();

                    //if문으로 챌린지가 설정되어 있지 않은 액티비티/프레그먼트 또는 챌린지가 설정되어 있는 액티비티/프레그먼트로 이동하게 하는 코드 입력 예정
                    //우선 설정되어 있지 않은 화면부터
                    Intent intent = new Intent(getApplicationContext(), ChallengeActivity_Not.class);
                    startActivity(intent);

                }*/
                else if(id == R.id.menu_community) {
                    //Toast.makeText(getApplicationContext(), "커뮤니티", Toast.LENGTH_SHORT).show();

                    //커뮤니티 화면으로 이동하는 페이지 코드 입력 예정
                    //커뮤니티 페이지로 이동하는 코드
                    Intent intent = new Intent(getApplicationContext(), CommunityActivity.class);
                    startActivity(intent);

                }
                else if(id == R.id.menu_setting) {
                    //설정 페이지로 이동하는 코드
                    Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                    startActivity(intent);
                }

                return false;
            }
        });

    }

    // Progress Circle /////////////////////////////////////////////////////////////
    @Override
    public CharSequence format(int progress, int max) {
        return String.format(DEFAULT_PATTERN, (int) ((float) progress / (float) max * 100));
    }
/*
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return false;
        }
        return super.onOptionsItemSelected(item);
    }*/

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(toggle.onOptionsItemSelected(item)) {    //navigation toggle
            //SharedPreference
            preferences = getApplicationContext().getSharedPreferences("userInfo", MODE_PRIVATE);
            String userName = preferences.getString("name","");

            //NavigationDrawer Header 텍스트값 변경
            tvUserName = (TextView) navHeader.findViewById(R.id.tv_username1);
            tvUserName.setText(userName+"님");

            return false;
        }
        return super.onOptionsItemSelected(item);
    }

}


