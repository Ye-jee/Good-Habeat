package com.example.goodhabeat_view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class PasswordChangeActivity extends AppCompatActivity {

    EditText et_existPwd, et_changePwd, et_changePwdCheck;
    Button changePwd_btn;
    String pw_change_check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);


        et_existPwd = (EditText)findViewById(R.id.et_changePwd);
        et_changePwd = (EditText)findViewById(R.id.et_changePwd);
        et_changePwdCheck = (EditText)findViewById(R.id.et_changePwdCheck);

        changePwd_btn = (Button) findViewById(R.id.changePwd_btn);

        SharedPreferences preferences;
        preferences = getApplicationContext().getSharedPreferences("userInfo", MODE_PRIVATE);
        String user_id = preferences.getString("user_id","");


        changePwd_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input_pw = et_existPwd.getText().toString();
                String input_et_changePwd = et_changePwd.getText().toString();
                String input_et_changePwdCheck = et_changePwdCheck.getText().toString();
                if(input_et_changePwd.equals(input_et_changePwdCheck)){
                    pw_change_check = "ok";


                    String url_chage_url = "http://10.0.2.2:3000/password_chage";
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url_chage_url, new Response.Listener<String>() {
                        //Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println("/login response : " + response);
                            System.out.println("---------------------");

                            if (response.equals("wrong password")) {
                                Toast.makeText(getApplicationContext(), "비밀번호를 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
                                //Toast.makeText(getApplicationContext(), "Login fail : " + response, Toast.LENGTH_SHORT).show();
                            }  else {
                                try {

                                    Toast.makeText(PasswordChangeActivity.this, "비밀번호가 변경되었습니다", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                                    startActivity(intent);
                                    finish();
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
                            parameters.put("pw", input_pw);
                            parameters.put("user_id", user_id);
                            parameters.put("password", input_et_changePwd);
                            parameters.put("pw_change_check", pw_change_check);

                            return parameters;
                        }
                    };
                    requestQueue.add(stringRequest);

                }
            }
        });









        getSupportActionBar().hide();

    }
}