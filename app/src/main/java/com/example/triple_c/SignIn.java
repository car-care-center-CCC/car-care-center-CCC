package com.example.triple_c;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.core.Amplify;

public class SignIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        EditText username = findViewById(R.id.usernameSignin);
        EditText password = findViewById(R.id.passwordSignin);
        Button signin = findViewById(R.id.signinButton);
        TextView goToSignUp = findViewById(R.id.signUpInSignIn);

        signin.setOnClickListener(v -> Amplify.Auth.signIn(
                username.getText().toString(),
                password.getText().toString(),
                result -> {
                    Log.i("AuthQuickstart", result.isSignInComplete() ? "Sign in succeeded" : "Sign in not complete");
                    if (result.isSignInComplete()) {
                        handler1();
                        if (username.getText().toString().equals("admin2")){
                            Intent goToDashBoard = new Intent(SignIn.this, Dashboard.class);
                            startActivity(goToDashBoard);
                        } else {
                            Intent goToMain = new Intent(SignIn.this, MainActivity.class);
                            startActivity(goToMain);
                        }
                    } else {
                        handler2();
                    }
                },
                error -> {
                    Log.e("AuthQuickstart", error.toString());
                    handler2();
                }
        ));

        goToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignIn.this, SignUp.class);
                startActivity(intent);
            }
        });

    }


    public void handler1() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_LONG).show();
            }
        });
    }


    public void handler2() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "Incorrect username or password!", Toast.LENGTH_LONG).show();
            }
        });
    }


}