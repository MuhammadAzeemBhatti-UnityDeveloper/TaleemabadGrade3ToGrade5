/**
package com.orenda.taimo.myapplication;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;



 // A simple {@link Fragment} subclass.

public class ViewpagerScreen2 extends Fragment {

    FrameLayout ll;
    public static ViewpagerScreen2 newInstance() {
        ViewpagerScreen2 fragment = new ViewpagerScreen2();

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_viewpager_screen2, container, false);
        ll=(FrameLayout) view.findViewById(R.id.fragtwo);
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StoryBoardingActivity.viewPager.setCurrentItem(2,true);
            }
        });
        return view;
    }

}
*/