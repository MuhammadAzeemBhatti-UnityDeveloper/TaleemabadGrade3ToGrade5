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
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.deduct;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.parrotLife;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.selectedSubject;

@SuppressLint("ValidFragment")
public class FillInTheBlankEnglish extends Fragment implements View.OnClickListener, View.OnTouchListener, View.OnDragListener {

    MediaPlayer mediaPlayerTouch;
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;

    TextView fillInTheBlanksTextViewOption1, fillInTheBlanksTextViewOption2;

    LinearLayout fillInTheBlanksLayoutRow1, fillInTheBlanksLayoutRow2, fillInTheBlanksLayoutRow3, fillInTheBlanksLayoutRow4, fillInTheBlanksLayoutRow5, fillInTheBlanksLayoutRow6;
    ArrayList<FillInTheBlanksTextViewModel> textViewList = new ArrayList<>();
    private String selectedData = null;
    private TextView selectedView = null;
    private int answerCount = 0;
    float fontSize = 22;
    ArrayList<LinearLayout> linearLayouts = new ArrayList<>();
    public int stringIndex = 0;
    private boolean isDroped = true;
    private boolean isTextViewDroped = true;
    TextView TextViewParrotPlus,TextViewAlienPlus;
    AppAnalytics appAnalytics;
    Timer T = new Timer();
    int count = 0;
    boolean start = true;
    ImageView ImageViewParrotFire, ImageViewAlienAvatarLife, ImageViewParrotAvatarLife, ImageViewAlienFire;
    ImageView ImageViewParrotAvatar, ImageViewAlienAvatar;
    ImageView ImageViewParrotHit, ImageViewAlienHit;
    int realHeight;
    int realWidth;

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
    ConstraintLayout fillInTheBlanksMainLayout;
    int  fillInTheBlanksTextViewOption2XCoordinate,  fillInTheBlanksTextViewOption2YCoordinate;
    int whichOptionisCorrect;
    Boolean ordinaryMCQ;
    MediaPlayer mp;
    boolean demoStarted=false;
    String checkLang;
    boolean stillInDemoState=false;
*/
    public FillInTheBlankEnglish(int testId, TestJsonParseModel test, Context context, Activity activity) {
        this.testId = testId;
        this.test = test;
        mContext = context;
        this.activity = activity;

        Log.wtf("-this", " TEST ID : " + testId);
    }

