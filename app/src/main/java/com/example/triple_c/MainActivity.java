package com.example.triple_c;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addCar = findViewById(R.id.addCarButton);
        Button askForHelp=findViewById(R.id.askForServices);
        Button confirm=findViewById(R.id.contactUs);
    }
}