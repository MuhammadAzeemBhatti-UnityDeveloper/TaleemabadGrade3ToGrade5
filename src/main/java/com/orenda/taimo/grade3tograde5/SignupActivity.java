package com.orenda.taimo.grade3tograde5;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.installreferrer.api.InstallReferrerClient;
//import com.facebook.CampaignTrackingReceiver;
//import com.facebook.GraphRequest;
//import com.facebook.GraphResponse;
//import com.facebook.appevents.AppEventsLogger;
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationCallback;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationResult;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.location.LocationSettingsRequest;
//import com.google.android.gms.location.LocationSettingsResponse;
//import com.google.android.gms.location.SettingsClient;
//import com.google.firebase.analytics.FirebaseAnalytics;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
//import com.google.firebase.auth.FirebaseAuthUserCollisionException;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.auth.PhoneAuthCredential;
//import com.google.firebase.auth.PhoneAuthProvider;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.functions.FirebaseFunctions;
//import com.google.firebase.functions.HttpsCallableResult;
//import com.google.firebase.iid.FirebaseInstanceId;
//import com.google.firebase.messaging.FirebaseMessaging;
//import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
//import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.hbb20.CountryCodePicker;
import com.unity3d.player.UnityPlayer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import firebase.analytics.AppAnalytics;
import firebase.analytics.SignupAnalytic;
//import localnotifications.NotificationHelper;


public class SignupActivity extends AppCompatActivity implements View.OnClickListener {
    // private FirebaseAuth mAuth;
    ProgressDialog pd;
    boolean teacherQuery = false;
    //String userTypeQuery=null;
    //String userTypeOtherOptionReason=null;
    public static Date appStartTime;
    String purchaseData;// variable to store purchase data split with comma as done in coupon check
    public static String userUid = null;
    String questionnaireSelectedOption = null;
    String questionnaireOtherOptionReason = null;
    Button btn_telenor, btn_mobilink, btn_ufone, btn_zong, btn_other;
    private EditText editTextNumber, editTextCode, signUpEditTextName;
    private Button buttonSubmit, buttonVerify, buttonSubmitName;
    private ConstraintLayout numberScreen, otpScreen, feedbackScreen, surveryScreen;
    private TextView otpResendTextView;
    private RelativeLayout mainLayout, relativeLayoutProgressBar;
    boolean firebaseSignup = false; //boolean to check if signing in from firebase phoneAuth or Mobilink API, true means signing from firebase phoneauth
    boolean restartUnity = false; // boolean to restart unity after signun is called from unity
    CountryCodePicker countryCodePicker;
    boolean userAlreadyExist =false; //boolean to check if user is already registered with the number

    String phoneNumber, mVerificationID, userName;

//    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
//    PhoneAuthProvider.ForceResendingToken mResendToken;
//    //private final Executor backgroundExecutor = Executors.newSingleThreadExecutor();


    // Feedback exit survey
    Button option1, option2, option3;
    EditText feedbackEditText;
    boolean otpScreenVisible = false, numberScreenVisible = false;

