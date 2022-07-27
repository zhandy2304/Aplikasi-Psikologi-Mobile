package com.example.psikologi2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Maps extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap map;
    // creating array list for adding all our locations.
    private ArrayList<LatLng> locationArrayList;
    LatLng center;
    CameraPosition cameraPosition;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((R.layout.activity_maps));

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.id_map);
        mapFragment.getMapAsync(this);


        LatLng unhas = new LatLng(-5.141341, 119.482776);
        LatLng psikeater1 = new LatLng(-5.142530,119.483052);
        LatLng psikeater2 = new LatLng(-5.136180, 119.495281);
        LatLng psikeater3 = new LatLng(-5.143417, 119.481850);
        LatLng psikeater4 = new LatLng(-5.149540, 119.449489);

        // in below line we are initializing our array list.
        locationArrayList = new ArrayList<>();

        // on below line we are adding our
        // locations in our array list.
        locationArrayList.add(unhas);
        locationArrayList.add(psikeater1);
        locationArrayList.add(psikeater2);
        locationArrayList.add(psikeater3);
        locationArrayList.add(psikeater4);

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;

        center = new LatLng(-5.141341, 119.482776);
        cameraPosition = new CameraPosition.Builder().target(center).zoom(15).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        for (int i = 0; i < locationArrayList.size(); i++) {

            // below line is use to add marker to each location of our array list.
            map.addMarker(new MarkerOptions().position(locationArrayList.get(i)).title("Marker"));

            /*// below lin is use to zoom our camera on map.
            map.animateCamera(CameraUpdateFactory.zoomTo(20.0f));

            // below line is use to move our camera to the specific location.
            map.moveCamera(CameraUpdateFactory.newLatLng(locationArrayList.get(i)));*/
        }
    }

}