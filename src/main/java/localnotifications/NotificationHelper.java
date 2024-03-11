package localnotifications;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

public class NotificationHelper {

    public static int ALARM_TYPE_RTC = 100;
    private static AlarmManager alarmManagerRTC,alarmManagerRTC2;
    private static PendingIntent alarmIntentRTC,alarmIntentRTC2;

    public static void scheduleRepeatingRTCNotification(Context context){

        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY,15);
        calendar.set(Calendar.MINUTE,00);

        //Setting intent to class where Alarm broadcast message will be handled
        Intent intent=new Intent(context,AlarmReceiver.class);
        intent.putExtra("notificationtime","Evening");
        //Setting alarm pending intent
        alarmIntentRTC=PendingIntent.getBroadcast(context,ALARM_TYPE_RTC,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        //getting instance of AlarmManager service
        alarmManagerRTC=(AlarmManager)context.getSystemService(ALARM_SERVICE);

        //Setting alarm to wake up device every day for clock time.
        alarmManagerRTC.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,alarmIntentRTC);
    }

    public static void scheduleRepeatingMorningRTCNotification(Context context){
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY,8);
        calendar.set(Calendar.MINUTE,00);

        //Setting intent to class where Alarm broadcast message will be handled
        Intent intent=new Intent(context,AlarmReceiver.class);
        intent.putExtra("notificationtime","Morning");
        //Setting alarm pending intent
        alarmIntentRTC2=PendingIntent.getBroadcast(context,ALARM_TYPE_RTC,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        //getting instance of AlarmManager service
        alarmManagerRTC2=(AlarmManager)context.getSystemService(ALARM_SERVICE);

        //Setting alarm to wake up device every day for clock time.
        alarmManagerRTC2.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,alarmIntentRTC2);

    }

    public static NotificationManager getNotificationManager(Context context) {
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    /*
     * Enable boot receiver to persist alarms set for notifications across device reboots
     */

    public static void enableBootReceiver(Context context) {
        ComponentName receiver = new ComponentName(context, AlarmBootReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    public static void disableBootReceiver(Context context) {

        ComponentName receiver = new ComponentName(context, AlarmBootReceiver.class);
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }

}
