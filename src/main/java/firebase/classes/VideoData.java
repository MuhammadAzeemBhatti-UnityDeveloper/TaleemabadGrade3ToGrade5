package firebase.classes;

import android.content.Context;

//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
import com.orenda.taimo.grade3tograde5.SignupActivity;

import java.util.Date;

public class VideoData {
    public String videoName, startTime, status, finishTime;
    Context context;

    public VideoData(String videoName, String startTime, String status, String finishTime, Context context) {
        this.videoName = videoName;
        this.startTime = startTime;
        this.status = status;
        this.finishTime = finishTime;
        this.context = context;
    }
    public VideoData(String videoName, String startTime, String status, String finishTime) {
        this.videoName = videoName;
        this.startTime = startTime;
        this.status = status;
        this.finishTime = finishTime;
    }
    public void setVideoData(){
        final String userUid;
        String Alphabet=getAlphabet(videoName);
        String Subject=getSubject(videoName);
        String Grade=getGrade(videoName);

        if(SignupActivity.userUid!=null){
            userUid = SignupActivity.userUid;
          //  final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users/"+userUid+"/"+Grade+'/'+Subject+"/Videos/"+Alphabet);
            VideoData videoData=new VideoData(this.videoName,this.startTime, this.status,this.finishTime);
          //  mDatabase.push().setValue(videoData);
            setRecentActivity(userUid);
        }
        else{
           /*
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = mAuth.getCurrentUser();
            userUid = currentUser.getUid();*/
        }
        /*
        if(userUid!=null){
        }
        else{
            FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
            mFirebaseAnalytics.logEvent("Video_send_data_error", null);
        }*/
    }
    /*public void setVideoData(){
        final String userUid;
        String Alphabet=getAlphabet(videoName);
        String Subject=getSubject(videoName);
        String Grade=getGrade(videoName);

        if(SignupActivity.userUid!=null){
             userUid = SignupActivity.userUid;
        }
        else{
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = mAuth.getCurrentUser();
            userUid = currentUser.getUid();
        }
        if(userUid!=null){
            final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users/"+userUid+"/"+Grade+'/'+Subject+"/Videos/"+Alphabet);
            VideoData videoData=new VideoData(this.videoName,this.startTime, this.status,this.finishTime);
            mDatabase.push().setValue(videoData);
            setRecentActivity(userUid);
        }
        else{
            FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
            mFirebaseAnalytics.logEvent("Video_send_data_error", null);
        }
    }
    */
    public void setRecentActivity(String useruid){
        long time=new Date().getTime();
      //  final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users/"+useruid+"/Activity");
        RecentActivity recentActivity=new RecentActivity(this.videoName+"_video",4,time);
     //   mDatabase.push().setValue(recentActivity);
    }


