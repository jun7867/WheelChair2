package com.example.mjkim.wheelchair2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class ReviewDetail extends AppCompatActivity {

    ImageButton back_button, menu_button, more_blog;
    TextView location_name, address_name, phone_number;
    String name = "";

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


        Intent intent = getIntent();

        location_name.setText(intent.getExtras().getString("NAME"));
        name = intent.getExtras().getString("NAME");
        address_name.setText(intent.getExtras().getString("ROAD_ADDRESS"));
        phone_number.setText(intent.getExtras().getString("TELEPHONE"));


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
}
