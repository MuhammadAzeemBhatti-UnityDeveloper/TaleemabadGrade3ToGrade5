package firebase.analytics;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

//import com.facebook.appevents.AppEventsLogger;
//import com.google.firebase.analytics.FirebaseAnalytics;

import java.lang.reflect.Parameter;

public class AppAnalytics {
    private Context context;

    public AppAnalytics(Context context) {
        this.context = context;
    }

    public void setCurrentScreenName(final String screenName, final String className, final Activity activity){
       // final FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
             //   mFirebaseAnalytics.setCurrentScreen(activity, screenName,className);
            }
        });

    }
    public void GameEventStart(String ffirebase_screen_class, String slo_type, String game_grade, String game_subject, String game_design_type, int game_version, int game_total_stars, boolean is_free, int game_questions, String question_type, int question_id_1, int question_id_x, String deep_link)
    {
       // FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle params = new Bundle();
        params.putString("ffirebase_screen_class",ffirebase_screen_class);
        params.putString("slo_type", slo_type);
        params.putString("game_grade", game_grade);
        params.putString("game_subject", game_subject);
        params.putString("game_design_type", game_design_type);
        params.putInt("game_version", game_version);
        params.putInt("game_total_stars", game_total_stars);
        params.putBoolean("is_free", is_free);
        params.putInt("game_questions", game_questions);
        params.putString("question_type", question_type);
        params.putInt("question_id_1", question_id_1);
        params.putInt("question_id_x", question_id_x);
        params.putString("deep_link", deep_link);
      //  mFirebaseAnalytics.logEvent("game_event_start", params);
    }
    public void GameEventEnd(String ffirebase_screen_class, int game_total_stars, int user_game_stars)
    {
       // FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle params = new Bundle();
        params.putString("ffirebase_screen_class",ffirebase_screen_class);
        params.putInt("game_total_stars", game_total_stars);
        params.putInt("user_game_stars", user_game_stars);
      //  mFirebaseAnalytics.logEvent("game_event_end", params);
    }
    public void AnswerEvent(int question_id, String question_type, String user_anwer_value, boolean user_anwer_correct)
    {
      //  FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle params = new Bundle();
        params.putInt("question_id", question_id);
        params.putString("question_type", question_type);
        params.putString("user_anwer_value", user_anwer_value);
        params.putBoolean("user_anwer_correct", user_anwer_correct);
       // mFirebaseAnalytics.logEvent("answer_event", params);
    }
    public void VideoEventStart(String ffirebase_screen_class, String ffirebase_screen_id,String video_name, int video_id, String video_grade, String video_format, int video_size, String video_subject, boolean is_free, boolean video_interactive, long video_duration, int video_translation, String user_current_language, boolean download_status, boolean network_status, String network_type, String deep_link, String uid)
    {
      //  FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle params = new Bundle();
        params.putString("ffirebase_screen_class", ffirebase_screen_class);
        params.putString("ffirebase_screen_id", ffirebase_screen_id);
        params.putString("video_name", video_name);
        params.putInt("video_id", video_id);
        params.putString("video_grade", video_grade);
        params.putString("video_format", video_format);
        params.putInt("video_size", video_size);
        params.putString("video_subject", video_subject);
        params.putBoolean("is_free", is_free);
        params.putBoolean("video_interactive", video_interactive);
        params.putLong("video_duration", video_duration);
        params.putInt("video_translation", video_translation);
        params.putString("user_current_language", user_current_language);
        params.putBoolean("download_status", download_status);
        params.putBoolean("network_status", network_status);
        params.putString("network_type", network_type);
        params.putString("deep_link", deep_link);
        params.putString("video_uid", uid);
      //  mFirebaseAnalytics.logEvent("video_event_start", params);
        //AppEventsLogger logger = AppEventsLogger.newLogger(context);
        //logger.logEvent("video_event_start", params);
}
    public void VideoEventEnd(String video_name, int video_id, long video_exit_duration, int play_pause_counter, boolean download_status, String uid, int video_watch_time)
    {
  //      FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle params = new Bundle();
        params.putString("video_name", video_name);
        params.putInt("video_id", video_id);
        params.putLong("video_exit_duration", video_exit_duration);
        params.putInt("play_pause_counter", play_pause_counter);
        params.putBoolean("download_status", download_status);
        params.putString("video_uid", uid);
        params.putInt("video_watch_time",video_watch_time);
      //  mFirebaseAnalytics.logEvent("video_event_end", params);

        //AppEventsLogger logger = AppEventsLogger.newLogger(context);
        //logger.logEvent("video_event_end", params);
    }
    public void PlayPause(String video_name, int video_id, String initial_state, String current_state, long play_pause_instance, boolean network_status, String network_type)
    {
      //  FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle params = new Bundle();
        params.putString("video_name", video_name);
        params.putInt("video_id", video_id);
        params.putString("initial_state", initial_state);
        params.putString("current_state", current_state);
        params.putLong("play_pause_instance", play_pause_instance);
        params.putBoolean("network_status", network_status);
        params.putString("network_type", network_type);
       // mFirebaseAnalytics.logEvent("play_pause", params);
    }
    public void VideoDownloadStart(String download_video_name, int download_video_id, boolean download_status, long download_instance, boolean user_purchase_status, boolean is_free, boolean download_initated, String download_initate_error, int download_size, boolean network_status, String network_type)
    {
     //   FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle params = new Bundle();
        params.putString("download_video_name", download_video_name);
        params.putInt("download_video_id", download_video_id);
        params.putBoolean("download_status", download_status);
        params.putLong("download_instance", download_instance);
        params.putBoolean("user_purchase_status", user_purchase_status);
        params.putBoolean("is_free", is_free);
        params.putBoolean("download_initated", download_initated);
        params.putString("download_initate_error", download_initate_error);
        params.putInt("download_size", download_size);
        params.putBoolean("network_status", network_status);
        params.putString("network_type", network_type);
      //  mFirebaseAnalytics.logEvent("video_download_start", params);
    }
    public void VideoDownloadEnd(String download_video_name, int download_video_id, boolean download_status, long download_size, boolean is_free)
    {
     //   FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle params = new Bundle();
        params.putString("download_video_name", download_video_name);
        params.putInt("download_video_id", download_video_id);
        params.putBoolean("download_status", download_status);
        params.putLong("download_size", download_size);
        params.putBoolean("is_free", is_free);
     //   mFirebaseAnalytics.logEvent("video_download_end", params);
    }
    public void VideoDownloadError(String download_video_name, int download_video_id, String download_error)
    {
      //  FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle params = new Bundle();
        params.putString("download_video_name", download_video_name);
        params.putInt("download_video_id", download_video_id);
        params.putString("download_error", download_error);
     //   mFirebaseAnalytics.logEvent("video_download_error", params);
    }
    public void LanguageButton(String video_name, int video_id, String current_language, String changed_language, long video_change_duration)
    {
     //   FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle params = new Bundle();
        params.putString("video_name", video_name);
        params.putInt("video_id", video_id);
        params.putString("current_language", current_language);
        params.putString("changed_language", changed_language);
        params.putLong("video_change_duration", video_change_duration);
    //    mFirebaseAnalytics.logEvent("language_button", params);
    }
    public void SliderMoved(String video_name, int video_id, long initial_slider_position, long final_slider_position, boolean network_status, String network_type)
    {
      //  FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle params = new Bundle();
        params.putString("video_name", video_name);
        params.putInt("video_id", video_id);
        params.putLong("initial_slider_position", initial_slider_position);
        params.putLong("final_slider_position", final_slider_position);
        params.putBoolean("network_status", network_status);
        params.putString("network_type", network_type);
      //  mFirebaseAnalytics.logEvent("slider_moved", params);
    }
    public void GlobalVolumeChanged(String ffirebase_screen_class, int video_duration, int initial_volume_position, int final_volume_position)
    {
      //  FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle params = new Bundle();
        //params.putString("ffirebase_screen_class", ffirebase_screen_class);
        params.putInt("video_duration", video_duration);
        params.putInt("initial_volume_position", initial_volume_position);
        params.putInt("final_volume_position", final_volume_position);
      //  mFirebaseAnalytics.logEvent("global_volume_changed", params);
    }
    public void GlobalBrightnessChanged(String ffirebase_screen_class, int video_duration, int initial_screen_brightness, int final_screen_brightness)
    {
     //   FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle params = new Bundle();
        params.putString("ffirebase_screen_class", ffirebase_screen_class);
        params.putInt("video_duration", video_duration);
        params.putInt("initial_screen_brightness", initial_screen_brightness);
        params.putInt("final_screen_brightness", final_screen_brightness);
     //   mFirebaseAnalytics.logEvent("global_brightness_changed", params);
    }
