package com.example.goodhabeat_view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class IntroActivity extends AppCompatActivity {


    SharedPreferences preferences;

    //인트로 액티비티 알파벳 변경함 -> intro에서 Intro로!



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        
        getSupportActionBar().hide();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // SharedPreference
                preferences = getApplicationContext().getSharedPreferences("userInfo", MODE_PRIVATE);
                String userName = preferences.getString("nickname","");
                System.out.println("preferences : " + userName);

                if (!(userName.equals(""))) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class); //화면 전환
                    startActivity(intent);
                    finish();
                }
            }
        }, 3000); //딜레이 타임 조절
    }
}