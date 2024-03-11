package com.orenda.taimo.grade3tograde5;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchaseHistoryRecord;
import com.android.billingclient.api.PurchaseHistoryResponseListener;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.functions.FirebaseFunctions;
//import com.google.firebase.functions.HttpsCallableResult;
import com.unity3d.player.UnityPlayer;

import java.util.ArrayList;
import java.util.List;

import firebase.analytics.AppAnalytics;

public class GooglePurchase implements PurchasesUpdatedListener {
    private BillingClient billingClient;
    Activity activity;
    Context context;
    String bundle;
    public GooglePurchase(Activity activity, Context context, String bundle) {
        this.activity = activity;
        this.context = context;
        this.bundle = bundle;
    }

    public void EstablishConnection(String SubscriptionPackage){

        billingClient = BillingClient.newBuilder(activity).setListener(this).enablePendingPurchases().build();

        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(BillingResult billingResult) {
                if (billingResult.getResponseCode() ==  BillingClient.BillingResponseCode.OK) {
                    // The BillingClient is ready. You can query purchases here.
                    Log.d("BillingConnection","Connection is ready");
                    QueryInAppProducts(SubscriptionPackage);
                }
            }
            @Override
            public void onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
                Log.d("BillingConnection","Disconnect");
            }
        });
    }

    void QueryInAppProducts(String SubscriptionPackage){
        List<String> skuList = new ArrayList<>();
        //skuList.add("android.test.purchased");
        skuList.add(SubscriptionPackage);
        SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
        params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP);
        billingClient.querySkuDetailsAsync(params.build(),
                new SkuDetailsResponseListener() {
                    @Override
                    public void onSkuDetailsResponse(BillingResult billingResult,
                                                     List<SkuDetails> skuDetailsList) {
                        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && skuDetailsList != null) {

                            for (SkuDetails skuDetails : skuDetailsList) {
                                String sku = skuDetails.getSku();
                                String price = skuDetails.getPrice();
                            }
                            LaunchBillingFlow(skuDetailsList.get(0));

                        }else{
                            Log.d("BillingConnection","Error-- code: "+billingResult.getResponseCode()+" message: "+billingResult.getDebugMessage());
                            UnityPlayer.UnitySendMessage("ScriptHandler", "SubscriptionGoogleReturn","false,null,null,null");
                        }
                    }
                });
    }

    void LaunchBillingFlow(SkuDetails skuDetails){

        BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                .setSkuDetails(skuDetails)
                .build();
        int responseCode = billingClient.launchBillingFlow(activity,flowParams).getResponseCode();
    }

    @Override
    public void onPurchasesUpdated(BillingResult billingResult, @Nullable List<Purchase> list) {
        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK
                && list != null) {
            handlePurchase(list.get(0));
        } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED) {
            // Handle an error caused by a user cancelling the purchase flow.
            new AppAnalytics(context).GooglePaymentUserCancelled(bundle);
            UnityPlayer.UnitySendMessage("ScriptHandler", "SubscriptionGoogleReturn","false,null,null,null");
        } else if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED){
            // Handle any other error codes.
        }
        else if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.ERROR){
            new AppAnalytics(context).GooglePaymentError(bundle);
        }
    }
    void handlePurchase(Purchase purchase) {
        if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED) {
            // Grant entitlement to the user.
            Long validityTime = 31104000L;
            switch(bundle){
                case "kto51months":
                    validityTime = 2629800L;
                    break;
                case "kto53months":
                case "3to53months":
                case "kto23months":
                    validityTime = 7776000L;
                    break;
                case "kto56months":
                case "3to56months":
                case "kto26months":
                    validityTime = 15552000L;
                    break;
                case "kto512months":
                case "3to512months":
                case "kto212months":
                    validityTime = 31104000L;
                    break;
            }
            String appBundle="kto5"; // convert kto53(6,12)months to kto5 for app to handle
            switch(bundle){
                case "kto51months":
                case "kto53months":
                case "kto56months":
                case "kto512months":
                    appBundle = "kto5";
                    break;
                case "3to53months":
                case "3to56months":
                case "3to512months":
                    appBundle = "3to5";
                    break;
                case "kto23months":
                case "kto26months":
                case "kto212months":
                    appBundle = "kto2";
                    break;
            }
            Long activationDate = System.currentTimeMillis()/ 1000;
            Long expiryDate = activationDate+validityTime;
            setPurchaseData("true","GooglePlayPurchase","null",appBundle, activationDate.toString(), expiryDate.toString());
            new AppAnalytics(context).setSaleSuccessfulAnalytics("GooglePayment",activationDate.toString(),appBundle,"");
            UnityPlayer.UnitySendMessage("ScriptHandler", "SubscriptionGoogleReturn","true,"+appBundle+","+activationDate+","+expiryDate);
            // Acknowledge the purchase if it hasn't already been acknowledged.
            if (!purchase.isAcknowledged()) {
                ConsumeParams acknowledgePurchaseParams =
                        ConsumeParams.newBuilder()
                                .setPurchaseToken(purchase.getPurchaseToken())
                                .build();
                billingClient.consumeAsync(acknowledgePurchaseParams, new ConsumeResponseListener() {
                    @Override
                    public void onConsumeResponse(BillingResult billingResult, String s) {

                    }
                });
            }
        }
    }
    public void setPurchaseData(String status, String mode, String coupon, String bundle ,String activationDate, String expiryDate){
       // purchase purchaseobj = new purchase(status, mode, coupon, bundle, activationDate, expiryDate);
        //purchaseobj.setPurchaseData(purchaseobj);

//        FirebaseFunctions mFunctions;
//        mFunctions = FirebaseFunctions.getInstance();
//        purchaseSetter(mFunctions, activationDate, expiryDate, bundle).addOnCompleteListener(new OnCompleteListener<String>() {
//            @Override
//            public void onComplete(@NonNull Task<String> task) {
//
//                if (!task.isSuccessful()) {
//                    Log.d("googlePurchase","UNSuccessful:"+ task.getException());
//                }else{
//                    Log.d("googlePurchase","Successful");
//                }
//            }
//        });
    }
