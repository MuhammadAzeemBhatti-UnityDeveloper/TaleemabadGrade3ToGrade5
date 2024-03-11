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

import androidx.constraintlayout.widget.ConstraintLayout;
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
import java.util.ArrayList;
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


public class OrdinaryMCQ extends Fragment implements View.OnClickListener {
    MediaPlayer mediaPlayerTouch;
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;


    TextView ordinaryMCQTextViewQuestion;
    TextView ordinaryMCQTextViewOption1, ordinaryMCQTextViewOption2, ordinaryMCQTextViewOption3, ordinaryMCQTextViewOption4;
    ImageView ordinaryMCQImageViewParrotFire, ordinaryMCQImageViewAlienAvatarLife, ordinaryMCQImageViewParrotAvatarLife, ordinaryMCQImageViewAlienFire;
    ImageView ordinaryMCQImageViewParrotAvatar, ordinaryMCQImageViewAlienAvatar;
    ImageView ordinaryMCQImageViewParrotHit, ordinaryMCQImageViewAlienHit;
    ConstraintLayout ordinaryMCQMainLayout;
    TextView TextViewParrotPlus, TextViewAlienPlus;
    Timer T = new Timer();
    int count = 0;
    boolean start = true;
    float questionFontSize = 22;
    float optionFontSize = 22;
    float questionBigFontSize = 32;
    ArrayList<TestJsonParseModel> testList = new ArrayList<>();

    int selectedQuestionIndex = 0;

    int realHeight;
    int realWidth;
    AppAnalytics appAnalytics;


    Integer[] arr = new Integer[4];
    int testId = -1;
    TestJsonParseModel test = null;
    Context mContext;
    Activity activity;

    private DataSource.Factory dataSourceFactory;
    private ExtractorsFactory extractorsFactory;
    private MediaSource mediaSource1;
    private MediaSource mediaSource2;
    private MediaSource mediaSource3;
    private MediaSource mediaSource4;
    private SimpleExoPlayer player;
    private SimpleExoPlayer playerOption1;
    private SimpleExoPlayer playerOption2;
    private SimpleExoPlayer playerOption3;
    private SimpleExoPlayer playerOption4;
    private final String streamUrl = "http://cdn.audios.taleemabad.com/QuestionBank/";
    private boolean compileCalled = false;
    private TextView selectedTextView = null;


    /*
    int DEMO_MODE=0;
    Boolean ordinaryMCQ;
    MediaPlayer mp;
    boolean demoStarted=false;
    String checkLang;
    boolean stillInDemoState =false;

     */


    @SuppressLint("ValidFragment")
    public OrdinaryMCQ(int testId, TestJsonParseModel test, Context context, Activity activity) {
        this.testId = testId;
        this.test = test;
        mContext = context;
        this.activity = activity;
        appAnalytics = new AppAnalytics(mContext);

        Log.wtf("-this", " TEST ID : " + testId);
    }

    public OrdinaryMCQ() {
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
        View view = inflater.inflate(R.layout.ordinary_mcq, container, false);

        player = ExoPlayerFactory.newSimpleInstance(getContext());
        playerOption1 = ExoPlayerFactory.newSimpleInstance(getContext());
        playerOption2 = ExoPlayerFactory.newSimpleInstance(getContext());
        playerOption3 = ExoPlayerFactory.newSimpleInstance(getContext());
        playerOption4 = ExoPlayerFactory.newSimpleInstance(getContext());
//        bgAudio();
        sharedPrefs = getActivity().getApplicationContext().getSharedPreferences("DefaultSPForTaleemabadGrade6", 0);
        editor = sharedPrefs.edit();
        initializeView(view);
        return view;
    }

