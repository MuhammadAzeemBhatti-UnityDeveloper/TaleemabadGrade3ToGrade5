package com.orenda.taimo.grade3tograde5.Tests;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

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
import com.orenda.taimo.grade3tograde5.Models.MatchImageToImageImageTextModel;
import com.orenda.taimo.grade3tograde5.Models.MatchImageToImageViewModel;
import com.orenda.taimo.grade3tograde5.Models.TestJsonParseModel;
import com.orenda.taimo.grade3tograde5.R;
import com.orenda.taimo.grade3tograde5.SimpleTestActivity;
import com.orenda.taimo.grade3tograde5.SocraticActivity;

//import static com.facebook.FacebookSdk.getApplicationContext;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.testIndex;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.topic;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.unSocratic;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.alienLife;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.deduct;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.parrotLife;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.selectedSubject;
import static com.orenda.taimo.grade3tograde5.SimpleTestActivity.totalScore;

@SuppressLint("ValidFragment")

public class MatchingImageToImage extends Fragment implements View.OnClickListener, View.OnTouchListener {
    MediaPlayer mediaPlayerTouch;
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;
    String selectedData;
    ImageView selectedImageView = null;
    TextView selectedTextView = null;
    TextView selectedTextViewUp = null;
    TextView selectedTextViewDown = null;
    boolean upFound = false;
    boolean downFound = false;
    boolean upFoundText = false;
    boolean downFoundText = false;

    TextView matchingImageToImageTextViewQuestion;
    TextView TextViewParrotPlus,TextViewAlienPlus;

    ImageView matchingImageToImageImageViewRow1Option1, matchingImageToImageImageViewRow1Option2, matchingImageToImageImageViewRow1Option3, matchingImageToImageImageViewRow1Option4;
    ImageView matchingImageToImageImageViewRow2Option1, matchingImageToImageImageViewRow2Option2, matchingImageToImageImageViewRow2Option3, matchingImageToImageImageViewRow2Option4;

    TextView matchingImageToImageTextViewRow1Option1, matchingImageToImageTextViewRow1Option2, matchingImageToImageTextViewRow1Option3, matchingImageToImageTextViewRow1Option4;
    TextView matchingImageToImageTextViewRow2Option1, matchingImageToImageTextViewRow2Option2, matchingImageToImageTextViewRow2Option3, matchingImageToImageTextViewRow2Option4;



    ImageView ImageViewParrotFire, ImageViewAlienAvatarLife, ImageViewParrotAvatarLife, ImageViewAlienFire;
    ImageView ImageViewParrotAvatar, ImageViewAlienAvatar;
    ImageView ImageViewParrotHit, ImageViewAlienHit;
    int realHeight;
    int realWidth;
    AppAnalytics appAnalytics;
    Timer T = new Timer();
    int count = 0;
    boolean start = true;
    float fontSize = 22;

    ArrayList<MatchImageToImageViewModel> imageViewList = new ArrayList<>();
    ArrayList<MatchImageToImageImageTextModel> imageViewTextViewListUp = new ArrayList<>();
    ArrayList<MatchImageToImageImageTextModel> imageViewTextViewListDown = new ArrayList<>();

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

    /*int DEMO_MODE=0;
    ConstraintLayout matchingImageToImageMainLayout;
    int  MatchImageToImageXCoordinate,  MatchImageToImageYCoordinate;
    int  moveToXcoordinate, moveToYcoordinate;
    ImageView handImagview;
    Boolean ordinaryMCQ;
    MediaPlayer mp;
    boolean demoStarted=false;
    String checkLang;
    int voiceSpeaked=0;
     */

    public MatchingImageToImage(int testId, TestJsonParseModel test, Context context, Activity activity) {
        this.testId = testId;
        this.test = test;
        mContext = context;
        this.activity = activity;

        Log.wtf("-this", " TEST ID : " + testId);
    }


