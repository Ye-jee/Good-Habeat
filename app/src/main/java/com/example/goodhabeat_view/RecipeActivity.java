package com.example.goodhabeat_view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecipeActivity extends AppCompatActivity {

    TextView recipe_name, recipe_howto, recipe_ingredient;
    ImageView recipe_view;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        String menuName = intent.getStringExtra("menuName");
        String foodName = intent.getStringExtra("foodName");
        //System.out.println("getStringExtra test : " + menuName);
        //System.out.println("---------------------");

        recipe_name = (TextView) findViewById(R.id.recipe_name);
        recipe_ingredient = (TextView) findViewById(R.id.recipe_ingredient);
        recipe_howto= (TextView) findViewById(R.id.recipe_howto);
        recipe_view = (ImageView) findViewById(R.id.user_profilePic);

        // Volley
        if(menuName != null) {
            String url = "http://10.0.2.2:3000/today_diet/recipe";
            VolleyRecipeUrl(url, menuName);
        } else if(foodName != null) {
            String url = "http://10.0.2.2:3000/season_food/recipe";
            VolleyRecipeUrl(url, foodName);
        }

    }

    // 외부 함수
    public void VolleyRecipeUrl(String url, String name) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //System.out.println("/recipe response : " + response);
                //System.out.println("---------------------");

                try {
                    JSONArray resArray = new JSONArray(response);

                    for(int i=0; i<resArray.length(); i++) {
                        JSONObject recipeObj = resArray.getJSONObject(i);

                        String menu_name = recipeObj.getString("menu_name");
                        String recipe_image = recipeObj.getString("recipe_image");
                        String ingredient = recipeObj.getString("ingredient");
                        String recipe_description = recipeObj.getString("recipe_description");

                        recipe_name.setText(menu_name);
                        setURLImage(recipe_image); // 이미지 세팅

                        // 구분자로 문자열 분리
                        String[] str_ingredient = ingredient.split("@");
                        for(String s : str_ingredient) { recipe_ingredient.append(s + "\n"); }

                        String[] str_description = recipe_description.split("@");
                        for(String s : str_description) { recipe_howto.append(s + "\n\n"); }

                    }

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
                parameters.put("menu_name", name);
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

    // 외부 함수
    private void setURLImage(String recipe_image) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    bitmap = getBitmap(recipe_image);
                }catch (Exception e){
                    e.printStackTrace();
                } finally {
                    if(bitmap != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                recipe_view.setImageBitmap(bitmap);
                            }
                        });
                    }
                }
            }
        }).start();
    }

    private Bitmap getBitmap(String url) {
        URL imgUrl = null;
        HttpURLConnection connection = null;
        InputStream is = null;

        Bitmap retBitmap = null;

        try{
            imgUrl = new URL(url);
            connection = (HttpURLConnection) imgUrl.openConnection();
            connection.setDoInput(true); //url로 input받는 flag 허용
            connection.connect(); //연결
            is = connection.getInputStream();
            retBitmap = BitmapFactory.decodeStream(is);

        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            if(connection!=null) {
                connection.disconnect();
            }
            return retBitmap;
        }
    }

}