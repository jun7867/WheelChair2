package com.example.mjkim.wheelchair2.Review;

import android.util.Log;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ReviewData {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    ReviewList reviewList = new ReviewList();

    public void saveData(String name, ReviewList temp_reviewList){

        reviewList = temp_reviewList;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        reviewList.setEmail(user.getEmail());
        reviewList.setName(user.getDisplayName());

        DatabaseReference usersRef = databaseReference.child("review lists");


        usersRef.child(name).push().setValue(reviewList);// 기본 database 하위 message라는 child에 chatData를 list로 만들기
}
}




