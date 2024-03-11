package com.orenda.taimo.grade3tograde5;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.content.BroadcastReceiver;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.webkit.MimeTypeMap;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

//import com.amazonaws.mobile.client.AWSMobileClient;
//import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
//import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
//import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
//import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
//import com.amazonaws.services.s3.AmazonS3Client;
import com.devbrackets.android.exomedia.listener.OnCompletionListener;
import com.devbrackets.android.exomedia.listener.OnErrorListener;
import com.devbrackets.android.exomedia.listener.OnPreparedListener;
import com.devbrackets.android.exomedia.ui.widget.VideoView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.unity3d.player.UnityPlayer;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import firebase.analytics.AppAnalytics;
import firebase.classes.VideoData;


public class VideoActivity extends AppCompatActivity implements  View.OnClickListener  {
    public static String VIDEO_ID;
    private BroadcastReceiver mMessageReceiver, mProgressMessageReceiver;
    private DownloadForegroundService downloadForegroundService;
    private Intent downloadServiceIntent;

    private SeekBar mSeekBar;
    String Alpha;//variable to store current alphabet session
    //ImageView alphaImage;
    ConstraintLayout linearLayoutMain, linearLayoutProblem;
    ImageButton playbtn, homeBtn, satpinBtn, downloadBtn,toggleEnglishBtn, toggleUrduBtn,problemBtn,shareBtn;
    int unityvar; //to call correct unity method
    String Grade="";
    String Subject="";
    int ContentUnlocked; //0=locked 1=unlocked
    int PurchaseStatus; //0=locked 1=unlocked
    boolean IsFree;
    //private PlayerWebView mVideoView;
    private Handler mHandler = null;
    private Handler seekHandler=null;
    VideoView videoView;
    String video_url = "";//http://df0owiy4mrnj1.cloudfront.net/custom400lessonS.m3u8";
    LinearLayout linearLayoutTop;
    public boolean isVideoDownloading=false,videoIsDownloaded=false;
    public String videoDownloading="";
    String startTime,finishTime, status;
    String language=null;
    int play_pause_counter=0;
    long seekStartTime,seekEndTime; // variable to store video seek time, start and end respectively
    int SecondsConsumed = 0; // variable to add second also for 7 minutes trial
    int freeTrialSecondsLeft =0; // check from unity pref free seconds
    int dailyTrialEnabled = 0; //if daily trial is enabled
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_video);
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        fullScreen();
        //alphaImage=findViewById(R.id.alphaImg);
        linearLayoutMain=findViewById(R.id.videoActivityToplayout);
        linearLayoutProblem=findViewById(R.id.videoActivityProblemLayout);
        playbtn=findViewById(R.id.playBtn);
        homeBtn=findViewById(R.id.btnHome);
        satpinBtn=findViewById(R.id.btnSatpin);
        downloadBtn=findViewById(R.id.btnDownload);
        toggleEnglishBtn=findViewById(R.id.toggleEng);
        toggleUrduBtn=findViewById(R.id.toggleUrdu);
        mSeekBar = (SeekBar) findViewById(R.id.video_seekbar);
        problemBtn = findViewById(R.id.problemBtn);
        shareBtn = findViewById(R.id.btnShare);
        SetOnClickListners();

        mHandler = new Handler();
        seekHandler=new Handler();

        Intent intent = getIntent();
        Alpha=intent.getStringExtra("Alpha");
        Grade=intent.getStringExtra("GradeSelected");
        Subject=intent.getStringExtra("SubjectSelected");
        IsFree=intent.getBooleanExtra("IsFree",false);
        ContentUnlocked = intent.getIntExtra("ContentUnlocked",0);
        PurchaseStatus = intent.getIntExtra("PurchaseStatus",0);
        freeTrialSecondsLeft = intent.getIntExtra("freeTrialSecondsLeft", 0);
        dailyTrialEnabled = intent.getIntExtra("DailyTrialEnabled",0);
        if(getIntent()!=null && getIntent().hasExtra("NotificationId")){
            new AppAnalytics(getApplicationContext()).CustomNotificationOpen(intent.getIntExtra("NotificationId",0),
                    intent.getStringExtra("NotificationTime"),intent.getStringExtra("NotificationType"));
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.cancel(getIntent().getIntExtra("NotificationId",0)); //closes notification
        }
        setUserPreferenceLanguage();
        checkDownloadVideo(Alpha);
        //SetBackgroundImage();
        SetVideoId();
        //SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        //boolean isFreeUser = preferences.getBoolean("free_app_user", false);

    }


    void SetOnClickListners(){
        playbtn.setOnClickListener(this);
        homeBtn.setOnClickListener(this);
        satpinBtn.setOnClickListener(this);
        downloadBtn.setOnClickListener(this);
        linearLayoutMain.setOnClickListener(this);
        toggleEnglishBtn.setOnClickListener(this);
        toggleUrduBtn.setOnClickListener(this);
        mSeekBar.setOnSeekBarChangeListener(mVideoSeekBarChangeListener);
        problemBtn.setOnClickListener(this);
        shareBtn.setOnClickListener(this);
    }

    private void initiateBroadcastReceiver (String fileName) {
        mMessageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // Get extra data included in the Intent
                boolean downloaded = intent.getBooleanExtra("status", false);
                if(downloaded){
                    Toast.makeText(getApplicationContext(),"Download Complete",Toast.LENGTH_SHORT).show();
                    isVideoDownloading=false;
                    if(videoDownloading.equals(Alpha)){
                        downloadBtn.setImageResource(R.drawable.download_complete_btn);
                    }
                }else{
                    isVideoDownloading=false;
                }
                //TODO:: check if downloaded or not and then perform the task that you want to
                //Toast.makeText(context, ""+downloaded, Toast.LENGTH_SHORT).show();
            }
        };
    
        LocalBroadcastManager.getInstance(this).registerReceiver(
                mMessageReceiver, new IntentFilter(fileName));
    }

    private void initiateProgressBroadcastReceiver (String fileName) {

        mProgressMessageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // Get extra data included in the Intent
                int downloaded = intent.getIntExtra("progress", 0);
                Log.wtf("VideoActivity", "Received Progress: "+downloaded);
                //TODO:: progress received, update the download progress on UI

            }

        };
        LocalBroadcastManager.getInstance(this).registerReceiver(
                mProgressMessageReceiver, new IntentFilter(fileName));

    }




    private void setUserPreferenceLanguage(){
        if(Grade.equals("Nursery") && (Subject.equals("English") || Subject.equals("Math"))){
            SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
            language=preferences.getString("videoLanguage","Urdu");
            if(language.equals("English")){
                toggleEnglishBtn.setImageResource(R.drawable.toggle_eng_on);
                toggleUrduBtn.setImageResource(R.drawable.toggle_urdu_off);
            }else if(language.equals("Urdu")){
                toggleEnglishBtn.setImageResource(R.drawable.toggle_eng_off);
                toggleUrduBtn.setImageResource(R.drawable.toggle_urdu_on);
            }
        }else{
            ConstraintLayout ToggleLanguageLayout = findViewById(R.id.languagetogglelayout);
            ToggleLanguageLayout.setVisibility(View.GONE);
            ToggleLanguageLayout = null;
            language = "Urdu";
        }

    }

    private void SetVideoId(){
        if(videoIsDownloaded){
            setOfflineVideoId(Grade+Subject+"UrduLanguage"+Alpha);
        }else{
        	//AWS
           // video_url = "http://d3lt10t4pogyn.cloudfront.net/"+Grade+"/"+Subject+"/"+language+"Language/"+Alpha+".m3u8";
           //RAPID VM//
           video_url = "http://175.107.197.227/Movavi%20Library/"+Grade+"/"+Subject+"/"+language+"Language/"+Alpha+".mp4";
           //RAPID object storage
            //video_url = "https://rcns_taleemabad.s3.rapidcompute.com/taleemabad/VideosRepository/"+Grade+"/"+Subject+"/"+language+"Language/"+Alpha+".mp4";
            Log.d("videoUrl: ",video_url);
        }
        setupVideoView();
    }

    private void SetBackgroundImage(){
        int id = getResources().getIdentifier("alpha_"+Grade.toLowerCase()+"_"+Subject.toLowerCase()+"_"+Alpha.toLowerCase(), "drawable", getPackageName());
        //alphaImage.setImageResource(id);
    }


    private void setOfflineVideoId(String alpha){
        File file=new File(getFilesDir(),alpha);
        file.setReadable(true);
        Uri uri=Uri.fromFile(file);
        video_url=uri.toString();
    }

    //function to check video is downloaded or not
    //then set download button accordingly
    public void checkDownloadVideo(String alpha){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isDownloaded = preferences.getBoolean(Grade+Subject+"UrduLanguage"+alpha+"Video", false);
        if(isDownloaded){
            videoIsDownloaded=true;
            downloadBtn.setImageResource(R.drawable.download_complete_btn);
        }else{
            videoIsDownloaded=false;
            downloadBtn.setImageResource(R.drawable.download_btn);
        }
    }

    private void setupVideoView() {
       // Date currentTime=Calendar.getInstance().getTime();
        play_pause_counter=0; //reset play pause counter to 0
        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("MM/dd/yyyy kk:mm:ss", Locale.ENGLISH);
        startTime = simpleDateFormat.format(Calendar.getInstance().getTime());
        // Make sure to use the correct VideoView import
        videoView = (VideoView)findViewById(R.id.dm_player_web_view);
        videoView.setOnClickListener(this);
        videoView.setOnPreparedListener(new OnPreparedListener() {
            @Override
            public void onPrepared() {

                setVideoStartAnalytics(); //call function to set analytics
                //seekBarUpdate();
                mSeekBar.setMax((int) videoView.getDuration());
                videoView.start();
                playbtn.setImageResource(R.drawable.green_pause_button);
                seekHandler.postDelayed(runnableSeek, 100);

            }
        });
        videoView.setOnErrorListener(new OnErrorListener() {
            @Override
            public boolean onError(Exception e) {
                Log.d("videoUrl",""+e.getMessage());
                Log.d("videoUrl",""+e);
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(VideoActivity.this);

                builder.setTitle("Error!");
                builder.setMessage("Video is not available at the moment");
                builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(VideoActivity.this,UnityPlayerActivity.class));
                        finish();
                    }
                });

                android.app.AlertDialog dialog = builder.create();
                dialog.show();
                return false;
            }
        });
        videoView.setOnCompletionListener(new OnCompletionListener() {
            @Override
            public void onCompletion() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sendVideoData("Complete");
                    }
                }).start();
                UnityPlayer.UnitySendMessage("ScriptHandler","SetVideoCompletePrefs",Alpha);
