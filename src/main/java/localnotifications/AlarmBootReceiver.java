package localnotifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmBootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED") || intent.getAction().equals("android.intent.action.QUICKBOOT_POWERON") ) {
            NotificationHelper.scheduleRepeatingRTCNotification(context);
            NotificationHelper.scheduleRepeatingMorningRTCNotification(context);
        }
    }
}