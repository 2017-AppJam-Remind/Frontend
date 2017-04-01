package com.example.parktaejun.remind.Tutorial;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.parktaejun.remind.MainActivity;
import com.example.parktaejun.remind.R;

public class Tutorial_4_Fragment extends Fragment {

    LinearLayout linearLayout;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    public int tut_check;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.activity_tutorial_4_fragment, container, false);
        linearLayout = (LinearLayout)view.findViewById(R.id.activity_tutorial_4_fragment);

        tut_check = getPreference();

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainActivity = new Intent(getActivity(), MainActivity.class);
                tut_check = 1;
                savePreference(0);
                startActivity(mainActivity);
            }
        });
        return view;
    }

    public int getPreference(){
        pref = getActivity().getSharedPreferences("pref", 0);
        return pref.getInt("user_name", 0);
    }

    public void savePreference(int check){
        pref = getActivity().getSharedPreferences("pref", 0);
        editor = pref.edit();
        editor.putInt("user_name", check);
        editor.commit();
    }
}
