package com.orenda.taimo.grade3tograde5;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
//import com.facebook.CurrentAccessTokenExpirationBroadcastReceiver;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
import com.unity3d.player.UnityPlayer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import firebase.analytics.AppAnalytics;

public class SimPaisa {
    String PhoneNumber;
    String SimNetwork;
    int OperatorId;
    Context Context;
    RequestQueue queue;
    Activity ActivityContext;
    ProgressDialog pd;
    String Code;
    String UserUid;
    String HostUrl = "http://api.simpaisa.com:9991";
    private String WEBKey = "MRJtbqjQABJJWrtKwewRNJNq";
    private String ProductId = "1257";
    private String MerchantId = "1000167";

    SimPaisa(String phoneNumber, String simNetwork, Context context, Activity activityContext, String useruid) {
        PhoneNumber = phoneNumber;
        SimNetwork = simNetwork;
        Context = context;
        ActivityContext = activityContext;
        UserUid = useruid;
    }

    SimPaisa(String phoneNumber, String simNetwork,Context context, Activity activityContext, String code, String useruid) {
        PhoneNumber = phoneNumber;
        SimNetwork = simNetwork;
        Context = context;
        ActivityContext = activityContext;
        Code = code;
        UserUid = useruid;
    }

    SimPaisa(String useruid,Context context, Activity activityContext){
        UserUid = useruid;
        Context = context;
        ActivityContext = activityContext;
    }

    SimPaisa(){

    }

    void GetOperatorId(){
        switch (SimNetwork){
            case "Mobilink":
                OperatorId = 100001;
                break;
            case "Telenor":
                OperatorId = 100002;
                break;
            case "Zong":
                OperatorId = 100003;
                break;
        }
    }

