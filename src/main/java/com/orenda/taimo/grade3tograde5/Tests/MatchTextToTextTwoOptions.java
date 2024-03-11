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
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.Nullable;

import firebase.analytics.AppAnalytics;

import androidx.fragment.app.Fragment;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.orenda.taimo.grade3tograde5.Models.ColumnPositionModel;
import com.orenda.taimo.grade3tograde5.Models.TestJsonParseModel;
import com.orenda.taimo.grade3tograde5.Models.compileQuadrantModel;
import com.orenda.taimo.grade3tograde5.R;
import com.orenda.taimo.grade3tograde5.SimpleTestActivity;
import com.orenda.taimo.grade3tograde5.SocraticActivity;

//import static com.facebook.FacebookSdk.getApplicationContext;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.testIndex;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.topic;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.unSocratic;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.alienLife;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.correctCount;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.deduct;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.parrotLife;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.selectedSubject;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.totalScore;
import static com.orenda.taimo.grade3tograde5.Tests.FingerLineTempTwoOptions.quadExist;

@SuppressLint("ValidFragment")


public class MatchTextToTextTwoOptions extends Fragment implements View.OnClickListener, View.OnTouchListener {

    MediaPlayer mediaPlayerTouch;
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;
    String selectedData;
    View selectedView;
    public static float canvasTotalX;
    public static float canvasTotalY;
    public static float startX;
    public static float startY;
    public static float endX;
    public static float endY;

    TextView TextViewParrotPlus, TextViewAlienPlus;
    TextView matchTextToTextTwoOptionsTextViewQuestion;
    TextView matchTextToTextTwoOptionsTextViewColumnLeft1, matchTextToTextTwoOptionsTextViewColumnLeft2;
    TextView matchTextToTextTwoOptionsTextViewColumnRight1, matchTextToTextTwoOptionsTextViewColumnRight2;
    boolean dropDone = false;
    public static ArrayList<View> matchTextToTextTwoOptionsTextViewList = new ArrayList<View>();
    ArrayList<compileQuadrantModel> answerList = new ArrayList<>();
    public static ArrayList<ColumnPositionModel> coulmnList = new ArrayList<ColumnPositionModel>();

    ImageView ImageViewParrotFire, ImageViewAlienAvatarLife, ImageViewParrotAvatarLife, ImageViewAlienFire;
    ImageView ImageViewParrotAvatar, ImageViewAlienAvatar;
    ImageView ImageViewParrotHit, ImageViewAlienHit;
    int realHeight;
    int realWidth;
    AppAnalytics appAnalytics;
    Timer T = new Timer();
    int count = 0;
    boolean start = true;
    RelativeLayout canvasLayout;
    View canvasView;

    float questionFontSize = 22;
    float optionFontSize = 22;
    float questionBigFontSize = 32;
    Integer[] arr = new Integer[2];
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
/*
    int DEMO_MODE=0;
    ConstraintLayout matchTextToTextTwoOptionsMainLayout;
    int  MatchTextToTextTwoOptionsXCoordinate,  MatchTextToTextTwoOptionsYCoordinate;
    ImageView handImagview;
    Boolean ordinaryMCQ;
    MediaPlayer mp;
    boolean demoStarted=false;
    String checkLang;
    boolean stillInDemoState=false;*/



    public MatchTextToTextTwoOptions(int testId, TestJsonParseModel test, Context context, Activity activity) {
        this.testId = testId;
        this.test = test;
        mContext = context;
        this.activity = activity;
        canvasTotalX = 0;
        canvasTotalY = 0;
        startX = 0;
        startY = 0;
        endX = 0;
        endY = 0;
        matchTextToTextTwoOptionsTextViewList = new ArrayList<View>();

        coulmnList = new ArrayList<ColumnPositionModel>();

        Log.wtf("-this", " TEST ID : " + testId);
    }

