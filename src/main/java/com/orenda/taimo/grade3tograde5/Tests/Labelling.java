package com.orenda.taimo.grade3tograde5.Tests;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;

import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import firebase.analytics.AppAnalytics;

import androidx.fragment.app.Fragment;

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

//import static com.facebook.FacebookSdk.getApplicationContext;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.tempIndex;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.testIndex;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.topic;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.unSocratic;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.alienLife;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.correctCount;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.deduct;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.parrotLife;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.selectedSubject;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.totalScore;

@SuppressLint("ValidFragment")
public class Labelling extends Fragment implements View.OnClickListener, View.OnTouchListener, View.OnDragListener {

    MediaPlayer mediaPlayerTouch;
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;
    String selectedData;
    TextView selectedView;

    TextView labellingTextViewOption1, labellingTextViewOption2, labellingTextViewOption3, labellingTextViewOption4,
            labellingTextViewAnswer1, labellingTextViewAnswer2, labellingTextViewAnswer3, labellingTextViewAnswer4;

    TextView labellingTextViewQuestion;
    ImageView labellingImageViewQuestion;
    TextView TextViewParrotPlus, TextViewAlienPlus;

    ImageView ImageViewParrotFire, ImageViewAlienAvatarLife, ImageViewParrotAvatarLife, ImageViewAlienFire;
    ImageView ImageViewParrotAvatar, ImageViewAlienAvatar;
    ImageView ImageViewParrotHit, ImageViewAlienHit;
    int realHeight;
    int realWidth;
    AppAnalytics appAnalytics;
    Timer T = new Timer();
    int count = 0;
    boolean start = true;
    boolean dropDone = false;
    private int answerCount = 0;
    float fontSize = 22;

    Integer[] arr = new Integer[4];
    int testId = -1;
    TestJsonParseModel test = null;
    Context mContext;
    Activity activity;

    private DataSource.Factory dataSourceFactory;
    private ExtractorsFactory extractorsFactory;
    private MediaSource mediaSource;
    private SimpleExoPlayer player;
    private final String streamUrl = "http://cdn.audios.taleemabad.com/QuestionBank/";
    private boolean compileCalled = false;
    private TextView selectedTextView = null;

    /*
    int DEMO_MODE=0;
    ConstraintLayout labellingMainLayout;
    int  labellingXCoordinate,  labellingYCoordinate;
    ImageView handImagview;
    int whichOptionIsCorrect;
    Boolean ordinaryMCQ;
    MediaPlayer mp;
    boolean demoStarted=false;
    String checkLang;
    boolean stillInDemoState=false;
    */

    public Labelling(int testId, TestJsonParseModel test, Context context, Activity activity) {
        this.testId = testId;
        this.test = test;
        mContext = context;
        this.activity = activity;

        Log.wtf("-this", " TEST ID : " + testId);
    }

    public Labelling() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.labelling, container, false);
        player = ExoPlayerFactory.newSimpleInstance(getContext());
        sharedPrefs = getActivity().getApplicationContext().getSharedPreferences("DefaultSPForTaleemabadGrade6", 0);
        editor = sharedPrefs.edit();
        appAnalytics = new AppAnalytics(mContext);
        initializeView(view);

        return view;
    }

    public void initializeView(View view) {
        labellingTextViewQuestion = view.findViewById(R.id.labellingTextViewQuestion);


        labellingTextViewOption1 = view.findViewById(R.id.labellingTextViewOption1);
        labellingTextViewOption2 = view.findViewById(R.id.labellingTextViewOption2);
        labellingTextViewOption3 = view.findViewById(R.id.labellingTextViewOption3);
        labellingTextViewOption4 = view.findViewById(R.id.labellingTextViewOption4);

        labellingTextViewAnswer1 = view.findViewById(R.id.labellingTextViewAnswer1);
        labellingTextViewAnswer2 = view.findViewById(R.id.labellingTextViewAnswer2);
        labellingTextViewAnswer3 = view.findViewById(R.id.labellingTextViewAnswer3);
        labellingTextViewAnswer4 = view.findViewById(R.id.labellingTextViewAnswer4);

        labellingImageViewQuestion = view.findViewById(R.id.labellingImageViewQuestion);

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
        //labellingMainLayout     = view .findViewById(R.id.labellingMainLayout);


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
                    int realHeight = metrics.heightPixels; // This is real height
                    int realWidth = metrics.widthPixels; // This is real width

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


                    fontSize = (0.028f * realHeight);
                    if (screenInches < 7) {
                        fontSize = (0.028f * (1080f / (1.5f)));
                    } else {
                        fontSize = (0.028f * 800f);
                    }
                    Log.wtf("-this", "height : " + realHeight + "  FONT SIZE : " + fontSize);

                    float fontSizeQuestion = (0.029f * realHeight);
                    if (screenInches < 7) {
                        fontSizeQuestion = (0.029f * (1080f / 1.85f));
                    } else {
                        fontSizeQuestion = (0.029f * 800f);
                    }

                    Log.wtf("-this", "height : " + realHeight + "  Q SFONT SIZE : " + fontSizeQuestion);
                    Log.wtf("-this", "QUESTION SIZE : " + labellingTextViewQuestion.getText().toString().length());

                    labellingTextViewQuestion.setTextSize(fontSizeQuestion);

                    labellingTextViewOption1.setTextSize(fontSize);
                    labellingTextViewOption2.setTextSize(fontSize);
                    labellingTextViewOption3.setTextSize(fontSize);
                    labellingTextViewOption4.setTextSize(fontSize);

                    labellingTextViewAnswer1.setTextSize(fontSize);
                    labellingTextViewAnswer2.setTextSize(fontSize);
                    labellingTextViewAnswer3.setTextSize(fontSize);
                    labellingTextViewAnswer4.setTextSize(fontSize);

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
      //  setOnClickListeners(view);
        startTestTimer();

        //To retrieve
//        SharedPreferences sharedPrefForChecking = getActivity().getSharedPreferences("DemoLabelling", DEMO_MODE);
//        ordinaryMCQ = sharedPrefForChecking.getBoolean("DemoLabelling", false); //0 is the default value


        setOnClickListeners(view);
    }
