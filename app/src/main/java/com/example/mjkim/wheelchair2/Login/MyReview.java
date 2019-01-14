//package com.example.mjkim.wheelchair2.Login;
//
//import android.content.Intent;
//import android.os.Handler;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ImageButton;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import com.example.mjkim.wheelchair2.MenuScreen;
//import com.example.mjkim.wheelchair2.NameSearch.FirebaseJson;
//import com.example.mjkim.wheelchair2.R;
//import com.example.mjkim.wheelchair2.Review.ReviewJson;
//import com.example.mjkim.wheelchair2.Review.ReviewList;
//import com.google.firebase.auth.FirebaseAuth;
//
//import java.util.ArrayList;
//
//public class MyReview extends AppCompatActivity {
//
//    private FirebaseAuth auth;
//    private UserReviewAdapter userReviewAdapter;
//    public static ArrayList<ReviewList> userReviewLists;
//    private FirebaseJson firebaseJson;
//    private static String json;
//    private String full_json;
//    public ReviewJson fullReviewJson = new ReviewJson();
//    private TextView review_count;
//
//    TextView location_name, address_name, phone_number;
//    private String name;
//    private String review_name;
//    int mapx, mapy;
//    private double rating;
//    private Boolean tag1;
//    private Boolean tag2;
//    private Boolean tag3;
//    private Boolean tag4;
//    private Boolean tag5;
//    private Boolean tag6;
//    private String review;
//    private String reviewer_name;
//    private String email;
//    private double location_mapx;
//    private double location_mapy;
//    private String imageUrl1;
//    private String imageUrl2;
//    private String imageUrl3;
//    private String imageUrl4;
//    private String imageUrl5;
//    private String imageUrl6;
//    private String imageUrl7;
//    private String imageUrl8;
//    private String imageUrl9;
//    private String date;
//    String phone = "";
//    String address = "";
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_my_review);
//
//        ImageButton back_button;
//        ImageButton menu_button;
//        back_button = (ImageButton)findViewById(R.id.back_b);
//        menu_button = (ImageButton)findViewById(R.id.menu_b);
//        review_count = (TextView)findViewById(R.id.review_count);
//        firebaseJson = new FirebaseJson();
//
//
////        getUserReviews();
//        showUserReview();
//
//        back_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
//
//        menu_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view){ openMenuTab(); }});
//
//
//
//
//    }
//
//
//    public void openMenuTab(){
//        Intent intent = new Intent(this, MenuScreen.class);
//        startActivity(intent);
//    }
//
//    // 리뷰들을 리스트뷰에 띄움
//    public void showUserReview() {
//        Intent intent = getIntent();
//        userReviewLists = getUserReviews();
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                ListView userReviewListView = (ListView) findViewById(R.id.review_list);
//                userReviewAdapter = new UserReviewAdapter(MyReview.this, userReviewLists);
//                userReviewListView.setAdapter(userReviewAdapter);
//            }
//        }, 1000);
//
//
//
//
////        location_name.setText(intent.getExtras().getString("NAME"));
////        name = intent.getExtras().getString("NAME");
////        address_name.setText(intent.getExtras().getString("ROAD_ADDRESS"));
////        address = intent.getExtras().getString("ROAD_ADDRESS");
////        phone_number.setText(intent.getExtras().getString("TELEPHONE"));
////        phone = intent.getExtras().getString("TELEPHONE");
////        mapx = intent.getExtras().getInt("MAPX");
////        mapy = intent.getExtras().getInt("MAPY");
////        rating = intent.getExtras().getDouble("RATING");
////        tag1 = intent.getExtras().getBoolean("TAG1");
////        tag2 = intent.getExtras().getBoolean("TAG2");
////        tag3 = intent.getExtras().getBoolean("TAG3");
////        tag4 = intent.getExtras().getBoolean("TAG4");
////        tag5 = intent.getExtras().getBoolean("TAG5");
////        tag6 = intent.getExtras().getBoolean("TAG6");
////        review = intent.getExtras().getString("STRING");
////        email = intent.getExtras().getString("EMAIL");
////        imageUrl1 = intent.getExtras().getString("IMAGEURL1");
////        imageUrl2 = intent.getExtras().getString("IMAGEURL2");
////        imageUrl3 = intent.getExtras().getString("IMAGEURL3");
////        imageUrl4 = intent.getExtras().getString("IMAGEURL4");
////        imageUrl5 = intent.getExtras().getString("IMAGEURL5");
////        imageUrl6 = intent.getExtras().getString("IMAGEURL6");
////        imageUrl7 = intent.getExtras().getString("IMAGEURL7");
////        imageUrl8 = intent.getExtras().getString("IMAGEURL8");
////        imageUrl9 = intent.getExtras().getString("IMAGEURL9");
//
//    }
//
//    // 유저의 메일을 통해 유저가 작성한 리뷰들을 가져옴.
//    public ArrayList<ReviewList> getUserReviews() {
//        auth = FirebaseAuth.getInstance();
//        String userEmail = new String(auth.getCurrentUser().getEmail());
//        System.out.println("EmailCode : " + userEmail);
//
//        firebaseJson.getFullJson();
//
//        System.out.println("테스트제이슨 : " + FirebaseJson.fullReviewJson.getReview_json_string());
////        if(full_json.h)
//
//        userReviewLists = new ArrayList<ReviewList>();
//
//        // 2자리에 리뷰갯수 넣으면 됨
//        review_count.setText("2");
//
//        return userReviewLists;
//    }
//}













