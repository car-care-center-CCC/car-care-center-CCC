package com.example.triple_c;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Car;
import com.amplifyframework.datastore.generated.model.Location;
import com.amplifyframework.datastore.generated.model.Request;
import com.amplifyframework.datastore.generated.model.Service;
import com.amplifyframework.datastore.generated.model.User;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class AskForService extends AppCompatActivity {

    EditText serviceDescription, phoneNumber;
    TextView countryName, cityName, serviceName;
    Button shareLocation, submit;
    FusedLocationProviderClient fusedLocationProviderClient;
    String countryNameStorage, cityNameStorage;
    Double longitudeStorage, latitudeStorage;
    User user;
    Service service;
    Location currentLocation = null;
    boolean checked = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_for_service);

        serviceName = findViewById(R.id.serviceName);
        serviceDescription = findViewById(R.id.serviceDescription);
        phoneNumber = findViewById(R.id.phoneNumber);

        countryName = findViewById(R.id.displayTheCountry);
        cityName = findViewById(R.id.displayTheCity);
        shareLocation = findViewById(R.id.shareLocationBtn);
        submit = findViewById(R.id.submitBtn);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(AskForService.this);
        shareLocationListener();

        ////////////////////////////////// get user and service by Id ////////////////////////////////////
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AskForService.this);
        String userId = sharedPreferences.getString("userId", "");
        Intent intent = getIntent();
        String serviceId = intent.getExtras().getString("serviceId");

        Amplify.API.query(
                ModelQuery.get(User.class, userId),
                response -> {
                    user = response.getData();
                    Log.i("User ================ ", response.getData().getUsername());

                    ////////////////////////////// cars recycler view /////////////////////////////
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            List<Car> carList = user.getCars();
                            RecyclerView carRecyclerView = findViewById(R.id.carRecycler);
                            carRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            carRecyclerView.setAdapter(new CarAdapter(carList));
                        }
                    });
                },
                error -> Log.e("MyAmplifyApp", error.toString(), error)
        );

        Amplify.API.query(
                ModelQuery.get(Service.class, serviceId),
                response -> {
                    Log.i("User ================ ", response.getData().getName());
                    serviceName.setText(response.getData().getName());
                    service = response.getData();
                },
                error -> Log.e("MyAmplifyApp", error.toString(), error)
        );
    }

    ////////////////////////////// Function to handle submit request /////////////////////////////
    @Override
    protected void onStart() {
        super.onStart();
        submit.setOnClickListener(view -> {
            if (checked && !serviceDescription.getText().toString().equals("") && !phoneNumber.getText().toString().equals("")) {
                saveTheDataInTheCloud();
            } else {
                handler2();
            }
        });
    }

    ////////////////////////////// Function to handle the listener of add location /////////////////////////////
    public void shareLocationListener() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            System.out.println("Hello");
            showLocation();
        } else
            ActivityCompat.requestPermissions(AskForService.this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 5);
    }


    private void saveTheDataInTheCloud() {
        saveLocationData(countryNameStorage, cityNameStorage, longitudeStorage, latitudeStorage);
        saveServiceData(serviceName.getText().toString(),
                serviceDescription.getText().toString(),
                phoneNumber.getText().toString());
        handler1();
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
                        List<Address> address = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 10);
                        Address obj = address.get(0);
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
    private void saveServiceData(String name, String description, String number) {
        Request request = Request.builder()
                .name(name)
                .description(description)
                .phone(number)
                .isTaken(false)
                .service(service)
                .user(user)
                .location(currentLocation)
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
                                  Double longitudeNumber, Double latitudeNumber) {
        Location location = Location.builder()
                .countryName(country)
                .cityName(city != null ? city : "")
                .longitude(longitudeNumber)
                .latitude(latitudeNumber)
                .build();
        currentLocation = location;

        Amplify.API.mutate(
                ModelMutation.create(location),
                response -> Log.i("MyAmplifyApp", "Added request with id: " + response.getData().getId()),
                error -> Log.e("MyAmplifyApp", "Create failed", error)
        );
        finish();
    }

    ////////////////////////////// Function to check if location checked /////////////////////////////
    public boolean onCheckboxClicked(View view) {
        checked = ((CheckBox) view).isChecked();
        if (checked) {
            Toast.makeText(this, "Submitted", Toast.LENGTH_SHORT).show();
            countryName.setText(countryNameStorage);
            cityName.setText(cityNameStorage);
        } else {
            countryName.setText("");
            cityName.setText("");
        }
        return checked;
    }

    ////////////////////////////// Function to handle if submitted done or not /////////////////////////////
    public void handler1() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "Request has sent Successfully!", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void handler2() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "Please fill all fields!", Toast.LENGTH_LONG).show();
            }
        });
    }
}