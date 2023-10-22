package com.example.piggybankapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {

    //
    //Data
    FirebaseAuth mAuth;


    public void updateUI(FirebaseUser user)
    {
        if (user!=null) {
            Intent toSignIn = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(toSignIn);
            finish();
        }
        else
        {
            Toast.makeText(SignupActivity.this, "Authentication failed.",
                    Toast.LENGTH_SHORT).show();
        }


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);



        //Data
        TextView signinLink = findViewById(R.id.signinLink);
        EditText email = findViewById(R.id.useremail);
        EditText password = findViewById(R.id.userpassword);
        ImageView signUpBtn = findViewById(R.id.signUpButton);



        signinLink.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent toSignIn = new Intent(SignupActivity.this,LoginActivity.class);
                startActivity(toSignIn);
            }
        });


     // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();




        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful())
                                {
                                    // Sign up success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);
                                    //updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());

                                    updateUI(null);


                                }
                            }
                        });

            }
        });




    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            //if the user is already signed in reload the user
            Intent toHome = new Intent(SignupActivity.this,HomeActivity.class);
            startActivity(toHome);
            finish();
        }
    }






}