package com.example.mjkim.wheelchair2.Login;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mjkim.wheelchair2.MenuScreen;
import com.example.mjkim.wheelchair2.NameSearch.FirebaseJson;
import com.example.mjkim.wheelchair2.R;
import com.example.mjkim.wheelchair2.Review.ReviewJson;
import com.example.mjkim.wheelchair2.Review.ReviewList;
import com.example.mjkim.wheelchair2.ReviewDetail;
import com.example.mjkim.wheelchair2.WatchReview.WatchReviewAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

// 내가 쓴 리뷰보는 activity.
public class MyReview extends AppCompatActivity {
    public static ArrayList<ReviewJson> reviewJson = new ArrayList<ReviewJson>();
    FirebaseAuth auth;
    StorageReference ref;
    private DatabaseReference mDatabase;
    FirebaseJson fullJson;
    private static String json;
    ReviewList get;
    String total;
    ReviewList reviewList;


    public static ArrayList<ReviewList> reviewLists;
    TextView address_name, phone_number, review_count;
    private UserReviewAdapter reviewAdapter;
    private String location_name;
    private String name;
    private String review_name, reviewer_name;
    private int mapx, mapy, totalLocationCount, num;
    private double rating;
    private Boolean tag1;
    private Boolean tag2;
    private Boolean tag3;
    private Boolean tag4;
    private Boolean tag5;
    private Boolean tag6;
    private String review;
    private String email;
    private double location_mapx;
    private double location_mapy;
    private String imageUrl1;
    private String imageUrl2;
    private String imageUrl3;
    private String imageUrl4;
    private String imageUrl5;
    private String imageUrl6;
    private String imageUrl7;
    private String imageUrl8;
    private String imageUrl9;
    private String date;
    private String phone = "";
    private String address = "";
    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    final Context context;

    {
        context = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_review);
        totalLocationCount = 0;
        reviewLists = new ArrayList<ReviewList>();


        // 내 리뷰 띄우기
        auth= FirebaseAuth.getInstance();
        final String userEmail = new String(auth.getCurrentUser().getEmail()); //Useremail이 현재 사용자 이메일이다.
        mDatabase = database.getReference();
        final DatabaseReference mymyreview = database.getReference();

        Toast.makeText(MyReview.this, "로딩중입니다",
                                    Toast.LENGTH_SHORT).show();


        mDatabase.child("review lists").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                System.out.println("\n"+"memeter"+dataSnapshot.getKey());



                mymyreview.child("review lists").child(dataSnapshot.getKey()).orderByChild("email").equalTo(userEmail).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        ReviewList myreview = dataSnapshot.getValue(ReviewList.class);

                        location_name = myreview.getLocation_name();
                        System.out.println("fighting1 : " + location_name);
                         //myreview.getLocation_name();
                        System.out.println("yeeeeeee1 : " + location_name);
//                        name = intent.getExtras().getString("NAME");
//                        address_name.setText(intent.getExtras().getString("ROAD_ADDRESS"));
//                        address = intent.getExtras().getString("ROAD_ADDRESS");
//                        phone_number.setText(intent.getExtras().getString("TELEPHONE"));
//                        phone = intent.getExtras().getString("TELEPHONE");
//                        mapx = intent.getExtras().getInt("MAPX");
//                        mapy = intent.getExtras().getInt("MAPY");
                        date = myreview.getDate();
                        review_name = myreview.getReview_name();
                        rating = myreview.getRating();
                        System.out.println("yeeeeeee2 : " + Double.toString(rating));
                        tag1 = myreview.getTag1();
                        tag2 = myreview.getTag2();
                        tag3 = myreview.getTag3();
                        tag4 = myreview.getTag4();
                        tag5 = myreview.getTag5();
                        tag6 = myreview.getTag6();
                        review = myreview.getReview();
                        System.out.println("yeeeeeee3 : " + review);
//                        email = intent.getExtras().getString("EMAIL");
                        imageUrl1 = myreview.getImageUrl1();
                        imageUrl2 = myreview.getImageUrl2();
                        imageUrl3 = myreview.getImageUrl3();
                        imageUrl4 = myreview.getImageUrl4();
                        imageUrl5 = myreview.getImageUrl5();
                        imageUrl6 = myreview.getImageUrl6();
                        imageUrl7 = myreview.getImageUrl7();
                        imageUrl8 = myreview.getImageUrl8();
                        imageUrl9 = myreview.getImageUrl9();

