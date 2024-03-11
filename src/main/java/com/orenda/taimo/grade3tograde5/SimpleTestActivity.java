package com.orenda.taimo.grade3tograde5;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
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
import com.orenda.taimo.grade3tograde5.Models.AnwserModel;
import com.orenda.taimo.grade3tograde5.Models.ColumnImagesModel;
import com.orenda.taimo.grade3tograde5.Models.ExplainationModel;
import com.orenda.taimo.grade3tograde5.Models.OptionsModel;
import com.orenda.taimo.grade3tograde5.Models.QuestionModel;
import com.orenda.taimo.grade3tograde5.Models.TestJsonParseModel;
import com.orenda.taimo.grade3tograde5.Tests.BigPictureQuestionTextAnswer;
import com.orenda.taimo.grade3tograde5.Tests.BigPictureQuestionTextAnswerTwoOptions;
import com.orenda.taimo.grade3tograde5.Tests.ComprehensionFourChoices;
import com.orenda.taimo.grade3tograde5.Tests.ComprehensionTwoChoices;
import com.orenda.taimo.grade3tograde5.Tests.ComprehensionTwoChoicesQuestionImage;
import com.orenda.taimo.grade3tograde5.Tests.DragTextBoxToPicture;
import com.orenda.taimo.grade3tograde5.Tests.DragTextBoxToPictureTwoOptions;
import com.orenda.taimo.grade3tograde5.Tests.FillInTheBlank;
import com.orenda.taimo.grade3tograde5.Tests.FillInTheBlankEnglish;
import com.orenda.taimo.grade3tograde5.Tests.FillInTheBlankFourOptions;
import com.orenda.taimo.grade3tograde5.Tests.FillInTheBlankFourOptionsEnglish;
import com.orenda.taimo.grade3tograde5.Tests.Labelling;
import com.orenda.taimo.grade3tograde5.Tests.LabellingTwoOptions;
import com.orenda.taimo.grade3tograde5.Tests.MatchTextToText;
import com.orenda.taimo.grade3tograde5.Tests.MatchTextToTextTwoOptions;
import com.orenda.taimo.grade3tograde5.Tests.MatchingImageToImage;
import com.orenda.taimo.grade3tograde5.Tests.MathsAddition;
import com.orenda.taimo.grade3tograde5.Tests.MathsMcq;
import com.orenda.taimo.grade3tograde5.Tests.MathsSubtration;
import com.orenda.taimo.grade3tograde5.Tests.OrdinaryMCQ;
import com.orenda.taimo.grade3tograde5.Tests.OrdinaryMCQAudio;
import com.orenda.taimo.grade3tograde5.Tests.OrdinaryMCQAudioTwoOptions;
import com.orenda.taimo.grade3tograde5.Tests.OrdinaryMCQLong;
import com.orenda.taimo.grade3tograde5.Tests.OrdinaryMCQTwoOptions;
import com.orenda.taimo.grade3tograde5.Tests.OrdinaryMCQTwoOptionsLong;
import com.orenda.taimo.grade3tograde5.Tests.PictureQuestionTextAnswer;
import com.orenda.taimo.grade3tograde5.Tests.PictureQuestionTextAnswerTwoOptions;
import com.orenda.taimo.grade3tograde5.Tests.Picture_Text_Question_Picture_Answer;
import com.orenda.taimo.grade3tograde5.Tests.Picture_Text_Question_Picture_Answer_Two_Options;
import com.orenda.taimo.grade3tograde5.Tests.SoundRecognitionSpelling2Options;
import com.orenda.taimo.grade3tograde5.Tests.SoundRecognitionSpelling4Options;
import com.orenda.taimo.grade3tograde5.Tests.WordProblem;
import com.unity3d.player.UnityPlayer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

import firebase.analytics.AppAnalytics;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
//import static com.facebook.FacebookSdk.getApplicationContext;

public class SimpleTestActivity extends AppCompatActivity implements View.OnClickListener {

    FrameLayout fragmentContainer;
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;
    public ArrayList<TestJsonParseModel> testList = null;
    ConstraintLayout testActivityMainLayout, testActivityPopUpLayout;
    public static String selectedSubject = "";
    public static String selectedGrade = "";
    public static int testIndex = 0;
    ImageView testActivityImageViewBG, testActivityImageViewPopUpClose, testActivityImageViewPopUpParrot;
    TextView testActivityTextViewQuestionBig, testActivityTextViewContinue;
    ImageView testActivityImageViewInPopUpImageImageOnly, testActivityImageViewInPopUpImageImage;
    TextView testActivityTextViewInPopUpOnlyText, testActivityTextViewInPopUpImageText;
    public static ImageView testActivityImageViewHome, testActivityImageViewDaimond;

    TestJsonParseModel test = null;
    String type = null;
    private float fontSize = 22;
    public static String topic = "";
    String topicName = "";
    String subTopicName = "";
    int testId = -1;
    int questionAttempt = 0;
    public static int tempIndex = 0;
    public static int alienLife = 100;
    public static int parrotLife = 100;
    public static int deduct = 11;
    AppAnalytics appAnalytics;
    Timer T = new Timer();
    int time = 0;
    boolean start = true;
    public static int correctCount = 0;
    public static int totalScore = 0;
    public static String question_id = null;
    MediaPlayer mediaPlayerBg;
    private boolean testPaused = false;
    boolean expActive = false;
    boolean bigQuesActive = false;
    int totalscoreDB = 160;
    public static boolean unSocratic = false;

    int start_counter = 0, end_counter = 0;
    boolean playSound = false;
    private String source = "";

    private DataSource.Factory dataSourceFactory;
    private ExtractorsFactory extractorsFactory;
    private MediaSource mediaSource;
    private SimpleExoPlayer player;
    private final String streamUrlTest = "http://cdn.audios.taleemabad.com/QuestionBank/Grade1EnglishComprehensionWhoIsOutsideQuestion1QuestionAudio.mp3";
    private final String streamUrl = "http://cdn.audios.taleemabad.com/QuestionBank/";
    private static final int PERMISSION_REQUEST_CODE = 200;
    private boolean continueClicked =  false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
//                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_test);

        FullScreen();
        if(getIntent()!=null && getIntent().hasExtra("NotificationId")){
            new AppAnalytics(getApplicationContext()).CustomNotificationOpen(getIntent().getIntExtra("NotificationId",0),
                    getIntent().getStringExtra("NotificationTime"),getIntent().getStringExtra("NotificationType"));
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.cancel(getIntent().getIntExtra("NotificationId",0)); //closes notification
        }
        //ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);


