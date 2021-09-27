package com.example.triple_c;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

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
        Button signoutButton = findViewById(R.id.signoutButton);
        signoutButton.setOnClickListener(v -> {
            Amplify.Auth.signOut(
                    () -> Log.i("AuthQuickstart", "Signed out successfully"),
                    error -> Log.e("AuthQuickstart", error.toString())
            );
        });


        RelativeLayout serviscesMain =findViewById(R.id.servicesmain);
        RelativeLayout profilemain =findViewById(R.id.profilemain);
        RelativeLayout contactusmain =findViewById(R.id.contactusmain);

        serviscesMain.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View view) {
                                                 Intent goToOurServices = new Intent(MainActivity.this, OurServices.class);
                                                 startActivity(goToOurServices);
                                             }
                                         });

        profilemain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToProfileMain=new Intent(MainActivity.this,Profile.class);
                startActivity(goToProfileMain);
            }
        });

        contactusmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToContactUsMains=new Intent(MainActivity.this,ContactUs.class);
                startActivity(goToContactUsMains);
            }
        });

    }
}


