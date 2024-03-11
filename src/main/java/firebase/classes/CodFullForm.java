package firebase.classes;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.MutableData;
//import com.google.firebase.database.Transaction;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CodFullForm {
   public String name;
   public String signupName;
   public String address;
   public String city;
   public String phoneNumber;
    public String signupPhoneNumber;
   public String formType;
   public String bundle;
   public String bundleDuration;
   public String date;
   public String uid;
   public int orderId;
   public String purchaseMethod;
   public boolean booksAddOn;
   public String promoCode;
   public boolean workSheetAddOn;
    public CodFullForm(String name,  String signupName, String address, String city, String phoneNumber, String signupPhoneNumber,String formType, String bundle, String bundleDuration, int orderId, String purchaseMethod, boolean booksAddOn, String promoCode, boolean workSheetAddOn) {
        this.name = name;
        this.signupName = signupName;
        this.address = address;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.signupPhoneNumber = signupPhoneNumber;
        this.formType=formType;
        this.bundle =bundle;
        this.bundleDuration=bundleDuration;
        this.orderId = orderId;
        this.purchaseMethod = purchaseMethod;
        this.booksAddOn = booksAddOn;
        this.promoCode = promoCode;
        this.workSheetAddOn = workSheetAddOn;
    }

    public CodFullForm(String name, String signupName, String address, String city, String phoneNumber, String signupPhoneNumber, String formType, String bundle, String bundleDuration, String date, String uid, int orderId, String purchaseMethod, boolean booksAddOn, String promoCode, boolean workSheetAddOn) {
        this.name = name;
        this.signupName = signupName;
        this.address = address;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.signupPhoneNumber = signupPhoneNumber;
        this.formType = formType;
        this.bundle = bundle;
        this.bundleDuration = bundleDuration;
        this.date = date;
        this.uid = uid;
        this.orderId = orderId;
        this.purchaseMethod = purchaseMethod;
        this.booksAddOn = booksAddOn;
        this.promoCode = promoCode;
        this.workSheetAddOn = workSheetAddOn;
    }

    public void setFullCodFormData(){

//        FirebaseAuth mAuth = FirebaseAuth.getInstance();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
        this.date=new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
//        final String userUid = currentUser.getUid();
//        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("COD/FullForm/"+userUid);
      //  CodFullForm codFullForm=new CodFullForm(this.name, this.signupName,this.address, this.city,this.phoneNumber, this.signupPhoneNumber,this.formType, this.bundle, this.bundleDuration,this.date,userUid,this.orderId, this.purchaseMethod, this.booksAddOn, this.promoCode, this.workSheetAddOn);
       // mDatabase.push().setValue(codFullForm);
    }
}
