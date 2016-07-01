package com.gaia.app.smartwarehouse;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

import com.gaia.app.smartwarehouse.adapters.ItemAdapter;
import com.gaia.app.smartwarehouse.classes.Item;
import com.gaia.app.smartwarehouse.classes.Userdata;

import java.util.ArrayList;

/**
 * Created by anant on 13/06/16.
 */

public class ItemActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Context context;
    private Toolbar toolbar;
    private static String str;
    private static ItemAdapter itemAdapter;

    @Override
    public void setSupportActionBar(@Nullable Toolbar toolbar) {
        super.setSupportActionBar(toolbar);
        this.toolbar = toolbar;

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent intent = getIntent();
        str = intent.getStringExtra("Category");

        // Adding custom toolbar to the page

        context = getApplicationContext();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(str);
        setSupportActionBar(toolbar);


        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordi);
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.activity_item, coordinatorLayout);


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.viewall);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 3);
        recyclerView.setLayoutManager(layoutManager);
        itemAdapter = new ItemAdapter(this, new ArrayList<Item>());
        DefaultItemAnimator animator = new DefaultItemAnimator() {
            @Override
            public boolean canReuseUpdatedViewHolder(RecyclerView.ViewHolder viewHolder) {
                return false;
            }

        };
        recyclerView.getItemAnimator().setChangeDuration(0);

        final Userdata details = new Userdata(this);

        ArrayList<Item> itemArrayList = new ArrayList<>();

        Cursor cursor2 = details.getitemsdata(str);
        if (cursor2.moveToFirst()) {
            do {
                String iname, unit, weight, quant;
                iname = cursor2.getString(0);
                unit = cursor2.getString(1);
                weight = cursor2.getString(2);
                quant = cursor2.getString(3);
                Item item = new Item(iname, str, unit, weight, quant);
                itemArrayList.add(item);
            } while (cursor2.moveToNext());


            itemAdapter.add(itemArrayList);

            recyclerView.setAdapter(itemAdapter);

            //Navigation drawer code
            //TODO change navigation drawer
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
        }
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
            finish();
        } else if (id == R.id.login) {

        } else if (id == R.id.notifications) {

        } else if (id == R.id.account_settings) {
            Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(i);
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    public static void refreshItem(String cat, String name, String weight) {


        if (str != null) {
            if (str.equals(cat)) {
                itemAdapter.changeWeight(name, weight);
            }
        }
    }
}
