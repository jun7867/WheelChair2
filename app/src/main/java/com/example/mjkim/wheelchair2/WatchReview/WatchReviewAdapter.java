package com.example.mjkim.wheelchair2.WatchReview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mjkim.wheelchair2.CertainReviewDetail;
import com.example.mjkim.wheelchair2.R;
import com.example.mjkim.wheelchair2.Review.ReviewList;
import com.example.mjkim.wheelchair2.ReviewDetail;

import java.util.ArrayList;

public class WatchReviewAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private Activity m_activity;
    private ArrayList<ReviewList> arr;
    public static int select = 0; //출력되는 블로그 개수 선택 1이면 5개 2이면 최대 20개
    public WatchReviewAdapter(Activity act, ArrayList<ReviewList> arr_item) {
        this.m_activity = act;
        arr = arr_item;
        mInflater = (LayoutInflater)m_activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if(select == 1){
            if(ReviewDetail.length < 3) return arr.size();
            else return 3;
        }
        else return arr.size();
    }

    @Override
    public Object getItem(int position) { return arr.get(position); }

    public long getItemId(int position){ return position; }

    @Override

    public View getView(final int position, View convertView, ViewGroup parent) {

        if(convertView == null){

            int res = 0;
            res = R.layout.more_information_list;
            convertView = mInflater.inflate(res, parent, false);

        }


        TextView title = (TextView)convertView.findViewById(R.id.postname);
        TextView description = (TextView)convertView.findViewById(R.id.post_des);
        TextView bloggername = (TextView)convertView.findViewById(R.id.reviewer);
        TextView postdate = (TextView)convertView.findViewById(R.id.postdate);
        LinearLayout layout_view =  (LinearLayout)convertView.findViewById(R.id.review_view);

        //int resId=  m_activity.getResources().getIdentifier(arr.get(position)., "drawable", m_activity.getPackageName());

        //imView.setBackgroundResource(resId);

        int num = position + 1;
        title.setText("리뷰 " + num);
        title.setText(arr.get(position).getReview_name());
        description.setText(arr.get(position).getReview());
        bloggername.setText(arr.get(position).getName());
        postdate.setText(arr.get(position).getDate());
        System.out.println(arr.get(position).getDate());

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

        Intent intent = new Intent(m_activity, CertainReviewDetail.class);
        intent.putExtra("IMAGE1", arr.get(a).getImageUrl1());
        intent.putExtra("IMAGE2", arr.get(a).getImageUrl2());
        intent.putExtra("IMAGE3", arr.get(a).getImageUrl3());
        intent.putExtra("IMAGE4", arr.get(a).getImageUrl4());
        intent.putExtra("IMAGE5", arr.get(a).getImageUrl5());
        intent.putExtra("IMAGE6", arr.get(a).getImageUrl6());
        intent.putExtra("IMAGE7", arr.get(a).getImageUrl7());
        intent.putExtra("IMAGE8", arr.get(a).getImageUrl8());
        intent.putExtra("IMAGE9", arr.get(a).getImageUrl9());

        intent.putExtra("Email", arr.get(a).getEmail());
        intent.putExtra("Name", arr.get(a).getName());
        intent.putExtra("Review", arr.get(a).getReview());
        intent.putExtra("Date",arr.get(a).getDate());
        intent.putExtra("Review_name",arr.get(a).getReview_name());
        intent.putExtra("Rating",arr.get(a).getRating());
        intent.putExtra("Tag1",arr.get(a).getTag1());
        intent.putExtra("Tag2",arr.get(a).getTag2());
        intent.putExtra("Tag3",arr.get(a).getTag3());
        intent.putExtra("Tag4",arr.get(a).getTag4());
        intent.putExtra("Tag5",arr.get(a).getTag5());
        intent.putExtra("Tag6",arr.get(a).getTag6());


        m_activity.startActivity(intent);
    }


}