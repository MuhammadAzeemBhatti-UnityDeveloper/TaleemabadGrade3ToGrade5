package firebase.analytics;

import android.content.Context;

//import com.google.firebase.analytics.FirebaseAnalytics;

public class MenuAnalytics {
   // FirebaseAnalytics mFirebaseAnalytics;
    public String SubjectName;
    private Context context;

    public MenuAnalytics(String subjectName, Context context) {
        SubjectName = subjectName;
        this.context = context;
    }

    public void setSubjectSelectMenuAnalytics(){
//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        mFirebaseAnalytics.logEvent("Subject_Selected_"+SubjectName, null);
    }

    public void setPurchaseMenuAnalytics(){
//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        mFirebaseAnalytics.logEvent("Purchase_Clicked", null);
    }
    public void setSubscriptionMenuAnalytics(){
//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        mFirebaseAnalytics.logEvent("Subscription_Clicked", null);
    }
    public void setSimPaisaSubscriptionMenuAnaltics(){
//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        mFirebaseAnalytics.logEvent("SimPaisa_Subscription_Clicked", null);
    }

    public void setGooglePurchaseMenuAnaltics(){
//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        mFirebaseAnalytics.logEvent("Google_Purchase_Clicked", null);
    }

    public void setHotlinePurchaseMenuAnalytics(){
//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        mFirebaseAnalytics.logEvent("Hotline_Purchase_Clicked", null);
    }
    public void setCodPurchaseMenuAnalytics(){
//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        mFirebaseAnalytics.logEvent("COD_Purchase_Clicked", null);
    }
    public void setMenuBackAnalytics(){
//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        mFirebaseAnalytics.logEvent("Subject_Selected_Back_"+SubjectName, null);
    }


}
