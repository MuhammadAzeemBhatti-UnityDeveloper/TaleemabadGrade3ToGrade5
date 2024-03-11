package com.orenda.taimo.grade3tograde5;

//import com.amazonaws.mobile.client.AWSMobileClient;
//import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
//import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
//import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
//import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
//import com.amazonaws.services.s3.AmazonS3Client;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
//import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
//import com.google.firebase.functions.FirebaseFunctions;
//import com.google.firebase.functions.HttpsCallableResult;
//import com.google.firebase.inappmessaging.FirebaseInAppMessaging;
//import com.google.firebase.installations.FirebaseInstallations;
//import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
//import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.unity3d.player.*;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.Calendar;
import java.util.Date;

import firebase.analytics.AppAnalytics;
import firebase.analytics.AppTimeAnalytics;
import firebase.analytics.GameAnalytics;
import firebase.analytics.MenuAnalytics;
import firebase.classes.CodFullForm;
import firebase.classes.CodHalfForm;
import firebase.classes.GamesData;

import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class UnityPlayerActivity extends Activity{
    protected UnityPlayer mUnityPlayer; // don't change the name of this variable; referenced from native code
    public static long firebaseApptotaltime;
//    FirebaseAuth mAuth;
//    FirebaseUser firebaseUser = null;
//    FirebaseFunctions mFunctions;

    // Setup activity layout
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.RGBX_8888); // <--- This makes xperia play happy
//        mAuth = FirebaseAuth.getInstance();
//        firebaseUser = mAuth.getCurrentUser();
//        Log.d("dynamicLink","UID: "+firebaseUser.getUid());
        mUnityPlayer = new UnityPlayer(this);
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
        //     WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(mUnityPlayer);
        mUnityPlayer.requestFocus();
        //AWSMobileClient.getInstance().initialize(getApplicationContext()).execute();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if(getIntent()!=null && getIntent().hasExtra("NotificationId")){
            new AppAnalytics(getApplicationContext()).CustomNotificationOpen(getIntent().getIntExtra("NotificationId",0),
                    getIntent().getStringExtra("NotificationTime"),getIntent().getStringExtra("NotificationType"));
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.cancel(getIntent().getIntExtra("NotificationId",0)); //closes notification
        }
        //getFirebaseInstallationId();

        getFirebaseDynamicLink();
    }
    void getFirebaseInstallationId(){
//        FirebaseInstallations.getInstance().getId()
//                .addOnCompleteListener(new OnCompleteListener<String>() {
//                    @Override
//                    public void onComplete(@NonNull Task<String> task) {
//                        if (task.isSuccessful()) {
//                            Log.d("Installations", "Installation ID: " + task.getResult());
//                        } else {
//                            Log.e("Installations", "Unable to get Installation ID");
//                        }
//                    }
//                });
    }
    void getFirebaseDynamicLink(){
//        FirebaseDynamicLinks.getInstance()
//                .getDynamicLink(getIntent())
//                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
//                    @Override
//                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
//                        // Get deep link from result (may be null if no link is found)
//                        Uri deepLink = null;
//                        if (pendingDynamicLinkData != null) {
//                            deepLink = pendingDynamicLinkData.getLink();
//                            String dynamicLinkType = deepLink != null ? deepLink.getQueryParameter("dynamicLinkType") : null;
//                            switch (dynamicLinkType){
//                                case "Game":
//                                    String game = null;
//                                    game= deepLink != null ? deepLink.getQueryParameter("game") : null;
//                                    UnityPlayer.UnitySendMessage("Main Camera","PlayDeeplinkGame",game);
//                                    break;
//                                case "OrderForm":
//                                    String orderform;
//                                    orderform = deepLink != null ? deepLink.getQueryParameter("orderform") : null;
//                                    UnityPlayer.UnitySendMessage("Main Camera", "DeeplinkOrderForm", orderform);
//                                    break;
//                                case "PaymentMethod":
//                                    String bundleToBuy;
//                                    bundleToBuy = deepLink != null ? deepLink.getQueryParameter("bundle") : null;
//                                    UnityPlayer.UnitySendMessage("Main Camera", "DeeplinkPaymentMethodForm", bundleToBuy);
//                                    break;
//                                case "PaymentMethodCod":
//                                    String bundle,bundleDuration;
//                                    bundle = deepLink != null ? deepLink.getQueryParameter("bundle") : null;
//                                    bundleDuration = deepLink != null ? deepLink.getQueryParameter("bundleDuration") : null;
//                                    UnityPlayer.UnitySendMessage("Main Camera", "DeeplinkPaymentMethodCODForm", bundle+","+bundleDuration);
//                                    break;
//                                case "VideoLink":
//                                    Intent intent = new Intent(getApplicationContext(),VideoActivity.class);
//                                    String grade, subject, videoname, language;
//                                    grade = deepLink != null ? deepLink.getQueryParameter("Grade") : null;
//                                    subject = deepLink != null ? deepLink.getQueryParameter("Subject") : null;
//                                    videoname = deepLink != null ? deepLink.getQueryParameter("VideoTitle") : null;
//                                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                                    intent.putExtra("ContentUnlocked",preferences.getInt("AllContentUnlocked", 0));
//                                    intent.putExtra("PurchaseStatus",preferences.getInt("app_purchase", 0));
//                                    intent.putExtra("GradeSelected", grade);
//                                    intent.putExtra("SubjectSelected", subject);
//                                    intent.putExtra("Alpha", videoname);
//                                    startActivity(intent);
//                                    break;
//                                case "VideoShare":
//                                    SharedPreferences packagePrefs = getApplicationContext().getSharedPreferences(getApplicationContext().getPackageName() + ".v2.playerprefs", Context.MODE_PRIVATE);
//                                    Intent videoShareIntent = new Intent(getApplicationContext(),VideoActivity.class);
//                                    grade = deepLink != null ? deepLink.getQueryParameter("Grade") : null;
//                                    subject = deepLink != null ? deepLink.getQueryParameter("Subject") : null;
//                                    videoname = deepLink != null ? deepLink.getQueryParameter("VideoName") : null;
//                                    Log.d("ShareVideo","In unity checking deeplink-- Grade: "+grade+"-- Subject: "+subject+"-- videoName= "+videoname);
//                                    videoShareIntent.putExtra("GradeSelected", grade);
//                                    videoShareIntent.putExtra("SubjectSelected",subject);
//                                    videoShareIntent.putExtra("Alpha", videoname);
//                                    videoShareIntent.putExtra("ContentUnlocked",packagePrefs.getInt("AllContentUnlocked",0));
//                                    int purchaseStatus=0;
//                                    if (packagePrefs.getInt("app_purchase", 0) == 1 || packagePrefs.getInt("SubscribedBundlelifetime", 0) == 1 || packagePrefs.getInt("SubscribedBundlekto5", 0) == 1 || packagePrefs.getInt("SubscribedBundlekto2", 0) == 1 || packagePrefs.getInt("SubscribedBundle3to5", 0) == 1)
//                                    {
//                                        purchaseStatus=1;
//                                    }
//                                    videoShareIntent.putExtra("PurchaseStatus", purchaseStatus);
//
//                                    int DailyTrialEnabled = packagePrefs.getInt("DailyTrialEnabled",0);
//                                    int DailyTrialConsumed = packagePrefs.getInt("DailyTrialConsumed",0);
//                                    int FourteenDayTrialGiven = packagePrefs.getInt("FourteenDayTrialGiven",0);
//                                    if(DailyTrialEnabled == 1 && DailyTrialConsumed == 0){
//                                        int freeTrialSecondsLeft = 420 - packagePrefs.getInt("SevenMinutesTrialSecondsConsumed",0);
//                                        videoShareIntent.putExtra("freeTrialSecondsLeft",freeTrialSecondsLeft);
//                                        videoShareIntent.putExtra("DailyTrialEnabled",DailyTrialEnabled);
//                                    }
//                                    if(packagePrefs.getInt("AllContentUnlocked",0)==1 || purchaseStatus==1 || (DailyTrialEnabled == 1 && DailyTrialConsumed == 0))
//                                    {
//                                        startActivity(videoShareIntent);
//                                    }
//                                    new AppAnalytics(getApplicationContext()).VideoReceived(grade,subject,videoname,grade+subject+videoname,firebaseUser.getUid());
//                                    break;
//                            }
//                        }
//                    }
//                })
//                .addOnFailureListener(this, new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.d("deeplink", "getDynamicLink:onFailure", e);
//                    }
//                });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // To support deep linking, we need to make sure that the client can get access to
        // the last sent intent. The clients access this through a JNI api that allows them
        // to get the intent set on launch. To update that after launch we have to manually
        // replace the intent with the one caught here.
        setIntent(intent);
    }


    // Quit Unity
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Date dateTimeAppStart=SignupActivity.appStartTime;
        Date dateTimeNow=Calendar.getInstance().getTime();
        if(dateTimeAppStart!=null && dateTimeNow!=null){
            long diff=dateTimeNow.getTime()-dateTimeAppStart.getTime();
            long appTimeInSeconds=(diff/1000);
            AppTimeAnalytics appTimeAnalytics=new AppTimeAnalytics(appTimeInSeconds,getApplicationContext());
            appTimeAnalytics.setAppTimeAnalytics();
        }
        mUnityPlayer.quit();
    }

    // Pause Unity
    @Override
    protected void onPause() {
        super.onPause();
        mUnityPlayer.pause();
        Log.d("ondestroyunity","pause called");
    }

    // Resume Unity
    @Override
    protected void onResume() {
        super.onResume();
        mUnityPlayer.resume();

        //checkIfForceSignup();
        Log.d("ondestroyunity","resume called");
        getFirebaseDynamicLink();
    }

    // Low Memory Unity
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mUnityPlayer.lowMemory();
    }

    // Trim Memory Unity
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_RUNNING_CRITICAL) {
            mUnityPlayer.lowMemory();
        }
    }

    // This ensures the layout will be correct.
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mUnityPlayer.configurationChanged(newConfig);
    }

    // Notify Unity of the focus change.
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        mUnityPlayer.windowFocusChanged(hasFocus);
    }

    // For some reason the multiple keyevent type is not supported by the ndk.
    // Force event injection by overriding dispatchKeyEvent().
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_MULTIPLE)
            return mUnityPlayer.injectEvent(event);
        return super.dispatchKeyEvent(event);
    }

    // Pass any events not handled by (unfocused) views straight to UnityPlayer
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return mUnityPlayer.injectEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mUnityPlayer.injectEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mUnityPlayer.injectEvent(event);
    }

    /*API12*/
    public boolean onGenericMotionEvent(MotionEvent event) {
        return mUnityPlayer.injectEvent(event);
    }
    public void checkIfForceSignup(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Long AnonymousD0Signup = preferences.getLong("AnonymousD0Signup", 0);
        if (System.currentTimeMillis() > AnonymousD0Signup + 86400000 ) {
            Intent intent = new Intent(UnityPlayerActivity.this, SignupActivity.class);;
            startActivity(intent);
            //finish();
        }

    }
    public void VideoActivity(String Grade, String Subject, String Name, boolean IsFree, int ContentUnlocked, int PurchaseStatus){
        new AppAnalytics(getApplicationContext()).VideoButtonPressed(Name, Grade, Subject);
        Intent intent = new Intent(UnityPlayerActivity.this, VideoActivity.class);
        intent.putExtra("GradeSelected", Grade);
        intent.putExtra("SubjectSelected",Subject);
        intent.putExtra("Alpha", Name);
        intent.putExtra("IsFree", IsFree);
        intent.putExtra("ContentUnlocked",ContentUnlocked);
        intent.putExtra("PurchaseStatus", PurchaseStatus);

        SharedPreferences packagePrefs = getApplicationContext().getSharedPreferences(getApplicationContext().getPackageName() + ".v2.playerprefs", Context.MODE_PRIVATE);
        int DailyTrialEnabled = packagePrefs.getInt("DailyTrialEnabled",0);
        int DailyTrialConsumed = packagePrefs.getInt("DailyTrialConsumed",0);
        int FourteenDayTrialGiven = packagePrefs.getInt("FourteenDayTrialGiven",0);
        if(DailyTrialEnabled == 1 && DailyTrialConsumed == 0){
            int freeTrialSecondsLeft = 420 - packagePrefs.getInt("SevenMinutesTrialSecondsConsumed",0);
            intent.putExtra("freeTrialSecondsLeft",freeTrialSecondsLeft);
            intent.putExtra("DailyTrialEnabled",DailyTrialEnabled);
        }
        startActivity(intent);
    }

    public void SocraticActivity(String Grade, String Subject, String Name){
        Intent intent = new Intent(UnityPlayerActivity.this, SocraticActivity.class);
        Log.wtf("-this","VideoActivityClicked"+Grade+" :: "+Subject+" :: "+Name);
        //  intent.putExtra("Alpha", Name);
        String TopicUID = "";
        switch (Subject){
            case "English":
                TopicUID = "Grade2EnglishSocraticAdjectivesAdjectives";
                break;
            case "Math":
                TopicUID = "Grade2MathsSocraticDivisionIntroductionToDivision";
                Subject = "Maths";
                break;
            case "Urdu":
                TopicUID = "Grade2UrduSocraticIsmIsm";
                break;
        }
        intent.putExtra("GradeSelected", Grade);
        intent.putExtra("SubjectSelected",Subject);
        intent.putExtra("Alpha", TopicUID);
        intent.putExtra("source","socratic");
        startActivity(intent);
    }

    public void Test(String Grade, String Subject, String Name, String Uid){
        Intent intent = new Intent(UnityPlayerActivity.this, SimpleTestActivity.class);
        String TopicUID = "";
        TopicUID= Uid;
        if(Subject.equals("Math")){
            Subject = "Maths";
        }
        Log.wtf("-this","TestActivityClicked"+Grade+" :: "+Subject+" :: "+Name);
        intent.putExtra("GradeSelected", Grade);
        intent.putExtra("SubjectSelected",Subject);
        intent.putExtra("Alpha", TopicUID);
        intent.putExtra("source","nonsocratic");
        startActivity(intent);
    }

    /**
     * function to save user trial value in preference
     * if trialStaus is true set app unlock(trial) pref
     * else set app lock pref
     * caled from unity
     * */
    public void setTrialPref(boolean trialStatus){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();

        if(trialStatus){
            editor.putInt("FourteenDayTrialGiven",1);
            editor.putInt("AllContentUnlocked", 1);
        }else{
            editor.putInt("FourteenDayTrialGiven",0);
            editor.putInt("AllContentUnlocked", 0);

        }

        editor.apply();
    }

    /**
     * function to save user purchase value in preference
     * if purchaseStatus is true set app unlock(purchase) pref
     * else set app lock pref
     * caled from unity
     * */
    public void setPurchasePref(boolean purchaseStatus){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        if(purchaseStatus){
            editor.putInt("app_purchase",1);
            editor.putInt("AllContentUnlocked", 1);
        }else{
            editor.putInt("app_purchase",0);
            editor.putInt("AllContentUnlocked", 0);
        }
        editor.apply();
    }


    public void alphabet(String alpha,int purchaseStatus, String gradeSelected, String subjectSelected) {
        Intent intent = new Intent(UnityPlayerActivity.this, VideoActivity.class);
        intent.putExtra("Alpha", alpha);
        intent.putExtra("PurchaseStatus",purchaseStatus);
        intent.putExtra("gradeSelected",gradeSelected);
        intent.putExtra("subjectSelected",subjectSelected);
        Log.d("alphaAlpha", alpha);
        startActivity(intent);
    }

    public void exitApp(){
        finishAffinity();
    }
