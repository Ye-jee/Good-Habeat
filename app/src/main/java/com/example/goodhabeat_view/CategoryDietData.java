package com.example.goodhabeat_view;

public class CategoryDietData {
    String diet_Title;
    String diet_Food;
    String diet_Carbo;
    String diet_Protein;
    String diet_Fat;
    String diet_Calories;
    String diet_Disease;

    public CategoryDietData(String diet_Title, String diet_Food, String diet_Carbo, String diet_Protein, String diet_Fat, String diet_Calories, String diet_Disease) {
        this.diet_Title = diet_Title;
        this.diet_Food = diet_Food;
        this.diet_Carbo = diet_Carbo;
        this.diet_Protein = diet_Protein;
        this.diet_Fat = diet_Fat;
        this.diet_Calories = diet_Calories;
        this.diet_Disease = diet_Disease;
    }

    public String getDiet_Title() { return diet_Title; }

    public String getDiet_Food() { return diet_Food; }

    public String getDiet_Carbo() { return diet_Carbo; }

    public String getDiet_Protein() { return diet_Protein; }

    public String getDiet_Fat() { return diet_Fat; }

    public String getDiet_Calories() { return diet_Calories; }

    public String getDiet_Disease() { return diet_Disease; }

}

