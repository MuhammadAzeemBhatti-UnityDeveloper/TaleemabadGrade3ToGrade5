package com.orenda.taimo.grade3tograde5.Models;

import java.util.ArrayList;

public class TestJsonParseModel {
    String type = null;
     String topicName = null;
     String difficultyLevel = "easy";
     String timeStamp = null;
     String videoName = null;
    QuestionModel question = null;
    ExplainationModel explaination = null;
    ArrayList<OptionsModel> optionList = new ArrayList<>();
    ArrayList<AnwserModel> answerList = new ArrayList<>();
    ArrayList<ColumnImagesModel> leftColumnList = new ArrayList<>();
    ArrayList<ColumnImagesModel> RightList = new ArrayList<>();


    public TestJsonParseModel(String type, String topicName, String difficultyLevel, QuestionModel question, ExplainationModel explaination, ArrayList<OptionsModel> optionList, ArrayList<AnwserModel> answerList) {
        this.type = type;
        this.topicName = topicName;
        this.difficultyLevel = difficultyLevel;
        this.question = question;
        this.explaination = explaination;
        this.optionList = optionList;
        this.answerList = answerList;
    }

    public TestJsonParseModel() {

    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ExplainationModel getExplaination() {
        return explaination;
    }

    public void setExplaination(ExplainationModel explaination) {
        this.explaination = explaination;
    }

    public QuestionModel getQuestion() {
        return question;
    }

    public void setQuestion(QuestionModel question) {
        this.question = question;
    }

    public ArrayList<OptionsModel> getOptionList() {
        return optionList;
    }

    public void setOptionList(ArrayList<OptionsModel> optionList) {
        this.optionList = optionList;
    }

    public ArrayList<AnwserModel> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(ArrayList<AnwserModel> answerList) {
        this.answerList = answerList;
    }

    public ArrayList<ColumnImagesModel> getLeftColumnList() {
        return leftColumnList;
    }

    public void setLeftColumnList(ArrayList<ColumnImagesModel> leftColumnList) {
        this.leftColumnList = leftColumnList;
    }

    public ArrayList<ColumnImagesModel> getRightList() {
        return RightList;
    }

    public void setRightList(ArrayList<ColumnImagesModel> rightList) {
        RightList = rightList;
    }
}
