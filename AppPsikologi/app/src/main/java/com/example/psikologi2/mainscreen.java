package com.example.psikologi2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.SupportMapFragment;

import java.util.ArrayList;

public class mainscreen extends AppCompatActivity {

    ImageView IVpsikeater, IVmaps;
    LinearLayout Profil, About;
    TextView TVusername;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainscreen);

        IVpsikeater = findViewById(R.id.idIVpsikeater);
        Profil      = findViewById(R.id.idLL1);
        About       = findViewById(R.id.idLL3);
        TVusername  = findViewById(R.id.idTVusername);
        IVmaps      = findViewById(R.id.idIVmaps);
        SharedPreferences preferences;

        Intent intent   = getIntent();

        SessionManager sessionManager = new SessionManager(mainscreen.this);

        //mengecek network dan location permission
        if (ActivityCompat.checkSelfPermission(mainscreen.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mainscreen.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) mainscreen.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

            showSettingsAlert();
        }

        TVusername.setText(sessionManager.getEmail());

        IVpsikeater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(mainscreen.this, Daftar_Psikolog.class);
                startActivity(intent);
            }
        });

        IVmaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(mainscreen.this, Maps.class);
                startActivity(intent);
            }
        });

        Profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(mainscreen.this, Profil_User.class);
                startActivity(intent);
            }
        });

        About.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(mainscreen.this, About.class);
                startActivity(intent);
            }
        });
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mainscreen.this);

        // Setting Dialog Title
        alertDialog.setTitle("GPS");

        // Setting Dialog Message
        alertDialog.setMessage("Please enable location in settings for accurate results!");

        // On Pressing Setting button
        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                });

        // On pressing cancel button
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }
}