//    public void googlePayment() {
//        Intent intent = new Intent(UnityPlayerActivity.this, paymentActivity.class);
//        startActivity(intent);
//    }


    public void otherPayment() {
       /* Intent intent = new Intent(UnityPlayerActivity.this, webview.class);
        startActivity(intent);*/
    }

    public void phoneCallPayment() {
        MenuAnalytics menuAnalytics=new MenuAnalytics("", getApplicationContext());
        menuAnalytics.setHotlinePurchaseMenuAnalytics();
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:03329792473"));
        startActivity(intent);
    }

    //    public void couponPayment(String coupon, String mode) {
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users/" + currentUser.getUid());
//        purchase purchaseObj = new purchase("true", mode, coupon);
//        mDatabase.child("purchase").setValue(purchaseObj);
//        UnityPlayer.UnitySendMessage("selectionObject", "unlockAllGames","aa");
//    }
    public void jazzCashPayment(String jazzNumber){
        Log.d("jazzcash",""+jazzNumber);

    }
   /* public void redownloadfile() {
        File file = new File(getApplicationContext().getExternalFilesDir(null), "sceneassesbundle");
        if (file.exists()) {
            boolean filedel = file.delete();
            if (filedel) {
                Intent intent = new Intent(UnityPlayerActivity.this, downloadGamesFile.class);
                intent.putExtra("redownload", true);
                startActivity(intent);
            }
        }else{
            Intent intent = new Intent(UnityPlayerActivity.this, downloadGamesFile.class);
            intent.putExtra("redownload", true);
            startActivity(intent);
        }
    }*/

    /*public void downloadTrialfile()
    {
        Log.d("trialfiledl","download trial file");
        Intent intent = new Intent(UnityPlayerActivity.this, downloadGamesFile.class);
        intent.putExtra("redownload", true); //putting redownload to not set purchase data in downloadGamesFile
        intent.putExtra("trial",true);
        startActivity(intent);
    }*/

    public void whatsappunlock() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Pakistani bachon ki pasandeeda taleemi app. Download Taleemabad Learning App now: https://play.google.com/store/apps/details?id=com.orenda.taimo.myapplication");
        sendIntent.setType("text/plain");
        sendIntent.setPackage("com.whatsapp");
        startActivity(sendIntent);
    }

    public void PlayStoreRating(){
        try {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=com.orenda.taimo.myapplication")));
        } catch (android.content.ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=com.orenda.taimo.myapplication")));
        }
    }

    public void SubmitBadReview(String review){
//        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("InAppReviews/"+firebaseUser.getUid());
//        UserReview userReview = new UserReview(review);
//        mDatabase.push().setValue(userReview);
    }

    public void happyFaceRating() {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=com.orenda.taimo.myapplication")));
        } catch (android.content.ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=com.orenda.taimo.myapplication")));
        }
    }

