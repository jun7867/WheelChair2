package com.example.mjkim.wheelchair2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class FirstScreen extends AppCompatActivity {

    ImageButton menu_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_screen);

        menu_button = (ImageButton)findViewById(R.id.menu_b);

        menu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMenuTab();
            }
        });


    }

    public void onClick(View view){
        if(view.getId()==R.id.surround_b){
            Intent intent=new Intent(this,GoogleMapActivity.class);
            startActivity(intent);
        }
        if(view.getId()==R.id.name_b){
            Intent intent=new Intent(this,SearchActivity.class);
            startActivity(intent);
        }
    }
    public void openMenuTab(){
        Intent intent = new Intent(this, MenuScreen.class);
        startActivity(intent);
    }
}
