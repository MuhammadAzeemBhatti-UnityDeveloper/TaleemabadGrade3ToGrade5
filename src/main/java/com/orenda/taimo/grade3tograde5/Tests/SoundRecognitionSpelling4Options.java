package com.orenda.taimo.grade3tograde5.Tests;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;

import android.os.Handler;
import androidx.annotation.RequiresApi;
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
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.orenda.taimo.grade3tograde5.Models.TestJsonParseModel;
import com.orenda.taimo.grade3tograde5.R;
import com.orenda.taimo.grade3tograde5.SimpleTestActivity;
import com.orenda.taimo.grade3tograde5.SocraticActivity;

import firebase.analytics.AppAnalytics;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

//import static com.facebook.FacebookSdk.getApplicationContext;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.alienLife;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.correctCount;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.deduct;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.parrotLife;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.selectedSubject;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.testIndex;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.topic;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.totalScore;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.unSocratic;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class SoundRecognitionSpelling4Options extends Fragment implements View.OnClickListener {

    private TextView option1, option2, option3, option4, questionTextViewQuestion;
    private ImageView playSound;
    private String soundName;

    MediaPlayer mediaPlayerTouch;
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;


    ImageView ordinaryMCQImageViewParrotFire, ordinaryMCQImageViewAlienAvatarLife, ordinaryMCQImageViewParrotAvatarLife, ordinaryMCQImageViewAlienFire;
    ImageView ordinaryMCQImageViewParrotAvatar, ordinaryMCQImageViewAlienAvatar;
    ImageView ordinaryMCQImageViewParrotHit, ordinaryMCQImageViewAlienHit;
    ConstraintLayout ordinaryMCQMainLayout;
    TextView TextViewParrotPlus, TextViewAlienPlus;
    AppAnalytics appAnalytics;
    Timer T = new Timer();
    int count = 0;
    boolean start = true;
    float questionFontSize = 22;
    float optionFontSize = 22;
    float questionBigFontSize = 32;

    int realHeight;
    int realWidth;


    Integer[] arr = new Integer[4];

    int testId = -1;
    TestJsonParseModel test = null;
    Context mContext;
    Activity activity;

    private Boolean first_time=true;


    /*
    int DEMO_MODE=0;
    Boolean ordinaryMCQ;
    MediaPlayer mp;
    boolean demoStarted=false;
    String checkLang;
    ConstraintLayout mainConstraintLayout, playImageConstraintLayout;
    ImageView handOnImage;
    boolean ClickOnBigImage=false;
     */




    public SoundRecognitionSpelling4Options(int testId, TestJsonParseModel test, Context context, Activity activity) {
        this.testId = testId;
        this.test = test;
        mContext = context;
        this.activity = activity;
        Log.wtf("-test", " TEST ID : " + testId);
        Log.wtf("-test", " TEST Question : " + test.getQuestion().getAudio());
        Log.wtf("-test", " TEST Answer : " + test.getAnswerList().get(0).getText());
        Log.wtf("-test", " TEST Option 1 : " + test.getOptionList().get(0).getText());
        Log.wtf("-test", " TEST Option 2  : " + test.getOptionList().get(1).getText());
        Log.wtf("-test", " TEST Option 3  : " + test.getOptionList().get(2).getText());
        Log.wtf("-test", " TEST Option 4  : " + test.getOptionList().get(3).getText());

    }


    public SoundRecognitionSpelling4Options() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        sharedPrefs = getActivity().getApplicationContext().getSharedPreferences("DefaultSPForTaleemabadGrade6", 0);
        editor = sharedPrefs.edit();
        View view = inflater.inflate(R.layout.sound_recognition_spelling4_options, container, false);
        appAnalytics = new AppAnalytics(mContext);
        mediaPlayerTouch=new MediaPlayer();
        initializeViews(view);
        return view;

    }

    private void initializeViews(View view) {
        loadAudio(test.getQuestion().getAudio());
        option1 = view.findViewById(R.id.Option1x1);
        option2 = view.findViewById(R.id.Option1x2);
        option3 = view.findViewById(R.id.Option1x3);
        option4 = view.findViewById(R.id.Option1x4);
        questionTextViewQuestion = view.findViewById(R.id.questionTextViewQuestion);
        playSound = view.findViewById(R.id.play_sound_image);
        option1.setVisibility(View.GONE);
        option2.setVisibility(View.GONE);
        option3.setVisibility(View.GONE);
        option4.setVisibility(View.GONE);


        ordinaryMCQImageViewParrotFire = view.findViewById(R.id.ordinaryMCQImageViewParrotFire);
        ordinaryMCQImageViewAlienFire = view.findViewById(R.id.ordinaryMCQImageViewAlienFire);
        ordinaryMCQImageViewParrotAvatarLife = view.findViewById(R.id.ordinaryMCQImageViewParrotAvatarLife);
        ordinaryMCQImageViewAlienAvatarLife = view.findViewById(R.id.ordinaryMCQImageViewAlienAvatarLife);
        ordinaryMCQImageViewParrotAvatar = view.findViewById(R.id.ordinaryMCQImageViewParrotAvatar);
        ordinaryMCQImageViewAlienAvatar = view.findViewById(R.id.ordinaryMCQImageViewAlienAvatar);
        ordinaryMCQImageViewParrotHit = view.findViewById(R.id.ordinaryMCQImageViewParrotHit);
        ordinaryMCQImageViewAlienHit = view.findViewById(R.id.ordinaryMCQImageViewAlienHit);

        TextViewParrotPlus = view.findViewById(R.id.TextViewParrotPlus);
        TextViewAlienPlus = view.findViewById(R.id.TextViewAlienPlus);

//        mainConstraintLayout=view.findViewById(R.id.mainConstraintLayout_id);
//        playImageConstraintLayout=view.findViewById(R.id.constraint_id);


        if (view != null) {
            ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {


                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public void onGlobalLayout() {

                        final DisplayMetrics metrics = new DisplayMetrics();
                        final WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);

                        windowManager.getDefaultDisplay().getRealMetrics(metrics);

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
                        questionTextViewQuestion.setTextSize(questionFontSize);
                        option1.setTextSize(optionFontSize);
                        option2.setTextSize(optionFontSize);
                        option3.setTextSize(optionFontSize);
                        option3.setTextSize(optionFontSize);

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

            setOnClickListeners(view);
            startTestTimer();


            //To retrieve
            //To retrieve
//            SharedPreferences sharedPrefForChecking = getActivity().getSharedPreferences("DemoSoundRecognitionSpelling4Options", DEMO_MODE);
//            ordinaryMCQ = sharedPrefForChecking.getBoolean("SoundRecognitionSpelling4Options", false); //0 is the default value

            if (first_time){
                fillupTest();
            }

          /*  if (!ordinaryMCQ) {
//                SimpleTestActivity.testActivityImageViewHome.setVisibility(View.GONE);
//                SimpleTestActivity.testActivityImageViewDaimond.setVisibility(View.GONE);
                if (SocraticActivity.testActivityImageViewHome!=null){
                    SocraticActivity.testActivityImageViewHome.setVisibility(View.GONE);
                }
                if (SocraticActivity.testActivityImageViewDaimond!=null){
                    SocraticActivity.testActivityImageViewDaimond.setVisibility(View.GONE);
                }
                if (SimpleTestActivity.testActivityImageViewHome!=null){
                    SocraticActivity.testActivityImageViewHome.setVisibility(View.GONE);
                }
                if (SimpleTestActivity.testActivityImageViewDaimond!=null){
                    SocraticActivity.testActivityImageViewDaimond.setVisibility(View.GONE);
                }

                // SimpleTestActivity.testActivityImageViewfeedback.setVisibility(View.GONE);
                ordinaryMCQImageViewParrotAvatarLife.setVisibility(View.GONE);
                ordinaryMCQImageViewParrotAvatar.setVisibility(View.GONE);
                ordinaryMCQImageViewAlienAvatar.setVisibility(View.GONE);
                ordinaryMCQImageViewAlienAvatarLife.setVisibility(View.GONE);

                final ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(playImageConstraintLayout);
                handOnImage = new ImageView(activity);
                handOnImage.setImageResource(R.drawable.hand);
                handOnImage.setId(ViewCompat.generateViewId());
                handOnImage.requestLayout();

                playImageConstraintLayout.addView(handOnImage);

                constraintSet.connect(handOnImage.getId(), ConstraintSet.TOP, R.id.guidelineImageTop, ConstraintSet.TOP, 40);
                constraintSet.connect(handOnImage.getId(), ConstraintSet.END, R.id.guidelineImageRight, ConstraintSet.START, 40);
                constraintSet.connect(handOnImage.getId(), ConstraintSet.START, R.id.guidelineImageLeft, ConstraintSet.END, 40);
                constraintSet.connect(handOnImage.getId(), ConstraintSet.BOTTOM, R.id.guidelineImageBottom, ConstraintSet.TOP, 40);
                constraintSet.applyTo(playImageConstraintLayout);
                handOnImage.bringToFront();


//                handOnImage.getLayoutParams().width = 200;
//                handOnImage.getLayoutParams().height =200;
//                handOnImage.bringToFront();

                //            tap_icon1.setVisibility(View.VISIBLE);

                animateHandUpp(0, handOnImage);

            }

           */

        }


    }


/*
    void setUpDemo(){
        if (!ordinaryMCQ){
            //fillUpTest();

            SimpleTestActivity.testActivityImageViewHome.setVisibility(View.GONE);
            SimpleTestActivity.testActivityImageViewDaimond.setVisibility(View.GONE);
          //  SimpleTestActivity.testActivityImageViewfeedback.setVisibility(View.GONE);
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
    public void startDemo() {

        if (mp != null) {
            mp.release();
        }
        SharedPreferences checkLanguage = getActivity().getApplicationContext().getSharedPreferences("DefaultSPForTaleemabadGrade6", 0);
        checkLang = checkLanguage.getString("MenuLanguage", "en");
        if (checkLang != null && checkLang.equals("en")) {
            mp = MediaPlayer.create(getContext(), R.raw.eng1);

        } else {
            mp = MediaPlayer.create(getContext(), R.raw.urdu1);

        }
        // mp.setLooping(true);
        mp.start();


        demoStarted = true;

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();

            }
        });

        final Handler handlerr = new Handler();
        option1.setVisibility(View.VISIBLE);
        option2.setVisibility(View.VISIBLE);


        String ansText = test.getAnswerList().get(0).getText();

        String option1_Text = option1.getText().toString();
        String option2_Text = option2.getText().toString();
        String option3_Text = option3.getText().toString();
        String option4_Text = option4.getText().toString();


        final ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(mainConstraintLayout);
        final ImageView imageview = new ImageView(activity);
        imageview.setImageResource(R.drawable.hand);
        imageview.setId(ViewCompat.generateViewId());
        imageview.requestLayout();

        // Toast.makeText(getContext(),"aa",Toast.LENGTH_SHORT).show();
        if (option1_Text.equals(ansText)) {
            handlerr.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    jab();

                    mainConstraintLayout.addView(imageview);

                    constraintSet.connect(imageview.getId(), ConstraintSet.TOP, R.id.guidelineButton1x1Top, ConstraintSet.TOP, 20);
                    //       constraintSet.connect(imageview.getId(),ConstraintSet.END,R.id.guidelineOption1Right,ConstraintSet.START,80);
                    constraintSet.connect(imageview.getId(), ConstraintSet.START, R.id.guidelineButton1x1Left, ConstraintSet.END, 40);
                    constraintSet.connect(imageview.getId(), ConstraintSet.BOTTOM, R.id.guidelineButton1x1Bottom, ConstraintSet.TOP, 20);
                    constraintSet.applyTo(mainConstraintLayout);

                    imageview.getLayoutParams().width = option1.getMeasuredHeight() / 2;
                    imageview.getLayoutParams().height = option2.getMeasuredHeight() / 2;
                    imageview.bringToFront();

                    //            tap_icon1.setVisibility(View.VISIBLE);
                    animateHandUpp(1, imageview);

                }
            }, 1200);

        } else if (option2_Text.equals(ansText)) {

            //  tap_icon1.setVisibility(View.GONE);
            handlerr.postDelayed(new Runnable() {
                @Override
                public void run() {

                    mainConstraintLayout.addView(imageview);

                    constraintSet.connect(imageview.getId(), ConstraintSet.TOP, R.id.guidelineButton1x1Top, ConstraintSet.TOP, 20);
//                    constraintSet.connect(imageview.getId(),ConstraintSet.END,R.id.guidelineOption2Right,ConstraintSet.START,80);
                    constraintSet.connect(imageview.getId(), ConstraintSet.START, R.id.guidelineButton1x2Left, ConstraintSet.END, 40);
                    constraintSet.connect(imageview.getId(), ConstraintSet.BOTTOM, R.id.guidelineButton1x1Bottom, ConstraintSet.TOP, 20);
                    constraintSet.applyTo(mainConstraintLayout);

                    imageview.getLayoutParams().width = option1.getMeasuredHeight() / 2;
                    imageview.getLayoutParams().height = option2.getMeasuredHeight() / 2;
                    imageview.bringToFront();

                    animateHandUpp(2, imageview);

                }
            }, 1200);
            //tap_icon2.setVisibility(View.VISIBLE);
//            tap_icon3.setVisibility(View.GONE);
//            tap_icon4.setVisibility(View.GONE);

        } else if (option3_Text.equals(ansText)) {

            //  tap_icon1.setVisibility(View.GONE);
            handlerr.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    jab();
                    //  tap_icon2.setVisibility(View.VISIBLE);

                    mainConstraintLayout.addView(imageview);

                    constraintSet.connect(imageview.getId(), ConstraintSet.TOP, R.id.guidelineButton2x1Top, ConstraintSet.TOP, 20);
//                    constraintSet.connect(imageview.getId(),ConstraintSet.END,R.id.guidelineOption2Right,ConstraintSet.START,80);
                    constraintSet.connect(imageview.getId(), ConstraintSet.START, R.id.guidelineButton1x1Left, ConstraintSet.END, 40);
                    constraintSet.connect(imageview.getId(), ConstraintSet.BOTTOM, R.id.guidelineButton2x1Bottom, ConstraintSet.TOP, 20);
                    constraintSet.applyTo(mainConstraintLayout);
                    //     imageview.setPadding(5,25,25,5);
//                    imageview.getLayoutParams().width = 120;
//                    imageview.getLayoutParams().height = 120;

                    imageview.getLayoutParams().width = option1.getMeasuredHeight() / 2;
                    imageview.getLayoutParams().height = option2.getMeasuredHeight() / 2;
                    imageview.bringToFront();

                    animateHandUpp(3, imageview);

                }
            }, 1200);


        }
        else if (option4_Text.equals(ansText)) {

            //  tap_icon1.setVisibility(View.GONE);
            handlerr.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    jab();
                    //  tap_icon2.setVisibility(View.VISIBLE);

                    mainConstraintLayout.addView(imageview);

                    constraintSet.connect(imageview.getId(), ConstraintSet.TOP, R.id.guidelineButton2x1Top, ConstraintSet.TOP, 20);
//                    constraintSet.connect(imageview.getId(),ConstraintSet.END,R.id.guidelineButton1x2Right,ConstraintSet.START,80);
                    constraintSet.connect(imageview.getId(), ConstraintSet.START, R.id.guidelineButton1x2Left, ConstraintSet.END, 40);
                    constraintSet.connect(imageview.getId(), ConstraintSet.BOTTOM, R.id.guidelineButton2x1Bottom, ConstraintSet.TOP, 20);
                    constraintSet.applyTo(mainConstraintLayout);
                    //     imageview.setPadding(5,25,25,5);
//                    imageview.getLayoutParams().width = 120;
//                    imageview.getLayoutParams().height = 120;

                    imageview.getLayoutParams().width = option1.getMeasuredHeight() / 2;
                    imageview.getLayoutParams().height = option2.getMeasuredHeight() / 2;
                    imageview.bringToFront();

                    animateHandUpp(4, imageview);

                }
            }, 1200);


        }
    }

 */

/*
    public void animateHandUpp(int icon, final ImageView imageView) {
//        ordinaryMCQTextViewOption1.setVisibility(View.VISIBLE);
//        ordinaryMCQTextViewOption2.setVisibility(View.VISIBLE);
//        ordinaryMCQTextViewOption3.setVisibility(View.VISIBLE);
//        ordinaryMCQTextViewOption4.setVisibility(View.VISIBLE);
        if (imageView.getVisibility()==View.GONE){
            return;
        }
        imageView.animate()
//                .translationX(50)
//                .translationY(50)
                .alpha(1.0f)
                .scaleX(0.7f)
                .scaleY(0.7f)
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
            option1.animate()
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
            option2.animate()
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
        }
        else if (icon==3){
            option3.animate()
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
        }
        else if (icon==4){
            option4.animate()
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
        }
        else if (icon==0){
            playSound.animate()
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
                            if (ClickOnBigImage){
                                imageView.setVisibility(View.GONE);
                                return;
                            }else {
                                animateHandDownn(0, imageView);

                            }
                        }
                    });
        }


    }

 */

/*
    public void animateHandDownn(final int icon, final ImageView imageView) {

        imageView.animate()
//                .translationX(50)
//                .translationY(50)
                .alpha(1.0f)
                .scaleX(0.4f)
                .scaleY(0.4f)
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
                    option1.animate()
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
                    option2.animate()
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
                    option3.animate()
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
                }
                else if (icon==4){
                    option4.animate()
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
                }
                else if (icon==0){
                    playSound.animate()
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
                                    animateHandUpp(0,imageView);
                                }
                            });
                }


            }
        }, 200);


    }

 */



    public void setOnClickListeners(View view) {

        option1.setOnClickListener(null);
        option2.setOnClickListener(null);
        option3.setOnClickListener(null);
        option4.setOnClickListener(null);
        playSound.setOnClickListener(this);
    }

    public void enableClickListener() {

        option1.setOnClickListener(this);
        option2.setOnClickListener(this);
        option3.setOnClickListener(this);
        option4.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play_sound_image:
                //playAssetSound("Grade1EnglishTwoAndThreeLetterSoundsAiOaIeQuestion2Option1Audio");
                loadAudio(test.getQuestion().getQuestionButtonAudio());
                break;

            case R.id.Option1x1:
                loadAudio(test.getOptionList().get(arr[0]).getAudio());
                compileAns(option1);

                break;
            case R.id.Option1x2:
                loadAudio(test.getOptionList().get(arr[1]).getAudio());
                compileAns(option2);

                break;
            case R.id.Option1x3:
                loadAudio(test.getOptionList().get(arr[2]).getAudio());
                compileAns(option3);

                break;
            case R.id.Option1x4:
                loadAudio(test.getOptionList().get(arr[3]).getAudio());
                compileAns(option4);
                break;

            case R.id.explanationTextViewInPopUp:
                break;
        }
    }


    public void compileAns(final TextView textView) {
        option1.setOnClickListener(null);
        option2.setOnClickListener(null);
        option3.setOnClickListener(null);
        option4.setOnClickListener(null);
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
            if(unSocratic == true) {
                appAnalytics.setOptionSelected(selectedSubject, topic, test.getType(), textView.getText().toString(), true);
                appAnalytics.setAnswer(selectedSubject, topic + "" + testIndex, topic, test.getType(), test.getAnswerList().size(), 1, true, this.count);
            }
            score = tScore;
            correctCount++;
            totalScore = totalScore + score;
            textView.setBackgroundResource(R.drawable.match_ttt_background_green);
            parrotFire();

        } else {
            if(unSocratic == true) {
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
                if(unSocratic == true){
                    ((SimpleTestActivity)activity).setExplanation(finalScore);
                } else {
                    ((SocraticActivity)activity).setVideo();
                }
                if(getFragmentManager() != null) {
                    getFragmentManager().beginTransaction()
                            .remove(SoundRecognitionSpelling4Options.this).commit();
                }

            }
        }, 1000);

    }

    public void showViews() {
        if (sharedPrefs.getString("GradeSelected", "Grade 6").equalsIgnoreCase("Grade 6")) {
            setBackGrounds(sharedPrefs.getString("SupervisedSubjectSelected", "English"));
        }
        ordinaryMCQImageViewParrotAvatar.setVisibility(View.VISIBLE);
        ordinaryMCQImageViewParrotAvatarLife.setVisibility(View.VISIBLE);
        ordinaryMCQImageViewAlienAvatar.setVisibility(View.VISIBLE);
        ordinaryMCQImageViewAlienAvatarLife.setVisibility(View.VISIBLE);

        setViews();

    }


    private void fillupTest() {

        generateRandom();
        if (test.getQuestion() != null) {
            questionTextViewQuestion.setText(String.valueOf(test.getQuestion().getText()));
        }
        if (test.getOptionList() != null) {
            option1.setText(test.getOptionList().get(arr[0]).getText());
            option2.setText(test.getOptionList().get(arr[1]).getText());
            option3.setText(test.getOptionList().get(arr[2]).getText());
            option4.setText(test.getOptionList().get(arr[3]).getText());
        }

//        option1.setText(String.valueOf(test.getOptionList().get(0).getText()));
//        option2.setText(String.valueOf(test.getOptionList().get(1).getText()));
//        option3.setText(String.valueOf(test.getOptionList().get(2).getText()));
//        option4.setText(String.valueOf(test.getOptionList().get(3).getText()));
    }

    public void setViews() {
        option1.setVisibility(View.GONE);
        option2.setVisibility(View.GONE);
        option3.setVisibility(View.GONE);
        option4.setVisibility(View.GONE);

        if (selectedSubject.equalsIgnoreCase("English") || selectedSubject.equalsIgnoreCase("Maths") || selectedSubject.equalsIgnoreCase("Science")
                || selectedSubject.equalsIgnoreCase("Geography")) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    jab();
                    option1.setVisibility(View.VISIBLE);

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            jab();
                            option2.setVisibility(View.VISIBLE);

                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    jab();
                                    option3.setVisibility(View.VISIBLE);
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            jab();
                                            option4.setVisibility(View.VISIBLE);

//                                            setUpDemo();
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
                    option4.setVisibility(View.VISIBLE);

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            jab();
                            option3.setVisibility(View.VISIBLE);
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    jab();
                                    option2.setVisibility(View.VISIBLE);
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            jab();
                                            option1.setVisibility(View.VISIBLE);

//                                            setUpDemo();

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


    public void setBackGrounds(String subject) {
        switch (subject) {
            case ("English"):
                // ordinaryMCQMainLayout.setBackgroundResource(R.mipmap.bg_pink);
                option1.setBackgroundResource(R.drawable.english_bg);
                option2.setBackgroundResource(R.drawable.english_bg);
                option3.setBackgroundResource(R.drawable.english_bg);
                option4.setBackgroundResource(R.drawable.english_bg);

                break;
            case ("Maths"):
                //   ordinaryMCQMainLayout.setBackgroundResource(R.mipmap.bg_green_b);
                option1.setBackgroundResource(R.drawable.maths_bg);
                option2.setBackgroundResource(R.drawable.maths_bg);
                option3.setBackgroundResource(R.drawable.maths_bg);
                option4.setBackgroundResource(R.drawable.maths_bg);
                break;

            case ("Urdu"):
                //    ordinaryMCQMainLayout.setBackgroundResource(R.mipmap.bg_purple);
                option1.setBackgroundResource(R.drawable.urdu_bg);
                option2.setBackgroundResource(R.drawable.urdu_bg);
                option3.setBackgroundResource(R.drawable.urdu_bg);
                option4.setBackgroundResource(R.drawable.urdu_bg);

                break;

            case ("GeneralKnowledge"):
            case ("Science"):
                //    ordinaryMCQMainLayout.setBackgroundResource(R.mipmap.bg_blue);
                option1.setBackgroundResource(R.drawable.science_bg);
                option2.setBackgroundResource(R.drawable.science_bg);
                option3.setBackgroundResource(R.drawable.science_bg);
                option4.setBackgroundResource(R.drawable.science_bg);
                break;

        }
    }


    public void parrotFire() {
        fire1();
        parrotPlus();
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
        alienPlus();
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


    public void parrotPlus() {

        TextViewParrotPlus.setVisibility(View.VISIBLE);
        final Animation animationTranslateIn = AnimationUtils.loadAnimation(mContext, R.anim.floatfade);
        Runnable task = new Runnable() {
            public void run() {
                //run the animated task
                TextViewParrotPlus.setAnimation(animationTranslateIn);
            }
        };
        task.run();

        animationTranslateIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                TextViewParrotPlus.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    public void alienPlus() {

        TextViewAlienPlus.setVisibility(View.VISIBLE);
        final Animation animationTranslateIn = AnimationUtils.loadAnimation(mContext, R.anim.floatfade);
        final Runnable task = new Runnable() {
            public void run() {
                //run the animated task
                TextViewAlienPlus.setAnimation(animationTranslateIn);
            }
        };
        task.run();

        animationTranslateIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                TextViewAlienPlus.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

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

    public void playAssetSound(String soundFileName) {
        try {
            Log.wtf("-playsound", "Sound name : " + soundFileName);
            AssetFileDescriptor descriptor = getContext().getAssets().openFd(soundFileName + ".mp3");
            mediaPlayerTouch.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            descriptor.close();

            mediaPlayerTouch.prepare();
            mediaPlayerTouch.setVolume(1f, 1f);
            mediaPlayerTouch.setLooping(false);
            mediaPlayerTouch.start();
            mediaPlayerTouch.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                    if (first_time){
                        showViews();
                        fillupTest();
                        first_time=false;
                    }
                    enableClickListener();
                    mediaPlayerTouch=new MediaPlayer();

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loadAudio(String name) {
        //Grade1Voiceovers
        //String path = Environment.getExternalStorageDirectory().getPath();
        File path = new File(String.valueOf(getActivity().getApplicationContext().getExternalFilesDir(null)));
        Log.wtf("path_", "path : " + path);
        mediaPlayerTouch = new MediaPlayer();
        try {
            mediaPlayerTouch.setDataSource(path + "/" + name + ".mp3");
            mediaPlayerTouch.prepare();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayerTouch.start();
        mediaPlayerTouch.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
                if (first_time) {
                    showViews();
                    fillupTest();
                    enableClickListener();
                    first_time = false;
                }
                mediaPlayerTouch = new MediaPlayer();

            }
        });
    }

}