//    public void getRemoteConfig(){
//        final FirebaseRemoteConfig firebaseRemoteConfig= FirebaseRemoteConfig.getInstance();
//        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
//                .setMinimumFetchIntervalInSeconds(3600)
//                .build();
//        firebaseRemoteConfig.setConfigSettingsAsync(configSettings);
//        // [START set_default_values]
//        firebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults);
//        // [END set_default_values]
//        Log.d("remoteconfig","value: "+firebaseRemoteConfig.getString("trial_value"));
//
//
//        firebaseRemoteConfig.fetch(10).addOnSuccessListener(this, new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                firebaseRemoteConfig.activate();
//                Log.d("remoteconfig","value next:"+firebaseRemoteConfig.getString("trial_value"));
//                //if value is 1 give trial
//                if(firebaseRemoteConfig.getString("trial_value").equals("1")){
//                    UnityPlayer.UnitySendMessage("Ferris_Wheel","remoteconfigresult","1");
//                }
//                else{
//                    UnityPlayer.UnitySendMessage("Ferris_Wheel","remoteconfigresult","0");
//                }
//            }
//        });
//    }
//
//    //give trial if not given already
//    public void setTrial(){
//        FirebaseAuth mAuth;
//        mAuth = FirebaseAuth.getInstance();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        final String userUid = currentUser.getUid();
//        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("trial/"+userUid);
//        ValueEventListener valueEventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    UnityPlayer.UnitySendMessage("TrialTickerObject","noTrial","1");
//                }
//                else{
//                    //Give trial
//                    trial trailObj=new trial("true");
//                    FirebaseDatabase.getInstance().getReference("trial/").child(userUid).setValue(trailObj);
//                    getDailyTrialRemoteConfig();
//                    //getTrialDaysRemoteConfig();
//
//                }
//            }
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            };
//
//        mDatabase.addListenerForSingleValueEvent(valueEventListener);
//
//    }

//    public void getDailyTrialRemoteConfig(){
//        Log.d("dailyTrialEnabled","called: ");
//        final FirebaseRemoteConfig firebaseRemoteConfig= FirebaseRemoteConfig.getInstance();
//        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
//                .setMinimumFetchIntervalInSeconds(60)
//                .build();
//        firebaseRemoteConfig.setConfigSettingsAsync(configSettings);
//        firebaseRemoteConfig.setDefaultsAsync(R.xml.daily_trial_remote_config);
//        firebaseRemoteConfig.fetchAndActivate()
//                .addOnCompleteListener(this, new OnCompleteListener<Boolean>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Boolean> task) {
//                        String dailyTrialEnabled= "yes"; //default value yes
//                        if (task.isSuccessful()) {
//                            boolean updated = task.getResult();
//                            dailyTrialEnabled =firebaseRemoteConfig.getString("daily_trial_enabled");
//                            Log.d("dailyTrialEnabled","success val: "+dailyTrialEnabled);
//                            UnityPlayer.UnitySendMessage("TrialTickerObject","giveTrial",dailyTrialEnabled);
//                            setDailyTrialEnabledAnalytics(dailyTrialEnabled);
//                        } else {
//                            Log.d("dailyTrialEnabled","fail val: "+dailyTrialEnabled);
//                            UnityPlayer.UnitySendMessage("TrialTickerObject","giveTrial",dailyTrialEnabled);
//                            setDailyTrialEnabledAnalytics(dailyTrialEnabled);
//                        }
//
//                    }
//                });
//    }
//
//    public void getTrialDaysRemoteConfig(){
//        final FirebaseRemoteConfig firebaseRemoteConfig= FirebaseRemoteConfig.getInstance();
//        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
//                .setMinimumFetchIntervalInSeconds(60)
//                .build();
//        firebaseRemoteConfig.setConfigSettingsAsync(configSettings);
//        firebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults);
//        firebaseRemoteConfig.fetchAndActivate()
//                .addOnCompleteListener(this, new OnCompleteListener<Boolean>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Boolean> task) {
//                        String days= "7"; //default value
//                        if (task.isSuccessful()) {
//
//                            boolean updated = task.getResult();
//                            days =firebaseRemoteConfig.getString("trial_user_type");
//                            UnityPlayer.UnitySendMessage("TrialTickerObject","giveTrial",days);
//                            setTrialDaysUserProperty(days);
//                        } else {
//                            UnityPlayer.UnitySendMessage("TrialTickerObject","giveTrial",days);
//                            setTrialDaysUserProperty(days);
//                        }
//
//                    }
//                });
//    }

    void setTrialDaysUserProperty(String trialDays){
        new AppAnalytics(getApplicationContext()).setTrialDayUserProperty(trialDays);
    }

    void setDailyTrialEnabledAnalytics(String status){
        new AppAnalytics(getApplicationContext()).DailyTrialEnabled(status);
    }

    //set onDataChangeListener for SubjectJsons
//    public void CheckSubjectJson(int NurseryEnglishJson, int NurseryUrduJson, int NurseryMathJson, int NurseryTarbiyatJson, int Grade1EnglishJson, int Grade1UrduJson, int Grade1MathJson, int Grade1TarbiyatJson, int Grade2EnglishJson, int Grade2UrduJson, int Grade2MathJson, int NGrade2GeneralKnowledgeJson)
//    {
//        final int[] SubjectsLastUpdate = new int[]{NurseryEnglishJson, NurseryUrduJson, NurseryMathJson, NurseryTarbiyatJson, Grade1EnglishJson, Grade1UrduJson, Grade1MathJson, Grade1TarbiyatJson, Grade2EnglishJson, Grade2UrduJson, Grade2MathJson, NGrade2GeneralKnowledgeJson};
//        final String[] Subjects = new String[]{"NurseryEnglish", "NurseryUrdu", "NurseryMath", "NurseryTarbiyat", "Grade1English", "Grade1Urdu", "Grade1Math", "Grade1Tarbiyat", "Grade2English", "Grade2Urdu", "Grade2Math", "Grade2GeneralKnowledge"};
//        for (int i = 0; i<Subjects.length ; i++)
//        {
//            final int i2 = i;
//            final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("AutoContentUpdation/" + Subjects[i]);
//            ValueEventListener valueEventListener = new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    if(Integer.valueOf(dataSnapshot.getValue().toString()) != SubjectsLastUpdate[i2])
//                    {
//                        Log.d("aaaa","value " + dataSnapshot.getValue().toString() + "Subject " + Subjects[i2]);
//                        UnityPlayer.UnitySendMessage("ScriptHandler","UpdateJsonForThisSubject",Subjects[i2]+","+dataSnapshot.getValue().toString());
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            };
//            mDatabase.addListenerForSingleValueEvent(valueEventListener);
//        }
//    }

    //    public void couponValidation(String coupon){
