package com.example.triple_c;

import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArraySet;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.User;

public class Profile extends AppCompatActivity {
    ArraySet<User> list = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


    }
}