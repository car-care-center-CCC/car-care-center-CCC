package com.example.triple_c;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telecom.Call;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.textclassifier.TextLinks;
import android.widget.TextView;

import com.amplifyframework.datastore.generated.model.Request;

import org.jetbrains.annotations.NotNull;

public class ContactUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);


    }


}