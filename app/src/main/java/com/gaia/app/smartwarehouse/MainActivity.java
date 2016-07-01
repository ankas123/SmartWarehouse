package com.gaia.app.smartwarehouse;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gaia.app.smartwarehouse.adapters.RecyclerRowAdapter;
import com.gaia.app.smartwarehouse.classes.Category;
import com.gaia.app.smartwarehouse.classes.Item;
import com.gaia.app.smartwarehouse.classes.ProductsData;
import com.gaia.app.smartwarehouse.classes.Userdata;
import com.gaia.app.smartwarehouse.service.ItemGetTask;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private EditText editText_dialog;
    private String email;
    private RecyclerView recyclerView;
    private RecyclerRowAdapter adapter;
    private LinearLayoutManager layoutManager;
    private ArrayList<String> itemSearchList = new ArrayList<String>();
    private ArrayList<Category> cname_list=new ArrayList<>();
    private ProgressBar spinner;
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
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.v("token",refreshedToken);
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordi);
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.content_main, coordinatorLayout);


        recyclerView = (RecyclerView) findViewById(R.id.rv1);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerRowAdapter(this, new ArrayList<Category>());
        recyclerView.setAdapter(adapter);

        spinner = (ProgressBar) findViewById(R.id.progressBar1);


        if (!isNetworkConnected()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.DialogBoxStyle);

            builder.setTitle(" No Internet Connection !");
            builder.setPositiveButton("Refresh", null);
            builder.setNegativeButton("Cancel", null);
            builder.show();

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

                    adapter.add(cat);
                } while (cursor.moveToNext());

            }

        }

        else {
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
                    cname_list.add(output);
                   details.create_category_table(output);
                }
            },spinner).execute(email);
         new Updatedata().execute(cname_list);

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
            Intent intent =new Intent(this,SearchbarActivity.class);
            startActivity(intent);

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
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);

        } else if (id == R.id.account_settings) {
            Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(i);
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


    class Updatedata extends AsyncTask<ArrayList<Category>,Void,Void>
    {
        Userdata details =new Userdata(MainActivity.this);
        @Override
        protected Void doInBackground(ArrayList<Category>... params) {
          for(int i=0;i<params[0].size();i++)
          {
              details.create_category_table(params[0].get(i));
          }
            return null;
        }
    }
}
