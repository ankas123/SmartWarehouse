package com.gaia.app.smartwarehouse.service;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by anant on 29/06/16.
 */

public class InstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.v("token","1"+refreshedToken);
    }

    private void sendRegistrationToServer(String token) {


    }


}
