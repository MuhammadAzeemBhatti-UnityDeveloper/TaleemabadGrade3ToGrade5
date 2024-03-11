package com.orenda.taimo.grade3tograde5.Models;

import android.graphics.Paint;

public class LineModel {

    private Paint mPaint;
    private float startX;
    private float startY;
    private float endX;
    private float endY;
    private int startQuadrant = 0;
    private int EndQuadrant = 0;

    public LineModel() {
    }

    public LineModel(Paint mPaint, float startX, float startY, float endX, float endY) {
        this.mPaint = mPaint;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    public int getStartQuadrant() {
        return startQuadrant;
    }

    public void setStartQuadrant(int startQuadrant) {
        this.startQuadrant = startQuadrant;
    }

    public int getEndQuadrant() {
        return EndQuadrant;
    }

    public void setEndQuadrant(int endQuadrant) {
        EndQuadrant = endQuadrant;
    }

    public Paint getmPaint() {
        return mPaint;
    }

    public void setmPaint(Paint mPaint) {
        this.mPaint = mPaint;
    }

    public float getStartX() {
        return startX;
    }

    public void setStartX(float startX) {
        this.startX = startX;
    }

    public float getStartY() {
        return startY;
    }

    public void setStartY(float startY) {
        this.startY = startY;
    }

    public float getEndX() {
        return endX;
    }

    public void setEndX(float endX) {
        this.endX = endX;
    }

    public float getEndY() {
        return endY;
    }

    public void setEndY(float endY) {
        this.endY = endY;
    }
}
