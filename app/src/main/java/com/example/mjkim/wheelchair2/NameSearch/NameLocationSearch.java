package com.example.mjkim.wheelchair2.NameSearch;

import android.os.AsyncTask;
import android.text.Html;

import com.example.mjkim.wheelchair2.NameSearch.FirebaseJson;
import com.example.mjkim.wheelchair2.Review.ReviewList;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class NameLocationSearch extends AsyncTask<String, Void, ArrayList<ReviewList>> {


    public static int total_num = 0;


    private ArrayList<String> location_result;


    @Override
    protected void onPreExecute() { super.onPreExecute(); }


    @Override
    protected ArrayList<ReviewList> doInBackground(String... strings) {


        String name;
        String link;
        String description;
        String telephone;
        String address;
        String road_address;
        int mapx;
        int mapy;



        //파서기 시작
        location_result = new ArrayList<String>();
        ArrayList<ReviewList> reviewList = new ArrayList<ReviewList>();

        try {





        } catch (Exception e) {
            e.printStackTrace();
        }


        //파서기 끝
        return reviewList;

    }



    @Override
    protected void onProgressUpdate(Void...params){ }

    @Override
    protected void onPostExecute(ArrayList<ReviewList> result){
        super.onPostExecute(result);
    }
}