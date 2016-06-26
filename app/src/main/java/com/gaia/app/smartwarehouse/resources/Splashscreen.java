package com.gaia.app.smartwarehouse.resources;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import com.gaia.app.smartwarehouse.LoginActivity;
import com.gaia.app.smartwarehouse.MainActivity;
import com.gaia.app.smartwarehouse.R;
import com.gaia.app.smartwarehouse.classes.Userdata;

public class Splashscreen extends AppCompatActivity {

    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        progress();

    }

    class Progress extends AsyncTask<Void,Void,Void>{
        Userdata details =new Userdata(Splashscreen.this);
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Cursor cursor=details.getuserdata();
            if(cursor.moveToFirst())
            {
                Intent intent = new Intent(Splashscreen.this, MainActivity.class);
                startActivity(intent);
            }
            else
            {
                Intent intent = new Intent(Splashscreen.this, LoginActivity.class);
                startActivity(intent);
            }

            finish();


            return null;


        }
    }
    public void progress()
    {




    }
}
