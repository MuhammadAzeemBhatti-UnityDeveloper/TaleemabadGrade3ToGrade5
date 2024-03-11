package com.orenda.taimo.grade3tograde5.Tests;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.orenda.taimo.grade3tograde5.Models.MathsAdditionModel;
import com.orenda.taimo.grade3tograde5.Models.TestJsonParseModel;
import com.orenda.taimo.grade3tograde5.R;
import com.orenda.taimo.grade3tograde5.SimpleTestActivity;
import com.orenda.taimo.grade3tograde5.SocraticActivity;

import firebase.analytics.AppAnalytics;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.correctCount;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.selectedSubject;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.testIndex;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.topic;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.totalScore;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.unSocratic;


@SuppressLint("ValidFragment")
public class MathsAddition extends Fragment implements View.OnClickListener {

    MediaPlayer mediaPlayerTouch;
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;


    private TextView mathsAdditionTextView1, mathsAdditionTextView2, mathsAdditionTextView3, mathsAdditionTextView4, mathsAdditionTextView5, mathsAdditionTextView6, mathsAdditionTextView7, mathsAdditionTextView8, mathsAdditionTextView9, mathsAdditionTextView0;

    private TextView displayText, mathsAdditionCarryTextViewT, mathsAdditionCarryTextViewH, mathsAdditionCarryTextViewTH;

    private ImageView mathsAdditionImageViewDel, mathsAdditionImageViewTick;

    private TextView mathsAdditionTextViewHTH, mathsAdditionTextViewTH, mathsAdditionTextViewH, mathsAdditionTextViewT, mathsAdditionTextViewU;
    private TextView mathsAdditionTextViewHTH1, mathsAdditionTextViewTH1, mathsAdditionTextViewH1, mathsAdditionTextViewT1, mathsAdditionTextViewU1;
    private TextView mathsAdditionTextViewOperator, mathsAdditionTextViewTH2, mathsAdditionTextViewH2, mathsAdditionTextViewT2, mathsAdditionTextViewU2;
    private TextView mathsAdditionTextViewHTH3, mathsAdditionTextViewTH3, mathsAdditionTextViewH3, mathsAdditionTextViewT3, mathsAdditionTextViewU3;

    private ConstraintLayout explanationMcqPopUpLayout;
    private TextView explanationTextViewInPopUp;
    private ImageView explanationTextViewPopUpClose;


    ArrayList<MathsAdditionModel> list = new ArrayList<>();
    ArrayList<TextView> answerTextViews = new ArrayList<>();

    String finalAnswer;

    String selectedData;
    TextView selectedView;
    int answerCount = 0;


    String ans = "";
    int unit1, unit2, ten1, ten2, hun1, hun2, thou1, thou2;
    int tcarry = 0, hcarry = 0, thcarry = 0;
    Integer answer;

    Boolean U3 = false, T3 = false, H3 = false, TH3 = false;
    Boolean one = false, two = false, three = false;
    int realWidth, realHeight;

    int testId = -1;
    TestJsonParseModel test = null;
    Context mContext;
    Activity activity;


    AppAnalytics appAnalytics;
    boolean start = true;
    Timer T = new Timer();
    int count = 0;


    /*
    int DEMO_MODE = 0;
    ImageView handImageview;
    ConstraintLayout mainConstraintLayout;
    ConstraintLayout parentMainlayout_id;
    int dragBactToX, dragBactToY;
    int whichTextviewWillDrag;
    Boolean ordinaryMCQ;
    MediaPlayer mp;
    boolean demoStarted = false;
    String checkLang;
    boolean buttonPressedOnTV=false;
    boolean buttonPressedOnTick=false;

     */


    public MathsAddition(int testId, TestJsonParseModel test, Context context, Activity activity) {
        this.testId = testId;
        this.test = test;
        mContext = context;
        this.activity = activity;
        Log.wtf("-this", " TEST ID : " + testId);
        Log.wtf("-this", " TEST ID : " + test.getQuestion().getText());
        Log.wtf("-this", " TEST ID : " + test.getAnswerList().get(0).getText());
        Log.wtf("-this", " TEST ID : " + test.getOptionList().get(0).getText());
        Log.wtf("-this", " TEST ID : " + test.getOptionList().get(1).getText());


    }

