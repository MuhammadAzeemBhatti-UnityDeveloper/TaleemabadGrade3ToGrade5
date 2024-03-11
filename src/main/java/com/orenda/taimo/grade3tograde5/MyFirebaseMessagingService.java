package com.orenda.taimo.grade3tograde5;

//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.messaging.FirebaseMessagingService;
//import com.google.firebase.messaging.RemoteMessage;


public class MyFirebaseMessagingService  {

//    private static final String TAG = "MyFirebaseMsgService";

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */

    // [START receive_message]
//    @SuppressLint("WrongThread")
//    @Override
//    public void onMessageReceived(RemoteMessage remoteMessage) {
//        // [START_EXCLUDE]
//        // There are two types of messages data messages and notification messages. Data messages are handled
//        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
//        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
//        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
//        // When the user taps on the notification they are returned to the app. Messages containing both notification
//        // and data payloads are treated as notification messages. The Firebase console always sends notification
//        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
//        // [END_EXCLUDE]
//
//        // TODO(developer): Handle FCM messages here.
//        // Not getting messages here? See why this may be: https://goo.gl/39bRNJF
//        Log.d(TAG, "From: " + remoteMessage.getFrom());
//
//        // Check if message contains a notification payload.
//        if (remoteMessage.getNotification() != null) {
//            Log.d(TAG,"key data:"+remoteMessage.getData().get("type"));
//            String bundle =  (remoteMessage.getData().containsKey("bundle"))? remoteMessage.getData().get("bundle"):null;
//
//            if (remoteMessage.getData() != null) {
//                String firebaseNotification = (remoteMessage.getData().containsKey("firebaseNotification"))? remoteMessage.getData().get("firebaseNotification"):"false";
//                remoteMessage.getData().remove("firebaseNotification");
//                if(firebaseNotification.equalsIgnoreCase("true")){
//                    String notificationtype = (remoteMessage.getData().containsKey("type"))? remoteMessage.getData().get("type"):null;
//                    String notificationvideoname = (remoteMessage.getData().containsKey("videoname"))? remoteMessage.getData().get("videoname"):null;
//                    String notificationtestname = (remoteMessage.getData().containsKey("testname"))? remoteMessage.getData().get("testname"):null;
//                    String notificationgrade = (remoteMessage.getData().containsKey("grade"))? remoteMessage.getData().get("grade"):null;
//                    String notificationsubject = (remoteMessage.getData().containsKey("subject"))? remoteMessage.getData().get("subject"):null;
//                    String notificationlink = (remoteMessage.getData().containsKey("link"))? remoteMessage.getData().get("link"):null;
//                    String notificationnurserygametype = (remoteMessage.getData().containsKey("nurserygametype"))? remoteMessage.getData().get("nurserygametype"):null;
//                    Log.d("rtest",": "+notificationsubject);
//                    if(remoteMessage.getNotification().getImageUrl()!=null){
//                        new HandlePictureStyleFirebaseCustomNotification(this, remoteMessage.getNotification().getImageUrl().toString(), remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), notificationtype, notificationlink, notificationgrade, notificationsubject, notificationvideoname, notificationtestname, notificationnurserygametype).execute();
//                        Log.d("showImage","show image"+remoteMessage.getNotification().getImageUrl());
//                    }else {
//                        Log.d("showImage","don't show image");
//                        HandleFirebaseCustomNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), notificationtype, notificationlink, notificationgrade, notificationsubject, notificationvideoname, notificationtestname, notificationnurserygametype);
//                    }
//                }
//            }else {
//                createFirebaseNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), "");
//                //Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getTitle() + remoteMessage.getNotification().getBody());
//            }
//        }
//        else if(remoteMessage.getData().size()>0 && remoteMessage.getData().containsKey("type")){
//            String notificationid = (remoteMessage.getData().containsKey("notificationid"))? remoteMessage.getData().get("notificationid"):null;
//            String notificationtimestamp = (remoteMessage.getData().containsKey("timestamp"))? remoteMessage.getData().get("timestamp"):null;
//            String notificationtype = (remoteMessage.getData().containsKey("type"))? remoteMessage.getData().get("type"):null;
//            String notificationimage = (remoteMessage.getData().containsKey("imageLink"))? remoteMessage.getData().get("imageLink"):null;
//            String notificationtitle = (remoteMessage.getData().containsKey("title"))? remoteMessage.getData().get("title"):null;
//            String notificationbody = (remoteMessage.getData().containsKey("body"))? remoteMessage.getData().get("body"):null;
//            String notificationbutton1 = (remoteMessage.getData().containsKey("button1"))? remoteMessage.getData().get("button1"):null;
//            String notificationbutton2 = (remoteMessage.getData().containsKey("button2"))? remoteMessage.getData().get("button2"):null;
//            String notificationvideoname = (remoteMessage.getData().containsKey("videoname"))? remoteMessage.getData().get("videoname"):null;
//            String notificationtestname = (remoteMessage.getData().containsKey("testname"))? remoteMessage.getData().get("testname"):null;
//            String notificationgrade = (remoteMessage.getData().containsKey("grade"))? remoteMessage.getData().get("grade"):null;
//            String notificationsubject = (remoteMessage.getData().containsKey("subject"))? remoteMessage.getData().get("subject"):null;
//            String notificationlink = (remoteMessage.getData().containsKey("link"))? remoteMessage.getData().get("link"):null;
//            String notificationnurserygametype = (remoteMessage.getData().containsKey("nurserygametype"))? remoteMessage.getData().get("nurserygametype"):null;
//
//            new AppAnalytics(this).CustomNotificationRecieved(notificationid,notificationtimestamp,notificationtype);
//            //check if image has to be shown in notification
//            if(notificationimage!=null){
//                new generatePictureStyleNotification(this, notificationid,notificationtimestamp,notificationtype,notificationtitle,notificationbody,notificationimage,
//                        notificationbutton1,notificationbutton2,notificationvideoname, notificationtestname,notificationgrade,notificationsubject,
//                        notificationlink, notificationnurserygametype).execute();
//            }
//            else{
//                NotificationBuilder(notificationid, notificationtimestamp,notificationtype, notificationtitle, notificationbody,
//                        notificationbutton1,notificationbutton2, notificationvideoname, notificationtestname, notificationgrade,notificationsubject
//                        ,notificationlink,notificationnurserygametype);
//            }
////            if(remoteMessage.getData().get("type").equals("image")){
////                new generatePictureStyleNotification(this,remoteMessage.getData().get("title"),remoteMessage.getData().get("body"),remoteMessage.getData().get("imageLink")).execute();
////            }else if(remoteMessage.getData().get("type").equals("text")){
////                createNotification2(remoteMessage.getData().get("body"),remoteMessage.getData().get("title"),remoteMessage.getData().get("click_action"));
////            }
//        }
//
//        // Also if you intend on generating your own notifications as a result of a received FCM
//        // message, here is where that should be initiated. See sendNotification method below.
//        //Log.d(TAG, "From: " + remoteMessage.getFrom());
//     //   Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
//        //create notification
//       // Toast.makeText(getApplicationContext(),""+remoteMessage.getData().get("title"),Toast.LENGTH_SHORT).show();
//    }
//
//
//    // [END receive_message]
//    /**
//     * Called if InstanceID token is updated. This may occur if the security of
//     * the previous token had been compromised. Note that this is called when the InstanceID token
//     * is initially generated so this is where you would retrieve the token.
//     */
//
//    @Override
//    public void onNewToken(String token) {
//        Log.d(TAG, "Refreshed token: " + token);
//        new AppAnalytics(getApplicationContext()).FCMToken(token);
//        // If you want to send messages to this application instance or
//        // manage this apps subscriptions on the server side, send the
//        // Instance ID token to your app server.
//        //sendRegistrationToServer(token);
//    }

//    private void NotificationBuilder(String notificationId, String notificationtimestamp, String type, String title, String body, String button1, String button2, String videoname, String testname, String grade, String subject, String link, String nurserygametype){
//
//        int m;
//        if(notificationId!=null){
//            m=Integer.parseInt(notificationId);
//        }else{
//            m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE); // to get unique id for notification
//        }
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        FirebaseAuth mAuth = FirebaseAuth.getInstance();
//        final FirebaseUser currentUser = mAuth.getCurrentUser();
//        Long AnonymousD0Signup = preferences.getLong("AnonymousD0Signup", 0);
//        Intent intent = null;
//        if(currentUser != null){
//            if(!currentUser.isAnonymous() || System.currentTimeMillis() < AnonymousD0Signup + 86400000){
//                intent = createIntentForNotification(type, link, grade, subject, videoname, testname, nurserygametype);
//            }else{
//                intent = new Intent(getApplicationContext(), SignupActivity.class);
//            }
//        }else{
//            intent = new Intent(getApplicationContext(), SignupActivity.class);
//        }
//
//        intent.putExtra("NotificationId",m);
//        intent.putExtra("NotificationTime",notificationtimestamp);
//        intent.putExtra("NotificationType",type);
//        PendingIntent resultIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent,
//                PendingIntent.FLAG_ONE_SHOT);
//
//        NotificationCompat.Builder mNotificationBuilder = new NotificationCompat.Builder( this,"GeneralNotifications")
//                .setSmallIcon(R.drawable.notificationicon)//app_icon
//                .setContentTitle(title)
//                .setContentText(body)
//                .setContentIntent(resultIntent)
//                .setDeleteIntent(createOnDismissedIntent(this, notificationId,notificationtimestamp,type)) // notification dismiss
//                .setAutoCancel(true);
//        if(button1!=null ){
//            mNotificationBuilder.addAction(0,button1,resultIntent);
//        }
//        if(button2!=null || type.equalsIgnoreCase("VideoTest")){
//            Intent button2intent =null;
//            if(currentUser != null){
//                if(!currentUser.isAnonymous() || System.currentTimeMillis() < AnonymousD0Signup + 86400000){
//                    button2intent = createIntentForNotificationButton2(type, link, grade, subject, videoname, testname, nurserygametype);
//                }else{
//                    button2intent = new Intent(getApplicationContext(), SignupActivity.class);
//                }
//            }else{
//                button2intent = new Intent(getApplicationContext(), SignupActivity.class);
//            }
//
//            button2intent.putExtra("NotificationId",m);
//            PendingIntent button2resultIntent = PendingIntent.getActivity(getApplicationContext(), 0, button2intent,
//                    PendingIntent.FLAG_ONE_SHOT);
//            mNotificationBuilder.addAction(0,button2,button2resultIntent);
//        }
//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        notificationManager.notify(m, mNotificationBuilder.build());
//
//    }
//
//    private Intent createIntentForNotification(String type, String link, String grade, String subject, String videoname, String testname, String nurserygametype){
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        Intent intent = null;
//        switch (type){
//            case "TestVideos":
//            case "Videos":
//                intent = new Intent(getApplicationContext(),VideoActivity.class);
//                intent.putExtra("Notification",true);
//                intent.putExtra("GradeSelected", grade);
//                intent.putExtra("SubjectSelected", subject);
//                intent.putExtra("Alpha", videoname);
//                intent.putExtra("ContentUnlocked",preferences.getInt("AllContentUnlocked", 0));
//                intent.putExtra("PurchaseStatus",preferences.getInt("app_purchase", 0));
//                break;
//
//            case "Test":
//                if(!grade.equalsIgnoreCase("Nursery")){
//                    intent = new Intent(getApplicationContext(), SimpleTestActivity.class);
//                    intent.putExtra("Notification", true);
//                    intent.putExtra("GradeSelected", grade);
//                    intent.putExtra("SubjectSelected", subject);
//                    intent.putExtra("Alpha", testname);
//                    intent.putExtra("source", "nonsocratic");
//
//                }else{
//                    intent = new Intent(getApplicationContext(), UnityPlayerActivity.class);
//                    intent.putExtra("Notification", true);
//                    intent.putExtra("GradeSelected", grade);
//                    intent.putExtra("SubjectSelected", subject);
//                    intent.putExtra("Alpha", testname);
//                    intent.putExtra("GameType", nurserygametype);
//
//                    //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                }
//                break;
//
//            case "Survey":
//                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
//                break;
//            case "ParentPortal":
//                    intent = new Intent(getApplicationContext(), UnityPlayerActivity.class);
//                    intent.putExtra("Notification", true);
//                    intent.putExtra("ParentPortal",true);
//                break;
//            case "Referral":
//                intent = new Intent(getApplicationContext(), UnityPlayerActivity.class);
//                intent.putExtra("Notification", true);
//                intent.putExtra("ReferralNotification",true);
//                break;
//            case "Home":
//                intent = new Intent(getApplicationContext(), UnityPlayerActivity.class);
//                break;
//            default:
//                    intent = new Intent(getApplicationContext(), UnityPlayerActivity.class);
//                break;
//        }
//        return intent;
//    }
//    private Intent createIntentForNotificationButton2(String type, String link, String grade, String subject, String videoname, String testname, String nurserygametype){
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        Intent intent = null;
//        switch (type){
//            case "TestVideos":
//                if(!grade.equalsIgnoreCase("Nursery")){
//                        intent = new Intent(getApplicationContext(), SimpleTestActivity.class);
//                        intent.putExtra("Notification", true);
//                        intent.putExtra("GradeSelected", grade);
//                        intent.putExtra("SubjectSelected", subject);
//                        intent.putExtra("Alpha", testname);
//                        intent.putExtra("source", "nonsocratic");
//
//                }else{
//                    intent = new Intent(getApplicationContext(), UnityPlayerActivity.class);
//                    intent.putExtra("Notification", true);
//                    intent.putExtra("GradeSelected", grade);
//                    intent.putExtra("SubjectSelected", subject);
//                    intent.putExtra("Alpha", testname);
//                    intent.putExtra("GameType", nurserygametype);
//                    //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                }
//                break;
//
//            default:
//                intent = new Intent(getApplicationContext(), UnityPlayerActivity.class);
//                break;
//        }
//        return intent;
//    }
//
//    private void HandleFirebaseCustomNotification(String title, String messageBody, String type, String link, String grade, String subject, String videoname, String testname, String nurserygametype)   {
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        FirebaseAuth mAuth = FirebaseAuth.getInstance();
//        final FirebaseUser currentUser = mAuth.getCurrentUser();
//        Long AnonymousD0Signup = preferences.getLong("AnonymousD0Signup", 0);
//        Intent intent = null;
//        if(currentUser != null){
//            if(!currentUser.isAnonymous() || System.currentTimeMillis() < AnonymousD0Signup + 86400000){
//                switch (type) {
//                    case "Videos":
//                        intent = new Intent(getApplicationContext(), VideoActivity.class);
//                        intent.putExtra("Notification", true);
//                        intent.putExtra("GradeSelected", grade);
//                        intent.putExtra("SubjectSelected", subject);
//                        intent.putExtra("Alpha", videoname);
//                        intent.putExtra("ContentUnlocked", preferences.getInt("AllContentUnlocked", 0));
//                        intent.putExtra("PurchaseStatus", preferences.getInt("app_purchase", 0));
//                        break;
//                    case "Test":
//                        if (!grade.equalsIgnoreCase("Nursery")) {
//                            intent = new Intent(getApplicationContext(), SimpleTestActivity.class);
//                            intent.putExtra("Notification", true);
//                            intent.putExtra("GradeSelected", grade);
//                            intent.putExtra("SubjectSelected", subject);
//                            intent.putExtra("Alpha", testname);
//                            intent.putExtra("source", "nonsocratic");
//
//                        } else {
//                            intent = new Intent(getApplicationContext(), UnityPlayerActivity.class);
//                            //intent.putExtra("Notification", true);
//                            //intent.putExtra("GradeSelected", grade);
//                            //intent.putExtra("SubjectSelected", subject);
//                            //intent.putExtra("Alpha", testname);
//                            //intent.putExtra("GameType", nurserygametype);
//                            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                        }
//                        break;
//                    case "Survey":
//                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
//                        break;
//                    case "ParentPortal":
//                        intent = new Intent(getApplicationContext(), UnityPlayerActivity.class);
//                        //intent.putExtra("Notification", true);
//                        //intent.putExtra("ParentPortal", true);
//                        break;
//                    case "Referral":
//                        intent = new Intent(getApplicationContext(), UnityPlayerActivity.class);
//                        //intent.putExtra("Notification", true);
//                        //intent.putExtra("ReferralNotification", true);
//                        break;
//                    case "Home":
//                        intent = new Intent(getApplicationContext(), UnityPlayerActivity.class);
//                        break;
//                    default:
//                        intent = new Intent(getApplicationContext(), UnityPlayerActivity.class);
//                        break;
//                }
//            }else{
//                intent = new Intent(getApplicationContext(), SignupActivity.class);
//            }
//        }else{
//            intent = new Intent(getApplicationContext(), SignupActivity.class);
//        }
//
//
//
//        PendingIntent resultIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent,
//                PendingIntent.FLAG_ONE_SHOT);
//
//        NotificationCompat.Builder mNotificationBuilder = new NotificationCompat.Builder( this,"GeneralNotifications")
//
//                .setSmallIcon(R.drawable.notificationicon)
//                .setContentTitle(title)
//                .setContentText(messageBody)
//                .setContentIntent(resultIntent)
//                .setAutoCancel(true);
//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(0, mNotificationBuilder.build());
//
//    }
//
//    private void createFirebaseNotification(String title, String messageBody, String activityToOpen) {
//        Intent intent = new Intent(getApplicationContext(),SignupActivity.class);
//        PendingIntent resultIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent,
//                PendingIntent.FLAG_ONE_SHOT);
//        Uri sound=Uri.parse("android.resource://com.orenda.taimo.myapplication/raw/notificationtone");
//        NotificationCompat.Builder mNotificationBuilder = new NotificationCompat.Builder( this,"GeneralNotifications")
//
//                // .setSmallIcon(R.drawable.notificationicon)//app_icon
//                .setSmallIcon(R.drawable.notificationicon)
//                .setContentTitle(title)
//                .setContentText(messageBody)
//                .setSound(sound)
//                .setContentIntent(resultIntent)
//                .setAutoCancel(true);
//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(0, mNotificationBuilder.build());
//
//    }
//
//
//    private PendingIntent createOnDismissedIntent(Context context, String notificationId, String notificationTimeStamp, String notificationType) {
//        int notificationIdConverted = Integer.parseInt(notificationId);
//        Intent notificationDismisIntent = new Intent(context, NotificationDismissedReceiver.class);
//        notificationDismisIntent.putExtra("NotificationId",notificationId);
//        notificationDismisIntent.putExtra("NotificationTime",notificationTimeStamp);
//        notificationDismisIntent.putExtra("NotificationType",notificationType);
//        PendingIntent pendingIntent1 =
//                PendingIntent.getBroadcast(context.getApplicationContext(),
//                        notificationIdConverted, notificationDismisIntent, 0);
//        return pendingIntent1;
//    }
//}
//    class generatePictureStyleNotification extends AsyncTask<String, Void, Bitmap>{
//
//        private Context mContext;
//        private String notificationId,notificationtimestamp,imageUrl, type, title, body, button1, button2, videoname, testname, grade, subject, link, nurserygametype;
//
//        public generatePictureStyleNotification(Context context,String notificationId, String notificationtimestamp, String type, String title, String body, String image ,String button1, String button2, String videoname, String testname, String grade, String subject, String link, String nurserygametype) {
//            super();
//            this.mContext = context;
//            this.notificationId = notificationId;
//            this.notificationtimestamp= notificationtimestamp;
//            this.type = type;
//            this.title = title;
//            this.body = body;
//            this.imageUrl = image;
//            this.button1 = button1;
//            this.button2 = button2;
//            this.videoname =videoname;
//            this.testname = testname;
//            this.grade = grade;
//            this.subject = subject;
//            this.link= link;
//            this.nurserygametype = nurserygametype;
//        }
//
//        private Intent createIntentForNotification(String type, String link, String grade, String subject, String videoname, String testname, String nurserygametype){
//            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext.getApplicationContext());
//            Intent intent = null;
//            switch (type){
//                case "TestVideos":
//                case "Videos":
//                    intent = new Intent(mContext.getApplicationContext(),VideoActivity.class);
//                    intent.putExtra("Notification",true);
//                    intent.putExtra("GradeSelected", grade);
//                    intent.putExtra("SubjectSelected", subject);
//                    intent.putExtra("Alpha", videoname);
//                    intent.putExtra("ContentUnlocked",preferences.getInt("AllContentUnlocked", 0));
//                    intent.putExtra("PurchaseStatus",preferences.getInt("app_purchase", 0));
//                    break;
//
//                case "Test":
//                    if(!grade.equalsIgnoreCase("Nursery")){
//                            intent = new Intent(mContext.getApplicationContext(), SimpleTestActivity.class);
//                            intent.putExtra("Notification", true);
//                            intent.putExtra("GradeSelected", grade);
//                            intent.putExtra("SubjectSelected", subject);
//                            intent.putExtra("Alpha", testname);
//                            intent.putExtra("source", "nonsocratic");
//
//                    }else{
//                        intent = new Intent(mContext.getApplicationContext(), UnityPlayerActivity.class);
//                        intent.putExtra("Notification", true);
//                        intent.putExtra("GradeSelected", grade);
//                        intent.putExtra("SubjectSelected", subject);
//                        intent.putExtra("Alpha", testname);
//                        intent.putExtra("GameType", nurserygametype);
//                        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    }
//                    break;
//
//                case "Survey":
//                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
//                    break;
//                case "ParentPortal":
//                    intent = new Intent(mContext.getApplicationContext(), UnityPlayerActivity.class);
//                    intent.putExtra("Notification", true);
//                    intent.putExtra("ParentPortal",true);
//                     break;
//                case "Referral":
//                    intent = new Intent(mContext.getApplicationContext(), UnityPlayerActivity.class);
//                    intent.putExtra("Notification", true);
//                    intent.putExtra("ReferralNotification",true);
//                    break;
//                case "Home":
//                    intent = new Intent(mContext.getApplicationContext(), UnityPlayerActivity.class);
//                    break;
//                default:
//                    intent = new Intent(mContext.getApplicationContext(), UnityPlayerActivity.class);
//                    break;
//            }
//            return intent;
//        }
//        private Intent createIntentForNotificationButton2(String type, String link, String grade, String subject, String videoname, String testname, String nurserygametype){
//            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext.getApplicationContext());
//            Intent intent = null;
//            switch (type){
//                case "TestVideos":
//                    if(!grade.equalsIgnoreCase("Nursery")){
//                            intent = new Intent(mContext.getApplicationContext(), SimpleTestActivity.class);
//                            intent.putExtra("Notification", true);
//                            intent.putExtra("GradeSelected", grade);
//                            intent.putExtra("SubjectSelected", subject);
//                            intent.putExtra("Alpha", testname);
//                            intent.putExtra("source", "nonsocratic");
//                    }else{
//                        intent = new Intent(mContext.getApplicationContext(), UnityPlayerActivity.class);
//                        intent.putExtra("Notification", true);
//                        intent.putExtra("GradeSelected", grade);
//                        intent.putExtra("SubjectSelected", subject);
//                        intent.putExtra("Alpha", testname);
//                        intent.putExtra("GameType", nurserygametype);
//                        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    }
//                    break;
//
//                default:
//                    intent = new Intent(mContext.getApplicationContext(), UnityPlayerActivity.class);
//                    break;
//            }
//            return intent;
//        }
//        @Override
//        protected Bitmap doInBackground(String... params) {
//
//            InputStream in;
//            try {
//                URL url = new URL(this.imageUrl);
//                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                connection.setDoInput(true);
//                connection.connect();
//                in = connection.getInputStream();
//                Bitmap myBitmap = BitmapFactory.decodeStream(in);
//                return myBitmap;
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//        @Override
//        protected void onPostExecute(Bitmap result) {
//            super.onPostExecute(result);
//            int m;
//            if(notificationId!=null){
//                m=Integer.parseInt(notificationId);
//            }else{
//                m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE); // to get unique id for notification
//            }
//            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
//            FirebaseAuth mAuth = FirebaseAuth.getInstance();
//            final FirebaseUser currentUser = mAuth.getCurrentUser();
//            Long AnonymousD0Signup = preferences.getLong("AnonymousD0Signup", 0);
//            Intent intent = null;
//            if(currentUser != null){
//                if(!currentUser.isAnonymous() || System.currentTimeMillis() < AnonymousD0Signup + 86400000){
//                    intent =  createIntentForNotification(type, link, grade, subject, videoname, testname, nurserygametype);
//                }else{
//                    intent = new Intent(mContext, SignupActivity.class);
//                }
//            }else{
//                intent = new Intent(mContext, SignupActivity.class);
//            }
//
//
//            intent.putExtra("NotificationId",m);
//            intent.putExtra("NotificationTime",notificationtimestamp);
//            intent.putExtra("NotificationType",type);
//
//            PendingIntent resultIntent = PendingIntent.getActivity(mContext.getApplicationContext(), 0, intent,
//                    PendingIntent.FLAG_ONE_SHOT);
//            Uri sound=Uri.parse("android.resource://com.orenda.taimo.myapplication/raw/beep");
//            NotificationCompat.Builder mNotificationBuilder = new NotificationCompat.Builder( mContext,"GeneralNotifications")
//
//                   // .setSmallIcon(R.drawable.notificationicon)//app_icon
//                    .setSmallIcon(R.drawable.notificationicon)
//                    .setContentTitle(title)
//                    .setContentText(body)
//                    .setSound(sound)
//                    .setLargeIcon(result)
//                    .setStyle(new NotificationCompat.BigPictureStyle()
//                            .bigPicture(result)
//                            .bigLargeIcon(null))
//                    .setContentIntent(resultIntent)
//                    .setDeleteIntent(createOnDismissedIntent(mContext, notificationId,notificationtimestamp,type)) // notification dismiss
//                    .setAutoCancel(true);
//            if(button1!=null){
//                mNotificationBuilder.addAction(0,button1,resultIntent);
//            }
//            if(button2!=null || type.equalsIgnoreCase("VideoTest")){
//                Intent button2intent = null;
//                if(currentUser != null){
//                    if(!currentUser.isAnonymous() || System.currentTimeMillis() < AnonymousD0Signup + 86400000){
//                        button2intent =  createIntentForNotificationButton2(type, link, grade, subject, videoname, testname, nurserygametype);
//                    }else{
//                        button2intent = new Intent(mContext, SignupActivity.class);
//                    }
//                }else{
//                    button2intent = new Intent(mContext, SignupActivity.class);
//                }
//                button2intent.putExtra("NotificationId",m);
//                PendingIntent button2resultIntent = PendingIntent.getActivity(mContext.getApplicationContext(), 0, button2intent,
//                        PendingIntent.FLAG_ONE_SHOT);
//                mNotificationBuilder.addAction(0,button2,button2resultIntent);
//            }
//
//            NotificationManager notificationManager =
//                    (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
//
//            notificationManager.notify(m, mNotificationBuilder.build());
//
//        }
//        private PendingIntent createOnDismissedIntent(Context context, String notificationId, String notificationTimeStamp, String notificationType) {
//            int notificationIdConverted = Integer.parseInt(notificationId);
//            Intent notificationDismisIntent = new Intent(context, NotificationDismissedReceiver.class);
//            notificationDismisIntent.putExtra("NotificationId",notificationId);
//            notificationDismisIntent.putExtra("NotificationTime",notificationTimeStamp);
//            notificationDismisIntent.putExtra("NotificationType",notificationType);
//            PendingIntent pendingIntent1 =
//                    PendingIntent.getBroadcast(context.getApplicationContext(),
//                            notificationIdConverted, notificationDismisIntent, 0);
//            return pendingIntent1;
//        }
//    }
//
//    class HandlePictureStyleFirebaseCustomNotification extends AsyncTask<String, Void, Bitmap>{
//
//    private Context mContext;
//    private String imageUrl, type, title, body, videoname, testname, grade, subject, link, nurserygametype;
//
//    public HandlePictureStyleFirebaseCustomNotification(Context context, String image, String title, String body, String type,  String link, String grade, String subject, String videoname, String testname, String nurserygametype) {
//        super();
//        this.mContext = context;
//        this.type = type;
//        this.title = title;
//        this.body = body;
//        this.imageUrl = image;
//        this.videoname =videoname;
//        this.testname = testname;
//        this.grade = grade;
//        this.subject = subject;
//        this.link= link;
//        this.nurserygametype = nurserygametype;
//    }
//
//    @Override
//    protected Bitmap doInBackground(String... params) {
//
//        InputStream in;
//        try {
//            URL url = new URL(this.imageUrl);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setDoInput(true);
//            connection.connect();
//            in = connection.getInputStream();
//            Bitmap myBitmap = BitmapFactory.decodeStream(in);
//            return myBitmap;
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//    @Override
//    protected void onPostExecute(Bitmap result) {
//        super.onPostExecute(result);
//        Intent intent = null;
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
//        FirebaseAuth mAuth = FirebaseAuth.getInstance();
//        final FirebaseUser currentUser = mAuth.getCurrentUser();
//        Long AnonymousD0Signup = preferences.getLong("AnonymousD0Signup", 0);
//        if(currentUser != null){
//            if(!currentUser.isAnonymous() || System.currentTimeMillis() < AnonymousD0Signup + 86400000){
//                switch (type) {
//                    case "Videos":
//                        intent = new Intent(mContext, VideoActivity.class);
//                        intent.putExtra("Notification", true);
//                        intent.putExtra("GradeSelected", grade);
//                        intent.putExtra("SubjectSelected", subject);
//                        intent.putExtra("Alpha", videoname);
//                        intent.putExtra("ContentUnlocked", preferences.getInt("AllContentUnlocked", 0));
//                        intent.putExtra("PurchaseStatus", preferences.getInt("app_purchase", 0));
//                        break;
//                    case "Test":
//                        if (!grade.equalsIgnoreCase("Nursery")) {
//                            intent = new Intent(mContext, SimpleTestActivity.class);
//                            intent.putExtra("Notification", true);
//                            intent.putExtra("GradeSelected", grade);
//                            intent.putExtra("SubjectSelected", subject);
//                            intent.putExtra("Alpha", testname);
//                            intent.putExtra("source", "nonsocratic");
//
//                        } else {
//                            intent = new Intent(mContext, UnityPlayerActivity.class);
//                            //intent.putExtra("Notification", true);
//                            //intent.putExtra("GradeSelected", grade);
//                            //intent.putExtra("SubjectSelected", subject);
//                            //intent.putExtra("Alpha", testname);
//                            //intent.putExtra("GameType", nurserygametype);
//                            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                        }
//                        break;
//                    case "Survey":
//                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
//                        break;
//                    case "ParentPortal":
//                        intent = new Intent(mContext, UnityPlayerActivity.class);
//                        //intent.putExtra("Notification", true);
//                        //intent.putExtra("ParentPortal", true);
//                        break;
//                    case "Referral":
//                        intent = new Intent(mContext, UnityPlayerActivity.class);
//                        //intent.putExtra("Notification", true);
//                        //intent.putExtra("ReferralNotification", true);
//                        break;
//                    case "Home":
//                        intent = new Intent(mContext, UnityPlayerActivity.class);
//                        break;
//                    default:
//                        intent = new Intent(mContext, UnityPlayerActivity.class);
//                        break;
//                }
//            }else{
//                intent = new Intent(mContext, SignupActivity.class);
//            }
//        }else{
//            intent = new Intent(mContext, SignupActivity.class);
//        }
//
//        PendingIntent resultIntent = PendingIntent.getActivity(mContext, 0, intent,
//                PendingIntent.FLAG_ONE_SHOT);
//
//        NotificationCompat.Builder mNotificationBuilder = new NotificationCompat.Builder( mContext,"GeneralNotifications")
//
//                .setSmallIcon(R.drawable.notificationicon)
//                .setContentTitle(title)
//                .setContentText(body)
//                .setLargeIcon(result)
//                .setStyle(new NotificationCompat.BigPictureStyle()
//                        .bigPicture(result)
//                        .bigLargeIcon(null))
//                .setContentIntent(resultIntent)
//                .setAutoCancel(true);
//        NotificationManager notificationManager =
//                (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(0, mNotificationBuilder.build());
//    }
}