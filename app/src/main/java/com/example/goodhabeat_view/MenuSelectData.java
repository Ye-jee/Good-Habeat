package com.example.goodhabeat_view;

public class MenuSelectData {

    String menuPicUrl;
    String menuName;
    Double menuCarbo;
    Double menuProtein;
    Double menuFat;
    String menuInfo;
    Double menuCal;
    Integer recipeId; // intent용 정보

    public MenuSelectData(String menuPicUrl, String menuName, Double menuCarbo, Double menuProtein, Double menuFat, String menuInfo, Double menuCal, Integer recipeId) {
        this.menuPicUrl = menuPicUrl;
        this.menuName = menuName;
        this.menuCarbo = menuCarbo;
        this.menuProtein = menuProtein;
        this.menuFat = menuFat;
        this.menuInfo = menuInfo;
        this.menuCal = menuCal;
        this.recipeId = recipeId; // intent용 정보
    }

    public String getMenuPicUrl() { return menuPicUrl; }

    public String getMenuName() { return menuName; }

    public Double getMenuCarbo() { return menuCarbo; }

    public Double getMenuProtein() { return menuProtein; }

    public Double getMenuFat() { return menuFat; }

    public String getMenuInfo() { return menuInfo;}

    public Double getMenuCal() { return menuCal; }

    public Integer getRecipeId() { return recipeId; }

}
