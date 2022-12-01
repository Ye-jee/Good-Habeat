package com.example.goodhabeat_view;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoryDiet_DetailsMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_diet_details_menu);

        //타이틀바 없애는 코드
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //리사이클러뷰 관련 코드
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.menuDetails_container);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        ArrayList<CategoryDiet_DetailMenuData> categoryDiet_detailMenuData = new ArrayList<>();

        int menuDetail_img[] = {R.drawable.menu_detail_ex1, R.drawable.menu_detail_ex2, R.drawable.menu_detail_ex3, R.drawable.menu_detail_ex4, R.drawable.menu_detail_ex5};
        String menuDetail_title[] = {"수수밥", "근대국", "삼치간장조림", "취나물볶음", "총각김치"};
        String menuDetail_informaiton[] = {"탄수화물 36g 단백질 3g 지방 1g", "탄수화물 5g 단백질 3g 지방 1g", "탄수화물 7g 단백질 22g 지방 7g", "탄수화물 4g 단백질 1g 지방 4g", "탄수화물 6g 단백질 1g 지방 0g"};
        String menuDetail_calories[] = {"168kcal", "47kcal", "196kcal", "61kcal", "29kcal"};

        for(int i = 0; i<menuDetail_title.length; i++) {
            CategoryDiet_DetailMenuData dataSet = new CategoryDiet_DetailMenuData(menuDetail_img[i], menuDetail_title[i], menuDetail_informaiton[i], menuDetail_calories[i]);
            categoryDiet_detailMenuData.add(dataSet);
        }

        //클릭 이벤트를 구현을 위한 추가코드
        CategoryDiet_DetailMenuData_RecyclerViewAdapter categoryDiet_detailMenuData_recyclerViewAdapter = new CategoryDiet_DetailMenuData_RecyclerViewAdapter(categoryDiet_detailMenuData);

        categoryDiet_detailMenuData_recyclerViewAdapter.setOnItemClickListener(new CategoryDiet_DetailMenuData_RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int postion, String data) {
                Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT).show();

                //이제 해당 아이템 메뉴 이름에 맞는 레시피 페이지로 넘어가야 함
            }
        });


        recyclerView.setAdapter(categoryDiet_detailMenuData_recyclerViewAdapter);


    }
}