package com.orenda.taimo.grade3tograde5;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;

import firebase.analytics.AppAnalytics;

public class CustomDialog extends Dialog implements View.OnClickListener {
    
    public Activity context;
    public Dialog dialog;
    public Button buttonOp1, buttonOp2, buttonOp3;
    public ImageView imageViewCancel;
    
    public CustomDialog (@NonNull Activity context) {
        super(context);
        this.context = context;
    }
    
    public CustomDialog (@NonNull Activity context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        
        buttonOp1 = findViewById(R.id.buttonOption1);
        buttonOp2 = findViewById(R.id.buttonOption2);
        buttonOp3 = findViewById(R.id.buttonOption3);
        
        buttonOp1.setOnClickListener(this);
        buttonOp2.setOnClickListener(this);
        buttonOp3.setOnClickListener(this);
        
        imageViewCancel = findViewById(R.id.imageViewCancel);
        imageViewCancel.setOnClickListener(this);
    }
    
    @Override
    public void onClick (View view) {
        switch (view.getId()) {
            case R.id.imageViewCancel: {
                dismiss();
                break;
            }
            case R.id.buttonOption1:
            case R.id.buttonOption2:
            case R.id.buttonOption3:
                //makeToast(((Button)view).getText());
                new AppAnalytics(context).VideoStreamIssue(((Button) view).getText().toString());
                dismiss();
                break;
        }
    }
    
    private void makeToast (CharSequence text) {
        Toast.makeText(context, text.toString(), Toast.LENGTH_SHORT).show();
    }
}
