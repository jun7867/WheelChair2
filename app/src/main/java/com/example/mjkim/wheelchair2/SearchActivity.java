package com.example.mjkim.wheelchair2;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class SearchActivity extends AppCompatActivity {

    ImageButton back_button;
    ImageButton menu_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

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
            public void onClick(View view) {
                openMenuTab();
            }
        });
    }


    public void openMenuTab(){
        Intent intent = new Intent(this, MenuScreen.class);
        startActivity(intent);
    }

    public void searchClick(View view){
        Intent intent = new Intent(this, GoogleMapActivity.class);
        startActivity(intent);
    }
}
