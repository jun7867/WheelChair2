package com.example.mjkim.wheelchair2;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mjkim.wheelchair2.NameSearch.NameLocationAdapter;
import com.example.mjkim.wheelchair2.NaverSearch.NaverLocationAdapter;
import com.example.mjkim.wheelchair2.NaverSearch.NaverLocationList;
import com.example.mjkim.wheelchair2.NaverSearch.NaverLocationSearch;
import com.example.mjkim.wheelchair2.NameSearch.FirebaseJson;
import com.example.mjkim.wheelchair2.Review.ReviewList;

import java.util.ArrayList;

public class NameSearchActivity extends AppCompatActivity {


    ImageButton back_button;
    ImageButton menu_button;
    int i, save = 0;
    //public static ArrayList<ReviewList> reviewlist;       // 네이버 지역 리스트
    public static ArrayList<NaverLocationList> locationLists;    // reviewlist -> LocationList 수정(희석)
    ArrayList<NaverLocationList> naverLocationLists = new ArrayList<NaverLocationList>();
    private NaverLocationSearch naverLocationSearch;
    private NaverLocationAdapter adapter;            // 리스트뷰의 네이버지역검색 아답터
    private FirebaseJson firebaseJson = new FirebaseJson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_search);



        //버튼 선언
        back_button = (ImageButton) findViewById(R.id.back_b);
        menu_button = (ImageButton) findViewById(R.id.menu_b);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        menu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMenuTab();
            }
        });
    }


    public void openMenuTab() {
        Intent intent = new Intent(this, MenuScreen.class);
        startActivity(intent);
    }

    public void onSearch(View v) {

        FirebaseJson.reviewJson.clear();
        naverLocationSearch = new NaverLocationSearch();
        naverLocationLists = new ArrayList<NaverLocationList>();
        locationLists = new ArrayList<NaverLocationList>();



        EditText e1 = (EditText) findViewById(R.id.review_search);


        try {
            locationLists = naverLocationSearch.execute(e1.getText().toString()).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(locationLists.isEmpty()) {
            Toast.makeText(NameSearchActivity.this, "검색 결과가 없습니다",
                    Toast.LENGTH_SHORT).show();
        }



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ListView lv = (ListView) findViewById(R.id.review_location_list);
                adapter = new NaverLocationAdapter(NameSearchActivity.this, locationLists, save);
                lv.setAdapter(adapter);
            }
        }, 1000);

//        ListView lv = (ListView) findViewById(R.id.review_location_list);
//        adapter = new NaverLocationAdapter(NameSearchActivity.this, locationLists, save);
//        lv.setAdapter(adapter);


        //System.out.println("샘플: " + firebaseJson.json);

    }

}
