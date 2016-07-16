package com.gaia.app.smartwarehouse.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gaia.app.smartwarehouse.R;
import com.gaia.app.smartwarehouse.classes.Item;
import com.gaia.app.smartwarehouse.resources.Drawrectangle;
import com.gaia.app.smartwarehouse.resources.ItemViewHolder;

import java.util.ArrayList;

/**
 * Created by anant on 13/06/16.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder>{

    private Context context;
    private ArrayList<Item> dataarray;
    Drawrectangle drawrectangle;
    public ItemAdapter(Context context, ArrayList<Item> dataarray)
    {
        this.context=context;
        this.dataarray=dataarray;
    }



    public void add(ArrayList<Item> items) {
         dataarray.addAll(items);
            notifyDataSetChanged();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);

        ItemViewHolder viewHolder = new ItemViewHolder(context, view, dataarray, true);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.textView.setText(dataarray.get(position).getIname());
        String weight = dataarray.get(position).getWeight();


        if (weight.equals("null")) {
            //  holder.fill.getLayoutParams().height = 0;
            holder.drawrectangle.setValue(0);
            holder.setInput(Float.valueOf(0));
        } else {
            holder.setInput(Float.valueOf(weight));
            Float value = new Float(weight);
            //holder.fill.getLayoutParams().height = getPercentHeight(value);
            holder.drawrectangle.setValue(Math.round(value));
        }

    }

    @Override
    public int getItemCount() {
        return dataarray.size();
    }

    int getPercentHeight(float weight){
            int height;
            height= (int) ((weight/120)*60);

            int dimension = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height, context.getResources().getDisplayMetrics());
            return dimension;
    }

    public void changeWeight(String name, String weight){
        final int size = dataarray.size();
        for(int i = size - 1; i>= 0; i--) {
            if (dataarray.get(i).getIname().equals(name)) {
                dataarray.get(i).setWeight(weight);
                notifyItemChanged(i);

            }
        }
    }
}