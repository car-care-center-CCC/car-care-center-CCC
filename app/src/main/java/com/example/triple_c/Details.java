package com.example.triple_c;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Details extends AppCompatActivity implements OnMapReadyCallback {

    public double lat;
    public double lng;
    public String cityName;
    public String countryName;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();

        lat = intent.getExtras().getDouble("lat");
        lng = intent.getExtras().getDouble("lng");
        cityName = intent.getExtras().getString("cityName");
        countryName = intent.getExtras().getString("countryName");

        // render fields
        TextView userNameView = findViewById(R.id.usernameView);
        userNameView.setText(intent.getExtras().getString("username"));

        TextView requestNameView = findViewById(R.id.requestNameView);
        requestNameView.setText("Request Name: " + intent.getExtras().getString("requestName"));

        TextView descriptionView = findViewById(R.id.descriptionView);
        descriptionView.setText("Description : " + intent.getExtras().getString("requestDescription"));

        TextView phoneView = findViewById(R.id.phoneView);
        phoneView.setText("Phone Number: " + intent.getExtras().getString("phone"));

        TextView carTypeView = findViewById(R.id.carTypeView);
        carTypeView.setText("Car Type: " + intent.getExtras().getString("carType"));

        TextView carModelView = findViewById(R.id.carModelView);
        carModelView.setText("Car Model: " + intent.getExtras().getString("carModel"));

        TextView gasolineView = findViewById(R.id.gasolineView);
        gasolineView.setText("Gasoline Type: " + intent.getExtras().getString("gasoline"));

        TextView cityNameView = findViewById(R.id.cityNameView);
        cityNameView.setText("Country Name: " + countryName);

        TextView countryNameView = findViewById(R.id.countryNameView);
        countryNameView.setText("City Name: " + cityName);

        // preparing map
        if (lat != 0 && lng != 0) {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }
    }

    // when map is ready
    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng location = new LatLng(lat, lng);
        googleMap.addMarker(new MarkerOptions().position(location).title(cityName));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
    }
}