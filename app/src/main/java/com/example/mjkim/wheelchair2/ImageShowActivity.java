package com.example.mjkim.wheelchair2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ImageShowActivity extends AppCompatActivity {
    ImageView imagebig;
    StorageReference ref,ref2,ref3,ref4,ref5,ref6,ref7,ref8,ref9;
    String image1,image2,image3,image4,image5,image6,image7,image8,image9;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_show);
        imagebig=(ImageView)findViewById(R.id.imageBigView);
        Intent intent = getIntent();
        image1 = intent.getExtras().getString("IMAGE1");
        image2 = intent.getExtras().getString("IMAGE2");
        image3 = intent.getExtras().getString("IMAGE3");
        image4 = intent.getExtras().getString("IMAGE4");
        image5 = intent.getExtras().getString("IMAGE5");
        image6 = intent.getExtras().getString("IMAGE6");
        image7 = intent.getExtras().getString("IMAGE7");
        image8 = intent.getExtras().getString("IMAGE8");
        image9 = intent.getExtras().getString("IMAGE9");

        ref = FirebaseStorage.getInstance().getReference("images/"+image1);
        ref2 = FirebaseStorage.getInstance().getReference("images/"+image2);
        ref3 = FirebaseStorage.getInstance().getReference("images/"+image3);
        ref4 = FirebaseStorage.getInstance().getReference("images/"+image4);
        ref5 = FirebaseStorage.getInstance().getReference("images/"+image5);
        ref6 = FirebaseStorage.getInstance().getReference("images/"+image6);
        ref7 = FirebaseStorage.getInstance().getReference("images/"+image7);
        ref8 = FirebaseStorage.getInstance().getReference("images/"+image8);
        ref9 = FirebaseStorage.getInstance().getReference("images/"+image9);

        if(image1!=null) {
                Glide.with(this /* context */)
                        .load(ref)
                        .into(imagebig);
            }
         if(image2!=null) {
             Glide.with(this /* context */)
                     .load(ref2)
                     .into(imagebig);
         }
        if(image3!=null) {
            Glide.with(this /* context */)
                    .load(ref3)
                    .into(imagebig);
        }
        if(image4!=null) {
            Glide.with(this /* context */)
                    .load(ref4)
                    .into(imagebig);
        }
        if(image5!=null) {
            Glide.with(this /* context */)
                    .load(ref5)
                    .into(imagebig);
        }
        if(image6!=null) {
            Glide.with(this /* context */)
                    .load(ref6)
                    .into(imagebig);
        }
        if(image7!=null) {
            Glide.with(this /* context */)
                    .load(ref7)
                    .into(imagebig);
        }
        if(image8!=null) {
            Glide.with(this /* context */)
                    .load(ref8)
                    .into(imagebig);
        }
        if(image9!=null){
            Glide.with(this /* context */)
                    .load(ref9)
                    .into(imagebig);
    }}
}