//        if(isInternetAvailable()) {
//            final ProgressDialog pd = new ProgressDialog(UnityPlayerActivity.this);
//            pd.setMessage("Please wait!");
//            pd.setCancelable(false);
//            pd.show();
//            //UnityPlayer.UnitySendMessage("ScriptHandler", "SubscriptionCodeReturn","true,kto2,1593724663,1625260663");
//            //setPurchaseData("true","ScratchCard",coupon,"kto2","1593724663","1625260663");
//       // mFunctions = FirebaseFunctions.getInstance();
//        CouponChecker1(coupon).addOnCompleteListener(new OnCompleteListener<String>() {
//            @Override
//            public void onComplete(@NonNull Task<String> task) {
//
//                if (!task.isSuccessful()) {
//                    pd.cancel();
//                    Log.d("CouponError","Error: "+task.getException().getMessage());
//                    ShowDialogBox("Error","Internal Error");
//                }else{
//                    pd.cancel();
//                }
//            }
//        });
//
//        }else{
//            UnityPlayer.UnitySendMessage("ScriptHandler","ShowInternetIssuePopup","");
//        }
//
//    }
//    private Task<String> CouponChecker1(String coupon) {
//        // Create the arguments to the callable function.
//
//        Map<String, Object> data = new HashMap<>();
//        data.put("coupon", coupon);
////        return mFunctions
////                .getHttpsCallable("CouponChecker-1")
////                .call(data)
////                .continueWith(new Continuation<HttpsCallableResult, String>() {
////                    @Override
////                    public String then(@NonNull Task<HttpsCallableResult> task) throws Exception {
////                        //Object result1 = task.getResult().getData();
////                        HashMap<String,Object> hashMap= (HashMap<String, Object>) task.getResult().getData();
////                        //String result = (String) task.getResult().getData();
////
////                        if (hashMap.get("status").toString().equals("true")) {
////                            String bundle = hashMap.get("bundle").toString();
////                            String activationDate = hashMap.get("activationDate").toString();
////                            String expiryDate = hashMap.get("expiryDate").toString();
////                            UnityPlayer.UnitySendMessage("ScriptHandler", "SubscriptionCodeReturn","true,"+bundle+","+activationDate+","+expiryDate);
////                            setPurchaseData("true","Coupon", coupon, bundle,activationDate,expiryDate);
////                            setSaleSuccessfulAnalytics("CashOnDelivery/BankTransfer",activationDate, bundle, coupon);
////                            setCouponValidityAnalytics(true,coupon);
////                            }
////                        else{
////                            setCouponValidityAnalytics(false,coupon);
////                            UnityPlayer.UnitySendMessage("ScriptHandler", "SubscriptionCodeReturn","false,,,");
////                        }
////                        return hashMap.toString();
////                    }
////                });
//    }
    public void setPurchaseData(String status, String mode, String coupon, String bundle ,String activationDate, String expiryDate){
        // purchase purchaseobj = new purchase(status, mode, coupon, bundle, activationDate, expiryDate);
        //purchaseobj.setPurchaseData(purchaseobj);

//        FirebaseFunctions mFunctions;
//        mFunctions = FirebaseFunctions.getInstance();
//        purchaseSetter(mFunctions, status, mode, activationDate, expiryDate, bundle,coupon).addOnCompleteListener(new OnCompleteListener<String>() {
//            @Override
//            public void onComplete(@NonNull Task<String> task) {
//
//                if (!task.isSuccessful()) {
//                    Log.d("googlePurchase","UnSuccessful:"+ task.getException());
//                }else{
//                    Log.d("googlePurchase","Successful");
//                }
//            }
//        });
    }
//    private Task<String> purchaseSetter(FirebaseFunctions mFunctions,String status, String mode, String activationDate, String expiryDate, String bundle, String coupon) {
//        // Create the arguments to the callable function.
//        FirebaseAuth mAuth = FirebaseAuth.getInstance();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        Map<String, Object> data = new HashMap<>();
//        data.put("uid", currentUser.getUid());
//        data.put("status", status);
//        data.put("activationDate", activationDate);
//        data.put("expiryDate", expiryDate);
//        data.put("mode", mode);
//        data.put("bundle", bundle);
//        data.put("coupon", coupon);
//        return mFunctions
//                .getHttpsCallable("purchaseSetterApp")
//                .call(data)
//                .continueWith(new Continuation<HttpsCallableResult, String>() {
//                    @Override
//                    public String then(@NonNull Task<HttpsCallableResult> task) throws Exception {
//                        String result = (String) task.getResult().getData();
//                        return result;
//                    }
//                });
//    }


    void setSaleSuccessfulAnalytics(String salesMethod, String purchaseDate, String bundle, String coupon){
        AppAnalytics appAnalytics = new AppAnalytics(getApplicationContext());
        appAnalytics.setSaleSuccessfulAnalytics(salesMethod,purchaseDate,bundle, coupon);
    }

    void setCouponValidityAnalytics(boolean validityStatus, String coupon){
        new AppAnalytics(getApplicationContext()).CouponValidityAnalytics(validityStatus, coupon);
    }



    public void setGameAnalytic(final String gameName, String analyticType){
        switch (analyticType){
            case "start":
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        GameAnalytics gameAnalytics=new GameAnalytics(gameName,getApplicationContext());
                        gameAnalytics.setStartGameAnalytic();
                    }
                }).start();
                break;
            case "end":
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        GameAnalytics gameAnalytics=new GameAnalytics(gameName,getApplicationContext());
                        gameAnalytics.setEndGameAnalytic();
                    }
                }).start();
                break;
            case "back":
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        GameAnalytics gameAnalytics=new GameAnalytics(gameName,getApplicationContext());
                        gameAnalytics.setGameBackAnalytic();
                    }
                }).start();
                break;
        }
    }

    public void setMenuAnalytics(final String subject, String analyticType){
        switch (analyticType){
            case "subjectselect":
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MenuAnalytics menuAnalytics=new MenuAnalytics(subject, getApplicationContext());
                        menuAnalytics.setSubjectSelectMenuAnalytics();
                    }
                }).start();
                break;
            case "back":
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MenuAnalytics menuAnalytics=new MenuAnalytics(subject, getApplicationContext());
                        menuAnalytics.setMenuBackAnalytics();
                    }
                }).start();
                break;
            case "subscription":
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MenuAnalytics menuAnalytics=new MenuAnalytics(subject, getApplicationContext());
                        menuAnalytics.setSubscriptionMenuAnalytics();
                    }
                }).start();
                break;
            case "SimPaisa":
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MenuAnalytics menuAnalytics=new MenuAnalytics(subject, getApplicationContext());
                        menuAnalytics.setSimPaisaSubscriptionMenuAnaltics();
                    }
                }).start();
                break;
            case "purchase":
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MenuAnalytics menuAnalytics=new MenuAnalytics(subject, getApplicationContext());
                        menuAnalytics.setPurchaseMenuAnalytics();
                    }
                }).start();

                break;
            case "google":
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MenuAnalytics menuAnalytics=new MenuAnalytics("", getApplicationContext());
                        menuAnalytics.setGooglePurchaseMenuAnaltics();
                    }
                }).start();

            case "hotline":
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MenuAnalytics menuAnalytics=new MenuAnalytics("", getApplicationContext());
                        menuAnalytics.setHotlinePurchaseMenuAnalytics();
                    }
                }).start();
            case "cod":
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MenuAnalytics menuAnalytics=new MenuAnalytics("", getApplicationContext());
                        menuAnalytics.setCodPurchaseMenuAnalytics();
                    }
                }).start();
        }
    }

    public void allGameData(String startTime, int stars, String completeTime, String gameName, String status){
        GamesData gamesData=new GamesData(startTime, completeTime, gameName, status, stars,getApplicationContext());
        gamesData.setGameData();
    }
    public void writingGameData(String startTime, int stars, String completeTime, String gameName, String gameType, String status){
        allGameData(startTime,stars, completeTime, gameName, status);
        /*Log.d("writingGameData","start time: "+startTime+" stars: "+stars+" complete time: "+completeTime+" Game Name: "+gameName+" Game Type: "+gameType+" Status: "+status);
        writingGames writingGames=new writingGames(startTime, completeTime, gameName, gameType, status, stars);
        writingGames.setGameData();*/
    }
    public void identificationGameData(String startTime, int stars, String completeTime, String gameName, String gameType, String status){
        allGameData(startTime,stars, completeTime, gameName, status);
        /*Log.d("identificationGameData","start time: "+startTime+" stars: "+stars+" complete time: "+completeTime+" Game Name: "+gameName+" Game Type: "+gameType+" Status: "+status);
        identificationGames identificationGames=new identificationGames(startTime, completeTime, gameName, gameType, status, stars);
        identificationGames.setGameData();*/
    }
    public void vocabularyGameData(String startTime, int stars, String completeTime, String gameName, String gameType, String status){
        allGameData(startTime,stars, completeTime, gameName, status);
        /* Log.d("vocabularyGameData","start time: "+startTime+" stars: "+stars+" complete time: "+completeTime+" Game Name: "+gameName+" Game Type: "+gameType+" Status: "+status);
        vocabularyGames vocabularyGames=new vocabularyGames(startTime, completeTime, gameName, gameType, status, stars);
        vocabularyGames.setGameData();*/
    }

