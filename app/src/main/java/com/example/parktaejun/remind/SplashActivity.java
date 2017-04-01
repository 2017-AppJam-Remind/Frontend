package com.example.parktaejun.remind;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.parktaejun.remind.Font.Font;
import com.example.parktaejun.remind.Tutorial.Tutorial_1_Fragment;
import com.example.parktaejun.remind.Tutorial.Tutorial_4_Fragment;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

public class SplashActivity extends Activity {

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        Tutorial_4_Fragment tutorial_4_fragment = new Tutorial_4_Fragment();
        final int check = tutorial_4_fragment.tut_check;

        Font.setGlobalFont(this, getWindow().getDecorView());
        //폰트 설정

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //전체화면 만들기

        setContentView(R.layout.activity_splash);



        Handler hd = new Handler();
        hd.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(check == 1){
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                }
                else{
                    intent = new Intent(SplashActivity.this, TutorialActivity.class);
                }
                startActivity(intent);
                finish();       // 2 초후 이미지를 닫아버림
            }
        }, 2000);

    }

}