    public MathsAddition() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.maths_addition, container, false);

        sharedPrefs = getActivity().getApplicationContext().getSharedPreferences("DefaultSPForTaleemabadGrade6", 0);
        editor = sharedPrefs.edit();
        appAnalytics = new AppAnalytics(getContext());
        initializeView(view);
        setupData();
        setOnClickListeners(view);


        //checkDemo();
        return view;
    }

    public void initializeView(View view) {

        mathsAdditionTextViewHTH = view.findViewById(R.id.mathsAdditionTextViewHTH);
        mathsAdditionTextViewHTH1 = view.findViewById(R.id.mathsAdditionTextViewHTH1);
        mathsAdditionTextViewHTH3 = view.findViewById(R.id.mathsAdditionTextViewHTH3);

        mathsAdditionTextViewOperator = view.findViewById(R.id.mathsAdditionTextViewOperator);

        mathsAdditionTextViewTH = view.findViewById(R.id.mathsAdditionTextViewTH);
        mathsAdditionTextViewTH1 = view.findViewById(R.id.mathsAdditionTextViewTH1);
        mathsAdditionTextViewTH2 = view.findViewById(R.id.mathsAdditionTextViewTH2);

        mathsAdditionTextViewH = view.findViewById(R.id.mathsAdditionTextViewH);
        mathsAdditionTextViewH1 = view.findViewById(R.id.mathsAdditionTextViewH1);
        mathsAdditionTextViewH2 = view.findViewById(R.id.mathsAdditionTextViewH2);

        mathsAdditionTextViewT = view.findViewById(R.id.mathsAdditionTextViewT);
        mathsAdditionTextViewT1 = view.findViewById(R.id.mathsAdditionTextViewT1);
        mathsAdditionTextViewT2 = view.findViewById(R.id.mathsAdditionTextViewT2);

        mathsAdditionTextViewU = view.findViewById(R.id.mathsAdditionTextViewU);
        mathsAdditionTextViewU1 = view.findViewById(R.id.mathsAdditionTextViewU1);
        mathsAdditionTextViewU2 = view.findViewById(R.id.mathsAdditionTextViewU2);


        // answer textviews
        mathsAdditionTextViewTH3 = view.findViewById(R.id.mathsAdditionTextViewTH3);
        mathsAdditionTextViewH3 = view.findViewById(R.id.mathsAdditionTextViewH3);
        mathsAdditionTextViewT3 = view.findViewById(R.id.mathsAdditionTextViewT3);
        mathsAdditionTextViewU3 = view.findViewById(R.id.mathsAdditionTextViewU3);
        displayText = view.findViewById(R.id.showText);

        //carry
        mathsAdditionCarryTextViewT = view.findViewById(R.id.mathsAdditionCarryTextViewT);
        mathsAdditionCarryTextViewH = view.findViewById(R.id.mathsAdditionCarryTextViewH);
        mathsAdditionCarryTextViewTH = view.findViewById(R.id.mathsAdditionCarryTextViewTH);

        //explanation
        explanationMcqPopUpLayout = view.findViewById(R.id.explanationPopUpLayout);
        explanationTextViewPopUpClose = view.findViewById(R.id.explanationTextViewPopUpClose);
        explanationTextViewInPopUp = view.findViewById(R.id.explanationTextViewInPopUp);


        mathsAdditionTextView1 = view.findViewById(R.id.mathsAdditionTextView1);
        mathsAdditionTextView2 = view.findViewById(R.id.mathsAdditionTextView2);
        mathsAdditionTextView3 = view.findViewById(R.id.mathsAdditionTextView3);
        mathsAdditionTextView4 = view.findViewById(R.id.mathsAdditionTextView4);
        mathsAdditionTextView5 = view.findViewById(R.id.mathsAdditionTextView5);
        mathsAdditionTextView6 = view.findViewById(R.id.mathsAdditionTextView6);
        mathsAdditionTextView7 = view.findViewById(R.id.mathsAdditionTextView7);
        mathsAdditionTextView8 = view.findViewById(R.id.mathsAdditionTextView8);
        mathsAdditionTextView9 = view.findViewById(R.id.mathsAdditionTextView9);
        mathsAdditionTextView0 = view.findViewById(R.id.mathsAdditionTextView0);

        mathsAdditionImageViewDel = view.findViewById(R.id.mathsAdditionImageViewDel);
        mathsAdditionImageViewTick = view.findViewById(R.id.mathsAdditionImageViewTick);

//        mainConstraintLayout = view.findViewById(R.id.mainConstraintLayout_id);


        ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    mathsAdditionTextView1.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    int height = mathsAdditionTextView1.getHeight();
                    //  int height = mathsAdditionTextView1.getWidth();
                    int width = mathsAdditionTextView1.getWidth();
                    ViewGroup.LayoutParams params = mathsAdditionTextView1.getLayoutParams();
                    params.width = width;
                    params.height = width;
                    mathsAdditionTextView1.setLayoutParams(params);

                    params = mathsAdditionTextView2.getLayoutParams();
                    params.width = width;
                    params.height = width;
                    mathsAdditionTextView2.setLayoutParams(params);

                    params = mathsAdditionTextView3.getLayoutParams();
                    params.width = width;
                    params.height = width;
                    mathsAdditionTextView3.setLayoutParams(params);

                    params = mathsAdditionTextView4.getLayoutParams();
                    params.width = width;
                    params.height = width;
                    mathsAdditionTextView4.setLayoutParams(params);

                    params = mathsAdditionTextView5.getLayoutParams();
                    params.width = width;
                    params.height = width;
                    mathsAdditionTextView5.setLayoutParams(params);

                    params = mathsAdditionTextView6.getLayoutParams();
                    params.width = width;
                    params.height = width;
                    mathsAdditionTextView6.setLayoutParams(params);

                    params = mathsAdditionTextView7.getLayoutParams();
                    params.width = width;
                    params.height = width;
                    mathsAdditionTextView7.setLayoutParams(params);

                    params = mathsAdditionTextView8.getLayoutParams();
                    params.width = width;
                    params.height = width;
                    mathsAdditionTextView8.setLayoutParams(params);

                    params = mathsAdditionTextView9.getLayoutParams();
                    params.width = width;
                    params.height = width;
                    mathsAdditionTextView9.setLayoutParams(params);

                    params = mathsAdditionTextView0.getLayoutParams();
                    params.width = width;
                    params.height = width;
                    mathsAdditionTextView0.setLayoutParams(params);

                    params = mathsAdditionImageViewTick.getLayoutParams();
                    params.width = width;
                    params.height = width;
                    mathsAdditionImageViewTick.setLayoutParams(params);

                    params = mathsAdditionImageViewDel.getLayoutParams();
                    params.width = width;
                    params.height = width;
                    mathsAdditionImageViewDel.setLayoutParams(params);


                }
            });
        }


    }


    /*
    void checkDemo() {
        //To retrieve
        SharedPreferences sharedPrefForChecking = getActivity().getSharedPreferences("DemoMathsAddition", DEMO_MODE);
        ordinaryMCQ = sharedPrefForChecking.getBoolean("MathsAddition", false); //0 is the default value

        Log.w("data_matth", "final answer  :" + finalAnswer);


        setUpDemo();

    }

     */
