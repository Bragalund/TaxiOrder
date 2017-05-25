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
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getChildFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {

            @Override
            public void onPlaceSelected(Place place) {
                Log.i(TAG, "Place Selected: " + place.getName() + "  " + place.getLatLng());
                System.out.println("-------------------   onPlaceSelected --------------");
                communicator.respond(place.getLatLng());
            }

            @Override
            public void onError(Status status) {
                System.out.println("There was an error when the user pressed the place in the Places Autocomplete-bar");
                Log.i(TAG, "An error occurred: " + status);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(getContext(), data);
                Log.i(TAG, "Place: " + place.getName());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getContext(), data);

                //Errormessage
                //05-24 19:08:56.188 2356-2714/com.google.android.gms E/AsyncOperation: serviceID=65, operation=AddPlace
                //OperationException[Status{statusCode=NETWORK_ERROR, resolution=null}]

                //Found the answer...
                // https://stackoverflow.com/questions/44118233/google-places-api-for-android-network-error

                //Problem was gone the day after. I didnt change anything...

                System.out.println(PlaceAutocomplete.getStatus(getContext(), data));
                Log.i(TAG, status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
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
