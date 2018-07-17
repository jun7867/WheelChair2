package com.example.mjkim.wheelchair2;

import android.content.Intent;
import android.os.Handler;
import android.os.HardwarePropertiesManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LoadingScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_screen);

        Handler handler =new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(LoadingScreen.this,FirstScreen.class);
                startActivity(intent);
                finish();
            }
        },2000);
    }
}
