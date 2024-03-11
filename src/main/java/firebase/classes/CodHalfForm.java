package firebase.classes;

//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;

public class CodHalfForm {
    public String phoneNumber;
    public String address;

    public CodHalfForm(String phoneNumber, String address) {
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public void setHalfCodFormData(){
//        FirebaseAuth mAuth = FirebaseAuth.getInstance();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        final String userUid = currentUser.getUid();
//        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("COD/HalfForm/"+userUid);
        CodHalfForm codHalfForm=new CodHalfForm(this.phoneNumber,this.address);
      //  mDatabase.push().setValue(codHalfForm);
    }
}
