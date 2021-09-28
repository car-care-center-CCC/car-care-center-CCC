package com.example.triple_c;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.collection.ArraySet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;


import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Car;
import com.amplifyframework.datastore.generated.model.Request;
import com.amplifyframework.datastore.generated.model.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Profile extends AppCompatActivity {
    ArraySet<User> list = null;

    List<Request> responseList = new ArrayList<Request>();
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        TextView editText = findViewById(R.id.firstAndLastName);
        TextView phoneNumberText = findViewById(R.id.phoneNumberText);
        TextView emailText = findViewById(R.id.emailText);

//        Amplify.API.query(
//                ModelQuery.list(com.amplifyframework.datastore.generated.model.User.class),
//                response -> {
//
//                    for (User todo : response.getData()) {
//                        Log.i("MyAmplifyApp", todo.getFirstname());
//                        if (todo.getFirstname().equals("ibrahim")) {
////                            responseList.add(todo);
//
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    editText.setText(todo.getFirstname() + " " + todo.getLastname());
//                                }
//                            });
//                            System.out.println("============" + todo.getFirstname());
//                            System.out.println("============" + todo.getCreatedAt());
//                        }
//
//                    }
////                    handler.sendEmptyMessage(1); // send to the handler
//                },
//                error -> Log.e("MyAmplifyApp", "Query failure", error)
//        );

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Profile.this);
        String userId = sharedPreferences.getString("userId", "");

        Amplify.API.query(
                ModelQuery.get(User.class, userId),
                response -> {
                    user = response.getData();
                    responseList = user.getRequest();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            renderTheData();
//                            renderNewImg(user.getId());
                            String allTheFirstName = user.getFirstname();
                            String firstLetter = allTheFirstName.substring(0, 1);// get First letter of the string
                            String remLettersString = allTheFirstName.substring(1).toLowerCase();// Get remaining letter using substring

                            firstLetter = firstLetter.toUpperCase();
                            String firstLetterCapitalizedName = firstLetter + remLettersString;

                            String allLastName = user.getLastname();
                            String firstLetterLastName = allLastName.substring(0, 1);
                            String remLastName = allLastName.substring(1).toLowerCase();

                            firstLetterLastName = firstLetterLastName.toUpperCase();
                            String lastName = firstLetterLastName + remLastName;

//                            responseList=user.getRequest();
                            editText.setText(firstLetterCapitalizedName + " " + lastName);

                            phoneNumberText.setText(user.getPhone());
                            emailText.setText(user.getEmail());
                        }
                    });

                },
                error -> Log.e("MyAmplifyApp", error.toString(), error)
        );


//        try {
//            renderTheData();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        TextView moveToCar = findViewById(R.id.moveToCar);
        moveToCar.setOnClickListener(view -> {
            Intent moveToCarPage = new Intent(Profile.this, AddCar.class);
            startActivity(moveToCarPage);
        });

        TextView moveToServicePage = findViewById(R.id.moveToServicePage);
        moveToServicePage.setOnClickListener(view -> {
            Intent moveToService = new Intent(Profile.this, OurServices.class);
            startActivity(moveToService);
        });

        Button addPhoto = findViewById(R.id.addPhoto);
        addPhoto.setOnClickListener(view -> {
            Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
            chooseFile.setType("*/*");
            chooseFile = Intent.createChooser(chooseFile, "Choose a file");
            startActivityForResult(chooseFile, 1234);
        });

    }

    public void renderTheData() {
        RecyclerView recyclerView = findViewById(R.id.recyclerViewInProfilePage);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new OurAdapter(responseList));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        saveImgToS3(user.getId(), data.getData());
    }

    public void saveImgToS3(String imgName, Uri imgData) {
        try {
            InputStream exampleInputStream = getContentResolver().openInputStream(imgData);
            Amplify.Storage.uploadInputStream(
                    imgName,
                    exampleInputStream,
                    result -> {
                        Log.i("MyAmplifyApp", "Successfully uploaded: " + result.getKey());
                        renderNewImg(imgName);
                    },
                    storageFailure -> Log.e("MyAmplifyApp", "Upload failed", storageFailure)
            );
        } catch (FileNotFoundException error) {
            Log.e("MyAmplifyApp", "Could not find file to open for input stream.", error);
        }
    }

    public void renderNewImg(String imgName) {
        ImageView profilePic = (ImageView) findViewById(R.id.profilePic);
        Amplify.Storage.downloadFile(
                imgName,
                new File(getApplicationContext().getFilesDir() + "/" + imgName + ".jpg"),
                result -> {
                    Log.i("MyAmplifyApp", "Successfully downloaded: " + result.getFile().getName());
                    Bitmap bitmap = BitmapFactory.decodeFile(result.getFile().getPath());
                    profilePic.setImageBitmap(bitmap);
                },
                error -> Log.e("MyAmplifyApp", "Download Failure", error)
        );
    }


