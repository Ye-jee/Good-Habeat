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

        recipe_view.setImageResource(R.drawable.healthy_diet_tomato_tteokbokki);

        recipe_name.setText("토마토 떡볶이");
        recipe_howto.setText("1. 토마토, 양배추, 파프리카, 브로콜리를 깨끗이 씻는다. \n2. 토마토는 꼭지를 떼고 8등분 한다."
                +"\n3. 양배추, 파프리카, 브로콜리는 먹기 좋은 크기로 썬다. \n4. 냄비에 양념을 넣고 끓으면 떡을 넣는다. "
                +"\n5. 양념이 졸아들면 토마토, 양배추, 파프리카, 브로콜리를 넣고 잘 섞는다.\n");
        recipe_ingredient.setText("토마토 150g\n쌀 떡볶이 120g\n양배추 30g\n파프리카 30g\n브로콜리 20g\n고추장 20g\n고춧가루 2g,\n설탕 7g\n물 300g");



    }

}