package com.example.psikologi2;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CLV_Daftar_Psikolog extends ArrayAdapter<String> {

    //Declarasi variable
    private final Activity context;
    private ArrayList<String> VNama;
    private ArrayList<String> VTempatPraktek;
    private ArrayList<String> VAlumni;
    private ArrayList<String> VPengalaman;
    private ArrayList<String> VHarga;
    private ArrayList<String> VBintang;
    private ArrayList<String> VPhoto;
    private ArrayList<String> VSenin;
    private ArrayList<String> VSelasa;
    private ArrayList<String> VRabu;
    private ArrayList<String> VKamis;
    private ArrayList<String> VJumat;
    private ArrayList<String> VNoHp;


    public CLV_Daftar_Psikolog(Activity context,ArrayList<String> Nama, ArrayList<String> TempatPraktek, ArrayList<String> Alumni,
                               ArrayList<String> Pengalaman, ArrayList<String> Harga, ArrayList<String> Bintang, ArrayList<String> Photo,
                               ArrayList<String> Senin, ArrayList<String> Selasa, ArrayList<String> Rabu, ArrayList<String> Kamis,
                               ArrayList<String> Jumat, ArrayList<String> NoHp)
    {
        super(context, R.layout.list_item_dokter,Nama);
        this.context        = context;
        this.VNama          = Nama;
        this.VTempatPraktek = TempatPraktek;
        this.VAlumni        = Alumni;
        this.VPengalaman    = Pengalaman;
        this.VHarga         = Harga;
        this.VBintang       = Bintang;
        this.VPhoto         = Photo;
        this.VSenin         = Senin;
        this.VSelasa        = Selasa;
        this.VRabu          = Rabu;
        this.VKamis         = Kamis;
        this.VJumat         = Jumat;
        this.VNoHp          = NoHp;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        //Load Custom Layout untuk list
        View rowView = inflater.inflate(R.layout.list_item_dokter, null, true);

        //Declarasi komponen
        TextView nama                 = rowView.findViewById(R.id.idTVnama);
        TextView tempatpraktek        = rowView.findViewById(R.id.idTVlokasi);
        TextView bintang              = rowView.findViewById(R.id.idTVbintang);
        TextView harga                = rowView.findViewById(R.id.harga);
        ImageView foto               = rowView.findViewById(R.id.idCIVfoto);

        //Set Parameter Value sesuai widget textview
        nama.setText(VNama.get(position));
        tempatpraktek.setText(VTempatPraktek.get(position));
        bintang.setText(VBintang.get(position));
        harga.setText(VHarga.get(position));

        if (VPhoto.get(position).equals(""))
            Picasso.get().load("https://tkjalpha19.com/mobile/image/noimages.png").into(foto);
        else
        {
            Picasso.get().load("https://tkjalpha19.com/mobile/image/"+VPhoto.get(position)).into(foto);
        }

        //Load the animation from the xml file and set it to the row
        //load animasi untuk listview
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.down_from_top);
        animation.setDuration(500);
        rowView.startAnimation(animation);

        return rowView;
    }
}
