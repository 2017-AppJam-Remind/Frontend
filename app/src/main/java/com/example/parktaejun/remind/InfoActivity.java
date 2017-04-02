package com.example.parktaejun.remind;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parktaejun.remind.Font.Font;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Date;

public class InfoActivity extends AppCompatActivity {

    ImageView imageView;
    TextView date;
    TextView name;
    TextView memo;
    ImageView sun;
    ImageView cloud;
    ImageView rain;
    ImageView snow;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Font.setGlobalFont(this, getWindow().getDecorView());

        imageView = (ImageView)findViewById(R.id.image);
        date = (TextView)findViewById(R.id.date);
        name = (TextView)findViewById(R.id.name);
        memo = (TextView)findViewById(R.id.memo);

        sun = (ImageView)findViewById(R.id.sun);
        cloud = (ImageView)findViewById(R.id.cloud);
        rain = (ImageView)findViewById(R.id.rain);
        snow = (ImageView)findViewById(R.id.snow);

        button = (Button)findViewById(R.id.back);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        View toolbar_view = LayoutInflater.from(this).inflate(R.layout.toolbar, null);
        final ImageView title = (ImageView) toolbar_view.findViewById(R.id.toolbar_title);
        title.setImageResource(R.drawable.logo);
        getSupportActionBar().setCustomView(toolbar_view);

        toolbar_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "click");
                finish();
            }
        });


        Intent intent = this.getIntent();

        Picasso.with(this)
                .load(intent.getStringExtra("image"))
                .into(imageView);

        date.setText(intent.getStringExtra("date"));
        name.setText(intent.getStringExtra("name"));
        memo.setText(intent.getStringExtra("memo"));

        String weather = intent.getStringExtra("weather");

        if(weather.equals("1")){
            sun.setImageResource(R.drawable.clicked_sun);
            cloud.setImageResource(R.drawable.unclicked_cloud);
            rain.setImageResource(R.drawable.unclicked_rain);
            snow.setImageResource(R.drawable.unclicked_snow);
        }else if(weather.equals("2")){
            sun.setImageResource(R.drawable.unclicked_sun);
            cloud.setImageResource(R.drawable.clicked_cloud);
            rain.setImageResource(R.drawable.unclicked_rain);
            snow.setImageResource(R.drawable.unclicked_snow);
        }else if(weather.equals("3")){
            sun.setImageResource(R.drawable.unclicked_sun);
            cloud.setImageResource(R.drawable.unclicked_cloud);
            rain.setImageResource(R.drawable.clicked_rain);
            snow.setImageResource(R.drawable.unclicked_snow);
        }else if(weather.equals("4")){
            sun.setImageResource(R.drawable.unclicked_sun);
            cloud.setImageResource(R.drawable.unclicked_cloud);
            rain.setImageResource(R.drawable.unclicked_rain);
            snow.setImageResource(R.drawable.clicked_snow);
        }else{
            Toast.makeText(this, "fucking err ...", Toast.LENGTH_SHORT).show();
            finish();
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
