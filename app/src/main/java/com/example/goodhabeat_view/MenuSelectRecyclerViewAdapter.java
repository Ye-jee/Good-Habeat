package com.example.goodhabeat_view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MenuSelectRecyclerViewAdapter extends RecyclerView.Adapter<MenuSelectRecyclerViewAdapter.ViewHolder>{

    ArrayList<MenuSelectData> data;

    public MenuSelectRecyclerViewAdapter(ArrayList<MenuSelectData> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu_select, parent, false);

        ViewHolder menuSelect_viewHolder = new ViewHolder(view);

        //클릭 이벤트 구현을 위해 추가된 코드
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = "";
                int position = menuSelect_viewHolder.getAdapterPosition();
                //int position = new ViewHolder(view).getAdapterPosition();

                if(position != RecyclerView.NO_POSITION) {
                    //
                    data = menuSelect_viewHolder.getMenu_name().getText().toString();
                }
                itemClickListener.onItemClicked(position, data);
            }
        });


        return menuSelect_viewHolder;
        //return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MenuSelectData item = data.get(position);
        holder.menu_img.setImageResource(item.getMenu_img());
        holder.menu_name.setText(item.getMenu_name());
        holder.menu_information.setText(item.getMenu_information());
        holder.menu_kcal.setText(item.getMenu_kcal());

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
        private ImageView menu_img;
        private TextView menu_name;
        private TextView menu_information;
        private TextView menu_kcal;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            menu_img = (ImageView) itemView.findViewById(R.id.menu_img);
            menu_name = (TextView) itemView.findViewById(R.id.menu_name);
            menu_information = (TextView) itemView.findViewById(R.id.menu_information);
            menu_kcal = (TextView) itemView.findViewById(R.id.menu_calories);


            //우선 주석처리함
            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        //클릭시 발생하는 이벤트 코드 작성

                        //getAdapterPosition()은 클릭된 아이템 위치 가져옴(0번 부터 시작함)
                        Toast.makeText(view.getContext(), pos + "번째 메뉴 선택함!", Toast.LENGTH_SHORT).show();


                    }

                }
            });*/

        }

        public TextView getMenu_name() {
            return menu_name;
        }
    }
}
