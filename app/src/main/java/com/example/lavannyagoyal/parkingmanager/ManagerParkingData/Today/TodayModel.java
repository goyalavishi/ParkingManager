package com.example.lavannyagoyal.parkingmanager.ManagerParkingData.Today;

import android.media.Image;

public class TodayModel {

    String parkingname;
    int revenue;
    String type;
    int availableParking;
    int capacity;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAvailableParking() {
        return availableParking;
    }

    public void setAvailableParking(int availableParking) {
        this.availableParking = availableParking;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getParkingname() {
        return parkingname;
    }

    public void setParkingname(String parkingname) {
        this.parkingname = parkingname;
    }

    public int getRevenue() {
        return revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }
}
