package com.example.psikologi2;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profil_User_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profil_User_fragment extends Fragment {

    TextView NamaUser,Email, Umur, Jenis_Kelamin, NamaUtama, EditProfil;
    LinearLayout Logout;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Profil_User_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profil_User_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Profil_User_fragment newInstance(String param1, String param2) {
        Profil_User_fragment fragment = new Profil_User_fragment();
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
        View view = inflater.inflate(R.layout.activity_profil_user, container, false);

        NamaUtama    = view.findViewById(R.id.idTVnamautama);
        NamaUser    = view.findViewById(R.id.NamaUser);
        Umur       = view.findViewById(R.id.idTVumur);
        Jenis_Kelamin   = view.findViewById(R.id.idTVjeniskelamin);
        Email       = view.findViewById(R.id.Email);
        Logout      = view.findViewById(R.id.idLLlogout);
        EditProfil      = view.findViewById(R.id.idTVEditProfile);

        SessionManager sessionManager = new SessionManager(getActivity());

        NamaUtama.setText(sessionManager.getNama());
        NamaUser.setText(sessionManager.getNama());
        Email.setText(sessionManager.getEmail());
        Umur.setText(sessionManager.getUmur());
        Jenis_Kelamin.setText(sessionManager.getJenisKelamin());

        EditProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getActivity(), Edit_Profil_User.class);
                startActivity(intent);
            }
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SessionManager sesi = new SessionManager(getActivity());
                Intent intent =new Intent(getActivity(), Login.class);
                sesi.logout();
                startActivity(intent);
            }
        });
        return view;
    }
}