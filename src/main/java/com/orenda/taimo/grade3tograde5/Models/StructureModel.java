package com.orenda.taimo.grade3tograde5.Models;

import java.util.ArrayList;

public class StructureModel {
    String topic = "";
    ArrayList<SubTopicModel> subTopicList = new ArrayList<>();

    public StructureModel(String topic, ArrayList<SubTopicModel> subTopicList) {
        this.topic = topic;
        this.subTopicList = subTopicList;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public ArrayList<SubTopicModel> getSubTopicList() {
        return subTopicList;
    }

    public void setSubTopicList(ArrayList<SubTopicModel> subTopicList) {
        this.subTopicList = subTopicList;
    }
}
