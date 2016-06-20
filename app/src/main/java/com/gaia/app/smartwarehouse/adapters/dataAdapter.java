package com.gaia.app.smartwarehouse.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gaia.app.smartwarehouse.R;
import com.gaia.app.smartwarehouse.classes.Dataclass;

/**
 * Created by praveen_gadi on 6/19/2016.
 */
public class dataAdapter extends RecyclerView.Adapter<dataAdapter.ViewHolder> {

    Context context;
  listAdapter arrayAdapter;

    public dataAdapter(Context context, listAdapter arrayAdapter)
    {
this.context=context;
        this.arrayAdapter=arrayAdapter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sampledata, null);
        ViewHolder mh = new ViewHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Dataclass object= (Dataclass) arrayAdapter.getItem(position);

        holder.email.setText(object.getA());
        holder.username.setText(object.getB());
        holder.password.setText(object.getC());


    }

    @Override
    public int getItemCount() {
        return arrayAdapter.getCount();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView email,username,password,flag;
        RecyclerView recyclerView;
        public ViewHolder(View itemView) {
            super(itemView);
            email=(TextView)itemView.findViewById(R.id.email);
            username=(TextView)itemView.findViewById(R.id.username);
            password=(TextView)itemView.findViewById(R.id.password);
            recyclerView=(RecyclerView) itemView.findViewById(R.id.rvdata);

        }
    }
}
