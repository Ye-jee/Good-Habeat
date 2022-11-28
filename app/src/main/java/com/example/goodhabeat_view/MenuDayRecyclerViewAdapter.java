package com.example.goodhabeat_view;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
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
import androidx.recyclerview.widget.RecyclerView;

import com.cloudinary.android.MediaManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MenuDayRecyclerViewAdapter extends RecyclerView.Adapter<MenuDayRecyclerViewAdapter.ViewHolder> {
    Context itemContext;
    ArrayList<MenuDayData> data;

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
        holder.menuPicUrl.setText(item.getMenuPicUrl());
        holder.menuName.setText(item.getMenuName());
        holder.menuCal.setText(item.getMenuCal());
        holder.menuCarbo.setText(item.getMenuCarbo());
        holder.menuProtein.setText(item.getMenuProtein());
        holder.menuFat.setText(item.getMenuFat());
    }

    @Override
    public int getItemCount() { return data.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView menuPicUrl;
        private TextView menuName;
        private TextView menuCal;
        private TextView menuCarbo;
        private TextView menuProtein;
        private TextView menuFat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            menuPicUrl = (TextView) itemView.findViewById(R.id.menu_pic_day);
            //Uri imagePath = data.getData();
            //Picasso.get().load(imagePath).into(menuPicUrl);
            menuName = (TextView) itemView.findViewById(R.id.menu_name);
            menuCal = (TextView) itemView.findViewById(R.id.menu_cal);
            menuCarbo = (TextView) itemView.findViewById(R.id.menu_carbo);
            menuProtein = (TextView) itemView.findViewById(R.id.menu_protein);
            menuFat = (TextView) itemView.findViewById(R.id.menu_fat);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), RecipeActivity.class);
                    //intent.putExtra("",MenuDayData.get(position).getMenuName());
                    itemContext.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                }
            });
        }

    }

    // 이미지
    /*
    private void initConfig() {
        Map config = new HashMap();
        config.put("cloud_name", "goodhabeat");
        config.put("api_key", "631594594435193");
        config.put("api_secret", "2YNh2GmCs2CQ8NFu6JS3QONbZDg");
        //config.put("secure", true);
        MediaManager.init(this, config);
    }
    */

    /*
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    // 이미지
                    Uri imagePath;

                    if(result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        imagePath = data.getData();
                        Picasso.get().load(imagePath).into();
                    }
                 }
            }
    );
    */

}
