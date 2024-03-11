package com.orenda.taimo.grade3tograde5.Models;

import android.widget.TextView;

public class FillInTheBlanksTextViewModel {
    TextView textView;
    String answer;

    public FillInTheBlanksTextViewModel(TextView textView, String answer) {

        this.textView = textView;
        this.answer = answer;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
