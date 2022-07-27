package com.example.psikologi2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Daftar_Psikolog extends AppCompatActivity {

    SwipeRefreshLayout srl_main;
    ArrayList<String> array_nama,array_tempat_prakter,array_alumni,array_pengalaman,
                    array_harga, array_bintang, array_foto, array_id_psikeater, array_senin,
                    array_selasa, array_rabu, array_kamis, array_jumat, array_nohp;
    ProgressDialog progressDialog;
    ListView listProses;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_psikolog);

        //set variable sesuai dengan widget yang digunakan
        listProses = findViewById(R.id.idLVdokter);
        srl_main    = findViewById(R.id.swipe_container);
        progressDialog = new ProgressDialog(this);

        srl_main.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                scrollRefresh();
                srl_main.setRefreshing(false);
            }
        });
        // Scheme colors for animation
        srl_main.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light)

        );

        scrollRefresh();
    }

    public void scrollRefresh(){
        progressDialog.setMessage("Mengambil Data.....");
        progressDialog.setCancelable(false);
        progressDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getData();
            }
        },2000);
    }

    void initializeArray(){
        array_nama          = new ArrayList<String>();
        array_tempat_prakter= new ArrayList<String>();
        array_alumni        = new ArrayList<String>();
        array_pengalaman    = new ArrayList<String>();
        array_harga         = new ArrayList<String>();
        array_bintang       = new ArrayList<String>();
        array_foto          = new ArrayList<String>();
        array_id_psikeater  = new ArrayList<String>();
        array_senin         = new ArrayList<String>();
        array_selasa        = new ArrayList<String>();
        array_rabu          = new ArrayList<String>();
        array_kamis         = new ArrayList<String>();
        array_jumat         = new ArrayList<String>();
        array_nohp          = new ArrayList<String>();

        array_nama.clear();
        array_tempat_prakter.clear();
        array_alumni.clear();
        array_pengalaman.clear();
        array_harga.clear();
        array_bintang.clear();
        array_foto.clear();
        array_id_psikeater.clear();


    }

    public void getData(){
        initializeArray();
        AndroidNetworking.get("https://tkjalpha19.com/mobile/api_kelompok_4/uas_getData.php")
                .setTag("Get Data")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();

                        try{
                            Boolean status = response.getBoolean("status");
                            if(status){
                                JSONArray ja = response.getJSONArray("result");
                                Log.d("respon",""+ja);
                                for(int i = 0 ; i < ja.length() ; i++){
                                    JSONObject jo = ja.getJSONObject(i);

                                    array_id_psikeater.add(jo.getString("id_psikeater"));
                                    array_nama.add(jo.getString("nama"));
                                    array_tempat_prakter.add(jo.getString("tempat_praktek"));
                                    array_alumni.add(jo.getString("alumni"));
                                    array_pengalaman.add(jo.getString("pengalaman"));
                                    array_harga.add(jo.getString("harga"));
                                    array_bintang.add(jo.getString("bintang"));
                                    array_foto.add(jo.getString("foto"));
                                    array_senin.add(jo.getString("senin"));
                                    array_selasa.add(jo.getString("selasa"));
                                    array_rabu.add(jo.getString("rabu"));
                                    array_kamis.add(jo.getString("kamis"));
                                    array_jumat.add(jo.getString("jumat"));
                                    array_nohp.add(jo.getString("no_hp"));

                                }

                                //Menampilkan data berbentuk adapter menggunakan class CLVDataUser
                                final CLV_Daftar_Psikolog adapter = new CLV_Daftar_Psikolog(Daftar_Psikolog.this, array_nama,
                                        array_tempat_prakter, array_alumni, array_pengalaman, array_harga, array_bintang, array_foto,
                                        array_senin, array_selasa, array_rabu, array_kamis, array_jumat, array_nohp);
                                //Set adapter to list
                                listProses.setAdapter(adapter);

                                //edit and delete
                                listProses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Log.d("TestKlik",""+array_nama.get(position));
                                        Toast.makeText(Daftar_Psikolog.this, array_nama.get(position), Toast.LENGTH_SHORT).show();

                                        //Setelah proses koneksi keserver selesai, maka aplikasi akan berpindah class
                                        //DataActivity.class dan membawa/mengirim data-data hasil query dari server.
                                        Intent i = new Intent(Daftar_Psikolog.this, Detail.class);
                                        i.putExtra("nama",array_nama.get(position));
                                        i.putExtra("tempat_praktek",array_tempat_prakter.get(position));
                                        i.putExtra("alumni",array_alumni.get(position));
                                        i.putExtra("pengalaman",array_pengalaman.get(position));
                                        i.putExtra("harga",array_harga.get(position));
                                        i.putExtra("bintang",array_bintang.get(position));
                                        i.putExtra("foto",array_foto.get(position));
                                        i.putExtra("id_psikeater",array_id_psikeater.get(position));
                                        i.putExtra("senin",array_senin.get(position));
                                        i.putExtra("selasa",array_selasa.get(position));
                                        i.putExtra("rabu",array_rabu.get(position));
                                        i.putExtra("kamis",array_kamis.get(position));
                                        i.putExtra("jumat",array_jumat.get(position));
                                        i.putExtra("nohp",array_nohp.get(position));

                                        startActivity(i);
                                    }
                                });


                            }else{
                                Toast.makeText(Daftar_Psikolog.this, "Gagal Mengambil Data", Toast.LENGTH_SHORT).show();

                            }

                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            if(resultCode==RESULT_OK){
                scrollRefresh();
            }else if(resultCode==RESULT_CANCELED){
                Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
            }
        }

        if(requestCode==2){
            if(resultCode==RESULT_OK){
                scrollRefresh();
            }else if(resultCode==RESULT_CANCELED){
                Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }

}



