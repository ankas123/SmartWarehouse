package com.gaia.app.smartwarehouse;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

/**
 * Created by anant on 23/06/16.
 */

public class Additem extends AppCompatActivity {
    private EditText name;
    private EditText unit;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newitem);

        Toolbar toolbar = (Toolbar) findViewById(R.id.additem_MyToolbar);

        setSupportActionBar(toolbar);


        Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("cname");
        String cname =(String) bundle.get("cname");


    }
}
