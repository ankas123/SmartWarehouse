package com.gaia.app.smartwarehouse.classes;

import java.util.ArrayList;

/**
 * Created by anant on 23/06/16.
 */

public class Category {
    private String cname;
    private ArrayList<Item> items;

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public Category(String cname, ArrayList<Item> items) {
        this.cname = cname;
        this.items = items;
    }
}
