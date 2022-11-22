package com.example.goodhabeat_view;

public class MenuSelectData {
    int menu_img;
    String menu_name;
    String menu_information;
    String menu_kcal;

    public MenuSelectData(int menu_img, String menu_name, String menu_information, String menu_kcal) {
        this.menu_img = menu_img;
        this.menu_name = menu_name;
        this.menu_information = menu_information;
        this.menu_kcal = menu_kcal;
    }

    public int getMenu_img() {
        return menu_img;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public String getMenu_information() {
        return menu_information;
    }

    public String getMenu_kcal() {
        return menu_kcal;
    }
}