//    public void GlobalBatteryCharge(int battery_health)
//    {
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle params = new Bundle();
//        params.putInt("battery_health", battery_health);
//        mFirebaseAnalytics.logEvent("global_battery_charge", params);
//    }
//    public void GlobalTapPosition(String ffirebase_screen_class, int video_duration, float user_tap_position_x, float user_tap_position_y, float user_tap_pressure)
//    {
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle params = new Bundle();
//        params.putString("ffirebase_screen_class", ffirebase_screen_class);
//        params.putInt("video_duration", video_duration);
//        params.putFloat("user_tap_position_x", user_tap_position_x);
//        params.putFloat("user_tap_position_y", user_tap_position_y);
//        params.putFloat("user_tap_pressure", user_tap_pressure);
//        mFirebaseAnalytics.logEvent("global_tap_position", params);
//    }
//    public void GlobalAppToBG(String ffirebase_screen_class, int video_duration)
//    {
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle params = new Bundle();
//        params.putString("ffirebase_screen_class", ffirebase_screen_class);
//        params.putInt("video_duration", video_duration);
//        mFirebaseAnalytics.logEvent("global_app_to_bg", params);
//    }
//    public void GlobalAppToFG(String ffirebase_screen_class)
//    {
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle params = new Bundle();
//        params.putString("ffirebase_screen_class", ffirebase_screen_class);
//        mFirebaseAnalytics.logEvent("global_app_to_fg", params);
//    }
//    public void ParentPortalAccess(String user_purchase_status)
//    {
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle params = new Bundle();
//        params.putString("user_purchase_status", user_purchase_status);
//        mFirebaseAnalytics.logEvent("parent_portal_access", params);
//    }
//    public void ParentPortalInfoEnter(String user_field_1, String user_field_2)
//    {
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle params = new Bundle();
//        params.putString("user_field_1", user_field_1);
//        params.putString("user_field_2", user_field_2);
//        mFirebaseAnalytics.logEvent("parent_portal_info_enter", params);
//    }
//    public void LeaderboardAccessed(String user_leaderboard_pos)
//    {
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle params = new Bundle();
//        params.putString("user_leaderboard_pos", user_leaderboard_pos);
//        mFirebaseAnalytics.logEvent("leaderboard_accessed", params);
//    }
//    public void ParentPortalButtons(String subject_button, int par_portal_eng_stars, int par_portal_urdu_stars, int par_portal_math_stars, int par_portal_tarb_stars)
//    {
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle params = new Bundle();
//        params.putString("subject_button", subject_button);
//        params.putInt("par_portal_eng_stars", par_portal_eng_stars);
//        params.putInt("par_portal_urdu_stars", par_portal_urdu_stars);
//        params.putInt("par_portal_math_stars", par_portal_math_stars);
//        params.putInt("par_portal_tarb_stars", par_portal_tarb_stars);
//        mFirebaseAnalytics.logEvent("parent_portal_buttons", params);
//    }
//    public void AttentionNeeded(String attention_subject, String attention_activity)
//    {
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle params = new Bundle();
//        params.putString("attention_subject", attention_subject);
//        params.putString("attention_activity", attention_activity);
//        mFirebaseAnalytics.logEvent("attention_needed", params);
//    }
//    public void AttentionNeededTap(String attention_activity)
//    {
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle params = new Bundle();
//        params.putString("attention_activity", attention_activity);
//        mFirebaseAnalytics.logEvent("attention_needed_tap", params);
//    }
//    public void BuyPressed(boolean user_purcahse_status, boolean trial_status, int trial_days_left)
//    {
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle params = new Bundle();
//        params.putBoolean("user_purcahse_status", user_purcahse_status);
//        params.putBoolean("trial_status", trial_status);
//        params.putInt("trial_days_left", trial_days_left);
//        mFirebaseAnalytics.logEvent("buy_pressed", params);
//    }
//    public void CustomSessionStart(boolean user_purcahse_status, boolean trial_status, int trial_days_left, int battery_health, boolean charge_status, boolean network_status, String network_type, String background_data, int device_space_left, boolean permission_granted, int initial_screen_brightness, int initial_volume_position)
//    {
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle params = new Bundle();
//        params.putBoolean("user_purcahse_status", user_purcahse_status);
//        params.putBoolean("trial_status", trial_status);
//        params.putInt("trial_days_left", trial_days_left);
//        params.putInt("battery_health", battery_health);
//        params.putBoolean("charge_status", charge_status);
//        params.putBoolean("network_status", network_status);
//        params.putString("network_type", network_type);
//        params.putString("background_data", background_data);
//        params.putInt("device_space_left", device_space_left);
//        params.putBoolean("permission_granted", permission_granted);
//        params.putInt("initial_screen_brightness", initial_screen_brightness);
//        params.putInt("initial_volume_position", initial_volume_position);
//        mFirebaseAnalytics.logEvent("custom_session_start", params);
//    }
//    public void AssetBundleDlSuccess(String asset_bundle_name, int total_asset_bundles, int asset_bundle_id, int asset_bundle_size, int device_space_left, int asset_bundle_dl_order)
//    {
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle params = new Bundle();
//        params.putString("asset_bundle_name", asset_bundle_name);
//        params.putInt("total_asset_bundles", total_asset_bundles);
//        params.putInt("asset_bundle_id", asset_bundle_id);
//        params.putInt("asset_bundle_size", asset_bundle_size);
//        params.putInt("device_space_left", device_space_left);
//        params.putInt("asset_bundle_dl_order", asset_bundle_dl_order);
//        mFirebaseAnalytics.logEvent("asset_bundle_dl_success", params);
//    }
//    public void AssetBundleDlFail(String asset_bundle_name, int asset_bundle_id, int asset_bundle_size, int device_space_left, boolean network_status, String network_type, boolean permissions_granted)
//    {
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle params = new Bundle();
//        params.putString("asset_bundle_name", asset_bundle_name);
//        params.putInt("asset_bundle_id", asset_bundle_id);
//        params.putInt("asset_bundle_size", asset_bundle_size);
//        params.putInt("device_space_left", device_space_left);
//        params.putBoolean("network_status", network_status);
//        params.putString("network_type", network_type);
//        params.putBoolean("permissions_granted", permissions_granted);
//        mFirebaseAnalytics.logEvent("asset_bundle_dl_fail", params);
//    }
//
//    public void scratchcard(boolean trial_status, int trial_days_left){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle params = new Bundle();
//        params.putBoolean("trial_status",trial_status);
//        params.putInt("trial_days_left",trial_days_left);
//        mFirebaseAnalytics.logEvent("scratchcard",params);
//    }
//
//    public void book_package(boolean trial_status, int trial_days_left){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle params = new Bundle();
//        params.putBoolean("trial_status",trial_status);
//        params.putInt("trial_days_left",trial_days_left);
//        mFirebaseAnalytics.logEvent("book_package",params);
//    }
//
//    public void jazz_cash_initiated(String order_page){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle params = new Bundle();
//        params.putString("order_page",order_page);
//        mFirebaseAnalytics.logEvent("jazz_cash_initiated",params);
//    }
//
//    public void jazz_cash_success(String product_order, int order_value){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle params = new Bundle();
//        params.putString("product_order",product_order);
//        params.putInt("order_value",order_value);
//        mFirebaseAnalytics.logEvent("jazz_cash_success",params);
//    }
//
//    public void jazz_cash_failed(String product_order, int order_value, int error_code, String error_message){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle params = new Bundle();
//        params.putString("product_order",product_order);
//        params.putInt("order_value",order_value);
//        params.putInt("error_code",error_code);
//        params.putString("error_message",error_message);
//        mFirebaseAnalytics.logEvent("jazz_cash_failed",params);
//    }
//
//    public void jazz_cash_cancelled(String product_order, int order_value){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle params = new Bundle();
//        params.putString("product_order",product_order);
//        params.putInt("order_value",order_value);
//        mFirebaseAnalytics.logEvent("jazz_cash_cancelled",params);
//    }
//    public void cod_order_initiated(String product_order, int order_value){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle params = new Bundle();
//        params.putString("product_order",product_order);
//        params.putInt("order_value",order_value);
//        mFirebaseAnalytics.logEvent("cod_order_initiated",params);
//    }
//    public void cod_order_successful(String product_order, int order_value){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle params = new Bundle();
//        params.putString("product_order",product_order);
//        params.putInt("order_value",order_value);
//        mFirebaseAnalytics.logEvent("cod_order_successful",params);
//    }
//    public void cod_order_incomplete(String product_order, int order_value){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle params = new Bundle();
//        params.putString("product_order",product_order);
//        params.putInt("order_value",order_value);
//        mFirebaseAnalytics.logEvent("cod_order_incomplete",params);
//    }
//    public void credit_card_initiated(int order_value){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle params = new Bundle();
//        params.putInt("order_value",order_value);
//        mFirebaseAnalytics.logEvent("credit_card_initiated",params);
//    }
//    public void credit_card_incomplete(int order_value){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle params = new Bundle();
//        params.putInt("order_value",order_value);
//        mFirebaseAnalytics.logEvent("credit_card_incomplete",params);
//    }
//    public void call_to_order(String product_order, int order_value){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle params = new Bundle();
//        params.putString("product_order",product_order);
//        params.putInt("order_value",order_value);
//        mFirebaseAnalytics.logEvent("call_to_order",params);
//    }
//    public void validate_scratchcard(boolean is_valid){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle params = new Bundle();
//        params.putBoolean("is_valid",is_valid);
//        mFirebaseAnalytics.logEvent("validate_scratchcard",params);
//    }

    public void GradeButtonClick(String GradeButtonType, String UserType){
      //  FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
      //  mFirebaseAnalytics.setUserProperty("UserType", UserType);
        Bundle params = new Bundle();
        params.putString("ButtonType",GradeButtonType);
      //  mFirebaseAnalytics.logEvent("GradeButtonType",params);
    }

    public void SelectedSubject(String Subject){
      //  FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle params = new Bundle();
        params.putString("Subject",Subject);
      //  mFirebaseAnalytics.logEvent("selected_subject",params);
    }
    public void SelectedGrade(String Grade){
      //  FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle params = new Bundle();
        params.putString("Grade",Grade);
//        mFirebaseAnalytics.logEvent("selected_grade",params);
//        mFirebaseAnalytics.setUserProperty("current_grade_selected",Grade);
    }
     public void SelectedInitialGrade(String Grade){
       // FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle params = new Bundle();
        params.putString("Grade",Grade);
//        mFirebaseAnalytics.logEvent("intital_grade",params);
//        mFirebaseAnalytics.setUserProperty("initial_grade_selected",Grade);
    }
    public void ParentPortal(){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        mFirebaseAnalytics.logEvent("parent_portal_selected",null);

       // AppEventsLogger logger = AppEventsLogger.newLogger(context);
        //logger.logEvent("parent_portal_selected");
    }

    // Test Analytics


    public void setQuestionStart(String subject , String question_id, String question_type, int correct_options, String difficulty, int display_question_number, int json_question_number, String testName)
    {
      //  FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle params = new Bundle();
        params.putString("subject",subject);
        params.putString("question_id",question_id);
        params.putString("question_type",question_type);
        params.putInt("correct_options",correct_options);
        params.putString("difficulty",difficulty);
        params.putInt("display_question_number",display_question_number);
        params.putInt("json_question_number",json_question_number);
        params.putString("test_name",testName);
      //  mFirebaseAnalytics.logEvent("question_start",params);
    }

    public void setAnswer(String subject, String question_id,String test_name,  String question_type, int correct_options, int num_correct, boolean all_correct, int time_spent_on_question)
    {
      //  FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle params = new Bundle();
        params.putString("subject",subject);
        params.putString("question_id",question_id);
        params.putString("test_name",test_name);
        params.putString("question_type",question_type);
        params.putInt("correct_options",correct_options);
        params.putInt("num_correct",num_correct);
        params.putBoolean("all_correct",all_correct);
        params.putInt("time_spent_on_question",time_spent_on_question);
        params.putInt("time_spent_on_question",time_spent_on_question);
      //  mFirebaseAnalytics.logEvent("answer_event",params);
    }

    public void setBigQuestionStart(String subject  , String question_id,String test_name, String question_type, int correct_options, String difficulty, int display_question_number, int json_question_number)
    {
      //  FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle params = new Bundle();
        params.putString("subject",subject);
        params.putString("question_id",question_id);
        params.putString("test_name",test_name);
        params.putString("question_type",question_type);
        params.putInt("correct_options",correct_options);
        params.putString("difficulty",difficulty);
        params.putInt("display_question_number",display_question_number);
        params.putInt("json_question_number",json_question_number);
     //   mFirebaseAnalytics.logEvent("big_question_start",params);
    }



    public void setBigQuestionEvent(int time_spent_on_bigquestion, String question_id,String test_name)
    {
      //  FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle params = new Bundle();
        params.putInt("time_spent_on_bigquestion",time_spent_on_bigquestion);
        params.putString("question_id",question_id);
        params.putString("test_name",test_name);
       // mFirebaseAnalytics.logEvent("continue",params);
    }
    public void setExplanationEvent(String  time_spent_on_explanation, String question_id,String test_name)
    {
      //  FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle params = new Bundle();
        params.putString("time_spent_on_explanation",time_spent_on_explanation);
        params.putString("question_id",question_id);
        params.putString("test_name",test_name);
      //  mFirebaseAnalytics.logEvent("explanation_closed",params);
    }

