package com.example.psikologi2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class Pembayaran extends AppCompatActivity {

    TextView NamaUser, NamaPsikeater, AlamatPsikeater, Harga, JadwalKonsultasi, TotalPembayaran;

    CircleImageView FotoPsikeater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);

        NamaUser        = findViewById(R.id.idTVUser);
        NamaPsikeater   = findViewById(R.id.idTVNamaPsikeater);
        AlamatPsikeater = findViewById(R.id.idTVLokasiPsikeater);
        Harga           = findViewById(R.id.idTVharga);
        JadwalKonsultasi = findViewById(R.id.idTVjadwal);
        TotalPembayaran  = findViewById(R.id.idTVTotalHarga);
        FotoPsikeater     = findViewById(R.id.idCIVfoto);

        SessionManager sessionManager = new SessionManager(Pembayaran.this);

        NamaUser.setText(sessionManager.getNama());

        Intent intent = getIntent();

        // menangkap data nama,nama dan kelas kemudian disimpan pada
        // variabelnya masing-masing
        String nama = intent.getStringExtra("nama");
        String tempatpraktek = intent.getStringExtra("tempatpraktek");
        String harga = intent.getStringExtra("harga");
        String waktukonsultasi = intent.getStringExtra("waktukonsultasi");

        // membuat bundle kemudian membuka bungkusan dari foto yang dikirimkan
        Bundle bundle = this.getIntent().getExtras();
        int pic = bundle.getInt("image");

        // memasukkan data yang diterima berupa foto, nama, nim dan kelas
        NamaPsikeater.setText(nama);
        AlamatPsikeater.setText(tempatpraktek);
        Harga.setText(harga);
        TotalPembayaran.setText(harga);
        JadwalKonsultasi.setText(waktukonsultasi);
        //FotoPsikeater.setImageResource(pic);

    }
}