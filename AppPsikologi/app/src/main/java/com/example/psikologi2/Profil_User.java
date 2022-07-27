package com.example.psikologi2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Profil_User extends AppCompatActivity {

    TextView NamaUser,Email,Umur, Jenis_Kelamin;
    LinearLayout Logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_user);
        NamaUser = findViewById(R.id.NamaUser);
        Email = findViewById(R.id.Email);
        Logout = findViewById(R.id.idLLlogout);
        Umur       = findViewById(R.id.idTVumur);
        Jenis_Kelamin   = findViewById(R.id.idTVjeniskelamin);

        Intent intent   = getIntent();

        SessionManager sessionManager = new SessionManager(Profil_User.this);
        NamaUser.setText(sessionManager.getNama());
        Email.setText(sessionManager.getEmail());
        Umur.setText(sessionManager.getUmur());
        Jenis_Kelamin.setText(sessionManager.getJenisKelamin());
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SessionManager sesi = new SessionManager(Profil_User.this);
                Intent intent =new Intent(Profil_User.this, Login.class);
                sesi.logout();
                startActivity(intent);
                finish();
            }
        });


    }
}