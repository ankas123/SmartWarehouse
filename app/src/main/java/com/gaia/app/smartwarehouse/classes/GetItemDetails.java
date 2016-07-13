package com.gaia.app.smartwarehouse.classes;

import android.app.Application;

/**
 * Created by praveen_gadi on 7/14/2016.
 */
public class GetItemDetails extends Application {
    private static Item item;
    public Item getItem()
    {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;


    }
}
