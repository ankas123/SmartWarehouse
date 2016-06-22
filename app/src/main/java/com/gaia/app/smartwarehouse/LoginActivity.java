package com.gaia.app.smartwarehouse;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.gaia.app.smartwarehouse.service.LoginTask;
import com.gaia.app.smartwarehouse.classes.Userdata;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private EditText et1, et2;
    private TextInputLayout textInput1,textInput2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordi);
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.content_login, coordinatorLayout);

        textInput1=(TextInputLayout)findViewById(R.id.login_textinput1);
        textInput2=(TextInputLayout)findViewById(R.id.login_textinput2);
        et1 = (EditText) findViewById(R.id.username);
        et2 = (EditText) findViewById(R.id.password);




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    public void signup(View view) {
        finish();
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    public void login(View view) {
        String email,password;
        email = et1.getText().toString().trim();
        password = et2.getText().toString().trim();

        if(!validateEmail(email))
            et1.setError("Enter a valid E-mail address");
        else
        {
            if((password.length())<6)
                et2.setError("Password must have minimum 6 characters");
                else
            {
                LoginTask httprequest = new LoginTask(LoginActivity.this);
                httprequest.execute(email,password);
            }

        }


    }
public boolean validateEmail(String email)
{

    final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    Pattern pattern=Pattern.compile(EMAIL_PATTERN);
    Matcher matcher=pattern.matcher(email);
        return matcher.matches();
}

    public boolean validatePassword(String password)
    {
        return false;
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


        } else if (id == R.id.wishlist) {


        } else if (id == R.id.notifications) {

        } else if (id == R.id.login) {

        } else if (id == R.id.account_settings) {

        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}

