package com.example.triple_c;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.amplifyframework.core.Amplify;

public class SignIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        EditText username = findViewById(R.id.usernameSignin);
        EditText password = findViewById(R.id.passwordSignin);
        Button signin = findViewById(R.id.signinButton);

        signin.setOnClickListener(v-> Amplify.Auth.signIn(
                username.getText().toString(),
                password.getText().toString(),
                result -> {
                    Log.i("AuthQuickstart", result.isSignInComplete() ? "Sign in succeeded" : "Sign in not complete");
                    startActivity(new Intent(SignIn.this, MainActivity.class));
                },
                error -> Log.e("AuthQuickstart", error.toString())
        ));

    }
}