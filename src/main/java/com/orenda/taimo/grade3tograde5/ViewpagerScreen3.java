/**package com.orenda.taimo.myapplication;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;



  //A simple {@link Fragment} subclass.

public class ViewpagerScreen3 extends Fragment {
    ImageView imageButton;

    public static ViewpagerScreen3 newInstance() {

        ViewpagerScreen3 fragment = new ViewpagerScreen3();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_viewpager_screen3, container, false);
        imageButton=(ImageView) view.findViewById(R.id.imgbtnEnter);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("firstLogin",false);
                editor.apply();
                Intent intent=new Intent(getContext(),SignupActivity.class);
                startActivity(intent);
                getActivity().finish();

            }
        });
        return view;
    }

}
*/