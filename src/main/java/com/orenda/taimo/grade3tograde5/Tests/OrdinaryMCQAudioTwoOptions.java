package com.orenda.taimo.grade3tograde5.Tests;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
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

import com.orenda.taimo.grade3tograde5.Models.TestJsonParseModel;
import com.orenda.taimo.grade3tograde5.R;
import com.orenda.taimo.grade3tograde5.SimpleTestActivity;
import com.orenda.taimo.grade3tograde5.SocraticActivity;

import firebase.analytics.AppAnalytics;

import java.io.File;
import java.io.IOException;
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
public class OrdinaryMCQAudioTwoOptions extends Fragment implements View.OnClickListener, View.OnTouchListener, View.OnDragListener {


    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;
    MediaPlayer mediaPlayerTouch;
    ImageView play_sound_image1, play_sound_image2, answer_image_view;
    int image1, image2;
    ImageView selectedView;

    boolean checkedForDemo=false;


    ConstraintLayout ordinaryMCQMainLayout;
    ImageView ordinaryMCQImageViewParrotFire, ordinaryMCQImageViewAlienAvatarLife, ordinaryMCQImageViewParrotAvatarLife, ordinaryMCQImageViewAlienFire;
    ImageView ordinaryMCQImageViewParrotAvatar, ordinaryMCQImageViewAlienAvatar;
    ImageView ordinaryMCQImageViewParrotHit, ordinaryMCQImageViewAlienHit;
    TextView TextViewParrotPlus, TextViewAlienPlus,questionTextViewQuestion;
    AppAnalytics appAnalytics;
    Timer T = new Timer();
    int count = 0;
    boolean start = true;
    float questionFontSize = 22;
    float optionFontSize = 22;
    float questionBigFontSize = 32;

    int realHeight;
    int realWidth;

    int testId = -1;
    TestJsonParseModel test = null;
    Context mContext;
    Activity activity;

    String answer;

    /*
    int DEMO_MODE=0;
    ImageView handImageview;
    ConstraintLayout mainlayout_id;
    ConstraintLayout parentMainlayout_id;
    int  DragTextBoxToPictureTwoOptionsXCoordinate,  DragTextBoxToPictureTwoOptionsYCoordinate;
    int whichTextviewWillDrag;
    Boolean ordinaryMCQ;
    MediaPlayer mp;
    boolean demoStarted=false;
    String checkLang;
    boolean stillInDemoState=false;

     */


    public OrdinaryMCQAudioTwoOptions(int testId, TestJsonParseModel test, Context context, Activity activity) {
        this.testId = testId;
        this.test = test;
        mContext = context;
        this.activity = activity;
        Log.wtf("-test", " TEST ID : " + testId);
        Log.wtf("-test", " TEST Question : " + test.getQuestion().getAudio());
        Log.wtf("-test", " TEST Answer : " + test.getAnswerList().get(0).getText());
        Log.wtf("-test", " TEST Audio 1 : " + test.getOptionList().get(0).getAudio());
        Log.wtf("-test", " TEST Audio 2  : " + test.getOptionList().get(1).getAudio());

    }

    public OrdinaryMCQAudioTwoOptions() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view;

        sharedPrefs = getActivity().getApplicationContext().getSharedPreferences("DefaultSPForTaleemabadGrade6", 0);
        editor = sharedPrefs.edit();
        view = inflater.inflate(R.layout.fragment_ordinary_mcqaudio_two_options, container, false);
        appAnalytics = new AppAnalytics(mContext);
        mediaPlayerTouch=new MediaPlayer();

