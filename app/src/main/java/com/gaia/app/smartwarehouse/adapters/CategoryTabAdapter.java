package com.gaia.app.smartwarehouse.adapters;

import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gaia.app.smartwarehouse.AnimationforAdapters;
import com.gaia.app.smartwarehouse.R;

import java.util.ArrayList;

/**
 * Created by praveen_gadi on 6/29/2016.
 */
public class CategoryTabAdapter extends RecyclerView.Adapter<CategoryTabAdapter.ViewHolder> {

    private ArrayList<String> data;
    Fragment context;
    private int prevposition=0;

    public CategoryTabAdapter(Fragment context, ArrayList<String> data)
    {
        this.context=context;
        this.data=data;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_card, parent,false);
        ViewHolder mh = new ViewHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.textView.setText(data.get(position));
        if(position>prevposition)
          AnimationforAdapters.animate(holder,true);
        else
            AnimationforAdapters.animate(holder,false);

        prevposition=position;


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        CardView cardView;


        public ViewHolder(View itemView) {
            super(itemView);

            cardView=(CardView)itemView.findViewById(R.id.cv_anm);
            textView = (TextView) itemView.findViewById(R.id.text_data);
        }
    }}