    private static final int REQUEST_CODE_ASK_PERMISSIONS_1 = 101;
    private static final int REQUEST_CODE_ASK_PERMISSIONS_2 = 102;
    private static final int REQUEST_CODE_ASK_PERMISSIONS_3 = 103;
    private static final int REQUEST_CHECK_SETTINGS = 201;
    //private FusedLocationProviderClient fusedLocationProviderClient;
    //private LocationCallback locationCallback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_signup);
        // CheckMaximumSignedInUsersWithOneNumber();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        fullScreen();
        relativeLayoutProgressBar = findViewById(R.id.relativeLayoutProgressBar);
      //  mAuth = FirebaseAuth.getInstance();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            //takePermission();
        }
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirstOpen = preferences.getBoolean("firstLogin", true);
        if (isFirstOpen) {
            createNotificationChannel();
//            FirebaseMessaging.getInstance().subscribeToTopic("TaleemabadAndroidFirebaseNotification"); //subscribing to firebase topic for notifications
//            FirebaseAuth.getInstance().signOut();
            InstallReferrerClient referrerClient = InstallReferrerClient.newBuilder(this).build();
            //backgroundExecutor.execute(() -> getInstallReferrerFromClient(referrerClient));
            Intent intent = new Intent(SignupActivity.this, StoryBoardingActivity.class);
            startActivity(intent);
            finish();
        }
        else
        {
            Intent intent = new Intent(SignupActivity.this, UnityPlayerActivity.class);
            startActivity(intent);
            finish();
           /*
            createNotificationChannel();
            // Check if user is signed in (non-null) and update UI accordingly.
           /// final FirebaseUser currentUser = mAuth.getCurrentUser();
            Intent unityIntent = getIntent();
            boolean signinFromUnityCalled = unityIntent.getBooleanExtra("signinFromUnity", false);
            if (signinFromUnityCalled) {
                restartUnity = true;
                phoneAuthInit();
            } else {
//                if (currentUser != null) {
//                    userUid = currentUser.getUid();
                    boolean isUserNameTaken = preferences.getBoolean("userNameTaken", false);
                    if (isUserNameTaken) {
                        Bundle firebaseBundle = getIntent().getExtras();
                        if (firebaseBundle != null ) {
                            Long AnonymousD0Signup = preferences.getLong("AnonymousD0Signup", 0);
                            String firebaseNotification = (firebaseBundle.containsKey("firebaseNotification"))? firebaseBundle.getString("firebaseNotification"):"false";
//                            if(firebaseNotification.equalsIgnoreCase("true") && (!currentUser.isAnonymous() || System.currentTimeMillis() < AnonymousD0Signup + 86400000 )){
//                                checkFirebaseNotification();
//                            }else{
//                                if (currentUser.isAnonymous()) {
//                                    if (System.currentTimeMillis() > AnonymousD0Signup + 86400000 ) {
//                                        phoneAuthInit();
//                                    } else {
//                                        Intent intent = new Intent(SignupActivity.this, UnityPlayerActivity.class);
//                                        startActivity(intent);
//                                        finish();
//                                    }
//
//                                } else {
//                                    Intent intent = new Intent(SignupActivity.this, UnityPlayerActivity.class);
//                                    startActivity(intent);
//                                    finish();
//                                }
//                            }
                        }
                        else{
//                            if (currentUser.isAnonymous()) {
//                                //FirebaseAuth.getInstance().signOut();
//                                Long AnonymousD0Signup = preferences.getLong("AnonymousD0Signup", 0);
//                                if (System.currentTimeMillis() > AnonymousD0Signup + 86400000 ) {
//                                    phoneAuthInit();
//                                } else {
//                                    Intent intent = new Intent(SignupActivity.this, UnityPlayerActivity.class);
//                                    startActivity(intent);
//                                    finish();
//                                }
//                            } else {
//                                Intent intent = new Intent(SignupActivity.this, UnityPlayerActivity.class);
//                                startActivity(intent);
//                                finish();
//                            }
                        }
                    } else {
                        //userSimOperator();
                        ShowUserNameLayout();
                    }
//                } else {
//                    //user is not signed in, take simOperator and sign in with phone Auth
//                    ShowUserNameLayout(); //now
//                    //phoneAuthInit(); now
//                    //signInAnonymously();
//                }
            }
            */
        }
    }
    void checkFirebaseNotification(){
        Bundle firebaseBundle = getIntent().getExtras();
        if (firebaseBundle != null) {
            String firebaseNotification = (firebaseBundle.containsKey("firebaseNotification"))? firebaseBundle.getString("firebaseNotification"):"false";
            firebaseBundle.remove("firebaseNotification");
            if(firebaseNotification.equalsIgnoreCase("true")){
//						String notificationid = (remoteMessage.getData().containsKey("notificationid"))? remoteMessage.getData().get("notificationid"):null;
//			            String notificationtimestamp = (remoteMessage.getData().containsKey("timestamp"))? remoteMessage.getData().get("timestamp"):null;
                String notificationtype = (firebaseBundle.containsKey("type"))? firebaseBundle.getString("type"):null;
//			            String notificationimage = (remoteMessage.getData().containsKey("imageLink"))? remoteMessage.getData().get("imageLink"):null;
                String notificationvideoname = (firebaseBundle.containsKey("videoname"))? firebaseBundle.getString("videoname"):null;
                String notificationtestname = (firebaseBundle.containsKey("testname"))? firebaseBundle.getString("testname"):null;
                String notificationgrade = (firebaseBundle.containsKey("grade"))? firebaseBundle.getString("grade"):null;
                String notificationsubject = (firebaseBundle.containsKey("subject"))? firebaseBundle.getString("subject"):null;
                String notificationlink = (firebaseBundle.containsKey("link"))? firebaseBundle.getString("link"):null;
                String notificationnurserygametype = (firebaseBundle.containsKey("nurserygametype"))? firebaseBundle.getString("nurserygametype"):null;
                HandleNotification(notificationtype, notificationlink,notificationgrade,notificationsubject,notificationvideoname,notificationtestname,notificationnurserygametype);
            }
        }
    }

    void HandleNotification(String type, String link, String grade, String subject, String videoname, String testname, String nurserygametype) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Intent intent = null;

        switch (type) {
            case "Videos":
                intent = new Intent(getApplicationContext(), VideoActivity.class);
                intent.putExtra("Notification", true);
                intent.putExtra("GradeSelected", grade);
                intent.putExtra("SubjectSelected", subject);
                intent.putExtra("Alpha", videoname);
                intent.putExtra("ContentUnlocked", preferences.getInt("AllContentUnlocked", 0));
                intent.putExtra("PurchaseStatus", preferences.getInt("app_purchase", 0));
                break;
            case "Test":
                if (!grade.equalsIgnoreCase("Nursery")) {
                    intent = new Intent(getApplicationContext(), SimpleTestActivity.class);
                    intent.putExtra("Notification", true);
                    intent.putExtra("GradeSelected", grade);
                    intent.putExtra("SubjectSelected", subject);
                    intent.putExtra("Alpha", testname);
                    intent.putExtra("source", "nonsocratic");

                } else {
                    intent = new Intent(getApplicationContext(), UnityPlayerActivity.class);
                    intent.putExtra("Notification", true);
                    intent.putExtra("GradeSelected", grade);
                    intent.putExtra("SubjectSelected", subject);
                    intent.putExtra("Alpha", testname);
                    intent.putExtra("GameType", nurserygametype);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                }
                break;
            case "Survey":
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                break;
            case "ParentPortal":
                intent = new Intent(getApplicationContext(), UnityPlayerActivity.class);
                intent.putExtra("Notification", true);
                intent.putExtra("ParentPortal", true);
                break;
            case "Referral":
                intent = new Intent(getApplicationContext(), UnityPlayerActivity.class);
                intent.putExtra("Notification", true);
                intent.putExtra("ReferralNotification", true);
                break;
            case "Home":
                intent = new Intent(getApplicationContext(), UnityPlayerActivity.class);
                break;
            default:
                intent = new Intent(getApplicationContext(), UnityPlayerActivity.class);
                break;
        }
        startActivity(intent);
        finish();
        //Log.d("Notification Test","Signup activity: Activity started");
    }
    void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("GeneralNotifications", name, importance);
            channel.setDescription(description);
            channel.enableLights(true);
            channel.enableVibration(true);
            AudioAttributes att = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .build();
            channel.setSound(Uri.parse("android.resource://com.orenda.taimo.myapplication/raw/notificationtone"), att);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    /*void getInstallReferrerFromClient(InstallReferrerClient referrerClient) {

        referrerClient.startConnection(new InstallReferrerStateListener() {
            @Override
            public void onInstallReferrerSetupFinished(int responseCode) {
                switch (responseCode) {
                    case InstallReferrerClient.InstallReferrerResponse.OK:
                        ReferrerDetails response = null;
                        try {
                            response = referrerClient.getInstallReferrer();
                        } catch (RemoteException e) {
                            e.printStackTrace();
                            return;
                        }
                        final String referrerUrl = response.getInstallReferrer();


                        // TODO: If you're using GTM, call trackInstallReferrerforGTM instead.
                        trackInstallReferrer(referrerUrl);

                        // End the connection
                        referrerClient.endConnection();

                        break;
                    case InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED:
                        // API not available on the current Play Store app.
                        break;
                    case InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE:
                        // Connection couldn't be established.
                        break;
                }
            }

            @Override
            public void onInstallReferrerServiceDisconnected() {
            }
        });
    }*/

    // Tracker for Classic GA (call this if you are using Classic GA only)
    /*private void trackInstallReferrer(final String referrerUrl) {
        new Handler(getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                CampaignTrackingReceiver receiver = new CampaignTrackingReceiver();
                Intent intent = new Intent("com.android.vending.INSTALL_REFERRER");
                intent.putExtra("referrer", referrerUrl);
                receiver.onReceive(getApplicationContext(), intent);
            }
        });
    } */

    private void  phoneAuthInit() {
        numberScreenVisible = true;
        callSignUpAnalytic("PhoneAuth");
        mainLayout = findViewById(R.id.mainLayout);
        numberScreen = findViewById(R.id.number_page);
        otpScreen = findViewById(R.id.otp_page);
        feedbackScreen = findViewById(R.id.exitFeedbackQuit);


        editTextNumber = findViewById(R.id.signUpEditTextNumber);
        editTextCode = findViewById(R.id.signUpEditTextVerificationCode);
        otpResendTextView = findViewById(R.id.otpResendTextView);

        buttonSubmit = findViewById(R.id.signUpButtonSubmit);
        buttonVerify = findViewById(R.id.signUpButtonVerify);

        countryCodePicker = findViewById(R.id.signUpCountryCodePicker);

        countryCodePicker.registerCarrierNumberEditText(editTextNumber);

        numberScreen.setVisibility(View.VISIBLE);


//        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//            @Override
//            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//                //siginWithPhoneCredential(phoneAuthCredential);
//            }
//
//            @Override
//            public void onVerificationFailed(@NonNull FirebaseException e) {
//                Log.d("phoneAUth", "failed" + phoneNumber);
//                if (e instanceof FirebaseAuthInvalidCredentialsException) {
//                    relativeLayoutProgressBar.setVisibility(View.GONE);
//                    Toast.makeText(getApplicationContext(), "Please enter valid phone number", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//            @Override
//            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//                //super.onCodeSent(s, forceResendingToken);
//                numberScreenVisible = false;
//                Log.d("phoneAUth", "code" + phoneNumber);
//                mVerificationID = s;
//                mResendToken = forceResendingToken;
//                relativeLayoutProgressBar.setVisibility(View.GONE);
//                numberScreen.setVisibility(View.GONE);
//                otpScreen.setVisibility(View.VISIBLE);
//
//
//            }
//        };

        buttonSubmit.setOnClickListener(this);
        buttonVerify.setOnClickListener(this);
        otpResendTextView.setOnClickListener(this);


        // feedback exit layout buttons
        // calling   getFeedback on button click passing button texts and edit

        // changing  exitFeedbackQuit(layout) visibility


        option1 = findViewById(R.id.exitFeedbackOption1);
        option2 = findViewById(R.id.exitFeedbackOption2);
        option3 = findViewById(R.id.exitFeedbackOption3);
        feedbackEditText = findViewById(R.id.exitFeedbackEditText);

        option1.setOnClickListener(this);
        option2.setOnClickListener(this);
        option3.setOnClickListener(this);
        feedbackEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    feedbackEditText.setHint("Other Enter Here");
                } else {
                    feedbackEditText.setHint("");
                }
            }
        });
        feedbackEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    getFeedback(feedbackEditText.getText().toString());
                }
                return false;
            }
        });


    }

