package firebase.analytics;

import android.content.Context;
import android.os.Bundle;

//import com.facebook.appevents.AppEventsLogger;
//import com.google.firebase.analytics.FirebaseAnalytics;

public class AppTimeAnalytics {
    Long appTime;
    Long gameTime;
//    FirebaseAnalytics mFirebaseAnalytics;
    public String gameName;
    private Context context;

    public AppTimeAnalytics(Long appTime, Context context) {
        this.appTime = appTime;
        this.context = context;
    }

    public AppTimeAnalytics(Long gameTime, String gameName, Context context) {
        this.gameTime = gameTime;
        this.gameName = gameName;
        this.context = context;
    }

    public void setAppTimeAnalytics(){
//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle bundle=new Bundle();
//        bundle.putLong("totalAppTime",this.appTime);
//        mFirebaseAnalytics.logEvent("Total_AppTime", bundle);
//        AppEventsLogger logger = AppEventsLogger.newLogger(context);
//        logger.logEvent("Total_AppTime", bundle);
    }

    public void setGameTimeAnalytics(){
//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle bundle=new Bundle();
//        bundle.putLong("gameTime",this.gameTime);
//        bundle.putString("gameName",this.gameName);
//        mFirebaseAnalytics.logEvent("Total_GameTime", bundle);
    }

}