    public FillInTheBlankEnglish() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fill_in_the_blank, container, false);
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

        fillInTheBlanksLayoutRow1 = view.findViewById(R.id.fillInTheBlanksRow1);
        fillInTheBlanksLayoutRow2 = view.findViewById(R.id.fillInTheBlanksLayoutRow2);
        fillInTheBlanksLayoutRow3 = view.findViewById(R.id.fillInTheBlanksLayoutRow3);
        fillInTheBlanksLayoutRow4 = view.findViewById(R.id.fillInTheBlanksLayoutRow4);
        fillInTheBlanksLayoutRow5 = view.findViewById(R.id.fillInTheBlanksLayoutRow5);
        fillInTheBlanksLayoutRow6 = view.findViewById(R.id.fillInTheBlanksLayoutRow6);
        linearLayouts.add(fillInTheBlanksLayoutRow1);
        linearLayouts.add(fillInTheBlanksLayoutRow2);
        linearLayouts.add(fillInTheBlanksLayoutRow3);
        linearLayouts.add(fillInTheBlanksLayoutRow4);
        linearLayouts.add(fillInTheBlanksLayoutRow5);
        linearLayouts.add(fillInTheBlanksLayoutRow6);
        for (int i = 0; i < linearLayouts.size(); i++) {
            //  linearLayouts.get(i).setPadding(10,0,10,0);
        }

        TextViewParrotPlus = view.findViewById(R.id.TextViewParrotPlus);
        TextViewAlienPlus = view.findViewById(R.id.TextViewAlienPlus);

        fillInTheBlanksTextViewOption1 = view.findViewById(R.id.fillInTheBlanksTextViewOption1);
        fillInTheBlanksTextViewOption2 = view.findViewById(R.id.fillInTheBlanksTextViewOption2);

        ImageViewParrotFire = view.findViewById(R.id.ImageViewParrotFire);
        ImageViewAlienFire = view.findViewById(R.id.ImageViewAlienFire);
        ImageViewParrotAvatarLife = view.findViewById(R.id.ImageViewParrotAvatarLife);
        ImageViewAlienAvatarLife = view.findViewById(R.id.ImageViewAlienAvatarLife);
        ImageViewParrotAvatar = view.findViewById(R.id.ImageViewParrotAvatar);
        ImageViewAlienAvatar = view.findViewById(R.id.ImageViewAlienAvatar);
        ImageViewParrotHit = view.findViewById(R.id.ImageViewParrotHit);
        ImageViewAlienHit = view.findViewById(R.id.ImageViewAlienHit);
       // fillInTheBlanksMainLayout = view.findViewById(R.id.fillInTheBlanksMainLayout);


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
                        fontSize = (0.027f * (1080f / (2f)));
                    } else {
                        fontSize = (0.027f * 800);
                    }
                    Log.wtf("-this", "height : " + realHeight + "  FONT SIZE : " + fontSize);

                    float fontSizeQuestion = (0.03f * realHeight);
                    if (screenInches < 7) {
                        fontSizeQuestion = (0.03f * (1080 / 2));
                    } else {
                        fontSizeQuestion = (0.03f * 800);
                    }

                    Log.wtf("-this", "height : " + realHeight + "  Q SFONT SIZE : " + fontSizeQuestion);
                    fillInTheBlanksTextViewOption1.getViewTreeObserver().removeOnGlobalLayoutListener(this);


                    fillInTheBlanksTextViewOption1.setTextSize(fontSize);
                    fillInTheBlanksTextViewOption2.setTextSize(fontSize);

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

       // setOnClickListeners(view);
        startTestTimer();

        //To retrieve
        //SharedPreferences sharedPref = getActivity().getSharedPreferences("DemoFillInTheBlankEnglish", DEMO_MODE);
        //ordinaryMCQ = sharedPref.getBoolean("DemoFillInTheBlankEnglish", false); //0 is the default value

        setOnClickListeners(view);

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

            SharedPreferences sharedPrefForSaving = getContext().getSharedPreferences("DemoFillInTheBlankEnglish", DEMO_MODE);
            SharedPreferences.Editor editor = sharedPrefForSaving.edit();
            editor.putBoolean("DemoFillInTheBlankEnglish", true);
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

        final ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(fillInTheBlanksMainLayout);
        final ImageView imageview = new ImageView(activity);
        imageview.setImageResource(R.drawable.hand);
        imageview.setId(ViewCompat.generateViewId());
        fillInTheBlanksMainLayout.addView(imageview);


        String ans =textViewList.get(0).getAnswer();
        String ansOption1 =fillInTheBlanksTextViewOption1.getText().toString();
        String ansOption2 =fillInTheBlanksTextViewOption2.getText().toString();

        if (ansOption1.equals(ans)) {

            constraintSet.connect(imageview.getId(), ConstraintSet.TOP, R.id.guidelineOptionTop, ConstraintSet.TOP, 10);
            constraintSet.connect(imageview.getId(), ConstraintSet.END, R.id.guidelineOption1Right, ConstraintSet.START, 10);
            constraintSet.connect(imageview.getId(), ConstraintSet.START, R.id.guidelineOption1Left, ConstraintSet.END, 10);
            constraintSet.connect(imageview.getId(), ConstraintSet.BOTTOM, R.id.guidelineOptionBottom, ConstraintSet.TOP, 10);
            constraintSet.applyTo(fillInTheBlanksMainLayout);

            whichOptionisCorrect =1;
            final Handler handlerr = new Handler();
            handlerr.postDelayed(new Runnable() {
                @Override
                public void run() {

                    int[] location = new int[2];
                    fillInTheBlanksTextViewOption1.getLocationOnScreen(location);

                    fillInTheBlanksTextViewOption2XCoordinate = location[0];
                    fillInTheBlanksTextViewOption2YCoordinate= location[1];
                    moveto(imageview);
                }
            }, 1200);

        }else if (ansOption2.equals(ans)) {

            constraintSet.connect(imageview.getId(), ConstraintSet.TOP, R.id.guidelineOptionTop, ConstraintSet.TOP, 10);
            constraintSet.connect(imageview.getId(), ConstraintSet.END, R.id.guidelineOption2Right, ConstraintSet.START, 10);
            constraintSet.connect(imageview.getId(), ConstraintSet.START, R.id.guidelineOption2Left, ConstraintSet.END, 10);
            constraintSet.connect(imageview.getId(), ConstraintSet.BOTTOM, R.id.guidelineOptionBottom, ConstraintSet.TOP, 10);
            constraintSet.applyTo(fillInTheBlanksMainLayout);

            whichOptionisCorrect =2;

            final Handler handlerr = new Handler();
            handlerr.postDelayed(new Runnable() {
                @Override
                public void run() {

                    int[] location = new int[2];
                    fillInTheBlanksTextViewOption2.getLocationOnScreen(location);

                    fillInTheBlanksTextViewOption2XCoordinate = location[0];
                    fillInTheBlanksTextViewOption2YCoordinate = location[1];
                    moveto(imageview);
                }
            }, 1200);
        }

    }
    */
