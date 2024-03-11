package firebase.classes;

//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;

public class Subscription {
    public long ActivationDate;
    public long ExpiryDate;
    public String Mode;

    public Subscription(long activationDate, long expiryDate, String mode) {
        ActivationDate = activationDate;
        ExpiryDate = expiryDate;
        Mode = mode;
    }

    public Subscription() {
    }

    public void SetSubscriptionData(String userUid, Subscription subscription, String PhoneNumber){
        /*final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users/"+userUid+"/Subscription");
        String key = mDatabase.child("SubscriptionList").push().getKey();
        mDatabase.child("SubscriptionList").child(key).setValue(subscription);
        mDatabase.child("Status").setValue("Active");
        mDatabase.child("ActiveSubscriptionId").setValue(key);*/
//        final DatabaseReference phoneNumberDatabase = FirebaseDatabase.getInstance().getReference("SubscribersPhoneNumber"); //"+userUid+"/Subscription");
//        phoneNumberDatabase.child(PhoneNumber).child("UID").setValue(userUid);
    }

//    public void SetUnSubscribeData(String userUid){
//        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users/"+userUid+"/Subscription");
//        mDatabase.child("Status").setValue("InActive");
//    }
}
