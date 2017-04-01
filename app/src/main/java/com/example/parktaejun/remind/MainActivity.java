package com.example.parktaejun.remind;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.parktaejun.remind.Adapter.MainListAdapter;
import com.example.parktaejun.remind.Datas.Data;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    MainListAdapter mainListAdapter;
    List<Data> items = new ArrayList<>();
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listview = (ListView)findViewById(R.id.listview);
        mainListAdapter = new MainListAdapter(this, items);
        listview.setAdapter(mainListAdapter);

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
    }
}