//    public void codRemoteConfig(){
//        final FirebaseRemoteConfig firebaseRemoteConfig= FirebaseRemoteConfig.getInstance();
//        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
//                .setMinimumFetchIntervalInSeconds(3600)
//                .build();
//        firebaseRemoteConfig.setConfigSettingsAsync(configSettings);
//        // [START set_default_values]
//        firebaseRemoteConfig.setDefaultsAsync(R.xml.cod_remote_config_default);
//        // [END set_default_values]
//
//        firebaseRemoteConfig.fetch(10).addOnSuccessListener(this, new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                firebaseRemoteConfig.activate();
//                String codRemoteConfigValue=firebaseRemoteConfig.getString("cod_value");
//                UnityPlayer.UnitySendMessage("selectionObject","codremoteconfigresult",codRemoteConfigValue);
//            }
//        });
//    }

    public void submitHalfCodForm(String phoneNumber, String address){
        CodHalfForm codHalfForm=new CodHalfForm(phoneNumber,address);
        codHalfForm.setHalfCodFormData();
    }

    //    public void submitFullCodForm(String name, String address, String city, String phoneNumber, String formType, String bundle, String bundleDuration, String PurchaseMethod, boolean booksAddOn, String promoCode, boolean WorkSheetAddOn){
//        if(isInternetAvailable()) {
//        	Log.d("CODForm","name: "+name+" -address: "+address+" -city: "+city+" -phoneNumber: "+phoneNumber+ " -formType: "+formType+" -bundle: "+bundle+" -bundleDuration"+bundleDuration+"-PurchaseMethod: "+PurchaseMethod+" -booksAddOn: "+booksAddOn+ "- promoCode: "+promoCode+ " -WorkSheetAddOn"+WorkSheetAddOn);
//            int orderId = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE); // to get unique order id
//            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//            String signupName = preferences.getString("UserName","Taleemabad User");;
//            String signupPhoneNumber = preferences.getString("UserPhoneNumber","NumberNotAvailable");
//            CodFullForm codFullForm=new CodFullForm(name, signupName, address, city, phoneNumber,signupPhoneNumber,formType,bundle,bundleDuration,orderId,PurchaseMethod, booksAddOn, promoCode, WorkSheetAddOn);
//            codFullForm.setFullCodFormData();
//            UnityPlayer.UnitySendMessage("ScriptHandler","CashOnDeliveryFormReturn",Integer.toString(orderId));
//          //  mFunctions = FirebaseFunctions.getInstance();
//            SubmitCODFormData(name, address, city, phoneNumber, formType, bundle, bundleDuration, PurchaseMethod, booksAddOn, promoCode, WorkSheetAddOn).addOnCompleteListener(new OnCompleteListener<String>() {
//                @Override
//                public void onComplete(@NonNull Task<String> task) {
//
//                    if (!task.isSuccessful()) {
//                        Log.d("CODError",": "+task.getException());
//                        //ShowDialogBox("Error","Internal Error: "+task.getException());
//                    }else{
//                    }
//                }
//            });
//            new AppAnalytics(getApplicationContext()).CashOnDeliveryAnalytics(name, address, city, phoneNumber, formType, bundle, bundleDuration, PurchaseMethod, booksAddOn, promoCode, WorkSheetAddOn);
//        }else{
//            UnityPlayer.UnitySendMessage("ScriptHandler","ShowInternetIssuePopup","");
//        }
//
//    }
//    private Task<String> SubmitCODFormData(String name, String address, String city, String phoneNumber, String formType, String bundle, String bundleDuration, String PurchaseMethod, boolean booksAddOn, String promoCode, boolean WorkSheetAddOn) {
//
//        Map<String, Object> data = new HashMap<>();
//        data.put("name", name);
//        data.put("address", address);
//        data.put("city", city);
//        data.put("phoneNumber", phoneNumber);
//        data.put("formType", formType);
//        data.put("bundle", bundle);
//        data.put("bundleDuration", bundleDuration);
//        data.put("PurchaseMethod", PurchaseMethod);
//        data.put("booksAddOn", booksAddOn);
//        data.put("promoCode", promoCode);
//        data.put("WorkSheetAddOn", WorkSheetAddOn);
//
////        return mFunctions
////                .getHttpsCallable("inAppPurchaseHttp")
////                .call(data)
////                .continueWith(new Continuation<HttpsCallableResult, String>() {
////                    @Override
////                    public String then(@NonNull Task<HttpsCallableResult> task) throws Exception {
////
////                        //Object result1 = task.getResult().getData();
////                        //HashMap<String,Object> hashMap= (HashMap<String, Object>) task.getResult().getData();
////                        String result = (String) task.getResult().getData();
////
////                        return result;
////                    }
////                });
//    }
    public void LiveTuitionForm(String name, String phoneNumber,String promoCode){
        if(isInternetAvailable()) {
            int orderId = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE); // to get unique order id
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String signupName = preferences.getString("UserName","Taleemabad User");;
            String signupPhoneNumber = preferences.getString("UserPhoneNumber","NumberNotAvailable");
            CodFullForm codFullForm=new CodFullForm(name, signupName, "", "", phoneNumber,signupPhoneNumber,"LiveTuition","","",orderId,"", false, promoCode, false);
            codFullForm.setFullCodFormData();
            UnityPlayer.UnitySendMessage("ScriptHandler","CashOnDeliveryFormReturn",Integer.toString(orderId));
            //new AppAnalytics(getApplicationContext()).CashOnDeliveryAnalytics(bundle);
        }else{
            UnityPlayer.UnitySendMessage("ScriptHandler","ShowInternetIssuePopup","");
        }
    }


    public void sendUserUid(){
//        FirebaseAuth mAuth;
//        mAuth = FirebaseAuth.getInstance();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
        //      UnityPlayer.UnitySendMessage("Controller","receivedUserUid",currentUser.getUid().toString());
    }

    public void downloadSingleGameFile(String key){
        /*Intent intent=new Intent(UnityPlayerActivity.this,downloadGamesFile.class);
        intent.putExtra("downloadkey",key);
        startActivity(intent);*/
    }

    /*public void comprehensionVideo(String videoId, String subject){
        Intent intent=new  Intent(UnityPlayerActivity.this,ComprehensionActivity.class);
        intent.putExtra("videoId",videoId);
        intent.putExtra("subject",subject);
        startActivity(intent);
    }*/

    public void setCurrentScreenAnalytics(String screenName,String className){
        AppAnalytics appAnalytics=new AppAnalytics(getApplicationContext());
        appAnalytics.setCurrentScreenName(screenName,className,this);
    }

    public void setGameEventStart(String ffirebase_screen_class, String slo_type, String game_grade, String game_subject, String game_design_type, int game_version, int game_total_stars, boolean is_free, int game_questions, String question_type, int question_id_1, int question_id_x, String deep_link){
        AppAnalytics appAnalytics=new AppAnalytics(getApplicationContext());
        appAnalytics.GameEventStart(ffirebase_screen_class,slo_type,game_grade,game_subject,game_design_type,game_version,game_total_stars,is_free,game_questions,question_type,question_id_1,question_id_x,deep_link);
    }

    public void setGameEventEnd(String ffirebase_screen_class, int game_total_stars, int user_game_stars){
        AppAnalytics appAnalytics=new AppAnalytics(getApplicationContext());
        appAnalytics.GameEventEnd(ffirebase_screen_class,game_total_stars,user_game_stars);
    }

    public void setAnswerEvent(int question_id, String question_type,String user_answer_value, boolean user_answer_correct){
        AppAnalytics appAnalytics=new AppAnalytics(getApplicationContext());
        appAnalytics.AnswerEvent(question_id, question_type, user_answer_value, user_answer_correct);
    }

    public void GetUserUid(){
//        FirebaseAuth mAuth;
//        mAuth = FirebaseAuth.getInstance();
//        firebaseUser = mAuth.getCurrentUser();
//        String UserUid = firebaseUser.getUid().toString();
        //  UnityPlayer.UnitySendMessage("ScriptHandler","SetUserUid",UserUid);
    }

    public void SetSelectedSubjectAnalytics(String Subject){
        AppAnalytics appAnalytics=new AppAnalytics(getApplicationContext());
        appAnalytics.SelectedSubject(Subject);
    }
    public void SetSelectedGradeAnalytics(String Grade){
        AppAnalytics appAnalytics=new AppAnalytics(getApplicationContext());
        appAnalytics.SelectedGrade(Grade);
    }
    public void SetIntitalGradeAnalytics(String Grade){
        AppAnalytics appAnalytics=new AppAnalytics(getApplicationContext());
        appAnalytics.SelectedInitialGrade(Grade);
    }
    public void SetParentPortalAnalytcs(){
        AppAnalytics appAnalytics=new AppAnalytics(getApplicationContext());
        appAnalytics.ParentPortal();
    }