    public void initializeView(final View view) {

        ordinaryMCQTextViewQuestion = view.findViewById(R.id.ordinaryMCQTextViewQuestion);
        ordinaryMCQTextViewOption1 = view.findViewById(R.id.ordinaryMCQTextViewOption1);
        ordinaryMCQTextViewOption2 = view.findViewById(R.id.ordinaryMCQTextViewOption2);
        ordinaryMCQTextViewOption3 = view.findViewById(R.id.ordinaryMCQTextViewOption3);
        ordinaryMCQTextViewOption4 = view.findViewById(R.id.ordinaryMCQTextViewOption4);

        ordinaryMCQImageViewParrotFire = view.findViewById(R.id.ordinaryMCQImageViewParrotFire);
        ordinaryMCQImageViewAlienFire = view.findViewById(R.id.ordinaryMCQImageViewAlienFire);
        ordinaryMCQImageViewParrotAvatarLife = view.findViewById(R.id.ordinaryMCQImageViewParrotAvatarLife);
        ordinaryMCQImageViewAlienAvatarLife = view.findViewById(R.id.ordinaryMCQImageViewAlienAvatarLife);
        ordinaryMCQImageViewParrotAvatar = view.findViewById(R.id.ordinaryMCQImageViewParrotAvatar);
        ordinaryMCQImageViewAlienAvatar = view.findViewById(R.id.ordinaryMCQImageViewAlienAvatar);
        ordinaryMCQImageViewParrotHit = view.findViewById(R.id.ordinaryMCQImageViewParrotHit);
        ordinaryMCQImageViewAlienHit = view.findViewById(R.id.ordinaryMCQImageViewAlienHit);
        ordinaryMCQMainLayout = view.findViewById(R.id.ordinaryMCQMainLayout);

        TextViewParrotPlus = view.findViewById(R.id.TextViewParrotPlus);
        TextViewAlienPlus = view.findViewById(R.id.TextViewAlienPlus);


        if (view != null) {
            ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {


                    @Override
                    public void onGlobalLayout() {

                        final DisplayMetrics metrics = new DisplayMetrics();
                        final WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                            windowManager.getDefaultDisplay().getRealMetrics(metrics);
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

                        //    Log.wtf("-this", "height : " + realHeight + "  Width : " + realWidth + " Inches : " + screenInches);


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
                        //   Log.wtf("-this", "height : " + realHeight + "  Option FONT SIZE : " + optionFontSize);


                        //  questionFontSize = 18;
                        ordinaryMCQTextViewQuestion.setTextSize(questionFontSize);
                        ordinaryMCQTextViewOption1.setTextSize(optionFontSize);
                        ordinaryMCQTextViewOption2.setTextSize(optionFontSize);
                        ordinaryMCQTextViewOption3.setTextSize(optionFontSize);
                        ordinaryMCQTextViewOption4.setTextSize(optionFontSize);

                        Log.wtf("-this", "height : " + realHeight + "  QuestionBig  FONT SIZE : " + questionBigFontSize);

                        ordinaryMCQImageViewAlienAvatarLife.setBackgroundResource(R.drawable.alien_life_line_pink);
                        int level = alienLife * (100);   // pct goes from 0 to 100
                        ordinaryMCQImageViewAlienAvatarLife.getBackground().setLevel(level);

                        ordinaryMCQImageViewParrotAvatarLife.setBackgroundResource(R.drawable.parrot_life_line_blue);
                        int level1 = parrotLife * (100);
                        ordinaryMCQImageViewParrotAvatarLife.getBackground().setLevel(level1);

                    }
                });
            }
            setUpTest();
//            setOnClickListeners(view);
            startTestTimer();

            //To retrieve
//            SharedPreferences sharedPrefForChecking = getActivity().getSharedPreferences("DemoOrdinaryMCQ", DEMO_MODE);
//            ordinaryMCQ = sharedPrefForChecking.getBoolean("OrdinaryMCQ", false); //0 is the default value

