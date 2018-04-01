package com.example.estelleyyy.clinic_in_a_box;

import android.app.Application;

/**
 * Created by wenjiazhang on 2018-03-18.
 */

public class GlobalVariables extends Application {
    private int patientID;
    private float tempc;
    private float spo2;
    private float bp_sys;
    private float bp_dia;

    private String firstName, lastName;

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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    // questionnaire results
    private int[] Qresult = new int[5];
    private int age;

    public void getQresult(int[] QResult) {
        QResult = Qresult;
    }
    public void setQresult(int[] Qresult) {
        this.Qresult = Qresult;
    }

    public int getAge() { return age; }
    public void setAge(int age) {
        this.age = age;
    }


    // physical tests values
    private double[] BloodPressure = new double[2];
    private double oxygen;
    private double temp;

    public double[] getBloodPressure() {
        return BloodPressure;
    }
    public void setBloodPressure(double[] patientID) {
        this.BloodPressure = patientID;
    }

    public double getOxygen() { return oxygen; }
    public void setOxygen(double oxygen) {
        this.oxygen = oxygen;
    }

    public double getTemp() { return temp; }
    public void setTemp(double temp) {
        this.temp = temp;
    }


    // risk value
    private double risk;
    public double getRisk() { return risk; }
    public void setRisk(double risk) {
        this.risk = risk;
    }


    public void temperature(float tempc) {this.tempc = tempc;}
    public void BP_SYS(float bp_sys) {this.bp_sys = bp_sys;}
    public void BP_DIA(float bp_dia) {this.bp_dia = bp_dia;}
    public void SPO2(float spo2) {this.temp = spo2;}
}
