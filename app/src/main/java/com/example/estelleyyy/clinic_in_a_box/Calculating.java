package com.example.estelleyyy.clinic_in_a_box;


/**
 * Created by wendy on 2018-04-02.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Calculating extends AppCompatActivity{

     DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculating);

        databaseHelper = new DatabaseHelper(this);


        int[] Qresults;
        Qresults = ((GlobalVariables) this.getApplication()).getQresult();


        for (int i =0; i<5; i++) {
            System.out.println("Qresult is: " + Qresults[i]);
        }

        CurrentTestResult(Qresults);


    }

    // go to the last page (no tests needed)
    public void goToReport(View v) {
        Intent startNewActivity = new Intent(this, TabReportActivity.class);
        startActivity(startNewActivity);
    }



    // function to display (current) test result
    double CurrentTestResult(int[] Qresults) {

        double result;
        int age = ((GlobalVariables) this.getApplication()).getAge();
        //Creating function calling class object.
        DiagnosisAlgorithm FN = new DiagnosisAlgorithm(DiagnosisAlgorithm.context);

        //Calling function from another class.
        result = FN.RiskPercentAlg(Qresults, age);

        //set risk percentage to global variable
        ((GlobalVariables) this.getApplication()).setRisk(result);

        //push risk to database
        PushToDatabase(Qresults);

        return result;


        //return output;

    }


    // connect to database
    public void PushToDatabase(int[] Qresult) {
        databaseHelper = new DatabaseHelper(this);
        TestData t = new TestData();

        // push the questionnaire & test results to the database[]
        t.setQ1(Qresult[0]);
        t.setQ2(Qresult[1]);
        t.setQ3(Qresult[2]);
        t.setQ4(Qresult[3]);
        t.setQ5(Qresult[4]);

        // push age
        int age = ((GlobalVariables) this.getApplication()).getAge();
        t.setTestAge(age);

        // push test Pid
        int Pid = ((GlobalVariables) this.getApplication()).getPatientID();
        t.setPatientID(Pid);

        // push risk
        double risk = ((GlobalVariables) this.getApplication()).getRisk();
        t.setRiskPercentage(risk);
        System.out.println("看看risk："+risk);

        // push physical
        double[] P1 = ((GlobalVariables) this.getApplication()).getBloodPressure();
        double P2 = ((GlobalVariables) this.getApplication()).getOxygen();
        double P3 = ((GlobalVariables) this.getApplication()).getTemp();
        t.setP1_1(P1[0]);
        t.setP1_2(P1[1]);
        t.setP2(P2);
        t.setP3(P3);


        boolean isInserted = databaseHelper.insertTest(t);
        System.out.println("Check if data is inserted: " +isInserted);

    }


}
