package com.example.restro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Splash_screen extends AppCompatActivity {
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    protected void onStart() {
        super.onStart();
//         Check for existing Google Sign In account, if the user is already signed in
// the GoogleSignInAccount will be non-null.

        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null && getSharedPref().equals("in")) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(Splash_screen.this, HomeFragmentParent.class);
                    startActivity(i);
                    finish();
                }
            },1000);
        }
        else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(Splash_screen.this, Login_page_praent.class);
                    startActivity(i);
                }
            },1000);
        }
    }
    public String getSharedPref(){
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);

       return  sh.getString("LOG", "");

    }
}