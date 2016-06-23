package com.gaia.app.smartwarehouse.service;

import android.os.AsyncTask;

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

import static com.gaia.app.smartwarehouse.service.CommonUtilities.ADDITEM_URL;

/**
 * Created by anant on 23/06/16.
 */

public class AddItemTask extends AsyncTask<String,Void,String> {

    public AddItemTask(Afteradd work) {
        super();
        this.work=work;
    }

    public interface Afteradd{
        void getMessage(String s);
    }

    private Afteradd work = null;

    @Override
    protected String doInBackground(String... strings) {
        String email = strings[0];
        String name = strings[1];
        String unit = strings[2];
        String cat = strings[3];

        try {

            URL url = new URL(ADDITEM_URL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);

            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

            String data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8")
                    +URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8")
                    + URLEncoder.encode("unit", "UTF-8") + "=" + URLEncoder.encode(unit, "UTF-8")
                    + URLEncoder.encode("cat", "UTF-8") + "=" + URLEncoder.encode(cat, "UTF-8") ;
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "ISO-8859-1"));
            StringBuilder stringBuilder = new StringBuilder();

            String JSON_STRING;
            while ((JSON_STRING = bufferedReader.readLine()) != null) {
                stringBuilder.append(JSON_STRING + "\n");
            }

            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return stringBuilder.toString().trim();


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
        try {
            JSONObject object = new JSONObject(s);
            String TAG_RESULT = "message";
            String message = object.getString(TAG_RESULT);
            work.getMessage(message);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
