package com.example.goodhabeat_view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class ImagePagerAdapter extends PagerAdapter {

    private int[] images = {R.drawable.food01, R.drawable.food02, R.drawable.food03, R.drawable.food04, R.drawable.food05};

    private LayoutInflater inflater;
    private Context context;

    public ImagePagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.image_pager_item, container, false);

        ImageView imageView  = view.findViewById(R.id.imageView_pager);
        //TextView textView = view.findViewById(R.id.textView_practice);

        imageView.setImageResource(images[position]);
        //textView.setText((position+1) + "번 째 이미지입니다.");

        container.addView(view);
        return view;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.invalidate();
    }

}