//    private Task<String> checkPhoneNumber() {
//        // Create the arguments to the callable function.
////        FirebaseFunctions mFunctions;
////        mFunctions = FirebaseFunctions.getInstance();
////        Map<String, Object> data = new HashMap<>();
////        data.put("phoneNumber", this.phoneNumber);
////        return mFunctions
////                .getHttpsCallable("checkPhoneNumber")
////                .call(data)
////                .continueWith(new Continuation<HttpsCallableResult, String>() {
////                    @Override
////                    public String then(@NonNull Task<HttpsCallableResult> task) throws Exception {
////                        String result = (String) task.getResult().getData();
////                        Log.d("VerifySignup", "checkPhoneNumber " + result);
////                        return result;
////                    }
////                });
//    }

//    private Task<String> verifyMobilinkCodeAPI() {
//        // Create the arguments to the callable function.
//        String textCode = editTextCode.getText().toString();
//        FirebaseFunctions mFunctions;
//        mFunctions = FirebaseFunctions.getInstance();
//        Map<String, Object> data = new HashMap<>();
//        data.put("phoneNumber", this.phoneNumber);
//        data.put("code", textCode);
//        Log.d("VerifySignup", "phone: " + this.phoneNumber + " code: " + textCode);
//        return mFunctions
//                .getHttpsCallable("verifyAuthCode")
//                .call(data)
//                .continueWith(new Continuation<HttpsCallableResult, String>() {
//                    @Override
//                    public String then(@NonNull Task<HttpsCallableResult> task) throws Exception {
//                        String result = (String) task.getResult().getData();
//                        Log.d("VerifySignup", " - " + result);
//                        return result;
//                    }
//                });
//    }

    private void startPhoneNumberVerifiction() {
        firebaseSignup = true;
        phoneNumber = countryCodePicker.getFullNumberWithPlus();
        Log.d("phoneAUth", "start" + phoneNumber);
//        PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                phoneNumber,
//                60,
//                TimeUnit.SECONDS, this,
//                mCallbacks
//        );

    }

//    private void verifyPhoneNumberWithCode() {
//        String textCode = editTextCode.getText().toString();
//        new AppAnalytics(getApplicationContext()).SignupOTPEntered();
//        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationID, textCode);
//        Intent unityIntent = getIntent();
//        boolean signinFromUnityCalled = unityIntent.getBooleanExtra("signinFromUnity", false);//for users upto 151
//        if (signinFromUnityCalled) {
//            CheckIfPhoneNumberExists(credential);
//        }else{
//            siginWithPhoneCredential(credential);
//        }
//        //CheckIfPhoneNumberExists(credential); uncomment to bring signup back on d1
//        //siginWithPhoneCredential(credential);
//        //linkPhoneNumberWithAnonymous(credential);
//    }

//    void CheckIfPhoneNumberExists(PhoneAuthCredential phoneAuthCredential) {
//        checkPhoneNumber().addOnCompleteListener(new OnCompleteListener<String>() {
//            @Override
//            public void onComplete(@NonNull Task<String> task) {
//                if (!task.isSuccessful()) {
//                    startPhoneNumberVerifiction(); //failed local Auth so called firebase authentication
//                } else {//success
//                    String result = task.getResult();
//                    switch (result) {
//                        case "exists": //user is already registered with firebase phoneauth
//                            siginWithPhoneCredential(phoneAuthCredential); //firebase phoneauth call
//                            break;
//                        case "not exists":// user is not registered with firebase phoneauth
//                            linkPhoneNumberWithAnonymous(phoneAuthCredential);
//                            break;
//                    }
//                }
//            }
//        });
//    }
//    private void linkPhoneNumberWithAnonymous(PhoneAuthCredential phoneAuthCredential){
//        mAuth.getCurrentUser().linkWithCredential(phoneAuthCredential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            final FirebaseUser firebaseUser = mAuth.getCurrentUser();
//                            userUid = firebaseUser.getUid();
//                            FirebaseAnalytics.getInstance(getApplicationContext()).setUserId(userUid); // set user uid for firebase analytics
//                            callSuccessSignUpAnalytics("PhoneAuth", userUid);
//                            relativeLayoutProgressBar.setVisibility(View.GONE);
//                            otpScreen.setVisibility(View.GONE);
//                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                            SharedPreferences.Editor editor = preferences.edit();
//                            editor.putString("UserPhoneNumber", phoneNumber);
//                            editor.apply();
//
//                            setUserProperty();
//                            setUserDatabase();
//                        } else {
//                            Log.w("LinkAuth", "linkWithCredential:failure", task.getException());
//                            try {
//                                throw task.getException();
//                            } catch (FirebaseAuthUserCollisionException e) {
//                                Log.w("LinkAuth", "linkWithCredential: sign catch");
//                                siginWithPhoneCredential(phoneAuthCredential);
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                });
//    }