/*
    void setUpDemo(){
        if (!ordinaryMCQ){
            //fillUpTest();

//            SimpleTestActivity.testActivityImageViewHome.setVisibility(View.INVISIBLE);
//            SimpleTestActivity.testActivityImageViewDaimond.setVisibility(View.INVISIBLE);

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

            //   SimpleTestActivity.testActivityImageViewfeedback.setVisibility(View.INVISIBLE);

//                setOnClickListenr(view);
            final Handler handlerr = new Handler();
            handlerr.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    jab();
                    ImageViewParrotAvatarLife.setVisibility(View.INVISIBLE);
                    ImageViewParrotAvatar.setVisibility(View.INVISIBLE);
                    ImageViewAlienAvatarLife.setVisibility(View.INVISIBLE);
                    ImageViewAlienAvatar.setVisibility(View.INVISIBLE);
                    startDemo();
                }
            }, 100);

            SharedPreferences sharedPrefForSaving = getContext().getSharedPreferences("DemoLabelling", DEMO_MODE);
            SharedPreferences.Editor editor = sharedPrefForSaving.edit();
            editor.putBoolean("DemoLabelling", true);
            editor.apply();

        }
        else {
            SimpleTestActivity.testActivityImageViewHome.setVisibility(View.VISIBLE);
            SimpleTestActivity.testActivityImageViewDaimond.setVisibility(View.VISIBLE);
         //   SimpleTestActivity.testActivityImageViewfeedback.setVisibility(View.VISIBLE);
            ImageViewAlienAvatarLife.setVisibility(View.VISIBLE);
            ImageViewParrotAvatarLife.setVisibility(View.VISIBLE);
            ImageViewParrotAvatar.setVisibility(View.VISIBLE);
            ImageViewAlienAvatar.setVisibility(View.VISIBLE);
            demoStarted=false;
        }

    }

 */

