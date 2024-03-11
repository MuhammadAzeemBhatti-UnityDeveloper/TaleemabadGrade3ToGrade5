package com.orenda.taimo.grade3tograde5.Tests;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.FileDataSource;
import com.orenda.taimo.grade3tograde5.Models.TestJsonParseModel;
import com.orenda.taimo.grade3tograde5.R;
import com.orenda.taimo.grade3tograde5.SimpleTestActivity;
import com.orenda.taimo.grade3tograde5.SocraticActivity;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import firebase.analytics.AppAnalytics;

//import static com.facebook.FacebookSdk.getApplicationContext;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.tempIndex;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.testIndex;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.topic;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.unSocratic;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.alienLife;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.deduct;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.parrotLife;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.selectedSubject;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.totalScore;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.correctCount;


public class OrdinaryMCQLong extends Fragment implements View.OnClickListener {
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;
    TextView ordinaryMCQLongTextViewOption1, ordinaryMCQLongTextViewOption2, ordinaryMCQLongTextViewOption3, ordinaryMCQLongTextViewOption4;
    TextView ordinaryMCQLongTextViewQuestion;

    ImageView ImageViewParrotFire, ImageViewAlienAvatarLife, ImageViewParrotAvatarLife, ImageViewAlienFire;
    ImageView ImageViewParrotAvatar, ImageViewAlienAvatar;
    ImageView ImageViewParrotHit, ImageViewAlienHit;
    TextView TextViewParrotPlus, TextViewAlienPlus;
    int realHeight;
    int realWidth;
    AppAnalytics appAnalytics;

    float questionFontSize = 22;
    float optionFontSize = 22;
    float questionBigFontSize = 32;

    int testId = -1;
    TestJsonParseModel test = null;
    Context mContext;
    Integer[] arr = new Integer[4];
    Activity activity;
    Timer T = new Timer();
    int count = 0;
    boolean start = true;

    private DataSource.Factory dataSourceFactory;
    private ExtractorsFactory extractorsFactory;
    private MediaSource mediaSource;
    private SimpleExoPlayer player;
    private final String streamUrl = "http://cdn.audios.taleemabad.com/QuestionBank/";
    private boolean compileCalled = false;
    private TextView selectedTextView = null;

    /*
    int DEMO_MODE=0;
    ConstraintLayout ordinaryMCQLongMainLayout;
    Boolean ordinaryMCQ;
    MediaPlayer mp;
    boolean demoStarted=false;
    String checkLang;
    boolean stillInDemoState=false;

     */

    @SuppressLint("ValidFragment")
    public OrdinaryMCQLong(int testId, TestJsonParseModel test, Context context, Activity activity) {
        this.testId = testId;
        this.test = test;
        mContext = context;
        this.activity = activity;
        appAnalytics = new AppAnalytics(mContext);

        Log.wtf("-this", " TEST ID : " + testId);
    }

    public OrdinaryMCQLong() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.ordinary_mcq_long, container, false);
        player = ExoPlayerFactory.newSimpleInstance(getContext());
        sharedPrefs = getActivity().getApplicationContext().getSharedPreferences("DefaultSPForTaleemabadGrade6", 0);
        editor = sharedPrefs.edit();
        initializeView(view);
        setOnClickListeners(view);


        return view;
    }

    public void initializeView(View view) {
//        ordinaryMCQLongMainLayout = view.findViewById(R.id.ordinaryMCQLongMainLayout);

        ordinaryMCQLongTextViewQuestion = view.findViewById(R.id.ordinaryMCQLongTextViewQuestion);


        ordinaryMCQLongTextViewOption1 = view.findViewById(R.id.ordinaryMCQLongTextViewOption1);
        ordinaryMCQLongTextViewOption2 = view.findViewById(R.id.ordinaryMCQLongTextViewOption2);
        ordinaryMCQLongTextViewOption3 = view.findViewById(R.id.ordinaryMCQLongTextViewOption3);
        ordinaryMCQLongTextViewOption4 = view.findViewById(R.id.ordinaryMCQLongTextViewOption4);

        ImageViewParrotFire = view.findViewById(R.id.ImageViewParrotFire);
        ImageViewAlienFire = view.findViewById(R.id.ImageViewAlienFire);
        ImageViewParrotAvatarLife = view.findViewById(R.id.ImageViewParrotAvatarLife);
        ImageViewAlienAvatarLife = view.findViewById(R.id.ImageViewAlienAvatarLife);
        ImageViewParrotAvatar = view.findViewById(R.id.ImageViewParrotAvatar);
        ImageViewAlienAvatar = view.findViewById(R.id.ImageViewAlienAvatar);
        ImageViewParrotHit = view.findViewById(R.id.ImageViewParrotHit);
        ImageViewAlienHit = view.findViewById(R.id.ImageViewAlienHit);
        TextViewParrotPlus = view.findViewById(R.id.TextViewParrotPlus);
        TextViewAlienPlus = view.findViewById(R.id.TextViewAlienPlus);

        ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {

                    final DisplayMetrics metrics = new DisplayMetrics();
                    final WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        windowManager.getDefaultDisplay().getRealMetrics(metrics);
                    } else {
                        windowManager.getDefaultDisplay().getMetrics(metrics);
                    }
                    realHeight = metrics.heightPixels; // This is real height
                    realWidth = metrics.widthPixels; // This is real width

                    DisplayMetrics dm = new DisplayMetrics();
                    windowManager.getDefaultDisplay().getMetrics(dm);
                    int widthS = dm.widthPixels;
                    int heightS = dm.heightPixels;
                    double wi = (double) widthS / (double) dm.xdpi;
                    double hi = (double) heightS / (double) dm.ydpi;
                    double x = Math.pow(wi, 2);
                    double y = Math.pow(hi, 2);
                    double screenInches = Math.sqrt(x + y);

                    Log.wtf("-this", "height : " + realHeight + "  Width : " + realWidth + " Inches : " + screenInches);

                    questionFontSize = (0.042f * realHeight);
                    if (screenInches < 7) {
                        questionFontSize = (0.042f * (1080f / (2.3f)));
                    } else {
                        questionFontSize = (0.042f * (1080 / 2));
                    }
                    Log.wtf("-this", "height : " + realHeight + "  QUESTION FONT SIZE : " + questionFontSize);

                    optionFontSize = (0.045f * 1080 / 2);
                    if (screenInches < 7) {
                        optionFontSize = (0.045f * (1080f / 2.3f));
                    } else {
                        optionFontSize = (0.045f * 1080 / 2);
                    }


                    Log.wtf("-this", "height : " + realHeight + "  Q SFONT SIZE : " + questionFontSize);
                    Log.wtf("-this", "QUESTION SIZE : " + ordinaryMCQLongTextViewQuestion.getText().toString().length());

                    ordinaryMCQLongTextViewQuestion.setTextSize(questionFontSize);

                    ordinaryMCQLongTextViewOption1.setTextSize(optionFontSize);
                    ordinaryMCQLongTextViewOption2.setTextSize(optionFontSize);
                    ordinaryMCQLongTextViewOption3.setTextSize(optionFontSize);
                    ordinaryMCQLongTextViewOption4.setTextSize(optionFontSize);

                    ImageViewAlienAvatarLife.setBackgroundResource(R.drawable.alien_life_line_pink);
                    int level = alienLife * (100);   // pct goes from 0 to 100
                    ImageViewAlienAvatarLife.getBackground().setLevel(level);

                    ImageViewParrotAvatarLife.setBackgroundResource(R.drawable.parrot_life_line_blue);
                    int level1 = parrotLife * (100);
                    ImageViewParrotAvatarLife.getBackground().setLevel(level1);

                }
            });
        }
        setUpTest();
        setOnClickListeners(view);
        startTestTimer();

        //To retrieve
