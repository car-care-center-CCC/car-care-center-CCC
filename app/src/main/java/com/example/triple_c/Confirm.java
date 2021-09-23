package com.example.triple_c;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.amplifyframework.core.Amplify;

public class Confirm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        EditText confirmationInput = findViewById(R.id.confirmationInput);
        EditText usernameConfirm = findViewById(R.id.usernameConfirm);
        Button confirmButton = findViewById(R.id.confirmButton);

        confirmButton.setOnClickListener(v -> {
            Amplify.Auth.confirmSignUp(
                    usernameConfirm.getText().toString(),
                    confirmationInput.getText().toString(),
                    result -> Log.i("AuthQuickstart", result.isSignUpComplete() ? "Confirm signUp succeeded" : "Confirm sign up not complete"),
                    error -> Log.e("AuthQuickstart", error.toString())
            );

            Intent goHome = new Intent(Confirm.this, MainActivity.class);
            startActivity(goHome);
        });
    }
}