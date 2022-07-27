package com.example.psikologi2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Profil_Psikeater extends AppCompatActivity {
    TextView NamaUser,Tempat_Praktek,Alumni,Pengalaman, Biayakonsultasi;
    LinearLayout Logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_psikeater);
        NamaUser = findViewById(R.id.idTVUser);
        Alumni   = findViewById(R.id.idTValumni);
        Pengalaman = findViewById(R.id.idTVpengalaman);
        Tempat_Praktek = findViewById(R.id.idTVtempatpraktek);
        Biayakonsultasi = findViewById(R.id.harga);
        Logout = findViewById(R.id.idLLlogout);

        Intent intent   = getIntent();

        SessionManager sessionManager = new SessionManager(Profil_Psikeater.this);
        NamaUser.setText(sessionManager.getNama());
        Tempat_Praktek.setText(sessionManager.gettempatpraktek());
        Alumni.setText(sessionManager.getalumni());
        Pengalaman.setText(sessionManager.getpengalaman());
        Biayakonsultasi.setText(sessionManager.getharga());
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SessionManager sesi = new SessionManager(Profil_Psikeater.this);
                Intent intent =new Intent(Profil_Psikeater.this, Login.class);
                sesi.logout();
                startActivity(intent);
                finish();
            }
        });


    }
}