    public String getAlphabet(String videoName){
        switch (videoName){
            case "a":
                return "a";
            case "s":
                return "s";
            case "t":
                return "t";
            case "i":
                return "i";
            case "p":
                return "p";
            case "n":
                return "n";
            case "c":
                return "c";
            case "k":
                return "k";
            case "e":
                return "e";
            case "h":
                return "h";
            case "r":
                return "r";
            case "m":
                return "m";
            case "d":
                return "d";
            case "g":
                return "g";
            case "o":
                return "o";
            case "u":
                return "u";
            case "l":
                return "l";
            case "f":
                return "f";
            case "b":
                return "b";
            case "j":
                return "j";
            case "z":
                return "z";
            case "w":
                return "w";
            case "v":
                return "v";
            case "y":
                return "y";
            case "x":
                return "x";
            case "q":
                return "q";
            case "alif":
                return "alif";
            case "bay":
                return "bay";
            case "pay":
                return "pay";
            case "thay":
                return "thay";
            case "tay":
                return "tay";
            case "say":
                return "say";
            case "jeem":
                return "jeem";
            case "chay":
                return "chay";
            case "hay":
                return "hay";
            case "khay":
                return "khay";
            case "daal":
                return "daal";
            case "dhal":
                return "dhaal";
            case "zaal":
                return "zaal";
            case "raay":
                return "ray";
            case "araay":
                return "array";
            case "zay":
                return "zay";
            case "zhay":
                return "zhay";
            case "seen":
                return "seen";
            case "sheen":
                return "sheen";
            case "swad":
                return "swad";
            case "zwad":
                return "zwad";
            case "toay":
                return "toay";
            case "zoay":
                return "zoay";
            case "ain":
                return "ain";
            case "ghain":
                return "ghain";
            case "fay":
                return "fay";
            case "qaf":
                return "qaf";
            case "kaf":
                return "kaf";
            case "gaf":
                return "gaf";
            case "lam":
                return "lam";
            case "meem":
                return "meem";
            case "noon":
                return "noon";
            case "wow":
                return "wow";
            case "goldochashmihay":
                return "goldochashmihay";
            case "hamza":
                return "hamza";
            case "chotibariyay":
                return "chotibariyay";
            case "1":
                return "one";
            case "2":
                return "two";
            case "3":
                return "three";
            case "4":
                return "four";
            case "5":
                return "five";
            case "6":
                return "six";
            case "7":
                return "seven";
            case "8":
                return "eight";
            case "9":
                return "nine";
            case "tensunits":
                return "tensunit";
            case "10":
                return "ten";
            case "1112":
                return "eleventwelve";
            case "0":
                return "zero";
            case "add":
                return "add";
            case "safainisfeman":
                return "safainisfeman";
            case "lalachkisaza":
                return "lalachkisaza";
            case "darakhtonkfawaid":
                return "darakhtonkfawaid";
            case "panikibachat":
                return "panikibachat";
            case "bijlikipedawar":
                return "bijlikipedawar";
            case "chorikarnaburiadat":
                return "chorikarnaburiadat";
            case "gharoorkasarnecha":
                return "gharoorkasarnecha";
            case "aikaoraikgyara":
                return "aikaoraikgyara";
            case "sachachiadat":
                return "sachachiadat";
            case "chardinkichandni":
                return "chardinkichandni";
            case "safaikayfaiday":
                return "safaikayfaiday";
            case "jahanchahwahrah":
                return "jahanchahwahrah";
            case "jaisakarowaisabharo":
                return "jaisakarowaisabharo";
            case "insankirdarsaypehchano":
                return "insankirdarsaypehchano";
            case "darkayagayjeet":
                return "darkayagayjeet";
        }
        return "";
    }