/*
    @Override
    public void onDestroy() {
        if (mp!=null){
            mp.release();
        }
        super.onDestroy();
    }

    @Override
    public void onResume() {


        if ( demoStarted){
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
    public void startDemo(){
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
       // mp.setLooping(true);
        mp.start();
        demoStarted =true;

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();

                callForDemoVoice();

            }
        });

        String anstext = test.getOptionList().get(0).getText();

        String option1 = labellingTextViewOption1.getText().toString();
        String option2 = labellingTextViewOption2.getText().toString();
        String option3 = labellingTextViewOption3.getText().toString();
        String option4 = labellingTextViewOption4.getText().toString();

        final ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(labellingMainLayout);
        handImagview = new ImageView(activity);

        handImagview.setImageResource(R.drawable.hand);
        handImagview.setId(ViewCompat.generateViewId());


        if (option1.equals(anstext)){
            labellingMainLayout.addView(handImagview);

            constraintSet.connect(handImagview.getId(), ConstraintSet.TOP, R.id.guidelineOption1TextTop, ConstraintSet.TOP,10);
            constraintSet.connect(handImagview.getId(),ConstraintSet.END, R.id.guidelineOptionTextRight,ConstraintSet.START,10);
            constraintSet.connect(handImagview.getId(),ConstraintSet.START, R.id.guidelineOptionTextLeft,ConstraintSet.END,10);
            constraintSet.connect(handImagview.getId(),ConstraintSet.BOTTOM, R.id.guidelineOption1TextBottom,ConstraintSet.TOP,10);
            constraintSet.applyTo(labellingMainLayout);

            int[] location = new int[2];
            labellingTextViewOption1.getLocationOnScreen(location);
            labellingXCoordinate = location[0];
            labellingYCoordinate = location[1];

            whichOptionIsCorrect =1;
        }else if (option2.equals(anstext)){
            labellingMainLayout.addView(handImagview);

            constraintSet.connect(handImagview.getId(), ConstraintSet.TOP, R.id.guidelineOption2TextTop,ConstraintSet.TOP,10);
            constraintSet.connect(handImagview.getId(),ConstraintSet.END, R.id.guidelineOptionTextRight,ConstraintSet.START,10);
            constraintSet.connect(handImagview.getId(),ConstraintSet.START, R.id.guidelineOptionTextLeft,ConstraintSet.END,10);
            constraintSet.connect(handImagview.getId(),ConstraintSet.BOTTOM, R.id.guidelineOption2TextBottom,ConstraintSet.TOP,10);
            constraintSet.applyTo(labellingMainLayout);

            int[] location = new int[2];
            labellingTextViewOption2.getLocationOnScreen(location);
            labellingXCoordinate = location[0];
            labellingYCoordinate = location[1];
            whichOptionIsCorrect =2;

        }
        else if (option3.equals(anstext)){
            labellingMainLayout.addView(handImagview);

            constraintSet.connect(handImagview.getId(), ConstraintSet.TOP, R.id.guidelineOption3TextTop,ConstraintSet.TOP,10);
            constraintSet.connect(handImagview.getId(),ConstraintSet.END, R.id.guidelineOptionTextRight,ConstraintSet.START,10);
            constraintSet.connect(handImagview.getId(),ConstraintSet.START, R.id.guidelineOptionTextLeft,ConstraintSet.END,10);
            constraintSet.connect(handImagview.getId(),ConstraintSet.BOTTOM, R.id.guidelineOption3TextBottom,ConstraintSet.TOP,10);
            constraintSet.applyTo(labellingMainLayout);

            int[] location = new int[2];
            labellingTextViewOption3.getLocationOnScreen(location);
            labellingXCoordinate = location[0];
            labellingYCoordinate = location[1];

            whichOptionIsCorrect =3;

        }
        else if (option4.equals(anstext)){
            labellingMainLayout.addView(handImagview);
            constraintSet.connect(handImagview.getId(), ConstraintSet.TOP, R.id.guidelineOption4TextTop,ConstraintSet.TOP,10);
            constraintSet.connect(handImagview.getId(),ConstraintSet.END, R.id.guidelineOptionTextRight,ConstraintSet.START,10);
            constraintSet.connect(handImagview.getId(),ConstraintSet.START, R.id.guidelineOptionTextLeft,ConstraintSet.END,10);
            constraintSet.connect(handImagview.getId(),ConstraintSet.BOTTOM, R.id.guidelineOption4TextBottom,ConstraintSet.TOP,10);
            constraintSet.applyTo(labellingMainLayout);

            int[] location = new int[2];
            labellingTextViewOption4.getLocationOnScreen(location);
            labellingXCoordinate = location[0];
            labellingYCoordinate = location[1];

            whichOptionIsCorrect =4;

        }

        //       Toast.makeText(getContext(),"1: "+DragTextBoxToPictureTwoOptionsXCoordinate, Toast.LENGTH_LONG).show();
        //       Toast.makeText(getContext(),"2: "+DragTextBoxToPictureTwoOptionsYCoordinate, Toast.LENGTH_LONG).show();
//        Toast.makeText(getContext(),"2: "+anstext, Toast.LENGTH_LONG).show();


        final Handler handlerr = new Handler();
        handlerr.postDelayed(new Runnable() {
            @Override
            public void run() {

                moveto(handImagview);

            }
        }, 1200);

    }

 */
