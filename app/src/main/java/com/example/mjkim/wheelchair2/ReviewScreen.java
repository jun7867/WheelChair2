package com.example.mjkim.wheelchair2;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mjkim.wheelchair2.Review.ReviewData;
import com.example.mjkim.wheelchair2.Review.ReviewList;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class ReviewScreen extends AppCompatActivity {

    private static final int pick_from_camera = 0;
    private static final int pick_from_album = 1; // 갤러리 불러올때 요청 상수.
    private int id_view;
    private FirebaseStorage storage;
    StorageReference storageRef;
    public ReviewList reviewList = new ReviewList();
    ReviewData reviewData = new ReviewData();
    String imagePath1="";
    String imagePath2="";
    String imagePath3="";
    String phone ="";

    double rating;
    //Boolean tag[] = new Boolean[6];
    Boolean tag1,tag2,tag3,tag4,tag5,tag6;
    String review;

    ImageButton back_button;
    ImageButton menu_button;
    Button map_search;
    ImageView picture1,picture2,picture3;
    EditText review_text;
    RatingBar review_rating_bar;
    CheckBox chk[] = new CheckBox[6];
    TextView location_name, address_name, phone_number;
    static String downloadUrl;
    static String name;
    int pic1;
    int pic2;
    int pic3;
    int mapx, mapy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
        }
        setContentView(R.layout.activity_review_screen);

        storage=FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        Integer[] numChkIDs = { R.id.check_box_b1, R.id.check_box_b2, R.id.check_box_b3,
                R.id.check_box_b4, R.id.check_box_b5, R.id.check_box_b6};

        back_button = (ImageButton)findViewById(R.id.back_b);
        menu_button = (ImageButton)findViewById(R.id.menu_b);
        map_search = (Button)findViewById(R.id.current_b);
        picture1 = (ImageView)findViewById(R.id.picture1);
        picture2 = (ImageView)findViewById(R.id.picture2);
        picture3 = (ImageView)findViewById(R.id.picture3);
        review_text = (EditText) findViewById(R.id.review_text);
        review_rating_bar = (RatingBar) findViewById(R.id.ratingBar);
        location_name = (TextView) findViewById(R.id.location_name);
        address_name = (TextView)findViewById(R.id.address_name);
        phone_number = (TextView)findViewById(R.id.telephone_number);



        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        menu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){ openMenuTab(); }});

        map_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { openMapSearch();
            }
        });



        //시설정보
        Intent intent = getIntent();

        location_name.setText(intent.getExtras().getString("NAME"));
        address_name.setText(intent.getExtras().getString("ROAD_ADDRESS"));
        phone_number.setText(intent.getExtras().getString("TELEPHONE"));
        phone = intent.getExtras().getString("TELEPHONE");
        mapx = intent.getExtras().getInt("MAPX");
        mapy = intent.getExtras().getInt("MAPY");


        phone_number.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent1 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                startActivity(intent1);
            }
        });





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


    }
    public String getPath(Uri uri){
        String [] proj={MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader=new CursorLoader(this,uri,proj,null,null,null);

        Cursor cursor=cursorLoader.loadInBackground();
        int index=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        return cursor.getString(index);
    }

    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==pick_from_album) {
            if (id_view == R.id.picture1) {
                imagePath1 = getPath(data.getData());
                File f1 = new File(imagePath1);
                picture1.setImageURI(Uri.fromFile(f1));
                pic1=1;
            }
            if (id_view == R.id.picture2) {
                imagePath2 = getPath(data.getData());
                File f2 = new File(imagePath2);
                picture2.setImageURI(Uri.fromFile(f2));
                pic2=1;
            }
            if (id_view == R.id.picture3) {
                imagePath3 = getPath(data.getData());
                File f3 = new File(imagePath3);
                picture3.setImageURI(Uri.fromFile(f3));
                pic3=1;
            }
        }
//            if(requestCode==pick_from_album){
//                File f=new File(imagePath);
//                if(id_view == R.id.picture1) {
//                    picture1.setImageURI(Uri.fromFile(f));
//                    pic1=1;
//                }
//                if(id_view == R.id.picture2){
//                    pic2=1;
//                    picture2.setImageURI(Uri.fromFile(f));
//                }
//                if(id_view == R.id.picture3){
//                    pic3=1;
//                    picture3.setImageURI(Uri.fromFile(f));
//                }
//            }
        }

   private void upload(String uri,String uri2,String uri3){
        System.out.println("시작");

           Uri file = Uri.fromFile(new File(uri));
           StorageReference riverRef=storageRef.child("images/"+file.getLastPathSegment());
           UploadTask uploadTask = riverRef.putFile(file);
           uploadTask.addOnFailureListener(new OnFailureListener() {
               @Override
               public void onFailure(@NonNull Exception e) {

               }
           }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
               @Override
               public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                   downloadUrl=taskSnapshot.toString();
                   System.out.println("다운1: "+downloadUrl);
                   reviewList.setImageUrl1(downloadUrl);
                   if(pic2!=1 && pic3!=1) reviewData.saveData(name,reviewList);
               }
           });

            Uri file2 = Uri.fromFile(new File(uri2));
            StorageReference riverRef2=storageRef.child("images/"+file2.getLastPathSegment());
            UploadTask uploadTask2 = riverRef2.putFile(file2);
            uploadTask2.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    downloadUrl=taskSnapshot.toString();
                    System.out.println("다운2: "+downloadUrl);
                    reviewList.setImageUrl2(downloadUrl);
                    if(pic3!=1) reviewData.saveData(name,reviewList);
                }
            });


           Uri file3 = Uri.fromFile(new File(uri3));
           StorageReference riverRef3=storageRef.child("images/"+file3.getLastPathSegment());
           UploadTask uploadTask3 = riverRef3.putFile(file3);
           uploadTask3.addOnFailureListener(new OnFailureListener() {
               @Override
               public void onFailure(@NonNull Exception e) {

               }
           }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
               @Override
               public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                   downloadUrl=taskSnapshot.toString();
                   System.out.println("다운3: "+downloadUrl);
                   reviewList.setImageUrl3(downloadUrl);
                   reviewData.saveData(name,reviewList);
               }
           });



   }
    public void openMapSearch(){
        Intent intent = new Intent(this, WatchLocationActivity.class);
        intent.putExtra("NAME", name);
        intent.putExtra("MAPX", mapx);
        intent.putExtra("MAPY", mapy);
        startActivity(intent);
    }


    public void onFinish(View view){
        System.out.println("패스1: "+imagePath1);
        System.out.println("패스2: "+imagePath2);
        System.out.println("패스3: "+ imagePath3);



        Intent review_intent = getIntent();

        name = review_intent.getExtras().getString("NAME");
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

        if(imagePath1 !=null || imagePath2 !=null || imagePath3 !=null) {
            upload(imagePath1, imagePath2, imagePath3);
        }
        if(imagePath1 ==null && imagePath2 ==null && imagePath3==null) {
            reviewData.saveData(name, reviewList);
        }

        Toast.makeText(this, "리뷰가 등록되었습니다 ", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, FirstScreen.class);
        startActivity(intent);



    }

}
