package com.example.goodhabeat_view;

public class Menu {
    String food_name;
    String food_description;
    int food_calories, carbohydrate, protein, fat;


    public Menu(String food_name, int food_calories) {
        this.food_name = food_name;
        this.food_description = food_description;
        this.food_calories = food_calories;
        this.carbohydrate = carbohydrate;
        this.protein = protein;
        this.fat = fat;
    }

    public String getFood_name() {
        return food_name;
    }

    public String getFood_description() {
        return food_description;
    }

    public int getFood_calories() {
        return food_calories;
    }

    public int getCarbohydrate() {
        return carbohydrate;
    }

    public int getProtein() {
        return protein;
    }

    public int getFat() {
        return fat;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public void setFood_description(String food_description) {
        this.food_description = food_description;
    }

    public void setFood_calories(int food_calories) {
        this.food_calories = food_calories;
    }

    public void setCarbohydrate(int carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }
}
