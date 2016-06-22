package com.gaia.app.smartwarehouse;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

import com.gaia.app.smartwarehouse.adapters.ItemAdapter;

/**
 * Created by anant on 13/06/16.
 */

public class ItemActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Context context;
    Toolbar toolbar;
    String itemArray[]=new String[]{
            "item 1","item 2","item 3","item 4","item 5","item 6","item 7","item 8","item 9","item 10","item 11","item 12"
    };
    String str="Category ";
    @Override
    public void setSupportActionBar(@Nullable Toolbar toolbar) {
        super.setSupportActionBar(toolbar);
        this.toolbar=toolbar;

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Adding custom toolbar to the page
        context =getApplicationContext();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Category");
        setSupportActionBar(toolbar);



        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordi);
        LayoutInflater layoutInflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.activity_item, coordinatorLayout );



        Intent intent=getIntent();
        str=intent.getStringExtra(str);


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.viewall);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),3);
        recyclerView.setLayoutManager(layoutManager);

        ItemAdapter itemAdapter = new ItemAdapter(this,itemArray);
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
            Intent i = new Intent(getApplicationContext(),SettingsActivity.class);
            startActivity(i);
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}
