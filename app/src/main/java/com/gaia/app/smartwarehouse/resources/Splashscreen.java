package com.gaia.app.smartwarehouse.resources;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        progressBar.setProgress(0);
        progress();

    }
    public void progress()
    {

        Userdata details =new Userdata(Splashscreen.this);
        SQLiteDatabase db=details.getReadableDatabase();
        Cursor cursor=details.getdata(db);
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

    }
}
