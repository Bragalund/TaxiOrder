package com.example.bragalund.taxiorder.Util;


import android.app.Fragment;

import com.google.android.gms.maps.model.LatLng;

public interface Communicator {
    void respond(LatLng latLng);
    void replaceSearchBarInPlacesFragment(Fragment fragment);
    void changeBottomFragment(Fragment fragment);
    void changeTopFragment(Fragment fragment);
    void setDestinationAddressToOrder(String destinationAddress);
    void setCurrentAddressToOrder(String currentAddress);
    void orderTaxi();
    void goBackStack();
    void setGoogleMapFragment();
}