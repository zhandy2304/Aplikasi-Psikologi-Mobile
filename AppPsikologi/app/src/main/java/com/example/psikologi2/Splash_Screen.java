package com.example.psikologi2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Splash_Screen extends AppCompatActivity {

    private int waktu_loading=4000;
    //4000=4 detik

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SessionManager sesi = new SessionManager(Splash_Screen.this);
                if (sesi.isLogin()){
                    Intent home=new Intent(Splash_Screen.this, Navigation_Bar.class);
                    startActivity(home);
                } else {
                    Intent home=new Intent(Splash_Screen.this, OnBoarding.class);
                    startActivity(home);
                }
                //setelah loading maka akan langsung berpindah ke home activit
                finish();

            }
        },waktu_loading);
    }
}