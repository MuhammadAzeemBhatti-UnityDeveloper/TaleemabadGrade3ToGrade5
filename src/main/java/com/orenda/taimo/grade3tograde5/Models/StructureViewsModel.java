package com.orenda.taimo.grade3tograde5.Models;

import android.widget.LinearLayout;
import android.widget.TextView;

public class StructureViewsModel {
    LinearLayout linearLayout;
    TextView textView;

    public StructureViewsModel(LinearLayout linearLayout, TextView textView) {
        this.linearLayout = linearLayout;
        this.textView = textView;
    }

    public LinearLayout getLinearLayout() {
        return linearLayout;
    }

    public void setLinearLayout(LinearLayout linearLayout) {
        this.linearLayout = linearLayout;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }
}
