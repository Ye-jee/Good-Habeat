package com.example.goodhabeat_view;

import java.io.Serializable;
import java.util.ArrayList;

public class SelectedMenuItemData implements Serializable {

    Integer item_index;
    Double carbohydrate;
    Double protein;
    Double fat;
    Double calorie;

    Integer riceItemData;
    Integer soupItemData;
    ArrayList<Integer> kimchiData;
    //ArrayList<Integer> viewModelData;
    //ArrayList<Integer> viewModelData;

    public SelectedMenuItemData(Integer item_index, Double carbohydrate, Double protein, Double fat, Double calorie) {
        this.item_index = item_index;
        this.carbohydrate = carbohydrate;
        this.protein = protein;
        this.fat = fat;
        this.calorie = calorie;
    }

    public Integer getItem_index() { return item_index; }
    public void setItem_index(Integer item_index) { this.item_index = item_index; }

    public Double getCarbohydrate() { return carbohydrate; }
    public void setCarbohydrate(Double carbohydrate) { this.carbohydrate = carbohydrate; }

    public Double getProtein() { return protein; }
    public void setProtein(Double protein) { this.protein = protein; }

    public Double getFat() { return fat; }
    public void setFat(Double fat) { this.fat = fat; }

    public Double getCalorie() { return calorie; }
    public void setCalorie(Double calorie) { this.calorie = calorie; }

    public Integer getRiceItemData() { return riceItemData; }
    public void setRiceItemData(Integer riceItemData) { this.riceItemData = riceItemData; }

    public Integer getSoupItemData() { return soupItemData; }
    public void setSoupItemData(Integer soupItemData) { this.soupItemData = soupItemData; }

    public ArrayList<Integer> getKimchiData() { return kimchiData; }

    public void setKimchiData(ArrayList<Integer> kimchiData) { this.kimchiData = kimchiData; }


}