//    public void renderTheData() throws InterruptedException {
//        RecyclerView recyclerView = findViewById(R.id.recyclerViewInProfilePage);
//        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//
//
//        Handler handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
//            @Override
//            public boolean handleMessage(@NonNull Message message) {  //It will notify the recyclerview that there are a data changed
//                recyclerView.getAdapter().notifyDataSetChanged();
//                return false;
//            }
//        });
//
//        Amplify.API.query(
//                ModelQuery.list(com.amplifyframework.datastore.generated.model.Request.class),
//                response -> {
//
//                    for (Request todo : response.getData()) {
//                        Log.i("MyAmplifyApp", todo.getName());
//                            responseList.add(todo);
//                        System.out.println("++++++++++++++" + todo.getService());
//                    }
//                    handler.sendEmptyMessage(1); // send to the handler
//                },
//                error -> Log.e("MyAmplifyApp", "Query failure", error)
//        );
//
////        Request request= new Request(" HI" , "Ibrahim" , "nothing" , "077445" , false, "washing", "ibrahim" , "");
//        Thread.sleep(2000);
//        System.out.println("========================================"+responseList);
//        OurAdapter adapter= new OurAdapter(responseList);
//        recyclerView.setAdapter(adapter);
//    }


    private void updateData() {

        Amplify.API.query(
                ModelQuery.list(com.amplifyframework.datastore.generated.model.User.class),
                response -> {
                    boolean isThere = false;
                    for (User user : response.getData()) {
                        Log.i("MyAmplifyApp", user.getId());
                        if (user != null) {
                            if (user.getUsername().equals("Ibrahim")) {

                                if (true) {
                                    Log.i("MyAmplifyApp", "UpdateQuery");

                                    User userUpdate = user.copyOfBuilder()
                                            .firstname("Hello")
                                            .lastname("Hello")
                                            .phone("0772448924")
                                            .username("Ibrahim")
                                            .email("ibrahimalhamshari742@gmail.com")
                                            .image("Hello")
                                            .id(user.getId())
                                            .build();

                                    Amplify.API.mutate(ModelMutation.update(userUpdate),
                                            response3 -> {
                                                Log.i("MyAmplifyApp", "Updated Todo with id: " + response3.getData().getFirstname());
                                                System.out.println("+++++++++++++++++++++++++++++++" + user.getId());
                                                System.out.println();
                                            },
                                            error -> Log.e("MyAmplifyApp", "Update failed", error)
                                    );
                                } else {
                                    Log.e("MyAmplifyApp", "Insert Query");
                                    Amplify.API.mutate(ModelMutation.create(user),
                                            response2 -> Log.i("MyAmplifyApp", "Added Todo with id: " + response2.getData().getId()),
                                            error -> Log.e("MyAmplifyApp", "Create failed", error)
                                    );
                                }


                                System.out.println("==========================" + user.getEmail());
                                System.out.println("==========================" + user.getId());
                                break;
                            }
                        }
                    }
                },
                error -> Log.e("MyAmplifyApp", "Query failure", error)
        );

    }
}