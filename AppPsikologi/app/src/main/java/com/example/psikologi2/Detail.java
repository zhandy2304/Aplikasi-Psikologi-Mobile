package com.example.psikologi2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Detail extends AppCompatActivity {

    ImageView imageView;
    TextView TVNama, TVLokasi, TVBintang, TVAlumni, TVPengalaman, TVHarga,
            TVSenin, TVSelasa, TVRabu, TVKamis, TVJumat;
    Button Bayar, Chat;
    String nama, tempatpraktek, harga;
    Spinner WaktuKonsultasi;
    CircleImageView FotoPsikeater;
    int foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        // insialisasi variabel dengan widget yang digunakan
        imageView   = findViewById(R.id.idCIVfoto);
        TVNama      = findViewById(R.id.idTVnama);
        TVLokasi    = findViewById(R.id.idTVlokasi);
        TVBintang   = findViewById(R.id.idTVbintang);
        TVAlumni    = findViewById(R.id.idTValumni);
        TVPengalaman = findViewById(R.id.idTVpengalaman);
        TVHarga     = findViewById(R.id.idTVharga);
        Chat        = findViewById(R.id.idBTchat);
        Bayar       = findViewById(R.id.idBTbayar);
        TVSenin     = findViewById(R.id.idTVSenin);
        TVSelasa    = findViewById(R.id.idTVSelasa);
        TVRabu      = findViewById(R.id.idTVRabu);
        TVKamis     = findViewById(R.id.idTVKamis);
        TVJumat     = findViewById(R.id.idTVJumat);
        WaktuKonsultasi = findViewById(R.id.spinnerwaktu);
        FotoPsikeater   = findViewById(R.id.idCIVfoto);

        Intent intent = getIntent();

        String message = "Halo saya ingin konsultasi";

        // menangkap data nama,nama dan kelas kemudian disimpan pada
        // variabelnya masing-masing
        String id = intent.getStringExtra("id_psikeater");
        String nama = intent.getStringExtra("nama");
        String tempatpraktek = intent.getStringExtra("tempat_praktek");
        String alumni = intent.getStringExtra("alumni");
        String pengalaman = intent.getStringExtra("pengalaman");
        String bintang = intent.getStringExtra("bintang");
        String harga = intent.getStringExtra("harga");
        String photo = intent.getStringExtra("foto");
        String senin = intent.getStringExtra("senin");
        String selasa = intent.getStringExtra("selasa");
        String rabu = intent.getStringExtra("rabu");
        String kamis = intent.getStringExtra("kamis");
        String jumat = intent.getStringExtra("jumat");
        String nohp = intent.getStringExtra("nohp");

        // memasukkan data yang diterima berupa foto, nama, nim dan kelas
        TVNama.setText(nama);
        TVLokasi.setText(tempatpraktek);
        TVAlumni.setText(alumni);
        TVPengalaman.setText(pengalaman);
        TVHarga.setText(harga);
        TVBintang.setText(bintang);
        TVSenin.setText(senin);
        TVSelasa.setText(selasa);
        TVRabu.setText(rabu);
        TVKamis.setText(kamis);
        TVJumat.setText(jumat);
        if (photo.equals(""))
            Picasso.get().load("https://tkjalpha19.com/mobile/image/noimage.png").into(imageView);
        else
        {
            Picasso.get().load("https://tkjalpha19.com/mobile/image/"+photo).into(imageView);
        }

        Chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean installed = appInstalledOrNot("com.whatsapp");
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+62"+ nohp + "&text="+ message));
                startActivity(intent);
            }
        });

        Bayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { ShowDialog(); }
        });

    }

    private void ShowDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder
                //Alert title
                .setTitle("Pembayaran")

                //isi dialog
                .setMessage("Apakah anda yakin ingin melakukan pembayaran ?")

                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        nama            = TVNama.getText().toString();
                        tempatpraktek   = TVLokasi.getText().toString();
                        harga           = TVHarga.getText().toString();


                        Intent intent =new Intent(Detail.this, Pembayaran.class);

                       // melakukan bundle untuk mengirim foto
                        /*Bundle bundle = new Bundle();
                        bundle.putInt("image", FotoPsikeater);
                        intent.putExtras(bundle);*/

                        //spinitem = WaktuKonsultasi.getSelectedItem().toString();
                        intent.putExtra("nama", nama);
                        intent.putExtra("tempatpraktek", tempatpraktek);
                        intent.putExtra("harga", harga);
                        //intent.putExtra("waktukonsultasi", spinitem);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    //Create method appInstalledOrNot
    private boolean appInstalledOrNot(String url){
        PackageManager packageManager =getPackageManager();
        boolean app_installed;
        try {
            packageManager.getPackageInfo(url,PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }catch (PackageManager.NameNotFoundException e){
            app_installed = false;
        }
        return app_installed;
    }
}