//    private void siginWithPhoneCredential(PhoneAuthCredential phoneAuthCredential) {
//        Log.d("LinkAuth", "siginWithPhoneCredential");
//        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//
//                if (task.isSuccessful()) {//
//                    final FirebaseUser firebaseUser = mAuth.getCurrentUser();
//                    userUid = firebaseUser.getUid();
//                    FirebaseAnalytics.getInstance(getApplicationContext()).setUserId(userUid); // set user uid for firebase analytics
//                    callSuccessSignUpAnalytics("PhoneAuth", userUid);
//                    relativeLayoutProgressBar.setVisibility(View.GONE);
//                    otpScreen.setVisibility(View.GONE);
//                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                    SharedPreferences.Editor editor = preferences.edit();
//                    editor.putString("UserPhoneNumber", phoneNumber);
//                    editor.apply();
//                    getLocation();
//                    //ShowUserNameLayout(); now
//                    //showFeedbackDialog(); //now
//                    setUserProperty();
//                    setUserDatabase();
//                } else {
//                    Log.d("LinkAuth", "siginWithPhoneCredential failed"+task.getException());
//                    relativeLayoutProgressBar.setVisibility(View.GONE);
//                    Toast.makeText(getApplicationContext(), "Invalid Code", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });
//    }

//    private void resendVerificationCode(String phoneNumber,
//                                        PhoneAuthProvider.ForceResendingToken token) {
//        Toast.makeText(this, "Resending Code", Toast.LENGTH_SHORT).show();
//        PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                phoneNumber,        // Phone number to verify
//                60,                 // Timeout duration
//                TimeUnit.SECONDS,   // Unit of timeout
//                this,               // Activity (for callback binding)
//                mCallbacks,         // OnVerificationStateChangedCallbacks
//                token);             // ForceResendingToken from callbacks
//    }

    void ShowUserNameLayout() {
        otpScreenVisible = false;
        numberScreenVisible = false;
        ConstraintLayout NameLayout = findViewById(R.id.name_page);
        NameLayout.setVisibility(View.VISIBLE);
        signUpEditTextName = findViewById(R.id.signUpEditTextName);
        buttonSubmitName = findViewById(R.id.buttonSubmitName);
        buttonSubmitName.setOnClickListener(this);
    }

    //called when buttonSubmitName is pressed
    void SaveUserName() {
        numberScreenVisible = false;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("userNameTaken", true);
        editor.putString("UserName", userName);
        editor.apply();
        ConstraintLayout NameLayout = findViewById(R.id.name_page);
        NameLayout.setVisibility(View.GONE);
        callSuccessSignUpAnalytics("UserName", ""); //now
        //showFeedbackDialog(); now
        phoneAuthInit(); //now

    }

    private void showFeedbackDialog() {
        otpScreenVisible = false;
        numberScreenVisible = false;
        surveryScreen = findViewById(R.id.downloadsurvey);
        surveryScreen.setVisibility(View.VISIBLE);
        final Button option1 = findViewById(R.id.feedbackOption1);
        final Button option2 =  findViewById(R.id.feedbackOption2);
        final Button option3 =  findViewById(R.id.feedbackOption3);
        final Button option4 =  findViewById(R.id.feedbackOption5);
        final EditText feedbackOption = findViewById(R.id.feedbackEditText);


        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String feedback = option1.getText().toString();
                getSurveyResponse(feedback);
            }
        });

        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String feedback = option2.getText().toString();
                getSurveyResponse(feedback);
            }
        });

        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String feedback = option3.getText().toString();
                getSurveyResponse(feedback);
            }
        });
        option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String feedback = option4.getText().toString();
                getSurveyResponse(feedback);
            }
        });

        feedbackOption.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    feedbackOption.setHint("Other Enter Here");
                } else {
                    feedbackOption.setHint("");
                }
            }
        });
        feedbackOption.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    String feedback = textView.getText().toString();
                    Log.wtf("feedback_", "edt: " + feedback);
                    getSurveyResponse(feedback);
                }
                return false;
            }
        });
    }
    /**
     * getting feedback text
     *
     * @param downloadSource (button text/ other edit text)
     */
    private void getSurveyResponse(String downloadSource) {
        new AppAnalytics(getApplicationContext()).UserDownloadSourceAnalytics(downloadSource);
        surveryScreen.setVisibility(View.GONE);
        //relativeLayoutProgressBar.setVisibility(View.VISIBLE);
        startActivity(new Intent(SignupActivity.this, UnityPlayerActivity.class));
        finish();
        //setUserProperty();
        //setUserDatabase();
    }

//    void signInAnonymously() {
//        Log.d("anonSignin","in anon signup");
//        callSignUpAnalytic("Anonymous");
//        ShowSignupProgessDialog();
//        mAuth.signInAnonymously()
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            Log.d("anonSignin","Success");
//                            Long currentDate = System.currentTimeMillis();
//                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                            SharedPreferences.Editor editor = preferences.edit();
//                            editor.putLong("AnonymousD0Signup",currentDate);
//                            editor.apply();
//                            // Sign in success, update UI with the signed-in user's information
//                            final FirebaseUser firebaseUser = mAuth.getCurrentUser();
//                            userUid = firebaseUser.getUid();
//                            Log.d("UserUID","anon: "+userUid);
//                            FirebaseAnalytics.getInstance(getApplicationContext()).setUserId(userUid); // set user uid for firebase analytics
//                            callSuccessSignUpAnalytics("Anonymous", userUid);
//                            HideSignupProgressDialog();
//                            getLocation();
//                            ShowUserNameLayout();
//                        } else {
//                            Log.d("anonSignin","Fail");
//                            // If sign in fails, display a message to the user.
//                            HideSignupProgressDialog();
//                            AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
//
//                            builder.setTitle("Error!");
//                            builder.setMessage("Please connect with internet for verification");
//                            builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    signInAnonymously();
//                                }
//                            });
//
//                            builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    finish();
//                                }
//                            });
//
//                            AlertDialog dialog = builder.create();
//                            dialog.show();
//                        }
//                    }
//                });
//    }

