package com.example.goodhabeat_view;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_add);

        //우선 식단 수정 액티비티 코드를 그대로 가져옴!!
        getSupportActionBar().hide();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.dietChangeList_container);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<DietChangeListData> diet_change_list_data = new ArrayList<>();

        String dietChange_menu_name[] = {"옥수수밥", "조기구이", "된장찌개"};
        String dietChange_menu_kcal[] = {"300kcal", "318kcal", "145kcal"};

        for(int i = 0; i< dietChange_menu_name.length; i++){
            DietChangeListData dataSet = new DietChangeListData(dietChange_menu_name[i], dietChange_menu_kcal[i]);
            diet_change_list_data.add(dataSet);
        }

        recyclerView.setAdapter(new com.example.goodhabeat_view.DietChangeListRecyclerViewAdapter(diet_change_list_data));


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