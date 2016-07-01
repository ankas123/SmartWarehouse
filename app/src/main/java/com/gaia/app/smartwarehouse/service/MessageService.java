package com.gaia.app.smartwarehouse.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.gaia.app.smartwarehouse.ItemActivity;
import com.gaia.app.smartwarehouse.MainActivity;
import com.gaia.app.smartwarehouse.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by anant on 28/06/16.
 */

public class MessageService extends FirebaseMessagingService {
    private String cat, name, weight;
    private final Handler handler = new Handler();
    public static final String BROADCAST_ACTION = "com.gaia.app.smartwarehouse.service.changedata";

    Intent intent = new Intent(BROADCAST_ACTION);
    private Runnable sendUpdatesToUI = new Runnable() {
        public void run() {
            Log.v("sending","yay");
            intent.putExtra("cat", cat);
            intent.putExtra("name", name);
            intent.putExtra("weight", weight);
            sendBroadcast(intent);
        }
    };

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle("FCM Test")
                .setContentText(remoteMessage.getData().get("weight"))
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                .setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        manager.notify(0, builder.build());

        cat = remoteMessage.getData().get("cat");
        name = remoteMessage.getData().get("name");
        weight = remoteMessage.getData().get("weight");
        Log.v("data", cat + " " + name + " " + weight);

        ItemActivity.refreshItem(cat, name, weight);


        handler.removeCallbacks(sendUpdatesToUI);
        handler.post(sendUpdatesToUI);


    }
}
