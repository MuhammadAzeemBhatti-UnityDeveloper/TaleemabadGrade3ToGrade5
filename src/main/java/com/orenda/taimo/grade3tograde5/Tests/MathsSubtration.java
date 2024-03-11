package com.orenda.taimo.grade3tograde5.Tests;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.os.Handler;

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

import com.orenda.taimo.grade3tograde5.Models.TestJsonParseModel;
import com.orenda.taimo.grade3tograde5.R;
import com.orenda.taimo.grade3tograde5.SimpleTestActivity;
import com.orenda.taimo.grade3tograde5.SocraticActivity;

import firebase.analytics.AppAnalytics;

import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.correctCount;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.selectedSubject;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.testIndex;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.topic;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.totalScore;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.unSocratic;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class MathsSubtration extends Fragment implements View.OnClickListener {


    MediaPlayer mediaPlayerTouch;
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;

    private TextView mathsSubtractionTextView1, mathsSubtractionTextView2, mathsSubtractionTextView3, mathsSubtractionTextView4, mathsSubtractionTextView5, mathsSubtractionTextView6, mathsSubtractionTextView7, mathsSubtractionTextView8, mathsSubtractionTextView9, mathsSubtractionTextView0;

    private TextView displayText, mathsSubtractionCarryTextViewU, mathsSubtractionCarryTextViewT, mathsSubtractionCarryTextViewH, mathsSubtractionCarryTextViewTH;

    private ImageView mathsSubtractionImageViewDel, mathsSubtractionImageViewTick;

    private TextView mathsSubtractionTextViewHTH, mathsSubtractionTextViewTH, mathsSubtractionTextViewH, mathsSubtractionTextViewT, mathsSubtractionTextViewU;
    private TextView mathsSubtractionTextViewHTH1, mathsSubtractionTextViewTH1, mathsSubtractionTextViewH1, mathsSubtractionTextViewT1, mathsSubtractionTextViewU1;
    private TextView mathsSubtractionTextViewOperator, mathsSubtractionTextViewTH2, mathsSubtractionTextViewH2, mathsSubtractionTextViewT2, mathsSubtractionTextViewU2;
    private TextView mathsSubtractionTextViewHTH3, mathsSubtractionTextViewTH3, mathsSubtractionTextViewH3, mathsSubtractionTextViewT3, mathsSubtractionTextViewU3;

    String selectedData;
    TextView selectedView;

    public MathsSubtration() {
    }

    int answerCount = 0;


    private ConstraintLayout explanationMcqPopUpLayout;
    private TextView explanationTextViewInPopUp;
    private ImageView explanationTextViewPopUpClose;


    private int unit1, unit2, ten1, ten2, hun1, hun2, thou1, thou2;
    private Integer answer;
    int ucarry = 0, tcarry = 0, hcarry = 0, thcarry = 0;
    String finalAnswer;

    private Boolean U3 = false, T3 = false, H3 = false, TH3 = false;
    Boolean one = false, two = false, three = false, four = false;
    int realWidth, realHeight;

    private Boolean borrow1 = false, borrow2 = false, borrow3 = false, borrow4 = false, borrow5 = false, borrow6 = false, borrow7 = false;

    int testId = -1;
    TestJsonParseModel test = null;
    Context mContext;
    Activity activity;

    AppAnalytics appAnalytics;
    boolean start = true;
    Timer T = new Timer();
    int count = 0;

    /*int DEMO_MODE = 0;
    ImageView handImageview;
    ConstraintLayout mainConstraintLayout;
    ConstraintLayout parentMainlayout_id;
    int dragBactToX, dragBactToY;
    int moveForCarryX, moveForCarryY;
    int whichTextviewWillDrag;
    Boolean ordinaryMCQ;
    MediaPlayer mp;
    boolean demoStarted = false;
    String checkLang;
    boolean buttonPressedOnTV=false;
    boolean buttonPressedOnTick=false;
    int operand1, operand2;
    boolean userHasPressedOnCarry=false;
    boolean userMovedToSecondBox=false;
     */


    public MathsSubtration(int testId, TestJsonParseModel test, Context context, Activity activity) {
        this.testId = testId;
        this.test = test;
        mContext = context;
        this.activity = activity;
        Log.wtf("-this", " TEST ID : " + testId);
        Log.wtf("-this", " TEST Question : " + test.getQuestion().getText());
        Log.wtf("-this", " TEST Answer : " + test.getAnswerList().get(0).getText());
        Log.wtf("-this", " TEST Operand 2 : " + test.getOptionList().get(0).getText());
        Log.wtf("-this", " TEST Operand 2  : " + test.getOptionList().get(1).getText());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.maths_subtration, container, false);
        sharedPrefs = getActivity().getApplicationContext().getSharedPreferences("DefaultSPForTaleemabadGrade6", 0);
        editor = sharedPrefs.edit();
        appAnalytics = new AppAnalytics(getContext());
        initializeView(view);
        setupData();
        borrowComputation();

        //checkDemo();
        return view;
    }


    public void setupData() {
        String ans = test.getAnswerList().get(0).getText();
        final Pattern pattern = Pattern.compile("[\\.'\"]");
        final String[] result = pattern.split(ans);
        finalAnswer = result[0];


        String operand1 = test.getOptionList().get(0).getText();
        String operand2 = test.getOptionList().get(1).getText();

        Log.wtf("data_math", "operand 1 :" + operand1);
        Log.wtf("data_math", "operand 2 :" + operand2);

        final Pattern pattern1 = Pattern.compile("[\\.'\"]");
        final String[] result1 = pattern1.split(operand1);
        final String[] result2 = pattern1.split(operand2);
        String op1 = result1[0];
        String op2 = result2[0];

        Log.wtf("data_math", "final answer  :" + finalAnswer);


        if (op1.length() == 1) {
            Log.wtf("data_math", "length 1 :");
            one = true;
            mathsSubtractionTextViewU1.setText(op1);
        } else if (op1.length() == 2) {
            Log.wtf("data_math", "length 2 :");
            two = true;
            char[] char1 = op1.toCharArray();
            mathsSubtractionTextViewU1.setText(String.valueOf(char1[1]));
            if (mathsSubtractionTextViewU1.getText().toString().equals("")) {
                mathsSubtractionTextViewU1.setText("0");
            }
            if (mathsSubtractionTextViewT1.getText().toString().equals("")) {
                mathsSubtractionTextViewT1.setText("0");
            }
            mathsSubtractionTextViewT1.setText(String.valueOf(char1[0]));
        } else if (op1.length() == 3) {
            three = true;
            char[] char1 = op1.toCharArray();
            Log.wtf("data_math", "length 3 :" + char1.toString());

            mathsSubtractionTextViewU1.setText(String.valueOf(char1[2]));
            if (mathsSubtractionTextViewU1.getText().toString().equals("")) {
                mathsSubtractionTextViewU1.setText("0");
            }
            mathsSubtractionTextViewT1.setText(String.valueOf(char1[1]));
            if (mathsSubtractionTextViewT1.getText().toString().equals("")) {
                mathsSubtractionTextViewT1.setText("0");
            }
            mathsSubtractionTextViewH1.setText(String.valueOf(char1[0]));
            if (mathsSubtractionTextViewH1.getText().toString().equals("")) {
                mathsSubtractionTextViewH1.setText("0");
            }
        } else if (op1.length() == 4) {
            Log.wtf("data_math", "length 4 :");
            four = true;
            char[] char1 = op1.toCharArray();
            mathsSubtractionTextViewU1.setText(String.valueOf(char1[3]));
            if (mathsSubtractionTextViewU1.getText().toString().equals("")) {
                mathsSubtractionTextViewU1.setText("0");
            }
            mathsSubtractionTextViewT1.setText(String.valueOf(char1[2]));
            if (mathsSubtractionTextViewT1.getText().toString().equals("")) {
                mathsSubtractionTextViewT1.setText("0");
            }
            mathsSubtractionTextViewH1.setText(String.valueOf(char1[1]));
            if (mathsSubtractionTextViewH1.getText().toString().equals("")) {
                mathsSubtractionTextViewH1.setText("0");
            }

            mathsSubtractionTextViewTH1.setText(String.valueOf(char1[0]));
            if (mathsSubtractionTextViewTH1.getText().toString().equals("")) {
                mathsSubtractionTextViewTH1.setText("0");
            }
        }


        if (op2.length() == 1) {
            mathsSubtractionTextViewU2.setText(op2);
        } else if (op2.length() == 2) {
            char[] char2 = op2.toCharArray();
            mathsSubtractionTextViewU2.setText(String.valueOf(char2[1]));
            if (mathsSubtractionTextViewU2.getText().toString().equals("")) {
                mathsSubtractionTextViewU2.setText("0");
            }
            if (mathsSubtractionTextViewT2.getText().toString().equals("")) {
                mathsSubtractionTextViewT2.setText("0");
            }
            mathsSubtractionTextViewT2.setText(String.valueOf(char2[0]));
        } else if (op2.length() == 3) {
            char[] char2 = op2.toCharArray();
            mathsSubtractionTextViewU2.setText(String.valueOf(char2[2]));
            if (mathsSubtractionTextViewU2.getText().toString().equals("")) {
                mathsSubtractionTextViewU2.setText("0");
            }
            mathsSubtractionTextViewT2.setText(String.valueOf(char2[1]));
            if (mathsSubtractionTextViewT2.getText().toString().equals("")) {
                mathsSubtractionTextViewT2.setText("0");
            }
            mathsSubtractionTextViewH2.setText(String.valueOf(char2[0]));
            if (mathsSubtractionTextViewH2.getText().toString().equals("")) {
                mathsSubtractionTextViewH2.setText("0");
            }
        } else if (op2.length() == 4) {
            char[] char2 = op2.toCharArray();
            mathsSubtractionTextViewU2.setText(String.valueOf(char2[3]));
            if (mathsSubtractionTextViewU2.getText().toString().equals("")) {
                mathsSubtractionTextViewU2.setText("0");
            }
            mathsSubtractionTextViewT2.setText(String.valueOf(char2[2]));
            if (mathsSubtractionTextViewT2.getText().toString().equals("")) {
                mathsSubtractionTextViewT2.setText("0");
            }
            mathsSubtractionTextViewH2.setText(String.valueOf(char2[1]));
            if (mathsSubtractionTextViewH2.getText().toString().equals("")) {
                mathsSubtractionTextViewH2.setText("0");
            }
            mathsSubtractionTextViewTH2.setText(String.valueOf(char2[0]));
            if (mathsSubtractionTextViewTH2.getText().toString().equals("")) {
                mathsSubtractionTextViewTH2.setText("0");
            }
        }


        mathsSubtractionTextViewOperator.setText("-");

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
                SimpleTestActivity.testActivityImageViewDaimond.setVisibility(View.GONE);
            }

            // SimpleTestActivity.testActivityImageViewfeedback.setVisibility(View.GONE);

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
           // SimpleTestActivity.testActivityImageViewfeedback.setVisibility(View.VISIBLE);
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


        String one =test.getOptionList().get(0).getText();
        String two =test.getOptionList().get(1).getText();
        Character operan1String = one.charAt(one.length() - 3);
        Character operan2String = two.charAt(two.length()-3);
        operand1 = operan1String;
        operand2 = operan2String;

        Toast.makeText(getContext(), "1: " + operan1String, Toast.LENGTH_SHORT).show();
        Toast.makeText(getContext(), "2: " + operan2String, Toast.LENGTH_SHORT).show();


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

                    constraintSet.connect(handImageview.getId(), ConstraintSet.TOP, R.id.guidelineButtonDelTop, ConstraintSet.TOP, 20);
                    //       constraintSet.connect(handImageview.getId(),ConstraintSet.END,R.id.guidelineButton2Right,ConstraintSet.START,80);
                    constraintSet.connect(handImageview.getId(), ConstraintSet.START, R.id.guidelineButton2Left, ConstraintSet.END, 40);
                    constraintSet.connect(handImageview.getId(), ConstraintSet.BOTTOM, R.id.guidelineButtonDelBottom, ConstraintSet.TOP, 20);
                    constraintSet.applyTo(mainConstraintLayout);

                    handImageview.getLayoutParams().width = mathsSubtractionTextView0.getMeasuredHeight() / 2;
                    handImageview.getLayoutParams().height = mathsSubtractionTextView0.getMeasuredHeight() / 2;
                    handImageview.bringToFront();

                    int[] location = new int[2];
                    mathsSubtractionTextView0.getLocationOnScreen(location);
                    dragBactToX = location[0];
                    dragBactToY = location[1];

                    //            tap_icon1.setVisibility(View.VISIBLE);

                    if (operand1<operand2){
                        moveForCarry(0, handImageview);
                    }else {
                        animateHandUpp(0, handImageview);

                    }

                }
            }, 1200);
        } else if (ansWill == 1) {
            handlerr.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    jab();

                    mainConstraintLayout.addView(handImageview);
                    constraintSet.connect(handImageview.getId(), ConstraintSet.TOP, R.id.guidelineButton1Top, ConstraintSet.TOP, 20);
                    //       constraintSet.connect(handImageview.getId(),ConstraintSet.END,R.id.guidelineButton1Right,ConstraintSet.START,80);
                    constraintSet.connect(handImageview.getId(), ConstraintSet.START, R.id.guidelineButton1Left, ConstraintSet.END, 40);
                    constraintSet.connect(handImageview.getId(), ConstraintSet.BOTTOM, R.id.guidelineButton1Bottom, ConstraintSet.TOP, 20);
                    constraintSet.applyTo(mainConstraintLayout);


                    handImageview.getLayoutParams().width = mathsSubtractionTextView0.getMeasuredHeight() / 2;
                    handImageview.getLayoutParams().height = mathsSubtractionTextView0.getMeasuredHeight() / 2;
                    handImageview.bringToFront();

                    int[] location = new int[2];
                    mathsSubtractionTextView1.getLocationOnScreen(location);
                    dragBactToX = location[0];
                    dragBactToY = location[1];

                    //            tap_icon1.setVisibility(View.VISIBLE);
                    if (operand1<operand2){
                        moveForCarry(1, handImageview);
                    }else {
                        animateHandUpp(1, handImageview);
                    }
                }
            }, 1200);

        } else if (ansWill == 2) {
            handlerr.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    jab();

                    mainConstraintLayout.addView(handImageview);


                    constraintSet.connect(handImageview.getId(), ConstraintSet.TOP, R.id.guidelineButton1Top, ConstraintSet.TOP, 20);
                    //       constraintSet.connect(handImageview.getId(),ConstraintSet.END,R.id.guidelineButton2Right,ConstraintSet.START,80);
                    constraintSet.connect(handImageview.getId(), ConstraintSet.START, R.id.guidelineButton2Left, ConstraintSet.END, 40);
                    constraintSet.connect(handImageview.getId(), ConstraintSet.BOTTOM, R.id.guidelineButton1Bottom, ConstraintSet.TOP, 20);
                    constraintSet.applyTo(mainConstraintLayout);

                    handImageview.getLayoutParams().width = mathsSubtractionTextView0.getMeasuredHeight() / 2;
                    handImageview.getLayoutParams().height = mathsSubtractionTextView0.getMeasuredHeight() / 2;
                    handImageview.bringToFront();

                    int[] location = new int[2];
                    mathsSubtractionTextView2.getLocationOnScreen(location);
                    dragBactToX = location[0];
                    dragBactToY = location[1];

                    //            tap_icon1.setVisibility(View.VISIBLE);
                    if (operand1<operand2){
                        moveForCarry(2, handImageview);
                    }else {
                        animateHandUpp(2, handImageview);
                    }
                }
            }, 1200);

        } else if (ansWill == 3) {
            handlerr.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    jab();


                    int[] location = new int[2];
                    mathsSubtractionTextView3.getLocationOnScreen(location);
                    dragBactToX = location[0];
                    dragBactToY = location[1];
                    dragBactToX = dragBactToX + 10;
                    dragBactToY = dragBactToY + 10;

                    mainConstraintLayout.addView(handImageview);


                    constraintSet.connect(handImageview.getId(), ConstraintSet.TOP, R.id.guidelineButton1Top, ConstraintSet.TOP, 20);
                    constraintSet.connect(handImageview.getId(), ConstraintSet.END, R.id.guidelineButton3Right, ConstraintSet.START, 80);
                    constraintSet.connect(handImageview.getId(), ConstraintSet.START, R.id.guidelineButton3Left, ConstraintSet.END, 40);
                    constraintSet.connect(handImageview.getId(), ConstraintSet.BOTTOM, R.id.guidelineButton1Bottom, ConstraintSet.TOP, 20);
                    constraintSet.applyTo(mainConstraintLayout);

                    handImageview.getLayoutParams().width = mathsSubtractionTextView0.getMeasuredHeight() / 2;
                    handImageview.getLayoutParams().height = mathsSubtractionTextView0.getMeasuredHeight() / 2;
                    handImageview.bringToFront();

                    //            tap_icon1.setVisibility(View.VISIBLE);
                    if (operand1<operand2){
                        moveForCarry(3, handImageview);
                    }else {
                        animateHandUpp(3, handImageview);
                    }
                }
            }, 1200);

        } else if (ansWill == 4) {
            handlerr.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    jab();

                    mainConstraintLayout.addView(handImageview);

                    constraintSet.connect(handImageview.getId(), ConstraintSet.TOP, R.id.guidelineButton4Top, ConstraintSet.TOP, 20);
                    //       constraintSet.connect(handImageview.getId(),ConstraintSet.END,R.id.guidelineButton1Right,ConstraintSet.START,80);
                    constraintSet.connect(handImageview.getId(), ConstraintSet.START, R.id.guidelineButton1Left, ConstraintSet.END, 40);
                    constraintSet.connect(handImageview.getId(), ConstraintSet.BOTTOM, R.id.guidelineButton4Bottom, ConstraintSet.TOP, 20);
                    constraintSet.applyTo(mainConstraintLayout);

                    handImageview.getLayoutParams().width = mathsSubtractionTextView0.getMeasuredHeight() / 2;
                    handImageview.getLayoutParams().height = mathsSubtractionTextView0.getMeasuredHeight() / 2;
                    handImageview.bringToFront();


                    int[] location = new int[2];
                    mathsSubtractionTextView4.getLocationOnScreen(location);
                    dragBactToX = location[0];
                    dragBactToY = location[1];

                    //            tap_icon1.setVisibility(View.VISIBLE);
                    if (operand1<operand2){
                        moveForCarry(4, handImageview);
                    }else {
                        animateHandUpp(4, handImageview);
                    }
                }
            }, 1200);

        } else if (ansWill == 5) {
            handlerr.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    jab();

                    mainConstraintLayout.addView(handImageview);

                    constraintSet.connect(handImageview.getId(), ConstraintSet.TOP, R.id.guidelineButton4Top, ConstraintSet.TOP, 20);
                    //       constraintSet.connect(handImageview.getId(),ConstraintSet.END,R.id.guidelineButton2Right,ConstraintSet.START,80);
                    constraintSet.connect(handImageview.getId(), ConstraintSet.START, R.id.guidelineButton2Left, ConstraintSet.END, 40);
                    constraintSet.connect(handImageview.getId(), ConstraintSet.BOTTOM, R.id.guidelineButton4Bottom, ConstraintSet.TOP, 20);
                    constraintSet.applyTo(mainConstraintLayout);


                    int[] location = new int[2];
                    mathsSubtractionTextView5.getLocationOnScreen(location);
                    dragBactToX = location[0];
                    dragBactToY = location[1];

                    handImageview.getLayoutParams().width = mathsSubtractionTextView0.getMeasuredHeight() / 2;
                    handImageview.getLayoutParams().height = mathsSubtractionTextView0.getMeasuredHeight() / 2;
                    handImageview.bringToFront();

                    //            tap_icon1.setVisibility(View.VISIBLE);
                    if (operand1<operand2){
                        moveForCarry(5, handImageview);
                    }else {
                        animateHandUpp(5, handImageview);
                    }
                }
            }, 1200);

        } else if (ansWill == 6) {
            handlerr.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    jab();

                    mainConstraintLayout.addView(handImageview);
                    constraintSet.connect(handImageview.getId(), ConstraintSet.TOP, R.id.guidelineButton4Top, ConstraintSet.TOP, 20);
                    //       constraintSet.connect(handImageview.getId(),ConstraintSet.END,R.id.guidelineButton3Right,ConstraintSet.START,80);
                    constraintSet.connect(handImageview.getId(), ConstraintSet.START, R.id.guidelineButton3Left, ConstraintSet.END, 40);
                    constraintSet.connect(handImageview.getId(), ConstraintSet.BOTTOM, R.id.guidelineButton4Bottom, ConstraintSet.TOP, 20);
                    constraintSet.applyTo(mainConstraintLayout);


                    int[] location = new int[2];
                    mathsSubtractionTextView6.getLocationOnScreen(location);
                    dragBactToX = location[0];
                    dragBactToY = location[1];

                    handImageview.getLayoutParams().width = mathsSubtractionTextView0.getMeasuredHeight() / 2;
                    handImageview.getLayoutParams().height = mathsSubtractionTextView0.getMeasuredHeight() / 2;
                    handImageview.bringToFront();

                    //            tap_icon1.setVisibility(View.VISIBLE);
                    if (operand1<operand2){
                        moveForCarry(6, handImageview);
                    }else {
                        animateHandUpp(6, handImageview);
                    }
                }
            }, 1200);

        } else if (ansWill == 7) {
            handlerr.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    jab();

                    mainConstraintLayout.addView(handImageview);

                    constraintSet.connect(handImageview.getId(), ConstraintSet.TOP, R.id.guidelineButton7Top, ConstraintSet.TOP, 20);
                    //       constraintSet.connect(handImageview.getId(),ConstraintSet.END,R.id.guidelineButton1Right,ConstraintSet.START,80);
                    constraintSet.connect(handImageview.getId(), ConstraintSet.START, R.id.guidelineButton1Left, ConstraintSet.END, 40);
                    constraintSet.connect(handImageview.getId(), ConstraintSet.BOTTOM, R.id.guidelineButton7Bottom, ConstraintSet.TOP, 20);
                    constraintSet.applyTo(mainConstraintLayout);


                    int[] location = new int[2];
                    mathsSubtractionTextView7.getLocationOnScreen(location);
                    dragBactToX = location[0];
                    dragBactToY = location[1];

                    handImageview.getLayoutParams().width = mathsSubtractionTextView0.getMeasuredHeight() / 2;
                    handImageview.getLayoutParams().height = mathsSubtractionTextView0.getMeasuredHeight() / 2;
                    handImageview.bringToFront();

                    //            tap_icon1.setVisibility(View.VISIBLE);
                    if (operand1<operand2){
                        moveForCarry(7, handImageview);
                    }else {
                        animateHandUpp(7, handImageview);
                    }
                }
            }, 1200);

        } else if (ansWill == 8) {
            handlerr.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    jab();

                    mainConstraintLayout.addView(handImageview);

                    constraintSet.connect(handImageview.getId(), ConstraintSet.TOP, R.id.guidelineButton7Top, ConstraintSet.TOP, 20);
                    //       constraintSet.connect(handImageview.getId(),ConstraintSet.END,R.id.guidelineButton2Right,ConstraintSet.START,80);
                    constraintSet.connect(handImageview.getId(), ConstraintSet.START, R.id.guidelineButton2Left, ConstraintSet.END, 40);
                    constraintSet.connect(handImageview.getId(), ConstraintSet.BOTTOM, R.id.guidelineButton7Bottom, ConstraintSet.TOP, 20);
                    constraintSet.applyTo(mainConstraintLayout);

                    handImageview.getLayoutParams().width = mathsSubtractionTextView0.getMeasuredHeight() / 2;
                    handImageview.getLayoutParams().height = mathsSubtractionTextView0.getMeasuredHeight() / 2;
                    handImageview.bringToFront();


                    int[] location = new int[2];
                    mathsSubtractionTextView8.getLocationOnScreen(location);
                    dragBactToX = location[0];
                    dragBactToY = location[1];


                    //            tap_icon1.setVisibility(View.VISIBLE);
                    if (operand1<operand2){
                        moveForCarry(8, handImageview);
                    }else {
                        animateHandUpp(8, handImageview);
                    }
                }
            }, 1200);

        } else if (ansWill == 9) {
            handlerr.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    jab();

                    mainConstraintLayout.addView(handImageview);


                    constraintSet.connect(handImageview.getId(), ConstraintSet.TOP, R.id.guidelineButton7Top, ConstraintSet.TOP, 20);
                    //       constraintSet.connect(handImageview.getId(),ConstraintSet.END,R.id.guidelineButton3Right,ConstraintSet.START,80);
                    constraintSet.connect(handImageview.getId(), ConstraintSet.START, R.id.guidelineButton3Left, ConstraintSet.END, 40);
                    constraintSet.connect(handImageview.getId(), ConstraintSet.BOTTOM, R.id.guidelineButton7Bottom, ConstraintSet.TOP, 20);
                    constraintSet.applyTo(mainConstraintLayout);

                    handImageview.getLayoutParams().width = mathsSubtractionTextView0.getMeasuredHeight() / 2;
                    handImageview.getLayoutParams().height = mathsSubtractionTextView0.getMeasuredHeight() / 2;
                    handImageview.bringToFront();


                    int[] location = new int[2];
                    mathsSubtractionTextView9.getLocationOnScreen(location);
                    dragBactToX = location[0];
                    dragBactToY = location[1];

                    //            tap_icon1.setVisibility(View.VISIBLE);
                    if (operand1<operand2){
                        moveForCarry(9, handImageview);
                    }else {
                        animateHandUpp(9, handImageview);
                    }
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
            mathsSubtractionTextView0.animate()
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
            mathsSubtractionTextView1.animate()
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
            mathsSubtractionTextView2.animate()
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
            mathsSubtractionTextView3.animate()
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
            mathsSubtractionTextView4.animate()
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
            mathsSubtractionTextView5.animate()
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
            mathsSubtractionTextView6.animate()
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
            mathsSubtractionTextView7.animate()
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
            mathsSubtractionTextView8.animate()
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
            mathsSubtractionTextView9.animate()
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
    public void moveForCarry(final int icon, final ImageView imageView){
        if (operand1<operand2){

            int[] location = new int[2];
            mathsSubtractionTextViewT1.getLocationOnScreen(location);
            moveForCarryX = location[0];
            moveForCarryY = location[1];

//                imageView.setVisibility(View.INVISIBLE);

            imageView.animate()
                    .x(moveForCarryX)
                    .y(moveForCarryY)
                    .alpha(1.0f)
                    .scaleX(1.2f)
                    .scaleY(1.2f)
                    .setDuration(500)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);

                            imageView.setVisibility(View.VISIBLE);


                            if (userHasPressedOnCarry){
                                movetoTV(icon, imageView);

                            }else {
                                animateHandDownOnCarry(icon, imageView);

                            }


                        //movetoback(imageview);
                        }
                    });

        }
    }

 */

