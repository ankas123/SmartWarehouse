package com.gaia.app.smartwarehouse.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by praveen_gadi on 6/19/2016.
 */
public class ListAdapter extends ArrayAdapter {

    public List list=new ArrayList();
    public ListAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public void add(Object object) {
        super.add(object);
         list.add(object);
    }
}
