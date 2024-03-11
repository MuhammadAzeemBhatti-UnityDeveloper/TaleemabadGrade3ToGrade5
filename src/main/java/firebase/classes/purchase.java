package firebase.classes;

//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;

//class to update purchase about user
//class used in downloadGamesFile
public class purchase {

    public String status;
    public String mode;
    public String coupon;
    public String bundle;
    public String activationDate;
    public String expiryDate;



    public purchase(String status, String mode, String coupon, String bundle, String activationDate, String expiryDate) {
        this.status = status;
        this.mode = mode;
        this.coupon = coupon;
        this.bundle = bundle;
        this.activationDate = activationDate;
        this.expiryDate = expiryDate;
    }

    public purchase(String status, String mode, String coupon) {
        this.status = status;
        this.mode = mode;
        this.coupon = coupon;
    }

//    public void setPurchaseData(purchase purchaseObj){
//        FirebaseAuth mAuth = FirebaseAuth.getInstance();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users/" + currentUser.getUid());
//        mDatabase.child("purchase").child(bundle).setValue(purchaseObj);
//    }
}
