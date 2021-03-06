package com.example.estelleyyy.clinic_in_a_box;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenjiazhang on 2018-03-18.
 */

public class GlobalVariables extends Application {
    private int patientID = 0;
    private String firstName = "";
    private String lastName = "";

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
    private int[] Qresult = new int[] {0, 0, 0, 0, 0};
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
    private double[] BloodPressure = new double[] {0.0, 0.0};
    private double oxygen = 98;
    private double armpit = 35.1;

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

    public double getTemp() { return armpit; }
    public void setTemp(double temp) {
        this.armpit = temp;
    }


    // risk value
    private double risk;
    public double getRisk() { return risk; }
    public void setRisk(double risk) {
        this.risk = risk;
    }

}
