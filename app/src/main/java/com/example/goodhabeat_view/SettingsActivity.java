package com.example.goodhabeat_view;

import static com.example.goodhabeat_view.LoginActivity.requestQueue;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
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
    TextView again_test, test_result;
    TextView getout;

    EditText editNick, editBirth, editHeight, editWeight ;
    Button save, nick;

    String nick_check = "0", id_check = "0",birth_check = "0", height_check = "0", weight_check = "0", save_check = "0";
    String nick_confirm = "0";
    Calendar calendar = Calendar.getInstance();

    String id_get, nick_get, email_get, birth_get, height_get, weight_get;
    String con_get, hp_get, v_get, lc_get, lsa_get, lsu_get;
    String con_string, hp_string, v_string, lc_string, lsa_string, lsu_string;
    StringBuilder String_sum = new StringBuilder("");

    ConstraintLayout password_change;

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

        again_test = (TextView)findViewById(R.id.textView39);
        test_result = (TextView)findViewById(R.id.textView38);
        getout  = (TextView)findViewById(R.id.getout);

        editNick = (EditText)findViewById(R.id.settingsEditNick) ;
        editBirth = (EditText)findViewById(R.id.settingsEditBirth) ;
        editHeight = (EditText)findViewById(R.id.setting_Height) ;
        editWeight = (EditText)findViewById(R.id.setting_Weight) ;
        password_change = (ConstraintLayout)findViewById(R.id.password_change);

        save = (Button)findViewById(R.id.save_button);
        nick = (Button)findViewById(R.id.btn_nickname);

        //--------------------------------------------------------------------------------------------------------------------------------------------------------

        /*
        String nickname = response;
        preferences = getApplicationContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("nickname", nickname);
        editor.commit();
        */

        //프리퍼런스로 로그인한 닉네임 가져오기
        preferences = getApplicationContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String nickname_get = preferences.getString("nickname", "nickname 오류" );
        System.out.println(nickname_get);
        //--------------------------------------------------------------------------------------------------------------------------------------------------------



        //닉네임 확인
        String url_nickCheck = "http://10.0.2.2:3000/nick_check";
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        //닉네임 존재 확인
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url_nickCheck,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                            nick_confirm = "ok";

                            try {
                                System.out.println("응답완료: "+response);
                                JSONArray jsonarray = new JSONArray(response);
                                //JSONArray jArray = mainObj.getJSONArray("");
                                JSONObject setting_json = jsonarray.getJSONObject(0);
                                id_get = setting_json.getString("id");
                                nick_get = setting_json.getString("nickname");
                                email_get = setting_json.getString("email");
                                birth_get = setting_json.getString("birth");
                                String birth_cut =  birth_get.substring(0,10); //abc
                                height_get = setting_json.getString("height");
                                weight_get = setting_json.getString("weight");

                                tvUserName.setText(id_get);
                                editNick.setText(nick_get);
                                tvUserEmail.setText(email_get);
                                editBirth.setText(birth_cut);
                                editHeight.setText(height_get);
                                editWeight.setText(weight_get);
                                nick_confirm = "ok";
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

                                } else {
                                    //2면 사용 불가
                                    System.out.println(response+ " / 닉네임 사용 불가능");
                                    Toast.makeText(getApplicationContext(), "사용 불가능한 닉네임입니다", Toast.LENGTH_SHORT).show();
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


        //--------------------------------------------------------------------------------------------------------------------------------------------------------
        // 수정 저장


        // 세팅 버튼
        String url_setting = "http://10.0.2.2:3000/setting_save";
        requestQueue = Volley.newRequestQueue(getApplicationContext());


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nickname = editNick.getText().toString();
                    if(!nickname.equals("")) nick_check = "ok";
                    else nick_check = "no";

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
                if(nick_confirm.equals("ok") && nick_check.equals("ok") && birth_check.equals("ok") && height_check.equals("ok") && weight_check.equals("ok"))
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
                                params.put("save_check", save_check);
                                params.put("id_get", id_get);
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

                            test_result.setText(String_sum.substring(0, String_sum.length()-2));


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
        again_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RecommendedDietSurveyActivity.class);
                startActivity(intent);

            }
        });

        getout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder dlg = new AlertDialog.Builder(getApplicationContext());
                dlg.setMessage("정말 탈퇴하시겠습니까?");
                dlg.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String url_getout = "http://10.0.2.2:3000/getout";
                        requestQueue = Volley.newRequestQueue(getApplicationContext());

                        SharedPreferences preferences;
                        preferences = getApplicationContext().getSharedPreferences("userInfo", MODE_PRIVATE);
                        String user_id = preferences.getString("user_id","");

                        StringRequest request = new StringRequest(
                                Request.Method.POST,
                                url_getout,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Toast.makeText(getApplicationContext(), "탈퇴되었습니다.", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), introActivity.class);
                                        startActivity(intent);
                                        finish();
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

                                params.put("user_id", user_id);

                                return params;
                            }
                        };
                        requestQueue.add(request);

                    }
                });
                dlg.setNegativeButton("취소", null);
                dlg.show();
            }
        });


        password_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PasswordChangeActivity.class);
                startActivity(intent);
                finish();
            }
        });


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