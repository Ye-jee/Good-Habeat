package com.example.goodhabeat_view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
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

public class MenuFragment_soup extends Fragment {

    private SharedViewModel viewModel;

    Button selectCompleteBtn;
    ArrayList<SelectedMenuItemData> selected_menu = new ArrayList<>();
    ArrayList<Integer> selected_item_id = new ArrayList<>();

    SharedPreferences preferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_soup, container, false);

        selectCompleteBtn = (Button) view.findViewById(R.id.selectCompleteBtn);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.soupMenu_container);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ArrayList<com.example.goodhabeat_view.MenuSelectData> menu_select_data = new ArrayList<>();

        // Volley
        String url = "http://10.0.2.2:3000/menu_select";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);

                try {
                    JSONArray resArray = new JSONArray(response);

                    for(int i=0; i<resArray.length(); i++) {
                        JSONObject dietObj = resArray.getJSONObject(i);

                        String menu_image = dietObj.getString("recipe_image");
                        String menu_name = dietObj.getString("menu_name");
                        String carbo = dietObj.getString("carbohydrate");
                        String protein = dietObj.getString("protein");
                        String fat = dietObj.getString("fat");
                        String menu_info = "탄수화물 " + carbo + "g " + "단백질 " + protein + "g " + "지방 " + fat + "g ";
                        String menu_calorie = dietObj.getString("calorie");
                        Integer recipe_id = dietObj.getInt("recipe_id");

                        MenuSelectData dataSet = new MenuSelectData(menu_image, menu_name, Double.parseDouble(carbo), Double.parseDouble(protein), Double.parseDouble(fat), menu_info, Double.parseDouble(menu_calorie), recipe_id);
                        menu_select_data.add(dataSet);
                    }

                    recyclerView.setAdapter(new MenuSelectRecyclerViewAdapter(getContext(), menu_select_data, selected_menu));

                }catch (Exception e){ e.printStackTrace(); }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "ERROR : " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        ){
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<>();
                parameters.put("category", "2");
                return parameters;
            }
        };

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

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        // 선택 완료 버튼
        selectCompleteBtn.setOnClickListener(item -> {
            SelectedMenuItemData soupItem = new SelectedMenuItemData(0, 0.0, 0.0, 0.0, 0.0);
            //if(selected_menu != null) selected_menu.clear();


            for(int i=0; i<selected_menu.size(); i++){
                selected_item_id.add(selected_menu.get(i).getItem_index());
                System.out.println("soup 데이터 저장 : " + selected_menu.get(i).getItem_index());
            }

            if(selected_menu.size() == 1) {
                preferences = getContext().getSharedPreferences("selected_diet_soup", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("selected_diet_soup", selected_menu.get(0).getItem_index().toString());
                editor.commit();

                //Intent intent = new Intent(getContext(), DietAddActivity.class);
                //intent.putExtra("send_data", selected_menu.toString());
                //startActivity(intent);
            } else if (selected_menu.size() > 1) {
                preferences = getContext().getSharedPreferences("selected_diet_soup", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();
                for (int i=0; i<selected_menu.size()-1; i++) {
                    selected_menu.remove(i);
                    if(selected_menu.size() >= 1) {
                        editor.putString("selected_diet_soup", selected_menu.get(selected_menu.size()-1).getItem_index().toString());
                        editor.commit();
                    }
                }
            }


            /*
            System.out.println("-----------------------------------");
            soupItem.setSoupItemData(selected_item_id);
            viewModel.select(soupItem);*/
        });

        // 다른 프래그먼트 데이터 가져오기
        viewModel.getSelected().observe(getViewLifecycleOwner(), item -> {
            //System.out.println("soup fragment : " + item.getRiceItemData());
            System.out.println("-----------------------------------");
            //Toast.makeText(getContext(), "soup fragment : " + item.getItem_index(), Toast.LENGTH_SHORT).show();
        });

    }
}