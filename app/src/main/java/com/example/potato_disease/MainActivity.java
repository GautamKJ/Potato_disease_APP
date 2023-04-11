package com.example.potato_disease;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("msg","just checking");
        Intent intent=new Intent(this,Weather.class);
        startActivity(intent);
//
//        Userlocation userlocation = new Userlocation();
//
//        Log.d("checking ",userlocation.lat+"        "+userlocation.longi);
    }
}