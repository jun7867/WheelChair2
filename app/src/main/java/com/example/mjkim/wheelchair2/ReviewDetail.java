package com.example.mjkim.wheelchair2;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.mjkim.wheelchair2.NameSearch.FirebaseJson;
import com.example.mjkim.wheelchair2.NaverSearch.NaverBlogAdapter;
import com.example.mjkim.wheelchair2.NaverSearch.NaverBlogList;
import com.example.mjkim.wheelchair2.NaverSearch.NaverBlogSearch;
import com.example.mjkim.wheelchair2.Review.ReviewList;
import com.example.mjkim.wheelchair2.WatchReview.MoreReviewActivity;
import com.example.mjkim.wheelchair2.WatchReview.WatchReviewAdapter;
import com.example.mjkim.wheelchair2.WatchReview.WatchReviewList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ReviewDetail extends AppCompatActivity {


    public static ArrayList<NaverBlogList> blogList;       // 네이버 지역 리스트
    public static ArrayList<ReviewList> reviewLists;
    private NaverBlogSearch naverBlogSearch;
    private NaverBlogAdapter blogAdapter;
    private WatchReviewAdapter reviewAdapter;
    public static int length;
    ImageButton back_button, menu_button, more_blog, more_review;
    Button map_search, review_button;
    TextView location_name, address_name, phone_number;
    ScrollView scrollView;
    String phone = "";
    String address = "";
    private String name;
    private double mapx;
    private double mapy;
    private double rating;
    private Boolean tag1;
    private Boolean tag2;
    private Boolean tag3;
    private Boolean tag4;
    private Boolean tag5;
    private Boolean tag6;
    private String review;
    private String reviewer_name;
    private String email;
    private double location_mapx;
    private double location_mapy;
    private String imageUrl1;
    private String imageUrl2;
    private String imageUrl3;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_detail);

        // 제일 위부터 보기
        scrollView = new ScrollView(this);
        scrollView.findViewById(R.id.scroll_view);
//        scrollView.smoothScrollTo(0,0);
//        scrollView.scrollBy(0,200);
        scrollView.scrollTo(0,600);
//        scrollView.fullScroll(ScrollView.FOCUSABLES_TOUCH_MODE);
//        scrollView.smoothScrollTo(0,0);

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

        map_search = (Button)findViewById(R.id.current_b);
        map_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { openMapSearch();
            }
        });

        review_button = (Button) findViewById(R.id.review_b);
        review_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { writeReviewTab();
            }
        });

        blogList = new ArrayList<NaverBlogList>();
        naverBlogSearch = new NaverBlogSearch();

        Intent intent = getIntent();
        reviewLists = new ArrayList<ReviewList>();


        //해당 장소의 리뷰들을 리스트로 저장하고 JSON 파싱을한다
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

                    rating = jsonObj.getDouble("rating");
                    System.out.println("점수: " + rating);
                    tag1 = jsonObj.getBoolean("tag1");
                    tag2 = jsonObj.getBoolean("tag2");
                    tag3 = jsonObj.getBoolean("tag3");
                    tag4 = jsonObj.getBoolean("tag4");
                    tag5 = jsonObj.getBoolean("tag5");
                    tag6 = jsonObj.getBoolean("tag6");
                    review = jsonObj.getString("review");
                    System.out.println("리뷰: " + review);
                    reviewer_name = jsonObj.getString("name");
                    email = jsonObj.getString("email");
                    location_mapx = jsonObj.getInt("mapx");
                    System.out.println("MAPX: " + location_mapx);
                    location_mapy = jsonObj.getInt("mapy");
                    System.out.println("MAPY: " + location_mapy);
                    imageUrl1 = jsonObj.getString("imageUrl1");
                    imageUrl2 = jsonObj.getString("imageUrl2");
                    imageUrl3 = jsonObj.getString("imageUrl3");




                    System.out.println("지역 이름1 " + intent.getExtras().getString("NAME"));
                    System.out.println("지역 이름2 " + location_name);

                    if(intent.getExtras().getString("NAME").equals(location_name)) {
                        reviewLists.add(num++, new ReviewList(intent.getExtras().getString("NAME"), rating, tag1, tag2, tag3, tag4, tag5, tag6, review, reviewer_name,
                                email, location_mapx, location_mapy, imageUrl1, imageUrl2, imageUrl3));
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


        blogAdapter = new NaverBlogAdapter(ReviewDetail.this, blogList);
        blogListView.setAdapter(blogAdapter);




        // 리뷰 어댑터
        WatchReviewAdapter.select = 1;

        ListView reviewListView = (ListView) findViewById(R.id.short_review_list);


        reviewAdapter = new WatchReviewAdapter(ReviewDetail.this, reviewLists);
        reviewListView.setAdapter(reviewAdapter);





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


        phone_number.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent1 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                startActivity(intent1);
            }
        });


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
        Intent intent = new Intent(this, FindNameLocationActivity.class);
        intent.putExtra("NAME", name);
        intent.putExtra("MAPX", mapx);
        intent.putExtra("MAPY", mapy);
        startActivity(intent);
    }

    public void writeReviewTab(){
        Intent intent = new Intent(this, ReviewScreen.class);
        intent.putExtra("NAME", name);
        intent.putExtra("MAPX", mapx);
        intent.putExtra("MAPY", mapy);
        intent.putExtra("ROAD_ADDRESS", address);
        startActivity(intent);
    }

}
