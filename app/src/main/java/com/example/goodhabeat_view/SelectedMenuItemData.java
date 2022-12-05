package com.example.goodhabeat_view;

import java.io.Serializable;

public class SelectedMenuItemData implements Serializable {

    Integer item_index;
    Double carbohydrate;
    Double protein;
    Double fat;
    Double calorie;

    public SelectedMenuItemData(Integer item_index, Double carbohydrate, Double protein, Double fat, Double calorie) {
        this.item_index = item_index;
        this.carbohydrate = carbohydrate;
        this.protein = protein;
        this.fat = fat;
        this.calorie = calorie;
    }

    public Integer getItem_index() { return item_index; }

    public Double getCarbohydrate() { return carbohydrate; }

    public Double getProtein() { return protein; }

    public Double getFat() { return fat; }

    public Double getCalorie() { return calorie; }
}
