package com.orenda.taimo.grade3tograde5.Tests;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.webkit.MimeTypeMap;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.devbrackets.android.exomedia.listener.OnPreparedListener;
import com.devbrackets.android.exomedia.ui.widget.VideoView;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.orenda.taimo.grade3tograde5.R;
import com.orenda.taimo.grade3tograde5.SocraticActivity;
import com.orenda.taimo.grade3tograde5.UnityPlayerActivity;
import com.unity3d.player.UnityPlayer;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import firebase.analytics.AppAnalytics;
import firebase.classes.VideoData;

public class videoFragment extends Fragment implements View.OnClickListener {
    String VideoName;
    int testId = -1;
    Context mContext;
    Activity activity;
    public static String VIDEO_ID;
    private SeekBar mSeekBar;
    String Alpha;//variable to store current alphabet session
    ImageView alphaImage;
    ConstraintLayout linearLayoutMain;
    ImageButton playbtn, homeBtn, satpinBtn, downloadBtn,toggleEnglishBtn, toggleUrduBtn;
    int unityvar; //to call correct unity method
    String Grade="";
    String Subject="";
//    int ContentUnlocked; //0=locked 1=unlocked
    int PurchaseStatus = 0; //0=locked 1=unlocked
//    boolean IsFree;
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
    long seekStartTime,seekEndTime;
    AppAnalytics appAnalytics;

    ConstraintLayout ToggleLanguageLayout;
    CircularProgressBar circularProgressBar;

    @SuppressLint("ValidFragment")
//    public videoFragment(String videoName, Context context, Activity activity) {
//        this.testId = testId;
//        mContext = context;
//        this.activity = activity;
//        this.VideoName = videoName;
//
//
//        Log.wtf("-this", " TEST ID : " + testId);
//    }
//    public videoFragment(String videoName, Context context, Activity activity,String alpha,
//                         String Grade, String subject, boolean isfree, int contentUnlocked,
//                         int purchaseStarus) {
//        this.testId = testId;
//        mContext = context;
//        this.activity = activity;
//        this.VideoName = videoName;
//        this.Alpha=alpha;
//        this.Grade=Grade;
//        this.Subject=subject;
//        this.IsFree=isfree;
//        this.ContentUnlocked =contentUnlocked;
//        this.PurchaseStatus = purchaseStarus;
//
//
//        Log.wtf("-this", " TEST ID : " + testId);
//    }
    public videoFragment(String videoName, Context context, Activity activity,String alpha,
                         String Grade, String subject) {
        this.testId = testId;
        mContext = context;
        this.activity = activity;
        this.VideoName = videoName;
        this.Alpha=alpha;
        this.Grade=Grade;
        this.Subject=subject;
        appAnalytics = new AppAnalytics(mContext);


        Log.wtf("-this", " Alpha : " + alpha);
    }
    public videoFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        alphaImage=view.findViewById(R.id.alphaImg);
        linearLayoutMain=view.findViewById(R.id.videoActivityToplayout);
        playbtn=view.findViewById(R.id.playBtn);
        homeBtn=view.findViewById(R.id.btnHome);
        satpinBtn=view.findViewById(R.id.btnSatpin);
        downloadBtn=view.findViewById(R.id.btnDownload);
        toggleEnglishBtn=view.findViewById(R.id.toggleEng);
        toggleUrduBtn=view.findViewById(R.id.toggleUrdu);
        mSeekBar = (SeekBar) view.findViewById(R.id.video_seekbar);
        ToggleLanguageLayout = view.findViewById(R.id.languagetogglelayout);
        videoView = (VideoView)view.findViewById(R.id.dm_player_web_view);
        circularProgressBar = (CircularProgressBar)view.findViewById(R.id.videodownloadprogress);


        SetOnClickListners();

        mHandler = new Handler();
        seekHandler=new Handler();




        setUserPreferenceLanguage();
        checkDownloadVideo(Alpha);
        SetBackgroundImage();
        SetVideoId();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
//        setVideoEndAnalytics();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                sendVideoData("Left");
//            }
//        }).start();
//        Intent intent=new Intent(mContext,UnityPlayerActivity.class);
//        startActivity(intent);
//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                ((SocraticActivity)activity).setVideo();
//                getFragmentManager().beginTransaction()
//                        .remove(videoFragment.this).commit();
//
//            }
//        }, 500);

