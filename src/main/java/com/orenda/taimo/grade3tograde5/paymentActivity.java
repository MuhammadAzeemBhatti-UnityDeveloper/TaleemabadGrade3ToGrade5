/*package com.orenda.taimo.myapplication;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.unity3d.player.*;

import java.util.ArrayList;
import java.util.List;

import billing.BillingManager;
import firebase.classes.purchase;

public class paymentActivity extends AppCompatActivity {
    private BillingManager mBillingManager;
    int unityval=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        // Create and initialize BillingManager which talks to BillingLibrary


        mBillingManager = new BillingManager(this, new BillingManager.BillingUpdatesListener() {
            @Override
            public void onBillingClientSetupFinished() {
                Log.d("paymentactivityoncreate","clien set up finished");
                Log.d("billingClient","Client is ready");
                List<String> skuList = new ArrayList<>();
                skuList.add("fullapp_purchase");
                SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
                params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP);
                Log.d("skuList",""+mBillingManager);

                mBillingManager.querySkuDetailsAsync(BillingClient.SkuType.INAPP, skuList, new SkuDetailsResponseListener() {

                    @Override
                    public void onSkuDetailsResponse(int responseCode, List<SkuDetails> skuDetailsList) {
                        String sku=null;
                        Log.d("skuListRes",""+responseCode+""+skuDetailsList);
                        if (responseCode == BillingClient.BillingResponse.OK
                                && skuDetailsList != null) {
                            for (SkuDetails skuDetails : skuDetailsList) {
                                sku = skuDetails.getSku();
                                String price = skuDetails.getPrice();
                                Log.d("skuItem","sku: "+sku+"price: "+price);
                            }
                            BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                                    .setSku(sku)
                                    .setType(BillingClient.SkuType.INAPP)
                                    .build();
                            mBillingManager.initiatePurchaseFlow(sku,BillingClient.SkuType.INAPP);
                        }
                    }
                });

            }

            @Override
            public void onConsumeFinished(String token, @BillingClient.BillingResponse int result) {
                Log.d("billingClientConsume","Client is ready"+result);
            }

            @Override
            public void onPurchasesUpdated(List<Purchase> purchases) {
                Log.d("billingClientPurchaseaa", "purchase updated" + purchases.size()+purchases);
                if(purchases.size()>0){
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    FirebaseUser currentUser = mAuth.getCurrentUser();
                    final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users/" + currentUser.getUid());
                    purchase purchaseObj = new purchase("true", "google", "null");
                    mDatabase.child("purchase").setValue(purchaseObj);
                    unityval=1;
                    Intent intent=new Intent(paymentActivity.this, UnityPlayerActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Intent intent=new Intent(paymentActivity.this, UnityPlayerActivity.class);
                    finish();
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        if(unityval==1){
            UnityPlayer.UnitySendMessage("ScriptHandler", "UnlockAllGames","aa");
        }
    }
}
*/