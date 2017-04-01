package com.example.parktaejun.remind;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.parktaejun.remind.Font.Font;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Font.setGlobalFont(this, getWindow().getDecorView());


    }
}