    public MatchingImageToImage() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.matching_image_to_image, container, false);
        player = ExoPlayerFactory.newSimpleInstance(getContext());
        sharedPrefs = getActivity().getApplicationContext().getSharedPreferences("DefaultSPForTaleemabadGrade6", 0);
        editor = sharedPrefs.edit();
        appAnalytics = new AppAnalytics(mContext);
        initializeView(view);

        return view;
    }

    public void initializeView(View view) {

        matchingImageToImageTextViewQuestion = view.findViewById(R.id.matchingImageToImageTextViewQuestion);

        matchingImageToImageImageViewRow1Option1 = view.findViewById(R.id.matchingImageToImageImageViewRow1Option1);
        matchingImageToImageImageViewRow1Option2 = view.findViewById(R.id.matchingImageToImageImageViewRow1Option2);
        matchingImageToImageImageViewRow1Option3 = view.findViewById(R.id.matchingImageToImageImageViewRow1Option3);
        matchingImageToImageImageViewRow1Option4 = view.findViewById(R.id.matchingImageToImageImageViewRow1Option4);

        matchingImageToImageImageViewRow2Option1 = view.findViewById(R.id.matchingImageToImageImageViewRow2Option1);
        matchingImageToImageImageViewRow2Option2 = view.findViewById(R.id.matchingImageToImageImageViewRow2Option2);
        matchingImageToImageImageViewRow2Option3 = view.findViewById(R.id.matchingImageToImageImageViewRow2Option3);
        matchingImageToImageImageViewRow2Option4 = view.findViewById(R.id.matchingImageToImageImageViewRow2Option4);

        matchingImageToImageTextViewRow1Option1 = view.findViewById(R.id.matchingImageToImageTextViewRow1Option1);
        matchingImageToImageTextViewRow1Option2 = view.findViewById(R.id.matchingImageToImageTextViewRow1Option2);
        matchingImageToImageTextViewRow1Option3 = view.findViewById(R.id.matchingImageToImageTextViewRow1Option3);
        matchingImageToImageTextViewRow1Option4 = view.findViewById(R.id.matchingImageToImageTextViewRow1Option4);

        matchingImageToImageTextViewRow2Option1 = view.findViewById(R.id.matchingImageToImageTextViewRow2Option1);
        matchingImageToImageTextViewRow2Option2 = view.findViewById(R.id.matchingImageToImageTextViewRow2Option2);
        matchingImageToImageTextViewRow2Option3 = view.findViewById(R.id.matchingImageToImageTextViewRow2Option3);
        matchingImageToImageTextViewRow2Option4 = view.findViewById(R.id.matchingImageToImageTextViewRow2Option4);

        imageViewTextViewListUp.add(new MatchImageToImageImageTextModel(matchingImageToImageImageViewRow1Option1, matchingImageToImageTextViewRow1Option1));
        imageViewTextViewListUp.add(new MatchImageToImageImageTextModel(matchingImageToImageImageViewRow1Option2, matchingImageToImageTextViewRow1Option2));
        imageViewTextViewListUp.add(new MatchImageToImageImageTextModel(matchingImageToImageImageViewRow1Option3, matchingImageToImageTextViewRow1Option3));
        imageViewTextViewListUp.add(new MatchImageToImageImageTextModel(matchingImageToImageImageViewRow1Option4, matchingImageToImageTextViewRow1Option4));

        imageViewTextViewListDown.add(new MatchImageToImageImageTextModel(matchingImageToImageImageViewRow2Option1, matchingImageToImageTextViewRow2Option1));
        imageViewTextViewListDown.add(new MatchImageToImageImageTextModel(matchingImageToImageImageViewRow2Option2, matchingImageToImageTextViewRow2Option2));
        imageViewTextViewListDown.add(new MatchImageToImageImageTextModel(matchingImageToImageImageViewRow2Option3, matchingImageToImageTextViewRow2Option3));
        imageViewTextViewListDown.add(new MatchImageToImageImageTextModel(matchingImageToImageImageViewRow2Option4, matchingImageToImageTextViewRow2Option4));

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
        //matchingImageToImageMainLayout = view.findViewById(R.id.matchingImageToImageMainLayout);

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
                    Log.wtf("-this", "QUESTION SIZE : " + matchingImageToImageTextViewQuestion.getText().toString().length());

                    matchingImageToImageTextViewQuestion.setTextSize(fontSizeQuestion);

                    float MatchTextSize = (0.080f * 1080 / 2);
                    if (screenInches < 7) {
                        MatchTextSize = (0.150f * (1080f / 2.3f));
                    } else {
                        MatchTextSize = (0.150f * 1080f / 2f);
                    }
                    Log.wtf("-this", "height : " + realHeight + "  Match Text Size: " + MatchTextSize);

                    matchingImageToImageTextViewRow1Option1.setTextSize(MatchTextSize);
                    matchingImageToImageTextViewRow1Option2.setTextSize(MatchTextSize);
                    matchingImageToImageTextViewRow1Option3.setTextSize(MatchTextSize);
                    matchingImageToImageTextViewRow1Option4.setTextSize(MatchTextSize);

                    matchingImageToImageTextViewRow2Option1.setTextSize(MatchTextSize);
                    matchingImageToImageTextViewRow2Option2.setTextSize(MatchTextSize);
                    matchingImageToImageTextViewRow2Option3.setTextSize(MatchTextSize);
                    matchingImageToImageTextViewRow2Option4.setTextSize(MatchTextSize);

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
//        SharedPreferences sharedPrefForChecking = getActivity().getSharedPreferences("DemoMatchImageToImage", DEMO_MODE);
//        ordinaryMCQ = sharedPrefForChecking.getBoolean("DemoMatchImageToImage", false); //0 is the default value
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

            //  SimpleTestActivity.testActivityImageViewfeedback.setVisibility(View.INVISIBLE);
            ImageViewParrotAvatarLife.setVisibility(View.INVISIBLE);
            ImageViewParrotAvatar.setVisibility(View.INVISIBLE);
            ImageViewAlienAvatarLife.setVisibility(View.INVISIBLE);
            ImageViewAlienAvatar.setVisibility(View.INVISIBLE);

//                setOnClickListenr(view);
            final Handler handlerr = new Handler();
            handlerr.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    jab();
                    startDemo();
                }
            }, 100);

            SharedPreferences sharedPrefForSaving = getContext().getSharedPreferences("DemoMatchImageToImage", DEMO_MODE);
            SharedPreferences.Editor editor = sharedPrefForSaving.edit();
            editor.putBoolean("DemoMatchImageToImage", true);
            editor.apply();

        }
        else {
            SimpleTestActivity.testActivityImageViewHome.setVisibility(View.VISIBLE);
            SimpleTestActivity.testActivityImageViewDaimond.setVisibility(View.VISIBLE);
          //  SimpleTestActivity.testActivityImageViewfeedback.setVisibility(View.VISIBLE);
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
        if (mp!=null){
            mp.release();
        }
        super.onPause();
    }

 */
