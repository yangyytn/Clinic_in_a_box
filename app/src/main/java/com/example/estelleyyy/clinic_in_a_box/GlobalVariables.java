package com.example.estelleyyy.clinic_in_a_box;

import android.app.Application;

/**
 * Created by wenjiazhang on 2018-03-18.
 */

public class GlobalVariables extends Application {
    private int patientID;
    private String firstName;

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
