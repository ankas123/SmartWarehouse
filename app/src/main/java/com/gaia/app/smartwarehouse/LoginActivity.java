package com.gaia.app.smartwarehouse;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
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

import com.gaia.app.smartwarehouse.service.LoginTask;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity  {


    private EditText et1, et2;
    private TextInputLayout textInput1,textInput2;

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

        if (!isNetworkConnected()) {

            Snackbar snackbar = Snackbar.make(findViewById(R.id.cordilogin), "No Network Connection", Snackbar.LENGTH_INDEFINITE);
            View view2 = snackbar.getView();
            view2.setBackgroundColor(ContextCompat.getColor(this, R.color.snackbarcolor));
            snackbar.show();
        }





        et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!validateEmail(s.toString()))
                    et1.setError("Enter a valid E-mail address");
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
                    et2.setError("Password must have minimum 6 characters");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    public void signup(View view) {

        ActivityOptionsCompat activityOptionsCompat=ActivityOptionsCompat.makeSceneTransitionAnimation(this,null);
        Intent intent = new Intent(this,SignupActivity.class);
        startActivity(intent,activityOptionsCompat.toBundle());
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
                if (!isNetworkConnected()) {

                    Snackbar snackbar = Snackbar.make(findViewById(R.id.cordilogin), "No Network Connection", Snackbar.LENGTH_INDEFINITE);
                    View view2 = snackbar.getView();
                    view2.setBackgroundColor(ContextCompat.getColor(this, R.color.snackbarcolor));
                    snackbar.show();
                }
                else {
                    LoginTask httprequest = new LoginTask(LoginActivity.this, LoginActivity.this);
                    httprequest.execute(email, password);
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

    public boolean validatePassword(String password)
    {
        if(password.length()<6)
        return false;
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finish();

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

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}

