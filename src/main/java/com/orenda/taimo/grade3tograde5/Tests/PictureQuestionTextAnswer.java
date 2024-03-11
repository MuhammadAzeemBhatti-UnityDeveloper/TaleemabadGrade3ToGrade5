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


public class PictureQuestionTextAnswer extends Fragment implements View.OnClickListener {
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;
    TextView pictureQuestionTextAnswerTextViewOption1, pictureQuestionTextAnswerTextViewOption2, pictureQuestionTextAnswerTextViewOption3, pictureQuestionTextAnswerTextViewOption4;

    TextView pictureQuestionTextAnswerTextViewQuestion;
    ImageView pictureQuestionTextAnswerImageViewPicture;

    ImageView ImageViewParrotFire, ImageViewAlienAvatarLife, ImageViewParrotAvatarLife, ImageViewAlienFire;
    ImageView ImageViewParrotAvatar, ImageViewAlienAvatar;
    ImageView ImageViewParrotHit, ImageViewAlienHit;
    TextView TextViewParrotPlus, TextViewAlienPlus;
    int realHeight;
    int realWidth;
    AppAnalytics appAnalytics;
    Timer T = new Timer();
    int count = 0;
    boolean start = true;
    float fontSize = 22;

    int testId = -1;
    TestJsonParseModel test = null;
    Context mContext;
    Activity activity;
    Integer[] arr = new Integer[4];

    private DataSource.Factory dataSourceFactory;
    private ExtractorsFactory extractorsFactory;
    private MediaSource mediaSource;
    private SimpleExoPlayer player;
    private final String streamUrl = "http://cdn.audios.taleemabad.com/QuestionBank/";
    private boolean compileCalled = false;
    private TextView selectedTextView = null;
/*
    int DEMO_MODE = 0;
    ConstraintLayout pictureQuestionTextAnswerMainLayout;
    Boolean ordinaryMCQ;
    MediaPlayer mp;
    boolean demoStarted = false;
    String checkLang;
    boolean stillInDemoState=false;

 */

    public PictureQuestionTextAnswer(int testId, TestJsonParseModel test, Context context, Activity activity) {
        this.testId = testId;
        this.test = test;
        mContext = context;
        this.activity = activity;

        Log.wtf("-this", " TEST ID : " + testId);
    }

    public PictureQuestionTextAnswer() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.picture_question_text_answer, container, false);
        player = ExoPlayerFactory.newSimpleInstance(getContext());
        sharedPrefs = getActivity().getApplicationContext().getSharedPreferences("DefaultSPForTaleemabadGrade6", 0);
        editor = sharedPrefs.edit();
        appAnalytics = new AppAnalytics(mContext);
        initializeView(view);


        return view;
    }

    public void initializeView(View view) {
        pictureQuestionTextAnswerTextViewQuestion = view.findViewById(R.id.pictureQuestionTextAnswerTextViewQuestion);

        pictureQuestionTextAnswerImageViewPicture = view.findViewById(R.id.pictureQuestionTextAnswerImageViewPicture);


        pictureQuestionTextAnswerTextViewOption1 = view.findViewById(R.id.pictureQuestionTextAnswerTextViewOption1);
        pictureQuestionTextAnswerTextViewOption2 = view.findViewById(R.id.pictureQuestionTextAnswerTextViewOption2);
        pictureQuestionTextAnswerTextViewOption3 = view.findViewById(R.id.pictureQuestionTextAnswerTextViewOption3);
        pictureQuestionTextAnswerTextViewOption4 = view.findViewById(R.id.pictureQuestionTextAnswerTextViewOption4);

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
//        pictureQuestionTextAnswerMainLayout = view.findViewById(R.id.pictureQuestionTextAnswerMainLayout);


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
                    Log.wtf("-this", "QUESTION SIZE : " + pictureQuestionTextAnswerTextViewQuestion.getText().toString().length());

                    pictureQuestionTextAnswerTextViewQuestion.setTextSize(fontSizeQuestion);

                    pictureQuestionTextAnswerTextViewOption1.setTextSize(fontSize);
                    pictureQuestionTextAnswerTextViewOption2.setTextSize(fontSize);
                    pictureQuestionTextAnswerTextViewOption3.setTextSize(fontSize);
                    pictureQuestionTextAnswerTextViewOption4.setTextSize(fontSize);

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
//        SharedPreferences sharedPref = getActivity().getSharedPreferences("PictureQuestionTextAnswer", DEMO_MODE);
//        ordinaryMCQ = sharedPref.getBoolean("PictureQuestionTextAnswer", false); //0 is the default value

        setOnClickListeners(view);

    }

