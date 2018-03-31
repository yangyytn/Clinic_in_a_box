package com.example.estelleyyy.clinic_in_a_box;

import android.app.Application;

/**
 * Created by wenjiazhang on 2018-03-18.
 */

public class GlobalVariables extends Application {
    private int patientID;

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }


    // questionnaire results
    private int[] Qresult = new int[5];
    private int age;

    public int[] getQresult() {
        return Qresult;
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

}
