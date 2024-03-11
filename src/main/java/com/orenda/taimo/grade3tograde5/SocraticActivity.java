package com.orenda.taimo.grade3tograde5;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.orenda.taimo.grade3tograde5.Models.AnwserModel;
import com.orenda.taimo.grade3tograde5.Models.ColumnImagesModel;
import com.orenda.taimo.grade3tograde5.Models.ExplainationModel;
import com.orenda.taimo.grade3tograde5.Models.OptionsModel;
import com.orenda.taimo.grade3tograde5.Models.QuestionModel;
import com.orenda.taimo.grade3tograde5.Models.TestJsonParseModel;
import com.orenda.taimo.grade3tograde5.Tests.OrdinaryMCQ;
import com.orenda.taimo.grade3tograde5.Tests.OrdinaryMCQLong;
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
import com.orenda.taimo.grade3tograde5.Tests.OrdinaryMCQTwoOptions;
import com.orenda.taimo.grade3tograde5.Tests.OrdinaryMCQTwoOptionsLong;
import com.orenda.taimo.grade3tograde5.Tests.PictureQuestionTextAnswer;
import com.orenda.taimo.grade3tograde5.Tests.PictureQuestionTextAnswerTwoOptions;
import com.orenda.taimo.grade3tograde5.Tests.Picture_Text_Question_Picture_Answer;
import com.orenda.taimo.grade3tograde5.Tests.Picture_Text_Question_Picture_Answer_Two_Options;
import com.orenda.taimo.grade3tograde5.Tests.WordProblem;
import com.orenda.taimo.grade3tograde5.Tests.videoFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Timer;

import firebase.analytics.AppAnalytics;

