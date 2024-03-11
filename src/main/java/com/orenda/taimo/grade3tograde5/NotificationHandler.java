package com.orenda.taimo.grade3tograde5;

import android.content.Intent;
//import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class NotificationHandler extends AppCompatActivity {
String gameString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_handler);
        Intent getintent = getIntent();
        gameString=getintent.getStringExtra("GameAlpha");
        Intent intent =new Intent(NotificationHandler.this,UnityPlayerActivity.class);
        startActivity(intent);

    }

    /*@Override
    public void onStop() {
        super.onStop();
        UnityPlayer.UnitySendMessage("changer", "load_scene", gameString);

    }*/

}