package com.example.mjkim.wheelchair2.Login;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mjkim.wheelchair2.R;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

public class UserImformationActivity extends AppCompatActivity {
    TextView name;
    TextView email;
    TextView proId;
    TextView uid;
    private FirebaseAuth auth;
    Button logout, my_review;
    ImageButton back_button;
    FirebaseUser currentUser; //로그인 된 유저정보 변수
    FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_imformation);
        auth= FirebaseAuth.getInstance();
        name=(TextView)findViewById(R.id.nameId);
        email=(TextView)findViewById(R.id.emailId);
//        proId=(TextView)findViewById(R.id.providerId);
//        uid=(TextView)findViewById(R.id.uid);
        logout=(Button)findViewById(R.id.logout);
        my_review=(Button)findViewById(R.id.my_review_b);
        name.setText(auth.getCurrentUser().getDisplayName());
        email.setText(auth.getCurrentUser().getEmail());
        back_button = (ImageButton) findViewById(R.id.back_b);

//        proId.setText(auth.getCurrentUser().getProviderId());
//        uid.setText(auth.getCurrentUser().getUid());


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                LoginManager.getInstance().logOut(); //페이스북 로그아웃
                finish();
                Intent intent =new Intent(UserImformationActivity.this,LoginScreen.class);
                startActivity(intent);

            }
        });

        my_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =new Intent(UserImformationActivity.this,MyReview.class);
                startActivity(intent);

            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
//    @Override
//    public void onStop(){
//        super.onStop();
//
//        currentUser = mAuth.getCurrentUser();
//
//        Intent intent = new Intent(UserImformationActivity.this,LoginScreen.class);
//        startActivity(intent);
//        finish();
//
//    }


}
