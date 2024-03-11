package com.orenda.taimo.grade3tograde5.Models;

public class QuestionModel {
    String text = null;
    String image = null;
    String audio = null;
    String hint = null;
    String questionbuttonaudio = null;

    public QuestionModel() {}


    public QuestionModel(String text, String image, String audio, String hint, String questionbuttonaudio) {
        this.text = text;
        this.image = image;
        this.audio = audio;
        this.hint = hint;
        this.questionbuttonaudio = questionbuttonaudio;
    }
    public QuestionModel(String text, String image, String audio, String hint) {
        this.text = text;
        this.image = image;
        this.audio = audio;
        this.hint = hint;
    }
    public String getQuestionButtonAudio() {
        return questionbuttonaudio;
    }
    public void setQuestionButtonAudio(String text) {
        this.questionbuttonaudio = text;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getAudio() {
        return audio;
    }
    public void setAudio(String audio) {
        this.audio = audio;
    }
    public String getHint() {
        return hint;
    }
    public void setHint(String hint) {
        this.hint = hint;
    }
}