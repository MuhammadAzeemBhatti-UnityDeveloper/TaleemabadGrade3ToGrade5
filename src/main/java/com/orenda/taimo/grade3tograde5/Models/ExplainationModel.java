package com.orenda.taimo.grade3tograde5.Models;

public class ExplainationModel {
    String text = "";
    String image =  "";
    String audio = "";

    public ExplainationModel(String text, String image, String audio) {
        this.text = text;
        this.image = image;
        this.audio = audio;
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
}
