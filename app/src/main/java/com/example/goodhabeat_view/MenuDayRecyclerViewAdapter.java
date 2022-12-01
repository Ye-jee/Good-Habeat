package com.example.goodhabeat_view;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.cloudinary.android.MediaManager;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
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

        TextView eat_done = (TextView) view.findViewById(R.id.eat_done);
        TextView eat_dont = (TextView) view.findViewById(R.id.eat_dont);

        // '먹었어요!' 버튼
        eat_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eat_done.setTextColor(Color.parseColor("#2E8B57"));
                if (eat_dont.getCurrentTextColor() == Color.parseColor("#2E8B57")) {
                    eat_dont.setTextColor(Color.parseColor("#A5A5A5"));
                }
            }
        });

        // '못먹었어요' 버튼
        eat_dont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View dialogView = (View) View.inflate(view.getContext(), R.layout.dont_eat, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(view.getContext());
                //  dlg.setTitle("CHECK IN");
                dlg.setView(dialogView);
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        eat_dont.setTextColor(Color.parseColor("#2E8B57"));
                        //bf_donteat.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
                        if (eat_done.getCurrentTextColor() == Color.parseColor("#2E8B57")) {
                            eat_done.setTextColor(Color.parseColor("#A5A5A5"));
                        }
                    }
                });
                dlg.show();
            }
        });

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