                        reviewList = new ReviewList();

                        reviewList.setReview_name(review_name);
                        reviewList.setLocation_name(location_name);
                        reviewList.setRating(rating);
                        reviewList.setTag1(tag1);
                        reviewList.setTag2(tag2);
                        reviewList.setTag3(tag3);
                        reviewList.setTag4(tag4);
                        reviewList.setTag5(tag5);
                        reviewList.setTag6(tag6);
                        reviewList.setReview(review);
                        reviewList.setDate(date);
                        reviewList.setImageUrl1(imageUrl1);
                        reviewList.setImageUrl2(imageUrl2);
                        reviewList.setImageUrl3(imageUrl3);
                        reviewList.setImageUrl4(imageUrl4);
                        reviewList.setImageUrl5(imageUrl5);
                        reviewList.setImageUrl6(imageUrl6);
                        reviewList.setImageUrl7(imageUrl7);
                        reviewList.setImageUrl8(imageUrl8);
                        reviewList.setImageUrl9(imageUrl9);


                        //System.out.println("reviewList : " + reviewList.getLocation_name());
                        reviewList.setLocation_name(location_name);
                        reviewLists.add(totalLocationCount, reviewList);

                        totalLocationCount++;

                        // 리뷰 갯수
                        review_count = (TextView) findViewById(R.id.review_count);
                        review_count.setText(Integer.toString(totalLocationCount));



                        System.out.println(dataSnapshot.getKey() + " was " + myreview.getEmail() +myreview.getDate()+"\n"+myreview.getReview()+ " meters tall.");
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                        ReviewList myreview = dataSnapshot.getValue(ReviewList.class);
//                        System.out.println(dataSnapshot.getKey() + " was " + myreview.getEmail() +myreview.getDate()+ " meters tall.");
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//                        ReviewList myreview = dataSnapshot.getValue(ReviewList.class);
//                        System.out.println(dataSnapshot.getKey() + " was " + myreview.getEmail() +myreview.getDate()+ " meters tall.");
                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                        ReviewList myreview = dataSnapshot.getValue(ReviewList.class);
//                        System.out.println(dataSnapshot.getKey() + " was " + myreview.getEmail() +myreview.getDate()+ " meters tall.");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        System.out.println("errororororororor");
                    }
                });
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        // 리뷰 어댑터 끼우기
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ListView reviewListView = (ListView) findViewById(R.id.my_review_list);
                reviewAdapter = new UserReviewAdapter(MyReview.this, reviewLists);
                reviewListView.setAdapter(reviewAdapter);
            }
        }, 3000);



        // 수정 버튼
        Button correctButton = (Button)findViewById(R.id.correct_button);


        // 삭제 버튼
        Button deleteButton = (Button)findViewById(R.id.delete_button);
//        deleteButton.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                switch (v.getId()) {
//                    case R.id.delete_button:
//                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
//                                context);
//
//                        // 제목셋팅
//                        alertDialogBuilder.setTitle("등록한 장소정보 삭제");
//
//                        // AlertDialog 셋팅
//                        alertDialogBuilder
//                                .setMessage("등록한 장소정보를 삭제하겠습니까?")
//                                .setCancelable(false)
//                                .setPositiveButton("삭제 완료",
//                                        new DialogInterface.OnClickListener() {
//                                            public void onClick(
//                                                    DialogInterface dialog, int id) {
//                                                // 등록한 장소정보를 삭제한다
//                                                System.out.println("삭제테스트");
//                                            }
//                                        })
//                                .setNegativeButton("삭제 취소",
//                                        new DialogInterface.OnClickListener() {
//                                            public void onClick(
//                                                    DialogInterface dialog, int id) {
//                                                // 다이얼로그를 취소한다
//                                                dialog.cancel();
//                                            }
//                                        });
//
//                        // 다이얼로그 생성
//                        AlertDialog alertDialog = alertDialogBuilder.create();
//
//                        // 다이얼로그 보여주기
//                        alertDialog.show();
//                        break;
//
//                    default:
//                        break;
//                }
//
//            }
//        });




        ImageButton back_button;
        ImageButton menu_button;

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
    }

    public void openMenuTab(){
        Intent intent = new Intent(this, MenuScreen.class);
        startActivity(intent);
    }
}


