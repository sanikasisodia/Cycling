package com.example.cyclingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.cyclingapp.data.Setup;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       new Setup();
    }
}