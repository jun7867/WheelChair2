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

import com.example.mjkim.wheelchair2.BlogDetail;
import com.example.mjkim.wheelchair2.R;
import com.example.mjkim.wheelchair2.ReviewDetail;
import com.example.mjkim.wheelchair2.ReviewSearch;

import java.util.ArrayList;

public class NaverBlogAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private Activity m_activity;
    private ArrayList<NaverBlogList> arr;
    public static int select = 0; //출력되는 블로그 개수 선택 1이면 5개 2이면 최대 20개
    public NaverBlogAdapter(Activity act, ArrayList<NaverBlogList> arr_item) {
        this.m_activity = act;
        arr = arr_item;
        mInflater = (LayoutInflater)m_activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if(select == 1) return 3;
        else return arr.size();

    }

    @Override
    public Object getItem(int position) { return arr.get(position); }

    public long getItemId(int position){ return position; }

    @Override

    public View getView(final int position, View convertView, ViewGroup parent) {

        if(convertView == null){

            int res = 0;
            res = R.layout.naver_blog_list;
            convertView = mInflater.inflate(res, parent, false);

        }


        TextView title = (TextView)convertView.findViewById(R.id.postname);
        TextView description = (TextView)convertView.findViewById(R.id.post_des);
        TextView bloggername = (TextView)convertView.findViewById(R.id.blogger);
        TextView postdate = (TextView)convertView.findViewById(R.id.postdate);
        LinearLayout layout_view =  (LinearLayout)convertView.findViewById(R.id.blog_view);

        //int resId=  m_activity.getResources().getIdentifier(arr.get(position)., "drawable", m_activity.getPackageName());

        //imView.setBackgroundResource(resId);

        title.setText(arr.get(position).getName());
        description.setText(arr.get(position).getDescription());
        bloggername.setText(arr.get(position).getBloogername());
        postdate.setText(arr.get(position).getPostdate());

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

        Intent intent = new Intent(m_activity, BlogDetail.class);
        intent.putExtra("LINK", arr.get(a).getLink()); //해당 블로그 링크를 보내준다
        m_activity.startActivity(intent);
    }


}