/*
    public void moveto(final ImageView imageview){
        imageview.setVisibility(View.VISIBLE);
        int[] location1 = new int[2];
        //textViewList.get(0).getTextView().getLocationOnScreen(location1);

        labellingTextViewAnswer1.getLocationOnScreen(location1);
        final int movetoX = location1[0];
        final int movetoY = location1[1];

        if (whichOptionIsCorrect==1){
            labellingTextViewOption1.bringToFront();
            labellingTextViewOption1.animate()
                    .translationX(-50)
                    .alpha(0f)
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(500)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            // animateup();
                            labellingTextViewOption1.animate()
                                    .translationX(0)
                                    .alpha(1.0f)
                                    .scaleX(1f)
                                    .scaleY(1f)
                                    .setDuration(500)
                                    .setListener(new AnimatorListenerAdapter() {
                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            super.onAnimationEnd(animation);
                                            // animateup();
                                        }
                                    });
                        }
                    });

        }else if (whichOptionIsCorrect==2){
            labellingTextViewOption2.bringToFront();
            labellingTextViewOption2.animate()
                    .translationX(-50)
                    .alpha(0f)
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(500)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            // animateup();
                            labellingTextViewOption2.animate()
                                    .translationX(0)
                                    .alpha(1f)
                                    .scaleX(1f)
                                    .scaleY(1f)
                                    .setDuration(500)
                                    .setListener(new AnimatorListenerAdapter() {
                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            super.onAnimationEnd(animation);
                                            // animateup();
                                        }
                                    });
                        }
                    });

        }else if (whichOptionIsCorrect==3){
            labellingTextViewOption3.bringToFront();
            labellingTextViewOption3.animate()
                    .translationX(-50)
                    .alpha(0f)
                    .scaleX(1f)
                    .setDuration(500)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            // animateup();
                            labellingTextViewOption3.animate()
                                    .translationX(0)
                                    .alpha(1f)
                                    .scaleX(1f)
                                    .scaleY(1f)
                                    .setDuration(500)
                                    .setListener(new AnimatorListenerAdapter() {
                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            super.onAnimationEnd(animation);
                                            // animateup();
                                        }
                                    });
                        }
                    });

        }else if (whichOptionIsCorrect==4){
            labellingTextViewOption4.bringToFront();
            labellingTextViewOption4.animate()
                    .translationX(-50)
                    .alpha(0f)
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(500)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            // animateup();

                            labellingTextViewOption4.animate()
                                    .translationX(0)
                                    .alpha(1f)
                                    .scaleX(1f)
                                    .scaleY(1f)
                                    .setDuration(500)
                                    .setListener(new AnimatorListenerAdapter() {
                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            super.onAnimationEnd(animation);
                                            // animateup();
                                        }
                                    });
                        }
                    });

        }

        imageview.bringToFront();
        imageview.animate()
                .x(movetoX)
                .y(movetoY)
                .alpha(1.0f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(600)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        // animateup();
                        if (answerCount!=0){
                            imageview.setVisibility(View.GONE);
                            return;
                        }

                        final Handler handlerr = new Handler();
                        handlerr.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                imageview.animate()
                                        .x(movetoX-1)
                                        .y(movetoY)
                                        .alpha(0f)
                                        .setDuration(500)
                                        .setListener(new AnimatorListenerAdapter() {
                                            @Override
                                            public void onAnimationEnd(Animator animation) {
                                                super.onAnimationEnd(animation);
                                                // animateup();
                                                if (answerCount!=0){
                                                    imageview.setVisibility(View.GONE);
                                                    return;
                                                }
                                                imageview.setVisibility(View.GONE);
                                                movetoback(imageview);
                                            }
                                        });

                            }
                        }, 500);

                    }
                });
    }

 */