/*
    public void startDemoVoice(){
        SharedPreferences checkLanguage = getActivity().getApplicationContext().getSharedPreferences("DefaultSPForTaleemabadGrade6", 0);
        checkLang =checkLanguage.getString("MenuLanguage","en");
        if (checkLang!=null && checkLang.equals("en")){
            mp = MediaPlayer.create(getContext(), R.raw.eng1);

        }else {
            mp = MediaPlayer.create(getContext(), R.raw.urdu1);

        }
//    mp.setLooping(true);
        mp.start();

    }

 */
/*
    public void startDemo(){


        if (mp != null) {
            mp.release();
        }

        startDemoVoice();

        demoStarted =true;

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                voiceSpeaked =voiceSpeaked+1;
                if (voiceSpeaked==2 || voiceSpeaked>1) {
                    mp.release();
                }else {
                    startDemoVoice();
                }
            }
        });

        final ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(matchingImageToImageMainLayout);
        final ImageView imageview = new ImageView(activity);

        imageview.setImageResource(R.drawable.hand);
        imageview.setId(ViewCompat.generateViewId());

        matchingImageToImageMainLayout.addView(imageview);
        constraintSet.connect(imageview.getId(), ConstraintSet.TOP, R.id.guidelineOptionRow1ImageTop,ConstraintSet.TOP,10);
        constraintSet.connect(imageview.getId(),ConstraintSet.END, R.id.guidelineOptionImage3Right,ConstraintSet.START,10);
        constraintSet.connect(imageview.getId(),ConstraintSet.START, R.id.guidelineOptionImage3Left,ConstraintSet.END,10);
        constraintSet.connect(imageview.getId(),ConstraintSet.BOTTOM, R.id.guidelineOptionRow1ImageBottom,ConstraintSet.TOP,10);
        constraintSet.applyTo(matchingImageToImageMainLayout);


        imageViewTextViewListUp.get(2).getTextView().getText().toString();

        for (int i=0; i<imageViewTextViewListDown.size(); i++){
            String textFromUpperRow =imageViewTextViewListUp.get(2).getTextView().getText().toString();
            String textFromLowerRow =imageViewTextViewListDown.get(i).getTextView().getText().toString();

            if (textFromUpperRow.equals(textFromLowerRow)){

                int[] location1 = new int[2];

                imageViewTextViewListDown.get(i).getImageView().getLocationOnScreen(location1);
//                matchingImageToImageImageViewRow2Option4.getLocationOnScreen(location1);
                imageViewTextViewListDown.get(i).getImageView().getLocationOnScreen(location1);
                moveToXcoordinate= location1[0];
                moveToYcoordinate= location1[1];



            }

        }



        final Handler handlerr = new Handler();
        handlerr.postDelayed(new Runnable() {
            @Override
            public void run() {

                int[] location = new int[2];
                matchingImageToImageImageViewRow1Option3.getLocationOnScreen(location);
//                //    Toast.makeText(getContext(),"X axis is "+location[0] +"and Y axis is "+location[1],Toast.LENGTH_LONG).show();

                MatchImageToImageXCoordinate = location[0];
                MatchImageToImageYCoordinate = location[1];

                pressOptionFromUpperRow(imageview);
                //moveto(imageview);

            }
        }, 1200);

    }

 */
/*
    public void pressOptionFromUpperRow(final ImageView imageview){
        imageview.bringToFront();
        imageview.animate()
                .x(MatchImageToImageXCoordinate)
                .y(MatchImageToImageYCoordinate)
                .alpha(1f)
                .scaleX(0.8f)
                .scaleY(0.8f)
                .setDuration(500)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        // animateup();

                        imageview.animate()
                                .x(MatchImageToImageXCoordinate)
                                .y(MatchImageToImageYCoordinate)
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
                });
    }

 */
/*
    public void moveto(final ImageView imageview){

        int[] location1 = new int[2];
//        textViewList.get(0).getTextView().getLocationOnScreen(location1);

        matchingImageToImageImageViewRow2Option4.getLocationOnScreen(location1);
        int movetoX = location1[0];
        int movetoY = location1[1];

        imageview.bringToFront();
        imageview.animate()
                .x(moveToXcoordinate)
                .y(moveToYcoordinate)
                .alpha(1.0f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(500)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        // animateup();

                        if (imageViewList.size()>0){
                            imageview.setVisibility(View.GONE);
                            return;
                        }

                        pressOptionFromLowerRow(imageview);
                        //   movetoback(imageview);

                    }
                });
    }

 */
