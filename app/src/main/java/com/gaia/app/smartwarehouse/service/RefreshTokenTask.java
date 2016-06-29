package com.gaia.app.smartwarehouse.service;

import android.os.AsyncTask;

import com.google.firebase.iid.FirebaseInstanceId;

/**
 * Created by anant on 29/06/16.
 */

public class RefreshTokenTask extends AsyncTask<String, Void, String> {
    private String email;
    @Override
    protected String doInBackground(String... strings) {
        email = strings[0];
        String token = FirebaseInstanceId.getInstance().getToken();
        return token;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        SendTokenTask send = new SendTokenTask();
        send.execute(email,s);

    }

}
