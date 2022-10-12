package com.example.goodhabeat_view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class JoinActivity extends AppCompatActivity {
    TextView tvUserAdmin, tvJoinBirth;
    EditText etJoinID, etJoinPwd, etJoinPwd_check, etJoinNick, etJoinHeight, etJoinWeight,
            etJoinEmail1, etJoinEmail2;
    Button btnID, btnPwd, btnNick, btnJoin;

    RequestQueue requestQueue;

    RadioGroup rgGender;
    RadioButton rbFemale, rbMale;

    String gender;

    Calendar calendar = Calendar.getInstance(); // datePickerDialog

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        tvUserAdmin = (TextView) findViewById(R.id.tvUserAdmin);
        tvJoinBirth = (TextView) findViewById(R.id.tvJoinBirth);

        etJoinID = (EditText) findViewById(R.id.etJoinId);
        etJoinPwd = (EditText) findViewById(R.id.etJoinPwd);
        etJoinPwd_check = (EditText) findViewById(R.id.etJoinPwdCheck);
        etJoinNick = (EditText) findViewById(R.id.etJoinNickname);
        etJoinHeight = (EditText) findViewById(R.id.etJoinHeight);
        etJoinWeight = (EditText) findViewById(R.id.etJoinWeight);
        etJoinEmail1 = (EditText) findViewById(R.id.etJoinEmail1);
        etJoinEmail2 = (EditText) findViewById(R.id.etJoinEmail2);

        btnID = (Button) findViewById(R.id.btn_idCheck);
        btnPwd = (Button) findViewById(R.id.btn_PwdCheck);
        btnNick = (Button) findViewById(R.id.btn_nickCheck);
        btnJoin = (Button) findViewById(R.id.btn_Join);

        /*--
        //성별 임시 삭제
        rgGender = (RadioGroup) findViewById(R.id.radioGender);
        rbFemale = (RadioButton) findViewById(R.id.rbtn_female);
        rbMale = (RadioButton) findViewById(R.id.rbtn_male);
        --*/

        /*rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rbName = radioGroup.findViewById(i);
                Toast.makeText(getApplicationContext(), rbName.getText().toString(), Toast.LENGTH_SHORT).show();

            }
        });*/

        //기능
        //아이디 중복 확인 기능(DB의 있는 데이터들과 비교함으로, 중복되지 않으면, User 테이블에 추가됨
        //지금은 문자열과 비교해 그 문자열과 일치하지 않으면 사용가능한 아이디라고 나올 수 있게 한다.
        //아이디와 닉네임 기능을 비슷함, 닉네임도 아이디와 마찬가지로 중복 확인 기능


        //타이틀바 없애는 코드
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //이용약관 텍스트 스크롤
        tvUserAdmin.setMovementMethod(new ScrollingMovementMethod());

        btnID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputID = etJoinID.getText().toString();
                String comID = "abcde";
                if(inputID.equals(comID)) {
                    Toast.makeText(getApplicationContext(), "이미 존재하는 아이디입니다. 다시 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "사용 가능한 아이디입니다", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnNick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputNick = etJoinNick.getText().toString();
                String comNick = "가나다라";
                if(inputNick.equals(comNick)) {
                    Toast.makeText(getApplicationContext(), "이미 존재하는 닉네임입니다. 다시 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "사용 가능한 닉네임입니다", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 생년월일 입력
        DatePickerDialog birthPicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                tvJoinBirth.setText(year + "-" + (month+1) + "-" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        birthPicker.getWindow().setBackgroundDrawableResource(R.drawable.round_rec_background);

        tvJoinBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                birthPicker.show();
            }
        });


        //EditText 비밀번호와 비밀번호 확인의 내용이 같은지 비교! 우선 버튼을 통해 - Toast 메시지를 통해 알려줌!
        btnPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pwd = etJoinPwd.getText().toString();
                String pwd_check = etJoinPwd_check.getText().toString();
                if(pwd.equals(pwd_check)) {
                    Toast.makeText(getApplicationContext(), "비밀번호가 일치합니다.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다. 다시 입력해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //키, 몸무게, 성별(라디오 버튼) 입력 받기
        //이메일 확인은, 이메일로 인증번호를 보내고, 입력해서 일치하면 확인되는 것으로??
        //우선 etJoinEmail1과 @와 etJoinEmail2를 합쳐서 저장하는 것으로!!
        //그리고 이용자 동의 체크하면 회원가입 끝-!

        /*--
        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rbName = radioGroup.findViewById(i);
                //Toast.makeText(getApplicationContext(), rbName.getText().toString(), Toast.LENGTH_SHORT).show();
                if(rbName.getText().toString().equals("여성")) {
                    gender = "2";
                }
                else if(rbName.getText().toString().equals("남성")) {
                    gender = "1";
                }
            }
        });

         */

        //가입하기 버튼을 누르면, DB에 사용자 정보가 저장이 되고
        //어느 화면으로 넘어가야 하나??
        
        //--------------------------------------------------------------------------------------------------------------------------------------------------------
        // join버튼 누를 때 node.js -> db 연결
        // (유나 수정 _ 22/10/06) - post 방식 추가, 예지가 작성한 volley 백업 후 삭제함
        // (유나 수정 _ 22/10/09) - btnJoin의 setOnClickListender와 request분리(삭제_그럴 필요 없었음)
        // (유나 수정 _ 22/10/09) - node.js를 통해 db에 회원정보 저장되는 것 확인


        String url = "http://10.0.2.2:3000/delivery";
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id = etJoinID.getText().toString();
                String pwd = etJoinPwd.getText().toString();
                String nickName = etJoinNick.getText().toString();
                String birth = tvJoinBirth.getText().toString();
                String height = etJoinHeight.getText().toString();
                String weight = etJoinWeight.getText().toString();
                String email = etJoinEmail1.getText().toString() + "@" + etJoinEmail2.getText().toString();


            StringRequest request = new StringRequest(
                    Request.Method.POST,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{
                                JSONObject respObj = new JSONObject(response);

                            }catch (Exception e){
                                e.printStackTrace();
                            }

                            Toast.makeText(getApplicationContext(), "회원가입 완료!", Toast.LENGTH_SHORT).show();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "ERROR : " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            System.out.println(error.getMessage());
                        }
                    }
            ) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("nickname", nickName);
                    params.put("id", id);
                    params.put("password", pwd);
                    params.put("email", email);
                    params.put("birth", birth);
                    params.put("height", height);
                    params.put("weight", weight);

                    return params;
                }
            };
            requestQueue.add(request);

            }
        }); // btnJoin 클릭 리스너 버튼 끝 */
    } //onCreate 끝
    
    
    // 외부 함수
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

    // 화면 터치 시 키보드 내림
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        TextView tv = (TextView) findViewById(R.id.textView5);  // 이게 있어야 오류가 안 남
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(tv.getWindowToken(), 0);
        return true;
    }

}