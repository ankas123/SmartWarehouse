package com.gaia.app.smartwarehouse.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gaia.app.smartwarehouse.ItemActivity;
import com.gaia.app.smartwarehouse.R;
import com.gaia.app.smartwarehouse.classes.Category;

import java.util.ArrayList;

/**
 * Created by praveen_gadi on 6/14/2016.
 */
public class RecyclerRowAdapter extends RecyclerView.Adapter<RecyclerRowAdapter.ItemRowHolder> {
    private LinearLayoutManager layoutManager;
    private Context context;
    private ArrayList<Category> items;

    public RecyclerRowAdapter(Context context,ArrayList<Category> items)
    {

        this.context=context;
        this.items=items;
    }


    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public void add(Category catitems) {

        items.add(catitems);

    }
    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlist, null);
        ItemRowHolder mh = new ItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(ItemRowHolder holder, int position) {

        holder.textView.setText(items.get(position).getCname().trim());
        RecycleritemAdapter recyclerAdapter=new RecycleritemAdapter(context,items.get(position).getItems());
        Integer in =items.size();
        Log.v("catsize",in.toString());
        layoutManager=new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.recycler_view_list.setLayoutManager(layoutManager);
        holder.recycler_view_list.setAdapter(recyclerAdapter);
        holder.recycler_view_list.setNestedScrollingEnabled(true);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public RecyclerView recycler_view_list;
        public TextView textView;
        public Button button;
        public ItemRowHolder(View itemView) {
            super(itemView);
            recycler_view_list=(RecyclerView)itemView.findViewById(R.id.recycler_view_list);
            textView= (TextView)itemView.findViewById(R.id.textV);
            button=(Button)itemView.findViewById(R.id.buttonid);

            button.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int a=getAdapterPosition();
            a++;
            String s="Category "+a;
            Toast.makeText(context,s, Toast.LENGTH_SHORT).show();
            Intent intent =new Intent(context,ItemActivity.class);
            intent.putExtra("Category ",s);
            context.startActivity(intent);
        }
    }

}
