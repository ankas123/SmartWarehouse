package com.gaia.app.smartwarehouse.service;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import static com.gaia.app.smartwarehouse.service.CommonUtilities.SIGNUP_URL;

/**
 * Created by praveen_gadi on 6/15/2016.
 */
public class SignupTask extends AsyncTask<String, Void, String> {


    private Context context;
    private Userdata details;
    private JSONObject jsonObject;
    private String JSON_STRING, email, password;
    private String TAG_RESULT = "message";


    public SignupTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {

        details = new Userdata(context);
        email = params[0];
        password = params[1];
        try {


            URL url = new URL(SIGNUP_URL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);

            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

            String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8")  + "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
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
            Log.v("fkjhkj",stringBuilder.toString().trim());
            return stringBuilder.toString().trim();

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

        try {
            jsonObject = new JSONObject(s);
            String message = jsonObject.getString(TAG_RESULT);
            switch (message) {
                case "100":
                    details = new Userdata(context);
                    details.cleardata();

                    details.updateuserdata(email, password, null, null, null, null, null);
                    details.close();

                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
