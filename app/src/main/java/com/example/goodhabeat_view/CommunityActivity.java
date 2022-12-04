package com.example.goodhabeat_view;

import static com.example.goodhabeat_view.LoginActivity.requestQueue;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CommunityActivity extends AppCompatActivity {

    //네비게이션 관련 코드
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;

    int my_heart_icons =1;
    String my_state;
    TextView myWrote, write_content;

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
        myWrote = (TextView) findViewById(R.id.myWrote);
        write_content = (TextView) findViewById(R.id.write_content);

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
        ArrayList<CommunityData> community_data = new ArrayList<>();

        //SharedPreference
        preferences = getApplicationContext().getSharedPreferences("userInfo", MODE_PRIVATE);
        String userId = preferences.getString("user_id","");

        write_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String commu_post = "http://10.0.2.2:3000/commu_post";
                requestQueue =  Volley.newRequestQueue(getApplicationContext());

                Request request = new StringRequest(Request.Method.POST, commu_post, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            System.out.println("response : " + response);

                            JSONArray resArray = new JSONArray(response);

                            for(int i=0; i<resArray.length(); i++) {
                                JSONObject dietObj = resArray.getJSONObject(i);
                                //System.out.println("dietObj[" + i + "] : " + dietObj);
                                String my_post_id = dietObj.getString("post_id");
                                String my_nickname = dietObj.getString("nickname");
                                String my_content = dietObj.getString("content");
                                String my_postdate = dietObj.getString("post_date");
                                String my_post_date = my_postdate.substring(0,10);
                                String my_posttime = dietObj.getString("post_time");
                                String my_post_time = my_posttime.substring(0,5);
                                String my_post_image = dietObj.getString("post_image");
                                String my_like_count = dietObj.getString("like_count");
                                my_heart_icons = 0;
                                String my_del_state = dietObj.getString("del_state");
                                if(Integer.parseInt(my_del_state) > 0){
                                    my_state = "";
                                }


                                CommunityData dataSet = new CommunityData(my_post_id, my_nickname, my_post_date, my_post_time, my_content, my_post_image, my_heart_icons , my_like_count , my_state);
                                community_data.add(dataSet);
                            }

                            recyclerView.setAdapter(new CommunityRecyclerViewAdapter(getApplicationContext(), community_data));

                        }catch (Exception e){ e.printStackTrace(); }
                        System.out.println("글 가져오기 성공 : " + response);
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                System.out.println(error.getMessage());
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("user_id", userId);

                        return params;
                    }
                };

                requestQueue.add(request);
            }
        });

        String commu_post = "http://10.0.2.2:3000/commu_post";
        requestQueue =  Volley.newRequestQueue(getApplicationContext());

        Request request = new StringRequest(Request.Method.POST, commu_post, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    System.out.println("response : " + response);

                    JSONArray resArray = new JSONArray(response);

                    for(int i=0; i<resArray.length(); i++) {
                        JSONObject dietObj = resArray.getJSONObject(i);
                        //System.out.println("dietObj[" + i + "] : " + dietObj);
                        String my_post_id = dietObj.getString("post_id");
                        String my_nickname = dietObj.getString("nickname");
                        String my_content = dietObj.getString("content");
                        String my_postdate = dietObj.getString("post_date");
                        String my_post_date = my_postdate.substring(0,10);
                        String my_posttime = dietObj.getString("post_time");
                        String my_post_time = my_posttime.substring(0,5);
                        String my_post_image = dietObj.getString("post_image");
                        String my_like_count = dietObj.getString("like_count");
                        my_heart_icons = 0;
                        String my_del_state = dietObj.getString("del_state");
                        if(Integer.parseInt(my_del_state) > 0){
                            my_state = "";
                        }

                        System.out.println("글 아이디: " +my_post_id);


                        CommunityData dataSet = new CommunityData(my_post_id, my_nickname, my_post_date, my_post_time, my_content, my_post_image, my_heart_icons , my_like_count , my_state);
                        community_data.add(dataSet);
                    }

                    recyclerView.setAdapter(new CommunityRecyclerViewAdapter(getApplicationContext(), community_data));

                }catch (Exception e){ e.printStackTrace(); }
                System.out.println("글 가져오기 성공 : " + response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", userId);

                return params;
            }
        };

        requestQueue.add(request);





        //특정 사용자 글만 보는 법
        myWrote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String state = "1";
                VolleyMyCommuinty(state);
            }
        });




        //-----------------------------------------------------------------------------------------------------------------------------

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

    // 외부 함수
    public void VolleyMyCommuinty(String state) {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.community_list_container);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<CommunityData> community_data = new ArrayList<>();

        //SharedPreference
        preferences = getApplicationContext().getSharedPreferences("userInfo", MODE_PRIVATE);
        String userId = preferences.getString("user_id","");


        String commu_mypost = "http://10.0.2.2:3000/commu_mypost";
        requestQueue =  Volley.newRequestQueue(getApplicationContext());

        Request request = new StringRequest(Request.Method.POST, commu_mypost, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    System.out.println("response : " + response);

                    JSONArray resArray = new JSONArray(response);

                    for(int i=0; i<resArray.length(); i++) {
                        JSONObject dietObj = resArray.getJSONObject(i);
                        //System.out.println("dietObj[" + i + "] : " + dietObj);
                        String my_post_id = dietObj.getString("post_id");
                        String my_nickname = dietObj.getString("nickname");
                        String my_content = dietObj.getString("content");
                        String my_postdate = dietObj.getString("post_date");
                        String my_post_date = my_postdate.substring(0,10);
                        String my_posttime = dietObj.getString("post_time");
                        String my_post_time = my_posttime.substring(0,5);
                        String my_post_image = dietObj.getString("post_image");
                        String my_like_count = dietObj.getString("like_count");
                            my_heart_icons = 0;
                        String my_del_state = dietObj.getString("del_state");
                        if(Integer.parseInt(my_del_state) > 0){
                            my_state = "삭제";
                        }


                        CommunityData dataSet = new CommunityData(my_post_id, my_nickname, my_post_date, my_post_time, my_content, my_post_image, my_heart_icons , my_like_count , my_state);
                        community_data.add(dataSet);
                    }

                    recyclerView.setAdapter(new CommunityRecyclerViewAdapter(getApplicationContext(), community_data));

                }catch (Exception e){ e.printStackTrace(); }
                System.out.println("글 가져오기 성공 : " + response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", userId);

                return params;
            }
        };

        requestQueue.add(request);


    }
}