package com.example.parktaejun.remind.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.parktaejun.remind.Datas.Data;
import com.example.parktaejun.remind.R;

import java.util.List;

/**
 * Created by parktaejun on 2017. 4. 1..
 */

public class MainListAdapter extends BaseAdapter {

    private Context context;
    private List<Data> items;

    public MainListAdapter(Context context, List<Data> items){
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        view = LayoutInflater.from(context).inflate(R.layout.items_main_list, null);
        return view;
    }
}