public class SocraticActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    FrameLayout fragmentContainer,videofragmentContainer;
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;
    public ArrayList<TestJsonParseModel> testList = null;
    ConstraintLayout testActivityMainLayout, testActivityPopUpLayout, feedbackPopup;
    public static String selectedSubject = "English";
    public static String selectedGrade = "Grade 2";
    public static int testIndex = 0;
    ImageView testActivityImageViewBG, testActivityImageViewPopUpClose, testActivityImageViewPopUpParrot;
    TextView testActivityTextViewQuestionBig, testActivityTextViewContinue;
    ImageView testActivityImageViewInPopUpImageImageOnly, testActivityImageViewInPopUpImageImage;
    TextView testActivityTextViewInPopUpOnlyText, testActivityTextViewInPopUpImageText;
    public static ImageView testActivityImageViewHome, testActivityImageViewDaimond;

    RadioGroup radioGroup;
    RadioButton radioButton1, radioButton2, radioButton3, radioButton4, radioButton5;
    EditText editTextOther;
    TestJsonParseModel test = null;
    String type = null;
    private float fontSize = 22;
    public static String topicPath = null;
    String topicName = "";
    String subTopicName = "";
    int testId = -1;
    int questionAttempt = 1;
    int tempIndex = 0;
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
    boolean other = false;
    private boolean videoActive = false;
    private boolean continueClicked =  false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_test);
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        sharedPrefs = getApplicationContext().getSharedPreferences("DefaultSPForTaleemabadGrade6", 0);
        editor = sharedPrefs.edit();
        FullScreen();
        testList = new ArrayList<>();
        test = null;

        selectedGrade = getIntent().getStringExtra("GradeSelected");
        selectedSubject = getIntent().getStringExtra("SubjectSelected");
        topicPath = getIntent().getStringExtra("Alpha");

        //  bgAudio();
        appAnalytics = new AppAnalytics(this);
        appAnalytics.setCurrentScreenName("SocraticTest", "SocraticTestActivity", this);

        initializeViews();
    }

    public void initializeViews() {
        testActivityImageViewHome = findViewById(R.id.testActivityImageViewHome);
        testActivityImageViewDaimond = findViewById(R.id.testActivityImageViewDaimond);

        fragmentContainer = findViewById(R.id.TestActivityfragmentContainer);
        videofragmentContainer = findViewById(R.id.videofragmentContainer);
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
        testActivityMainLayout.setBackgroundResource(R.mipmap.bg_pink);


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


        if(topicPath != null){
           // topicPath = "Grade2EnglishSocraticAdjectivesAdjectives";
            getTestDataHigher(selectedGrade, topicPath, selectedSubject);
            appAnalytics.setSocraticTestStart(testList.size(), test.getTopicName());

        } else{
            Log.wtf("-this","Test Activity Finished");
            finish();
        }


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
            case ("Geography"):
                testActivityMainLayout.setBackgroundResource(R.mipmap.bg_green);
                testActivityPopUpLayout.setBackgroundResource(R.drawable.geography_popup);
                break;
            case ("History"):
                testActivityMainLayout.setBackgroundResource(R.mipmap.bg_orange);
                testActivityPopUpLayout.setBackgroundResource(R.drawable.history_popup);
                break;
            case ("Science"):
                testActivityMainLayout.setBackgroundResource(R.mipmap.bg_blue_s);
                testActivityPopUpLayout.setBackgroundResource(R.drawable.science_popup);
                break;

        }
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
        //  bgAudioPause();
        bigQuesActive = true;
        start = true;
        continueClicked = false;
        String temp = question;
        if (type.equalsIgnoreCase("FillInTheBlank2Options") || type.equalsIgnoreCase("FillInTheBlank")) {
            temp = question.replaceAll("_", "_______");
        }
        testActivityTextViewQuestionBig.setText(temp);
        testActivityTextViewQuestionBig.setVisibility(View.VISIBLE);
        testActivityTextViewContinue.setVisibility(View.VISIBLE);
        appAnalytics.setBigQuestionStart(selectedSubject, test.getTopicName()+"" + "" + testIndex, test.getTopicName(), test.getType(), test.getAnswerList().size(), test.getDifficultyLevel()+"", (tempIndex + 1), (testIndex + 1));


    }

    public void continueClicked() {
        if(continueClicked == false) {
            continueClicked = true;
            bigQuesActive = false;
            start = false;
            testActivityTextViewQuestionBig.setVisibility(View.GONE);
            testActivityTextViewContinue.setVisibility(View.GONE);
            appAnalytics.setBigQuestionEvent(time, test.getTopicName() + "" + "" + testIndex, test.getTopicName() + "");

            if (test != null) {
                setTestFragment(type, testId, test);
            }
        }

    }

    public void setVideo() {

        videoActive = true;
        setTestFragment("video",testId,test);
        testIndex++;
        appAnalytics.setSocraticQuestionEnd(selectedSubject, test.getTopicName()+"" + "" + testIndex, test.getType(), test.getAnswerList().size(), test.getDifficultyLevel()+"", (tempIndex + 1), (testIndex + 1), test.getTopicName());

    }
    public void setTestAgain() {
        videoActive = false;
        setTestHigher();
    }

    public void closePopup() {
        expActive = false;
        start = false;
        appAnalytics.setExplanationEvent(time + "", test.getTopicName()+"" + "" + testIndex, test.getTopicName());

        testActivityImageViewPopUpClose.setVisibility(View.GONE);
        testActivityImageViewPopUpParrot.setVisibility(View.GONE);
        testActivityPopUpLayout.setVisibility(View.GONE);
        testIndex++;
        setTestHigher();



    }

    private Bitmap getBitmapFromAsset(String strName) {
        Log.wtf("-this", "Bitmap : " + strName);
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


    public void setTestHigher() {

        if (testList != null && testList.size() > 0) {
            totalscoreDB = testList.size() * 10;
            deduct = (100 / testList.size());
            Log.wtf("-this", "Test Index : " + testIndex + " TestList Size : " + testList.size()+ " Deduct : "+deduct);
            if (testIndex < testList.size()) {
                test = testList.get(testIndex);

                type = test.getType();

                appAnalytics.setSocraticQuestionStart(selectedSubject, test.getTopicName()+"" + "" + testIndex, test.getType(), test.getAnswerList().size(), test.getDifficultyLevel()+"", (tempIndex + 1), (testIndex + 1), test.getTopicName());
                setBigQuestion(test.getQuestion().getText());
            } else {
                flushValues();
              //  startActivity(new Intent(this, MainActivity.class));
                finish();
            }
        } else {
            appAnalytics.setSocraticTestEnd(testList.size(), questionAttempt, correctCount, totalScore, test.getTopicName(), test.getTopicName() + "" + testIndex, "Test");
            appAnalytics.setSocraticTestCompleted(testList.size(), questionAttempt, correctCount, totalScore, test.getTopicName());
            flushValues();
           // startActivity(new Intent(this, MainActivity.class));
            finish();
        }

    }


    public void getTestDataHigher(String grade, String topic, String subject) {
        Log.wtf("-this","Subject : "+subject+" Topic : "+topic);
        String json = null;
        String fileName = "AdaptiveTestExample.json";
        fileName = selectedGrade+selectedSubject+"Socratic.json";
        Log.wtf("-this","FileName : "+fileName);
        try {
            InputStream filedata = null;
            filedata = this.getAssets().open(fileName);
            int size = filedata.available();
            byte[] buffer = new byte[size];
            filedata.read(buffer);
            filedata.close();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                json = new String(buffer, StandardCharsets.UTF_8);
            }
            Log.wtf("-this", "803 TOPIC : " + topic + " Filename : " + fileName);
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray(topic.replaceAll(" ",""));
            // JSONArray jsonArray = jsonObject.getJSONArray("Grade6HistoryIndusValleyCivilizationIndusValleyCivilizationPart1");
            Log.wtf("-this", "TOPIC : " + topic + " Array Length : " + jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++) {
                TestJsonParseModel testObject = new TestJsonParseModel();
//                Log.wtf("-this","Array Index : "+ i);
                JSONObject obj = jsonArray.getJSONObject(i);
                Log.wtf("-this", "Array Index : " + i + " " + obj.getString("Type"));
                String type = obj.getString("Type");
                String topicN = obj.getString("TopicName");
                testObject.setTopicName(topicName);
                testObject.setVideoName(obj.getString("VideoName"));
                Log.wtf("-this","VideoName : "+testObject.getVideoName());
                subTopicName = topicN;
                if (topicName == "") {
                    topicName = subTopicName;
                }
                testObject.setType(type);
                JSONObject questionObj = obj.getJSONObject("Question");
                QuestionModel question = new QuestionModel();

                if(type.equalsIgnoreCase("SoundRecognitionSpellingPhonics") || type.equalsIgnoreCase("SoundRecognitionSpellingPhonics2Options")) {
                    question = new QuestionModel(questionObj.getString("Text"),
                            questionObj.getString("Image"), questionObj.getString("Audio"), questionObj.getString("Hint"), questionObj.getString("QuestionButtonAudio"));

                } else {
                    question = new QuestionModel(questionObj.getString("Text"),
                            questionObj.getString("Image"), questionObj.getString("Audio"), questionObj.getString("Hint"));

                }
                testObject.setQuestion(question);
                ExplainationModel explaination = new ExplainationModel("",
                        "", "");
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
                        if (optionObj.getString("Text").equalsIgnoreCase("1")) {
                            optionList.add(new OptionsModel("True", optionObj.getString("Image"), optionObj.getString("Audio")));
                        } else if (optionObj.getString("Text").equalsIgnoreCase("0")) {
                            optionList.add(new OptionsModel("False", optionObj.getString("Image"), optionObj.getString("Audio")));
                        } else {
                            optionList.add(new OptionsModel(optionObj.getString("Text"), optionObj.getString("Image"), optionObj.getString("Audio")));
                        }
                    }
                    testObject.setOptionList(optionList);
                    ArrayList<AnwserModel> answerList = new ArrayList<>();
                    if (type.equalsIgnoreCase("DragTextBoxToPicture2Options") || type.equalsIgnoreCase("DragTextBoxToPicture")
                            || type.equalsIgnoreCase("Labelling")) {
                    } else {
                        JSONArray answerArray = obj.getJSONArray("CorrectAnswerOptions");
                        for (int k = 0; k < answerArray.length(); k++) {
                            JSONObject answerObj = answerArray.getJSONObject(k);
                            if (answerObj.getString("Text").equalsIgnoreCase("1")) {
                                answerList.add(new AnwserModel("True", answerObj.getString("Image"), answerObj.getString("Audio")));
                            } else if (answerObj.getString("Text").equalsIgnoreCase("0")) {
                                answerList.add(new AnwserModel("False", answerObj.getString("Image"), answerObj.getString("Audio")));
                            } else {
                                answerList.add(new AnwserModel(answerObj.getString("Text"), answerObj.getString("Image"), answerObj.getString("Audio")));
                            }
                        }
                    }
                    testObject.setAnswerList(answerList);
                }
                testList.add(testObject);
            }
            Log.wtf("-this", "Get Test : TestList Size : " + testList.size());
            setTestHigher();
        } catch (
                JSONException e) {
            setTestHigher();
            e.printStackTrace();
        } catch (
                UnsupportedEncodingException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    public void setTestFragment(String type, int testId, TestJsonParseModel test) {
        SimpleTestActivity.unSocratic = false;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (type) {
            case ("video"):

                fragmentTransaction.add(R.id.videofragmentContainer, new videoFragment(test.getTopicName(), SocraticActivity.this, SocraticActivity.this,test.getVideoName(),selectedGrade,selectedSubject));
                fragmentTransaction.commit();
                break;
            case ("OrdinaryMCQ"):

                fragmentTransaction.add(R.id.TestActivityfragmentContainer, new OrdinaryMCQ(testId, test, SocraticActivity.this, SocraticActivity.this));
                fragmentTransaction.commit();

                break;
            case ("OrdinaryMCQLong"):
                fragmentTransaction.add(R.id.TestActivityfragmentContainer, new OrdinaryMCQLong(testId, test, SocraticActivity.this, SocraticActivity.this));
                fragmentTransaction.commit();
                break;
            case ("OrdinaryMCQ2Options"):
                fragmentTransaction.add(R.id.TestActivityfragmentContainer, new OrdinaryMCQTwoOptions(testId, test, SocraticActivity.this, SocraticActivity.this));
                fragmentTransaction.commit();

                break;
            case ("WordProblem"):
                fragmentTransaction.add(R.id.TestActivityfragmentContainer, new WordProblem(testId, test, SocraticActivity.this, SocraticActivity.this));
                fragmentTransaction.commit();

                break;
            case ("FillInTheBlank"):
                if (selectedSubject.equalsIgnoreCase("Urdu") || selectedSubject.equalsIgnoreCase("History")) {
                    fragmentTransaction.add(R.id.TestActivityfragmentContainer, new FillInTheBlankFourOptions(testId, test, SocraticActivity.this, SocraticActivity.this));
                    fragmentTransaction.commit();
                } else {
                    fragmentTransaction.add(R.id.TestActivityfragmentContainer, new FillInTheBlankFourOptionsEnglish(testId, test, SocraticActivity.this, SocraticActivity.this));
                    fragmentTransaction.commit();
                }
                break;
            case ("OrdinaryMCQLong2Options"):
                fragmentTransaction.add(R.id.TestActivityfragmentContainer, new OrdinaryMCQTwoOptionsLong(testId, test, SocraticActivity.this, SocraticActivity.this));
                fragmentTransaction.commit();

                break;
            case ("MatchTextToText"):
                fragmentTransaction.add(R.id.TestActivityfragmentContainer, new MatchTextToText(testId, test, SocraticActivity.this, SocraticActivity.this));
                fragmentTransaction.commit();

                break;
            case ("MatchTextToText2Options"):
                fragmentTransaction.add(R.id.TestActivityfragmentContainer, new MatchTextToTextTwoOptions(testId, test, SocraticActivity.this, SocraticActivity.this));
                fragmentTransaction.commit();

                break;
            case ("Labelling"):
                fragmentTransaction.add(R.id.TestActivityfragmentContainer, new Labelling(testId, test, SocraticActivity.this, SocraticActivity.this));
                fragmentTransaction.commit();

                break;
            case ("Labelling2Options"):
                fragmentTransaction.add(R.id.TestActivityfragmentContainer, new LabellingTwoOptions(testId, test, SocraticActivity.this, SocraticActivity.this));
                fragmentTransaction.commit();

                break;
            case ("PictureQuestionTextAnswer"):
                fragmentTransaction.add(R.id.TestActivityfragmentContainer, new PictureQuestionTextAnswer(testId, test, SocraticActivity.this, SocraticActivity.this));
                fragmentTransaction.commit();

                break;
            case ("PictureQuestionTextAnswer2Options"):
                fragmentTransaction.add(R.id.TestActivityfragmentContainer, new PictureQuestionTextAnswerTwoOptions(testId, test, SocraticActivity.this, SocraticActivity.this));
                fragmentTransaction.commit();

                break;
            case ("MatchingImageToImage"):
                fragmentTransaction.add(R.id.TestActivityfragmentContainer, new MatchingImageToImage(testId, test, SocraticActivity.this, SocraticActivity.this));
                fragmentTransaction.commit();

                break;
            case ("Comprehension2ChoicesQuestionImage"):
                fragmentTransaction.add(R.id.TestActivityfragmentContainer, new ComprehensionTwoChoicesQuestionImage(testId, test, SocraticActivity.this, SocraticActivity.this));
                fragmentTransaction.commit();

                break;
            case ("Comprehension4Choices"):
                fragmentTransaction.add(R.id.TestActivityfragmentContainer, new ComprehensionFourChoices(testId, test, SocraticActivity.this, SocraticActivity.this));
                fragmentTransaction.commit();

                break;
            case ("Comprehension2Choices"):
                fragmentTransaction.add(R.id.TestActivityfragmentContainer, new ComprehensionTwoChoices(testId, test, SocraticActivity.this, SocraticActivity.this));
                fragmentTransaction.commit();

                break;
            case ("DragTextBoxToPicture"):
                fragmentTransaction.add(R.id.TestActivityfragmentContainer, new DragTextBoxToPicture(testId, test, SocraticActivity.this, SocraticActivity.this));
                fragmentTransaction.commit();

                break;
            case ("DragTextBoxToPicture2Options"):
                Log.wtf("-this", "Drag Text to Picture Two options : " + testIndex);
                fragmentTransaction.add(R.id.TestActivityfragmentContainer, new DragTextBoxToPictureTwoOptions(testId, test, SocraticActivity.this, SocraticActivity.this));
                fragmentTransaction.commit();

                break;
            case ("PictureTextQuestionPictureAnswer"):
                fragmentTransaction.add(R.id.TestActivityfragmentContainer, new Picture_Text_Question_Picture_Answer(testId, test, SocraticActivity.this, SocraticActivity.this));
                fragmentTransaction.commit();

                break;
            case ("PictureTextQuestionPictureAnswer2Options"):
                fragmentTransaction.add(R.id.TestActivityfragmentContainer, new Picture_Text_Question_Picture_Answer_Two_Options(testId, test, SocraticActivity.this, SocraticActivity.this));
                fragmentTransaction.commit();

                break;
            case ("BigPictureQuestionTextAnswer"):
                fragmentTransaction.add(R.id.TestActivityfragmentContainer, new BigPictureQuestionTextAnswer(testId, test, SocraticActivity.this, SocraticActivity.this));
                fragmentTransaction.commit();

                break;
            case ("BigPictureQuestionTextAnswer2Options"):
                fragmentTransaction.add(R.id.TestActivityfragmentContainer, new BigPictureQuestionTextAnswerTwoOptions(testId, test, SocraticActivity.this, SocraticActivity.this));
                fragmentTransaction.commit();

                break;
            case ("FillInTheBlank2Options"):
                if (selectedSubject.equalsIgnoreCase("Urdu") || selectedSubject.equalsIgnoreCase("Hostory")) {
                    fragmentTransaction.add(R.id.TestActivityfragmentContainer, new FillInTheBlank(testId, test, SocraticActivity.this, SocraticActivity.this));
                    fragmentTransaction.commit();
                } else {
                    fragmentTransaction.add(R.id.TestActivityfragmentContainer, new FillInTheBlankEnglish(testId, test, SocraticActivity.this, SocraticActivity.this));
                    fragmentTransaction.commit();
                }

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
                continueClicked();
                break;
            case R.id.testActivityImageViewPopUpClose:
                closePopup();
                break;
            case R.id.testActivityImageViewHome:

           //     startActivity(new Intent(this, MainActivity.class));
                if (testActivityTextViewQuestionBig.getVisibility() == View.VISIBLE) {
                    appAnalytics.setSocraticTestEnd(testList.size(), questionAttempt, correctCount, totalScore, test.getTopicName(), test.getTopicName() + "" + testIndex, "BigQuestion");
                  //  String timestamp = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
                    //  NotificationDatabaseHandler.getInstance(getApplicationContext()).InsertNotification("text", "TestIncomplete", topic+"$"+selectedSubject+"$"+sharedPrefs.getString("GradeSelected", "Grade 6"), Integer.parseInt(timestamp));
                    appAnalytics.setSocraticTestHomeBack(selectedSubject, "BigQuestion");
                } else if (testActivityPopUpLayout.getVisibility() == View.VISIBLE) {
                    appAnalytics.setSocraticTestEnd(testList.size(), questionAttempt, correctCount, totalScore, test.getTopicName(), test.getTopicName() + "" + testIndex, "Explanation");
                //    String timestamp = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
                    //   NotificationDatabaseHandler.getInstance(getApplicationContext()).InsertNotification("text", "TestIncomplete", topic+"$"+selectedSubject+"$"+sharedPrefs.getString("GradeSelected", "Grade 6"), Integer.parseInt(timestamp));
                    appAnalytics.setSocraticTestHomeBack(selectedSubject, "Explanation");
                }  else if(videoActive) {
                    appAnalytics.setSocraticTestEnd(testList.size(), questionAttempt, correctCount, totalScore, test.getTopicName(), test.getTopicName() + "" + testIndex, "Video");
                  //  String timestamp = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
                    //    NotificationDatabaseHandler.getInstance(getApplicationContext()).InsertNotification("text", "TestIncomplete", topic+"$"+selectedSubject+"$"+sharedPrefs.getString("GradeSelected", "Grade 6"), Integer.parseInt(timestamp));
                    appAnalytics.setSocraticTestHomeBack(selectedSubject, "Video");
                }
                else {
                appAnalytics.setSocraticTestEnd(testList.size(), questionAttempt, correctCount, totalScore, test.getTopicName(), test.getTopicName() + "" + testIndex, "Test");
                //  String timestamp = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
                //    NotificationDatabaseHandler.getInstance(getApplicationContext()).InsertNotification("text", "TestIncomplete", topic+"$"+selectedSubject+"$"+sharedPrefs.getString("GradeSelected", "Grade 6"), Integer.parseInt(timestamp));
                appAnalytics.setSocraticTestHomeBack(selectedSubject, "Test");
            }
                flushValues();
                finish();
                break;
            case R.id.testActivityImageViewDaimond:

                break;
        }

    }

    @Override
    public void onBackPressed() {
        if (mediaPlayerBg != null) {
            mediaPlayerBg.release();
        }
        if (testActivityTextViewQuestionBig.getVisibility() == View.VISIBLE) {
            appAnalytics.setSocraticTestEnd(testList.size(), questionAttempt, correctCount, totalScore, test.getTopicName(), test.getTopicName() + "" + testIndex, "BigQuestion");

        } else if (testActivityPopUpLayout.getVisibility() == View.VISIBLE) {
            appAnalytics.setSocraticTestEnd(testList.size(), questionAttempt, correctCount, totalScore, test.getTopicName(), test.getTopicName() + "" + testIndex, "Explanation");

        } else if(videoActive){
            appAnalytics.setSocraticTestEnd(testList.size(), questionAttempt, correctCount, totalScore, test.getTopicName(), test.getTopicName() + "" + testIndex, "Video");

        } else {
            appAnalytics.setSocraticTestEnd(testList.size(), questionAttempt, correctCount, totalScore, test.getTopicName(), test.getTopicName() + "" + testIndex, "Test");

        }
        //  TriggerFirebaseSync();
      //  startActivity(new Intent(this, MainActivity.class));
        flushValues();
        finish();
    }

    @Override
    protected void onDestroy() {
        if (mediaPlayerBg != null) {
            mediaPlayerBg.release();
        }
        flushValues();
        super.onDestroy();
    }

    void flushValues() {
        alienLife = 100;
        parrotLife = 100;
        correctCount = 0;
        totalScore = 0;
        testIndex = 0;
        continueClicked = false;
    }


    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        Log.wtf("-this","OnResume");
        super.onResume();
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

    }
}