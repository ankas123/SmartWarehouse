package com.gaia.app.smartwarehouse.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gaia.app.smartwarehouse.LoginActivity;
import com.gaia.app.smartwarehouse.R;
import com.gaia.app.smartwarehouse.classes.Dataclass;
import com.gaia.app.smartwarehouse.classes.Userdata;

/**
 * Created by praveen_gadi on 6/19/2016.
 */
public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    Context context;
    private Userdata details;
  ListAdapter arrayAdapter;

    public DataAdapter(Context context, ListAdapter arrayAdapter)
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

     /*   holder.email.setText(object.getEmail());
        holder.username.setText(object.getName());
        holder.orgn.setText(object.getOrgn());
        holder.address.setText(object.getAddress());
        holder.logindate.setText(object.getDate());*/

    }

    @Override
    public int getItemCount() {
        return arrayAdapter.getCount();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView email,username,orgn,logindate,address;
        Button logout;
        RecyclerView recyclerView;
        public ViewHolder(View itemView) {
            super(itemView);
            logout=(Button)itemView.findViewById(R.id.button4) ;
            logout.setOnClickListener(this);
            recyclerView=(RecyclerView) itemView.findViewById(R.id.rvdata);

        }

        @Override
        public void onClick(View v) {
            Userdata details = new Userdata(context);
            SQLiteDatabase sqLiteDatabase = details.getWritableDatabase();
            details.cleardata();
            Intent intent =new Intent(context,LoginActivity.class);
            context.startActivity(intent);
        }
    }
}
