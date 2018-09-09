package com.example.mjkim.wheelchair2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.module.AppGlideModule;
import com.example.mjkim.wheelchair2.Review.ReviewList;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Logger;

public class CertainReviewDetail extends AppCompatActivity {
    ImageView imageShow1,imageShow2,imageShow3,imageShow4,imageShow5,imageShow6,imageShow7,imageShow8,imageShow9;
    ImageView tagShow1,tagShow2,tagShow3,tagShow4,tagShow5,tagShow6;
    RatingBar ratingBar;
    ImageButton menu_button;
    ImageButton back_button;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    StorageReference httpsReference;
    public ReviewList reviewList = new ReviewList();
    private Uri filePath;
    TextView emailT;
    TextView nameT;
    TextView reviewT;
    TextView reviewDetailT;
    TextView dateT;

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

        imageShow1 = (ImageView) findViewById(R.id.imageView1);
        imageShow2 = (ImageView) findViewById(R.id.imageView2);
        imageShow3 = (ImageView) findViewById(R.id.imageView3);
        imageShow4 = (ImageView) findViewById(R.id.imageView4);
        imageShow5 = (ImageView) findViewById(R.id.imageView5);
        imageShow6 = (ImageView) findViewById(R.id.imageView6);
        imageShow7 = (ImageView) findViewById(R.id.imageView7);
        imageShow8 = (ImageView) findViewById(R.id.imageView8);
        imageShow9 = (ImageView) findViewById(R.id.imageView9);

        emailT = (TextView) findViewById(R.id.email);
        nameT = (TextView) findViewById(R.id.name);
        reviewT = (TextView) findViewById(R.id.review_name);
        reviewDetailT = (TextView) findViewById(R.id.review_detail);
        dateT = (TextView) findViewById(R.id.date);


        Intent intent = getIntent();

        //reviewLists = intent.getExtras().getStringArrayList("ARRAY");

        float number = (float)intent.getExtras().getDouble("Rating");
        ratingBar.setRating((float)intent.getExtras().getDouble("Rating"));

        final String image1 = intent.getExtras().getString("IMAGE1");
        final String image2 = intent.getExtras().getString("IMAGE2");
        final String image3 = intent.getExtras().getString("IMAGE3");
        final String image4 = intent.getExtras().getString("IMAGE4");
        final String image5 = intent.getExtras().getString("IMAGE5");
        final String image6 = intent.getExtras().getString("IMAGE6");
        final String image7 = intent.getExtras().getString("IMAGE7");
        final String image8 = intent.getExtras().getString("IMAGE8");
        final String image9 = intent.getExtras().getString("IMAGE9");
        final String email = intent.getExtras().getString("Email");
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

        if(TagT1 == true) tagShow1.setImageResource(R.drawable.tag_checked_1);
        if(TagT2 == true) tagShow1.setImageResource(R.drawable.tag_checked_2);
        if(TagT3 == true) tagShow1.setImageResource(R.drawable.tag_checked_3);
        if(TagT4 == true) tagShow1.setImageResource(R.drawable.tag_checked_4);
        if(TagT5 == true) tagShow1.setImageResource(R.drawable.tag_checked_5);
        if(TagT6 == true) tagShow1.setImageResource(R.drawable.tag_checked_6);

        emailT.setText(email);
        nameT.setText(name);
        dateT.setText(date);
        reviewT.setText(review_name);
        reviewDetailT.setText(review);
//        System.out.println("이미지쓰"+image1);
        StorageReference ref = FirebaseStorage.getInstance().getReference("images/"+image1);
//        String path=ref.getPath();
//        storage.getReference().child("images/IMG_20180806_033819.jpg").getDownloadUrl();
//        System.out.println("이것이다"+image1+ "  "+ reviewList.getImageName());
//        storageRef.child("images").child(reviewList.getImageName());
//        String path=storageRef.getPath();
//        storage.getReference().child("image").child(reviewList.getImageName()).getDownloadUrl();
//        System.out.println("야래"+path);
//        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),);
//        imageShow1.setImageBitmap(bitmap);
//        StorageReference storageRef = storage.getReference();
//        StorageReference spaceRef = storageRef.child(image1);
        Glide.with(this /* context */)
                .load(ref)
                .into(imageShow1);

    }

    public void openMenuTab(){
        Intent intent = new Intent(this, MenuScreen.class);
        startActivity(intent);
    }
}
