package firebase.classes;

//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;

public class writingGames {
    public String startTime, completionTume, GameName, GameType, Status;
    public int stars;

    public writingGames(String startTime, String completionTume, String gameName, String gameType, String status, int stars) {
        this.startTime = startTime;
        this.completionTume = completionTume;
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
//        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users/"+userUid+"/"+Subject+"/Writing/"+Alphabet);
        writingGames writingGames=new writingGames(this.startTime,this.completionTume,this.GameName,this.GameType,this.Status,this.stars);
        //mDatabase.push().setValue(writingGames);
    }

    public String getAlphabet(String gameName){
        switch (gameName){
            case "Eng_a_writing":
                return "a";
            case "Eng_b_writing":
                return "b";
            case "Eng_c_writing":
                return "c";
            case "Eng_d_writing":
                return "d";
            case "Eng_e_writing":
                return "e";
            case "Eng_f_writing":
                return "f";
            case "Eng_g_writing":
                return "g";
            case "Eng_h_writing":
                return "h";
            case "Eng_i_writing":
                return "i";
            case "Eng_j_writing":
                return "j";
            case "Eng_k_writing":
                return "k";
            case "Eng_l_writing":
                return "l";
            case "Eng_m_writing":
                return "m";
            case "Eng_n_writing":
                return "n";
            case "Eng_o_writing":
                return "o";
            case "Eng_p_writing":
                return "p";
            case "Eng_q_writing":
                return "q";
            case "Eng_r_writing":
                return "r";
            case "Eng_s_writing":
                return "s";
            case "Eng_t_writing":
                return "t";
            case "Eng_u_writing":
                return "u";
            case "Eng_v_writing":
                return "v";
            case "Eng_w_writing":
                return "w";
            case "Eng_x_writing":
                return "x";
            case "Eng_y_writing":
                return "y";
            case "Eng_z_writing":
                return "z";
            case "Urdu_alif_writing":
                return "alif";
            case "Urdu_array_writing":
                return "array";
            case "Urdu_bay_writing 1":
                return "bay";
            case "Urdu_chay_writing":
                return "chay";
            case "Urdu_daal_writing":
                return "daal";
            case "Urdu_dhaal_writing":
                return "dhaal";
            case "Urdu_hay_writing":
                return "hay";
            case "Urdu_jeem_writing":
                return "jeem";
            case "Urdu_khay_writing":
                return "khay";
            case "Urdu_pay_writing":
                return "pay";
            case "Urdu_ray_writing":
                return "ray";
            case "Urdu_say_writing":
                return "say";
            case "Urdu_tay_writing":
                return "tay";
            case "Urdu_thay_writing":
                return "thay";
            case "Urdu_zaal_writing":
                return "zaal";
            case "Urdu_zay_writing":
                return "zay";
            case "Urdu_zhay_writing":
                return "zhay";
            case "Math_1_writing":
                return "one";
            case "Math_2_writing":
                return "two";
            case "Math_3_writing":
                return "three";
            case "Math_4_writing":
                return "four";
            case "Math_5_writing":
                return "five";
            case "Math_6_writing":
                return "six";
            case "Math_7_writing":
                return "seven";
            case "Math_8_writing":
                return "eight";
            case "Math_9_writing":
                return "nine";
        }
        return "";
    }

    public String getSubject(String gameName){
        switch (gameName){
            case "Eng_a_writing":
            case "Eng_b_writing":
            case "Eng_c_writing":
            case "Eng_d_writing":
            case "Eng_e_writing":
            case "Eng_f_writing":
            case "Eng_g_writing":
            case "Eng_h_writing":
            case "Eng_i_writing":
            case "Eng_j_writing":
            case "Eng_k_writing":
            case "Eng_l_writing":
            case "Eng_m_writing":
            case "Eng_n_writing":
            case "Eng_o_writing":
            case "Eng_p_writing":
            case "Eng_q_writing":
            case "Eng_r_writing":
            case "Eng_s_writing":
            case "Eng_t_writing":
            case "Eng_u_writing":
            case "Eng_v_writing":
            case "Eng_w_writing":
            case "Eng_x_writing":
            case "Eng_y_writing":
            case "Eng_z_writing":
                return "English";
            case "Urdu_alif_writing":
            case "Urdu_array_writing":
            case "Urdu_bay_writing 1":
            case "Urdu_chay_writing":
            case "Urdu_daal_writing":
            case "Urdu_dhaal_writing":
            case "Urdu_hay_writing":
            case "Urdu_jeem_writing":
            case "Urdu_khay_writing":
            case "Urdu_pay_writing":
            case "Urdu_ray_writing":
            case "Urdu_say_writing":
            case "Urdu_tay_writing":
            case "Urdu_thay_writing":
            case "Urdu_zaal_writing":
            case "Urdu_zay_writing":
            case "Urdu_zhay_writing":
                return "Urdu";
            case "Math_1_writing":
            case "Math_2_writing":
            case "Math_3_writing":
            case "Math_4_writing":
            case "Math_5_writing":
            case "Math_6_writing":
            case "Math_7_writing":
            case "Math_8_writing":
            case "Math_9_writing":
                return "Maths";
        }
        return "";
    }
}
