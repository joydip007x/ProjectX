package com.example.projectx;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class EmptyDemo extends AppCompatActivity {


    String postUID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_demo);
        postUID= getIntent().getExtras().getString("postUID");



    }

}