    public MatchTextToTextTwoOptions() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.match_text_to_text_two_options, container, false);
        player = ExoPlayerFactory.newSimpleInstance(getContext());
        sharedPrefs = getActivity().getApplicationContext().getSharedPreferences("DefaultSPForTaleemabadGrade6", 0);
        editor = sharedPrefs.edit();
        appAnalytics = new AppAnalytics(mContext);
        initializeView(view);

        return view;
    }

    public void initializeView(View view) {
        matchTextToTextTwoOptionsTextViewQuestion = view.findViewById(R.id.matchTextToTextTwoOptionsTextViewQuestion);

        matchTextToTextTwoOptionsTextViewColumnLeft1 = view.findViewById(R.id.matchTextToTextTwoOptionsTextViewColumnLeft1);
        matchTextToTextTwoOptionsTextViewColumnLeft2 = view.findViewById(R.id.matchTextToTextTwoOptionsTextViewColumnLeft2);

        matchTextToTextTwoOptionsTextViewColumnRight1 = view.findViewById(R.id.matchTextToTextTwoOptionsTextViewColumnRight1);
        matchTextToTextTwoOptionsTextViewColumnRight2 = view.findViewById(R.id.matchTextToTextTwoOptionsTextViewColumnRight2);

        matchTextToTextTwoOptionsTextViewList.add(matchTextToTextTwoOptionsTextViewColumnLeft1);
        matchTextToTextTwoOptionsTextViewList.add(matchTextToTextTwoOptionsTextViewColumnLeft2);

        matchTextToTextTwoOptionsTextViewList.add(matchTextToTextTwoOptionsTextViewColumnRight1);
        matchTextToTextTwoOptionsTextViewList.add(matchTextToTextTwoOptionsTextViewColumnRight2);

        canvasLayout = view.findViewById(R.id.canvasLayout);
        canvasView = view.findViewById(R.id.mainview);

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

//        matchTextToTextTwoOptionsMainLayout =view.findViewById(R.id.matchTextToTextTwoOptionsMainLayout);

        //  matchTextToTextTwoOptionsTextViewColumnLeft1.get

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


                    matchTextToTextTwoOptionsTextViewQuestion.setTextSize(questionFontSize);
//                    Left Columns

                    matchTextToTextTwoOptionsTextViewColumnLeft1.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    coulmnList.add(new ColumnPositionModel(matchTextToTextTwoOptionsTextViewColumnLeft1.getX(), matchTextToTextTwoOptionsTextViewColumnLeft1.getY(),
                            matchTextToTextTwoOptionsTextViewColumnLeft1.getWidth(), matchTextToTextTwoOptionsTextViewColumnLeft1.getHeight(), null));

                    matchTextToTextTwoOptionsTextViewColumnLeft1.setTextSize(optionFontSize);

                    matchTextToTextTwoOptionsTextViewColumnLeft2.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    coulmnList.add(new ColumnPositionModel(matchTextToTextTwoOptionsTextViewColumnLeft2.getX(), matchTextToTextTwoOptionsTextViewColumnLeft2.getY(),
                            matchTextToTextTwoOptionsTextViewColumnLeft2.getWidth(), matchTextToTextTwoOptionsTextViewColumnLeft2.getHeight(), null));
                    matchTextToTextTwoOptionsTextViewColumnLeft2.setTextSize(optionFontSize);


//                    RIGHT COLUMNS

                    matchTextToTextTwoOptionsTextViewColumnRight1.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    coulmnList.add(new ColumnPositionModel(matchTextToTextTwoOptionsTextViewColumnRight1.getX(), matchTextToTextTwoOptionsTextViewColumnRight1.getY(),
                            matchTextToTextTwoOptionsTextViewColumnRight1.getWidth(), matchTextToTextTwoOptionsTextViewColumnRight1.getHeight(), null));
                    matchTextToTextTwoOptionsTextViewColumnRight1.setTextSize(optionFontSize);

                    matchTextToTextTwoOptionsTextViewColumnRight2.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    coulmnList.add(new ColumnPositionModel(matchTextToTextTwoOptionsTextViewColumnRight2.getX(), matchTextToTextTwoOptionsTextViewColumnRight2.getY(),
                            matchTextToTextTwoOptionsTextViewColumnRight2.getWidth(), matchTextToTextTwoOptionsTextViewColumnRight2.getHeight(), null));
                    matchTextToTextTwoOptionsTextViewColumnRight2.setTextSize(optionFontSize);

                    canvasView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    canvasTotalX = (canvasView.getX() + canvasView.getWidth());
                    canvasTotalY = (canvasView.getY() + canvasView.getHeight());

                    ImageViewAlienAvatarLife.setBackgroundResource(R.drawable.alien_life_line_pink);
                    int level = alienLife * (100);   // pct goes from 0 to 100
                    ImageViewAlienAvatarLife.getBackground().setLevel(level);

                    ImageViewParrotAvatarLife.setBackgroundResource(R.drawable.parrot_life_line_blue);
                    int level1 = parrotLife * (100);
                    ImageViewParrotAvatarLife.getBackground().setLevel(level1);

                    Log.wtf("-this", " Canvas Width : " + canvasView.getWidth() + "  Height : " + canvasView.getHeight());
                    setUpTest();
                }
            });
        }

        for (int i = 0; i < matchTextToTextTwoOptionsTextViewList.size(); i++) {
            setBackGroundSingle(sharedPrefs.getString("SupervisedSubjectSelected", "English"), (TextView) matchTextToTextTwoOptionsTextViewList.get(i));
        }

        setOnClickListeners(view);
        startTestTimer();


        //To retrieve
