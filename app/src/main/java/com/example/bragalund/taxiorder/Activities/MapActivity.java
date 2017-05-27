package com.example.bragalund.taxiorder.Activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import com.example.bragalund.taxiorder.AsyncTasks.OrderTaxiTask;
import com.example.bragalund.taxiorder.DB.Order;
import com.example.bragalund.taxiorder.Fragments.CorrectLocationFragment;
import com.example.bragalund.taxiorder.Fragments.GoogleMapFragment;
import com.example.bragalund.taxiorder.Fragments.PlaceAutoCompleteFragment;
import com.example.bragalund.taxiorder.Fragments.TimePickerFragment;
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
        order = new Order(0, "", "", 0, 0);

        fragmentManager = getFragmentManager();

        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.bottom_fragment_container, new GoogleMapFragment()).commit();

        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.top_fragment_container, new PlaceAutoCompleteFragment()).commit();
    }

    @Override
    public void respond(LatLng latLng) {
        GoogleMapFragment mapFragment = (GoogleMapFragment) getFragmentManager().findFragmentById(R.id.bottom_fragment_container);
        mapFragment.removeDestinationMarker();
        mapFragment.addNewMarkerToMap(latLng);
        mapFragment.zoomOntoTwoMarkers();
        replaceSearchBarInPlacesFragment(new CorrectLocationFragment());
    }

    @Override
    public void replaceSearchBarInPlacesFragment(Fragment fragment) {
        transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.place_autocomplete_fragment, fragment).commit();
    }

    @Override
    public void goBackStack() {
        super.onBackPressed();
    }


    @Override
    public void setDestinationAddressToOrder(String destinationAddress) {
        order.setDestinationAddress(destinationAddress);
    }

    @Override
    public void setCurrentAddressToOrder(String currentAddress) {
        order.setCurrentAddress(currentAddress);
    }

    @Override
    public void orderTaxi() {
        TimePickerFragment timePickerFragment = (TimePickerFragment) getFragmentManager().findFragmentById(R.id.google_map_fragment);
        order.setHour(timePickerFragment.getHour());
        order.setMin(timePickerFragment.getMin());

        //Async task
        OrderTaxiTask orderTaxiTask = new OrderTaxiTask(this);
        orderTaxiTask.execute(order);

        //Changes back to startscreen
        changeActivityToStartScreen();
    }

    private void changeActivityToStartScreen() {
        Intent intent = new Intent(getApplicationContext(), StartScreenActivity.class);
        startActivity(intent);
    }

    @Override
    public void changeBottomFragment(Fragment fragment) {
        transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.google_map_fragment, fragment).commit();
    }

    @Override
    public void changeTopFragment(Fragment fragment) {
        transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.place_autocomplete_fragment, fragment).commit();
    }

    @Override
    public void setGoogleMapFragment() {

        //Wanted to do this (but it doesnt work, because of timepickerfragment is loaded in fragment):
        //GoogleMapFragment googleMapFragment = (GoogleMapFragment)  fragmentManager.findFragmentById(R.id.google_map_fragment);
        //transaction.replace(R.id.google_map_fragment, googleMapFragment.getmMap());
        //transaction.commit();

        //Recreates google-map-fragment (it is a workaround) and user have to get current location again.
        //TODO get same instance of google map when app starts without having error with renaming id of object...
        Fragment old = fragmentManager.findFragmentById(R.id.bottom_fragment_container);
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.remove(old);
        Fragment newInstance = recreateFragment(old);
        ft.add(R.id.bottom_fragment_container, newInstance);
        ft.commit();
    }


    //Workaround method...
    private Fragment recreateFragment(Fragment f) {
        try {
            Fragment.SavedState savedState = fragmentManager.saveFragmentInstanceState(f);

            Fragment newInstance = f.getClass().newInstance();
            newInstance.setInitialSavedState(savedState);

            return newInstance;
        } catch (Exception e) // InstantiationException, IllegalAccessException
        {
            throw new RuntimeException("Cannot reinstantiate fragment " + f.getClass().getName(), e);
        }
    }
}
