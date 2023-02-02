package com.example.goodhabeat_view;

import static android.content.Context.MODE_PRIVATE;
import static com.example.goodhabeat_view.LoginActivity.requestQueue;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CommunityRecyclerViewAdapter extends RecyclerView.Adapter<CommunityRecyclerViewAdapter.ViewHolder>{

    int heart_on = 1;
    Context itemContext;
    ArrayList<CommunityData> data;

    Bitmap bitmap;


    public CommunityRecyclerViewAdapter(Context itemContext, ArrayList<CommunityData> data) {
        this.itemContext = itemContext;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_community, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final CommunityData item = data.get(position);
        holder.post_Id.setText(item.getCommunity_post_num());
        holder.user_nikName.setText(item.getCommunity_user_nikName());
        holder.create_date.setText(item.getCommunity_create_date());
        holder.create_time.setText(item.getCommunity_create_time());
        holder.content_text.setText(item.getCommunity_content_text());
        setURLImage(item.getCommunity_content_img(), holder.content_img); //이미지 세팅
        //holder.content_img.setImageResource(item.getCommunity_content_img());
       // holder.heart_img.setImageResource(item.getCommunity_heart_img());

        if(heart_on == 1)
            {holder.heart_img.setImageResource(R.drawable.community_heart_fil);}
        else
            {holder.heart_img.setImageResource(R.drawable.community_heart_empty);}

        holder.heart_number.setText(item.getCommunity_heart_number());
        holder.delete_text.setText(item.getCommunity_delete_text());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView post_Id;
        private TextView user_nikName;
        private TextView create_date;
        private TextView create_time;
        private TextView content_text;
        private ImageView content_img; //이미지
        private ImageView heart_img;
        private TextView heart_number;
        private TextView delete_text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            post_Id = (TextView) itemView.findViewById(R.id.post_Id);
            user_nikName = (TextView) itemView.findViewById(R.id.commu_user_nicName);
            create_date = (TextView) itemView.findViewById(R.id.commu_create_date);
            create_time = (TextView) itemView.findViewById(R.id.commu_create_time);
            content_text = (TextView) itemView.findViewById(R.id.commu_content_text);
            content_img = (ImageView) itemView.findViewById(R.id.commu_content_img);
            heart_img = (ImageView) itemView.findViewById(R.id.commu_heart_img);
            heart_number = (TextView) itemView.findViewById(R.id.commu_heart_number);
            delete_text = (TextView) itemView.findViewById(R.id.commu_delete_text);
            SharedPreferences preferences;

            //--------------------------------------------------------------------------------------------------------------------------------------------------------
            preferences = itemView.getContext().getSharedPreferences("userInfo", MODE_PRIVATE);
            String user_nickname = preferences.getString("nickname","");
            String userId = preferences.getString("user_id","");
            //--------------------------------------------------------------------------------------------------------------------------------------------------------

            String id_post = (String) post_Id.getText();
            System.out.println("글 아이디: "+id_post);

            heart_img.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    String post_nick = data.get(position).getCommunity_user_nikName();

                    if(!user_nickname.equals(post_nick)){
                        // Volley
                        String commu_post = "http://10.0.2.2:3000/commu_heart";
                        requestQueue = Volley.newRequestQueue(itemView.getContext());

                        Request request = new StringRequest(Request.Method.POST,
                                commu_post, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try{
                                    System.out.println("하트 누르기 성공 : " + response);
                                    String state = "1";
                                    ((CommunityActivity)CommunityActivity.mContext).VolleyCommuinty(state);
                                    if(response.equals("1")){
                                        heart_on = 1;
                                    } else {
                                        heart_on = 2;
                                    }
                                }catch (Exception e){ e.printStackTrace(); }
                                System.out.println("heart_on: " + heart_on);
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
                                params.put("post_id", data.get(position).getCommunity_post_num());
                                params.put("user_id", userId);

                                return params;
                            }
                        };

                        requestQueue.add(request);

                    } else {

                    }
                    String state = "1";
                    ((CommunityActivity)CommunityActivity.mContext).VolleyCommuinty(state);
                }
            });




        }//ViewHolder(@NonNull View itemView) 끝
    }//ViewHolder 끝

    // 외부 함수
    private void setURLImage(String user_image, ImageView userImage) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    bitmap = getBitmap(user_image);
                }catch (Exception e){
                    e.printStackTrace();
                } finally {
                    if(bitmap != null) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                userImage.setImageBitmap(bitmap);
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
