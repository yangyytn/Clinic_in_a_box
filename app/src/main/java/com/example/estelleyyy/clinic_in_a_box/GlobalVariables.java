package com.example.estelleyyy.clinic_in_a_box;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenjiazhang on 2018-03-18.
 */

public class GlobalVariables extends Application {
    private int patientID;


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
    //two values for BP: systalic, dia...
    private double[] BloodPressure = new double[2];
    private double oxygen;
    private float armpit;

    public double[] getBloodPressure() {
        return BloodPressure;
    }
    public void setBloodPressure(double[] bp) {
        this.BloodPressure = bp;
    }

    public double getOxygen() { return oxygen; }
    public void setOxygen(double oxygen) {
        this.oxygen = oxygen;
    }

    public float getTemp() { return armpit; }
    public void setTemp(float temp) {
        this.armpit = temp;
    }


    // risk value
    private double risk;
    public double getRisk() { return risk; }
    public void setRisk(double risk) {
        this.risk = risk;
    }

}
