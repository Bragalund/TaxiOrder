package com.example.bragalund.taxiorder;


import android.Manifest;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import static com.example.bragalund.taxiorder.R.id.googleMapFragment;

public class GoogleMapFragment extends Fragment
        implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    Marker destinationMarker;
    Marker currentLocationMarker;

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 0;
    Location mLastLocation;
    MapFragment mMapFragment;
    protected GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    double lat = 0, lng = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gmaps, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buildGoogleApiClient();
        mMapFragment = (com.google.android.gms.maps.MapFragment) getChildFragmentManager().findFragmentById(googleMapFragment);
        mMapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //checks permissions...
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            setUpMap();
        } else {
            //Launches request to user to give permissions to fine location.
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    private void setUpMap() {
        //Checks permissions one more time...
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            System.out.println("Permissions is good... ;) ");
            LatLng loc = new LatLng(lat, lng);
            mMap.setMyLocationEnabled(true);
            mMap.addMarker(new MarkerOptions().position(loc).title("You are here!"));
            currentLocationMarker = mMap.addMarker(new MarkerOptions().position(loc).title("You are here!"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 12));
            mMap.animateCamera(CameraUpdateFactory.zoomIn());
            mMap.animateCamera(CameraUpdateFactory.zoomTo(16), 2000, null);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay!
                    setUpMap();
                } else {
                    //The map will load, but it will not zoom onto current location.
                    //Possible to hardcode hotel latitude and longitude here!
                    System.out.println("The permission for fine_location was not granted...");
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }


    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
            if (mLastLocation != null) {
                lat = mLastLocation.getLatitude();
                lng = mLastLocation.getLongitude();
                LatLng loc = new LatLng(lat, lng);
                currentLocationMarker.remove();
                currentLocationMarker = mMap.addMarker(new MarkerOptions().position(loc).title("You are here"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 12));
                mMap.animateCamera(CameraUpdateFactory.zoomIn());
                mMap.animateCamera(CameraUpdateFactory.zoomTo(16), 2000, null);
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(getContext(), "The connection was suspended.", Toast.LENGTH_LONG).show();
    }

    public void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getContext(), "The connection failed", Toast.LENGTH_LONG).show();
    }

    public void addNewMarkerToMap(LatLng latLng) {
        destinationMarker = mMap.addMarker(new MarkerOptions().position(latLng).title("Destination"));
    }

    public void removeDestinationMarker() {
        if (!(destinationMarker == null)) {
            destinationMarker.remove();
        }
    }

    public void zoomOntoTwoMarkers(){
        ArrayList<Marker> markers = new ArrayList<>();
        markers.add(currentLocationMarker);
        markers.add(destinationMarker);

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker marker : markers) {
            builder.include(marker.getPosition());
        }

        LatLngBounds bounds = builder.build();

        int padding = 100;
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding);

        mMap.animateCamera(cameraUpdate);
    }


}
