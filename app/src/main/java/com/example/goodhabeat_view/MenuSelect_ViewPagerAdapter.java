package com.example.goodhabeat_view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class MenuSelect_ViewPagerAdapter extends FragmentPagerAdapter {

    int tab_index;

    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    private ArrayList<String> name = new ArrayList<>();

    public MenuSelect_ViewPagerAdapter(@NonNull FragmentManager fm, int tab_index) {
        super(fm);
        this.tab_index = tab_index;

        fragmentArrayList.add(new MenuFragment_rice());
        fragmentArrayList.add(new MenuFragment_soup());
        fragmentArrayList.add(new MenuFragment_kimchi());
        fragmentArrayList.add(new MenuFragment_sideDish());
        fragmentArrayList.add(new MenuFragment_aBowl());

        name.add("밥");
        name.add("국/탕");
        name.add("김치");
        name.add("반찬");
        name.add("한그릇");
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
