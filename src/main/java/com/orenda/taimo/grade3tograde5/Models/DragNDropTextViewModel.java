package com.orenda.taimo.grade3tograde5.Models;

import android.widget.TextView;

public class DragNDropTextViewModel {
    TextView textView = null;
    boolean dragListenerActive = false;
    boolean touchListenerActive = false;

    public DragNDropTextViewModel(TextView textView, boolean dragListenerActive, boolean touchListenerActive) {
        this.textView = textView;
        this.dragListenerActive = dragListenerActive;
        this.touchListenerActive = touchListenerActive;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public boolean isDragListenerActive() {
        return dragListenerActive;
    }

    public void setDragListenerActive(boolean dragListenerActive) {
        this.dragListenerActive = dragListenerActive;
    }

    public boolean isTouchListenerActive() {
        return touchListenerActive;
    }

    public void setTouchListenerActive(boolean touchListenerActive) {
        this.touchListenerActive = touchListenerActive;
    }
}
