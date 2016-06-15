package com.gaia.app.smartwarehouse.resources;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.gaia.app.smartwarehouse.DetailActivity;
import com.gaia.app.smartwarehouse.R;
import com.gaia.app.smartwarehouse.SignupActivity;

/**
 * Created by anant on 14/06/16.
 */

public class NavItemSelect extends AppCompatActivity  {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    MenuItem item;
    DrawerLayout drawer;
    Context context;

    public NavItemSelect(Context context, MenuItem item, DrawerLayout drawer) {
        this.item = item;
        this.drawer = drawer;
        this.context = context;

    }

    public void action() {

        int id = item.getItemId();

        if (id == R.id.detail) {
            Intent i = new Intent(getApplicationContext(), DetailActivity.class);
            startActivity(i);

        }
        else if (id == R.id.wishlist) {

        }else if (id == R.id.login) {
            Intent intent =new Intent(this,SignupActivity.class);
            startActivity(intent);

        } else if (id == R.id.notifications) {

        } else if (id == R.id.account_settings) {

        }

        drawer.closeDrawer(GravityCompat.START);
    }


}