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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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

public class MainActivity extends AppCompatActivity {
    TextView today_clock;
    LinearLayout menu_list;
    ImageView breakfast_main;
    ImageView lunch_main;
    ImageView dinner_main;

    TextView challenge_date;
    ProgressBar goal_main_bar;
    ProgressBar today_cal_bar;

    TextView tvSeason;
    ImageView season_food_img;
    TextView season_name;
    TextView season_recipe;
    TextView season_exp;

    String url;
    Integer season_date;

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
        setContentView(R.layout.activity_main);

        today_clock = (TextView) findViewById(R.id.today_clock);
        menu_list = (LinearLayout) findViewById(R.id.menu_list);
        breakfast_main = (ImageView) findViewById(R.id.breakfast_main);
        lunch_main = (ImageView) findViewById(R.id.lunch_main);
        dinner_main = (ImageView) findViewById(R.id.dinner_main);

        challenge_date = (TextView) findViewById(R.id.challenge_date);
        goal_main_bar = (ProgressBar) findViewById(R.id.goal_main_bar);
        today_cal_bar = (ProgressBar) findViewById(R.id.today_cal_bar);

        tvSeason = (TextView) findViewById(R.id.tv_season);
        season_food_img = (ImageView) findViewById(R.id.season_food_img);
        season_name = (TextView) findViewById(R.id.season_name);
        season_recipe = (TextView) findViewById(R.id.season_recipe);
        season_exp = (TextView) findViewById(R.id.season_exp);


        //getSupportActionBar().hide();

        breakfast_main.setImageResource(R.drawable.salmon);
        breakfast_main.setImageResource(R.drawable.salad);
        breakfast_main.setImageResource(R.drawable.broccoli);
        season_food_img.setImageResource(R.drawable.strawberryy);

        //상단 메뉴 사진 클릭시 음식메뉴 리스트 이동
        menu_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        url="http://192.168.0.58:8080/capston/exprot2.jsp";
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject mainObj = new JSONObject(response);
                    JSONArray jArray = mainObj.getJSONArray("List");
                    JSONObject food_name1 = jArray.getJSONObject(0);
                    String name = food_name1.getString("food_name");
                    String description = food_name1.getString("food_description");
                    season_name.setText(name);
                    //food_season.setText("음식 계절: "+season);
                    season_exp.setText(description);


                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //에러코드
                    }

                });
        requestQueue.add(stringRequest);

        season_date = 6;
        if(season_date==12|season_date<3){
            tvSeason.setText("겨울에 추천하는 제철 음식");
        } else if(season_date<6) {
            tvSeason.setText("봄에 추천하는 제철 음식");
        }else if(season_date<9){
            tvSeason.setText("여름에 추천하는 제철 음식");
        } else if(season_date<12) {
            tvSeason.setText("가을에 추천하는 제철 음식");
        }

        int value = 7;
        //goal_main_bar.setProgress(value);

        int value2 = 500;
        //today_cal_bar.setProgress(value2);

        season_recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RecipeActivity.class);
                startActivity(intent);
            }
        });


        season_exp.setText("딸기는 장미과 딸기속에 속하는 과채류이다. 산딸기, 뱀딸기, 야생딸기 등과 재배하는 딸기로 구분된다. 딸기에 있는 영양소에는 비타민C, 안토시아닌, 칼륨, 엽산 등이 있다.");


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

//깃허브 수정 예시asdsadsdadsaasd