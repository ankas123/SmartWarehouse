package com.gaia.app.smartwarehouse.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
 * Created by praveen_gadi on 6/14/2016.
 */
public class RecycleritemAdapter extends RecyclerView.Adapter<RecycleritemAdapter.ViewHolder> {
    private ArrayList<Item> dataarray;
    Context context;

    public RecycleritemAdapter(Context context,ArrayList<Item> dataArray)
    {
        this.context=context;
        dataarray=dataArray;

    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textView;
        private CardView card;
        private ImageView fill;
        public ViewHolder(View itemView) {
            super(itemView);
            card = (CardView) itemView.findViewById(R.id.cv2);
            textView= (TextView)itemView.findViewById(R.id.itemtext);
            fill = (ImageView) itemView.findViewById(R.id.itcardrec);
            card.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {


            Intent intent=new Intent(context, DetailActivity.class);
            context.startActivity(intent);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(dataarray.get(position).getIname().trim());
        Integer in =dataarray.size();
        String weight = dataarray.get(position).getWeight();

        if(weight.equals("null")) {
            holder.fill.getLayoutParams().height = 0;

        }
        else{

            Float value = new Float(weight);
            holder.fill.getLayoutParams().height=getPercentHeight(value);
        }
        Log.v("size",in.toString());

    }

    @Override
    public int getItemCount() {

        return dataarray.size();
    }
    int getPercentHeight(float weight){

        int height;
        height= (int) ((weight/120)*50);
        int dimension = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height, context.getResources().getDisplayMetrics());
        return dimension;


    }



}
