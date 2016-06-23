package com.gaia.app.smartwarehouse;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.Toast;

import com.gaia.app.smartwarehouse.classes.Userdata;
import com.gaia.app.smartwarehouse.service.AddItemTask;

/**
 * Created by anant on 23/06/16.
 */

public class Additem extends AppCompatActivity {

    String email;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newitem);

        Toolbar toolbar = (Toolbar) findViewById(R.id.additem_MyToolbar);

        setSupportActionBar(toolbar);

        String cname =getIntent().getStringExtra("cname");
        EditText editTexttname = (EditText) findViewById(R.id.additem_edittext);
        EditText editTextunit = (EditText) findViewById(R.id.additem_edittext1);
        String name =  editTexttname.getText().toString().trim();
        String unit = editTextunit.getText().toString().trim();
        Userdata details =new Userdata(this);
        Cursor cursor=details.getdata();

        if(cursor.moveToFirst()) {
            do {
                email = cursor.getString(0);

            } while (cursor.moveToNext());
        }

        new AddItemTask(new AddItemTask.Afteradd() {
            @Override
            public void getMessage(String s) {
                switch (s){
                    case "100":
                        break;
                    default:
                        Toast.makeText(Additem.this,"Error in sending",Toast.LENGTH_SHORT).show();
                }
            }
        }).execute(email,name,unit,cname);

    }
}
