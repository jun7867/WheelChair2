package com.example.mjkim.wheelchair2;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mjkim.wheelchair2.NaverSearch.NaverBlogAdapter;
import com.example.mjkim.wheelchair2.NaverSearch.NaverBlogList;
import com.example.mjkim.wheelchair2.NaverSearch.NaverBlogSearch;

import java.util.ArrayList;

public class ReviewDetail extends AppCompatActivity {


    public static ArrayList<NaverBlogList> bloglist;       // 네이버 지역 리스트
    private NaverBlogSearch naverBlogSearch;
    private NaverBlogAdapter adapter;
    ImageButton back_button, menu_button, more_blog;
    Button map_search, review_button;
    TextView location_name, address_name, phone_number;
    String name = "";
    String phone = "";
    int mapx, mapy;

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

        bloglist = new ArrayList<NaverBlogList>();
        naverBlogSearch = new NaverBlogSearch();

        Intent intent = getIntent();

        NaverBlogAdapter.select = 1;

        try {
            bloglist = naverBlogSearch.execute(intent.getExtras().getString("NAME")).get();
        }catch (Exception e){
            e.printStackTrace();
        }


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
        Intent intent = new Intent(this, GoogleMapActivity.class);
        intent.putExtra("MAPX", mapx);
        intent.putExtra("MAPY", mapy);
        startActivity(intent);
    }

}
