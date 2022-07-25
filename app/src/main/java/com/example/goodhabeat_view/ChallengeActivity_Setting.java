package com.example.goodhabeat_view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

public class ChallengeActivity_Setting extends AppCompatActivity {

    TextView tv_challengeName, tv_challengePeriod;

    RequestQueue requestQueue;

    //네비게이션 관련 코드
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;

    //네비게이션 드로우어 헤더
    View navHeader;

    SharedPreferences preferences;
    TextView tvUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_setting);

        tv_challengeName = (TextView) findViewById(R.id.tv_challengeName);
        tv_challengePeriod = (TextView) findViewById(R.id.tv_challengePeriod);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        String url = "http://192.168.56.1:8000/AndroidAppEx/capstoneEx/challengeMainEx1.jsp";

        /*StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject mainObj = new JSONObject(response);
                            JSONArray jsonArray = mainObj.getJSONArray("challenges");

                            for(int i=0; i<2;i++) {
                                JSONObject challenge = jsonArray.getJSONObject(i);

                                String challenge_Name = challenge.getString("challenge_name");
                                String challenge_Period = challenge.getString("challenge_start_date") + " ~ " + challenge.getString("challenge_end_date");

                                tv_challengeName.setText(challenge_Name);
                                tv_challengePeriod.setText(challenge_Period);
                            }

                        }catch (Exception e) {

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "error : " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(stringRequest);*/


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
                else if(id == R.id.menu_challenge) {
                    //Toast.makeText(getApplicationContext(), "챌린지", Toast.LENGTH_SHORT).show();

                    //if문으로 챌린지가 설정되어 있지 않은 액티비티/프레그먼트 또는 챌린지가 설정되어 있는 액티비티/프레그먼트로 이동하게 하는 코드 입력 예정
                    //우선 설정되어 있지 않은 화면부터
                    Intent intent = new Intent(getApplicationContext(), ChallengeActivity_Not.class);
                    startActivity(intent);

                }
                /*else if(id == R.id.menu_community) {
                    Toast.makeText(getApplicationContext(), "커뮤니티", Toast.LENGTH_SHORT).show();

                    //커뮤니티 화면으로 이동하는 페이지 코드 입력 예정
                }*/

                return false;
            }
        });
    }

    //onCreate 메소드 밖에
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