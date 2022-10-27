package com.example.goodhabeat_view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    int tab_index;

    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    private ArrayList<String> name = new ArrayList<>();

    public ViewPagerAdapter(@NonNull FragmentManager fm, int tab_index) {
        super(fm);
        this.tab_index = tab_index;

        fragmentArrayList.add(new DietFragment_convenience());
        fragmentArrayList.add(new DietFragment_highProtein());
        fragmentArrayList.add(new DietFragment_lowCalories());
        fragmentArrayList.add(new DietFragment_lowFat());
        fragmentArrayList.add(new DietFragment_lowSalt());
        fragmentArrayList.add(new DietFragment_lowSugar());

        name.add("간편식");
        name.add("고단백질");
        name.add("저칼로리");
        name.add("저지방");
        name.add("저염");
        name.add("저당");
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return name.get(position);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }
}
