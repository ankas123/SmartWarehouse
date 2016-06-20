package com.gaia.app.smartwarehouse.service;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.Toast;

import com.gaia.app.smartwarehouse.MainActivity;
import com.gaia.app.smartwarehouse.classes.Userdata;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by praveen_gadi on 6/15/2016.
 */
public class SignupTask extends AsyncTask<String, Void, String> {


    private Context context;
    Userdata details;
    String JSON_STRING;
    String SIGNUP_URL = "http://iotwithesp8266.net23.net/signup.php";
    public SignupTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {

        String id = params[0];

        details = new Userdata(context);
        String email = params[1], username = params[2], password = params[3];
        try {

            Userdata details = new Userdata(context);
            SQLiteDatabase sqLiteDatabase = details.getWritableDatabase();
            details.cleardata(sqLiteDatabase);

            details.updatedata(email, username, password, sqLiteDatabase);
            details.close();

            URL url = new URL(SIGNUP_URL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);

            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

            String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&" + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();

            InputStream inputStream = httpURLConnection.getInputStream();
            inputStream.close();

            httpURLConnection.disconnect();
            return "SignUp Succesful";

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s.equals("SignUp Succesful")) {
            Toast.makeText(context, s, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);

        } else if (s.length() == 0)
            Toast.makeText(context, "Problem in login", Toast.LENGTH_LONG).show();
        else {
            int c = 0;
            JSONObject jsonObject = null;
            String b = "login Successful", a = "", ch1 = null, ch2 = null, ch3 = null;
            try {
                jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("jsonstring");


                while (c < jsonArray.length()) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(c);
                    ch1 = jsonObject1.getString("email");
                    ch2 = jsonObject1.getString("username");
                    ch3 = jsonObject1.getString("password");
                    c++;
                }
                if (ch1.length() > 0 && ch2.length() > 0 && ch3.length() > 0) {

                    Userdata details = new Userdata(context);
                    SQLiteDatabase sqLiteDatabase = details.getWritableDatabase();
                    details.cleardata(sqLiteDatabase);

                    details.updatedata(ch1, ch2, ch3, sqLiteDatabase);
                    details.close();

                    Toast.makeText(context, b, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                } else
                    Toast.makeText(context, "Incorrect  Username or password ", Toast.LENGTH_LONG).show();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
