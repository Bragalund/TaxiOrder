package com.example.bragalund.taxiorder.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bragalund.taxiorder.Util.Communicator;
import com.example.bragalund.taxiorder.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import static android.app.Activity.RESULT_OK;
import static com.google.android.gms.wearable.DataMap.TAG;

public class PlaceAutoCompleteFragment extends Fragment {


    private PlaceAutocompleteFragment placeAutocompleteFragment;
    private static final int RESULT_CANCELED = 0;
    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 0;
    Communicator communicator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_place_autocomplete, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getChildFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        setPlaceAutocompleteFragment(autocompleteFragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {

            @Override
            public void onPlaceSelected(Place place) {
                Log.i(TAG, "Place Selected: " + place.getName() + "  " + place.getLatLng());
                communicator.setDestinationAddressToOrder(place.getAddress().toString());
                communicator.respond(place.getLatLng());
            }

            @Override
            public void onError(Status status) {
                System.out.println("There was an error when the user pressed the place in the Places Autocomplete-bar");
                Log.i(TAG, "An error occurred: " + status);
            }
        });

        autocompleteFragment.getView().findViewById(R.id.place_autocomplete_clear_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                communicator.changeTopFragment(autocompleteFragment);  //This creates nullpointer when trying to get text from
                communicator.setGoogleMapFragment();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                //Everything went well when using the Places Autocomplete search bar
                Place place = PlaceAutocomplete.getPlace(getContext(), data);
                Log.i(TAG, "Place: " + place.getName());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                //There was an error when using the PlaceAutoComplete-searchbar
                Status status = PlaceAutocomplete.getStatus(getContext(), data);
                System.out.println(PlaceAutocomplete.getStatus(getContext(), data));
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                //User canceled operation
            }
        }
    }

    public PlaceAutocompleteFragment getPlaceAutocompleteFragment() {
        return placeAutocompleteFragment;
    }

    public void setPlaceAutocompleteFragment(PlaceAutocompleteFragment placeAutocompleteFragment) {
        this.placeAutocompleteFragment = placeAutocompleteFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            communicator = (Communicator) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
