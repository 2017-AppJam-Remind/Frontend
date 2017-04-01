package com.example.parktaejun.remind;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.parktaejun.remind.Font.Font;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Date;

public class InfoActivity extends AppCompatActivity {

    ImageView imageView;
    TextView date;
    TextView name;
    TextView weather;
    TextView memo;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Font.setGlobalFont(this, getWindow().getDecorView());

        imageView = (ImageView)findViewById(R.id.image);
        date = (TextView)findViewById(R.id.date);
        name = (TextView)findViewById(R.id.name);
        weather = (TextView)findViewById(R.id.weather);
        memo = (TextView)findViewById(R.id.memo);
        button = (Button)findViewById(R.id.button);

        Intent intent = this.getIntent();

        Picasso.with(this)
                .load(intent.getStringExtra("image"))
                .into(imageView);

        date.setText(intent.getStringExtra("date"));
        name.setText(intent.getStringExtra("name"));
        weather.setText(intent.getStringExtra("weather"));
        memo.setText(intent.getStringExtra("memo"));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
