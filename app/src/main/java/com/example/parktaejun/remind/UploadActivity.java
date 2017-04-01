package com.example.parktaejun.remind;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parktaejun.remind.Font.Font;
import com.example.parktaejun.remind.Server.JSONService;
import com.example.parktaejun.remind.Server.Remind;
import com.example.parktaejun.remind.Server.RemindUp;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UploadActivity extends AppCompatActivity {

    private static final int MY_PERMISSION_REQUEST_STORAGE = 201;
    ImageButton imageButton;
    TextView date;
    EditText name;
    ImageView sun;
    ImageView cloud;
    ImageView rain;
    ImageView snow;
    EditText memo;
    Button upload;
    Button cancel;
    Uri mImageUri;
    Retrofit retrofit;
    JSONService jsonService;
    String check;
    File file;

    String weather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        Font.setGlobalFont(this, getWindow().getDecorView());

        reqPermission();

        imageButton = (ImageButton) findViewById(R.id.image);
        date = (TextView) findViewById(R.id.date);
        name = (EditText) findViewById(R.id.name);
        memo = (EditText) findViewById(R.id.memo);

        sun = (ImageView)findViewById(R.id.sun);
        cloud = (ImageView)findViewById(R.id.cloud);
        rain = (ImageView)findViewById(R.id.rain);
        snow = (ImageView)findViewById(R.id.snow);

        upload = (Button) findViewById(R.id.upload);
        cancel = (Button) findViewById(R.id.cancel);

        Intent intent = this.getIntent();
        check = intent.getStringExtra("location");

        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        date.setText(currentDateTimeString);

        sun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sun.setImageResource(R.drawable.clicked_sun);
                cloud.setImageResource(R.drawable.unclicked_cloud);
                rain.setImageResource(R.drawable.unclicked_rain);
                snow.setImageResource(R.drawable.unclicked_snow);
                weather = "1";
            }
        });

        cloud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sun.setImageResource(R.drawable.unclicked_sun);
                cloud.setImageResource(R.drawable.clicked_cloud);
                rain.setImageResource(R.drawable.unclicked_rain);
                snow.setImageResource(R.drawable.unclicked_snow);
                weather = "2";
            }
        });

        rain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sun.setImageResource(R.drawable.unclicked_sun);
                cloud.setImageResource(R.drawable.unclicked_cloud);
                rain.setImageResource(R.drawable.clicked_rain);
                snow.setImageResource(R.drawable.unclicked_snow);
                weather = "3";
            }
        });

        snow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sun.setImageResource(R.drawable.unclicked_sun);
                cloud.setImageResource(R.drawable.unclicked_cloud);
                rain.setImageResource(R.drawable.unclicked_rain);
                snow.setImageResource(R.drawable.clicked_snow);
                weather = "4";
            }
        });

        retrofit = new Retrofit.Builder().baseUrl("http://soylatte.kr:3000").addConverterFactory(GsonConverterFactory.create()).build();
        jsonService = retrofit.create(JSONService.class);


        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addImage();
                file = getNewFile(v.getContext(), "remind");
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check.equals("1")){
                    one_upload();
                }else if(check.equals("2")){
                    two_upload();
                }else if(check.equals("3")){
                    three_upload();
                }else{
                    Toast.makeText(UploadActivity.this, "ERR ...", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == MY_PERMISSION_REQUEST_STORAGE){
                mImageUri = data.getData();
            }
        }
    }

    public void addImage(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, MY_PERMISSION_REQUEST_STORAGE);
    }

    public static String getFolderName(String name) {
        File mediaStorageDir =
                new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                        name);

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return "";
            }
        }
        return mediaStorageDir.getAbsolutePath();
    }

    public static File getNewFile(Context context, String folderName) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.KOREA);

        String timeStamp = simpleDateFormat.format(new Date());

        String path;
        if (isSDAvailable()) {
            path = getFolderName(folderName) + File.separator + timeStamp + ".jpg";
        } else {
            path = context.getFilesDir().getPath() + File.separator + timeStamp + ".jpg";
        }

        if (TextUtils.isEmpty(path)) {
            return null;
        }

        return new File(path);
    }

    public void one_upload(){
        Call<RemindUp> call;
        call = jsonService.one_upload(name.getText().toString(), weather, memo.getText().toString(), file);
        call.enqueue(new Callback<RemindUp>() {
            @Override
            public void onResponse(Call<RemindUp> call, Response<RemindUp> response) {
                if(response.code() == 200){
                    Toast.makeText(UploadActivity.this, "SUCCESS", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<RemindUp> call, Throwable t) {
                Toast.makeText(UploadActivity.this, "FUCK", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    public void two_upload(){
        Call<RemindUp> call;
        call = jsonService.two_upload(name.getText().toString(), weather, memo.getText().toString(), file);
        call.enqueue(new Callback<RemindUp>() {
            @Override
            public void onResponse(Call<RemindUp> call, Response<RemindUp> response) {
                if(response.code() == 200){
                    Toast.makeText(UploadActivity.this, "SUCCESS", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<RemindUp> call, Throwable t) {
                Toast.makeText(UploadActivity.this, "FUCK", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    public void three_upload(){
        Call<RemindUp> call;
        call = jsonService.three_upload(name.getText().toString(), weather, memo.getText().toString(), file);
        call.enqueue(new Callback<RemindUp>() {
            @Override
            public void onResponse(Call<RemindUp> call, Response<RemindUp> response) {
                if(response.code() == 200){
                    Toast.makeText(UploadActivity.this, "SUCCESS", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<RemindUp> call, Throwable t) {
                Toast.makeText(UploadActivity.this, "FUCK", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private static boolean isSDAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public void reqPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST_STORAGE);
            }
        }
    }
}
