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
import android.widget.ImageView;
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
    TextView TagT1, TagT2, TagT3, TagT4, TagT5, TagT6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certain_review_detail);
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
        TagT1 = (TextView) findViewById(R.id.tag1);
        TagT2 = (TextView) findViewById(R.id.tag2);
        TagT3 = (TextView) findViewById(R.id.tag3);
        TagT4 = (TextView) findViewById(R.id.tag4);
        TagT5 = (TextView) findViewById(R.id.tag5);
        TagT6 = (TextView) findViewById(R.id.tag6);

        Intent intent = getIntent();

        //reviewLists = intent.getExtras().getStringArrayList("ARRAY");

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
}
