package com.orenda.taimo.grade3tograde5.Models;

import android.widget.ImageView;

public class MatchImageToImageViewModel {
    ImageView imageView1 = null;
    ImageView imageView2 = null;

    public MatchImageToImageViewModel(ImageView imageView1, ImageView imageView2) {
        this.imageView1 = imageView1;
        this.imageView2 = imageView2;
    }

    public ImageView getImageView1() {
        return imageView1;
    }

    public void setImageView1(ImageView imageView1) {
        this.imageView1 = imageView1;
    }

    public ImageView getImageView2() {
        return imageView2;
    }

    public void setImageView2(ImageView imageView2) {
        this.imageView2 = imageView2;
    }
}