/*
    public void moveto(final ImageView imageview){
        imageview.setVisibility(View.VISIBLE);
        int[] location1 = new int[2];
        textViewList.get(0).getTextView().getLocationOnScreen(location1);

        final int movetoX = location1[0];
        final int movetoY = location1[1];


        if (whichOptionisCorrect==1){
            fillInTheBlanksTextViewOption1.setVisibility(View.VISIBLE);
            fillInTheBlanksTextViewOption1.animate()
                    .translationY(60)
                    .alpha(0f)
                    .setDuration(500)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            fillInTheBlanksTextViewOption1.animate()
                                    .translationY(0)
                                    .alpha(1.0f)
                                    .setDuration(400)
                                    .setListener(new AnimatorListenerAdapter() {
                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            super.onAnimationEnd(animation);
                                        }
                                    });

                        }
                    });

        }else if (whichOptionisCorrect==2){
            fillInTheBlanksTextViewOption2.setVisibility(View.VISIBLE);
            fillInTheBlanksTextViewOption2.animate()
                    .translationY(60)
                    .alpha(0f)
                    .setDuration(500)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            fillInTheBlanksTextViewOption2.animate()
                                    .translationY(0)
                                    .alpha(1.0f)
                                    .setDuration(400)
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
                        }, 500);
                    }
                });
    }

 */