/*
    public void animateHandDownOnCarry(final int icon, final ImageView imageView){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                imageView.animate()
                        .alpha(1.0f)
                        .scaleX(0.8f)
                        .scaleY(0.8f)
                        .setDuration(500)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                imageView.animate()
                                        .alpha(1.0f)
                                        .scaleX(1.2f)
                                        .scaleY(1.2f)
                                        .setDuration(500)
                                        .setListener(new AnimatorListenerAdapter() {
                                            @Override
                                            public void onAnimationEnd(Animator animation) {
                                                super.onAnimationEnd(animation);

                                                moveForCarry(icon, imageView);
                                            }
                                        });

                            }
                        });
            }
        }, 200);
    }
    /*
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
                    mathsSubtractionTextView1.animate()
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
                    mathsSubtractionTextView0.animate()
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
                    mathsSubtractionTextView0.animate()
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
                    mathsSubtractionTextView3.animate()
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
                    mathsSubtractionTextView4.animate()
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
                    mathsSubtractionTextView5.animate()
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
                    mathsSubtractionTextView6.animate()
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
                    mathsSubtractionTextView7.animate()
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
                    mathsSubtractionTextView8.animate()
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
                    mathsSubtractionTextView9.animate()
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
    public void moveto(final int icon, final ImageView imageview){
        imageview.setVisibility(View.VISIBLE);
        int[] location1 = new int[2];
        //textViewList.get(0).getTextView().getLocationOnScreen(location1);

        mathsSubtractionImageViewTick.getLocationOnScreen(location1);
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

        mathsSubtractionImageViewTick.animate()
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
                            if (userMovedToSecondBox){
                                imageView.setVisibility(View.GONE);
                                return;
                            }else {
                                movetoTV(icon, imageView);
                            }

                            //  moveForCarry(icon, imageView);
                            //  movetoTV(icon, imageView);
                        }else {
                            animateHandDownOnTick(icon, imageView);

                        }
                    }
                });


    }
    */
