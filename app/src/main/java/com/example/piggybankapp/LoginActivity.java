package com.example.piggybankapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity

{

    //data
    FirebaseAuth mAuth;



    public void updateUI(FirebaseUser user)
    {
        //add if statement
        if (user!=null)
        {
        Intent toHomePage = new Intent(LoginActivity.this,HomeActivity.class);
        startActivity(toHomePage);
        finish();
        }
        else
        {
            Toast.makeText(LoginActivity.this, "Please verify your email and address.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {


        super.onCreate(savedInstanceState);
        //link class to layout
        setContentView(R.layout.activity_login);

        //Data
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        TextView signUpLink = findViewById(R.id.signupLink);
        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);
        ImageView signInBtn = findViewById(R.id.imageView2);




        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());

                                    updateUI(null);
                                }
                            }
                        });
            }
        });






        signUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toSignup = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(toSignup);
            }
        });





    }


    @Override
    public void onStart()
    {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null)
        {
            //reload();
            Intent toHome = new Intent(LoginActivity.this,HomeActivity.class);
            startActivity(toHome);
            finish();
        }
    }
}