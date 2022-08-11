package com.example.goodhabeat_view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class MenuWeekAdapter extends RecyclerView.Adapter<MenuWeekAdapter.ViewHolder> {
    ArrayList<MenuWeekData> data;

    public MenuWeekAdapter(ArrayList<MenuWeekData> data) { this.data = data; }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu_week, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuWeekAdapter.ViewHolder holder, int position) {
        final MenuWeekData item = data.get(position);
        holder.menuPicId.setImageResource(item.getMenuPicId());
        holder.menuName.setText(item.getMenuName());
        holder.menuText.setText(item.getMenuText());
    }

    @Override
    public int getItemCount() { return data.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RoundedImageView menuPicId;
        private TextView menuName;
        private TextView menuText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            menuPicId = (RoundedImageView) itemView.findViewById(R.id.menu_pic);
            menuName = (TextView) itemView.findViewById(R.id.menu_name);
            menuText = (TextView) itemView.findViewById(R.id.menu_text);
        }

    }
}
