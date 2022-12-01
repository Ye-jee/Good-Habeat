package com.example.goodhabeat_view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoryDiet_DetailMenuData_RecyclerViewAdapter extends RecyclerView.Adapter<CategoryDiet_DetailMenuData_RecyclerViewAdapter.ViewHolder>{

    ArrayList<CategoryDiet_DetailMenuData> data;

    public CategoryDiet_DetailMenuData_RecyclerViewAdapter(ArrayList<CategoryDiet_DetailMenuData> data) {
        this.data = data;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_diet_details_menu, parent, false);

        CategoryDiet_DetailMenuData_RecyclerViewAdapter.ViewHolder category_detailMenu_viewHolder = new CategoryDiet_DetailMenuData_RecyclerViewAdapter.ViewHolder(view);

        //클릭 이벤트 구현을 위해 추가된 코드
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = "";
                int position = category_detailMenu_viewHolder.getAdapterPosition();

                if(position != RecyclerView.NO_POSITION) {  //순서가 없지 않을 때 즉 그냥 아이템을 클릭할 시에
                    data = category_detailMenu_viewHolder.getMenuDetail_title().getText().toString();
                }
                itemClickListener.onItemClicked(position, data);
            }
        });

        return category_detailMenu_viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final CategoryDiet_DetailMenuData item = data.get(position);
        holder.menuDetail_img.setImageResource(item.getMenu_detail_img());
        holder.menuDetail_title.setText(item.getMenu_detail_Title());
        holder.menuDetail_information.setText(item.getMenu_detail_Information());
        holder.menuDetail_calories.setText(item.getMenu_detail_Calories());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }



    //클릭 이벤트 구현을 위해 추가된 코드
    //OnItemClickListener 인터페이스 선언
    public interface OnItemClickListener {
        void onItemClicked(int postion, String data);
    }

    //OnItemClickListener 참조 변수 선언
    private OnItemClickListener itemClickListener;

    //OnItemClickListener 전달 메소드
    public void setOnItemClickListener (OnItemClickListener listener) {
        itemClickListener = listener;
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
        }


        public TextView getMenuDetail_title() {
            return menuDetail_title;
        }
    }


}