//        SharedPreferences sharedPrefForChecking = getActivity().getSharedPreferences("DemoMatchTextToTextTwoOptions", DEMO_MODE);
//        ordinaryMCQ = sharedPrefForChecking.getBoolean("DemoMatchTextToTextTwoOptions", false); //0 is the default value


    }

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

            //  SimpleTestActivity.testActivityImageViewfeedback.setVisibility(View.GONE);
            ImageViewParrotAvatarLife.setVisibility(View.GONE);
            ImageViewParrotAvatar.setVisibility(View.GONE);
            ImageViewAlienAvatarLife.setVisibility(View.GONE);
            ImageViewAlienAvatar.setVisibility(View.GONE);

//                setOnClickListenr(view);
            final Handler handlerr = new Handler();
            handlerr.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    jab();
                    startDemo();
                }
            }, 100);

            SharedPreferences sharedPrefForSaving = getContext().getSharedPreferences("DemoMatchTextToTextTwoOptions", DEMO_MODE);
            SharedPreferences.Editor editor = sharedPrefForSaving.edit();
            editor.putBoolean("DemoMatchTextToTextTwoOptions", true);
            editor.apply();

        }
        else {
            SimpleTestActivity.testActivityImageViewHome.setVisibility(View.VISIBLE);
            SimpleTestActivity.testActivityImageViewDaimond.setVisibility(View.VISIBLE);
          //  SimpleTestActivity.testActivityImageViewfeedback.setVisibility(View.VISIBLE);
            ImageViewParrotAvatarLife.setVisibility(View.VISIBLE);
            ImageViewParrotAvatar.setVisibility(View.VISIBLE);
            ImageViewAlienAvatarLife.setVisibility(View.VISIBLE);
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
//            mp.setLooping(true);
            if (checkLang.equals("en")) {
                mp = MediaPlayer.create(getContext(), R.raw.eng3);
                mp.start();
            }
            else {
                mp = MediaPlayer.create(getContext(), R.raw.urdu3);
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
                            mp = MediaPlayer.create(getContext(), R.raw.eng3);

                        } else {
                            mp = MediaPlayer.create(getContext(), R.raw.urdu3);

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
            mp = MediaPlayer.create(getContext(), R.raw.eng3);

        }else {
            mp = MediaPlayer.create(getContext(), R.raw.urdu3);

        }
//        mp.setLooping(true);
        mp.start();

        demoStarted =true;

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();

                callForDemoVoice();

            }
        });


        final ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(matchTextToTextTwoOptionsMainLayout);
        final ImageView imageview = new ImageView(activity);

        imageview.setImageResource(R.drawable.hand);
        imageview.setId(ViewCompat.generateViewId());
        matchTextToTextTwoOptionsMainLayout.addView(imageview);

        constraintSet.connect(imageview.getId(), ConstraintSet.TOP, R.id.guidelineColumn1Top,ConstraintSet.TOP,10);
        constraintSet.connect(imageview.getId(),ConstraintSet.END, R.id.guidelineRightColumnRight,ConstraintSet.START,10);
        constraintSet.connect(imageview.getId(),ConstraintSet.START, R.id.guidelineRightColumnLeft,ConstraintSet.END,10);
        constraintSet.connect(imageview.getId(), ConstraintSet.BOTTOM, R.id.guidelineColumn1Bottom,ConstraintSet.TOP,10);
        constraintSet.applyTo(matchTextToTextTwoOptionsMainLayout);


        final Handler handlerr = new Handler();
        handlerr.postDelayed(new Runnable() {
            @Override
            public void run() {

                int[] location = new int[2];
                matchTextToTextTwoOptionsTextViewColumnRight1.getLocationOnScreen(location);
//                //    Toast.makeText(getContext(),"X axis is "+location[0] +"and Y axis is "+location[1],Toast.LENGTH_LONG).show();

                MatchTextToTextTwoOptionsXCoordinate = location[0];
                MatchTextToTextTwoOptionsYCoordinate = location[1];

                moveto(imageview);

            }
        }, 1200);

    }
    */
