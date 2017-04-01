package com.example.parktaejun.remind;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.parktaejun.remind.Adapter.Adapter_Tutorial;

public class TutorialActivity extends AppCompatActivity {

    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        Adapter_Tutorial adapter_tutorial = new Adapter_Tutorial(getSupportFragmentManager(), 4);
        viewPager.setAdapter(adapter_tutorial);
    }
}
