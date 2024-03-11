package com.orenda.taimo.grade3tograde5.Models;

import java.util.ArrayList;

public class TopicModel {
    int id;
    String subject;
    String topicEnglish;
    String topicUrdu;
    ArrayList<SubTopicModel> subTopicList;

    public TopicModel() {

    }

    public TopicModel(String subject, String topicEnglish, String topicUrdu, ArrayList<SubTopicModel> subTopicList) {
        this.subject = subject;
        this.topicEnglish = topicEnglish;
        this.topicUrdu = topicUrdu;
        this.subTopicList = subTopicList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTopicEnglish() {
        return topicEnglish;
    }

    public void setTopicEnglish(String topicEnglish) {
        this.topicEnglish = topicEnglish;
    }

    public String getTopicUrdu() {
        return topicUrdu;
    }

    public void setTopicUrdu(String topicUrdu) {
        this.topicUrdu = topicUrdu;
    }

    public ArrayList<SubTopicModel> getSubTopicList() {
        return subTopicList;
    }

    public void setSubTopicList(ArrayList<SubTopicModel> subTopicList) {
        this.subTopicList = subTopicList;
    }
}
