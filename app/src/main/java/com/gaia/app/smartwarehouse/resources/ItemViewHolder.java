package com.gaia.app.smartwarehouse.resources;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gaia.app.smartwarehouse.R;
import com.gaia.app.smartwarehouse.classes.Item;
import com.gaia.app.smartwarehouse.classes.ProductsData;

import java.util.ArrayList;

/**
 * Created by anant on 17/07/16.
 */

public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView textView;
    private ArrayList<Item> dataarray;
    public FrameLayout frame;
    private CardView card;
    public float input;
    public Drawrectangle drawrectangle;
    private Context context;

    public ItemViewHolder(Context context, View itemView, ArrayList<Item> dataarray , boolean bigger) {
        super(itemView);
        this.context = context;
        this.dataarray = dataarray;
        textView = (TextView) itemView.findViewById(R.id.itemtext);
        card = (CardView) itemView.findViewById(R.id.recycler_card);
        drawrectangle= (Drawrectangle) itemView.findViewById(R.id.rectView);



        if(bigger==true)
            bigger(card);
        else
            card.setOnClickListener(this);
    }

    public void bigger(CardView card) {
        final float scale = context.getResources().getDisplayMetrics().density;
        int height = (int) (135 * scale + 0.5f);
        int width = (int) (120 * scale + 0.5f);
        card.getLayoutParams().height=height;
        card.getLayoutParams().width=width;
    }

    public float getInput() {
        return input;
    }

    public void setInput(float input) {
        this.input = input;
    }

    @Override
    public void onClick(View v) {
        String name=dataarray.get(getAdapterPosition()).getIname();
        ProductsData userdata=new ProductsData(context);
        if(!userdata.search_result(name))
            Toast.makeText(context,"No such category or item found",Toast.LENGTH_LONG).show();
    }
}