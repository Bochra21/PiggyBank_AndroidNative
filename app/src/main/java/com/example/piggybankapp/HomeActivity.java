package com.example.piggybankapp;

import androidx.annotation.NonNull;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.wifi.hotspot2.pps.HomeSp;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
        DrawerLayout drawerLayout;
        BottomNavigationView bottomNavigationView;
        FragmentManager fragmentManager;
        Toolbar toolbar;



    FirebaseAuth mAuth = FirebaseAuth.getInstance(); // Initialize FirebaseAuth if you haven't already
    public void logOut()
    {

        mAuth.signOut();
        Intent intent = new Intent (HomeActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }



        public boolean onNavigationItemSelected( MenuItem item) {

           if( item.getItemId()== R.id.home) {
               getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

           }
            else if( item.getItemId()== R.id.categories) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CategoriesFragment()).commit();
            }
            else if( item.getItemId()== R.id.statistics) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new StatFragment()).commit();
            }
           else  if( item.getItemId()== R.id.logout) {
                logOut();
            }


        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.close,R.string.open);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        //bottom navbar
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                int itemId = item.getItemId();
                if (item.getItemId() == R.id.home) {
                    openFragment(new HomeFragment());
                } else if (item.getItemId() == R.id.categories) {
                    openFragment(new CategoriesFragment());
                } else if (item.getItemId() == R.id.statistics) {
                    openFragment(new StatFragment());
                }
                return true;
            }
        });


        fragmentManager = getSupportFragmentManager();
        openFragment(new HomeFragment());


         }
/*
        Button logoutBtn = findViewById(R.id.logout);

        logoutBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

             logOut();
            }
        });

    */


    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);

        }
        else
        {
            super.onBackPressed();
        }
        super.onBackPressed();
    }


    private void openFragment(Fragment fragment)
    {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container,fragment); //frame layout
        transaction.commit();


    }

}