//    public void setPostTestClicked(String testName)
//    {
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle params = new Bundle();
//
//        params.putString("test_name",testName);
//
//        mFirebaseAnalytics.logEvent("post_test_Clicked",params);
//    }
//    public void setSocraticTestClicked(String testName)
//    {
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle params = new Bundle();
//
//        params.putString("test_name",testName);
//
//        mFirebaseAnalytics.logEvent("socratic_test_Clicked",params);
//    }

    public void setTestStart(int total_questions,String testName)
    {
      //  FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle params = new Bundle();

        params.putInt("total_questions",total_questions);
        params.putString("test_name",testName);


       // mFirebaseAnalytics.logEvent("post_test_start",params);
    }
    public void setSimpleTestStart(int total_questions,String testName, String uid, String grade, String subject)
    {
       // FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle params = new Bundle();

        params.putInt("total_questions",total_questions);
        params.putString("test_name",testName);
        params.putString("test_uid", uid);
        params.putString("test_grade", grade);
        params.putString("test_subject", subject);
      //  mFirebaseAnalytics.logEvent("simple_test_start",params);

        //AppEventsLogger logger = AppEventsLogger.newLogger(context);
        //logger.logEvent("simple_test_start",params);
    }
    public void setSocraticTestStart(int total_questions,String testName)
    {
       // FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle params = new Bundle();

        params.putInt("total_questions",total_questions);
        params.putString("test_name",testName);

      //  mFirebaseAnalytics.logEvent("socratic_test_start",params);
    }

    public void setPostTestEnd(int total_questions, int questions_attempted, int questions_correct, int score,String testName, String end_question, String end_screen)
    {
       // FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle params = new Bundle();

        params.putInt("total_questions",total_questions);
        params.putInt("questions_attempted",questions_attempted);
        params.putInt("questions_correct",questions_correct);
        params.putInt("score",score);
        params.putString("test_name",testName);
        params.putString("end_question",testName);
        params.putString("end_screen",testName);

      //  mFirebaseAnalytics.logEvent("post_test_end",params);
    }



    public void setPostTestCompleted(int total_questions, int questions_attempted, int questions_correct, int score, String testName)
    {
       // FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle params = new Bundle();

        params.putInt("total_questions",total_questions);
        params.putInt("questions_attempted",questions_attempted);
        params.putInt("questions_correct",questions_correct);
        params.putInt("score",score);
        params.putString("test_name",testName);

       // mFirebaseAnalytics.logEvent("post_test_completed",params);
    }

    public void setSimpleTestEnd(int total_questions, int questions_attempted, int questions_correct, int score,String testName, String end_question, String end_screen, String uid, String grade, String subject)
    {
      //  FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle params = new Bundle();

        params.putInt("total_questions",total_questions);
        params.putInt("questions_attempted",questions_attempted);
        params.putInt("questions_correct",questions_correct);
        params.putInt("score",score);
        params.putString("test_name",testName);
        params.putString("end_question",testName);
        params.putString("end_screen",testName);
        params.putString("test_uid", uid);
        params.putString("test_grade", grade);
        params.putString("test_subject", subject);
     //   mFirebaseAnalytics.logEvent("simple_test_end",params);
//        AppEventsLogger logger = AppEventsLogger.newLogger(context);
//        logger.logEvent("simple_test_end",params);
    }

    public void setSimpleTestCompleted(int total_questions, int questions_attempted, int questions_correct, int score, String testName, String uid, String grade, String subject )
    {
      //  FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle params = new Bundle();

        params.putInt("total_questions",total_questions);
        params.putInt("questions_attempted",questions_attempted);
        params.putInt("questions_correct",questions_correct);
        params.putInt("score",score);
        params.putString("test_name",testName);
        params.putString("test_uid", uid);
        params.putString("test_grade", grade);
        params.putString("test_subject", subject);

      //  mFirebaseAnalytics.logEvent("simple_test_completed",params);

//        AppEventsLogger logger = AppEventsLogger.newLogger(context);
//        logger.logEvent("simple_test_completed",params);
    }


    public void setPostTestHomeBack(String subject, String screen)
    {
      //  FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle params = new Bundle();
        params.putString("subject",subject);
        params.putString("screen",screen);


      //  mFirebaseAnalytics.logEvent("post_home_back",params);
    }

    public void setSimpleTestHomeBack(String subject, String screen)
    {
       // FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle params = new Bundle();
        params.putString("subject",subject);
        params.putString("screen",screen);


      //  mFirebaseAnalytics.logEvent("simple_home_back",params);
    }

    public void setOptionSelected(String subject, String question_id, String question_type, String option_value,boolean correct)
    {
      ///  FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle params = new Bundle();
        params.putString("subject",subject);
        params.putString("question_id",question_id);
        params.putString("question_type",question_type);
        params.putString("option_value",option_value);
        params.putBoolean("correct",correct);

      //  mFirebaseAnalytics.logEvent("option_selected",params);
    }
    public void setOptionPlaced(String subject, String question_id, String question_type, String option_value,boolean correct)
    {
      //  FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle params = new Bundle();
        params.putString("subject",subject);
        params.putString("question_id",question_id);
        params.putString("question_type",question_type);
        params.putString("option_value",option_value);
        params.putBoolean("correct",correct);

      //  mFirebaseAnalytics.logEvent("option_placed",params);
    }


    public void setSocraticTestEnd(int total_questions, int questions_attempted, int questions_correct, int score,String testName, String end_question, String end_screen)
    {
       // FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle params = new Bundle();

        params.putInt("total_questions",total_questions);
        params.putInt("questions_attempted",questions_attempted);
        params.putInt("questions_correct",questions_correct);
        params.putInt("score",score);
        params.putString("test_name",testName);
        params.putString("end_question",testName);
        params.putString("end_screen",testName);

      //  mFirebaseAnalytics.logEvent("socratic_test_end",params);
    }

    public void setSocraticTestCompleted(int total_questions, int questions_attempted, int questions_correct, int score, String testName)
    {
      //  FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle params = new Bundle();

        params.putInt("total_questions",total_questions);
        params.putInt("questions_attempted",questions_attempted);
        params.putInt("questions_correct",questions_correct);
        params.putInt("score",score);
        params.putString("test_name",testName);

      //  mFirebaseAnalytics.logEvent("socratic_test_completed",params);
    }

    public void setSocraticTestHomeBack(String subject, String screen)
    {
       // FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle params = new Bundle();
        params.putString("subject",subject);
        params.putString("screen",screen);
       // mFirebaseAnalytics.logEvent("socratic_home_back",params);
    }

    public void setSocraticQuestionStart(String subject , String question_id, String question_type, int correct_options, String difficulty, int display_question_number, int json_question_number, String testName)
    {
      //  FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle params = new Bundle();
        params.putString("subject",subject);
        params.putString("question_id",question_id);
        params.putString("question_type",question_type);
        params.putInt("correct_options",correct_options);
        params.putString("difficulty",difficulty);
        params.putInt("display_question_number",display_question_number);
        params.putInt("json_question_number",json_question_number);
        params.putString("test_name",testName);
     //   mFirebaseAnalytics.logEvent("socratic_question_start",params);
    }
    public void setSocraticQuestionEnd(String subject , String question_id, String question_type, int correct_options, String difficulty, int display_question_number, int json_question_number, String testName)
    {
     //   FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle params = new Bundle();
        params.putString("subject",subject);
        params.putString("question_id",question_id);
        params.putString("question_type",question_type);
        params.putInt("correct_options",correct_options);
        params.putString("difficulty",difficulty);
        params.putInt("display_question_number",display_question_number);
        params.putInt("json_question_number",json_question_number);
        params.putString("test_name",testName);
     //   mFirebaseAnalytics.logEvent("socratic_question_end",params);
    }

    public void setSocraticVideoStart(String subject , String question_id, String testName)
    {
       // FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle params = new Bundle();
        params.putString("subject",subject);
        params.putString("test_name",testName);
        params.putString("question_id",question_id);

      //  mFirebaseAnalytics.logEvent("socratic_video_start",params);
    }
    public void setSocraticVideoComplete(String subject , String question_id,  String testName, int totalDuration)
    {
      //  FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle params = new Bundle();
        params.putString("subject",subject);
        params.putString("test_name",testName);
        params.putString("question_id",question_id);
        params.putInt("total_duration",totalDuration);

      //  mFirebaseAnalytics.logEvent("socratic_video_complete",params);
    }

    public void setSocraticVideoEnd(String subject , String question_id,  String testName, int totalDuration, int durationWatched)
    {
      //  FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle params = new Bundle();
        params.putString("subject",subject);
        params.putString("test_name",testName);
        params.putString("question_id",question_id);
        params.putInt("total_duration",totalDuration);
        params.putInt("duration_watched",durationWatched);

      //  mFirebaseAnalytics.logEvent("socratic_video_end",params);
    }

    //function to set true or false after subscription initialization
    public void setSimPaisaSubscriptionAnalytics(boolean subscriptionStatus){

     //   FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle params= new Bundle();
        params.putBoolean("subscription_status",subscriptionStatus);
       // mFirebaseAnalytics.logEvent("Simpaisa_subscription_status", params);
    }
    public void setSimPaisaUnsubscribeAnalytics(boolean unsubscribeStatus){
       // FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle params= new Bundle();
        params.putBoolean("unsubscribe_status",unsubscribeStatus);
       // mFirebaseAnalytics.logEvent("Simpaisa_unsubscribe_status", params);
    }

    public void setSaleInitiatedAnalytics(String salesMethod, String bundle){
      //  FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle params= new Bundle();
        params.putString("Sales_Method",salesMethod);
        params.putString("Bundle_Selected", bundle);
      //  mFirebaseAnalytics.logEvent("Sales_Initiated", params);

//        AppEventsLogger logger = AppEventsLogger.newLogger(context);
//        logger.logEvent("Sales_Initiated", params);
    }
    public void setSaleSuccessfulAnalytics(String salesMethod, String purchaseDate, String bundle, String coupon){
        int amountPaid = 0;
        switch (bundle){
            case "kto5":
                amountPaid =2000;
                break;
            case "kto2":
                amountPaid =1200;
                break;
            case "3to5":
                amountPaid =1200;
                break;
            case "lifetime":
                amountPaid =800;
                break;
            case "kto53months":
                amountPaid= 800;
                break;
            case "kto56months":
                amountPaid= 1400;
                break;
            case "kto512months":
                amountPaid= 2400;
                break;
            case "3to53months":
                amountPaid= 500;
                break;
            case "3to56months":
                amountPaid= 800;
                break;
            case "3to512months":
                amountPaid= 1500;
                break;
            case "kto23months":
                amountPaid= 500;
                break;
            case "kto26months":
                amountPaid= 800;
                break;
            case "kto212months":
                amountPaid= 1500;
                break;
        }
       // FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle params= new Bundle();
        params.putString("Sales_Method",salesMethod);
        params.putString("Purchase_Date",purchaseDate);
        params.putString("Bundle_Selected", bundle);
        params.putString("Coupon", coupon);
        params.putInt("Amount_Paid", amountPaid);
       // mFirebaseAnalytics.logEvent("Sales_Successful", params);

//        AppEventsLogger logger = AppEventsLogger.newLogger(context);
//        logger.logEvent("Sales_Successful", params);
        setCustomerUserProperty("true");

        /**
         * Firebase default purchase analytics
         */
        Bundle item1 = new Bundle();
       // item1.putString(FirebaseAnalytics.Param.ITEM_NAME, bundle);

        Bundle params2 = new Bundle();
//        params2.putDouble(FirebaseAnalytics.Param.VALUE, amountPaid);
//        params2.putString(FirebaseAnalytics.Param.CURRENCY, "PKR" );
//        params2.putParcelableArray("items",new Bundle[] {item1});
//        mFirebaseAnalytics.logEvent("purchase", params2);
    }

    public void CouponValidityAnalytics(boolean validityStatus, String coupon){
      //  FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle params= new Bundle();
        params.putBoolean("Validity_Status", validityStatus);
        params.putString("coupon", coupon);
       // mFirebaseAnalytics.logEvent("Coupon_Validity", params);

//        AppEventsLogger logger = AppEventsLogger.newLogger(context);
//        logger.logEvent("Coupon_Validity", params);

    }

    public void setCustomerUserProperty(String status){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        mFirebaseAnalytics.setUserProperty("PaidCustomer", status);
    }

    public void CashOnDeliveryAnalytics(String name, String address, String city, String phoneNumber, String formType, String bundle, String bundleDuration, String PurchaseMethod, boolean booksAddOn, String promoCode, boolean WorkSheetAddOn){
     //   FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle params= new Bundle();
        params.putString("Name", name);
        params.putString("Address", address);
        params.putString("City", city);
        params.putString("Phone_Number", phoneNumber);
        params.putString("Form_Type", formType);
        params.putString("Bundle_Selected", bundle);
        params.putString("Bundle_Duration", bundleDuration);
        params.putString("Purchase_Method", PurchaseMethod);
        params.putBoolean("Book_addon",booksAddOn);
        params.putString("Promo_Code", promoCode);
        params.putBoolean("WorkSheet_AddOn",WorkSheetAddOn);
     //   mFirebaseAnalytics.logEvent("Cash_On_Delivery_Form_Submit", params);
//        AppEventsLogger logger = AppEventsLogger.newLogger(context);
//        logger.logEvent("Cash_On_Delivery_Form_Submit", params);

    }

    public void InternalRatingAnalytics(int selectedRating){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle params= new Bundle();
//        params.putInt("Selected_Rating", selectedRating);
//        mFirebaseAnalytics.logEvent("Internal_Rating", params);
    }

    public void HamburgerMenuButtonClickedAnalytics(){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        mFirebaseAnalytics.logEvent("Hamburger_Menu_Button_Clicked", null);
    }
    public void MusicEnabledAnalytics(){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        mFirebaseAnalytics.logEvent("Music_Enabled", null);
    }
    public void MusicDisabledAnalytics(){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        mFirebaseAnalytics.logEvent("Music_Disabled", null);
    }
    public void SoundEnabledAnalytics(){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        mFirebaseAnalytics.logEvent("Sound_Enabled", null);
    }
    public void SoundDisabledAnalytics(){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        mFirebaseAnalytics.logEvent("Sound_Disabled", null);
    }
    public void SubscriptionButtonClickedAnalytics(){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        mFirebaseAnalytics.logEvent("Subscription_Clicked", null);

//        AppEventsLogger logger = AppEventsLogger.newLogger(context);
//        logger.logEvent("Subscription_Clicked");
    }
    public void TaleemabadSecondaryAppButtonAnalytics(){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        mFirebaseAnalytics.logEvent("Taleemabad_Secondary_Clicked", null);
    }

    public void setTrialDayUserProperty(String days){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        mFirebaseAnalytics.setUserProperty("trial_period", days);
    }

    public void SignupNumberEntered(String phoneNumber){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle params= new Bundle();
//        params.putString("Phone_Number",phoneNumber);
//        mFirebaseAnalytics.logEvent("Number_Entered", params);
    }

    public void SignupOTPEntered(){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        mFirebaseAnalytics.logEvent("OTP_Entered", null);
    }

    public void SignupResendOTPButtonClicked(){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        mFirebaseAnalytics.logEvent("Resend_OTP_Clicked", null);
    }

    public void CustomNotificationRecieved(String notficationID, String notficationTime, String notficationType){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle params= new Bundle();
//        params.putString("Notfication_ID",notficationID);
//        params.putString("Notification_Time",notficationTime);
//        params.putString("Notification_Type",notficationType);
//        mFirebaseAnalytics.logEvent("CustomNotification_Recieved", params);
    }
    public void CustomNotificationOpen(int notficationID, String notficationTime, String notficationType){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle params= new Bundle();
//        params.putInt("Notfication_ID",notficationID);
//        params.putString("Notification_Time",notficationTime);
//        params.putString("Notification_Type",notficationType);
//        mFirebaseAnalytics.logEvent("CustomNotification_Open", params);
    }
    public void CustomNotificationDismissed(String notficationID, String notficationTime, String notficationType){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle params= new Bundle();
//        params.putString("Notfication_ID",notficationID);
//        params.putString("Notification_Time",notficationTime);
//        params.putString("Notification_Type",notficationType);
//        mFirebaseAnalytics.logEvent("CustomNotification_Dismissed", params);
    }

    public void NotSigningInEvent(String reason){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle params= new Bundle();
//        params.putString("Reason",reason);
//        mFirebaseAnalytics.logEvent("NotSigningIn", params);
    }

    public void ShowReferralPopupAnalytics( ){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        mFirebaseAnalytics.logEvent("Referral_Button_Clicked", null);
    }
    public void HideReferralPopupAnalytics( ){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        mFirebaseAnalytics.logEvent("Referral_Close_Clicked", null);
    }
    public void ShareReferralPopupAnalytics( ){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        mFirebaseAnalytics.logEvent("Referral_Share_Clicked", null);
    }
    public void UserDownloadSourceAnalytics(String source){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle params= new Bundle();
//        params.putString("Source",source);
//        mFirebaseAnalytics.logEvent("User_Download_Source", params);
    }
    public void GooglePaymentUserCancelled(String source){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle params= new Bundle();
//        params.putString("Bundle",source);
//        mFirebaseAnalytics.logEvent("GooglePayment_UserCancelled", params);
    }
    public void GooglePaymentError(String source){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle params= new Bundle();
//        params.putString("Bundle",source);
//        mFirebaseAnalytics.logEvent("GooglePayment_Error", params);
    }

    public void DailyTrialEnabled(String status){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle params= new Bundle();
//        params.putString("Status",status);
//        mFirebaseAnalytics.logEvent("Daily_Trial_Enabled", params);
    }
    public void user_location(double longitude, double latitude, String locationType){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle params= new Bundle();
//        params.putDouble("longitude",longitude);
//        params.putDouble("latitude",latitude);
//        params.putString("LocationType",locationType);
//        mFirebaseAnalytics.logEvent("user_location", params);
    }

    public void SkipOnboardingVideo(int videoPosition){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle params= new Bundle();
//        params.putInt("videoPosition", videoPosition);
//        mFirebaseAnalytics.logEvent("Skip_Onboarding_Video", params);
    }

    public void VideoStreamIssue(String message){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle params= new Bundle();
//        params.putString("reason",message);
//        mFirebaseAnalytics.logEvent("Video_issue_report", params);
    }

    public void TrialExpired(){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        mFirebaseAnalytics.logEvent("Trial_Expired", null);
    }

    public void ParentPortalDetails(String age, String schoolName) {
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle params= new Bundle();
//        params.putString("Age", age);
//        params.putString("SchoolName", schoolName);
//        mFirebaseAnalytics.logEvent("Parent_Portal_Details", params);
    }

    public void FCMToken(String token){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle params= new Bundle();
//        params.putString("Token", token);
//        mFirebaseAnalytics.logEvent("FCM_Token", params);
    }

    public void EasypaisaOTCSuccessful(){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        mFirebaseAnalytics.logEvent("easypaisa_OTC_successful", null);
    }

    public void EasypaisaPaymentDeclined(){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        mFirebaseAnalytics.logEvent("easypaisa_payment_declined", null);
    }

    public void VideoButtonPressed(String video_name, String video_grade, String video_subject){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle params= new Bundle();
//        params.putString("video_name", video_name);
//        params.putString("video_grade", video_grade);
//        params.putString("video_subject", video_subject);
//        params.putString("video_uid", video_grade+video_subject+video_name);
//        mFirebaseAnalytics.logEvent("video_button_pressed", params);
    }

    public void BuyBooksButtonClicked(){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        mFirebaseAnalytics.logEvent("Books_Button_Clicked", null);
    }
    public void Nursery_Session_Expired(){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        mFirebaseAnalytics.logEvent("Nursery_Session_Expired", null);
    }
    public void Grade1_Session_Expired(){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        mFirebaseAnalytics.logEvent("Grade1_Session_Expired", null);
    }
    public void Grade2_Session_Expired(){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        mFirebaseAnalytics.logEvent("Grade2_Session_Expired", null);
    }
    public void Grade3_Session_Expired(){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        mFirebaseAnalytics.logEvent("Grade3_Session_Expired", null);
    }
    public void Grade4_Session_Expired(){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        mFirebaseAnalytics.logEvent("Grade4_Session_Expired", null);
    }
    public void Grade5_Session_Expired(){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        mFirebaseAnalytics.logEvent("Grade5_Session_Expired", null);
    }

