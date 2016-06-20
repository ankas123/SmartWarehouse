package com.gaia.app.smartwarehouse.resources;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gaia.app.smartwarehouse.LoginActivity;
import com.gaia.app.smartwarehouse.MainActivity;
import com.gaia.app.smartwarehouse.R;
import com.gaia.app.smartwarehouse.classes.userdatabase;

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

        Thread thread =new Thread()
        {
            public void run()
            {
                try {
                   int i;
                    for(i=0;i<100;i+=2) {
                       progressBar.setProgress(i);
                        sleep(50);
                    }


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {

                    userdatabase details =new userdatabase(Splashscreen.this);
                    SQLiteDatabase db= details.getReadableDatabase();
                    Cursor cursor = details.getdata(db);

                    if(cursor.moveToFirst())
                    {
                        finish();
                        Intent intent = new Intent(Splashscreen.this, MainActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        finish();
                        Intent intent = new Intent(Splashscreen.this, LoginActivity.class);
                        startActivity(intent);
                    }

                }
                           }

        };
        thread.start();
    }
}