        testList = new ArrayList<>();
        test = null;
        selectedGrade = getIntent().getStringExtra("GradeSelected");
        selectedSubject = getIntent().getStringExtra("SubjectSelected");
        if(selectedSubject.equals("Math")){
            selectedSubject = "Maths";
        }
        topic = getIntent().getStringExtra("Alpha");
        source = getIntent().getStringExtra("source");
        sharedPrefs = getApplicationContext().getSharedPreferences("DefaultSPForTaleemabadGrade6", 0);
        appAnalytics = new AppAnalytics(this);
        appAnalytics.setCurrentScreenName("Test", "TestActivity", this);
        //bgAudio();
        /*SharedPreferences sharedPrefs1 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if(sharedPrefs1.getBoolean("MusicEnabled",true)){
            createbgAudio();
        }*/
        initializeViews();
    }

    public void initializeViews() {
        testActivityImageViewHome = findViewById(R.id.testActivityImageViewHome);
        testActivityImageViewDaimond = findViewById(R.id.testActivityImageViewDaimond);

        fragmentContainer = findViewById(R.id.TestActivityfragmentContainer);
        testActivityMainLayout = findViewById(R.id.testActivityMainLayout);
        testActivityImageViewBG = findViewById(R.id.testActivityImageViewBG);
        testActivityImageViewPopUpClose = findViewById(R.id.testActivityImageViewPopUpClose);
        testActivityImageViewPopUpParrot = findViewById(R.id.testActivityImageViewPopUpParrot);

        testActivityPopUpLayout = findViewById(R.id.testActivityPopUpLayout);

        testActivityImageViewInPopUpImageImageOnly = findViewById(R.id.testActivityImageViewInPopUpImageImageOnly);
        testActivityTextViewInPopUpOnlyText = findViewById(R.id.testActivityTextViewInPopUpOnlyText);
        testActivityImageViewInPopUpImageImage = findViewById(R.id.testActivityImageViewInPopUpImageImage);
        testActivityTextViewInPopUpImageText = findViewById(R.id.testActivityTextViewInPopUpImageText);

        testActivityTextViewQuestionBig = findViewById(R.id.testActivityTextViewQuestionBig);
        testActivityTextViewContinue = findViewById(R.id.testActivityTextViewContinue);
        testActivityTextViewContinue.setOnClickListener(this);
        testActivityImageViewPopUpClose.setOnClickListener(this);
        testActivityImageViewHome.setOnClickListener(this);
        testActivityImageViewDaimond.setOnClickListener(this);

        testActivityMainLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                final DisplayMetrics metrics = new DisplayMetrics();
                final WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
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
                fontSize = (0.028f * realHeight);
                if (screenInches < 7) {
                    fontSize = (0.028f * (1080f / (1.8f)));
                } else {
                    fontSize = (0.028f * 800f);
                }

                //    Log.wtf("-this", "height : " + realHeight + "  Width : " + realWidth + " Inches : " + screenInches);

                float questionBigFontSize = (0.070f * 1080 / 2);
                if (screenInches < 7) {
                    questionBigFontSize = (0.060f * (1080f / 2.3f));
                } else {
                    questionBigFontSize = (0.060f * 1080 / 2);
                }
                //   Log.wtf("-this", "height : " + realHeight + "  QuestionBig  FONT SIZE : " + questionBigFontSize);

                testActivityTextViewQuestionBig.setTextSize(questionBigFontSize);

                testActivityMainLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        setBackGround(selectedSubject);
        getTestData(topic, selectedSubject);
        if (source.equalsIgnoreCase("socratic")) {

            appAnalytics.setTestStart(testList.size(), test.getTopicName());
        } else {
            appAnalytics.setSimpleTestStart(testList.size(), test.getTopicName(),topic,selectedGrade,selectedSubject);
        }

        start_counter++;
        editor = sharedPrefs.edit();
        editor.putInt("start_count", start_counter);
        int val = sharedPrefs.getInt("start_count", 0);
        editor.putInt("NumberOfTestStarted", val);
        editor.apply();
        Log.wtf("counter_check", "test start counter :" + sharedPrefs.getInt("NumberOfTestStarted", 0));


    }

    public void setBackGround(String subject) {
        Log.wtf("-this", "TestAct Selected Subject : " + subject);
        switch (subject) {
            case ("English"):
                testActivityMainLayout.setBackgroundResource(R.mipmap.bg_pink);
                testActivityPopUpLayout.setBackgroundResource(R.drawable.english_popup);
                break;
            case ("Maths"):
                testActivityMainLayout.setBackgroundResource(R.mipmap.bg_green_b);
                testActivityPopUpLayout.setBackgroundResource(R.drawable.maths_popup);
                break;
            case ("Urdu"):
                testActivityMainLayout.setBackgroundResource(R.mipmap.bg_purple);
                testActivityPopUpLayout.setBackgroundResource(R.drawable.urdu_popup);
                break;
            case ("GeneralKnowledge"):
                testActivityMainLayout.setBackgroundResource(R.mipmap.bg_blue_s);
                testActivityPopUpLayout.setBackgroundResource(R.drawable.science_popup);
                break;
            default:
                testActivityMainLayout.setBackgroundResource(R.mipmap.bg_green);
                testActivityPopUpLayout.setBackgroundResource(R.drawable.geography_popup);
                break;
        }
        editor = sharedPrefs.edit();
        editor.putString("SupervisedSubjectSelected", subject);
        editor.apply();
    }

    public void setBackGroundsHigher(String subject) {
        switch (subject) {
            case ("English"):
                // ordinaryMCQMainLayout.setBackgroundResource(R.mipmap.bg_pink);
                testActivityMainLayout.setBackgroundResource(R.mipmap.bg_pink);
                testActivityPopUpLayout.setBackgroundResource(R.drawable.english_popup);

                break;
            case ("Physics"):
                //   ordinaryMCQMainLayout.setBackgroundResource(R.mipmap.bg_green_b);
                testActivityMainLayout.setBackgroundResource(R.mipmap.bg_green_b);
                testActivityPopUpLayout.setBackgroundResource(R.drawable.maths_popup);
                break;

            case ("Pakistan Studies"):
                //   ordinaryMCQMainLayout.setBackgroundResource(R.mipmap.bg_green);
                testActivityMainLayout.setBackgroundResource(R.mipmap.bg_green);
                testActivityPopUpLayout.setBackgroundResource(R.drawable.geography_popup);
                break;

            case ("Biology"):
                //   ordinaryMCQMainLayout.setBackgroundResource(R.mipmap.bg_orange);
                testActivityMainLayout.setBackgroundResource(R.mipmap.bg_orange);
                testActivityPopUpLayout.setBackgroundResource(R.drawable.history_popup);
                break;
            case ("Chemistry"):
                //    ordinaryMCQMainLayout.setBackgroundResource(R.mipmap.bg_blue);
                testActivityMainLayout.setBackgroundResource(R.mipmap.bg_blue_s);
                testActivityPopUpLayout.setBackgroundResource(R.drawable.science_popup);
                break;

        }
    }

    public void setBigQuestion(String question) {
        bgAudioPause();
        bigQuesActive = true;
        continueClicked = false;
        start = true;
        startTestTimer();
        String temp = question;
        if (type.equalsIgnoreCase("FillInTheBlank2Options") || type.equalsIgnoreCase("FillInTheBlank")) {
            temp = question.replaceAll("_", "_______");
        }
        testActivityTextViewQuestionBig.setText(temp);
        testActivityTextViewQuestionBig.setVisibility(View.VISIBLE);
        testActivityTextViewContinue.setVisibility(View.VISIBLE);
        appAnalytics.setBigQuestionStart(selectedSubject, topic + "" + testIndex, test.getTopicName(), test.getType(), test.getAnswerList().size(), test.getDifficultyLevel() + "", (tempIndex + 1), (testIndex + 1));

        String uidAudio = topic + "Question" + (tempIndex + 1) + "QuestionAudio";
        setAudioDescription(uidAudio);
    }
    public void continueClicked() {

        Log.wtf("SimpleTestActivity", "Continue Clicked Method");

        Log.wtf("-this","Continue Clicked 1 ");

        if(continueClicked == false) {
            Log.wtf("SimpleTestActivity", "Continue Clicked true");
            Log.wtf("-this","Continue Clicked 2 ");
            continueClicked = true;
            bigQuesActive = false;
            if (player != null) {
                player.setPlayWhenReady(false);
                player.release();
            }
            bgAudio();
            // bgAudioUnPause();
            start = false;
            Log.wtf("Testing",time+" "+topic+" "+testIndex);
            appAnalytics.setBigQuestionEvent(time, topic + "" + testIndex, topic);
            testActivityTextViewQuestionBig.setVisibility(View.GONE);
            testActivityTextViewContinue.setVisibility(View.GONE);

            if (test != null) {
                Log.wtf("SimpleTestActivity", "Test not null");
                setTestFragment(type, testId, test);
            }
            else {
                Log.wtf("SimpleTestActivity", "Test is null");
            }

        }

    }

    public void setExplanation(int score) {
        Log.wtf("TestActivity_", "set Explanation  : " + score);
        expActive = true;
        bgAudioPause();
        start = true;
        startTestTimer();

        Log.wtf("-this", "Return Score : " + score);
//        if (sharedPrefs.getString("GradeSelected", "Grade 6").equalsIgnoreCase("Grade 6")) {
//            if (testId != -1) {
//                DatabaseHelper.getInstance(getApplicationContext()).UpdateQuestionMarks(testId, questionAttempt, score, (topic + "" + (tempIndex)), selectedSubject, topicName, totalscoreDB);
//            }
//            if(questionAttempt < 9){
//                questionAttempt++;
//            }
//
//        } else {
//            totalscoreDB = (testList.size() * 10);
//            //   DatabaseHelper.getInstance(getApplicationContext()).UpdateQuestionMarks(testId, questionAttempt, score, (topic + "" + (tempIndex)), selectedSubject, topicName,totalscoreDB);
//            syncGradeHigher(score + " ");
//            DatabaseHelper.getInstance(this).updateGradeMarksGradeHigher(topic, totalscoreDB, (correctCount * 10));
//            questionAttempt++;
//        }


        questionAttempt++;

//        if (sharedPrefs.getString("GradeSelected", "Grade 6").equalsIgnoreCase("Grade 6")) {
//
//            String expText = null;
//            String expImage = null;
//            expText = test.getExplaination().getText();
//            expImage = test.getExplaination().getImage();
//            if (expText != null && expText != "" && expText.length() > 1) {
//                testActivityImageViewPopUpClose.setVisibility(View.VISIBLE);
//                testActivityImageViewPopUpParrot.setVisibility(View.VISIBLE);
//                testActivityPopUpLayout.setVisibility(View.VISIBLE);
//                if (expImage != null && expImage != "" && expImage.length() > 1) {
//                    testActivityImageViewInPopUpImageImage.setVisibility(View.VISIBLE);
//                    testActivityTextViewInPopUpImageText.setVisibility(View.VISIBLE);
//                    testActivityImageViewInPopUpImageImageOnly.setVisibility(View.GONE);
//                    testActivityTextViewInPopUpOnlyText.setVisibility(View.GONE);
//                    testActivityImageViewInPopUpImageImage.setImageBitmap(getBitmapFromAsset(expImage));
//                    testActivityTextViewInPopUpImageText.setText(expText);
//                    testActivityTextViewInPopUpImageText.setTextSize(fontSize);
//                } else {
//                    testActivityImageViewInPopUpImageImage.setVisibility(View.GONE);
//                    testActivityTextViewInPopUpImageText.setVisibility(View.GONE);
//                    testActivityImageViewInPopUpImageImageOnly.setVisibility(View.GONE);
//                    testActivityTextViewInPopUpOnlyText.setVisibility(View.VISIBLE);
//                    testActivityTextViewInPopUpOnlyText.setText(expText);
//                    testActivityTextViewInPopUpOnlyText.setTextSize(fontSize);
//                }
//            } else if (expImage != null && expImage != "" && expImage.length() > 1) {
//                testActivityImageViewPopUpClose.setVisibility(View.VISIBLE);
//                testActivityImageViewPopUpParrot.setVisibility(View.VISIBLE);
//                testActivityPopUpLayout.setVisibility(View.VISIBLE);
//                testActivityImageViewInPopUpImageImage.setVisibility(View.GONE);
//                testActivityTextViewInPopUpImageText.setVisibility(View.GONE);
//                testActivityImageViewInPopUpImageImageOnly.setVisibility(View.VISIBLE);
//                testActivityTextViewInPopUpOnlyText.setVisibility(View.GONE);
//                testActivityImageViewInPopUpImageImageOnly.setImageBitmap(getBitmapFromAsset(expImage));
//            } else {
//                closePopup();
//            }
//        } else {
//            closePopup();
//        }
        String expText = null;
        String expImage = null;
        String expAudio = null;

        expAudio = test.getExplaination().getAudio();
        expText = test.getExplaination().getText();
        expImage = test.getExplaination().getImage();
        if (expAudio != null && expAudio != "" && expAudio.length() > 1) {
            loadAudio(expAudio);
        }
        if (expText != null && expText != "" && expText.length() > 1) {
            testActivityImageViewPopUpClose.setVisibility(View.VISIBLE);
            testActivityImageViewPopUpParrot.setVisibility(View.VISIBLE);
            testActivityPopUpLayout.setVisibility(View.VISIBLE);
            String uidAudio = topic + "Question" + (tempIndex + 1) + "ExplanationAudio";
            setAudioDescription(uidAudio);
            if (expImage != null && expImage != "" && expImage.length() > 1) {
                testActivityImageViewInPopUpImageImage.setVisibility(View.VISIBLE);
                testActivityTextViewInPopUpImageText.setVisibility(View.VISIBLE);
                testActivityImageViewInPopUpImageImageOnly.setVisibility(View.GONE);
                testActivityTextViewInPopUpOnlyText.setVisibility(View.GONE);
                testActivityImageViewInPopUpImageImage.setImageBitmap(getBitmapFromAsset(expImage));
                testActivityTextViewInPopUpImageText.setText(expText);
                testActivityTextViewInPopUpImageText.setTextSize(fontSize);
            } else {
                testActivityImageViewInPopUpImageImage.setVisibility(View.GONE);
                testActivityTextViewInPopUpImageText.setVisibility(View.GONE);
                testActivityImageViewInPopUpImageImageOnly.setVisibility(View.GONE);
                testActivityTextViewInPopUpOnlyText.setVisibility(View.VISIBLE);
                testActivityTextViewInPopUpOnlyText.setText(expText);
                testActivityTextViewInPopUpOnlyText.setTextSize(fontSize);
            }
        } else if (expImage != null && expImage != "" && expImage.length() > 1) {
            testActivityImageViewPopUpClose.setVisibility(View.VISIBLE);
            testActivityImageViewPopUpParrot.setVisibility(View.VISIBLE);
            testActivityPopUpLayout.setVisibility(View.VISIBLE);
            testActivityImageViewInPopUpImageImage.setVisibility(View.GONE);
            testActivityTextViewInPopUpImageText.setVisibility(View.GONE);
            testActivityImageViewInPopUpImageImageOnly.setVisibility(View.VISIBLE);
            testActivityTextViewInPopUpOnlyText.setVisibility(View.GONE);
            testActivityImageViewInPopUpImageImageOnly.setImageBitmap(getBitmapFromAsset(expImage));
        } else {
            closePopup();
        }
    }

    public void closePopup() {
        Log.d("flushingValues","Close Popop correct: "+correctCount+" total score: "+ totalScore);
        if(player!=null) {
            player.setPlayWhenReady(false);
            player.release();
        }
        expActive = false;
        start = false;
        appAnalytics.setExplanationEvent(time + "", test.getTopicName() + "" + "" + testIndex, test.getTopicName());
        testActivityImageViewPopUpClose.setVisibility(View.GONE);
        testActivityImageViewPopUpParrot.setVisibility(View.GONE);
        testActivityPopUpLayout.setVisibility(View.GONE);
        testIndex++;
        setTest();
//        if (sharedPrefs.getString("GradeSelected", "Grade 6").equalsIgnoreCase("Grade 6")) {
//            setTest();
//        } else {
//            setTestHigher();
//        }


    }

    private Bitmap getBitmapFromAsset(String strName) {
        Log.wtf("-this", "Bitmap Exp : " + strName);
        AssetManager assetManager = this.getAssets();
        InputStream istr = null;
        try {
            istr = assetManager.open(strName + ".png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(istr);
        return bitmap;
    }

    public void setTest() {
        if (testList != null) {
            Log.wtf("-this", "Test Index : " + testIndex + " TestList Size : " + testList.size());
            if (testList.size() > 0 && testIndex < testList.size()) {
                tempIndex = testIndex;
                if (testIndex == 3) {
                    tempIndex = 7;
                } else if (testIndex == 4) {
                    tempIndex = 8;
                } else if (testIndex == 5) {
                    tempIndex = 3;
                } else if (testIndex == 6) {
                    tempIndex = 4;
                } else if (testIndex == 7) {
                    tempIndex = 5;
                } else if (testIndex == 8) {
                    tempIndex = 6;
                }
                if (tempIndex < testList.size()) {
                    test = testList.get(tempIndex);
                    Log.wtf("-thus", "TestIndex : " + (testIndex + 1) + "   TempIndex : " + (tempIndex + 1));
                    File myDirectory = new File(getFilesDir(), "TestAudios");
                    deleteRecursive(myDirectory);
                    final Handler handler = new Handler();

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            downloadAudios(test);
                        }
                    }, 1);


                    if (tempIndex == 0) {
                        // testId = DatabaseHelper.getInstance(getApplicationContext()).InitializeTestTableRow(topic, selectedSubject, topicName, test.getTopicName());
                    }
                    type = test.getType();
                    appAnalytics.setQuestionStart(selectedSubject, topic + "" + testIndex, test.getType(), test.getAnswerList().size(), test.getDifficultyLevel() + "", (tempIndex + 1), (testIndex + 1), test.getTopicName());

                    Log.wtf("-this", "Test Type : " + type);
                    setBigQuestion(test.getQuestion().getText());
                } else {
                    sendScoreToUnity();
                    flushValues();
                    //   startActivity(new Intent(this, MainActivity.class));
                    startActivity(new Intent(SimpleTestActivity.this,UnityPlayerActivity.class));
                    finish();
                }

//                test = testList.get(testIndex);
//                testId = DatabaseHelper.getInstance(getApplicationContext()).InitializeTestTableRow(topic, selectedSubject, topicName, test.getTopicName());
//                type = test.getType();
//                Log.wtf("-this", "Test Type : " + type);
//                setBigQuestion(test.getQuestion().getText());

            } else {
                if (source.equalsIgnoreCase("socratic")) {
                    appAnalytics.setPostTestEnd(testList.size(), questionAttempt, correctCount, totalScore, test.getTopicName(), test.getTopicName() + "" + testIndex, "Test");
                    appAnalytics.setPostTestCompleted(testList.size(), questionAttempt, correctCount, totalScore, test.getTopicName());
                } else {
                    Log.d("flushingValues","SET EVENTS - correct: "+correctCount+" total score: "+ totalScore);
                    appAnalytics.setSimpleTestEnd(testList.size(), questionAttempt, correctCount, totalScore, test.getTopicName(), test.getTopicName() + "" + testIndex, "Test",topic,selectedGrade,selectedSubject);
                    appAnalytics.setSimpleTestCompleted(testList.size(), questionAttempt, correctCount, totalScore, test.getTopicName(),topic,selectedGrade,selectedSubject);
                }
//                String timestamp = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
//                // subject librrary
//                NotificationDatabaseHandler.getInstance(getApplicationContext()).InsertNotification("text", "TestNext", topic+"$"+selectedSubject+"$"+sharedPrefs.getString("GradeSelected", "Grade 6"), Integer.parseInt(timestamp));
                sendScoreToUnity();
                flushValues();
//                end_counter++;
//                editor = sharedPrefs.edit();
//                editor.putInt("end_count", end_counter);
//                int val = sharedPrefs.getInt("end_count", 0);
//                editor.putInt("NumberOfTestCompleted", val);
//                editor.apply();
//                Log.wtf("counter_check", "test completed counter :" + sharedPrefs.getInt("NumberOfTestCompleted", 0));
//
//                startActivity(new Intent(this, MainActivity.class));
                startActivity(new Intent(SimpleTestActivity.this,UnityPlayerActivity.class));
                finish();
            }
        } else {
            sendScoreToUnity();
            flushValues();
            //  startActivity(new Intent(this, MainActivity.class));
            startActivity(new Intent(SimpleTestActivity.this,UnityPlayerActivity.class));
            finish();
        }

    }


    public void getTestData(String topic, String subject) {
        String json;
        try {
            String fileName = selectedGrade + selectedSubject + ".json";
            /*switch (subject) {
                case ("English"):
                    fileName = "EnglishJSON.json";
                    break;
                case ("Maths"):
                    fileName = "MathsJSON.json";
                    break;
                case ("Urdu"):
                    fileName = "UrduJSON.json";
                    break;
                case ("Geography"):
                    fileName = "GeographyJSON.json";
                    break;
                case ("History"):
                    fileName = "HistoryJSON.json";
                    break;
                case ("Science"):
                    fileName = "ScienceJSON.json";
                    break;

            }
    */
            InputStream filedata = null;
            filedata = this.getAssets().open(fileName);
            int size = filedata.available();
            byte[] buffer = new byte[size];
            filedata.read(buffer);
            filedata.close();
            json = new String(buffer, "UTF-8");


//            InputStream filedata = null;
//            filedata = this.getAssets().open("AdaptiveTestExample.json");
//            int size = filedata.available();
//            byte[] buffer = new byte[size];
//            filedata.read(buffer);
//            filedata.close();
//            json = new String(buffer, "UTF-8");

            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray(topic);
            // JSONArray jsonArray = jsonObject.getJSONArray("Grade6HistoryIndusValleyCivilizationIndusValleyCivilizationPart1");

            Log.wtf("-this", "TOPIC : " + topic + " Array Length : " + jsonArray.length());

            for (int i = 0; i < jsonArray.length(); i++) {
                TestJsonParseModel testObject = new TestJsonParseModel();


//                Log.wtf("-this","Array Index : "+ i);
                JSONObject obj = jsonArray.getJSONObject(i);

                Log.wtf("-this", "Array Index : " + i + " " + obj.getString("Type"));
                String type = obj.getString("Type");
                String topicN = obj.getString("TopicName");
                subTopicName = topicN;
                Log.wtf("-this", "TopicName in Parse : " + topicN);

                String difficultyLevel = obj.getString("DifficultyLevel");

                if (i > 6) {
                    difficultyLevel = "superEasy";
                }

                testObject.setType(type);
                testObject.setDifficultyLevel(difficultyLevel);
                testObject.setTopicName(topicN);

                JSONObject questionObj = obj.getJSONObject("Question");
                QuestionModel question = new QuestionModel();

                if (type.equalsIgnoreCase("SoundRecognitionSpellingPhonics") || type.equalsIgnoreCase("SoundRecognitionSpellingPhonics2Options")) {
                    question = new QuestionModel(questionObj.getString("Text"),
                            questionObj.getString("Image"), questionObj.getString("Audio"), questionObj.getString("Hint"), questionObj.getString("QuestionButtonAudio"));

                } else {
                    question = new QuestionModel(questionObj.getString("Text"),
                            questionObj.getString("Image"), questionObj.getString("Audio"), questionObj.getString("Hint"));

                }
                testObject.setQuestion(question);

                JSONObject explainationObj = obj.getJSONObject("Explaination");
                ExplainationModel explaination = new ExplainationModel(explainationObj.getString("Text"),
                        explainationObj.getString("Image"), explainationObj.getString("Audio"));
                testObject.setExplaination(explaination);

                if (type.equalsIgnoreCase("MatchingImageToImage")) {

                    JSONArray column1Array = obj.getJSONArray("Column1Images");
                    ArrayList<ColumnImagesModel> column1list = new ArrayList<>();
                    for (int j = 0; j < column1Array.length(); j++) {

                        String temp = column1Array.get(j).toString();

                        column1list.add(new ColumnImagesModel(temp, null));
                    }
                    testObject.setLeftColumnList(column1list);

                    JSONArray column2Array = obj.getJSONArray("Column2Images");
                    ArrayList<ColumnImagesModel> column2list = new ArrayList<>();
                    for (int j = 0; j < column2Array.length(); j++) {

                        String temp = column2Array.get(j).toString();
                        column2list.add(new ColumnImagesModel(temp, null));
                    }
                    testObject.setRightList(column2list);

                } else if (type.equalsIgnoreCase("MatchTextToText") || type.equalsIgnoreCase("MatchTextToText2Options")) {

                    JSONArray column1Array = obj.getJSONArray("Column1Answers");
                    ArrayList<ColumnImagesModel> column1list = new ArrayList<>();
                    for (int j = 0; j < column1Array.length(); j++) {

                        String temp = column1Array.get(j).toString();

                        column1list.add(new ColumnImagesModel(null, temp));
                    }
                    testObject.setLeftColumnList(column1list);

                    JSONArray column2Array = obj.getJSONArray("Column2Answers");
                    ArrayList<ColumnImagesModel> column2list = new ArrayList<>();
                    for (int j = 0; j < column2Array.length(); j++) {

                        String temp = column2Array.get(j).toString();
                        column2list.add(new ColumnImagesModel(null, temp));
                    }
                    testObject.setRightList(column2list);

                } else {
                    JSONArray optionsArray = obj.getJSONArray("AnswerOptions");
                    ArrayList<OptionsModel> optionList = new ArrayList<>();
                    for (int j = 0; j < optionsArray.length(); j++) {
                        JSONObject optionObj = optionsArray.getJSONObject(j);
                        optionList.add(new OptionsModel(optionObj.getString("Text"), optionObj.getString("Image"), optionObj.getString("Audio")));
                        /*if (optionObj.getString("Text").equalsIgnoreCase("1")) {
                            optionList.add(new OptionsModel("True", optionObj.getString("Image"), optionObj.getString("Audio")));

                        } else if (optionObj.getString("Text").equalsIgnoreCase("0")) {
                            optionList.add(new OptionsModel("False", optionObj.getString("Image"), optionObj.getString("Audio")));

                        } else {
                            optionList.add(new OptionsModel(optionObj.getString("Text"), optionObj.getString("Image"), optionObj.getString("Audio")));
                        }*/
                    }
                    testObject.setOptionList(optionList);
                    ArrayList<AnwserModel> answerList = new ArrayList<>();
                    if (type.equalsIgnoreCase("DragTextBoxToPicture2Options") || type.equalsIgnoreCase("DragTextBoxToPicture")
                            || type.equalsIgnoreCase("Labelling") || type.equalsIgnoreCase("Labelling2Options") || type.equalsIgnoreCase("MathsMcq")) {

                    } else {
                        JSONArray answerArray = obj.getJSONArray("CorrectAnswerOptions");

                        for (int k = 0; k < answerArray.length(); k++) {
                            JSONObject answerObj = answerArray.getJSONObject(k);
                            answerList.add(new AnwserModel(answerObj.getString("Text"), answerObj.getString("Image"), answerObj.getString("Audio")));
                            /*if (answerObj.getString("Text").equalsIgnoreCase("1")) {
                                answerList.add(new AnwserModel("True", answerObj.getString("Image"), answerObj.getString("Audio")));

                            } else if (answerObj.getString("Text").equalsIgnoreCase("0")) {
                                answerList.add(new AnwserModel("False", answerObj.getString("Image"), answerObj.getString("Audio")));

                            } else {
                                answerList.add(new AnwserModel(answerObj.getString("Text"), answerObj.getString("Image"), answerObj.getString("Audio")));

                            }*/
                        }
                    }
                    testObject.setAnswerList(answerList);
                }

                testList.add(testObject);
            }

            Log.wtf("-this", "Get Test : TestList Size : " + testList.size());
            setTest();

        } catch (JSONException e) {
            setTest();
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void setTestFragment(String type, int testId, TestJsonParseModel test) {

        Log.wtf("SimpleTestActivity", "settestfragment: "+type);
        unSocratic = true;

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (type) {
            case ("OrdinaryMCQ"):
                fragmentTransaction.add(R.id.TestActivityfragmentContainer, new OrdinaryMCQ(testId, test, SimpleTestActivity.this, SimpleTestActivity.this));
                //   fragmentTransaction.add(R.id.TestActivityfragmentContainer, new MathsSubtration());
                fragmentTransaction.commit();

                break;
            case ("OrdinaryMCQLong"):
                fragmentTransaction.add(R.id.TestActivityfragmentContainer, new OrdinaryMCQLong(testId, test, SimpleTestActivity.this, SimpleTestActivity.this));
                //fragmentTransaction.add(R.id.TestActivityfragmentContainer, new MathsSubtration());
                fragmentTransaction.commit();

                break;
            case ("OrdinaryMCQ2Options"):
                fragmentTransaction.add(R.id.TestActivityfragmentContainer, new OrdinaryMCQTwoOptions(testId, test, SimpleTestActivity.this, SimpleTestActivity.this));
                //fragmentTransaction.add(R.id.TestActivityfragmentContainer, new MathsSubtration());
                fragmentTransaction.commit();

                break;
            case ("WordProblem"):
                fragmentTransaction.add(R.id.TestActivityfragmentContainer, new WordProblem(testId, test, SimpleTestActivity.this, SimpleTestActivity.this));
                fragmentTransaction.commit();

                break;
            case ("FillInTheBlank"):
                if (selectedSubject.equalsIgnoreCase("Urdu") || selectedSubject.equalsIgnoreCase("History")) {
                    fragmentTransaction.add(R.id.TestActivityfragmentContainer, new FillInTheBlankFourOptions(testId, test, SimpleTestActivity.this, SimpleTestActivity.this));
                    fragmentTransaction.commit();
                } else {
                    fragmentTransaction.add(R.id.TestActivityfragmentContainer, new FillInTheBlankFourOptionsEnglish(testId, test, SimpleTestActivity.this, SimpleTestActivity.this));
                    fragmentTransaction.commit();
                }
                break;
            case ("OrdinaryMCQLong2Options"):
                fragmentTransaction.add(R.id.TestActivityfragmentContainer, new OrdinaryMCQTwoOptionsLong(testId, test, SimpleTestActivity.this, SimpleTestActivity.this));
                fragmentTransaction.commit();

                break;
            case ("MatchTextToText"):
                fragmentTransaction.add(R.id.TestActivityfragmentContainer, new MatchTextToText(testId, test, SimpleTestActivity.this, SimpleTestActivity.this));
                fragmentTransaction.commit();

                break;
            case ("MatchTextToText2Options"):
                fragmentTransaction.add(R.id.TestActivityfragmentContainer, new MatchTextToTextTwoOptions(testId, test, SimpleTestActivity.this, SimpleTestActivity.this));
                fragmentTransaction.commit();

                break;
            case ("Labelling"):
                fragmentTransaction.add(R.id.TestActivityfragmentContainer, new Labelling(testId, test, SimpleTestActivity.this, SimpleTestActivity.this));
                fragmentTransaction.commit();

                break;
            case ("Labelling2Options"):
                fragmentTransaction.add(R.id.TestActivityfragmentContainer, new LabellingTwoOptions(testId, test, SimpleTestActivity.this, SimpleTestActivity.this));
                fragmentTransaction.commit();

                break;
            case ("PictureQuestionTextAnswer"):
                fragmentTransaction.add(R.id.TestActivityfragmentContainer, new PictureQuestionTextAnswer(testId, test, SimpleTestActivity.this, SimpleTestActivity.this));
                fragmentTransaction.commit();

                break;
            case ("PictureQuestionTextAnswer2Options"):
                fragmentTransaction.add(R.id.TestActivityfragmentContainer, new PictureQuestionTextAnswerTwoOptions(testId, test, SimpleTestActivity.this, SimpleTestActivity.this));
                fragmentTransaction.commit();

                break;
            case ("MatchingImageToImage"):
                fragmentTransaction.add(R.id.TestActivityfragmentContainer, new MatchingImageToImage(testId, test, SimpleTestActivity.this, SimpleTestActivity.this));
                fragmentTransaction.commit();

                break;
            case ("Comprehension2ChoicesQuestionImage"):
                fragmentTransaction.add(R.id.TestActivityfragmentContainer, new ComprehensionTwoChoicesQuestionImage(testId, test, SimpleTestActivity.this, SimpleTestActivity.this));
                fragmentTransaction.commit();

                break;
            case ("Comprehension4Choices"):
                fragmentTransaction.add(R.id.TestActivityfragmentContainer, new ComprehensionFourChoices(testId, test, SimpleTestActivity.this, SimpleTestActivity.this));
                fragmentTransaction.commit();

                break;
            case ("Comprehension2Choices"):
                fragmentTransaction.add(R.id.TestActivityfragmentContainer, new ComprehensionTwoChoices(testId, test, SimpleTestActivity.this, SimpleTestActivity.this));
                fragmentTransaction.commit();

                break;
            case ("DragTextBoxToPicture"):
                fragmentTransaction.add(R.id.TestActivityfragmentContainer, new DragTextBoxToPicture(testId, test, SimpleTestActivity.this, SimpleTestActivity.this));
                fragmentTransaction.commit();

                break;
            case ("DragTextBoxToPicture2Options"):
                Log.wtf("-this", "Drag Text to Picture Two options : " + testIndex);
                fragmentTransaction.add(R.id.TestActivityfragmentContainer, new DragTextBoxToPictureTwoOptions(testId, test, SimpleTestActivity.this, SimpleTestActivity.this));
                fragmentTransaction.commit();

                break;
            case ("PictureTextQuestionPictureAnswer"):
                fragmentTransaction.add(R.id.TestActivityfragmentContainer, new Picture_Text_Question_Picture_Answer(testId, test, SimpleTestActivity.this, SimpleTestActivity.this));
                fragmentTransaction.commit();

                break;
            case ("PictureTextQuestionPictureAnswer2Options"):
                fragmentTransaction.add(R.id.TestActivityfragmentContainer, new Picture_Text_Question_Picture_Answer_Two_Options(testId, test, SimpleTestActivity.this, SimpleTestActivity.this));
                fragmentTransaction.commit();

                break;
            case ("BigPictureQuestionTextAnswer"):
                fragmentTransaction.add(R.id.TestActivityfragmentContainer, new BigPictureQuestionTextAnswer(testId, test, SimpleTestActivity.this, SimpleTestActivity.this));
                fragmentTransaction.commit();

                break;
            case ("BigPictureQuestionTextAnswer2Options"):
                fragmentTransaction.add(R.id.TestActivityfragmentContainer, new BigPictureQuestionTextAnswerTwoOptions(testId, test, SimpleTestActivity.this, SimpleTestActivity.this));
                fragmentTransaction.commit();

                break;
            case ("FillInTheBlank2Options"):
                if (selectedSubject.equalsIgnoreCase("Urdu") || selectedSubject.equalsIgnoreCase("Hostory")) {
                    fragmentTransaction.add(R.id.TestActivityfragmentContainer, new FillInTheBlank(testId, test, SimpleTestActivity.this, SimpleTestActivity.this));
                    fragmentTransaction.commit();
                } else {
                    fragmentTransaction.add(R.id.TestActivityfragmentContainer, new FillInTheBlankEnglish(testId, test, SimpleTestActivity.this, SimpleTestActivity.this));
                    fragmentTransaction.commit();
                }
                break;
            case ("MathsMCQ"):
                fragmentTransaction.add(R.id.TestActivityfragmentContainer, new MathsMcq(testId, test, SimpleTestActivity.this, SimpleTestActivity.this));
                fragmentTransaction.commit();
                break;
            case ("MathsAddition"):
                fragmentTransaction.add(R.id.TestActivityfragmentContainer, new MathsAddition(testId, test, SimpleTestActivity.this, SimpleTestActivity.this));
                fragmentTransaction.commit();
                break;
            case ("MathsSubtraction"):
                fragmentTransaction.add(R.id.TestActivityfragmentContainer, new MathsSubtration(testId, test, SimpleTestActivity.this, SimpleTestActivity.this));
                fragmentTransaction.commit();
                break;
            case ("SoundRecognitionSpellingPhonics"):
                fragmentTransaction.add(R.id.TestActivityfragmentContainer, new SoundRecognitionSpelling4Options(testId, test, SimpleTestActivity.this, SimpleTestActivity.this));
                fragmentTransaction.commit();
                break;
            case ("SoundRecognitionSpellingPhonics2Options"):
                fragmentTransaction.add(R.id.TestActivityfragmentContainer, new SoundRecognitionSpelling2Options(testId, test, SimpleTestActivity.this, SimpleTestActivity.this));
                fragmentTransaction.commit();
                break;
            case ("OrdinaryMCQAudio"):
                fragmentTransaction.add(R.id.TestActivityfragmentContainer, new OrdinaryMCQAudio(testId, test, SimpleTestActivity.this, SimpleTestActivity.this));
                fragmentTransaction.commit();
                break;
            case ("OrdinaryMCQAudio2Options"):
                fragmentTransaction.add(R.id.TestActivityfragmentContainer, new OrdinaryMCQAudioTwoOptions(testId, test, SimpleTestActivity.this, SimpleTestActivity.this));
                fragmentTransaction.commit();
                break;
            default:
                break;

        }
    }

    public void FullScreen() {
        final View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        decorView.setOnSystemUiVisibilityChangeListener
                (new View.OnSystemUiVisibilityChangeListener() {
                    @Override
                    public void onSystemUiVisibilityChange(int visibility) {
                        // Note that system bars will only be "visible" if none of the
                        // LOW_PROFILE, HIDE_NAVIGATION, or FULLSCREEN flags are set.
                        if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
//                                int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
//
//                            int a=SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
//                                decorView.setSystemUiVisibility(a);
                            decorView.setSystemUiVisibility(
                                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                                            | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);


                        }
                    }
                });
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.testActivityTextViewContinue:
                Log.wtf("SimpleTestActivity", "Continue Clicked");
                continueClicked();
                break;
            case R.id.testActivityImageViewPopUpClose:
                closePopup();
                break;
            case R.id.testActivityImageViewHome:
                tapAudioHome();
                /*if (mediaPlayerBg != null) {
                    mediaPlayerBg.pause();
                    mediaPlayerBg.release();
                }*/

                if (testActivityTextViewQuestionBig.getVisibility() == View.VISIBLE) {
                    if (source.equalsIgnoreCase("socratic")) {
                        appAnalytics.setPostTestEnd(testList.size(), questionAttempt, correctCount, totalScore, test.getTopicName(), test.getTopicName() + "" + testIndex, "BigQuestion");
                        //  String timestamp = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
                        //  NotificationDatabaseHandler.getInstance(getApplicationContext()).InsertNotification("text", "TestIncomplete", topic+"$"+selectedSubject+"$"+sharedPrefs.getString("GradeSelected", "Grade 6"), Integer.parseInt(timestamp));
                        appAnalytics.setPostTestHomeBack(selectedSubject, "BigQuestion");
                    } else {
                        appAnalytics.setSimpleTestEnd(testList.size(), questionAttempt, correctCount, totalScore, test.getTopicName(), test.getTopicName() + "" + testIndex, "BigQuestion",topic,selectedGrade,selectedSubject);
                        //  String timestamp = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
                        //  NotificationDatabaseHandler.getInstance(getApplicationContext()).InsertNotification("text", "TestIncomplete", topic+"$"+selectedSubject+"$"+sharedPrefs.getString("GradeSelected", "Grade 6"), Integer.parseInt(timestamp));
                        appAnalytics.setSimpleTestHomeBack(selectedSubject, "BigQuestion");
                    }

                } else if (testActivityPopUpLayout.getVisibility() == View.VISIBLE) {
                    if (source.equalsIgnoreCase("socratic")) {
                        appAnalytics.setPostTestEnd(testList.size(), questionAttempt, correctCount, totalScore, test.getTopicName(), test.getTopicName() + "" + testIndex, "Explanation");
                        //   String timestamp = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
                        //   NotificationDatabaseHandler.getInstance(getApplicationContext()).InsertNotification("text", "TestIncomplete", topic+"$"+selectedSubject+"$"+sharedPrefs.getString("GradeSelected", "Grade 6"), Integer.parseInt(timestamp));
                        appAnalytics.setPostTestHomeBack(selectedSubject, "Explanation");
                    } else {
                        appAnalytics.setSimpleTestEnd(testList.size(), questionAttempt, correctCount, totalScore, test.getTopicName(), test.getTopicName() + "" + testIndex, "Explanation",topic,selectedGrade,selectedSubject);
                        //   String timestamp = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
                        //   NotificationDatabaseHandler.getInstance(getApplicationContext()).InsertNotification("text", "TestIncomplete", topic+"$"+selectedSubject+"$"+sharedPrefs.getString("GradeSelected", "Grade 6"), Integer.parseInt(timestamp));
                        appAnalytics.setSimpleTestHomeBack(selectedSubject, "Explanation");
                    }
                } else {
                    if (source.equalsIgnoreCase("socratic")) {
                        appAnalytics.setPostTestEnd(testList.size(), questionAttempt, correctCount, totalScore, test.getTopicName(), test.getTopicName() + "" + testIndex, "Test");
                        //   String timestamp = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
                        //    NotificationDatabaseHandler.getInstance(getApplicationContext()).InsertNotification("text", "TestIncomplete", topic+"$"+selectedSubject+"$"+sharedPrefs.getString("GradeSelected", "Grade 6"), Integer.parseInt(timestamp));
                        appAnalytics.setPostTestHomeBack(selectedSubject, "Test");
                    } else {
                        appAnalytics.setSimpleTestEnd(testList.size(), questionAttempt, correctCount, totalScore, test.getTopicName(), test.getTopicName() + "" + testIndex, "Test",topic,selectedGrade,selectedSubject);
                        //   String timestamp = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
                        //    NotificationDatabaseHandler.getInstance(getApplicationContext()).InsertNotification("text", "TestIncomplete", topic+"$"+selectedSubject+"$"+sharedPrefs.getString("GradeSelected", "Grade 6"), Integer.parseInt(timestamp));
                        appAnalytics.setSimpleTestHomeBack(selectedSubject, "Test");
                    }

                }

                //   startActivity(new Intent(this, StructuredLearningActivity.class));
                //  startActivity(new Intent(this, MainActivity.class));


                sendScoreToUnity();
                flushValues();
                startActivity(new Intent(SimpleTestActivity.this,UnityPlayerActivity.class));
                finish();
                break;
            case R.id.testActivityImageViewDaimond:

                break;
        }

    }

    @Override
    public void onBackPressed() {
        if (testActivityTextViewQuestionBig.getVisibility() == View.VISIBLE) {
            if (source.equalsIgnoreCase("socratic")) {
                appAnalytics.setPostTestEnd(testList.size(), questionAttempt, correctCount, totalScore, test.getTopicName(), test.getTopicName() + "" + testIndex, "BigQuestion");
            } else {
                appAnalytics.setSimpleTestEnd(testList.size(), questionAttempt, correctCount, totalScore, test.getTopicName(), test.getTopicName() + "" + testIndex, "BigQuestion",topic,selectedGrade,selectedSubject);

            }
        } else if (testActivityPopUpLayout.getVisibility() == View.VISIBLE) {
            if (source.equalsIgnoreCase("socratic")) {
                appAnalytics.setPostTestEnd(testList.size(), questionAttempt, correctCount, totalScore, test.getTopicName(), test.getTopicName() + "" + testIndex, "Explanation");
            } else {
                appAnalytics.setSimpleTestEnd(testList.size(), questionAttempt, correctCount, totalScore, test.getTopicName(), test.getTopicName() + "" + testIndex, "Explanation",topic,selectedGrade,selectedSubject);

            }
        } else {
            if (source.equalsIgnoreCase("socratic")) {
                appAnalytics.setPostTestEnd(testList.size(), questionAttempt, correctCount, totalScore, test.getTopicName(), test.getTopicName() + "" + testIndex, "Test");
            } else {
                appAnalytics.setSimpleTestEnd(testList.size(), questionAttempt, correctCount, totalScore, test.getTopicName(), test.getTopicName() + "" + testIndex, "Test",topic,selectedGrade,selectedSubject);

            }
        }
//        //  TriggerFirebaseSync();
//        startActivity(new Intent(this, MainActivity.class));
        sendScoreToUnity();
        flushValues();
        startActivity(new Intent(SimpleTestActivity.this,UnityPlayerActivity.class));
        finish();
    }


    void TriggerFirebaseSync() {

        ArrayList<Integer> testlist = new ArrayList<>();
        ArrayList<Integer> videolist = new ArrayList<>();

//        testlist = DatabaseHelper.getInstance(getApplicationContext()).getTestLastSyncAddress();
//        videolist = DatabaseHelper.getInstance(getApplicationContext()).getVideoLastSyncAddress();
//        Log.wtf("sync_test", "data" + testlist);
//        Log.wtf("sync", "" + testlist.size());
//
//        if (testlist.size() > 0 || videolist.size() > 0) {
//
//            SyncFirebaseDataInBackground sync = new SyncFirebaseDataInBackground(testlist, videolist, SimpleTestActivity.this);
//            sync.execute();
//
//        } else {
//            //  Log.wtf("sync", "all done");
//        }

    }