//    public void increasing_video_experiment_clicked(String Grade){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle params= new Bundle();
//        params.putString("grade", Grade);
//        mFirebaseAnalytics.logEvent("increasing_video_experiment_clicked", params);
//    }
//
//    public void increasing_video_experiment_dismissed(String Grade){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle params= new Bundle();
//        params.putString("grade", Grade);
//        mFirebaseAnalytics.logEvent("increasing_video_experiment_dismissed", params);
//    }

    public void LiveTuitionMenuClick(){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        mFirebaseAnalytics.logEvent("live_tuition_click", null);
    }

    public void LiveTuitionOrderNow(){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        mFirebaseAnalytics.logEvent("live_tuition_ordernow", null);
    }


    public void VideoComplete(String videoName, String uid, String grade, String subject,int videoWatchTime ){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle params= new Bundle();
//        params.putString("video_name", videoName);
//        params.putString("video_uid", uid);
//        params.putString("video_grade", grade);
//        params.putString("video_subject", subject);
//        params.putInt("video_watch_time", videoWatchTime);
//        mFirebaseAnalytics.logEvent("video_complete", params);
    }

    public void VideoShared(String grade, String subject,String videoName,String videoUid ,String userUid){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle params= new Bundle();
//        params.putString("grade", grade);
//        params.putString("subject", subject);
//        params.putString("video_name", videoName);
//        params.putString("video_uid", videoUid);
//        params.putString("user_uid", userUid);
//        mFirebaseAnalytics.logEvent("video_shared", params);
    }

    public void VideoReceived(String grade, String subject,String videoName,String videoUid ,String userUid){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle params= new Bundle();
//        params.putString("grade", grade);
//        params.putString("subject", subject);
//        params.putString("video_name", videoName);
//        params.putString("video_uid", videoUid);
//        params.putString("user_uid", userUid);
//        mFirebaseAnalytics.logEvent("video_received", params);
    }

//    public void OpenInterestialAd(){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        mFirebaseAnalytics.logEvent("interestial_ad_open", null);
//    }
//
//    public void CloseInterestialAd(){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        mFirebaseAnalytics.logEvent("interestial_ad_close", null);
//    }
}
