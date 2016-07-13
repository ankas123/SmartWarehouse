package com.gaia.app.smartwarehouse.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaia.app.smartwarehouse.DetailActivity;
import com.gaia.app.smartwarehouse.R;
import com.gaia.app.smartwarehouse.classes.Item;

import java.util.ArrayList;

/**
 * Created by anant on 13/06/16.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>{

    private Context context;
    private ArrayList<Item> dataarray;

    public ItemAdapter(Context context, ArrayList<Item> dataarray)
    {
        this.context=context;
        this.dataarray=dataarray;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView textView;
        ImageView fill;
        CardView card;
        public ViewHolder(View v){
            super(v);
            card = (CardView) v.findViewById(R.id.card_item);
            imageView = (ImageView) v.findViewById(R.id.cardrec);
            textView = (TextView) v.findViewById(R.id.cardtext);
            fill = (ImageView) v.findViewById(R.id.cardrec);
            card.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            ActivityOptionsCompat activityOptionsCompat=ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context,null);
            Intent intent = new Intent(context,DetailActivity.class);
            context.startActivity(intent,activityOptionsCompat.toBundle());
        }
    }

    public void add(ArrayList<Item> items) {
         dataarray.addAll(items);
            notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(dataarray.get(position).getIname());
        String weight = dataarray.get(position).getWeight();


        if(weight.equals("null")) {
            holder.fill.getLayoutParams().height = 0;

        }
        else{

            Float value = new Float(weight);
            holder.fill.getLayoutParams().height=getPercentHeight(value);

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