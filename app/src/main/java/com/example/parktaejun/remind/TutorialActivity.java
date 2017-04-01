package com.example.parktaejun.remind;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.parktaejun.remind.Adapter.TutorialAdapter;
import com.example.parktaejun.remind.Font.Font;

public class TutorialActivity extends AppCompatActivity {

    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        Font.setGlobalFont(this, getWindow().getDecorView());

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        TutorialAdapter tutorialAdapter = new TutorialAdapter(getSupportFragmentManager(), 4);
        viewPager.setAdapter(tutorialAdapter);
    }
}
