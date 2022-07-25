package com.example.goodhabeat_view;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class ChallengeActivity_Not extends AppCompatActivity {

    ImageView img_setChallenge;
    ConstraintLayout cl_recordChallenge;
    TextView tv_setChallenge;

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
        setContentView(R.layout.activity_challenge_not);

        img_setChallenge = (ImageView) findViewById(R.id.challenge_illust);
        cl_recordChallenge = (ConstraintLayout) findViewById(R.id.recordChallenge_container);
        tv_setChallenge = (TextView) findViewById(R.id.tv_setChallenge);

        //챌린지 설정하기 부분을 클릭하면 챌린지를 시작하는 페이지로 이동
        tv_setChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChallengeStartActivity.class);
                startActivity(intent);
            }
        });

        img_setChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChallengeStartActivity.class);
                startActivity(intent);
            }
        });

        cl_recordChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChallengeRecordActivity.class);
                startActivity(intent);
            }
        });

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