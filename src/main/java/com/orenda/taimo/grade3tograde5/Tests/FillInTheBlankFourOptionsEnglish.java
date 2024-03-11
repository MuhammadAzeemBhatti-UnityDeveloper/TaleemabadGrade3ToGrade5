package com.orenda.taimo.grade3tograde5.Tests;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.core.content.res.ResourcesCompat;

import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
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
import android.widget.LinearLayout;
import android.widget.TextView;


import java.io.File;
import java.util.ArrayList;
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
import com.orenda.taimo.grade3tograde5.Models.DragNDropTextViewModel;
import com.orenda.taimo.grade3tograde5.Models.FillInTheBlanksTextViewModel;
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
public class FillInTheBlankFourOptionsEnglish extends Fragment implements View.OnClickListener, View.OnTouchListener, View.OnDragListener {

    MediaPlayer mediaPlayerTouch;
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;

    TextView fillInTheBlanksFourOptionsTextViewOption1, fillInTheBlanksFourOptionsTextViewOption2, fillInTheBlanksFourOptionsTextViewOption3, fillInTheBlanksFourOptionsTextViewOption4;


    LinearLayout fillInTheBlanksLayoutFourOptionsRow1, fillInTheBlanksLayoutFourOptionsRow2, fillInTheBlanksLayoutFourOptionsRow3, fillInTheBlanksLayoutFourOptionsRow4, fillInTheBlanksLayoutFourOptionsRow5, fillInTheBlanksLayoutFourOptionsRow6;
    ArrayList<FillInTheBlanksTextViewModel> textViewList = new ArrayList<>();
    ArrayList<DragNDropTextViewModel> optionsList = new ArrayList<>();
    private String selectedData = null;
    private TextView selectedView = null;
    private int answerCount = 0;
    float fontSize = 22;
    ArrayList<LinearLayout> linearLayouts = new ArrayList<>();
    public int stringIndex = 0;
    boolean isDroped = false;
    float questionBigFontSize = 32;
    ArrayList<TestJsonParseModel> testList = new ArrayList<>();
    AppAnalytics appAnalytics;
    Timer T = new Timer();
    int count = 0;
    boolean start = true;
    TextView TextViewParrotPlus,TextViewAlienPlus;
    ImageView ImageViewParrotFire, ImageViewAlienAvatarLife, ImageViewParrotAvatarLife, ImageViewAlienFire;
    ImageView ImageViewParrotAvatar, ImageViewAlienAvatar;
    ImageView ImageViewParrotHit, ImageViewAlienHit;
    int realHeight;
    int realWidth;

    int fillInTheBlanksTextViewOption1Height;
    int fillInTheBlanksTextViewOption1Width;
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

    /*int DEMO_MODE=0;
    ConstraintLayout fillInTheBlanksFourOptionsMainLayout;
    int  fillInTheBlanksTextViewOption2XCoordinate,  fillInTheBlanksTextViewOption2YCoordinate;
    int whichOptionisCorrect;
    Boolean ordinaryMCQ;
    MediaPlayer mp;
    boolean demoStarted=false;
    String checkLang;
    boolean stillInDemoState=false;*/

    public FillInTheBlankFourOptionsEnglish(int testId, TestJsonParseModel test, Context context, Activity activity) {
        this.testId = testId;
        this.test = test;
        mContext = context;
        this.activity = activity;

        Log.wtf("-this", " TEST ID : " + testId);
    }

    public FillInTheBlankFourOptionsEnglish() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fill_in_the_blank_four_options, container, false);
        player = ExoPlayerFactory.newSimpleInstance(getContext());
        sharedPrefs = getActivity().getApplicationContext().getSharedPreferences("DefaultSPForTaleemabadGrade6", 0);
        editor = sharedPrefs.edit();
        appAnalytics = new AppAnalytics(mContext);
        initializeView(view);

        return view;
    }

    public void onButtonPressed(Uri uri) {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    public void initializeView(final View view) {

        fillInTheBlanksLayoutFourOptionsRow1 = view.findViewById(R.id.fillInTheBlanksFourOptionsRow1);
        fillInTheBlanksLayoutFourOptionsRow2 = view.findViewById(R.id.fillInTheBlanksLayoutFourOptionsRow2);
        fillInTheBlanksLayoutFourOptionsRow3 = view.findViewById(R.id.fillInTheBlanksLayoutFourOptionsRow3);
        fillInTheBlanksLayoutFourOptionsRow4 = view.findViewById(R.id.fillInTheBlanksLayoutFourOptionsRow4);
        fillInTheBlanksLayoutFourOptionsRow5 = view.findViewById(R.id.fillInTheBlanksLayoutFourOptionsRow5);
        fillInTheBlanksLayoutFourOptionsRow6 = view.findViewById(R.id.fillInTheBlanksLayoutFourOptionsRow6);
        linearLayouts.add(fillInTheBlanksLayoutFourOptionsRow1);
        linearLayouts.add(fillInTheBlanksLayoutFourOptionsRow2);
        linearLayouts.add(fillInTheBlanksLayoutFourOptionsRow3);
        linearLayouts.add(fillInTheBlanksLayoutFourOptionsRow4);
        linearLayouts.add(fillInTheBlanksLayoutFourOptionsRow5);
        linearLayouts.add(fillInTheBlanksLayoutFourOptionsRow6);

        TextViewParrotPlus = view.findViewById(R.id.TextViewParrotPlus);
        TextViewAlienPlus = view.findViewById(R.id.TextViewAlienPlus);

        fillInTheBlanksFourOptionsTextViewOption1 = view.findViewById(R.id.fillInTheBlanksFourOptionsTextViewOption1);
        fillInTheBlanksFourOptionsTextViewOption2 = view.findViewById(R.id.fillInTheBlanksFourOptionsTextViewOption2);
        fillInTheBlanksFourOptionsTextViewOption3 = view.findViewById(R.id.fillInTheBlanksFourOptionsTextViewOption3);
        fillInTheBlanksFourOptionsTextViewOption4 = view.findViewById(R.id.fillInTheBlanksFourOptionsTextViewOption4);

        ImageViewParrotFire = view.findViewById(R.id.ImageViewParrotFire);
        ImageViewAlienFire = view.findViewById(R.id.ImageViewAlienFire);
        ImageViewParrotAvatarLife = view.findViewById(R.id.ImageViewParrotAvatarLife);
        ImageViewAlienAvatarLife = view.findViewById(R.id.ImageViewAlienAvatarLife);
        ImageViewParrotAvatar = view.findViewById(R.id.ImageViewParrotAvatar);
        ImageViewAlienAvatar = view.findViewById(R.id.ImageViewAlienAvatar);
        ImageViewParrotHit = view.findViewById(R.id.ImageViewParrotHit);
        ImageViewAlienHit = view.findViewById(R.id.ImageViewAlienHit);

        //fillInTheBlanksFourOptionsMainLayout = view.findViewById(R.id.fillInTheBlanksFourOptionsMainLayout);

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

                    fontSize = (0.029f * realHeight);
                    if (screenInches < 7) {
                        fontSize = (0.029f * (1080f / 2f));
                    } else {
                        fontSize = (0.029f * 800);
                    }
                    Log.wtf("-this", "height : " + realHeight + "  FONT SIZE : " + fontSize);

                    float fontSizeQuestion = (0.03f * realHeight);
                    if (screenInches < 7) {
                        fontSizeQuestion = (0.03f * (1080 / 2));
                    } else {
                        fontSizeQuestion = (0.03f * 800);
                    }

                    Log.wtf("-this", "height : " + realHeight + "  Q SFONT SIZE : " + fontSizeQuestion);
                    fillInTheBlanksFourOptionsTextViewOption1.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    fillInTheBlanksTextViewOption1Height = fillInTheBlanksFourOptionsTextViewOption1.getHeight();
                    fillInTheBlanksTextViewOption1Width = fillInTheBlanksFourOptionsTextViewOption1.getWidth();


                    fillInTheBlanksFourOptionsTextViewOption1.setTextSize(fontSize);
                    fillInTheBlanksFourOptionsTextViewOption2.setTextSize(fontSize);
                    fillInTheBlanksFourOptionsTextViewOption3.setTextSize(fontSize);
                    fillInTheBlanksFourOptionsTextViewOption4.setTextSize(fontSize);

                    ImageViewAlienAvatarLife.setBackgroundResource(R.drawable.alien_life_line_pink);
                    int level = alienLife * (100);   // pct goes from 0 to 100
                    ImageViewAlienAvatarLife.getBackground().setLevel(level);

                    ImageViewParrotAvatarLife.setBackgroundResource(R.drawable.parrot_life_line_blue);
                    int level1 = parrotLife * (100);
                    ImageViewParrotAvatarLife.getBackground().setLevel(level1);

                    setUpTest();

                }
            });
        }


      //  setOnClickListeners(view);
        startTestTimer();

        //SharedPreferences sharedPref = getActivity().getSharedPreferences("DemoFillInTheBlankFourOptionsEnglish", DEMO_MODE);
        //ordinaryMCQ = sharedPref.getBoolean("DemoFillInTheBlankFourOptionsEnglish", false); //0 is the default value

        setOnClickListeners(view);

    }

    public void setUpTest() {
       showViews();
    }

    public void showViews() {

        setBackGrounds(sharedPrefs.getString("SupervisedSubjectSelected", "English"));

//        fillInTheBlanksFourOptionsTextViewOption1.setVisibility(View.VISIBLE);
//        fillInTheBlanksFourOptionsTextViewOption2.setVisibility(View.VISIBLE);
//        fillInTheBlanksFourOptionsTextViewOption3.setVisibility(View.VISIBLE);
//        fillInTheBlanksFourOptionsTextViewOption4.setVisibility(View.VISIBLE);

        ImageViewAlienAvatarLife.setVisibility(View.VISIBLE);
        ImageViewParrotAvatarLife.setVisibility(View.VISIBLE);
        ImageViewParrotAvatar.setVisibility(View.VISIBLE);
        ImageViewAlienAvatar.setVisibility(View.VISIBLE);

        fillUpTest((fillInTheBlanksFourOptionsTextViewOption1.getWidth() - 5), (fillInTheBlanksFourOptionsTextViewOption1.getHeight() - 5 ));
        setViews();

    }

