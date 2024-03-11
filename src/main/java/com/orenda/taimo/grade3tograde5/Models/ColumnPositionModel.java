package com.orenda.taimo.grade3tograde5.Models;

public class ColumnPositionModel {
    float viewStartX = 0;
    float viewStartY = 0;
    float viewEndX = 0;
    float viewEndY = 0;
    float viewXSize = 0;
    float viewYSize = 0;
    String text = null;

    public ColumnPositionModel(float viewStartX, float viewStartY, float viewXSize, float viewYSize, String text) {
        this.viewStartX = viewStartX;
        this.viewStartY = viewStartY;
        this.viewXSize = viewXSize;
        this.viewYSize = viewYSize;
        this.text = text;
    }

    public float getViewStartX() {
        return viewStartX;
    }

    public void setViewStartX(float viewStartX) {
        this.viewStartX = viewStartX;
    }

    public float getViewStartY() {
        return viewStartY;
    }

    public void setViewStartY(float viewStartY) {
        this.viewStartY = viewStartY;
    }

    public float getViewEndX() {
        viewEndX = viewStartX+viewXSize;
        return viewEndX;
    }

    public void setViewEndX(float viewEndX) {
        this.viewEndX = viewEndX;
    }

    public float getViewEndY() {
        viewEndY = viewStartY+viewYSize;
        return viewEndY;
    }

    public void setViewEndY(float viewEndY) {
        this.viewEndY = viewEndY;
    }

    public float getViewXSize() {
        viewEndY = viewStartY+viewYSize;
        return viewXSize;
    }

    public void setViewXSize(float viewXSize) {
        this.viewXSize = viewXSize;
    }

    public float getViewYSize() {
        return viewYSize;
    }

    public void setViewYSize(float viewYSize) {
        this.viewYSize = viewYSize;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
