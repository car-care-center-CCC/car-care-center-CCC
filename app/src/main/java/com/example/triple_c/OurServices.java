package com.example.triple_c;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kotlin.text.UStringsKt;

public class OurServices extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_our_services);

    }

    @Override
    protected void onStart() {
        super.onStart();

//        List<Service > servicesList=new ArrayList<>();
//
//        Amplify.API.query(
//                ModelQuery.list(Service.class),
//                response -> {
//                    Log.i("serviseResponse",response.getData().toString());
//                    for (Service service : response.getData()) {
//                        servicesList.add(service);
//
//                    }
//
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    TextView textView1=findViewById(R.id.service1);
//                    textView1.setText(servicesList.get(0).getName());
//
//                    TextView textView2=findViewById(R.id.service2);
//                    textView2.setText(servicesList.get(1).getName());
//
//                    TextView textView3=findViewById(R.id.service3);
//                    textView3.setText(servicesList.get(2).getName());
//
//                    TextView textView4=findViewById(R.id.service4);
//                    textView4.setText(servicesList.get(3).getName());
//
//                    TextView textView5=findViewById(R.id.service5);
//                    textView5.setText(servicesList.get(4).getName());
//
//                    TextView textView6=findViewById(R.id.service6);
//                    textView6.setText(servicesList.get(5).getName());
//                }
//            });
//
//
//                    Log.i("MyAmplifyApp", "outside the loop");
//                },
//                error -> Log.e("MyAmplifyApp", "Query failure", error)
//        );

        CardView card1 = findViewById(R.id.card1);
        card1.setOnClickListener(view -> {
            Intent goToAskForService = new Intent(OurServices.this, AskForService.class);
            goToAskForService.putExtra("serviceId", "6a010c27-6b37-4093-9e64-4c87bdbea800");
            startActivity(goToAskForService);
        });

        CardView card2 = findViewById(R.id.card2);
        card2.setOnClickListener(view -> {
            Intent goToAskForService = new Intent(OurServices.this, AskForService.class);
            goToAskForService.putExtra("serviceId", "1f111de1-cbba-44ab-bb13-28aa25fd6be9");
            startActivity(goToAskForService);
        });

        CardView card3 = findViewById(R.id.card3);
        card3.setOnClickListener(view -> {
            Intent goToAskForService = new Intent(OurServices.this, AskForService.class);
            goToAskForService.putExtra("serviceId", "20aae290-be18-47bc-b6b8-a16909bf3898");
            startActivity(goToAskForService);
        });

        CardView card4 = findViewById(R.id.card4);
        card4.setOnClickListener(view -> {
            Intent goToAskForService = new Intent(OurServices.this, AskForService.class);
            goToAskForService.putExtra("serviceId", "5ccb2369-fd80-451d-96b0-7bfe6da99d76");
            startActivity(goToAskForService);
        });

        CardView card5 = findViewById(R.id.card5);
        card5.setOnClickListener(view -> {
            Intent goToAskForService = new Intent(OurServices.this, AskForService.class);
            goToAskForService.putExtra("serviceId", "5adf8736-04a6-4978-86d8-4186a6a3ae8a");
            startActivity(goToAskForService);
        });

        CardView card6 = findViewById(R.id.card6);
        card6.setOnClickListener(view -> {
            Intent goToAskForService = new Intent(OurServices.this, AskForService.class);
            goToAskForService.putExtra("serviceId", "f8a2d3f3-2e01-48d3-992e-614cca5cdea4");
            startActivity(goToAskForService);
        });

    }


}