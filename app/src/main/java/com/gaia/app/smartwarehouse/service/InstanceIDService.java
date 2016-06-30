package com.gaia.app.smartwarehouse.service;

import android.database.Cursor;
import android.util.Log;

import com.gaia.app.smartwarehouse.classes.Userdata;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by anant on 29/06/16.
 */

public class InstanceIDService extends FirebaseInstanceIdService {

    private final Userdata details = new Userdata(this);;

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.v("token",refreshedToken);
        sendToken(refreshedToken);
    }

    private void sendToken(String refreshedToken) {

        Cursor cursor = details.getuserdata();
        String email;
        if (cursor.moveToFirst()) {
            email = cursor.getString(0);
            SendTokenTask send = new SendTokenTask();
            send.execute(refreshedToken,email);
        }

    }

}
