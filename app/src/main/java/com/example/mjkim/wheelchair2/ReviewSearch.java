package com.example.mjkim.wheelchair2;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mjkim.wheelchair2.NameSearch.FirebaseJson;
import com.example.mjkim.wheelchair2.NaverSearch.NaverLocationAdapter;
import com.example.mjkim.wheelchair2.NaverSearch.NaverLocationList;
import com.example.mjkim.wheelchair2.NaverSearch.NaverLocationSearch;

import java.util.ArrayList;

public class ReviewSearch extends AppCompatActivity{

    ImageButton back_button;
    ImageButton menu_button;
    Button search_button;
    int i;
    public static ArrayList<NaverLocationList> arrayresult;       // 네이버 지역 리스트
    ArrayList<NaverLocationList> naverLocationLists = new ArrayList<NaverLocationList>();
    private NaverLocationSearch naverLocationSearch;
    private NaverLocationAdapter adapter;            // 리스트뷰의 네이버지역검색 아답터
    private boolean lastItemVisibleFlag = false;    // 리스트 스크롤이 마지막 셀(맨 바닥)로 이동했는지 체크할 변수
    private int page = 0;                           // 페이징변수. 초기 값은 0 이다.
    private final int OFFSET = 10;                  // 한 페이지마다 로드할 데이터 갯수.
    private boolean mLockListView = false;          // 데이터 불러올때 중복안되게 하기위한 변수
    ProgressBar progressBar;                         // 데이터 로딩중을 표시할 프로그레스바
    int save = 1;


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

        FirebaseJson.reviewJson.clear();
        naverLocationSearch = new NaverLocationSearch();
        naverLocationLists = new ArrayList<NaverLocationList>();
        arrayresult = new ArrayList<NaverLocationList>();

        EditText e1 = (EditText)findViewById(R.id.review_search);




        try {
            arrayresult = naverLocationSearch.execute(e1.getText().toString()).get();
        }catch (Exception e){
            e.printStackTrace();
        }

        if(arrayresult.isEmpty()) {
            Toast.makeText(ReviewSearch.this, "검색 결과가 없습니다",
                    Toast.LENGTH_SHORT).show();
        }




        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ListView lv = (ListView) findViewById(R.id.location_list);
                adapter = new NaverLocationAdapter(ReviewSearch.this, arrayresult, save);
                lv.setAdapter(adapter);
            }
        }, 1200);



    }




}
