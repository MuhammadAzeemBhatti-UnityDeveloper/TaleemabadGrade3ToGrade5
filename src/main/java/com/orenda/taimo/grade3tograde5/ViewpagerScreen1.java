/**
package com.orenda.taimo.myapplication;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;


public class ViewpagerScreen1 extends Fragment {

FrameLayout ll;
    public static ViewpagerScreen1 newInstance() {

        ViewpagerScreen1 fragment = new ViewpagerScreen1();

        return fragment;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_viewpager_screen1, container, false);
        ll=(FrameLayout) view.findViewById(R.id.fragone);
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StoryBoardingActivity.viewPager.setCurrentItem(1,true);
            }
        });
        return view;

    }

}
**/