package com.orenda.taimo.grade3tograde5;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import firebase.analytics.AppAnalytics;
import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import android.preference.PreferenceManager;

public class DownloadForegroundService extends Service {

    public static final String CHANNEL_ID = "DownloadForegroundService";
    public static final String CHANNEL_ID2 = "DownloadForegroundService2";
    public static final String TAG = "DownloadForegroundService";
    public static final int PROGRESS_MAX = 100;
    private NotificationManager notificationManager;
    private NotificationCompat.Builder builder;
    private PendingIntent pendingIntent;
    private String fileName;

    @Override
    public void onCreate () {
        super.onCreate();
        Log.wtf(TAG, "onCreate");

        createNotificationChannel();

        Intent notificationIntent = new Intent(getApplicationContext(), SignupActivity.class);
        pendingIntent = PendingIntent.getActivity(getApplicationContext(),
                0, notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setContentTitle("Downloading...!")
                .setSmallIcon(R.drawable.app_icon)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        startForeground(101, notification);
    }

    @Override
    public void onDestroy () {
        super.onDestroy();
        Log.wtf(TAG, "onDestroy");
    }

    @Nullable
    @Override
    public IBinder onBind (Intent intent) {
        Log.wtf(TAG, "onBind");
        return null;
    }

    @Override
    public int onStartCommand (Intent intent, int flags, int startId) {
        Log.wtf(TAG, "service started");

        String fileURL = intent.getStringExtra("fileURL");

        fileName = intent.getStringExtra("fileName");

        buildNotification(fileName);

//        Handler delayHandler = new Handler();
//        Runnable runner = new Runnable() {
//            @Override
//            public void run () {
//                new FileDownloader().execute(fileURL);
//            }
//        };
//        delayHandler.post(runner);

//        new Thread( new Runnable() { @Override public void run() {
//            new FileDownloader().execute(fileURL);
//        } } ).start();

        new FileDownloader().execute(fileURL);

        return START_NOT_STICKY;
    }

    private void createNotificationChannel () {
        Log.wtf(TAG, "createNotificationChannel");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Download Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(serviceChannel);
        }
        else {
            notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        }
    }

    /**
     * This method makes a notification and adds the videoName passed through argument to the notification.
     *
     * @param videoName is used to pass the String video name to the method
     */
    private void buildNotification (String videoName) {
        Log.wtf(TAG, "buildNotification");
        builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
        builder.setContentTitle("Video is downloading...")
                .setSmallIcon(R.drawable.app_icon)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setOnlyAlertOnce(true)
                //.setNotificationSilent()
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        int PROGRESS_CURRENT = 0;
        builder.setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false);
        notificationManager.notify(101, builder.build());
    }

    /**
     * This method updates the download progress in the notification.
     *
     * @param progress is used to pass the download progress
     */
    private void updateNotificationProgress (int progress, String fileName) {
        Log.wtf(TAG, "updateNotificationProgress: " + progress);
        builder.setContentTitle( "Downloading progress: " + progress + "%")
                .setProgress(PROGRESS_MAX, progress, false);
        notificationManager.notify(101, builder.build());
    }

    /**
     * This method updates the notification when download is complete, and prompts user that the download is finished.
     *
     * @param videoName is used to pass the download progress
     */
    private void notificationFinalStage (String videoName, boolean successful) {
        Log.wtf(TAG, "notificationFinalStage: " + videoName + " " + successful);
        if (successful) {
            Log.wtf(TAG, "successfully downloaded");
            builder.setContentTitle(( "Downloading Complete"))
                    .setProgress(0, 0, false)
            ;
        }
        else {
            Log.wtf(TAG, "not downloaded");
            builder.setContentTitle("Downloading Failed " + videoName + " download.")
                    .setProgress(0, 0, false)
            ;
        }
        notificationManager.notify(102, builder.build());

        DownloadForegroundService.this.stopSelf();
    }

    private class FileDownloader extends AsyncTask<String, Integer, Boolean> {

        long target = 0;
        int sentProgress = 0;

        @Override
        protected Boolean doInBackground (String... strings) {
            Log.wtf(TAG, "doInBackground");

            OkHttpClient client = new OkHttpClient();
            String url = strings[0];
            Call call = client.newCall(new Request.Builder().url(url).get().build());

            try {
                Response response = call.execute();
                if (response.code() == 200 || response.code() == 201) {

                    Headers responseHeaders = response.headers();
                    for (int i = 0; i < responseHeaders.size(); i++) {
                        Log.d("LOG_TAG", responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }

                    InputStream inputStream = null;
                    try {
                        inputStream = response.body().byteStream();

                        byte[] buff = new byte[1024 * 4];
                        long downloaded = 0;
                        target = response.body().contentLength();
                        File mediaFile = new File(getFilesDir(), fileName + ".mp4");
                        OutputStream output = new FileOutputStream(mediaFile);

                        publishProgress(0);
                        while (true) {
                            int readed = inputStream.read(buff);

                            if (readed == -1) {
                                break;
                            }
                            output.write(buff, 0, readed);
                            //write buff
                            downloaded += readed;

                            double prog = ((double) downloaded) / ((double) target);
                            int percent = (int) (prog * 100.0);

                            if (percent > sentProgress) {
                                sentProgress = percent;
                                publishProgress(percent);
                            }

                            if (isCancelled()) {
                                return false;
                            }
                        }

                        output.flush();
                        output.close();

                        return downloaded == target;
                    } catch (IOException ignore) {
                        return false;
                    } finally {
                        if (inputStream != null) {
                            inputStream.close();
                        }
                    }
                }
                else {
                    return false;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onProgressUpdate (Integer... values) {
            super.onProgressUpdate(values);
            Log.wtf(TAG, "onProgressUpdate: " + values[0]);

            updateNotificationProgress(values[0], fileName);
        }

        @Override
        protected void onPostExecute (Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            Log.wtf(TAG, "onPostExecute: " + aBoolean);
            notificationFinalStage(fileName, aBoolean.booleanValue());
            sendMessageToActivity(fileName, aBoolean.booleanValue());
            if (aBoolean.booleanValue()) {
                setVideoDownloadSharedPref();
                setVideoDownloadEndAnalytics(target);
            }
            else {
                setVideoDownloadErrorAnalytics("Download failed.");
            }
        }
    }

    private void sendMessageToActivity(String fName, boolean downloaded) {
        Intent intent = new Intent(fName);
        intent.putExtra("status", downloaded);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    public void setVideoDownloadSharedPref(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putBoolean(fileName+"Video", true);
        editor.apply();
    }

    private void setVideoDownloadEndAnalytics(long videoSize){
        AppAnalytics appAnalytics=new AppAnalytics(getApplicationContext());
        appAnalytics.VideoDownloadEnd(fileName,-1,true, videoSize, true);
    }
    //function to set video download error analytic
    //called when error comes while downloading
    private void setVideoDownloadErrorAnalytics(String error){
        AppAnalytics appAnalytics=new AppAnalytics(getApplicationContext());
        appAnalytics.VideoDownloadError(fileName,-1,error);
    }
}