//        SharedPreferences sharedPrefForChecking = getActivity().getSharedPreferences("DemoOrdinaryMCQLONG", DEMO_MODE);
//        ordinaryMCQ = sharedPrefForChecking.getBoolean("OrdinaryMCQLONG", false); //0 is the default value

    }

/*
    void setUpDemo() {
        if (!ordinaryMCQ){
            //fillUpTest();

            //   setOnClickListenr(view);

//            SocraticActivity.testActivityImageViewHome.setVisibility(View.GONE);
//            SocraticActivity.testActivityImageViewDaimond.setVisibility(View.GONE);
//            SimpleTestActivity.testActivityImageViewHome.setVisibility(View.GONE);
//            SimpleTestActivity.testActivityImageViewDaimond.setVisibility(View.GONE);

            if (SocraticActivity.testActivityImageViewHome!=null){
                SocraticActivity.testActivityImageViewHome.setVisibility(View.GONE);
            }
            if (SocraticActivity.testActivityImageViewDaimond!=null){
                SocraticActivity.testActivityImageViewDaimond.setVisibility(View.GONE);
            }
            if (SimpleTestActivity.testActivityImageViewHome!=null){
                SimpleTestActivity.testActivityImageViewHome.setVisibility(View.GONE);
            }
            if (SimpleTestActivity.testActivityImageViewDaimond!=null){
                SimpleTestActivity.testActivityImageViewDaimond.setVisibility(View.GONE);
            }


            //    SimpleTestActivity.testActivityImageViewfeedback.setVisibility(View.GONE);
//            ordinaryMCQImageViewParrotAvatarLife.setVisibility(View.GONE);
//            ordinaryMCQImageViewParrotAvatar.setVisibility(View.GONE);
//            ordinaryMCQImageViewAlienAvatar.setVisibility(View.GONE);
//            ordinaryMCQImageViewAlienAvatarLife.setVisibility(View.GONE);
            ImageViewParrotAvatarLife.setVisibility(View.GONE);
            ImageViewAlienAvatarLife.setVisibility(View.GONE);
            ImageViewParrotAvatar.setVisibility(View.GONE);
            ImageViewAlienAvatar.setVisibility(View.GONE);

            final Handler handlerr = new Handler();
            handlerr.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    jab();

                    startDemo();
                }
            }, 100);

            SharedPreferences sharedPrefForSaving = getContext().getSharedPreferences("DemoOrdinaryMCQLONG", DEMO_MODE);
            SharedPreferences.Editor editor = sharedPrefForSaving.edit();
            editor.putBoolean("OrdinaryMCQLONG", true);
            editor.apply();
        }
        else {
            SimpleTestActivity.testActivityImageViewHome.setVisibility(View.VISIBLE);
            SimpleTestActivity.testActivityImageViewDaimond.setVisibility(View.VISIBLE);
          //  SimpleTestActivity.testActivityImageViewfeedback.setVisibility(View.VISIBLE);
            ImageViewAlienAvatarLife.setVisibility(View.VISIBLE);
            ImageViewParrotAvatarLife.setVisibility(View.VISIBLE);
            ImageViewParrotAvatar.setVisibility(View.VISIBLE);
            ImageViewAlienAvatar.setVisibility(View.VISIBLE);
            demoStarted=false;
        }



    }

 */

