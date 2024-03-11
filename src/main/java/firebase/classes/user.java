package firebase.classes;

//import com.facebook.internal.BoltsMeasurementEventListener;

//Class is used to register new user on firebase when signups
//Class is used in SignupActivity
public class user {
    public  String name;
    public  String email;
    public  String fcmToken;
    public int currentPosition;
    public String signupDate;
    public String signinDate;
    public boolean teacherStatus;

    public user(String name, String email, String fcmToken, int currentPosition, String signupDate, String signinDate, boolean teacherStatus) {
        this.name = name;
        this.email = email;
        this.fcmToken=fcmToken;
        this.currentPosition=currentPosition;
        this.signupDate=signupDate;
        this.signinDate=signinDate;
        this.teacherStatus=teacherStatus;
    }
}
