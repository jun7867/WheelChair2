package com.example.mjkim.wheelchair2;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mjkim.wheelchair2.NameSearch.FirebaseJson;
import com.example.mjkim.wheelchair2.NaverSearch.NaverBlogAdapter;
import com.example.mjkim.wheelchair2.NaverSearch.NaverBlogList;
import com.example.mjkim.wheelchair2.NaverSearch.NaverBlogSearch;
import com.example.mjkim.wheelchair2.Review.ReviewList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ReviewDetail extends AppCompatActivity {


    public static ArrayList<NaverBlogList> bloglist;       // 네이버 지역 리스트
    public static ArrayList<ReviewList> reviewLists;
    private NaverBlogSearch naverBlogSearch;
    private NaverBlogAdapter adapter;
    ImageButton back_button, menu_button, more_blog;
    Button map_search, review_button;
    TextView location_name, address_name, phone_number;
    String name = "";
    String phone = "";
    int mapx, mapy;

    private double rating;
    private Boolean tag1;
    private Boolean tag2;
    private Boolean tag3;
    private Boolean tag4;
    private Boolean tag5;
    private Boolean tag6;
    private String review;
    private String location_title;
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

        map_search = (Button)findViewById(R.id.current_b);
        map_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { openMapSearch();
            }
        });

        review_button = (Button) findViewById(R.id.review_b);
        review_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { openReviewTab();
            }
        });

        bloglist = new ArrayList<NaverBlogList>();
        naverBlogSearch = new NaverBlogSearch();

        Intent intent = getIntent();


        //해당 장소의 리뷰들을 리스트로 저장하고 JSON 파싱을한다
        if(FirebaseJson.reviewJson.size() > intent.getExtras().getInt("NUMBER")){

            reviewLists = new ArrayList<ReviewList>();
            int num = 0;



            String json = FirebaseJson.reviewJson.get(intent.getExtras().getInt("NUMBER")).getReview_json_string();
            int length = FirebaseJson.reviewJson.get(intent.getExtras().getInt("NUMBER")).getReview_count();
            JSONArray IDs = FirebaseJson.reviewJson.get(intent.getExtras().getInt("NUMBER")).getReview_json_userID();

            try{
                JSONObject obj = new JSONObject(json);


                for (int i = 0; i < length; i++) {
                    JSONObject jsonObj = obj.getJSONObject(IDs.getString(i));

                    rating = jsonObj.getDouble("rating");
                    tag1 = jsonObj.getBoolean("tag1");
                    tag2 = jsonObj.getBoolean("tag2");
                    tag3 = jsonObj.getBoolean("tag3");
                    tag4 = jsonObj.getBoolean("tag4");
                    tag5 = jsonObj.getBoolean("tag5");
                    tag6 = jsonObj.getBoolean("tag6");
                    review = jsonObj.getString("review");
                    location_title = jsonObj.getString("name");
                    email = jsonObj.getString("email");
                    location_mapx = jsonObj.getInt("mapx");
                    location_mapy = jsonObj.getInt("mapy");
                    imageUrl1 = jsonObj.getString("imageUrl1");
                    imageUrl2 = jsonObj.getString("imageUrl2");
                    imageUrl3 = jsonObj.getString("imageUrl3");


                    reviewLists.add(num++, new ReviewList(rating, tag1, tag2, tag3, tag4, tag5,tag6, review, location_title,
                            email, location_mapx, location_mapy, imageUrl1, imageUrl2, imageUrl3));
                }


            }catch (Exception e) {
                e.printStackTrace();

            }

        }



        try {
            bloglist = naverBlogSearch.execute(intent.getExtras().getString("NAME")).get();
        }catch (Exception e){
            e.printStackTrace();
        }

        NaverBlogAdapter.select = 1;

        ListView lv = (ListView) findViewById(R.id.short_blog_list);


        adapter = new NaverBlogAdapter(ReviewDetail.this, bloglist);
        lv.setAdapter(adapter);





        location_name.setText(intent.getExtras().getString("NAME"));
        name = intent.getExtras().getString("NAME");
        address_name.setText(intent.getExtras().getString("ROAD_ADDRESS"));
        phone_number.setText(intent.getExtras().getString("TELEPHONE"));
        phone = intent.getExtras().getString("TELEPHONE");

        mapx = intent.getExtras().getInt("MAPX");
        mapy = intent.getExtras().getInt("MAPY");


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

    public void openMapSearch(){
        Intent intent = new Intent(this, WatchLocationActivity.class);
        intent.putExtra("NAME", name);
        intent.putExtra("MAPX", mapx);
        intent.putExtra("MAPY", mapy);
        startActivity(intent);
    }

    public void openReviewTab(){
        Intent intent = new Intent(this, ReviewScreen.class);
        intent.putExtra("NAME", name);
        intent.putExtra("MAPX", mapx);
        intent.putExtra("MAPY", mapy);
        startActivity(intent);
    }

}
