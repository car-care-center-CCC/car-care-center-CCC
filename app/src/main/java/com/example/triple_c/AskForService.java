package com.example.triple_c;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Location;
import com.amplifyframework.datastore.generated.model.Request;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class AskForService extends AppCompatActivity {

    EditText serviceName, serviceDescription, phoneNumber;
    TextView countryName, cityName, longitude, latitude;
    Button shareLocation, submit;
    FusedLocationProviderClient fusedLocationProviderClient;
    String countryNameStorage, cityNameStorage;
    Double longitudeStorage, latitudeStorage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_for_service);

        serviceName = findViewById(R.id.serviceName);
        serviceDescription = findViewById(R.id.serviceDescription);
        phoneNumber = findViewById(R.id.phoneNumber);

        countryName = findViewById(R.id.displayTheCountry);
        cityName = findViewById(R.id.displayTheCity);
        longitude = findViewById(R.id.displayLongitude);
        latitude = findViewById(R.id.displayLatitude);

        shareLocation = findViewById(R.id.shareLocationBtn);
        submit = findViewById(R.id.submitBtn);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(AskForService.this);
    }


    @Override
    protected void onStart() {
        super.onStart();
        shareLocation.setOnClickListener(view -> shareLocationListener());
        submit.setOnClickListener(view -> saveTheDataInTheCloud());
    }



    ////////////////////////////// Function to handle the listener of add location /////////////////////////////
    private void shareLocationListener(){
        Toast.makeText(this, "Submitted", Toast.LENGTH_SHORT).show();
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            System.out.println("Hello");
            showLocation();
        }
        else
            ActivityCompat.requestPermissions(AskForService.this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 5);
    }


    private void saveTheDataInTheCloud(){
        saveLocationData(countryNameStorage, cityNameStorage, longitudeStorage, latitudeStorage);
        saveServiceData(serviceName.getText().toString(),
                serviceDescription.getText().toString(),
                phoneNumber.getText().toString());
    }



    ////////////////////////////// Function to show the location /////////////////////////////
    @SuppressLint("MissingPermission")
    private void showLocation() {
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<android.location.Location>() {
            @Override
            public void onComplete(@NonNull Task<android.location.Location> task) {
                android.location.Location location = task.getResult();
                if (location != null) {
                    Geocoder geocoder = new Geocoder(AskForService.this, Locale.getDefault());
                    try {
                        List<Address> address = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        Address obj = address.get(0);
                        countryName.setText(obj.getCountryName());
                        cityName.setText(obj.getLocality());
                        latitude.setText(String.valueOf(location.getLatitude()));
                        longitude.setText(String.valueOf(location.getLongitude()));
                        countryNameStorage = obj.getCountryName();
                        cityNameStorage = obj.getLocality();
                        longitudeStorage = location.getLongitude();
                        latitudeStorage = location.getLatitude();
                        System.out.println("++++++++++++++++++++++++++++++ " + obj.getLocality() + " ++++++++++++++++++++++++++++++");
                        Log.i("Location", obj.getCountryName());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    ////////////////////////////////// Function to save the service data ////////////////////////////////////
    private void saveServiceData(String name, String description, String number){
        Request request = Request.builder()
                .name(name)
                .description(description)
                .phone(number)
                .build();

        Amplify.API.mutate(
                ModelMutation.create(request),
                response -> Log.i("MyAmplifyApp", "Added request with id: " + response.getData().getId()),
                error -> Log.e("MyAmplifyApp", "Create failed", error)
        );
        finish();
    }


    //////////////////////////////////// Function to save the location data ////////////////////////////////////
    private void saveLocationData(String country, String city,
                                  Double latitudeNumber, Double longitudeNumber){
        Location location = Location.builder()
                .countryName(country)
                .cityName(city)
                .longitude(longitudeNumber)
                .latitude(latitudeNumber)
                .build();

        Amplify.API.mutate(
                ModelMutation.create(location),
                response -> Log.i("MyAmplifyApp", "Added request with id: " + response.getData().getId()),
                error -> Log.e("MyAmplifyApp", "Create failed", error)
        );
        finish();
    }
}