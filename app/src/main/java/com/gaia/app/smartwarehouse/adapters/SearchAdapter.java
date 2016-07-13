package com.gaia.app.smartwarehouse.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gaia.app.smartwarehouse.R;
import com.gaia.app.smartwarehouse.classes.ProductsData;

import java.util.ArrayList;

/**
 * Created by praveen_gadi on 7/13/2016.
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private Context context;
    private ArrayList<String> data;
    public SearchAdapter(Context context, ArrayList<String> data)
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
            String name=data.get(getAdapterPosition());
            ProductsData userdata=new ProductsData(context);
            if(!userdata.search_result(name))
                Toast.makeText(context,"No such category or item found",Toast.LENGTH_LONG).show();
        }
    }
}
