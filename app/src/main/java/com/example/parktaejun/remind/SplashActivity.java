package com.example.parktaejun.remind;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.parktaejun.remind.Font.Font;

import java.util.ArrayList;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        Font.setGlobalFont(this, getWindow().getDecorView());
        //폰트 설정

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //전체화면 만들기

        setContentView(R.layout.activity_splash);

        reqPermission();

        Handler hd = new Handler();
        hd.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, TutorialActivity.class);
                startActivity(intent);
                finish();       // 2 초후 이미지를 닫아버림
            }
        }, 2000);
    }

    private static final int MY_PERMISSION_REQUEST_STORAGE = 201;

    public void reqPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission(Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[] {Manifest.permission.BLUETOOTH_ADMIN, Manifest.permission.INTERNET,Manifest.permission.BLUETOOTH}, 100);
            }
        }
    }


}
