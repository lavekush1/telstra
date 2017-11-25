package com.lavekush.telstra.vo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataContainer {

    @SerializedName("title")
    String title;

    @SerializedName("rows")
    List<RowItem> items;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List getItems() {
        return items;
    }

    public void setItems(List items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "DataContainer{" +
                "title='" + title + '\'' +
                ", items=" + items +
                '}';
    }
}