        initializeViews(view);
        return view;
    }

    private void initializeViews(View view) {
        //playAssetSound("Grade1EnglishTwoAndThreeLetterSoundsAiOaIeQuestion2Option1Audio");
        loadAudio(test.getQuestion().getAudio());
        play_sound_image1 = view.findViewById(R.id.play_sound_image1);
        play_sound_image2 = view.findViewById(R.id.play_sound_image2);
        answer_image_view = view.findViewById(R.id.answer_image_view);
        questionTextViewQuestion = view.findViewById(R.id.questionTextViewQuestion);
        questionTextViewQuestion.setText(String.valueOf(test.getQuestion().getText()));

        image1 = play_sound_image1.getId();
        image2 = play_sound_image2.getId();
        ordinaryMCQMainLayout=view.findViewById(R.id.mainlayout_id);

        ordinaryMCQImageViewParrotFire = view.findViewById(R.id.ordinaryMCQImageViewParrotFire);
        ordinaryMCQImageViewAlienFire = view.findViewById(R.id.ordinaryMCQImageViewAlienFire);
        ordinaryMCQImageViewParrotAvatarLife = view.findViewById(R.id.ordinaryMCQImageViewParrotAvatarLife);
        ordinaryMCQImageViewAlienAvatarLife = view.findViewById(R.id.ordinaryMCQImageViewAlienAvatarLife);
        ordinaryMCQImageViewParrotAvatar = view.findViewById(R.id.ordinaryMCQImageViewParrotAvatar);
        ordinaryMCQImageViewAlienAvatar = view.findViewById(R.id.ordinaryMCQImageViewAlienAvatar);
        ordinaryMCQImageViewParrotHit = view.findViewById(R.id.ordinaryMCQImageViewParrotHit);
        ordinaryMCQImageViewAlienHit = view.findViewById(R.id.ordinaryMCQImageViewAlienHit);

//        parentMainlayout_id = view.findViewById(R.id.mainlayout_id);
//        mainlayout_id = view.findViewById(R.id.layoutid);


        TextViewParrotPlus = view.findViewById(R.id.TextViewParrotPlus);
        TextViewAlienPlus = view.findViewById(R.id.TextViewAlienPlus);
        answer_image_view.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        //At this point the layout is complete and the
                        //dimensions of recyclerView and any child views are known.
                        //Remove listener after changed RecyclerView's height to prevent infinite loop
                        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) answer_image_view.getLayoutParams();
                        params.width = answer_image_view.getHeight();
                        answer_image_view.setLayoutParams(params);
                        answer_image_view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });

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
            ordinaryMCQImageViewParrotAvatar.setVisibility(View.VISIBLE);
            ordinaryMCQImageViewParrotAvatarLife.setVisibility(View.VISIBLE);
            ordinaryMCQImageViewAlienAvatar.setVisibility(View.VISIBLE);
            ordinaryMCQImageViewAlienAvatarLife.setVisibility(View.VISIBLE);


            setOnClickListeners(view);
            startTestTimer();

            //To retrieve
