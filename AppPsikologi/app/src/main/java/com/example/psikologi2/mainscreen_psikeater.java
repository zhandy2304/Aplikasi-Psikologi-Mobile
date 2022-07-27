package com.example.psikologi2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;

public class mainscreen_psikeater extends AppCompatActivity {
    TextView TVusername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainscreen_psikeater);
        TVusername  = findViewById(R.id.idTVUser);
        SharedPreferences preferences;

        Intent intent   = getIntent();
        SessionManager sessionManager = new SessionManager(mainscreen_psikeater.this);

        //mengecek network dan location permission
        if (ActivityCompat.checkSelfPermission(mainscreen_psikeater.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mainscreen_psikeater.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) mainscreen_psikeater.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);


        }

        TVusername.setText(sessionManager.getNama());
    }


    }

