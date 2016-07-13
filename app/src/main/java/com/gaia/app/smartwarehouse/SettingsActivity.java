package com.gaia.app.smartwarehouse;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gaia.app.smartwarehouse.classes.Userdata;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by praveen_gadi on 6/19/2016.
 */
public class SettingsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {


    public LinearLayoutManager layoutManager;
    private Userdata details;
    private ProgressBar progressBar;
    private TextView textView;
    private EditText et_changemail;
    private EditText et1,et2,et3;
    String email,password;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>=21)
        {
            TransitionInflater transitionInflater=TransitionInflater.from(this);
            Transition transition=transitionInflater.inflateTransition(R.transition.transition_slide_right);
            getWindow().setEnterTransition(transition);
            getWindow().setReturnTransition(transition);

            TransitionInflater transitionInflater2=TransitionInflater.from(this);
            Transition transition2=transitionInflater2.inflateTransition(R.transition.transition_slide_left);
            getWindow().setExitTransition(transition2);
            getWindow().setReenterTransition(transition2);
        }
        setContentView(R.layout.activity_settings);

        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordi);
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.content_settings, coordinatorLayout);

        textView= (TextView) findViewById(R.id.textView);

      /*  ProgressDialog progressDialog=new ProgressDialog(SettingsActivity.this);
        progressDialog.setMessage("Loading..... ");
        progressDialog.show();*/

        Userdata details =new Userdata(this);
        Cursor cursor=details.getuserdata();

        if(cursor.moveToFirst())
        {
            do{

                email=cursor.getString(0);
                password=cursor.getString(1);
                textView.setText(email);
                Toast.makeText(getBaseContext(),password,Toast.LENGTH_LONG).show();

            }while (cursor.moveToNext());



        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        /*ObjectAnimator objectAnimator=ObjectAnimator.ofInt(progressBar,"progress",0,100);
        objectAnimator.setDuration(2000);
        objectAnimator.setInterpolator(new DecelerateInterpolator());
        objectAnimator.start();*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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

    public void logout(View view)
    {
        details = new Userdata(this);
        SQLiteDatabase sqLiteDatabase = details.getWritableDatabase();
        details.cleardata();
        finish();
        Intent intent =new Intent(this,LoginActivity.class);
        startActivity(intent);

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
        else if (id == R.id.login) {
            Intent intent=new Intent(this,LoginActivity.class);
            startActivity(intent);

        }else if (id == R.id.account_settings) {

        }
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }


    public void changeemail(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.DialogBoxStyle);

        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogview = inflater.inflate(R.layout.content_dialogbox, null);
        builder.setView(dialogview);
        et_changemail = (EditText) dialogview.findViewById(R.id.editText_dialogbox);
        et_changemail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!validateEmail(s.toString()))
                    et_changemail.setError("Enter a valid E-mail address");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        builder.setTitle("Change email");
        builder.setPositiveButton("change", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String string = et_changemail.getText().toString().trim();
                if(!validateEmail(string))
                {
                    Toast.makeText(getBaseContext(),"Invalid Email Address ",Toast.LENGTH_LONG).show();
                }
                else
                {

                }

            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }
    public void changepassword(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.DialogBoxStyle);

        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogview = inflater.inflate(R.layout.content_changepassword, null);
        builder.setView(dialogview);
        builder.setTitle("Change Password");
        et1= (EditText)dialogview.findViewById(R.id.et_changepassword);
        et2= (EditText)dialogview.findViewById(R.id.et_newpassword);
        et3= (EditText)dialogview.findViewById(R.id.et_renternewpassword);
        et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               if(!validatePassword(s.toString()))
               {
                   et1.setError("Password must have minimum 6 characters");
               }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!validatePassword(s.toString()))
                {
                    et2.setError("Password must have minimum 6 characters");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!validatePassword(s.toString()))
                {
                    et3.setError("Password must have minimum 6 characters");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        builder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                 String str=validatechangingPassword(et1.getText().toString().trim(),et2.getText().toString().trim(),et3.getText().toString().trim());
                switch (str)
                {
                    case "00" :
                        Toast.makeText(getBaseContext(),"Password not matching with previous",Toast.LENGTH_LONG).show();
                        break;
                    case "01" :
                        Toast.makeText(getBaseContext(),"new passwords are not matching",Toast.LENGTH_LONG).show();
                        break;
                    case "11" :

                        break;
                    default:
                        break;
                }

            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
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
        if(password.length()<6)
            return false;
        else
            return true;

    }
    public String validatechangingPassword(String changepassword,String newpassword,String renewpassword)
    {

       if(!password.equals(changepassword) && password.length()>0 && changepassword.length()>0)
           return "00";
       else if(!newpassword.equals(renewpassword) && newpassword.length()>0 && renewpassword.length()>0 )
           return "01";
         else
           return "11";

    }


}

