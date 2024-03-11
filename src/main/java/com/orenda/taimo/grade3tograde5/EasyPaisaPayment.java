package com.orenda.taimo.grade3tograde5;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.functions.FirebaseFunctions;
//import com.google.firebase.functions.HttpsCallableResult;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import com.unity3d.player.UnityPlayer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import firebase.analytics.AppAnalytics;

public class EasyPaisaPayment {

    Context ctx;
    String bundle;
    ProgressDialog pd;
    Activity activity;

    public EasyPaisaPayment(Context context, String bundle, Activity activity)
    {
        ctx = context;
        this.bundle =bundle;
        this.activity = activity;

    }

     void initiateEasyPaisaMAPayment (double amount, String phoneNumber, String email) {
        EasyPaisaPaymentTask easyPaisaPaymentTask = new EasyPaisaPaymentTask();
        easyPaisaPaymentTask.execute(String.valueOf(amount), phoneNumber, email);
         pd = new ProgressDialog(activity);
         pd.setMessage("Please wait!");
         pd.setCancelable(false);
         pd.show();
    }
    private class EasyPaisaPaymentTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute () {
            super.onPreExecute();
            //TODO:: Start progress bar
        }
        @Override
        protected String doInBackground (String... strings) {
            OkHttpClient client = new OkHttpClient();
//            client.setConnectTimeout(100, TimeUnit.SECONDS); // connect timeout
//            client.setReadTimeout(100, TimeUnit.SECONDS);    // socket timeout
            String jsonBody = "{\n" +
                    "    \"orderId\":\""+(int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE)+"\",\n" +
                    "    \"storeId\":64091,\n" +
                    "    \"transactionAmount\":" + strings[0] + ",\n" +
                    "    \"transactionType\":\"MA\",\n" +
                    "    \"mobileAccountNo\": \""+ strings[1] +"\",\n" +
                    "    \"emailAddress\": \"" + strings[2] + "\"\n" +
                    "}";
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, jsonBody);
            Request request = new Request.Builder()
                    .header("Content-Type", "application/json")
                    .header("credentials", "VGFsZWVtYWJhZExlYXJuaW5nQXBwOjUwMDc1YjcwNDAzNzI2NjM3MjQxYjkxNTUxNWIxYWRm")
                    .url("https://easypay.easypaisa.com.pk/easypay-service/rest/v4/initiate-ma-transaction")
                    .post(body)
                    .build();
            Call call = client.newCall(request);
            try {
                Response response = call.execute();
                String jsonResponse = response.body().string();
                JSONObject jsonObject = new JSONObject(jsonResponse);
                if (jsonObject.getString("responseDesc").equals("SUCCESS")) {

                    return "SUCCESS";

                }
                else {
                    if(pd!=null)pd.cancel();
                    return jsonObject.getString("responseDesc");
                }
            }
            catch (IOException | JSONException e) {
                e.printStackTrace();
                ShowDialogBox("Error","Transaction Failed");
                Log.wtf("EasyPaisaPayment", e.getMessage());
                return "FAILURE";
            }
        }
        @Override
        protected void onPostExecute (String s) {
            super.onPostExecute(s);
            //TODO:: Cancel progress bar
            if(pd!=null)pd.cancel();
            if (s.equals("SUCCESS")) {

                Long validityTime = 31104000L;
                switch(bundle){
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
                setPurchaseData("true","EasyPaisaPurchase","null",bundle, activationDate.toString(), expiryDate.toString());
                new AppAnalytics(ctx).setSaleSuccessfulAnalytics("EasyPaisa",activationDate.toString(),bundle,"");
                UnityPlayer.UnitySendMessage("ScriptHandler", "SubscriptionGoogleReturn","true,"+appBundle+","+activationDate+","+expiryDate);
                //Toast.makeText(ctx, "Payment Successful.", Toast.LENGTH_SHORT).show();
            }
            else {
                if(pd!=null)pd.cancel();
                new AppAnalytics(ctx).EasypaisaPaymentDeclined();
                ShowDialogBox("Error","Transaction Failed");
                //Toast.makeText(ctx, "Payment Unsuccessful.\n"+s, Toast.LENGTH_SHORT).show();
            }
        }
    }

     void initiateEasyPaisaOTC (double amount, String phoneNumber, String email) {
        EasyPaisaOTCTask easyPaisaOTCTask = new EasyPaisaOTCTask();
        easyPaisaOTCTask.execute(String.valueOf(amount), phoneNumber, email);
         pd = new ProgressDialog(activity);
         pd.setMessage("Please wait!");
         pd.setCancelable(false);
         pd.show();
    }
    public static String getCalculatedDate(String dateFormat, int days) {
        Calendar cal = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat s = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
        cal.add(Calendar.DAY_OF_YEAR, days);
        return s.format(new Date(cal.getTimeInMillis()));
    }
    private class EasyPaisaOTCTask extends AsyncTask<String, Void, String[]> {
        private AlertDialog dialog;
        @Override
        protected void onPreExecute () {
            super.onPreExecute();
            //TODO:: Start progress bar
        }
        @Override
        protected String[] doInBackground (String... strings) {
            OkHttpClient client = new OkHttpClient();
//            client.setConnectTimeout(100, TimeUnit.SECONDS); // connect timeout
//            client.setReadTimeout(100, TimeUnit.SECONDS);    // socket timeout
            String jsonBody = "{\n" +
                    "    \"orderId\":\"OTC"+(int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE)+"\",\n" +
                    "    \"storeId\":64091,\n" +
                    "    \"transactionAmount\":" + strings[0] + ",\n" +
                    "    \"transactionType\":\"OTC\",\n" +
                    "    \"msisdn\": \""+ strings[1] +"\",\n" +
                    "    \"emailAddress\": \"" + strings[2] + "\",\n" +
                    "    \"tokenExpiry\": \"" + getCalculatedDate("yyyyMMdd HHmmss", 2) + "\"\n" +
                    "}";
            Log.wtf("EasyPaisaPayment", jsonBody);
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, jsonBody);
            Request request = new Request.Builder()
                    .header("Content-Type", "application/json")
                    .header("credentials", "VGFsZWVtYWJhZExlYXJuaW5nQXBwOjUwMDc1YjcwNDAzNzI2NjM3MjQxYjkxNTUxNWIxYWRm")
                    .url("https://easypay.easypaisa.com.pk/easypay-service/rest/v4/initiate-otc-transaction")
                    .post(body)
                    .build();
            Call call = (Call) client.newCall(request);
            try {
                Response response = call.execute();
                String jsonResponse = response.body().string();
                JSONObject jsonObject = new JSONObject(jsonResponse);
                if (jsonObject.getString("responseDesc").equals("SUCCESS")) {
                    return new String[]{"SUCCESS", jsonObject.getString("paymentToken"),
                            jsonObject.getString("paymentTokenExpiryDateTime")};
                }
                else {
                    return new String[]{jsonObject.getString("responseDesc")};
                }
            }
            catch (IOException | JSONException e) {
                e.printStackTrace();
                Log.wtf("EasyPaisaPayment", e.getMessage());
                if(pd!=null)pd.cancel();
                return new String[]{"FAILURE"};
            }
        }
        @Override
        protected void onPostExecute (String[] s) {
            super.onPostExecute(s);
            //TODO:: Cancel progress bar
            if (s[0].equals("SUCCESS")) {
                if(pd!=null)pd.cancel();
                //Toast.makeText(getApplicationContext(), "OTC Generated Successful.", Toast.LENGTH_SHORT).show();
                String paymentToken = s[1];
                String paymentTokenExpiry = s[2];
                new AppAnalytics(ctx).EasypaisaOTCSuccessful();
                ShowDialogBox("Success","Pay the required amount to your nearest Easypaisa shop");
                //TODO:: Show user the payment token
            }
            else {
                if(pd!=null)pd.cancel();
                new AppAnalytics(ctx).EasypaisaPaymentDeclined();
                ShowDialogBox("Error","Transaction Failed");
                Log.wtf("EasyPaisaPayment", s[0]);
                //Toast.makeText(getApplicationContext(), "OTC Generation Unsuccessful.\n"+s[0], Toast.LENGTH_SHORT).show();
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
    void ShowDialogBox(String title, String Message){
        Log.d("testtest","show dialog box");
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