/*
    public void onDestroy() {
        if (mp!=null){
            mp.release();
        }
        super.onDestroy();
    }

    @Override
    public void onResume() {


        if ( demoStarted){
          //  mp.setLooping(true);
            if (checkLang.equals("en")) {
                mp = MediaPlayer.create(getContext(), R.raw.eng1);
                mp.start();
            }
            else {
                mp = MediaPlayer.create(getContext(), R.raw.urdu1);
                mp.start();

            }
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        stillInDemoState=false;
        if (mp!=null){
            mp.release();
        }
        super.onPause();
    }

 */
/*
    public void callForDemoVoice() {
        if (stillInDemoState){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (stillInDemoState) {
                        if (mp != null) {
                            mp.release();
                        }
                        if (checkLang != null && checkLang.equals("en")) {
                            mp = MediaPlayer.create(getContext(), R.raw.eng1);

                        } else {
                            mp = MediaPlayer.create(getContext(), R.raw.urdu1);

                        }
                        mp.start();
                        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                mp.release();
                                callForDemoVoice();
                            }
                        });
                    }
                }
            }, 5000);
        }
        else {
            return;
        }
    }
    */
/*
    public void startDemo() {
        stillInDemoState=true;

        if (mp != null) {
            mp.release();
        }

        SharedPreferences checkLanguage = getActivity().getApplicationContext().getSharedPreferences("DefaultSPForTaleemabadGrade6", 0);
        checkLang =checkLanguage.getString("MenuLanguage","en");
        if (checkLang!=null && checkLang.equals("en")){
            mp = MediaPlayer.create(getContext(), R.raw.eng1);

        }else {
            mp = MediaPlayer.create(getContext(), R.raw.urdu1);

        }
      //  mp.setLooping(true);
        mp.start();

        demoStarted =true;

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();

                callForDemoVoice();

            }
        });
        final Handler handlerr = new Handler();

        ordinaryMCQLongTextViewOption1.setVisibility(View.VISIBLE);
        ordinaryMCQLongTextViewOption2.setVisibility(View.VISIBLE);
        ordinaryMCQLongTextViewOption3.setVisibility(View.VISIBLE);
        ordinaryMCQLongTextViewOption4.setVisibility(View.VISIBLE);


        String ansText  = test.getAnswerList().get(0).getText();

        String option1_Text =ordinaryMCQLongTextViewOption1.getText().toString();
        String option2_Text =ordinaryMCQLongTextViewOption2.getText().toString();
        String option3_Text =ordinaryMCQLongTextViewOption3.getText().toString();
        String option4_Text =ordinaryMCQLongTextViewOption4.getText().toString();


        final ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(ordinaryMCQLongMainLayout);
        final ImageView imageview = new ImageView(activity);
        imageview.setImageResource(R.drawable.hand);
        imageview.setId(ViewCompat.generateViewId());
        imageview.requestLayout();

        if (option1_Text.equals(ansText)){
            handlerr.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    jab();

                    ordinaryMCQLongMainLayout.addView(imageview);
                    constraintSet.connect(imageview.getId(),ConstraintSet.TOP, R.id.guidelineOption1Top,ConstraintSet.TOP,0);
                    //    constraintSet.connect(imageview.getId(),ConstraintSet.END,R.id.guidelineOptionRight,ConstraintSet.START,0);
                    constraintSet.connect(imageview.getId(),ConstraintSet.START, R.id.guidelineOptionLeft,ConstraintSet.END,40);
                    constraintSet.connect(imageview.getId(),ConstraintSet.BOTTOM, R.id.guidelineOption1Bottom, ConstraintSet.TOP,0);
                    constraintSet.applyTo(ordinaryMCQLongMainLayout);
                    imageview.getLayoutParams().width = ordinaryMCQLongTextViewOption1.getMeasuredHeight();
                    imageview.getLayoutParams().height = ordinaryMCQLongTextViewOption1.getMeasuredHeight();
                    imageview.bringToFront();

                    //            tap_icon1.setVisibility(View.VISIBLE);
                    animateHandUpp(1, imageview);

                }
            }, 1200);
            //  tap_icon1.setVisibility(View.VISIBLE);
//            tap_icon2.setVisibility(View.GONE);
//            tap_icon3.setVisibility(View.GONE);
//            tap_icon4.setVisibility(View.GONE);

        }else if (option2_Text.equals(ansText)){

            //  tap_icon1.setVisibility(View.GONE);
            handlerr.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    jab();
                    //  tap_icon2.setVisibility(View.VISIBLE);


                    ordinaryMCQLongMainLayout.addView(imageview);
                    constraintSet.connect(imageview.getId(),ConstraintSet.BOTTOM, R.id.guidelineOption2Bottom,ConstraintSet.TOP,0);
                    //       constraintSet.connect(imageview.getId(),ConstraintSet.END,R.id.guidelineOptionRight,ConstraintSet.START,0);
                    constraintSet.connect(imageview.getId(),ConstraintSet.START, R.id.guidelineOptionLeft,ConstraintSet.END,40);
                    constraintSet.connect(imageview.getId(),ConstraintSet.TOP, R.id.guidelineOption2Top,ConstraintSet.TOP,0);
                    constraintSet.applyTo(ordinaryMCQLongMainLayout);
                    imageview.getLayoutParams().width = ordinaryMCQLongTextViewOption2.getMeasuredHeight();
                    imageview.getLayoutParams().height = ordinaryMCQLongTextViewOption2.getMeasuredHeight();
                    imageview.bringToFront();

                    animateHandUpp(2, imageview);

                }
            }, 1200);
            //tap_icon2.setVisibility(View.VISIBLE);
//            tap_icon3.setVisibility(View.GONE);
//            tap_icon4.setVisibility(View.GONE);

        }else if (option3_Text.equals(ansText)){

//            tap_icon1.setVisibility(View.GONE);
//            tap_icon2.setVisibility(View.GONE);
//            //tap_icon3.setVisibility(View.VISIBLE);
//            tap_icon4.setVisibility(View.GONE);

            handlerr.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    jab();

                    ordinaryMCQLongMainLayout.addView(imageview);

                    constraintSet.connect(imageview.getId(),ConstraintSet.BOTTOM, R.id.guidelineOption3Bottom,ConstraintSet.TOP,0);
//                    constraintSet.connect(imageview.getId(),ConstraintSet.END,R.id.guidelineOptionRight,ConstraintSet.START,0);
                    constraintSet.connect(imageview.getId(),ConstraintSet.START, R.id.guidelineOptionLeft,ConstraintSet.END,40);
                    constraintSet.connect(imageview.getId(),ConstraintSet.TOP, R.id.guidelineOption3Top,ConstraintSet.TOP,0);
                    constraintSet.applyTo(ordinaryMCQLongMainLayout);
                    imageview.getLayoutParams().width = ordinaryMCQLongTextViewOption3.getMeasuredHeight();
                    imageview.getLayoutParams().height = ordinaryMCQLongTextViewOption3.getMeasuredHeight();
                    imageview.bringToFront();

                    //    tap_icon3.setVisibility(View.VISIBLE);
                    animateHandUpp(3, imageview);

                }
            }, 1200);

        }else if (option4_Text.equals(ansText)){

            handlerr.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    jab();

                    ordinaryMCQLongMainLayout.addView(imageview);

                    constraintSet.connect(imageview.getId(),ConstraintSet.BOTTOM, R.id.guidelineOption4Bottom,ConstraintSet.TOP,0);
//                    constraintSet.connect(imageview.getId(),ConstraintSet.END,R.id.guidelineOptionRight,ConstraintSet.START,0);
                    constraintSet.connect(imageview.getId(),ConstraintSet.START, R.id.guidelineOptionLeft,ConstraintSet.END,40);
                    constraintSet.connect(imageview.getId(),ConstraintSet.TOP, R.id.guidelineOption4Top,ConstraintSet.TOP,0);
                    constraintSet.applyTo(ordinaryMCQLongMainLayout);
                    imageview.getLayoutParams().width = ordinaryMCQLongTextViewOption4.getMeasuredHeight();
                    imageview.getLayoutParams().height = ordinaryMCQLongTextViewOption4.getMeasuredHeight();
                    imageview.bringToFront();
                    //tap_icon4.setVisibility(View.VISIBLE);
                    animateHandUpp(4, imageview);

                }
            }, 1200);
//            tap_icon1.setVisibility(View.GONE);
//            tap_icon2.setVisibility(View.GONE);
//            tap_icon3.setVisibility(View.GONE);
            //tap_icon4.setVisibility(View.VISIBLE);

        }

    }
    */