//                if(Grade.equals("Nursery")){
//                    UnityPlayer.UnitySendMessage("ScriptHandler","SetVideoCompletePrefs",Alpha);
//                }
                setVideoCompleteAnalytics();
                setVideoEndAnalytics();
                startActivity(new Intent(VideoActivity.this,UnityPlayerActivity.class));
            }
        });
        videoView.setVideoURI(Uri.parse(video_url));
        Log.d("VideoURL",": "+video_url);
    }

    //function to check if video is locked or not
    boolean allowedtoplay(){
        return true;
    }

    SeekBar.OnSeekBarChangeListener mVideoSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            seekStartTime=videoView.getCurrentPosition();
            seekHandler.removeCallbacks(runnableSeek);
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            int k=seekBar.getProgress();
            seekEndTime=k;
            setSliderMovedAnalytics();
            videoView.seekTo(k);
            seekHandler.postDelayed(runnableSeek, 100);
        }
    };
    private void seekBarUpdate() {
      if (null == videoView) return;
        long currentTime=videoView.getCurrentPosition();
        mSeekBar.setProgress((int)currentTime);
    }

    private Runnable runnableSeek = new Runnable() {
        @Override
        public void run() {
            seekBarUpdate();
            SecondsConsumed++;
            if(dailyTrialEnabled == 1 && SecondsConsumed/10>=freeTrialSecondsLeft){
                seekHandler.removeCallbacks(runnableSeek);
                setVideoEndAnalytics();
                Intent intent=new Intent(VideoActivity.this,UnityPlayerActivity.class);
                startActivity(intent);
                finish();

            }else{
                seekHandler.postDelayed(this, 100);    
            }
            
        }
    };

    //function to send data to firebase
    public void sendVideoData(String status){
        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("MM/dd/yyyy kk:mm:ss", Locale.ENGLISH);
        finishTime = simpleDateFormat.format(Calendar.getInstance().getTime());
        if(Alpha!=null && startTime!=null && status!=null && finishTime!=null){
            VideoData videoData=new VideoData(Alpha,startTime,status,finishTime,getApplicationContext());
            videoData.setVideoData();
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(VideoActivity.this,UnityPlayerActivity.class);
        switch (v.getId()) {
            case R.id.playBtn:
                play_pause_counter++;
                setPlayPauseAnalytics();
                if (videoView.isPlaying()) {

                    if (null != videoView && videoView.isPlaying())
                        videoView.pause();
                        seekHandler.removeCallbacks(runnableSeek);
                        playbtn.setImageResource(R.drawable.green_play_btn);
                }else {
                   if (null != videoView && !videoView.isPlaying())
                        videoView.start();
                    seekHandler.postDelayed(runnableSeek, 100);
                    playbtn.setImageResource(R.drawable.green_pause_button);
                }
                break;
            case R.id.btnHome:
                setVideoEndAnalytics();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sendVideoData("Left");
                    }
                }).start();
                unityvar=1;
                startActivity(intent);
                finish();
                break;
            case R.id.dm_player_web_view:
            case R.id.videoActivityToplayout:
                if(linearLayoutMain.getVisibility()==View.VISIBLE){
                    fadeOutAnimation();
                }else{
                    fadeInAnimation();
                   }
                   break;
            case R.id.btnSatpin:
                setVideoEndAnalytics();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sendVideoData("Left");
                    }
                }).start();
                startActivity(intent);
                finish();
                break;
            case R.id.btnDownload:
                ConnectivityManager cm =
                        (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting(); //boolean to get network status(internet connectivity)
                checkDownloadVideo(Alpha);
                if(videoIsDownloaded){
                    Toast.makeText(getApplicationContext(),"Already downloaded!",Toast.LENGTH_SHORT).show();
                    // TODO: 29-Jun-19
                    //delete file
                }else {
                    if(isConnected){
                        if (isVideoDownloading) {
                            Toast.makeText(getApplicationContext(), "Please wait video is already downloading", Toast.LENGTH_SHORT).show();
                        } else {
                            if(ContentUnlocked==0){
                                if(IsFree){
                                    isVideoDownloading=true;
                                    videoDownloading = Alpha;
                                    Toast.makeText(getApplicationContext(), "Starting download!", Toast.LENGTH_LONG).show();
                                    downloadVideoFile();
                                }
                                else{
                                    ShowPurchaseDialog();
                                }
                            }
                            else if(ContentUnlocked==1) {
                                isVideoDownloading = true;
                                videoDownloading = Alpha;
                                Toast.makeText(getApplicationContext(), "Starting download!", Toast.LENGTH_LONG).show();
                                downloadVideoFile();
                            }
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Please connect to internet", Toast.LENGTH_LONG).show();
                    }

                }
                setVideoDownloadStartAnalytics();
                break;
            case R.id.toggleEng:
                Log.d("videoId","english");
                setLanguageButtonAnalytics("English");
                changeUserVideoLanguage("English");
                SetBackgroundImage();
                checkDownloadVideo(Alpha);
                SetVideoId();
                    break;
            case R.id.toggleUrdu:
                Log.d("videoId","urdu");
                setLanguageButtonAnalytics("Urdu");
                changeUserVideoLanguage("Urdu");
                SetBackgroundImage();
                checkDownloadVideo(Alpha);
                SetVideoId();
                break;
            case R.id.problemBtn:
                CustomDialog customDialog = new CustomDialog(VideoActivity.this, R.style.AppTheme_CustomDialog);
                customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                customDialog.show();
                break;
            case R.id.btnShare:
              ShareVideo();
              break;
        }
    }
    void ShareVideo(){
//        DynamicLink dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
//                .setLink(Uri.parse("http://app.taleemabad.com/unity?dynamicLinkType=VideoShare&Grade="+Grade+"&Subject="+Subject+"&VideoName="+Alpha))
//                .setDomainUriPrefix("https://taleemabad.page.link")
//                // Open links with this app on Android
//                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder("com.orenda.taimo.myapplication")
//                        .setMinimumVersion(151)
//                        .build())
//                // Open links with com.example.ios on iOS
//                .buildDynamicLink();
//
//        Uri dynamicLinkUri = dynamicLink.getUri();
//        Intent sendIntent = new Intent();
//        sendIntent.setAction(Intent.ACTION_SEND);
//        sendIntent.putExtra(Intent.EXTRA_TEXT, ""+dynamicLinkUri);
//        sendIntent.setType("text/plain");
//        startActivity(sendIntent);
//        FirebaseAuth mAuth = FirebaseAuth.getInstance();
//        FirebaseUser firebaseUser = mAuth.getCurrentUser();
//        new AppAnalytics(getApplicationContext()).VideoShared(Grade,Subject,Alpha,Grade+Subject+Alpha,firebaseUser.getUid());
    }

    private void changeUserVideoLanguage(String language){
        SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        if(language.equals("English")){
            toggleEnglishBtn.setImageResource(R.drawable.toggle_eng_on);
            toggleUrduBtn.setImageResource(R.drawable.toggle_urdu_off);
            editor.putString("videoLanguage",language);
            this.language = language;
        } else if (language.equals("Urdu")) {
            toggleEnglishBtn.setImageResource(R.drawable.toggle_eng_off);
            toggleUrduBtn.setImageResource(R.drawable.toggle_urdu_on);
            editor.putString("videoLanguage",language);
            this.language = language;
        }
        editor.apply();
    }

    void ShowPurchaseDialog(){
        videoView.pause();
        seekHandler.removeCallbacks(runnableSeek);
        playbtn.setImageResource(R.drawable.green_play_btn);

        AlertDialog.Builder PurchaseDialogBuilder = new AlertDialog.Builder(this);
        PurchaseDialogBuilder.setMessage("Please purchase the app to download this video.");
        PurchaseDialogBuilder.setCancelable(true);

        PurchaseDialogBuilder.setPositiveButton(
                "Buy",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        unityvar=6;
                        startActivity(new Intent(VideoActivity.this, UnityPlayerActivity.class));
                    }
                });

        PurchaseDialogBuilder.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        videoView.start();
                        seekHandler.postDelayed(runnableSeek, 100);
                        playbtn.setImageResource(R.drawable.green_pause_button);
                    }
                });

        AlertDialog PurchaseDialogAlert = PurchaseDialogBuilder.create();
        PurchaseDialogAlert.show();
    }

    public void downloadVideoFile(){
      /*  downloadForegroundService = new DownloadForegroundService();
        downloadServiceIntent = new Intent(this, DownloadForegroundService.class);
        downloadServiceIntent.putExtra("fileURL", "http://175.107.197.227/Movavi%20Library/"+Grade+"/"+Subject+"/"+language+"Language/"+Alpha+".mp4");
        downloadServiceIntent.putExtra("fileName", Grade+Subject+language+"Language"+videoDownloading);
        if (isMyServiceRunning(downloadForegroundService.getClass())) {
            Toast.makeText(this, "Already downloading.", Toast.LENGTH_SHORT).show();
        }
        else {
            ContextCompat.startForegroundService(this, downloadServiceIntent);
            initiateBroadcastReceiver(Grade+Subject+language+"Language"+videoDownloading+"Video");
            //initiateProgressBroadcastReceiver(Grade+Subject+language+"Language"+videoDownloading+"Video"+"Progress");
        }*/


        File file=new File(getApplicationContext().getFilesDir(),Grade+Subject+"UrduLanguage"+Alpha);
        final CircularProgressBar circularProgressBar = (CircularProgressBar)findViewById(R.id.videodownloadprogress);
        /*
        AWSMobileClient.getInstance().initialize(this).execute();
        */
         /*TransferUtility transferUtility =
                TransferUtility.builder()
                        .context(getApplicationContext())
                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                        .s3Client(new AmazonS3Client(AWSMobileClient.getInstance().getCredentialsProvider()))
                        .build();
        String videoDownload= Alpha+".mp4";
        String repository = "video-repository-taleemabad-ireland/DownloadAppVideos/"+Grade+"/"+Subject+"/"+language+"Language";
        final TransferObserver downloadObserver =
                transferUtility.download(repository,
                        videoDownload,
                        file
                        );*/

        /*downloadObserver.setTransferListener(new TransferListener() {

            @Override
            public void onStateChanged(int id, TransferState state) {
                if (TransferState.COMPLETED == state) {
                    Toast.makeText(getApplicationContext(),"Download Complete",Toast.LENGTH_SHORT).show();
                    isVideoDownloading=false;
                    if(videoDownloading.equals(Alpha)){
                        downloadBtn.setImageResource(R.drawable.download_complete_btn);
                    }
                    setVideoDownloadSharedPref();
                    setVideoDownloadEndAnalytics(downloadObserver.getBytesTotal());
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                float percentDonef = ((float)bytesCurrent/(float)bytesTotal) * 100;
                int percentDone = (int)percentDonef;

                circularProgressBar.setProgressMax(100);
                circularProgressBar.setProgress(percentDone);
                Log.d("MainActivityvideo", "   ID:" + id + "   bytesCurrent: " + bytesCurrent + "   bytesTotal: " + bytesTotal + " "  + "%"+" "+circularProgressBar);
                if(bytesCurrent==bytesTotal){
                    circularProgressBar.setProgress(0);
                }
            }

            @Override
            public void onError(int id, Exception ex) {
                // Handle errors
                isVideoDownloading=false;
                setVideoDownloadErrorAnalytics(ex.getMessage());
                videoDownloadFail();
                Log.d("amazonherevideo","hereErr: "+ex);
            }

        });*/

    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("Service status", "Running");
                return true;
            }
        }
        Log.i ("Service status", "Not running");
        return false;
    }


    //function to set video download shared pref
    public void setVideoDownloadSharedPref(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putBoolean(Grade+Subject+language+"Language"+videoDownloading+"Video", true);
        editor.apply();
    }

    public void fadeInAnimation(){
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(500);
        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                linearLayoutMain.setVisibility(View.VISIBLE);
                //alphaImage.setVisibility(View.VISIBLE);
                playbtn.setVisibility(View.VISIBLE);
                mSeekBar.setVisibility(View.VISIBLE);
                linearLayoutProblem.setVisibility(View.VISIBLE);
                problemBtn.setVisibility(View.VISIBLE);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        linearLayoutMain.startAnimation(fadeIn);
        linearLayoutProblem.startAnimation(fadeIn);
    }
    public void fadeOutAnimation(){
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setStartOffset(0);
        fadeOut.setDuration(500);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                linearLayoutMain.setVisibility(View.INVISIBLE);
                //alphaImage.setVisibility(View.INVISIBLE);
                playbtn.setVisibility(View.INVISIBLE);
                mSeekBar.setVisibility(View.INVISIBLE);
                linearLayoutProblem.setVisibility(View.INVISIBLE);
                problemBtn.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        linearLayoutMain.startAnimation(fadeOut);
        linearLayoutProblem.startAnimation(fadeOut);

    }

    public void fullScreen(){
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
    public void videoDownloadFail() {
       Toast.makeText(getApplicationContext(),"Video Downloading Failed",Toast.LENGTH_LONG).show();
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
    @Override
    public void onResume(){
        super.onResume();

    }

    @Override
    public void onPause(){
        super.onPause();
        if (videoView.isPlaying()) {
            if (null != videoView && videoView.isPlaying()) {
                videoView.pause();
                seekHandler.removeCallbacks(runnableSeek);
                playbtn.setImageResource(R.drawable.green_play_btn);
            }
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        videoView=null;
        
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
    }


    @Override
    public void onBackPressed(){
        setVideoEndAnalytics();
        new Thread(new Runnable() {
            @Override
            public void run() {
                sendVideoData("Left");
            }
        }).start();
        Intent intent=new Intent(VideoActivity.this,UnityPlayerActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onStop(){
        super.onStop();
        int SecondsConsumedToUnity= SecondsConsumed/10;
        if(!IsFree && dailyTrialEnabled == 1)
        UnityPlayer.UnitySendMessage("TrialTickerObject","UpdateSevenMinutesTrialLeft",Integer.toString(SecondsConsumedToUnity));
        if(unityvar==1){
            UnityPlayer.UnitySendMessage("ScriptHandler", "HomeButton","");
        }
        else if(unityvar==6){
            UnityPlayer.UnitySendMessage("selectionObject", "videoPopupShow",Alpha);
        }
        else if(unityvar==7){
            UnityPlayer.UnitySendMessage("selectionObject", "showAppPurchase","");
        }
    }

    /**
     * Analytics functions
     * */
    //function to set start video Analytics
    private void setVideoStartAnalytics() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);//to get video download status
        ConnectivityManager cm =
                (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting(); //boolean to get network status(internet connectivity)
        int translation=getvideotranslationNumber();
        AppAnalytics appAnalytics=new AppAnalytics(getApplicationContext());
        appAnalytics.VideoEventStart("VideoPlayer",Grade+Subject+Alpha,Alpha, -1,Grade, MimeTypeMap.getFileExtensionFromUrl(video_url),0,Subject,true,false,videoView.getDuration(),translation,language,preferences.getBoolean(Alpha+"Video", false),isConnected, (activeNetwork!=null)?activeNetwork.getTypeName():"","VideoPlayer"+Grade+Subject+Alpha,Grade+Subject+Alpha);
    }
    //function to set end video Analytics
    //called when video is finished or video is changed or home/satpin/back is pressed
    private void setVideoEndAnalytics(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);//to get video download status
        AppAnalytics appAnalytics=new AppAnalytics(getApplicationContext());
        appAnalytics.VideoEventEnd(Alpha,0,videoView!=null? videoView.getCurrentPosition():0,play_pause_counter,preferences.getBoolean(Alpha+"Video", false),Grade+Subject+Alpha,SecondsConsumed/10);
    }

    //function to set analytics when video is completed
    void setVideoCompleteAnalytics(){
        new AppAnalytics(getApplicationContext()).VideoComplete(Alpha,Grade+Subject+Alpha,Grade,Subject,SecondsConsumed/10);
    }

    private int getvideotranslationNumber() {
        switch (Grade){
            case "Nursery":
                switch (Subject){
                    case "English":
                    case "Math":
                        return 2;
                    case "Urdu":
                    case "Tarbiyat":
                        return 1;
                }
                return 2;
            case "Grade1":
            case "Grade2":
            case "Grade3":
            case "Grade4":            
            case "Grade5":
                return 1;
        }
        return 1;
    }
    //function to set play or pause of video analytics
    //called when play/pause button is clicked
    private void setPlayPauseAnalytics(){
        ConnectivityManager cm =
                (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting(); //boolean to get network status(internet connectivity)
        AppAnalytics appAnalytics=new AppAnalytics(getApplicationContext());
        appAnalytics.PlayPause(Alpha,-1, (videoView.isPlaying())? "Play":"Pause",(videoView.isPlaying())? "Pause":"Play",videoView.getCurrentPosition(), isConnected, (activeNetwork!=null)?activeNetwork.getTypeName():"");
    }
    //function to set download video analytic
    //called when download button is clicked
    private void setVideoDownloadStartAnalytics(){
        ConnectivityManager cm =
                (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting(); //boolean to get network status(internet connectivity)
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);//to get video download status
        AppAnalytics appAnalytics=new AppAnalytics(getApplicationContext());
        appAnalytics.VideoDownloadStart(Alpha,-1, preferences.getBoolean(Alpha+"Video", false),videoView.getCurrentPosition(),PurchaseStatus!=0,true, isVideoDownloading, (!isConnected)? "Internet unavailable": (videoIsDownloaded)? "Video is already downloaded": (isVideoDownloading && !videoDownloading.equals(Alpha))? "Another video is downloading": (PurchaseStatus==0 && !isVideoDownloading)? "User did not purchase the full version":"Null",0, isConnected, (activeNetwork!=null)?activeNetwork.getTypeName():"");
    }

    //function to set video download end analytic
    // called when video download ends
    private void setVideoDownloadEndAnalytics(long videoSize){
        AppAnalytics appAnalytics=new AppAnalytics(getApplicationContext());
        appAnalytics.VideoDownloadEnd(videoDownloading,-1,true, videoSize, true);
    }

    //function to set video download error analytic
    //called when error comes while downloading
    private void setVideoDownloadErrorAnalytics(String error){
        AppAnalytics appAnalytics=new AppAnalytics(getApplicationContext());
        appAnalytics.VideoDownloadError(videoDownloading,-1,error);
    }

    //function to set video langugage analytic
    //called whenever user presses language change button
    private void setLanguageButtonAnalytics(String languageChanged){
        AppAnalytics appAnalytics=new AppAnalytics(getApplicationContext());
        appAnalytics.LanguageButton(Alpha,-1,language,languageChanged,videoView.getCurrentPosition());
    }

    //function to set slider moved analytics
    //called whenever user seeks video
    private void setSliderMovedAnalytics(){
        ConnectivityManager cm =
                (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting(); //boolean to get network status(internet connectivity)
        AppAnalytics appAnalytics=new AppAnalytics(getApplicationContext());
        appAnalytics.SliderMoved(Alpha,-1,seekStartTime,seekEndTime,isConnected, (activeNetwork!=null)?activeNetwork.getTypeName():"");
    }
}