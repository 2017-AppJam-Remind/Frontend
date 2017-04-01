package com.example.parktaejun.remind.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.parktaejun.remind.Tutorial.Tutorial_1_Fragment;
import com.example.parktaejun.remind.Tutorial.Tutorial_2_Fragment;
import com.example.parktaejun.remind.Tutorial.Tutorial_3_Fragment;
import com.example.parktaejun.remind.Tutorial.Tutorial_4_Fragment;

/**
 * Created by parktaejun on 2017. 4. 1..
 */

public class TutorialAdapter extends FragmentStatePagerAdapter{

    private int tabCount;

    public TutorialAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                Tutorial_1_Fragment tutorial_1_fragment = new Tutorial_1_Fragment();
                return tutorial_1_fragment;
            case 1:
                Tutorial_2_Fragment tutorial_2_fragment = new Tutorial_2_Fragment();
                return tutorial_2_fragment;
            case 2:
                Tutorial_3_Fragment tutorial_3_fragment = new Tutorial_3_Fragment();
                return tutorial_3_fragment;
            case 3:
                Tutorial_4_Fragment tutorial_4_fragment = new Tutorial_4_Fragment();
                return tutorial_4_fragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
