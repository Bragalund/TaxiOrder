package com.example.bragalund.taxiorder;

import android.app.Fragment;
import android.content.Context;
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

import static com.google.android.gms.wearable.DataMap.TAG;

public class PlaceAutoCompleteFragment extends Fragment {

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
                // Denne metoden kjøres når brukeren trykker på et sted som har blitt foreslått av Place AutoComplete-Fragmentet.
                double latitude = place.getLatLng().latitude;
                double longitude = place.getLatLng().longitude;
                communicator.respond(latitude, longitude);
            }

            @Override
            public void onError(Status status) {
                Log.i(TAG, "An error occurred: " + status);
            }
        });
    }




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            communicator = (Communicator) context;
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
