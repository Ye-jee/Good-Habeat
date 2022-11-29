package com.example.goodhabeat_view;

public class CategoryDiet_DetailMenuData {

    int menu_detail_img;
    String menu_detail_Title;
    String menu_detail_Information;
    String menu_detail_Calories;

    public CategoryDiet_DetailMenuData(int menu_detail_img, String menu_detail_Title, String menu_detail_Information, String menu_detail_Calories) {
        this.menu_detail_img = menu_detail_img;
        this.menu_detail_Title = menu_detail_Title;
        this.menu_detail_Information = menu_detail_Information;
        this.menu_detail_Calories = menu_detail_Calories;
    }


    public int getMenu_detail_img() {
        return menu_detail_img;
    }

    public String getMenu_detail_Title() {
        return menu_detail_Title;
    }

    public String getMenu_detail_Information() {
        return menu_detail_Information;
    }

    public String getMenu_detail_Calories() {
        return menu_detail_Calories;
    }
}