/*
    void setUpDemo() {
        if (!ordinaryMCQ) {
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
                SocraticActivity.testActivityImageViewDaimond.setVisibility(View.GONE);
            }

            //  TestSimpleTestActivityActivity.testActivityImageViewfeedback.setVisibility(View.GONE);

            final Handler handlerr = new Handler();
            handlerr.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    jab();

                    startDemo();
                }
            }, 100);

        } else {
            SimpleTestActivity.testActivityImageViewHome.setVisibility(View.VISIBLE);
            SimpleTestActivity.testActivityImageViewDaimond.setVisibility(View.VISIBLE);
         //   SimpleTestActivity.testActivityImageViewfeedback.setVisibility(View.VISIBLE);
            demoStarted = false;
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
        mp.start();


        demoStarted = true;

        Toast.makeText(getContext(), "final: " + finalAnswer.substring(finalAnswer.length() - 1), Toast.LENGTH_SHORT).show();

        String ansInString = finalAnswer.substring(finalAnswer.length() - 1);
        int ansWill = Integer.parseInt(ansInString);

        final Handler handlerr = new Handler();
        final ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(mainConstraintLayout);
        handImageview = new ImageView(activity);
        handImageview.setImageResource(R.drawable.hand);
        handImageview.setId(ViewCompat.generateViewId());
        handImageview.requestLayout();



        if (ansWill == 0) {
            handlerr.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    jab();

                    mainConstraintLayout.addView(handImageview);
                    constraintSet.connect(handImageview.getId(),ConstraintSet.TOP, R.id.guidelineButtonDelTop,ConstraintSet.TOP,20);
                    //       constraintSet.connect(imageview.getId(),ConstraintSet.END,R.id.guidelineButton2Right,ConstraintSet.START,80);
                    constraintSet.connect(handImageview.getId(),ConstraintSet.START, R.id.guidelineButton2Left,ConstraintSet.END,40);
                    constraintSet.connect(handImageview.getId(),ConstraintSet.BOTTOM, R.id.guidelineButtonDelBottom,ConstraintSet.TOP,20);
                    constraintSet.applyTo(mainConstraintLayout);

                    handImageview.getLayoutParams().width = mathsAdditionTextView0.getMeasuredHeight()/2;
                    handImageview.getLayoutParams().height = mathsAdditionTextView0.getMeasuredHeight()/2;
                    handImageview.bringToFront();

                    int[] location = new int[2];
                    mathsAdditionTextView0.getLocationOnScreen(location);
                    dragBactToX = location[0];
                    dragBactToY = location[1];

                    //            tap_icon1.setVisibility(View.VISIBLE);
                    animateHandUpp(0, handImageview);

                }
            }, 1200);
        }
        else if (ansWill == 1) {
            handlerr.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    jab();

                    mainConstraintLayout.addView(handImageview);
                    constraintSet.connect(handImageview.getId(),ConstraintSet.TOP, R.id.guidelineButton1Top, ConstraintSet.TOP,20);
                    //       constraintSet.connect(imageview.getId(),ConstraintSet.END,R.id.guidelineButton1Right,ConstraintSet.START,80);
                    constraintSet.connect(handImageview.getId(),ConstraintSet.START, R.id.guidelineButton1Left,ConstraintSet.END,40);
                    constraintSet.connect(handImageview.getId(),ConstraintSet.BOTTOM, R.id.guidelineButton1Bottom,ConstraintSet.TOP,20);
                    constraintSet.applyTo(mainConstraintLayout);


                    handImageview.getLayoutParams().width = mathsAdditionTextView0.getMeasuredHeight()/2;
                    handImageview.getLayoutParams().height = mathsAdditionTextView0.getMeasuredHeight()/2;
                    handImageview.bringToFront();

                    int[] location = new int[2];
                    mathsAdditionTextView1.getLocationOnScreen(location);
                    dragBactToX = location[0];
                    dragBactToY = location[1];

                    //            tap_icon1.setVisibility(View.VISIBLE);
                    animateHandUpp(1, handImageview);

                }
            }, 1200);

        }
        else if (ansWill == 2) {
            handlerr.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    jab();

                    mainConstraintLayout.addView(handImageview);

                    constraintSet.connect(handImageview.getId(),ConstraintSet.TOP, R.id.guidelineButton1Top,ConstraintSet.TOP,20);
                    //       constraintSet.connect(imageview.getId(),ConstraintSet.END,R.id.guidelineButton2Right,ConstraintSet.START,80);
                    constraintSet.connect(handImageview.getId(),ConstraintSet.START, R.id.guidelineButton2Left,ConstraintSet.END,40);
                    constraintSet.connect(handImageview.getId(),ConstraintSet.BOTTOM, R.id.guidelineButton1Bottom,ConstraintSet.TOP,20);
                    constraintSet.applyTo(mainConstraintLayout);

                    handImageview.getLayoutParams().width = mathsAdditionTextView0.getMeasuredHeight()/2;
                    handImageview.getLayoutParams().height = mathsAdditionTextView0.getMeasuredHeight()/2;
                    handImageview.bringToFront();

                    int[] location = new int[2];
                    mathsAdditionTextView2.getLocationOnScreen(location);
                    dragBactToX = location[0];
                    dragBactToY = location[1];

                    //            tap_icon1.setVisibility(View.VISIBLE);
                    animateHandUpp(2, handImageview);

                }
            }, 1200);

        }
        else if (ansWill == 3) {
            handlerr.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    jab();


                    int[] location = new int[2];
                    mathsAdditionTextView3.getLocationOnScreen(location);
                    dragBactToX = location[0];
                    dragBactToY = location[1];
                    dragBactToX =dragBactToX+10;
                    dragBactToY =dragBactToY+10;

                    mainConstraintLayout.addView(handImageview);

                    constraintSet.connect(handImageview.getId(),ConstraintSet.TOP, R.id.guidelineButton1Top,ConstraintSet.TOP,20);
                    constraintSet.connect(handImageview.getId(),ConstraintSet.END,R.id.guidelineButton3Right,ConstraintSet.START,80);
                    constraintSet.connect(handImageview.getId(),ConstraintSet.START, R.id.guidelineButton3Left,ConstraintSet.END,40);
                    constraintSet.connect(handImageview.getId(),ConstraintSet.BOTTOM, R.id.guidelineButton1Bottom,ConstraintSet.TOP,20);
                    constraintSet.applyTo(mainConstraintLayout);

                    handImageview.getLayoutParams().width = mathsAdditionTextView0.getMeasuredHeight()/2;
                    handImageview.getLayoutParams().height = mathsAdditionTextView0.getMeasuredHeight()/2;
                    handImageview.bringToFront();

                    //            tap_icon1.setVisibility(View.VISIBLE);
                    animateHandUpp(3, handImageview);

                }
            }, 1200);

        }
        else if (ansWill == 4) {
            handlerr.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    jab();

                    mainConstraintLayout.addView(handImageview);
                    constraintSet.connect(handImageview.getId(),ConstraintSet.TOP, R.id.guidelineButton4Top,ConstraintSet.TOP,20);
                    //       constraintSet.connect(imageview.getId(),ConstraintSet.END,R.id.guidelineButton1Right,ConstraintSet.START,80);
                    constraintSet.connect(handImageview.getId(),ConstraintSet.START, R.id.guidelineButton1Left,ConstraintSet.END,40);
                    constraintSet.connect(handImageview.getId(),ConstraintSet.BOTTOM, R.id.guidelineButton4Bottom,ConstraintSet.TOP,20);
                    constraintSet.applyTo(mainConstraintLayout);

                    handImageview.getLayoutParams().width = mathsAdditionTextView0.getMeasuredHeight()/2;
                    handImageview.getLayoutParams().height = mathsAdditionTextView0.getMeasuredHeight()/2;
                    handImageview.bringToFront();


                    int[] location = new int[2];
                    mathsAdditionTextView4.getLocationOnScreen(location);
                    dragBactToX = location[0];
                    dragBactToY = location[1];

                    //            tap_icon1.setVisibility(View.VISIBLE);
                    animateHandUpp(4, handImageview);

                }
            }, 1200);

        }
        else if (ansWill == 5) {
            handlerr.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    jab();

                    mainConstraintLayout.addView(handImageview);
                    constraintSet.connect(handImageview.getId(),ConstraintSet.TOP, R.id.guidelineButton4Top,ConstraintSet.TOP,20);
                    //       constraintSet.connect(imageview.getId(),ConstraintSet.END,R.id.guidelineButton2Right,ConstraintSet.START,80);
                    constraintSet.connect(handImageview.getId(),ConstraintSet.START, R.id.guidelineButton2Left,ConstraintSet.END,40);
                    constraintSet.connect(handImageview.getId(),ConstraintSet.BOTTOM, R.id.guidelineButton4Bottom,ConstraintSet.TOP,20);
                    constraintSet.applyTo(mainConstraintLayout);


                    int[] location = new int[2];
                    mathsAdditionTextView5.getLocationOnScreen(location);
                    dragBactToX = location[0];
                    dragBactToY = location[1];

                    handImageview.getLayoutParams().width = mathsAdditionTextView0.getMeasuredHeight()/2;
                    handImageview.getLayoutParams().height = mathsAdditionTextView0.getMeasuredHeight()/2;
                    handImageview.bringToFront();

                    //            tap_icon1.setVisibility(View.VISIBLE);
                    animateHandUpp(5, handImageview);

                }
            }, 1200);

        }
        else if (ansWill == 6) {
            handlerr.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    jab();

                    mainConstraintLayout.addView(handImageview);
                    constraintSet.connect(handImageview.getId(),ConstraintSet.TOP, R.id.guidelineButton4Top,ConstraintSet.TOP,20);
                    //       constraintSet.connect(imageview.getId(),ConstraintSet.END,R.id.guidelineButton3Right,ConstraintSet.START,80);
                    constraintSet.connect(handImageview.getId(),ConstraintSet.START, R.id.guidelineButton3Left,ConstraintSet.END,40);
                    constraintSet.connect(handImageview.getId(),ConstraintSet.BOTTOM, R.id.guidelineButton4Bottom,ConstraintSet.TOP,20);
                    constraintSet.applyTo(mainConstraintLayout);


                    int[] location = new int[2];
                    mathsAdditionTextView6.getLocationOnScreen(location);
                    dragBactToX = location[0];
                    dragBactToY = location[1];

                    handImageview.getLayoutParams().width = mathsAdditionTextView0.getMeasuredHeight()/2;
                    handImageview.getLayoutParams().height = mathsAdditionTextView0.getMeasuredHeight()/2;
                    handImageview.bringToFront();

                    //            tap_icon1.setVisibility(View.VISIBLE);
                    animateHandUpp(6, handImageview);

                }
            }, 1200);

        }
        else if (ansWill == 7) {
            handlerr.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    jab();

                    mainConstraintLayout.addView(handImageview);
                    constraintSet.connect(handImageview.getId(),ConstraintSet.TOP, R.id.guidelineButton7Top,ConstraintSet.TOP,20);
                    //       constraintSet.connect(imageview.getId(),ConstraintSet.END,R.id.guidelineButton1Right,ConstraintSet.START,80);
                    constraintSet.connect(handImageview.getId(),ConstraintSet.START, R.id.guidelineButton1Left,ConstraintSet.END,40);
                    constraintSet.connect(handImageview.getId(),ConstraintSet.BOTTOM, R.id.guidelineButton7Bottom,ConstraintSet.TOP,20);
                    constraintSet.applyTo(mainConstraintLayout);


                    int[] location = new int[2];
                    mathsAdditionTextView7.getLocationOnScreen(location);
                    dragBactToX = location[0];
                    dragBactToY = location[1];

                    handImageview.getLayoutParams().width = mathsAdditionTextView0.getMeasuredHeight()/2;
                    handImageview.getLayoutParams().height = mathsAdditionTextView0.getMeasuredHeight()/2;
                    handImageview.bringToFront();

                    //            tap_icon1.setVisibility(View.VISIBLE);
                    animateHandUpp(7, handImageview);

                }
            }, 1200);

        }
        else if (ansWill == 8) {
            handlerr.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    jab();

                    mainConstraintLayout.addView(handImageview);
                    constraintSet.connect(handImageview.getId(),ConstraintSet.TOP, R.id.guidelineButton7Top,ConstraintSet.TOP,20);
                    //       constraintSet.connect(imageview.getId(),ConstraintSet.END,R.id.guidelineButton2Right,ConstraintSet.START,80);
                    constraintSet.connect(handImageview.getId(),ConstraintSet.START, R.id.guidelineButton2Left,ConstraintSet.END,40);
                    constraintSet.connect(handImageview.getId(),ConstraintSet.BOTTOM, R.id.guidelineButton7Bottom,ConstraintSet.TOP,20);
                    constraintSet.applyTo(mainConstraintLayout);

                    handImageview.getLayoutParams().width = mathsAdditionTextView0.getMeasuredHeight()/2;
                    handImageview.getLayoutParams().height = mathsAdditionTextView0.getMeasuredHeight()/2;
                    handImageview.bringToFront();



                    int[] location = new int[2];
                    mathsAdditionTextView8.getLocationOnScreen(location);
                    dragBactToX = location[0];
                    dragBactToY = location[1];


                    //            tap_icon1.setVisibility(View.VISIBLE);
                    animateHandUpp(8, handImageview);

                }
            }, 1200);

        }
        else if (ansWill == 9) {
            handlerr.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    jab();

                    mainConstraintLayout.addView(handImageview);

                    constraintSet.connect(handImageview.getId(),ConstraintSet.TOP, R.id.guidelineButton7Top,ConstraintSet.TOP,20);
                    //       constraintSet.connect(imageview.getId(),ConstraintSet.END,R.id.guidelineButton3Right,ConstraintSet.START,80);
                    constraintSet.connect(handImageview.getId(),ConstraintSet.START, R.id.guidelineButton3Left,ConstraintSet.END,40);
                    constraintSet.connect(handImageview.getId(),ConstraintSet.BOTTOM, R.id.guidelineButton7Bottom,ConstraintSet.TOP,20);
                    constraintSet.applyTo(mainConstraintLayout);

                    handImageview.getLayoutParams().width = mathsAdditionTextView0.getMeasuredHeight()/2;
                    handImageview.getLayoutParams().height = mathsAdditionTextView0.getMeasuredHeight()/2;
                    handImageview.bringToFront();


                    int[] location = new int[2];
                    mathsAdditionTextView9.getLocationOnScreen(location);
                    dragBactToX = location[0];
                    dragBactToY = location[1];

                    //            tap_icon1.setVisibility(View.VISIBLE);
                    animateHandUpp(9, handImageview);

                }
            }, 1200);

        }





    }

 */