/*
    void setUpDemo(){
        if (!ordinaryMCQ) {
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

            // SimpleTestActivity.testActivityImageViewfeedback.setVisibility(View.GONE);

            ImageViewAlienAvatarLife.setVisibility(View.GONE);
            ImageViewParrotAvatarLife.setVisibility(View.GONE);
            ImageViewParrotAvatar.setVisibility(View.GONE);
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

            SharedPreferences sharedPrefForSaving = getContext().getSharedPreferences("PictureQuestionTextAnswer", DEMO_MODE);
            SharedPreferences.Editor editor = sharedPrefForSaving.edit();
            editor.putBoolean("PictureQuestionTextAnswer", true);
            editor.apply();

        } else {
            SimpleTestActivity.testActivityImageViewHome.setVisibility(View.VISIBLE);
            SimpleTestActivity.testActivityImageViewDaimond.setVisibility(View.VISIBLE);
          //  SimpleTestActivity.testActivityImageViewfeedback.setVisibility(View.VISIBLE);
            ImageViewAlienAvatarLife.setVisibility(View.VISIBLE);
            ImageViewParrotAvatarLife.setVisibility(View.VISIBLE);
            ImageViewParrotAvatar.setVisibility(View.VISIBLE);
            ImageViewAlienAvatar.setVisibility(View.VISIBLE);
            demoStarted = false;
        }

    }
 */
