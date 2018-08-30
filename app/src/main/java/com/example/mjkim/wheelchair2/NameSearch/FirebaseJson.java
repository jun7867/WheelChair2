package com.example.mjkim.wheelchair2.NameSearch;

import android.support.annotation.NonNull;

import com.example.mjkim.wheelchair2.Review.ReviewJson;
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

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class FirebaseJson {

    private static String json;
    private static int review_num;

    public static ArrayList<ReviewJson> reviewJson = new ArrayList<ReviewJson>();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener mAuthListener;

    public void getJson(final String location_name, final int num){


        System.out.println("데이터베이스: "+ databaseReference.getKey());

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                readData(dataSnapshot, location_name, num);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }



    public void readData(DataSnapshot dataSnapshot, String name, int num){

        for(DataSnapshot ds : dataSnapshot.getChildren()){
            Object object = ds.getValue(Object.class);
            json = new Gson().toJson(object);


            try{
                JSONObject jsonObj = new JSONObject(json);
                System.out.println("실험용2: " + jsonObj.names());
                System.out.println("실험용3: " + jsonObj.length());
                System.out.println("실험용4: " + jsonObj.getString(name));
                System.out.println("실험용6: " + jsonObj.getJSONObject(name).names());
                System.out.println("실험용7: " + jsonObj.getJSONObject(name).length());
                System.out.println("실험용8: " + jsonObj.getJSONObject(name));
                review_num = jsonObj.getJSONObject(name).length();
                System.out.println("개수 초반: " + review_num);

                System.out.println("번호: " + num);
                reviewJson.add(num,new ReviewJson(name, review_num, jsonObj.getString(name), jsonObj.getJSONObject(name).names()));
                System.out.println("진짜 개많네1: " + reviewJson.get(num).getReview_count());
                System.out.println("진짜 개많네2: " + reviewJson.get(num).getReview_json_string());
                System.out.println("진짜 개많네3: " + reviewJson.get(num).getReview_json_userID());


            }catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    public static int getReview_num() {
        return review_num;
    }

    public static void setReview_num(int review_num) {
        FirebaseJson.review_num = review_num;
    }

}
