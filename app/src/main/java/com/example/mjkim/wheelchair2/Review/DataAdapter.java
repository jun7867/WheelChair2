package com.example.mjkim.wheelchair2.Review;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mjkim.wheelchair2.R;

import java.util.ArrayList;

public class DataAdapter extends BaseAdapter {
    ArrayList<String> data;
    Context context;
    DataAdapter(ArrayList<String> data, Context context){
        this.data=data;
        this.context=context;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            view=View.inflate(context,R.layout.data,null); // view가 없을때 만듬.
        }
        TextView e1=(TextView) view.findViewById(R.id.textView);
        TextView e2=(TextView) view.findViewById(R.id.textView2);

        String one=data.get(i);
//        e1.setText(one.getTitle());
//        e2.setText(one.getContext());

        return view;
    }

    }
