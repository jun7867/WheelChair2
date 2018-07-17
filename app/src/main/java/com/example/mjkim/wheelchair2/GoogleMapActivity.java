package com.example.mjkim.wheelchair2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

class GoogleMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);

        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);
    }



    //36.103245, 129.388664

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng HGU = new LatLng(36.103245, 129.388664);

        googleMap.addMarker(new MarkerOptions().position(HGU).title("HGU"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(HGU, 10));
    }
}