        super.onDetach();
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
    }



    private void setUserPreferenceLanguage(){
        if(Grade.equals("Nursery") && (Subject.equals("English") || Subject.equals("Math"))){
            SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(mContext);
            language=preferences.getString("videoLanguage","Urdu");
            if(language.equals("English")){
                toggleEnglishBtn.setImageResource(R.drawable.toggle_eng_on);
                toggleUrduBtn.setImageResource(R.drawable.toggle_urdu_off);
            }else if(language.equals("Urdu")){
                toggleEnglishBtn.setImageResource(R.drawable.toggle_eng_off);
                toggleUrduBtn.setImageResource(R.drawable.toggle_urdu_on);
            }
        }else{
            ToggleLanguageLayout.setVisibility(View.GONE);
            ToggleLanguageLayout = null;
            language = "Urdu";
        }

    }

    private void SetVideoId(){
        if(videoIsDownloaded){
            setOfflineVideoId(Grade+Subject+"UrduLanguage"+Alpha);
        }else{
            if(Subject.equals("Maths")){
                video_url = "http://d3lt10t4pogyn.cloudfront.net/"+Grade+"/Math/"+language+"Language/"+Alpha+"6k.m3u8".replaceAll(" ","");
            }else{
                video_url = "http://d3lt10t4pogyn.cloudfront.net/"+Grade+"/"+Subject+"/"+language+"Language/"+Alpha+"6k.m3u8".replaceAll(" ","");
            }
            //video_url = "http://d3lt10t4pogyn.cloudfront.net/"+Grade+"/"+Subject+"/"+language+"Language/"+Alpha+"6k.m3u8".replaceAll(" ","");
         //   video_url = "http://d3lt10t4pogyn.cloudfront.net/"+Grade+"/"+Subject+"/"+language+"Language/"+Alpha+".m3u8".replaceAll(" ","");
         //   video_url = "http://d3lt10t4pogyn.cloudfront.net/"+Grade.toLowerCase()+"/"+Subject+"/"+Alpha+".m3u8".replaceAll(" ","");
        }
        Log.wtf("-this", " Video_url : " + video_url);
        setupVideoView();
    }

    private void SetBackgroundImage(){
        int id = getResources().getIdentifier("alpha_"+Grade.toLowerCase()+"_"+Subject.toLowerCase()+"_"+Alpha.toLowerCase(), "drawable", mContext.getPackageName());
        alphaImage.setImageResource(id);
    }


    private void setOfflineVideoId(String alpha){
        File file=new File(mContext.getFilesDir(),alpha);
        file.setReadable(true);
        Uri uri=Uri.fromFile(file);
        video_url=uri.toString();
    }

    //function to check video is downloaded or not
    //then set download button accordingly
    public void checkDownloadVideo(String alpha){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
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
        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("MM/dd/yyyy kk:mm:ss");
        startTime = simpleDateFormat.format(Calendar.getInstance().getTime());
        // Make sure to use the correct VideoView import

        videoView.setOnClickListener(this);
        videoView.setOnPreparedListener(new OnPreparedListener() {
            @Override
            public void onPrepared() {
                setVideoStartAnalytics(); //call function to set analytics
                videoView.start();
                appAnalytics.setSocraticVideoStart(Subject,Alpha,VideoName);
                playbtn.setImageResource(R.drawable.green_pause_button);
                mSeekBar.setMax((int) videoView.getDuration());
                seekHandler.postDelayed(runnableSeek, 100);
                seekBarUpdate();
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
                // UnityPlayer.UnitySendMessage("ScriptHandler","SetVideoCompletePrefs",Alpha);
                setVideoEndAnalytics();
               // startActivity(new Intent(mContext, UnityPlayerActivity.class));

            }
        });
        videoView.setVideoURI(Uri.parse(video_url));
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
            seekHandler.postDelayed(this, 100);
        }
    };

    //function to send data to firebase
    public void sendVideoData(String status){
        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("MM/dd/yyyy kk:mm:ss");
        finishTime = simpleDateFormat.format(Calendar.getInstance().getTime());
        if(Alpha!=null && startTime!=null && status!=null && finishTime!=null){
            VideoData videoData=new VideoData(Alpha,startTime,status,finishTime,mContext);
            videoData.setVideoData();
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(mContext,UnityPlayerActivity.class);
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
                break;
            case R.id.dm_player_web_view:
                break;
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
                break;
            case R.id.btnDownload:
                ConnectivityManager cm =
                        (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting(); //boolean to get network status(internet connectivity)
                checkDownloadVideo(Alpha);
                if(videoIsDownloaded){
                    Toast.makeText(mContext,"Already downloaded!",Toast.LENGTH_SHORT).show();
                    // TODO: 29-Jun-19
                    //delete file
                }else {
                    if(isConnected){
                        if (isVideoDownloading) {
                            Toast.makeText(mContext, "Please wait video is already downloading", Toast.LENGTH_SHORT).show();
                        } else {
                            isVideoDownloading=true;
                            videoDownloading = Alpha;
                            Toast.makeText(mContext, "Starting download!", Toast.LENGTH_LONG).show();
                            downloadVideoFile();

//                            if(ContentUnlocked==0){
//                                if(IsFree){
//                                    isVideoDownloading=true;
//                                    videoDownloading = Alpha;
//                                    Toast.makeText(mContext, "Starting download!", Toast.LENGTH_LONG).show();
//                                    downloadVideoFile();
//                                }
//                                else{
//                                    ShowPurchaseDialog();
//                                }
//                            }
//                            else if(ContentUnlocked==1) {
//                                isVideoDownloading = true;
//                                videoDownloading = Alpha;
//                                Toast.makeText(mContext, "Starting download!", Toast.LENGTH_LONG).show();
//                                downloadVideoFile();
//                            }
                        }
                    }
                    else{
                        Toast.makeText(mContext,"Please connect to internet", Toast.LENGTH_LONG).show();
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
        }
    }

    private void changeUserVideoLanguage(String language){
        SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences(mContext);
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

        AlertDialog.Builder PurchaseDialogBuilder = new AlertDialog.Builder(mContext);
        PurchaseDialogBuilder.setMessage("Please purchase the app to download this video.");
        PurchaseDialogBuilder.setCancelable(true);

        PurchaseDialogBuilder.setPositiveButton(
                "Buy",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        unityvar=6;
                        startActivity(new Intent(mContext, UnityPlayerActivity.class));
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
        File file=new File(mContext.getFilesDir(),Grade+Subject+"UrduLanguage"+Alpha);
        /*
        AWSMobileClient.getInstance().initialize(mContext).execute();

        TransferUtility transferUtility =
                TransferUtility.builder()
                        .context(mContext)
                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                        .s3Client(new AmazonS3Client(AWSMobileClient.getInstance().getCredentialsProvider()))
                        .build();
        String videoDownload= Alpha+".mp4";
        String repository = "video-repository-taleemabad-ireland/DownloadFolder/"+Grade+"/"+Subject+"/"+language+"Language";
        final TransferObserver downloadObserver =
                transferUtility.download(repository,
                        videoDownload,
                        file
                );*/
        /*
        downloadObserver.setTransferListener(new TransferListener() {

            @Override
            public void onStateChanged(int id, TransferState state) {
                if (TransferState.COMPLETED == state) {
                    Toast.makeText(mContext,"Download Complete",Toast.LENGTH_SHORT).show();
                    isVideoDownloading=false;
                    if(videoDownloading.equals(Alpha)){
                        downloadBtn.setImageResource(R.drawable.download_complete_btn);
                    }

                    setVideoDownloadSharedPref();
                    setVideoDownloadEndAnalytics(downloadObserver.getBytesTotal());
                    final Handler handler = new Handler();


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

    public void videoDownloadFail() {
        Toast.makeText(mContext,"Video Downloading Failed",Toast.LENGTH_LONG).show();
    }

    //function to set video download shared pref
    public void setVideoDownloadSharedPref(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
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
                alphaImage.setVisibility(View.VISIBLE);
                playbtn.setVisibility(View.VISIBLE);
                mSeekBar.setVisibility(View.VISIBLE);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        linearLayoutMain.startAnimation(fadeIn);
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
                alphaImage.setVisibility(View.INVISIBLE);
                playbtn.setVisibility(View.INVISIBLE);
                mSeekBar.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        linearLayoutMain.startAnimation(fadeOut);

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
        if(videoView != null){
            if(videoView.getCurrentPosition() <  videoView.getDuration()){
                appAnalytics.setSocraticVideoEnd(Subject,Alpha,VideoName,(int)videoView.getDuration(),(int)videoView.getCurrentPosition());

            }
        }
        super.onDestroy();
        videoView=null;
    }



    @Override
    public void onStop(){
        super.onStop();
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
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);//to get video download status
        ConnectivityManager cm =
                (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting(); //boolean to get network status(internet connectivity)
        int translation=getvideotranslationNumber();
        AppAnalytics appAnalytics=new AppAnalytics(mContext);
        appAnalytics.VideoEventStart("VideoPlayer",Grade+Subject+Alpha,Alpha, -1,Grade, MimeTypeMap.getFileExtensionFromUrl(video_url),0,Subject,true,false,videoView.getDuration(),translation,language,preferences.getBoolean(Alpha+"Video", false),isConnected, (activeNetwork!=null)?activeNetwork.getTypeName():"","VideoPlayer"+Grade+Subject+Alpha,Grade+Subject+Alpha);
    }
    //function to set end video Analytics
    //called when video is finished or video is changed or home/satpin/back is pressed
    private void setVideoEndAnalytics(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);//to get video download status
        AppAnalytics appAnalytics=new AppAnalytics(mContext);
      //  appAnalytics.VideoEventEnd(Alpha,0,videoView.getCurrentPosition(),play_pause_counter,preferences.getBoolean(Alpha+"Video", false));
        appAnalytics.setSocraticVideoComplete(Subject,Alpha,VideoName,(int)videoView.getDuration());

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ((SocraticActivity)activity).setTestAgain();
                getFragmentManager().beginTransaction()
                        .remove(videoFragment.this).commit();

            }
        }, 500);
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
                return 1;
        }
        return 1;
    }
    //function to set play or pause of video analytics
    //called when play/pause button is clicked
    private void setPlayPauseAnalytics(){
        ConnectivityManager cm =
                (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting(); //boolean to get network status(internet connectivity)
        AppAnalytics appAnalytics=new AppAnalytics(mContext);
        appAnalytics.PlayPause(Alpha,-1, (videoView.isPlaying())? "Play":"Pause",(videoView.isPlaying())? "Pause":"Play",videoView.getCurrentPosition(), isConnected, (activeNetwork!=null)?activeNetwork.getTypeName():"");
    }
    //function to set download video analytic
    //called when download button is clicked
    private void setVideoDownloadStartAnalytics(){
        ConnectivityManager cm =
                (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting(); //boolean to get network status(internet connectivity)
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);//to get video download status
        AppAnalytics appAnalytics=new AppAnalytics(mContext);
        appAnalytics.VideoDownloadStart(Alpha,-1, preferences.getBoolean(Alpha+"Video", false),videoView.getCurrentPosition(),PurchaseStatus!=0,true, isVideoDownloading, (!isConnected)? "Internet unavailable": (videoIsDownloaded)? "Video is already downloaded": (isVideoDownloading && !videoDownloading.equals(Alpha))? "Another video is downloading": (PurchaseStatus==0 && !isVideoDownloading)? "User did not purchase the full version":"Null",0, isConnected, (activeNetwork!=null)?activeNetwork.getTypeName():"");
    }

    //function to set video download end analytic
    // called when video download ends
    private void setVideoDownloadEndAnalytics(long videoSize){
        AppAnalytics appAnalytics=new AppAnalytics(mContext);
        appAnalytics.VideoDownloadEnd(videoDownloading,-1,true, videoSize, true);
    }

    //function to set video download error analytic
    //called when error comes while downloading
    private void setVideoDownloadErrorAnalytics(String error){
        AppAnalytics appAnalytics=new AppAnalytics(mContext);
        appAnalytics.VideoDownloadError(videoDownloading,-1,error);
    }

    //function to set video langugage analytic
    //called whenever user presses language change button
    private void setLanguageButtonAnalytics(String languageChanged){
        AppAnalytics appAnalytics=new AppAnalytics(mContext);
        appAnalytics.LanguageButton(Alpha,-1,language,languageChanged,videoView.getCurrentPosition());
    }

    //function to set slider moved analytics
    //called whenever user seeks video
    private void setSliderMovedAnalytics(){
        ConnectivityManager cm =
                (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting(); //boolean to get network status(internet connectivity)
        AppAnalytics appAnalytics=new AppAnalytics(mContext);
        appAnalytics.SliderMoved(Alpha,-1,seekStartTime,seekEndTime,isConnected, (activeNetwork!=null)?activeNetwork.getTypeName():"");
    }
}

