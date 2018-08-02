package com.example.mjkim.wheelchair2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.mjkim.wheelchair2.NaverSearch.NaverBlogList;
import com.example.mjkim.wheelchair2.NaverSearch.NaverLocationAdapter;
import com.example.mjkim.wheelchair2.NaverSearch.NaverLocationList;

import java.util.ArrayList;

public class BlogDetail extends AppCompatActivity {

    WebView naverBlogView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_detail);

        Intent intent = getIntent();
        naverBlogView = (WebView) findViewById(R.id.NaverBlog);
        naverBlogView.setWebViewClient(new WebViewClient());
        naverBlogView.getSettings().setJavaScriptEnabled(true);
        naverBlogView.loadUrl(intent.getExtras().getString("LINK"));
        System.out.println(intent.getExtras().getString("LINK"));

    }
}
