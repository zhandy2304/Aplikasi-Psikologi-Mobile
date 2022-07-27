package com.example.psikologi2;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link mainscreen_psikeater_fragmen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class mainscreen_psikeater_fragmen extends Fragment {
    TextView TVusername;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public mainscreen_psikeater_fragmen() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment mainscreen_psikeater_fragmen.
     */
    // TODO: Rename and change types and number of parameters
    public static mainscreen_psikeater_fragmen newInstance(String param1, String param2) {
        mainscreen_psikeater_fragmen fragment = new mainscreen_psikeater_fragmen();
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
        View view = inflater.inflate(R.layout.activity_mainscreen_psikeater, container, false);

        TVusername = view.findViewById(R.id.idTVusername);

        SharedPreferences preferences;

        SessionManager sessionManager = new SessionManager(getActivity());

        TVusername.setText(sessionManager.getNama());
        return view;
    }
}