package com.example.goodhabeat_view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class DietAddActivity extends AppCompatActivity {
    Calendar calendar = Calendar.getInstance(); // datePickerDialog
    TextView dietAdd_selectDate;

    Double total_carbo = 0.0, total_protein = 0.0, total_fat = 0.0, total_calories = 0.0;
    TextView totalKcal_text, totalCarb_text, totalProtein_text, totalFat_text;

    SharedPreferences preferences;

    String menu_name, menu_calorie_str;
    Double menu_carbo, menu_protein, menu_fat;

    Button menuPlus_add; //음식추가 버튼
    Button add_completeBtn; //추가완료 버튼

    RadioGroup group_dayMeal;
    RadioButton add_bf, add_lc, add_dn;
    String meal = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_add);
        getSupportActionBar().hide();

        group_dayMeal = (RadioGroup) findViewById(R.id.group_dayMeal);
        add_bf = (RadioButton) findViewById(R.id.someday_breakfast);
        add_lc = (RadioButton) findViewById(R.id.someday_lunch);
        add_dn = (RadioButton) findViewById(R.id.someday_dinner);

        //생략


        // Intent
        Intent intent = getIntent();
        String check = intent.getStringExtra("check");

        //SharedPreference
        preferences = getApplicationContext().getSharedPreferences("selected_diet_rice", MODE_PRIVATE);
        String menu_rice = preferences.getString("selected_diet_rice","");
        System.out.println("rice preferences : " + menu_rice);

        preferences = getApplicationContext().getSharedPreferences("selected_diet_soup", MODE_PRIVATE);
        String menu_soup = preferences.getString("selected_diet_soup","");
        System.out.println("soup preferences : " + menu_soup);

        preferences = getApplicationContext().getSharedPreferences("selected_diet_kimchi", MODE_PRIVATE);
        String menu_kimchi = preferences.getString("selected_diet_kimchi","");
        System.out.println("kimchi preferences : " + menu_kimchi);

        preferences = getApplicationContext().getSharedPreferences("selected_diet_side", MODE_PRIVATE);
        String menu_side = preferences.getString("selected_diet_side","");
        System.out.println("side preferences : " + menu_side);

        preferences = getApplicationContext().getSharedPreferences("selected_diet_bowl", MODE_PRIVATE);
        String menu_bowl = preferences.getString("selected_diet_bowl","");
        System.out.println("bowl preferences : " + menu_bowl);

        ArrayList<String> prefArray = new ArrayList<>();
        if(menu_rice != null) {
            prefArray.add(menu_rice);   // index : 0, rice
        } if (menu_soup != null) {
            prefArray.add(menu_soup);   // index : 1, soup
        } if (menu_kimchi != null) {
            prefArray.add(menu_kimchi); // index : 2, kimchi
        } if (menu_side != null) {
            prefArray.add(menu_side);   // index : 3, side
        } if (menu_bowl != null) {
            prefArray.add(menu_bowl);   // index : 4, bowl
        }


        totalCarb_text = (TextView) findViewById(R.id.totalCarb_text);
        totalProtein_text = (TextView) findViewById(R.id.totalProt_text);
        totalFat_text = (TextView) findViewById(R.id.totalFat_text);
        totalKcal_text = (TextView) findViewById(R.id.totalKcal_text);


        // 날짜 선택
        dietAdd_selectDate = (TextView) findViewById(R.id.diet_add_select_date);
        DatePickerDialog datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                dietAdd_selectDate.setText(year + "." + (month+1) + "." + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePicker.getWindow().setBackgroundDrawableResource(R.drawable.round_rec_background);

        dietAdd_selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker.show();
            }
        });


        // 선택한 메뉴 세팅
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.dietAddList_container);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<DietChangeListData> diet_change_list_data = new ArrayList<>();

        if(check == null) {
            //
        } else if(check.equals("MENU SELECTED")) {
            // Volley
            String url = "http://10.0.2.2:3000/add_menu";
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    System.out.println("response : " + response);

                    try {
                        JSONArray resArray = new JSONArray(response);
                        //System.out.println("resArray : " + resArray);

                        for(int i=0; i<resArray.length(); i++) {
                            JSONArray menuArray = resArray.getJSONArray(i);
                            //System.out.println("menuArray[" + i + "] : " + menuArray);

                            for(int j=0; j<menuArray.length(); j++) {
                                JSONObject menuObj = menuArray.getJSONObject(j);
                                System.out.println("menuObj[" + i + "] : " + menuObj);

                                menu_name = menuObj.getString("menu_name");
                                menu_calorie_str = menuObj.getString("calorie");
                                menu_carbo = Double.parseDouble(menuObj.getString("carbohydrate"));

                                menu_protein = Double.parseDouble(menuObj.getString("protein"));
                                menu_fat = Double.parseDouble(menuObj.getString("fat"));

                                //System.out.println(i + "번째 메뉴 : " + menu_name);

                                DietChangeListData dataSet = new DietChangeListData(menu_name, menu_calorie_str + " kcal", menu_carbo, menu_protein, menu_fat, Double.parseDouble(menu_calorie_str));
                                diet_change_list_data.add(dataSet);

                                for(int k=0; k<diet_change_list_data.size(); k++) {
                                    total_carbo += diet_change_list_data.get(k).getCarbohydrate();
                                    total_protein += diet_change_list_data.get(k).getProtein();
                                    total_fat += diet_change_list_data.get(k).getFat();
                                    total_calories += diet_change_list_data.get(k).getCalorie();
                                }

                                totalCarb_text.setText(String.format("%.1f", total_carbo) + "g");
                                totalProtein_text.setText(String.format("%.1f", total_protein) + "g");
                                totalFat_text.setText(String.format("%.1f", total_fat) + "g");
                                totalKcal_text.setText(String.format("%.0f", total_calories) + "kcal");

                            }

                            // 데이터 리셋
                            SharedPreferences preferences1 = getApplicationContext().getSharedPreferences("selected_diet_rice", MODE_PRIVATE);
                            SharedPreferences preferences2 = getApplicationContext().getSharedPreferences("selected_diet_soup", MODE_PRIVATE);
                            SharedPreferences preferences3 = getApplicationContext().getSharedPreferences("selected_diet_kimchi", MODE_PRIVATE);
                            SharedPreferences preferences4 = getApplicationContext().getSharedPreferences("selected_diet_side", MODE_PRIVATE);
                            SharedPreferences preferences5 = getApplicationContext().getSharedPreferences("selected_diet_bowl", MODE_PRIVATE);
                            SharedPreferences.Editor editor1 = preferences1.edit();
                            SharedPreferences.Editor editor2 = preferences2.edit();
                            SharedPreferences.Editor editor3 = preferences3.edit();
                            SharedPreferences.Editor editor4 = preferences4.edit();
                            SharedPreferences.Editor editor5 = preferences5.edit();
                            editor1.clear(); editor1.commit();
                            editor2.clear(); editor2.commit();
                            editor3.clear(); editor3.commit();
                            editor4.clear(); editor4.commit();
                            editor5.clear(); editor5.commit();

                        }

                        recyclerView.setAdapter(new DietChangeListRecyclerViewAdapter(diet_change_list_data));

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
                    parameters.put("rice", menu_rice);
                    parameters.put("soup", menu_soup);
                    parameters.put("kimchi", menu_kimchi);
                    parameters.put("side", menu_side);
                    parameters.put("bowl", menu_bowl);
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


        //음식 추가 버튼 관련
        menuPlus_add = (Button) findViewById(R.id.menu_plus_add);
        menuPlus_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MenuSelectActivity.class);
                startActivity(intent);
            }
        });

        // meal 라디오 버튼
        group_dayMeal.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton meal_name = radioGroup.findViewById(i);
                if(meal_name.getText().toString().equals("아침")) {
                    meal = "1";
                } else if(meal_name.getText().toString().equals("점심")) {
                    meal = "2";
                } else if(meal_name.getText().toString().equals("저녁")) {
                    meal = "3";
                }
            }
        });

        //추가완료 버튼을 누르면, 메뉴가 바뀐 오늘의 식단으로 이동
        add_completeBtn = (Button) findViewById(R.id.menu_add_complete);
        add_completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 선택된 날짜
                String diet_date = dietAdd_selectDate.getText().toString();
                System.out.println("diet_date : " + diet_date);

                for(int i=0; i<prefArray.size(); i++) {
                    System.out.println("prefArray(" + i + ")" + prefArray.get(i));
                    AddDietVolley(prefArray.get(i), diet_date, meal);
                }

                Toast.makeText(DietAddActivity.this, "식단이 저장되었습니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MenuDayActivity.class);
                startActivity(intent);
            }
        });

    }

    public void AddDietVolley(String recipe_id, String diet_date, String meal) {
        // SharedPreference 사용자 아이디
        preferences = getApplicationContext().getSharedPreferences("userInfo", MODE_PRIVATE);
        String user_id = preferences.getString("user_id","");
        System.out.println("user_id : " + user_id);

        // Volley
        String url = "http://10.0.2.2:3000/add_diet";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("response : " + response);
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
                parameters.put("recipe_id", recipe_id);
                parameters.put("user_id", user_id);
                parameters.put("diet_date", diet_date);
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

}