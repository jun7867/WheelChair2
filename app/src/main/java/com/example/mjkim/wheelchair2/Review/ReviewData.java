package com.example.mjkim.wheelchair2.Review;
import android.net.Uri;
import android.support.design.widget.TextInputEditText;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.example.mjkim.wheelchair2.R;
import com.example.mjkim.wheelchair2.ReviewScreen;
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



        FirebaseDatabase.getInstance().getReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.d("MainActivity", "ValueEventListener : " + snapshot.getValue());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    // 데이터베이스 읽기 #2. Single ValueEventListener
        FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                Log.d("MainActivity", "Single ValueEventListener : " + snapshot.getValue());
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });

    // 데이터베이스 읽기 #3. ChildEventListener
        FirebaseDatabase.getInstance().getReference().addChildEventListener(new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Log.d("MainActivity", "ChildEventListener - onChildAdded : " + dataSnapshot.getValue());
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            Log.d("MainActivity", "ChildEventListener - onChildChanged : " + s);
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            Log.d("MainActivity", "ChildEventListener - onChildRemoved : " + dataSnapshot.getKey());
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            Log.d("MainActivity", "ChildEventListener - onChildMoved" + s);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Log.d("MainActivity", "ChildEventListener - onCancelled" + databaseError.getMessage());
        }
    });
}
}




