package com.example.goodhabeat_view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class ChallengeRecommandDietActivity extends AppCompatActivity {

    CheckBox cb_breakfast, cb_lunch, cb_dinner,
            cb_single, cb_weight, cb_sugar;

    /*EditText et_CurrentWeight, et_TargetWeight;*/

    RadioGroup rg_sugarFrequency;
    RadioButton rb_zeroOne, rb_twoFour, rb_fiveSeven, rb_Ten;

    Button btn_recmdCustomDiet;

    int eatingNumber;
    int single, lowcal, lowsalt, highcal, lowsugar, lowfat = 0;

    //체중조절 다이얼로그
    View dialogView_hw;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_recommand_diet);

        cb_breakfast = (CheckBox) findViewById(R.id.cb_breakfast);
        cb_lunch = (CheckBox) findViewById(R.id.cb_lunch);
        cb_dinner = (CheckBox) findViewById(R.id.cb_dinner);
        cb_single = (CheckBox) findViewById(R.id.cb_single);
        cb_weight = (CheckBox) findViewById(R.id.cb_weight);
        cb_sugar = (CheckBox) findViewById(R.id.cb_sugar);

        /*et_CurrentWeight = (EditText) findViewById(R.id.et_currentWeight);
        et_TargetWeight = (EditText) findViewById(R.id.et_targetWeight);*/

        rg_sugarFrequency = (RadioGroup) findViewById(R.id.rg_sugarFrequency);
        rb_zeroOne = (RadioButton) findViewById(R.id.rb_zeroOne);
        rb_twoFour = (RadioButton) findViewById(R.id.rb_twoFour);
        rb_fiveSeven = (RadioButton) findViewById(R.id.rb_fiveSeven);
        rb_Ten = (RadioButton) findViewById(R.id.rb_Ten);

        btn_recmdCustomDiet = (Button) findViewById(R.id.btn_recmdCustomDiet);

        //String url = "http://192.168.56.1:8000/AndroidAppEx/capstoneEx/challenge_recmdCustomDietEx.jsp";


        //타이틀바 없애는 코드
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        //맞춤 식단 설문조사 결과를 보여주는 페이지로 이동
        btn_recmdCustomDiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*Toast.makeText(getApplicationContext(), "맞춤 식단 관련 버튼을 클릭!!!하셨어요!!!! \n하루 식사 획수 - " + eatingNumber +
                        "\n간편식 - " + single + "\n저칼로리 - " + lowcal + "\n저염 - " + lowsalt + "\n고칼로리 - " + highcal +  "\n저당 - " + lowsugar, Toast.LENGTH_LONG).show();*/

                /*String url2 = url
                        + "?mealFrequency=" + eatingNumber
                        + "&convenience=" + single
                        + "&lowCalories=" + lowcal
                        + "&lowSalt=" + lowsalt
                        + "&highCalories=" + highcal
                        + "&lowSugar=" + lowsugar
                        + "&lowFat=" + lowfat
                        + "&userFkid=1";

                requestQueue = Volley.newRequestQueue(getApplicationContext());

                StringRequest stringRequest = new StringRequest(Request.Method.GET,
                        url2,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(getApplicationContext(), "맞춤 식단 정보 저장 완료!\n" + response, Toast.LENGTH_SHORT).show();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), "에러 원인 :" + error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });

                requestQueue.add(stringRequest);*/

                //CustomToast("맞춤 식단 설정이 완료되었습니다.");

                Intent intent = new Intent(getApplicationContext(), ChallengeRecommandDietActivity2.class);
                startActivity(intent);

            }
        });


    }

    public void CustomToast(String message){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup)findViewById(R.id.toast_layout));
        TextView toast_textview  = layout.findViewById(R.id.toast_textview);
        toast_textview.setText(String.valueOf(message));
        Toast toast = new Toast(getApplicationContext());
        //toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);  //TODO 메시지가 표시되는 위치지정 (가운데 표시)
        //toast.setGravity(Gravity.TOP, 0, 0);              //TODO 메시지가 표시되는 위치지정 (상단 표시)
        toast.setGravity(Gravity.BOTTOM, 0, 270);           //TODO 메시지가 표시되는 위치지정 (하단 표시)
        toast.setDuration(Toast.LENGTH_SHORT); //메시지 표시 시간
        toast.setView(layout);
        toast.show();
    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();        // Is the view now checked?

        //하루 식사 횟수
        if(view.getId() == R.id.cb_breakfast) {
            if(checked) {
                eatingNumber = 1;
                //Toast.makeText(getApplicationContext(), "하루 식사 횟수 : " + eatingNumber + "번", Toast.LENGTH_SHORT).show();
            }
            else {
                eatingNumber--;
                //Toast.makeText(getApplicationContext(), "하루 식사 횟수 : " + eatingNumber + "번", Toast.LENGTH_SHORT).show();
            }
        }

        if(view.getId() == R.id.cb_lunch) {
            if(checked) {
                eatingNumber++;
                //Toast.makeText(getApplicationContext(), "하루 식사 횟수 : " + eatingNumber + "번", Toast.LENGTH_SHORT).show();
            }
            else {
                eatingNumber--;
                //Toast.makeText(getApplicationContext(), "하루 식사 횟수 : " + eatingNumber + "번", Toast.LENGTH_SHORT).show();
            }
        }

        if(view.getId() == R.id.cb_dinner) {
            if(checked) {
                eatingNumber++;
                //Toast.makeText(getApplicationContext(), "하루 식사 횟수 : " + eatingNumber + "번", Toast.LENGTH_SHORT).show();
            }
            else {
                eatingNumber--;
                //Toast.makeText(getApplicationContext(), "하루 식사 횟수 : " + eatingNumber + "번", Toast.LENGTH_SHORT).show();
            }
        }


        //1인 가구
        if(view.getId() == R.id.cb_single) {
            if(checked) {
                single = 1;
                //Toast.makeText(getApplicationContext(), "간편식 : " + single, Toast.LENGTH_SHORT).show();
            }
            else {
                single = 0;
                //Toast.makeText(getApplicationContext(), "간편식 : " + single, Toast.LENGTH_SHORT).show();
            }
        }


        //체중조절 관련 - 감량, 증량
        if(view.getId() == R.id.cb_weight) {
            if(checked) {

                dialogView_hw = (View) View.inflate(ChallengeRecommandDietActivity.this, R.layout.challenge_hw_dialog, null);

                AlertDialog.Builder alertDialog_hw = new AlertDialog.Builder(ChallengeRecommandDietActivity.this);

                //hwDialog.setTitle("현재 몸무게와 목표 몸무게를 입력하세요.");

                alertDialog_hw.setView(dialogView_hw);

                alertDialog_hw.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText et_currentWeight = (EditText) dialogView_hw.findViewById(R.id.et_currentWeight);
                        EditText et_targetWeight = (EditText) dialogView_hw.findViewById(R.id.et_targetWeight);

                        if(et_currentWeight.getText().toString().equals("") || et_targetWeight.getText().toString().equals("")) {
                            Toast.makeText(getApplicationContext(), "현재 몸무게와 목표 몸무게를 입력해주세요!!", Toast.LENGTH_SHORT).show();
                            cb_weight.setChecked(false);
                        }
                        else {
                            int current = Integer.parseInt(et_currentWeight.getText().toString());
                            int target = Integer.parseInt(et_targetWeight.getText().toString());
                            int value = target - current;

                            if(value <= 0) {
                                lowcal = 1;
                                lowsalt = 1;
                                highcal = 0;
                                Toast.makeText(getApplicationContext(), "저칼로리 : " + lowcal + "\n저염 : " + lowsalt, Toast.LENGTH_SHORT).show();
                            }
                            else {
                                highcal = 1;
                                lowcal = 0;
                                lowsalt = 0;
                                Toast.makeText(getApplicationContext(), "고칼로리 : " + highcal, Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                });

                alertDialog_hw.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //hwDialog.dismiss(); - 코드 오류남, 이유는 AlertDialog.Builder에는 해당함수가 없기 때문에
                        cb_weight.setChecked(false);
                    }
                });

                //alertDialog_hw.getWindow().setBackgroundDrawableResource(R.drawable.round_rec_background);

                alertDialog_hw.show();

                /*if(et_CurrentWeight.getText().toString().equals("") || et_TargetWeight.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "현재 몸무게와 목표 몸무게를 먼저 입력해주세요!!", Toast.LENGTH_SHORT).show();
                    cb_weight.setChecked(false);
                }
                else {
                    int current = Integer.parseInt(et_CurrentWeight.getText().toString());
                    int target = Integer.parseInt(et_TargetWeight.getText().toString());
                    int value = target - current;

                    if(value <= 0) {
                        lowcal = 1;
                        lowsalt = 1;
                        highcal = 0;
                        //Toast.makeText(getApplicationContext(), "저칼로리 : " + lowcal + "\n저염 : " + lowsalt, Toast.LENGTH_SHORT).show();
                    }
                    else {
                        highcal = 1;
                        lowcal = 0;
                        lowsalt = 0;
                        //Toast.makeText(getApplicationContext(), "고칼로리 : " + highcal, Toast.LENGTH_SHORT).show();
                    }

                    //Toast.makeText(getApplicationContext(), "체중조절에 관심이 있습니다!", Toast.LENGTH_SHORT).show();
                }*/
            }

            else {
                lowcal = 0;
                lowsalt = 0;
                highcal = 0;
                Toast.makeText(getApplicationContext(), "체중조절에 관심이 없습니다...", Toast.LENGTH_SHORT).show();
            }
        }


        //단 음식, 저당
        if(view.getId() == R.id.cb_sugar) {
            if(checked) {
                //Toast.makeText(getApplicationContext(), "단 음식을 자주 먹습니다...", Toast.LENGTH_SHORT).show();

                rg_sugarFrequency.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        RadioButton rbName = radioGroup.findViewById(i);

                        if(rbName.getText().toString().equals("0~1번")) {
                            lowsugar = 0;
                            //Toast.makeText(getApplicationContext(), "적당히 섭취하시는군요! \n저당 : " + lowsugar , Toast.LENGTH_SHORT).show();
                        }
                        else if(rbName.getText().toString().equals("2~4번")) {
                            lowsugar = 0;
                            //Toast.makeText(getApplicationContext(), "어느 정도 섭취하시는군요! \n저당 : " + lowsugar, Toast.LENGTH_SHORT).show();
                        }
                        else if(rbName.getText().toString().equals("5~7번")) {
                            lowsugar = 1;
                            //Toast.makeText(getApplicationContext(), "섭취 빈도를 줄여야 될 것 같아요 \n저당 : " + lowsugar, Toast.LENGTH_SHORT).show();
                        }
                        else if(rbName.getText().toString().equals("10번 이상")) {
                            lowsugar = 1;
                            //Toast.makeText(getApplicationContext(), "무조건 섭취 빈도를 줄여야 될 것 같아요! \n저당 식단을 추천드립니다. \n저당 : " + lowsugar, Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
            else {      //오류 발생...
                //rg_sugarFrequency.check(-1);
                //rg_sugarFrequency.clearCheck();
                lowsugar = 0;
                //Toast.makeText(getApplicationContext(), "단 음식을 자주 먹지 않아요~", Toast.LENGTH_SHORT).show();
            }
        }

    }

}