            setOnClickListeners(view);

        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {

        super.onDetach();
    }
/*
    @Override
    public void onDestroy() {
        if (mp!=null){
            mp.release();
        }
        super.onDestroy();
    }

    @Override
    public void onPause() {
        stillInDemoState =false;
        if (mp!=null){
            mp.release();
        }
        super.onPause();
    }
    @Override
    public void onResume() {


        if ( demoStarted ){
           // mp.setLooping(true);
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
*/



/*
    void setUpDemo(){
        if (!ordinaryMCQ){
            //fillUpTest();

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

         //   SimpleTestActivity.testActivityImageViewfeedback.setVisibility(View.GONE);
            ordinaryMCQImageViewParrotAvatarLife.setVisibility(View.GONE);
            ordinaryMCQImageViewParrotAvatar.setVisibility(View.GONE);
            ordinaryMCQImageViewAlienAvatar.setVisibility(View.GONE);
            ordinaryMCQImageViewAlienAvatarLife.setVisibility(View.GONE);


//          setOnClickListenr(view);
            final Handler handlerr = new Handler();
            handlerr.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    jab();
                    startDemo();

                }
            }, 100);
            SharedPreferences sharedPrefForSaving = getContext().getSharedPreferences("DemoOrdinaryMCQ", DEMO_MODE);
            SharedPreferences.Editor editor = sharedPrefForSaving.edit();
            editor.putBoolean("OrdinaryMCQ", true);
            editor.apply();

        }
        else {
            SimpleTestActivity.testActivityImageViewHome.setVisibility(View.VISIBLE);
            SimpleTestActivity.testActivityImageViewDaimond.setVisibility(View.VISIBLE);
           // SimpleTestActivity.testActivityImageViewfeedback.setVisibility(View.VISIBLE);
            ordinaryMCQImageViewParrotAvatarLife.setVisibility(View.VISIBLE);
            ordinaryMCQImageViewParrotAvatar.setVisibility(View.VISIBLE);
            ordinaryMCQImageViewAlienAvatar.setVisibility(View.VISIBLE);
            ordinaryMCQImageViewAlienAvatarLife.setVisibility(View.VISIBLE);

            demoStarted=false;

        }
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
        ordinaryMCQTextViewOption1.setVisibility(View.VISIBLE);
        ordinaryMCQTextViewOption2.setVisibility(View.VISIBLE);
        ordinaryMCQTextViewOption3.setVisibility(View.VISIBLE);
        ordinaryMCQTextViewOption4.setVisibility(View.VISIBLE);


        String ansText  = test.getAnswerList().get(0).getText();

        String option1_Text =ordinaryMCQTextViewOption1.getText().toString();
        String option2_Text =ordinaryMCQTextViewOption2.getText().toString();
        String option3_Text =ordinaryMCQTextViewOption3.getText().toString();
        String option4_Text =ordinaryMCQTextViewOption4.getText().toString();


        final ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(ordinaryMCQMainLayout);
        final ImageView imageview = new ImageView(activity);
        imageview.setImageResource(R.drawable.hand);
        imageview.setId(ViewCompat.generateViewId());
        imageview.requestLayout();

        // Toast.makeText(getContext(),"aa",Toast.LENGTH_SHORT).show();
        if (option1_Text.equals(ansText)){
            handlerr.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    jab();

                    ordinaryMCQMainLayout.addView(imageview);
                    constraintSet.connect(imageview.getId(),ConstraintSet.TOP, R.id.guidelineOption1Top,ConstraintSet.TOP,20);
//                    constraintSet.connect(imageview.getId(),ConstraintSet.END,R.id.guidelineOption1Right,ConstraintSet.START,80);
                    constraintSet.connect(imageview.getId(),ConstraintSet.START, R.id.guidelineOption1Left,ConstraintSet.END,40);
                    constraintSet.connect(imageview.getId(),ConstraintSet.BOTTOM, R.id.guidelineOption1Bottom,ConstraintSet.TOP,20);
                    constraintSet.applyTo(ordinaryMCQMainLayout);

                    imageview.getLayoutParams().width = ordinaryMCQTextViewOption1.getMeasuredHeight()/2;
                    imageview.getLayoutParams().height = ordinaryMCQTextViewOption1.getMeasuredHeight()/2;
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

                    ordinaryMCQMainLayout.addView(imageview);
                    constraintSet.connect(imageview.getId(),ConstraintSet.TOP, R.id.guidelineOption1Top,ConstraintSet.TOP,20);
//                    constraintSet.connect(imageview.getId(),ConstraintSet.END,R.id.guidelineOption2Right,ConstraintSet.START,80);
                    constraintSet.connect(imageview.getId(),ConstraintSet.START, R.id.guidelineOption2Left,ConstraintSet.END,40);
                    constraintSet.connect(imageview.getId(),ConstraintSet.BOTTOM, R.id.guidelineOption1Bottom,ConstraintSet.TOP,20);
                    constraintSet.applyTo(ordinaryMCQMainLayout);
                    //     imageview.setPadding(5,25,25,5);
//                    imageview.getLayoutParams().width = 120;
//                    imageview.getLayoutParams().height = 120;

                    imageview.getLayoutParams().width = ordinaryMCQTextViewOption2.getMeasuredHeight()/2;
                    imageview.getLayoutParams().height = ordinaryMCQTextViewOption2.getMeasuredHeight()/2;
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

                    ordinaryMCQMainLayout.addView(imageview);
                    constraintSet.connect(imageview.getId(),ConstraintSet.TOP, R.id.guidelineOption3Top,ConstraintSet.TOP,20);
//                    constraintSet.connect(imageview.getId(),ConstraintSet.END,R.id.guidelineOption1Right,ConstraintSet.START,80);
                    constraintSet.connect(imageview.getId(),ConstraintSet.START, R.id.guidelineOption1Left,ConstraintSet.END,40);
                    constraintSet.connect(imageview.getId(),ConstraintSet.BOTTOM, R.id.guidelineOption3Bottom,ConstraintSet.TOP,20);
                    constraintSet.applyTo(ordinaryMCQMainLayout);
                    //       imageview.getLayoutParams().width = 120;

                    imageview.getLayoutParams().width = ordinaryMCQTextViewOption3.getMeasuredHeight()/2;
                    imageview.getLayoutParams().height = ordinaryMCQTextViewOption3.getMeasuredHeight()/2;
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

                    ordinaryMCQMainLayout.addView(imageview);
                    constraintSet.connect(imageview.getId(),ConstraintSet.TOP, R.id.guidelineOption3Top,ConstraintSet.TOP,20);
                    //                  constraintSet.connect(imageview.getId(),ConstraintSet.END,R.id.guidelineOption2Right,ConstraintSet.START,80);
                    constraintSet.connect(imageview.getId(),ConstraintSet.START, R.id.guidelineOption2Left, ConstraintSet.END,40);
                    constraintSet.connect(imageview.getId(),ConstraintSet.BOTTOM, R.id.guidelineOption3Bottom,ConstraintSet.TOP,20);
                    constraintSet.applyTo(ordinaryMCQMainLayout);
//                    imageview.getLayoutParams().width = 120;
//                    imageview.getLayoutParams().height = 120;

                    imageview.getLayoutParams().width = ordinaryMCQTextViewOption4.getMeasuredHeight()/2;
                    imageview.getLayoutParams().height = ordinaryMCQTextViewOption4.getMeasuredHeight()/2;
                    imageview.bringToFront();
                    //tap_icon4.setVisibility(View.VISIBLE);

                    //  setMargins(imageview,5,5,25,5);
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
            ordinaryMCQTextViewOption1.animate()
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
            ordinaryMCQTextViewOption2.animate()
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
            ordinaryMCQTextViewOption3.animate()
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
            ordinaryMCQTextViewOption4.animate()
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
                    ordinaryMCQTextViewOption1.animate()
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
                    ordinaryMCQTextViewOption2.animate()
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
                    ordinaryMCQTextViewOption3.animate()
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
                    ordinaryMCQTextViewOption4.animate()
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
                            });

                }


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
//        ordinaryMCQTextViewOption1.setVisibility(View.VISIBLE);
//        ordinaryMCQTextViewOption2.setVisibility(View.VISIBLE);
//        ordinaryMCQTextViewOption3.setVisibility(View.VISIBLE);
//        ordinaryMCQTextViewOption4.setVisibility(View.VISIBLE);
        ordinaryMCQImageViewParrotAvatar.setVisibility(View.VISIBLE);
        ordinaryMCQImageViewParrotAvatarLife.setVisibility(View.VISIBLE);
        ordinaryMCQImageViewAlienAvatar.setVisibility(View.VISIBLE);
        ordinaryMCQImageViewAlienAvatarLife.setVisibility(View.VISIBLE);
        setViews();

    }

    public void setBackGrounds(String subject) {
        switch (subject) {
            case ("English"):
                // ordinaryMCQMainLayout.setBackgroundResource(R.mipmap.bg_pink);
                ordinaryMCQTextViewOption1.setBackgroundResource(R.drawable.english_bg);
                ordinaryMCQTextViewOption2.setBackgroundResource(R.drawable.english_bg);
                ordinaryMCQTextViewOption3.setBackgroundResource(R.drawable.english_bg);
                ordinaryMCQTextViewOption4.setBackgroundResource(R.drawable.english_bg);

                break;
            case ("Maths"):
                //   ordinaryMCQMainLayout.setBackgroundResource(R.mipmap.bg_green_b);
                ordinaryMCQTextViewOption1.setBackgroundResource(R.drawable.maths_bg);
                ordinaryMCQTextViewOption2.setBackgroundResource(R.drawable.maths_bg);
                ordinaryMCQTextViewOption3.setBackgroundResource(R.drawable.maths_bg);
                ordinaryMCQTextViewOption4.setBackgroundResource(R.drawable.maths_bg);
                break;

            case ("Urdu"):
                //    ordinaryMCQMainLayout.setBackgroundResource(R.mipmap.bg_purple);
                ordinaryMCQTextViewOption1.setBackgroundResource(R.drawable.urdu_bg);
                ordinaryMCQTextViewOption2.setBackgroundResource(R.drawable.urdu_bg);
                ordinaryMCQTextViewOption3.setBackgroundResource(R.drawable.urdu_bg);
                ordinaryMCQTextViewOption4.setBackgroundResource(R.drawable.urdu_bg);

                break;
            case ("GeneralKnowledge"):
            case ("Science"):
                //    ordinaryMCQMainLayout.setBackgroundResource(R.mipmap.bg_blue);
                ordinaryMCQTextViewOption1.setBackgroundResource(R.drawable.science_bg);
                ordinaryMCQTextViewOption2.setBackgroundResource(R.drawable.science_bg);
                ordinaryMCQTextViewOption3.setBackgroundResource(R.drawable.science_bg);
                ordinaryMCQTextViewOption4.setBackgroundResource(R.drawable.science_bg);
                break;

        }
    }

    public void setBackGroundsHigher(String subject) {
        switch (subject) {
            case ("English"):
                // ordinaryMCQMainLayout.setBackgroundResource(R.mipmap.bg_pink);
                ordinaryMCQTextViewOption1.setBackgroundResource(R.drawable.english_bg);
                ordinaryMCQTextViewOption2.setBackgroundResource(R.drawable.english_bg);
                ordinaryMCQTextViewOption3.setBackgroundResource(R.drawable.english_bg);
                ordinaryMCQTextViewOption4.setBackgroundResource(R.drawable.english_bg);

                break;
            case ("Physics"):
                //   ordinaryMCQMainLayout.setBackgroundResource(R.mipmap.bg_green_b);
                ordinaryMCQTextViewOption1.setBackgroundResource(R.drawable.maths_bg);
                ordinaryMCQTextViewOption2.setBackgroundResource(R.drawable.maths_bg);
                ordinaryMCQTextViewOption3.setBackgroundResource(R.drawable.maths_bg);
                ordinaryMCQTextViewOption4.setBackgroundResource(R.drawable.maths_bg);
                break;

            case ("Pakistan Studies"):
                //   ordinaryMCQMainLayout.setBackgroundResource(R.mipmap.bg_green);
                ordinaryMCQTextViewOption1.setBackgroundResource(R.drawable.geography_bg);
                ordinaryMCQTextViewOption2.setBackgroundResource(R.drawable.geography_bg);
                ordinaryMCQTextViewOption3.setBackgroundResource(R.drawable.geography_bg);
                ordinaryMCQTextViewOption4.setBackgroundResource(R.drawable.geography_bg);
                break;

            case ("Biology"):
                //   ordinaryMCQMainLayout.setBackgroundResource(R.mipmap.bg_orange);
                ordinaryMCQTextViewOption1.setBackgroundResource(R.drawable.history_bg);
                ordinaryMCQTextViewOption2.setBackgroundResource(R.drawable.history_bg);
                ordinaryMCQTextViewOption3.setBackgroundResource(R.drawable.history_bg);
                ordinaryMCQTextViewOption4.setBackgroundResource(R.drawable.history_bg);
                break;
            case ("Chemistry"):
                //    ordinaryMCQMainLayout.setBackgroundResource(R.mipmap.bg_blue);
                ordinaryMCQTextViewOption1.setBackgroundResource(R.drawable.science_bg);
                ordinaryMCQTextViewOption2.setBackgroundResource(R.drawable.science_bg);
                ordinaryMCQTextViewOption3.setBackgroundResource(R.drawable.science_bg);
                ordinaryMCQTextViewOption4.setBackgroundResource(R.drawable.science_bg);
                break;

        }
    }

    public void fillUpTest() {
        generateRandom();
        if (test.getQuestion() != null) {
            ordinaryMCQTextViewQuestion.setText(test.getQuestion().getText());
        }
        if (test.getOptionList() != null) {
            ordinaryMCQTextViewOption1.setText(test.getOptionList().get(arr[0]).getText());
            ordinaryMCQTextViewOption2.setText(test.getOptionList().get(arr[1]).getText());
            ordinaryMCQTextViewOption3.setText(test.getOptionList().get(arr[2]).getText());
            ordinaryMCQTextViewOption4.setText(test.getOptionList().get(arr[3]).getText());

            String uidAudio1 = topic + "Question" + (tempIndex + 1) + "Option" + (arr[0] + 1) + "Audio";
            String uidAudio2 = topic + "Question" + (tempIndex + 1) + "Option" + (arr[1] + 1) + "Audio";
            String uidAudio3 = topic + "Question" + (tempIndex + 1) + "Option" + (arr[2] + 1) + "Audio";
            String uidAudio4 = topic + "Question" + (tempIndex + 1) + "Option" + (arr[3] + 1) + "Audio";

//            final Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//
//                    setAudioDescription(uidAudio1, uidAudio2, uidAudio3, uidAudio4);
//
//                }
//            }, 0);
        }
    }


    public void setOnClickListeners(View view) {

        ordinaryMCQTextViewOption1.setOnClickListener(this);
        ordinaryMCQTextViewOption2.setOnClickListener(this);
        ordinaryMCQTextViewOption3.setOnClickListener(this);
        ordinaryMCQTextViewOption4.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        final Handler handler = new Handler();
        switch (v.getId()) {
            case R.id.ordinaryMCQTextViewOption1:
                // v.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.on_click));
                String uidAudio = topic + "Question" + (tempIndex + 1) + "Option" + (arr[0] + 1) + "Audio.mp3";
                ordinaryMCQTextViewOption1.setOnClickListener(null);
                ordinaryMCQTextViewOption2.setOnClickListener(null);
                ordinaryMCQTextViewOption3.setOnClickListener(null);
                ordinaryMCQTextViewOption4.setOnClickListener(null);

                if (unSocratic == true) {
                    // setAudioDescription(uidAudio, ordinaryMCQTextViewOption1);
                    File myDirectory = new File(getContext().getFilesDir(), "TestAudios");
                    File file = new File(myDirectory,uidAudio);
                    if(file.exists()){
                        Log.wtf("-thus","File Exits  : "+uidAudio);
                        playOfflineAudio(Uri.fromFile(file));
                    } else {
                        Log.wtf("-thus","File DOES NOT Exits  : "+uidAudio + "  URI  "+Uri.fromFile(file));
                        compileAns(ordinaryMCQTextViewOption1);
                    }
                    selectedTextView = ordinaryMCQTextViewOption1;
                  //  playerOption1.setPlayWhenReady(true);
                } else {
                    compileAns(ordinaryMCQTextViewOption1);
                }


                // compileAns(ordinaryMCQTextViewOption1);

                break;
            case R.id.ordinaryMCQTextViewOption2:
                String uidAudio1 = topic + "Question" + (tempIndex + 1) + "Option" + (arr[1] + 1) + "Audio.mp3";
                ordinaryMCQTextViewOption1.setOnClickListener(null);
                ordinaryMCQTextViewOption2.setOnClickListener(null);
                ordinaryMCQTextViewOption3.setOnClickListener(null);
                ordinaryMCQTextViewOption4.setOnClickListener(null);

                if (unSocratic == true) {
                    //   setAudioDescription(uidAudio1, ordinaryMCQTextViewOption2);
                    File myDirectory = new File(getContext().getFilesDir(), "TestAudios");
                    File file = new File(myDirectory,uidAudio1);
                    if(file.exists()){
                        Log.wtf("-thus","File Exits  : "+uidAudio1);
                        playOfflineAudio(Uri.fromFile(file));
                    } else {
                        Log.wtf("-thus","File DOES NOT Exits  : "+uidAudio1 + "  URI  "+Uri.fromFile(file));
                        compileAns(ordinaryMCQTextViewOption2);
                    }
                    selectedTextView = ordinaryMCQTextViewOption2;
                  //  playerOption2.setPlayWhenReady(true);
                } else {
                    compileAns(ordinaryMCQTextViewOption2);
                }


                break;
            case R.id.ordinaryMCQTextViewOption3:
                //   v.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.on_click));

                String uidAudio2 = topic + "Question" + (tempIndex + 1) + "Option" + (arr[2] + 1) + "Audio.mp3";
                ordinaryMCQTextViewOption1.setOnClickListener(null);
                ordinaryMCQTextViewOption2.setOnClickListener(null);
                ordinaryMCQTextViewOption3.setOnClickListener(null);
                ordinaryMCQTextViewOption4.setOnClickListener(null);

                if (unSocratic == true) {
                    //  setAudioDescription(uidAudio2, ordinaryMCQTextViewOption3);
                    File myDirectory = new File(getContext().getFilesDir(), "TestAudios");
                    File file = new File(myDirectory,uidAudio2);
                    if(file.exists()){
                        Log.wtf("-thus","File Exits  : "+uidAudio2);
                        playOfflineAudio(Uri.fromFile(file));
                    } else {
                        Log.wtf("-thus","File DOES NOT Exits  : "+uidAudio2 + "  URI  "+Uri.fromFile(file));
                        compileAns(ordinaryMCQTextViewOption3);
                    }
                    selectedTextView = ordinaryMCQTextViewOption3;
                 //   playerOption3.setPlayWhenReady(true);
                } else {
                    compileAns(ordinaryMCQTextViewOption3);
                }

                //  compileAns(ordinaryMCQTextViewOption3);

                break;
            case R.id.ordinaryMCQTextViewOption4:
                //  v.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.on_click));

                String uidAudio3 = topic + "Question" + (tempIndex + 1) + "Option" + (arr[3] + 1) + "Audio.mp3";
                ordinaryMCQTextViewOption1.setOnClickListener(null);
                ordinaryMCQTextViewOption2.setOnClickListener(null);
                ordinaryMCQTextViewOption3.setOnClickListener(null);
                ordinaryMCQTextViewOption4.setOnClickListener(null);

                if (unSocratic == true) {
                    File myDirectory = new File(getContext().getFilesDir(), "TestAudios");
                    File file = new File(myDirectory,uidAudio3);
                    if(file.exists()){
                        Log.wtf("-thus","File Exits  : "+uidAudio3);
                        playOfflineAudio(Uri.fromFile(file));
                    } else {
                        Log.wtf("-thus","File DOES NOT Exits  : "+uidAudio3 + "  URI  "+Uri.fromFile(file));
                        compileAns(ordinaryMCQTextViewOption4);
                    }
                    selectedTextView = ordinaryMCQTextViewOption4;
                  //  playerOption4.setPlayWhenReady(true);
                    // setAudioDescription(uidAudio3, ordinaryMCQTextViewOption4);
                } else {
                    compileAns(ordinaryMCQTextViewOption4);
                }

                //  compileAns(ordinaryMCQTextViewOption4);
                break;

        }

    }

    public void compileAns(final TextView textView) {
        //stillInDemoState =false;
        ordinaryMCQTextViewOption1.setOnClickListener(null);
        ordinaryMCQTextViewOption2.setOnClickListener(null);
        ordinaryMCQTextViewOption3.setOnClickListener(null);
        ordinaryMCQTextViewOption4.setOnClickListener(null);
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
                            .remove(OrdinaryMCQ.this).commit();
                }

            }
        }, 1000);

    }


    public void setViews() {
        ordinaryMCQTextViewOption1.setVisibility(View.INVISIBLE);
        ordinaryMCQTextViewOption2.setVisibility(View.INVISIBLE);
        ordinaryMCQTextViewOption3.setVisibility(View.INVISIBLE);
        ordinaryMCQTextViewOption4.setVisibility(View.INVISIBLE);

        if (selectedSubject.equalsIgnoreCase("English") || selectedSubject.equalsIgnoreCase("Maths") || selectedSubject.equalsIgnoreCase("Science")
                || selectedSubject.equalsIgnoreCase("Geography")) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    jab();
                    ordinaryMCQTextViewOption1.setVisibility(View.VISIBLE);

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            jab();
                            ordinaryMCQTextViewOption2.setVisibility(View.VISIBLE);

                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    jab();
                                    ordinaryMCQTextViewOption3.setVisibility(View.VISIBLE);
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            jab();
                                            ordinaryMCQTextViewOption4.setVisibility(View.VISIBLE);

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
                    ordinaryMCQTextViewOption4.setVisibility(View.VISIBLE);

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            jab();
                            ordinaryMCQTextViewOption3.setVisibility(View.VISIBLE);
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    jab();
                                    ordinaryMCQTextViewOption2.setVisibility(View.VISIBLE);
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            jab();
                                            ordinaryMCQTextViewOption1.setVisibility(View.VISIBLE);

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


    public void parrotFire() {
        fire1();
        alienLife = alienLife - deduct;
        ordinaryMCQImageViewParrotFire.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                (realWidth - (ordinaryMCQImageViewParrotAvatarLife.getX() + ordinaryMCQImageViewParrotAvatarLife.getWidth() + ordinaryMCQImageViewParrotAvatar.getWidth() + ordinaryMCQImageViewAlienAvatarLife.getWidth() + ordinaryMCQImageViewParrotAvatarLife.getX())),                 // toXDelta
                0,              // fromYDelta
                0);                // toYDelta
        animate.setDuration(350);           // duration of animation
        animate.setFillAfter(false);
        animate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ordinaryMCQImageViewParrotFire.setVisibility(View.INVISIBLE);
                ordinaryMCQImageViewAlienAvatarLife.setBackgroundResource(R.drawable.alien_life_line_pink);
                int level = 100 * (alienLife);   // pct goes from 0 to 100
                ordinaryMCQImageViewAlienAvatarLife.getBackground().setLevel(level);

                ordinaryMCQImageViewAlienHit.setVisibility(View.VISIBLE);
                ordinaryMCQImageViewAlienAvatar.setImageResource(R.mipmap.alien_eye_close);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms
                        ordinaryMCQImageViewAlienHit.setVisibility(View.INVISIBLE);
                        ordinaryMCQImageViewAlienAvatar.setImageResource(R.mipmap.alien_eye_open);

                    }
                }, 550);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        ordinaryMCQImageViewParrotFire.startAnimation(animate);
    }


    public void alienFire() {
        fire2();
        parrotLife = parrotLife - deduct;

        ordinaryMCQImageViewAlienFire.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                -(realWidth - (ordinaryMCQImageViewParrotAvatarLife.getX() + ordinaryMCQImageViewParrotAvatarLife.getWidth() + ordinaryMCQImageViewParrotAvatar.getWidth() + ordinaryMCQImageViewAlienAvatarLife.getWidth() + ordinaryMCQImageViewParrotAvatarLife.getX())),                 // toXDelta
                0,              // fromYDelta
                0);                // toYDelta
        animate.setDuration(350);           // duration of animation
        animate.setFillAfter(false);
        animate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ordinaryMCQImageViewAlienFire.setVisibility(View.INVISIBLE);
                ordinaryMCQImageViewParrotAvatarLife.setBackgroundResource(R.drawable.parrot_life_line_blue);
                int level = 100 * (parrotLife);   // pct goes from 0 to 100
                ordinaryMCQImageViewParrotAvatarLife.getBackground().setLevel(level);

                ordinaryMCQImageViewParrotHit.setVisibility(View.VISIBLE);
                ordinaryMCQImageViewParrotAvatar.setImageResource(R.mipmap.parrot_eye_close);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms
                        ordinaryMCQImageViewParrotHit.setVisibility(View.INVISIBLE);
                        ordinaryMCQImageViewParrotAvatar.setImageResource(R.mipmap.parrot_eye_open);

                    }
                }, 550);


            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        ordinaryMCQImageViewAlienFire.startAnimation(animate);
    }


    public void fire1()
    {
	SharedPreferences sharedPrefs1 = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        if(sharedPrefs1.getBoolean("SoundEnabled",true)){
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
         if(sharedPrefs1.getBoolean("SoundEnabled",true)){
		if(getFragmentManager() != null) {
	            final MediaPlayer mediaPlayer2 = MediaPlayer.create(getContext(), R.raw.jab);
        	    mediaPlayer2.start();
	            mediaPlayer2.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
        	        @Override
                	public void onCompletion(MediaPlayer mp) {
	                    mp.release();
        	        }
            	});
        	}
	 }
    }

    public void fire2()
    {
	SharedPreferences sharedPrefs1 = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        if(sharedPrefs1.getBoolean("SoundEnabled",true)){
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

    public void tapAudio() {
        final MediaPlayer mediaPlayerTouch = MediaPlayer.create(getContext(), R.raw.touchone);
        mediaPlayerTouch.start();
        mediaPlayerTouch.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
    }

    public void generateRandom() {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        Collections.shuffle(Arrays.asList(arr));
        System.out.println(Arrays.toString(arr));

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
        player.setPlayWhenReady(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        player.setPlayWhenReady(false);
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
                    if(compileCalled == false) {
                        compileCalled = true;
                        compileAns(selectedTextView);
                    }
                }
            }
        });

    }

    @Override
    public void onStop() {
        if(simpleExoPlayer != null){
            simpleExoPlayer.release();
        }
        super.onStop();
    }

    @Override
    public void onDestroy() {
        if(simpleExoPlayer != null){
            simpleExoPlayer.release();
        }
        super.onDestroy();
    }



}

