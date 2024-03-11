package firebase.classes;

//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;

public class vocabularyGames {
    public String startTime, completionTime, GameName, GameType, Status;
    public int stars;

    public vocabularyGames(String startTime, String completionTime, String gameName, String gameType, String status, int stars) {
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
//        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users/"+userUid+"/"+Subject+"/Vocabulary/"+Alphabet);
        vocabularyGames vocabularyGames=new vocabularyGames(this.startTime,this.completionTime,this.GameName,this.GameType,this.Status,this.stars);
       // mDatabase.push().setValue(vocabularyGames);
    }

    public String getAlphabet(String gameName){
        switch (gameName){
            case "Eng_a_test2":
                return "a";
            case "Eng_b_test2":
                return "b";
            case "Eng_c_test2":
                return "c";
            case "Eng_d_test2":
                return "d";
            case "Eng_e_test2":
                return "e";
            case "Eng_f_test2":
                return "f";
            case "Eng_g_test2":
                return "g";
            case "Eng_h_test2":
                return "h";
            case "Eng_i_test2":
                return "i";
            case "Eng_j_test2":
                return "j";
            case "Eng_k_test2":
                return "k";
            case "Eng_l_test2":
                return "l";
            case "Eng_m_test2":
                return "m";
            case "Eng_n_test2":
                return "n";
            case "Eng_o_test2":
                return "o";
            case "Eng_p_test2":
                return "p";
            case "Eng_q_test2":
                return "q";
            case "Eng_r_test2":
                return "r";
            case "Eng_s_test2":
                return "s";
            case "Eng_t_test2":
                return "t";
            case "Eng_u_test2":
                return "u";
            case "Eng_v_test2":
                return "v";
            case "Eng_w_test2":
                return "w";
            case "Eng_x_test2":
                return "x";
            case "Eng_y_test2":
                return "y";
            case "Eng_z_test2":
                return "z";
            case "Urdu_alif_test2":
                return "alif";
            case "Urdu_array_test2":
                return "array";
            case "Urdu_bay_test2":
                return "bay";
            case "Urdu_chay_test2":
                return "chay";
            case "Urdu_daal_test2":
                return "daal";
            case "Urdu_dhaal_test2":
                return "dhaal";
            case "Urdu_hay_test2":
                return "hay";
            case "Urdu_jeem_test2":
                return "jeem";
            case "Urdu_khay_test2":
                return "khay";
            case "Urdu_pay_test2":
                return "pay";
            case "Urdu_ray_test2":
                return "ray";
            case "Urdu_say_test3":
                return "say";
            case "Urdu_Tay_Test2":
                return "tay";
            case "Urdu_thay_test2":
                return "thay";
            case "Urdu_zaal_test2":
                return "zaal";
            case "Urdu_zay_test2":
                return "zay";
            case "Urdu_zhay_test2":
                return "zhay";
            case "Math_1_test2":
                return "one";
            case "Math_2_test2":
                return "two";
            case "Math_3_test2":
                return "three";
            case "Math_4_test2":
                return "four";
            case "Math_5_test2":
                return "five";
            case "Math_6_test2":
                return "six";
            case "Math_7_test2":
                return "seven";
        }
        return "";
    }

    public String getSubject(String gameName){
        switch (gameName){
            case "Eng_a_test2":
            case "Eng_b_test2":
            case "Eng_c_test2":
            case "Eng_d_test2":
            case "Eng_e_test2":
            case "Eng_f_test2":
            case "Eng_g_test2":
            case "Eng_h_test2":
            case "Eng_i_test2":
            case "Eng_j_test2":
            case "Eng_k_test2":
            case "Eng_l_test2":
            case "Eng_m_test2":
            case "Eng_n_test2":
            case "Eng_o_test2":
            case "Eng_p_test2":
            case "Eng_q_test2":
            case "Eng_r_test2":
            case "Eng_s_test2":
            case "Eng_t_test2":
            case "Eng_u_test2":
            case "Eng_v_test2":
            case "Eng_w_test2":
            case "Eng_x_test2":
            case "Eng_y_test2":
            case "Eng_z_test2":
                return "English";
            case "Urdu_alif_test2":
            case "Urdu_array_test2":
            case "Urdu_bay_test2":
            case "Urdu_chay_test2":
            case "Urdu_daal_test2":
            case "Urdu_dhaal_test2":
            case "Urdu_hay_test2":
            case "Urdu_jeem_test2":
            case "Urdu_khay_test2":
            case "Urdu_pay_test2":
            case "Urdu_ray_test2":
            case "Urdu_say_test3":
            case "Urdu_Tay_Test2":
            case "Urdu_thay_test2":
            case "Urdu_zaal_test2":
            case "Urdu_zay_test2":
            case "Urdu_zhay_test2":
                return "Urdu";
            case "Math_1_test2":
            case "Math_2_test2":
            case "Math_3_test2":
            case "Math_4_test2":
            case "Math_5_test2":
            case "Math_6_test2":
            case "Math_7_test2":
                return "Maths";
        }
        return "";
    }
}
