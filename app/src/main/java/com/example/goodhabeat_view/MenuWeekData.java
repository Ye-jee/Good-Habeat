package com.example.goodhabeat_view;

public class MenuWeekData {
    int menuPicId;
    String menuName;
    String menuText;

    public MenuWeekData(int menuPicId, String menuName, String menuText) {
        this.menuPicId = menuPicId;
        this.menuName = menuName;
        this.menuText = menuText;
    }

    public int getMenuPicId() { return menuPicId; }
    public void setMenuPicId(int menuPicId) { this.menuPicId = menuPicId; }

    public String getMenuName() { return menuName; }
    public void setMenuName(String menuName) { this.menuName = menuName; }

    public String getMenuText() { return menuText; }
    public void setMenuText(String menuText) { this.menuText = menuText; }
}
