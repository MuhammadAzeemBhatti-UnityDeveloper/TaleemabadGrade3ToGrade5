package firebase.analytics;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

//import com.google.firebase.analytics.FirebaseAnalytics;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class SignupAnalytic {
//    FirebaseAnalytics mFirebaseAnalytics;
    public String signupMethod;
    private Context context;


    public SignupAnalytic(String signupMethod, Context context) {
        this.signupMethod = signupMethod;
        this.context=context;
    }


    public void setSignupAnalytics(){
//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//
//        Bundle params = new Bundle();
//        params.putString("signupMethod",signupMethod);
//        mFirebaseAnalytics.logEvent("SignupEvent_LoginMethod", params);
    }

    public void setSuccessSignupAnalytics(String phoneNumber, String UID){
//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle params = new Bundle();
//        params.putString("signupMethod",signupMethod);
//        params.putString("UID", UID);
//        params.putString("phoneNumber",phoneNumber);
//        mFirebaseAnalytics.logEvent("SignupEvent_Success", params);
//        //SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//        //String signupDate = simpleDateFormat.format(Calendar.getInstance().getTime());
//        String signupDate =String.valueOf(System.currentTimeMillis());
//        mFirebaseAnalytics.setUserProperty("SignUp_Success_Time", signupDate);

    }


    public void setSignupBackAnalytics(){
//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        mFirebaseAnalytics.logEvent("SignupEvent_LoginBack",null);
    }


    public void setUserTypeAnalytics(String userType, String userReason){
//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle params = new Bundle();
//        params.putString("SelectedOption", userType);
//        if(userReason!=null){
//            params.putString("UserReason",userReason);
//        }
//        mFirebaseAnalytics.logEvent("SignupEvent_Usertype",params);
    }
    public void setQuestionnaireAnalytics(String radioOption, String userReason){
//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle params = new Bundle();
//        if(radioOption!=null){
//            params.putString("SelectedOption",radioOption);
//        }
//        if(userReason!=null){
//            params.putString("UserReason",userReason);
//        }
//        mFirebaseAnalytics.logEvent("SignupEvent_Questionnaire",params);
    }
}
