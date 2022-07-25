package com.example.goodhabeat_view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class RecipeActivity extends AppCompatActivity {
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
        recipe_view = (ImageView) findViewById(R.id.recipe_view);

        recipe_view.setImageResource(R.drawable.berry_yogurt);

        recipe_name.setText("리얼 베리 요거트");
        recipe_howto.setText("1. 딸기를 잘게 잘라준다. \n2. 무가당 요거트 100ml를 유리컵에 부어준다."
                +"\n3. 잘게 자른 딸기와 블루베리를 위에 함께 얹어준다.(다른 견과류나 씨리얼을 더 추가해도 괜찮다) \n4. 그 위에 다시 요거트 100ml를 부어준다. "
                +"\n6. 당도를 조절하게 위해 원하는 만큼 딸기 콩포트(딸기청)를 올려준다.\n");
        recipe_ingredient.setText("딸기 5개\n블루베리 10개\n딸기 콩포트 또는 딸기청 10g(3숟갈 정도)\n무가당 요거트 200ml");



    }

}