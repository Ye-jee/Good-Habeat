package com.example.goodhabeat_view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class RecommendedDietResultActivity extends AppCompatActivity {

    SharedPreferences preferences;

    TextView tv_conven, tv_highProt, tv_vitamin, tv_lowCal, tv_lowSalt, tv_lowSugar;

    String conven="0", highProt="0", vitamin="0", lowCal="0", lowSalt="0", lowSugar="0";

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

        preferences = getApplicationContext().getSharedPreferences("userjoin", Context.MODE_PRIVATE);
        String id_get = preferences.getString("id", "id 오류 시 출력" );
        String single_get = preferences.getString("single", "single 오류 시 출력" );
        String weight_low_get = preferences.getString("weight_low", "weight_low 오류 시 출력" );
        String weight_high_get = preferences.getString("weight_high", "weight_high 오류 시 출력" );
        String sugar_get = preferences.getString("sugar", "sugar 오류 시 출력" );
        String protein_get = preferences.getString("protein", "protein 오류 시 출력" );
        String vitamin_get = preferences.getString("vitamin", "vitamin 오류 시 출력" );
        String lowKcal_get = preferences.getString("lowKcal", "lowKcal 오류 시 출력" );
        String salt_get = preferences.getString("salt", "salt 오류 시 출력" );



        if(single_get.equals("1"))
        {
            tv_conven.setBackgroundResource(R.drawable.button_custom2);
            conven="1";
        }

        if(sugar_get.equals("1")) {
            tv_lowSugar.setBackgroundResource(R.drawable.button_custom2);
            lowSugar="1";
        }

        if(protein_get.equals("1") || weight_high_get.equals("1")) {
            tv_highProt.setBackgroundResource(R.drawable.button_custom2);
            highProt="1";
        }

        if(vitamin_get.equals("1")) {
            tv_vitamin.setBackgroundResource(R.drawable.button_custom2);
            vitamin="1";
        }

        if(lowKcal_get.equals("1") || weight_low_get.equals("1")) {
            tv_lowCal.setBackgroundResource(R.drawable.button_custom2);
            lowCal = "1";
        }

        if(salt_get.equals("1")) {
            tv_lowSalt.setBackgroundResource(R.drawable.button_custom2);
            lowSalt="1";
        }

        String url_user_costom_join = "http://10.0.2.2:3000/user_costom_join";

        StringRequest request = new StringRequest(
                Request.Method.POST,
                url_user_costom_join,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("1")){
                            //1이면 사용 가능
                            System.out.println("진짜진짜 가입 성공");
                            Toast.makeText(getApplicationContext(), "가입이 성공하였습니다.", Toast.LENGTH_SHORT).show();

                        } else {
                            //2면 사용 불가
                            System.out.println(response+ " / 회원가입 불가");
                            Toast.makeText(getApplicationContext(), "회원가입에 실패했습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.getMessage());
                    }
                }
        ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", id_get);

                params.put("convenience", conven);
                params.put("high_protein", highProt);
                params.put("vitamin", vitamin);
                params.put("low_calorie", lowCal);
                params.put("low_salt", lowSalt);
                params.put("low_sugar", lowSugar);

                return params;
            }
        };
        requestQueue.add(request);













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