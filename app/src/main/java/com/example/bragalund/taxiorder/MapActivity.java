package com.example.bragalund.taxiorder;

import android.app.Activity;
import android.app.Fragment;
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
        transaction.replace(R.id.bottom_fragment_container, new GoogleMapFragment()).commit();

        transaction = fragmentManager.beginTransaction();
        //Loads the address form
        transaction.replace(R.id.top_fragment_container, new PlaceAutoCompleteFragment()).commit();
    }

    @Override
    public void respond(LatLng latLng) {
        GoogleMapFragment mapFragment = (GoogleMapFragment) getFragmentManager().findFragmentById(R.id.bottom_fragment_container);
        mapFragment.removeDestinationMarker();
        mapFragment.addNewMarkerToMap(latLng);
        mapFragment.zoomOntoTwoMarkers();
        changeTopFragment(new CorrectLocationFragment());
    }

    @Override
    public void changeTopFragment(Fragment fragment){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.top_fragment_container, fragment).commit();
    }

    @Override
    public void changeBottomFragment(Fragment fragment){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.bottom_fragment_container, fragment).commit();
    }
}
