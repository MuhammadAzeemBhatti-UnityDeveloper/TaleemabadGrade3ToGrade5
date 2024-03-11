package firebase.classes;

//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;

public class identificationGames {
    public String startTime, completionTime, GameName, GameType, Status;
    public int stars;

    public identificationGames(String startTime, String completionTime, String gameName, String gameType, String status, int stars) {
        this.startTime = startTime;
        this.completionTime = completionTime;
        GameName = gameName;
        GameType = gameType;
        Status = status;
        this.stars = stars;
    }

    public void setGameData(){
        String Alphabet=getAlphabet(GameName);
        String Subject=getSubject(GameName);
//        FirebaseAuth mAuth = FirebaseAuth.getInstance();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        final String userUid = currentUser.getUid();
//        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users/"+userUid+"/"+Subject+"/Identification/"+Alphabet);
        identificationGames identificationGames=new identificationGames(this.startTime,this.completionTime,this.GameName,this.GameType,this.Status,this.stars);
      //  mDatabase.push().setValue(identificationGames);
    }

    public String getAlphabet(String gameName){
        switch (gameName){
            case "Eng_a_test1":
                return "a";
            case "Eng_b_test1":
                return "b";
            case "Eng_c_test1":
                return "c";
            case "Eng_d_test1":
                return "d";
            case "Eng_e_test1":
                return "e";
            case "Eng_f_test1":
                return "f";
            case "Eng_g_test1":
                return "g";
            case "Eng_h_test1":
                return "h";
            case "Eng_i_test1":
                return "i";
            case "Eng_j_test1":
                return "j";
            case "Eng_k_test1":
                return "k";
            case "Eng_l_test1":
                return "l";
            case "Eng_m_test1":
                return "m";
            case "Eng_n_test1":
                return "n";
            case "Eng_o_test1":
                return "o";
            case "Eng_p_test1":
                return "p";
            case "Eng_q_test1":
                return "q";
            case "Eng_r_test1":
                return "r";
            case "Eng_s_test1":
                return "s";
            case "Eng_t_test1":
                return "t";
            case "Eng_u_test1":
                return "u";
            case "Eng_v_test1":
                return "v";
            case "Eng_w_test1":
                return "w";
            case "Eng_x_test1":
                return "x";
            case "Eng_y_test1":
                return "y";
            case "Eng_z_test1":
                return "z";
            case "Urdu_alif_test1":
                return "alif";
            case "Urdu_array_test1":
                return "array";
            case "Urdu_bay_test1":
                return "bay";
            case "Urdu_chay_test1":
                return "chay";
            case "Urdu_dhaal_test1":
                return "dhaal";
            case "Urdu_hay_test1":
                return "hay";
            case "Urdu_jeem_test1":
                return "jeem";
            case "Urdu_khay_test1":
                return "khay";
            case "Urdu_pay_test1":
                return "pay";
            case "Urdu_raay_test1":
                return "ray";
            case "Urdu_say_test1":
                return "say";
            case "Urdu_tay_test1":
                return "tay";
            case "Urdu_thay_test1":
                return "thay";
            case "Urdu_zaal_test1":
                return "zaal";
            case "Urdu_zay_test1":
                return "zay";
            case "Urdu_zhay_test1":
                return "zhay";
            case "Math_Count_1":
                return "one";
            case "Math_Count_2":
                return "two";
            case "Math_Count_3":
                return "three";
            case "Math_Count_4":
                return "four";
            case "Math_5_test1":
                return "five";
            case "Math_6_test1":
                return "six";
            case "Math_7_test1":
                return "seven";
            case "Math_8_test1":
                return "eight";
            case "Math_9_test1":
                return "nine";
        }
        return "";
    }

    public String getSubject(String gameName){
        switch (gameName){
            case "Eng_a_test1":
            case "Eng_b_test1":
            case "Eng_c_test1":
            case "Eng_d_test1":
            case "Eng_e_test1":
            case "Eng_f_test1":
            case "Eng_g_test1":
            case "Eng_h_test1":
            case "Eng_i_test1":
            case "Eng_j_test1":
            case "Eng_k_test1":
            case "Eng_l_test1":
            case "Eng_m_test1":
            case "Eng_n_test1":
            case "Eng_o_test1":
            case "Eng_p_test1":
            case "Eng_q_test1":
            case "Eng_r_test1":
            case "Eng_s_test1":
            case "Eng_t_test1":
            case "Eng_u_test1":
            case "Eng_v_test1":
            case "Eng_w_test1":
            case "Eng_x_test1":
            case "Eng_y_test1":
            case "Eng_z_test1":
                return "English";
            case "Urdu_alif_test1":
            case "Urdu_array_test1":
            case "Urdu_bay_test1":
            case "Urdu_chay_test1":
            case "Urdu_dhaal_test1":
            case "Urdu_hay_test1":
            case "Urdu_jeem_test1":
            case "Urdu_khay_test1":
            case "Urdu_pay_test1":
            case "Urdu_raay_test1":
            case "Urdu_say_test1":
            case "Urdu_tay_test1":
            case "Urdu_thay_test1":
            case "Urdu_zaal_test1":
            case "Urdu_zay_test1":
            case "Urdu_zhay_test1":
                return "Urdu";
            case "Math_Count_1":
            case "Math_Count_2":
            case "Math_Count_3":
            case "Math_Count_4":
            case "Math_5_test1":
            case "Math_6_test1":
            case "Math_7_test1":
            case "Math_8_test1":
            case "Math_9_test1":
                return "Maths";
        }
        return "";
    }
}
