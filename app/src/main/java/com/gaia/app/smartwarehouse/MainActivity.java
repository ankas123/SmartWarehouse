package com.gaia.app.smartwarehouse;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.gaia.app.smartwarehouse.adapters.RecyclerRowAdapter;
import com.gaia.app.smartwarehouse.classes.Category;
import com.gaia.app.smartwarehouse.classes.Item;
import com.gaia.app.smartwarehouse.classes.Userdata;
import com.gaia.app.smartwarehouse.service.ItemGetTask;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private EditText editText_dialog;
    private String email;
    private RecyclerView recyclerView;
    private RecyclerRowAdapter adapter;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordi);
        LayoutInflater layoutInflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.content_main, coordinatorLayout );


        recyclerView=(RecyclerView)findViewById(R.id.rv1) ;
        layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerRowAdapter(this,new ArrayList<Category>());
        recyclerView.setAdapter(adapter);

        if(!isNetworkConnected()) {
            AlertDialog.Builder builder=new AlertDialog.Builder(this,R.style.DialogBoxStyle);

            builder.setTitle(" No Internet Connection !");
            builder.setPositiveButton("Refresh",null);
            builder.setNegativeButton("Cancel",null);
            builder.show();

            final Userdata details =new Userdata(this);
            Cursor cursor=details.getofflinedata();
            if(cursor.moveToFirst())
            {

                do{
                    ArrayList<Item> itemArrayList= new ArrayList<>();
                    String cname=cursor.getString(0);
                   Cursor cursor2=details.getitemsdata(cname);
                    if(cursor2.moveToFirst()) {
                        do {
                            String iname, unit, weight, quant;
                            iname = cursor2.getString(0);
                            unit = cursor2.getString(1);
                            weight = cursor2.getString(2);
                            quant = cursor2.getString(3);
                            Item item = new Item(iname, cname, unit, weight, quant);
                            itemArrayList.add(item);
                        } while (cursor2.moveToNext());

                    }
                    Category cat = new Category(cname,itemArrayList);

                      adapter.add(cat);
                }while (cursor.moveToNext());

            }

        }
        else
        {
            final Userdata details =new Userdata(this);
            Cursor cursor=details.getuserdata();

            if(cursor.moveToFirst()) {
                do {
                    email = cursor.getString(0);

                } while (cursor.moveToNext());
            }


            ItemGetTask asyncTask = (ItemGetTask) new ItemGetTask(new ItemGetTask.PlottingItems(){

                @Override
                public void setItems(Category output){
                    adapter.add(output);
                    details.create_category_table(output);
                }
            }).execute(email);


        }





        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }


    public void addproduct(View view)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this,R.style.DialogBoxStyle);

        LayoutInflater inflater=this.getLayoutInflater();
        final View dialogview=inflater.inflate(R.layout.content_dialogbox,null);
        builder.setView(dialogview);
        editText_dialog=(EditText)dialogview.findViewById(R.id.editText_dialogbox);
        builder.setTitle("Add Category");
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               String string=editText_dialog.getText().toString().trim();
                Intent intent=new Intent(MainActivity.this,Additem.class);
                intent.putExtra("cname",string);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel",null);
        builder.show();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.notifications) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);



        int id = item.getItemId();


         if (id == R.id.detail) {
            Intent i = new Intent(getApplicationContext(), DetailActivity.class);
            startActivity(i);

        } else if (id == R.id.wishlist) {


        } else if (id == R.id.notifications) {

        } else if (id == R.id.login) {
            Intent intent=new Intent(this,LoginActivity.class);
            startActivity(intent);

        } else if (id == R.id.account_settings) {
             Intent i = new Intent(getApplicationContext(),SettingsActivity.class);
             startActivity(i);
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }


}
