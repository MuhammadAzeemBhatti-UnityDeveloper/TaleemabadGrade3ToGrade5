package com.orenda.taimo.grade3tograde5.Tests;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;


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
import com.orenda.taimo.grade3tograde5.Models.MathsMCQModel;
import com.orenda.taimo.grade3tograde5.Models.TestJsonParseModel;
import com.orenda.taimo.grade3tograde5.R;
import com.orenda.taimo.grade3tograde5.SimpleTestActivity;

import firebase.analytics.AppAnalytics;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

//import static com.facebook.FacebookSdk.getApplicationContext;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.correctCount;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.selectedSubject;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.tempIndex;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.testIndex;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.topic;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.totalScore;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.unSocratic;

@SuppressLint("ValidFragment")
public class MathsMcq extends Fragment implements View.OnClickListener, View.OnTouchListener, View.OnDragListener {

    MediaPlayer mediaPlayerTouch;
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;


    LinearLayout mathsMcqLayout1x1, mathsMcqLayout1x2, mathsMcqLayout1x3, mathsMcqLayout1x4, mathsMcqLayout1x5,
            mathsMcqLayout2x1, mathsMcqLayout2x2, mathsMcqLayout2x3, mathsMcqLayout2x4, mathsMcqLayout2x5,
            mathsMcqLayout3x1, mathsMcqLayout3x2, mathsMcqLayout3x3, mathsMcqLayout3x4, mathsMcqLayout3x5,
            mathsMcqLayout4x1, mathsMcqLayout4x2, mathsMcqLayout4x3, mathsMcqLayout4x4, mathsMcqLayout4x5;

    TextView mathsMcqTextView1x1, mathsMcqTextView1x2, mathsMcqTextView1x3, mathsMcqTextView1x4, mathsMcqTextView1x5,
            mathsMcqTextView2x1, mathsMcqTextView2x2, mathsMcqTextView2x3, mathsMcqTextView2x4, mathsMcqTextView2x5,
            mathsMcqTextView3x1, mathsMcqTextView3x2, mathsMcqTextView3x3, mathsMcqTextView3x4, mathsMcqTextView3x5,
            mathsMcqTextView4x1, mathsMcqTextView4x2, mathsMcqTextView4x3, mathsMcqTextView4x4, mathsMcqTextView4x5;

    TextView mathsMcqTextViewButton1x1, mathsMcqTextViewButton1x2, mathsMcqTextViewButton2x1, mathsMcqTextViewButton2x2,questionTextViewQuestion;


    ArrayList<MathsMCQModel> list = new ArrayList<>();
    ArrayList<TextView> answerTextViews = new ArrayList<>();

    String selectedData;
    TextView selectedView;
    boolean dropDone = false;
    int answerCount = 0;
    int testId = -1;
    TestJsonParseModel test = null;
    Context mContext;
    Activity activity;
    String operand1, operand2, operator, answer, option;

    AppAnalytics appAnalytics;
    boolean start = true;
    Timer T = new Timer();
    int count = 0;
    private DataSource.Factory dataSourceFactory;
    private ExtractorsFactory extractorsFactory;
    private MediaSource mediaSource;
    private SimpleExoPlayer player;
    private final String streamUrl = "http://cdn.audios.taleemabad.com/QuestionBank/";
    private boolean compileCalled = false;

    /*
    int DEMO_MODE=0;
    ImageView handImageview;
    ConstraintLayout mainConstraintLayout;
    ConstraintLayout parentMainlayout_id;
    int  DragTextBoxToPictureTwoOptionsXCoordinate,  DragTextBoxToPictureTwoOptionsYCoordinate;
    int  dragToPostionX,  dragToPostionY;
    int whichTextviewWillDrag;
    Boolean ordinaryMCQ;
    MediaPlayer mp;
    boolean demoStarted=false;
    String checkLang;
    */

    String op1, op2, ans;

    public MathsMcq(int testId, TestJsonParseModel test, Context context, Activity activity) {
        this.testId = testId;
        this.test = test;
        mContext = context;
        this.activity = activity;
        Log.wtf("maths_mcq", " TEST ID : " + testId);
        Log.wtf("maths_mcq", " Question : " + test.getQuestion().getText());
        Log.wtf("maths_mcq", " Answer: " + test.getOptionList().get(0).getText());

    }

