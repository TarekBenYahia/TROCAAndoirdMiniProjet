package com.example.troca;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyBroadcastReceiver extends BroadcastReceiver {
    static final String ACTION_SNOOZE= "OK";
    static final String EXTRA_NOTIFICATION_ID = "notification-id";
    private static final String TAG = "receiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if(ACTION_SNOOZE.equals(intent.getAction())){
            int notificationId = intent.getExtras().getInt(EXTRA_NOTIFICATION_ID);
            Log.e(TAG,"Cancel notification with id "+notificationId);
            NotificationManager notificationManager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(notificationId);

        }

    }
}