//    public void SetGradeButtonAnalytics(){
//        AppAnalytics appAnalytics=new AppAnalytics(getApplicationContext());
////        firebaseUser = mAuth.getCurrentUser();
////        String UserUid = firebaseUser.getUid();
////        String LastUidCharacter = UserUid.substring(UserUid.length() -1 );
//        int LastUidDigit = Integer.parseInt(LastUidCharacter);
//        String ButtonType= null;
//        String UserType = null;
//        switch (LastUidDigit){
//            case 1:
//            case 3:
//            case 5:
//            case 7:
//            case 9:
//                UserType = "Odd";
//                ButtonType = "GradeIcon";
//                break;
//            case 0:
//            case 2:
//            case 4:
//            case 6:
//            case 8:
//                UserType = "Even";
//                ButtonType = "BackIcon";
//                break;
//            default:
//                UserType = "Odd";
//                ButtonType = "GradeIcon";
//
//        }
//        appAnalytics.GradeButtonClick(ButtonType, UserType);
//    }

    void ShowDialogBox(String title, String Message){
        Log.d("testtest","show dialog box");
        AlertDialog.Builder builder = new AlertDialog.Builder(UnityPlayerActivity.this);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

//    void CheckSubscription() {
//        final String userUid = firebaseUser.getUid();
//        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users/" + userUid + "/Subscription");
//        ValueEventListener valueEventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    if (dataSnapshot.child("Status").getValue().equals("Active")) {
//                        String ActiveSubscriptionId = dataSnapshot.child("ActiveSubscriptionId").getValue().toString();
//                        Long ActivationDate = Long.parseLong(dataSnapshot.child("SubscriptionList").child(ActiveSubscriptionId).child("ActivationDate").getValue().toString());
//                        Long ExpiryDate = Long.parseLong(dataSnapshot.child("SubscriptionList").child(ActiveSubscriptionId).child("ExpiryDate").getValue().toString());
//                        Long CurrentDate = System.currentTimeMillis()/1000;
//                        if(CurrentDate<ExpiryDate){
//                            UnityPlayer.UnitySendMessage("ScriptHandler", "UpdateSubscriptionDates", ActivationDate+","+ExpiryDate+","+userUid);
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        };
//        mDatabase.addListenerForSingleValueEvent(valueEventListener);
//    }

    void showToastMessage(String message){
        Toast.makeText(getApplicationContext(),message, Toast.LENGTH_SHORT).show();
    }

    void MusicButton(boolean musicEnabled){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        if(musicEnabled){
            editor.putBoolean("MusicEnabled",true);
        }else{
            editor.putBoolean("MusicEnabled",false);
        }
        editor.apply();
    }

    void SoundButton(boolean soundEnabled){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        if(soundEnabled){
            editor.putBoolean("SoundEnabled",true);
        }else{
            editor.putBoolean("SoundEnabled",false);
        }
        editor.apply();
    }

    public void OpenTaleemabadSecondaryApp(){
        try {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=com.orenda.taleemabadgrade6")));
        } catch (android.content.ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=com.orenda.taleemabadgrade6")));
        }
    }

    public void ShowPhoneDialpadWithNumber(String phoneNumber){
        Log.d("phone","Show phone");
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+phoneNumber));
        startActivity(intent);
    }

    public void InitiateBankTransfer(){
        if(isInternetAvailable()) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"));
            startActivity(browserIntent);
        }else{
            UnityPlayer.UnitySendMessage("ScriptHandler","ShowInternetIssuePopup","");
        }

    }

    public void InitiateCreditDebitPayment(String SubscriptionPackage){
        if(isInternetAvailable()) {
            GooglePurchase googlePurchase = new GooglePurchase(this, this, SubscriptionPackage);
            googlePurchase.EstablishConnection(SubscriptionPackage);
        }else{
            UnityPlayer.UnitySendMessage("ScriptHandler","ShowInternetIssuePopup","");
        }
    }

    public void setSaleInitiatedAnalytics(String salesMethod, String bundle){
        AppAnalytics appAnalytics = new AppAnalytics(getApplicationContext());
        appAnalytics.setSaleInitiatedAnalytics(salesMethod, bundle);
    }

    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }

    public void InternalRatingAnalytics(int rating){
        new AppAnalytics(getApplicationContext()).InternalRatingAnalytics(rating);
    }

    public void HamburgerMenuButtonClickedAnalytics(){
        new AppAnalytics(getApplicationContext()).HamburgerMenuButtonClickedAnalytics();
    }

    public void MusicEnabledAnalytics(){
        new AppAnalytics(getApplicationContext()).MusicEnabledAnalytics();
    }
    public void MusicDisabledAnalytics(){
        new AppAnalytics(getApplicationContext()).MusicDisabledAnalytics();
    }
    public void SoundEnabledAnalytics(){
        new AppAnalytics(getApplicationContext()).SoundEnabledAnalytics();
    }
    public void SoundDisabledAnalytics(){
        new AppAnalytics(getApplicationContext()).SoundDisabledAnalytics();
    }

    public void SubscriptionButtonClickedAnalytics(){
        new AppAnalytics(getApplicationContext()).SubscriptionButtonClickedAnalytics();
    }

    public void TaleemabadSecondaryAppButtonAnalytics(){
        new AppAnalytics(getApplicationContext()).TaleemabadSecondaryAppButtonAnalytics();
    }

    public void BuyBooksButtonClickedAnalytics(){
        new AppAnalytics(getApplicationContext()).BuyBooksButtonClicked();
    }

    public void ShowFAQPage(){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://taleemabad.com/faq"));
        startActivity(intent);
    }

    public void ShowReferralPopupAnalytics(){
        new AppAnalytics(getApplicationContext()).ShowReferralPopupAnalytics();
    }

    public void HideReferralPopupAnalytics(){
        new AppAnalytics(getApplicationContext()).HideReferralPopupAnalytics();
    }

    public void ShareTaleemabadOnWhatsApp(){
        new AppAnalytics(getApplicationContext()).ShareReferralPopupAnalytics();
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Hi! Taleemabad Learning Application is the best source of digital education that you can provide to your children! From concept explaining videos to challenging tests, this Application has it all. I have been using it and I think you should give it a try too!\n https://play.google.com/store/apps/details?id=com.orenda.taimo.myapplication&referrer=utm_source%3Dinapp%2520share%26utm_medium%3Dinapp%2520message%26utm_term%3Dappdownload%26utm_content%3Dinapp%2520share%26utm_campaign%3Dinapp%2520share");
        sendIntent.setType("text/plain");
        //sendIntent.setPackage("com.whatsapp");
        startActivity(sendIntent);
    }

    public void trialOverAnalytics(){
        new AppAnalytics(getApplicationContext()).TrialExpired();
    }

    //function called from unity when user taps sign in button //also called from checkForceSignup in this activity
    public void Firebasesignin(){
        Intent intent = new Intent(UnityPlayerActivity.this, SignupActivity.class);
        intent.putExtra("signinFromUnity",true);
        startActivity(intent);
    }
    //function called from unity when user taps sign out button
    public void Firebasesignout(){
        Log.d("unitytesting","2");
    }
    //function called from unity to see if user is phoneauthenticated
