package com.example.goodhabeat_view;

public class DietChangeListData {
    String dietChange_menuName;
    String dietChange_menuKcal;

    public DietChangeListData(String dietChange_menuName, String dietChange_menuKcal) {
        this.dietChange_menuName = dietChange_menuName;
        this.dietChange_menuKcal = dietChange_menuKcal;
    }

    public String getDietChange_menuName() {
        return dietChange_menuName;
    }

    public String getDietChange_menuKcal() {
        return dietChange_menuKcal;
    }
}


