package com.example.bragalund.taxiorder.Activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.example.bragalund.taxiorder.DB.Order;
import com.example.bragalund.taxiorder.Fragments.CorrectLocationFragment;
import com.example.bragalund.taxiorder.Fragments.GoogleMapFragment;
import com.example.bragalund.taxiorder.Fragments.PlaceAutoCompleteFragment;
import com.example.bragalund.taxiorder.R;
import com.example.bragalund.taxiorder.Util.Communicator;
import com.google.android.gms.maps.model.LatLng;

public class MapActivity extends Activity implements Communicator {
    Order order;
    FragmentManager fragmentManager;
    FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);
        fragmentManager = getFragmentManager();

        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.bottom_fragment_container, new GoogleMapFragment()).commit();

        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.top_fragment_container, new PlaceAutoCompleteFragment()).commit();
    }

    @Override
    public void respond(LatLng latLng) {
        System.out.println("------------- Responded ------------");
        GoogleMapFragment mapFragment = (GoogleMapFragment) getFragmentManager().findFragmentById(R.id.bottom_fragment_container);
        mapFragment.removeDestinationMarker();
        mapFragment.addNewMarkerToMap(latLng);
        mapFragment.zoomOntoTwoMarkers();
        changeTopFragment(new CorrectLocationFragment());
    }

    @Override
    public void changeTopFragment(Fragment fragment) {
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.top_fragment_container, fragment).commit();
    }




    public void setCurrentAddressToOrder(String currentAddress) {

    }

    @Override
    public void changeBottomFragment(Fragment fragment) {
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.bottom_fragment_container, fragment).commit();
    }
}