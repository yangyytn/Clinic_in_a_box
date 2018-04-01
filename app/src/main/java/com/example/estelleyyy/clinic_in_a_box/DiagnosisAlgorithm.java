package com.example.estelleyyy.clinic_in_a_box;

/**
 * Created by wendy on 2018-03-11.
 */

import android.support.v7.app.AppCompatActivity;
import android.content.Context;




public class DiagnosisAlgorithm extends AppCompatActivity {

    static Context context;
    public DiagnosisAlgorithm(Context c) {
        context = c;
    }
    public static final int TEST_CASE = 6;

    // todo: connect and get test results from Arduino + MySignal
    public void GetTestValue_bloodPressure(double[] BloodPressure) {

        // BloodPressure[0] = Systolic Pressure
        // BloodPressure[1] = Diastolic Pressure

        int testcase = TEST_CASE;

        switch(testcase) {
            case 1:
                BloodPressure[0] = 90;
                BloodPressure[1] = 50;
                break;

            case 2:
                BloodPressure[0] = 90;
                BloodPressure[1] = 50;
                break;

            case 3:
                BloodPressure[0] = 35;
                BloodPressure[1] = 50;
                break;

            case 4:
                BloodPressure[0] = 80;
                BloodPressure[1] = 30;
                break;

            case 5:
                BloodPressure[0] = 80;
                BloodPressure[1] = 30;
                break;

            case 6:
                BloodPressure[0] = 80;
                BloodPressure[1] = 30;
                break;

            case 7:
                BloodPressure[0] = 80;
                BloodPressure[1] = 45;
                break;

            default:
                break;

        }


    }

    public double GetTestValue_bloodOxy() {

        double BloodOxy = 0;

        int testcase = TEST_CASE;

        switch(testcase) {
            case 1:
                BloodOxy = 0.95;
                break;

            case 2:
                BloodOxy = 0.91;
                break;

            case 3:
                BloodOxy = 0.91;
                break;

            case 4:
                BloodOxy = 0.945;
                break;

            case 5:
                BloodOxy = 0.945;
                break;

            case 6:
                BloodOxy = 0.915;
                break;

            case 7:
                BloodOxy = 0.91;
                break;

            default:
                break;
        }


        return BloodOxy;
    }

    public double GetTestValue_temp() {

        double Temperature = 0;
        int testcase = TEST_CASE;

        switch(testcase) {
            case 1:
                Temperature = 37.5;
                break;

            case 2:
                Temperature = 37.5;
                break;

            case 3:
                Temperature = 39;
                break;

            case 4:
                Temperature = 35.9;
                break;

            case 5:
                Temperature = 35.9;
                break;

            case 6:
                Temperature = 35.9;
                break;

            case 7:
                Temperature = 35.7;
                break;

            default:
                break;
        }


        return Temperature;
    }



    public double RiskPercentAlg(int[] Qresult, int age) {

        // based on questionnaire answers:
        int Social_response = Qresult[0];
        int cry = Qresult[1];
        int hands_feet = Qresult[2];
        int grunting = Qresult[3];
        int skin = Qresult[4];
                //((GlobalVariables) this.getApplication()).getAge();
        double risk = 0;

        int weight = 0;
        int high = 0;   // high risk

        // Q1:
        switch (Social_response) {
            case 0:     //yes
                weight = weight + 2;
                break;

            case 2:
                weight++;
                break;

            default:
                break;

        }

        // Q2:
        switch (cry) {
            case 1:     //yes
                weight++;
                break;

            default:
                break;
        }

        // Q3:
        switch (hands_feet) {
            case 1:     //yes
                weight++;
                break;

            default:
                break;
        }


        // Q4:
        switch (grunting) {
            case 1:     //yes
                weight++;
                break;

            default:
                break;
        }

        // Q5:
        switch (skin) {
            case 1:     //cyanosis
                weight = weight + 2;
                high++;
                break;

            case 2:     //blushed
                weight++;
                break;

            default:
                break;
        }


        //based on tests

        // todo: save these physical test results into global variables
        double[] BloodPressure = new double[2];
        GetTestValue_bloodPressure(BloodPressure);
        double oxygen = GetTestValue_bloodOxy();
        double temp = GetTestValue_temp();

        //If the child is less than 3 months and has a temperature > 38°C, or if the child is any age under 5 and has a temperature < 36°C [2]
        if ( ((temp > 38) && (age == 0)) || (temp < 36)) {
            weight = weight + 2;
        }

        //Blood oxygen saturation
        //less than 90% (for children less than 1 year old) [3] IMP
        //less than 92% (for children 1-5 year old) [3] IMP
        //Blood oxygen saturation less than 95% [2]
        if ( (oxygen<0.9) && (age<1) || (oxygen < 0.92) && (age>=1)) {
            weight = weight + 3;
            high++;
        }
        else if (oxygen < 0.95) {
            weight = weight+2;
        }

        //Blood pressure: (either or both parts) outside of the range [1]
        if (age<=1) {
            if ( (BloodPressure[0]<39) || (BloodPressure[0]>104) || (BloodPressure[1]>56) || (BloodPressure[1]<16) ) {
                weight++;
            }
        }
        else if ((age>1) && (age<=2)) {
            if ( (BloodPressure[0]<86) || (BloodPressure[0]>106) || (BloodPressure[1]>63) || (BloodPressure[1]<42) ) {
                weight++;
            }
        }
        else {
            if ( (BloodPressure[0]<89) || (BloodPressure[0]>112) || (BloodPressure[1]>72) || (BloodPressure[1]<46) ) {
                weight++;
            }
        }


        // todo: return high low moderate; different calculator
        // calculate the percentage
        if ( (high==2) || (weight > 8) ) {
            // high risk!
            System.out.println("weight is: " + weight);
            risk = weight * 7.5;
            if (risk>=100) {
                risk = 98;
            }
            return risk;
        }
        else if ( (high==0) && (weight<6)) {
            // low risk
            risk = weight * 6;
            return risk;
        }
        else {
            // moderate risk
            // calculate risk directly
            risk = weight * 7;
            return risk;
        }

    }


}