/*
    public void animateHandUpp(int icon, final ImageView imageView) {
//        ordinaryMCQTextViewOption1.setVisibility(View.VISIBLE);
//        ordinaryMCQTextViewOption2.setVisibility(View.VISIBLE);
//        ordinaryMCQTextViewOption3.setVisibility(View.VISIBLE);
//        ordinaryMCQTextViewOption4.setVisibility(View.VISIBLE);
        imageView.animate()
//                .translationX(50)
//                .translationY(50)
                .alpha(1.0f)
                .scaleX(1.2f)
                .scaleY(1.2f)
                .setDuration(500)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        // animateup();
                        //   animateHandDownn(1, imageView);
                    }
                });

        if (icon==1){
            ordinaryMCQLongTextViewOption1.animate()
//                .translationX(50)
//                .translationY(50)
                    .alpha(1.0f)
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(500)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            // animateup();
                            //    animatetvup();
                            animateHandDownn(1, imageView);
                        }
                    });

        }else if (icon==2){
            ordinaryMCQLongTextViewOption2.animate()
//                .translationX(50)
//                .translationY(50)
                    .alpha(1.0f)
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(500)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            // animateup();
                            //      animatetvup();
                            animateHandDownn(2, imageView);
                        }
                    });
        }else if (icon==3){
            ordinaryMCQLongTextViewOption3.animate()
//                .translationX(50)
//                .translationY(50)
                    .alpha(1.0f)
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(500)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            // animateup();
                            //      animatetvup();
                            animateHandDownn(3, imageView);
                        }
                    });
        }else if (icon==4){
            ordinaryMCQLongTextViewOption4.animate()
//                .translationX(50)
//                .translationY(50)
                    .alpha(1.0f)
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(500)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            // animateup();
                            //    animatetvup();
                            animateHandDownn(4, imageView);
                        }
                    });}


    }
    */

