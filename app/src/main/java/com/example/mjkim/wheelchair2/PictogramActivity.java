package com.example.mjkim.wheelchair2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

public class PictogramActivity extends AppCompatActivity {
    ImageButton back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictogram);
        int img[] = {
                R.drawable.login,R.drawable.logo,R.drawable.wheel,R.drawable.login,R.drawable.login,R.drawable.login,R.drawable.login,
                R.drawable.login,R.drawable.login,R.drawable.login,R.drawable.login,R.drawable.login,R.drawable.login,R.drawable.login,
                R.drawable.login
        }; // 후에 사진 변경하기.
        back_button = (ImageButton)findViewById(R.id.back_b);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        // 커스텀 아답타 생성
        MyAdapter adapter = new MyAdapter (
                getApplicationContext(),
                R.layout.item,       // GridView 항목의 레이아웃 item.xml
                img);    // 데이터

        GridView gv = (GridView)findViewById(R.id.gridview1);
        gv.setAdapter(adapter);  // 커스텀 아답타를 GridView 에 적용

        final TextView tv = (TextView) findViewById(R.id.textView1);

        // GridView 아이템을 클릭하면 상단 텍스트뷰에 position 출력
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                tv.setText("픽토그램."+id);  // 나중에 수정할것.
            }
        });


    }
    public void openMenuTab(){
        Intent intent = new Intent(this, MenuScreen.class);
        startActivity(intent);
    }
}
