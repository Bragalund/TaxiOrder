package com.example.bragalund.taxiorder;


import android.app.Fragment;

import com.google.android.gms.maps.model.LatLng;

public interface Communicator {
    void respond(LatLng latLng);
    void changeBottomFragment(Fragment fragment);
    void changeTopFragment(Fragment fragment);
}