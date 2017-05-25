package com.example.bragalund.taxiorder.DB;

import java.io.Serializable;

public class Order implements Serializable {
    String orderID;
    String currentAddress;
    String destinationAddress;
    int hour;
    int min;

    public Order(String orderID, String currentAddress, String destinationAddress, int hour, int min) {
        this.orderID = orderID;
        this.currentAddress = currentAddress;
        this.destinationAddress = destinationAddress;
        this.hour = hour;
        this.min = min;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }
}
