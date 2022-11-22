package com.example.goodhabeat_view;

public class CategoryDietData {

    int diet_Image;
    String diet_Title;
    String diet_Food;
    String diet_Information;
    String diet_Calories;
    String diet_Disease;


    public CategoryDietData(int diet_Image, String diet_Title, String diet_Food, String diet_Information, String diet_Calories, String diet_Disease) {
        this.diet_Image = diet_Image;
        this.diet_Title = diet_Title;
        this.diet_Food = diet_Food;
        this.diet_Information = diet_Information;
        this.diet_Calories = diet_Calories;
        this.diet_Disease = diet_Disease;
    }

    public int getDiet_Image() {
        return diet_Image;
    }

    public String getDiet_Title() {
        return diet_Title;
    }

    public String getDiet_Food() {
        return diet_Food;
    }

    public String getDiet_Information() {
        return diet_Information;
    }

    public String getDiet_Calories() {
        return diet_Calories;
    }

    public String getDiet_Disease() {
        return diet_Disease;
    }
}

