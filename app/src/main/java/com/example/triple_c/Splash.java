package com.example.triple_c;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;

public class Splash extends AppCompatActivity {
    private static int splashScreen = 5000;
    //Animation var
    Animation topAnim, bottomAnim;
    ImageView image;
    TextView logo, slogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        try {
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.addPlugin(new AWSS3StoragePlugin());

            Amplify.configure(getApplicationContext());
            Log.i("MyAmplifyApp", "Initialized Amplify");
        } catch (AmplifyException error) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error);
        }

//Animation
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom);
//Hooks
        image = findViewById(R.id.imageView3);
        logo = findViewById(R.id.textView11);
        slogan = findViewById(R.id.textView12);

        image.setAnimation(topAnim);
        logo.setAnimation(bottomAnim);
        slogan.setAnimation(bottomAnim);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                AuthUser authUser = Amplify.Auth.getCurrentUser();
                if (authUser != null) {
                    if (authUser.getUsername().equals("admin2")){
                        Intent goToDashBoard = new Intent(Splash.this, Dashboard.class);
                        startActivity(goToDashBoard);
                    } else {
                        Intent goToMain = new Intent(Splash.this, MainActivity.class);
                        startActivity(goToMain);
                    }
                } else {
                    Intent goToSignIn = new Intent(Splash.this, SignIn.class);
                    startActivity(goToSignIn);
                }
            }
        }, splashScreen);

    }
}