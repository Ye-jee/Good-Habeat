package com.example.goodhabeat_view;

import static com.android.volley.VolleyLog.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText etId, etPwd;
    TextView tvJoin;
    SharedPreferences preferences;
    Button btnLogin;

    //Volley
    static RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etId = (EditText) findViewById(R.id.etID);
        etPwd = (EditText) findViewById(R.id.etPwd);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        tvJoin = (TextView) findViewById(R.id.tvJoin);

        String url = "http://10.0.2.2:3000/login";


        //아이디와 비밀번호를 입력하고, '로그인'을 클릭하면 해당 사용자의 닉네임이 네비게이션뷰에 표시됨
        // + 해당 사용자의 메인홈 화면으로 넘어가야 함
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(), "버튼 클릭", Toast.LENGTH_SHORT).show();
                String input_id = etId.getText().toString();
                String input_pw = etPwd.getText().toString();

                if (TextUtils.isEmpty(input_id) || TextUtils.isEmpty(input_pw)) {
                    Toast.makeText(LoginActivity.this, "아이디와 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        //Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println("/login response : " + response);
                            System.out.println("---------------------");

                            if (response.equals("wrong password")) {
                                Toast.makeText(getApplicationContext(), "비밀번호를 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
                                //Toast.makeText(getApplicationContext(), "Login fail : " + response, Toast.LENGTH_SHORT).show();
                            } else if (response.equals("no id")) {
                                Toast.makeText(getApplicationContext(), "아이디가 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                            } else {
                                try {
                                    String nickname = response;
                                    preferences = getApplicationContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString("nickname", nickname);
                                    editor.commit();

                                    Toast.makeText(LoginActivity.this, nickname + "님 환영합니다.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                } catch(Exception e) { e.printStackTrace(); }
                            }

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
                            parameters.put("password", input_pw);
                            parameters.put("id", input_id);
                            return parameters;
                        }
                    };

                    // 사용자 지정 정책 --> 타임아웃 에러 해결
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
    public void CustomToast(String message) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_layout));
        TextView toast_textview = layout.findViewById(R.id.toast_textview);
        toast_textview.setText(String.valueOf(message));
        Toast toast = new Toast(getApplicationContext());
        //toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);  //TODO 메시지가 표시되는 위치지정 (가운데 표시)
        //toast.setGravity(Gravity.TOP, 0, 0);              //TODO 메시지가 표시되는 위치지정 (상단 표시)

        // 수정
        toast.setGravity(Gravity.BOTTOM, -150, -100);           //TODO 메시지가 표시되는 위치지정 (하단 표시)
        // 추가
        toast.setMargin(0.1f, 0.1f);

        toast.setDuration(Toast.LENGTH_SHORT); //메시지 표시 시간
        toast.setView(layout);
        toast.show();
    }


}
