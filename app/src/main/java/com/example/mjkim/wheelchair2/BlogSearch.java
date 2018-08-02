package com.example.mjkim.wheelchair2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mjkim.wheelchair2.NaverSearch.NaverBlogAdapter;
import com.example.mjkim.wheelchair2.NaverSearch.NaverBlogList;
import com.example.mjkim.wheelchair2.NaverSearch.NaverBlogSearch;
import com.example.mjkim.wheelchair2.NaverSearch.NaverLocationAdapter;
import com.example.mjkim.wheelchair2.NaverSearch.NaverLocationList;
import com.example.mjkim.wheelchair2.NaverSearch.NaverLocationSearch;

import java.util.ArrayList;

public class BlogSearch extends AppCompatActivity {

    ImageButton back_button;
    ImageButton menu_button;
    private NaverBlogAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_search);


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
        NaverBlogAdapter.select = 0;



        ListView lv = (ListView) findViewById(R.id.blog_list);


        adapter = new NaverBlogAdapter(BlogSearch.this, ReviewDetail.bloglist);
        lv.setAdapter(adapter);





    }

    public void openMenuTab(){
        Intent intent = new Intent(this, MenuScreen.class);
        startActivity(intent);
    }
}
