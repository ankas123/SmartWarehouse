package com.gaia.app.smartwarehouse;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.gaia.app.smartwarehouse.adapters.RecyclerRowAdapter;
import com.gaia.app.smartwarehouse.adapters.dataAdapter;
import com.gaia.app.smartwarehouse.adapters.listAdapter;
import com.gaia.app.smartwarehouse.classes.dataclass;
import com.gaia.app.smartwarehouse.classes.userdatabase;

import java.util.ArrayList;

/**
 * Created by praveen_gadi on 6/19/2016.
 */
public class SettingsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    ListView listView;
    public LinearLayoutManager layoutManager;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);




        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordi);
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.content_settings, coordinatorLayout);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    public void addproduct(View view)
    {
        Intent intent=new Intent(this,Addproduct.class);
        startActivity(intent);
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

    public void insidedata(View view)
    {
       listAdapter listAdapter=new listAdapter(this,1);
        userdatabase details =new userdatabase(this);
        SQLiteDatabase db=details.getReadableDatabase();
        Cursor cursor=details.getdata(db);
        if(cursor.moveToFirst())
        {
            do{
                 String email,username,password,flag;
                email=cursor.getString(0);
                username=cursor.getString(1);
                password=cursor.getString(2);
                dataclass data=new dataclass(email,username,password);
                listAdapter.add(data);

            }while (cursor.moveToNext());

            RecyclerView recyclerView=(RecyclerView)findViewById(R.id.rvdata) ;

            layoutManager=new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
              recyclerView.setLayoutManager(layoutManager);

            dataAdapter adapter=new dataAdapter(this,listAdapter);
            recyclerView.setAdapter(adapter);

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

        }
        else if (id == R.id.wishlist) {

        }else if (id == R.id.login) {
            Intent intent=new Intent(this,LoginActivity.class);
            startActivity(intent);

        } else if (id == R.id.notifications) {

        } else if (id == R.id.account_settings) {

        }
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}

