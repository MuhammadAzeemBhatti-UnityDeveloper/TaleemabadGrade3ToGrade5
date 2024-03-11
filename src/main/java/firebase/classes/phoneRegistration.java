package firebase.classes;

//class used for registering new user by phone number authentication
public class phoneRegistration {
    public String phone;
    public String fcmToke;

    public phoneRegistration(String phone, String fcmToken) {
        this.phone = phone;
        this.fcmToke=fcmToken;
    }
}
