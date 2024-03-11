package firebase.analytics;

import android.content.Context;
import android.os.Bundle;

//import com.google.firebase.analytics.FirebaseAnalytics;

public class GameAnalytics {
//    FirebaseAnalytics mFirebaseAnalytics;
    public String GameName;
    private Context context;
    public GameAnalytics(String gameName, Context context) {
        GameName = gameName;
        this.context = context;
    }

    public void setStartGameAnalytic(){
//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        mFirebaseAnalytics.logEvent("GameEvent_Start_"+GameName, null);
    }

    public void setEndGameAnalytic(){
//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        mFirebaseAnalytics.logEvent("GameEvent_End_"+GameName, null);
    }

    public void setGameBackAnalytic(){
//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        mFirebaseAnalytics.logEvent("GameEvent_Back_"+GameName, null);
    }
}