//            SharedPreferences sharedPrefForChecking = getActivity().getSharedPreferences("DemoOrdinaryMCQAudioTwoOptions", DEMO_MODE);
//            ordinaryMCQ = sharedPrefForChecking.getBoolean("OrdinaryMCQAudioTwoOptions", false); //0 is the default value

        }


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
            ordinaryMCQImageViewParrotAvatarLife.setVisibility(View.GONE);
            ordinaryMCQImageViewParrotAvatar.setVisibility(View.GONE);
            ordinaryMCQImageViewAlienAvatarLife.setVisibility(View.GONE);
            ordinaryMCQImageViewAlienAvatar.setVisibility(View.GONE);

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

        SharedPreferences checkLanguage = activity.getSharedPreferences("DefaultSPForTaleemabadGrade6", 0);
        checkLang =checkLanguage.getString("MenuLanguage","en");
        if (checkLang!=null && checkLang.equals("en")){
            mp = MediaPlayer.create(activity, R.raw.eng2);

        }else {
            mp = MediaPlayer.create(activity, R.raw.urdu2);

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

        String anstext = test.getAnswerList().get(0).getAudio().toString();

        String option1 = test.getOptionList().get(0).getAudio().toString();
        String option2 = test.getOptionList().get(1).getAudio().toString();
        Log.wtf("-opt", "ans : "+test.getAnswerList().get(0).getAudio());

        Log.wtf("-asdtest", " TEST Question : " + test.getQuestion().getAudio());
        Log.wtf("-asdtest", " TEST Answer : " + test.getAnswerList().get(0).getAudio());
        Log.wtf("-asdtest", " TEST Audio 1 : " + test.getOptionList().get(0).getAudio());
        Log.wtf("-asdtest", " TEST Audio 2  : " + test.getOptionList().get(1).getAudio());

// TEXTVIEW
        final ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(mainlayout_id);
        handImageview = new ImageView(activity);

        handImageview.setImageResource(R.drawable.hand);
        handImageview.setId(ViewCompat.generateViewId());


//        Toast.makeText(getContext(),"ans: "+anstext, Toast.LENGTH_SHORT).show();
//        Toast.makeText(getContext(),"1: "+option1, Toast.LENGTH_SHORT).show();
//        Toast.makeText(getContext(),"2 "+option2, Toast.LENGTH_SHORT).show();


        if (option1.equals(anstext)){
            mainlayout_id.addView(handImageview);
            constraintSet.connect(handImageview.getId(), ConstraintSet.TOP, R.id.Image1Top,ConstraintSet.TOP,10);
            constraintSet.connect(handImageview.getId(),ConstraintSet.END, R.id.Image1Right,ConstraintSet.START,10);
            constraintSet.connect(handImageview.getId(),ConstraintSet.START, R.id.Image1Left,ConstraintSet.END,10);
            constraintSet.connect(handImageview.getId(),ConstraintSet.BOTTOM, R.id.Image1Bottom,ConstraintSet.TOP,10);
            constraintSet.applyTo(mainlayout_id);

            handImageview.getLayoutParams().width = play_sound_image1.getMeasuredHeight()/2;
            handImageview.getLayoutParams().height = play_sound_image1.getMeasuredHeight()/2;
            handImageview.bringToFront();

            int[] location = new int[2];
            play_sound_image1.getLocationOnScreen(location);
            DragTextBoxToPictureTwoOptionsXCoordinate = location[0];
            DragTextBoxToPictureTwoOptionsYCoordinate = location[1];

            whichTextviewWillDrag =1;
        }else if (option2.equals(anstext)){
            mainlayout_id.addView(handImageview);
            constraintSet.connect(handImageview.getId(), ConstraintSet.TOP, R.id.Image2Top,ConstraintSet.TOP,10);
            constraintSet.connect(handImageview.getId(),ConstraintSet.END, R.id.Image2Right,ConstraintSet.START,10);
            constraintSet.connect(handImageview.getId(),ConstraintSet.START, R.id.Image2Left,ConstraintSet.END,10);
            constraintSet.connect(handImageview.getId(),ConstraintSet.BOTTOM, R.id.Image2Bottom,ConstraintSet.TOP,10);
            constraintSet.applyTo(mainlayout_id);

            handImageview.getLayoutParams().width = play_sound_image1.getMeasuredHeight()/2;
            handImageview.getLayoutParams().height = play_sound_image1.getMeasuredHeight()/2;
            handImageview.bringToFront();

            int[] location = new int[2];
            play_sound_image2.getLocationOnScreen(location);
            DragTextBoxToPictureTwoOptionsXCoordinate = location[0];
            DragTextBoxToPictureTwoOptionsYCoordinate = location[1];

            whichTextviewWillDrag=2;
        }
//        Toast.makeText(getContext(),"1: "+DragTextBoxToPictureTwoOptionsXCoordinate, Toast.LENGTH_LONG).show();
//        Toast.makeText(getContext(),"2: "+DragTextBoxToPictureTwoOptionsYCoordinate, Toast.LENGTH_LONG).show();
//        Toast.makeText(getContext(),"2: "+anstext, Toast.LENGTH_LONG).show();

        //  parentMainlayout_id.removeView(mainlayout_id);
        //        mainlayout_id.addView(handImageview);
//        constraintSet.connect(handImageview.getId(), ConstraintSet.TOP, R.id.Image1Top,ConstraintSet.TOP,10);
//        constraintSet.connect(handImageview.getId(),ConstraintSet.END, R.id.Image1Right,ConstraintSet.START,10);
//        constraintSet.connect(handImageview.getId(),ConstraintSet.START, R.id.Image1Left,ConstraintSet.END,10);
//        constraintSet.connect(handImageview.getId(),ConstraintSet.BOTTOM, R.id.Image1Bottom,ConstraintSet.TOP,10);
//        constraintSet.applyTo(mainlayout_id);
//
//        int[] location = new int[2];
//        play_sound_image1.getLocationOnScreen(location);
//        DragTextBoxToPictureTwoOptionsXCoordinate = location[0];
//        DragTextBoxToPictureTwoOptionsYCoordinate = location[1];
//
//        whichTextviewWillDrag =1;

        final Handler handlerr = new Handler();
        handlerr.postDelayed(new Runnable() {
            @Override
            public void run() {

                moveto(handImageview);

            }
        }, 1200);

    }

 */

