package com.example.goodhabeat_view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
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
import org.w3c.dom.Text;

import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends AppCompatActivity {

    TextView today_clock;

    /*LinearLayout menu_list;
    ImageView breakfast_main;
    ImageView lunch_main;
    ImageView dinner_main;*/

    //이미지 슬라이드 관련
    ConstraintLayout menu_image;
    ImagePagerAdapter imageAdapter;
    SwipeableViewPager swipeableViewPager;
    //ViewPager viewPager;
    CircleIndicator indicator;

    //카테고리 별 식단 페이지 이동 관련
    TextView categoryText_convenience;
    TextView categoryText_highPro;
    TextView categoryText_lowCal;
    TextView categoryText_vitamin_mineral;
    TextView categoryText_lowSalt;
    TextView categoryText_lowSugar;

    //TextView challenge_date;
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

        /*menu_list = (LinearLayout) findViewById(R.id.menu_img_area);
        breakfast_main = (ImageView) findViewById(R.id.breakfast_main);
        lunch_main = (ImageView) findViewById(R.id.lunch_main);
        dinner_main = (ImageView) findViewById(R.id.menu_main);*/

        menu_image = (ConstraintLayout) findViewById(R.id.menu_img_area);

        categoryText_convenience = (TextView) findViewById(R.id.main_convenience_category);
        categoryText_highPro = (TextView) findViewById(R.id.main_highPro_category);
        categoryText_lowCal = (TextView) findViewById(R.id.main_lowCal_category);
        categoryText_vitamin_mineral = (TextView) findViewById(R.id.main_vitamin_mineral_category);
        categoryText_lowSalt = (TextView) findViewById(R.id.main_lowSalt_category);
        categoryText_lowSugar = (TextView) findViewById(R.id.main_lowSugar_category);

        /*challenge_date = (TextView) findViewById(R.id.challenge_date);*/
        goal_main_bar = (ProgressBar) findViewById(R.id.goal_main_bar);
        today_cal_bar = (ProgressBar) findViewById(R.id.today_cal_bar);

        tvSeason = (TextView) findViewById(R.id.tv_season);
        season_food_img = (ImageView) findViewById(R.id.season_food_img);
        season_name = (TextView) findViewById(R.id.season_name);
        season_recipe = (TextView) findViewById(R.id.season_recipe);
        season_exp = (TextView) findViewById(R.id.season_exp);


        //getSupportActionBar().hide();

        /*breakfast_main.setImageResource(R.drawable.salmon);
        breakfast_main.setImageResource(R.drawable.salad);
        breakfast_main.setImageResource(R.drawable.broccoli);*/

        season_food_img.setImageResource(R.drawable.strawberryy);

        //상단 이미지 슬라이드 관련 코드

        swipeableViewPager = findViewById(R.id.ImageViewPager_swipleable);
        imageAdapter = new ImagePagerAdapter(this);
        swipeableViewPager.setAdapter(imageAdapter);

        indicator = findViewById(R.id.indicator);
        indicator.setViewPager(swipeableViewPager);

        //상단 메뉴 사진 클릭시 음식메뉴 리스트 이동
        menu_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
            }
        });

        /*swipeableViewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
            }
        });*/

        /*viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    view.performClick();
                }
                return false;
            }
        });

        viewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
            }
        });*/

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


        //메인 중간에 있는 카테고리 텍스트를 클릭하면, 해당 카테고리 탭에 해당하는 페이지로 이동하는 코드
        categoryText_convenience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //int tabIndex = 1;

                Intent intent = new Intent(getApplicationContext(), CategoryDietActivity.class);
                intent.putExtra("TabIndex", 0);
                startActivity(intent);
            }
        });

        categoryText_highPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), CategoryDietActivity.class);
                intent.putExtra("TabIndex", 1);
                startActivity(intent);
            }
        });

        categoryText_vitamin_mineral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), CategoryDietActivity.class);
                intent.putExtra("TabIndex", 2);
                startActivity(intent);
            }
        });

        categoryText_lowCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), CategoryDietActivity.class);
                intent.putExtra("TabIndex", 3);
                startActivity(intent);
            }
        });

        categoryText_lowSalt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), CategoryDietActivity.class);
                intent.putExtra("TabIndex", 4);
                startActivity(intent);
            }
        });

        categoryText_lowSugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), CategoryDietActivity.class);
                intent.putExtra("TabIndex", 5);
                startActivity(intent);
            }
        });


        season_date = 8;
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


        //season_exp.setText("딸기는 장미과 딸기속에 속하는 과채류이다. 산딸기, 뱀딸기, 야생딸기 등과 재배하는 딸기로 구분된다. 딸기에 있는 영양소에는 비타민C, 안토시아닌, 칼륨, 엽산 등이 있다.");


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
                    Toast.makeText(getApplicationContext(), "커뮤니티", Toast.LENGTH_SHORT).show();

                    //커뮤니티 화면으로 이동하는 페이지 코드 입력 예정
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

