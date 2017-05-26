package com.example.bragalund.taxiorder.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bragalund.taxiorder.R;
import com.example.bragalund.taxiorder.Util.Communicator;

public class TimePickerFragment extends Fragment {

    Communicator communicator;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_timepicker, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public int getCurrentHour() {
        int currentHour = this.getCurrentHour();
        return currentHour;
    }

    public int getCurrentMinute(){
        int currentMinute = this.getCurrentMinute();
        return currentMinute;
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
