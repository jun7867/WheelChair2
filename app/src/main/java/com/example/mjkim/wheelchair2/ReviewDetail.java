package com.example.mjkim.wheelchair2;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;

import com.example.mjkim.wheelchair2.NameSearch.FirebaseJson;
import com.example.mjkim.wheelchair2.NaverSearch.NaverBlogAdapter;
import com.example.mjkim.wheelchair2.NaverSearch.NaverBlogList;
import com.example.mjkim.wheelchair2.NaverSearch.NaverBlogSearch;
import com.example.mjkim.wheelchair2.NaverSearch.NaverLocationAdapter;
import com.example.mjkim.wheelchair2.Review.ReviewList;
import com.example.mjkim.wheelchair2.WatchReview.MoreReviewActivity;
import com.example.mjkim.wheelchair2.WatchReview.WatchReviewAdapter;
import com.example.mjkim.wheelchair2.WatchReview.WatchReviewList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Array;
import java.util.ArrayList;

public class ReviewDetail extends AppCompatActivity {


    public static ArrayList<NaverBlogList> blogList;       // 네이버 지역 리스트
    public static ArrayList<WatchReviewList> reviewList;
    public static ArrayList<ReviewList> reviewLists;
    private NaverBlogSearch naverBlogSearch;
    private NaverBlogAdapter blogAdapter;
    private WatchReviewAdapter reviewAdapter;
    public static int length;
    ImageButton back_button, menu_button, more_blog, more_review;
    ImageView tagShow1,tagShow2,tagShow3,tagShow4,tagShow5,tagShow6;
    Button map_search, review_button;
    TextView location_name, address_name, phone_number;
    ScrollView scrollView;
    RatingBar ratingBar;
    String phone = "";
    String address = "";
    private String name;
    private String review_name;
    private String reviewer_name;
    int mapx, mapy;
    private double rating;
    private Boolean tag1;
    private Boolean tag2;
    private Boolean tag3;
    private Boolean tag4;
    private Boolean tag5;
    private Boolean tag6;
    private String review;
    private String email;
    private double location_mapx;
    private double location_mapy;
    private String imageUrl1;
    private String imageUrl2;
    private String imageUrl3;
    private String imageUrl4;
    private String imageUrl5;
    private String imageUrl6;
    private String imageUrl7;
    private String imageUrl8;
    private String imageUrl9;
    private String date;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_detail);


        back_button = (ImageButton)findViewById(R.id.back_b);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        menu_button = (ImageButton)findViewById(R.id.menu_b);
        menu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { openMenuTab();
            }
        });

        location_name = (TextView) findViewById(R.id.location_name);
        address_name = (TextView) findViewById(R.id.address_name);
        address_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { openMapSearch();
            }
        });

        phone_number = (TextView) findViewById(R.id.telephone_number);


        more_blog = (ImageButton)findViewById(R.id.blogmore_b);
        more_blog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { openBlogTab();
            }
        });

        more_review = (ImageButton)findViewById(R.id.reviewmore_b);
        more_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { openReviewTab();
            }
        });



        review_button = (Button) findViewById(R.id.review_b);
        review_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { writeReviewTab();
            }
        });

        tagShow1 = (ImageView) findViewById(R.id.tag_done_1);
        tagShow2 = (ImageView) findViewById(R.id.tag_done_2);
        tagShow3 = (ImageView) findViewById(R.id.tag_done_3);
        tagShow4 = (ImageView) findViewById(R.id.tag_done_4);
        tagShow5 = (ImageView) findViewById(R.id.tag_done_5);
        tagShow6 = (ImageView) findViewById(R.id.tag_done_6);

        ratingBar = (RatingBar) findViewById(R.id.ratingBar2);

        blogList = new ArrayList<NaverBlogList>();
        naverBlogSearch = new NaverBlogSearch();

        Intent intent = getIntent();
        reviewLists = new ArrayList<ReviewList>();



        //해당 장소의 리뷰들을 리스트로 저장하고 JSON 파싱을한다

        double total_star = 0;
        int[] tag_array = {0,0,0,0,0,0};

        if(FirebaseJson.reviewJson.size() > intent.getExtras().getInt("NUMBER")){

            reviewLists = new ArrayList<ReviewList>();
            int num = 0;



            String json = FirebaseJson.reviewJson.get(intent.getExtras().getInt("NUMBER")).getReview_json_string();
            System.out.println(json);
            length = FirebaseJson.reviewJson.get(intent.getExtras().getInt("NUMBER")).getReview_count();
            System.out.println("길이: " + length);
            JSONArray IDs = FirebaseJson.reviewJson.get(intent.getExtras().getInt("NUMBER")).getReview_json_userID();
            System.out.println("아이디: " + IDs);
            String location_name = FirebaseJson.reviewJson.get(intent.getExtras().getInt("NUMBER")).getLocation_name();
            System.out.println("이름: " + location_name);

            try{
                JSONObject obj = new JSONObject(json);


                for (int i = 0; i < length; i++) {
                    JSONObject jsonObj = obj.getJSONObject(IDs.getString(i));
                    date = jsonObj.getString("date");
                    review_name = jsonObj.getString("review_name");
                    System.out.println("제목: " + review_name);
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
                    review = jsonObj.getString("review");
                    System.out.println("리뷰: " + review);
                    //reviewer_name = jsonObj.getString("name");
                    email = jsonObj.getString("email");
                    location_mapx = jsonObj.getInt("mapx");
                    System.out.println("MAPX: " + location_mapx);
                    location_mapy = jsonObj.getInt("mapy");
                    System.out.println("MAPY: " + location_mapy);
                    imageUrl1 = jsonObj.getString("imageUrl1");
                    imageUrl2 = jsonObj.getString("imageUrl2");
                    imageUrl3 = jsonObj.getString("imageUrl3");
                    imageUrl4 = jsonObj.getString("imageUrl4");
                    imageUrl5 = jsonObj.getString("imageUrl5");
                    imageUrl6 = jsonObj.getString("imageUrl6");
                    imageUrl7 = jsonObj.getString("imageUrl7");
                    imageUrl8 = jsonObj.getString("imageUrl8");
                    imageUrl9 = jsonObj.getString("imageUrl9");



                    if(intent.getExtras().getString("NAME").equals(location_name)) {
                        reviewLists.add(num++, new ReviewList(intent.getExtras().getString("NAME"),review_name, rating, tag1, tag2, tag3, tag4, tag5, tag6, review, reviewer_name,
                                email, location_mapx, location_mapy, date, imageUrl1, imageUrl2, imageUrl3,imageUrl4,imageUrl5,imageUrl6,
                                imageUrl7,imageUrl8,imageUrl9));
                    }

                }


            }catch (Exception e) {
                e.printStackTrace();

            }

        }


        //블로그 어댑터
        try {
            blogList = naverBlogSearch.execute(intent.getExtras().getString("NAME")).get();
        }catch (Exception e){
            e.printStackTrace();
        }

        NaverBlogAdapter.select = 1;

        ListView blogListView = (ListView) findViewById(R.id.short_blog_list);
        // 동적으로 리스트뷰 높이 할당
        if(blogList.size() == 0) {
            blogListView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 50));

        }
        else if(blogList.size() == 1) {
            blogListView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 500));
        }
        else if(blogList.size() == 2) {
            blogListView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 850));
        }

        blogAdapter = new NaverBlogAdapter(ReviewDetail.this, blogList);
        blogListView.setAdapter(blogAdapter);




        // 리뷰 어댑터
        WatchReviewAdapter.select = 1;

        ListView reviewListView = (ListView) findViewById(R.id.short_review_list);
        if(reviewLists.size() == 0) {
            reviewListView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 50));
        }
        else if(reviewLists.size() == 1) {
            reviewListView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 500));
        }
        else if(reviewLists.size() == 2) {
            reviewListView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 850));
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ListView reviewListView = (ListView) findViewById(R.id.short_review_list);
                reviewAdapter = new WatchReviewAdapter(ReviewDetail.this, reviewLists);
                reviewListView.setAdapter(reviewAdapter);
            }
        }, 900);




        location_name.setText(intent.getExtras().getString("NAME"));
        name = intent.getExtras().getString("NAME");
        address_name.setText(intent.getExtras().getString("ROAD_ADDRESS"));
        address = intent.getExtras().getString("ROAD_ADDRESS");
        phone_number.setText(intent.getExtras().getString("TELEPHONE"));
        phone = intent.getExtras().getString("TELEPHONE");
        mapx = intent.getExtras().getInt("MAPX");
        mapy = intent.getExtras().getInt("MAPY");
        rating = intent.getExtras().getDouble("RATING");
        tag1 = intent.getExtras().getBoolean("TAG1");
        tag2 = intent.getExtras().getBoolean("TAG2");
        tag3 = intent.getExtras().getBoolean("TAG3");
        tag4 = intent.getExtras().getBoolean("TAG4");
        tag5 = intent.getExtras().getBoolean("TAG5");
        tag6 = intent.getExtras().getBoolean("TAG6");
        review = intent.getExtras().getString("STRING");
        email = intent.getExtras().getString("EMAIL");
        imageUrl1 = intent.getExtras().getString("IMAGEURL1");
        imageUrl2 = intent.getExtras().getString("IMAGEURL2");
        imageUrl3 = intent.getExtras().getString("IMAGEURL3");
        imageUrl4 = intent.getExtras().getString("IMAGEURL4");
        imageUrl5 = intent.getExtras().getString("IMAGEURL5");
        imageUrl6 = intent.getExtras().getString("IMAGEURL6");
        imageUrl7 = intent.getExtras().getString("IMAGEURL7");
        imageUrl8 = intent.getExtras().getString("IMAGEURL8");
        imageUrl9 = intent.getExtras().getString("IMAGEURL9");



        phone_number.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent1 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                startActivity(intent1);
            }
        });


        //태그 기준 설정 및 출력

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



    }

    public void openMenuTab(){
        Intent intent = new Intent(this, MenuScreen.class);
        startActivity(intent);
    }

    public void openBlogTab(){
        Intent intent = new Intent(this, BlogSearch.class);
        intent.putExtra("BLOGNAME", name);
        startActivity(intent);
    }

    public void openReviewTab(){
        Intent intent = new Intent(this, MoreReviewActivity.class);
        intent.putExtra("BLOGNAME", name);
        startActivity(intent);
    }

    public void openMapSearch(){
        Intent intent = new Intent(this, WatchLocationActivity.class);
        intent.putExtra("NAME", name);
        intent.putExtra("MAPX", mapx);
        intent.putExtra("MAPY", mapy);
        System.out.println("멥 엑스1: " + mapx);
        System.out.println("멥 와이1: " + mapy);
        startActivity(intent);
    }

    public void writeReviewTab(){
        Intent intent = new Intent(this, ReviewScreen.class);
        intent.putExtra("NAME", name);
        intent.putExtra("MAPX", mapx);
        intent.putExtra("MAPY", mapy);
        intent.putExtra("ROAD_ADDRESS", address);
        intent.putExtra("TELEPHONE", phone);
        startActivity(intent);
    }

}