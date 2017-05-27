package com.example.bragalund.taxiorder.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.bragalund.taxiorder.DB.DBService;
import com.example.bragalund.taxiorder.DB.Order;

public class OrderTaxiTask extends AsyncTask<Order, Order, Order>{

    private Context mContext;

    //Creates constructor to get the context of the activity
    public OrderTaxiTask(Context context) {
        mContext = context;
    }

    //Backendlogic could be implemented here
    @Override
    protected Order doInBackground(Order... params) {
        DBService dbService = new DBService(mContext, DBService.DB_NAME, null, DBService.DATABASE_VERSION);
        Order order = (Order) params[0];
        dbService.insertOrderIntoDatabase(order);
        return order;
    }

    @Override
    protected void onPostExecute(Order order) {
        super.onPostExecute(order);
        Toast.makeText(mContext, "Taxi to "+order.getDestinationAddress()+" has been ordered.", Toast.LENGTH_LONG).show();

    }
}
