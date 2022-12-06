package com.example.goodhabeat_view;
// 캘린더

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.media.metrics.Event;
import android.preference.PreferenceManager;
import android.provider.CalendarContract;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class CustomCalendarView extends LinearLayout {


    RequestQueue requestQueue;
    SharedPreferences preferences;

    ImageButton NextButton, PreviousButton;
    TextView CurrentDate;
    GridView gridView;

    TextView tvToday;
    TextView menu_break, menu_lunch, menu_dinner;
    TextView time_break, time_lunch, time_dinner;
    ImageView check_break, check_lunch, check_dinner;
    String txtMenuBreak, txtMenuLunch, txtMenuDinner;
    double calorie_sum = 0, carbohydrate_sum = 0, protein_sum = 0, fat_sum = 0;
    int b,l,di;


    String Smenu_break, Smenu_lunch, Smenu_dinner;
    String Stime_break, Stime_lunch, Stime_dinner;

    ProgressBar cal_progress, carbo_progress, protein_progress, fat_progress;
    TextView tv_cal_num, tv_carbo_num, tv_protein_num, tv_fat_num;

    private static final int MAX_CALENDAR_DAYS = 42;
    Calendar calendar = Calendar.getInstance(Locale.KOREA);
    Context context;
    SimpleDateFormat dateFormat_kor = new SimpleDateFormat("yyyy년 MMM", Locale.KOREA);
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy", Locale.KOREA);
    SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM", Locale.KOREA);
    SimpleDateFormat monthFormat_data = new SimpleDateFormat("MM", Locale.KOREA);
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);
    SimpleDateFormat eventDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
    SimpleDateFormat dayOfMonthFormat = new SimpleDateFormat("dd", Locale.KOREA);

    MyGridAdapter myGridAdapter;
    AlertDialog alertDialog;
    List<Date> dates = new ArrayList<>();
    List<Events> eventsList = new ArrayList<>();

    DBOpenHelper dbOpenHelper;

    Date currentTime = Calendar.getInstance().getTime();
    public static String todayDateFormat = "yyyy-MM-dd";
    public static String getMonthFormat = "MM"; // 월
    public static String getTimeFormat = "HH"; // 시간(24시간)

    public CustomCalendarView(Context context) {
        super(context);
    }

    public CustomCalendarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        IntializeLayout();
        SetUpCalendar();

        PreviousButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH, -1);
                SetUpCalendar();
            }
        });

        NextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH, 1);
                SetUpCalendar();
            }
        });

        //--------------------------------------------------------------------------------------------------------------------------------------------------------
        //프리퍼런스로 로그인한 닉네임 가져오기
        preferences = getContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String nickname_get = preferences.getString("nickname", "nickname 오류" );
        System.out.println(nickname_get);
        //--------------------------------------------------------------------------------------------------------------------------------------------------------


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                calorie_sum = 0;
                carbohydrate_sum = 0;
                protein_sum = 0;
                fat_sum = 0;

                b=0;
                l=0;
                di=0;

                AlertDialog.Builder builder= new AlertDialog.Builder(context);
                builder.setCancelable(true);

                final String yesterday = eventDateFormat.format(dates.get(position - 1));
                final String y_month = monthFormat_data.format(dates.get(position - 1));
                final String y_year = yearFormat.format(dates.get(position - 1));
                final String y_dayOfMonth = dayOfMonthFormat.format(dates.get(position - 1));

                final String date = eventDateFormat.format(dates.get(position));
                final String month = monthFormat_data.format(dates.get(position));
                final String year = yearFormat.format(dates.get(position));
                final String dayOfMonth = dayOfMonthFormat.format(dates.get(position));

                final String tomorrow = eventDateFormat.format(dates.get(position + 1));
                final String t_month = monthFormat_data.format(dates.get(position + 1));
                final String t_year = yearFormat.format(dates.get(position + 1));
                final String t_dayOfMonth = dayOfMonthFormat.format(dates.get(position + 1));

                // 날짜 정보 저장 및 토스트 출력 (Preferences)
                preferences = getContext().getSharedPreferences("dateInfo", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("yesterdayYear", y_year);
                editor.putString("yesterdayMonth", y_month);
                editor.putString("yesterdayDate", y_dayOfMonth);


                editor.putString("todayYear", year);
                editor.putString("todayMonth", month);
                editor.putString("todayDate", dayOfMonth);

                editor.putString("tomorrowYear", t_year);
                editor.putString("tomorrowMonth", t_month);
                editor.putString("tomorrowDate", t_dayOfMonth);

                editor.commit();
                String yy = preferences.getString("yesterdayYear", y_year);
                String ym = preferences.getString("yesterdayMonth", y_month);
                String yd = preferences.getString("yesterdayDate", y_dayOfMonth);

                String y = preferences.getString("todayYear", year);
                String m = preferences.getString("todayMonth", month);
                String d = preferences.getString("todayDate", dayOfMonth);

                String ty = preferences.getString("tomorrowYear", t_year);
                String tm = preferences.getString("tomorrowMonth", t_month);
                String td = preferences.getString("tomorrowDate", t_dayOfMonth);
                //Toast.makeText(getContext(), m + " " + d + "일", Toast.LENGTH_LONG).show();
                System.out.println("선택한 어제 날짜: "+yy + "-" + ym + "-" + yd);
                System.out.println("선택한 날짜: "+y + "-" + m + "-" + d);
                System.out.println("선택한 내일 날짜: "+ty + "-" + tm + "-" + td);


                // bottom sheet dialog
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        getContext(), R.style.BottomSheetDialogTheme
                );
                View bottomSheetView = LayoutInflater.from(getContext())
                        .inflate(
                                R.layout.layout_bottom_sheet,
                                (LinearLayout)findViewById(R.id.bottomSheetContainer)
                        );

                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
                //--------------------------------------------------------------------------------------------------------------------------------------------------------

                // ProgressBar 설정
                cal_progress = (ProgressBar) bottomSheetView.findViewById(R.id.cal_amount);
                carbo_progress = (ProgressBar) bottomSheetView.findViewById(R.id.carbohydrate_amount);
                protein_progress = (ProgressBar) bottomSheetView.findViewById(R.id.protein_amount);
                fat_progress = (ProgressBar) bottomSheetView.findViewById(R.id.fat_amount);

                tv_cal_num = (TextView) bottomSheetView.findViewById(R.id.cal_progress_num);
                tv_carbo_num = (TextView) bottomSheetView.findViewById(R.id.carbohydrate_progress_num);
                tv_protein_num = (TextView) bottomSheetView.findViewById(R.id.protein_progress_num);
                tv_fat_num = (TextView) bottomSheetView.findViewById(R.id.fat_progress_num);

                int cal_progress_num = cal_progress.getProgress();
                int carbo_progress_num = carbo_progress.getProgress();
                int protein_progress_num = protein_progress.getProgress();
                int fat_progress_num = fat_progress.getProgress();

                String cal_str = String.valueOf(cal_progress_num);
                String carbo_str = String.valueOf(carbo_progress_num);
                String protein_str = String.valueOf(protein_progress_num);
                String fat_str = String.valueOf(fat_progress_num);

                tv_cal_num.setText(cal_str);
                tv_carbo_num.setText(carbo_str);
                tv_protein_num.setText(protein_str);
                tv_fat_num.setText(fat_str);

                //--------------------------------------------------------------------------------------------------------------------------------------------------------
                //날짜 별 식단
                String SelectDate = y + "-" + m + "-" + d;
                String SelectTomorrowDate = ty + "-" + tm + "-" + td;
                String SelectYesterdayDate = y + "-" + ym + "-" + yd;
                LinearLayout breakfast = (LinearLayout) bottomSheetView.findViewById(R.id.breakfast);
                LinearLayout lunch = (LinearLayout) bottomSheetView.findViewById(R.id.lunch);
                LinearLayout dinner = (LinearLayout) bottomSheetView.findViewById(R.id.dinner);

                String url_user_detail = "http://10.0.2.2:3000/user_diet";
                requestQueue = Volley.newRequestQueue(getContext());
                SimpleDateFormat systemTime = new SimpleDateFormat(getTimeFormat, Locale.getDefault());
                String system_time = systemTime.format(currentTime); // 문자열을 정수로 변경
                String[] break_menu ;
                StringRequest request = new StringRequest(
                        Request.Method.POST,
                        url_user_detail,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    System.out.println("응답완료: " + response);

                                    if(response.contains("detail_date")){
                                        System.out.println("테이블: user_detail");
                                        JSONArray detail_json = new JSONArray(response);
                                        for(int i=0; i<detail_json.length(); i++){
                                            JSONObject dietObj = detail_json.getJSONObject(i); //중괄호 벗겨서 하나씩 나눔

                                            //System.out.println("dietObj[" + i + "] : " + dietObj);
                                            String detail_meal = dietObj.getString("meal");
                                            String detail_time = dietObj.getString("detail_time");
                                            String menu_name = dietObj.getString("menu_name");

                                            String s_calorie = dietObj.getString("calorie");
                                            double calorie = Double.parseDouble(s_calorie);
                                            //System.out.println("[ calorie "+i+"] "+calorie);
                                            calorie_sum += calorie ;

                                            String s_carbohydrate = dietObj.getString("carbohydrate");
                                            double carbohydrate = Double.parseDouble(s_carbohydrate);
                                            carbohydrate_sum += carbohydrate;

                                            String s_protein = dietObj.getString("protein");
                                            double protein = Double.parseDouble(s_protein);
                                            protein_sum += protein;

                                            String s_fat  = dietObj.getString("fat");
                                            double fat = Double.parseDouble(s_fat);
                                            fat_sum += fat;

                                            if(detail_meal.equals("1")){
                                                time_break.setText(detail_time.substring(0, 5));
                                                menu_break.append(menu_name + ", ");
                                                b = 1;
                                            }

                                            if(detail_meal.equals("2")){
                                                time_lunch.setText(detail_time.substring(0, 5));
                                                menu_lunch.append(menu_name + ", ");
                                                l = 1;
                                            }

                                            if(detail_meal.equals("3")){
                                                time_dinner.setText(detail_time.substring(0, 5));
                                                menu_dinner.append(menu_name + ", ");
                                                di = 1;
                                            }

                                                /*
                                                switch (detail_meal){
                                                    case "1" :
                                                        time_break.setText(detail_time.substring(0, 5));
                                                        menu_break.append(menu_name + ", ");
                                                        break;

                                                    case "2" :
                                                       time_lunch.setText(detail_time.substring(0, 5));
                                                        menu_lunch.append(menu_name + ", ");

                                                        break;

                                                    case "3" :
                                                        time_dinner.setText(detail_time.substring(0, 5));
                                                        menu_dinner.append(menu_name + ", ");

                                                        break;
                                                } //meal 스위치 끝

                                                 */

                                        }//for문 끝


                                        if(b==1){
                                            String break_name = menu_break.getText().toString();
                                            break_name = break_name.substring(0, break_name.length() - 2);
                                            menu_break.setText(break_name);
                                        } else {
                                            breakfast.setVisibility(View.GONE);
                                        }

                                        if(l==1){
                                            String lunch_name = menu_lunch.getText().toString();
                                            lunch_name = lunch_name.substring(0, lunch_name.length() - 2);
                                            menu_lunch.setText(lunch_name);
                                        } else {
                                            lunch.setVisibility(View.GONE);
                                        }

                                        if(di==1){
                                            String dinner_name = menu_dinner.getText().toString();
                                            dinner_name = dinner_name.substring(0, dinner_name.length() - 2);
                                            menu_dinner.setText(dinner_name);
                                        } else {
                                            dinner.setVisibility(View.GONE);
                                        }


                                        int calorie_int = (int) Math.round(calorie_sum);
                                        int carbo_max = (int) Math.round(calorie_sum*0.5);
                                        int protein_max = (int) Math.round(calorie_sum*0.2);
                                        int fat_max = (int) Math.round(calorie_sum*0.3);

                                        int carbo_int = (int) Math.round(carbohydrate_sum);
                                        int protein_int = (int) Math.round(protein_sum);
                                        int fat_int = (int) Math.round(fat_sum);

                                        cal_progress.setProgress(calorie_int);
                                        System.out.println("칼로리 총 합산: "+calorie_int);
                                        int cal_progress_num = (int) Math.round((calorie_sum/2350)*100);
                                        System.out.println("칼로리 총 합산 퍼센트: "+cal_progress_num);

                                        carbo_progress.setMax(carbo_max);
                                        carbo_progress.setProgress(carbo_int);
                                        System.out.println("탄수화물 총 합산: "+carbo_int);
                                        int carbo_progress_num = (int) Math.round((carbohydrate_sum/carbo_max)*100);
                                        System.out.println("탄수화물 총 합산 퍼센트: "+carbo_progress_num);


                                        protein_progress.setMax(protein_max);
                                        protein_progress.setProgress(protein_int);
                                        System.out.println("단백질 총 합산: "+protein_int);
                                        int protein_progress_num = (int) Math.round((protein_sum/protein_max)*100);
                                        System.out.println("단백질 총 합산 퍼센트: "+protein_progress_num);

                                        fat_progress.setMax(fat_max);
                                        fat_progress.setProgress(fat_int);
                                        System.out.println("지방 총 합산: "+fat_int);
                                        int fat_progress_num = (int) Math.round((fat_sum/fat_max)*100);
                                        System.out.println("지방 총 합산 퍼센트: "+fat_progress_num);

                                        String cal_str = String.valueOf(cal_progress_num);
                                        String carbo_str = String.valueOf(carbo_progress_num);
                                        String protein_str = String.valueOf(protein_progress_num);
                                        String fat_str = String.valueOf(fat_progress_num);

                                        tv_cal_num.setText(cal_str);
                                        tv_carbo_num.setText(carbo_str);
                                        tv_protein_num.setText(protein_str);
                                        tv_fat_num.setText(fat_str);

                                    }//detail if문 끝

                                    if(response.contains("diet_date")){
                                        System.out.println("테이블: diet");
                                        JSONArray detail_json = new JSONArray(response);
                                        for(int i=0; i<detail_json.length(); i++){
                                            JSONObject dietObj = detail_json.getJSONObject(i); //중괄호 벗겨서 하나씩 나눔
                                            //System.out.println("dietObj[" + i + "] : " + dietObj);
                                            String detail_meal = dietObj.getString("meal");
                                            String diet_date = dietObj.getString("diet_date");
                                            String menu_name = dietObj.getString("menu_name");


                                            String s_calorie = dietObj.getString("calorie");
                                            double calorie = Double.parseDouble(s_calorie);
                                            //System.out.println("[ calorie "+i+"] "+calorie);
                                            calorie_sum += calorie ;

                                            String s_carbohydrate = dietObj.getString("carbohydrate");
                                            double carbohydrate = Double.parseDouble(s_carbohydrate);
                                            carbohydrate_sum += carbohydrate;

                                            String s_protein = dietObj.getString("protein");
                                            double protein = Double.parseDouble(s_protein);
                                            protein_sum += protein;

                                            String s_fat  = dietObj.getString("fat");
                                            double fat = Double.parseDouble(s_fat);
                                            fat_sum += fat;

                                            //-----------------------------------------------------------------


                                            if(detail_meal.equals("1")){
                                                time_break.setText("");
                                                menu_break.append(menu_name + ", ");
                                                b = 1;
                                            }

                                            if(detail_meal.equals("2")){
                                                time_lunch.setText("");
                                                menu_lunch.append(menu_name + ", ");
                                                l = 1;
                                            }

                                            if(detail_meal.equals("3")){
                                                time_dinner.setText("");
                                                menu_dinner.append(menu_name + ", ");
                                                di = 1;
                                            }


                                        }//for문 끝
                                        //메뉴 이름 보여주기
                                        if(b==1){
                                            String break_name = menu_break.getText().toString();
                                            break_name = break_name.substring(0, break_name.length() - 2);
                                            menu_break.setText(break_name);
                                        } else {
                                            breakfast.setVisibility(View.GONE);
                                        }

                                        if(l==1){
                                            String lunch_name = menu_lunch.getText().toString();
                                            lunch_name = lunch_name.substring(0, lunch_name.length() - 2);
                                            menu_lunch.setText(lunch_name);
                                        } else {
                                            lunch.setVisibility(View.GONE);
                                        }

                                        if(di==1){
                                            String dinner_name = menu_dinner.getText().toString();
                                            dinner_name = dinner_name.substring(0, dinner_name.length() - 2);
                                            menu_dinner.setText(dinner_name);
                                        } else {
                                            dinner.setVisibility(View.GONE);
                                        }

                                        int calorie_int = (int) Math.round(calorie_sum);
                                        int carbo_max = (int) Math.round(calorie_sum*0.5);
                                        int protein_max = (int) Math.round(calorie_sum*0.2);
                                        int fat_max = (int) Math.round(calorie_sum*0.3);

                                        int carbo_int = (int) Math.round(carbohydrate_sum);
                                        int protein_int = (int) Math.round(protein_sum);
                                        int fat_int = (int) Math.round(fat_sum);

                                        cal_progress.setProgress(0);
                                        System.out.println("칼로리 총 합산: "+calorie_int);
                                        int cal_progress_num = (int) Math.round((calorie_sum/2350)*100);
                                        System.out.println("칼로리 총 합산 퍼센트: "+cal_progress_num);

                                        carbo_progress.setMax(0);
                                        carbo_progress.setProgress(0);
                                        System.out.println("탄수화물 총 합산: "+carbo_int);
                                        int carbo_progress_num = (int) Math.round((carbohydrate_sum/carbo_max)*100);
                                        System.out.println("탄수화물 총 합산 퍼센트: "+carbo_progress_num);


                                        protein_progress.setMax(0);
                                        protein_progress.setProgress(0);
                                        System.out.println("단백질 총 합산: "+protein_int);
                                        int protein_progress_num = (int) Math.round((protein_sum/protein_max)*100);
                                        System.out.println("단백질 총 합산 퍼센트: "+protein_progress_num);

                                        fat_progress.setMax(0);
                                        fat_progress.setProgress(0);
                                        System.out.println("지방 총 합산: "+fat_int);
                                        int fat_progress_num = (int) Math.round((fat_sum/fat_max)*100);
                                        System.out.println("지방 총 합산 퍼센트: "+fat_progress_num);

                                        String cal_str = String.valueOf(cal_progress_num);
                                        String carbo_str = String.valueOf(carbo_progress_num);
                                        String protein_str = String.valueOf(protein_progress_num);
                                        String fat_str = String.valueOf(fat_progress_num);

                                        tv_cal_num.setText("0");
                                        tv_carbo_num.setText("0");
                                        tv_protein_num.setText("0");
                                        tv_fat_num.setText("0");






                                    }//detail if문 끝





                                }catch (Exception e){ e.printStackTrace(); }

                                if(response.equals("[]")){
                                    breakfast.setVisibility(View.GONE);
                                    lunch.setVisibility(View.GONE);
                                    dinner.setVisibility(View.GONE);
                                }
                            }

                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                System.out.println("응답실패: "+error.getMessage());
                            }
                        }
                ) {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();

                        params.put("nick_check", nickname_get);
                        params.put("SelectDate", SelectDate);
                        params.put("SelectTomorrowDate", SelectTomorrowDate);
                        params.put("SelectYesterdayDate", SelectYesterdayDate);


                        return params;
                    }
                };
                requestQueue.add(request);



                //(구버전)날짜 별 식단

                // 날짜 별 식단
                tvToday = (TextView) bottomSheetView.findViewById(R.id.tvToday);

                menu_break = (TextView) bottomSheetView.findViewById(R.id.menu_break);
                menu_lunch = (TextView) bottomSheetView.findViewById(R.id.menu_lunch);
                menu_dinner = (TextView) bottomSheetView.findViewById(R.id.menu_dinner);

                check_break = (ImageView) bottomSheetView.findViewById(R.id.check_break);
                check_lunch = (ImageView) bottomSheetView.findViewById(R.id.check_lunch);
                check_dinner = (ImageView) bottomSheetView.findViewById(R.id.check_dinner);

                time_break = (TextView) bottomSheetView.findViewById(R.id.time_break);
                time_lunch = (TextView) bottomSheetView.findViewById(R.id.time_lunch);
                time_dinner = (TextView) bottomSheetView.findViewById(R.id.time_dinner);





                // 메뉴 취소선 (취소선은 없애기로 결정)
                //menu_break.setPaintFlags(menu_break.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG); //취소선
                //menu_lunch.setPaintFlags(menu_lunch.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG); //취소선



                // 날짜 변경
                tvToday.setText(m + "월 " + d + "일");

                RequestQueue requestQueue = Volley.newRequestQueue(getContext());

