package com.gaia.app.smartwarehouse;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.gaia.app.smartwarehouse.service.SignupTask;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by praveen_gadi on 6/15/2016.
 */
public class SignupActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    private EditText et1,et3,et4;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(Build.VERSION.SDK_INT>=21)
        {
            TransitionInflater transitionInflater=TransitionInflater.from(this);
            Transition transition=transitionInflater.inflateTransition(R.transition.transition_slide_right);
            getWindow().setEnterTransition(transition);
            getWindow().setReturnTransition(transition);
        }

        setContentView(R.layout.activity_signup);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordi);
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.content_signup, coordinatorLayout);

        et1=(EditText)findViewById(R.id.email);
        et3=(EditText)findViewById(R.id.editText_password);
        et4=(EditText)findViewById(R.id.reenter_password);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }



    public void signup(View view)
    {
        String email,password,reenter_password,result;

        email=et1.getText().toString().trim();
        password=et3.getText().toString().trim();
        reenter_password=et4.getText().toString().trim();
        if(!validateEmail(email))
            et1.setError("Enter a valid E-mail address");
        else
        {
            result=(String) validatePassword(password,reenter_password);
            if((result.length()>0))
            {
                switch (result)
                {
                    case "01" :
                        et3.setError("Password must have minimum 6 characters");
                        break;
                    case "10" :
                        et4.setError("Password must have minimum 6 characters");
                        break;
                    case "00" :
                        Toast.makeText(getBaseContext(),"Passwords are not matching",Toast.LENGTH_LONG).show();
                        break;
                    case "11" :
                        SignupTask httprequest = new SignupTask(SignupActivity.this);
                        httprequest.execute(email,password);
                        break;
                    default:
                        Toast.makeText(getBaseContext(),"problem in validate password",Toast.LENGTH_LONG).show();
                        break;
                }
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
    public String validatePassword(String password,String repassword)
    {
        if(password.length()<6)
            return "01";
        else if(repassword.length()<6)
            return "10";
        else if (password.equals(repassword))
            return "11";
        else
            return "00";
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

        }
        else if (id == R.id.wishlist) {

        }else if (id == R.id.login) {

        } else if (id == R.id.notifications) {

        } else if (id == R.id.account_settings) {
        }
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}
