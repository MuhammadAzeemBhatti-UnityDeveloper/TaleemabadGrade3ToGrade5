package firebase.classes;

public class RecentActivity {
    public String activityName;
    public int stars;
    public long gameTime;


    public RecentActivity(String activityName, int stars, long gameTime) {
        this.activityName = activityName;
        this.stars = stars;
        this.gameTime=gameTime;
    }
}