/*
    void setUpDemo(){
        if (!ordinaryMCQ){
            //fillUpTest();

//            setOnClickListenr(view);

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



//                setOnClickListenr(view);
            final Handler handlerr = new Handler();
            handlerr.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    jab();
                    ImageViewAlienAvatarLife.setVisibility(View.GONE);
                    ImageViewParrotAvatarLife.setVisibility(View.GONE);
                    ImageViewParrotAvatar.setVisibility(View.GONE);
                    ImageViewAlienAvatar.setVisibility(View.GONE);
                    startDemo();

                }
            }, 100);

            SharedPreferences sharedPrefForSaving = getContext().getSharedPreferences("DemoFillInTheBlankFourOptionsEnglish", DEMO_MODE);
            SharedPreferences.Editor editor = sharedPrefForSaving.edit();
            editor.putBoolean("DemoFillInTheBlankFourOptionsEnglish", true);
            editor.apply();

        }
        else {
            SimpleTestActivity.testActivityImageViewHome.setVisibility(View.VISIBLE);
            SimpleTestActivity.testActivityImageViewDaimond.setVisibility(View.VISIBLE);
           // SimpleTestActivity.testActivityImageViewfeedback.setVisibility(View.VISIBLE);
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
          //  mp.setLooping(true);
            if (checkLang.equals("en")) {
                mp = MediaPlayer.create(getContext(), R.raw.eng2);
                mp.start();
            }
            else {
                mp = MediaPlayer.create(getContext(), R.raw.urdu2);
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
                            mp = MediaPlayer.create(getContext(), R.raw.eng2);

                        } else {
                            mp = MediaPlayer.create(getContext(), R.raw.urdu2);

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
            mp = MediaPlayer.create(getContext(), R.raw.eng2);

        }else {
            mp = MediaPlayer.create(getContext(), R.raw.urdu2);

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
        final ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(fillInTheBlanksFourOptionsMainLayout);
        final ImageView imageview = new ImageView(activity);

        imageview.setImageResource(R.drawable.hand);
        imageview.setId(ViewCompat.generateViewId());
        fillInTheBlanksFourOptionsMainLayout.addView(imageview);



        String ans =textViewList.get(0).getAnswer();
        String ansOption1 =fillInTheBlanksFourOptionsTextViewOption1.getText().toString();
        String ansOption2 =fillInTheBlanksFourOptionsTextViewOption2.getText().toString();
        String ansOption3 =fillInTheBlanksFourOptionsTextViewOption3.getText().toString();
        String ansOption4 =fillInTheBlanksFourOptionsTextViewOption4.getText().toString();

        if (ansOption1.equals(ans)) {

            constraintSet.connect(imageview.getId(), ConstraintSet.TOP, R.id.guidelineOptionTop,ConstraintSet.TOP,10);
            constraintSet.connect(imageview.getId(), ConstraintSet.END, R.id.guidelineOption1Right,ConstraintSet.START,10);
            constraintSet.connect(imageview.getId(),ConstraintSet.START, R.id.guidelineOption1Left,ConstraintSet.END,10);
            constraintSet.connect(imageview.getId(),ConstraintSet.BOTTOM, R.id.guidelineOptionBottom,ConstraintSet.TOP,10);
            constraintSet.applyTo(fillInTheBlanksFourOptionsMainLayout);

            whichOptionisCorrect =1;
            final Handler handlerr = new Handler();
            handlerr.postDelayed(new Runnable() {
                @Override
                public void run() {

                    int[] location = new int[2];
                    fillInTheBlanksFourOptionsTextViewOption1.getLocationOnScreen(location);

                    fillInTheBlanksTextViewOption2XCoordinate = location[0];
                    fillInTheBlanksTextViewOption2YCoordinate= location[1];

                    moveto(imageview);

                }
            }, 1200);


        }else if (ansOption2.equals(ans)){


            constraintSet.connect(imageview.getId(), ConstraintSet.TOP, R.id.guidelineOptionTop, ConstraintSet.TOP, 10);
            constraintSet.connect(imageview.getId(), ConstraintSet.END, R.id.guidelineOption2Right, ConstraintSet.START, 10);
            constraintSet.connect(imageview.getId(), ConstraintSet.START, R.id.guidelineOption2Left, ConstraintSet.END, 10);
            constraintSet.connect(imageview.getId(), ConstraintSet.BOTTOM, R.id.guidelineOptionBottom, ConstraintSet.TOP, 10);
            constraintSet.applyTo(fillInTheBlanksFourOptionsMainLayout);

            whichOptionisCorrect =2;

            final Handler handlerr = new Handler();
            handlerr.postDelayed(new Runnable() {
                @Override
                public void run() {

                    int[] location = new int[2];
                    fillInTheBlanksFourOptionsTextViewOption2.getLocationOnScreen(location);

                    fillInTheBlanksTextViewOption2XCoordinate = location[0];
                    fillInTheBlanksTextViewOption2YCoordinate= location[1];

                    moveto(imageview);

                }
            }, 1200);

        }
        else if (ansOption3.equals(ans)){

            constraintSet.connect(imageview.getId(), ConstraintSet.TOP, R.id.guidelineOptionTop, ConstraintSet.TOP, 10);
            constraintSet.connect(imageview.getId(), ConstraintSet.END, R.id.guidelineOption3Right, ConstraintSet.START, 10);
            constraintSet.connect(imageview.getId(), ConstraintSet.START, R.id.guidelineOption3Left, ConstraintSet.END, 10);
            constraintSet.connect(imageview.getId(), ConstraintSet.BOTTOM, R.id.guidelineOptionBottom, ConstraintSet.TOP, 10);
            constraintSet.applyTo(fillInTheBlanksFourOptionsMainLayout);
            final Handler handlerr = new Handler();
            whichOptionisCorrect =3;
            handlerr.postDelayed(new Runnable() {
                @Override
                public void run() {

                    int[] location = new int[2];
                    fillInTheBlanksFourOptionsTextViewOption3.getLocationOnScreen(location);

                    fillInTheBlanksTextViewOption2XCoordinate = location[0];
                    fillInTheBlanksTextViewOption2YCoordinate= location[1];

                    moveto(imageview);

                }
            }, 1200);


        }
        else if (ansOption4.equals(ans)){

            constraintSet.connect(imageview.getId(), ConstraintSet.TOP, R.id.guidelineOptionTop, ConstraintSet.TOP, 10);
            constraintSet.connect(imageview.getId(), ConstraintSet.END, R.id.guidelineOption4Right, ConstraintSet.START, 10);
            constraintSet.connect(imageview.getId(), ConstraintSet.START, R.id.guidelineOption4Left, ConstraintSet.END, 10);
            constraintSet.connect(imageview.getId(), ConstraintSet.BOTTOM, R.id.guidelineOptionBottom, ConstraintSet.TOP, 10);
            constraintSet.applyTo(fillInTheBlanksFourOptionsMainLayout);

            whichOptionisCorrect =4;

            final Handler handlerr = new Handler();
            handlerr.postDelayed(new Runnable() {
                @Override
                public void run() {

                    int[] location = new int[2];
                    fillInTheBlanksFourOptionsTextViewOption4.getLocationOnScreen(location);

                    fillInTheBlanksTextViewOption2XCoordinate = location[0];
                    fillInTheBlanksTextViewOption2YCoordinate= location[1];

                    moveto(imageview);

                }
            }, 1200);

        }


    }
    public void moveto(final ImageView imageview){
        imageview.setVisibility(View.VISIBLE);
        int[] location1 = new int[2];
        textViewList.get(0).getTextView().getLocationOnScreen(location1);

        final int movetoX = location1[0];
        final int movetoY = location1[1];


        if (whichOptionisCorrect==1){
            fillInTheBlanksFourOptionsTextViewOption1.animate()
                    .translationY(60)
                    .alpha(0f)
                    .setDuration(500)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            fillInTheBlanksFourOptionsTextViewOption1.animate()
                                    .translationY(0)
                                    .alpha(1.0f)
                                    .setDuration(500)
                                    .setListener(new AnimatorListenerAdapter() {
                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            super.onAnimationEnd(animation);
                                        }
                                    });

                        }
                    });



        }else if (whichOptionisCorrect==2){
            fillInTheBlanksFourOptionsTextViewOption2.animate()
                    .translationY(60)
                    .alpha(0f)
                    .setDuration(500)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            fillInTheBlanksFourOptionsTextViewOption2.animate()
                                    .translationY(0)
                                    .alpha(1.0f)
                                    .setDuration(500)
                                    .setListener(new AnimatorListenerAdapter() {
                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            super.onAnimationEnd(animation);
                                        }
                                    });

                        }
                    });



        }
        else if (whichOptionisCorrect==3){
            fillInTheBlanksFourOptionsTextViewOption3.animate()
                    .translationY(60)
                    .alpha(0f)
                    .setDuration(500)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            fillInTheBlanksFourOptionsTextViewOption3.animate()
                                    .translationY(0)
                                    .alpha(1.0f)
                                    .setDuration(500)
                                    .setListener(new AnimatorListenerAdapter() {
                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            super.onAnimationEnd(animation);
                                        }
                                    });

                        }
                    });


        }
        else if (whichOptionisCorrect==4){
            fillInTheBlanksFourOptionsTextViewOption4.animate()
                    .translationY(60)
                    .alpha(0f)
                    .setDuration(500)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            fillInTheBlanksFourOptionsTextViewOption4.animate()
                                    .translationY(0)
                                    .alpha(1.0f)
                                    .setDuration(500)
                                    .setListener(new AnimatorListenerAdapter() {
                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            super.onAnimationEnd(animation);
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
                .scaleX(0.8f)
                .scaleY(0.8f)
                .setDuration(500)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        final Handler handlerr = new Handler();
                        handlerr.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                imageview.animate()
                                        .x(movetoX)
                                        .y(movetoY-1)
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
                        }, 500);    }
                });
    }

    public void movetoback(final ImageView imageview){

        imageview.bringToFront();
        imageview.animate()
                .x(fillInTheBlanksTextViewOption2XCoordinate)
                .y(fillInTheBlanksTextViewOption2YCoordinate)
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(500)
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


    public void setBackGrounds(String subject) {
        switch (subject) {
            case ("English"):
                fillInTheBlanksFourOptionsTextViewOption1.setBackgroundResource(R.drawable.english_bg);
                fillInTheBlanksFourOptionsTextViewOption2.setBackgroundResource(R.drawable.english_bg);
                fillInTheBlanksFourOptionsTextViewOption3.setBackgroundResource(R.drawable.english_bg);
                fillInTheBlanksFourOptionsTextViewOption4.setBackgroundResource(R.drawable.english_bg);

                break;
            case ("Maths"):
                fillInTheBlanksFourOptionsTextViewOption1.setBackgroundResource(R.drawable.maths_bg);
                fillInTheBlanksFourOptionsTextViewOption2.setBackgroundResource(R.drawable.maths_bg);
                fillInTheBlanksFourOptionsTextViewOption3.setBackgroundResource(R.drawable.maths_bg);
                fillInTheBlanksFourOptionsTextViewOption4.setBackgroundResource(R.drawable.maths_bg);
                break;

            case ("Urdu"):
                fillInTheBlanksFourOptionsTextViewOption1.setBackgroundResource(R.drawable.urdu_bg);
                fillInTheBlanksFourOptionsTextViewOption2.setBackgroundResource(R.drawable.urdu_bg);
                fillInTheBlanksFourOptionsTextViewOption3.setBackgroundResource(R.drawable.urdu_bg);
                fillInTheBlanksFourOptionsTextViewOption4.setBackgroundResource(R.drawable.urdu_bg);

                break;
            case ("GeneralKnowledge"):
            case ("Science"):
                fillInTheBlanksFourOptionsTextViewOption1.setBackgroundResource(R.drawable.science_bg);
                fillInTheBlanksFourOptionsTextViewOption2.setBackgroundResource(R.drawable.science_bg);
                fillInTheBlanksFourOptionsTextViewOption3.setBackgroundResource(R.drawable.science_bg);
                fillInTheBlanksFourOptionsTextViewOption4.setBackgroundResource(R.drawable.science_bg);
                break;

        }
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

    public void fillUpTest(int fillInTheBlanksTextViewOption1Width, int fillInTheBlanksTextViewOption1Height) {
        Typeface face = ResourcesCompat.getFont(getContext(),
                R.font.raleway_bold);

        Log.wtf("-this ","English");
        String test01 = test.getQuestion().getText();
        textViewList.clear();
        fillInTheBlanksFourOptionsTextViewOption1.setText(test.getOptionList().get(0).getText());
        fillInTheBlanksFourOptionsTextViewOption2.setText(test.getOptionList().get(1).getText());
        fillInTheBlanksFourOptionsTextViewOption3.setText(test.getOptionList().get(2).getText());
        fillInTheBlanksFourOptionsTextViewOption4.setText(test.getOptionList().get(3).getText());

        Log.wtf("-this", "Text Q : " + test01);
        int TextLength = test01.length();
        Log.wtf("-this", "Length : " + test01.length());
        int lineSize = 57;
        int div = 0;
        int mod = 0;
        if(TextLength + 15 < (lineSize+1)){
            div = 1;
        } else{
            div = (TextLength + 15) / lineSize;
            mod = TextLength % lineSize;
            if (mod > 0) {
                div = div + 1;
            }
        }

        stringIndex = 0;

        Log.wtf("-this", "MOD : " + mod + "  DIV : " + div);
        for (int j = 0; j < div; j++) {
            int i = j;
            if (div < 3){
                i = j + 2;
            }
            String test0 = null;
            if(this.stringIndex < test01.length()){
                if (test01.length() > (this.stringIndex + lineSize)) {
                    Log.wtf("-this", "IF  : " + 238);
                    test0 = test01.substring(this.stringIndex, (this.stringIndex + lineSize));
                } else {
                    Log.wtf("-this", "ELSE  : " + 242);
                    test0 = test01.substring(this.stringIndex);
                }
                Log.wtf("-this", "Index  : " + i + " stringIndex  : " + this.stringIndex + "   TEXT : " + test0 + "  Length : " + test0.length());
//                        if(i == 0) {
                if (test0.length() < lineSize + 1) {
                    String[] resultArray0 = test0.split("_");
                    int resultArraySize = resultArray0.length;
                    Log.wtf("-this", "IF  : " + 251 + "  " + resultArraySize);

                    if (resultArraySize == 1) {
                        Log.wtf("-this", "IF  : " + 253);
                        // SetQhere
                        TextView tv0q = new TextView(getContext());

                        String temp = resultArray0[0];
                        if(temp.length() < lineSize){

                            tv0q.setText(temp);
                            tv0q.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            linearLayouts.get(i).addView(tv0q);
                            tv0q.setTextColor(getResources().getColor(R.color.cardview_light_background));
                            tv0q.setTextSize(fontSize);
                            tv0q.setTypeface(face);
                            this.stringIndex = temp.length() + this.stringIndex;
                        } else{
                            String temp1 = temp.substring(0, temp.lastIndexOf(' '));
                            tv0q.setText(temp1);
                            tv0q.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            linearLayouts.get(i).addView(tv0q);
                            tv0q.setTextColor(getResources().getColor(R.color.cardview_light_background));
                            tv0q.setTextSize(fontSize);
                            tv0q.setTypeface(face);
                            this.stringIndex = temp1.length() + this.stringIndex;
                        }


                    } else if (resultArraySize == 2) {
                        Log.wtf("-this", "IF  : " + 268);
                        if (test0.contains("_")) {
                            Log.wtf("-this", "IF  : " + 270);
                            Log.wtf("-this", "resultArray0[0]  : " + resultArray0[0] + " Length : " + resultArray0[0].length());
                            Log.wtf("-this", "resultArray0[0]  : " + resultArray0[0].lastIndexOf(' '));
                            Log.wtf("-this", "resultArray0[1]  : " + resultArray0[1] + " Length : " + resultArray0[1].length());
                            Log.wtf("-this", "resultArray0[1]  : " + resultArray0[1].indexOf(' '));

                            if ((resultArray0[0].length() + resultArray0[1].length() + 15) < (lineSize + 1)) {

                                Log.wtf("-this", "IF  : " + 278);
                                TextView tv0q = new TextView(getContext());

                                String temp10 = resultArray0[0];
                                if(temp10.length() > 0){
                                    tv0q.setText(temp10);
                                    tv0q.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                    linearLayouts.get(i).addView(tv0q);
                                    tv0q.setTextColor(getResources().getColor(R.color.cardview_light_background));
                                    tv0q.setTextSize(fontSize);
                                    tv0q.setTypeface(face);
                                }

                                // SetAnsHere
                                TextView tv0a = new TextView(getContext());

                                tv0a.setLayoutParams(new ViewGroup.LayoutParams(fillInTheBlanksTextViewOption1Width, fillInTheBlanksTextViewOption1Height));
                                tv0a.setBackgroundResource(R.drawable.maths_mcq_blank_roundbg);
                                linearLayouts.get(i).addView(tv0a);
                                tv0a.setTextColor(getResources().getColor(R.color.cardview_light_background));
                                tv0a.setTextSize(fontSize-3);
                                tv0a.setTypeface(face);
                                tv0a.setTextColor(Color.TRANSPARENT);
                                tv0a.setGravity(Gravity.CENTER);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                                    tv0a.setId(View.generateViewId());
                                }
                                textViewList.add(new FillInTheBlanksTextViewModel(tv0a, ""));


                                if(resultArray0[1].length() > 1){
                                    TextView tv1q = new TextView(getContext());
                                    String temp = resultArray0[1];

                                    tv1q.setText(temp);
                                    tv1q.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                    linearLayouts.get(i).addView(tv1q);
                                    tv1q.setTextColor(getResources().getColor(R.color.cardview_light_background));
                                    tv1q.setTextSize(fontSize);
                                    tv1q.setTypeface(face);

                                    this.stringIndex = temp.length() + temp10.length() + this.stringIndex;
                                } else{
                                    this.stringIndex = + temp10.length() + this.stringIndex;
                                }
                            } else {
                                Log.wtf("-this", "Else  : " + 281);
                                String temp0 = resultArray0[0];
                                int remainingIndex = (lineSize - (resultArray0[0].length() + 15));

                                if (remainingIndex > 0 ) {
                                    Log.wtf("-this", "IF  : " + 285);
                                    Log.wtf("-this", "RemainIndex  : " + remainingIndex+ " resultarray0 : "+resultArray0[0].length());
                                    Log.wtf("-this", "resultArray0[0]  : " +resultArray0[0]);

                                    String temp ="";
//                                    if(remainingIndex < resultArray0[0].length()){
//                                        temp = resultArray0[0].substring(0, remainingIndex);
//                                    } else {
//                                        temp = resultArray0[0];
//                                    }

                                    temp = resultArray0[0];
                                    TextView tv1q = new TextView(getContext());
                                    Log.wtf("-this", "IF Temp : " + 305 + "  " + temp);
                                    tv1q.setText(temp);
                                    tv1q.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                    linearLayouts.get(i).addView(tv1q);
                                    tv1q.setTextColor(getResources().getColor(R.color.cardview_light_background));
                                    tv1q.setTextSize(fontSize);
                                    tv1q.setTypeface(face);

                                    TextView tv0a = new TextView(getContext());

                                    tv0a.setLayoutParams(new ViewGroup.LayoutParams(fillInTheBlanksTextViewOption1Width, fillInTheBlanksTextViewOption1Height));
                                    tv0a.setBackgroundResource(R.drawable.maths_mcq_blank_roundbg);
                                    linearLayouts.get(i).addView(tv0a);
                                    tv0a.setTextColor(getResources().getColor(R.color.cardview_light_background));
                                    tv0a.setTextSize(fontSize-3);
                                    tv0a.setTypeface(face);
                                    tv0a.setTextColor(Color.TRANSPARENT);
                                    tv0a.setGravity(Gravity.CENTER);
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                                        tv0a.setId(View.generateViewId());
                                    }
                                    textViewList.add(new FillInTheBlanksTextViewModel(tv0a, "لکھ"));


                                    Log.wtf("-this", "IF  : " + 313 + "  " + (temp.length() + 15));
                                    String temp13 = resultArray0[1];
                                    int rIndex = lineSize - (temp.length() + 15);
                                    if((temp13.trim().length() > 0 )){
                                        Log.wtf("-this","temp13 : "+ temp13);
                                        String temp11 = temp13;
                                        String temp12 = temp11;
                                        if(rIndex < temp13.length()){
                                            temp11 = temp13.substring(0,rIndex);
                                            temp12 = temp11.substring(0, temp11.lastIndexOf(' '));
                                        }

                                        TextView tv0q = new TextView(getContext());
                                        tv0q.setText(temp12);
                                        tv0q.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        linearLayouts.get(i).addView(tv0q);
                                        tv0q.setTextColor(getResources().getColor(R.color.cardview_light_background));
                                        tv0q.setTextSize(fontSize);
                                        tv0q.setTypeface(face);

                                        this.stringIndex = temp.length() + temp12.length() + this.stringIndex +2;
                                    } else{
                                        this.stringIndex = temp.length() + this.stringIndex + 2;
                                    }

                                    Log.wtf("-this", "STRING INDEX 343 :    " + this.stringIndex);


                                } else {
                                    Log.wtf("-this", "Else  : " + 321);

                                    if(temp0.trim().length() > 0){
                                        TextView tv0q = new TextView(getContext());
                                        String temp1 = temp0.substring(0, temp0.lastIndexOf(' '));
                                        tv0q.setText(temp1);
                                        tv0q.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        linearLayouts.get(i).addView(tv0q);
                                        tv0q.setTextColor(getResources().getColor(R.color.cardview_light_background));
                                        tv0q.setTextSize(fontSize);
                                        tv0q.setTypeface(face);

                                        this.stringIndex = temp1.length() + this.stringIndex;
                                    }

                                }

                            }


                        }

                    } else if (resultArraySize == 3) {
                        Log.wtf("-this", "IF  : " + 324);
                        if (test0.contains("_")) {
                            Log.wtf("-this", "IF  : " + 326);
                            Log.wtf("-this", "resultArray0[0]  : " + resultArray0[0] + " Length : " + resultArray0[0].length());
                            Log.wtf("-this", "resultArray0[0]  : " + resultArray0[0].lastIndexOf(' '));
                            Log.wtf("-this", "resultArray0[1]  : " + resultArray0[1] + " Length : " + resultArray0[1].length());
                            Log.wtf("-this", "resultArray0[1]  : " + resultArray0[1].lastIndexOf(' '));
                            Log.wtf("-this", "resultArray0[2]  : " + resultArray0[2] + " Length : " + resultArray0[2].length());
                            Log.wtf("-this", "resultArray0[2]  : " + resultArray0[2].lastIndexOf(' '));


                            if ((resultArray0[0].length() + resultArray0[1].length() + resultArray0[2].length() + 15) < (lineSize + 1)) {

                                Log.wtf("-this", "IF  : " + 334);
                                TextView tv0q = new TextView(getContext());

                                String temp10 = resultArray0[0];
                                tv0q.setText(temp10);
                                tv0q.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                linearLayouts.get(i).addView(tv0q);
                                tv0q.setTextColor(getResources().getColor(R.color.cardview_light_background));
                                tv0q.setTextSize(fontSize);
                                tv0q.setTypeface(face);


                                // SetAnsHere
                                TextView tv0a = new TextView(getContext());

                                tv0a.setLayoutParams(new ViewGroup.LayoutParams(fillInTheBlanksTextViewOption1Width, fillInTheBlanksTextViewOption1Height));
                                tv0a.setBackgroundResource(R.drawable.maths_mcq_blank_roundbg);
                                linearLayouts.get(i).addView(tv0a);
                                tv0a.setTextColor(getResources().getColor(R.color.cardview_light_background));
                                tv0a.setTextSize(fontSize-3);
                                tv0a.setTypeface(face);
                                tv0a.setTextColor(Color.TRANSPARENT);
                                tv0a.setGravity(Gravity.CENTER);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                                    tv0a.setId(View.generateViewId());
                                }
                                textViewList.add(new FillInTheBlanksTextViewModel(tv0a, "لکھ"));


                                TextView tv1q = new TextView(getContext());

                                String temp = resultArray0[1];
//                                String temp1 = temp.substring(0, temp.lastIndexOf(' '));
                                tv1q.setText(temp);
                                tv1q.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                if (tv1q.getParent() != null) {
                                    ((ViewGroup) tv1q.getParent()).removeView(tv1q); // <- fix
                                }
                                linearLayouts.get(i).addView(tv1q);

                                tv1q.setTextColor(getResources().getColor(R.color.cardview_light_background));
                                tv1q.setTextSize(fontSize);
                                tv1q.setTypeface(face);

                                // SetAnsHere
                                TextView tv0b = new TextView(getContext());

                                tv0b.setLayoutParams(new ViewGroup.LayoutParams(fillInTheBlanksTextViewOption1Width, fillInTheBlanksTextViewOption1Height));
                                tv0b.setBackgroundResource(R.drawable.maths_mcq_blank_roundbg);
                                linearLayouts.get(i).addView(tv0b);
                                tv0b.setTextColor(getResources().getColor(R.color.cardview_light_background));
                                tv0b.setTextSize(fontSize-3);
                                tv0b.setTypeface(face);
                                tv0b.setTextColor(Color.TRANSPARENT);
                                tv0b.setGravity(Gravity.CENTER);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                                    tv0b.setId(View.generateViewId());
                                }
                                textViewList.add(new FillInTheBlanksTextViewModel(tv0b, "لکھ"));

                                TextView tv2q = new TextView(getContext());

                                String temp11 = resultArray0[2];
//                                String temp1 = temp.substring(0, temp.lastIndexOf(' '));
                                tv2q.setText(temp11);
                                tv2q.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                if (tv2q.getParent() != null) {
                                    ((ViewGroup) tv2q.getParent()).removeView(tv2q); // <- fix
                                }
                                linearLayouts.get(i).addView(tv2q);

                                tv2q.setTextColor(getResources().getColor(R.color.cardview_light_background));
                                tv2q.setTextSize(fontSize);
                                tv2q.setTypeface(face);


                                this.stringIndex = temp.length() + temp10.length() + +temp11.length() + this.stringIndex;

                            } else {
                                Log.wtf("-this", "IF  : " + 415);
                                TextView tv0q = new TextView(getContext());

                                String temp10 = resultArray0[0];
                                tv0q.setText(temp10);
                                tv0q.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                linearLayouts.get(i).addView(tv0q);
                                tv0q.setTextColor(getResources().getColor(R.color.cardview_light_background));
                                tv0q.setTextSize(fontSize);
                                tv0q.setTypeface(face);


                                // SetAnsHere
                                TextView tv0a = new TextView(getContext());

                                tv0a.setLayoutParams(new ViewGroup.LayoutParams(fillInTheBlanksTextViewOption1Width, fillInTheBlanksTextViewOption1Height));
                                tv0a.setBackgroundResource(R.drawable.maths_mcq_blank_roundbg);
                                linearLayouts.get(i).addView(tv0a);
                                tv0a.setTextColor(getResources().getColor(R.color.cardview_light_background));
                                tv0a.setTextSize(fontSize-3);
                                tv0a.setTypeface(face);
                                tv0a.setTextColor(Color.TRANSPARENT);
                                tv0a.setGravity(Gravity.CENTER);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                                    tv0a.setId(View.generateViewId());
                                }
                                textViewList.add(new FillInTheBlanksTextViewModel(tv0a, "لکھ"));


                                TextView tv1q = new TextView(getContext());

                                String temp = resultArray0[1];
//                                String temp1 = temp.substring(0, temp.lastIndexOf(' '));
                                tv1q.setText(temp);
                                tv1q.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                if (tv1q.getParent() != null) {
                                    ((ViewGroup) tv1q.getParent()).removeView(tv1q); // <- fix
                                }
                                linearLayouts.get(i).addView(tv1q);

                                tv1q.setTextColor(getResources().getColor(R.color.cardview_light_background));
                                tv1q.setTextSize(fontSize-3);
                                tv1q.setTypeface(face);

                                this.stringIndex = temp.length() + temp10.length() + this.stringIndex;
                            }
                        } else {
                            Log.wtf("-this", "Else  : " + 454);
                            String temp0 = resultArray0[0];
                            int remainingIndex = (lineSize - (resultArray0[0].length() + 15));

                            if (remainingIndex > 3) {
                                Log.wtf("-this", "IF  : " + 459);

                                String temp = resultArray0[0].substring(0, remainingIndex);
                                String temp1 = temp.substring(0, temp.lastIndexOf(' '));
                                TextView tv1q = new TextView(getContext());
                                Log.wtf("-this", "IF  : " + 464 + " temp1 : " + temp1 + " Length : " + temp1.length());
                                Log.wtf("-this", "IF Temp : " + 465 + "  " + temp);
                                Log.wtf("-this", "IF  Temp1 : " + 466 + "  " + temp1);
                                tv1q.setText(temp1);
                                tv1q.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                linearLayouts.get(i).addView(tv1q);
                                tv1q.setTextColor(getResources().getColor(R.color.cardview_light_background));
                                tv1q.setTextSize(fontSize);
                                tv1q.setTypeface(face);

                                TextView tv0a = new TextView(getContext());

                                tv0a.setLayoutParams(new ViewGroup.LayoutParams(fillInTheBlanksTextViewOption1Width, fillInTheBlanksTextViewOption1Height));
                                tv0a.setBackgroundResource(R.drawable.maths_mcq_blank_roundbg);
                                linearLayouts.get(i).addView(tv0a);
                                tv0a.setTextColor(getResources().getColor(R.color.cardview_light_background));
                                tv0a.setTextSize(fontSize-3);
                                tv0a.setTypeface(face);
                                tv0a.setTextColor(Color.TRANSPARENT);
                                tv0a.setGravity(Gravity.CENTER);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                                    tv0a.setId(View.generateViewId());
                                }
                                textViewList.add(new FillInTheBlanksTextViewModel(tv0a, "لکھ"));
                                if ((temp1.length() + 15) > (lineSize)) {
                                    Log.wtf("-this", "IF  : " + 488 + "  " + (temp.length() + 15));
                                    this.stringIndex = temp1.length() + this.stringIndex;
                                } else {
                                    Log.wtf("-this", "IF  : " + 491 + "  " + (temp.length() + 15));
                                    TextView tv0q = new TextView(getContext());
                                    String temp11 = resultArray0[1];
                                    String temp12 = temp11.substring(0, temp11.lastIndexOf(' '));
                                    tv0q.setText(temp12);
                                    tv0q.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                    linearLayouts.get(i).addView(tv0q);
                                    tv0q.setTextColor(getResources().getColor(R.color.cardview_light_background));
                                    tv0q.setTextSize(fontSize);
                                    tv0q.setTypeface(face);

                                    this.stringIndex = temp1.length() + temp12.length() + this.stringIndex;
                                }
                                Log.wtf("-this", "STRING INDEX 343 :    " + this.stringIndex);


                            } else {
                                Log.wtf("-this", "Else  : " + 508);

                                TextView tv0q = new TextView(getContext());
                                String temp1 = temp0.substring(0, temp0.lastIndexOf(' '));
                                tv0q.setText(temp1);
                                tv0q.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                linearLayouts.get(i).addView(tv0q);
                                tv0q.setTextColor(getResources().getColor(R.color.cardview_light_background));
                                tv0q.setTextSize(fontSize);
                                tv0q.setTypeface(face);

                                this.stringIndex = temp1.length() + this.stringIndex;
                            }

                        }

                    } else {
                        Log.wtf("-this", "ELSE  : " + 525);
                        // SetQhere
                        TextView tv0q = new TextView(getContext());

                        String temp11 = resultArray0[0];
                        String temp12 = temp11.substring(0, temp11.lastIndexOf(' '));
                        tv0q.setText(temp12);
                        tv0q.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        linearLayouts.get(i).addView(tv0q);
                        tv0q.setTextColor(getResources().getColor(R.color.cardview_light_background));
                        tv0q.setTextSize(fontSize);
                        tv0q.setTypeface(face);
                        this.stringIndex = temp12.length() + this.stringIndex;
                    }
                } else {
                    Log.wtf("-this", "ELSE  : " + 516);
                    // SetQhere
                    TextView tv0q = new TextView(getContext());

                    tv0q.setText(test0);
                    tv0q.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    linearLayouts.get(i).addView(tv0q);
                    tv0q.setTextColor(getResources().getColor(R.color.cardview_light_background));
                    tv0q.setTextSize(fontSize);
                    tv0q.setTypeface(face);
                    this.stringIndex = test0.length() + this.stringIndex;
                }

            }

        }
        for (int i = 0; i < textViewList.size(); i++) {
            textViewList.get(i).getTextView().setOnDragListener(this);
            textViewList.get(i).getTextView().setText(test.getAnswerList().get(i).getText()+"");
            textViewList.get(i).setAnswer(test.getAnswerList().get(i).getText());

        }


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {


        }

    }


    public void setOnClickListeners(View view) {
        for (FillInTheBlanksTextViewModel md : textViewList) {
            md.getTextView().setOnDragListener(this);
            //  md.getTextView().setOnTouchListener(this);
        }

        fillInTheBlanksFourOptionsTextViewOption1.setOnTouchListener(this);
        fillInTheBlanksFourOptionsTextViewOption2.setOnTouchListener(this);
        fillInTheBlanksFourOptionsTextViewOption3.setOnTouchListener(this);
        fillInTheBlanksFourOptionsTextViewOption4.setOnTouchListener(this);
        optionsList.add(new DragNDropTextViewModel(fillInTheBlanksFourOptionsTextViewOption1, false, true));
        optionsList.add(new DragNDropTextViewModel(fillInTheBlanksFourOptionsTextViewOption2, false, true));
        optionsList.add(new DragNDropTextViewModel(fillInTheBlanksFourOptionsTextViewOption3, false, true));
        optionsList.add(new DragNDropTextViewModel(fillInTheBlanksFourOptionsTextViewOption4, false, true));


    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
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

            if (isDroped == false) {
                if (!(selectedView.getId() == optionsList.get(0).getTextView().getId() || selectedView.getId() == optionsList.get(1).getTextView().getId()
                        || selectedView.getId() == optionsList.get(2).getTextView().getId() || selectedView.getId() == optionsList.get(3).getTextView().getId())) {

                }
            }

            if (selectedView.getId() == optionsList.get(0).getTextView().getId() || selectedView.getId() == optionsList.get(1).getTextView().getId()
                    || selectedView.getId() == optionsList.get(2).getTextView().getId() || selectedView.getId() == optionsList.get(3).getTextView().getId()) {
                for (int i = 0; i < optionsList.size(); i++) {
                    if (optionsList.get(i).getTextView().getId() == selectedView.getId()) {

                    } else {
//                        optionsList.get(i).getTextView().setOnDragListener(null);
//                        optionsList.get(i).setDragListenerActive(false);
                    }
                }
                isDroped = false;
            }


            return true;
        }
        return motionEvent.getAction() == MotionEvent.ACTION_UP;
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        int action = event.getAction();
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                // do nothing
                // Log.wtf("-drag", "Started " + v.toString());
                // return true;
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                //    v.setBackgroundDrawable(enterShape);
                //  Log.wtf("-drag", "Entered " + v.toString());
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                // v.setBackgroundDrawable(normalShape);
                // Log.wtf("-drag", "Exited " + v.toString());
                // textViewAnswer1.setVisibility(View.VISIBLE);
                break;
            case DragEvent.ACTION_DROP:
                TextView txtView = (TextView) v;
                if (selectedView.getId() == fillInTheBlanksFourOptionsTextViewOption1.getId() || selectedView.getId() == fillInTheBlanksFourOptionsTextViewOption2.getId()
                        || selectedView.getId() == fillInTheBlanksFourOptionsTextViewOption3.getId() || selectedView.getId() == fillInTheBlanksFourOptionsTextViewOption4.getId()) {

                    if (txtView.getId() == fillInTheBlanksFourOptionsTextViewOption1.getId() || txtView.getId() == fillInTheBlanksFourOptionsTextViewOption2.getId()
                            || txtView.getId() == fillInTheBlanksFourOptionsTextViewOption3.getId() || txtView.getId() == fillInTheBlanksFourOptionsTextViewOption4.getId()) {
                        Log.wtf("-drag", " Test Inc: " + answerCount);

                        break;

                    } else {

                        if (textViewList.size() == 1) {
                            Log.wtf("-drag", " IDs: " + textViewList.get(0).getTextView().getId() + "  " + selectedView.getId());
                            textViewList.get(0).getTextView().setOnDragListener(null);
                            textViewList.get(0).getTextView().setOnTouchListener(this);
                            Log.wtf("-drag", " In FOR IF ");
                            answerCount++;
                            if (selectedView.getId() == fillInTheBlanksFourOptionsTextViewOption1.getId()) {
                                String uidAudio = topic + "Question" + (tempIndex+1) + "Option" + 1 + "Audio.mp3";
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
                            } else if (selectedView.getId() == fillInTheBlanksFourOptionsTextViewOption2.getId()) {
                                String uidAudio = topic + "Question" + (tempIndex+1) + "Option" + 2 + "Audio.mp3";
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
                            }else if (selectedView.getId() == fillInTheBlanksFourOptionsTextViewOption3.getId()) {
                                String uidAudio = topic + "Question" + (tempIndex+1) + "Option" + 3 + "Audio.mp3";
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
                            }else if (selectedView.getId() == fillInTheBlanksFourOptionsTextViewOption4.getId()) {
                                String uidAudio = topic + "Question" + (tempIndex+1) + "Option" + 4 + "Audio.mp3";
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
                        } else if (textViewList.size() == 2) {
                            {
                                if (textViewList.get(0).getTextView().getId() == txtView.getId()) {
                                    Log.wtf("-drag", " IDs: " + textViewList.get(0).getTextView().getId() + "  " + selectedView.getId());

                                    textViewList.get(0).getTextView().setOnDragListener(null);
                                    textViewList.get(0).getTextView().setOnTouchListener(this);

                                    Log.wtf("-drag", " In FOR IF ");
                                    answerCount++;
                                    if (selectedView.getId() == fillInTheBlanksFourOptionsTextViewOption1.getId()) {
                                        String uidAudio = topic + "Question" + (tempIndex+1) + "Option" + 1 + "Audio.mp3";
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
                                    } else if (selectedView.getId() == fillInTheBlanksFourOptionsTextViewOption2.getId()) {
                                        String uidAudio = topic + "Question" + (tempIndex+1) + "Option" + 2 + "Audio.mp3";
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
                                    }else if (selectedView.getId() == fillInTheBlanksFourOptionsTextViewOption3.getId()) {
                                        String uidAudio = topic + "Question" + (tempIndex+1) + "Option" + 3 + "Audio.mp3";
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
                                    }else if (selectedView.getId() == fillInTheBlanksFourOptionsTextViewOption4.getId()) {
                                        String uidAudio = topic + "Question" + (tempIndex+1) + "Option" + 4 + "Audio.mp3";
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
                                } else {
                                    Log.wtf("-drag", " IDs: " + textViewList.get(1).getTextView().getId() + "  " + selectedView.getId());

                                    textViewList.get(1).getTextView().setOnDragListener(null);
                                    textViewList.get(1).getTextView().setOnTouchListener(this);

                                    Log.wtf("-drag", " In FOR IF ");
                                    answerCount++;
                                    if (selectedView.getId() == fillInTheBlanksFourOptionsTextViewOption1.getId()) {
                                        String uidAudio = topic + "Question" + (tempIndex+1) + "Option" + 1 + "Audio.mp3";
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
                                    } else if (selectedView.getId() == fillInTheBlanksFourOptionsTextViewOption2.getId()) {
                                        String uidAudio = topic + "Question" + (tempIndex+1) + "Option" + 2 + "Audio.mp3";
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
                                    }else if (selectedView.getId() == fillInTheBlanksFourOptionsTextViewOption3.getId()) {
                                        String uidAudio = topic + "Question" + (tempIndex+1) + "Option" + 3 + "Audio.mp3";
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
                                    }else if (selectedView.getId() == fillInTheBlanksFourOptionsTextViewOption4.getId()) {
                                        String uidAudio = topic + "Question" + (tempIndex+1) + "Option" + 4 + "Audio.mp3";
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
                                }

                            }

                        }

                        selectedView.setOnDragListener(this);
                        selectedView.setOnTouchListener(null);
                        //  Toast.makeText(getContext(), " ButtonOption Inc: " + answerCount, Toast.LENGTH_SHORT).show();
                        Log.wtf("-drag", " ButtonOption Inc: " + answerCount);

                    }


                } else if (txtView.getId() == fillInTheBlanksFourOptionsTextViewOption1.getId() || txtView.getId() == fillInTheBlanksFourOptionsTextViewOption2.getId()
                        || txtView.getId() == fillInTheBlanksFourOptionsTextViewOption3.getId() || txtView.getId() == fillInTheBlanksFourOptionsTextViewOption4.getId()) {

                    txtView.setOnDragListener(null);
                    txtView.setOnTouchListener(this);
                    answerCount--;
                    Log.wtf("-drag", " textView Dec : " + answerCount);
                    selectedView.setOnTouchListener(null);
                    selectedView.setOnDragListener(this);
                    // Toast.makeText(getContext(), " textView Dec : " + answerCount, Toast.LENGTH_SHORT).show();

                } else {
                    Log.wtf("-drag", " textView Nothing : ");

                    selectedView.setOnTouchListener(null);
                    selectedView.setOnDragListener(this);
                    txtView.setOnDragListener(null);
                    txtView.setOnTouchListener(this);

                }

                selectedView.setBackgroundResource(R.drawable.maths_mcq_blank_roundbg);
                selectedView.setTextColor(Color.TRANSPARENT);
//                txtView.setOnTouchListener(this);
//                txtView.setOnDragListener(null);
                txtView.setText(selectedData);
                txtView.setTextColor(getResources().getColor(R.color.cardview_light_background));
                setBackGroundSingle(sharedPrefs.getString("SupervisedSubjectSelected", "English"), txtView);


                //stillInDemoState=false;

                if (answerCount == textViewList.size()) {
                    compileAns();
                }
                isDroped = true;
                // Toast.makeText(getContext(), " ButtonOption : " + selectedData, Toast.LENGTH_SHORT).show();

                break;
            case DragEvent.ACTION_DRAG_ENDED:
                //  Log.wtf("-drag", "Ended " + v.toString());
                // textViewAnswer1.setVisibility(View.VISIBLE);
                break;
            case DragEvent.ACTION_DRAG_LOCATION:

                break;
            default:
                break;
        }
        return true;
    }

    public void compileAns() {
        start = false;
        T.cancel();
        int count = 0;
        int score = 0;
        int tScore = 0;
        if (textViewList.size() == 1) {
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
        } else {
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
        }


        for (int i = 0; i < textViewList.size(); i++) {
            if (textViewList.get(i).getTextView().getText().toString().equalsIgnoreCase(textViewList.get(i).getAnswer())) {
                if(unSocratic == true) {
                    appAnalytics.setOptionPlaced(selectedSubject, topic, test.getType(), textViewList.get(i).getAnswer(), true);
                }

                textViewList.get(i).getTextView().setBackgroundResource(R.drawable.ordinary_mcq_option_bg_green);
                count++;
                score = score + tScore;

            } else {
                if(unSocratic == true) {
                    appAnalytics.setOptionPlaced(selectedSubject, topic, test.getType(), textViewList.get(i).getAnswer(), false);
                }

                textViewList.get(i).getTextView().setBackgroundResource(R.drawable.ordinary_mcq_option_bg_red);
            }
            Log.wtf("-this", "CompileAns : TextViewString : " + textViewList.get(i).getTextView().getText().toString() +
                    " ANS : " + textViewList.get(i).getAnswer());
        }

        if (textViewList.size() == 1) {
            if(score > 0){
                parrotFire();
                correctCount++;
            } else{
                alienFire();
            }
        }else{
            if(score < ((tScore * 2)/2)){
                alienFire();
            } else{
                parrotFire();
                correctCount++;

            }
        }
        if(count == textViewList.size()){
            if(unSocratic == true) {
                appAnalytics.setAnswer(selectedSubject, topic + "" + testIndex, topic, test.getType(), test.getAnswerList().size(), count, true, this.count);
            }
        } else{
            if(unSocratic == true) {
                appAnalytics.setAnswer(selectedSubject, topic + "" + testIndex, topic, test.getType(), test.getAnswerList().size(), count, false, this.count);
            }
        }


        totalScore = totalScore + score;
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
                            .remove(FillInTheBlankFourOptionsEnglish.this).commit();
                }

            }
        }, 1000);


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


    public void setViews(){
        fillInTheBlanksFourOptionsTextViewOption1.setVisibility(View.INVISIBLE);
        fillInTheBlanksFourOptionsTextViewOption2.setVisibility(View.INVISIBLE);
        fillInTheBlanksFourOptionsTextViewOption3.setVisibility(View.INVISIBLE);
        fillInTheBlanksFourOptionsTextViewOption4.setVisibility(View.INVISIBLE);

        if(selectedSubject.equalsIgnoreCase("English") || selectedSubject.equalsIgnoreCase("Maths") || selectedSubject.equalsIgnoreCase("Science")
                || selectedSubject.equalsIgnoreCase("Geography")){

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    jab();
                    fillInTheBlanksFourOptionsTextViewOption1.setVisibility(View.VISIBLE);

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            jab();
                            fillInTheBlanksFourOptionsTextViewOption2.setVisibility(View.VISIBLE);
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    jab();
                                    fillInTheBlanksFourOptionsTextViewOption3.setVisibility(View.VISIBLE);
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            jab();
                                            fillInTheBlanksFourOptionsTextViewOption4.setVisibility(View.VISIBLE);

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
                    fillInTheBlanksFourOptionsTextViewOption4.setVisibility(View.VISIBLE);

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            jab();
                            fillInTheBlanksFourOptionsTextViewOption3.setVisibility(View.VISIBLE);
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    jab();
                                    fillInTheBlanksFourOptionsTextViewOption2.setVisibility(View.VISIBLE);
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            jab();
                                            fillInTheBlanksFourOptionsTextViewOption1.setVisibility(View.VISIBLE);

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

    public void parrotPlus(){

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
    public void alienPlus(){

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
                        if(start == true){
                            count++;
                        } else{
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
