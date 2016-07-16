package com.gaia.app.smartwarehouse.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gaia.app.smartwarehouse.R;
import com.gaia.app.smartwarehouse.classes.Item;
import com.gaia.app.smartwarehouse.classes.ProductsData;
import com.gaia.app.smartwarehouse.resources.Drawrectangle;

import java.util.ArrayList;

/**
 * Created by anant on 13/06/16.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>{

    private Context context;
    private ArrayList<Item> dataarray;
    Drawrectangle drawrectangle;
    public ItemAdapter(Context context, ArrayList<Item> dataarray)
    {
        this.context=context;
        this.dataarray=dataarray;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView textView;
       // ImageView fill;
        CardView card;
        public ViewHolder(View v){
            super(v);
            card = (CardView) v.findViewById(R.id.card_item);

            textView = (TextView) v.findViewById(R.id.cardtext);
         //   fill = (ImageView) v.findViewById(R.id.cardrec);
            drawrectangle= (Drawrectangle) v.findViewById(R.id.rectView);

            card.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            String name=dataarray.get(getAdapterPosition()).getIname();
            ProductsData userdata=new ProductsData(context);
            if(!userdata.search_result(name))
                Toast.makeText(context,"No such category or item found",Toast.LENGTH_LONG).show();
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


        if (weight.equals("null")) {
            //  holder.fill.getLayoutParams().height = 0;
            drawrectangle.setValue(0);

        } else {

            Float value = new Float(weight);
            //holder.fill.getLayoutParams().height = getPercentHeight(value);
            drawrectangle.setValue(Math.round(value));
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