package com.example.woofer.model;

/**
 * Java representation of data to be displayed in RecyclerView
 * Created by ali on 2017/05/12.
 */
public class ListItem {
    private String title;
    private String update;
    private int imageResid;

    public String getTitle() {
        return title;
    }

    public int getImageResid() {
        return imageResid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImageResid(int imageResid) {
        this.imageResid = imageResid;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }
}
