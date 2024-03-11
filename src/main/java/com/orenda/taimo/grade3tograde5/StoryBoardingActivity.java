package com.orenda.taimo.grade3tograde5;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.VideoView;

import firebase.analytics.AppAnalytics;


public class StoryBoardingActivity extends AppCompatActivity implements View.OnClickListener {
  // public static ViewPager viewPager;
  //LinearLayout linearLayout;
  //FragmentPagerAdapter fragmentPagerAdapter;
  VideoView StoryBoardingVideoView;
  ImageButton skipBtn;
  int videoPauseTime;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_story_boarding);
    fullScreen();
    StoryBoardingVideoView = findViewById(R.id.story_boarding_videoview);
    skipBtn = findViewById(R.id.skipBtn);
    skipBtn.setOnClickListener(this);
    SetVideoPlayer();

    //linearLayout=findViewById(R.id.activity_story_boarding);
    //viewPager=(ViewPager) findViewById(R.id.vpPager);
    // fragmentPagerAdapter=new MyPagerAdapter(getSupportFragmentManager());
    // viewPager.setAdapter(fragmentPagerAdapter);


     /*linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("pagerPage",""+viewPager.getCurrentItem());
                Toast.makeText(getApplicationContext(),"click"+viewPager.getCurrentItem(),Toast.LENGTH_SHORT).show();
            }
        });*/
  }

  /*
  public static class MyPagerAdapter extends FragmentPagerAdapter {
      private static int NUM_ITEMS = 3;int currentNum;
      public MyPagerAdapter(FragmentManager fm)
      {
          super(fm);
      }

      @Override
      public Fragment getItem(int position) {
          Log.d("position",""+position);
          switch (position) {
              case 0:
                  return  ViewpagerScreen1.newInstance();
              case 1:
                  return  ViewpagerScreen2.newInstance();
              case 2:
                  return  ViewpagerScreen3.newInstance();
              default:
                  return null;
          }
      }

      @Override
      public int getCount() {
          return NUM_ITEMS;
      }
  }
  */
  public void SetVideoPlayer(){
    String path = "android.resource://" + getPackageName() + "/" + R.raw.storyboadingvideo;
    StoryBoardingVideoView.setVideoURI(Uri.parse(path));
    StoryBoardingVideoView.start();
    StoryBoardingVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
      @Override
      public void onCompletion(MediaPlayer mediaPlayer) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("firstLogin",false);
        editor.apply();
        Intent intent=new Intent(StoryBoardingActivity.this,UnityPlayerActivity.class);
        startActivity(intent);
        finish();
      }
    });
  }
  public void fullScreen(){
    final View decorView = getWindow().getDecorView();
    decorView.setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                    | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

    decorView.setOnSystemUiVisibilityChangeListener
            (new View.OnSystemUiVisibilityChangeListener() {
              @Override
              public void onSystemUiVisibilityChange(int visibility) {
                // Note that system bars will only be "visible" if none of the
                // LOW_PROFILE, HIDE_NAVIGATION, or FULLSCREEN flags are set.
                if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                  // TODO: The system bars are visible. Make any desired
//                                int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
//
//                            int a=SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
//                                decorView.setSystemUiVisibility(a);
                  decorView.setSystemUiVisibility(
                          View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                  | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                  | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                  | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                                  | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                                  | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

                           /* Handler mHandler=null;
                            Message msg = mHandler.obtainMessage(decorView.setSystemUiVisibility(uiOptions)); //Implement your hide functionality accordingly
                            mHandler.sendMessageDelayed(msg, 3000);
*/


                } else {
                  // TODO: The system bars are NOT visible. Make any desired

                }
              }
            });
  }

  @Override
  public void onPause() {
    super.onPause();
    StoryBoardingVideoView.pause();
    videoPauseTime=StoryBoardingVideoView.getCurrentPosition();
  }

  @Override
  public void onResume(){
    super.onResume();
    if(StoryBoardingVideoView!=null && !StoryBoardingVideoView.isPlaying()){
      StoryBoardingVideoView.seekTo(videoPauseTime);
      StoryBoardingVideoView.start();
    }
  }

  @Override
  public void onClick (View view) {
    switch (view.getId()) {
      case R.id.skipBtn: {
        StoryBoardingVideoView.pause();
        int currentDuration = (StoryBoardingVideoView!=null)?StoryBoardingVideoView.getCurrentPosition():0;
        new AppAnalytics(getApplicationContext()).SkipOnboardingVideo(currentDuration);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("firstLogin",false);
        editor.apply();
        Intent intent=new Intent(StoryBoardingActivity.this,UnityPlayerActivity.class);
        startActivity(intent);
        finish();
        break;
      }
    }
  }
}



