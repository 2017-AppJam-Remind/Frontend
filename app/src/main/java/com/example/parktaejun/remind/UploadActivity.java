package com.example.parktaejun.remind;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
    public static File file;
    String weather;
    Bitmap image_bitmap;

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

        sun = (ImageView) findViewById(R.id.sun);
        cloud = (ImageView) findViewById(R.id.cloud);
        rain = (ImageView) findViewById(R.id.rain);
        snow = (ImageView) findViewById(R.id.snow);

        upload = (Button) findViewById(R.id.upload);
        cancel = (Button) findViewById(R.id.cancel);

        Intent intent = this.getIntent();
        check = intent.getStringExtra("location");

        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        date.setText(currentDateTimeString);

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
                Toast.makeText(UploadActivity.this, "add after", Toast.LENGTH_SHORT).show();
            }

        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveBitmaptoJpeg(image_bitmap, "Remind", "remind");

                if (file.exists()) {
                    Toast.makeText(UploadActivity.this, "file exist", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UploadActivity.this, "file doesn't exist", Toast.LENGTH_SHORT).show();
                }

                if (check.equals("one")) {
                    one_upload();
                } else if (check.equals("two")) {
                    two_upload();
                } else if (check.equals("three")) {
                    three_upload();
                } else {
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
        if (resultCode == RESULT_OK) {
            if (requestCode == MY_PERMISSION_REQUEST_STORAGE) {
                mImageUri = data.getData();
                try {
                    //Uri에서 이미지 이름을 얻어온다.
                    //String name_Str = getImageNameToUri(data.getData());

                    //이미지 데이터를 비트맵으로 받아온다.
                    image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());

                    //배치해놓은 ImageView에 set
                    imageButton.setImageBitmap(image_bitmap);

                    //Toast.makeText(getBaseContext(), "name_Str : "+name_Str , Toast.LENGTH_SHORT).show();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public void addImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, MY_PERMISSION_REQUEST_STORAGE);
    }


    public void one_upload() {
        Call<RemindUp> call;
        call = jsonService.one_upload(name.getText().toString(), weather, memo.getText().toString(), file);
        call.enqueue(new Callback<RemindUp>() {
            @Override
            public void onResponse(Call<RemindUp> call, Response<RemindUp> response) {
                if (response.code() == 200) {
                    Toast.makeText(UploadActivity.this, "SUCCESS", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(UploadActivity.this, " SUCCESS ", Toast.LENGTH_SHORT).show();
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

    public void two_upload() {
        Call<RemindUp> call;
        call = jsonService.two_upload(name.getText().toString(), weather, memo.getText().toString(), file);
        call.enqueue(new Callback<RemindUp>() {
            @Override
            public void onResponse(Call<RemindUp> call, Response<RemindUp> response) {
                if (response.code() == 200) {
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

    public void three_upload() {
        Call<RemindUp> call;
        call = jsonService.three_upload(name.getText().toString(), weather, memo.getText().toString(), file);
        call.enqueue(new Callback<RemindUp>() {
            @Override
            public void onResponse(Call<RemindUp> call, Response<RemindUp> response) {
                if (response.code() == 200) {
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

    public void reqPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            }
        }
    }

    public static void saveBitmaptoJpeg(Bitmap bitmap,String folder, String name){
        String ex_storage =Environment.getExternalStorageDirectory().getAbsolutePath();
        // Get Absolute Path in External Sdcard
        String folder_name = "/"+folder+"/";
        String file_name = name+".jpg";
        String string_path = ex_storage+folder_name;

        try{
            file = new File(string_path);
            if(!file.isDirectory()){
                file.mkdirs();
            }
            FileOutputStream out = new FileOutputStream(string_path+file_name);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.close();

        }catch(FileNotFoundException exception){
            Log.e("FileNotFoundException", exception.getMessage());
        }catch(IOException exception){
            Log.e("IOException", exception.getMessage());
        }
    }
}