/*
    @Override
    public void onDestroy() {
        if (mp != null) {
            mp.release();
        }
        super.onDestroy();
    }

    @Override
    public void onResume() {

        if (demoStarted) {
           // mp.setLooping(true);
            if (checkLang.equals("en")) {
                mp = MediaPlayer.create(getContext(), R.raw.eng1);
                mp.start();
            } else {
                mp = MediaPlayer.create(getContext(), R.raw.urdu1);
                mp.start();

            }
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        stillInDemoState=false;
        if (mp != null) {
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
    public void startDemo() {
        stillInDemoState=true;

//        if (TestActivity.mediaPlayerBg != null) {
//            TestActivity.mediaPlayerBg.release();
//        }

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

                callForDemoVoice();

            }
        });

        final Handler handlerr = new Handler();

        pictureQuestionTextAnswerTextViewOption1.setVisibility(View.VISIBLE);
        pictureQuestionTextAnswerTextViewOption2.setVisibility(View.VISIBLE);
        pictureQuestionTextAnswerTextViewOption3.setVisibility(View.VISIBLE);
        pictureQuestionTextAnswerTextViewOption4.setVisibility(View.VISIBLE);


        String ansText = test.getAnswerList().get(0).getText();

        String option1_Text = pictureQuestionTextAnswerTextViewOption1.getText().toString();
        String option2_Text = pictureQuestionTextAnswerTextViewOption2.getText().toString();
        String option3_Text = pictureQuestionTextAnswerTextViewOption3.getText().toString();
        String option4_Text = pictureQuestionTextAnswerTextViewOption4.getText().toString();


        final ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(pictureQuestionTextAnswerMainLayout);
        final ImageView imageview = new ImageView(activity);
        imageview.setImageResource(R.drawable.hand);
        imageview.setId(ViewCompat.generateViewId());

        if (option1_Text.equals(ansText)) {
            handlerr.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    jab();


                    pictureQuestionTextAnswerMainLayout.addView(imageview);
                    constraintSet.connect(imageview.getId(), ConstraintSet.TOP, R.id.guidelineOption1Top, ConstraintSet.TOP, 10);
                    constraintSet.connect(imageview.getId(), ConstraintSet.END, R.id.guidelineOptionRight, ConstraintSet.START, 10);
                    constraintSet.connect(imageview.getId(), ConstraintSet.START, R.id.guidelineOptionLeft, ConstraintSet.END, 10);
                    constraintSet.connect(imageview.getId(), ConstraintSet.BOTTOM, R.id.guidelineOption1Bottom, ConstraintSet.TOP, 10);
                    constraintSet.applyTo(pictureQuestionTextAnswerMainLayout);
                    imageview.bringToFront();

                    //            tap_icon1.setVisibility(View.VISIBLE);
                    animateHandUpp(1, imageview);

                }
            }, 1200);
            //  tap_icon1.setVisibility(View.VISIBLE);
//            tap_icon2.setVisibility(View.GONE);
//            tap_icon3.setVisibility(View.GONE);
//            tap_icon4.setVisibility(View.GONE);

        } else if (option2_Text.equals(ansText)) {

            //  tap_icon1.setVisibility(View.GONE);
            handlerr.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    jab();
                    //  tap_icon2.setVisibility(View.VISIBLE);


                    pictureQuestionTextAnswerMainLayout.addView(imageview);
                    constraintSet.connect(imageview.getId(), ConstraintSet.TOP, R.id.guidelineOption2Top, ConstraintSet.TOP, 10);
                    constraintSet.connect(imageview.getId(), ConstraintSet.END, R.id.guidelineOptionRight, ConstraintSet.START, 10);
                    constraintSet.connect(imageview.getId(), ConstraintSet.START, R.id.guidelineOptionLeft, ConstraintSet.END, 10);
                    constraintSet.connect(imageview.getId(), ConstraintSet.BOTTOM, R.id.guidelineOption2Bottom, ConstraintSet.TOP, 10);
                    constraintSet.applyTo(pictureQuestionTextAnswerMainLayout);
                    imageview.bringToFront();

                    animateHandUpp(2, imageview);

                }
            }, 1200);
            //tap_icon2.setVisibility(View.VISIBLE);
//            tap_icon3.setVisibility(View.GONE);
//            tap_icon4.setVisibility(View.GONE);

        } else if (option3_Text.equals(ansText)) {

//            tap_icon1.setVisibility(View.GONE);
//            tap_icon2.setVisibility(View.GONE);
//            //tap_icon3.setVisibility(View.VISIBLE);
//            tap_icon4.setVisibility(View.GONE);

            handlerr.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    jab();

                    pictureQuestionTextAnswerMainLayout.addView(imageview);
                    constraintSet.connect(imageview.getId(), ConstraintSet.TOP, R.id.guidelineOption3Top, ConstraintSet.TOP, 10);
                    constraintSet.connect(imageview.getId(), ConstraintSet.END, R.id.guidelineOptionRight, ConstraintSet.START, 10);
                    constraintSet.connect(imageview.getId(), ConstraintSet.START, R.id.guidelineOptionLeft, ConstraintSet.END, 10);
                    constraintSet.connect(imageview.getId(), ConstraintSet.BOTTOM, R.id.guidelineOption3Bottom, ConstraintSet.TOP, 10);
                    constraintSet.applyTo(pictureQuestionTextAnswerMainLayout);
                    imageview.bringToFront();

                    //    tap_icon3.setVisibility(View.VISIBLE);
                    animateHandUpp(3, imageview);

                }
            }, 1200);

        } else if (option4_Text.equals(ansText)) {
            handlerr.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    jab();

                    pictureQuestionTextAnswerMainLayout.addView(imageview);
                    constraintSet.connect(imageview.getId(), ConstraintSet.TOP, R.id.guidelineOption4Top, ConstraintSet.TOP, 10);
                    constraintSet.connect(imageview.getId(), ConstraintSet.END, R.id.guidelineOptionRight, ConstraintSet.START, 10);
                    constraintSet.connect(imageview.getId(), ConstraintSet.START, R.id.guidelineOptionLeft, ConstraintSet.END, 10);
                    constraintSet.connect(imageview.getId(), ConstraintSet.BOTTOM, R.id.guidelineOption4Bottom, ConstraintSet.TOP, 10);
                    constraintSet.applyTo(pictureQuestionTextAnswerMainLayout);
                    imageview.bringToFront();
                    //tap_icon4.setVisibility(View.VISIBLE);
                    animateHandUpp(4, imageview);


                }
            }, 1200);
//            tap_icon1.setVisibility(View.GONE);
//            tap_icon2.setVisibility(View.GONE);
//            tap_icon3.setVisibility(View.GONE);
            //tap_icon4.setVisibility(View.VISIBLE);
        }
    }


    public void animateHandUpp(int icon, final ImageView imageView) {
//        ordinaryMCQTextViewOption1.setVisibility(View.VISIBLE);
//        ordinaryMCQTextViewOption2.setVisibility(View.VISIBLE);
//        ordinaryMCQTextViewOption3.setVisibility(View.VISIBLE);
//        ordinaryMCQTextViewOption4.setVisibility(View.VISIBLE);
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


        if (icon == 1) {
            pictureQuestionTextAnswerTextViewOption1.animate()
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

        } else if (icon == 2) {
            pictureQuestionTextAnswerTextViewOption2.animate()
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
        } else if (icon == 3) {
            pictureQuestionTextAnswerTextViewOption3.animate()
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
                            animateHandDownn(3, imageView);
                        }
                    });
        } else if (icon == 4) {
            pictureQuestionTextAnswerTextViewOption4.animate()
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
                            animateHandDownn(4, imageView);
                        }
                    });
        }
    }

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
                if (icon == 1) {
                    pictureQuestionTextAnswerTextViewOption1.animate()
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
                                    animateHandUpp(1, imageView);
                                }
                            });

                } else if (icon == 2) {
                    pictureQuestionTextAnswerTextViewOption2.animate()
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
                                    animateHandUpp(2, imageView);
                                }
                            });
                } else if (icon == 3) {
                    pictureQuestionTextAnswerTextViewOption3.animate()
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
                                    animateHandUpp(3, imageView);
                                }
                            });
                } else if (icon == 4) {
                    pictureQuestionTextAnswerTextViewOption4.animate()
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
                                    //   animatetvup();

                                    animateHandUpp(4, imageView);
                                }
                            });
                }
            }
        }, 200);

    }

 */

    public void setUpTest() {
        showViews();

    }

    public void showViews() {
        fillUpTest();
        setBackGrounds(sharedPrefs.getString("SupervisedSubjectSelected", "English"));
        pictureQuestionTextAnswerTextViewQuestion.setVisibility(View.VISIBLE);
//        pictureQuestionTextAnswerTextViewOption1.setVisibility(View.VISIBLE);
//        pictureQuestionTextAnswerTextViewOption2.setVisibility(View.VISIBLE);
//        pictureQuestionTextAnswerTextViewOption3.setVisibility(View.VISIBLE);
//        pictureQuestionTextAnswerTextViewOption4.setVisibility(View.VISIBLE);
        pictureQuestionTextAnswerImageViewPicture.setVisibility(View.VISIBLE);

        ImageViewAlienAvatarLife.setVisibility(View.VISIBLE);
        ImageViewParrotAvatarLife.setVisibility(View.VISIBLE);
        ImageViewParrotAvatar.setVisibility(View.VISIBLE);
        ImageViewAlienAvatar.setVisibility(View.VISIBLE);
        setViews();

    }

    public void setBackGrounds(String subject) {
        switch (subject) {
            case ("English"):
                pictureQuestionTextAnswerTextViewOption1.setBackgroundResource(R.drawable.english_bg);
                pictureQuestionTextAnswerTextViewOption2.setBackgroundResource(R.drawable.english_bg);
                pictureQuestionTextAnswerTextViewOption3.setBackgroundResource(R.drawable.english_bg);
                pictureQuestionTextAnswerTextViewOption4.setBackgroundResource(R.drawable.english_bg);

                break;
            case ("Maths"):
                pictureQuestionTextAnswerTextViewOption1.setBackgroundResource(R.drawable.maths_bg);
                pictureQuestionTextAnswerTextViewOption2.setBackgroundResource(R.drawable.maths_bg);
                pictureQuestionTextAnswerTextViewOption3.setBackgroundResource(R.drawable.maths_bg);
                pictureQuestionTextAnswerTextViewOption4.setBackgroundResource(R.drawable.maths_bg);
                break;

            case ("Urdu"):
                pictureQuestionTextAnswerTextViewOption1.setBackgroundResource(R.drawable.urdu_bg);
                pictureQuestionTextAnswerTextViewOption2.setBackgroundResource(R.drawable.urdu_bg);
                pictureQuestionTextAnswerTextViewOption3.setBackgroundResource(R.drawable.urdu_bg);
                pictureQuestionTextAnswerTextViewOption4.setBackgroundResource(R.drawable.urdu_bg);

                break;

            case ("GeneralKnowledge"):
            case ("Science"):
                pictureQuestionTextAnswerTextViewOption1.setBackgroundResource(R.drawable.science_bg);
                pictureQuestionTextAnswerTextViewOption2.setBackgroundResource(R.drawable.science_bg);
                pictureQuestionTextAnswerTextViewOption3.setBackgroundResource(R.drawable.science_bg);
                pictureQuestionTextAnswerTextViewOption4.setBackgroundResource(R.drawable.science_bg);
                break;

        }
    }

    public void fillUpTest() {
        generateRandom();
        pictureQuestionTextAnswerTextViewQuestion.setText(test.getQuestion().getText());
        pictureQuestionTextAnswerImageViewPicture.setImageBitmap(getBitmapFromAsset(test.getQuestion().getImage()));
        pictureQuestionTextAnswerTextViewOption1.setText(test.getOptionList().get(arr[0]).getText());
        pictureQuestionTextAnswerTextViewOption2.setText(test.getOptionList().get(arr[1]).getText());
        pictureQuestionTextAnswerTextViewOption3.setText(test.getOptionList().get(arr[2]).getText());
        pictureQuestionTextAnswerTextViewOption4.setText(test.getOptionList().get(arr[3]).getText());

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

    public void setOnClickListeners(View view) {

        pictureQuestionTextAnswerTextViewOption1.setOnClickListener(this);
        pictureQuestionTextAnswerTextViewOption2.setOnClickListener(this);
        pictureQuestionTextAnswerTextViewOption3.setOnClickListener(this);
        pictureQuestionTextAnswerTextViewOption4.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pictureQuestionTextAnswerTextViewOption1:
                pictureQuestionTextAnswerTextViewOption1.setOnClickListener(null);
                pictureQuestionTextAnswerTextViewOption2.setOnClickListener(null);
                pictureQuestionTextAnswerTextViewOption3.setOnClickListener(null);
                pictureQuestionTextAnswerTextViewOption4.setOnClickListener(null);
                String uidAudio = topic+"Question"+(tempIndex+1)+"Option"+(arr[0]+1)+"Audio.mp3";
                selectedTextView = pictureQuestionTextAnswerTextViewOption1;
                if (unSocratic == true) {
                    // setAudioDescription(uidAudio, ordinaryMCQTextViewOption1);
                    File myDirectory = new File(getContext().getFilesDir(), "TestAudios");
                    File file = new File(myDirectory,uidAudio);
                    if(file.exists()){
                        Log.wtf("-thus","File Exits  : "+uidAudio);
                        playOfflineAudio(Uri.fromFile(file));
                    } else {
                        compileAns(selectedTextView);
                        Log.wtf("-thus","File DOES NOT Exits  : "+uidAudio + "  URI  "+Uri.fromFile(file));
                    }

                    //  playerOption1.setPlayWhenReady(true);
                } else {
                    compileAns(pictureQuestionTextAnswerTextViewOption1);
                }

                break;
            case R.id.pictureQuestionTextAnswerTextViewOption2:
                pictureQuestionTextAnswerTextViewOption1.setOnClickListener(null);
                pictureQuestionTextAnswerTextViewOption2.setOnClickListener(null);
                pictureQuestionTextAnswerTextViewOption3.setOnClickListener(null);
                pictureQuestionTextAnswerTextViewOption4.setOnClickListener(null);
                String uidAudio1 = topic+"Question"+(tempIndex+1)+"Option"+(arr[1]+1)+"Audio.mp3";
                selectedTextView = pictureQuestionTextAnswerTextViewOption2;
                if (unSocratic == true) {
                    // setAudioDescription(uidAudio, ordinaryMCQTextViewOption1);
                    File myDirectory = new File(getContext().getFilesDir(), "TestAudios");
                    File file = new File(myDirectory,uidAudio1);
                    if(file.exists()){
                        Log.wtf("-thus","File Exits  : "+uidAudio1);
                        playOfflineAudio(Uri.fromFile(file));
                    } else {
                        compileAns(selectedTextView);
                        Log.wtf("-thus","File DOES NOT Exits  : "+uidAudio1 + "  URI  "+Uri.fromFile(file));
                    }

                    //  playerOption1.setPlayWhenReady(true);
                } else {
                    compileAns(pictureQuestionTextAnswerTextViewOption2);
                }

                break;
            case R.id.pictureQuestionTextAnswerTextViewOption3:
                pictureQuestionTextAnswerTextViewOption1.setOnClickListener(null);
                pictureQuestionTextAnswerTextViewOption2.setOnClickListener(null);
                pictureQuestionTextAnswerTextViewOption3.setOnClickListener(null);
                pictureQuestionTextAnswerTextViewOption4.setOnClickListener(null);
                String uidAudio2 = topic+"Question"+(tempIndex+1)+"Option"+(arr[2]+1)+"Audio.mp3";
                selectedTextView = pictureQuestionTextAnswerTextViewOption3;
                if (unSocratic == true) {
                    // setAudioDescription(uidAudio, ordinaryMCQTextViewOption1);
                    File myDirectory = new File(getContext().getFilesDir(), "TestAudios");
                    File file = new File(myDirectory,uidAudio2);
                    if(file.exists()){
                        Log.wtf("-thus","File Exits  : "+uidAudio2);
                        playOfflineAudio(Uri.fromFile(file));
                    } else {
                        compileAns(selectedTextView);
                        Log.wtf("-thus","File DOES NOT Exits  : "+uidAudio2 + "  URI  "+Uri.fromFile(file));
                    }

                    //  playerOption1.setPlayWhenReady(true);
                } else {
                    compileAns(pictureQuestionTextAnswerTextViewOption3);
                }
                break;
            case R.id.pictureQuestionTextAnswerTextViewOption4:
                pictureQuestionTextAnswerTextViewOption1.setOnClickListener(null);
                pictureQuestionTextAnswerTextViewOption2.setOnClickListener(null);
                pictureQuestionTextAnswerTextViewOption3.setOnClickListener(null);
                pictureQuestionTextAnswerTextViewOption4.setOnClickListener(null);
                String uidAudio3 = topic+"Question"+(tempIndex+1)+"Option"+(arr[3]+1)+"Audio.mp3";
                selectedTextView = pictureQuestionTextAnswerTextViewOption4;
                if (unSocratic == true) {
                    // setAudioDescription(uidAudio, ordinaryMCQTextViewOption1);
                    File myDirectory = new File(getContext().getFilesDir(), "TestAudios");
                    File file = new File(myDirectory,uidAudio3);
                    if(file.exists()){
                        Log.wtf("-thus","File Exits  : "+uidAudio3);
                        playOfflineAudio(Uri.fromFile(file));
                    } else {
                        compileAns(selectedTextView);
                        Log.wtf("-thus","File DOES NOT Exits  : "+uidAudio3 + "  URI  "+Uri.fromFile(file));
                    }

                    //  playerOption1.setPlayWhenReady(true);
                } else {
                    compileAns(pictureQuestionTextAnswerTextViewOption4);
                }
                break;
            default:
                break;

        }
    }

    public void compileAns(TextView txtView) {
//        stillInDemoState=false;

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

        if (txtView.getText().toString().equalsIgnoreCase(test.getAnswerList().get(0).getText())) {
            if(unSocratic == true) {
                appAnalytics.setOptionSelected(selectedSubject, topic, test.getType(), txtView.getText().toString(), true);

                appAnalytics.setAnswer(selectedSubject, topic + "" + testIndex, topic, test.getType(), test.getAnswerList().size(), 1, true, this.count);
            }
            score = tScore;
            correctCount++;
            totalScore = totalScore + score;
            txtView.setBackgroundResource(R.drawable.match_ttt_background_green);
            parrotFire();
        } else {
            if(unSocratic == true) {
                appAnalytics.setOptionSelected(selectedSubject, topic, test.getType(), txtView.getText().toString(), false);
                appAnalytics.setAnswer(selectedSubject, topic + "" + testIndex, topic, test.getType(), test.getAnswerList().size(), 0, false, this.count);
            }
            txtView.setBackgroundResource(R.drawable.match_ttt_background_red);
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
                            .remove(PictureQuestionTextAnswer.this).commit();
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
        pictureQuestionTextAnswerTextViewOption1.setVisibility(View.INVISIBLE);
        pictureQuestionTextAnswerTextViewOption2.setVisibility(View.INVISIBLE);
        pictureQuestionTextAnswerTextViewOption3.setVisibility(View.INVISIBLE);
        pictureQuestionTextAnswerTextViewOption4.setVisibility(View.INVISIBLE);

        if (selectedSubject.equalsIgnoreCase("English") || selectedSubject.equalsIgnoreCase("Maths") || selectedSubject.equalsIgnoreCase("Science")
                || selectedSubject.equalsIgnoreCase("Geography")) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    jab();
                    pictureQuestionTextAnswerTextViewOption1.setVisibility(View.VISIBLE);

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            jab();
                            pictureQuestionTextAnswerTextViewOption2.setVisibility(View.VISIBLE);

                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    jab();
                                    pictureQuestionTextAnswerTextViewOption3.setVisibility(View.VISIBLE);
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            jab();
                                            pictureQuestionTextAnswerTextViewOption4.setVisibility(View.VISIBLE);

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
                    pictureQuestionTextAnswerTextViewOption4.setVisibility(View.VISIBLE);

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            jab();
                            pictureQuestionTextAnswerTextViewOption3.setVisibility(View.VISIBLE);

                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    jab();
                                    pictureQuestionTextAnswerTextViewOption2.setVisibility(View.VISIBLE);
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            jab();
                                            pictureQuestionTextAnswerTextViewOption1.setVisibility(View.VISIBLE);

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
                    if(compileCalled == false) {
                        compileCalled = true;
                        compileAns(selectedTextView);
                    }
                }
            }
        });

    }



}
