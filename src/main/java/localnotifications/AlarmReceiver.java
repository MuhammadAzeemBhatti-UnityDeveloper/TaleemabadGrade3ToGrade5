package localnotifications;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import androidx.core.app.NotificationCompat;
import android.widget.RemoteViews;

import com.orenda.taimo.grade3tograde5.R;
import com.orenda.taimo.grade3tograde5.UnityPlayerActivity;

import java.util.Random;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context,Intent intent) {
        String notificationTime=intent.getStringExtra("notificationtime");
        if(notificationTime.equals("Morning")){
            Intent intentToRepeat = new Intent(context, UnityPlayerActivity.class);
            //set flag to restart/relaunch the app
            intentToRepeat.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            //Pending intent to handle launch of Activity in intent above

            PendingIntent pendingIntent =
                    PendingIntent.getActivity(context, NotificationHelper.ALARM_TYPE_RTC, intentToRepeat, PendingIntent.FLAG_UPDATE_CURRENT);

            //Build notification
            Notification repeatedNotification = buildLocalNotification(context, pendingIntent,notificationTime).build();

            //Send local notification
            NotificationHelper.getNotificationManager(context).notify(NotificationHelper.ALARM_TYPE_RTC, repeatedNotification);

        }
        else if(notificationTime.equals("Evening")){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            Long lastapptime = preferences.getLong("lastAppUsedTime", 0);
           // if (System.currentTimeMillis() - lastapptime > 1) {//86400000
                //Intent to invoke app when click on notification.
                //In this sample, we want to start/launch this sample app when user clicks on notification
                Intent intentToRepeat = new Intent(context, UnityPlayerActivity.class);
                //set flag to restart/relaunch the app
                intentToRepeat.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                //Pending intent to handle launch of Activity in intent above

                PendingIntent pendingIntent =
                        PendingIntent.getActivity(context, NotificationHelper.ALARM_TYPE_RTC, intentToRepeat, PendingIntent.FLAG_UPDATE_CURRENT);

                //Build notification
                Notification repeatedNotification = buildLocalNotification(context, pendingIntent,notificationTime).build();

                //Send local notification
                NotificationHelper.getNotificationManager(context).notify(NotificationHelper.ALARM_TYPE_RTC, repeatedNotification);
            //}
        }

    }

    public NotificationCompat.Builder buildLocalNotification(Context context, PendingIntent pendingIntent,String notificationTime) {

        RemoteViews contentView = new RemoteViews(context.getPackageName(),R.layout.notificationdesing);
            if(notificationTime.equals("Morning")){
                Random rand=new Random();
                int number=rand.nextInt(2);
                if(number==0){
                    contentView.setImageViewResource(R.id.notification_image, R.drawable.pinky_classroom_noplay_notification);
                }
                else if(number==1){
                    contentView.setImageViewResource(R.id.notification_image, R.drawable.pinkymother_classroom_noplay_notification);
                }
                contentView.setImageViewResource(R.id.notification_background, R.drawable.classroom_background_noplay_notification);
                contentView.setTextViewText(R.id.title, "Yaad rakhein Taleemabad App mein 7 minute classroom kay aik ghantay kay baraber hein");
                contentView.setTextColor(R.id.title, Color.BLACK);
            }
            else if(notificationTime.equals("Evening")){
                contentView.setImageViewResource(R.id.notification_background, R.drawable.night_background_noplay_notification);
                contentView.setImageViewResource(R.id.notification_image, R.drawable.pinky_background_noplay_notification);
                contentView.setTextViewText(R.id.title, "Apnay bachon ko yaad say Taleemabad kay zariye sabaq parhayein");
            }

        NotificationCompat.Builder builder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(context,"DailyProgressNotification")
                        .setSmallIcon(R.drawable.notificationicon)
                        .setContentIntent(pendingIntent)
                        .setContent(contentView)
                        .setAutoCancel(true);
        return builder;
    }
}
