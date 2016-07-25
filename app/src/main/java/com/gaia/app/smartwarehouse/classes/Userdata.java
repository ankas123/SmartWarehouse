package com.gaia.app.smartwarehouse.classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by praveen_gadi on 6/17/2016.
 */
public class Userdata extends SQLiteOpenHelper {
    /*
    * SQLite is an open-source relational database
    * i.e. used to perform database operations on android devices such as storing, manipulating or retrieving persistent data from the database.

     * It is embedded in android bydefault.

     * SQLiteOpenHelper class provides the functionality to use the SQLite database.
    */

    private static final String DB_name = "appdatabase";
    private static final int DB_version = 1;
    private static final String Table_name = "userdata";
    private static final String Category_Table_name = "categorynames";
    private static final String create_Table_query = "CREATE TABLE userdata(email TEXT,pass TEXT,fname TEXT,lname TEXT,orgn TEXT,address TEXT,date TEXT)";
    private static final String categorynames_Table_query = "CREATE TABLE categorynames(cname TEXT)";
    private static final String TAG_NAME = "email";
    private static final String TAG_PASS = "pass";
    private static final String TAG_FNAME = "fname";
    private static final String TAG_LNAME = "lname";
    private static final String TAG_ORGN = "orgn";
    private static final String TAG_ADDRESS = "address";
    private static final String TAG_DATE = "date";

    private static final String ITEM_CATEGORY = "cname";
    private static final String ITEM_ID = "id";
;    private static final String ITEM_NAME = "iname";
    private static final String ITEM_UNIT = "unit";
    private static final String ITEM_WEIGHT = "weight";
    private static final String ITEM_QUANTITY = "quant";

    private static SQLiteDatabase readable_db, writable_db;

    private Context context;


    public Userdata(Context context) {
        //Context of an Activity is necessary
        super(context, DB_name, null, DB_version);
        this.context = context;
        Log.e("Database  ", "database created");
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        /*It is called only once when the Database is created. Tables which are compulsory have to be created here
        *
        * Here we are creating only one table for storing User details
        *
        * */
        db.execSQL(create_Table_query);
        db.execSQL(categorynames_Table_query);
        Log.e("Table ", "Table created");
    }

    public void cleardata() {

        /*
        * This method is called when user made logout
         * database is completely cleared execpt userdata and categorynames Tables(empty)
        * */
        writable_db = this.getWritableDatabase();
        readable_db = this.getReadableDatabase();

        writable_db.delete(Table_name, null, null);

        String[] projections = {ITEM_CATEGORY};
        Cursor cursor = readable_db.query(Category_Table_name, projections, null, null, null, null, null);


        if (cursor.moveToFirst()) {
            do {
                String cname = cursor.getString(0);
                writable_db.delete(cname, null, null);
            } while (cursor.moveToNext());

        }
        writable_db.delete(Category_Table_name, null, null);
        Log.e("Table ", "Table cleared");
    }


    public void updateuserdata(String email, String password, String fname, String lname, String orgn, String address, String date) {
        /*
        * This method is called  at the starting of app and  when an  another user logins
        *
        * */
        writable_db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TAG_NAME, email);
        contentValues.put(TAG_PASS, password);
        contentValues.put(TAG_FNAME, fname);
        contentValues.put(TAG_LNAME, lname);
        contentValues.put(TAG_ORGN, orgn);
        contentValues.put(TAG_ADDRESS, address);
        contentValues.put(TAG_DATE, date);
        writable_db.insert(Table_name, null, contentValues);

        Log.e("Update Table  ", "Table updated");
    }


    public Cursor getuserdata() {
        /*
        *
        *This method is called whenever there is a need of userdetails
        * It is usually called for getting email address of the user.
        * */
        readable_db = this.getReadableDatabase();
        Cursor cursor;
        String[] projections = {TAG_NAME, TAG_FNAME, TAG_LNAME, TAG_ORGN, TAG_ADDRESS, TAG_DATE};
        cursor = readable_db.query(Table_name, projections, null, null, null, null, null);
        return cursor;
    }
    public void create_category_table(Category category) {
        /*
        * This method is called  at the starting of app  when data is loaded from online server from MainActivity
        * In this method, Actually the data is updated.Tables are previously created and the data is updates in those tables
        *
        * */
        writable_db = this.getWritableDatabase();
        writable_db.execSQL("DROP TABLE IF EXISTS " + category.getCname());

        writable_db.delete(Category_Table_name, "cname = ?", new String[]{category.getCname().trim()});

        ContentValues cnames = new ContentValues();
        cnames.put(ITEM_CATEGORY, category.getCname());
        writable_db.insert(Category_Table_name, null, cnames);

        String create_category_table = "CREATE TABLE " + category.getCname().trim() + "(id TEXT ,iname TEXT ,unit TEXT,weight TEXT,quant TEXT)";
        writable_db.execSQL(create_category_table);

        ArrayList<Item> items = category.getItems();

        for (int i = 0; i < items.size(); i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(ITEM_ID,items.get(i).getId());
            contentValues.put(ITEM_NAME, items.get(i).getIname());
            contentValues.put(ITEM_UNIT, items.get(i).getUnit());
            contentValues.put(ITEM_WEIGHT, items.get(i).getWeight());
            contentValues.put(ITEM_QUANTITY, items.get(i).getQuant());

            writable_db.insert(category.getCname(), null, contentValues);
        }
        Log.e("Offline data", "Offline data updated");
    }




    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
