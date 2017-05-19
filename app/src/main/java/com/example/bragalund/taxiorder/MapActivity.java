package com.example.bragalund.taxiorder;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;

public class MapActivity extends Activity implements Communicator{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        //Loads the google maps-mMapFragment into the gmaps_fragment_container
        transaction.replace(R.id.gmaps_fragment_container, new GoogleMapFragment()).commit();

        transaction = fragmentManager.beginTransaction();
        //Loads the address form
        transaction.replace(R.id.top_fragment_container, new PlaceAutoCompleteFragment()).commit();
    }

    @Override
    public void respond(LatLng latLng) {
        FragmentManager fragmentManager = getFragmentManager();
        GoogleMapFragment map = (GoogleMapFragment) fragmentManager.findFragmentById(R.id.map);
        map.addNewMarkerToMap(latLng);
    }
}