/*
    public void animateHandDownn(final int icon, final ImageView imageView) {

        imageView.animate()
//                .translationX(50)
//                .translationY(50)
                .alpha(1.0f)
                .scaleX(0.8f)
                .scaleY(0.8f)
                .setDuration(500)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        // animateup();
                    }
                });

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (icon==1){
                    ordinaryMCQLongTextViewOption1.animate()
//                .translationX(50)
//                .translationY(50)
                            .alpha(1.0f)
                            .scaleX(0.97f)
                            .scaleY(0.97f)
                            .setDuration(500)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    // animateup();
                                    //    animatetvup();
                                    animateHandUpp(1,imageView);
                                }
                            });

                }else if (icon==2){
                    ordinaryMCQLongTextViewOption2.animate()
//                .translationX(50)
//                .translationY(50)
                            .alpha(1.0f)
                            .scaleX(0.97f)
                            .scaleY(0.97f)
                            .setDuration(500)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    // animateup();
                                    animateHandUpp(2,imageView);
                                }
                            });
                }else if (icon==3){
                    ordinaryMCQLongTextViewOption3.animate()
//                .translationX(50)
//                .translationY(50)
                            .alpha(1.0f)
                            .scaleX(0.97f)
                            .scaleY(0.97f)
                            .setDuration(500)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    // animateup();
                                    //    animatetvup();
                                    animateHandUpp(3,imageView);
                                }
                            });
                }else if (icon==4){
                    ordinaryMCQLongTextViewOption4.animate()
//                .translationX(50)
//                .translationY(50)
                            .alpha(1.0f)
                            .scaleX(0.97f)
                            .scaleY(0.97f)
                            .setDuration(500)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    // animateup();
                                    //   animatetvup();

                                    animateHandUpp(4,imageView);
                                }
                            });}


            }
        }, 200);


    }
*/

    public void setUpTest() {
        showViews();

    }

    public void showViews() {
        fillUpTest();
        if (sharedPrefs.getString("GradeSelected", "Grade 6").equalsIgnoreCase("Grade 6")) {
            setBackGrounds(sharedPrefs.getString("SupervisedSubjectSelected", "English"));
        } else {
            setBackGroundsHigher(sharedPrefs.getString("SupervisedSubjectSelected", "English"));
        }

        ordinaryMCQLongTextViewQuestion.setVisibility(View.VISIBLE);
//        ordinaryMCQLongTextViewOption1.setVisibility(View.VISIBLE);
//        ordinaryMCQLongTextViewOption2.setVisibility(View.VISIBLE);
//        ordinaryMCQLongTextViewOption3.setVisibility(View.VISIBLE);
//        ordinaryMCQLongTextViewOption4.setVisibility(View.VISIBLE);

        ImageViewAlienAvatarLife.setVisibility(View.VISIBLE);
        ImageViewParrotAvatarLife.setVisibility(View.VISIBLE);
        ImageViewParrotAvatar.setVisibility(View.VISIBLE);
        ImageViewAlienAvatar.setVisibility(View.VISIBLE);
        setViews();

    }

    public void setBackGrounds(String subject) {
        switch (subject) {
            case ("English"):
                ordinaryMCQLongTextViewOption1.setBackgroundResource(R.drawable.english_bg);
                ordinaryMCQLongTextViewOption2.setBackgroundResource(R.drawable.english_bg);
                ordinaryMCQLongTextViewOption3.setBackgroundResource(R.drawable.english_bg);
                ordinaryMCQLongTextViewOption4.setBackgroundResource(R.drawable.english_bg);

                break;
            case ("Maths"):
                ordinaryMCQLongTextViewOption1.setBackgroundResource(R.drawable.maths_bg);
                ordinaryMCQLongTextViewOption2.setBackgroundResource(R.drawable.maths_bg);
                ordinaryMCQLongTextViewOption3.setBackgroundResource(R.drawable.maths_bg);
                ordinaryMCQLongTextViewOption4.setBackgroundResource(R.drawable.maths_bg);
                break;

            case ("Urdu"):
                ordinaryMCQLongTextViewOption1.setBackgroundResource(R.drawable.urdu_bg);
                ordinaryMCQLongTextViewOption2.setBackgroundResource(R.drawable.urdu_bg);
                ordinaryMCQLongTextViewOption3.setBackgroundResource(R.drawable.urdu_bg);
                ordinaryMCQLongTextViewOption4.setBackgroundResource(R.drawable.urdu_bg);

                break;

            case ("GeneralKnowledge"):
            case ("Science"):
                ordinaryMCQLongTextViewOption1.setBackgroundResource(R.drawable.science_bg);
                ordinaryMCQLongTextViewOption2.setBackgroundResource(R.drawable.science_bg);
                ordinaryMCQLongTextViewOption3.setBackgroundResource(R.drawable.science_bg);
                ordinaryMCQLongTextViewOption4.setBackgroundResource(R.drawable.science_bg);
                break;

        }
    }

    public void setBackGroundsHigher(String subject) {
        switch (subject) {
            case ("English"):
                // ordinaryMCQMainLayout.setBackgroundResource(R.mipmap.bg_pink);
                ordinaryMCQLongTextViewOption1.setBackgroundResource(R.drawable.english_bg);
                ordinaryMCQLongTextViewOption2.setBackgroundResource(R.drawable.english_bg);
                ordinaryMCQLongTextViewOption3.setBackgroundResource(R.drawable.english_bg);
                ordinaryMCQLongTextViewOption4.setBackgroundResource(R.drawable.english_bg);
                break;
            case ("Physics"):
                //   ordinaryMCQMainLayout.setBackgroundResource(R.mipmap.bg_green_b);
                ordinaryMCQLongTextViewOption1.setBackgroundResource(R.drawable.maths_bg);
                ordinaryMCQLongTextViewOption2.setBackgroundResource(R.drawable.maths_bg);
                ordinaryMCQLongTextViewOption3.setBackgroundResource(R.drawable.maths_bg);
                ordinaryMCQLongTextViewOption4.setBackgroundResource(R.drawable.maths_bg);
                break;

            case ("Pakistan Studies"):
                //   ordinaryMCQMainLayout.setBackgroundResource(R.mipmap.bg_green);
                ordinaryMCQLongTextViewOption1.setBackgroundResource(R.drawable.geography_bg);
                ordinaryMCQLongTextViewOption2.setBackgroundResource(R.drawable.geography_bg);
                ordinaryMCQLongTextViewOption3.setBackgroundResource(R.drawable.geography_bg);
                ordinaryMCQLongTextViewOption4.setBackgroundResource(R.drawable.geography_bg);
                break;

            case ("Biology"):
                //   ordinaryMCQMainLayout.setBackgroundResource(R.mipmap.bg_orange);
                ordinaryMCQLongTextViewOption1.setBackgroundResource(R.drawable.history_bg);
                ordinaryMCQLongTextViewOption2.setBackgroundResource(R.drawable.history_bg);
                ordinaryMCQLongTextViewOption3.setBackgroundResource(R.drawable.history_bg);
                ordinaryMCQLongTextViewOption4.setBackgroundResource(R.drawable.history_bg);
                break;
            case ("Chemistry"):
                //    ordinaryMCQMainLayout.setBackgroundResource(R.mipmap.bg_blue);
                ordinaryMCQLongTextViewOption1.setBackgroundResource(R.drawable.science_bg);
                ordinaryMCQLongTextViewOption2.setBackgroundResource(R.drawable.science_bg);
                ordinaryMCQLongTextViewOption3.setBackgroundResource(R.drawable.science_bg);
                ordinaryMCQLongTextViewOption4.setBackgroundResource(R.drawable.science_bg);
                break;

        }
    }

    public void fillUpTest() {

        if (test.getQuestion() != null) {
            ordinaryMCQLongTextViewQuestion.setText(test.getQuestion().getText());
        }

        if (test.getOptionList() != null) {
            generateRandom();
            ordinaryMCQLongTextViewOption1.setText(test.getOptionList().get(arr[0]).getText());
            ordinaryMCQLongTextViewOption2.setText(test.getOptionList().get(arr[1]).getText());
            ordinaryMCQLongTextViewOption3.setText(test.getOptionList().get(arr[2]).getText());
            ordinaryMCQLongTextViewOption4.setText(test.getOptionList().get(arr[3]).getText());

        }


    }


    public void setOnClickListeners(View view) {

        ordinaryMCQLongTextViewOption1.setOnClickListener(this);
        ordinaryMCQLongTextViewOption2.setOnClickListener(this);
        ordinaryMCQLongTextViewOption3.setOnClickListener(this);
        ordinaryMCQLongTextViewOption4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ordinaryMCQLongTextViewOption1:
                ordinaryMCQLongTextViewOption1.setOnClickListener(null);
                ordinaryMCQLongTextViewOption2.setOnClickListener(null);
                ordinaryMCQLongTextViewOption3.setOnClickListener(null);
                ordinaryMCQLongTextViewOption4.setOnClickListener(null);
                String uidAudio = topic + "Question" + (tempIndex + 1) + "Option" + (arr[0] + 1) + "Audio.mp3";
                selectedTextView = ordinaryMCQLongTextViewOption1;
                if (unSocratic == true) {
                    // setAudioDescription(uidAudio, ordinaryMCQTextViewOption1);
                    File myDirectory = new File(getContext().getFilesDir(), "TestAudios");
                    File file = new File(myDirectory, uidAudio);
                    if (file.exists()) {
                        Log.wtf("-thus", "File Exits  : " + uidAudio);
                        playOfflineAudio(Uri.fromFile(file));
                    } else {
                        compileAns(selectedTextView);
                        Log.wtf("-thus", "File DOES NOT Exits  : " + uidAudio + "  URI  " + Uri.fromFile(file));
                    }

                    //  playerOption1.setPlayWhenReady(true);
                } else {
                    compileAns(ordinaryMCQLongTextViewOption1);
                }

                break;
            case R.id.ordinaryMCQLongTextViewOption2:
                ordinaryMCQLongTextViewOption1.setOnClickListener(null);
                ordinaryMCQLongTextViewOption2.setOnClickListener(null);
                ordinaryMCQLongTextViewOption3.setOnClickListener(null);
                ordinaryMCQLongTextViewOption4.setOnClickListener(null);
                String uidAudio1 = topic + "Question" + (tempIndex + 1) + "Option" + (arr[1] + 1) + "Audio.mp3";
                selectedTextView = ordinaryMCQLongTextViewOption2;
                if (unSocratic == true) {
                    // setAudioDescription(uidAudio, ordinaryMCQTextViewOption1);
                    File myDirectory = new File(getContext().getFilesDir(), "TestAudios");
                    File file = new File(myDirectory, uidAudio1);
                    if (file.exists()) {
                        Log.wtf("-thus", "File Exits  : " + uidAudio1);
                        playOfflineAudio(Uri.fromFile(file));
                    } else {
                        compileAns(selectedTextView);
                        Log.wtf("-thus", "File DOES NOT Exits  : " + uidAudio1 + "  URI  " + Uri.fromFile(file));
                    }

                    //  playerOption1.setPlayWhenReady(true);
                } else {
                    compileAns(ordinaryMCQLongTextViewOption2);
                }

                break;
            case R.id.ordinaryMCQLongTextViewOption3:
                ordinaryMCQLongTextViewOption1.setOnClickListener(null);
                ordinaryMCQLongTextViewOption2.setOnClickListener(null);
                ordinaryMCQLongTextViewOption3.setOnClickListener(null);
                ordinaryMCQLongTextViewOption4.setOnClickListener(null);
                String uidAudio2 = topic + "Question" + (tempIndex + 1) + "Option" + (arr[2] + 1) + "Audio.mp3";
                selectedTextView = ordinaryMCQLongTextViewOption3;
                if (unSocratic == true) {
                    // setAudioDescription(uidAudio, ordinaryMCQTextViewOption1);
                    File myDirectory = new File(getContext().getFilesDir(), "TestAudios");
                    File file = new File(myDirectory, uidAudio2);
                    if (file.exists()) {
                        Log.wtf("-thus", "File Exits  : " + uidAudio2);
                        playOfflineAudio(Uri.fromFile(file));
                    } else {
                        compileAns(selectedTextView);
                        Log.wtf("-thus", "File DOES NOT Exits  : " + uidAudio2 + "  URI  " + Uri.fromFile(file));
                    }

                    //  playerOption1.setPlayWhenReady(true);
                } else {
                    compileAns(ordinaryMCQLongTextViewOption3);
                }

                break;
            case R.id.ordinaryMCQLongTextViewOption4:
                ordinaryMCQLongTextViewOption1.setOnClickListener(null);
                ordinaryMCQLongTextViewOption2.setOnClickListener(null);
                ordinaryMCQLongTextViewOption3.setOnClickListener(null);
                ordinaryMCQLongTextViewOption4.setOnClickListener(null);
                String uidAudio3 = topic + "Question" + (tempIndex + 1) + "Option" + (arr[3] + 1) + "Audio.mp3";
                selectedTextView = ordinaryMCQLongTextViewOption4;
                if (unSocratic == true) {
                    // setAudioDescription(uidAudio, ordinaryMCQTextViewOption1);
                    File myDirectory = new File(getContext().getFilesDir(), "TestAudios");
                    File file = new File(myDirectory, uidAudio3);
                    if (file.exists()) {
                        Log.wtf("-thus", "File Exits  : " + uidAudio3);
                        playOfflineAudio(Uri.fromFile(file));
                    } else {
                        compileAns(selectedTextView);
                        Log.wtf("-thus", "File DOES NOT Exits  : " + uidAudio3 + "  URI  " + Uri.fromFile(file));
                    }

                    //  playerOption1.setPlayWhenReady(true);
                } else {
                    compileAns(ordinaryMCQLongTextViewOption4);
                }

                break;
            default:
                break;

        }
    }

    public void compileAns(final TextView textView) {
        //stillInDemoState=false;

        start = false;
        T.cancel();
        int score = 0;
        int tScore = 0;
        if (test.getDifficultyLevel().equalsIgnoreCase("easy")) {
            tScore = 10;
        } else if (test.getDifficultyLevel().equalsIgnoreCase("medium")) {
            tScore = 20;
        } else if (test.getDifficultyLevel().equalsIgnoreCase("hard")) {
            tScore = 30;
        } else if (test.getDifficultyLevel().equalsIgnoreCase("bonus")) {
            tScore = 50;
        } else if (test.getDifficultyLevel().equalsIgnoreCase("superEasy")) {
            tScore = 5;
        }
        if (textView.getText().toString().equalsIgnoreCase(test.getAnswerList().get(0).getText())) {

            score = tScore;
            correctCount++;
            totalScore = totalScore + score;
            if (unSocratic == true) {
                appAnalytics.setOptionSelected(selectedSubject, topic, test.getType(), textView.getText().toString(), true);
                appAnalytics.setAnswer(selectedSubject, topic + "" + testIndex, topic, test.getType(), test.getAnswerList().size(), 1, true, this.count);
            }
            textView.setBackgroundResource(R.drawable.match_ttt_background_green);
            parrotFire();
        } else {
            if (unSocratic == true) {
                appAnalytics.setOptionSelected(selectedSubject, topic, test.getType(), textView.getText().toString(), false);
                appAnalytics.setAnswer(selectedSubject, topic + "" + testIndex, topic, test.getType(), test.getAnswerList().size(), 0, false, this.count);
            }
            textView.setBackgroundResource(R.drawable.match_ttt_background_red);
            alienFire();
        }

        // updateScore in Database HERE

        final Handler handler = new Handler();
        final int finalScore = score;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (unSocratic == true) {
                    ((SimpleTestActivity) activity).setExplanation(finalScore);
                } else {
                    ((SocraticActivity) activity).setVideo();
                }
                if (getFragmentManager() != null) {
                    getFragmentManager().beginTransaction()
                            .remove(OrdinaryMCQLong.this).commit();
                }

            }
        }, 1000);


    }

    public void generateRandom() {

        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        Collections.shuffle(Arrays.asList(arr));
        System.out.println(Arrays.toString(arr));

    }


    public void parrotFire() {
        fire1();

        alienLife = alienLife - deduct;
        ImageViewParrotFire.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                (realWidth - (ImageViewParrotAvatarLife.getX() + ImageViewParrotAvatarLife.getWidth() + ImageViewParrotAvatar.getWidth() + ImageViewAlienAvatarLife.getWidth() + ImageViewParrotAvatarLife.getX())),                 // toXDelta
                0,              // fromYDelta
                0);                // toYDelta
        animate.setDuration(300);           // duration of animation
        animate.setFillAfter(false);
        animate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ImageViewParrotFire.setVisibility(View.INVISIBLE);
                ImageViewAlienAvatarLife.setBackgroundResource(R.drawable.alien_life_line_pink);
                int level = 100 * (alienLife);   // pct goes from 0 to 100
                ImageViewAlienAvatarLife.getBackground().setLevel(level);

                ImageViewAlienHit.setVisibility(View.VISIBLE);
                ImageViewAlienAvatar.setImageResource(R.mipmap.alien_eye_close);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms
                        ImageViewAlienHit.setVisibility(View.INVISIBLE);
                        ImageViewAlienAvatar.setImageResource(R.mipmap.alien_eye_open);

                    }
                }, 500);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        ImageViewParrotFire.startAnimation(animate);
    }


    public void alienFire() {
        fire2();
        parrotLife = parrotLife - deduct;

        ImageViewAlienFire.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                -(realWidth - (ImageViewParrotAvatarLife.getX() + ImageViewParrotAvatarLife.getWidth() + ImageViewParrotAvatar.getWidth() + ImageViewAlienAvatarLife.getWidth() + ImageViewParrotAvatarLife.getX())),                 // toXDelta
                0,              // fromYDelta
                0);                // toYDelta
        animate.setDuration(300);           // duration of animation
        animate.setFillAfter(false);
        animate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ImageViewAlienFire.setVisibility(View.INVISIBLE);
                ImageViewParrotAvatarLife.setBackgroundResource(R.drawable.parrot_life_line_blue);
                int level = 100 * (parrotLife);   // pct goes from 0 to 100
                ImageViewParrotAvatarLife.getBackground().setLevel(level);

                ImageViewParrotHit.setVisibility(View.VISIBLE);
                ImageViewParrotAvatar.setImageResource(R.mipmap.parrot_eye_close);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms
                        ImageViewParrotHit.setVisibility(View.INVISIBLE);
                        ImageViewParrotAvatar.setImageResource(R.mipmap.parrot_eye_open);

                    }
                }, 500);


            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        ImageViewAlienFire.startAnimation(animate);
    }


    public void fire1() {
        SharedPreferences sharedPrefs1 = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        if (sharedPrefs1.getBoolean("SoundEnabled", true)) {
            final MediaPlayer mediaPlayer1 = MediaPlayer.create(getContext(), R.raw.fire_a);
            mediaPlayer1.start();
            mediaPlayer1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });
        }
    }

    public void jab() {
        SharedPreferences sharedPrefs1 = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        if (sharedPrefs1.getBoolean("SoundEnabled", true)) {
            if (getFragmentManager() != null) {
                final MediaPlayer mediaPlayer2 = MediaPlayer.create(getContext(), R.raw.jab);
                mediaPlayer2.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    }
                });
                mediaPlayer2.start();
            }
        }


    }

    public void fire2() {
        SharedPreferences sharedPrefs1 = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        if (sharedPrefs1.getBoolean("SoundEnabled", true)) {
            final MediaPlayer mediaPlayer1 = MediaPlayer.create(getContext(), R.raw.fire_b);
            mediaPlayer1.start();
            mediaPlayer1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });
        }
    }


    public void setViews() {
        ordinaryMCQLongTextViewOption1.setVisibility(View.INVISIBLE);
        ordinaryMCQLongTextViewOption2.setVisibility(View.INVISIBLE);
        ordinaryMCQLongTextViewOption3.setVisibility(View.INVISIBLE);
        ordinaryMCQLongTextViewOption4.setVisibility(View.INVISIBLE);

        if (selectedSubject.equalsIgnoreCase("English") || selectedSubject.equalsIgnoreCase("Maths") || selectedSubject.equalsIgnoreCase("Science")
                || selectedSubject.equalsIgnoreCase("Geography")) {

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    jab();
                    ordinaryMCQLongTextViewOption1.setVisibility(View.VISIBLE);

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            jab();
                            ordinaryMCQLongTextViewOption2.setVisibility(View.VISIBLE);

                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    jab();
                                    ordinaryMCQLongTextViewOption3.setVisibility(View.VISIBLE);
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            jab();
                                            ordinaryMCQLongTextViewOption4.setVisibility(View.VISIBLE);

                                            //setUpDemo();
                                        }
                                    }, 300);

                                }
                            }, 300);

                        }
                    }, 300);
                }
            }, 300);
        } else {

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    jab();
                    ordinaryMCQLongTextViewOption4.setVisibility(View.VISIBLE);

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            jab();
                            ordinaryMCQLongTextViewOption3.setVisibility(View.VISIBLE);

                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    jab();
                                    ordinaryMCQLongTextViewOption2.setVisibility(View.VISIBLE);
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            jab();
                                            ordinaryMCQLongTextViewOption1.setVisibility(View.VISIBLE);

                                            //setUpDemo();

                                        }
                                    }, 300);

                                }
                            }, 300);

                        }
                    }, 300);
                }
            }, 300);
        }

    }


    public void startTestTimer() {

        count = 0;

        T.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Runnable rn = (new Runnable() {
                    @Override
                    public void run() {
                        if (start == true) {
                            count++;
                        } else {
                            return;
                        }

                    }
                });
                rn.run();
            }
        }, 1000, 1000);


    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    SimpleExoPlayer simpleExoPlayer = null;

    private void playOfflineAudio(Uri uri) {

        DataSpec dataSpec = new DataSpec(uri);
        final FileDataSource fileDataSource = new FileDataSource();
        try {
            fileDataSource.open(dataSpec);
        } catch (FileDataSource.FileDataSourceException e) {
            e.printStackTrace();
        }

        DefaultRenderersFactory renderersFactory = new DefaultRenderersFactory(
                getContext(),
                null,
                DefaultRenderersFactory.EXTENSION_RENDERER_MODE_OFF
        );
        TrackSelector trackSelector = new DefaultTrackSelector();
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(
                renderersFactory,
                trackSelector
        );

        DataSource.Factory factory = new DataSource.Factory() {
            @Override
            public DataSource createDataSource() {
                return fileDataSource;
            }
        };
        final MediaSource audioSource = new ExtractorMediaSource(fileDataSource.getUri(),
                factory, new DefaultExtractorsFactory(), null, null);


        simpleExoPlayer.prepare(audioSource);
        simpleExoPlayer.setPlayWhenReady(true);
        simpleExoPlayer.addListener(new Player.EventListener() {
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if (playbackState == ExoPlayer.STATE_ENDED) {
                    if (compileCalled == false) {
                        compileCalled = true;
                        compileAns(selectedTextView);
                    }
                }
            }
        });

    }

    @Override
    public void onStop() {
        if (simpleExoPlayer != null) {
            simpleExoPlayer.release();
        }
        super.onStop();
    }

    @Override
    public void onDestroy() {
        if (simpleExoPlayer != null) {
            simpleExoPlayer.release();
        }
        super.onDestroy();
    }


}

