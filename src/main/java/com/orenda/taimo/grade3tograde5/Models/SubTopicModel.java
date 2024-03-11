package com.orenda.taimo.grade3tograde5.Models;

public class SubTopicModel {
    String subTopicEnglish = "";
    String subTopicUrdu = "";
    String path= "";

    public SubTopicModel(String subTopicEnglish, String subTopicUrdu, String path) {
        this.subTopicEnglish = subTopicEnglish;
        this.subTopicUrdu = subTopicUrdu;
        this.path = path;
    }

    public SubTopicModel() {

    }

    public String getSubTopicEnglish() {
        return subTopicEnglish;
    }

    public void setSubTopicEnglish(String subTopicEnglish) {
        this.subTopicEnglish = subTopicEnglish;
    }

    public String getSubTopicUrdu() {
        return subTopicUrdu;
    }

    public void setSubTopicUrdu(String subTopicUrdu) {
        this.subTopicUrdu = subTopicUrdu;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
