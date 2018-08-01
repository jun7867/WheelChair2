package com.example.mjkim.wheelchair2.NaverSearch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mjkim.wheelchair2.R;
import com.example.mjkim.wheelchair2.ReviewDetail;
import com.example.mjkim.wheelchair2.ReviewSearch;

import java.util.ArrayList;

public class NaverLocationAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private Activity m_activity;
    private ArrayList<NaverLocationList> arr;
    public NaverLocationAdapter(Activity act, ArrayList<NaverLocationList> arr_item) {
        this.m_activity = act;
        arr = arr_item;
        mInflater = (LayoutInflater)m_activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() { return arr.size(); }

    @Override
    public Object getItem(int position) { return arr.get(position); }

    public long getItemId(int position){ return position; }

    @Override

    public View getView(final int position, View convertView, ViewGroup parent) {

        if(convertView == null){

            int res = 0;

            res = R.layout.naver_location_list;

            convertView = mInflater.inflate(res, parent, false);

        }


        TextView title = (TextView)convertView.findViewById(R.id.vi_name);

        TextView address = (TextView)convertView.findViewById(R.id.vi_address);

        TextView telephone = (TextView)convertView.findViewById(R.id.vi_telephone);

        LinearLayout layout_view =  (LinearLayout)convertView.findViewById(R.id.vi_view);

        //int resId=  m_activity.getResources().getIdentifier(arr.get(position)., "drawable", m_activity.getPackageName());

        //imView.setBackgroundResource(resId);

        title.setText(arr.get(position).getName());

        address.setText(arr.get(position).getAddress());

        telephone.setText(arr.get(position).getTelephone());

        /*  버튼에 이벤트처리를 하기위해선 setTag를 이용해서 사용할 수 있습니다.

         *   Button btn 가 있다면, btn.setTag(position)을 활용해서 각 버튼들

         *   이벤트처리를 할 수 있습니다.

         */

        layout_view.setOnClickListener(new View.OnClickListener(){

           public void onClick(View v){

               GoIntent(position);

           }

        });

        return convertView;

    }



    public void GoIntent(int a){

        Intent intent = new Intent(m_activity, ReviewDetail.class);

//putExtra 로 선택한 아이템의 정보를 인텐트로 넘겨 줄 수 있다.

        intent.putExtra("NAME", arr.get(a).getName());
        intent.putExtra("LINK", arr.get(a).getLink());
        intent.putExtra("DESCRIPTION", arr.get(a).getDescription());
        intent.putExtra("ADDRESS", arr.get(a).getAddress());
        intent.putExtra("ROAD_ADDRESS", arr.get(a).getRoad_address());
        intent.putExtra("TELEPHONE", arr.get(a).getTelephone());
        intent.putExtra("MAPX", arr.get(a).getMapx());
        intent.putExtra("MAPY", arr.get(a).getMapy());

        m_activity.startActivity(intent);

    }


}
