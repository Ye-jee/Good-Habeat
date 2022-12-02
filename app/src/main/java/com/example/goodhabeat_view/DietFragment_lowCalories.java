package com.example.goodhabeat_view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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


public class DietFragment_lowCalories extends Fragment {

    int count_bind = 0;
    ArrayList<JSONObject> diet_item = new ArrayList<>();
    JSONArray categoryArray = new JSONArray();

    String diet_title;
    String diet_food;
    String diet_carbo;
    String diet_protein;
    String diet_fat;
    String diet_calories;
    String diet_disease;

    String menu_buf = "";
    Double carbo_buf = 0.0;
    Double protein_buf = 0.0;
    Double fat_buf = 0.0;
    Double cal_buf = 0.0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_diet_low_calories, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.lowCaloriesDiet_container);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ArrayList<CategoryDietData> category_diet_data = new ArrayList<>();

        String url = "http://10.0.2.2:3000/category_diet/low_calorie";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //System.out.println("저칼로리 response : " + response);

                try {
                    JSONArray resArray = new JSONArray(response);

                    for(int i=0; i<resArray.length(); i++) {
                        JSONObject dietObj = resArray.getJSONObject(i);
                        //System.out.println("dietObj[" + i + "]" + dietObj);

                        Integer diet_bind_id = dietObj.getInt("diet_bind_id");
                        if(diet_bind_id == count_bind+1) {
                            diet_item.add(dietObj);
                            //System.out.println("diet_item : " + diet_item);
                        }else {
                            // 현재 식단 배열에 넣고 다음 식단으로 넘어가기
                            JSONArray itemArray = new JSONArray(diet_item);
                            categoryArray.put(itemArray);

                            diet_item.clear();

                            count_bind++;
                            diet_item.add(dietObj);
                        }
                        // 마지막 식단
                        if(i == resArray.length()-2) {
                            JSONArray itemArray = new JSONArray(diet_item);
                            categoryArray.put(itemArray);
                            //System.out.println("categoryArray : " + categoryArray);

                            diet_item.clear();
                        }
                    }

                    //System.out.println("for문 밖으로 나옴 > categoryArray : " + categoryArray);

                    for(int i=0; i<categoryArray.length(); i++) {
                        JSONArray bindArray = categoryArray.getJSONArray(i);
                        //System.out.println("bindArray[" + i + "] : " + bindArray);

                        for(int j=0; j<bindArray.length(); j++) {
                            JSONObject bindObj = bindArray.getJSONObject(j);

                            // 식단 이름 (메인 메뉴)
                            Integer rep = bindObj.getInt("rep");
                            if(rep == 1) {
                                diet_title = bindObj.getString("menu_name");
                            }

                            // 식단 메뉴 (1)
                            String menu_name = bindObj.getString("menu_name");
                            menu_buf += (menu_name + " ");

                            // 영양 정보 (1)
                            Double calorie = bindObj.getDouble("calorie");
                            cal_buf += calorie;
                            Double carbohydrate = bindObj.getDouble("carbohydrate");
                            carbo_buf += carbohydrate;
                            Double protein = bindObj.getDouble("protein");
                            protein_buf += protein;
                            Double fat = bindObj.getDouble("fat");
                            fat_buf += fat;

                            // 질병
                            JSONObject bindObj0 = bindArray.getJSONObject(0);
                            diet_disease = bindObj0.getString("disease"); // 질병
                        }

                        // 식단 메뉴 (2)
                        diet_food = menu_buf;
                        menu_buf = "";

                        // 영양 정보 (2)
                        diet_carbo = String.format("%.1f", carbo_buf);
                        carbo_buf = 0.0;
                        diet_protein = String.format("%.1f", protein_buf);
                        protein_buf = 0.0;
                        diet_fat = String.format("%.1f", fat_buf);
                        fat_buf = 0.0;
                        diet_calories = String.format("%.1f", cal_buf);
                        cal_buf = 0.0;

                        CategoryDietData dataSet = new CategoryDietData(diet_title + " 식단", diet_food, diet_carbo + " g", diet_protein + " g", diet_fat + " g", diet_calories + " kcal", diet_disease);
                        category_diet_data.add(dataSet);
                    }

                    diet_food = "";

                    recyclerView.setAdapter(new com.example.goodhabeat_view.CategoryDietRecyclerViewAdapter(category_diet_data));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "ERROR : " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

        // 사용자 지정 정책 --> 타임아웃 에러 해결
        stringRequest.setRetryPolicy(new com.android.volley.DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue = Volley.newRequestQueue(getContext());
        if (requestQueue == null) {
            Volley.newRequestQueue(getContext());
        }

        stringRequest.setShouldCache(false); // 이전 결과가 있어도 새로 요청하여 응답을 보여줌
        requestQueue.add(stringRequest);

        return view;
    }
}