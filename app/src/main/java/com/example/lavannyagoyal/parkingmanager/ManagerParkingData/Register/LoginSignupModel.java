package com.example.lavannyagoyal.parkingmanager.ManagerParkingData.Register;

public class LoginSignupModel {

    String email;
    int uid;

    public int getId() {
        return uid;
    }

    public void setId(int uid) {
        this.uid = uid;
    }

    String username;
    int mobilenumber;

    public int getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(int mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String name) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
