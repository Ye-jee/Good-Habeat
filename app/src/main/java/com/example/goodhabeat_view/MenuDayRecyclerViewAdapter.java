package com.example.goodhabeat_view;

import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MenuDayRecyclerViewAdapter extends RecyclerView.Adapter<MenuDayRecyclerViewAdapter.ViewHolder> {
    ArrayList<com.example.goodhabeat_view.MenuDayData> data;

    public MenuDayRecyclerViewAdapter(ArrayList<com.example.goodhabeat_view.MenuDayData> data) { this.data = data; }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu_day, parent, false);

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
        final com.example.goodhabeat_view.MenuDayData item = data.get(position);
        holder.menuPicId.setImageResource(item.getMenuPicId());
        holder.menuName.setText(item.getMenuName());
        holder.menuText.setText(item.getMenuText());
    }

    @Override
    public int getItemCount() { return data.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView menuPicId;
        private TextView menuName;
        private TextView menuText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            menuPicId = (ImageView) itemView.findViewById(R.id.menu_pic);
            menuName = (TextView) itemView.findViewById(R.id.menu_name);
            menuText = (TextView) itemView.findViewById(R.id.menu_text);
        }

    }

}
