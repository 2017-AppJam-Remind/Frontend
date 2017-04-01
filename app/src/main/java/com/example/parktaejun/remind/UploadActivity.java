package com.example.parktaejun.remind;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.parktaejun.remind.Font.Font;

public class UploadActivity extends AppCompatActivity {

    ImageButton imageButton;
    TextView date;
    EditText name;
    EditText weather;
    EditText memo;
    Button upload;
    Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        Font.setGlobalFont(this, getWindow().getDecorView());

        imageButton = (ImageButton) findViewById(R.id.image);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
