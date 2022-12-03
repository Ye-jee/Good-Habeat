package com.example.goodhabeat_view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

public class DietAddActivity extends AppCompatActivity {

    //생년월일 관련
    Calendar calendar = Calendar.getInstance(); // datePickerDialog
    TextView dietAdd_selectDate;

    //음식추가 버튼
    Button menuPlus_add;

    //추가완료 버튼
    Button add_completeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_add);

        //우선 식단 수정 액티비티 코드를 그대로 가져옴!!

        getSupportActionBar().hide();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.dietAddList_container);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<DietChangeListData> diet_change_list_data = new ArrayList<>();

        /*String dietChange_menu_name[] = {"옥수수밥", "조기구이", "된장찌개"};
        String dietChange_menu_kcal[] = {"300kcal", "318kcal", "145kcal"};*/

        String dietChange_menu_name[] = {"현미밥", "아욱된장국", "계란말이"};
        String dietChange_menu_kcal[] = {"153kcal", "34kcal", "134kcal"};

        for(int i = 0; i< dietChange_menu_name.length; i++){
            DietChangeListData dataSet = new DietChangeListData(dietChange_menu_name[i], dietChange_menu_kcal[i]);
            diet_change_list_data.add(dataSet);
        }

        //recyclerView.setAdapter(new DietChangeListRecyclerViewAdapter(diet_change_list_data));
        //13초 뒤에 나타나게 함 - 날짜를 선택해야 해서!!
        //아님 그냥 아이템들이 안 나타나게 할 것
        /*Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerView.setAdapter(new DietChangeListRecyclerViewAdapter(diet_change_list_data));
            }
        }, 13000);*/ //딜레이 타임 조절


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



        // 생년월일 입력
        dietAdd_selectDate = (TextView) findViewById(R.id.diet_add_select_date);

        DatePickerDialog birthPicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                dietAdd_selectDate.setText(year + "." + (month+1) + "." + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        birthPicker.getWindow().setBackgroundDrawableResource(R.drawable.round_rec_background);

        dietAdd_selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                birthPicker.show();
            }
        });


    }
}