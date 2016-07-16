package com.gaia.app.smartwarehouse.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gaia.app.smartwarehouse.R;
import com.gaia.app.smartwarehouse.classes.Item;
import com.gaia.app.smartwarehouse.resources.ItemViewHolder;

import java.util.ArrayList;

/**
 * Created by praveen_gadi on 6/14/2016.
 */
public class RecycleritemAdapter extends RecyclerView.Adapter<ItemViewHolder> {
    private ArrayList<Item> dataarray;
    private Context context;
    private String category;



    public RecycleritemAdapter(Context context, String category, ArrayList<Item> dataArray) {
        this.context = context;
        this.dataarray = dataArray;
        this.category = category;
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(context, view, dataarray, false);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.textView.setText(dataarray.get(position).getIname().trim());
        Integer in = dataarray.size();
        String weight = dataarray.get(position).getWeight();

        if (weight.equals("null")) {
          //  holder.fill.getLayoutParams().height = 0;
            holder.drawrectangle.setValue(0);
            holder.setInput(Float.valueOf(0));

        } else {
            holder.setInput(Float.valueOf(weight));
            Float value = Float.valueOf(weight);
            //holder.fill.getLayoutParams().height = getPercentHeight(value);
            holder.drawrectangle.setValue(Math.round(value));
        }
        Log.v("size", in.toString());

    }

    @Override
    public int getItemCount() {

        return dataarray.size();
    }

    int getPercentHeight(float weight) {

        int height;
        height = (int) ((weight / 120) * 50);
        int dimension = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height, context.getResources().getDisplayMetrics());
        return dimension;


    }

    public void changeWeight(String name, String weight) {
        final int size = dataarray.size();
        Log.v("name", name);
        for (int i = 0; i <= size -1; i++) {
            if (dataarray.get(i).getIname().equals(name)) {
                Log.v("name", dataarray.get(i).getIname());
                dataarray.get(i).setWeight(weight);
                notifyItemChanged(i);
                Log.v("change", weight);

            }
        }
    }
}
