package com.example.mjkim.wheelchair2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mjkim.wheelchair2.NaverSearch.NaverLocationAdapter;

public class ReviewDetail extends AppCompatActivity {

    ImageButton back_button;
    ImageButton menu_button;
    TextView text;

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


        text = (TextView) findViewById(R.id.sample);


        Intent intent = getIntent();
        text.setText(intent.getExtras().getString("NAME") + "\n" +
                intent.getExtras().getString("LINK") + "\n" +
                intent.getExtras().getString("DESCRIPTION") + "\n" +
                intent.getExtras().getString("ADDRESS") + "\n" +
                intent.getExtras().getString("ROAD_ADDRESS") + "\n" +
                intent.getExtras().getInt("MAPX") + "\n" +
                intent.getExtras().getInt("MAPY") + "\n");



    }

    public void openMenuTab(){
        Intent intent = new Intent(this, MenuScreen.class);
        startActivity(intent);
    }
}