/*
    public void movetoback(final ImageView imageview){

        imageview.bringToFront();
        imageview.animate()
                .x(labellingXCoordinate)
                .y(labellingYCoordinate)
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(300)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        // animateup();


                        final Handler handlerr = new Handler();
                        handlerr.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                moveto(imageview);
                            }
                        }, 500);
                    }
                });
    }
*/



    public void setUpTest() {
        showViews();

    }

    public void showViews() {
        fillUpTest();
        setBackGrounds(sharedPrefs.getString("SupervisedSubjectSelected", "English"));

//        labellingTextViewQuestion.setVisibility(View.VISIBLE);
//        labellingTextViewOption1.setVisibility(View.VISIBLE);
//        labellingTextViewOption2.setVisibility(View.VISIBLE);
//        labellingTextViewOption3.setVisibility(View.VISIBLE);
//        labellingTextViewOption4.setVisibility(View.VISIBLE);
//        labellingTextViewAnswer1.setVisibility(View.VISIBLE);
//        labellingTextViewAnswer2.setVisibility(View.VISIBLE);
//        labellingTextViewAnswer3.setVisibility(View.VISIBLE);
//        labellingTextViewAnswer4.setVisibility(View.VISIBLE);
        labellingImageViewQuestion.setVisibility(View.VISIBLE);

        ImageViewAlienAvatarLife.setVisibility(View.VISIBLE);
        ImageViewParrotAvatarLife.setVisibility(View.VISIBLE);
        ImageViewParrotAvatar.setVisibility(View.VISIBLE);
        ImageViewAlienAvatar.setVisibility(View.VISIBLE);
        setViews();

    }

    public void setBackGrounds(String subject) {
        switch (subject) {
            case ("English"):
                labellingTextViewOption1.setBackgroundResource(R.drawable.english_bg);
                labellingTextViewOption2.setBackgroundResource(R.drawable.english_bg);
                labellingTextViewOption3.setBackgroundResource(R.drawable.english_bg);
                labellingTextViewOption4.setBackgroundResource(R.drawable.english_bg);

                break;
            case ("Maths"):
                labellingTextViewOption1.setBackgroundResource(R.drawable.maths_bg);
                labellingTextViewOption2.setBackgroundResource(R.drawable.maths_bg);
                labellingTextViewOption3.setBackgroundResource(R.drawable.maths_bg);
                labellingTextViewOption4.setBackgroundResource(R.drawable.maths_bg);
                break;

            case ("Urdu"):
                labellingTextViewOption1.setBackgroundResource(R.drawable.urdu_bg);
                labellingTextViewOption2.setBackgroundResource(R.drawable.urdu_bg);
                labellingTextViewOption3.setBackgroundResource(R.drawable.urdu_bg);
                labellingTextViewOption4.setBackgroundResource(R.drawable.urdu_bg);

                break;
            case ("GeneralKnowledge"):
            case ("Science"):
                labellingTextViewOption1.setBackgroundResource(R.drawable.science_bg);
                labellingTextViewOption2.setBackgroundResource(R.drawable.science_bg);
                labellingTextViewOption3.setBackgroundResource(R.drawable.science_bg);
                labellingTextViewOption4.setBackgroundResource(R.drawable.science_bg);
                break;

        }
    }

    public void fillUpTest() {
        labellingTextViewQuestion.setText(test.getQuestion().getText());
        labellingImageViewQuestion.setImageBitmap(getBitmapFromAsset(test.getQuestion().getImage()));
        generateRandom();
        labellingTextViewOption1.setText(test.getOptionList().get(arr[0]).getText());
        labellingTextViewOption2.setText(test.getOptionList().get(arr[1]).getText());
        labellingTextViewOption3.setText(test.getOptionList().get(arr[2]).getText());
        labellingTextViewOption4.setText(test.getOptionList().get(arr[3]).getText());

    }

    private Bitmap getBitmapFromAsset(String strName) {
        Log.wtf("-this", "Bitmap : " + strName);
        Bitmap bitmap = null;
        File file0 = new File(getContext().getFilesDir(), "/Assets/" + strName + ".png");
        if (file0.exists()) {
            bitmap = BitmapFactory.decodeFile(file0.getAbsolutePath());
        } else {
            AssetManager assetManager = mContext.getAssets();
            InputStream istr = null;
            try {
                istr = assetManager.open(strName + ".png");
            } catch (IOException e) {
                e.printStackTrace();
            }
            bitmap = BitmapFactory.decodeStream(istr);
        }

        return bitmap;
    }

    public void setBackGroundSingle(String subject, TextView textView) {
        switch (subject) {
            case ("English"):
                textView.setBackgroundResource(R.drawable.english_bg);

                break;
            case ("Maths"):
                textView.setBackgroundResource(R.drawable.maths_bg);
                break;

            case ("Urdu"):
                textView.setBackgroundResource(R.drawable.urdu_bg);


                break;
            case ("GeneralKnowledge"):
            case ("Science"):
                textView.setBackgroundResource(R.drawable.science_bg);

                break;

        }
    }

    public void setOnClickListeners(View view) {

        labellingTextViewOption1.setOnTouchListener(this);
        labellingTextViewOption2.setOnTouchListener(this);
        labellingTextViewOption3.setOnTouchListener(this);
        labellingTextViewOption4.setOnTouchListener(this);

        labellingTextViewAnswer1.setOnDragListener(this);
        labellingTextViewAnswer2.setOnDragListener(this);
        labellingTextViewAnswer3.setOnDragListener(this);
        labellingTextViewAnswer4.setOnDragListener(this);


    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        dropDone = false;
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                    view);
            //  view.startDrag(data, shadowBuilder, view, 0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                view.startDragAndDrop(data, shadowBuilder, null, 0);
            } else {
                view.startDrag(data, shadowBuilder, null, 0);
            }
            TextView v = (TextView) view;
            selectedData = v.getText().toString();
            selectedView = v;


            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        int action = event.getAction();
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                // do nothing
                // Log.wtf("-drag","Started "+v.toString());
                // return true;
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                //    v.setBackgroundDrawable(enterShape);
                //  Log.wtf("-drag","Entered "+v.toString());
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                // v.setBackgroundDrawable(normalShape);
                //  Log.wtf("-drag","Exited "+v.toString());
                // textViewAnswer1.setVisibility(View.VISIBLE);
                break;
            case DragEvent.ACTION_DROP:
                TextView txtView = (TextView) v;
                if (selectedView.getId() == labellingTextViewOption1.getId() || selectedView.getId() == labellingTextViewOption2.getId()
                        || selectedView.getId() == labellingTextViewOption3.getId() || selectedView.getId() == labellingTextViewOption4.getId()) {

                    if (txtView.getId() == labellingTextViewOption1.getId() || txtView.getId() == labellingTextViewOption2.getId()
                            || txtView.getId() == labellingTextViewOption3.getId() || txtView.getId() == labellingTextViewOption4.getId()) {
                        Log.wtf("-drag", " Picked : OPTION || Droped : OPTION");
//
                        break;
                    } else {
                        Log.wtf("-drag", " Picked : OPTION || Droped : ANSWER");
                        selectedView.setOnTouchListener(null);
                        selectedView.setOnDragListener(this);
                        txtView.setOnDragListener(null);
                        txtView.setOnTouchListener(this);
                        answerCount++;
                        if(selectedView.getId() == labellingTextViewOption1.getId()){
                            String uidAudio = topic + "Question" + (tempIndex+1) + "Option" + arr[0]+1 + "Audio.mp3";
                            if (unSocratic == true) {
                                // setAudioDescription(uidAudio, ordinaryMCQTextViewOption1);
                                File myDirectory = new File(getContext().getFilesDir(), "TestAudios");
                                File file = new File(myDirectory,uidAudio);
                                if(file.exists()){
                                    Log.wtf("-thus","File Exits  : "+uidAudio);
                                    playOfflineAudio(Uri.fromFile(file));
                                } else {
                                    Log.wtf("-thus","File DOES NOT Exits  : "+uidAudio + "  URI  "+Uri.fromFile(file));
                                }
                            }else {

                            }
                        } else if(selectedView.getId() == labellingTextViewOption2.getId()){
                            String uidAudio = topic + "Question" + (tempIndex+1) + "Option" + arr[1]+1 + "Audio.mp3";
                            if (unSocratic == true) {
                                // setAudioDescription(uidAudio, ordinaryMCQTextViewOption1);
                                File myDirectory = new File(getContext().getFilesDir(), "TestAudios");
                                File file = new File(myDirectory,uidAudio);
                                if(file.exists()){
                                    Log.wtf("-thus","File Exits  : "+uidAudio);
                                    playOfflineAudio(Uri.fromFile(file));
                                } else {
                                    Log.wtf("-thus","File DOES NOT Exits  : "+uidAudio + "  URI  "+Uri.fromFile(file));
                                }
                            }else {

                            }
                        } else if(selectedView.getId() == labellingTextViewOption3.getId()){
                            String uidAudio = topic + "Question" + (tempIndex+1) + "Option" + arr[2]+1 + "Audio.mp3";
                            if (unSocratic == true) {
                                // setAudioDescription(uidAudio, ordinaryMCQTextViewOption1);
                                File myDirectory = new File(getContext().getFilesDir(), "TestAudios");
                                File file = new File(myDirectory,uidAudio);
                                if(file.exists()){
                                    Log.wtf("-thus","File Exits  : "+uidAudio);
                                    playOfflineAudio(Uri.fromFile(file));
                                } else {
                                    Log.wtf("-thus","File DOES NOT Exits  : "+uidAudio + "  URI  "+Uri.fromFile(file));
                                }
                            }else {

                            }
                        } else if(selectedView.getId() == labellingTextViewOption4.getId()){
                            String uidAudio = topic + "Question" + (tempIndex+1) + "Option" + arr[3]+1 + "Audio.mp3";
                            if (unSocratic == true) {
                                // setAudioDescription(uidAudio, ordinaryMCQTextViewOption1);
                                File myDirectory = new File(getContext().getFilesDir(), "TestAudios");
                                File file = new File(myDirectory,uidAudio);
                                if(file.exists()){
                                    Log.wtf("-thus","File Exits  : "+uidAudio);
                                    playOfflineAudio(Uri.fromFile(file));
                                } else {
                                    Log.wtf("-thus","File DOES NOT Exits  : "+uidAudio + "  URI  "+Uri.fromFile(file));
                                }
                            }else {

                            }
                        }
                        selectedView.setBackgroundResource(R.drawable.maths_mcq_blank_roundbg);
                        selectedView.setTextColor(Color.TRANSPARENT);
                        txtView.setText(selectedData);
                        txtView.setTextColor(getResources().getColor(R.color.cardview_light_background));
                        setBackGroundSingle(sharedPrefs.getString("SupervisedSubjectSelected", "English"), txtView);
                    }

                } else {
                    if (txtView.getId() == labellingTextViewOption1.getId() || txtView.getId() == labellingTextViewOption2.getId()
                            || txtView.getId() == labellingTextViewOption3.getId() || txtView.getId() == labellingTextViewOption4.getId()) {
                        Log.wtf("-drag", " Picked : ANSWER || Droped : OPTION");
                        selectedView.setOnTouchListener(null);
                        selectedView.setOnDragListener(this);
                        txtView.setOnDragListener(null);
                        txtView.setOnTouchListener(this);
                        answerCount--;
                        selectedView.setBackgroundResource(R.drawable.maths_mcq_blank_roundbg);
                        selectedView.setTextColor(Color.TRANSPARENT);
                        txtView.setText(selectedData);
                        txtView.setTextColor(getResources().getColor(R.color.cardview_light_background));
                        setBackGroundSingle(sharedPrefs.getString("SupervisedSubjectSelected", "English"), txtView);
                    } else {
                        Log.wtf("-drag", " Picked : ANSWER || Droped : ANSWER");
                        selectedView.setOnTouchListener(null);
                        selectedView.setOnDragListener(this);
                        txtView.setOnDragListener(null);
                        txtView.setOnTouchListener(this);

                        selectedView.setBackgroundResource(R.drawable.maths_mcq_blank_roundbg);
                        selectedView.setTextColor(Color.TRANSPARENT);
                        txtView.setText(selectedData);
                        txtView.setTextColor(getResources().getColor(R.color.cardview_light_background));
                        setBackGroundSingle(sharedPrefs.getString("SupervisedSubjectSelected", "English"), txtView);

                    }
                }

                //stillInDemoState=false;

                if (answerCount == 4) {
                    compileAns();
                }

                break;
            case DragEvent.ACTION_DRAG_ENDED:
                // Log.wtf("-drag","Ended "+v.toString());
                // textViewAnswer1.setVisibility(View.VISIBLE);
                break;
            case DragEvent.ACTION_DRAG_LOCATION:
                //  Log.wtf("-drag","DRAG Location "+v.toString());

                // textViewAnswer1.setVisibility(View.VISIBLE);
            default:
                break;
        }
        return true;
    }


    @Override
    public void onClick(View v) {
        if (sharedPrefs.getBoolean("SoundEnabled", true)) {
            tapAudio();
        }
        switch (v.getId()) {
        }
    }

    public void compileAns() {
        //stillInDemoState=false;
        start = false;
        T.cancel();
        labellingTextViewOption1.setOnTouchListener(null);
        labellingTextViewOption2.setOnTouchListener(null);
        labellingTextViewOption3.setOnTouchListener(null);
        labellingTextViewOption4.setOnTouchListener(null);

        labellingTextViewAnswer1.setOnDragListener(null);
        labellingTextViewAnswer2.setOnDragListener(null);
        labellingTextViewAnswer3.setOnDragListener(null);
        labellingTextViewAnswer4.setOnDragListener(null);

        int count = 0;
        int score = 0;
        int tScore = 0;
        if (test.getDifficultyLevel().equalsIgnoreCase("easy")) {
            tScore = 2;
        } else if (test.getDifficultyLevel().equalsIgnoreCase("medium")) {
            tScore = 5;
        } else if (test.getDifficultyLevel().equalsIgnoreCase("hard")) {
            tScore = 7;
        } else if (test.getDifficultyLevel().equalsIgnoreCase("bonus")) {
            tScore = 10;
        } else if (test.getDifficultyLevel().equalsIgnoreCase("superEasy")) {
            tScore = 1;
        }

        if (labellingTextViewAnswer1.getText().toString().equalsIgnoreCase(test.getOptionList().get(0).getText())) {
            if (unSocratic == true) {
                appAnalytics.setOptionPlaced(selectedSubject, topic, test.getType(), labellingTextViewAnswer1.getText().toString(), true);
            }

            count++;
            score = score + tScore;
            labellingTextViewAnswer1.setBackgroundResource(R.drawable.ordinary_mcq_option_bg_green);
        } else {
            if (unSocratic == true)
                appAnalytics.setOptionPlaced(selectedSubject, topic, test.getType(), labellingTextViewAnswer1.getText().toString(), false);

            labellingTextViewAnswer1.setBackgroundResource(R.drawable.ordinary_mcq_option_bg_red);
        }
        if (labellingTextViewAnswer2.getText().toString().equalsIgnoreCase(test.getOptionList().get(1).getText())) {
            if (unSocratic == true)
                appAnalytics.setOptionPlaced(selectedSubject, topic, test.getType(), labellingTextViewAnswer2.getText().toString(), true);

            count++;
            score = score + tScore;
            labellingTextViewAnswer2.setBackgroundResource(R.drawable.ordinary_mcq_option_bg_green);
        } else {
            if(unSocratic == true)
              appAnalytics.setOptionPlaced(selectedSubject,topic,test.getType(),labellingTextViewAnswer2.getText().toString(),false);

            labellingTextViewAnswer2.setBackgroundResource(R.drawable.ordinary_mcq_option_bg_red);
        }
        if (labellingTextViewAnswer3.getText().toString().equalsIgnoreCase(test.getOptionList().get(2).getText())) {
            if(unSocratic == true)
               appAnalytics.setOptionPlaced(selectedSubject,topic,test.getType(),labellingTextViewAnswer3.getText().toString(),true);

            count++;
            score = score + tScore;
            labellingTextViewAnswer3.setBackgroundResource(R.drawable.ordinary_mcq_option_bg_green);
        } else {
            if(unSocratic == true)
               appAnalytics.setOptionPlaced(selectedSubject,topic,test.getType(),labellingTextViewAnswer3.getText().toString(),false);

            labellingTextViewAnswer3.setBackgroundResource(R.drawable.ordinary_mcq_option_bg_red);
        }
        if (labellingTextViewAnswer4.getText().toString().equalsIgnoreCase(test.getOptionList().get(3).getText())) {
            if(unSocratic == true)
               appAnalytics.setOptionPlaced(selectedSubject,topic,test.getType(),labellingTextViewAnswer4.getText().toString(),true);

            count++;
            score = score + tScore;
            labellingTextViewAnswer4.setBackgroundResource(R.drawable.ordinary_mcq_option_bg_green);
        } else {
            if(unSocratic == true)
              appAnalytics.setOptionPlaced(selectedSubject,topic,test.getType(),labellingTextViewAnswer4.getText().toString(),false);

            labellingTextViewAnswer4.setBackgroundResource(R.drawable.ordinary_mcq_option_bg_red);
        }

        if (score < ((tScore * 4) / 2)) {
            alienFire();
        } else {
            parrotFire();
            correctCount++;

        }

        totalScore = totalScore + score;

        if (count == 4) {
            if(unSocratic == true) {
                appAnalytics.setAnswer(selectedSubject, topic + "" + testIndex, topic, test.getType(), test.getAnswerList().size(), count, true, this.count);
            }
        } else {
            if(unSocratic == true) {
                appAnalytics.setAnswer(selectedSubject, topic + "" + testIndex, topic, test.getType(), test.getAnswerList().size(), count, false, this.count);
            }
        }

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
                if(getFragmentManager() != null) {
                    getFragmentManager().beginTransaction()
                            .remove(Labelling.this).commit();
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

    public void tapAudio() {
        mediaPlayerTouch = MediaPlayer.create(getContext(), R.raw.touchone);
        mediaPlayerTouch.start();
    }


    public void parrotFire() {
        parrotPlus();
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
        alienPlus();
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


    public void setViews() {
        labellingTextViewOption1.setVisibility(View.INVISIBLE);
        labellingTextViewOption2.setVisibility(View.INVISIBLE);
        labellingTextViewOption3.setVisibility(View.INVISIBLE);
        labellingTextViewOption4.setVisibility(View.INVISIBLE);
        labellingTextViewAnswer1.setVisibility(View.INVISIBLE);
        labellingTextViewAnswer2.setVisibility(View.INVISIBLE);
        labellingTextViewAnswer3.setVisibility(View.INVISIBLE);
        labellingTextViewAnswer4.setVisibility(View.INVISIBLE);

        if (selectedSubject.equalsIgnoreCase("English") || selectedSubject.equalsIgnoreCase("Maths") || selectedSubject.equalsIgnoreCase("Science")
                || selectedSubject.equalsIgnoreCase("Geography")) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    jab();
                    labellingTextViewOption1.setVisibility(View.VISIBLE);
                    labellingTextViewAnswer1.setVisibility(View.VISIBLE);

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            jab();
                            labellingTextViewOption2.setVisibility(View.VISIBLE);
                            labellingTextViewAnswer2.setVisibility(View.VISIBLE);
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    jab();
                                    labellingTextViewOption3.setVisibility(View.VISIBLE);
                                    labellingTextViewAnswer3.setVisibility(View.VISIBLE);
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            jab();
                                            labellingTextViewOption4.setVisibility(View.VISIBLE);
                                            labellingTextViewAnswer4.setVisibility(View.VISIBLE);

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
                    labellingTextViewOption4.setVisibility(View.VISIBLE);
                    labellingTextViewAnswer4.setVisibility(View.VISIBLE);

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            jab();
                            labellingTextViewOption3.setVisibility(View.VISIBLE);
                            labellingTextViewAnswer3.setVisibility(View.VISIBLE);
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    jab();
                                    labellingTextViewOption2.setVisibility(View.VISIBLE);
                                    labellingTextViewAnswer2.setVisibility(View.VISIBLE);
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            jab();
                                            labellingTextViewOption1.setVisibility(View.VISIBLE);
                                            labellingTextViewAnswer1.setVisibility(View.VISIBLE);

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
