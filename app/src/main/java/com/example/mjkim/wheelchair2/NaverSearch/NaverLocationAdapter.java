package com.example.mjkim.wheelchair2.NaverSearch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.mjkim.wheelchair2.NameSearch.FirebaseJson;
import com.example.mjkim.wheelchair2.R;
import com.example.mjkim.wheelchair2.Review.ReviewJson;
import com.example.mjkim.wheelchair2.Review.ReviewList;
import com.example.mjkim.wheelchair2.ReviewDetail;
import com.example.mjkim.wheelchair2.ReviewScreen;
import com.example.mjkim.wheelchair2.ReviewSearch;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class NaverLocationAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private FirebaseJson firebaseJson;
    private Activity m_activity;
    private ArrayList<NaverLocationList> arr;
    private int save;


    private double rating;
    private Boolean tag1;
    private Boolean tag2;
    private Boolean tag3;
    private Boolean tag4;
    private Boolean tag5;
    private Boolean tag6;
    private int length;
    public static ArrayList<ReviewList> reviewLists;
    RatingBar ratingBar;
    ImageView tagShow1,tagShow2,tagShow3,tagShow4,tagShow5,tagShow6;






    public NaverLocationAdapter(Activity act, ArrayList<NaverLocationList> arr_item, int save) {
        this.m_activity = act;
        arr = arr_item;
        mInflater = (LayoutInflater)m_activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.save = save;
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

        // 평균 별점과 픽토그램
        double total_star = 0;
        int[] tag_array = {0,0,0,0,0,0};


        if(FirebaseJson.reviewJson.size() > position){

            reviewLists = new ArrayList<ReviewList>();
            int num = 0;






            String json = FirebaseJson.reviewJson.get(position).getReview_json_string();
            System.out.println(json);
            length = FirebaseJson.reviewJson.get(position).getReview_count();
            System.out.println("길이: " + length);
            JSONArray IDs = FirebaseJson.reviewJson.get(position).getReview_json_userID();
            System.out.println("아이디: " + IDs);
            String location_name = FirebaseJson.reviewJson.get(position).getLocation_name();
            System.out.println("장소: " + location_name);

            try{
                JSONObject obj = new JSONObject(json);



                for (int i = 0; i < length; i++) {
                    JSONObject jsonObj = obj.getJSONObject(IDs.getString(i));
                    rating = jsonObj.getDouble("rating");
                    total_star += rating;
                    System.out.println("점수: " + rating);
                    tag1 = jsonObj.getBoolean("tag1");
                    if(tag1 == true) tag_array[0]  = tag_array[0] +  1;
                    tag2 = jsonObj.getBoolean("tag2");
                    if(tag2 == true) tag_array[1]  = tag_array[1] +  1;
                    tag3 = jsonObj.getBoolean("tag3");
                    if(tag3 == true) tag_array[2]  = tag_array[2] +  1;
                    tag4 = jsonObj.getBoolean("tag4");
                    if(tag4 == true) tag_array[3]  = tag_array[3] +  1;
                    tag5 = jsonObj.getBoolean("tag5");
                    if(tag5 == true) tag_array[4]  = tag_array[4] +  1;
                    tag6 = jsonObj.getBoolean("tag6");
                    if(tag6 == true) tag_array[5]  = tag_array[5] +  1;


                }


            }catch (Exception e) {
                e.printStackTrace();

            }

        }

        //태그 기준 설정 및 출력

        tagShow1 = (ImageView) convertView.findViewById(R.id.tag_done_1);
        tagShow2 = (ImageView) convertView.findViewById(R.id.tag_done_2);
        tagShow3 = (ImageView) convertView.findViewById(R.id.tag_done_3);
        tagShow4 = (ImageView) convertView.findViewById(R.id.tag_done_4);
        tagShow5 = (ImageView) convertView.findViewById(R.id.tag_done_5);
        tagShow6 = (ImageView) convertView.findViewById(R.id.tag_done_6);

        ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBar4);

        if(tag_array[0] > length/2 &&  tag_array[0] != 0) tagShow1.setImageResource(R.drawable.restroom);
        System.out.println("태그1: " + tag_array[0]);
        if(tag_array[1] > length/2 &&  tag_array[1] != 0) tagShow2.setImageResource(R.drawable.parking);
        System.out.println("태그2: " + tag_array[1]);
        if(tag_array[2] > length/2 &&  tag_array[2] != 0) tagShow3.setImageResource(R.drawable.elevator);
        System.out.println("태그3: " + tag_array[2]);
        if(tag_array[3] > length/2 &&  tag_array[3] != 0) tagShow4.setImageResource(R.drawable.slope);
        System.out.println("태그4: " + tag_array[3]);
        if(tag_array[4] > length/2 &&  tag_array[4] != 0) tagShow5.setImageResource(R.drawable.table);
        System.out.println("태그5: " + tag_array[4]);
        if(tag_array[5] > length/2 &&  tag_array[5] != 0) tagShow6.setImageResource(R.drawable.assistant);
        System.out.println("태그6: " + tag_array[5]);

        total_star = total_star/length;
        ratingBar.setRating((float)total_star);
        //태그 기준 설정 및 출력 끝





        TextView title = (TextView)convertView.findViewById(R.id.vi_name);
        TextView road_address = (TextView)convertView.findViewById(R.id.vi_address);
        TextView telephone = (TextView)convertView.findViewById(R.id.vi_telephone);
        LinearLayout layout_view =  (LinearLayout)convertView.findViewById(R.id.vi_view);

        //int resId=  m_activity.getResources().getIdentifier(arr.get(position)., "drawable", m_activity.getPackageName());

        //imView.setBackgroundResource(resId);

        title.setText(arr.get(position).getName());
        road_address.setText(arr.get(position).getRoad_address());
        telephone.setText(arr.get(position).getTelephone());
        //review_num.setText("리뷰 " + FirebaseJson.reviewJson.get(position).getReview_count());






        /*  버튼에 이벤트처리를 하기위해선 setTag를 이용해서 사용할 수 있습니다.

         *   Button btn 가 있다면, btn.setTag(position)을 활용해서 각 버튼들

         *   이벤트처리를 할 수 있습니다.
         */

        layout_view.setOnClickListener(new View.OnClickListener(){

           public void onClick(View v){
//               new Handler().postDelayed(new Runnable() {
//                   @Override
//                   public void run() {
//                       GoIntent(position);
//                   }
//               }, 2000);
               GoIntent(position);

           }

        });

        return convertView;

    }



    public void GoIntent(int a){
        Intent intent;



        // 이름으로 검색에서 리스트 뷰를 누르면 장소 정보를 자세히본다.
        // 리뷰 작성하기에서 리스트 뷰를 누르면 리뷰 작성으로 간다.
        if(save == 1) intent = new Intent(m_activity, ReviewScreen.class);
        else intent = new Intent(m_activity, ReviewDetail.class);

//putExtra 로 선택한 아이템의 정보를 인텐트로 넘겨 줄 수 있다.

        intent.putExtra("NAME", arr.get(a).getName());
        intent.putExtra("LINK", arr.get(a).getLink());
        intent.putExtra("DESCRIPTION", arr.get(a).getDescription());
        intent.putExtra("ADDRESS", arr.get(a).getAddress());
        intent.putExtra("ROAD_ADDRESS", arr.get(a).getRoad_address());
        intent.putExtra("TELEPHONE", arr.get(a).getTelephone());
        intent.putExtra("MAPX", arr.get(a).getMapx());
        intent.putExtra("MAPY", arr.get(a).getMapy());
        intent.putExtra("NUMBER", a);


        m_activity.startActivity(intent);
    }


}