/*
                // bottom dialog context
                String url = "http://192.168.219.106:8090/myapp/goodHabit_diet.jsp"
                        + "?month=" + m + "&dayOfMonth=" + d;

                StringRequest stringRequest = new StringRequest(Request.Method.GET,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject mainObj = new JSONObject(response);
                                    System.out.println("\n\n" + response + "//////////////////////////////////////");
                                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                                    JSONArray jArray = mainObj.getJSONArray("Details");

                                    for (int i=0; i<jArray.length(); i++) {
                                        JSONObject jObject = jArray.getJSONObject(i);
                                        if (jObject.getString("meal").equals("1")) {
                                            check_break.setVisibility(View.VISIBLE);
                                            menu_break.setText("고구마 맛탕"); // 나중에 diet 말고 다른 테이블에서 메뉴명 및 실천 여부 가져오기
                                        }
                                        if (jObject.getString("meal").equals("2")) {
                                            check_lunch.setVisibility(View.VISIBLE);
                                            menu_lunch.setText("해장국"); // 나중에 diet 말고 다른 테이블에서 메뉴명 및 실천 여부 가져오기
                                        }
                                        if (jObject.getString("meal").equals("3")) {
                                            check_dinner.setVisibility(View.VISIBLE);
                                            menu_dinner.setText("샐러드"); // 나중에 diet 말고 다른 테이블에서 메뉴명 및 실천 여부 가져오기
                                        }
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getContext(), "ERROR : " + error.toString(), Toast.LENGTH_LONG).show();
                            }
                        });
*/
                // Volley.timeout.error
                /*
                stringRequest.setRetryPolicy(new RetryPolicy() {
                    @Override
                    public int getCurrentTimeout() {
                        return 50000;
                    }

                    @Override
                    public int getCurrentRetryCount() {
                        return 50000;
                    }

                    @Override
                    public void retry(VolleyError error) throws VolleyError {

                    }
                });


                requestQueue.add(stringRequest);*/


            }
        });

    }

    public CustomCalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void SaveEvent(String event, String time, String date, String month, String year) {
        dbOpenHelper = new DBOpenHelper(context);
        SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
        dbOpenHelper.SaveEvent(event, time, date, month, year, database);
        dbOpenHelper.close();
        Toast.makeText(context, "Event Saved", Toast.LENGTH_SHORT).show();
    }

    private void IntializeLayout() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.calendar_layout, this);
        NextButton = view.findViewById(R.id.nextBtn);
        PreviousButton = view.findViewById(R.id.previousBtn);
        CurrentDate = view.findViewById(R.id.current_Date);
        gridView = view.findViewById(R.id.gridView);
    }

    private void SetUpCalendar() {
        String currwntDate = dateFormat_kor.format(calendar.getTime());
        CurrentDate.setText(currwntDate);
        dates.clear();

        Calendar monthCalendar = (Calendar) calendar.clone();
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1);
        int FirstDayofMonth = monthCalendar.get(Calendar.DAY_OF_WEEK) - 1;
        monthCalendar.add(Calendar.DAY_OF_MONTH, -FirstDayofMonth);
        CollectEventsPerMonth(monthFormat.format(calendar.getTime()), yearFormat.format(calendar.getTime()));

        while (dates.size() < MAX_CALENDAR_DAYS) {
            dates.add(monthCalendar.getTime());
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        myGridAdapter = new MyGridAdapter(context, dates, calendar, eventsList);
        gridView.setAdapter(myGridAdapter);
    }

    private void CollectEventsPerMonth(String Month, String year) {
        eventsList.clear();
    }

}
