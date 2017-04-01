package com.example.parktaejun.remind.Adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.parktaejun.remind.Datas.Data;
import com.example.parktaejun.remind.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by parktaejun on 2017. 4. 1..
 */

public class MainListAdapter extends ArrayAdapter<Data> {

    private LayoutInflater mInflater;

    public MainListAdapter(Context context, List<Data> object){
        // 상위 클래스의 초기화 과정
        // context, 0, 자료구조
        super(context, 0, object);
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        Typeface tf = Typeface.createFromAsset(context.getAssets(), "NotoSansCJKkr-Regular.otf");
//        name.setTypeface(tf);
//        family.setTypeface(tf);
//        phone.setTypeface(tf);
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        View view = null;

        if (v == null) {
            // XML 레이아웃을 직접 읽어서 리스트뷰에 넣음
            view = mInflater.inflate(R.layout.items_main_list, null);
        } else {
            view = v;
        }

        final Data data = this.getItem(position);

        if (data != null) {
            ImageView imageView = (ImageView)view.findViewById(R.id.image);
            TextView nameText = (TextView) view.findViewById(R.id.name);
            TextView dateText = (TextView) view.findViewById(R.id.date);

            String url = data.getImage();
            Picasso.with(getContext())
                    .load(url)
                    .into(imageView);
            nameText.setText(data.getName());
            dateText.setText(data.getDate());
        }
        return view;
    }

}
