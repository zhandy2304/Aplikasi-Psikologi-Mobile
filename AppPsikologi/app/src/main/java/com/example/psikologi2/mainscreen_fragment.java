package com.example.psikologi2;

import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link mainscreen_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */


public class mainscreen_fragment extends Fragment {

    ImageView IVpsikeater, Info, IVmaps;
    LinearLayout Profil;
    TextView TVusername;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public mainscreen_fragment() {
        // Required empty public constructor
    }



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment mainscreen_fragment.
     */

    // TODO: Rename and change types and number of parameters
    public static mainscreen_fragment newInstance(String param1, String param2) {
        mainscreen_fragment fragment = new mainscreen_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_mainscreen, container, false);

        IVpsikeater = view.findViewById(R.id.idIVpsikeater);
        Profil      = view.findViewById(R.id.idLL1);
        Info        = view.findViewById(R.id.idIVinfo);
        TVusername  = view.findViewById(R.id.idTVusername);
        IVmaps      = view.findViewById(R.id.idIVmaps);
        SharedPreferences preferences;

        SessionManager sessionManager = new SessionManager(getActivity());

        TVusername.setText(sessionManager.getNama());

        preferences = getActivity().getSharedPreferences("SHARED_PREF", MODE_PRIVATE);

        //mengecek network dan location permission
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

            showSettingsAlert();
        }

        IVpsikeater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getActivity(), Daftar_Psikolog.class);
                startActivity(intent);
            }
        });

        IVmaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getActivity(), Maps.class);
                startActivity(intent);
            }
        });

        Profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getActivity(), Profil_User.class);
                startActivity(intent);
            }
        });

        Info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getActivity(), About.class);
                startActivity(intent);
            }
        });

        return view;
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

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