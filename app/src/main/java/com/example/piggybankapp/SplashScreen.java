package com.example.piggybankapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity
{

    private static final long duration = 5000;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent intent =new Intent(SplashScreen.this,LoginActivity.class);
                startActivity(intent);
                finish();

            }
        },duration);




    }
}