/*
    public void pressOptionFromLowerRow(final ImageView imageview){
        imageview.bringToFront();
        imageview.animate()
                .alpha(1.0f)
                .scaleX(0.8f)
                .scaleY(0.8f)
                .setDuration(500)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        // animateup();

                        imageview.animate()
                                .alpha(1.0f)
                                .scaleX(1f)
                                .scaleY(1f)
                                .setDuration(500)
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        super.onAnimationEnd(animation);
                                        // animateup();

                                        if (imageViewList.size()>0){
                                            imageview.setVisibility(View.GONE);
                                            return;
                                        }

                                        movetoback(imageview);

                                    }
                                });

                        if (imageViewList.size()>0){
                            imageview.setVisibility(View.GONE);
                            return;
                        }


                    }
                });
    }

 */
/*
    public void movetoback(final ImageView imageview){

        imageview.bringToFront();
        imageview.animate()
                .x(MatchImageToImageXCoordinate)
                .y(MatchImageToImageYCoordinate)
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(500)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        // animateup();

                        pressOptionFromUpperRow(imageview);
//                        moveto(imageview);
                    }
                });
    }

 */


    public void setUpTest() {
       showViews();


    }

    public void showViews() {
        fillUpTest();
        setBackGrounds(sharedPrefs.getString("SupervisedSubjectSelected","English"));

        matchingImageToImageTextViewQuestion.setVisibility(View.VISIBLE);

//        matchingImageToImageImageViewRow1Option1.setVisibility(View.VISIBLE);
//        matchingImageToImageImageViewRow1Option2.setVisibility(View.VISIBLE);
//        matchingImageToImageImageViewRow1Option3.setVisibility(View.VISIBLE);
//        matchingImageToImageImageViewRow1Option4.setVisibility(View.VISIBLE);
//        matchingImageToImageImageViewRow2Option1.setVisibility(View.VISIBLE);
//        matchingImageToImageImageViewRow2Option2.setVisibility(View.VISIBLE);
//        matchingImageToImageImageViewRow2Option3.setVisibility(View.VISIBLE);
//        matchingImageToImageImageViewRow2Option4.setVisibility(View.VISIBLE);

        ImageViewAlienAvatarLife.setVisibility(View.VISIBLE);
        ImageViewParrotAvatarLife.setVisibility(View.VISIBLE);
        ImageViewParrotAvatar.setVisibility(View.VISIBLE);
        ImageViewAlienAvatar.setVisibility(View.VISIBLE);
        setViews();
    }

    public void setBackGrounds(String subject) {
        switch (subject) {
            case ("English"):
                matchingImageToImageImageViewRow1Option1.setBackgroundResource(R.drawable.english_image_bg);
                matchingImageToImageImageViewRow1Option2.setBackgroundResource(R.drawable.english_image_bg);
                matchingImageToImageImageViewRow1Option3.setBackgroundResource(R.drawable.english_image_bg);
                matchingImageToImageImageViewRow1Option4.setBackgroundResource(R.drawable.english_image_bg);
                matchingImageToImageImageViewRow2Option1.setBackgroundResource(R.drawable.english_image_bg);
                matchingImageToImageImageViewRow2Option2.setBackgroundResource(R.drawable.english_image_bg);
                matchingImageToImageImageViewRow2Option3.setBackgroundResource(R.drawable.english_image_bg);
                matchingImageToImageImageViewRow2Option4.setBackgroundResource(R.drawable.english_image_bg);


                break;
            case ("Maths"):
                matchingImageToImageImageViewRow1Option1.setBackgroundResource(R.drawable.maths_image_bg);
                matchingImageToImageImageViewRow1Option2.setBackgroundResource(R.drawable.maths_image_bg);
                matchingImageToImageImageViewRow1Option3.setBackgroundResource(R.drawable.maths_image_bg);
                matchingImageToImageImageViewRow1Option4.setBackgroundResource(R.drawable.maths_image_bg);
                matchingImageToImageImageViewRow2Option1.setBackgroundResource(R.drawable.maths_image_bg);
                matchingImageToImageImageViewRow2Option2.setBackgroundResource(R.drawable.maths_image_bg);
                matchingImageToImageImageViewRow2Option3.setBackgroundResource(R.drawable.maths_image_bg);
                matchingImageToImageImageViewRow2Option4.setBackgroundResource(R.drawable.maths_image_bg);
                break;

            case ("Urdu"):
                matchingImageToImageImageViewRow1Option1.setBackgroundResource(R.drawable.urdu_image_bg);
                matchingImageToImageImageViewRow1Option2.setBackgroundResource(R.drawable.urdu_image_bg);
                matchingImageToImageImageViewRow1Option3.setBackgroundResource(R.drawable.urdu_image_bg);
                matchingImageToImageImageViewRow1Option4.setBackgroundResource(R.drawable.urdu_image_bg);
                matchingImageToImageImageViewRow2Option1.setBackgroundResource(R.drawable.urdu_image_bg);
                matchingImageToImageImageViewRow2Option2.setBackgroundResource(R.drawable.urdu_image_bg);
                matchingImageToImageImageViewRow2Option3.setBackgroundResource(R.drawable.urdu_image_bg);
                matchingImageToImageImageViewRow2Option4.setBackgroundResource(R.drawable.urdu_image_bg);


                break;

            case ("GeneralKnowledge"):
            case ("Science"):
                matchingImageToImageImageViewRow1Option1.setBackgroundResource(R.drawable.science_image_bg);
                matchingImageToImageImageViewRow1Option2.setBackgroundResource(R.drawable.science_image_bg);
                matchingImageToImageImageViewRow1Option3.setBackgroundResource(R.drawable.science_image_bg);
                matchingImageToImageImageViewRow1Option4.setBackgroundResource(R.drawable.science_image_bg);
                matchingImageToImageImageViewRow2Option1.setBackgroundResource(R.drawable.science_image_bg);
                matchingImageToImageImageViewRow2Option2.setBackgroundResource(R.drawable.science_image_bg);
                matchingImageToImageImageViewRow2Option3.setBackgroundResource(R.drawable.science_image_bg);
                matchingImageToImageImageViewRow2Option4.setBackgroundResource(R.drawable.science_image_bg);

                break;

        }
    }

    public void fillUpTest() {
        matchingImageToImageTextViewQuestion.setText(test.getQuestion().getText());

        matchingImageToImageImageViewRow1Option1.setImageBitmap(getBitmapFromAsset(test.getLeftColumnList().get(3).getImage()));
        matchingImageToImageImageViewRow1Option1.setContentDescription(test.getLeftColumnList().get(3).getImage());

        matchingImageToImageImageViewRow1Option2.setImageBitmap(getBitmapFromAsset(test.getLeftColumnList().get(0).getImage()));
        matchingImageToImageImageViewRow1Option2.setContentDescription(test.getLeftColumnList().get(0).getImage());

        matchingImageToImageImageViewRow1Option3.setImageBitmap(getBitmapFromAsset(test.getLeftColumnList().get(2).getImage()));
        matchingImageToImageImageViewRow1Option3.setContentDescription(test.getLeftColumnList().get(2).getImage());

        matchingImageToImageImageViewRow1Option4.setImageBitmap(getBitmapFromAsset(test.getLeftColumnList().get(1).getImage()));
        matchingImageToImageImageViewRow1Option4.setContentDescription(test.getLeftColumnList().get(1).getImage());


        matchingImageToImageImageViewRow2Option1.setImageBitmap(getBitmapFromAsset(test.getRightList().get(1).getImage()));
        matchingImageToImageImageViewRow2Option1.setContentDescription(test.getLeftColumnList().get(1).getImage());

        matchingImageToImageImageViewRow2Option2.setImageBitmap(getBitmapFromAsset(test.getRightList().get(0).getImage()));
        matchingImageToImageImageViewRow2Option2.setContentDescription(test.getLeftColumnList().get(0).getImage());

        matchingImageToImageImageViewRow2Option3.setImageBitmap(getBitmapFromAsset(test.getRightList().get(3).getImage()));
        matchingImageToImageImageViewRow2Option3.setContentDescription(test.getLeftColumnList().get(3).getImage());

        matchingImageToImageImageViewRow2Option4.setImageBitmap(getBitmapFromAsset(test.getRightList().get(2).getImage()));
        matchingImageToImageImageViewRow2Option4.setContentDescription(test.getLeftColumnList().get(2).getImage());

    }

    private Bitmap getBitmapFromAsset(String strName) {
        Log.wtf("-this","Bitmap : " +strName);
        Bitmap bitmap = null;
        File file0 = new File(getContext().getFilesDir(), "/Assets/" + strName + ".png");
        if(file0.exists()){
            bitmap = BitmapFactory.decodeFile(file0.getAbsolutePath());
        } else{
            AssetManager assetManager = mContext.getAssets();
            InputStream istr = null;
            try {
                istr = assetManager.open(strName+".png");
            } catch (IOException e) {
                e.printStackTrace();
            }
            bitmap = BitmapFactory.decodeStream(istr);
        }

        return bitmap;
    }

    public void setOnClickListeners(View view) {

        matchingImageToImageImageViewRow1Option1.setOnTouchListener(this);
        matchingImageToImageImageViewRow1Option2.setOnTouchListener(this);
        matchingImageToImageImageViewRow1Option3.setOnTouchListener(this);
        matchingImageToImageImageViewRow1Option4.setOnTouchListener(this);

        matchingImageToImageImageViewRow2Option1.setOnTouchListener(this);
        matchingImageToImageImageViewRow2Option2.setOnTouchListener(this);
        matchingImageToImageImageViewRow2Option3.setOnTouchListener(this);
        matchingImageToImageImageViewRow2Option4.setOnTouchListener(this);

        matchingImageToImageTextViewRow1Option1.setOnClickListener(this);
        matchingImageToImageTextViewRow1Option2.setOnClickListener(this);
        matchingImageToImageTextViewRow1Option3.setOnClickListener(this);
        matchingImageToImageTextViewRow1Option4.setOnClickListener(this);

        matchingImageToImageTextViewRow2Option1.setOnClickListener(this);
        matchingImageToImageTextViewRow2Option2.setOnClickListener(this);
        matchingImageToImageTextViewRow2Option3.setOnClickListener(this);
        matchingImageToImageTextViewRow2Option4.setOnClickListener(this);

    }


    public boolean onTouch(View view, MotionEvent event) {
        ImageView v = (ImageView) view;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //  Log.wtf("-this", "Touch Action Down");

                if (selectedImageView == null) {
                    Log.wtf("-this", "SelectedView = null 231");
                    selectedImageView = v;
                    for (int i = 0; i < imageViewTextViewListUp.size(); i++) {
                        if (selectedImageView.getId() == imageViewTextViewListUp.get(i).getImageView().getId()) {
                            imageViewTextViewListUp.get(i).getTextView().setVisibility(View.VISIBLE);
                            selectedTextViewUp = imageViewTextViewListUp.get(i).getTextView();
                            Log.wtf("-this", "Up Found 237");
                            upFound = true;
                        }
                    }

                    if (upFound == false) {
                        for (int i = 0; i < imageViewTextViewListDown.size(); i++) {
                            if (selectedImageView.getId() == imageViewTextViewListDown.get(i).getImageView().getId()) {
                                imageViewTextViewListDown.get(i).getTextView().setVisibility(View.VISIBLE);
                                selectedTextViewUp = imageViewTextViewListDown.get(i).getTextView();
                                Log.wtf("-this", "Down Found 247");
                                downFound = true;
                            }
                        }
                    }

                } else {
                    if (selectedImageView.getId() == v.getId()) {
                        Log.wtf("-this", "Selected == V 255");
                    } else {
                        for (int i = 0; i < imageViewList.size(); i++) {
                            if (selectedImageView.getId() == imageViewList.get(i).getImageView1().getId()
                                    || selectedImageView.getId() == imageViewList.get(i).getImageView1().getId()) {

                                Log.wtf("-this", "Already Selected 261");
                                break;
                            }
                        }

                        if (upFound == true) {
                            for (int i = 0; i < imageViewTextViewListDown.size(); i++) {
                                if (v.getId() == imageViewTextViewListDown.get(i).getImageView().getId()) {
                                    imageViewTextViewListDown.get(i).getTextView().setVisibility(View.VISIBLE);
                                    Log.wtf("-this", "Down 2 Found 270");
                                    downFound = true;
                                }
                            }
                        } else if (downFound == true) {
                            for (int i = 0; i < imageViewTextViewListUp.size(); i++) {
                                if (v.getId() == imageViewTextViewListUp.get(i).getImageView().getId()) {
                                    imageViewTextViewListUp.get(i).getTextView().setVisibility(View.VISIBLE);
                                    Log.wtf("-this", "Up 2 Found 270");
                                    upFound = true;
                                }
                            }
                        }

                        if (upFound == true && downFound == true) {
                            Log.wtf("-this", "Both Found 285");
                            imageViewList.add(new MatchImageToImageViewModel(selectedImageView, v));
                            selectedImageView = null;
                        } else {
                            Log.wtf("-this", "Not Found 289");
                            selectedTextViewUp.setVisibility(View.GONE);
                            selectedImageView = null;
                        }
                        upFound = false;
                        downFound = false;

                        Log.wtf("-this", "ELSE END 295");
                    }

                    Log.wtf("-this", "ELSE END 298");
                    updateNumber();
                    if(imageViewList.size() == 4){
                        compileAnse();
                    }
                }

                break;

            case MotionEvent.ACTION_UP:
                //  Log.wtf("-this", "Touch Action Up");
                break;

        }

        return true;
    }

    @Override
    public void onClick(View v) {

            Log.wtf("-this", "OnClick");
            selectedTextView = (TextView) v;
            int selectedIndex = -1;

            for (int j = 0; j < imageViewTextViewListUp.size(); j++) {
                if (selectedTextView.getId() == imageViewTextViewListUp.get(j).getTextView().getId()) {
                    Log.wtf("-this", "Onclick Matched UP  320");
                    for (int i = 0; i < imageViewList.size(); i++) {
                        if (imageViewTextViewListUp.get(j).getImageView().getId() == imageViewList.get(i).getImageView1().getId()
                                || imageViewTextViewListUp.get(j).getImageView().getId() == imageViewList.get(i).getImageView2().getId()) {
                            imageViewTextViewListUp.get(j).getTextView().setVisibility(View.GONE);
                            selectedIndex = i;
                            for (int l = 0; l < imageViewTextViewListDown.size(); l++) {
                                if (imageViewTextViewListDown.get(l).getImageView().getId() == imageViewList.get(i).getImageView1().getId()
                                        || imageViewTextViewListDown.get(l).getImageView().getId() == imageViewList.get(i).getImageView2().getId()) {
                                    imageViewTextViewListDown.get(l).getTextView().setVisibility(View.GONE);
                                }
                            }

                        }
                    }
                }

            }


            for (int j = 0; j < imageViewTextViewListDown.size(); j++) {
                if (selectedTextView.getId() == imageViewTextViewListDown.get(j).getTextView().getId()) {
                    Log.wtf("-this", "Onclick Matched Down  340");
                    for (int i = 0; i < imageViewList.size(); i++) {
                        if (imageViewTextViewListDown.get(j).getImageView().getId() == imageViewList.get(i).getImageView1().getId()
                                || imageViewTextViewListDown.get(j).getImageView().getId() == imageViewList.get(i).getImageView2().getId()) {
                            imageViewTextViewListDown.get(j).getTextView().setVisibility(View.GONE);
                            selectedIndex = i;
                            for (int l = 0; l < imageViewTextViewListUp.size(); l++) {
                                if (imageViewTextViewListUp.get(l).getImageView().getId() == imageViewList.get(i).getImageView1().getId()
                                        || imageViewTextViewListUp.get(l).getImageView().getId() == imageViewList.get(i).getImageView2().getId()) {
                                    imageViewTextViewListUp.get(l).getTextView().setVisibility(View.GONE);
                                }
                            }

                        }
                    }
                }

            }

            if (selectedIndex > -1) {
                imageViewList.remove(selectedIndex);
            }


            selectedTextViewUp.setVisibility(View.GONE);
            selectedImageView = null;
            upFound = false;
            downFound =false;
            updateNumber();

    }

    public void updateNumber() {
        boolean oneFound = false;
        boolean twoFound = false;
        for (int j = 0; j < imageViewList.size(); j++) {

            for (int i = 0; i < imageViewTextViewListUp.size(); i++) {
                if (imageViewList.get(j).getImageView1().getId() == imageViewTextViewListUp.get(i).getImageView().getId()) {
                    imageViewTextViewListUp.get(i).getTextView().setText(""+(j+1));
                    oneFound = true;
                 //   Log.wtf("-this", "updateNumner oneFound = true  381 Index = "+j);
                }
            }

            if(oneFound == true){
                for (int i = 0; i < imageViewTextViewListDown.size(); i++) {
                    if (imageViewList.get(j).getImageView2().getId() == imageViewTextViewListDown.get(i).getImageView().getId()) {
                        imageViewTextViewListDown.get(i).getTextView().setText(""+(j+1));
                    //    Log.wtf("-this", "updateNumner IF  oneFound = true  389 Index = "+j);
                    }
                }
            }

            if(oneFound == false){
                Log.wtf("-this", "updateNumner IF  oneFound = False  395 Index = "+j);
                for (int i = 0; i < imageViewTextViewListDown.size(); i++) {
                    if (imageViewList.get(j).getImageView1().getId() == imageViewTextViewListDown.get(i).getImageView().getId()) {
                        imageViewTextViewListDown.get(i).getTextView().setText(""+(j+1));
                      //  Log.wtf("-this", "updateNumner Down  Found   399 Index = "+j);
                    }
                }
                for (int i = 0; i < imageViewTextViewListUp.size(); i++) {
                    if (imageViewList.get(j).getImageView2().getId() == imageViewTextViewListUp.get(i).getImageView().getId()) {
                        imageViewTextViewListUp.get(i).getTextView().setText(""+(j+1));
                     //   Log.wtf("-this", "updateNumner UP  Found   405");
                    }
                }
            }


            for (int i = 0; i < imageViewTextViewListUp.size(); i++) {
                if (imageViewList.get(j).getImageView2().getId() == imageViewTextViewListUp.get(i).getImageView().getId()) {
                    imageViewTextViewListUp.get(i).getTextView().setText(""+(j+1));
                    twoFound = true;
                 //   Log.wtf("-this", "updateNumner oneFound = true  381 Index = "+j);
                }
            }

            if(twoFound == true){
                for (int i = 0; i < imageViewTextViewListDown.size(); i++) {
                    if (imageViewList.get(j).getImageView1().getId() == imageViewTextViewListDown.get(i).getImageView().getId()) {
                        imageViewTextViewListDown.get(i).getTextView().setText(""+(j+1));
                      //  Log.wtf("-this", "updateNumner IF  oneFound = true  389 Index = "+j);
                    }
                }
            }

            if(twoFound == false){
               // Log.wtf("-this", "updateNumner IF  oneFound = False  395 Index = "+j);
                for (int i = 0; i < imageViewTextViewListDown.size(); i++) {
                    if (imageViewList.get(j).getImageView2().getId() == imageViewTextViewListDown.get(i).getImageView().getId()) {
                        imageViewTextViewListDown.get(i).getTextView().setText(""+(j+1));
                       // Log.wtf("-this", "updateNumner Down  Found   399 Index = "+j);
                    }
                }
                for (int i = 0; i < imageViewTextViewListUp.size(); i++) {
                    if (imageViewList.get(j).getImageView1().getId() == imageViewTextViewListUp.get(i).getImageView().getId()) {
                        imageViewTextViewListUp.get(i).getTextView().setText(""+(j+1));
                      //  Log.wtf("-this", "updateNumner UP  Found   405");
                    }
                }
            }

        }


    }
    public void compileAnse() {

        matchingImageToImageImageViewRow1Option1.setOnTouchListener(null);
        matchingImageToImageImageViewRow1Option2.setOnTouchListener(null);
        matchingImageToImageImageViewRow1Option3.setOnTouchListener(null);
        matchingImageToImageImageViewRow1Option4.setOnTouchListener(null);

        matchingImageToImageImageViewRow2Option1.setOnTouchListener(null);
        matchingImageToImageImageViewRow2Option2.setOnTouchListener(null);
        matchingImageToImageImageViewRow2Option3.setOnTouchListener(null);
        matchingImageToImageImageViewRow2Option4.setOnTouchListener(null);

        matchingImageToImageTextViewRow1Option1.setOnClickListener(null);
        matchingImageToImageTextViewRow1Option2.setOnClickListener(null);
        matchingImageToImageTextViewRow1Option3.setOnClickListener(null);
        matchingImageToImageTextViewRow1Option4.setOnClickListener(null);

        matchingImageToImageTextViewRow2Option1.setOnClickListener(null);
        matchingImageToImageTextViewRow2Option2.setOnClickListener(null);
        matchingImageToImageTextViewRow2Option3.setOnClickListener(null);
        matchingImageToImageTextViewRow2Option4.setOnClickListener(null);

        start = false;
        T.cancel();
        int count = 0;
        int score = 0;
        int tScore = 0;
        if(test.getDifficultyLevel().equalsIgnoreCase("easy")){
            tScore = 2;
        } else if(test.getDifficultyLevel().equalsIgnoreCase("medium")){
            tScore = 5;
        } else if(test.getDifficultyLevel().equalsIgnoreCase("hard")){
            tScore = 7;
        } else if(test.getDifficultyLevel().equalsIgnoreCase("bonus")){
            tScore = 10;
        } else if(test.getDifficultyLevel().equalsIgnoreCase("superEasy")){
            tScore = 1;
        }

        if(score < ((tScore * 4)/2)){
            alienFire();
        } else{
            parrotFire();
            totalScore = totalScore + score;
        }

        totalScore = totalScore + score;

        for (int i = 0; i < imageViewList.size();i++){
            if(imageViewList.get(i).getImageView1().getContentDescription().toString().equalsIgnoreCase(imageViewList.get(i).getImageView2().getContentDescription().toString())){
                if(unSocratic == true)
                appAnalytics.setOptionPlaced(selectedSubject,topic,test.getType(),(imageViewList.get(i).getImageView1().getContentDescription().toString()+","+imageViewList.get(i).getImageView2().getContentDescription().toString()),true);

                score = score + tScore;
                imageViewList.get(i).getImageView1().setBackgroundResource(R.drawable.green_image_bg);
                imageViewList.get(i).getImageView2().setBackgroundResource(R.drawable.green_image_bg);
                count++;
            } else{
                if(unSocratic == true)
                appAnalytics.setOptionPlaced(selectedSubject,topic,test.getType(),(imageViewList.get(i).getImageView1().getContentDescription().toString()+","+imageViewList.get(i).getImageView2().getContentDescription().toString()),false);

                imageViewList.get(i).getImageView1().setBackgroundResource(R.drawable.red_image_bg);
                imageViewList.get(i).getImageView2().setBackgroundResource(R.drawable.red_image_bg);
            }
        }

        if(count == 4){
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
                            .remove(MatchingImageToImage.this).commit();
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
        matchingImageToImageImageViewRow1Option1.setVisibility(View.INVISIBLE);
        matchingImageToImageImageViewRow1Option2.setVisibility(View.INVISIBLE);
        matchingImageToImageImageViewRow1Option3.setVisibility(View.INVISIBLE);
        matchingImageToImageImageViewRow1Option4.setVisibility(View.INVISIBLE);
        matchingImageToImageImageViewRow2Option1.setVisibility(View.INVISIBLE);
        matchingImageToImageImageViewRow2Option2.setVisibility(View.INVISIBLE);
        matchingImageToImageImageViewRow2Option3.setVisibility(View.INVISIBLE);
        matchingImageToImageImageViewRow2Option4.setVisibility(View.INVISIBLE);

        if(selectedSubject.equalsIgnoreCase("English") || selectedSubject.equalsIgnoreCase("Maths") || selectedSubject.equalsIgnoreCase("Science")
                || selectedSubject.equalsIgnoreCase("Geography")){
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    jab();
                    matchingImageToImageImageViewRow1Option1.setVisibility(View.VISIBLE);
                    matchingImageToImageImageViewRow1Option2.setVisibility(View.VISIBLE);
                    matchingImageToImageImageViewRow1Option3.setVisibility(View.VISIBLE);
                    matchingImageToImageImageViewRow1Option4.setVisibility(View.VISIBLE);

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            jab();
                            matchingImageToImageImageViewRow2Option1.setVisibility(View.VISIBLE);
                            matchingImageToImageImageViewRow2Option2.setVisibility(View.VISIBLE);
                            matchingImageToImageImageViewRow2Option3.setVisibility(View.VISIBLE);
                            matchingImageToImageImageViewRow2Option4.setVisibility(View.VISIBLE);

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
                    matchingImageToImageImageViewRow2Option1.setVisibility(View.VISIBLE);
                    matchingImageToImageImageViewRow2Option2.setVisibility(View.VISIBLE);
                    matchingImageToImageImageViewRow2Option3.setVisibility(View.VISIBLE);
                    matchingImageToImageImageViewRow2Option4.setVisibility(View.VISIBLE);

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            jab();
                            matchingImageToImageImageViewRow1Option1.setVisibility(View.VISIBLE);
                            matchingImageToImageImageViewRow1Option2.setVisibility(View.VISIBLE);
                            matchingImageToImageImageViewRow1Option3.setVisibility(View.VISIBLE);
                            matchingImageToImageImageViewRow1Option4.setVisibility(View.VISIBLE);

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
    void setAudioDescription(String filName,TextView textView) {

        player.addListener(new Player.EventListener() {
            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

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
        Log.wtf("-thus","Audio URL OrdinaryMCQ : "+url);
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
