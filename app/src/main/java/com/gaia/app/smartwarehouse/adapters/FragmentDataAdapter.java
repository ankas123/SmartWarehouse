package com.gaia.app.smartwarehouse.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gaia.app.smartwarehouse.R;

import java.util.ArrayList;

import layout.CategoryFragment;

/**
 * Created by praveen_gadi on 6/29/2016.
 */
public class FragmentDataAdapter extends RecyclerView.Adapter<FragmentDataAdapter.ViewHolder> {

    private ArrayList<String> data;
    Fragment context;

    public FragmentDataAdapter(Fragment context, ArrayList<String> data)
    {
        this.context=context;
        this.data=data;
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
        CardView cardView;
        TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            cardView= (CardView) itemView.findViewById(R.id.cv_fragment);
            textView= (TextView) itemView.findViewById(R.id.text_data);
            cardView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

        }
    }
}
