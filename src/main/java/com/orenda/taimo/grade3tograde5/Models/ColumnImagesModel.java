package com.orenda.taimo.grade3tograde5.Models;

public class ColumnImagesModel {
    String image = null;
    String Text = null;

    public ColumnImagesModel(String image, String text) {
        this.image = image;
        Text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }
}