/*
    public void moveto(final ImageView imageview){
        int[] location1 = new int[2];
//        textViewList.get(0).getTextView().getLocationOnScreen(location1);

        matchTextToTextTwoOptionsTextViewColumnLeft2.getLocationOnScreen(location1);
        int movetoX = location1[0];
        int movetoY = location1[1];

        imageview.bringToFront();
        imageview.animate()
                .x(movetoX)
                .y(movetoY)
                .alpha(1.0f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(800)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        // animateup();

                        if (answerList.size()>0){
                            imageview.setVisibility(View.GONE);
                            return;
                        }
                        movetoback(imageview);

                    }
                });
    }
*/
    /*
    public void movetoback(final ImageView imageview){

        imageview.bringToFront();
        imageview.animate()
                .x(MatchTextToTextTwoOptionsXCoordinate)
                .y(MatchTextToTextTwoOptionsYCoordinate)
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(800)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        // animateup();

                        moveto(imageview);
                    }
                });
    }
*/

    public void setUpTest() {
        showViews();
    }

    public void showViews() {
        ImageViewAlienAvatarLife.setVisibility(View.VISIBLE);
        ImageViewParrotAvatarLife.setVisibility(View.VISIBLE);
        ImageViewParrotAvatar.setVisibility(View.VISIBLE);
        ImageViewAlienAvatar.setVisibility(View.VISIBLE);
        fillUpTest();
        matchTextToTextTwoOptionsTextViewQuestion.setVisibility(View.VISIBLE);
//        for (int i = 0; i < matchTextToTextTwoOptionsTextViewList.size();i++){
//            matchTextToTextTwoOptionsTextViewList.get(i).setVisibility(View.VISIBLE);
//        }
        setViews();
    }

    public void setBackGroundSingle(String subject, TextView matchTextToTextTwoOptionsTextView) {
        switch (subject) {
            case ("English"):
                matchTextToTextTwoOptionsTextView.setBackgroundResource(R.drawable.english_bg);

                break;
            case ("Maths"):
                matchTextToTextTwoOptionsTextView.setBackgroundResource(R.drawable.maths_bg);
                break;


            case ("Urdu"):
                matchTextToTextTwoOptionsTextView.setBackgroundResource(R.drawable.urdu_bg);
                break;

            case ("GeneralKnowledge"):
            case ("Science"):
                matchTextToTextTwoOptionsTextView.setBackgroundResource(R.drawable.science_bg);

                break;

        }
    }

    public void fillUpTest() {
        matchTextToTextTwoOptionsTextViewQuestion.setText(test.getQuestion().getText());

        generateRandom();
        matchTextToTextTwoOptionsTextViewColumnLeft1.setText(test.getLeftColumnList().get(arr[0]).getText());
        coulmnList.get(0).setText(test.getLeftColumnList().get(arr[0]).getText());
        matchTextToTextTwoOptionsTextViewColumnLeft2.setText(test.getLeftColumnList().get(arr[1]).getText());
        coulmnList.get(1).setText(test.getLeftColumnList().get(arr[1]).getText());

        generateRandom();

        matchTextToTextTwoOptionsTextViewColumnRight1.setText(test.getRightList().get(arr[0]).getText());
        coulmnList.get(2).setText(test.getRightList().get(arr[0]).getText());
        matchTextToTextTwoOptionsTextViewColumnRight2.setText(test.getRightList().get(arr[1]).getText());
        coulmnList.get(3).setText(test.getRightList().get(arr[1]).getText());

    }


    public void setOnClickListeners(View view) {
        canvasView.setOnTouchListener(this);

    }

    @Override
    public void onClick(View v) {
        if (sharedPrefs.getBoolean("SoundEnabled", true)) {
            tapAudio();
        }
        switch (v.getId()) {
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            FingerLineTempTwoOptions.findQuadrantTwoOptions fq = new FingerLineTempTwoOptions.findQuadrantTwoOptions(MatchTextToTextTwoOptions.startX, MatchTextToTextTwoOptions.startY, canvasTotalX, canvasTotalY);
            int StartQuadrant = fq.getQuadrant();
            FingerLineTempTwoOptions.findQuadrantTwoOptions fq1 = new FingerLineTempTwoOptions.findQuadrantTwoOptions(MatchTextToTextTwoOptions.endX, endY, canvasTotalX, canvasTotalY);
            int EndQuadrant = fq1.getQuadrant();
            if (StartQuadrant != 0 && EndQuadrant != 0 && StartQuadrant != EndQuadrant) {
                if (!quadExist(StartQuadrant, EndQuadrant)) {
                    if (StartQuadrant < 3 && EndQuadrant > 2) {
                        answerList.add(new compileQuadrantModel((StartQuadrant - 1), (EndQuadrant - 1)));

                    } else if (StartQuadrant > 2 && EndQuadrant < 3) {
                        answerList.add(new compileQuadrantModel((StartQuadrant - 1), (EndQuadrant - 1)));
                    } else {

                    }
                } else {
                }

            } else {

            }
//            stillInDemoState=false;

            if (answerList.size() == 2) {
                compileAns();
            }
        }
        return false;
    }

    public void compileAns() {
        //stillInDemoState=false;
        canvasView.setOnTouchListener(null);

        start = false;
        T.cancel();
        int count = 0;
        int score = 0;
        int tScore = 0;
        if (test.getDifficultyLevel().equalsIgnoreCase("easy")) {
            tScore = 5;
        } else if (test.getDifficultyLevel().equalsIgnoreCase("medium")) {
            tScore = 10;
        } else if (test.getDifficultyLevel().equalsIgnoreCase("hard")) {
            tScore = 15;
        } else if (test.getDifficultyLevel().equalsIgnoreCase("bonus")) {
            tScore = 25;
        } else if (test.getDifficultyLevel().equalsIgnoreCase("superEasy")) {
            tScore = 2;
        }

        Log.wtf("-this", "ColumnList SIze : " + coulmnList.size());
        for (int i = 0; i < answerList.size(); i++) {
            if (answerList.get(i).getStartQuadrant() < 2) {
                for (int j = 0; j < test.getLeftColumnList().size(); j++) {
                    Log.wtf("-this", "End Quad : " + answerList.get(i).getStartQuadrant() + " I : " + i + (coulmnList.get(answerList.get(i).getStartQuadrant()).getText()));
                    Log.wtf("-this", "Test List Size : " + test.getLeftColumnList().size() + "   and J : " + j + test.getLeftColumnList().get(j).getText());
                    if (coulmnList.get(answerList.get(i).getStartQuadrant()).getText()
                            .equalsIgnoreCase(test.getLeftColumnList().get(j).getText())) {

                        if (coulmnList.get(answerList.get(i).getEndQuadrant()).getText()
                                .equalsIgnoreCase(test.getRightList().get(j).getText())) {
                            if (unSocratic == true)
                                appAnalytics.setOptionSelected(selectedSubject, topic, test.getType(), (coulmnList.get(answerList.get(i).getEndQuadrant()).getText()
                                        + "," + test.getRightList().get(j).getText()), true);

                            score = score + tScore;
                            count++;
                            matchTextToTextTwoOptionsTextViewList.get(answerList.get(i).getStartQuadrant()).setBackgroundResource(R.drawable.ordinary_mcq_option_bg_green);
                            matchTextToTextTwoOptionsTextViewList.get(answerList.get(i).getEndQuadrant()).setBackgroundResource(R.drawable.ordinary_mcq_option_bg_green);
                        } else {
                            if (unSocratic == true)
                                appAnalytics.setOptionSelected(selectedSubject, topic, test.getType(), (coulmnList.get(answerList.get(i).getEndQuadrant()).getText()
                                        + "," + test.getRightList().get(j).getText()), false);

                            matchTextToTextTwoOptionsTextViewList.get(answerList.get(i).getStartQuadrant()).setBackgroundResource(R.drawable.ordinary_mcq_option_bg_red);
                            matchTextToTextTwoOptionsTextViewList.get(answerList.get(i).getEndQuadrant()).setBackgroundResource(R.drawable.ordinary_mcq_option_bg_red);
                        }
                    }
                }

            }
            if (answerList.get(i).getStartQuadrant() > 1) {
                for (int j = 0; j < test.getLeftColumnList().size(); j++) {
                    if (coulmnList.get(answerList.get(i).getStartQuadrant()).getText()
                            .equalsIgnoreCase(test.getRightList().get(j).getText())) {
                        if (coulmnList.get(answerList.get(i).getEndQuadrant()).getText()
                                .equalsIgnoreCase(test.getLeftColumnList().get(j).getText())) {
                            if (unSocratic == true)
                                appAnalytics.setOptionSelected(selectedSubject, topic, test.getType(), (coulmnList.get(answerList.get(i).getEndQuadrant()).getText()
                                        + "," + test.getLeftColumnList().get(j).getText()), true);

                            score = score + tScore;
                            count++;
                            matchTextToTextTwoOptionsTextViewList.get(answerList.get(i).getStartQuadrant()).setBackgroundResource(R.drawable.ordinary_mcq_option_bg_green);
                            matchTextToTextTwoOptionsTextViewList.get(answerList.get(i).getEndQuadrant()).setBackgroundResource(R.drawable.ordinary_mcq_option_bg_green);
                        } else {
                            if (unSocratic == true)
                                appAnalytics.setOptionSelected(selectedSubject, topic, test.getType(), (coulmnList.get(answerList.get(i).getEndQuadrant()).getText()
                                        + "," + test.getLeftColumnList().get(j).getText()), false);

                            matchTextToTextTwoOptionsTextViewList.get(answerList.get(i).getStartQuadrant()).setBackgroundResource(R.drawable.ordinary_mcq_option_bg_red);
                            matchTextToTextTwoOptionsTextViewList.get(answerList.get(i).getEndQuadrant()).setBackgroundResource(R.drawable.ordinary_mcq_option_bg_red);
                        }
                    }
                }

            }

        }

        if (count == 2) {
            if (unSocratic == true) {
                appAnalytics.setAnswer(selectedSubject, topic + "" + testIndex, topic, test.getType(), test.getAnswerList().size(), count, true, this.count);
            }

        } else {
            if (unSocratic == true) {
                appAnalytics.setAnswer(selectedSubject, topic + "" + testIndex, topic, test.getType(), test.getAnswerList().size(), count, false, this.count);
            }
        }


        if (score < ((tScore * 2) / 2)) {
            alienFire();
        } else {
            parrotFire();
            correctCount++;

        }

        totalScore = totalScore + score;

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
                            .remove(MatchTextToTextTwoOptions.this).commit();
                }

            }
        }, 1000);

    }

    public void generateRandom() {

        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
            Log.wtf("_this", "Arr : " + i);
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
        matchTextToTextTwoOptionsTextViewColumnLeft1.setVisibility(View.INVISIBLE);
        matchTextToTextTwoOptionsTextViewColumnLeft2.setVisibility(View.INVISIBLE);

        matchTextToTextTwoOptionsTextViewColumnRight1.setVisibility(View.INVISIBLE);
        matchTextToTextTwoOptionsTextViewColumnRight2.setVisibility(View.INVISIBLE);

        if (selectedSubject.equalsIgnoreCase("English") || selectedSubject.equalsIgnoreCase("Maths") || selectedSubject.equalsIgnoreCase("Science")
                || selectedSubject.equalsIgnoreCase("Geography")) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    jab();
                    matchTextToTextTwoOptionsTextViewColumnLeft1.setVisibility(View.VISIBLE);
                    matchTextToTextTwoOptionsTextViewColumnLeft2.setVisibility(View.VISIBLE);

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            jab();
                            matchTextToTextTwoOptionsTextViewColumnRight1.setVisibility(View.VISIBLE);
                            matchTextToTextTwoOptionsTextViewColumnRight2.setVisibility(View.VISIBLE);

//                            setUpDemo();

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
                    matchTextToTextTwoOptionsTextViewColumnRight1.setVisibility(View.VISIBLE);
                    matchTextToTextTwoOptionsTextViewColumnRight2.setVisibility(View.VISIBLE);

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            jab();
                            matchTextToTextTwoOptionsTextViewColumnLeft1.setVisibility(View.VISIBLE);
                            matchTextToTextTwoOptionsTextViewColumnLeft2.setVisibility(View.VISIBLE);


//                            setUpDemo();
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

    void setAudioDescription(String filName, TextView textView) {

        player.addListener(new Player.EventListener() {
            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

//                if (playbackState == ExoPlayer.STATE_ENDED && (compileCalled == false)){
//                    compileCalled = true;
//                    Log.wtf("-thus"," PlayerState : END");
//                    compileAns(textView);
//                }
            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }

        });

        dataSourceFactory = new DefaultDataSourceFactory(getActivity().getApplicationContext(), "ExoplayerDemo");
        extractorsFactory = new DefaultExtractorsFactory();


        String url = streamUrl + filName + ".mp3";
        Log.wtf("-thus", "Audio URL  : " + url);
        mediaSource = new ExtractorMediaSource(Uri.parse(url),
                dataSourceFactory,
                extractorsFactory,
                null,
                null);

        player.prepare(mediaSource);

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


}
