package com.example.estelleyyy.clinic_in_a_box;

import android.app.Application;

/**
 * Created by wenjiazhang on 2018-03-18.
 */

public class GlobalVariables extends Application {
    private int patientID;
    private String firstName;
    private float temp;
    private float spo2;
    private float bp_sys;
    private float bp_dia;


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

    public void temperature(float temp) {this.temp = temp;}
    public void BP_SYS(float bp_sys) {this.bp_sys = bp_sys;}
    public void BP_DIA(float bp_dia) {this.bp_dia = bp_dia;}
    public void SPO2(float spo2) {this.temp = spo2;}
}
