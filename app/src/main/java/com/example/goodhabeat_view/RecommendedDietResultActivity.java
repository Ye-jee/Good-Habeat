package com.example.goodhabeat_view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class RecommendedDietResultActivity extends AppCompatActivity {

    TextView tv_conven, tv_highProt, tv_vitamin, tv_lowCal, tv_lowSalt, tv_lowSugar;

    Button btn_selectCustomDiet;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommanded_diet_result);

        tv_conven = (TextView) findViewById(R.id.tv_conven);
        tv_highProt = (TextView) findViewById(R.id.tv_highProt);
        tv_vitamin = (TextView) findViewById(R.id.tv_vitamin);
        tv_lowCal = (TextView) findViewById(R.id.tv_lowCal);
        tv_lowSalt = (TextView) findViewById(R.id.tv_lowSalt);
        tv_lowSugar = (TextView) findViewById(R.id.tv_lowSugar);

        btn_selectCustomDiet = (Button) findViewById(R.id.btn_selectCustomDiet);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        //String url = "http://192.168.56.1:8000/AndroidAppEx/capstoneEx/challenge_CustomDietMainEx1.jsp";
        String url = "http://192.168.56.1:8000/AndroidAppEx/capstoneEx/challenge_CustomDietMainEx2.jsp";

        /*StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject mainObj = new JSONObject(response);
                            JSONArray jsonArray = mainObj.getJSONArray("userCustoms");

                            //int index = 0;

                            //for(int i=1; i<2;i++) {
                            JSONObject userCustom = jsonArray.getJSONObject(0);

                            String convenience = userCustom.getString("convenience");
                            String lowCal = userCustom.getString("low_calories");
                            String lowSalt = userCustom.getString("low_salt");
                            String highCal = userCustom.getString("high_calories");
                            String lowSugar = userCustom.getString("low_sugar");
                            String lowFat = userCustom.getString("low_fat");

                            if(convenience.equals("1")) {
                                //tv_conven.setBackgroundColor(Color.parseColor("#2E8B57"));
                                //tv_conven.setTextColor(Color.parseColor("#2E8B57"));
                                tv_conven.setBackgroundResource(R.drawable.button_custom2);
                            }
                            if(lowCal.equals("1")) {
                                //tv_lowCal.setBackgroundColor(Color.parseColor("#2E8B57"));
                                //tv_lowCal.setTextColor(Color.parseColor("#2E8B57"));
                                tv_lowCal.setBackgroundResource(R.drawable.button_custom2);
                            }
                            if(lowSalt.equals("1")) {
                                //tv_lowSalt.setBackgroundColor(Color.parseColor("#2E8B57"));
                                //tv_lowSalt.setTextColor(Color.parseColor("#2E8B57"));
                                tv_lowSalt.setBackgroundResource(R.drawable.button_custom2);
                            }
                            if(highCal.equals("1")) {
                                //tv_highCal.setBackgroundColor(Color.parseColor("#2E8B57"));
                                //tv_highCal.setTextColor(Color.parseColor("#2E8B57"));
                                tv_highCal.setBackgroundResource(R.drawable.button_custom2);
                            }
                            if(lowSugar.equals("1")) {
                                //tv_lowSugar.setBackgroundColor(Color.parseColor("#2E8B57"));
                                //tv_lowSugar.setTextColor(Color.parseColor("#2E8B57"));
                                tv_lowSugar.setBackgroundResource(R.drawable.button_custom2);
                            }
                            if(lowFat.equals("1")) {
                                //tv_lowFat.setBackgroundColor(Color.parseColor("#2E8B57"));
                                //tv_lowFat.setTextColor(Color.parseColor("#2E8B57"));
                                tv_lowFat.setBackgroundResource(R.drawable.button_custom2);
                            }

                            //}

                        }catch (Exception e) {
                            //Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "error : " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(stringRequest);*/


        //가짜 데이터(?) - 간편식, 저염, 저칼로리 식단 설정
        tv_conven.setBackgroundResource(R.drawable.button_custom2);
        tv_lowCal.setBackgroundResource(R.drawable.button_custom2);
        tv_lowSalt.setBackgroundResource(R.drawable.button_custom2);



        //타이틀바 없애는 코드
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        btn_selectCustomDiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //CustomToast("맞춤 식단 설정이 완료되었습니다.");

                //가짜 데이터를 챌린지를 시작하는 페이지에 전달하는 코드
                /*String customDietResult = "간편식, 저칼로리, 저염식";
                Intent intent = new Intent(getApplicationContext(), ChallengeStartActivity.class);
                intent.putExtra("customResult", customDietResult);
                startActivity(intent);*/

                //로그인 페이지로 이동하는 코드
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    //커스텀 토스트 메시지 메소드
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