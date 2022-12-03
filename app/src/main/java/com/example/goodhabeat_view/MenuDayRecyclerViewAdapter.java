package com.example.goodhabeat_view;

import static android.content.Context.MODE_PRIVATE;
import static com.example.goodhabeat_view.LoginActivity.requestQueue;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cloudinary.android.MediaManager;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MenuDayRecyclerViewAdapter extends RecyclerView.Adapter<MenuDayRecyclerViewAdapter.ViewHolder> {
    Context itemContext;
    ArrayList<MenuDayData> data;

    Bitmap bitmap;

    public MenuDayRecyclerViewAdapter(Context itemContext, ArrayList<MenuDayData> data) {
        this.itemContext = itemContext;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu_day_test, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MenuDayData item = data.get(position);
        setURLImage(item.getMenuPicUrl(), holder.menuImage); // 이미지 세팅
        holder.menuName.setText(item.getMenuName());
        holder.menuCal.setText(item.getMenuCal());
        holder.menuCarbo.setText(item.getMenuCarbo());
        holder.menuProtein.setText(item.getMenuProtein());
        holder.menuFat.setText(item.getMenuFat());

    }

    @Override
    public int getItemCount() { return data.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView menuImage;
        private TextView menuName;
        private TextView menuCal;
        private TextView menuCarbo;
        private TextView menuProtein;
        private TextView menuFat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            menuImage = (ImageView) itemView.findViewById(R.id.menu_pic_day);
            menuName = (TextView) itemView.findViewById(R.id.menu_name);
            menuCal = (TextView) itemView.findViewById(R.id.menu_cal);
            menuCarbo = (TextView) itemView.findViewById(R.id.menu_carbo);
            menuProtein = (TextView) itemView.findViewById(R.id.menu_protein);
            menuFat = (TextView) itemView.findViewById(R.id.menu_fat);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    Intent intent = new Intent(itemView.getContext(), RecipeActivity.class);
                    intent.putExtra("menuName", data.get(position).getMenuName());
                    itemContext.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                }
            });

            // 먹었어요 버튼
            TextView eat_done = (TextView) itemView.findViewById(R.id.eat_done);
            eat_done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(itemView.getContext());
                    dlg.setMessage("맛있게 드셨나요?");
                    dlg.setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            eat_done.setTextColor(Color.parseColor("#2E8B57"));

                            // Volley
                            String url = "http://10.0.2.2:3000/check_done";
                            requestQueue = Volley.newRequestQueue(itemView.getContext());

                            SharedPreferences preferences;
                            preferences = itemView.getContext().getSharedPreferences("userInfo", MODE_PRIVATE);
                            String user_id = preferences.getString("user_id","");

                            Date systemDate = Calendar.getInstance().getTime();
                            SimpleDateFormat currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                            String date = currentDate.format(systemDate);

                            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                            String time = currentTime.format(systemDate);

                            int position = getAdapterPosition();
                            String meal = data.get(position).getMeal();
                            String menu_name = data.get(position).getMenuName();

                            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try{
                                                System.out.println("response : " + response);
                                            }catch (Exception e){ e.printStackTrace(); }
                                            Toast.makeText(itemView.getContext(), "데이터 전송 완료", Toast.LENGTH_SHORT).show();
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(itemView.getContext(), "ERROR : " + error.getMessage(), Toast.LENGTH_SHORT).show();
                                            System.out.println(error.getMessage());
                                        }
                                    }
                            ) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<>();
                                    params.put("user_id", user_id);
                                    params.put("menu_name", menu_name);
                                    params.put("date", date);
                                    params.put("time", time);
                                    params.put("meal", meal);

                                    return params;
                                }
                            };

                            requestQueue.add(request);
                        }
                    });
                    dlg.setNegativeButton("취소", null);
                    dlg.show();
                }
            });
        }
    }

    // 외부 함수
    private void setURLImage(String recipe_image, ImageView menuImage) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    bitmap = getBitmap(recipe_image);
                }catch (Exception e){
                    e.printStackTrace();
                } finally {
                    if(bitmap != null) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                menuImage.setImageBitmap(bitmap);
                            }
                        });
                    }
                }
            }
        }).start();
    }

    private Bitmap getBitmap(String url) {
        URL imgUrl = null;
        HttpURLConnection connection = null;
        InputStream is = null;

        Bitmap retBitmap = null;

        try{
            imgUrl = new URL(url);
            connection = (HttpURLConnection) imgUrl.openConnection();
            connection.setDoInput(true); //url로 input받는 flag 허용
            connection.connect(); //연결
            is = connection.getInputStream();
            retBitmap = BitmapFactory.decodeStream(is);

        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            if(connection!=null) {
                connection.disconnect();
            }
            return retBitmap;
        }
    }

}
