package com.example.goodhabeat_view;

public class DietChangeListData {
    String dietChange_menuName;
    String dietChange_menuKcal;

    Double carbohydrate;
    Double protein;
    Double fat;
    Double calorie;

    public DietChangeListData(String dietChange_menuName, String dietChange_menuKcal, Double carbohydrate, Double protein, Double fat, Double calorie) {
        this.dietChange_menuName = dietChange_menuName;
        this.dietChange_menuKcal = dietChange_menuKcal;
        this.carbohydrate = carbohydrate;
        this.protein = protein;
        this.fat = fat;
        this.calorie = calorie;
    }

    public String getDietChange_menuName() { return dietChange_menuName; }
    public void setDietChange_menuName(String dietChange_menuName) { this.dietChange_menuName = dietChange_menuName; }

    public String getDietChange_menuKcal() { return dietChange_menuKcal; }
    public void setDietChange_menuKcal(String dietChange_menuKcal) { this.dietChange_menuKcal = dietChange_menuKcal; }

    public Double getCarbohydrate() { return carbohydrate; }
    public void setCarbohydrate(Double carbohydrate) { this.carbohydrate = carbohydrate; }

    public Double getProtein() { return protein; }
    public void setProtein(Double protein) { this.protein = protein; }

    public Double getFat() { return fat; }
    public void setFat(Double fat) { this.fat = fat; }

    public Double getCalorie() { return calorie; }
    public void setCalorie(Double calorie) { this.calorie = calorie; }

}

