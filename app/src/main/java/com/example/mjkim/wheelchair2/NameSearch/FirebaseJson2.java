package com.example.mjkim.wheelchair2.NameSearch;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.mjkim.wheelchair2.Review.ReviewJson;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.json.JSONObject;

public class FirebaseJson2 extends AsyncTask<String ,Void, ReviewJson> {

    private ReviewJson reviewJson = new ReviewJson();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private static String json;
    private static int review_num;

    @Override
    protected void onPreExecute() {
        System.out.println("아신크 제이슨 파일1");
        super.onPreExecute();
        System.out.println("아신크 제이슨 파일1");
    }

    @Override
    protected ReviewJson doInBackground(String... strings) {

        final String string = strings[0];
        System.out.println("아신크 제이슨 파일2");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                readData(dataSnapshot, string);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return reviewJson;
    }

    @Override
    protected void onProgressUpdate(Void... params) {
        System.out.println("아신크 제이슨 파일3");
    }

    @Override
    protected void onPostExecute(ReviewJson result) {
        System.out.println("아신크 제이슨 파일4");
        super.onPostExecute(result);
    }


    public void readData(DataSnapshot dataSnapshot, String name) {

        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            Object object = ds.getValue(Object.class);
            json = new Gson().toJson(object);


            try {
                JSONObject jsonObj = new JSONObject(json);
                System.out.println("실험용1: " + jsonObj.keys());
                System.out.println("실험용2: " + jsonObj.names());
                System.out.println("실험용3: " + jsonObj.length());
                System.out.println("실험용4: " + jsonObj.getString(name));
                System.out.println("실험용5: " + jsonObj.get(name));
                System.out.println("실험용6: " + jsonObj.getJSONObject(name).names());
                System.out.println("실험용7: " + jsonObj.getJSONObject(name).length());
                review_num = jsonObj.getJSONObject(name).length();
                System.out.println("개수 초반: " + review_num);

                reviewJson = new ReviewJson(review_num, jsonObj.getString(name), jsonObj.getJSONObject(name).names());


            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("오류입니다");
            }

        }
    }
}

