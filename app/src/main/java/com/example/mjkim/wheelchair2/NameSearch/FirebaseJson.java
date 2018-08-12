package com.example.mjkim.wheelchair2.NameSearch;

import android.support.annotation.NonNull;

import com.example.mjkim.wheelchair2.Review.ReviewList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

public class FirebaseJson {

    public static String json;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener mAuthListener;

    public void getJson(){

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showData(DataSnapshot dataSnapshot){
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            ReviewList reviewList = new ReviewList();
            Object object = ds.getValue(Object.class);
            json = new Gson().toJson(object);

            


             try{
                 JSONObject jsonObj = new JSONObject(json);



                 System.out.println("실험용1: " + jsonObj.keys());
                 System.out.println("실험용2: " + jsonObj.names());
                 System.out.println("실험용3: " + jsonObj.length());

             }catch (Exception e) {
                 e.printStackTrace();
                 System.out.println("오류입니다");
             }

        }
    }
}
