package com.gaia.app.smartwarehouse;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.gaia.app.smartwarehouse.classes.ProductsData;
import com.gaia.app.smartwarehouse.classes.TabViewerAdapter;
import com.gaia.app.smartwarehouse.fragments.CategoryFragment;
import com.gaia.app.smartwarehouse.fragments.ItemsFragment;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;



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
    private List<String> searchList = new ArrayList<>();
    private View view_searchbar,view_tablayout;
    private RecyclerView recyclerView;

    private LinearLayoutManager layoutManager;
    private AppBarLayout appBarLayout;
    private EditText searchtext;
    TabViewerAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(Build.VERSION.SDK_INT>=21)
        {
            TransitionInflater transitionInflater=TransitionInflater.from(this);
            Transition transition=transitionInflater.inflateTransition(R.transition.transition_slide_right);
            getWindow().setEnterTransition(transition);
            getWindow().setReturnTransition(transition);
        }
        setContentView(R.layout.activity_searchbar);


        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_search);
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.content_searchbar, coordinatorLayout);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        view_searchbar=toolbar.findViewById(R.id.searchbar);
        searchtext= (EditText) view_searchbar.findViewById(R.id.editText_search);
        setSupportActionBar(toolbar);
        view_searchbar.setVisibility(View.INVISIBLE);
        appBarLayout= (AppBarLayout) findViewById(R.id.appbar_search);
        view_tablayout=appBarLayout.findViewById(R.id.tab_layout);


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
        adapter = new TabViewerAdapter(getSupportFragmentManager());


       ProductsData details = new ProductsData(this);
        Cursor cursor = details.getcategorydata();
        if (cursor.moveToFirst()) {

            do {
                String cname = cursor.getString(0);
                cnameList.add(cname);
                //searchList.add(cname);
                Cursor cursor2 = details.getitemsdata(cname);
                if (cursor2.moveToFirst()) {
                    do {
                        String iname;
                        iname = cursor2.getString(0);
                        inameList.add(iname);
                       // searchList.add(iname);
                    } while (cursor2.moveToNext());

                }
            } while (cursor.moveToNext());

        }

       adapter.addfragment(new CategoryFragment(cnameList,""),"category");
        adapter.addfragment(new ItemsFragment(inameList),"items");

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
           searchbarview();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void searchbarview() {
        int getvisibility=view_searchbar.getVisibility();
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

        if(getvisibility>0)
        {
            view_searchbar.setVisibility(View.VISIBLE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
        else
        {
            view_searchbar.setVisibility(View.INVISIBLE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
        searchtext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0)
                    searchtext.setError(Html.fromHtml("<font color='#fffffff'>enter valid data</font>"));
                else
                {

                    adapter.updatefragment(new CategoryFragment(inameList,""),"category");
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


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

class SearchTask extends AsyncTask<String,Void,Void>
{
    @Override
    protected Void doInBackground(String... params) {
        String s=params[0];

        return null;
    }
}

}

