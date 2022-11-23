package com.example.goodhabeat_view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SettingsActivity extends AppCompatActivity {

    //네비게이션 관련 코드
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;

    //네비게이션 드로우어 헤더
    View navHeader;

    RequestQueue requestQueue;

    SharedPreferences preferences;
    TextView tvUserName, tvUserEmail;

    EditText editNick, editBirth, editHeight, editWeight ;
    Button save, nick;

    String nick_check = "0", birth_check = "0", height_check = "0", weight_check = "0", save_check = "0";
    String id_confirm = "id_no";

    Calendar calendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //타이틀바 없애는 코드
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        // 추가 --------------------------------------------------------------------------------------------------------------------------------------------------------
        tvUserName = (TextView)findViewById(R.id.textView28);
        tvUserEmail = (TextView)findViewById(R.id.textView29);

        editNick = (EditText)findViewById(R.id.settingsEditNick) ;
        editBirth = (EditText)findViewById(R.id.settingsEditBirth) ;
        editHeight = (EditText)findViewById(R.id.setting_Height) ;
        editWeight = (EditText)findViewById(R.id.setting_Weight) ;

        save = (Button)findViewById(R.id.button);
        nick = (Button)findViewById(R.id.btn_nickname);

        //--------------------------------------------------------------------------------------------------------------------------------------------------------

        /*
        String nickname = response;
        preferences = getApplicationContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("nickname", nickname);
        editor.commit();
        */

        //프리퍼런스로 id 연결
        preferences = getApplicationContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String id_get = preferences.getString("id_pre", "id 오류" );
        String nickname_get = preferences.getString("nickname_pre", "nickname 오류" );
        String email_get = preferences.getString("email_pre", "email 오류" );
        String birth_get = preferences.getString("birth_pre", "birth 오류" );
        String height_get = preferences.getString("height_pre", "height 오류" );
        String weight_get = preferences.getString("weight_pre", "weight 오류" );


        tvUserName.setText(id_get);
        tvUserEmail.setText(email_get);

        editNick.setText(nickname_get);
        editBirth.setText(birth_get);
        editHeight.setText(height_get);
        editWeight.setText(weight_get);


        //아이디 확인
        String url_idCheck = "http://10.0.2.2:3000/id_check";
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        System.out.println(id_get);
        //닉네임 존재 확인
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url_idCheck,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("id_yes")){
                            //1이면 사용 가능
                            System.out.println(response + " / 존재하는 계정");
                            //Toast.makeText(getApplicationContext(), "존재함", Toast.LENGTH_SHORT).show();
                            id_confirm = "id_yes";
                        } else {
                            //2면 사용 불가
                            System.out.println(response+ " / 존재하지 않는 계정");
                            //Toast.makeText(getApplicationContext(), "존재 안 함", Toast.LENGTH_SHORT).show();
                            id_confirm = "id_no";
                        }
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

                        params.put("id_check", id_get);

                        return params;
                    }
                };
        requestQueue.add(request);


        // 생년월일 입력에 캘린더뷰 추가
        DatePickerDialog birthPicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                editBirth.setText(year + "-" + (month+1) + "-" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        birthPicker.getWindow().setBackgroundDrawableResource(R.drawable.round_rec_background);

        editBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                birthPicker.show();
                // 추가 코드 ----
                birth_check = "ok";
                // ------------
            }
        });

        //닉네임 중복 확인 버튼
        String url_nick = "http://10.0.2.2:3000/nick_overlap";
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        nick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nickname = editNick.getText().toString();

                StringRequest request = new StringRequest(
                        Request.Method.POST,
                        url_nick,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(response.equals("nick_do")){
                                    //1이면 사용 가능
                                    System.out.println(response + " / 닉네임 사용 가능");
                                    Toast.makeText(getApplicationContext(), "사용 가능한 닉네임입니다", Toast.LENGTH_SHORT).show();
                                    nick_check = "ok";
                                } else {
                                    //2면 사용 불가
                                    System.out.println(response+ " / 닉네임 사용 불가능");
                                    Toast.makeText(getApplicationContext(), "사용 불가능한 닉네임입니다", Toast.LENGTH_SHORT).show();
                                    nick_check = "no";
                                }
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

                        params.put("nickname", nickname);

                        return params;
                    }
                };
                requestQueue.add(request);

            }
        }); // btnID 클릭 리스너 버튼 끝 */

        // 세팅 버튼
        String url_setting = "http://10.0.2.2:3000/setting_save";
        requestQueue = Volley.newRequestQueue(getApplicationContext());


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nickname = editNick.getText().toString();

                String birth = editBirth.getText().toString();
                    if(!birth.equals("")) birth_check = "ok";
                    else birth_check = "no";

                String height = editHeight.getText().toString();
                    if(!height.equals("")) height_check = "ok";
                    else height_check = "no";

                String weight = editWeight.getText().toString();
                    if(!weight.equals("")) weight_check = "ok";
                    else weight_check = "no";
                //아이디가 존재하고 수정 내용 사항들이 빈칸이 아니면 데이터를 보냄 (if문)
                if(id_confirm.equals("id_yes") && nick_check.equals("ok") && birth_check.equals("ok") && height_check.equals("ok") && weight_check.equals("ok"))
                    {
                        save_check = "ok";
                        StringRequest request = new StringRequest(
                                Request.Method.POST,
                                url_setting,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if(response.equals("ok")){
                                            //1이면 사용 가능
                                            System.out.println(response + " / 수정 완료");
                                            Toast.makeText(getApplicationContext(), "수정 완료!", Toast.LENGTH_SHORT).show();
                                            save_check = "ok";
                                        } else {
                                            //2면 사용 불가
                                            System.out.println(response+ " / 수정 불가능");
                                            Toast.makeText(getApplicationContext(), "기입한 정보를 확인해 주세요.", Toast.LENGTH_SHORT).show();
                                            save_check = "no";
                                        }
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
                                params.put("id_get", id_get);
                                params.put("save_check", save_check);
                                params.put("nickname", nickname);
                                params.put("birth", birth);
                                params.put("height", height);
                                params.put("weight", weight);

                                return params;
                            }
                        };
                        requestQueue.add(request);

                    } //if문 끝
                else {
                    Toast.makeText(getApplicationContext(), "정보를 모두 기입해 주세요.", Toast.LENGTH_SHORT).show();
                }
            }//Click 리스너 끝
        }); // save 버튼 끝










            //--------------------------------------------------------------------------------------------------------------------------------------------------------

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
}