package com.example.bragalund.taxiorder.Fragments;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.bragalund.taxiorder.Activities.MainActivity;
import com.example.bragalund.taxiorder.DB.DBService;
import com.example.bragalund.taxiorder.DB.Order;
import com.example.bragalund.taxiorder.Util.Communicator;
import com.example.bragalund.taxiorder.R;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;

public class OrderTaxiFragment extends Fragment {
    Button yesButton, noButton;
    Communicator communicator;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.order_taxi_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        yesButton = (Button) getView().findViewById(R.id.yes_order_taxi_fragment_button);
        noButton = (Button) getView().findViewById(R.id.no_order_taxi_fragment_button);
        yesButton.setOnClickListener(yesButtonOnClickListener);
        noButton.setOnClickListener(noButtonOnClickListener);
    }

    View.OnClickListener noButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            System.out.println("No button was clicked...");
            communicator.changeTopFragment(new PlaceAutocompleteFragment());
            communicator.changeBottomFragment(new GoogleMapFragment());
        }
    };

    View.OnClickListener yesButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            System.out.println("Yes button was clicked...");

            //TODO implement new intent and store data to database here...
            DBService dbService = new DBService(getContext(), DBService.DB_NAME, null, DBService.DATABASE_VERSION);
            Order order = new Order(0, "someCurrenttestAddress 1", "someDestinationAddress 2", 02, 25);
            dbService.insertOrderIntoDatabase(order);


            Toast.makeText(getContext(), "Your taxi has been ordered.", Toast.LENGTH_LONG).show();
            changeActivityToStartScreen();
        }
    };

    private void changeActivityToStartScreen(){
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
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
