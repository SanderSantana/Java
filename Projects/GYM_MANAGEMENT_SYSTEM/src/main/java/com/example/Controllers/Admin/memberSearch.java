package com.example.Controllers.Admin;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class memberSearch {


    int id, phoneNumber, outstanding;
    String fName, lName, gender, email, dob, subscription, address;


    public memberSearch(String fName, String lName, String gender, String email, String dob, String subscription, String address, int id, int phoneNumber, int outstanding) {

        this.id = id;
        this.phoneNumber = phoneNumber;
        this.outstanding = outstanding;
        this.fName = fName;
        this.lName = lName;
        this.gender = gender;
        this.email = email;
        this.dob = dob;
        this.subscription = subscription;
        this.address = address;


    }

    public int getId() {
        return id;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public int getOutstanding() {
        return outstanding;
    }

    public String getFName() {
        return fName;
    }

    public String getLName() {
        return lName;
    }

    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getDob() {
        return dob;
    }

    public String getSubscription() {
        return subscription;
    }

    public String getAddress() {
        return address;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setOutstanding(int outstanding) {
        this.outstanding = outstanding;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

