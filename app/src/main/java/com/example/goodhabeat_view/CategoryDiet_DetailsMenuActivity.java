package com.example.goodhabeat_view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CategoryDiet_DetailsMenuActivity extends AppCompatActivity {

    TextView tvDietTitle, tvDietInfo, tvDietCalorie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_diet_details_menu);

        tvDietTitle = (TextView) findViewById(R.id.categoryDiet);
        tvDietInfo = (TextView) findViewById(R.id.diet_Information);
        tvDietCalorie = (TextView) findViewById(R.id.diet_Calories);

        Intent intent = getIntent();
        String diet_title = intent.getStringExtra("diet_title");
        String diet_info = intent.getStringExtra("diet_info");
        String diet_calorie = intent.getStringExtra("diet_calorie");

        tvDietTitle.setText(diet_title);
        tvDietInfo.setText(diet_info);
        tvDietCalorie.setText("총 " + diet_calorie);

        // Volley에 사용
        String diet_category = intent.getStringExtra("diet_category");
        Integer diet_bind_id = intent.getIntExtra("diet_bind_id", 0);

        //타이틀바 없애는 코드
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //리사이클러뷰 관련 코드
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.menuDetails_container);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        ArrayList<CategoryDiet_DetailMenuData> categoryDiet_detailMenuData = new ArrayList<>();

        // Volley
        String url = "http://10.0.2.2:3000/category_diet/diet_menu";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray resArray = new JSONArray(response);

                    for(int i=0; i<resArray.length(); i++) {
                        JSONObject dietObj = resArray.getJSONObject(i);

                        String menuDetail_img = dietObj.getString("recipe_image");
                        String menuDetail_title = dietObj.getString("menu_name");
                        String carbo = dietObj.getString("carbohydrate");
                        String protein = dietObj.getString("protein");
                        String fat = dietObj.getString("fat");
                        String menuDetail_informaiton = "탄수화물 " + carbo + "g " + "단백질 " + protein + "g " + "지방 " + fat + "g ";
                        String menuDetail_calories = dietObj.getString("calorie");

                        CategoryDiet_DetailMenuData dataSet = new CategoryDiet_DetailMenuData(menuDetail_img, menuDetail_title, menuDetail_informaiton, "총 " + menuDetail_calories + " kcal");
                        categoryDiet_detailMenuData.add(dataSet);
                    }

                    recyclerView.setAdapter(new CategoryDiet_DetailMenuData_RecyclerViewAdapter(getApplicationContext(), categoryDiet_detailMenuData));

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
                parameters.put("category", diet_category);
                parameters.put("diet_bind_id", diet_bind_id.toString());
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