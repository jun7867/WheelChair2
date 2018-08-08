package com.example.mjkim.wheelchair2;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.mjkim.wheelchair2.Review.ReviewData;
import com.example.mjkim.wheelchair2.Review.ReviewList;

import java.io.File;

public class ReviewScreen extends AppCompatActivity {

    private static final int pick_from_camera = 0;
    private static final int pick_from_album = 1;
    private int id_view;
    public ReviewList reviewList = new ReviewList();
    ReviewData reviewData = new ReviewData();


    double rating;
    //Boolean tag[] = new Boolean[6];
    Boolean tag1,tag2,tag3,tag4,tag5,tag6;
    String review;



    ImageButton back_button;
    ImageButton menu_button;
    ImageView picture1,picture2,picture3;
    EditText review_text;
    RatingBar review_rating_bar;
    CheckBox chk[] = new CheckBox[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_screen);

        Integer[] numChkIDs = { R.id.check_box_b1, R.id.check_box_b2, R.id.check_box_b3,
                R.id.check_box_b4, R.id.check_box_b5, R.id.check_box_b6};

        back_button = (ImageButton)findViewById(R.id.back_b);
        menu_button = (ImageButton)findViewById(R.id.menu_b);
        picture1 = (ImageView)findViewById(R.id.picture1);
        picture2 = (ImageView)findViewById(R.id.picture2);
        picture3 = (ImageView)findViewById(R.id.picture3);
        review_text = (EditText) findViewById(R.id.review_text);
        review_rating_bar = (RatingBar) findViewById(R.id.ratingBar);


        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        menu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){ openMenuTab(); }});



        //모든 정보 저장및 확인

        //별점
        review_rating_bar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float temp_rating, boolean fromUser) { rating = temp_rating; }});

        //체크박스
        for(int i = 0; i < 6; i++){ chk[i] = (CheckBox)findViewById(numChkIDs[i]); }



    }


    public void openMenuTab(){
        Intent intent = new Intent(this, MenuScreen.class);
        startActivity(intent);
    }

    public void onClick(View view){

        id_view = view.getId();


        if(view.getId() == R.id.picture1 || view.getId() == R.id.picture2 || view.getId() == R.id.picture3){
            DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) { doTakePhotoAction(); }
            };

            DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) { doTakeAlbumAction(); }
            };

            DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) { dialogInterface.dismiss(); }
            };

            new AlertDialog.Builder(this)
                    .setTitle("업로드할 이미지 선택")
                    .setPositiveButton("앨범선택", albumListener)
                    .setNeutralButton("취소", cancelListener)
                    .setNegativeButton("사진촬영", cameraListener)
                    .show();

        }
    }


    public void onFinish(View view){

        Intent review_intent = getIntent();

        String name = review_intent.getExtras().getString("NAME");
        double mapx = (double)review_intent.getExtras().getInt("MAPX");
        double mapy = (double)review_intent.getExtras().getInt("MAPY");

        //리뷰 문장 받아오기
        review = review_text.getText().toString();

        if(chk[0].isChecked() == true) tag1 = true; else tag1 = false;
        if(chk[1].isChecked() == true) tag2 = true; else tag2 = false;
        if(chk[2].isChecked() == true) tag3 = true; else tag3 = false;
        if(chk[3].isChecked() == true) tag4 = true; else tag4 = false;
        if(chk[4].isChecked() == true) tag5 = true; else tag5 = false;
        if(chk[5].isChecked() == true) tag6 = true; else tag6 = false;


        reviewList = new ReviewList(rating, tag1,tag2,tag3,tag4,tag5,tag6, review,mapx,mapy);


        reviewData.saveData(name, reviewList);




        Toast.makeText(this, "리뷰가 등록되었습니다 ", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, FirstScreen.class);
        startActivity(intent);

    }




    public void doTakePhotoAction(){ //카메라 촬영후 이미지 가져오기
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        //임시 사용할 파일의 경로 생성
        if(intent.resolveActivity(getPackageManager()) != null){ startActivityForResult(intent, pick_from_camera); }

    }
    public void doTakeAlbumAction(){ //앨범에서 이미지 가져오기

        //앨범 호출

        Intent intent = new Intent(Intent.ACTION_PICK);

        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, pick_from_album );

        //임시 사용할 파일의 경로 생성
        //Intent intent = new Intent(Intent.ACTION_PICK);
        //intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        //startActivityForResult(intent,pick_from_album );
    }

    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data){

        if(resultCode == RESULT_OK){
            if(requestCode == pick_from_camera){
                Bundle extras = data.getExtras();

                Bitmap imageBitmap = (Bitmap) extras.get("data");
                if(id_view == R.id.picture1) picture1.setImageBitmap(imageBitmap);
                if(id_view == R.id.picture2) picture2.setImageBitmap(imageBitmap);
                if(id_view == R.id.picture3) picture3.setImageBitmap(imageBitmap);
            }
            if(requestCode == pick_from_album){
                if(id_view == R.id.picture1) picture1.setImageURI(data.getData());
                if(id_view == R.id.picture2) picture2.setImageURI(data.getData());
                if(id_view == R.id.picture3) picture3.setImageURI(data.getData());
            }
        }
    }
}
