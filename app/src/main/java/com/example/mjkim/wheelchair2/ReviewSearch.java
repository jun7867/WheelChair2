package com.example.mjkim.wheelchair2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import com.example.mjkim.wheelchair2.NaverSearch.NaverLocationList;
import com.example.mjkim.wheelchair2.NaverSearch.NaverLocationSearch;

import java.util.ArrayList;

public class ReviewSearch extends AppCompatActivity {

    ImageButton back_button;
    ImageButton menu_button;
    ArrayList<NaverLocationList> arrayresult;
    private NaverLocationSearch naverLocationSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_search);


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






    }


    public void openMenuTab(){
        Intent intent = new Intent(this, MenuScreen.class);
        startActivity(intent);
    }


    public void onSearch(View v){

        naverLocationSearch = new NaverLocationSearch();

        //TextView t1 = (TextView)findViewById(R.id.textView);
        EditText e1 = (EditText)findViewById(R.id.review_search);
        //ListView l1 = (ListView)findViewById(R.id.list_item);




        try {
            arrayresult = naverLocationSearch.execute(e1.getText().toString()).get();
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
