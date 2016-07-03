package com.gaia.app.smartwarehouse;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.gaia.app.smartwarehouse.adapters.SearchViewAdapter;
import com.gaia.app.smartwarehouse.classes.ProductsData;
import com.gaia.app.smartwarehouse.classes.TabViewerAdapter;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

import layout.CategoryFragment;
import layout.ItemsFragment;
import layout.WishlistFragment;

/**
 * Created by praveen_gadi on 6/29/2016.
 */
public class SearchbarActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
  private TabLayout tabLayout;
    private ViewPager  viewPager;
    private List<String> cnameList=new ArrayList<>();
    private List<String> inameList=new ArrayList<>();
    private List<String> wishList=new ArrayList<>();
    private List<String> searchList = new ArrayList<>();

    private RecyclerView recyclerView;
    private SearchViewAdapter adapter;
    private LinearLayoutManager layoutManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchbar);




        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_search);
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.content_searchbar, coordinatorLayout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void setupViewPager(ViewPager viewPager) {
        TabViewerAdapter adapter = new TabViewerAdapter(getSupportFragmentManager());

       wishList.add("wishlist");
       ProductsData details = new ProductsData(this);
        Cursor cursor = details.getcategorydata();
        if (cursor.moveToFirst()) {

            do {
                String cname = cursor.getString(0);
                cnameList.add(cname);
                searchList.add(cname);
                Cursor cursor2 = details.getitemsdata(cname);
                if (cursor2.moveToFirst()) {
                    do {
                        String iname;
                        iname = cursor2.getString(0);
                        inameList.add(iname);
                        searchList.add(iname);
                    } while (cursor2.moveToNext());

                }
            } while (cursor.moveToNext());

        }
        for (int i=0;i<100;i++)
        {
            searchList.add("ajcsna");
        }

       adapter.addfragment(new CategoryFragment(searchList),"category");
        adapter.addfragment(new ItemsFragment(inameList),"items");
        adapter.addfragment(new WishlistFragment(wishList),"wishlist");
        viewPager.setAdapter(adapter);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout) {});
tabLayout.setTabsFromPagerAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        //searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        Log.e("search","search");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {

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


        } else if (id == R.id.wishlist) {


        } else if (id == R.id.notifications) {

        } else if (id == R.id.login) {

        } else if (id == R.id.account_settings) {

        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Searchbar Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.gaia.app.smartwarehouse/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Searchbar Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.gaia.app.smartwarehouse/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }



    public void searchResult(String name)
    {
        ProductsData userdata=new ProductsData(this);
        if(!userdata.search_result(name))
            Toast.makeText(getBaseContext(),"No such category or item found",Toast.LENGTH_LONG).show();
    }


    public void searchview()
    {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.content_searchdialogbox);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        dialog.show();

        recyclerView = (RecyclerView)dialog.findViewById(R.id.search_recyclerview);
        layoutManager = new LinearLayoutManager(SearchbarActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new SearchViewAdapter(this,(ArrayList<String>)searchList);
        recyclerView.setAdapter(adapter);



        ImageView back_button = (ImageView) dialog.findViewById(R.id.back_button);
        ImageView searchbutton = (ImageView) dialog.findViewById(R.id.search_go);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                dialog.dismiss();

            }
        });
        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText search_EditText = (EditText) dialog.findViewById(R.id.search_product);
                String ch = search_EditText.getText().toString().trim();
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                dialog.dismiss();
                searchResult(ch);
            }
        });
    }
}

