package com.example.mjkim.wheelchair2;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mjkim.wheelchair2.Login.LoginScreen;
import com.example.mjkim.wheelchair2.Login.User;
import com.example.mjkim.wheelchair2.Login.UserImformationActivity;
import com.example.mjkim.wheelchair2.naver_map_fragment.FragmentMapActivity;
import com.nhn.android.maps.NMapLocationManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.FileInputStream;

public class FirstScreen extends AppCompatActivity {

    ImageButton menu_button;
    ImageButton login_button;
    private BackPressCloseHandler backPressCloseHandler;


    private LocationManager locationManager;

    private FirebaseAuth mAuth; // 이메일 비밀번호 로그인 모듈변수
    private FirebaseUser currentUser; //로그인 된 유저정보 변수
    private TextView mTextMessage;
//    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
//            = new BottomNavigationView.OnNavigationItemSelectedListener() {
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//            switch (menuItem.getItemId()) {
//                case R.id.navigation_home:
//                    mTextMessage.setText(currentUser.getEmail());
//                    return true;
//                case R.id.navigation_dashboard:
//                    mTextMessage.setText(currentUser.getUid());
//                    return true;
//                case R.id.navigation_notifications:
//                    mTextMessage.setText(currentUser.getEmail());
//                    return true;
//            }
//
//            return false;
//        }
//    };
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

        login_button = (ImageButton)findViewById(R.id.login_b);

        login_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(LoginScreen.save == 0) openLoginTab();
                else if(LoginScreen.save == 1) openUserTab();

            }
        });

        backPressCloseHandler = new BackPressCloseHandler(this);


        mAuth=FirebaseAuth.getInstance();
  //      mTextMessage=(TextView)findViewById(R.id.message);
  //      BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
  //      navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }
//    //로그인 되어있으면 currentUser 변수에 유저정보 할당. 아닌경우 login 페이지로 이동!
//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        currentUser = mAuth.getCurrentUser();
//        if(currentUser == null){
//            startActivity(new Intent(FirstScreen.this, LoginScreen.class));
//            finish();
//        }
//    }


    public void onClick(View view){
        if(view.getId()== R.id.surround_b){
            Intent intent=new Intent(this,FindNearLocationActivity.class);
            startActivity(intent);
        }
        if(view.getId()== R.id.name_b){
            Intent intent=new Intent(this,SearchActivity.class);
            startActivity(intent);
        }
        if(view.getId() == R.id.review_b){
            Intent intent=new Intent(this,ReviewSearch.class);
            startActivity(intent);

        }
    }
    public void openMenuTab(){
        Intent intent = new Intent(this, MenuScreen.class);
        startActivity(intent);
    }

    public void openLoginTab(){
        Intent intent = new Intent(this, LoginScreen.class);
        startActivity(intent);
    }

    public void openUserTab(){
        Intent intent = new Intent(this, UserImformationActivity.class);
        startActivity(intent);
    }


}
