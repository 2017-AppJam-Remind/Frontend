package com.example.parktaejun.remind;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.parktaejun.remind.Adapter.MainListAdapter;
import com.example.parktaejun.remind.Datas.Data;
import com.example.parktaejun.remind.Font.Font;
import com.example.parktaejun.remind.Server.JSONService;
import com.example.parktaejun.remind.Server.Remind;

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
    BluetoothSPP bt;
    String receive;
    FloatingActionButton fab;
    Retrofit retrofit;
    SwipeRefreshLayout mBaseLayout;
    int check;
    JSONService jsonService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Font.setGlobalFont(this, getWindow().getDecorView());

        fab = (FloatingActionButton)findViewById(R.id.float_btn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent uploadIntent = new Intent(MainActivity.this, UploadActivity.class);
                startActivity(uploadIntent);
            }
        });

        mBaseLayout = (SwipeRefreshLayout)findViewById(R.id.mBaseLayout);
        mBaseLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));

        listview = (ListView)findViewById(R.id.listview);
        mainListAdapter = new MainListAdapter(this, items);
        listview.setAdapter(mainListAdapter);

        mBaseLayout.setOnRefreshListener(this);

        retrofit = new Retrofit.Builder().baseUrl("http://soylatte.kr:3000").addConverterFactory(GsonConverterFactory.create()).build();
        jsonService = retrofit.create(JSONService.class);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String image = items.get(position).getImage();
                String name = items.get(position).getName();
                String date = items.get(position).getDate();

                Intent infoIntent = new Intent(MainActivity.this, InfoActivity.class);
                infoIntent.putExtra("image", image);
                infoIntent.putExtra("name", name);
                infoIntent.putExtra("date", date);
                startActivity(infoIntent);
            }
        });

        bt = new BluetoothSPP(this);

        if(!bt.isBluetoothAvailable())
        {
            Toast.makeText(getApplicationContext()
                    , "블루투스를 켜주세요"
                    , Toast.LENGTH_SHORT).show();
            finish();
        }

        bt.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener()

        {
            public void onDeviceConnected(String name, String address) {
                Toast.makeText(getApplicationContext()
                        , "연결되었습니다", Toast.LENGTH_SHORT).show();
            }

            public void onDeviceDisconnected() {
                Toast.makeText(getApplicationContext()
                        , "연결이끊겼습니다"
                        , Toast.LENGTH_SHORT).show();
            }

            public void onDeviceConnectionFailed() {
            }
        });

        bt.setAutoConnectionListener(new BluetoothSPP.AutoConnectionListener() {
            public void onNewConnection(String name, String address) {
            }

            public void onAutoConnectionStarted() {
            }
        });
        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
            public void onDataReceived(byte[] data, String message) {
                receive = message;
                check = Integer.parseInt(message);
                mBaseLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        mBaseLayout.setRefreshing(true);
                        mainListAdapter.clear();
                        loadRemind(check, jsonService);
                        mBaseLayout.setRefreshing(false);
                    }
                });
            }
        });
    }

    public void onStart() {
        super.onStart();
        if (!bt.isBluetoothEnabled()) {
            bt.enable();
        } else {
            if (!bt.isServiceAvailable()) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER);
                setup();
            }
        }
    }
    public void setup() {
        bt.autoConnect("main");
    }

    public void loadRemind(int check, JSONService jsonService){
        Call<List<Remind>> call;
        if(check == 1) {
            call = jsonService.one_download();
            call.enqueue(new Callback<List<Remind>>() {
                @Override
                public void onResponse(Call<List<Remind>> call, Response<List<Remind>> response) {
                    if (response.code() == 200) {
                        List<Remind> reminds = response.body();
                        for (Remind remind : reminds) {
                            Log.e("article", remind.image + "," + remind.name + "," + remind.date);
                            initList(remind.image,remind.name, remind.date);
                        }
                    }
                }
                @Override
                public void onFailure(Call<List<Remind>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "1번 알수없는 에러에요..", Toast.LENGTH_SHORT).show();
                }
            });
        }else if(check == 2){
            call = jsonService.two_download();
            call.enqueue(new Callback<List<Remind>>() {
                @Override
                public void onResponse(Call<List<Remind>> call, Response<List<Remind>> response) {
                    if (response.code() == 200) {
                        List<Remind> reminds = response.body();
                        for (Remind remind : reminds) {
                            Log.e("article", remind.image + "," + remind.name + "," + remind.date);
                            initList(remind.image,remind.name, remind.date);
                        }
                    }
                }
                @Override
                public void onFailure(Call<List<Remind>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "1번 알수없는 에러에요..", Toast.LENGTH_SHORT).show();
                }
            });
        }else if(check == 3){
            call = jsonService.three_download();
            call.enqueue(new Callback<List<Remind>>() {
                @Override
                public void onResponse(Call<List<Remind>> call, Response<List<Remind>> response) {
                    if (response.code() == 200) {
                        List<Remind> reminds = response.body();
                        for (Remind remind : reminds) {
                            Log.e("article", remind.image + "," + remind.name + "," + remind.date);
                            initList(remind.image,remind.name, remind.date);
                        }
                    }
                }
                @Override
                public void onFailure(Call<List<Remind>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "1번 알수없는 에러에요..", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void initList(String image, String name, String date){
        mainListAdapter.add(new Data(image, name,date));
        mainListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        mainListAdapter.clear();
        loadRemind(check, jsonService);
        mBaseLayout.setRefreshing(false);
    }
}
