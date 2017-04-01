package com.example.parktaejun.remind;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.parktaejun.remind.Adapter.MainListAdapter;
import com.example.parktaejun.remind.Datas.Data;
import com.example.parktaejun.remind.Font.Font;
import com.example.parktaejun.remind.Server.JSONService;
import com.example.parktaejun.remind.Server.Remind;
import com.example.parktaejun.remind.Tutorial.Tutorial_4_Fragment;

import java.util.ArrayList;
import java.util.List;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    MainListAdapter mainListAdapter;
    List<Data> items = new ArrayList<>();
    ListView listview;
    String receive;
    FloatingActionButton fab;
    Retrofit retrofit;
    SwipeRefreshLayout mBaseLayout;
    JSONService jsonService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Font.setGlobalFont(this, getWindow().getDecorView());

        listview = (ListView)findViewById(R.id.listview);
        mainListAdapter = new MainListAdapter(this, items);
        listview.setAdapter(mainListAdapter);

        Intent intent = this.getIntent();
        receive = intent.getStringExtra("message");

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

                fab = (FloatingActionButton)findViewById(R.id.float_btn);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent uploadIntent = new Intent(MainActivity.this, UploadActivity.class);
                        uploadIntent.putExtra("location", receive);
                        startActivity(uploadIntent);
                    }
                });

                mBaseLayout = (SwipeRefreshLayout)findViewById(R.id.mBaseLayout);
                mBaseLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));

                mBaseLayout.setOnRefreshListener(MainActivity.this);

                retrofit = new Retrofit.Builder().baseUrl("http://soylatte.kr:3000").addConverterFactory(GsonConverterFactory.create()).build();
                jsonService = retrofit.create(JSONService.class);


                mBaseLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        mBaseLayout.setRefreshing(true);
                        mainListAdapter.clear();
                        loadRemind(jsonService);
                        mBaseLayout.setRefreshing(false);
                    }
                });

                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String image = items.get(position).getImage();
                        String date = items.get(position).getDate();
                        String name = items.get(position).getName();
                        String weather = items.get(position).getWeather();
                        String memo = items.get(position).getMemo();

                        Intent infoIntent = new Intent(MainActivity.this, InfoActivity.class);
                        infoIntent.putExtra("image", image);
                        infoIntent.putExtra("date", date);
                        infoIntent.putExtra("name", name);
                        infoIntent.putExtra("weather", weather);
                        infoIntent.putExtra("memo", memo);
                        Tutorial_4_Fragment.bt.send(weather, true);
                        startActivity(infoIntent);
                    }
                });
            }


    public void loadRemind(JSONService jsonService){
        Call<Remind> call;
        call = jsonService.download(receive);
        call.enqueue(new Callback<Remind>() {
                @Override
                public void onResponse(Call<Remind> call, Response<Remind> response) {
                    if (response.code() == 200) {
                        List<Remind> reminds = (List<Remind>) response.body();
                        for (Remind remind : reminds) {
                            Log.d("remind", remind.image + "," + remind.date + "," + remind.name + "," + remind.weather + "," + remind.memo);
                            initList(remind.image, remind.date, remind.name, remind.weather, remind.memo);
                        }
                    }
                }
                @Override
                public void onFailure(Call<Remind> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "main : " + receive + "번 알수없는 에러에요..", Toast.LENGTH_SHORT).show();
                }
            });
    }

    public void initList(String image, String date, String name, String weather, String memo){
        mainListAdapter.add(new Data( image,  date,  name,  weather,  memo));
        mainListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        mainListAdapter.clear();
        loadRemind(jsonService);
        mBaseLayout.setRefreshing(false);
    }
}