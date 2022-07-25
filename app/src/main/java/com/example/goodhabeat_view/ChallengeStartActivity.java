package com.example.goodhabeat_view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import java.util.Calendar;

public class ChallengeStartActivity extends AppCompatActivity {

    EditText etChallengeName;
    Button btnChallengeStart;

    RequestQueue requestQueue;
    String url;

    TextView tvChallengeStartDate, tvChallengeEndDate, tv_recommandDiet, tv_customDietResult;

    Calendar calendar = Calendar.getInstance(); // datePickerDialog

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_start);

        etChallengeName = (EditText) findViewById(R.id.et_ChallengeName);
        tvChallengeStartDate = (TextView) findViewById(R.id.tv_StartChallengeDate);
        tvChallengeEndDate = (TextView) findViewById(R.id.tv_EndChallengeDate);

        btnChallengeStart = (Button) findViewById(R.id.btn_ChallenStart);

        tv_recommandDiet = (TextView) findViewById(R.id.tv_RecommendDiet);

        tv_customDietResult = (TextView) findViewById(R.id.tv_customDietResult);
        tv_customDietResult.setText("");
        tv_customDietResult.setVisibility(View.INVISIBLE);


        // 시작 날짜 입력
        DatePickerDialog birthPicker_start = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                tvChallengeStartDate.setText(year + "-" + (month+1) + "-" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        birthPicker_start.getWindow().setBackgroundDrawableResource(R.drawable.round_rec_background);

        tvChallengeStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                birthPicker_start.show();
            }
        });

        // 끝나는 날짜 입력
        DatePickerDialog birthPicker_end = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                tvChallengeEndDate.setText(year + "-" + (month+1) + "-" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        birthPicker_end.getWindow().setBackgroundDrawableResource(R.drawable.round_rec_background);

        tvChallengeEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                birthPicker_end.show();
            }
        });



        //url = "http://192.168.56.1:8000/AndroidAppEx/capstoneEx/challengeStartEx.jsp";


        //설정하는 페이지에서 다시 되돌아와, tv_customDietResult 텍스트뷰에 값을 입력받으면 되는 코드 작성 예정
        //Intent 가져오기
        Intent intent = getIntent();
        String customResult = intent.getStringExtra("customResult");
        tv_customDietResult.setText(customResult);
        if(!tv_customDietResult.getText().toString().equals("")) {
            tv_customDietResult.setVisibility(View.VISIBLE);
        }


        //타이틀바 없애는 코드
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        btnChallengeStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*String Cname = etChallengeName.getText().toString();
                String CStartdate = etChallengeStartDate.getText().toString();
                String CEnddate = etChallengeEndDate.getText().toString();

                String url2 = url
                        + "?challengeName=" + Cname
                        + "&challengeStartDate=" + CStartdate
                        + "&challengeEndDate=" + CEnddate
                        + "&userCustomFkid=1"
                        + "&userDetailsFkid=1";

                requestQueue = Volley.newRequestQueue(getApplicationContext());

                StringRequest stringRequest = new StringRequest(Request.Method.GET,
                        url2,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(getApplicationContext(), "챌린지 시작&추가 완료!" + response, Toast.LENGTH_SHORT).show();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), "에러 원인 :" + error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });

                requestQueue.add(stringRequest);*/

                //Toast.makeText(getApplicationContext(), "챌린지 설정이 완료되었습니다.", Toast.LENGTH_SHORT).show();


                //어느 액티비티로 이동해야 하나?
                // -> 맞춤 식단이 설정되어 있지 않으면, 설정하는 페이지(액티비티)로 이동!
                // -> 맞춤 식단이 설정되어 있으면, 챌린지가 이미 설정되어 있는 액티비티로!

                /*boolean textnullvalue = tv_customDietResult.getText().toString().equals("");
                CustomToast(String.valueOf(textnullvalue));*/


                if(tv_customDietResult.getText().toString().equals("")) {
                    CustomToast("먼저 맞춤 식단을 추천받으세요.");
                }
                else {
                    CustomToast("챌린지 설정이 완료되었습니다.");

                    Intent intent = new Intent(getApplicationContext(), ChallengeActivity_Setting.class);
                    startActivity(intent);
                }
            }
        });



        //맞춤 식단 추천 받기 텍스트 뷰를 클릭하면, 해당 액티비티로 이동!
        tv_recommandDiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChallengeRecommandDietActivity.class);
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


}