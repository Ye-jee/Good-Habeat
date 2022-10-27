package com.example.goodhabeat_view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MotionEventCompat;
import androidx.viewpager.widget.ViewPager;

public class SwipeableViewPager extends ViewPager {

    private static final int OFF_SET = 10;
    private float preX;

    public SwipeableViewPager(@NonNull Context context) {
        super(context);
    }

    public SwipeableViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        final int action = ev.getAction() & MotionEventCompat.ACTION_MASK;

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                preX = ev.getX();
                break;

            case MotionEvent.ACTION_MOVE:
                float x = ev.getX();
                if(x - OFF_SET <= preX && preX <= x + OFF_SET){
                    return false;       // 하위 View 로 이벤트를 전달하는 의미
                }
                else {
                    return true;        //이벤트를 가져온다는 의미
                }
        }
        return super.onInterceptTouchEvent(ev);

    }
}