//    void syncGradeHigher(String Marks) {
//        Log.wtf("TestActivity_", "firebase data: ");
//        FirebaseDatabase database;
//        DatabaseReference myRef = null;
//        DatabaseReference myRef2 = null;
//        database = FirebaseDatabase.getInstance();
//
//        String gradeR = DatabaseHelper.getInstance(this).GetTest(subTopicName);
//        sharedPrefs = this.getSharedPreferences("DefaultSPForTaleemabadGrade6", MODE_PRIVATE);
//        String uid = sharedPrefs.getString("UniqueId", "");
//        String grade = sharedPrefs.getString("GradeSelected", "Grade 6");
//        Log.wtf("sync", "called Grade 9");
//        myRef = database.getReference("Grade6_Users").child(uid)
//                .child(grade).child(selectedSubject).child(topic)
//                .child("Tests Attempted")
//                .child(subTopicName)
//                .child("Grade");
//        myRef.setValue(gradeR);
//
//        myRef2 = database.getReference("Grade6_Users").child(uid)
//                .child(grade).child(selectedSubject).child(topic)
//                .child("Tests Attempted")
//                .child(subTopicName);
//        myRef2.child("Question" + questionAttempt).setValue(Marks);
//
//
//    }

    private void loadAudio(String name) {
        //Grade1Voiceovers
        //String path = Environment.getExternalStorageDirectory().getPath();
        File path = new File(String.valueOf(getApplicationContext().getExternalFilesDir(null)));
        Log.wtf("path_", "path : " + path);
        MediaPlayer mediaPlayerTouch = new MediaPlayer();
        try {
            mediaPlayerTouch.setDataSource(path + "/" + name + ".mp3");
            mediaPlayerTouch.prepare();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayerTouch.start();
        mediaPlayerTouch.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
    }

    void flushValues() {
        Log.d("flushingValues","FLUSH");
        //   unSocratic = false;
        alienLife = 100;
        parrotLife = 100;
        SocraticActivity.alienLife = 100;
        SocraticActivity.parrotLife = 100;
        correctCount = 0;
        totalScore = 0;
        testIndex = 0;
        continueClicked = false;
    }

    public void startTestTimer() {
        time = 0;
        T.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Runnable rn = (new Runnable() {
                    @Override
                    public void run() {
                        if (start == true) {
                            if (!testPaused) {
                                time++;
                            }
                        } else {
                            return;
                        }

                    }
                });
                rn.run();
            }
        }, 1000, 1000);
    }

    public void createbgAudio() {
        mediaPlayerBg = MediaPlayer.create(this, R.raw.final_bg);
        mediaPlayerBg.setLooping(true);
        mediaPlayerBg.setVolume(0.08f, 0.08f);
        mediaPlayerBg.start();
    }

    public void bgAudio() {
        if (mediaPlayerBg != null) {
            if (!mediaPlayerBg.isPlaying()) {
                mediaPlayerBg.start();
            }
        }

    }

    public void bgAudioPause() {
        if (mediaPlayerBg != null) {
            if (mediaPlayerBg.isPlaying()) {
                mediaPlayerBg.pause();

            }
        }

    }

    public void bgAudioUnPause() {
        if (mediaPlayerBg != null)
            mediaPlayerBg.start();
    }

    public void tapAudioHome() {
//        MediaPlayer mediaPlayerTouch = MediaPlayer.create(this, R.raw.menu_click);
//        mediaPlayerTouch.start();
//        mediaPlayerTouch.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mp) {
//                mp.release();
//            }
//        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        testPaused = true;
        bgAudioPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //player.setPlayWhenReady(true);
        testPaused = false;
        //  bgAudioUnPause();
        if (playSound) {
            if (mediaPlayerBg != null) {
                if (!mediaPlayerBg.isPlaying()) {
                    bgAudio();
                }
            }
        } else {
            playSound = true;
        }
    }

    @Override
    public void onStop(){
        super.onStop();
        if(player!=null) {
            player.setPlayWhenReady(false);
            player.release();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        flushValues();
        if (mediaPlayerBg != null) {
            mediaPlayerBg.release();
            mediaPlayerBg = null;
        }
    }

    void setAudioDescription(String filName) {
        player = ExoPlayerFactory.newSimpleInstance(this);
        player.addListener(new Player.EventListener() {
            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
//                Log.wtf("-thus"," PlayerState : "+playbackState);
//                if (playbackState == ExoPlayer.STATE_ENDED){
//                    //player back ended
//                    Log.wtf("-thus"," PlayerState : END");
//                }
            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }

        });

        dataSourceFactory = new DefaultDataSourceFactory(getApplicationContext(), "ExoplayerDemo");
        extractorsFactory = new DefaultExtractorsFactory();


        String url = streamUrl + filName + ".mp3";
        Log.wtf("-thus", "Audio URL : " + url);
        mediaSource = new ExtractorMediaSource(Uri.parse(url),
                dataSourceFactory,
                extractorsFactory,
                null,
                null);

        player.prepare(mediaSource);
        player.setPlayWhenReady(true);

    }

    public void downloadAudios(TestJsonParseModel test) {

        new Thread(new Runnable() {
            @Override
            public void run () {
                String bytes = null;

                if (test.getType().equalsIgnoreCase("OrdinaryMCQ") || test.getType().equalsIgnoreCase("OrdinaryMCQLong")
                        || test.getType().equalsIgnoreCase("FillInTheBlank")
                        || test.getType().equalsIgnoreCase("Labelling")
                        || test.getType().equalsIgnoreCase("PictureQuestionTextAnswer")
                        || test.getType().equalsIgnoreCase("Comprehension4Choices")
                        || test.getType().equalsIgnoreCase("DragTextBoxToPicture")
                        || test.getType().equalsIgnoreCase("BigPictureQuestionTextAnswer")

                ) {
                    String uidAudioOption1 = topic + "Question" + (tempIndex + 1) + "Option1Audio";
                    String uidAudioOption2 = topic + "Question" + (tempIndex + 1) + "Option2Audio";
                    String uidAudioOption3 = topic + "Question" + (tempIndex + 1) + "Option3Audio";
                    String uidAudioOption4 = topic + "Question" + (tempIndex + 1) + "Option4Audio";

                    DownloadTask downloadTaskOption1;
                    DownloadTask downloadTaskOption2;
                    DownloadTask downloadTaskOption3;
                    DownloadTask downloadTaskOption4;
                    downloadTaskOption1 = new DownloadTask(getApplicationContext(), uidAudioOption1, uidAudioOption1 + ".mp3");
                    downloadTaskOption2 = new DownloadTask(getApplicationContext(), uidAudioOption2, uidAudioOption2 + ".mp3");
                    downloadTaskOption3 = new DownloadTask(getApplicationContext(), uidAudioOption3, uidAudioOption3 + ".mp3");
                    downloadTaskOption4 = new DownloadTask(getApplicationContext(), uidAudioOption4, uidAudioOption4 + ".mp3");
                    bytes = null;
                    try {
                        downloadTaskOption1.execute().get();
                        downloadTaskOption2.execute().get();
                        downloadTaskOption3.execute().get();
                        downloadTaskOption4.execute().get();

                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (CancellationException e) {
                        e.printStackTrace();
                    }
                } else if (test.getType().equalsIgnoreCase("OrdinaryMCQ2Options")
                        || test.getType().equalsIgnoreCase("WordProblem")
                        || test.getType().equalsIgnoreCase("OrdinaryMCQLong2Options")
                        || test.getType().equalsIgnoreCase("Labelling2Options")
                        || test.getType().equalsIgnoreCase("PictureQuestionTextAnswer2Options")
                        || test.getType().equalsIgnoreCase("Comprehension2Choices")
                        || test.getType().equalsIgnoreCase("DragTextBoxToPicture2Options")
                        || test.getType().equalsIgnoreCase("BigPictureQuestionTextAnswer2Options")
                        || test.getType().equalsIgnoreCase("FillInTheBlank2Options")

                ) {
                    String uidAudioOption1 = topic + "Question" + (tempIndex + 1) + "Option1Audio";
                    String uidAudioOption2 = topic + "Question" + (tempIndex + 1) + "Option2Audio";


                    DownloadTask downloadTaskOption1;
                    DownloadTask downloadTaskOption2;

                    downloadTaskOption1 = new DownloadTask(getApplicationContext(), uidAudioOption1, uidAudioOption1 + ".mp3");
                    downloadTaskOption2 = new DownloadTask(getApplicationContext(), uidAudioOption2, uidAudioOption2 + ".mp3");

                    bytes = null;
                    try {
                        bytes = downloadTaskOption1.execute().get();
                        bytes = downloadTaskOption2.execute().get();

                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();

    }

    public void deleteRecursive(File fileOrDirectory) {

        if (fileOrDirectory.isDirectory()) {
            for (File child : fileOrDirectory.listFiles()) {
                deleteRecursive(child);
            }
        }

        fileOrDirectory.delete();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean locationAccepted1 = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (locationAccepted || locationAccepted1) {

                        //   downloadFile();
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE)) {
                            showMessageOKCancel("You need to allow the permissions",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE},
                                                        PERMISSION_REQUEST_CODE);
                                                // downloadFile();
                                            }
                                        }
                                    });
                            return;
                        }


                    }
                }


                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(SimpleTestActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    void sendScoreToUnity(){
        int wrongAnswer = questionAttempt - correctCount;
        Log.d("UnityTestCalled","UID: "+topic+" - Question Attempt: "+questionAttempt+" - Correct: "+correctCount+" - Wrong: "+wrongAnswer);

        UnityPlayer.UnitySendMessage("ScriptHandler","SetGameCompletePrefs",topic+"$"+questionAttempt+"$"+correctCount+"$"+wrongAnswer);
    }
}