package com.example.triple_c;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

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

        EditText username = findViewById(R.id.inputUsername);
        EditText email = findViewById(R.id.inputEmail);
        EditText password = findViewById(R.id.inputPassword);
        EditText phone = findViewById(R.id.inputPhone);
        EditText firstname = findViewById(R.id.inputFirstName);
        EditText lastname = findViewById(R.id.inputLastname);
        Button signup = findViewById(R.id.signupButton);

        signup.setOnClickListener(v -> {
            AuthSignUpOptions options = AuthSignUpOptions.builder()
                    .userAttribute(AuthUserAttributeKey.email(), email.getText().toString())
                    .build();
            Amplify.Auth.signUp(username.getText().toString(), password.getText().toString(), options,
                    result -> {
                        User user = User.builder().username(username.getText().toString()).firstname(firstname.getText().toString()).lastname(lastname.getText().toString()).phone(phone.getText().toString()).email(email.getText().toString()).build();

                        Amplify.API.mutate(
                                ModelMutation.create(user),
                                response2 -> Log.i("MyAmplifyApp", "Added user with id: " + response2.getData().getId()),
                                error -> Log.e("MyAmplifyApp", "Create failed", error)
                        );

                        Log.i("AuthQuickStart", "Result: " + result.toString());
                    },
                    error -> Log.e("AuthQuickStart", "Sign up failed", error)
            );

            Intent goConfirm = new Intent(SignUp.this, Confirm.class);
            startActivity(goConfirm);
        });

    }
}