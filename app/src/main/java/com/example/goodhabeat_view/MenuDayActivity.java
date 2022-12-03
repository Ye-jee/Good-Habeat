package com.example.goodhabeat_view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
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
import com.cloudinary.android.MediaManager;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MenuDayActivity extends AppCompatActivity {

    public static String todayDateFormat = "yyyy년 MM월 dd일";

    RadioGroup group_dayMeal;
    RadioButton today_bf, today_lc, today_dn;
    String meal = "1";

    SharedPreferences preferences;

    //오늘 날짜
    TextView todayDate;

    //식단 수정, 추가 버튼
    Button diet_modifyBtn;
    Button diet_addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_day);

        getSupportActionBar().hide();

        todayDate = (TextView) findViewById(R.id.todayDate);

        group_dayMeal = (RadioGroup) findViewById(R.id.group_dayMeal);
        today_bf = (RadioButton) findViewById(R.id.someday_breakfast);
        today_lc = (RadioButton) findViewById(R.id.someday_lunch);
        today_dn = (RadioButton) findViewById(R.id.someday_dinner);

        diet_modifyBtn = (Button) findViewById(R.id.diet_modifyBtn);    // 식단 수정 버튼
        diet_addBtn = (Button) findViewById(R.id.diet_addBtn);          //식단 추가 버튼

        // 초기 화면 (아침 식단)
        VolleySelectMeal(meal);


        // meal 라디오 버튼
        group_dayMeal.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton meal_name = radioGroup.findViewById(i);
                if(meal_name.getText().toString().equals("아침")) {
                    meal = "1";
                    //Toast.makeText(MenuDayActivity.this, "아침 클릭", Toast.LENGTH_SHORT).show();
                    VolleySelectMeal(meal);
                } else if(meal_name.getText().toString().equals("점심")) {
                    meal = "2";
                    //Toast.makeText(MenuDayActivity.this, "점심 클릭", Toast.LENGTH_SHORT).show();
                    VolleySelectMeal(meal);
                } else if(meal_name.getText().toString().equals("저녁")) {
                    meal = "3";
                    //Toast.makeText(MenuDayActivity.this, "저녁 클릭", Toast.LENGTH_SHORT).show();
                    VolleySelectMeal(meal);
                }
            }
        });

        // 오늘 날짜 출력
        todayDate.setText(getCurrentDate());

        // 식단 수정 페이지로 이동
        diet_modifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DietChangeActivity.class);
                startActivity(intent);
            }
        });

        //식단 추가 페이지로 이동
        diet_addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DietAddActivity.class);
                startActivity(intent);
            }
        });

    }

    // 외부 함수
    public void VolleySelectMeal(String meal) {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.menuDay_container);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        ArrayList<MenuDayData> menu_day_data = new ArrayList<>();

        //SharedPreference
        preferences = getApplicationContext().getSharedPreferences("userInfo", MODE_PRIVATE);
        String userId = preferences.getString("user_id","");

        // Volley
        String url = "http://10.0.2.2:3000/today_diet";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //System.out.println("/today_diet response : " + response);
                //System.out.println("---------------------");

                try {
                    JSONArray resArray = new JSONArray(response);

                    for(int i=0; i<resArray.length(); i++) {
                        JSONObject dietObj = resArray.getJSONObject(i);
                        //System.out.println("dietObj[" + i + "] : " + dietObj);

                        String recipe_image = dietObj.getString("recipe_image");
                        String menu_name = dietObj.getString("menu_name");
                        String calorie = dietObj.getString("calorie");
                        String carbo = dietObj.getString("carbohydrate");
                        String protein = dietObj.getString("protein");
                        String fat = dietObj.getString("fat");
                        String intent_meal = meal;

                        MenuDayData dataSet = new MenuDayData(recipe_image, menu_name, calorie + "Kcal", "탄수화물 " + carbo + "g ", "단백질 " + protein + "g ", "지방 " + fat + "g", intent_meal);
                        menu_day_data.add(dataSet);
                    }

                    recyclerView.setAdapter(new MenuDayRecyclerViewAdapter(getApplicationContext(), menu_day_data));

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
                parameters.put("user_id", userId);
                parameters.put("meal", meal);
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

    // 오늘 날짜 출력
    public static String getCurrentDate() {
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat(todayDateFormat, Locale.getDefault());
        return format.format(currentTime);
    }

}