/*

    public void animateHandUpp(final int icon, final ImageView imageView) {
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

        if (icon==0){
            mathsAdditionTextView0.animate()
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
                            if (buttonPressedOnTV){
                                buttonPressedOnTick=false;
                                moveto(icon, imageView);
                            }else {
                                animateHandDownn(1, imageView);
                            }
                        }
                    });
        }else if (icon==1){
            mathsAdditionTextView1.animate()
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

                            if (buttonPressedOnTV){
                                buttonPressedOnTick=false;
                                moveto(icon, imageView);
                            }else {
                                animateHandDownn(1, imageView);
                            }
                        }
                    });

        }else if (icon==2){
            mathsAdditionTextView2.animate()
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
                            if (buttonPressedOnTV){
                                buttonPressedOnTick=false;
                                moveto(icon, imageView);
                            }else {
                                animateHandDownn(1, imageView);
                            }
                        }
                    });
        }
        else if (icon==3){
            mathsAdditionTextView3.animate()
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
                            if (buttonPressedOnTV){
                                buttonPressedOnTick=false;
                                moveto(icon, imageView);
                            }else {
                                animateHandDownn(3, imageView);
                            }
                        }
                    });
        }
        else if (icon==4){
            mathsAdditionTextView4.animate()
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
                            if (buttonPressedOnTV){
                                buttonPressedOnTick=false;
                                moveto(icon, imageView);
                            }else {
                                animateHandDownn(4, imageView);
                            }
                        }
                    });
        }
        else if (icon==5){
            mathsAdditionTextView5.animate()
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
                            if (buttonPressedOnTV){
                                buttonPressedOnTick=false;
                                moveto(icon, imageView);
                            }else {
                                animateHandDownn(5, imageView);
                            }
                        }
                    });
        }
        else if (icon==6){
            mathsAdditionTextView6.animate()
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
                            if (buttonPressedOnTV){
                                buttonPressedOnTick=false;
                                moveto(icon, imageView);
                            }else {
                                animateHandDownn(6, imageView);
                            }
                        }
                    });
        }
        else if (icon==7){
            mathsAdditionTextView7.animate()
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
                            if (buttonPressedOnTV){
                                buttonPressedOnTick=false;
                                moveto(icon, imageView);
                            }else {
                                animateHandDownn(7, imageView);
                            }
                        }
                    });
        }
        else if (icon==8){
            mathsAdditionTextView8.animate()
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
                            if (buttonPressedOnTV){
                                buttonPressedOnTick=false;
                                moveto(icon, imageView);
                            }else {
                                animateHandDownn(8, imageView);
                            }
                        }
                    });
        }

        else if (icon==9){
            mathsAdditionTextView9.animate()
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
                            if (buttonPressedOnTV){
                                buttonPressedOnTick=false;
                                moveto(icon, imageView);
                                // animateHandUpOnTick(icon,imageView);
                            }else {
                                animateHandDownn(1, imageView);
                            }
                        }
                    });
        }



    }
*/
/*
    public void animateHandUpOnTick(final int icon, final ImageView imageView) {
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

        mathsAdditionImageViewTick.animate()
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

                        if (buttonPressedOnTick){
                            buttonPressedOnTick=false;
                            // animateHandUpp(icon, imageView);

                            movetoTV(icon, imageView);
                        }else {
                            animateHandDownOnTick(icon, imageView);

                        }
                    }
                });


    }
*/
/*
    public void moveto(final int icon, final ImageView imageview){
        imageview.setVisibility(View.VISIBLE);
        int[] location1 = new int[2];
        //textViewList.get(0).getTextView().getLocationOnScreen(location1);

        mathsAdditionImageViewTick.getLocationOnScreen(location1);
        final int movetoX = location1[0];
        final int movetoY = location1[1];

        imageview.setVisibility(View.INVISIBLE);
        imageview.bringToFront();
        imageview.animate()
                .x(movetoX)
                .y(movetoY)
                .alpha(1.0f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(100)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        // animateup();
                        final Handler handlerr = new Handler();
                        imageview.setVisibility(View.VISIBLE);
                        animateHandUpOnTick(icon, imageview);
                        //movetoback(imageview);
                    }
                });
    }
*/
/*
    public void movetoTV(final int icon, final ImageView imageview){
        imageview.setVisibility(View.VISIBLE);
        int[] location1 = new int[2];
        //textViewList.get(0).getTextView().getLocationOnScreen(location1);

        mathsAdditionImageViewTick.getLocationOnScreen(location1);
        final int movetoX = location1[0];
        final int movetoY = location1[1];

        imageview.setVisibility(View.INVISIBLE);
        imageview.bringToFront();
        imageview.animate()
                .x(dragBactToX)
                .y(dragBactToY)
                .alpha(1.0f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(100)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        // animateup();
                        final Handler handlerr = new Handler();
                        imageview.setVisibility(View.VISIBLE);
                        buttonPressedOnTV=false;
                        animateHandUpp(icon, imageview);
                        //movetoback(imageview);
                    }
                });
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
                    mathsAdditionTextView1.animate()
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
                                    buttonPressedOnTV =true;
                                    animateHandUpp(1,imageView);
                                }
                            });

                }else if (icon==2){
                    mathsAdditionTextView2.animate()
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
                                    buttonPressedOnTV =true;
                                    animateHandUpp(2,imageView);
                                }
                            });
                }
                else if (icon==0){
                    mathsAdditionTextView0.animate()
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
                                    buttonPressedOnTV =true;
                                    animateHandUpp(0,imageView);
                                }
                            });
                }
                else if (icon==3){
                    mathsAdditionTextView3.animate()
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
                                    buttonPressedOnTV =true;
                                    animateHandUpp(3,imageView);
                                }
                            });
                }
                else if (icon==4){
                    mathsAdditionTextView3.animate()
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
                                    buttonPressedOnTV =true;
                                    animateHandUpp(4,imageView);
                                }
                            });
                }
                else if (icon==5){
                    mathsAdditionTextView3.animate()
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
                                    buttonPressedOnTV =true;
                                    animateHandUpp(5,imageView);
                                }
                            });
                }
                else if (icon==6){
                    mathsAdditionTextView3.animate()
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
                                    buttonPressedOnTV =true;
                                    animateHandUpp(6,imageView);
                                }
                            });
                }
                else if (icon==7){
                    mathsAdditionTextView3.animate()
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
                                    buttonPressedOnTV =true;
                                    animateHandUpp(7,imageView);
                                }
                            });
                }
                else if (icon==8){
                    mathsAdditionTextView3.animate()
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
                                    buttonPressedOnTV =true;
                                    animateHandUpp(8,imageView);
                                }
                            });
                }
                else if (icon==9){
                    mathsAdditionTextView9.animate()
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
                                    buttonPressedOnTV =true;
                                    animateHandUpp(9,imageView);
                                }
                            });
                }


            }
        }, 200);


    }
    */