    public MathsMcq() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.maths_mcq, container, false);
        player = ExoPlayerFactory.newSimpleInstance(getContext());
        sharedPrefs = getActivity().getApplicationContext().getSharedPreferences("DefaultSPForTaleemabadGrade6", 0);
        appAnalytics=new AppAnalytics(getContext());
        editor = sharedPrefs.edit();
        initializeView(view);
        setOnClickListeners(view);


        return view;
    }

    public void initializeView(View view) {

        mathsMcqLayout1x1 = view.findViewById(R.id.mathsMcqLayout1x1);
        mathsMcqLayout1x2 = view.findViewById(R.id.mathsMcqLayout1x2);
        mathsMcqLayout1x3 = view.findViewById(R.id.mathsMcqLayout1x3);
        mathsMcqLayout1x4 = view.findViewById(R.id.mathsMcqLayout1x4);
        mathsMcqLayout1x5 = view.findViewById(R.id.mathsMcqLayout1x5);

        mathsMcqLayout2x1 = view.findViewById(R.id.mathsMcqLayout2x1);
        mathsMcqLayout2x2 = view.findViewById(R.id.mathsMcqLayout2x2);
        mathsMcqLayout2x3 = view.findViewById(R.id.mathsMcqLayout2x3);
        mathsMcqLayout2x4 = view.findViewById(R.id.mathsMcqLayout2x4);
        mathsMcqLayout2x5 = view.findViewById(R.id.mathsMcqLayout2x5);

        mathsMcqLayout3x1 = view.findViewById(R.id.mathsMcqLayout3x1);
        mathsMcqLayout3x2 = view.findViewById(R.id.mathsMcqLayout3x2);
        mathsMcqLayout3x3 = view.findViewById(R.id.mathsMcqLayout3x3);
        mathsMcqLayout3x4 = view.findViewById(R.id.mathsMcqLayout3x4);
        mathsMcqLayout3x5 = view.findViewById(R.id.mathsMcqLayout3x5);

        mathsMcqLayout4x1 = view.findViewById(R.id.mathsMcqLayout4x1);
        mathsMcqLayout4x2 = view.findViewById(R.id.mathsMcqLayout4x2);
        mathsMcqLayout4x3 = view.findViewById(R.id.mathsMcqLayout4x3);
        mathsMcqLayout4x4 = view.findViewById(R.id.mathsMcqLayout4x4);
        mathsMcqLayout4x5 = view.findViewById(R.id.mathsMcqLayout4x5);

        mathsMcqTextView1x1 = view.findViewById(R.id.mathsMcqTextView1x1);
        mathsMcqTextView1x2 = view.findViewById(R.id.mathsMcqTextView1x2);
        mathsMcqTextView1x3 = view.findViewById(R.id.mathsMcqTextView1x3);
        mathsMcqTextView1x4 = view.findViewById(R.id.mathsMcqTextView1x4);
        mathsMcqTextView1x5 = view.findViewById(R.id.mathsMcqTextView1x5);

        mathsMcqTextView2x1 = view.findViewById(R.id.mathsMcqTextView2x1);
        mathsMcqTextView2x2 = view.findViewById(R.id.mathsMcqTextView2x2);
        mathsMcqTextView2x3 = view.findViewById(R.id.mathsMcqTextView2x3);
        mathsMcqTextView2x4 = view.findViewById(R.id.mathsMcqTextView2x4);
        mathsMcqTextView2x5 = view.findViewById(R.id.mathsMcqTextView2x5);

        mathsMcqTextView3x1 = view.findViewById(R.id.mathsMcqTextView3x1);
        mathsMcqTextView3x2 = view.findViewById(R.id.mathsMcqTextView3x2);
        mathsMcqTextView3x3 = view.findViewById(R.id.mathsMcqTextView3x3);
        mathsMcqTextView3x4 = view.findViewById(R.id.mathsMcqTextView3x4);
        mathsMcqTextView3x5 = view.findViewById(R.id.mathsMcqTextView3x5);

        mathsMcqTextView4x1 = view.findViewById(R.id.mathsMcqTextView4x1);
        mathsMcqTextView4x2 = view.findViewById(R.id.mathsMcqTextView4x2);
        mathsMcqTextView4x3 = view.findViewById(R.id.mathsMcqTextView4x3);
        mathsMcqTextView4x4 = view.findViewById(R.id.mathsMcqTextView4x4);
        mathsMcqTextView4x5 = view.findViewById(R.id.mathsMcqTextView4x5);

        mathsMcqTextViewButton1x1 = view.findViewById(R.id.mathsMcqTextViewButton1x1);
        mathsMcqTextViewButton1x2 = view.findViewById(R.id.mathsMcqTextViewButton1x2);
        mathsMcqTextViewButton2x1 = view.findViewById(R.id.mathsMcqTextViewButton2x1);
        mathsMcqTextViewButton2x2 = view.findViewById(R.id.mathsMcqTextViewButton2x2);
        questionTextViewQuestion = view.findViewById(R.id.questionTextViewQuestion);

        //mainConstraintLayout =view.findViewById(R.id.mainConstraintLayout);
        fillUpTest();
        setTable();

        ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {


                    mathsMcqTextViewButton1x1.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    int height = mathsMcqTextViewButton1x1.getHeight();
                    //  int height = mathsAdditionTextView1.getWidth();
                    // int widthButton = mathsMcqTextViewButton1x1.getWidth();

                    ViewGroup.LayoutParams params = mathsMcqTextViewButton1x1.getLayoutParams();
                    params.width = height;
                    params.height = height;
                    mathsMcqTextViewButton1x1.setLayoutParams(params);

                    params = mathsMcqTextViewButton1x2.getLayoutParams();
                    params.width = height;
                    params.height = height;
                    mathsMcqTextViewButton1x2.setLayoutParams(params);

                    params = mathsMcqTextViewButton2x1.getLayoutParams();
                    params.width = height;
                    params.height = height;
                    mathsMcqTextViewButton2x1.setLayoutParams(params);

                    params = mathsMcqTextViewButton2x2.getLayoutParams();
                    params.width = height;
                    params.height = height;
                    mathsMcqTextViewButton2x2.setLayoutParams(params);

                    mathsMcqTextView1x1.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    int width1 = mathsMcqTextView1x1.getWidth();

                    ViewGroup.LayoutParams params1 = mathsMcqTextView1x1.getLayoutParams();
                    params1.width = width1;
                    params1.height = width1;
                    mathsMcqTextView1x1.setLayoutParams(params1);

                    params1 = mathsMcqTextView1x2.getLayoutParams();
                    params1.width = width1;
                    params1.height = width1;
                    mathsMcqTextView1x2.setLayoutParams(params1);

                    params1 = mathsMcqTextView1x3.getLayoutParams();
                    params1.width = width1;
                    params1.height = width1;
                    mathsMcqTextView1x3.setLayoutParams(params1);

                    params1 = mathsMcqTextView1x4.getLayoutParams();
                    params1.width = width1;
                    params1.height = width1;
                    mathsMcqTextView1x4.setLayoutParams(params1);

                    params1 = mathsMcqTextView1x5.getLayoutParams();
                    params1.width = width1;
                    params1.height = width1;
                    mathsMcqTextView1x5.setLayoutParams(params1);

                    params1 = mathsMcqTextView2x1.getLayoutParams();
                    params1.width = width1;
                    params1.height = width1;
                    mathsMcqTextView2x1.setLayoutParams(params1);

                    params1 = mathsMcqTextView2x2.getLayoutParams();
                    params1.width = width1;
                    params1.height = width1;
                    mathsMcqTextView2x2.setLayoutParams(params1);

                    params1 = mathsMcqTextView2x3.getLayoutParams();
                    params1.width = width1;
                    params1.height = width1;
                    mathsMcqTextView2x3.setLayoutParams(params1);

                    params1 = mathsMcqTextView2x4.getLayoutParams();
                    params1.width = width1;
                    params1.height = width1;
                    mathsMcqTextView2x4.setLayoutParams(params1);

                    params1 = mathsMcqTextView2x5.getLayoutParams();
                    params1.width = width1;
                    params1.height = width1;
                    mathsMcqTextView2x5.setLayoutParams(params1);

                    params1 = mathsMcqTextView3x1.getLayoutParams();
                    params1.width = width1;
                    params1.height = width1;
                    mathsMcqTextView3x1.setLayoutParams(params1);

                    params1 = mathsMcqTextView3x2.getLayoutParams();
                    params1.width = width1;
                    params1.height = width1;
                    mathsMcqTextView3x2.setLayoutParams(params1);

                    params1 = mathsMcqTextView3x3.getLayoutParams();
                    params1.width = width1;
                    params1.height = width1;
                    mathsMcqTextView3x3.setLayoutParams(params1);

                    params1 = mathsMcqTextView3x4.getLayoutParams();
                    params1.width = width1;
                    params1.height = width1;
                    mathsMcqTextView3x4.setLayoutParams(params1);

                    params1 = mathsMcqTextView3x5.getLayoutParams();
                    params1.width = width1;
                    params1.height = width1;
                    mathsMcqTextView3x5.setLayoutParams(params1);

                    params1 = mathsMcqTextView4x1.getLayoutParams();
                    params1.width = width1;
                    params1.height = width1;
                    mathsMcqTextView4x1.setLayoutParams(params1);

                    params1 = mathsMcqTextView4x2.getLayoutParams();
                    params1.width = width1;
                    params1.height = width1;
                    mathsMcqTextView4x2.setLayoutParams(params1);

                    params1 = mathsMcqTextView4x3.getLayoutParams();
                    params1.width = width1;
                    params1.height = width1;
                    mathsMcqTextView2x3.setLayoutParams(params1);

                    params1 = mathsMcqTextView4x4.getLayoutParams();
                    params1.width = width1;
                    params1.height = width1;
                    mathsMcqTextView4x4.setLayoutParams(params1);

                    params1 = mathsMcqTextView4x5.getLayoutParams();
                    params1.width = width1;
                    params1.height = width1;
                    mathsMcqTextView4x5.setLayoutParams(params1);
                }
            });
        }
        //To retrieve
