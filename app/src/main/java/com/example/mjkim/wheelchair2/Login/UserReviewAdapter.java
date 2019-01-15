package com.example.mjkim.wheelchair2.Login;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mjkim.wheelchair2.CertainReviewDetail;
import com.example.mjkim.wheelchair2.CorrectionScreen;
import com.example.mjkim.wheelchair2.R;
import com.example.mjkim.wheelchair2.Review.ReviewList;
import com.example.mjkim.wheelchair2.ReviewDetail;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;

public class UserReviewAdapter extends BaseAdapter {
    private DatabaseReference mDatabase;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private LayoutInflater mInflater;
    private Activity m_activity;
    private ArrayList<ReviewList> arr;
    private int my_review_index;
    Context context;
    Activity activity;
    public UserReviewAdapter(Activity act, ArrayList<ReviewList> arr_item,Context context, Activity activity,int my_review_index) {
        this.m_activity = act;
        arr = arr_item;
        mInflater = (LayoutInflater)m_activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context=context;
        this.activity=activity;
        this.my_review_index=my_review_index;
    }

    @Override
    public int getCount() {
         return arr.size();
    }

    @Override
    public Object getItem(int position) { return arr.get(position); }

    public long getItemId(int position){ return position; }

    @Override

    public View getView(final int position, View convertView, ViewGroup parent) {

        if(convertView == null){

            int res = 0;
            res = R.layout.my_review_list;
            convertView = mInflater.inflate(res, parent, false);

        }

        Button correctionButton = (Button) convertView.findViewById(R.id.correction_button);
        Button deleteButton = (Button) convertView.findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogInterface.OnClickListener confirm = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        System.out.println("삭제테스트");

                        delete(position);
                    }
                };
                DialogInterface.OnClickListener cancle = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       //취소되었을때
                    }
                };
                new AlertDialog.Builder(activity) // Adapter에서 쓰려면 사용하려는 activity에서 받아와야한다.
                        .setTitle("삭제하시겠습니까?")
                        .setPositiveButton("삭제", confirm)
                        .setNegativeButton("취소", cancle)
                        .show();
            }
        });

        TextView locationName = (TextView)convertView.findViewById(R.id.location_name);
        TextView title = (TextView)convertView.findViewById(R.id.postname);
        TextView description = (TextView)convertView.findViewById(R.id.post_des);
        TextView email = (TextView)convertView.findViewById(R.id.email);
        TextView postDate = (TextView)convertView.findViewById(R.id.postdate);
        LinearLayout layout_view =  (LinearLayout)convertView.findViewById(R.id.review_view);

        //int resId=  m_activity.getResources().getIdentifier(arr.get(position)., "drawable", m_activity.getPackageName());

        //imView.setBackgroundResource(resId);




        // 수정 버튼
        correctionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){ GoCorrectionScreen(position); }});



        locationName.setText(arr.get(position).getLocation_name());
        title.setText(arr.get(position).getReview_name());
        description.setText(arr.get(position).getReview());
        email.setText(arr.get(position).getEmail());
        postDate.setText(arr.get(position).getDate());
        System.out.println("date : " + arr.get(position).getDate());

        /*  버튼에 이벤트처리를 하기위해선 setTag를 이용해서 사용할 수 있습니다.

         *   Button btn 가 있다면, btn.setTag(position)을 활용해서 각 버튼들

         *   이벤트처리를 할 수 있습니다.

         */

        layout_view.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){

                GoReview(position);

            }

        });

        return convertView;

    }

    private void delete(int a) {
        mDatabase = database.getReference();
        mDatabase.child("review lists").child(arr.get(a).getLocation_name()).child(arr.get(a).getKey()).setValue(null);
        System.out.println("\n 삭제테스트 1: "+ arr.get(a).getKey()+"  ");
        my_review_index--;
        Intent intent = new Intent(activity, MyReview.class);
        m_activity.startActivity(intent);

    }


    public void GoReview(int a){

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

    public void GoCorrectionScreen(int a) {

        Intent intent = new Intent(m_activity, CorrectionScreen.class);
        intent.putExtra("IMAGE1", arr.get(a).getImageUrl1());
        intent.putExtra("IMAGE2", arr.get(a).getImageUrl2());
        intent.putExtra("IMAGE3", arr.get(a).getImageUrl3());
        intent.putExtra("IMAGE4", arr.get(a).getImageUrl4());
        intent.putExtra("IMAGE5", arr.get(a).getImageUrl5());
        intent.putExtra("IMAGE6", arr.get(a).getImageUrl6());
        intent.putExtra("IMAGE7", arr.get(a).getImageUrl7());
        intent.putExtra("IMAGE8", arr.get(a).getImageUrl8());
        intent.putExtra("IMAGE9", arr.get(a).getImageUrl9());

        intent.putExtra("Location_name", arr.get(a).getLocation_name());
//        intent.putExtra("Address_name", arr.get(a).get)
        intent.putExtra("Email", arr.get(a).getEmail());
        intent.putExtra("Name", arr.get(a).getName());
        intent.putExtra("Review", arr.get(a).getReview());
        intent.putExtra("Date", arr.get(a).getDate());
        intent.putExtra("Review_name", arr.get(a).getReview_name());
        intent.putExtra("Rating", arr.get(a).getRating());
        intent.putExtra("Tag1", arr.get(a).getTag1());
        intent.putExtra("Tag2", arr.get(a).getTag2());
        intent.putExtra("Tag3", arr.get(a).getTag3());
        intent.putExtra("Tag4", arr.get(a).getTag4());
        intent.putExtra("Tag5", arr.get(a).getTag5());
        intent.putExtra("Tag6", arr.get(a).getTag6());
        intent.putExtra("Key", arr.get(a).getKey());


        m_activity.startActivity(intent);
    }

}