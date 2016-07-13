package com.gaia.app.smartwarehouse.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gaia.app.smartwarehouse.R;
import com.gaia.app.smartwarehouse.fragments.ItemsFragment;

import java.util.ArrayList;

/**
 * Created by praveen_gadi on 7/1/2016.
 */

public class ItemTabAdapter extends RecyclerView.Adapter<ItemTabAdapter.ViewHolder> {

private ArrayList<String> data,data2;
        ItemsFragment context;

public ItemTabAdapter(ItemsFragment context, ArrayList<String> data)
        {
        this.context=context;
        this.data=data;
            data2=data;
            notifyDataSetChanged();
            notifyDataSetChanged();
        }
    public ItemTabAdapter(String s)
    {
        s=s.toLowerCase();
        if(s.length()>0)
        {
            data.clear();
            for(String str:data2)
            {
                if(str.toLowerCase().contains(s))
                {
                    data.add(str);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    public void add(String string) {

        data.add(string);
        notifyDataSetChanged();

    }
@Override
public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_card, null);
        ViewHolder mh = new ViewHolder(v);
        return mh;
        }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(data.get(position));

    }

    @Override
public int getItemCount() {
        return data.size();
        }

public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView textView;
    CardView cardView;

    public ViewHolder(View itemView) {
        super(itemView);
        cardView= (CardView) itemView.findViewById(R.id.cv_datacard);
        textView = (TextView) itemView.findViewById(R.id.text_data);
        cardView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int a = getAdapterPosition();
       /* ActivityOptionsCompat activityOptionsCompat=ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context,null);
        Intent intent = new Intent(context,ItemActivity.class);
        intent.putExtra("Category", data.get(a));
        context.startActivity(intent,activityOptionsCompat.toBundle());*/


    }
}}

