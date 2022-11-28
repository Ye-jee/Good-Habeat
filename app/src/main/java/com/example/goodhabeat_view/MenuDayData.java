package com.example.goodhabeat_view;

public class MenuDayData {
    String menuPicUrl;
    String menuName;
    String menuCal;
    String menuCarbo;
    String menuProtein;
    String menuFat;

    public MenuDayData(String menuPicUrl, String menuName, String menuCal, String menuCarbo, String menuProtein, String menuFat) {
        this.menuPicUrl = menuPicUrl;
        this.menuName = menuName;
        this.menuCal = menuCal;
        this.menuCarbo = menuCarbo;
        this.menuProtein = menuProtein;
        this.menuFat = menuFat;
    }

    public String getMenuPicUrl() { return menuPicUrl; }
    public void setMenuPicUrl(String menuPicUrl) { this.menuPicUrl = menuPicUrl; }

    public String getMenuName() { return menuName; }
    public void setMenuName(String menuName) { this.menuName = menuName; }

    public String getMenuCal() { return menuCal; }
    public void setMenuCal(String menuCal) { this.menuCal = menuCal; }

    public String getMenuCarbo() { return menuCarbo; }
    public void setMenuCarbo(String menuCarbo) { this.menuCarbo = menuCarbo; }

    public String getMenuProtein() { return menuProtein; }
    public void setMenuProtein(String menuProtein) { this.menuProtein = menuProtein; }

    public String getMenuFat() { return menuFat; }
    public void setMenuFat(String menuFat) { this.menuFat = menuFat; }

}
