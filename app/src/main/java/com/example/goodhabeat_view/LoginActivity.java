package com.example.goodhabeat_view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.google.android.material.navigation.NavigationView;

public class LoginActivity extends AppCompatActivity {

    EditText etId, etPwd;
    TextView tvJoin;
    SharedPreferences preferences;
    Button btnLogin;

    //SharedPreferences preferences2;
    //TextView tvUserName;

    //프레그먼트
    /*FragmentManager fm;
    ChallengeFragment_Not challengeFragment_not;

    ConstraintLayout container;*/


    //Volley
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etId = (EditText) findViewById(R.id.etID);
        etPwd = (EditText) findViewById(R.id.etPwd);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        tvJoin = (TextView) findViewById(R.id.tvJoin);

        /*String url = "http://192.168.56.1:8000/AndroidAppEx/capstoneEx/capstoneLoginEx.jsp";
        requestQueue = Volley.newRequestQueue(getApplicationContext());*/


        //아이디와 비밀번호를 입력하고, '로그인'을 클릭하면 해당 사용자의 닉네임이 네비게이션뷰에 표시됨
        // + 해당 사용자의 메인홈 화면으로 넘어가야 함
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = etId.getText().toString();
                String pwd = etPwd.getText().toString();

                String nickname = "가나다라";

                if(id.equals("abcde") && pwd.equals("12345")) {
                    preferences = getApplicationContext().getSharedPreferences("userInfo", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("name", nickname);
                    editor.commit();

                    CustomToast(nickname +"님\n로그인 성공!");
                    //Toast.makeText(getApplicationContext(), nickname +"님\n로그인 성공!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
                else {
                    CustomToast("아이디나 비밀번호가 틀렸습니다.\n다시 입력하세요");
                    //Toast.makeText(getApplicationContext(), "아이디나 비밀번호가 틀렸습니다.\n다시 입력하세요", Toast.LENGTH_SHORT).show();
                }

                /*String url2 = url
                        + "?id=" + etId.getText().toString()
                        + "&pwd=" + etPwd.getText().toString();

                System.out.println("\n" + url2);

                StringRequest stringRequest = new StringRequest(Request.Method.GET,
                        url2,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject mainObj = new JSONObject(response);
                                    JSONArray jsonArray = mainObj.getJSONArray("users");

                                    JSONObject user = jsonArray.getJSONObject(0);
                                    String nickname = user.getString("nickname");

                                    System.out.println("\n\n" + nickname);

                                    //닉넴임 변수에 값이 들어있으면
                                    if(nickname != null) {
                                        preferences = getApplicationContext().getSharedPreferences("userInfo", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = preferences.edit();
                                        editor.putString("name", nickname);
                                        editor.commit();

                                        Toast.makeText(getApplicationContext(), nickname + "님, 환영합니다.", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), "nickname이 null값입니다. 로그인 오류 발생", Toast.LENGTH_SHORT).show();
                                    }
                                }catch(Exception e) {}
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), "error : " + error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                requestQueue.add(stringRequest);*/


/*
                //해당 사용자의 메인 홈페이지로 넘아가는 코드 추가 - intent 이용
                //메인화면으로 이동하는 코드
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

 */

            }
        });

        //'가입하기'를 클릭하면 회원가입을 할 수 있는 액티비티로 넘어감
        tvJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
                startActivity(intent);
            }
        });

        //타이틀바 없애는 코드
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();



    }

    //커스텀 토스트 메시지 메소드
    public void CustomToast(String message){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup)findViewById(R.id.toast_layout));
        TextView toast_textview  = layout.findViewById(R.id.toast_textview);
        toast_textview.setText(String.valueOf(message));
        Toast toast = new Toast(getApplicationContext());
        //toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);  //TODO 메시지가 표시되는 위치지정 (가운데 표시)
        //toast.setGravity(Gravity.TOP, 0, 0);              //TODO 메시지가 표시되는 위치지정 (상단 표시)
        toast.setGravity(Gravity.BOTTOM, 0, 270);           //TODO 메시지가 표시되는 위치지정 (하단 표시)
        toast.setDuration(Toast.LENGTH_SHORT); //메시지 표시 시간
        toast.setView(layout);
        toast.show();
    }


}
