package com.orenda.taimo.grade3tograde5;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import firebase.analytics.AppAnalytics;

public class NotificationDismissedReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.wtf("-NotificationDismissedReceiver", "dismiss : ");
        new AppAnalytics(context).CustomNotificationDismissed(intent.getStringExtra("NotificationId"),
                intent.getStringExtra("NotificationTime"),intent.getStringExtra("NotificationType"));

    }
}