//    public void Firebaseissignin(){
//        Log.d("unitytesting","3");
//        final FirebaseUser currentUser = mAuth.getCurrentUser();
//        if (currentUser != null) {
//                if (currentUser.isAnonymous()) {
//                    UnityPlayer.UnitySendMessage("ScriptHandler","AndtounIsSignedIn","no");
//                }else{
//                    UnityPlayer.UnitySendMessage("ScriptHandler","AndtounIsSignedIn","yes");
//                }
//        }
//        //UnityPlayer.UnitySendMessage("ScriptHandler","AndtounIsSignedIn","yes");
//        //UnityPlayer.UnitySendMessage("ScriptHandler","AndtounIsSignedIn","no");
//    }

    public void SetParentPortalDetails(String age, String schoolName) {
        new AppAnalytics(getApplicationContext()).ParentPortalDetails(age, schoolName);
    }

    public void ParentPortalCompleteDetailsToast(){
        Toast.makeText(getApplicationContext(),"Please complete the details",Toast.LENGTH_SHORT).show();
    }

//    public void disableActiveBundleInDatabase(String bundle){
//        Log.d("DisableActive",": "+bundle);
//        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users/" + firebaseUser.getUid() + "/Purchase/"+bundle);
//        Log.d("DisableActive",": "+mDatabase);
//        ValueEventListener valueEventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    Log.d("DisableActive",":exists ");
//                        String LatestBundleActivated = String.valueOf(dataSnapshot.getChildrenCount());
//                        mDatabase.child(LatestBundleActivated).child("status").setValue("false");
//
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.d("DisableActive",":err "+databaseError.getMessage());
//            }
//        };
//        mDatabase.addListenerForSingleValueEvent(valueEventListener);
//    }


    public void initiateEasyPaisaOTPPayment(String bundle, double amount, String phoneNumber, String email){
        if(phoneNumber.equals("")){
            Toast.makeText(getApplicationContext(),"Please enter phone number",Toast.LENGTH_SHORT).show();
        }else{
            EasyPaisaPayment easyPaisaPayment = new EasyPaisaPayment(getApplicationContext(),bundle, this);
            easyPaisaPayment.initiateEasyPaisaOTC(amount,phoneNumber,email);
        }
    }

    public void initiateEasyPaisaMAPayment(String bundle, double amount, String phoneNumber, String email){
        if(phoneNumber.equals("")){
            Toast.makeText(getApplicationContext(),"Please enter phone number",Toast.LENGTH_SHORT).show();
        }else{
            EasyPaisaPayment easyPaisaPayment = new EasyPaisaPayment(getApplicationContext(),bundle, this);
            easyPaisaPayment.initiateEasyPaisaMAPayment(amount,phoneNumber,email);
        }
    }

//    public void checkForceSignup(){
//        Log.d("checkForceSignup","Called");
//        final FirebaseUser currentUser = mAuth.getCurrentUser();
//        if (currentUser != null) {
//            if (currentUser.isAnonymous()) {
//                Log.d("checkForceSignup","is anon");
//                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
//                Long AnonymousD0Signup = preferences.getLong("AnonymousD0Signup", 0);
//                if (System.currentTimeMillis() > AnonymousD0Signup + 86400000 ) {
//                    Log.d("checkForceSignup","Time passed");
//                    Firebasesignin();
//                }
//            }
//        }
//    }
    /**function called by unity if no video, test and game activity is performed in 26seconds-
     *on firstopen to trigger event for inapp message
     */
