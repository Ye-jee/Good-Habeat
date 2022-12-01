package com.example.goodhabeat_view;

public class CommunityData {

    int community_user_pic;
    String community_user_nikName;
    String community_create_date;
    String community_create_time;
    String community_content_text;
    int community_content_img;
    int community_heart_img;
    String community_heart_number;
    String community_delete_text;

    public CommunityData(int community_user_pic, String community_user_nikName, String community_create_date, String community_create_time,
                         String community_content_text, int community_content_img, int community_heart_img, String community_heart_number, String community_delete_text) {

        this.community_user_pic = community_user_pic;
        this.community_user_nikName = community_user_nikName;
        this.community_create_date = community_create_date;
        this.community_create_time = community_create_time;
        this.community_content_text = community_content_text;
        this.community_content_img = community_content_img;
        this.community_heart_img = community_heart_img;
        this.community_heart_number = community_heart_number;
        this.community_delete_text = community_delete_text;
    }

    public int getCommunity_user_pic() {
        return community_user_pic;
    }

    public String getCommunity_user_nikName() {
        return community_user_nikName;
    }

    public String getCommunity_create_date() {
        return community_create_date;
    }

    public String getCommunity_create_time() {
        return community_create_time;
    }

    public String getCommunity_content_text() {
        return community_content_text;
    }

    public int getCommunity_content_img() {
        return community_content_img;
    }

    public int getCommunity_heart_img() {
        return community_heart_img;
    }

    public String getCommunity_heart_number() {
        return community_heart_number;
    }

    public String getCommunity_delete_text() {
        return community_delete_text;
    }
}