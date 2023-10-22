package com.example.piggybankapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Data
        TextView signinLink = findViewById(R.id.signinLink);

        signinLink.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent toSignIn = new Intent(SignupActivity.this,LoginActivity.class);
                startActivity(toSignIn);
            }
        });


    }
}