    void ShowProgressDialog(){
        pd = new ProgressDialog(ActivityContext);
        pd.setMessage("Please wait!");
        pd.setCancelable(false);
        pd.show();
    }

//    void CheckActiveSubscription(){
//        ShowProgressDialog();
//        final DatabaseReference phoneNumberDatabase = FirebaseDatabase.getInstance().getReference("SubscribersPhoneNumber/"+PhoneNumber);
//        ValueEventListener valueEventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    final String SubscriptionUID = dataSnapshot.child("UID").getValue().toString();
//                    final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users/" + SubscriptionUID + "/Subscription");
//                    ValueEventListener valueEventListener = new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            if (dataSnapshot.exists()) {
//                                if (dataSnapshot.child("Status").getValue().equals("Active")) {
//                                    String ActiveSubscriptionId = dataSnapshot.child("ActiveSubscriptionId").getValue().toString();
//                                    Long ActivationDate = Long.parseLong(dataSnapshot.child("SubscriptionList").child(ActiveSubscriptionId).child("ActivationDate").getValue().toString());
//                                    Long ExpiryDate = Long.parseLong(dataSnapshot.child("SubscriptionList").child(ActiveSubscriptionId).child("ExpiryDate").getValue().toString());
//                                    Long CurrentDate = System.currentTimeMillis()/1000;
//                                    if(CurrentDate<ExpiryDate){
//                                        UnityPlayer.UnitySendMessage("ScriptHandler", "UpdateSubscriptionDates", ActivationDate+","+ExpiryDate+","+SubscriptionUID);
//                                        pd.cancel();
//                                    }else{
//                                        mDatabase.child("Status").setValue("InActive");
//                                        //call sim paisa to subscription
//                                        pd.cancel();
//                                        InitiateTransaction();
//                                    }
//                                } else {
//                                    //call sim paisa to subscription
//                                    pd.cancel();
//                                    InitiateTransaction();
//                                }
//                            } else {
//                                //call sim paisa to subscription
//                                pd.cancel();
//                                InitiateTransaction();
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//                    };
//                    mDatabase.addListenerForSingleValueEvent(valueEventListener);
//                } else {
//                    pd.cancel();
//                    InitiateTransaction();
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        };
//        phoneNumberDatabase.addListenerForSingleValueEvent(valueEventListener);
//    }
    //Make transaction request
    void InitiateTransaction() {
        ShowProgressDialog();
        GetOperatorId();
        queue = Volley.newRequestQueue(Context);
        HostUrl = HostUrl + "/dcb-integration/transaction/" + WEBKey + "/WEB/make-transaction";
        Map<String, String> params = new HashMap<>();
        params.put("productID", ProductId);
        params.put("mobileNo", PhoneNumber);
        params.put("operatorID", String.valueOf(OperatorId));
        params.put("userKey", UserUid);
        Log.d("Response", "Initiate Params are: " + params);
        Log.d("Response", "Initiate: " + ProductId + " " + PhoneNumber + " " + OperatorId + " " + UserUid);
            /*for (Map.Entry<String, String> e : params.entrySet()) {
                Log.d("ResponseMap","key: "+e.getKey()+" -- "+"value: "+e.getValue());
            }*/
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, HostUrl, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response", "" + response);
                String status = null;
                try {
                    status = response.getString("status");
                    if (status.equals("1")) {
                        pd.cancel();
                        UnityPlayer.UnitySendMessage("ScriptHandler", "SimPaisaMakeTransactionResponse", "1");
                    } else {
                        pd.cancel();
                        TransactionFailed();
                        UnityPlayer.UnitySendMessage("ScriptHandler", "SimPaisaMakeTransactionResponse", "0");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("status:", "status is :" + status);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Response", "Error" + error);
                pd.cancel();
                TransactionFailed();
                UnityPlayer.UnitySendMessage("ScriptHandler", "SimPaisaMakeTransactionResponse", "0");
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonObjectRequest);
    }

    public void VerifyCodePayment(){
        GetOperatorId();
        ShowProgressDialog();
        queue = Volley.newRequestQueue(Context);
        HostUrl = HostUrl+"/dcb-integration/transaction/"+WEBKey+"/WEB/verify-payment";
        Map<String, String> params = new HashMap<>();
        params.put("productID",ProductId);
        params.put("mobileNo", PhoneNumber);
        params.put("operatorID", String.valueOf(OperatorId));
        params.put("userKey",UserUid);
        params.put("codeOTP",Code);
        Log.d("Response","Verify Params are: "+params);
        Log.d("Response","Verify: "+ProductId+" "+PhoneNumber+" "+OperatorId+" "+UserUid+" "+Code);
        /*for (Map.Entry<String, String> e : params.entrySet()) {
            Log.d("ResponseMap","key: "+e.getKey()+" -- "+"value: "+e.getValue());
        }*/
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, HostUrl, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response","response is : "+response);
                String status = null;
                try {
                    status = response.getString("status");
                    if(status.equals("1")){
                        pd.cancel();
                        SetSubscriptionAnalytics(true);
                        UnityPlayer.UnitySendMessage("ScriptHandler","SimPaisaVerifyCodeResponse","1");
                    }else{
                        pd.cancel();
                        TransactionFailed();
                        SetSubscriptionAnalytics(false);
                        UnityPlayer.UnitySendMessage("ScriptHandler","SimPaisaVerifyCodeResponse","0");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("status:","status is :"+status);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.cancel();
                TransactionFailed();
                UnityPlayer.UnitySendMessage("ScriptHandler","SimPaisaVerifyCodeResponse","0");
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonObjectRequest);
    }

    void FetchMSISDN(){
        ShowProgressDialog();
        queue = Volley.newRequestQueue(Context);
        Log.d("Response","arrived here Header enrichement");
        HostUrl = HostUrl + "/dcb-integration/transaction/"+WEBKey+"/WEB/fetch-msisdn?userKey="+UserUid+"&productID="+ProductId;
        Log.d("Response","host"+HostUrl);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HostUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Response"," Fetch- Response is: "+response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Response","Error"+error);
                        pd.cancel();
                        TransactionFailed();
                    }
                }){
            @Override
            public String getBodyContentType(){
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                3,
                2));
        queue.add(stringRequest);
    }

    void UnSubscribe(){
        Log.d("Response"," in unsubscribe ");
        ShowProgressDialog();
        queue = Volley.newRequestQueue(Context);
        HostUrl = HostUrl + "/dcb-integration/recursion/"+WEBKey+"/WEB/unsubscribe";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HostUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Response"," in unsubscribe- Response is: "+response);
                String trimResponse = response.trim();
                String status = null;
                try {
                    JSONObject jsonObject = new JSONObject(trimResponse);
                    status = jsonObject.getString("responseCode");
                    Log.d("Response"," in unsubscribe- status: "+status);
                    if(status.equals("0000")){
                        pd.cancel();
                        UnsubscribedDialog();
                        SetUnsubscrbeAnalytics(true);
                        UnityPlayer.UnitySendMessage("ScriptHandler","UnsubscribeSimPaisaResponse","0000");
                    }
                    else{
                        pd.cancel();
                        SetUnsubscrbeAnalytics(false);
                        UnityPlayer.UnitySendMessage("ScriptHandler","UnsubscribeSimPaisaResponse","0");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.cancel();
                        TransactionFailed();
                    }
                }
        ){
            @Override
            public String getBodyContentType(){
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("UserID",UserUid);
                params.put("ProductID",ProductId);
                params.put("MerchantID",MerchantId);
                Log.d("Response"," "+UserUid+" "+ProductId+" "+MerchantId);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(stringRequest);

    }

    void TransactionFailed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityContext);
        builder.setTitle("Error!");
        builder.setMessage("Please try again");

        builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    void UnsubscribedDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityContext);
        builder.setTitle("unsubscribed!");
        builder.setMessage("You have successfully unsubscribed from Taleemabad");

        builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    void SetSubscriptionAnalytics(boolean status){
        AppAnalytics appAnalytics = new AppAnalytics(Context);
        appAnalytics.setSimPaisaSubscriptionAnalytics(status);
    }
    void SetUnsubscrbeAnalytics(boolean status){
        AppAnalytics appAnalytics = new AppAnalytics(Context);
        appAnalytics.setSimPaisaUnsubscribeAnalytics(status);
    }
}