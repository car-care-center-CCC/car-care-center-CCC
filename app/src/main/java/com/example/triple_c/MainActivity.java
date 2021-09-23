package com.example.triple_c;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        try {
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.addPlugin(new AWSS3StoragePlugin());

            Amplify.configure(getApplicationContext());
            Log.i("MyAmplifyApp", "Initialized Amplify");
        } catch (AmplifyException error) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error);
        }

        Amplify.Auth.fetchAuthSession(
                result -> Log.i("AmplifyQuickstart", result.toString()),
                error -> Log.e("AmplifyQuickstart", error.toString())
        );

        Button signoutButton = findViewById(R.id.signoutButton);
        signoutButton.setOnClickListener( v-> {
                Amplify.Auth.signOut(
                        () -> Log.i("AuthQuickstart", "Signed out successfully"),
                        error -> Log.e("AuthQuickstart", error.toString())
                );
        });

        Button addCar = findViewById(R.id.addCarButton);
        Button askForHelp = findViewById(R.id.askForServices);
        Button confirm = findViewById(R.id.confirmPagee);
        Button contactUs = findViewById(R.id.contactUs);
        Button details = findViewById(R.id.details);
        Button ourServicesPage = findViewById(R.id.ourServicesPage);
        Button profile = findViewById(R.id.profile);
        Button signIn = findViewById(R.id.signIn);
        Button signUp = findViewById(R.id.signUp);
        Button splash = findViewById(R.id.splash);

        addCar.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, AddCar.class));
        });
        askForHelp.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, AskForService.class));
        });
        confirm.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, Confirm.class));
        });
        contactUs.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, ContactUs.class));
        });
        details.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, Details.class));
        });
        ourServicesPage.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, OurServices.class));
        });
        profile.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, Profile.class));
        });
        signIn.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, SignIn.class));
        });
        signUp.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, SignUp.class));
        });
        splash.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, Splash.class));
        });

    }
}
