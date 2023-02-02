package com.example.goodhabeat_view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class CategoryDiet_DetailMenuData_RecyclerViewAdapter extends RecyclerView.Adapter<CategoryDiet_DetailMenuData_RecyclerViewAdapter.ViewHolder>{
    Context itemContext;
    ArrayList<CategoryDiet_DetailMenuData> data;

    Bitmap bitmap;

    public CategoryDiet_DetailMenuData_RecyclerViewAdapter(Context itemContext, ArrayList<CategoryDiet_DetailMenuData> data) {
        this.itemContext = itemContext;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_diet_details_menu, parent, false);

        CategoryDiet_DetailMenuData_RecyclerViewAdapter.ViewHolder category_detailMenu_viewHolder = new CategoryDiet_DetailMenuData_RecyclerViewAdapter.ViewHolder(view);

        return category_detailMenu_viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final CategoryDiet_DetailMenuData item = data.get(position);
        setURLImage(item.getMenu_detail_img(), holder.menuDetail_img); // 이미지 세팅
        holder.menuDetail_title.setText(item.getMenu_detail_Title());
        holder.menuDetail_information.setText(item.getMenu_detail_Information());
        holder.menuDetail_calories.setText(item.getMenu_detail_Calories());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView menuDetail_img;
        private TextView menuDetail_title;
        private TextView menuDetail_information;
        private TextView menuDetail_calories;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            menuDetail_img = (ImageView) itemView.findViewById(R.id.menu_detail_img);
            menuDetail_title = (TextView) itemView.findViewById(R.id.menu_detail_title);
            menuDetail_information = (TextView) itemView.findViewById(R.id.menu_detail_information);
            menuDetail_calories = (TextView) itemView.findViewById(R.id.menu_detail_calories);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    Intent intent = new Intent(itemView.getContext(), RecipeActivity.class);
                    intent.putExtra("menuName", data.get(position).getMenu_detail_Title());
                    itemContext.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                }
            });

        }
    }

    // 이미지
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