/*
    public void animateHandDownOnTick(final int icon, final ImageView imageView) {

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

                mathsAdditionImageViewTick.animate()
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
                                buttonPressedOnTick =true;
                                animateHandUpOnTick(icon,imageView);
                            }
                        });
            }
        }, 200);


    }
*/

    public void setOnClickListeners(View view) {
        mathsAdditionTextView1.setOnClickListener(this);
        mathsAdditionTextView2.setOnClickListener(this);
        mathsAdditionTextView3.setOnClickListener(this);
        mathsAdditionTextView4.setOnClickListener(this);
        mathsAdditionTextView5.setOnClickListener(this);
        mathsAdditionTextView6.setOnClickListener(this);
        mathsAdditionTextView7.setOnClickListener(this);
        mathsAdditionTextView8.setOnClickListener(this);
        mathsAdditionTextView9.setOnClickListener(this);
        mathsAdditionTextView0.setOnClickListener(this);
        mathsAdditionImageViewDel.setOnClickListener(this);
        mathsAdditionImageViewTick.setOnClickListener(this);

    }

    public void setupData() {

//        String ans = test.getAnswerList().get(0).getText();
//        final Pattern pattern = Pattern.compile("[\\.'\"]");
//        final String[] result = pattern.split(ans);
        finalAnswer = test.getAnswerList().get(0).getText();


        String operand1 = test.getOptionList().get(0).getText();
        String operand2 = test.getOptionList().get(1).getText();

//        final Pattern pattern1 = Pattern.compile("[\\.'\"]");
//        final String[] result1 = pattern1.split(operand1);
//        final String[] result2 = pattern1.split(operand2);
        String op1 = test.getOptionList().get(0).getText();
        String op2 = test.getOptionList().get(1).getText();


        Log.w("data_math", "final answer  :" + finalAnswer);
        Log.w("data_math", "operand 1 :" + op1);
        Log.w("data_math", "operand 2 :" + op2);


        if (op1.length() == 1) {
            one = true;
            mathsAdditionTextViewU1.setText(op1);
        } else if (op1.length() == 2) {
            two = true;
            char[] char1 = op1.toCharArray();
            mathsAdditionTextViewU1.setText(String.valueOf(char1[1]));
            if (mathsAdditionTextViewU1.getText().toString().isEmpty()) {
                mathsAdditionTextViewU1.setText("0");
            }
            if (mathsAdditionTextViewT1.getText().toString().isEmpty()) {
                mathsAdditionTextViewT1.setText("0");
            }
            mathsAdditionTextViewT1.setText(String.valueOf(char1[0]));
        } else if (op1.length() == 3) {
            three = true;
            char[] char1 = op1.toCharArray();
            mathsAdditionTextViewU1.setText(String.valueOf(char1[2]));
            mathsAdditionTextViewT1.setText(String.valueOf(char1[1]));
            mathsAdditionTextViewH1.setText(String.valueOf(char1[0]));
        }

        if (op2.length() == 1) {
            mathsAdditionTextViewU2.setText(op2);
        } else if (op2.length() == 2) {
            char[] char2 = op2.toCharArray();
            mathsAdditionTextViewU2.setText(String.valueOf(char2[1]));
            if (mathsAdditionTextViewU2.getText().toString().isEmpty()) {
                mathsAdditionTextViewU2.setText("0");
            }
            if (mathsAdditionTextViewT2.getText().toString().isEmpty()) {
                mathsAdditionTextViewT2.setText("0");
            }
            mathsAdditionTextViewT2.setText(String.valueOf(char2[0]));
        } else if (op2.length() == 3) {
            char[] char2 = op2.toCharArray();
            mathsAdditionTextViewU2.setText(String.valueOf(char2[2]));
            mathsAdditionTextViewT2.setText(String.valueOf(char2[1]));
            mathsAdditionTextViewH2.setText(String.valueOf(char2[0]));
        }
//        mathsAdditionTextViewTH1.setText("2");
//        mathsAdditionTextViewH1.setText("7");
//        mathsAdditionTextViewT1.setText("9");
//        mathsAdditionTextViewU1.setText("3");
//
//        mathsAdditionTextViewTH2.setText("5");
//        mathsAdditionTextViewH2.setText("3");
//        mathsAdditionTextViewT2.setText("4");
//        mathsAdditionTextViewU2.setText("4");

        mathsAdditionTextViewOperator.setText("+");


    }


    private List<Integer> digits(int i) {
        List<Integer> digits = new ArrayList<Integer>();
        while (i > 0) {
            digits.add(i % 10);
            i /= 10;
        }
        return digits;
    }


    @Override
    public void onClick(View v) {
        if (sharedPrefs.getBoolean("SoundEnabled", true)) {
            tapAudio();
        }
        switch (v.getId()) {
            case R.id.mathsAdditionTextView0:
                displayText.append("0");
                if (!U3) {
                    mathsAdditionTextViewU3.append("0");
                }
                if (!T3 && U3) {
                    mathsAdditionTextViewT3.append("0");
                }
                if (!H3 && T3 && U3) {
                    mathsAdditionTextViewH3.append("0");
                }
                if (!TH3 && H3 && T3 && U3) {
                    mathsAdditionTextViewTH3.append("0");
                }
                break;
            case R.id.mathsAdditionTextView1:
                displayText.append("1");
                if (!U3) {
                    mathsAdditionTextViewU3.append("1");
                }
                if (!T3 && U3) {
                    mathsAdditionTextViewT3.append("1");
                }
                if (!H3 && T3 && U3) {
                    mathsAdditionTextViewH3.append("1");
                }
                if (!TH3 && H3 && T3 && U3) {
                    mathsAdditionTextViewTH3.append("1");
                }

                break;
            case R.id.mathsAdditionTextView2:
                displayText.append("2");

                if (!U3) {
                    mathsAdditionTextViewU3.append("2");
                }
                if (!T3 && U3) {
                    mathsAdditionTextViewT3.append("2");
                }
                if (!H3 && T3 && U3) {
                    mathsAdditionTextViewH3.append("2");
                }
                if (!TH3 && H3 && T3 && U3) {
                    mathsAdditionTextViewTH3.append("2");
                }

                break;
            case R.id.mathsAdditionTextView3:
                displayText.append("3");
                if (!U3) {
                    mathsAdditionTextViewU3.append("3");
                }
                if (!T3 && U3) {
                    mathsAdditionTextViewT3.append("3");
                }
                if (!H3 && T3 && U3) {
                    mathsAdditionTextViewH3.append("3");
                }
                if (!TH3 && H3 && T3 && U3) {
                    mathsAdditionTextViewTH3.append("3");
                }

                break;
            case R.id.mathsAdditionTextView4:
                displayText.append("4");
                if (!U3) {
                    mathsAdditionTextViewU3.append("4");
                }
                if (!T3 && U3) {
                    mathsAdditionTextViewT3.append("4");
                }
                if (!H3 && T3 && U3) {
                    mathsAdditionTextViewH3.append("4");
                }
                if (!TH3 && H3 && T3 && U3) {
                    mathsAdditionTextViewTH3.append("4");
                }

                break;
            case R.id.mathsAdditionTextView5:
                displayText.append("5");
                if (!U3) {
                    mathsAdditionTextViewU3.append("5");
                }
                if (!T3 && U3) {
                    mathsAdditionTextViewT3.append("5");
                }
                if (!H3 && T3 && U3) {
                    mathsAdditionTextViewH3.append("5");
                }
                if (!TH3 && H3 && T3 && U3) {
                    mathsAdditionTextViewTH3.append("5");
                }

                break;
            case R.id.mathsAdditionTextView6:
                displayText.append("6");
                if (!U3) {
                    mathsAdditionTextViewU3.append("6");
                }
                if (!T3 && U3) {
                    mathsAdditionTextViewT3.append("6");
                }
                if (!H3 && T3 && U3) {
                    mathsAdditionTextViewH3.append("6");
                }
                if (!TH3 && H3 && T3 && U3) {
                    mathsAdditionTextViewTH3.append("6");
                }

                break;
            case R.id.mathsAdditionTextView7:
                displayText.append("7");
                if (!U3) {
                    mathsAdditionTextViewU3.append("7");
                }

                if (!T3 && U3) {
                    mathsAdditionTextViewT3.append("7");
                }
                if (!H3 && T3 && U3) {
                    mathsAdditionTextViewH3.append("7");
                }
                if (!TH3 && H3 && T3 && U3) {
                    mathsAdditionTextViewTH3.append("7");
                }

                break;
            case R.id.mathsAdditionTextView8:
                displayText.append("8");
                if (!U3) {
                    mathsAdditionTextViewU3.append("8");
                }
                if (!T3 && U3) {
                    mathsAdditionTextViewT3.append("8");
                }
                if (!H3 && T3 && U3) {
                    mathsAdditionTextViewH3.append("8");
                }
                if (!TH3 && H3 && T3 && U3) {
                    mathsAdditionTextViewTH3.append("8");
                }

                break;
            case R.id.mathsAdditionTextView9:
                displayText.append("9");
                if (!U3) {
                    mathsAdditionTextViewU3.append("9");
                }
                if (!T3 && U3) {
                    mathsAdditionTextViewT3.append("9");
                }
                if (!H3 && T3 && U3) {
                    mathsAdditionTextViewH3.append("9");
                }
                if (!TH3 && H3 && T3 && U3) {
                    mathsAdditionTextViewTH3.append("9");
                }

                break;
            case R.id.mathsAdditionImageViewDel:
                String numbers = displayText.getText().toString();
                String numbers1 = mathsAdditionTextViewU3.getText().toString().trim();
                String numbers2 = mathsAdditionTextViewT3.getText().toString().trim();
                String numbers3 = mathsAdditionTextViewH3.getText().toString().trim();
                String numbers4 = mathsAdditionTextViewTH3.getText().toString().trim();

                if (numbers.length() > 0) {
                    String newnumbers = new StringBuilder(numbers)
                            .deleteCharAt(numbers.length() - 1).toString();
                    displayText.setText(newnumbers);
                }
                if (numbers1.length() >= 1 && !U3) {
                    String newnumbers1 = new StringBuilder(numbers1).deleteCharAt(numbers.length() - 1).toString();
                    mathsAdditionTextViewU3.setText(newnumbers1);
                }

                if (numbers2.length() >= 1 && !T3) {
                    String newnumbers2 = new StringBuilder(numbers2)
                            .deleteCharAt(numbers.length() - 1).toString();
                    mathsAdditionTextViewT3.setText(newnumbers2);
                }

                if (numbers3.length() >= 1 && !H3) {
                    String newnumbers3 = new StringBuilder(numbers3)
                            .deleteCharAt(numbers.length() - 1).toString();
                    mathsAdditionTextViewH3.setText(newnumbers3);
                }
                if (numbers4.length() >= 1 && !TH3) {
                    String newnumbers4 = new StringBuilder(numbers4)
                            .deleteCharAt(numbers.length() - 1).toString();
                    mathsAdditionTextViewTH3.setText(newnumbers4);
                }

                break;
            case R.id.mathsAdditionImageViewTick:

                String value = displayText.getText().toString().trim();
                if (value.isEmpty()) {
                    Toast.makeText(getContext(), "Please type first", Toast.LENGTH_SHORT).show();
                } else {
                    answer = Integer.parseInt(value);
                    Computation();
                }


                break;

        }

    }


    public void Computation() {
        if (!(String.valueOf(answer).isEmpty())) {
            if (!U3 && !(mathsAdditionTextViewU3.getText().toString().isEmpty())) {
                mathsAdditionTextViewU3.setText("");
                if (answer.toString().length() == 2) {
                    int ans1 = digits(answer).get(0);
                    int ans2 = digits(answer).get(1);
                    mathsAdditionTextViewU3.setText(String.valueOf(ans1));
                    mathsAdditionCarryTextViewT.setVisibility(View.VISIBLE);
                    mathsAdditionCarryTextViewT.setText(String.valueOf(ans2));
                    tcarry = ans2;
                } else {
                    mathsAdditionTextViewU3.setText(String.valueOf(answer));

                }
                U3 = true;
                Log.wtf("-this", "check  first U3 :");
                displayText.setText("");
                answer = null;
                setBackground();
                if (one) {
                    CompileAnswer();
                }



            } else if (!T3 && U3) {
                mathsAdditionTextViewT3.setText("");
                Log.wtf("compile_an", "value is "+two);
                if (!two) {
                    if (answer.toString().length() == 2) {
                        mathsAdditionTextViewT3.setText(String.valueOf(digits(answer).get(0)));
                        mathsAdditionCarryTextViewH.setVisibility(View.VISIBLE);
                        mathsAdditionCarryTextViewH.setText(String.valueOf(digits(answer).get(1)));
                        tcarry = digits(answer).get(1);

                    }
                } else {
                    mathsAdditionTextViewT3.setText(String.valueOf(answer));
                }
                T3 = true;
                setBackground();
                displayText.setText("");
                answer = null;
                CompileAnswer();
            } else if (!H3 && T3 && U3) {
                Log.wtf("-this", "condition: 1151");
                mathsAdditionTextViewH3.setText("");
                if (answer.toString().length() == 2) {
                    int ans1 = digits(answer).get(0);
                    int ans2 = digits(answer).get(1);
                    mathsAdditionTextViewH3.setText(String.valueOf(ans1));
                    mathsAdditionCarryTextViewTH.setVisibility(View.VISIBLE);
                    mathsAdditionCarryTextViewTH.setText(String.valueOf(ans2));
                    thcarry = ans2;
                } else {
                    mathsAdditionTextViewH3.setText(String.valueOf(answer));
                }
                H3 = true;
                setBackground();
                displayText.setText("");
                answer = null;
                if (two) {
                    CompileAnswer();
                }


            } else if (!TH3 && H3 && T3 && U3) {
                Log.wtf("-this", "condition: 1151");
                mathsAdditionTextViewTH3.setText("");
                mathsAdditionTextViewTH3.setText(String.valueOf(answer));
                TH3 = true;
                setBackground();
                displayText.setText("");
                answer = null;

                String finalValue = mathsAdditionTextViewTH3.getText().toString() + mathsAdditionTextViewH3.getText().toString() +
                        mathsAdditionTextViewT3.getText().toString() + mathsAdditionTextViewU3.getText().toString();
                Log.wtf("-this", "Final Answer : " + finalValue);


            }


        }

    }

    public void CompileAnswer() {
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

        if (one) {
            Log.wtf("compile_answer", "ONE ");
            int answer = Integer.parseInt(finalAnswer);
            Log.wtf("compile_answer", "final answer : " + answer);
            String ans = mathsAdditionTextViewU3.getText().toString().trim();
            int typed_answer = Integer.parseInt(ans);
            Log.wtf("compile_answer", "typed answer : " + typed_answer);
            if (answer == typed_answer) {
                mathsAdditionTextViewU3.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_green_edge_background));
                if (unSocratic == true) {
                    appAnalytics.setOptionSelected(selectedSubject, topic, test.getType(), String.valueOf(typed_answer), true);
                    appAnalytics.setAnswer(selectedSubject, topic + "" + testIndex, topic, test.getType(), test.getAnswerList().size(), 1, true, this.count);
                }
                score = tScore;
                correctCount++;
                totalScore = totalScore + score;
                Log.wtf("compile_answer", "Correct ");

            } else {
                mathsAdditionTextViewU3.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_red_edge_background));
                if (unSocratic == true) {
                    appAnalytics.setOptionSelected(selectedSubject, topic, test.getType(), String.valueOf(typed_answer), false);
                    appAnalytics.setAnswer(selectedSubject, topic + "" + testIndex, topic, test.getType(), test.getAnswerList().size(), 0, false, this.count);
                }
                Log.wtf("compile_answer", "wrong ");
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
                    if (getFragmentManager() != null) {
                        getFragmentManager().beginTransaction()
                                .remove(MathsAddition.this).commit();
                    }

                }
            }, 1000);

        } else if (two) {
            Log.wtf("compile_answer", "TWO ");
            int answer = Integer.parseInt(finalAnswer);
            String ans = mathsAdditionTextViewT3.getText().toString().trim() + mathsAdditionTextViewU3.getText().toString().trim();
            int typed_answer = Integer.parseInt(ans);
            Log.wtf("compile_answer", "final answer : " + answer);
            Log.wtf("compile_answer", "typed answer : " + typed_answer);

            if (answer == typed_answer) {
                Log.wtf("compile_answer2", "correnct answer : ");
                if (unSocratic == true) {
                    appAnalytics.setOptionSelected(selectedSubject, topic, test.getType(), String.valueOf(typed_answer), true);
                    appAnalytics.setAnswer(selectedSubject, topic + "" + testIndex, topic, test.getType(), test.getAnswerList().size(), 1, true, this.count);
                }
                score = tScore;
                correctCount++;
                totalScore = totalScore + score;
                mathsAdditionTextViewU3.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_green_edge_background));
                mathsAdditionTextViewT3.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_green_background));

            } else {
                Log.wtf("compile_answer2", " answer : ");
                if (unSocratic == true) {
                    appAnalytics.setOptionSelected(selectedSubject, topic, test.getType(), String.valueOf(typed_answer), false);
                    appAnalytics.setAnswer(selectedSubject, topic + "" + testIndex, topic, test.getType(), test.getAnswerList().size(), 0, false, this.count);
                }
                mathsAdditionTextViewU3.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_red_edge_background));
                mathsAdditionTextViewT3.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_red_background));

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
                    getFragmentManager().beginTransaction()
                            .remove(MathsAddition.this).commit();

                }
            }, 1000);

        } else if (three) {
        }