//    private Task<String> purchaseSetter(FirebaseFunctions mFunctions, String activationDate, String expiryDate, String bundle) {
//        // Create the arguments to the callable function.
//        FirebaseAuth mAuth = FirebaseAuth.getInstance();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        Map<String, Object> data = new HashMap<>();
//        data.put("uid", currentUser.getUid());
//        data.put("status", "true");
//        data.put("activationDate", activationDate);
//        data.put("expiryDate", expiryDate);
//        data.put("mode", "GooglePayment");
//        data.put("bundle", bundle);
//        return mFunctions
//                .getHttpsCallable("purchaseSetterApp")
//                .call(data)
//                .continueWith(new Continuation<HttpsCallableResult, String>() {
//                    @Override
//                    public String then(@NonNull Task<HttpsCallableResult> task) throws Exception {
//                        String result = (String) task.getResult().getData();
//                        return result;
//                    }
//                });
//    }


    void querypurchase(){
        Log.d("BillingConnection"," in History ");
        billingClient.queryPurchaseHistoryAsync(BillingClient.SkuType.INAPP, new PurchaseHistoryResponseListener() {
                    @Override
                    public void onPurchaseHistoryResponse(BillingResult billingResult, List<PurchaseHistoryRecord> list) {
                        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK
                                && list != null) {
                            Log.d("BillingConnection"," in History if");
                            for (PurchaseHistoryRecord purchase : list) {
                                // Process the result.
                                Log.d("BillingConnection","History ");
                            }
                        }else{
                            Log.d("BillingConnection"," in History else");
                        }
                    }
                }
        );

    }
}
