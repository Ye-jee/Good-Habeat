package com.example.goodhabeat_view;

public class CategoryMenuData {

    int menu_Image;
    String menu_Title;
    String menu_Food;
    String menu_Information;
    String menu_Calories;
    String menu_Disease;


    public CategoryMenuData(int menu_Image, String menu_Title, String menu_Food, String menu_Information, String menu_Calories, String menu_Disease) {
        this.menu_Image = menu_Image;
        this.menu_Title = menu_Title;
        this.menu_Food = menu_Food;
        this.menu_Information = menu_Information;
        this.menu_Calories = menu_Calories;
        this.menu_Disease = menu_Disease;
    }

    public int getMenu_Image() {
        return menu_Image;
    }

    public String getMenu_Title() {
        return menu_Title;
    }

    public String getMenu_Food() {
        return menu_Food;
    }

    public String getMenu_Information() {
        return menu_Information;
    }

    public String getMenu_Calories() {
        return menu_Calories;
    }

    public String getMenu_Disease() { return menu_Disease; }
}

