package com.example.goodhabeat_view;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CommunityWritingActivity extends AppCompatActivity {
    SharedPreferences preferences;
    RequestQueue requestQueue;

    String id_get;

    Button postBtn;
    EditText et_writeContent;

    Date currentTime = Calendar.getInstance().getTime();
    public static String todayDateFormat = "yyyy-MM-dd";

    //이미지 올리기 -------------
    private static final String TAG = "Upload ###";

    private static int IMAGE_REQ = 1;
    private Uri imagePath;

    private ImageView cameraButton, selectCamera;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_writing);

        getSupportActionBar().hide();

        postBtn = (Button)findViewById(R.id.postBtn);
        et_writeContent = (EditText)findViewById(R.id.et_writeContent);
        cameraButton = (ImageView)findViewById(R.id.cameraButton);
        selectCamera = (ImageView)findViewById(R.id.selectCamera);

        //--------------------------------------------------------------------------------------------------------------------------------------------------------
        //프리퍼런스로 로그인한 닉네임 가져오기
        preferences = getApplicationContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String nickname_get = preferences.getString("nickname", "nickname 오류" );
        System.out.println(nickname_get);
        //--------------------------------------------------------------------------------------------------------------------------------------------------------
        //시간
        SimpleDateFormat todayFormat = new SimpleDateFormat(todayDateFormat, Locale.getDefault());
        String clock = todayFormat.format(currentTime);
        System.out.println("작성 날짜: "+clock);
        //--------------------------------------------------------------------------------------------------------------------------------------------------------
        //닉네임 가져오기
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
                            JSONArray jsonarray = new JSONArray(response);
                            //JSONArray jArray = mainObj.getJSONArray("");
                            JSONObject setting_json = jsonarray.getJSONObject(0);
                            id_get = setting_json.getString("id");
                            System.out.println("유저 아이디: "+id_get);

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
        //--------------------------------------------------------------------------------------------------------------------------------------------------------
       /*
        //사진 선택
        selectCamera.setOnClickListener(v -> {
            /*
             * 1-1. ask the user to give the media permission
             * 1-2. moving to the gallery


            // 1-1
            requestPermission();

        });
        */
        cameraButton.setOnClickListener(v -> {
            requestPermission();
            MediaManager.get().upload(imagePath).callback(new UploadCallback() {
                @Override
                public void onStart(String requestId) {
                    Log.d(TAG, "onStart : " + "started");
                }

                @Override
                public void onProgress(String requestId, long bytes, long totalBytes) {
                    Log.d(TAG, "onStart : " + "uploading");
                }

                @Override
                public void onSuccess(String requestId, Map resultData) {
                    Log.d(TAG, "onStart : " + "success");
                }

                @Override
                public void onError(String requestId, ErrorInfo error) {
                    Log.d(TAG, "onStart : " + error);
                }

                @Override
                public void onReschedule(String requestId, ErrorInfo error) {
                    Log.d(TAG, "onStart : " + error);
                }
            }).dispatch();
        });

        initConfig();

//--------------------------------------------------------------------------------------------------------------------------------------------------------
        //글작성 버튼
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = et_writeContent .getText().toString();

                String url_commu_wirte = "http://10.0.2.2:3000/commu_wirte";
                requestQueue = Volley.newRequestQueue(getApplicationContext());
                //닉네임 존재 확인
                StringRequest request = new StringRequest(
                        Request.Method.POST,
                        url_commu_wirte,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(getApplicationContext(), "등록되었습니다.", Toast.LENGTH_SHORT).show();
                                finish();
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

                        params.put("user_id", id_get);
                        params.put("content", content);
                        params.put("post_date", clock);
                        params.put("post_image", clock);

                        return params;
                    }
                };
                requestQueue.add(request);

            }
        }); //셋 클릭 끝.



    }

    private void initConfig() {
        Map config = new HashMap();
        config.put("cloud_name", "goodhabeat");
        config.put("api_key", "631594594435193");
        config.put("api_secret", "2YNh2GmCs2CQ8NFu6JS3QONbZDg");
        //config.put("secure", true);
        MediaManager.init(this, config);
    }

    private void requestPermission() {
        if(ContextCompat.checkSelfPermission(CommunityWritingActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED)
        {
            selectImage();
        } else {
            ActivityCompat.requestPermissions(CommunityWritingActivity.this, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, IMAGE_REQ);
        }

    }
    /*
     * select the image from the gallery
     */
    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*"); // if you want to you can use pdf/gif/video
        intent.setAction(Intent.ACTION_GET_CONTENT);
        someActivityResultLauncher.launch(intent);
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        imagePath = data.getData();
                        Picasso.get().load(imagePath).into(selectCamera);
                    }
                }
            }
    );
}

