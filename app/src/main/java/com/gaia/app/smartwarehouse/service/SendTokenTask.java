package com.gaia.app.smartwarehouse.service;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.gaia.app.smartwarehouse.service.CommonUtilities.TOKEN_URL;

/**
 * Created by anant on 29/06/16.
 */

public class SendTokenTask extends AsyncTask<String, Void, String> {
    private JSONObject jsonObject;
    private String TAG_RESULT = "message";

    @Override
    protected String doInBackground(String... strings) {
        String email = strings[0],token = strings[1];
        Log.v("tokenid",token);
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("token", token)
                .add("email", email)
                .build();

        Request request = new Request.Builder()
                .url(TOKEN_URL)
                .post(body)
                .build();
        Response response ;
        try {
            response = client.newCall(request).execute();
            return response.body().string().trim();
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
                    Log.v("tokensend","true");
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