//        SharedPreferences sharedPrefForChecking = getActivity().getSharedPreferences("DemoMathsMCQ", DEMO_MODE);
//        ordinaryMCQ = sharedPrefForChecking.getBoolean("MathsMCQ", false); //0 is the default value
//
//        setUpDemo();
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

            //   SimpleTestActivity.testActivityImageViewfeedback.setVisibility(View.GONE);

//                setOnClickListenr(view);
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
            demoStarted=false;
        }


    }

     */
    /*
    public void startDemo(){


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
      //  mp.setLooping(true);
        mp.start();
        demoStarted =true;

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();

            }
        });

        String anstext = test.getOptionList().get(0).getText();

//        Toast.makeText(getContext(),"1: "+DragTextBoxToPictureTwoOptionsXCoordinate, Toast.LENGTH_LONG).show();
//        Toast.makeText(getContext(),"2: "+DragTextBoxToPictureTwoOptionsYCoordinate, Toast.LENGTH_LONG).show();

        String option1Text = mathsMcqTextViewButton1x1.getText().toString();
        String option2Text = mathsMcqTextViewButton1x2.getText().toString();
        String option3Text = mathsMcqTextViewButton2x1.getText().toString();
        String option4Text = mathsMcqTextViewButton2x1.getText().toString();

        float option1  = Float.parseFloat(option1Text);
        float option2  = Float.parseFloat(option2Text);
        float option3  = Float.parseFloat(option3Text);
        float option4  = Float.parseFloat(option4Text);

        if (ans.equals("_")){
            int operand1 = Integer.parseInt(op1);
            int operand2 = Integer.parseInt(op2);

            if (operator.equalsIgnoreCase("x")){
                int result = operand1*operand2;

                mathsMcqTextView1x5.setVisibility(View.VISIBLE);
                if(result==option1){

                    generateHand(1, false, false);
                }else if (result==option2){
                    generateHand(2,false, false);

                }else if (result==option3){
                    generateHand(3,false, false);
                }else if (result==option4){
                    generateHand(4,false, false);
                }

                final Handler handlerr = new Handler();
                handlerr.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        moveto(handImageview);

                    }
                }, 1200);

            }
        }else if (op1.equals("_") || op2.equals("_")){
            if (op1.equals("_")){
                if (operator.equalsIgnoreCase("x")){
                    int operand2 = Integer.parseInt(op2);

                    int intOption1 = (int)option1;
                    int intOption2 = (int)option2;
                    int intOption3 = (int)option3;
                    int intOption4 = (int)option4;

                    int result1 = operand2 * intOption1;
                    int result2 = operand2 * intOption2;
                    int result3 = operand2 * intOption3;
                    int result4 = operand2 * intOption4;

                    int answer = Integer.parseInt(ans);

                    if (result1==answer){
                        generateHand(1, true, false);
                    }else if (result2==answer){
                        generateHand(2,true, false);

                    }else if (result3==answer){
                        generateHand(3,true, false);

                    }else if (result4==answer){
                        generateHand(4,true, false);

                    }

                }else {
                }
            }else if (op2.equals("_")){
                if (operator.equalsIgnoreCase("x")){
                    int operand1 = Integer.parseInt(op1);

                    int intOption1 = (int)option1;
                    int intOption2 = (int)option2;
                    int intOption3 = (int)option3;
                    int intOption4 = (int)option4;

                    int result1 = operand1 * intOption1;
                    int result2 = operand1 * intOption2;
                    int result3 = operand1 * intOption3;
                    int result4 = operand1 * intOption4;

                    int answer = Integer.parseInt(ans);

                    if (result1==answer){
                        generateHand(1, false, true);
                    }else if (result2==answer){
                        generateHand(2,false,true);

                    }else if (result3==answer){
                        generateHand(3, false,true);

                    }else if (result4==answer){
                        generateHand(4, false,true);

                    }

                }else {
                }
            }
        }


//               answerTextViews.get(0).getText().toString().equalsIgnoreCase(list.get(0).getOption())
    }

     */

    /*
    public void generateHand(int atOption, boolean moveToOperand1, boolean moveToOperand2 ){
        if(atOption==1){
            final ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(mainConstraintLayout);
            handImageview = new ImageView(activity);

            handImageview.setImageResource(R.drawable.hand);
            handImageview.setId(ViewCompat.generateViewId());

            mainConstraintLayout.addView(handImageview);


            constraintSet.connect(handImageview.getId(), ConstraintSet.TOP, R.id.guidelineButton1x1Top,ConstraintSet.TOP,10);
            constraintSet.connect(handImageview.getId(),ConstraintSet.END, R.id.guidelineButton1x1Right,ConstraintSet.START,10);
            constraintSet.connect(handImageview.getId(),ConstraintSet.START, R.id.guidelineButton1x1Left,ConstraintSet.END,10);
            constraintSet.connect(handImageview.getId(),ConstraintSet.BOTTOM, R.id.guidelineButton1x1Bottom, ConstraintSet.TOP,10);
            constraintSet.applyTo(mainConstraintLayout);

            handImageview.getLayoutParams().width = mathsMcqTextViewButton1x1.getMeasuredHeight()/2;
            handImageview.getLayoutParams().height = mathsMcqTextViewButton1x1.getMeasuredHeight()/2;
            handImageview.bringToFront();

            int[] location = new int[2];
            mathsMcqTextViewButton1x1.getLocationOnScreen(location);
            DragTextBoxToPictureTwoOptionsXCoordinate = location[0];
            DragTextBoxToPictureTwoOptionsYCoordinate = location[1];


            if (moveToOperand1){
                int[] location1 = new int[2];
                mathsMcqTextView1x1.getLocationOnScreen(location1);
                dragToPostionX = location1[0];
                dragToPostionY = location1[1];

            }else if (moveToOperand2){
                int[] location1 = new int[2];
                mathsMcqTextView1x3.getLocationOnScreen(location1);
                dragToPostionX = location1[0];
                dragToPostionY = location1[1];

            }else {
                int[] location1 = new int[2];
                mathsMcqTextView1x5.getLocationOnScreen(location1);
                dragToPostionX = location1[0];
                dragToPostionY = location1[1];

            }

            whichTextviewWillDrag =1;

        }else if (atOption==2){
            final ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(mainConstraintLayout);
            handImageview = new ImageView(activity);

            handImageview.setImageResource(R.drawable.hand);
            handImageview.setId(ViewCompat.generateViewId());

            mainConstraintLayout.addView(handImageview);

            constraintSet.connect(handImageview.getId(), ConstraintSet.TOP, R.id.guidelineButton1x1Top,ConstraintSet.TOP,10);
            constraintSet.connect(handImageview.getId(),ConstraintSet.END, R.id.guidelineButton1x2Right,ConstraintSet.START,10);
            constraintSet.connect(handImageview.getId(),ConstraintSet.START, R.id.guidelineButton1x2Left,ConstraintSet.END,10);
            constraintSet.connect(handImageview.getId(),ConstraintSet.BOTTOM, R.id.guidelineButton1x1Bottom,ConstraintSet.TOP,10);
            constraintSet.applyTo(mainConstraintLayout);

            handImageview.getLayoutParams().width = mathsMcqTextViewButton1x1.getMeasuredHeight()/2;
            handImageview.getLayoutParams().height = mathsMcqTextViewButton1x1.getMeasuredHeight()/2;
            handImageview.bringToFront();

            int[] location = new int[2];
            mathsMcqTextViewButton1x2.getLocationOnScreen(location);
            DragTextBoxToPictureTwoOptionsXCoordinate = location[0];
            DragTextBoxToPictureTwoOptionsYCoordinate = location[1];



            if (moveToOperand1){
                int[] location1 = new int[2];
                mathsMcqTextView1x1.getLocationOnScreen(location1);
                dragToPostionX = location1[0];
                dragToPostionY = location1[1];

            }else if (moveToOperand2){
                int[] location1 = new int[2];
                mathsMcqTextView1x3.getLocationOnScreen(location1);
                dragToPostionX = location1[0];
                dragToPostionY = location1[1];

            }else {
                int[] location1 = new int[2];
                mathsMcqTextView1x5.getLocationOnScreen(location1);
                dragToPostionX = location1[0];
                dragToPostionY = location1[1];

            }

            whichTextviewWillDrag =2;

        }else if (atOption==3){
            final ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(mainConstraintLayout);
            handImageview = new ImageView(activity);

            handImageview.setImageResource(R.drawable.hand);
            handImageview.setId(ViewCompat.generateViewId());

            mainConstraintLayout.addView(handImageview);

            constraintSet.connect(handImageview.getId(), ConstraintSet.TOP, R.id.guidelineButton2x1Top,ConstraintSet.TOP,10);
            constraintSet.connect(handImageview.getId(),ConstraintSet.END, R.id.guidelineButton1x1Right,ConstraintSet.START,10);
            constraintSet.connect(handImageview.getId(),ConstraintSet.START, R.id.guidelineButton1x1Left,ConstraintSet.END,10);
            constraintSet.connect(handImageview.getId(),ConstraintSet.BOTTOM, R.id.guidelineButton2x1Bottom,ConstraintSet.TOP,10);
            constraintSet.applyTo(mainConstraintLayout);

            handImageview.getLayoutParams().width = mathsMcqTextViewButton1x1.getMeasuredHeight()/2;
            handImageview.getLayoutParams().height = mathsMcqTextViewButton1x1.getMeasuredHeight()/2;
            handImageview.bringToFront();

            int[] location = new int[2];
            mathsMcqTextViewButton2x1.getLocationOnScreen(location);
            DragTextBoxToPictureTwoOptionsXCoordinate = location[0];
            DragTextBoxToPictureTwoOptionsYCoordinate = location[1];


            if (moveToOperand1){
                int[] location1 = new int[2];
                mathsMcqTextView1x1.getLocationOnScreen(location1);
                dragToPostionX = location1[0];
                dragToPostionY = location1[1];

            }else if (moveToOperand2){
                int[] location1 = new int[2];
                mathsMcqTextView1x3.getLocationOnScreen(location1);
                dragToPostionX = location1[0];
                dragToPostionY = location1[1];

            }else {
                int[] location1 = new int[2];
                mathsMcqTextView1x5.getLocationOnScreen(location1);
                dragToPostionX = location1[0];
                dragToPostionY = location1[1];

            }


            whichTextviewWillDrag =3;
        }else if (atOption==4){
            final ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(mainConstraintLayout);
            handImageview = new ImageView(activity);

            handImageview.setImageResource(R.drawable.hand);
            handImageview.setId(ViewCompat.generateViewId());

            mainConstraintLayout.addView(handImageview);

            constraintSet.connect(handImageview.getId(), ConstraintSet.TOP, R.id.guidelineButton2x1Top,ConstraintSet.TOP,10);
            constraintSet.connect(handImageview.getId(),ConstraintSet.END, R.id.guidelineButton1x2Right,ConstraintSet.START,10);
            constraintSet.connect(handImageview.getId(),ConstraintSet.START, R.id.guidelineButton1x2Left,ConstraintSet.END,10);
            constraintSet.connect(handImageview.getId(),ConstraintSet.BOTTOM, R.id.guidelineButton2x1Bottom,ConstraintSet.TOP,10);
            constraintSet.applyTo(mainConstraintLayout);

            handImageview.getLayoutParams().width = mathsMcqTextViewButton1x1.getMeasuredHeight()/2;
            handImageview.getLayoutParams().height = mathsMcqTextViewButton1x1.getMeasuredHeight()/2;
            handImageview.bringToFront();

            int[] location = new int[2];
            mathsMcqTextViewButton2x2.getLocationOnScreen(location);
            DragTextBoxToPictureTwoOptionsXCoordinate = location[0];
            DragTextBoxToPictureTwoOptionsYCoordinate = location[1];


            if (moveToOperand1){
                int[] location1 = new int[2];
                mathsMcqTextView1x1.getLocationOnScreen(location1);
                dragToPostionX = location1[0];
                dragToPostionY = location1[1];

            }else if (moveToOperand2){
                int[] location1 = new int[2];
                mathsMcqTextView1x3.getLocationOnScreen(location1);
                dragToPostionX = location1[0];
                dragToPostionY = location1[1];


            }else {
                int[] location1 = new int[2];
                mathsMcqTextView1x5.getLocationOnScreen(location1);
                dragToPostionX = location1[0];
                dragToPostionY = location1[1];


            }
            whichTextviewWillDrag =4;
        }
    }

     */
    /*
    public void moveto(final ImageView imageview){
        imageview.setVisibility(View.VISIBLE);
        int[] location1 = new int[2];
        //textViewList.get(0).getTextView().getLocationOnScreen(location1);


        if (whichTextviewWillDrag==1){
            mathsMcqTextViewButton1x1.animate()
                    .translationX(60)
                    .alpha(0f)
                    .setDuration(500)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mathsMcqTextViewButton1x1.animate()
                                    .translationX(0)
                                    .alpha(1.0f)
                                    .setDuration(100)
                                    .setListener(new AnimatorListenerAdapter() {
                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            super.onAnimationEnd(animation);
                                        }
                                    });

                        }
                    });

        }else if (whichTextviewWillDrag==2){
            mathsMcqTextViewButton1x2.animate()
                    .translationX(60)
                    .alpha(1.0f)
                    .setDuration(500)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mathsMcqTextViewButton1x2.animate()
                                    .translationX(0)
                                    .alpha(1.0f)
                                    .setDuration(100)
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
                .x(dragToPostionX)
                .y(dragToPostionY)
                .alpha(1.0f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(500)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        // animateup();
                        if (answerCount!=0){
                            handImageview.setVisibility(View.GONE);
                            return;
                        }
                        final Handler handlerr = new Handler();
                        handlerr.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                imageview.animate()
                                        .x(dragToPostionX-1)
                                        .y(dragToPostionY)
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

                        //movetoback(imageview);
                    }
                });
    }

     */

    /*
    public void movetoback(final ImageView imageview){
        imageview.setVisibility(View.INVISIBLE);


        imageview.bringToFront();
        imageview.animate()
                .x(DragTextBoxToPictureTwoOptionsXCoordinate)
                .y(DragTextBoxToPictureTwoOptionsYCoordinate)
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(500)
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

    public void fillUpTest() {
        if (test.getQuestion() != null) {
            //   questionTextViewQuestion.setText(String.valueOf(test.getQuestion().getText()));
        }
        Log.wtf("math_data", "fill up test");
        String question = test.getQuestion().getText();
        String lines[] = question.split("\\r?\\n");
        String line1 = lines[0];
        String line2 = lines[1];
        String line3 = lines[2];
        String line4 = lines[3];
        Log.wtf("info_", "line 1" + line1);
        Log.wtf("math_data", "line 1:" + line1);
        if (line1.contains("x") || line1.contains("-") || line1.contains("+")) {

            if (line1.contains("x")) {
                operator = "x";
            } else if (line1.contains("-")) {
                operator = "-";
            } else if (line1.contains("+")) {
                operator = String.valueOf("+");
            }
            String[] data1 = line1.split(Pattern.quote(operator));
            op1 = data1[0];
            String lineval2 = data1[1];
            if (lineval2.contains("=")) {
                String[] data11 = lineval2.split("=");
                op2 = data11[0];
                ans = data11[1];
                Log.wtf("math_data", "option 1 : " + op1);
                Log.wtf("math_data", "option 2 : " + op2);
                Log.wtf("math_data", "option 4 : " + ans);
                if (op1.equalsIgnoreCase("_")) {
                    operand1 = null;
                } else {
                    operand1 = op1;
                }

                if (op2.equalsIgnoreCase("_")) {
                    operand2 = null;
                } else {
                    operand2 = op2;
                }
                if (ans.equalsIgnoreCase("_")) {
                    answer = null;
                } else {
                    answer = ans;
                }

                list.add(new MathsMCQModel(operand1, operand2, operator, answer, test.getOptionList().get(0).getText()));

            }
        }
        Log.wtf("math_data", "line 2:" + line2);

        if (line2.contains("x") || line2.contains("-") | line2.contains("+")) {

            if (line2.contains("x")) {
                operator = "x";
            } else if (line2.contains("+")) {
                operator = String.valueOf("+");
            } else if (line2.contains("_")) {
                operator = "-";
            }
            String[] data1 = line2.split(Pattern.quote(operator));
            op1 = data1[0];
            String lineval2 = data1[1];
            if (lineval2.contains("=")) {
                String[] data11 = lineval2.split("=");
                op2 = data11[0];
                ans = data11[1];
                Log.wtf("math_data", "option 1 : " + op1);
                Log.wtf("math_data", "option 2 : " + op2);
                Log.wtf("math_data", "option 4 : " + ans);
                if (op1.equalsIgnoreCase("_")) {
                    operand1 = null;
                } else {
                    operand1 = op1;
                }

                if (op2.equalsIgnoreCase("_")) {
                    operand2 = null;
                } else {
                    operand2 = op2;
                }
                if (ans.equalsIgnoreCase("_")) {
                    answer = null;
                } else {
                    answer = ans;
                }

                list.add(new MathsMCQModel(operand1, operand2, operator, answer, test.getOptionList().get(1).getText()));

            }
        }

        Log.wtf("math_data", "line 3:" + line3);

        if (line3.contains("x") || line3.contains("+") || line3.contains("-")) {


            if (line3.contains("x")) {
                operator = "x";
            } else if (line3.contains("+")) {
                operator = String.valueOf("+");
            } else if (line3.contains("_")) {
                operator = "-";
            }
            String[] data1 = line3.split(Pattern.quote(operator));
            op1 = data1[0];
            String lineval2 = data1[1];
            if (lineval2.contains("=")) {
                String[] data11 = lineval2.split("=");
                op2 = data11[0];
                ans = data11[1];
                Log.wtf("math_data", "option 1 : " + op1);
                Log.wtf("math_data", "option 2 : " + op2);
                Log.wtf("math_data", "option 4 : " + ans);

                if (op1.equalsIgnoreCase("_")) {
                    operand1 = null;
                } else {
                    operand1 = op1;
                }

                if (op2.equalsIgnoreCase("_")) {
                    operand2 = null;
                } else {
                    operand2 = op2;
                }
                if (ans.equalsIgnoreCase("_")) {
                    answer = null;
                } else {
                    answer = ans;
                }

                list.add(new MathsMCQModel(operand1, operand2, operator, answer, test.getOptionList().get(2).getText()));

            }
        }

        Log.wtf("math_data", "line 4:" + line4);


        if (line4.contains("x") || line4.contains("+") || line4.contains("-")) {

            if (line4.contains("x")) {
                operator = "x";
            } else if (line4.contains("+")) {
                operator = String.valueOf("+");
            } else if (line4.contains("_")) {
                operator = "-";
            }
            String[] data1 = line4.split(Pattern.quote(operator));
            op1 = data1[0];
            String lineval2 = data1[1];
            if (lineval2.contains("=")) {
                String[] data11 = lineval2.split("=");
                op2 = data11[0];
                ans = data11[1];
                Log.wtf("math_data", "option 1 : " + op1);
                Log.wtf("math_data", "option 2 : " + op2);
                Log.wtf("math_data", "option 4 : " + ans);
                if (op1.equalsIgnoreCase("_")) {
                    operand1 = null;
                } else {
                    operand1 = op1;
                }

                if (op2.equalsIgnoreCase("_")) {
                    operand2 = null;
                } else {
                    operand2 = op2;
                }
                if (ans.equalsIgnoreCase("_")) {
                    answer = null;
                } else {
                    answer = ans;
                }

                list.add(new MathsMCQModel(operand1, operand2, operator, answer, test.getOptionList().get(3).getText()));

            }
        }

        Log.wtf("math_mcq_data", "question 1 : " + line1);
        Log.wtf("math_mcq_data", "question 2 : " + line2);
        Log.wtf("math_mcq_data", "question 3 : " + line3);
        Log.wtf("math_mcq_data", "question 4 : " + line4);
    }

    public void setTable() {
        startTestTimer();
        // Setting First Row
        if (list.get(0).getOperand1() != null) {
            mathsMcqTextView1x1.setText(list.get(0).getOperand1());
        } else {
            mathsMcqTextView1x1.setText(list.get(0).getOption());
            mathsMcqTextView1x1.setTextColor(Color.TRANSPARENT);
            mathsMcqTextView1x1.setBackgroundResource(R.drawable.maths_mcq_blank_roundbg);
            mathsMcqTextView1x1.setOnDragListener(this);
            answerTextViews.add(mathsMcqTextView1x1);
            mathsMcqTextView1x1.setPadding(15, 0, 20, 10);

        }

        mathsMcqTextView1x2.setText(list.get(0).getOperator());

        if (list.get(0).getOperand2() != null) {
            mathsMcqTextView1x3.setText(list.get(0).getOperand2());
        } else {
            mathsMcqTextView1x3.setText(list.get(0).getOption());
            mathsMcqTextView1x3.setTextColor(Color.TRANSPARENT);
            mathsMcqTextView1x3.setBackgroundResource(R.drawable.maths_mcq_blank_roundbg);
            mathsMcqTextView1x3.setOnDragListener(this);
            answerTextViews.add(mathsMcqTextView1x3);
            mathsMcqTextView1x3.setPadding(15, 0, 20, 10);
        }

        if (list.get(0).getAnswer() != null) {
            mathsMcqTextView1x5.setText(list.get(0).getAnswer());
        } else {
            mathsMcqTextView1x5.setText(list.get(0).getOption());
            mathsMcqTextView1x5.setTextColor(Color.TRANSPARENT);
            mathsMcqTextView1x5.setBackgroundResource(R.drawable.maths_mcq_blank_roundbg);
            mathsMcqTextView1x5.setOnDragListener(this);
            mathsMcqTextView1x5.setPadding(15, 10, 20, 10);
            answerTextViews.add(mathsMcqTextView1x5);
        }

        // Setting Second Row
        if (list.get(1).getOperand1() != null) {
            mathsMcqTextView2x1.setText(list.get(1).getOperand1());
        } else {
            mathsMcqTextView2x1.setText(list.get(1).getOption());
            mathsMcqTextView2x1.setTextColor(Color.TRANSPARENT);
            mathsMcqTextView2x1.setBackgroundResource(R.drawable.maths_mcq_blank_roundbg);
            mathsMcqTextView2x1.setOnDragListener(this);
            mathsMcqTextView2x1.setPadding(15, 10, 20, 10);
            answerTextViews.add(mathsMcqTextView2x1);
        }

        mathsMcqTextView2x2.setText(list.get(1).getOperator());

        if (list.get(1).getOperand2() != null) {
            mathsMcqTextView2x3.setText(list.get(1).getOperand2());
        } else {
            mathsMcqTextView2x3.setText(list.get(1).getOption());
            mathsMcqTextView2x3.setTextColor(Color.TRANSPARENT);
            mathsMcqTextView2x3.setBackgroundResource(R.drawable.maths_mcq_blank_roundbg);
            mathsMcqTextView2x3.setOnDragListener(this);
            mathsMcqTextView2x3.setPadding(15, 10, 20, 10);
            answerTextViews.add(mathsMcqTextView2x3);
        }

        if (list.get(1).getAnswer() != null) {
            mathsMcqTextView2x5.setText(list.get(1).getAnswer());
        } else {
            mathsMcqTextView2x5.setText(list.get(1).getOption());
            mathsMcqTextView2x5.setTextColor(Color.TRANSPARENT);
            mathsMcqTextView2x5.setBackgroundResource(R.drawable.maths_mcq_blank_roundbg);
            mathsMcqTextView2x5.setOnDragListener(this);
            mathsMcqTextView2x5.setPadding(15, 10, 20, 10);
            answerTextViews.add(mathsMcqTextView2x5);
        }
        // Setting Third Row
        if (list.get(2).getOperand1() != null) {
            mathsMcqTextView3x1.setText(list.get(2).getOperand1());
        } else {
            mathsMcqTextView3x1.setText(list.get(2).getOption());
            mathsMcqTextView3x1.setTextColor(Color.TRANSPARENT);
            mathsMcqTextView3x1.setBackgroundResource(R.drawable.maths_mcq_blank_roundbg);
            mathsMcqTextView3x1.setOnDragListener(this);
            mathsMcqTextView3x1.setPadding(15, 10, 20, 10);
            answerTextViews.add(mathsMcqTextView3x1);
        }

        mathsMcqTextView3x2.setText(list.get(2).getOperator());

        if (list.get(2).getOperand2() != null) {
            mathsMcqTextView3x3.setText(list.get(2).getOperand2());
        } else {
            mathsMcqTextView3x3.setText(list.get(2).getOption());
            mathsMcqTextView3x3.setTextColor(Color.TRANSPARENT);
            mathsMcqTextView3x3.setBackgroundResource(R.drawable.maths_mcq_blank_roundbg);
            mathsMcqTextView3x3.setOnDragListener(this);
            mathsMcqTextView3x3.setPadding(15, 10, 20, 10);
            answerTextViews.add(mathsMcqTextView3x3);
        }

        if (list.get(2).getAnswer() != null) {
            mathsMcqTextView3x5.setText(list.get(2).getAnswer());
        } else {
            mathsMcqTextView3x5.setText(list.get(2).getOption());
            mathsMcqTextView3x5.setTextColor(Color.TRANSPARENT);
            mathsMcqTextView3x5.setBackgroundResource(R.drawable.maths_mcq_blank_roundbg);
            mathsMcqTextView3x5.setOnDragListener(this);
            mathsMcqTextView3x5.setPadding(15, 10, 20, 10);
            answerTextViews.add(mathsMcqTextView3x5);
        }

        // Setting Fourth Row
        if (list.get(3).getOperand1() != null) {
            mathsMcqTextView4x1.setText(list.get(3).getOperand1());
        } else {
            mathsMcqTextView4x1.setText(list.get(3).getOption());
            mathsMcqTextView4x1.setTextColor(Color.TRANSPARENT);
            mathsMcqTextView4x1.setBackgroundResource(R.drawable.maths_mcq_blank_roundbg);
            mathsMcqTextView4x1.setOnDragListener(this);
            mathsMcqTextView4x1.setPadding(15, 10, 20, 10);
            answerTextViews.add(mathsMcqTextView4x1);
        }

        mathsMcqTextView4x2.setText(list.get(3).getOperator());

        if (list.get(3).getOperand2() != null) {
            mathsMcqTextView4x3.setText(list.get(3).getOperand2());
        } else {
            mathsMcqTextView4x3.setText(list.get(3).getOption());
            mathsMcqTextView4x3.setTextColor(Color.TRANSPARENT);
            mathsMcqTextView4x3.setBackgroundResource(R.drawable.maths_mcq_blank_roundbg);
            mathsMcqTextView4x3.setOnDragListener(this);
            mathsMcqTextView4x3.setPadding(15, 10, 20, 10);
            answerTextViews.add(mathsMcqTextView4x3);
        }

        if (list.get(3).getAnswer() != null) {
            mathsMcqTextView4x5.setText(list.get(3).getAnswer());
        } else {
            mathsMcqTextView4x5.setText(list.get(3).getOption());
            mathsMcqTextView4x5.setTextColor(Color.TRANSPARENT);
            mathsMcqTextView4x5.setBackgroundResource(R.drawable.maths_mcq_blank_roundbg);
            mathsMcqTextView4x5.setOnDragListener(this);
            mathsMcqTextView4x5.setPadding(15, 10, 20, 10);
            answerTextViews.add(mathsMcqTextView4x5);
        }

        ArrayList<Integer> numbers = new ArrayList<Integer>();
        Random randomGenerator = new Random();
        while (numbers.size() < 4) {

            int random = randomGenerator.nextInt(4);
            if (!numbers.contains(random)) {
                numbers.add(random);
            }
        }

        mathsMcqTextViewButton1x1.setText(test.getOptionList().get(0).getText());
        mathsMcqTextViewButton1x2.setText(test.getOptionList().get(1).getText());
        mathsMcqTextViewButton2x1.setText(test.getOptionList().get(2).getText());
        mathsMcqTextViewButton2x2.setText(test.getOptionList().get(3).getText());

    }

    public void setOnClickListeners(View view) {


        mathsMcqTextViewButton1x1.setOnTouchListener(this);
        mathsMcqTextViewButton1x2.setOnTouchListener(this);
        mathsMcqTextViewButton2x1.setOnTouchListener(this);
        mathsMcqTextViewButton2x2.setOnTouchListener(this);
    }

    @Override
    public void onClick(View v) {
//
//        if(v.getId() == mathsMcqTextViewPopUpClose.getId()){
//            mathsMcqPopUpLayout.setVisibility(View.INVISIBLE);
//            mathsMcqTextViewPopUpClose.setVisibility(View.INVISIBLE);
//        }

    }

    @Override
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
            selectedView.setOnDragListener(this);

            boolean asnwerPicked = false;
            for (int i = 0; i < answerTextViews.size(); i++) {
                if (answerTextViews.get(i).getId() == selectedView.getId()) {
                    asnwerPicked = true;
                }
            }
            if (asnwerPicked == true) {
                for (int i = 0; i < answerTextViews.size(); i++) {
                    answerTextViews.get(i).setOnDragListener(null);
                }
            } else {
                for (int i = 0; i < answerTextViews.size(); i++) {
                    answerTextViews.get(i).setOnDragListener(this);
                }
            }


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
                if (selectedView.getId() == mathsMcqTextViewButton1x1.getId() || selectedView.getId() == mathsMcqTextViewButton1x2.getId()
                        || selectedView.getId() == mathsMcqTextViewButton2x1.getId() || selectedView.getId() == mathsMcqTextViewButton2x2.getId()) {

                    if (txtView.getId() == mathsMcqTextViewButton1x1.getId() || txtView.getId() == mathsMcqTextViewButton1x2.getId()
                            || txtView.getId() == mathsMcqTextViewButton2x1.getId() || txtView.getId() == mathsMcqTextViewButton2x2.getId()) {
                        break;

                    } else {
                        answerCount++;
                        if (selectedView.getId() == mathsMcqTextViewButton1x1.getId()) {
                            String uidAudio = topic + "Question" + (tempIndex+1) + "Option" + 1 + "Audio";
                            setAudioDescription(uidAudio);
                        } else if (selectedView.getId() == mathsMcqTextViewButton1x2.getId()) {
                            String uidAudio = topic + "Question" + (tempIndex+1) + "Option" + 2 + "Audio";
                            setAudioDescription(uidAudio);
                        } else if (selectedView.getId() == mathsMcqTextViewButton2x1.getId()) {
                            String uidAudio = topic + "Question" + (tempIndex+1) + "Option" + 3 + "Audio";
                            setAudioDescription(uidAudio);
                        } else if (selectedView.getId() == mathsMcqTextViewButton2x2.getId()) {
                            String uidAudio = topic + "Question" + (tempIndex+1) + "Option" + 4 + "Audio";
                            setAudioDescription(uidAudio);
                        }
                        //  Toast.makeText(getContext(), " ButtonOption Inc: " + answerCount, Toast.LENGTH_SHORT).show();
                        Log.wtf("-drag", " ButtonOption Inc: " + answerCount);
                    }


                } else if (txtView.getId() == mathsMcqTextViewButton1x1.getId() || txtView.getId() == mathsMcqTextViewButton1x2.getId()
                        || txtView.getId() == mathsMcqTextViewButton2x1.getId() || txtView.getId() == mathsMcqTextViewButton2x2.getId()) {

                    answerCount--;
                    Log.wtf("-drag", " textView Dec : " + answerCount);


                    // Toast.makeText(getContext(), " textView Dec : " + answerCount, Toast.LENGTH_SHORT).show();

                } else {
                    Log.wtf("-drag", " textView Nothing : ");

                }

                selectedView.setBackgroundResource(R.drawable.maths_mcq_blank_roundbg);
                selectedView.setTextColor(Color.TRANSPARENT);
                selectedView.setOnTouchListener(null);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    selectedView.setForeground(null);
                }
                txtView.setOnTouchListener(this);
                txtView.setText(selectedData);
                txtView.setTextColor(getResources().getColor(R.color.white));
                txtView.setPadding(25, 25, 35, 35);
                txtView.setBackgroundResource(R.drawable.maths_bg);


                if (txtView.getId() == mathsMcqTextViewButton1x1.getId() || txtView.getId() == mathsMcqTextViewButton1x2.getId()
                        || txtView.getId() == mathsMcqTextViewButton2x1.getId() || txtView.getId() == mathsMcqTextViewButton2x2.getId()) {
                    txtView.setOnDragListener(null);

                }
                if (answerCount == 4) {
                    setResult();
                }
                // Toast.makeText(getContext(), " ButtonOption : " + selectedData, Toast.LENGTH_SHORT).show();

                break;
            case DragEvent.ACTION_DRAG_ENDED:
                //  Log.wtf("-drag", "Ended " + v.toString());
                // textViewAnswer1.setVisibility(View.VISIBLE);
                break;
            case DragEvent.ACTION_DRAG_LOCATION:
                // Log.wtf("-drag", "DRAG Location " + v.toString());

                // textViewAnswer1.setVisibility(View.VISIBLE);
            default:
                break;
        }
        return true;
    }

    public void setResult() {

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

        float sp = 10f;
        for (int i = 0; i < answerTextViews.size(); i++) {
            if (answerTextViews.get(i).getText().toString().equalsIgnoreCase(list.get(i).getOption())) {
                answerTextViews.get(i).setBackgroundResource(R.drawable.maths_green_button_round_bg);
                if(unSocratic == true) {
                    appAnalytics.setOptionSelected(selectedSubject, topic, test.getType(), answerTextViews.get(i).getText().toString(), true);
                    appAnalytics.setAnswer(selectedSubject, topic + "" + testIndex, topic, test.getType(), test.getAnswerList().size(), 1, true, this.count);
                }
                score = tScore;
                correctCount++;
                totalScore = totalScore + score;

            } else {
                answerTextViews.get(i).setBackgroundResource(R.drawable.maths_red_button_round_bg);
                if(unSocratic == true) {
                    appAnalytics.setOptionSelected(selectedSubject, topic, test.getType(), answerTextViews.get(i).getText().toString().toString(), false);
                    appAnalytics.setAnswer(selectedSubject, topic + "" + testIndex, topic, test.getType(), test.getAnswerList().size(), 0, false, this.count);
                }


            }
            // answerTextViews.get(i).setPadding(30,0,35,15);
            float temp = answerTextViews.get(i).getTextSize();
            if (temp < sp) {
                sp = temp;
            }
            Log.wtf("-this", "Temp " + i + " : " + temp + " || SP : " + sp);
            answerTextViews.get(i).setOnTouchListener(null);
            answerTextViews.get(i).setOnDragListener(null);
        }

        for (int i = 0; i < answerTextViews.size(); i++) {
            //  answerTextViews.get(i).setTextSize(sp);
        }

        mathsMcqTextViewButton1x1.setOnTouchListener(null);
        mathsMcqTextViewButton1x2.setOnTouchListener(null);
        mathsMcqTextViewButton2x1.setOnTouchListener(null);
        mathsMcqTextViewButton2x2.setOnTouchListener(null);


        final Handler handler = new Handler();
        final int finalScore = score;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ((SimpleTestActivity) activity).setExplanation(finalScore);
                if(getFragmentManager() != null) {
                    getFragmentManager().beginTransaction()
                            .remove(MathsMcq.this).commit();
                }

            }
        }, 1000);



    }

    public void setBackGrounds(String subject) {
        switch (subject) {
            case ("English"):
                // ordinaryMCQMainLayout.setBackgroundResource(R.mipmap.bg_pink);
                mathsMcqTextViewButton1x1.setBackgroundResource(R.drawable.english_bg);
                mathsMcqTextViewButton1x2.setBackgroundResource(R.drawable.english_bg);
                mathsMcqTextViewButton2x1.setBackgroundResource(R.drawable.english_bg);
                mathsMcqTextViewButton2x2.setBackgroundResource(R.drawable.english_bg);
                break;
            case ("Maths"):
                //   ordinaryMCQMainLayout.setBackgroundResource(R.mipmap.bg_green_b);
                mathsMcqTextViewButton1x1.setBackgroundResource(R.drawable.maths_bg);
                mathsMcqTextViewButton1x2.setBackgroundResource(R.drawable.maths_bg);
                mathsMcqTextViewButton2x1.setBackgroundResource(R.drawable.maths_bg);
                mathsMcqTextViewButton2x2.setBackgroundResource(R.drawable.maths_bg);
                break;

            case ("Urdu"):
                //    ordinaryMCQMainLayout.setBackgroundResource(R.mipmap.bg_purple);
                mathsMcqTextViewButton1x1.setBackgroundResource(R.drawable.urdu_bg);
                mathsMcqTextViewButton1x2.setBackgroundResource(R.drawable.urdu_bg);
                mathsMcqTextViewButton2x1.setBackgroundResource(R.drawable.urdu_bg);
                mathsMcqTextViewButton2x2.setBackgroundResource(R.drawable.urdu_bg);

                break;

            case ("GeneralKnowledge"):
                //    ordinaryMCQMainLayout.setBackgroundResource(R.mipmap.bg_blue);
                mathsMcqTextViewButton1x1.setBackgroundResource(R.drawable.science_bg);
                mathsMcqTextViewButton1x2.setBackgroundResource(R.drawable.science_bg);
                mathsMcqTextViewButton2x1.setBackgroundResource(R.drawable.science_bg);
                mathsMcqTextViewButton2x2.setBackgroundResource(R.drawable.science_bg);
                break;

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

    void setAudioDescription(String filName) {

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


        String url = streamUrl+filName+".mp3";
        Log.wtf("-thus","Audio URL MathsMCQ : "+url);
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
