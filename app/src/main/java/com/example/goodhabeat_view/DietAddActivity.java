package com.example.goodhabeat_view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class DietAddActivity extends AppCompatActivity {
    Calendar calendar = Calendar.getInstance(); // datePickerDialog
    TextView dietAdd_selectDate;

    Double total_carbo = 0.0, total_protein = 0.0, total_fat = 0.0, total_calories = 0.0;
    TextView totalKcal_text, totalCarb_text, totalProtein_text, totalFat_text;

    String menu_name, menu_calorie;

    Button menuPlus_add; //음식추가 버튼
    Button add_completeBtn; //추가완료 버튼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_add);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        String check = intent.getStringExtra("check");

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

            // 선택한 메뉴의 recipe_id 가져오기
            ArrayList<SelectedMenuItemData> selected_menu = (ArrayList<SelectedMenuItemData>) intent.getSerializableExtra("selected_menu");

            // 선택한 메뉴의 총 탄수화물, 단백질, 지방
            for(int i=0; i<selected_menu.size(); i++) {
                total_carbo += selected_menu.get(i).getCarbohydrate();
                total_protein += selected_menu.get(i).getProtein();
                total_fat += selected_menu.get(i).getFat();
                total_calories += selected_menu.get(i).getCalorie();
            }
            totalCarb_text.setText(String.format("%.1f", total_carbo));
            totalProtein_text.setText(String.format("%.1f", total_protein));
            totalFat_text.setText(String.format("%.1f", total_fat));
            totalKcal_text.setText(String.format("%.1f", total_calories));

            // 서버로 전송할 파라미터 배열
            JSONArray paramArray = new JSONArray();
            for(int i=0; i<selected_menu.size(); i++) {
                paramArray.put(selected_menu.get(i).getItem_index());
            }

            String url = "http://10.0.2.2:3000/add_menu";
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //System.out.println("response : " + response);

                    try {
                        JSONArray resArray = new JSONArray(response);
                        //System.out.println("resArray : " + resArray);

                        for(int i=0; i<resArray.length(); i++) {
                            JSONObject menuObj = resArray.getJSONObject(i);
                            //System.out.println("menuObj[" + i + "] : " + menuObj);

                            menu_name = menuObj.getString("menu_name");
                            menu_calorie = menuObj.getString("calorie");

                            DietChangeListData dataSet = new DietChangeListData(menu_name, menu_calorie + " kcal");
                            diet_change_list_data.add(dataSet);

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
                    parameters.put("recipe_array", paramArray.toString());
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

        //추가완료 버튼을 누르면, 메뉴가 바뀐 오늘의 식단으로 이동
        add_completeBtn = (Button) findViewById(R.id.menu_add_complete);
        add_completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MenuDayActivity.class);
                startActivity(intent);
            }
        });

    }

}