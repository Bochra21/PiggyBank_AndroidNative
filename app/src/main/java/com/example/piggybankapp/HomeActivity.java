package com.example.piggybankapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.wifi.hotspot2.pps.HomeSp;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity
{

    FirebaseAuth mAuth = FirebaseAuth.getInstance(); // Initialize FirebaseAuth if you haven't already

    public void logOut()
    {

        mAuth.signOut();
        Intent intent = new Intent (HomeActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        Button logoutBtn = findViewById(R.id.logout);

        logoutBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

             logOut();
            }
        });

    }
}