    public String getSubject(String videoName){
        switch (videoName){
            case "a":
            case "s":
            case "t":
            case "i":
            case "p":
            case "n":
            case "c":
            case "k":
            case "e":
            case "h":
            case "r":
            case "m":
            case "d":
            case "g":
            case "o":
            case "u":
            case "l":
            case "f":
            case "b":
            case "j":
            case "z":
            case "w":
            case "v":
            case "y":
            case "x":
            case "q":
                return "English";
            case "alif":
            case "bay":
            case "pay":
            case "thay":
            case "tay":
            case "say":
            case "jeem":
            case "chay":
            case "hay":
            case "khay":
            case "daal":
            case "dhal":
            case "zaal":
            case "raay":
            case "araay":
            case "zay":
            case "zhay":
            case "seen":
            case "sheen":
            case "swad":
            case "zwad":
            case "toay":
            case "zoay":
            case "ain":
            case "ghain":
            case "fay":
            case "qaf":
            case "kaf":
            case "gaf":
            case "lam":
            case "meem":
            case "noon":
            case "wow":
            case "goldochashmihay":
            case "hamza":
            case "chotibariyay":
                return "Urdu";
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
            case "10":
            case "1112":
            case "0":
            case "add":
                return "Maths";
            case "safainisfeman":
            case "lalachkisaza":
            case "darakhtonkfawaid":
            case "panikibachat":
            case "bijlikipedawar":
            case "chorikarnaburiadat":
            case "gharoorkasarnecha":
            case "aikaoraikgyara":
            case "sachachiadat":
            case "chardinkichandni":
            case "safaikayfaiday":
            case "jahanchahwahrah":
            case "jaisakarowaisabharo":
            case "insankirdarsaypehchano":
            case "darkayagayjeet":
                return "Tarbiyat";
            case "grade1Nouns":
            case "grade1WordFamiliesAt":
            case "grade1WordFamiliesAn":
            case "grade1WordFamiliesAp":
            case "grade1WordFamiliesEn":
            case "grade1WordFamiliesIn":
            case "grade1WordFamiliesOt":
                return "English";
            case "grade1Ordinal":
            case "grade1Addition1":
            case "grade1Addition2":
            case "grade1BigSmall":
            case "grade1Subtraction1":
            case "grade1Subtraction2":
            case "grade1table2":
            case "grade1table3":
            case "grade1table4":
            case "grade1table5":
            case "grade1Circle":
            case "grade1Square":
            case "grade1Triangle":
            case "grade1Rectangle":
            case "grade1Time":
            case "grade1Calender":
                return "Maths";

        }
        return "";
    }

    public String getGrade(String videoName) {
        switch (videoName){
            case "a":
            case "s":
            case "t":
            case "i":
            case "p":
            case "n":
            case "c":
            case "k":
            case "e":
            case "h":
            case "r":
            case "m":
            case "d":
            case "g":
            case "o":
            case "u":
            case "l":
            case "f":
            case "b":
            case "j":
            case "z":
            case "w":
            case "v":
            case "y":
            case "x":
            case "q":
            case "alif":
            case "bay":
            case "pay":
            case "thay":
            case "tay":
            case "say":
            case "jeem":
            case "chay":
            case "hay":
            case "khay":
            case "daal":
            case "dhal":
            case "zaal":
            case "raay":
            case "araay":
            case "zay":
            case "zhay":
            case "seen":
            case "sheen":
            case "swad":
            case "zwad":
            case "toay":
            case "zoay":
            case "ain":
            case "ghain":
            case "fay":
            case "qaf":
            case "kaf":
            case "gaf":
            case "lam":
            case "meem":
            case "noon":
            case "wow":
            case "goldochashmihay":
            case "hamza":
            case "chotibariyay":
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
            case "0":
            case "tensunits":
            case "10":
            case "1112":
            case "add":
            case "safainisfeman":
            case "lalachkisaza":
            case "darakhtonkfawaid":
            case "panikibachat":
            case "bijlikipedawar":
            case "chorikarnaburiadat":
            case "gharoorkasarnecha":
            case "aikaoraikgyara":
            case "sachachiadat":
            case "chardinkichandni":
            case "safaikayfaiday":
            case "jahanchahwahrah":
            case "jaisakarowaisabharo":
            case "insankirdarsaypehchano":
            case "darkayagayjeet":
                return "Nursery";
            case "grade1Nouns":
            case "grade1WordFamiliesAt":
            case "grade1WordFamiliesAn":
            case "grade1WordFamiliesAp":
            case "grade1WordFamiliesEn":
            case "grade1WordFamiliesIn":
            case "grade1WordFamiliesOt":
            case "grade1Ordinal":
            case "grade1Addition1":
            case "grade1Addition2":
            case "grade1BigSmall":
            case "grade1Subtraction1":
            case "grade1Subtraction2":
            case "grade1table2":
            case "grade1table3":
            case "grade1table4":
            case "grade1table5":
            case "grade1Circle":
            case "grade1Square":
            case "grade1Triangle":
            case "grade1Rectangle":
            case "grade1Time":
            case "grade1Calender":
                return "GradeOne";
        }
        return "";
    }
}
