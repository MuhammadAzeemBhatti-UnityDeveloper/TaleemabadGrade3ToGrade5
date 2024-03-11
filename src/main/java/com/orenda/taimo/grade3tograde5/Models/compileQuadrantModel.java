package com.orenda.taimo.grade3tograde5.Models;

public class compileQuadrantModel {
    int startQuadrant;
    int endQuadrant;

    public compileQuadrantModel(int startQuadrant, int endQuadrant) {
        this.startQuadrant = startQuadrant;
        this.endQuadrant = endQuadrant;
    }

    public int getStartQuadrant() {
        return startQuadrant;
    }

    public void setStartQuadrant(int startQuadrant) {
        this.startQuadrant = startQuadrant;
    }

    public int getEndQuadrant() {
        return endQuadrant;
    }

    public void setEndQuadrant(int endQuadrant) {
        this.endQuadrant = endQuadrant;
    }
}
