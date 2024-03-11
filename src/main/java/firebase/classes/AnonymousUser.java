package firebase.classes;

public class AnonymousUser {
    public  String fcmToken;
    public String signupDate;
    public String signinDate;
    public String phoneNumber;
    public String userName;

    public AnonymousUser(String fcmToken, String signupDate, String signinDate, String phoneNumber, String userName) {
        this.fcmToken = fcmToken;
        this.signupDate = signupDate;
        this.signinDate = signinDate;
        this.phoneNumber = phoneNumber;
        this.userName = userName;
    }
}
