package com.gaia.app.smartwarehouse.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gaia.app.smartwarehouse.R;
import com.gaia.app.smartwarehouse.classes.Item;
import com.gaia.app.smartwarehouse.resources.ItemViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anant on 13/06/16.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder>{

    private Context context;
    private ArrayList<Item> dataarray;
    private SparseBooleanArray selectedItems;

    public ItemAdapter(Context context, ArrayList<Item> dataarray)
    {
        this.context=context;
        this.dataarray=dataarray;
        selectedItems = new SparseBooleanArray();
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
        holder.itemView.setActivated(selectedItems.get(position, false));

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



    public void changeWeight(String name, String weight){
        final int size = dataarray.size();
        for(int i = size - 1; i>= 0; i--) {
            if (dataarray.get(i).getIname().equals(name)) {
                dataarray.get(i).setWeight(weight);
                notifyItemChanged(i);

            }
        }
    }


    public void toggleSelection(int pos) {
        if (selectedItems.get(pos, false)) {
            selectedItems.delete(pos);
        }
        else {
            selectedItems.put(pos, true);
        }
        notifyItemChanged(pos);
    }

    public void clearSelections() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    public List<Integer> getSelectedItems() {
        List<Integer> items =
                new ArrayList<Integer>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); i++) {
            items.add(selectedItems.keyAt(i));
        }
        return items;
    }

}