//    void setUserProperty() {
//        FirebaseAnalytics.getInstance(getApplicationContext()).setUserId(userUid); // set user uid for firebase analytics
//        FirebaseAnalytics mFirebaseAnalytics;
//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
//        mFirebaseAnalytics.setUserProperty("name", userName);
//        if (phoneNumber == null || phoneNumber == "") {
//            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//            phoneNumber = preferences.getString("UserPhoneNumber", "Taleemabad User");
//            preferences = null;
//        }
//        mFirebaseAnalytics.setUserProperty("phonenumber", phoneNumber);
//    }

//    void setUserDatabase() {
//        relativeLayoutProgressBar.setVisibility(View.VISIBLE);
//        final FirebaseUser currentUser = mAuth.getCurrentUser();
//        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users/" + userUid);
//        UpdateFcmOnBigQuery();
//        GetFreeUserRemoteConfigValue();
//        GetBundleExperiementRemoteConfigValue();
//        ValueEventListener valueEventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                FirebaseAnalytics.getInstance(getApplicationContext()).setUserId(currentUser.getUid());// set user uid for firebase analytics
//                if (dataSnapshot.exists()) {
//                    //user database existscheckPu
//                    mDatabase.child("fcmToken").setValue(FirebaseInstanceId.getInstance().getToken());
//                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
//                    String signinDate = simpleDateFormat.format(Calendar.getInstance().getTime());
//                    mDatabase.child("signinDate").setValue(signinDate);
//                    mDatabase.child("phoneNumber").setValue(phoneNumber);
//                    //mDatabase.child("userName").setValue(userName);
//                    relativeLayoutProgressBar.setVisibility(View.GONE);
//
//                    checkPurchase(userUid);
//
//                } else {
//                    //user database doesn't exists
//                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
//                    String accountCreateTime = simpleDateFormat.format(Calendar.getInstance().getTime());
//                    if (phoneNumber == null || phoneNumber == "") {
//                        phoneNumber = preferences.getString("UserPhoneNumber", "Taleemabad User");
//                    }
//                    if (userName == null || userName == "") {
//                        userName = preferences.getString("UserName", "Taleemabad User");
//                    }
//                    AnonymousUser anonymousUser = new AnonymousUser(FirebaseInstanceId.getInstance().getToken(), accountCreateTime, accountCreateTime, phoneNumber, userName);
//                    mDatabase.setValue(anonymousUser);
//                    //setFacebookUserProperties();
//                    relativeLayoutProgressBar.setVisibility(View.GONE);
//                    showFeedbackDialog();
//                    //startActivity(new Intent(SignupActivity.this, UnityPlayerActivity.class)); now
//                    //finish(); now
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


    public void fullScreen() {
        final View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        decorView.setOnSystemUiVisibilityChangeListener
                (new View.OnSystemUiVisibilityChangeListener() {
                    @Override
                    public void onSystemUiVisibilityChange(int visibility) {
                        // Note that system bars will only be "visible" if none of the
                        // LOW_PROFILE, HIDE_NAVIGATION, or FULLSCREEN flags are set.
                        if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                            // TODO: The system bars are visible. Make any desired
//                                int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
//
//                            int a=SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
//                                decorView.setSystemUiVisibility(a);
                            decorView.setSystemUiVisibility(
                                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                                            | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);


                        } else {
                            // TODO: The system bars are NOT visible. Make any desired

                        }
                    }
                });
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

    }

    void ShowSignupProgessDialog() {
        pd = new ProgressDialog(SignupActivity.this);
        pd.setMessage("Please wait while authenticating");
        pd.setCancelable(false);
        pd.show();
    }

    void HideSignupProgressDialog() {
        if (pd.isShowing()) {
            pd.dismiss();
        }
    }

    public void logInFail(String errorMessage) {
        if (pd != null) {
            if (pd.isShowing()) {
                pd.dismiss();
            }
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);

        builder.setTitle("Error!");
        builder.setMessage(errorMessage);
        builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

//    void UpdateFcmOnBigQuery(){
//        String fcmToken = FirebaseInstanceId.getInstance().getToken();
//        Log.d("fcmToken",": "+fcmToken);
//        UpdateFcm(fcmToken).addOnCompleteListener(new OnCompleteListener<String>() {
//            @Override
//            public void onComplete(@NonNull Task<String> task) {
//
//                if (!task.isSuccessful()) {
//
//                }else{
//
//                }
//            }
//        });
//    }
//    private Task<String> UpdateFcm(String fcmToken) {
//        // Create the arguments to the callable function.
//        FirebaseFunctions mFunctions = FirebaseFunctions.getInstance();
//        Map<String, Object> data = new HashMap<>();
//        data.put("fcm", fcmToken);
//        return mFunctions
//                .getHttpsCallable("fcmUpdate")
//                .call(data)
//                .continueWith(new Continuation<HttpsCallableResult, String>() {
//                    @Override
//                    public String then(@NonNull Task<HttpsCallableResult> task) throws Exception {
//
//                        //Object result1 = task.getResult().getData();
//                        //HashMap<String,Object> hashMap= (HashMap<String, Object>) task.getResult().getData();
//                        String result = (String) task.getResult().getData();
//
//                        return result;
//                    }
//                });
//    }
//
//    void GetFreeUserRemoteConfigValue(){
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        SharedPreferences.Editor editor = preferences.edit();
//        FirebaseAnalytics mFirebaseAnalytics;
//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
//        Log.d("GetFreeUserRemoteConfig","GetFreeUserRemoteConfigValue: ");
//        final FirebaseRemoteConfig firebaseRemoteConfig= FirebaseRemoteConfig.getInstance();
//        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
//                .setMinimumFetchIntervalInSeconds(60)
//                .build();
//        firebaseRemoteConfig.setConfigSettingsAsync(configSettings);
//        firebaseRemoteConfig.setDefaultsAsync(R.xml.free_user_remote_config);
//        firebaseRemoteConfig.fetchAndActivate()
//                .addOnCompleteListener(this, new OnCompleteListener<Boolean>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Boolean> task) {
//                        String freeUser= "0"; //default value yes
//                        if (task.isSuccessful()) {
//                            boolean updated = task.getResult();
//                            freeUser =firebaseRemoteConfig.getString("free_app_user");
//                            if(freeUser.equals("1")){
//                                mFirebaseAnalytics.setUserProperty("free_app_user", "true");
//                                editor.putBoolean("free_app_user",true);
//                                editor.apply();
//                            }else{
//                                mFirebaseAnalytics.setUserProperty("free_app_user", "false");
//                                editor.putBoolean("free_app_user",false);
//                                editor.apply();
//                            }
//                            Log.d("GetFreeUserRemoteConfig","success val: "+freeUser);
//                        } else {
//                            mFirebaseAnalytics.setUserProperty("free_app_user", "false");
//                            editor.putBoolean("free_app_user",false);
//                            editor.apply();
//                            Log.d("GetFreeUserRemoteConfig","fail val: "+freeUser);
//                        }
//
//                    }
//                });
//    }
//
//    void GetBundleExperiementRemoteConfigValue(){
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        SharedPreferences.Editor editor = preferences.edit();
//        Log.d("GetBundleExperiement","GetFreeUserRemoteConfigValue: ");
//        final FirebaseRemoteConfig firebaseRemoteConfig= FirebaseRemoteConfig.getInstance();
//        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
//                .setMinimumFetchIntervalInSeconds(60)
//                .build();
//        firebaseRemoteConfig.setConfigSettingsAsync(configSettings);
//        firebaseRemoteConfig.setDefaultsAsync(R.xml.bundle_experiment_remote_config);
//        firebaseRemoteConfig.fetchAndActivate()
//                .addOnCompleteListener(this, new OnCompleteListener<Boolean>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Boolean> task) {
//                        String bundleDurationUserType= "0"; //default value yes
//                        if (task.isSuccessful()) {
//                            boolean updated = task.getResult();
//                            bundleDurationUserType =firebaseRemoteConfig.getString("bundle_duration_experiment");
//                            if(bundleDurationUserType.equals("0")){
//                                editor.putString("bundleDurationUserType",bundleDurationUserType);
//                                editor.apply();
//                            }else{
//                                editor.putString("bundleDurationUserType",bundleDurationUserType);
//                                editor.apply();
//                            }
//                            Log.d("GetFreeUserRemoteConfig","success val: "+bundleDurationUserType);
//                        } else {
//                            editor.putString("bundleDurationUserType",bundleDurationUserType);
//                            editor.apply();
//                        }
//
//                    }
//                });
//    }
//    //function to check if app is purchased,
//    void checkPurchase(String userUid) {
//        relativeLayoutProgressBar.setVisibility(View.VISIBLE);
//        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users/" + userUid + "/Purchase");
//        ValueEventListener valueEventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                        DataSnapshot dataSnapshot1 = dataSnapshot.child(snapshot.getKey());
//                        String LatestBundleActivated = String.valueOf(dataSnapshot1.getChildrenCount());
//                        if (snapshot.child(LatestBundleActivated).child("status").getValue().equals("true")) {
//                            Long activationDate = Long.parseLong(snapshot.child(LatestBundleActivated).child("activationDate").getValue().toString());
//                            Long expiryDate = Long.parseLong(snapshot.child(LatestBundleActivated).child("expiryDate").getValue().toString());
//                            Long currentDate = System.currentTimeMillis() / 1000;
//                            String bundle = snapshot.child(LatestBundleActivated).child("bundle").getValue().toString();
//                            if (currentDate < expiryDate) {
//                                SharedPreferences packagePrefs = getApplicationContext().getSharedPreferences(getApplicationContext().getPackageName() + ".v2.playerprefs", Context.MODE_PRIVATE);
//                                SharedPreferences.Editor editor = packagePrefs.edit();
//                                editor.putInt("SubscribedBundle" + bundle, 1);
//                                editor.putString(bundle + "SubscriptionActivationDate", String.valueOf(activationDate));
//                                editor.putString(bundle + "SubscriptionExpiryDate", String.valueOf(expiryDate));
//                                //to finish trial
//                                editor.putInt("AllContentUnlocked", 0);
//                                editor.putInt("FourteenDayTrialGiven", 2);
//                                editor.putInt("DailyTrialEnabled", 0);
//                                editor.apply();
//
//                            } else {
//                                mDatabase.child(snapshot.getKey()).child(LatestBundleActivated).child("status").setValue("false");
//                            }
//                        }
//                    }
//                    relativeLayoutProgressBar.setVisibility(View.GONE);
//                    showFeedbackDialog(); //now
//                    //startActivity(new Intent(SignupActivity.this, UnityPlayerActivity.class)); npw
//                    //finish(); now
//                } else {
//                    relativeLayoutProgressBar.setVisibility(View.GONE);
//                    showFeedbackDialog(); //now
//                    //startActivity(new Intent(SignupActivity.this, UnityPlayerActivity.class)); now
//                    //finish(); now
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                relativeLayoutProgressBar.setVisibility(View.GONE);
//                showFeedbackDialog(); //now
//                //startActivity(new Intent(SignupActivity.this, UnityPlayerActivity.class)); now
//                //finish(); now
//            }
//        };
//        mDatabase.addListenerForSingleValueEvent(valueEventListener);
//    }
//

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        appStartTime = Calendar.getInstance().getTime();
//        FirebaseAnalytics mFirebaseAnalytics;
//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
//        mFirebaseAnalytics.setUserProperty("lastOpen", appStartTime.toString());
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(restartUnity){
            UnityPlayer.UnitySendMessage("ScriptHandler","RestartUnity","True");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 11: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                break;
            }

            case REQUEST_CODE_ASK_PERMISSIONS_1:
            case REQUEST_CODE_ASK_PERMISSIONS_2:
            case REQUEST_CODE_ASK_PERMISSIONS_3: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++) {
                        if (permissions[i].equals(Manifest.permission.ACCESS_FINE_LOCATION) && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                            Log.d("permission", "Matched: " + i);
                            locationServiceCheck();
                        }
                    }
                }
                break;
            }
        }
    }

    private void takePermission() {
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_SETTINGS) != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_SETTINGS);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray
                    (new String[listPermissionsNeeded.size()]), 11);
        }


    }
    @Override
    public void onBackPressed() {
        SignupAnalytic obj = new SignupAnalytic(null, getApplicationContext());
        obj.setSignupBackAnalytics();

        if (otpScreenVisible && relativeLayoutProgressBar != null && relativeLayoutProgressBar.getVisibility()!=View.VISIBLE) {
            Log.wtf("back_", "otp screen back: ");
            otpScreen.setVisibility(View.GONE);
            feedbackScreen.setVisibility(View.VISIBLE);
        } else if (numberScreenVisible && relativeLayoutProgressBar != null && relativeLayoutProgressBar.getVisibility()!=View.VISIBLE) {
            Log.wtf("back_", "number screen back: ");
            numberScreen.setVisibility(View.GONE);
            feedbackScreen.setVisibility(View.VISIBLE);
        } else if(relativeLayoutProgressBar != null && relativeLayoutProgressBar.getVisibility()==View.VISIBLE){

        }else{
            super.onBackPressed();
        }

    }


    public void callSignUpAnalytic(final String method) {
        SignupAnalytic obj = new SignupAnalytic(method, getApplicationContext());
        obj.setSignupAnalytics();
    }

    public void callSuccessSignUpAnalytics(final String method, String UID) {
        SignupAnalytic obj = new SignupAnalytic(method, getApplicationContext());
        obj.setSuccessSignupAnalytics(phoneNumber, UID);
    }

    /*void setFacebookUserProperties() {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        String userId = firebaseUser.getUid();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        String accountCreateTime = simpleDateFormat.format(Calendar.getInstance().getTime());
        AppEventsLogger.setUserID(userId);

        Bundle user_props = new Bundle();
        user_props.putString("$account_created_time", accountCreateTime);
        user_props.putString("userStatus", "newUser");
        AppEventsLogger.updateUserProperties(user_props, new GraphRequest.Callback() {
            @Override
            public void onCompleted(GraphResponse response) {
                Log.d("facebookUserProperty", "completed: " + response);
            }
        });

    }*/

    //if Pakistan number call firebase checkPhoneNumber function
    //else start auth with firebase
    /*void CheckPhoneNumberCountryCode() {
        phoneNumber = countryCodePicker.getFullNumberWithPlus();
        if (countryCodePicker.getSelectedCountryCode().equals("92")) {
            CheckUfoneNumberAndTime();
        } else {
            startPhoneNumberVerifiction();
        }

    }*/

    //if time is greater than 8pm and less than 8am and number is of Ufone auth with firebase else with Mobilink
    /*private void CheckUfoneNumberAndTime() {
        SimpleDateFormat formatHours = new SimpleDateFormat("HH");
        String getHours = formatHours.format(new Date());

        if (Integer.parseInt(getHours) >= 18 || Integer.parseInt(getHours) < 8) {
            String UserPhoneNumber = countryCodePicker.getFullNumber();
            String SimOperatorCode = UserPhoneNumber.substring(2, 5);
            switch (SimOperatorCode) {
                case "330":
                case "331":
                case "332":
                case "333":
                case "334":
                case "335":
                case "336":
                case "337":
                    Log.d("VerifySignup", "Ufone Number");
                    startPhoneNumberVerifiction();
                    break;
                default:
                    Log.d("VerifySignup", "Default");
                    CallMobilinkPhoneAuth();
            }

        } else {
            Log.d("VerifySignup", "Time is fine");
            CallMobilinkPhoneAuth();
        }


    }*/

    /*void CallMobilinkPhoneAuth() {
        checkPhoneNumber().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {

                if (!task.isSuccessful()) {
                    startPhoneNumberVerifiction(); //failed local Auth so called firebase authentication
                } else {//success
                    String result = task.getResult();
                    switch (result) {
                        case "exists": //user is already registered with firebase phoneauth
                            Log.d("phoneAUth", "!! exists");
                            startPhoneNumberVerifiction(); //firebase phoneauth call
                            break;
                        case "code-sent":// user is not registered with firebase phoneauth and code is sent from mobilink
                            Log.d("phoneAUth", "!! code-sent");
                            relativeLayoutProgressBar.setVisibility(View.GONE);
                            numberScreen.setVisibility(View.GONE);
                            otpScreen.setVisibility(View.VISIBLE);
                            break;
                        case "login-with-firebase": // user is not registered with firebase phoneauth and code is not sent from mobilink
                            Log.d("phoneAUth", "!! login-with-firebase");
                            startPhoneNumberVerifiction(); //firebase phoneauth call
                            break;
                    }
                }
            }
        });
    }*/

    /*void CheckCodeVerificationMethod() {
        if (firebaseSignup) {
            verifyPhoneNumberWithCode();
        } else {
            verifyMobilinkAPICode();
        }
    }*/

    /*private void verifyMobilinkAPICode() {
        verifyMobilinkCodeAPI().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {

                if (!task.isSuccessful()) {
                    startPhoneNumberVerifiction(); //failed local Auth so called firebase authentication
                } else {//success
                    String result = task.getResult();
                    switch (result) {
                        case "valid-code": //user is already registered with firebase phoneauth
                            SignUpWithEmailPassword();
                            Log.d("VerifySignup", "!! valid code");
                            break;
                        case "invalid-code":// user is not registered with firebase phoneauth and code is sent from mobilink
                            Toast.makeText(getApplicationContext(), "Invalid Code", Toast.LENGTH_SHORT).show();
                            Log.d("VerifySignup", "!! invalid code");
                            break;
                        case "code-expired": // user is not registered with firebase phoneauth and code is not sent from mobilink
                            Toast.makeText(getApplicationContext(), "Code Expired, Please try again", Toast.LENGTH_SHORT).show();
                            Log.d("VerifySignup", "!! code-expired");
                            break;
                        case "error":
                            Log.d("VerifySignup", "!! error");
                            break;
                    }
                }
            }
        });
    }*/

    /*private void SignUpWithEmailPassword() {
        mAuth.createUserWithEmailAndPassword(phoneNumber + "@taleemabad.com", phoneNumber + "taleemabad")
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("phoneAUth", "signInWithEmail:success");
                            final FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            userUid = firebaseUser.getUid();
                            FirebaseAnalytics.getInstance(getApplicationContext()).setUserId(userUid); // set user uid for firebase analytics
                            callSuccessSignUpAnalytics("MobilinkPhoneAuth", userUid);
                            relativeLayoutProgressBar.setVisibility(View.GONE);
                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("UserPhoneNumber", phoneNumber);
                            editor.apply();
                            ShowUserNameLayout();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("phoneAUth", "signInWithEmail:failure", task.getException());
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthUserCollisionException e) {
                                LogInWithEmailPassword();
                            } catch (Exception e) {

                            }
                            // ...
                        }

                        // ...
                    }
                });
    } */

    /*private void LogInWithEmailPassword() {
        mAuth.signInWithEmailAndPassword(phoneNumber + "@taleemabad.com", phoneNumber + "taleemabad")
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("phoneAUth", "signInWithEmail:success");
                            final FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            userUid = firebaseUser.getUid();
                            FirebaseAnalytics.getInstance(getApplicationContext()).setUserId(userUid); // set user uid for firebase analytics
                            callSuccessSignUpAnalytics("MobilinkPhoneAuth", userUid);
                            relativeLayoutProgressBar.setVisibility(View.GONE);
                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("UserPhoneNumber", phoneNumber);
                            editor.apply();
                            ShowUserNameLayout();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("phoneAUth", "signInWithEmail:failure", task.getException());

                        }

                        // ...
                    }
                });
    }*/

    /**
     * function to call when Resend code is clicked
     * if auth is from firebase resend firebase code
     * if auth is from mobilink call Firebase phone auth
     */
