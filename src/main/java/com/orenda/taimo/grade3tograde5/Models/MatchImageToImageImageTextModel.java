package com.orenda.taimo.grade3tograde5.Models;

import android.widget.ImageView;
import android.widget.TextView;

public class MatchImageToImageImageTextModel {
    ImageView imageView = null;
    TextView textView = null;

    public MatchImageToImageImageTextModel(ImageView imageView, TextView textView) {
        this.imageView = imageView;
        this.textView = textView;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }
}
