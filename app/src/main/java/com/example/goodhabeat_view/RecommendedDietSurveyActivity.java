package com.example.goodhabeat_view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RecommendedDietSurveyActivity extends AppCompatActivity {

    CheckBox cb_single, cb_weight, cb_sugar1, cb_sugar2 , cb_protein1, cb_protein2, cb_vitamin1, cb_vitamin2,
            cb_lowKcal1, cb_lowKcal2, cb_lowSalt1 ;

    /*EditText et_CurrentWeight, et_TargetWeight;*/
    TextView tv_currentWeight, tv_targetWeight;


    /*RadioGroup rg_sugarFrequency;
    RadioButton rb_zeroOne, rb_twoFour, rb_fiveSeven, rb_Ten;*/

    Button btn_recmdCustomDiet;

    String id_get;

    int eatingNumber;
    int single,weight_low, weight_high, sugar, protein, vitamin,  lowKcal, salt ;

    SharedPreferences preferences;

    //체중조절 다이얼로그
    View dialogView_hw;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommanded_diet_survey);

        cb_single = (CheckBox) findViewById(R.id.cb_single); //1인가구 입니다.

        cb_weight = (CheckBox) findViewById(R.id.cb_weight); //체중조절을 하고 싶어요

        cb_sugar1 = (CheckBox) findViewById(R.id.cb_sugar1); //곡식 음료
        cb_sugar2 = (CheckBox) findViewById(R.id.cb_sugar2); //단 음식

        cb_protein1 = (CheckBox) findViewById(R.id.cb_protein1); //생선 고기 계란 콩 자주 섭취
        cb_protein2 = (CheckBox) findViewById(R.id.cb_protein2); //유제품

        cb_vitamin1 = (CheckBox) findViewById(R.id.cb_vitamin1); //채소류, 버섯
        cb_vitamin2 = (CheckBox) findViewById(R.id.cb_vitamin2); //과일

        cb_lowKcal1 = (CheckBox) findViewById(R.id.cb_lowKcal1); //튀김
        cb_lowKcal2 = (CheckBox) findViewById(R.id.cb_lowKcal2); //기름 많은 고기

        cb_lowSalt1 = (CheckBox) findViewById(R.id.cb_lowSalt1); //짠 음식


        tv_currentWeight = (TextView) findViewById(R.id.tv_currentWeight);
        tv_targetWeight = (TextView) findViewById(R.id.tv_targetWeight);

        btn_recmdCustomDiet = (Button) findViewById(R.id.btn_recmdCustomDiet);


        //타이틀바 없애는 코드
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        preferences = getApplicationContext().getSharedPreferences("userjoin", Context.MODE_PRIVATE);
        String nickname_get = preferences.getString("nickname", "닉네임 못가져옴" );
        System.out.println("userjoin 가져온 닉네임: "+nickname_get);


        //닉네임 확인
        String url_nickCheck = "http://10.0.2.2:3000/nick_check";
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        //닉네임 존재 확인
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url_nickCheck,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            System.out.println("응답완료: "+response);
                            JSONArray jsonarray = new JSONArray(response);
                            //JSONArray jArray = mainObj.getJSONArray("");
                            JSONObject setting_json = jsonarray.getJSONObject(0);
                            id_get = setting_json.getString("user_id");



                        }catch (Exception e){ e.printStackTrace(); }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("실패 이유: "+error.getMessage());
                    }
                }
        ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("nick_check", nickname_get);

                return params;
            }
        };
        requestQueue.add(request);









        //맞춤 식단 설문조사 결과를 보여주는 페이지로 이동
        btn_recmdCustomDiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Intent intent = new Intent(getApplicationContext(), RecommendedDietResultActivity.class);
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

    //외부 함수
    //체크박스를 눌렀을 때
    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();        // Is the view now checked?

        //1인 가구
        if(view.getId() == R.id.cb_single) {
            if(checked) {
                single = 1;

            }
            else {
                single = 0;
            }
        }


        //체중조절 관련 - 감량, 증량
        if(view.getId() == R.id.cb_weight) {
            if(checked) {

                dialogView_hw = (View) View.inflate(RecommendedDietSurveyActivity.this, R.layout.challenge_hw_dialog, null);

                AlertDialog.Builder alertDialog_hw = new AlertDialog.Builder(RecommendedDietSurveyActivity.this);

                //hwDialog.setTitle("현재 몸무게와 목표 몸무게를 입력하세요.");

                alertDialog_hw.setView(dialogView_hw);

                alertDialog_hw.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText et_currentWeight = (EditText) dialogView_hw.findViewById(R.id.et_currentWeight);
                        EditText et_targetWeight = (EditText) dialogView_hw.findViewById(R.id.et_targetWeight);

                        if(et_currentWeight.getText().toString().equals("") || et_targetWeight.getText().toString().equals("")) {
                            Toast.makeText(getApplicationContext(), "현재 몸무게와 목표 몸무게를 입력해주세요!", Toast.LENGTH_SHORT).show();
                            cb_weight.setChecked(false);
                        }
                        else {
                            int current = Integer.parseInt(et_currentWeight.getText().toString());
                            int target = Integer.parseInt(et_targetWeight.getText().toString());
                            int value = target - current;

                            if(value <= 0) {
                                weight_low = 1;
                                //Toast.makeText(getApplicationContext(), "저칼로리 : " + lowcal + "\n저염 : " + lowsalt, Toast.LENGTH_SHORT).show();
                            }
                            else {
                                weight_high = 1;
                                //Toast.makeText(getApplicationContext(), "고칼로리 : " + highcal, Toast.LENGTH_SHORT).show();
                            }

                            tv_currentWeight.setVisibility(View.VISIBLE);
                            tv_targetWeight.setVisibility(View.VISIBLE);

                            tv_currentWeight.setText("현재 몸무게 : " + current + "kg");
                            tv_targetWeight.setText("목표 몸무게 : " + target + "kg");
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
                tv_currentWeight.setVisibility(View.GONE);
                tv_targetWeight.setVisibility(View.GONE);
            }
        }

        //sugar
        if(view.getId() == R.id.cb_sugar1||view.getId() == R.id.cb_sugar2) {
            if(checked) {
                sugar = 1;

            }
            else {
                sugar = 0;
            }
        }

        //protein
        if(view.getId() == R.id.cb_protein1||view.getId() == R.id.cb_protein2) {
            if(checked) {
                protein = 1;

            }
            else {
                protein = 0;
            }
        }

        //vitamin
        if(view.getId() == R.id.cb_vitamin1||view.getId() == R.id.cb_vitamin2) {
            if(checked) {
                vitamin = 1;

            }
            else {
                vitamin = 0;
            }
        }

        //lowKcal
        if(view.getId() == R.id.cb_lowKcal1||view.getId() == R.id.cb_lowKcal2) {
            if(checked) {
                lowKcal = 1;

            }
            else {
                lowKcal = 0;
            }
        }

        //lowSalt
        if(view.getId() == R.id.cb_lowSalt1) {
            if(checked) {
                salt  = 1;

            }
            else {
                salt  = 0;
            }
        }


        preferences = getApplicationContext().getSharedPreferences("userjoin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("id", id_get);
        System.out.println("id는? "+id_get);
        editor.putString("single", String.valueOf(single));
        editor.putString("weight_low", String.valueOf(weight_low));
        editor.putString("weight_high", String.valueOf(weight_high));
        editor.putString("sugar", String.valueOf(sugar));
        editor.putString("protein", String.valueOf(protein));
        editor.putString("vitamin", String.valueOf(vitamin));
        editor.putString("lowKcal", String.valueOf(lowKcal));
        editor.putString("salt", String.valueOf(salt));

        editor.commit(); // 저장|



    } //체크박스

}