package com.gaia.app.smartwarehouse.classes;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.Toast;

import com.gaia.app.smartwarehouse.MainActivity;

import org.json.JSONArray;
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

/**
 * Created by praveen_gadi on 6/15/2016.
 */
public class BackgroundTask extends AsyncTask<String,Void,String> {


       public Context context;
    userdatabase details;
    String s;
        public BackgroundTask(Context context) {
        this.context = context;
        }

@Override
protected String doInBackground(String... params) {
        String signupurl = "http://iotwithesp8266.net23.net/signup.php";
        String id = params[0];

    details=new userdatabase(context);

        if (id.equals("signup")) {

        String email = params[1], username = params[2], password = params[3];
        try {

            userdatabase details =new userdatabase(context);
            SQLiteDatabase sqLiteDatabase=details.getWritableDatabase();
            details.cleardata(sqLiteDatabase);

            details.updatedata(email,username,password,sqLiteDatabase);
            details.close();

        URL url = new URL(signupurl);
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

        }
    if(id.equals("login")) {
        String loginurl = "http://iotwithesp8266.net23.net/login.php";
        String result = null;

        try {
            String username = params[1], password = params[2];

            URL url = url = new URL(loginurl);
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
            String ch = "", jsonstring;
            while ((jsonstring = bufferedReader.readLine()) != null) {
                ch += jsonstring;
                ch += "\n";
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return ch;


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        return "problem in uploading";
        }

@Override
protected void onPostExecute(String s) {
        super.onPostExecute(s);
    if(s.equals("SignUp Succesful")) {
        Toast.makeText(context, s, Toast.LENGTH_LONG).show();
        Intent intent=new Intent(context, MainActivity.class);
        context.startActivity(intent);

    }
    else if(s.length()==0)
        Toast.makeText(context,"Problem in login", Toast.LENGTH_LONG).show();
    else
    {
        int c=0;
        JSONObject jsonObject= null;
        String b="login Successful",a="",ch1 = null,ch2 = null,ch3 = null;
        try {
            jsonObject = new JSONObject(s);
            JSONArray jsonArray=jsonObject.getJSONArray("jsonstring");


            while (c<jsonArray.length())
            {
                JSONObject jsonObject1=jsonArray.getJSONObject(c);
                ch1=jsonObject1.getString("email");
               ch2 =jsonObject1.getString("username");
                ch3 =jsonObject1.getString("password");
                c++;
            }
            if(ch1.length()>0 && ch2.length()>0 && ch3.length()>0) {

                userdatabase details =new userdatabase(context);
                SQLiteDatabase sqLiteDatabase=details.getWritableDatabase();
                details.cleardata(sqLiteDatabase);

                details.updatedata(ch1,ch2,ch3,sqLiteDatabase);
                details.close();

                Toast.makeText(context, b, Toast.LENGTH_LONG).show();
                Intent intent=new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }
            else
                Toast.makeText(context,"Incorrect  Username or password ", Toast.LENGTH_LONG).show();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
        }
}
