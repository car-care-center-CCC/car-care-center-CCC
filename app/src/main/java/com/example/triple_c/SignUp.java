package com.example.triple_c;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.User;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        EditText username = findViewById(R.id.inputUsername);
        EditText email = findViewById(R.id.inputEmail);
        EditText password = findViewById(R.id.inputPassword);
        EditText phone = findViewById(R.id.inputPhone);
        EditText firstname = findViewById(R.id.inputFirstName);
        EditText lastname = findViewById(R.id.inputLastname);
        Button signup = findViewById(R.id.signupButton);

        // create user to cognito
        signup.setOnClickListener(v -> {
            AuthSignUpOptions options = AuthSignUpOptions.builder()
                    .userAttribute(AuthUserAttributeKey.email(), email.getText().toString())
                    .build();
            Amplify.Auth.signUp(username.getText().toString(), password.getText().toString(), options,
                    result -> {

                        // save to dynamoDp
                        User user = User.builder().username(username.getText().toString()).firstname(firstname.getText().toString()).lastname(lastname.getText().toString()).phone(phone.getText().toString()).email(email.getText().toString()).build();
                        Amplify.API.mutate(
                                ModelMutation.create(user),
                                response2 -> {
                                    Log.i("MyAmplifyApp", "Added user with id: " + response2.getData().getId());
                                    Intent goConfirm = new Intent(SignUp.this, Confirm.class);
                                    startActivity(goConfirm);
                                },
                                error -> Log.e("MyAmplifyApp", "Create failed", error)
                        );
                        Log.i("AuthQuickStart", "Result: " + result.toString());
                    },
                    error -> {
                        handler1();
                        Log.e("AuthQuickStart", "Sign up failed", error);
                    }
            );
        });

        TextView goSignIn = findViewById(R.id.goSignIn);
        goSignIn.setOnClickListener(view -> {
            Intent goToSignIn = new Intent(SignUp.this, SignIn.class);
            startActivity(goToSignIn);
        });
    }

    public void handler1() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "Username already exist!", Toast.LENGTH_LONG).show();
            }
        });
    }
}