package com.example.piggybankapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;


//TODO selected items do not work.
public class SideBar extends AppCompatActivity
{
    FirebaseAuth mAuth = FirebaseAuth.getInstance(); // Initialize FirebaseAuth if you haven't already

    public void logOut()
    {

        mAuth.signOut();
        Intent intent = new Intent (SideBar.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
     DrawerLayout drawerLayout;
     NavigationView navigationView;
     ActionBarDrawerToggle drawerToggle;

     public boolean onOptionsItemSelected(MenuItem item ){
         if (drawerToggle.onOptionsItemSelected(item)){
             return true;
         }
         return super.onOptionsItemSelected(item);
     }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        drawerToggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);//for screen readers
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true) is a method call used
        // to enable the display of the "Up" or "Back" button in the app's action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                            if( item.getItemId()==R.id.home)
                            {
                                System.out.println("home selected");
                               Toast.makeText(SideBar.this, "home", Toast.LENGTH_SHORT).show();
                               Intent intent=new Intent(SideBar.this,HomeActivity.class) ;
                               startActivity(intent);
                               finish();

                            }
                            else if (item.getItemId()==R.id.categories){
                                System.out.println("category selected");
                                Toast.makeText(SideBar.this, "categories", Toast.LENGTH_SHORT).show();
                            }
                            else if (item.getItemId() == R.id.logout) {
                                System.out.println("logout selected");
                                Toast.makeText(SideBar.this, "logout", Toast.LENGTH_SHORT).show();
                                logOut();
                                // System.exit(0); // Avoid using System.exit(0) to close the app
                                return true; // Return true to indicate that the item click has been handled
                            }


                        return false;
                    }
                }
        );

    }

    public void onBackPressed(){
         if(drawerLayout.isDrawerOpen(GravityCompat.START))
         {
             drawerLayout.closeDrawer(GravityCompat.START);
         }
         else
         {
             super.onBackPressed();

         }
    }


}