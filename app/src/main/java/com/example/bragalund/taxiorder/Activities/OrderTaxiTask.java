package com.example.bragalund.taxiorder.Activities;

import android.os.AsyncTask;

import com.example.bragalund.taxiorder.DB.Order;

public class OrderTaxiTask extends AsyncTask<Order, Order, Order>{

    @Override
    protected Order doInBackground(Order ...orders) {
        return orders;
    }

    @Override
    protected void onPostExecute(Order order) {
        super.onPostExecute(order);

    }
}
