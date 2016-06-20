package com.gaia.app.smartwarehouse.service;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.Toast;

import com.gaia.app.smartwarehouse.MainActivity;
import com.gaia.app.smartwarehouse.classes.Userdata;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

import static com.gaia.app.smartwarehouse.service.CommonUtilities.LOGIN_URL;

/**
 * Created by praveen_gadi on 6/15/2016.
 */
public class LoginTask extends AsyncTask<String, Void, String> {


    private Context context;
    Userdata details;
    private String JSON_STRING;

    private JSONObject jsonObject;
    private String TAG_RESULT = "message";
    private String TAG_ID = "id";
    private String TAG_NAME = "name";
    private String TAG_PASS = "pass";
    private String TAG_FNAME = "fname";
    private String TAG_LNAME = "lname";
    private String TAG_ORGN = "orgn";
    private String TAG_ADDRESS = "address";
    private String TAG_DATE = "date";

    private String username, password;

    public LoginTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {

        details = new Userdata(context);

        try {
            username = params[1];
            password = params[2];

            URL url = new URL(LOGIN_URL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);

            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

            String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();


            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "ISO-8859-1"));
            StringBuilder stringBuilder = new StringBuilder();

            while ((JSON_STRING = bufferedReader.readLine()) != null) {
                stringBuilder.append(JSON_STRING + "\n");
            }

            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            stringBuilder.toString().trim();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        String name, pass, id, fname, lname, orgn, address, date;
        try {
            jsonObject = new JSONObject(s);
            String message = jsonObject.getString(TAG_RESULT);
            id = jsonObject.getString(TAG_ID);
            name = jsonObject.getString(TAG_NAME);
            pass = jsonObject.getString(TAG_PASS);
            fname = jsonObject.getString(TAG_FNAME);
            lname = jsonObject.getString(TAG_LNAME);
            orgn = jsonObject.getString(TAG_ORGN);
            address = jsonObject.getString(TAG_ADDRESS);
            date = jsonObject.getString(TAG_DATE);
            switch (message) {
                case "0":
                    Toast.makeText(context, "Username does not exists", Toast.LENGTH_LONG).show();
                    break;
                case "1":
                    Toast.makeText(context, "Password is Wrong", Toast.LENGTH_LONG).show();
                    break;
                case "100":
                    Userdata details = new Userdata(context);
                    SQLiteDatabase sqLiteDatabase = details.getWritableDatabase();
                    details.cleardata(sqLiteDatabase);
                    details.updatedata(id, name, pass, fname, lname, orgn, address, date, sqLiteDatabase);
                    details.close();
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);

                    break;
                default:
                    Toast.makeText(context, "Connection Error", Toast.LENGTH_LONG).show();
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
