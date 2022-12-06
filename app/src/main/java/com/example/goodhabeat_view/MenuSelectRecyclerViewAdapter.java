package com.example.goodhabeat_view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MenuSelectRecyclerViewAdapter extends RecyclerView.Adapter<MenuSelectRecyclerViewAdapter.ViewHolder>{
    Context itemContext;
    ArrayList<MenuSelectData> data;

    ArrayList<Integer> selected_item_id = new ArrayList<>();
    ArrayList<Double> selected_item_carbo = new ArrayList<>();
    ArrayList<Double> selected_item_protein = new ArrayList<>();
    ArrayList<Double> selected_item_fat = new ArrayList<>();
    ArrayList<Double> selected_item_cal = new ArrayList<>();
    ArrayList<SelectedMenuItemData> intent_data = new ArrayList<>();

    Bitmap bitmap;

    public MenuSelectRecyclerViewAdapter(Context itemContext, ArrayList<MenuSelectData> data, ArrayList<SelectedMenuItemData> intent_data) {
        this.itemContext = itemContext;
        this.data = data;
        this.intent_data = intent_data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu_select, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MenuSelectData item = data.get(position);
        setURLImage(item.getMenuPicUrl(), holder.menuImage);
        holder.menuName.setText(item.getMenuName());
        holder.menuInfo.setText(item.getMenuInfo());
        holder.menuCal.setText(item.getMenuCal().toString() + " kcal");

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView menuImage;
        private TextView menuName;
        private TextView menuInfo;
        private TextView menuCal;
        private ConstraintLayout menuContainer;

        int check_cnt = 0;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            menuImage = (ImageView) itemView.findViewById(R.id.menu_img);
            menuName = (TextView) itemView.findViewById(R.id.menu_name);
            menuInfo = (TextView) itemView.findViewById(R.id.menu_information);
            menuCal = (TextView) itemView.findViewById(R.id.menu_calories);
            menuContainer = (ConstraintLayout) itemView.findViewById(R.id.menu_item_container);


            itemView.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    //Toast.makeText(itemContext.getApplicationContext(), position + "번째 선택, 메뉴 이름 :" + data.get(position).getMenuName() + "레시피 아이디 : " + data.get(position).getRecipeId(), Toast.LENGTH_SHORT).show();

                    intent_data.clear();

                    check_cnt++;
                    if (check_cnt % 2 == 1) {
                        // 아이템 추가
                        menuContainer.setBackgroundResource(R.drawable.round_rec_background4);
                        selected_item_id.add(data.get(position).getRecipeId());
                        selected_item_carbo.add(data.get(position).getMenuCarbo());
                        selected_item_protein.add(data.get(position).getMenuProtein());
                        selected_item_fat.add(data.get(position).getMenuFat());
                        selected_item_cal.add(data.get(position).getMenuCal());

                    } else {
                        // 아이템 삭제
                        menuContainer.setBackgroundResource(R.drawable.round_rec_background);
                        selected_item_id.remove(Integer.valueOf(data.get(position).getRecipeId()));
                        selected_item_carbo.remove(Double.valueOf(data.get(position).getMenuCarbo()));
                        selected_item_protein.remove(Double.valueOf(data.get(position).getMenuProtein()));
                        selected_item_fat.remove(Double.valueOf(data.get(position).getMenuFat()));
                        selected_item_cal.remove(Double.valueOf(data.get(position).getMenuCal()));
                    }

                    System.out.println("데이터 갯수 : " + selected_item_id.size());
                    for(int i=0; i<selected_item_id.size(); i++){
                        System.out.println("남은 데이터 : " + selected_item_id.get(i) + " /n -- /n");
                        SelectedMenuItemData dataSet = new SelectedMenuItemData(selected_item_id.get(i), selected_item_carbo.get(i), selected_item_protein.get(i), selected_item_fat.get(i), selected_item_cal.get(i));
                        intent_data.add(dataSet);
                    }

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

