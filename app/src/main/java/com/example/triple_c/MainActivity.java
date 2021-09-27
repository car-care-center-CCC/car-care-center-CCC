package com.example.triple_c;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.User;

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

/////////////// Adding Services as a hard coded /////////////////

//            Service service = Service.builder()
//                    .name("Fuel Supply")
//                    .build();
//
//            Amplify.API.mutate(
//                    ModelMutation.create(service),
//                    response -> Log.i("MyAmplifyApp", "Added Todo with id: " + response.getData().getId()),
//                    error -> Log.e("MyAmplifyApp", "Create failed", error)
//            );

            Log.i("MyAmplifyApp", "Initialized Amplify");
        } catch (AmplifyException error) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error);
        }

        AuthUser authUser = Amplify.Auth.getCurrentUser();
        if (authUser != null) {
            String username = authUser.getUsername();
            Amplify.API.query(
                    ModelQuery.list(com.amplifyframework.datastore.generated.model.User.class),
                    response -> {
                        for (User user : response.getData()) {
                            if (user.getUsername().equals(username)) {
                                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                                SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
                                sharedPreferencesEditor.putString("userId", user.getId());
                                sharedPreferencesEditor.putString("username", user.getUsername());
                                sharedPreferencesEditor.apply();
                            }
                        }
                    },
                    error -> Log.e("MyAmplifyApp", error.toString(), error)
            );
        }


        // // // // // // // // // // // // // // // // // // // // // // // // // // //
        Button signoutButton = findViewById(R.id.signoutButton);
        signoutButton.setOnClickListener(v -> {
            Amplify.Auth.signOut(
                    () -> Log.i("AuthQuickstart", "Signed out successfully"),
                    error -> Log.e("AuthQuickstart", error.toString())
            );
        });

        Button addCar = findViewById(R.id.addCarButton);
//        Button askForHelp = findViewById(R.id.askForServices);
        Button confirm = findViewById(R.id.confirmPagee);
        Button contactUs = findViewById(R.id.contactUs);
//        Button details = findViewById(R.id.details);
        Button ourServicesPage = findViewById(R.id.ourServicesPage);
        Button profile = findViewById(R.id.profile);
        Button signIn = findViewById(R.id.signIn);
        Button signUp = findViewById(R.id.signUp);
        Button splash = findViewById(R.id.splash);
//        Button Details = findViewById(R.id.Details);

        addCar.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, AddCar.class));
        });
//        askForHelp.setOnClickListener(view -> {
//            startActivity(new Intent(MainActivity.this, AskForService.class));
//        });
        confirm.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, Confirm.class));
        });
        contactUs.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, ContactUs.class));
        });
//        details.setOnClickListener(view -> {
//            startActivity(new Intent(MainActivity.this, Details.class));
//        });
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
//        Details.setOnClickListener(view -> {
//            startActivity(new Intent(MainActivity.this, Details.class));
//        });

    }
}