//        if (!(String.valueOf(answer).isEmpty())) {
//            if ((!U3 && !(mathsAdditionTextViewU3.getText().toString().isEmpty()) && one)) {
//                mathsAdditionTextViewU3.setText("");
//                Log.wtf("math_addition", "answer is :" + finalAnswer);
//                Log.wtf("math_addition", "typed is :" + answer);
//                mathsAdditionTextViewU3.setText(String.valueOf(answer));
//                displayText.setText("");
//                U3 = true;
//                if (String.valueOf(answer).equals(finalAnswer)) {
//                    mathsAdditionTextViewU3.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_green_edge_background));
//                } else {
//                    mathsAdditionTextViewU3.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_red_edge_background));
//                }
//
//
//            } else if (!T3 && two) {
//                Log.wtf("math_addition", "second condition");
//                mathsAdditionTextViewU3.setText("");
//                if (String.valueOf(answer).length() == 2) {
//                    if (mathsAdditionTextViewU3.getText().toString().isEmpty()) {
//                        String ans = String.valueOf(answer);
//                        int ans1 = digits(answer).get(0);
//                        int ans2 = digits(answer).get(1);
//                        mathsAdditionTextViewU3.setText(String.valueOf(ans1));
//                        mathsAdditionCarryTextViewT.setVisibility(View.VISIBLE);
//                        mathsAdditionCarryTextViewT.setText(String.valueOf(ans2));
//                        tcarry = ans2;
//                        Log.wtf("math_addition", "answer :" + ans);
//                        Log.wtf("math_addition", "carry :" + tcarry);
//                        Log.wtf("math_addition", "check 1 :" + ans1);
//                        Log.wtf("math_addition", "Check 2 :" + ans2);
//                        U3 = true;
//                    }
//                } else if (U3) {
//                    Log.wtf("math_additio_", "answer :");
//                    if (mathsAdditionTextViewT3.getText().toString().isEmpty()) {
//                        //   int ans= answer=tcarry;
//                        //  mathsAdditionTextViewT3.setText(String.valueOf(ans));
//                    }
//
//
//                }
//
//            } else if (!H3 && T3 && U3) {
//                mathsAdditionTextViewH3.setText("");
//                if (hun1 + hun2 + hcarry >= 10) {
//                    int ans = digits(answer).get(0);
//                    hcarry = 0;
//                    thcarry = 1;
//                    mathsAdditionCarryTextViewTH.setVisibility(View.VISIBLE);
//                    mathsAdditionTextViewH3.setText(String.valueOf(ans));
//                    displayText.setText("");
//                    H3 = true;
//                    setBackground();
//
//                }
//
//            }
//        }


    }


    public void tapAudio() {
        mediaPlayerTouch = MediaPlayer.create(getContext(), R.raw.touchone);
        mediaPlayerTouch.start();
    }

    public void tapAudio(int id) {
        if (sharedPrefs.getBoolean("MenuSound", true)) {
            mediaPlayerTouch = MediaPlayer.create(getContext(), id);
            mediaPlayerTouch.start();
            mediaPlayerTouch.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });
        } else
            return;
    }

    public void setBackground() {
        if (!T3 && U3) {
            Log.wtf("-this", "U3 is  :" + U3);
            mathsAdditionTextViewU.setBackgroundResource(0);
            mathsAdditionTextViewU1.setBackgroundResource(0);
            mathsAdditionTextViewU2.setBackgroundResource(0);
            mathsAdditionTextViewU3.setBackgroundResource(0);
            mathsAdditionTextViewT.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_mid_background));
            mathsAdditionTextViewT1.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_mid_background));
            mathsAdditionTextViewT2.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_mid_background));
            mathsAdditionTextViewT3.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_mid_background));

        } else if (!H3 && T3 && U3) {
            Log.wtf("-this", "T3 is  :" + T3);
            mathsAdditionTextViewT.setBackgroundResource(0);
            mathsAdditionTextViewT1.setBackgroundResource(0);
            mathsAdditionTextViewT2.setBackgroundResource(0);
            mathsAdditionTextViewT3.setBackgroundResource(0);
            mathsAdditionTextViewH.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_mid_background));
            mathsAdditionTextViewH1.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_mid_background));
            mathsAdditionTextViewH2.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_mid_background));
            mathsAdditionTextViewH3.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_mid_background));
        } else if (!TH3 && H3 && T3 && U3) {
            Log.wtf("-this", "H3 is  :" + H3);
            mathsAdditionTextViewH.setBackgroundResource(0);
            mathsAdditionTextViewH1.setBackgroundResource(0);
            mathsAdditionTextViewH2.setBackgroundResource(0);
            mathsAdditionTextViewH3.setBackgroundResource(0);
            mathsAdditionTextViewTH.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_mid_background));
            mathsAdditionTextViewTH1.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_mid_background));
            mathsAdditionTextViewTH2.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_mid_background));
            mathsAdditionTextViewTH3.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_mid_background));
        } else if (TH3 && H3 && T3 && U3) {
            Log.wtf("-this", "TH3 is  :" + TH3);
            mathsAdditionTextViewTH.setBackgroundResource(0);
            mathsAdditionTextViewTH1.setBackgroundResource(0);
            mathsAdditionTextViewTH2.setBackgroundResource(0);
            mathsAdditionTextViewTH3.setBackgroundResource(0);

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


}
