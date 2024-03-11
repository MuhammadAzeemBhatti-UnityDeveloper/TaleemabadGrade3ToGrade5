package firebase.classes;

//Class to validate coupon
//Class used in UnityActivity
public class couponvalidityfirebase {
    public String status;
    public String useruid;
    public String activationDate;

    public couponvalidityfirebase(String status, String useruid, String activationDate) {
        this.status = status;
        this.useruid = useruid;
        this.activationDate = activationDate;

    }
}
