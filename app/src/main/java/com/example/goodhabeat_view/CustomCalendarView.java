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
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class CustomCalendarView extends LinearLayout {

    ImageButton NextButton, PreviousButton;
    TextView CurrentDate;
    GridView gridView;

    TextView tvToday;
    TextView menu_break, menu_lunch, menu_dinner;
    TextView time_break, time_lunch, time_dinner;
    ImageView check_break, check_lunch, check_dinner;
    String txtMenuBreak, txtMenuLunch, txtMenuDinner;

    ProgressBar cal_progress, carbo_progress, protein_progress, fat_progress;
    TextView tv_cal_num, tv_carbo_num, tv_protein_num, tv_fat_num;

    private static final int MAX_CALENDAR_DAYS = 42;
    Calendar calendar = Calendar.getInstance(Locale.KOREA);
    Context context;
    SimpleDateFormat dateFormat_kor = new SimpleDateFormat("yyyy년 MMM", Locale.KOREA);
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy", Locale.KOREA);
    SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM", Locale.KOREA);
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);
    SimpleDateFormat eventDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
    SimpleDateFormat dayOfMonthFormat = new SimpleDateFormat("d", Locale.KOREA);

    MyGridAdapter myGridAdapter;
    AlertDialog alertDialog;
    List<Date> dates = new ArrayList<>();
    List<Events> eventsList = new ArrayList<>();

    SharedPreferences preferences;
    DBOpenHelper dbOpenHelper;

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


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder= new AlertDialog.Builder(context);
                builder.setCancelable(true);

                final String date = eventDateFormat.format(dates.get(position));
                final String month = monthFormat.format(dates.get(position));
                final String year = yearFormat.format(dates.get(position));
                final String dayOfMonth = dayOfMonthFormat.format(dates.get(position));

                // 날짜 정보 저장 및 토스트 출력 (Preferences)
                preferences = getContext().getSharedPreferences("dateInfo", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("todayMonth", month);
                editor.putString("todayDate", dayOfMonth);
                editor.commit();

                String m = preferences.getString("todayMonth", month);
                String d = preferences.getString("todayDate", dayOfMonth);
                //Toast.makeText(getContext(), m + " " + d + "일", Toast.LENGTH_LONG).show();


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


                // 날짜 변경
                tvToday.setText(m + " " + d + "일");
                
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
