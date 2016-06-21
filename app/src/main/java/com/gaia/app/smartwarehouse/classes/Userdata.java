package com.gaia.app.smartwarehouse.classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by praveen_gadi on 6/17/2016.
 */
public class Userdata extends SQLiteOpenHelper {

    private static final String DB_name="appdatabase";
    private static final int DB_version=1;
    private static final String Table_name="userdata";
    private static final String create_Table_query="CREATE TABLE userdata(email TEXT,pass TEXT,fname TEXT,lname TEXT,orgn TEXT,address TEXT,date TEXT)";
    private static final String clear_Table_query="DELETE * FROM userdata";
    private String TAG_NAME = "email";
    private String TAG_PASS = "pass";
    private String TAG_FNAME = "fname";
    private String TAG_LNAME = "lname";
    private String TAG_ORGN = "orgn";
    private String TAG_ADDRESS = "address";
    private String TAG_DATE = "date";




    public Userdata(Context context) {
        super(context,DB_name,null,DB_version);
        Log.e("Database     ","database created");
    }


    public Userdata(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
           db.execSQL(create_Table_query);
        Log.e("Table ","Table created");
    }
    public void cleardata(SQLiteDatabase db)
    {
          db.delete(Table_name,null,null);
        Log.e("Table ","Table cleared");
    }




    public void updatedata(String email,String password,String fname,String lname,String orgn,String address,String date,SQLiteDatabase db)
    {
        ContentValues contentValues=new ContentValues();
        contentValues.put(TAG_NAME,email);
        contentValues.put(TAG_PASS,password);
        contentValues.put(TAG_FNAME,fname);
        contentValues.put(TAG_LNAME,lname);
        contentValues.put(TAG_ORGN,orgn);
        contentValues.put(TAG_ADDRESS,address);
        contentValues.put(TAG_DATE,date);
        db.insert(Table_name,null,contentValues);

        Log.e("Update Table  ","Table updated");
    }


public Cursor getdata(SQLiteDatabase db) {
    Cursor cursor;
    String[] projections = {TAG_NAME,TAG_PASS};
    cursor = db.query(Table_name, projections, null, null, null, null, null);
    return cursor;
}


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
