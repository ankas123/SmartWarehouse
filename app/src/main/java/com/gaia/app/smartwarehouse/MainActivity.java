package com.gaia.app.smartwarehouse;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.EditText;

import com.gaia.app.smartwarehouse.adapters.RecyclerRowAdapter;
import com.gaia.app.smartwarehouse.classes.Category;
import com.gaia.app.smartwarehouse.classes.Item;
import com.gaia.app.smartwarehouse.classes.ProductsData;
import com.gaia.app.smartwarehouse.classes.Userdata;
import com.gaia.app.smartwarehouse.service.ItemGetTask;
import com.gaia.app.smartwarehouse.service.MessageService;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private EditText editText_dialog;
    private String email;
    private RecyclerView recyclerView;
    private RecyclerRowAdapter adapter;
    private LinearLayoutManager layoutManager;
    private ArrayList<String> itemSearchList = new ArrayList<String>();
    private View view;
    private ProgressDialog pdia;
    private ArrayList<Category> cnameList=new ArrayList<>();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

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
        if(Build.VERSION.SDK_INT>=21)
        {

           TransitionInflater transitionInflater=TransitionInflater.from(this);
            Transition transition=transitionInflater.inflateTransition(R.transition.transition_slide_left);
            getWindow().setExitTransition(transition);
            getWindow().setReenterTransition(transition);
        }
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.v("token", refreshedToken);
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordi);
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.content_main, coordinatorLayout);



        recyclerView = (RecyclerView) findViewById(R.id.rv1);

        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerRowAdapter(this, new ArrayList<Category>());
        ScaleInAnimationAdapter AnimationAdapter=new ScaleInAnimationAdapter(adapter);
        AnimationAdapter.setDuration(750);
        AnimationAdapter.setInterpolator(new OvershootInterpolator());
        recyclerView.setAdapter(AnimationAdapter);



        if (!isNetworkConnected()) {

            Snackbar snackbar=Snackbar.make(findViewById(R.id.contentmain), "No Network Connection", Snackbar.LENGTH_INDEFINITE);
            View view=snackbar.getView();
            view.setBackgroundColor(ContextCompat.getColor(this,R.color.snackbarcolor));
            snackbar.show();


            final ProductsData details = new ProductsData(this);

            Cursor cursor = details.getcategorydata();
            if (cursor.moveToFirst()) {

                do {
                    ArrayList<Item> itemArrayList = new ArrayList<>();
                    String cname = cursor.getString(0);
                    Cursor cursor2 = details.getitemsdata(cname);
                    if (cursor2.moveToFirst()) {
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
                    Category cat = new Category(cname, itemArrayList);

                    adapter. add(cat);
                } while (cursor.moveToNext());

            }

        } else {
            final Userdata details = new Userdata(this);
            Cursor cursor = details.getuserdata();

            if (cursor.moveToFirst()) {
                do {
                    email = cursor.getString(0);

                } while (cursor.moveToNext());
            }


            ItemGetTask asyncTask = (ItemGetTask) new ItemGetTask(new ItemGetTask.PlottingItems() {

                @Override
                public void setItems(Category output) {
                    adapter.add(output);
                     cnameList.add(output);

                }

                @Override
                public void progress() {
                    pdia = new ProgressDialog(MainActivity.this);
                    pdia.setMessage("Loading...");
                    pdia.show();
                }

                @Override
                public void stop() {
                    pdia.dismiss();
                }


            }).execute(email);
           new  UpdateCategoryData().execute(cnameList);

        }


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

        this.registerReceiver(broadcastReceiver, new IntentFilter(MessageService.BROADCAST_ACTION));

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    public void addproduct(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.DialogBoxStyle);

        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogview = inflater.inflate(R.layout.content_dialogbox, null);
        builder.setView(dialogview);
        editText_dialog = (EditText) dialogview.findViewById(R.id.editText_dialogbox);
        builder.setTitle("Add Category");
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String string = editText_dialog.getText().toString().trim();
                Intent intent = new Intent(MainActivity.this, Additem.class);
                intent.putExtra("cname", string);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", null);
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
                "Main Page", // TODO: Define a title for the content shown.
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
                "Main Page", // TODO: Define a title for the content shown.
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

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.v("received", "yes");
            refreshMain(intent.getStringExtra("cat"), intent.getStringExtra("name"), intent.getStringExtra("weight"));
            new RefreshItemWeight().execute(intent.getStringExtra("cat"), intent.getStringExtra("name"), intent.getStringExtra("weight"));
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, new IntentFilter(MessageService.BROADCAST_ACTION));
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);

    }


    public void refreshMain(String cat, String name, String weight) {
        Log.v("cat", cat);
        final String fcat = cat, fname = name, fweight = weight;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.refreshWeight(fcat, fname, fweight);
            }
        });

    }

    class RefreshItemWeight extends AsyncTask<String,Void,Void> {
        ProductsData refresh_data=new ProductsData(MainActivity.this);
        SQLiteDatabase db=refresh_data.getWritableDatabase();
        @Override
        protected Void doInBackground(String... params) {
            String cname=params[0],iname=params[1],weight=params[2];
            ContentValues contentValues=new ContentValues();
            Log.v("working","yes");
            contentValues.put("weight",weight);
            db.update(cname,contentValues,"iname = ?", new String[]{iname});
            return null;
        }
    }
    class UpdateCategoryData extends AsyncTask<ArrayList<Category>,Void,Void>
    {
           ProductsData details=new ProductsData(MainActivity.this);
        @Override
        protected Void doInBackground(ArrayList<Category>... params) {
            for(int i=0;i<params[0].size();i++)
                details.create_category_table(params[0].get(i));
            return null;
        }
    }
}
