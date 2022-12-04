package com.example.goodhabeat_view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends AppCompatActivity {

    Date currentTime = Calendar.getInstance().getTime();
    public static String todayDateFormat = "yyyy년 MM월 dd일";
    public static String getMonthFormat = "MM";
    public static String getTimeFormat = "HH";

    TextView today_clock;
    TextView today_clock_text;
    TextView tv_TodayDate;

    //이미지 슬라이드 관련
    ImagePagerAdapter imageAdapter;
    //SwipeableViewPager swipeableViewPager;
    ViewPager viewPager;
    CircleIndicator indicator;

    Bitmap bitmap;

    //카테고리 별 식단 페이지 이동 관련
    TextView categoryText_convenience;
    TextView categoryText_highPro;
    TextView categoryText_lowCal;
    TextView categoryText_vitamin_mineral;
    TextView categoryText_lowSalt;
    TextView categoryText_lowSugar;

    double calorie_sum = 0; // 88
    ProgressBar today_cal_bar;

    ImageView season_image;
    TextView tvSeason;
    ImageView season_food_img;
    TextView season_name;
    TextView season_recipe;
    TextView season_exp;

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
        today_clock_text = (TextView) findViewById(R.id.today_clock_text);
        tv_TodayDate = (TextView) findViewById(R.id.tv_TodayDate);

        categoryText_convenience = (TextView) findViewById(R.id.main_convenience_category);
        categoryText_highPro = (TextView) findViewById(R.id.main_highPro_category);
        categoryText_lowCal = (TextView) findViewById(R.id.main_lowCal_category);
        categoryText_vitamin_mineral = (TextView) findViewById(R.id.main_vitamin_mineral_category);
        categoryText_lowSalt = (TextView) findViewById(R.id.main_lowSalt_category);
        categoryText_lowSugar = (TextView) findViewById(R.id.main_lowSugar_category);

        today_cal_bar = (ProgressBar) findViewById(R.id.today_cal_bar);

        season_image = (ImageView) findViewById(R.id.season_image);
        tvSeason = (TextView) findViewById(R.id.tv_season);
        season_food_img = (ImageView) findViewById(R.id.season_food_img);
        season_name = (TextView) findViewById(R.id.season_name);
        season_recipe = (TextView) findViewById(R.id.season_recipe);
        season_exp = (TextView) findViewById(R.id.season_exp);

        //SharedPreference
        preferences = getApplicationContext().getSharedPreferences("userInfo", MODE_PRIVATE);
        String userName = preferences.getString("nickname","");
        //System.out.println("preferences nickname : " + userName );

        // 오늘 날짜
        SimpleDateFormat todayFormat = new SimpleDateFormat(todayDateFormat, Locale.getDefault());
        tv_TodayDate.setText(todayFormat.format(currentTime));

        // 시간대 (아침, 점심, 저녁)
        SimpleDateFormat timeFormat = new SimpleDateFormat(getTimeFormat, Locale.getDefault());
        Integer clock = Integer.parseInt(timeFormat.format(currentTime));

        //상단 이미지 슬라이드 관련 코드
        // 상단 메뉴 사진 클릭시, 오늘의 메뉴 페이지 이동하는 코드는 ImagePagerAdapter에서 작성함
        viewPager = findViewById(R.id.ImageViewPager);

        if(clock >= 6 && clock <= 11){
            today_clock.setText("아침");
            setCurrentMenuImage(1, userName);
        } else if(clock >= 12 && clock <= 17) {
            today_clock.setText("점심");
            setCurrentMenuImage(2, userName);
        } else if(clock >= 18 && clock <= 23) {
            today_clock.setText("저녁");
            setCurrentMenuImage(3, userName);
        } else if(clock >= 0 && clock <= 5) {
            setCurrentMenuImage(1, userName); // 임시 배치
        }


        // Volley
        // main 입장 시 사용자 id가 서버로 전송됨
        // 사용자 닉네임 전송 -> nickname 필드값이 동일한 user_id 찾기 -> 서버에서 찾은 user_id로 오늘의 식단 기능에 활용
        String url = "http://10.0.2.2:3000/main";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray detail_json = new JSONArray(response);
                    System.out.println(detail_json);

                    // 사용자 아이디
                    String user_id = detail_json.getJSONObject(0).getString("user_id");
                    preferences = getApplicationContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("user_id", user_id);
                    editor.commit();

                    // 총 칼로리량
                    for(int i=0; i<detail_json.length(); i++){
                        JSONObject dietObj = detail_json.getJSONObject(i);
                        Double calorie = Double.parseDouble(dietObj.getString("calorie"));
                        calorie_sum += calorie ;
                    }

                    int calorie_int = (int) Math.round(calorie_sum);

                    today_cal_bar.setMax(2350); //프로그레스바 Max 크기
                    today_cal_bar.setProgress(calorie_int); //프로그레스 바 칼로리

                }catch (Exception e){ e.printStackTrace(); }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "ERROR : " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        ){
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<>();
                parameters.put("nickname", userName);
                return parameters;
            }
        };

        // 사용자 지정 정책 --> 타임아웃 에러 해결
        customVolleyEnd(stringRequest, requestQueue);

        /**/

        //메인 중간에 있는 카테고리 텍스트를 클릭하면, 해당 카테고리 탭에 해당하는 페이지로 이동하는 코드
        MoveCategory();

        /**/

        // 계절 식재료
        SimpleDateFormat monthFormat = new SimpleDateFormat(getMonthFormat, Locale.getDefault());
        Integer season_date = Integer.parseInt(monthFormat.format(currentTime));

        if(season_date == 12 | season_date < 3){
            season_image.setImageResource(R.drawable.season_icon_winter_02);
            tvSeason.setText("겨울에 추천하는 제철 음식");
        } else if(season_date < 6) {
            season_image.setImageResource(R.drawable.season_icon_spring_02);
            tvSeason.setText("봄에 추천하는 제철 음식");
        }else if(season_date < 9){
            season_image.setImageResource(R.drawable.season_icon_summer_03);
            tvSeason.setText("여름에 추천하는 제철 음식");
        } else if(season_date < 12) {
            season_image.setImageResource(R.drawable.season_icon_autumn_03);
            tvSeason.setText("가을에 추천하는 제철 음식");
        }

        // Volley
        String url_s = "http://10.0.2.2:3000/main/season";
        RequestQueue requestQueue_s = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest_s = new StringRequest(Request.Method.POST, url_s, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //System.out.println("/main/season response : " + response);
                //System.out.println("---------------------");

                try {
                    JSONArray resArray = new JSONArray(response);
                    int max_num_value = resArray.length();

                    Random random = new Random();
                    int i = random.nextInt(max_num_value);
                    JSONObject foodObj = resArray.getJSONObject(i);

                    String food_name = foodObj.getString("food_name");
                    String food_description = foodObj.getString("food_description");
                    //String food_image = foodObj.getString("food_image");

                    season_name.setText(food_name);
                    season_exp.setText(food_description);
                    season_exp.setMovementMethod(new ScrollingMovementMethod()); // 텍스트뷰 스크롤 설정
                    season_exp.setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);

                    season_food_img.setImageResource(R.drawable.strawberryy);

                }catch (Exception e){ e.printStackTrace(); }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "ERROR : " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        ){
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<>();
                parameters.put("season", season_date.toString());
                return parameters;
            }
        };

        // 사용자 지정 정책 --> 타임아웃 에러 해결
        customVolleyEnd(stringRequest_s, requestQueue_s);

        season_recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RecipeActivity.class);
                System.out.println("season_name : " + season_name.getText().toString());
                intent.putExtra("foodName", season_name.getText().toString());
                startActivity(intent);
            }
        });

        /**/

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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(toggle.onOptionsItemSelected(item)) {    //navigation toggle
            //SharedPreference
            preferences = getApplicationContext().getSharedPreferences("userInfo", MODE_PRIVATE);
            String userName = preferences.getString("nickname","");

            //NavigationDrawer Header 텍스트값 변경
            tvUserName = (TextView) navHeader.findViewById(R.id.tv_username1);
            tvUserName.setText(userName+" 님");

            return false;
        }
        return super.onOptionsItemSelected(item);
    }

    // 이미지 URL 가져오기
    public void setCurrentMenuImage(Integer meal, String nickname) {
        ArrayList<ImageUrlData> menu_image_data = new ArrayList<>();

        String url = "http://10.0.2.2:3000/main/menu_image";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //System.out.println(response);

                try {
                    JSONArray resArray = new JSONArray(response);
                    //System.out.println(resArray);

                    for(int i=0; i<resArray.length(); i++) {
                        JSONObject imgObj = resArray.getJSONObject(i);
                        System.out.println("imgObj[" + i + "] : " + imgObj);

                        String recipe_image = imgObj.getString("recipe_image");
                        recipe_image = recipe_image.replaceAll("'\'", "");
                        System.out.println(recipe_image);


                        ImageUrlData dataSet = new ImageUrlData(recipe_image);
                        //ImageUrlData dataSet = new ImageUrlData(setURLImage(recipe_image));
                        menu_image_data.add(dataSet);
                    }

                    imageAdapter = new ImagePagerAdapter(getApplicationContext(), menu_image_data);
                    viewPager.setAdapter(imageAdapter);

                    indicator = findViewById(R.id.indicator);
                    indicator.setViewPager(viewPager);

                }catch (Exception e){ e.printStackTrace(); }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "ERROR : " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        ){
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<>();
                parameters.put("nickname", nickname);
                parameters.put("meal", meal.toString());
                return parameters;
            }
        };

        // 사용자 지정 정책 --> 타임아웃 에러 해결
        customVolleyEnd(stringRequest, requestQueue);

    }

    // 카테고리 선택
    public void MoveCategory() {
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
    }

    // 사용자 지정 정책 --> 타임아웃 에러 해결
    public void customVolleyEnd(StringRequest stringRequest, RequestQueue requestQueue){
        stringRequest.setRetryPolicy(new com.android.volley.DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        if (requestQueue == null) {
            Volley.newRequestQueue(getApplicationContext());
        }

        stringRequest.setShouldCache(false); // 이전 결과가 있어도 새로 요청하여 응답을 보여줌
        requestQueue.add(stringRequest);
    }

}