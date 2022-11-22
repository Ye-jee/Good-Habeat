package com.example.goodhabeat_view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DietChangeListRecyclerViewAdapter extends RecyclerView.Adapter<DietChangeListRecyclerViewAdapter.ViewHolder>{

    ArrayList<DietChangeListData> data;

    public DietChangeListRecyclerViewAdapter(ArrayList<DietChangeListData> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_diet_change_list2, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final DietChangeListData item = data.get(position);
        holder.dietChange_menuName.setText(item.getDietChange_menuName());
        holder.dietChange_menuKcal.setText(item.getDietChange_menuKcal());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView dietChange_menuName;
        private TextView dietChange_menuKcal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dietChange_menuName = (TextView) itemView.findViewById(R.id.dietChange_menu_name);
            dietChange_menuKcal = (TextView) itemView.findViewById(R.id.dietChange_menu_kcal);
        }
    }
}
