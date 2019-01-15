package com.example.mjkim.wheelchair2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class CertainReviewDetail extends AppCompatActivity {
    ImageView picture1,picture2, picture3, picture4, picture5, picture6, picture7, picture8, picture9;
    String image1,image2,image3,image4,image5,image6,image7,image8,image9;
    ImageView tagShow1,tagShow2,tagShow3,tagShow4,tagShow5,tagShow6;
    StorageReference ref,ref2,ref3,ref4,ref5,ref6,ref7,ref8,ref9;
    RatingBar ratingBar;
    ImageButton menu_button;
    ImageButton back_button;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    TextView emailT;
    TextView nameT;
    TextView reviewT;
    TextView reviewDetailT;
    TextView dateT;
    ScrollView scrollView;
    TextView TagT1, TagT2, TagT3, TagT4, TagT5, TagT6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_certain_review_detail);


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

        ratingBar = (RatingBar) findViewById(R.id.ratingBar2);

        tagShow1 = (ImageView) findViewById(R.id.tag_done_1);
        tagShow2 = (ImageView) findViewById(R.id.tag_done_2);
        tagShow3 = (ImageView) findViewById(R.id.tag_done_3);
        tagShow4 = (ImageView) findViewById(R.id.tag_done_4);
        tagShow5 = (ImageView) findViewById(R.id.tag_done_5);
        tagShow6 = (ImageView) findViewById(R.id.tag_done_6);

        picture1 = (ImageView) findViewById(R.id.picture1);
        picture2 = (ImageView) findViewById(R.id.picture2);
        picture3 = (ImageView) findViewById(R.id.picture3);
        picture4 = (ImageView) findViewById(R.id.picture4);
        picture5 = (ImageView) findViewById(R.id.picture5);
        picture6 = (ImageView) findViewById(R.id.picture6);
        picture7 = (ImageView) findViewById(R.id.picture7);
        picture8 = (ImageView) findViewById(R.id.picture8);
        picture9 = (ImageView) findViewById(R.id.picture9);

        emailT = (TextView) findViewById(R.id.email);
        nameT = (TextView) findViewById(R.id.name);
        reviewT = (TextView) findViewById(R.id.review_name);
        reviewDetailT = (TextView) findViewById(R.id.review_detail);
        dateT = (TextView) findViewById(R.id.date);


        Intent intent = getIntent();

        //reviewLists = intent.getExtras().getStringArrayList("ARRAY");

        float number = (float)intent.getExtras().getDouble("Rating");
        ratingBar.setRating((float)intent.getExtras().getDouble("Rating"));

        image1 = intent.getExtras().getString("IMAGE1");
        image2 = intent.getExtras().getString("IMAGE2");
        image3 = intent.getExtras().getString("IMAGE3");
        image4 = intent.getExtras().getString("IMAGE4");
        image5 = intent.getExtras().getString("IMAGE5");
        image6 = intent.getExtras().getString("IMAGE6");
        image7 = intent.getExtras().getString("IMAGE7");
        image8 = intent.getExtras().getString("IMAGE8");
        image9 = intent.getExtras().getString("IMAGE9");
        final String email = intent.getExtras().getString("Email");
        System.out.println("heeseok : " + email);
        final String name = intent.getExtras().getString("Name");
        final String review = intent.getExtras().getString("Review");
        final String review_name = intent.getExtras().getString("Review_name");
        final String date = intent.getExtras().getString("Date");
        final boolean TagT1 = intent.getExtras().getBoolean("Tag1");
        final boolean TagT2 = intent.getExtras().getBoolean("Tag2");
        final boolean TagT3 = intent.getExtras().getBoolean("Tag3");
        final boolean TagT4 = intent.getExtras().getBoolean("Tag4");
        final boolean TagT5 = intent.getExtras().getBoolean("Tag5");
        final boolean TagT6 = intent.getExtras().getBoolean("Tag6");

        if(TagT1 == true) tagShow1.setImageResource(R.drawable.restroom);
        if(TagT2 == true) tagShow2.setImageResource(R.drawable.parking);
        if(TagT3 == true) tagShow3.setImageResource(R.drawable.elevator);
        if(TagT4 == true) tagShow4.setImageResource(R.drawable.slope);
        if(TagT5 == true) tagShow5.setImageResource(R.drawable.table);
        if(TagT6 == true) tagShow6.setImageResource(R.drawable.assistant);

        emailT.setText(email);
        nameT.setText(name);
        dateT.setText(date);
        reviewT.setText(review_name);
        reviewDetailT.setText(review);
        ref = FirebaseStorage.getInstance().getReference("images/"+image1);
        ref2 = FirebaseStorage.getInstance().getReference("images/"+image2);
        ref3 = FirebaseStorage.getInstance().getReference("images/"+image3);
        ref4 = FirebaseStorage.getInstance().getReference("images/"+image4);
        ref5 = FirebaseStorage.getInstance().getReference("images/"+image5);
        ref6 = FirebaseStorage.getInstance().getReference("images/"+image6);
        ref7 = FirebaseStorage.getInstance().getReference("images/"+image7);
        ref8 = FirebaseStorage.getInstance().getReference("images/"+image8);
        ref9 = FirebaseStorage.getInstance().getReference("images/"+image9);

        if(image1!="")
            Glide.with(this /* context */)
                    .load(ref)
                    .into(picture1);

        if(image2!="")
        Glide.with(this /* context */)
                .load(ref2)
                .into(picture2);

        if(image3!="")
        Glide.with(this /* context */)
                .load(ref3)
                .into(picture3);

        if(image4!="")
        Glide.with(this /* context */)
                .load(ref4)
                .into(picture4);

        if(image5!="")
        Glide.with(this /* context */)
                .load(ref5)
                .into(picture5);

        if(image6!="")
        Glide.with(this /* context */)
                .load(ref6)
                .into(picture6);

        if(image7!="")
        Glide.with(this /* context */)
                .load(ref7)
                .into(picture7);

        if(image8!="")
        Glide.with(this /* context */)
                .load(ref8)
                .into(picture8);

        if(image9!="")
        Glide.with(this /* context */)
                .load(ref9)
                .into(picture9);

    }
    public void onClick(View view){
        Intent intent = new Intent(this, ImageShowActivity.class);

        if(view.getId()== R.id.picture1){
            intent.putExtra("IMAGE1",image1);
        }
        if(view.getId()== R.id.picture2){
            intent.putExtra("IMAGE2",image2);
        }
        if(view.getId()== R.id.picture3){
            intent.putExtra("IMAGE3",image3);
        }
        if(view.getId()== R.id.picture4){
            intent.putExtra("IMAGE4",image4);
        }
        if(view.getId()== R.id.picture5){
            intent.putExtra("IMAGE5",image5);
        }
        if(view.getId()== R.id.picture6){
            intent.putExtra("IMAGE6",image6);
        }
        if(view.getId()== R.id.picture7){
            intent.putExtra("IMAGE7",image7);
        }
        if(view.getId()== R.id.picture8){
            intent.putExtra("IMAGE8",image8);
        }
        if(view.getId()== R.id.picture9){
            intent.putExtra("IMAGE9",image9);
        }
        startActivity(intent);
    }
    public void openMenuTab(){
        Intent intent = new Intent(this, MenuScreen.class);
        startActivity(intent);
    }
}
