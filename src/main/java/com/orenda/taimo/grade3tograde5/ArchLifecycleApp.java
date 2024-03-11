package com.orenda.taimo.grade3tograde5;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.multidex.MultiDexApplication;
import android.util.Log;

//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.messaging.FirebaseMessaging;
//import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public class ArchLifecycleApp extends MultiDexApplication implements LifecycleObserver {
    long fgtime;
    long bgtime;
//    FirebaseAuth mAuth;
//    FirebaseUser firebaseUser = null;
    long firebaseApptotaltime;
    SharedPreferences preferences;
//    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    //SettingsContentObserver mSettingsContentObserver;
    @Override
    public void onCreate(){
        super.onCreate();


//        mAuth = FirebaseAuth.getInstance();
//        firebaseUser = mAuth.getCurrentUser();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
    }


    /**
     * Get total app time from firebase on OnCreate
     * if AppTotalTime not exist  on firebase
     * set firebaseApptotaltime=1 to differentitate from 0(fail to get data from firebase)
     * */
    protected void getAppTotalTimeFirebase() {
//        if (firebaseUser != null) {
//            String useruid = firebaseUser.getUid();
//
//            final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users/" + useruid + "/AppTotalTime");
//            ValueEventListener valueEventListener = new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    if (dataSnapshot.exists()) {
//                        firebaseApptotaltime = (long) dataSnapshot.getValue();
//                    } else {
//                        firebaseApptotaltime = 1;
//                    }
//                    sendAppTimeToFirebase();
//                }
//
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            };
//            mDatabase.addListenerForSingleValueEvent(valueEventListener);
//        }
    }

//    public void sendAppTimeToFirebase() {
//        int offlinetime = preferences.getInt("offlineTotalApptimeStored", 0);
//        long totalApptime = firebaseApptotaltime + offlinetime;
//        if(totalApptime>=12000){
//            FirebaseMessaging.getInstance().subscribeToTopic("TaleemabadAndroidHighUsersFirebaseNotification");
//        }
//        String useruid = firebaseUser.getUid();
//        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users/" + useruid);
//        mDatabase.child("AppTotalTime").setValue(totalApptime);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putInt("offlineTotalApptimeStored", 0);
//        editor.apply();
//    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void onAppBackgrounded() {
        //getApplicationContext().getContentResolver().unregisterContentObserver(mSettingsContentObserver);
        bgtime=System.currentTimeMillis()/1000;
        long seconds=bgtime-fgtime;
        if(seconds>900){
            userFifteenMinsSessionSend();
        }
        int offlinetime=preferences.getInt("offlineTotalApptimeStored",0);
        long totalApptime= seconds+offlinetime;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong("lastAppUsedTime",System.currentTimeMillis());
        editor.putInt("offlineTotalApptimeStored",(int) totalApptime);
        editor.apply();

        //App in background
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void onAppForegrounded() {
        //mSettingsContentObserver = new SettingsContentObserver(this,new Handler());
        //getApplicationContext().getContentResolver().registerContentObserver(android.provider.Settings.System.CONTENT_URI, true, mSettingsContentObserver );
        // App in foreground
        getAppTotalTimeFirebase();
        fgtime=System.currentTimeMillis()/1000;
        Log.d("testinglasttime","last time"+ preferences.getLong("lastAppUsedTime", 0));
    }

    void userFifteenMinsSessionSend(){
//        String useruid = SignupActivity.userUid;
//        if(useruid!=null) {
//            final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users/" + useruid);
//            String key = mDatabase.push().getKey();
//            mDatabase.child("FifteenMinsSessions").push().setValue(key);
//            mDatabase.child("FifteenMinLatest").setValue(key);
//        }
    }

/*public class SettingsContentObserver extends ContentObserver {
    int previousVolume;
    Context context;
    public SettingsContentObserver(Context c, Handler handler) {
        super(handler);
        context=c;
        AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        previousVolume = audio.getStreamVolume(AudioManager.STREAM_MUSIC);
    }

    @Override
    public boolean deliverSelfNotifications() {
        return super.deliverSelfNotifications();
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int currentVolume = audio.getStreamVolume(AudioManager.STREAM_MUSIC);

        int delta=previousVolume-currentVolume;

        if(delta>0)
        {
            Log.d("musicLevel","Decreased"+previousVolume+" : "+currentVolume);
            previousVolume=currentVolume;
        }
        else if(delta<0)
        {
            Log.d("musicLevel","Increased"+previousVolume+" : "+currentVolume);
            previousVolume=currentVolume;
        }
    }
}*/
}