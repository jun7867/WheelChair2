package com.example.mjkim.wheelchair2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mjkim.wheelchair2.NaverSearch.NaverBlogList;
import com.example.mjkim.wheelchair2.NaverSearch.NaverBlogSearch;
import com.example.mjkim.wheelchair2.NaverSearch.NaverLocationList;
import com.example.mjkim.wheelchair2.NaverSearch.NaverLocationSearch;

import java.util.ArrayList;

public class BlogSearch extends AppCompatActivity {

    ImageButton back_button;
    ImageButton menu_button;
    String blog_name;
    public static ArrayList<NaverBlogList> bloglist;       // 네이버 지역 리스트
    private NaverBlogSearch naverBlogSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_search);

        bloglist = new ArrayList<NaverBlogList>();
        naverBlogSearch = new NaverBlogSearch();

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

        blog_name = intent.getExtras().getString("BLOGNAME");




        try {
            bloglist = naverBlogSearch.execute(blog_name).get();
        }catch (Exception e){
            e.printStackTrace();
        }



    }

    public void openMenuTab(){
        Intent intent = new Intent(this, MenuScreen.class);
        startActivity(intent);
    }
}
