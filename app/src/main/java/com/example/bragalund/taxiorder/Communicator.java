package com.example.bragalund.taxiorder;


import com.google.android.gms.maps.model.LatLng;

public interface Communicator {
    void respond(LatLng latLng);
}