/*
    public void movetoTV(final int icon, final ImageView imageview){
        imageview.setVisibility(View.VISIBLE);
        int[] location1 = new int[2];
        //textViewList.get(0).getTextView().getLocationOnScreen(location1);

        mathsSubtractionImageViewTick.getLocationOnScreen(location1);
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

                mathsSubtractionImageViewTick.animate()
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

    public void initializeView(View view) {

        mathsSubtractionTextViewHTH = view.findViewById(R.id.mathsSubtractionTextViewHTH);
        mathsSubtractionTextViewHTH1 = view.findViewById(R.id.mathsSubtractionTextViewHTH1);
        mathsSubtractionTextViewHTH3 = view.findViewById(R.id.mathsSubtractionTextViewHTH3);

        mathsSubtractionTextViewOperator = view.findViewById(R.id.mathsSubtractionTextViewOperator);

        mathsSubtractionTextViewTH = view.findViewById(R.id.mathsSubtractionTextViewTH);
        mathsSubtractionTextViewTH1 = view.findViewById(R.id.mathsSubtractionTextViewTH1);
        mathsSubtractionTextViewTH2 = view.findViewById(R.id.mathsSubtractionTextViewTH2);

        mathsSubtractionTextViewH = view.findViewById(R.id.mathsSubtractionTextViewH);
        mathsSubtractionTextViewH1 = view.findViewById(R.id.mathsSubtractionTextViewH1);
        mathsSubtractionTextViewH2 = view.findViewById(R.id.mathsSubtractionTextViewH2);

        mathsSubtractionTextViewT = view.findViewById(R.id.mathsSubtractionTextViewT);
        mathsSubtractionTextViewT1 = view.findViewById(R.id.mathsSubtractionTextViewT1);
        mathsSubtractionTextViewT2 = view.findViewById(R.id.mathsSubtractionTextViewT2);

        mathsSubtractionTextViewU = view.findViewById(R.id.mathsSubtractionTextViewU);
        mathsSubtractionTextViewU1 = view.findViewById(R.id.mathsSubtractionTextViewU1);
        mathsSubtractionTextViewU2 = view.findViewById(R.id.mathsSubtractionTextViewU2);


        // answer textviews
        mathsSubtractionTextViewTH3 = view.findViewById(R.id.mathsSubtractionTextViewTH3);
        mathsSubtractionTextViewH3 = view.findViewById(R.id.mathsSubtractionTextViewH3);
        mathsSubtractionTextViewT3 = view.findViewById(R.id.mathsSubtractionTextViewT3);
        mathsSubtractionTextViewU3 = view.findViewById(R.id.mathsSubtractionTextViewU3);
        displayText = view.findViewById(R.id.showText);

        //carry
        mathsSubtractionCarryTextViewU = view.findViewById(R.id.mathsSubtractionCarryTextViewU);
        mathsSubtractionCarryTextViewT = view.findViewById(R.id.mathsSubtractionCarryTextViewT);
        mathsSubtractionCarryTextViewH = view.findViewById(R.id.mathsSubtractionCarryTextViewH);
        mathsSubtractionCarryTextViewTH = view.findViewById(R.id.mathsSubtractionCarryTextViewTH);

        //explanation
        //explanation
        explanationMcqPopUpLayout = view.findViewById(R.id.explanationPopUpLayout);
        explanationTextViewPopUpClose = view.findViewById(R.id.explanationTextViewPopUpClose);
        explanationTextViewInPopUp = view.findViewById(R.id.explanationTextViewInPopUp);


        mathsSubtractionTextView1 = view.findViewById(R.id.mathsSubtractionTextView1);
        mathsSubtractionTextView2 = view.findViewById(R.id.mathsSubtractionTextView2);
        mathsSubtractionTextView3 = view.findViewById(R.id.mathsSubtractionTextView3);
        mathsSubtractionTextView4 = view.findViewById(R.id.mathsSubtractionTextView4);
        mathsSubtractionTextView5 = view.findViewById(R.id.mathsSubtractionTextView5);
        mathsSubtractionTextView6 = view.findViewById(R.id.mathsSubtractionTextView6);
        mathsSubtractionTextView7 = view.findViewById(R.id.mathsSubtractionTextView7);
        mathsSubtractionTextView8 = view.findViewById(R.id.mathsSubtractionTextView8);
        mathsSubtractionTextView9 = view.findViewById(R.id.mathsSubtractionTextView9);
        mathsSubtractionTextView0 = view.findViewById(R.id.mathsSubtractionTextView0);

        mathsSubtractionImageViewDel = view.findViewById(R.id.mathsSubtractionImageViewDel);
        mathsSubtractionImageViewTick = view.findViewById(R.id.mathsSubtractionImageViewTick);

        ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    mathsSubtractionTextView1.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    int height = mathsSubtractionTextView1.getHeight();
                    //  int height = mathsSubtractionTextView1.getWidth();
                    int width = mathsSubtractionTextView1.getWidth();
                    ViewGroup.LayoutParams params = mathsSubtractionTextView1.getLayoutParams();
                    params.width = width;
                    params.height = width;
                    mathsSubtractionTextView1.setLayoutParams(params);

                    params = mathsSubtractionTextView2.getLayoutParams();
                    params.width = width;
                    params.height = width;
                    mathsSubtractionTextView2.setLayoutParams(params);

                    params = mathsSubtractionTextView3.getLayoutParams();
                    params.width = width;
                    params.height = width;
                    mathsSubtractionTextView3.setLayoutParams(params);

                    params = mathsSubtractionTextView4.getLayoutParams();
                    params.width = width;
                    params.height = width;
                    mathsSubtractionTextView4.setLayoutParams(params);

                    params = mathsSubtractionTextView5.getLayoutParams();
                    params.width = width;
                    params.height = width;
                    mathsSubtractionTextView5.setLayoutParams(params);

                    params = mathsSubtractionTextView6.getLayoutParams();
                    params.width = width;
                    params.height = width;
                    mathsSubtractionTextView6.setLayoutParams(params);

                    params = mathsSubtractionTextView7.getLayoutParams();
                    params.width = width;
                    params.height = width;
                    mathsSubtractionTextView7.setLayoutParams(params);

                    params = mathsSubtractionTextView8.getLayoutParams();
                    params.width = width;
                    params.height = width;
                    mathsSubtractionTextView8.setLayoutParams(params);

                    params = mathsSubtractionTextView9.getLayoutParams();
                    params.width = width;
                    params.height = width;
                    mathsSubtractionTextView9.setLayoutParams(params);

                    params = mathsSubtractionTextView0.getLayoutParams();
                    params.width = width;
                    params.height = width;

                    params = mathsSubtractionImageViewTick.getLayoutParams();
                    params.width = width;
                    params.height = width;
                    mathsSubtractionImageViewTick.setLayoutParams(params);

                    params = mathsSubtractionImageViewDel.getLayoutParams();
                    params.width = width;
                    params.height = width;
                    mathsSubtractionImageViewDel.setLayoutParams(params);


                }
            });
        }

        setOnClickListeners(view);

    }

    public void setOnClickListeners(View view) {

        mathsSubtractionTextView1.setOnClickListener(this);
        mathsSubtractionTextView2.setOnClickListener(this);
        mathsSubtractionTextView3.setOnClickListener(this);
        mathsSubtractionTextView4.setOnClickListener(this);
        mathsSubtractionTextView5.setOnClickListener(this);
        mathsSubtractionTextView6.setOnClickListener(this);
        mathsSubtractionTextView7.setOnClickListener(this);
        mathsSubtractionTextView8.setOnClickListener(this);
        mathsSubtractionTextView9.setOnClickListener(this);
        mathsSubtractionTextView0.setOnClickListener(this);

        mathsSubtractionImageViewDel.setOnClickListener(this);
        mathsSubtractionImageViewTick.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if (sharedPrefs.getBoolean("SoundEnabled", true)) {
            tapAudio();
        }
        switch (v.getId()) {
            case R.id.mathsSubtractionTextView0:
                displayText.append("0");
                if (!U3) {
                    mathsSubtractionTextViewU3.append("0");
                }
                if (!T3 && U3) {
                    mathsSubtractionTextViewT3.append("0");
                }
                if (!H3 && T3 && U3) {
                    mathsSubtractionTextViewH3.append("0");
                }
                if (!TH3 && H3 && T3 && U3) {
                    mathsSubtractionTextViewTH3.append("0");
                }


                break;
            case R.id.mathsSubtractionTextView1:
                displayText.append("1");
                if (!U3) {
                    mathsSubtractionTextViewU3.append("1");
                }
                if (!T3 && U3) {
                    mathsSubtractionTextViewT3.append("1");
                }
                if (!H3 && T3 && U3) {
                    mathsSubtractionTextViewH3.append("1");
                }
                if (!TH3 && H3 && T3 && U3) {
                    mathsSubtractionTextViewTH3.append("1");
                }

                break;
            case R.id.mathsSubtractionTextView2:
                displayText.append("2");

                if (!U3) {
                    mathsSubtractionTextViewU3.append("2");
                }
                if (!T3 && U3) {
                    mathsSubtractionTextViewT3.append("2");
                }
                if (!H3 && T3 && U3) {
                    mathsSubtractionTextViewH3.append("2");
                }
                if (!TH3 && H3 && T3 && U3) {
                    mathsSubtractionTextViewTH3.append("2");
                }

                break;
            case R.id.mathsSubtractionTextView3:
                displayText.append("3");
                if (!U3) {
                    mathsSubtractionTextViewU3.append("3");
                }
                if (!T3 && U3) {
                    mathsSubtractionTextViewT3.append("3");
                }
                if (!H3 && T3 && U3) {
                    mathsSubtractionTextViewH3.append("3");
                }
                if (!TH3 && H3 && T3 && U3) {
                    mathsSubtractionTextViewTH3.append("3");
                }

                break;
            case R.id.mathsSubtractionTextView4:
                displayText.append("4");
                if (!U3) {
                    mathsSubtractionTextViewU3.append("4");
                }
                if (!T3 && U3) {
                    mathsSubtractionTextViewT3.append("4");
                }
                if (!H3 && T3 && U3) {
                    mathsSubtractionTextViewH3.append("4");
                }
                if (!TH3 && H3 && T3 && U3) {
                    mathsSubtractionTextViewTH3.append("4");
                }

                break;
            case R.id.mathsSubtractionTextView5:
                displayText.append("5");
                if (!U3) {
                    mathsSubtractionTextViewU3.append("5");
                }
                if (!T3 && U3) {
                    mathsSubtractionTextViewT3.append("5");
                }
                if (!H3 && T3 && U3) {
                    mathsSubtractionTextViewH3.append("5");
                }
                if (!TH3 && H3 && T3 && U3) {
                    mathsSubtractionTextViewTH3.append("5");
                }

                break;
            case R.id.mathsSubtractionTextView6:
                displayText.append("6");
                if (!U3) {
                    mathsSubtractionTextViewU3.append("6");
                }
                if (!T3 && U3) {
                    mathsSubtractionTextViewT3.append("6");
                }
                if (!H3 && T3 && U3) {
                    mathsSubtractionTextViewH3.append("6");
                }
                if (!TH3 && H3 && T3 && U3) {
                    mathsSubtractionTextViewTH3.append("6");
                }

                break;
            case R.id.mathsSubtractionTextView7:
                displayText.append("7");
                if (!U3) {
                    mathsSubtractionTextViewU3.append("7");
                }

                if (!T3 && U3) {
                    mathsSubtractionTextViewT3.append("7");
                }
                if (!H3 && T3 && U3) {
                    mathsSubtractionTextViewH3.append("7");
                }
                if (!TH3 && H3 && T3 && U3) {
                    mathsSubtractionTextViewTH3.append("7");
                }

                break;
            case R.id.mathsSubtractionTextView8:
                displayText.append("8");
                if (!U3) {
                    mathsSubtractionTextViewU3.append("8");
                }
                if (!T3 && U3) {
                    mathsSubtractionTextViewT3.append("8");
                }
                if (!H3 && T3 && U3) {
                    mathsSubtractionTextViewH3.append("8");
                }
                if (!TH3 && H3 && T3 && U3) {
                    mathsSubtractionTextViewTH3.append("8");
                }

                break;
            case R.id.mathsSubtractionTextView9:
                displayText.append("9");
                if (!U3) {
                    mathsSubtractionTextViewU3.append("9");
                }
                if (!T3 && U3) {
                    mathsSubtractionTextViewT3.append("9");
                }
                if (!H3 && T3 && U3) {
                    mathsSubtractionTextViewH3.append("9");
                }
                if (!TH3 && H3 && T3 && U3) {
                    mathsSubtractionTextViewTH3.append("9");
                }

                break;
            case R.id.mathsSubtractionImageViewDel:
                String numbers = displayText.getText().toString();
                String numbers1 = mathsSubtractionTextViewU3.getText().toString().trim();
                String numbers2 = mathsSubtractionTextViewT3.getText().toString().trim();
                String numbers3 = mathsSubtractionTextViewH3.getText().toString().trim();
                String numbers4 = mathsSubtractionTextViewTH3.getText().toString().trim();

                if (numbers.length() > 0) {
                    String newnumbers = new StringBuilder(numbers)
                            .deleteCharAt(numbers.length() - 1).toString();
                    displayText.setText(newnumbers);
                }
                if (numbers1.length() >= 1 && !U3) {
                    String newnumbers1 = new StringBuilder(numbers1).deleteCharAt(numbers.length() - 1).toString();
                    mathsSubtractionTextViewU3.setText(newnumbers1);
                }

                if (numbers2.length() >= 1 && !T3) {
                    String newnumbers2 = new StringBuilder(numbers2)
                            .deleteCharAt(numbers.length() - 1).toString();
                    mathsSubtractionTextViewT3.setText(newnumbers2);
                }

                if (numbers3.length() >= 1 && !H3) {
                    String newnumbers3 = new StringBuilder(numbers3)
                            .deleteCharAt(numbers.length() - 1).toString();
                    mathsSubtractionTextViewH3.setText(newnumbers3);
                }
                if (numbers4.length() >= 1 && !TH3) {
                    String newnumbers4 = new StringBuilder(numbers4)
                            .deleteCharAt(numbers.length() - 1).toString();
                    mathsSubtractionTextViewTH3.setText(newnumbers4);
                }

                break;
            case R.id.mathsSubtractionImageViewTick:

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

    private void borrowComputation() {
        Log.wtf("borrow", "borrowComputation");
        String sunit1 = mathsSubtractionTextViewU1.getText().toString();
        String sunit2 = mathsSubtractionTextViewU2.getText().toString();
        String sten1 = mathsSubtractionTextViewT1.getText().toString();
        String sten2 = mathsSubtractionTextViewT2.getText().toString();
        String shundred1 = mathsSubtractionTextViewH1.getText().toString();
        String shundred2 = mathsSubtractionTextViewH2.getText().toString();
        String sthous1 = mathsSubtractionTextViewTH1.getText().toString();
        String sthous2 = mathsSubtractionTextViewTH2.getText().toString();

        if (one) {
            unit1 = Integer.parseInt(sunit1);
            unit2 = Integer.parseInt(sunit2);
        } else if (two) {
            if (sten1.equals("")) {
                unit1 = 0;
            } else {
                unit1 = Integer.parseInt(sunit1);
            }
            if (sten2.equals("")) {
                unit2 = 0;
            } else {
                unit2 = Integer.parseInt(sunit2);
            }
            if (sten1.equals("")) {
                ten1 = 0;
            } else {
                ten1 = Integer.parseInt(sten1);
            }

            if (sten2.equals("")) {
                ten2 = 0;
            } else {
                ten2 = Integer.parseInt(sten2);
            }
        } else if (three) {
            if (sten1.equals("")) {
                unit1 = 0;
            } else {
                unit1 = Integer.parseInt(sunit1);
            }
            if (sten2.equals("")) {
                unit2 = 0;
            } else {
                unit2 = Integer.parseInt(sunit2);
            }
            if (sten1.equals("")) {
                ten1 = 0;
            } else {
                ten1 = Integer.parseInt(sten1);
            }

            if (sten2.equals("")) {
                ten2 = 0;
            } else {
                ten2 = Integer.parseInt(sten2);
            }


            if (shundred1.equals("")) {
                hun1 = 0;
            } else {
                hun1 = Integer.parseInt(shundred1);
            }

            if (shundred2.equals("")) {
                hun2 = 0;
            } else {
                hun2 = Integer.parseInt(shundred2);
            }


        } else if (four) {


            if (sten1.equals("")) {
                unit1 = 0;
            } else {
                unit1 = Integer.parseInt(sunit1);
            }
            if (sten2.equals("")) {
                unit2 = 0;
            } else {
                unit2 = Integer.parseInt(sunit2);
            }
            if (sten1.equals("")) {
                ten1 = 0;
            } else {
                ten1 = Integer.parseInt(sten1);
            }

            if (sten2.equals("")) {
                ten2 = 0;
            } else {
                ten2 = Integer.parseInt(sten2);
            }


            if (shundred1.equals("")) {
                hun1 = 0;
            } else {
                hun1 = Integer.parseInt(shundred1);
            }

            if (shundred2.equals("")) {
                hun2 = 0;
            } else {
                hun2 = Integer.parseInt(shundred2);
            }


            if (sthous1.equals("")) {
                thou1 = 0;
            } else {
                thou1 = Integer.parseInt(sthous1);
            }

            if (sthous2.equals("")) {
                thou2 = 0;
            } else {
                thou2 = Integer.parseInt(sthous2);
            }


        }

//


        if (unit1 - unit2 < 0 && mathsSubtractionTextViewU3.getText().toString().isEmpty()) {

            if (ten1 > 0 && !borrow1) {
                Log.wtf("-this", "ten1  > 0 : 476" + borrow1);
                ucarry = 10;
                mathsSubtractionTextViewT1.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_borrow_bg));
                if (mathsSubtractionTextViewU3.getText().toString().isEmpty()) {
                    mathsSubtractionTextViewT1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mathsSubtractionTextViewU3.setText("");


                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    mathsSubtractionCarryTextViewU.setVisibility(View.VISIBLE);
                                    mathsSubtractionCarryTextViewU.setText(String.valueOf(ucarry));
                                    animateTextview(mathsSubtractionTextViewT1);
                                    ten1 = ten1 - 1;
                                    unit1 = ucarry + unit1;
                                    mathsSubtractionCarryTextViewU.animate()
                                            .translationY(500)
                                            .alpha(0.0f)
                                            .setDuration(1000)
                                            .setListener(new AnimatorListenerAdapter() {
                                                @Override
                                                public void onAnimationEnd(Animator animation) {
                                                    super.onAnimationEnd(animation);
                                                    mathsSubtractionCarryTextViewU.setVisibility(View.GONE);
                                                }
                                            });
                                }
                            }, 300);


                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mathsSubtractionTextViewT1.setText(String.valueOf(ten1));
                                    mathsSubtractionTextViewT1.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_borrow_bg));

                                }
                            }, 400);


                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    final float FREQ = 0.5f;
                                    final float DECAY = 2f;
                                    TimeInterpolator decayingSineWave = new TimeInterpolator() {
                                        @Override
                                        public float getInterpolation(float input) {
                                            double raw = Math.sin(FREQ * input * 2 * Math.PI);
                                            return (float) (raw * Math.exp(-input * DECAY));

                                        }
                                    };
                                    mathsSubtractionTextViewU1.animate()
                                            .yBy(-500)
                                            .setInterpolator(decayingSineWave)
                                            .setDuration(800)
                                            .start();
                                    mathsSubtractionTextViewU1.setText(String.valueOf(unit1));
                                    mathsSubtractionTextViewU1.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_borrow_bg));

                                    borrow1 = true;
                                    mathsSubtractionTextViewT1.setOnClickListener(null);

                                }
                            }, 600);


                        }
                    });


                }


            }
            if (hun1 > 0 && !borrow2 && ten1 <= 0) {
                ucarry = 10;
                tcarry = 9;
                mathsSubtractionTextViewH1.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_borrow_bg));
                if (mathsSubtractionTextViewU3.getText().toString().isEmpty()) {
                    mathsSubtractionTextViewH1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            animateTextview(mathsSubtractionTextViewH1);
                            hun1 = hun1 - 1;
                            final Handler handler = new Handler();
                            mathsSubtractionTextViewH1.setText(String.valueOf(hun1));
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mathsSubtractionCarryTextViewT.setVisibility(View.VISIBLE);
                                    mathsSubtractionCarryTextViewT.setText(String.valueOf(tcarry + 1));

                                }
                            }, 300);


                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mathsSubtractionCarryTextViewT.setText(String.valueOf(tcarry));
                                }
                            }, 600);


                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mathsSubtractionCarryTextViewT.animate()
                                            .translationY(500)
                                            .alpha(0.0f)
                                            .setDuration(1000)
                                            .setListener(new AnimatorListenerAdapter() {
                                                @Override
                                                public void onAnimationEnd(Animator animation) {
                                                    super.onAnimationEnd(animation);
                                                    mathsSubtractionCarryTextViewT.setVisibility(View.GONE);
                                                }
                                            });


                                    final float FREQ = 0.5f;
                                    final float DECAY = 2f;
                                    TimeInterpolator decayingSineWave = new TimeInterpolator() {
                                        @Override
                                        public float getInterpolation(float input) {
                                            double raw = Math.sin(FREQ * input * 2 * Math.PI);
                                            return (float) (raw * Math.exp(-input * DECAY));

                                        }
                                    };

                                    ten1 = ten1 + tcarry;
                                    mathsSubtractionTextViewT1.animate()
                                            .yBy(-500)
                                            .setInterpolator(decayingSineWave)
                                            .setDuration(800)
                                            .start();
                                    mathsSubtractionTextViewT1.setText(String.valueOf(ten1));
                                    mathsSubtractionTextViewT1.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_borrow_bg));

                                }
                            }, 900);


                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mathsSubtractionCarryTextViewU.setVisibility(View.VISIBLE);
                                    mathsSubtractionCarryTextViewU.setText(String.valueOf(ucarry));


                                }
                            }, 1200);


                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mathsSubtractionCarryTextViewU.animate()
                                            .translationY(500)
                                            .alpha(0.0f)
                                            .setDuration(1000)
                                            .setListener(new AnimatorListenerAdapter() {
                                                @Override
                                                public void onAnimationEnd(Animator animation) {
                                                    super.onAnimationEnd(animation);
                                                    mathsSubtractionCarryTextViewU.setVisibility(View.GONE);
                                                }
                                            });
//

                                    mathsSubtractionTextViewU1.setText(String.valueOf(ten1));

                                    final float FREQ = 0.5f;
                                    final float DECAY = 2f;
                                    TimeInterpolator decayingSineWave = new TimeInterpolator() {
                                        @Override
                                        public float getInterpolation(float input) {
                                            double raw = Math.sin(FREQ * input * 2 * Math.PI);
                                            return (float) (raw * Math.exp(-input * DECAY));

                                        }
                                    };

                                    unit1 = unit1 + ucarry;
                                    mathsSubtractionTextViewU1.animate()
                                            .yBy(-500)
                                            .setInterpolator(decayingSineWave)
                                            .setDuration(800)
                                            .start();
                                    mathsSubtractionTextViewU1.setText(String.valueOf(unit1));
                                    mathsSubtractionTextViewU1.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_borrow_bg));


                                }
                            }, 1500);

                            mathsSubtractionTextViewH1.setOnClickListener(null);
                        }
                    });

                    borrow2 = true;
                }


            }

            if (thou1 > 0 && !borrow3 && hun1 <= 0 && ten1 <= 0) {
                ucarry = 10;
                tcarry = 9;
                hcarry = 9;
                mathsSubtractionTextViewTH1.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_borrow_bg));
                if (mathsSubtractionTextViewU3.getText().toString().isEmpty()) {
                    mathsSubtractionTextViewTH1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            animateTextview(mathsSubtractionTextViewTH1);
                            thou1 = thou1 - 1;
                            hun1 = hun1 + hcarry;
                            ten1 = ten1 + tcarry;
                            unit1 = unit1 + ucarry;
                            final Handler handler = new Handler();
                            mathsSubtractionTextViewTH1.setText(String.valueOf(thou1));

                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mathsSubtractionCarryTextViewH.setVisibility(View.VISIBLE);
                                    mathsSubtractionCarryTextViewH.setText(String.valueOf(hcarry + 1));

                                }
                            }, 400);


                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mathsSubtractionCarryTextViewH.setText(String.valueOf(tcarry));
                                    mathsSubtractionCarryTextViewH.animate()
                                            .translationY(500)
                                            .alpha(0.0f)
                                            .setDuration(1000)
                                            .setListener(new AnimatorListenerAdapter() {
                                                @Override
                                                public void onAnimationEnd(Animator animation) {
                                                    super.onAnimationEnd(animation);
                                                    mathsSubtractionCarryTextViewH.setVisibility(View.GONE);
                                                }
                                            });


                                    final float FREQ = 0.5f;
                                    final float DECAY = 2f;
                                    TimeInterpolator decayingSineWave = new TimeInterpolator() {
                                        @Override
                                        public float getInterpolation(float input) {
                                            double raw = Math.sin(FREQ * input * 2 * Math.PI);
                                            return (float) (raw * Math.exp(-input * DECAY));

                                        }
                                    };

                                    mathsSubtractionTextViewH1.animate()
                                            .yBy(-500)
                                            .setInterpolator(decayingSineWave)
                                            .setDuration(1000)
                                            .start();
                                    mathsSubtractionTextViewH1.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_borrow_bg));
                                    mathsSubtractionTextViewH1.setText(String.valueOf(hun1));

                                }
                            }, 800);

                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mathsSubtractionCarryTextViewT.setVisibility(View.VISIBLE);
                                    mathsSubtractionCarryTextViewT.setText(String.valueOf(tcarry + 1));

                                }
                            }, 1200);


                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mathsSubtractionCarryTextViewT.setText(String.valueOf(tcarry));
                                }
                            }, 1600);


                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mathsSubtractionCarryTextViewT.animate()
                                            .translationY(500)
                                            .alpha(0.0f)
                                            .setDuration(1000)
                                            .setListener(new AnimatorListenerAdapter() {
                                                @Override
                                                public void onAnimationEnd(Animator animation) {
                                                    super.onAnimationEnd(animation);
                                                    mathsSubtractionCarryTextViewT.setVisibility(View.GONE);
                                                }
                                            });


                                    final float FREQ = 0.5f;
                                    final float DECAY = 2f;
                                    TimeInterpolator decayingSineWave = new TimeInterpolator() {
                                        @Override
                                        public float getInterpolation(float input) {
                                            double raw = Math.sin(FREQ * input * 2 * Math.PI);
                                            return (float) (raw * Math.exp(-input * DECAY));

                                        }
                                    };

                                    mathsSubtractionTextViewT1.animate()
                                            .yBy(-500)
                                            .setInterpolator(decayingSineWave)
                                            .setDuration(1000)
                                            .start();
                                    mathsSubtractionTextViewT1.setText(String.valueOf(ten1));
                                    mathsSubtractionTextViewT1.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_borrow_bg));

                                }
                            }, 2000);


                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mathsSubtractionCarryTextViewU.setVisibility(View.VISIBLE);
                                    mathsSubtractionCarryTextViewU.setText(String.valueOf(ucarry));


                                }
                            }, 2000);


                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mathsSubtractionCarryTextViewU.animate()
                                            .translationY(500)
                                            .alpha(0.0f)
                                            .setDuration(1000)
                                            .setListener(new AnimatorListenerAdapter() {
                                                @Override
                                                public void onAnimationEnd(Animator animation) {
                                                    super.onAnimationEnd(animation);
                                                    mathsSubtractionCarryTextViewU.setVisibility(View.GONE);
                                                }
                                            });
//

                                    mathsSubtractionTextViewU1.setText(String.valueOf(ten1));

                                    final float FREQ = 0.5f;
                                    final float DECAY = 2f;
                                    TimeInterpolator decayingSineWave = new TimeInterpolator() {
                                        @Override
                                        public float getInterpolation(float input) {
                                            double raw = Math.sin(FREQ * input * 2 * Math.PI);
                                            return (float) (raw * Math.exp(-input * DECAY));

                                        }
                                    };

                                    mathsSubtractionTextViewU1.animate()
                                            .yBy(-500)
                                            .setInterpolator(decayingSineWave)
                                            .setDuration(1000)
                                            .start();
                                    mathsSubtractionTextViewU1.setText(String.valueOf(unit1));
                                    mathsSubtractionTextViewU1.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_borrow_bg));


                                }
                            }, 2400);

                            mathsSubtractionTextViewTH1.setOnClickListener(null);
                        }
                    });

                    borrow3 = true;
                }


            }

        }

        if (ten1 - ten2 < 0) {
            Log.wtf("-check", "ten1  > 0 : 476" + borrow1);
            if (hun1 > 0 && !borrow4) {
                Log.wtf("-this", "ten1  > 0 : 476" + borrow1);
                tcarry = 10;
                mathsSubtractionTextViewH1.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_borrow_bg));
                if (mathsSubtractionTextViewT3.getText().toString().isEmpty()) {
                    mathsSubtractionTextViewH1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mathsSubtractionTextViewT3.setText("");
                            mathsSubtractionCarryTextViewT.setVisibility(View.VISIBLE);
                            mathsSubtractionCarryTextViewT.setText(String.valueOf(tcarry));
                            animateTextview(mathsSubtractionTextViewH1);
                            hun1 = hun1 - 1;
                            ten1 = tcarry + ten1;
                            mathsSubtractionCarryTextViewT.animate()
                                    .translationY(500)
                                    .alpha(0.0f)
                                    .setDuration(1000)
                                    .setListener(new AnimatorListenerAdapter() {
                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            super.onAnimationEnd(animation);
                                            mathsSubtractionCarryTextViewT.setVisibility(View.GONE);
                                        }
                                    });

                            mathsSubtractionTextViewH1.setText(String.valueOf(hun1));
                            mathsSubtractionTextViewH1.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_borrow_bg));


                            final float FREQ = 0.5f;
                            final float DECAY = 2f;
                            TimeInterpolator decayingSineWave = new TimeInterpolator() {
                                @Override
                                public float getInterpolation(float input) {
                                    double raw = Math.sin(FREQ * input * 2 * Math.PI);
                                    return (float) (raw * Math.exp(-input * DECAY));

                                }
                            };
                            mathsSubtractionTextViewT1.animate()
                                    .yBy(-500)
                                    .setInterpolator(decayingSineWave)
                                    .setDuration(800)
                                    .start();
                            mathsSubtractionTextViewT1.setText(String.valueOf(ten1));
                            mathsSubtractionTextViewT1.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_borrow_bg));

                            borrow4 = true;

                            mathsSubtractionTextViewH1.setOnClickListener(null);

                        }
                    });


                }


            }

            if (thou1 > 0 && !borrow5 && hun1 <= 0) {
                tcarry = 10;
                hcarry = 9;
                hun1 = hun1 + hcarry;
                ten1 = ten1 + tcarry;
                thou1 = thou1 - 1;
                mathsSubtractionTextViewTH1.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_borrow_bg));
                if (mathsSubtractionTextViewT3.getText().toString().isEmpty()) {
                    mathsSubtractionTextViewTH1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            animateTextview(mathsSubtractionTextViewTH1);
                            final Handler handler = new Handler();
                            mathsSubtractionTextViewTH1.setText(String.valueOf(thou1));
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mathsSubtractionCarryTextViewH.setVisibility(View.VISIBLE);
                                    mathsSubtractionCarryTextViewH.setText(String.valueOf(hcarry + 1));

                                }
                            }, 300);


                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mathsSubtractionCarryTextViewH.setText(String.valueOf(hcarry));
                                }
                            }, 600);


                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mathsSubtractionCarryTextViewH.animate()
                                            .translationY(500)
                                            .alpha(0.0f)
                                            .setDuration(1000)
                                            .setListener(new AnimatorListenerAdapter() {
                                                @Override
                                                public void onAnimationEnd(Animator animation) {
                                                    super.onAnimationEnd(animation);
                                                    mathsSubtractionCarryTextViewH.setVisibility(View.GONE);
                                                }
                                            });


                                    final float FREQ = 0.5f;
                                    final float DECAY = 2f;
                                    TimeInterpolator decayingSineWave = new TimeInterpolator() {
                                        @Override
                                        public float getInterpolation(float input) {
                                            double raw = Math.sin(FREQ * input * 2 * Math.PI);
                                            return (float) (raw * Math.exp(-input * DECAY));

                                        }
                                    };


                                    mathsSubtractionTextViewH1.animate()
                                            .yBy(-500)
                                            .setInterpolator(decayingSineWave)
                                            .setDuration(800)
                                            .start();
                                    mathsSubtractionTextViewH1.setText(String.valueOf(hun1));
                                    mathsSubtractionTextViewH1.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_borrow_bg));

                                }
                            }, 900);


                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mathsSubtractionCarryTextViewT.setVisibility(View.VISIBLE);
                                    mathsSubtractionCarryTextViewT.setText(String.valueOf(tcarry));


                                }
                            }, 1200);


                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mathsSubtractionCarryTextViewT.animate()
                                            .translationY(500)
                                            .alpha(0.0f)
                                            .setDuration(1000)
                                            .setListener(new AnimatorListenerAdapter() {
                                                @Override
                                                public void onAnimationEnd(Animator animation) {
                                                    super.onAnimationEnd(animation);
                                                    mathsSubtractionCarryTextViewT.setVisibility(View.GONE);
                                                }
                                            });


                                    final float FREQ = 0.5f;
                                    final float DECAY = 2f;
                                    TimeInterpolator decayingSineWave = new TimeInterpolator() {
                                        @Override
                                        public float getInterpolation(float input) {
                                            double raw = Math.sin(FREQ * input * 2 * Math.PI);
                                            return (float) (raw * Math.exp(-input * DECAY));

                                        }
                                    };

                                    mathsSubtractionTextViewT1.animate()
                                            .yBy(-500)
                                            .setInterpolator(decayingSineWave)
                                            .setDuration(800)
                                            .start();
                                    mathsSubtractionTextViewT1.setText(String.valueOf(ten1));
                                    mathsSubtractionTextViewT1.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_borrow_bg));


                                }
                            }, 1500);

                            mathsSubtractionTextViewTH1.setOnClickListener(null);
                        }
                    });

                    borrow5 = true;
                }


            }
        }


        if (hun1 - hun2 < 0 && T3) {
            if (thou1 > 0 && !borrow6) {
                Log.wtf("-this", "ten1  > 0 : 476" + borrow1);
                hcarry = 10;
                mathsSubtractionTextViewTH1.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_borrow_bg));
                if (mathsSubtractionTextViewH3.getText().toString().isEmpty()) {
                    mathsSubtractionTextViewTH1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mathsSubtractionCarryTextViewH.setVisibility(View.VISIBLE);
                            mathsSubtractionCarryTextViewH.setText(String.valueOf(hcarry));
                            animateTextview(mathsSubtractionTextViewTH1);
                            thou1 = thou1 - 1;
                            hun1 = hcarry + hun1;
                            mathsSubtractionCarryTextViewH.animate()
                                    .translationY(500)
                                    .alpha(0.0f)
                                    .setDuration(1000)
                                    .setListener(new AnimatorListenerAdapter() {
                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            super.onAnimationEnd(animation);
                                            mathsSubtractionCarryTextViewH.setVisibility(View.GONE);
                                        }
                                    });

                            mathsSubtractionTextViewTH1.setText(String.valueOf(thou1));
                            mathsSubtractionTextViewTH1.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_borrow_bg));


                            final float FREQ = 0.5f;
                            final float DECAY = 2f;
                            TimeInterpolator decayingSineWave = new TimeInterpolator() {
                                @Override
                                public float getInterpolation(float input) {
                                    double raw = Math.sin(FREQ * input * 2 * Math.PI);
                                    return (float) (raw * Math.exp(-input * DECAY));

                                }
                            };
                            mathsSubtractionTextViewH1.animate()
                                    .yBy(-500)
                                    .setInterpolator(decayingSineWave)
                                    .setDuration(800)
                                    .start();
                            mathsSubtractionTextViewH1.setText(String.valueOf(hun1));
                            mathsSubtractionTextViewH1.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_borrow_bg));

                            borrow4 = true;

                            mathsSubtractionTextViewTH1.setOnClickListener(null);

                        }
                    });


                }
            }

        }


    }

    public void Computation() {


        if (!(String.valueOf(answer).isEmpty())) {
            if (!U3 && !(mathsSubtractionTextViewU3.getText().toString().isEmpty())) {
                mathsSubtractionTextViewU3.setText("");
                mathsSubtractionTextViewU3.setText(String.valueOf(answer));
                U3 = true;
                Log.wtf("-this", "check  first U3 :");
                displayText.setText("");
                answer = null;
                setBackground();
                if (one) {
                    borrowComputation();
                    CompileAnswer();
                }


            } else if (!T3 && U3) {
                mathsSubtractionTextViewT3.setText("");
                Log.wtf("-this", "condition: 2568");
                mathsSubtractionTextViewT3.setText(String.valueOf(answer));
                T3 = true;
                displayText.setText("");
                answer = null;
                setBackground();
                if (two) {
                    borrowComputation();
                    CompileAnswer();
                }

            } else if (!H3 && T3 && U3) {
                Log.wtf("-this", "condition: 2580");
                mathsSubtractionTextViewH3.setText("");
                mathsSubtractionTextViewH3.setText(String.valueOf(answer));
                H3 = true;
                setBackground();
                displayText.setText("");
                answer = null;
                if (three) {
                    borrowComputation();
                    CompileAnswer();
                }
            } else if (!TH3 && H3 && T3 && U3) {
                Log.wtf("-this", "condition: 2590");
                mathsSubtractionTextViewTH3.setText("");
                mathsSubtractionTextViewTH3.setText(String.valueOf(answer));
                TH3 = true;
                setBackground();
                displayText.setText("");
                answer = null;
                if (four) {
                    borrowComputation();
                    CompileAnswer();
                }

            } else {

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
            int answer = Integer.parseInt(finalAnswer);
            Log.wtf("compile_answer", "final answer : " + answer);
            Log.wtf("compile_answer", "typed answer : " + unit1 + unit2);
            String ans = mathsSubtractionTextViewU3.getText().toString().trim();
            int typed_answer = Integer.parseInt(ans);
            if (answer == typed_answer) {
                mathsSubtractionTextViewU3.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_green_edge_background));
                if (unSocratic == true) {
                    appAnalytics.setOptionSelected(selectedSubject, topic, test.getType(), String.valueOf(typed_answer), true);
                    appAnalytics.setAnswer(selectedSubject, topic + "" + testIndex, topic, test.getType(), test.getAnswerList().size(), 1, true, this.count);
                }
                score = tScore;
                correctCount++;
                totalScore = totalScore + score;


            } else {
                if (unSocratic == true) {
                    appAnalytics.setOptionSelected(selectedSubject, topic, test.getType(), String.valueOf(typed_answer), false);
                    appAnalytics.setAnswer(selectedSubject, topic + "" + testIndex, topic, test.getType(), test.getAnswerList().size(), 0, false, this.count);
                }
                mathsSubtractionTextViewU3.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_red_edge_background));

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
                                .remove(MathsSubtration.this).commit();
                    }

                }
            }, 1000);


        } else if (two) {
            int answer = Integer.parseInt(finalAnswer);
            String ans = mathsSubtractionTextViewT3.getText().toString().trim() + mathsSubtractionTextViewU3.getText().toString().trim();
            int typed_answer = Integer.parseInt(ans);
            Log.wtf("compile_answer", "final answer : " + answer);
            Log.wtf("compile_answer", "typed answer : " + ans);

            if (answer == typed_answer) {
                mathsSubtractionTextViewU3.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_green_edge_background));
                mathsSubtractionTextViewT3.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_green_background));
                if (unSocratic == true) {
                    appAnalytics.setOptionSelected(selectedSubject, topic, test.getType(), String.valueOf(typed_answer), true);
                    appAnalytics.setAnswer(selectedSubject, topic + "" + testIndex, topic, test.getType(), test.getAnswerList().size(), 1, true, this.count);
                }
                score = tScore;
                correctCount++;
                totalScore = totalScore + score;


            } else {
                mathsSubtractionTextViewU3.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_red_edge_background));
                mathsSubtractionTextViewT3.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_red_background));
                if (unSocratic == true) {
                    appAnalytics.setOptionSelected(selectedSubject, topic, test.getType(), String.valueOf(typed_answer), false);
                    appAnalytics.setAnswer(selectedSubject, topic + "" + testIndex, topic, test.getType(), test.getAnswerList().size(), 0, false, this.count);
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
                    getFragmentManager().beginTransaction()
                            .remove(MathsSubtration.this).commit();

                }
            }, 1000);


        } else if (three) {

            int answer = Integer.parseInt(finalAnswer);
            String ans = mathsSubtractionTextViewH3.getText().toString().trim() + mathsSubtractionTextViewT3.getText().toString().trim() + mathsSubtractionTextViewU3.getText().toString().trim();
            int typed_answer = Integer.parseInt(ans);
            Log.wtf("compile_answer", "final answer : " + answer);
            Log.wtf("compile_answer", "typed answer : " + ans);

            if (answer == typed_answer) {
                mathsSubtractionTextViewU3.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_green_edge_background));
                mathsSubtractionTextViewT3.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_green_background));
                mathsSubtractionTextViewH3.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_green_background));
                if (unSocratic == true) {
                    appAnalytics.setOptionSelected(selectedSubject, topic, test.getType(), String.valueOf(typed_answer), true);
                    appAnalytics.setAnswer(selectedSubject, topic + "" + testIndex, topic, test.getType(), test.getAnswerList().size(), 1, true, this.count);
                }
                score = tScore;
                correctCount++;
                totalScore = totalScore + score;


            } else {
                mathsSubtractionTextViewU3.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_red_edge_background));
                mathsSubtractionTextViewT3.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_red_background));
                mathsSubtractionTextViewH3.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_red_background));
                if (unSocratic == true) {
                    appAnalytics.setOptionSelected(selectedSubject, topic, test.getType(), String.valueOf(typed_answer), false);
                    appAnalytics.setAnswer(selectedSubject, topic + "" + testIndex, topic, test.getType(), test.getAnswerList().size(), 0, false, this.count);
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
                    getFragmentManager().beginTransaction()
                            .remove(MathsSubtration.this).commit();

                }
            }, 1000);

        } else if (four) {

            int answer = Integer.parseInt(finalAnswer);
            String ans = mathsSubtractionTextViewTH3.getText().toString().trim() + mathsSubtractionTextViewH3.getText().toString().trim() + mathsSubtractionTextViewT3.getText().toString().trim() + mathsSubtractionTextViewU3.getText().toString().trim();
            int typed_answer = Integer.parseInt(ans);
            Log.wtf("compile_answer", "final answer : " + answer);
            Log.wtf("compile_answer", "typed answer : " + ans);

            if (answer == typed_answer) {
                mathsSubtractionTextViewU3.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_green_edge_background));
                mathsSubtractionTextViewT3.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_green_background));
                mathsSubtractionTextViewH3.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_green_background));
                mathsSubtractionTextViewTH3.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_green_background));
                if (unSocratic == true) {
                    appAnalytics.setOptionSelected(selectedSubject, topic, test.getType(), String.valueOf(typed_answer), true);
                    appAnalytics.setAnswer(selectedSubject, topic + "" + testIndex, topic, test.getType(), test.getAnswerList().size(), 1, true, this.count);
                }
                score = tScore;
                correctCount++;
                totalScore = totalScore + score;


            } else {
                mathsSubtractionTextViewU3.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_red_edge_background));
                mathsSubtractionTextViewT3.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_red_background));
                mathsSubtractionTextViewH3.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_red_background));
                mathsSubtractionTextViewTH3.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_red_background));
                if (unSocratic == true) {
                    appAnalytics.setOptionSelected(selectedSubject, topic, test.getType(), String.valueOf(typed_answer), false);
                    appAnalytics.setAnswer(selectedSubject, topic + "" + testIndex, topic, test.getType(), test.getAnswerList().size(), 0, false, this.count);
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
                    getFragmentManager().beginTransaction()
                            .remove(MathsSubtration.this).commit();

                }
            }, 1000);

        }

//        if (!(String.valueOf(answer).isEmpty())) {
//            if ((!U3 && !(mathsSubtractionTextViewU3.getText().toString().isEmpty()) && one)) {
//                mathsSubtractionTextViewU3.setText("");
//                Log.wtf("math_Subtraction", "answer is :" + finalAnswer);
//                Log.wtf("math_Subtraction", "typed is :" + answer);
//                mathsSubtractionTextViewU3.setText(String.valueOf(answer));
//                displayText.setText("");
//                U3 = true;
//                if (String.valueOf(answer).equals(finalAnswer)) {
//                    mathsSubtractionTextViewU3.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_green_edge_background));
//                } else {
//                    mathsSubtractionTextViewU3.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_red_edge_background));
//                }
//
//
//            } else if (!T3 && two) {
//                Log.wtf("math_Subtraction", "second condition");
//                mathsSubtractionTextViewU3.setText("");
//                if (String.valueOf(answer).length() == 2) {
//                    if (mathsSubtractionTextViewU3.getText().toString().isEmpty()) {
//                        String ans = String.valueOf(answer);
//                        int ans1 = digits(answer).get(0);
//                        int ans2 = digits(answer).get(1);
//                        mathsSubtractionTextViewU3.setText(String.valueOf(ans1));
//                        mathsSubtractionCarryTextViewT.setVisibility(View.VISIBLE);
//                        mathsSubtractionCarryTextViewT.setText(String.valueOf(ans2));
//                        tcarry = ans2;
//                        Log.wtf("math_Subtraction", "answer :" + ans);
//                        Log.wtf("math_Subtraction", "carry :" + tcarry);
//                        Log.wtf("math_Subtraction", "check 1 :" + ans1);
//                        Log.wtf("math_Subtraction", "Check 2 :" + ans2);
//                        U3 = true;
//                    }
//                } else if (U3) {
//                    Log.wtf("math_additio_", "answer :");
//                    if (mathsSubtractionTextViewT3.getText().toString().isEmpty()) {
//                        //   int ans= answer=tcarry;
//                        //  mathsSubtractionTextViewT3.setText(String.valueOf(ans));
//                    }
//
//
//                }
//
//            } else if (!H3 && T3 && U3) {
//                mathsSubtractionTextViewH3.setText("");
//                if (hun1 + hun2 + hcarry >= 10) {
//                    int ans = digits(answer).get(0);
//                    hcarry = 0;
//                    thcarry = 1;
//                    mathsSubtractionCarryTextViewTH.setVisibility(View.VISIBLE);
//                    mathsSubtractionTextViewH3.setText(String.valueOf(ans));
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
            mathsSubtractionTextViewU.setBackgroundResource(0);
            mathsSubtractionTextViewU1.setBackgroundResource(0);
            mathsSubtractionTextViewU2.setBackgroundResource(0);
            mathsSubtractionTextViewU3.setBackgroundResource(0);
            mathsSubtractionTextViewT.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_mid_background));
            mathsSubtractionTextViewT1.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_mid_background));
            mathsSubtractionTextViewT2.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_mid_background));
            mathsSubtractionTextViewT3.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_mid_background));

        } else if (!H3 && T3 && U3) {
            Log.wtf("-this", "T3 is  :" + T3);
            mathsSubtractionTextViewT.setBackgroundResource(0);
            mathsSubtractionTextViewT1.setBackgroundResource(0);
            mathsSubtractionTextViewT2.setBackgroundResource(0);
            mathsSubtractionTextViewT3.setBackgroundResource(0);
            mathsSubtractionTextViewH.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_mid_background));
            mathsSubtractionTextViewH1.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_mid_background));
            mathsSubtractionTextViewH2.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_mid_background));
            mathsSubtractionTextViewH3.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_mid_background));
        } else if (!TH3 && H3 && T3 && U3) {
            Log.wtf("-this", "H3 is  :" + H3);
            mathsSubtractionTextViewH.setBackgroundResource(0);
            mathsSubtractionTextViewH1.setBackgroundResource(0);
            mathsSubtractionTextViewH2.setBackgroundResource(0);
            mathsSubtractionTextViewH3.setBackgroundResource(0);
            mathsSubtractionTextViewTH.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_mid_background));
            mathsSubtractionTextViewTH1.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_mid_background));
            mathsSubtractionTextViewTH2.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_mid_background));
            mathsSubtractionTextViewTH3.setBackgroundDrawable(getResources().getDrawable(R.drawable.maths_mid_background));
        } else if (TH3 && H3 && T3 && U3) {
            Log.wtf("-this", "TH3 is  :" + TH3);
            mathsSubtractionTextViewTH.setBackgroundResource(0);
            mathsSubtractionTextViewTH1.setBackgroundResource(0);
            mathsSubtractionTextViewTH2.setBackgroundResource(0);
            mathsSubtractionTextViewTH3.setBackgroundResource(0);

        }
    }


    private void animateTextview(TextView textView) {
        final float FREQ = 3f;
        final float DECAY = 2f;
        TimeInterpolator decayingSineWave = new TimeInterpolator() {
            @Override
            public float getInterpolation(float input) {
                double raw = Math.sin(FREQ * input * 2 * Math.PI);
                return (float) (raw * Math.exp(-input * DECAY));
            }
        };
        textView.animate()
                .xBy(-100)
                .setInterpolator(decayingSineWave)
                .setDuration(800)
                .start();

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