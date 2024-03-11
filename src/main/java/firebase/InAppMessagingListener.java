package firebase;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
//
//import com.google.firebase.inappmessaging.FirebaseInAppMessagingClickListener;
//import com.google.firebase.inappmessaging.FirebaseInAppMessagingDismissListener;
//import com.google.firebase.inappmessaging.model.Action;
//import com.google.firebase.inappmessaging.model.CampaignMetadata;
//import com.google.firebase.inappmessaging.model.InAppMessage;

import firebase.analytics.AppAnalytics;

public class InAppMessagingListener  {
    Context context;

    public InAppMessagingListener(Context context) {
        this.context = context;
    }

//    @Override
//    public void messageClicked(InAppMessage inAppMessage, Action action) {
//        // Determine which URL the user clicked
//        String url = action.getActionUrl();
//
//        String InAppMessagingType = inAppMessage.getData().get("SessionExpireExperiment");
//        if(InAppMessagingType!=null && InAppMessagingType.equalsIgnoreCase("true")){
//            String Grade = (inAppMessage.getData().containsKey("Grade"))? inAppMessage.getData().get("Grade"):"Default";
//            new AppAnalytics(context).increasing_video_experiment_clicked(Grade);
//        }
//        //String Testing = (inAppMessage.getData().containsKey("Testing"))? inAppMessage.getData().get("Testing"):"False";
//        Log.d("checkDynamicLink","Message clicked: ");
//        // Get general information about the campaign
//        CampaignMetadata metadata = inAppMessage.getCampaignMetadata();
//
//        // ...
//    }
//
//    @Override
//    public void messageDismissed(@NonNull InAppMessage inAppMessage) {
//        String InAppMessagingType = inAppMessage.getData().get("SessionExpireExperiment");
//        if(InAppMessagingType!=null && InAppMessagingType.equalsIgnoreCase("true")){
//            String Grade = (inAppMessage.getData().containsKey("Grade"))? inAppMessage.getData().get("Grade"):"Default";
//            new AppAnalytics(context).increasing_video_experiment_dismissed(Grade);
//        }
//    }
}