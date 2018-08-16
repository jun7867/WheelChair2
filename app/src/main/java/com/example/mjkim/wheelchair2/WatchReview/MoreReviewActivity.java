package com.example.mjkim.wheelchair2.WatchReview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.mjkim.wheelchair2.MenuScreen;
import com.example.mjkim.wheelchair2.R;
import com.example.mjkim.wheelchair2.ReviewDetail;

public class MoreReviewActivity extends AppCompatActivity {

    ImageButton back_button;
    ImageButton menu_button;
    private WatchReviewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_review);


        //버튼 선언
        back_button = (ImageButton)findViewById(R.id.back_b);
        menu_button = (ImageButton)findViewById(R.id.menu_b);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        menu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){ openMenuTab(); }});



        Intent intent = getIntent();
        WatchReviewAdapter.select = 0;



        ListView lv = (ListView) findViewById(R.id.review_list);


        adapter = new WatchReviewAdapter(MoreReviewActivity.this, ReviewDetail.reviewList);
        lv.setAdapter(adapter);





    }

    public void openMenuTab(){
        Intent intent = new Intent(this, MenuScreen.class);
        startActivity(intent);
    }
}