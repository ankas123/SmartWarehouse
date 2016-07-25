package com.gaia.app.smartwarehouse;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.gaia.app.smartwarehouse.adapters.BoxAdapter;
import com.gaia.app.smartwarehouse.classes.GetItemDetails;
import com.gaia.app.smartwarehouse.classes.Item;

/**
 * Created by anant on 14/06/16.
 */

public class DetailActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

     private BoxAdapter box;
    private Float weight;
    private TextView cname,iname,iweight;

    private Item item;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       if(Build.VERSION.SDK_INT>=21)
       {
           TransitionInflater transitionInflater=TransitionInflater.from(this);
           Transition transition=transitionInflater.inflateTransition(R.transition.transition_slide_bottom);
           getWindow().setEnterTransition(transition);
           getWindow().setReturnTransition(transition);
       }
        setContentView(R.layout.activity_detail);

       GetItemDetails getItemDetails=new GetItemDetails();
       item=getItemDetails.getItem();
        String string=item.getWeight();
        if(!string.equals("null"))
            weight=Float.parseFloat(item.getWeight());
        else
            weight=new Float(0);

        cname= (TextView) findViewById(R.id.textView4);
        cname.setText(item.getCname());
        iweight= (TextView) findViewById(R.id.textView6);
        iweight.setText(weight.toString());

        Toolbar toolbar = (Toolbar) findViewById(R.id.MyToolbar);
        setSupportActionBar(toolbar);

        RecyclerView grid = (RecyclerView) findViewById(R.id.grid);
        grid.setHasFixedSize(true);


       /* QuantityLoader quantityLoader = new QuantityLoader(getApplicationContext(),6.0f);
        grid.setAdapter(quantityLoader.returnAdapter());
        grid.setLayoutManager(quantityLoader.returnManager());*/

        GridLayoutManager manager = new GridLayoutManager(DetailActivity.this,4, LinearLayoutManager.VERTICAL,true);
        grid.setLayoutManager(manager);

        box =new BoxAdapter(weight);
        grid.setAdapter(box);


        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
        collapsingToolbarLayout.setTitle(item.getIname());





        //Navigation drawer code
        //TODO change navigation drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout1);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view1);
        navigationView.setNavigationItemSelectedListener(this);

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

        if (id == R.id.action_search) {
            ActivityOptionsCompat activityOptionsCompat=ActivityOptionsCompat.makeSceneTransitionAnimation(this,null);
            Intent intent = new Intent(this,SearchActivity.class);
            this.startActivity(intent,activityOptionsCompat.toBundle());
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout1);



        int id = item.getItemId();

        if (id == R.id.login) {
            ActivityOptionsCompat activityOptionsCompat=ActivityOptionsCompat.makeSceneTransitionAnimation(this,null);
            Intent intent = new Intent(this, LoginActivity.class);
            this.startActivity(intent,activityOptionsCompat.toBundle());

        } else if (id == R.id.account_settings) {
            ActivityOptionsCompat activityOptionsCompat=ActivityOptionsCompat.makeSceneTransitionAnimation(this,null);
            Intent intent = new Intent(this, SettingsActivity.class);
            this.startActivity(intent,activityOptionsCompat.toBundle());
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}
