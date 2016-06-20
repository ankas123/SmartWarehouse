package com.gaia.app.smartwarehouse.classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteTransactionListener;
import android.util.Log;

/**
 * Created by praveen_gadi on 6/17/2016.
 */
public class userdatabase extends SQLiteOpenHelper {

    public static final String DB_name="appdatabase";
    public static final int DB_version=1;
    public static final String Table_name="userdata";
    public static final String create_Table_query="CREATE TABLE userdata(email TEXT,username TEXT,password TEXT)";
    public static final String clear_Table_query="DELETE * FROM userdata";



    public userdatabase(Context context) {
        super(context,DB_name,null,DB_version);
        Log.e("Database     ","database created");
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




    public void updatedata(String email,String username,String password,SQLiteDatabase db)
    {
        ContentValues contentValues=new ContentValues();
        contentValues.put("email",email);
        contentValues.put("username",username);
        contentValues.put("password",password);
        db.insert(Table_name,null,contentValues);

        Log.e("Update Table  ","Table updated");
    }


public Cursor getdata(SQLiteDatabase db) {
    Cursor cursor;
    String[] projections = {"email", "password", "username"};
    cursor = db.query(Table_name, projections, null, null, null, null, null);
    return cursor;
}


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
