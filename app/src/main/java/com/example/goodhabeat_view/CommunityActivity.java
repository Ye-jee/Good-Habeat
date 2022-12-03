package com.example.goodhabeat_view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class CommunityActivity extends AppCompatActivity {

    //네비게이션 관련 코드
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;

    //네비게이션 드로우어 헤더
    View navHeader;

    ImageButton writing_imgBtn;

    SharedPreferences preferences;
    TextView tvUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        writing_imgBtn = (ImageButton)findViewById(R.id.writing_imgBtn);

        writing_imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CommunityWritingActivity.class);
                startActivity(intent);
            }
        });

        //--------------------------------------------------------------------------------------------------------------------------------------------------------
        //프리퍼런스로 로그인한 닉네임 가져오기
        preferences = getApplicationContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String nickname_get = preferences.getString("nickname", "nickname 오류" );
        System.out.println(nickname_get);
        //--------------------------------------------------------------------------------------------------------------------------------------------------------








        //타이틀바 없애는 코드
        getSupportActionBar().hide();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.community_list_container);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<com.example.goodhabeat_view.CommunityData> community_data = new ArrayList<>();

        //삭제버튼은 내 게시글에만 보여야 됨!!! - 리사이클러뷰에서는 어떻게 해야 되지?

        int user_pic[] = {R.drawable.community_person01, R.drawable.community_person01, R.drawable.community_person01};
        String user_nikName[] = {"user_name1", "로그인 사용자", "user_name2"};
        String create_date[] = {"2022.04.20", "2022.08.28", "2022.10.16"};
        String create_time[] = {"09:22", "10:11", "14:20"};
        String content_text[] = {"일어나자마자 아침으로 샐러드를 만들어 먹었어요! 뿌듯하다!",
                                    "오늘 아침으로 두부 샐러드를 먹었습니다 -ㅁ-",
                                    "샐러드도 치킨이 들어가니 먹을만 하네요...!"};
        int content_img[] = {R.drawable.community_img_salad, R.drawable.community_img_salad2, R.drawable.community_img_salad3};
        int heart_img[] = {R.drawable.community_heart_fil, R.drawable.community_heart_empty, R.drawable.community_heart_empty};
        String heart_number[] = {"152", "47" ,"66"};
        String delete_text[] = {"", "삭제", ""};

        for(int i = 0; i< user_pic.length; i++){
            com.example.goodhabeat_view.CommunityData dataSet = new com.example.goodhabeat_view.CommunityData(user_pic[i], user_nikName[i], create_date[i], create_time[i],
                                            content_text[i], content_img[i], heart_img[i], heart_number[i], delete_text[i]);
            community_data.add(dataSet);
        }

        recyclerView.setAdapter(new CommunityRecyclerViewAdapter(community_data));



        //네비게이션 관련 코드
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);

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
}