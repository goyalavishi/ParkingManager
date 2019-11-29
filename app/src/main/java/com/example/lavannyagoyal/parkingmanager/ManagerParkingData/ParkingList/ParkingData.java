package com.example.lavannyagoyal.parkingmanager.ManagerParkingData.ParkingList;

import com.example.lavannyagoyal.parkingmanager.ManagerParkingData.ParkingList.CarList;

import java.util.ArrayList;

public class ParkingData {

    String parkingid;
    String parkingname;
    String address;
    int available;
    String type;
    String licenseplateno;
    String date;
    String in;
    String out;
    int collection;

    public int getCollection() {
        return collection;
    }

    public void setCollection(int collection) {
        this.collection = collection;
    }

    public String getIn() {
        return in;
    }

    public void setIn(String in) {
        this.in = in;
    }

    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ParkingData()
    {

    }

    public ParkingData(String parkingid, String parkingname, String address, int available, String type, int totalSpace, long total_cars_entered_till_now, double total_amount_till_now, ArrayList<CarList> carList) {
        this.parkingid = parkingid;
        this.parkingname = parkingname;
        this.address = address;
        this.available = available;
        this.type = type;
        this.totalSpace = totalSpace;
        this.total_cars_entered_till_now = total_cars_entered_till_now;
        this.total_amount_till_now = total_amount_till_now;
        this.carList = carList;
    }

    int totalSpace;
    long total_cars_entered_till_now;
    double total_amount_till_now;
    ArrayList<CarList> carList;


    public ArrayList<CarList> getCarList() {
        return carList;
    }

    public void setCarList(ArrayList<CarList> carList) {
        this.carList = carList;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParkingid() {
        return parkingid;
    }

    public void setParkingid(String parkingid) {
        this.parkingid = parkingid;
    }

    public String getParkingname() {
        return parkingname;
    }

    public void setParkingname(String parkingname) {
        this.parkingname = parkingname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getTotalSpace() {
        return totalSpace;
    }

    public void setTotalSpace(int totalSpace) {
        this.totalSpace = totalSpace;
    }

    public long getTotal_cars_entered_till_now() {
        return total_cars_entered_till_now;
    }

    public void setTotal_cars_entered_till_now(long total_cars_enetered_till_now) {
        this.total_cars_entered_till_now = total_cars_enetered_till_now;
    }

    public double getTotal_amount_till_now() {
        return total_amount_till_now;
    }

    public void setTotal_amount_till_now(double total_amount_till_now) {
        this.total_amount_till_now = total_amount_till_now;
    }

    public String getLicenseplateno(){return licenseplateno;}

    public void setLicenseplateno(String licenseplateno){this.licenseplateno = licenseplateno;
    }
}