//    public void NoActivityPerformed(String Grade){
//        InAppMessagingListener listener = new InAppMessagingListener(this);
//        FirebaseInAppMessaging.getInstance().addClickListener(listener);
//        FirebaseInAppMessaging.getInstance().addDismissListener(listener);
//        switch (Grade){
//            case "Nursery":
//                new AppAnalytics(getApplicationContext()).Nursery_Session_Expired();
//                break;
//            case "Grade1":
//                new AppAnalytics(getApplicationContext()).Grade1_Session_Expired();
//                break;
//            case "Grade2":
//                new AppAnalytics(getApplicationContext()).Grade2_Session_Expired();
//                break;
//            case "Grade3":
//                new AppAnalytics(getApplicationContext()).Grade3_Session_Expired();
//                break;
//            case "Grade4":
//                new AppAnalytics(getApplicationContext()).Grade4_Session_Expired();
//                break;
//            case "Grade5":
//                new AppAnalytics(getApplicationContext()).Grade5_Session_Expired();
//                break;
//        }
//    }

    /**
     *function to check if user is eligble for free app
     */
    public void CheckFreeUser(){
        Log.d("CheckFreeuser","Check Free user called");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFreeUser = preferences.getBoolean("free_app_user", false);
        if(isFreeUser){
            UnityPlayer.UnitySendMessage("ScriptHandler","IsFreeExperiment","true");
        }else{
            UnityPlayer.UnitySendMessage("ScriptHandler","IsFreeExperiment","false");
        }

    }

    /**
     * function called from unity to get user bundle type
     */
    public void PaymentExperimentFiftyFifty(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String bundleUserType = preferences.getString("bundleDurationUserType","0");
        UnityPlayer.UnitySendMessage("ScriptHandler","ResponsePaymentExperimentFiftyFifty",bundleUserType);
    }
    public void LiveTuitionMenuClickAnalytics(){
        new AppAnalytics(getApplicationContext()).LiveTuitionMenuClick();
    }
    public void LiveTuitionOrderNowAnalytics(){
        new AppAnalytics(getApplicationContext()).LiveTuitionOrderNow();
    }

    public void downloadGameFilesAndroid(final String key, boolean multiDownload)
    {
        final File file;
        /*
                    TransferUtility transferUtility =
                            TransferUtility.builder()
                                    .context(getApplicationContext())
                                    .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                                    .s3Client(new AmazonS3Client(AWSMobileClient.getInstance().getCredentialsProvider()))
                                    .build();
*/
        /*
        TransferObserver downloadObserver =
                            transferUtility.download(
                                    "video-repository-taleemabad-ireland/assetbundles",
                                    key,
                                   file= new File(getApplicationContext().getExternalFilesDir(null), key+"demo"));

         */

                   /* downloadObserver.setTransferListener(new TransferListener() {
                        @Override
                        public void onStateChanged(int id, TransferState state) {
                            if (TransferState.COMPLETED == state) {
                                File file1=new File(getApplicationContext().getExternalFilesDir(null),key);
                                boolean check =file.renameTo(file1);
                                if(check){
                                    if(key.equals("Grade1Voiceovers1.zip") || key.equals("Grade2Voiceovers1.zip")){
                                        File root = new File(String.valueOf(getApplicationContext().getExternalFilesDir(null)));
                                        unpackZip(root+"/"+key);
                                    }
                                    Log.d("MainActivity","renamedfile");
                                }else {
                                    Log.d("MainActivity", "failed");
                                }
                                UnityPlayer.UnitySendMessage("ScriptHandler","OnCompleteDownload",key);
                                if(multiDownload){
                                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                SharedPreferences PlayerPrefs = getApplicationContext().getSharedPreferences(getApplicationContext().getPackageName() + ".v2.playerprefs", Context.MODE_PRIVATE);
                                    int purchaseStatus =0;
                                if (PlayerPrefs.getInt("app_purchase", 0) == 1 || PlayerPrefs.getInt("SubscribedUser", 0) == 1 || PlayerPrefs.getInt("SubscribedBundlelifetime", 0) == 1 || PlayerPrefs.getInt("SubscribedBundlekto5", 0) == 1 || PlayerPrefs.getInt("SubscribedBundlekto2", 0) == 1 || PlayerPrefs.getInt("SubscribedBundle3to5", 0) == 1){
                                    purchaseStatus =1;
                                }
                                //int purchaseStatus = preferences.getInt("app_purchase",0);

                                switch (purchaseStatus){
                                    case 0:
                                        checkNextDownloadTrial(key);
                                        break;
                                    case 1:
                                        checkNextDownloadAll(key);
                                        break;
                                }
                                }


                            }
                            else if(TransferState.FAILED == state){
                                Log.d("MainActivity","download failed");
                                UnityPlayer.UnitySendMessage("selectionObject","downloadFailed",key);
                            }
                        }
                        @Override
                        public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                            float percentDonef = ((float)bytesCurrent/(float)bytesTotal) * 100;
                            int percentDone = (int)percentDonef;
                            int currentMb= (int) bytesCurrent/(1024*1024);
                            int totalMb=(int) bytesTotal/(1024*1024);
                            int max=100;
                            String sendtounity=Float.toString(percentDonef/100);
                            UnityPlayer.UnitySendMessage("ScriptHandler","UpdateDownloadProgressAssetBundleName",key);
                            UnityPlayer.UnitySendMessage("ScriptHandler","UpdateDownloadProgress",sendtounity);
                            Log.d("MainActivity", "   ID:" + id + "   bytesCurrent: " + bytesCurrent + "   bytesTotal: " + bytesTotal + " " + percentDone + "%"+" : "+sendtounity+" key:"+key);
                        }

                        @Override
                        public void onError(int id, Exception ex) {
                            // Handle errors
                            Log.d("MainActivity","hereErr: "+ex);
                            File file=new File(getApplicationContext().getExternalFilesDir(null),key);
                            if(file.exists()){
                                file.delete();
                            }
                        }
                    });*/

    }

    void checkNextDownloadAll(String key){
        switch (key){
            case "parentportal":
                key = "Grade1Voiceovers1.zip";
                break;
            case "Grade1Voiceovers1.zip":
                key = "Grade2Voiceovers1.zip";
                break;
            case "Grade2Voiceovers1.zip":
                key = "eng_wordbuilder1";
                break;
            case "eng_wordbuilder1":
                key = "eng_s3";
                break;
            case "eng_s3":
                key = "eng_a3";
                break;
            case "eng_a3":
                key = "eng_t3";
                break;
            case "eng_b3":
                key = "eng_j3";
                break;
            case "eng_c3":
                key = "eng_k3";
                break;
            case "eng_d3":
                key = "eng_g4";
                break;
            case "eng_e3":
                key = "eng_h3";
                break;
            case "eng_f3":
                key="eng_b3";
                break;
            case "eng_g4":
                key="eng_o4";
                break;
            case "eng_h3":
                key = "eng_r3";
                break;
            case "eng_i3":
                key = "eng_p3";
                break;
            case "eng_j3":
                key = "eng_z3";
                break;
            case "eng_k3":
                key = "eng_e3";
                break;
            case "eng_l3":
                key = "eng_f3";
                break;
            case "eng_m4":
                key = "eng_d3";
                break;
            case "eng_n3":
                key = "eng_c3";
                break;
            case "eng_o4":
                key = "eng_u3";
                break;
            case "eng_p3":
                key = "eng_n3";
                break;
            case "eng_q3":
                //key = "math_13";
                break;
            case "eng_r3":
                key = "eng_m4";
                break;
            case "eng_t3":
                key = "eng_i3";
                break;
            case "eng_u3":
                key = "eng_l3";
                break;
            case "eng_v3":
                key = "eng_y3";
                break;
            case "eng_w3":
                key = "eng_v3";
                break;
            case "eng_x3":
                key = "eng_q3";
                break;
            case "eng_y3":
                key = "eng_x3";
                break;
            case "eng_z3":
                key = "eng_w3";
                break;
            case "math_13":
                key = "math_23";
                break;
            case "math_23":
                key = "math_33";
                break;
            case "math_33":
                key = "math_43";
                break;
            case "math_43":
                key = "math_53";
                break;
            case "math_53":
                key = "math_63";
                break;
            case "math_63":
                key = "math_73";
                break;
            case "math_73":
                key = "math_83";
                break;
            case "math_83":
                key = "math_93";
                break;
            case "math_93":
                key = "math_addition3";
                break;
            case "math_addition3":
                key = "math_singledigitaddition3";
                break;
            case "math_singledigitaddition3":
                //key = "urdu_alifmada2";
                break;
            case "urdu_imla":
                key = "urdu_alifmada2";
                break;
            case "urdu_alifmada2":
                key = "urdu_alif3";
                break;
            case "urdu_alif3":
                key = "urdu_bay4";
                break;
            case "urdu_array3":
                key = "urdu_zay3";
                break;
            case "urdu_bay4":
                key = "urdu_pay3";
                break;
            case "urdu_chay3":
                key = "urdu_hay3";
                break;
            case "urdu_daal4":
                key = "urdu_dhaal3";
                break;
            case "urdu_dhaal3":
                key = "urdu_zaal3";
                break;
            case "urdu_hay3":
                key = "urdu_khay3";
                break;
            case "urdu_jeem3":
                key = "urdu_chay3";
                break;
            case "urdu_khay3":
                key = "urdu_daal4";
                break;
            case "urdu_pay3":
                key = "urdu_thay3";
                break;
            case "urdu_ray3":
                key = "urdu_array3";
                break;
            case "urdu_say3":
                key = "urdu_jeem3";
                break;
            case "urdu_tay3":
                key = "urdu_say3";
                break;
            case "urdu_thay3":
                key = "urdu_tay3";
                break;
            case "urdu_zaal3":
                key = "urdu_ray3";
                break;
            case "urdu_zay3":
                key = "urdu_zhay3";
                break;
            case "urdu_zhay3":
                key = "urdu_seen3";
                break;
            case "urdu_seen3":
                key = "urdu_sheen4";
                break;
            case "urdu_sheen4":
                key = "urdu_suaad4";
                break;
            case "urdu_suaad4":
                key = "urdu_zuaad3";
                break;
            case "urdu_zuaad3":
                key = "urdu_tuoy4";
                break;
            case "urdu_tuoy4":
                key = "urdu_zoein3";
                break;
            case "urdu_zoein3":
                key = "urdu_aein3";
                break;
            case "urdu_aein3":
                key = "urdu_ghaein3";
                break;
            case "urdu_ghaein3":
                key = "urdu_fay3";
                break;
            case "urdu_fay3":
                key = "urdu_qaaf3";
                break;
            case "urdu_qaaf3":
                key = "urdu_kaaf3";
                break;
            case "urdu_kaaf3":
                key = "urdu_gaaf3";
                break;
            case "urdu_gaaf3":
                key = "urdu_laam3";
                break;
            case "urdu_laam3":
                key = "urdu_meem3";
                break;
            case "urdu_meem3":
                key = "urdu_noon3";
                break;
            case "urdu_noon3":
                key = "urdu_wow3";
                break;
            case "urdu_wow3":
                key = "urdu_hey3";
                break;
            case "urdu_hey3":
                key = "urdu_hamza2";
                break;
            case "urdu_hamza2":
                key="urdu_yaay3";
                break;
            case "urdu_yaay3":
                key = "interactive1";
                break;
            case "interactive1":
                break;
        }
        if(!new File(getApplicationContext().getExternalFilesDir(null), key).exists()){
            downloadGameFilesAndroid(key, true);
        }
    }

    void checkNextDownloadTrial(String key){
        switch (key){
            case "parentportal":
                key = "Grade1Voiceovers1.zip";
                break;
            case "Grade1Voiceovers1.zip":
                key = "Grade2Voiceovers1.zip";
                break;
            case "Grade2Voiceovers1.zip":
                key = "eng_wordbuilder1";
                break;
            case "eng_wordbuilder1":
                key = "eng_s3";
                break;
            case "eng_s3":
                key = "eng_a3";
                break;
            case "eng_a3":
                break;
            case "math_13":
                key = "math_23";
                break;
            case "math_23":
                key = "math_33";
                break;
            case "urdu_imla":
                key = "urdu_alifmada2";
                break;
            case "urdu_alifmada2":
                key = "urdu_alif3";
                break;
            case "urdu_alif3":
                key = "urdu_bay4";
                break;
        }
        if(!new File(getApplicationContext().getExternalFilesDir(null), key).exists()){
            downloadGameFilesAndroid(key, true);
        }
    }

    public boolean unpackZip(String filePath) {
        Log.d("unzip","start"+filePath);
        InputStream is;
        ZipInputStream zis;
        try {
            File zipfile = new File(filePath);
            String parentFolder = zipfile.getParentFile().getPath();
            Log.d("unzip","in try- zipfile: "+zipfile+" \n Parent file: "+parentFolder);
            String filename;
            is = new FileInputStream(filePath);
            zis = new ZipInputStream(new BufferedInputStream(is));
            ZipEntry ze;
            byte[] buffer = new byte[1024];
            int count;
            while ((ze = zis.getNextEntry()) != null) {
                filename = ze.getName();
                Log.d("unzip","FileName: "+filename);
                if (ze.isDirectory()) {
                    Log.d("unzip","ze is directory"+ze);
                    File fmd = new File(parentFolder + "/Grade1Voiceovers" + filename);
                    fmd.mkdirs();
                    continue;
                }
                FileOutputStream fout = new FileOutputStream(parentFolder + "/" + filename);
                while ((count = zis.read(buffer)) != -1) {
                    fout.write(buffer, 0, count);
                }
                Log.d("unzip","extract");
                fout.close();
                zis.closeEntry();
            }
            zis.close();
        } catch(IOException e) {
            Log.d("unzip",e.getMessage());
            return false;
        }
        return true;
    }
}
