package com.example.parktaejun.remind;

import android.content.Intent;
import android.os.AsyncTask;
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

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.example.parktaejun.remind.Adapter.MainListAdapter;
import com.example.parktaejun.remind.Datas.Data;
import com.example.parktaejun.remind.Font.Font;
import com.example.parktaejun.remind.Server.JSONService;
import com.example.parktaejun.remind.Server.Remind;
import com.example.parktaejun.remind.Tutorial.Tutorial_4_Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.parktaejun.remind.Tutorial.Tutorial_4_Fragment.bt;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    MainListAdapter mainListAdapter;
    List<Data> items = new ArrayList<>();
    ListView listview;
    String receive;
    FloatingActionButton fab;
    Retrofit retrofit;
    SwipeRefreshLayout mBaseLayout;
    JSONService jsonService;
    AQuery aq = new AQuery(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Font.setGlobalFont(this, getWindow().getDecorView());

        Toast.makeText(this, "데이터를 받아옵니다", Toast.LENGTH_SHORT).show();

        listview = (ListView)findViewById(R.id.listview);
        mainListAdapter = new MainListAdapter(this, items);
        listview.setAdapter(mainListAdapter);

        Intent intent = this.getIntent();
        receive = intent.getStringExtra("message");
        Toast.makeText(this, "message : "+ jsonService + receive, Toast.LENGTH_SHORT).show();

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
                loadList();
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
                bt.send(weather, true);
                startActivity(infoIntent);
            }
        });
    }


//    public void loadRemind(JSONService jsonService){
//        new AsynTask().execute(receive);
//        Call<JSONArray> call = jsonService.download(receive);
//        try {
//            Log.d("dudco", call.execute().body().toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        call.enqueue(new Callback<JSONArray>() {
//            @Override
//            public void onResponse(Call<JSONArray> call, Response<JSONArray> response) {
//                if (response.code() == 200) {
//                    Log.d("dudco", response.body().toString());
////                    List<Remind> reminds = (List<Remind>) response.body();
////                    for (Remind remind : reminds) {
////                        Log.d("remind", remind.image + "," + remind.date + "," + remind.name + "," + remind.weather + "," + remind.memo);
////                        initList(remind.image, remind.date, remind.name, remind.weather, remind.memo);
////                    }
//                }else{
//                    Toast.makeText(MainActivity.this, "ERR : " + response.code(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<JSONArray> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), "main : " + receive + "번 알수없는 에러에요..", Toast.LENGTH_SHORT).show();
//                t.printStackTrace();
//            }
//        });
//    }

//    class AsynTask extends AsyncTask<String, Void, Void>{
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            Log.d("dudco", "start excute");
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            Log.d("dudco", "end excute");
//        }
//
//        @Override
//        protected Void doInBackground(String... params) {
//           jsonService.download(params[0]).enqueue(new Callback<JSONArray>() {
//               @Override
//               public void onResponse(Call<JSONArray> call, Response<JSONArray> response) {
//                    Log.d("dudco", response.body().toString());
//               }
//
//               @Override
//               public void onFailure(Call<JSONArray> call, Throwable t) {
//                   Log.d("dudco", "fail");
//                   t.printStackTrace();
//               }
//           });
//            return null;
//        }
//    }

    public void loadList(){
        aq.ajax("http://soylatte.kr:3000/get", JSONArray.class, new AjaxCallback<JSONArray>(){
            @Override
            public void callback(String url, JSONArray object, AjaxStatus status) {
                super.callback(url, object, status);
                Log.d("dudco", object.toString());
                try {
                    for(int i = 0; i < object.length(); i++){
                        JSONObject jsons = (JSONObject) object.get(i);
                        initList(jsons.get("name").toString(), jsons.get("imageName").toString(), jsons.get("time").toString(), jsons.get("weather").toString(), jsons.get("memo").toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void initList(String name, String image, String date, String weather, String memo){
        mainListAdapter.add(new Data( name,  image,  date,  weather,  memo));
        mainListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        mainListAdapter.clear();
        loadList();
        mBaseLayout.setRefreshing(false);
    }
}