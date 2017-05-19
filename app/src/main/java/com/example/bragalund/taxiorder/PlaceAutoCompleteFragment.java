package com.example.bragalund.taxiorder;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;

import static com.google.android.gms.wearable.DataMap.TAG;

public class PlaceAutoCompleteFragment extends Fragment{

    private static String PATIENT_LAT, PATIENT_LON;
    Communicator communicator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_place_autocomplete, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getChildFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {

            @Override
            public void onPlaceSelected(Place place) {
                Log.i(TAG, "Place Selected: " + place.getName() + "  " + place.getLatLng());
               //TODO denne metoden kjøres når brukeren trykker på et sted som har blitt foreslått av Place AutoComplete-Fragmentet.
                //newInstance(place.getLatLng());
                communicator.respond(place.getLatLng());
            }

            @Override
            public void onError(Status status) {
                Log.i(TAG, "An error occurred: " + status);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        communicator= (Communicator) getActivity();
    }

    //TODO Finn ut av hvordan informasjon om langitude og longitude kan sendes fra dette fragmentet til GoogleMapFragment
    //TODO Det gjør at man kan tilpasse kartet utifra hvilke addresse personen ønsker å dra til
    public static PlaceAutocompleteFragment newInstance(LatLng latLng){
        PlaceAutocompleteFragment fragment = new PlaceAutocompleteFragment();
        Bundle bundle = new Bundle();
        double x = latLng.latitude;
        double y = latLng.longitude;
        bundle.putDouble(PATIENT_LAT, x);
        bundle.putDouble(PATIENT_LON, y);
        fragment.setArguments(bundle);
        return fragment;
    }


}