/*
    public void movetoback(final ImageView imageview){


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


    public void setUpTest() {
        showViews();
    }

    public void showViews() {

        setBackGrounds(sharedPrefs.getString("SupervisedSubjectSelected", "English"));

//        fillInTheBlanksTextViewOption1.setVisibility(View.VISIBLE);
//        fillInTheBlanksTextViewOption2.setVisibility(View.VISIBLE);

        ImageViewAlienAvatarLife.setVisibility(View.VISIBLE);
        ImageViewParrotAvatarLife.setVisibility(View.VISIBLE);
        ImageViewParrotAvatar.setVisibility(View.VISIBLE);
        ImageViewAlienAvatar.setVisibility(View.VISIBLE);

        fillUpTest((fillInTheBlanksTextViewOption1.getWidth() - 10), (fillInTheBlanksTextViewOption1.getHeight() - 10));
        setViews();

    }

    public void setBackGrounds(String subject) {
        switch (subject) {
            case ("English"):
                fillInTheBlanksTextViewOption1.setBackgroundResource(R.drawable.english_bg);
                fillInTheBlanksTextViewOption2.setBackgroundResource(R.drawable.english_bg);


                break;
            case ("Maths"):
                fillInTheBlanksTextViewOption1.setBackgroundResource(R.drawable.maths_bg);
                fillInTheBlanksTextViewOption2.setBackgroundResource(R.drawable.maths_bg);
                break;


            case ("Urdu"):
                fillInTheBlanksTextViewOption1.setBackgroundResource(R.drawable.urdu_bg);
                fillInTheBlanksTextViewOption2.setBackgroundResource(R.drawable.urdu_bg);

                break;
            case ("GeneralKnowledge"):
            case ("Science"):
                fillInTheBlanksTextViewOption1.setBackgroundResource(R.drawable.science_bg);
                fillInTheBlanksTextViewOption2.setBackgroundResource(R.drawable.science_bg);

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

        String test01 = test.getQuestion().getText();
        textViewList.clear();
        fillInTheBlanksTextViewOption1.setText(test.getOptionList().get(0).getText());
        fillInTheBlanksTextViewOption2.setText(test.getOptionList().get(1).getText());
        String subject = sharedPrefs.getString("SupervisedSubjectSelected", "English");

        Log.wtf("-this", "Text Q : " + test01);
        int TextLength = test01.length();
        Log.wtf("-this", "Length : " + test01.length());
        int lineSize = 56;
        int div = 0;
        int mod = 0;
        if(TextLength + 25 < (lineSize+1)){
            div = 1;
        } else{
            div = (TextLength + 25) / lineSize;
            mod = TextLength % lineSize;
            if (mod > 0) {
                div = div + 1;
            }
        }
        Log.wtf("-this", "MOD : " + mod + "  DIV : " + div);
        for (int j = 0; j < div; j++) {
            int i = j;
            if (div < 3){
                i = j + 2;
            }
            String test0 = null;
            if (test01.length() > (this.stringIndex + lineSize)) {
                Log.wtf("-this", "IF  : " + 216);
                test0 = test01.substring(this.stringIndex, (this.stringIndex + lineSize));
            } else {
                Log.wtf("-this", "ELSE  : " + 219);
                test0 = test01.substring(this.stringIndex);
            }
            Log.wtf("-this", "Index  : " + i + " stringIndex  : " + this.stringIndex + "   TEXT : " + test0 + "  Length : " + test0.length());
//                        if(i == 0) {
            if (test0.length() < lineSize + 1) {
                String[] resultArray0 = test0.split("_");
                int resultArraySize = resultArray0.length;
                Log.wtf("-this", "IF  : " + 222 + resultArraySize);

                if (resultArraySize == 1) {
                    Log.wtf("-this", "IF  : " + 229);
                    // SetQhere
                    TextView tv0q = new TextView(getContext());

                    String temp = resultArray0[0];
                    if (temp.length() < lineSize) {

                        tv0q.setText(temp);
                        tv0q.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        linearLayouts.get(i).addView(tv0q);
                        tv0q.setTextColor(getResources().getColor(R.color.cardview_light_background));
                        tv0q.setTextSize(fontSize);
                        tv0q.setTypeface(face);
                        this.stringIndex = temp.length() + this.stringIndex;
                    } else {
                        String temp1 = temp.substring(0, temp.lastIndexOf(' '));
                        tv0q.setText(temp1);
                        tv0q.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        linearLayouts.get(i).addView(tv0q);
                        tv0q.setTextColor(getResources().getColor(R.color.cardview_light_background));
                        tv0q.setTextSize(fontSize);
                        tv0q.setTypeface(face);
                        this.stringIndex = temp1.length() + this.stringIndex;
                    }
                }
                else if (resultArraySize == 2) {
                    Log.wtf("-this", "IF  : " + 237);
                    if (test0.contains("_")) {
                        Log.wtf("-this", "IF  : " + 239);
                        Log.wtf("-this", "resultArray0[0]  : " + resultArray0[0] + " Length : " + resultArray0[0].length());
                        Log.wtf("-this", "resultArray0[0]  : " + resultArray0[0].lastIndexOf(' '));
                        Log.wtf("-this", "resultArray0[1]  : " + resultArray0[1] + " Length : " + resultArray0[1].length());
                        Log.wtf("-this", "resultArray0[0]  : " + resultArray0[1].lastIndexOf(' '));

                        if ((resultArray0[0].length() + resultArray0[1].length() + 25) < (lineSize + 1)) {


                            Log.wtf("-this", "IF  : " + 245);
                            TextView tv0q = new TextView(getContext());



                            String temp12 =resultArray0[0];
                            if(temp12.length() > 0){
                                Log.wtf("-this", "IF  : " + 416);

                                tv0q.setText(temp12);
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
                            tv0a.setTextSize(fontSize);
                            tv0a.setTypeface(face);
                            tv0a.setTextColor(Color.TRANSPARENT);
                            tv0a.setGravity(Gravity.CENTER);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                                tv0a.setId(View.generateViewId());
                            }
                            textViewList.add(new FillInTheBlanksTextViewModel(tv0a, "لکھ"));




                            if((resultArray0[1].length() > 1) && (resultArray0[1].length() < (lineSize -(temp12.length() + 25)))){
                                Log.wtf("-this", "IF  : " + 444);
                                TextView tv1q = new TextView(getContext());
                                String temp = resultArray0[1];
                                int newIndex = temp.lastIndexOf(' ');
                                String temp1 = temp;
                                if(newIndex < temp.length() && newIndex > 0){
                                    temp1 = temp.substring(0, temp.lastIndexOf(' '));
                                }

                                tv1q.setText(temp1);
                                tv1q.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                linearLayouts.get(i).addView(tv1q);
                                tv1q.setTextColor(getResources().getColor(R.color.cardview_light_background));
                                tv1q.setTextSize(fontSize);
                                tv1q.setTypeface(face);

                                this.stringIndex = temp1.length() + temp12.length() + this.stringIndex;
                            } else{
                                this.stringIndex = + temp12.length() + this.stringIndex;
                            }


                        }
                        else {
                            Log.wtf("-this", "Else  : " + 281);
                            String temp0 = resultArray0[0];
                            int remainingIndex = (lineSize - (resultArray0[0].length() + 25));

                            if (remainingIndex > 0 ) {
                                Log.wtf("-this", "IF  : " + 285);
                                Log.wtf("-this", "RemainIndex  : " + remainingIndex+ " resultarray0 : "+resultArray0[0].length());
                                Log.wtf("-this", "resultArray0[0]  : " +resultArray0[0]);

                                String temp ="";
//                                if(remainingIndex < resultArray0[0].length()){
//                                   temp = resultArray0[0].substring(0, remainingIndex);
//                                } else {
//                                    temp = resultArray0[0];
//                                }

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
                                tv0a.setTextSize(fontSize);
                                tv0a.setTypeface(face);
                                tv0a.setTextColor(Color.TRANSPARENT);
                                tv0a.setGravity(Gravity.CENTER);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                                    tv0a.setId(View.generateViewId());
                                }
                                textViewList.add(new FillInTheBlanksTextViewModel(tv0a, "لکھ"));
                                if ((temp.length() + 25) > (lineSize - 3)) {
                                    Log.wtf("-this", "IF  : " + 310 + "  " + (temp.length() + 25));
                                    this.stringIndex = temp.length() + this.stringIndex + 2;
                                } else {
                                    Log.wtf("-this", "IF  : " + 313 + "  " + (temp.length() + 25));
                                    TextView tv0q = new TextView(getContext());
                                    String temp11 = resultArray0[1];
                                    int newIndex = lineSize - (temp.length() + 25);
                                    String temp12 = "";
                                    if(newIndex < temp11.length()){
                                        String temp99 =temp11.substring(0, newIndex);
                                        temp12 = temp99.substring(0, temp99.lastIndexOf(' '));
                                      //  temp12 = temp99;
                                    } else{
                                        temp12 = temp11.substring(0, temp11.lastIndexOf(' '));
                                    }
                                    tv0q.setText(temp12);
                                    tv0q.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                    linearLayouts.get(i).addView(tv0q);
                                    tv0q.setTextColor(getResources().getColor(R.color.cardview_light_background));
                                    tv0q.setTextSize(fontSize);
                                    tv0q.setTypeface(face);

                                    this.stringIndex = temp.length() + temp12.length() + this.stringIndex + 2;
                                }
                                Log.wtf("-this", "STRING INDEX 343 :    " + this.stringIndex);


                            } else {
                                Log.wtf("-this", "Else  : " + 321);

                                TextView tv0q = new TextView(getContext());
                                String temp1 = temp0.substring(0, temp0.lastIndexOf(' '));
                                tv0q.setText(temp1);
                                tv0q.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                linearLayouts.get(i).addView(tv0q);
                                tv0q.setTextColor(getResources().getColor(R.color.cardview_light_background));
                                tv0q.setTextSize(fontSize);
                                tv0q.setTypeface(face);

                                this.stringIndex = temp1.length() + this.stringIndex;
                                Log.wtf("-this", "ELSE IF 542 String Index : "+this.stringIndex);
                            }

                        }

                    } else {
                        Log.wtf("-this", "ELSE  : " + 346);
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
                }
                else if (resultArraySize == 3) {
                    Log.wtf("-this", "ELSE IF : " + 315);
                    if (test0.length() < (lineSize - 25)) {
                        Log.wtf("-this", "ELSE  : " + 317);
                        TextView tv0q = new TextView(getContext());
                        tv0q.setText(resultArray0[0]);
                        tv0q.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        linearLayouts.get(i).addView(tv0q);
                        tv0q.setTextColor(getResources().getColor(R.color.cardview_light_background));
                        tv0q.setTextSize(fontSize);
                        tv0q.setTypeface(face);

                        TextView tv0a = new TextView(getContext());
                        tv0a.setLayoutParams(new ViewGroup.LayoutParams(fillInTheBlanksTextViewOption1Width, fillInTheBlanksTextViewOption1Height));
                        tv0a.setBackgroundResource(R.drawable.maths_mcq_blank_roundbg);
                        linearLayouts.get(i).addView(tv0a);
                        tv0a.setTextColor(getResources().getColor(R.color.cardview_light_background));
                        tv0a.setTextSize(fontSize);
                        tv0a.setTypeface(face);
                        tv0a.setTextColor(Color.TRANSPARENT);
                        tv0a.setGravity(Gravity.CENTER);
                        tv0a.setText("غور");
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                            tv0a.setId(View.generateViewId());
                        }
                        textViewList.add(new FillInTheBlanksTextViewModel(tv0a, "جگہ"));

                        TextView tv0q2 = new TextView(getContext());
                        tv0q2.setText(resultArray0[1]);
                        tv0q2.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                        linearLayouts.get(i).addView(tv0q2);
                        tv0q2.setTextColor(getResources().getColor(R.color.cardview_light_background));
                        tv0q2.setTextSize(fontSize);
                        tv0q2.setTypeface(face);
                        this.stringIndex = test0.length() + this.stringIndex;

                    } else {
                        Log.wtf("-this", "ELSE  : " + 351);
                        TextView tv0q = new TextView(getContext());
                        tv0q.setText(resultArray0[0]);
                        tv0q.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        linearLayouts.get(i).addView(tv0q);
                        tv0q.setTextColor(getResources().getColor(R.color.cardview_light_background));
                        tv0q.setTextSize(fontSize);
                        tv0q.setTypeface(face);

                        this.stringIndex = this.stringIndex + resultArray0[0].length();
                        Log.wtf("-this", "resultArray SIZE  : " + resultArray0.length);
                        Log.wtf("-this", "String Index  : " + this.stringIndex + "  " + resultArray0[0]);
                        Log.wtf("-this", "String Index2  : " + this.stringIndex + "  " + resultArray0[1]);
                    }
                }
            } else {
                Log.wtf("-this", "ELSE  : " + 367);
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

        Log.wtf("answerList",test.getAnswerList().size()+"");
        for (int i = 0; i < textViewList.size(); i++) {
            textViewList.get(i).getTextView().setOnDragListener(this);
            textViewList.get(i).getTextView().setText(test.getAnswerList().get(i).getText()+"");
            textViewList.get(i).setAnswer(test.getAnswerList().get(i).getText());

        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            default:
                break;

        }

    }


    public void setOnClickListeners(View view) {
        for (FillInTheBlanksTextViewModel md : textViewList) {
            md.getTextView().setOnDragListener(this);
            //  md.getTextView().setOnTouchListener(this);
        }

        fillInTheBlanksTextViewOption1.setOnTouchListener(this);
        fillInTheBlanksTextViewOption2.setOnTouchListener(this);


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
            selectedView.setOnDragListener(this);

            boolean asnwerPicked = false;
            for (int i = 0; i < textViewList.size(); i++) {
                if (textViewList.get(i).getTextView().getId() == selectedView.getId()) {
                    isTextViewDroped = false;
                }
            }


            if (selectedView.getId() == fillInTheBlanksTextViewOption1.getId()) {
                fillInTheBlanksTextViewOption2.setOnDragListener(null);
                fillInTheBlanksTextViewOption1.setOnDragListener(this);
                Log.wtf("-this", "Option 1 Picked");
                isDroped = false;
            }
            if (selectedView.getId() == fillInTheBlanksTextViewOption2.getId()) {
                fillInTheBlanksTextViewOption1.setOnDragListener(null);
                fillInTheBlanksTextViewOption2.setOnDragListener(this);
                Log.wtf("-this", "Option 2 Picked");
                isDroped = false;
            }

            for (int i = 0; i < textViewList.size(); i++) {

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
                if (selectedView.getId() == fillInTheBlanksTextViewOption1.getId() || selectedView.getId() == fillInTheBlanksTextViewOption2.getId()) {

                    if (txtView.getId() == fillInTheBlanksTextViewOption1.getId() || txtView.getId() == fillInTheBlanksTextViewOption2.getId()) {
                        Log.wtf("-drag", " Test Inc: " + answerCount);

                        break;

                    } else {

                        if (textViewList.size() == 1) {
                            Log.wtf("-drag", " IDs: " + textViewList.get(0).getTextView().getId() + "  " + selectedView.getId());
                            textViewList.get(0).getTextView().setOnDragListener(null);
                            textViewList.get(0).getTextView().setOnTouchListener(this);
                            Log.wtf("-drag", " In FOR IF ");
                            answerCount++;
                            if (selectedView.getId() == fillInTheBlanksTextViewOption1.getId()) {
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
                            } else if (selectedView.getId() == fillInTheBlanksTextViewOption2.getId()) {
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
                            }
                        } else if (textViewList.size() == 2) {
                            {
                                if (textViewList.get(0).getTextView().getId() == txtView.getId()) {
                                    Log.wtf("-drag", "ELSE IDs: " + textViewList.get(0).getTextView().getId() + "  " + selectedView.getId());

                                    textViewList.get(0).getTextView().setOnDragListener(null);
                                    textViewList.get(0).getTextView().setOnTouchListener(this);

                                    Log.wtf("-drag", "ELSE In FOR IF ");
                                    if (selectedView.getId() == fillInTheBlanksTextViewOption1.getId()) {
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
                                    } else if (selectedView.getId() == fillInTheBlanksTextViewOption2.getId()) {
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
                                    }
                                    answerCount++;
                                } else {
                                    Log.wtf("-drag", "ELSE ELSE IDs: " + textViewList.get(1).getTextView().getId() + "  " + selectedView.getId());

                                    textViewList.get(1).getTextView().setOnDragListener(null);
                                    textViewList.get(1).getTextView().setOnTouchListener(this);

                                    Log.wtf("-drag", "ELSE In FOR ");
                                    if (selectedView.getId() == fillInTheBlanksTextViewOption1.getId()) {
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
                                    } else if (selectedView.getId() == fillInTheBlanksTextViewOption2.getId()) {
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
                                    }
                                    answerCount++;
                                }

                            }

                        }
                        //  Toast.makeText(getContext(), " ButtonOption Inc: " + answerCount, Toast.LENGTH_SHORT).show();
                        selectedView.setOnDragListener(this);
                        selectedView.setOnTouchListener(null);
                        Log.wtf("-drag", " ButtonOption Inc: " + answerCount);

                    }


                } else if (txtView.getId() == fillInTheBlanksTextViewOption1.getId() || txtView.getId() == fillInTheBlanksTextViewOption2.getId()) {

                    txtView.setOnDragListener(null);
                    txtView.setOnTouchListener(this);
                    answerCount--;
                    Log.wtf("-drag", " textView Dec : " + answerCount);
                    selectedView.setOnTouchListener(null);
                    selectedView.setOnDragListener(this);
                    Log.wtf("-drag", " textView Dec : " + answerCount);


                    // Toast.makeText(getContext(), " textView Dec : " + answerCount, Toast.LENGTH_SHORT).show();

                } else {
                    Log.wtf("-drag", " textView Nothing : ");
                    txtView.setOnDragListener(null);
                    txtView.setOnTouchListener(this);
                    selectedView.setOnTouchListener(null);
                    selectedView.setOnDragListener(this);

                }

                selectedView.setBackgroundResource(R.drawable.maths_mcq_blank_roundbg);
                selectedView.setTextColor(Color.TRANSPARENT);
                //  selectedView.setOnDragListener(this);
//                txtView.setOnTouchListener(this);
                txtView.setOnDragListener(null);
                txtView.setText(selectedData);
                txtView.setTextColor(getResources().getColor(R.color.cardview_light_background));
                setBackGroundSingle(sharedPrefs.getString("SupervisedSubjectSelected", "English"), txtView);


//                if (txtView.getId() == fillInTheBlanksTextViewOption1.getId() || txtView.getId() == fillInTheBlanksTextViewOption2.getId()) {
//                    txtView.setOnDragListener(null);
//
//                }

                isTextViewDroped = true;
                isDroped = true;

                //stillInDemoState=false;

                if (answerCount == textViewList.size()) {
                    compileAns();
                }
                // Toast.makeText(getContext(), " ButtonOption : " + selectedData, Toast.LENGTH_SHORT).show();

                break;
            case DragEvent.ACTION_DRAG_ENDED:
                //  Log.wtf("-drag", "Ended " + v.toString());
                if (isDroped == false) {
                    if (selectedView.getId() == fillInTheBlanksTextViewOption1.getId()) {
                        fillInTheBlanksTextViewOption2.setOnDragListener(this);
                        fillInTheBlanksTextViewOption1.setOnDragListener(null);
                        Log.wtf("-this", "Option 1 Droped");

                    }
                    if (selectedView.getId() == fillInTheBlanksTextViewOption2.getId()) {
                        fillInTheBlanksTextViewOption1.setOnDragListener(this);
                        fillInTheBlanksTextViewOption2.setOnDragListener(null);
                        Log.wtf("-this", "Option 2 Droped");
                    }
                }
                if (isTextViewDroped == false) {

                    for (int i = 0; i < textViewList.size(); i++) {
                        if (textViewList.get(i).getTextView().getId() == selectedView.getId()) {
                            Log.wtf("-this", "answerView Droped");
                            textViewList.get(i).getTextView().setOnDragListener(null);
                            // textViewList.get(i).getTextView().setOnDragListener(this);
                        }
                    }
                }
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

    public void compileAns() {
        //stillInDemoState=false;
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
            } else{
                alienFire();
            }
        }else{
            if(score < ((tScore * 2)/2)){
                alienFire();
            } else{
                parrotFire();
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
                            .remove(FillInTheBlankEnglish.this).commit();
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
        fillInTheBlanksTextViewOption1.setVisibility(View.INVISIBLE);
        fillInTheBlanksTextViewOption2.setVisibility(View.INVISIBLE);

        if(selectedSubject.equalsIgnoreCase("English") || selectedSubject.equalsIgnoreCase("Maths") || selectedSubject.equalsIgnoreCase("Science")
                || selectedSubject.equalsIgnoreCase("Geography")){
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    jab();
                    fillInTheBlanksTextViewOption1.setVisibility(View.VISIBLE);

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            jab();
                            fillInTheBlanksTextViewOption2.setVisibility(View.VISIBLE);

                            //setUpDemo();
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
                    fillInTheBlanksTextViewOption2.setVisibility(View.VISIBLE);

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            jab();
                            fillInTheBlanksTextViewOption1.setVisibility(View.VISIBLE);

                            //setUpDemo();

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
