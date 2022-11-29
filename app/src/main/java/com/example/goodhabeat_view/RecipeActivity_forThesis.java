package com.example.goodhabeat_view;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RecipeActivity_forThesis extends AppCompatActivity {
    TextView recipe_name, recipe_howto, recipe_ingredient;
    ImageView recipe_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        getSupportActionBar().hide();

        recipe_name = (TextView) findViewById(R.id.recipe_name);
        recipe_howto= (TextView) findViewById(R.id.recipe_howto);
        recipe_ingredient = (TextView) findViewById(R.id.recipe_ingredient);
        recipe_view = (ImageView) findViewById(R.id.user_profilePic);

        //recipe_view.setImageResource(R.drawable.healthy_diet_tomato_tteokbokki);

        /*recipe_name.setText("토마토 떡볶이");
        recipe_howto.setText("1. 토마토, 양배추, 파프리카, 브로콜리를 깨끗이 씻는다. \n2. 토마토는 꼭지를 떼고 8등분 한다."
                +"\n3. 양배추, 파프리카, 브로콜리는 먹기 좋은 크기로 썬다. \n4. 냄비에 양념을 넣고 끓으면 떡을 넣는다. "
                +"\n5. 양념이 졸아들면 토마토, 양배추, 파프리카, 브로콜리를 넣고 잘 섞는다.\n");
        recipe_ingredient.setText("토마토 150g\n쌀 떡볶이 120g\n양배추 30g\n파프리카 30g\n브로콜리 20g\n고추장 20g\n고춧가루 2g,\n설탕 7g\n물 300g");*/



        //전시폼보드 때의 레시피 화면
        recipe_view.setImageResource(R.drawable.menu_detail_ex2);

        recipe_name.setText("근대국");
        recipe_ingredient.setText("근대 400g\n청고추 2개\n홍고추 반 개\n양파 30g\n멸치육수 7컵\n된장 2큰술\n소금 약간\n다진마늘 작은 1큰술");

        recipe_howto.setText("1. 근대를 흐르는 물에 씻어 물기를 빼고 먹기 좋은 크기로 자른다\n2. 양파를 채썬다."
                +"\n3. 청고추와 홍고추도 마찬가지로 채썬다. \n4. 멸치육수에 된장 2큰술을 풀어준다 "
                +"\n5. 다진 마늘, 근대, 청고추, 홍고추를 넣고 중약불에서 10분 정도 끓인다.\n");



    }

}