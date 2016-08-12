package com.gaia.app.smartwarehouse;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.gaia.app.smartwarehouse.adapters.ItemAdapter;
import com.gaia.app.smartwarehouse.classes.Item;
import com.gaia.app.smartwarehouse.classes.ProductsData;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

/**
 * Created by anant on 13/06/16.
 */

public class ItemActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener,
        ActionMode.Callback {

    private Context context;
    private Toolbar toolbar;
    private GestureDetectorCompat gestureDetector;
    private static String str;
    private static ItemAdapter itemAdapter;
    private RecyclerView recyclerView;
    private ActionMode actionMode;
    private FloatingActionButton fab;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            TransitionInflater transitionInflater = TransitionInflater.from(this);
            Transition transition = transitionInflater.inflateTransition(R.transition.transition_slide_right);
            getWindow().setEnterTransition(transition);
            getWindow().setReturnTransition(transition);
        }
        setContentView(R.layout.activity_main);

        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordi);
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.content_item, coordinatorLayout);
        Intent intent = getIntent();
        str = intent.getStringExtra("Category");

        // Adding custom toolbar to the page

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(str);
        setSupportActionBar(toolbar);


        recyclerView = (RecyclerView) findViewById(R.id.viewall);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 3);
        recyclerView.setLayoutManager(layoutManager);
        gestureDetector =
                new GestureDetectorCompat(this, new RecyclerViewGestureListener());

        itemAdapter = new ItemAdapter(this, new ArrayList<Item>());

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                gestureDetector.onTouchEvent(e);
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        ProductsData details = new ProductsData(this);

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

            ScaleInAnimationAdapter AnimationAdapter = new ScaleInAnimationAdapter(itemAdapter);
            AnimationAdapter.setDuration(2000);
            AnimationAdapter.setInterpolator(new OvershootInterpolator());
            recyclerView.setAdapter(AnimationAdapter);


            if (!isNetworkConnected()) {

                Snackbar snackbar = Snackbar.make(findViewById(R.id.contentitem), "No Network Connection", Snackbar.LENGTH_INDEFINITE);
                View view = snackbar.getView();
                view.setBackgroundColor(ContextCompat.getColor(this, R.color.snackbarcolor));
                snackbar.show();
            }


            //Navigation drawer code
            //TODO change navigation drawer
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            fab = (FloatingActionButton) findViewById(R.id.item_fab);

        }
    }

    public void additem(View v) {
        Intent intent = new Intent(this, Additem.class);
        intent.putExtra("cname", str);
        startActivity(intent);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
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
        if (id == R.id.action_search) {
            ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, null);
            Intent intent = new Intent(this, SearchActivity.class);
            this.startActivity(intent, activityOptionsCompat.toBundle());
            return true;

        }
        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        int id = item.getItemId();

        if (id == R.id.login) {
            ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, null);
            Intent intent = new Intent(this, LoginActivity.class);
            this.startActivity(intent, activityOptionsCompat.toBundle());

        } else if (id == R.id.account_settings) {
            ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, null);
            Intent intent = new Intent(this, SettingsActivity.class);
            this.startActivity(intent, activityOptionsCompat.toBundle());
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


    private void myToggleSelection(int idx) {
        itemAdapter.toggleSelection(idx);
        String title = getString(R.string.selected_count, Integer.valueOf(itemAdapter.getSelectedItemCount()).toString());
        actionMode.setTitle(title);
    }

    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        MenuInflater inflater = actionMode.getMenuInflater();
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        inflater.inflate(R.menu.delete_item, menu);
        fab.setVisibility(View.GONE);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_delete:
                //TODO: add code for delete item
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {
        this.actionMode = null;
        itemAdapter.clearSelections();
        fab.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {

        if (view != null && view.getId()==R.id.recycler_card) {

            int idx = recyclerView.getChildPosition(view);
            Log.v("id", Integer.valueOf(idx).toString());

            if (actionMode != null) {
                Log.v("yes", Integer.valueOf(idx).toString());

                myToggleSelection(idx);
                if (itemAdapter.getSelectedItemCount()==0) {
                    actionMode.finish();
                }
                return;

            }

        }
    }

    class RecyclerViewGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Log.v("single", "confirmed");
            View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
            onClick(view);
            return super.onSingleTapConfirmed(e);
        }

        public void onLongPress(MotionEvent e) {
            Log.v("long press", "is made");
            View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
            if (actionMode != null) {
                return;
            }

            actionMode = startSupportActionMode(ItemActivity.this);
            int idx = recyclerView.getChildPosition(view);
            myToggleSelection(idx);
            super.onLongPress(e);
        }
    }
}