//    private void ResendVerificationCodeButton() {
//        if (firebaseSignup) {
//            resendVerificationCode(phoneNumber, mResendToken);
//        } else {
//            startPhoneNumberVerifiction();
//        }
//    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signUpButtonSubmit:
                if (!editTextNumber.getText().toString().equals("")) {
                    relativeLayoutProgressBar.setVisibility(View.VISIBLE);
                    otpScreenVisible = true;
                    new AppAnalytics(getApplicationContext()).SignupNumberEntered(editTextNumber.getText().toString());
                    //CheckPhoneNumberCountryCode();
                    startPhoneNumberVerifiction();
                } else {
                    Toast.makeText(this, "Please Enter Valid Phone Number", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.signUpButtonVerify:
                relativeLayoutProgressBar.setVisibility(View.VISIBLE);
                String textCode = editTextCode.getText().toString();
                if (textCode != null && !textCode.isEmpty() && !textCode.equals("")) {
                    // verifyPhoneNumberWithCode();
                    //CheckCodeVerificationMethod();
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter code", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.otpResendTextView:
                new AppAnalytics(getApplicationContext()).SignupResendOTPButtonClicked();
               // resendVerificationCode(phoneNumber, mResendToken);
                //ResendVerificationCodeButton();
                break;
            case R.id.buttonSubmitName:
                if (!(signUpEditTextName.getText().toString().isEmpty())) {
                    userName = signUpEditTextName.getText().toString();
                    buttonSubmitName.setClickable(false);
                    SaveUserName();

                } else {
                    Toast.makeText(this, "Please Enter Name", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.exitFeedbackOption1:
                getFeedback(option1.getText().toString());
                break;
            case R.id.exitFeedbackOption2:
                getFeedback(option2.getText().toString());
                break;
            case R.id.exitFeedbackOption3:
                getFeedback(option3.getText().toString());
                break;

        }
    }

   /* void updateFacebookUserProperties(){
        Bundle user_props = new Bundle();
        user_props.putString("userStatus","oldUser");
        AppEventsLogger.updateUserProperties(user_props, new GraphRequest.Callback() {
            @Override
            public void onCompleted(GraphResponse response) {
            }
        });
    }*/

    /**
     * getting feedback text and exiting app
     *
     * @param feedbackText (button text/ other edit text)
     */
    private void getFeedback(String feedbackText) {
        Log.wtf("feedback_", "Feedback String : " + feedbackText);
        // add user property or event here
        new AppAnalytics(getApplicationContext()).NotSigningInEvent(feedbackText);

        finishAffinity();
    }

    private void getLocation(){
        startLocationAlgorithm();
    }
    private void startLocationAlgorithm () {
       /*
        if (!locationPermissionsGranted()) { //Permissions not granted
            askForLocationPermissions(REQUEST_CODE_ASK_PERMISSIONS_1);
        }
        else {
            locationServiceCheck();
        }
        */
    }
    private void locationServiceCheck () {
  /*
        final LocationRequest locationRequest = createLocationRequest();

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess (LocationSettingsResponse locationSettingsResponse) {
                startLocationUpdates(locationRequest);
            }
        });

        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure (@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
//                    getLastKnownLocation();
                    //  try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    //ResolvableApiException resolvable = (ResolvableApiException) e;
                    //resolvable.startResolutionForResult(SignupActivity.this,
                    //        REQUEST_CHECK_SETTINGS);
                    //  } catch (IntentSender.SendIntentException sendEx) {
                    // Ignore the error.
                    //  }
                }
            }
        });
 */
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (resultCode == Activity.RESULT_OK) {
                locationServiceCheck();
            }
            else {
                //makeToast("GPS not enabled.", Toast.LENGTH_SHORT);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /*
    private void startLocationUpdates (LocationRequest locationRequest) {

        locationCallback = createLocationCallback();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            askForLocationPermissions(REQUEST_CODE_ASK_PERMISSIONS_2);
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());

    }

    private LocationCallback createLocationCallback () {
        final LocationCallback lc = new LocationCallback() {
            @Override
            public void onLocationResult (LocationResult locationResult) {
                if (locationResult == null) {
                    getLastKnownLocation();
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    Log.d("Location","Longi : "+location.getLongitude()+ " Latti : "+location.getLatitude());
                    new AppAnalytics(getApplicationContext()).user_location(location.getLongitude(),location.getLatitude(),"CurrentLocation");
                    //updateLocationOnUI(location);
                    //fusedLocationProviderClient.removeLocationUpdates(locationCallback);
                }
            }
        };

        return lc;
    }*/
    /*
    private LocationRequest createLocationRequest () {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2500);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }
    */
    private void getLastKnownLocation () {
        Location gpsLocation;
        Location networkLocation = null;
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                askForLocationPermissions(REQUEST_CODE_ASK_PERMISSIONS_2);
                return;
            }
            gpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            networkLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (gpsLocation != null) {
                Log.d("Location","Longi : "+gpsLocation.getLongitude()+ " Latti : "+gpsLocation.getLatitude());
                new AppAnalytics(getApplicationContext()).user_location(gpsLocation.getLongitude(),gpsLocation.getLatitude(),"LastKnowLocation");
                //updateLocationOnUI(gpsLocation);
            }
            else if (networkLocation != null) {
                Log.d("Location","Longi : "+networkLocation.getLongitude()+ " Latti : "+networkLocation.getLatitude());
                new AppAnalytics(getApplicationContext()).user_location(networkLocation.getLongitude(),networkLocation.getLatitude(),"NetworkLocation");
                //pdateLocationOnUI(networkLocation);
            }
            else {
                //makeToast("Couldn't get your location.", Toast.LENGTH_SHORT);
            }

        } catch (Exception e) {
            e.printStackTrace();
            //makeToast("Something went wrong.", Toast.LENGTH_SHORT);
        }
    }

    private boolean locationPermissionsGranted () {
        return (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                &&
                ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                &&
                ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED);
    }
    private void askForLocationPermissions (int requestCode) {
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_SETTINGS) != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_SETTINGS);
        }


        listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        listPermissionsNeeded.add(Manifest.permission.ACCESS_NETWORK_STATE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray
                        (new String[listPermissionsNeeded.size()]), requestCode);
            }
//            requestPermissions(new String[]{
//                    Manifest.permission.ACCESS_FINE_LOCATION,
//                    Manifest.permission.ACCESS_COARSE_LOCATION,
//                    Manifest.permission.ACCESS_NETWORK_STATE
//            }, requestCode);
        }
    }

}
