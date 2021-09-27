package com.example.triple_c;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Car;
import com.amplifyframework.datastore.generated.model.User;

import java.util.List;

public class AddCar extends AppCompatActivity {

    private String carType;
    private String carModel;
    private String gasoline;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        // // // // // // // // // Get Cars List // // // // // // // // // // // // // //
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AddCar.this);
        String userId = sharedPreferences.getString("userId", "");
        Amplify.API.query(
                ModelQuery.get(User.class, userId),
                response -> {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            user = response.getData();
                            List<Car> carList = response.getData().getCars();
                            RecyclerView addCarRecyclerView = findViewById(R.id.addCarRecycler);
                            addCarRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            addCarRecyclerView.setAdapter(new CarAdapter(carList));
                        }
                    });
                },
                error -> Log.e("MyAmplifyApp", error.toString(), error)
        );

        // // // // // // // // // Get views and text // // // // // // // // // // // // // //
        EditText editTextCarModel = findViewById(R.id.editTextCarModel);
        EditText editTextCarType = findViewById(R.id.editTextCarType);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);


        // // // // // // // // // add car listener // // // // // // // // // // // // // //
        Button addCar = findViewById(R.id.addCar);
        addCar.setOnClickListener(view -> {
            carType = editTextCarType.getText().toString();
            carModel = editTextCarModel.getText().toString();
            int chosenGasolineType = radioGroup.getCheckedRadioButtonId();
            RadioButton chosenButton = findViewById(chosenGasolineType);

            if (chosenButton != null && !carType.equals("") && !carModel.equals("")) {
                gasoline = chosenButton.getText().toString();
                addCar(carType, carModel, gasoline);
            } else {
                handler2();
            }
        });

        // // // // // // // // // go to profile listener // // // // // // // // // // // // // //
        Button buttonGoProfile = findViewById(R.id.buttonGoProfile);
        buttonGoProfile.setOnClickListener(view -> {
            Intent goToProfile = new Intent(AddCar.this, Profile.class);
            startActivity(goToProfile);
        });

    }

    // // // // // // // // // method to save car in could // // // // // // // // // // // // // //
    private void addCar(String carType, String carModel, String gasoline) {

        Car car = Car.builder()
                .type(carType)
                .model(carModel)
                .gasoline(gasoline)
                .user(user)
                .build();

        Amplify.API.mutate(
                ModelMutation.create(car),
                response2 -> {
                    Log.i("MyAmplifyApp", "Added car with id: " + response2.getData().getId());
                    handler1();
                    finish();
                    startActivity(getIntent());
                },
                error -> Log.i("MyAmplifyApp", "Create failed", error)
        );
    }

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