/*
    public void moveto(final ImageView imageview){
        imageview.setVisibility(View.VISIBLE);
        int[] location1 = new int[2];
        //textViewList.get(0).getTextView().getLocationOnScreen(location1);

        answer_image_view.getLocationOnScreen(location1);
        final int movetoX = location1[0];
        final int movetoY = location1[1];

        if (whichTextviewWillDrag==1){
            play_sound_image1.animate()
                    .translationX(60)
                    .alpha(0f)
                    .setDuration(500)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            play_sound_image1.animate()
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
            play_sound_image2.animate()
                    .translationX(60)
                    .alpha(1.0f)
                    .setDuration(500)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            play_sound_image2.animate()
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
                .x(movetoX)
                .y(movetoY)
                .alpha(1.0f)
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



    public void setOnClickListeners(View view) {
        play_sound_image1.setOnClickListener(this);
        play_sound_image2.setOnClickListener(this);
        play_sound_image1.setOnClickListener(this);
        play_sound_image2.setOnClickListener(this);

        play_sound_image1.setOnTouchListener(this);
        play_sound_image2.setOnTouchListener(this);

        answer_image_view.setOnDragListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play_sound_image1:
                Log.wtf("onClick", "image1");
                break;
            case R.id.play_sound_image2:
                Log.wtf("onClick", "image2");
                break;


        }
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                break;
            case DragEvent.ACTION_DROP:
                ImageView imageView = (ImageView) v;

                if (selectedView.getId() == play_sound_image1.getId() || selectedView.getId() == play_sound_image2.getId()) {

                    Log.wtf("-drag", " On Drag || Dropped");
                    imageView.setOnDragListener(null);
                    imageView.setBackgroundResource(android.R.color.transparent);
                    //   selectedView.setBackgroundResource(android.R.color.transparent);
                    selectedView.setOnTouchListener(null);
                    selectedView.setBackground(null);
                    answer_image_view.setImageResource(R.mipmap.sound_recognition);
                    Log.wtf("option_", "answer  : " + selectedView.getId());
                    compileAns(answer);
                }

                return true;

        }
        return true;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {

            Log.wtf("-drag", "On Touch || ACtion Down");
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                    view);
            //  view.startDrag(data, shadowBuilder, view, 0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                view.startDragAndDrop(data, shadowBuilder, null, 0);
            } else {
                view.startDrag(data, shadowBuilder, null, 0);
            }
            ImageView view1 = (ImageView) view;
            selectedView = view1;
            if (selectedView.getId() == image1) {
                //playAssetSound("Grade1EnglishTwoAndThreeLetterSoundsAiOaIeQuestion2Option1Audio");
                loadAudio(test.getOptionList().get(0).getAudio());
                answer = String.valueOf(test.getOptionList().get(0).getAudio());
            } else if (selectedView.getId() == image2) {
                //playAssetSound("Grade1EnglishTwoAndThreeLetterSoundsAiOaIeQuestion2Option1Audio");
                loadAudio(test.getOptionList().get(1).getAudio());
                answer = String.valueOf(test.getOptionList().get(1).getAudio());
            }
            Log.wtf("-drag", "On Touch || ACtion Down " + selectedView.getId());

            return true;


        }

        return false;
    }








    public void compileAns(final String answer) {
        //stillInDemoState=false;
        Log.wtf("option_", "answer  : " + selectedView.getId());
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
        if (answer.equalsIgnoreCase(test.getAnswerList().get(0).getAudio())) {
            if(unSocratic == true) {
                appAnalytics.setOptionSelected(selectedSubject, topic, test.getType(), answer, true);
                appAnalytics.setAnswer(selectedSubject, topic + "" + testIndex, topic, test.getType(), test.getAnswerList().size(), 1, true, this.count);
            }
            score = tScore;
            correctCount++;
            totalScore = totalScore + score;
            answer_image_view.setImageResource(0);
            answer_image_view.setBackground(getResources().getDrawable(R.drawable.maths_mcq_green));
            parrotFire();

        } else {
            if(unSocratic == true) {
                appAnalytics.setOptionSelected(selectedSubject, topic, test.getType(), answer, false);
                appAnalytics.setAnswer(selectedSubject, topic + "" + testIndex, topic, test.getType(), test.getAnswerList().size(), 0, false, this.count);
            }
            answer_image_view.setImageResource(0);
            answer_image_view.setBackground(getResources().getDrawable(R.drawable.maths_mcq_red));
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
                            .remove(OrdinaryMCQAudioTwoOptions.this).commit();
                }

            }
        }, 1000);

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
                    mediaPlayerTouch=new MediaPlayer();
                    if (!checkedForDemo){
                        checkedForDemo=true;
                        //setUpDemo();
                    }
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
            mediaPlayerTouch.setDataSource(path + "/"  + name + ".mp3");
            mediaPlayerTouch.prepare();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayerTouch.start();
    }

}
