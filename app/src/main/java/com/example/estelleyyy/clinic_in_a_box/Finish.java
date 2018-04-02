package com.example.estelleyyy.clinic_in_a_box;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Finish extends AppCompatActivity {

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        databaseHelper = new DatabaseHelper(this);
    }


    public void goToQuestionnaire(View v) {
        Intent startNewActivity = new Intent(this, Questionnaire.class);
        startActivity(startNewActivity);
    }

    public void goToNext(View v){
        // clear global variables

        //while pid still not 0: keep reset globals
        resetGlobals();

        Intent startNewActivity = new Intent(this, Welcome.class);
        startActivity(startNewActivity);
    }

    public boolean resetGlobals() {


        ((GlobalVariables) this.getApplication()).setPatientID(0);

        ((GlobalVariables) this.getApplication()).setFirstName("");

        ((GlobalVariables) this.getApplication()).setLastName("");

        int[] empty = new int[5];
        for (int i=0; i<5; i++) {
            empty[i]=0;
        }
        ((GlobalVariables) this.getApplication()).setQresult(empty);

        ((GlobalVariables) this.getApplication()).setAge(0);

        double zero = 0;
        double[] empty2 = new double[2];
        for (int i=0; i<2; i++) {
            empty[i]=0;
        }
        ((GlobalVariables) this.getApplication()).setBloodPressure(empty2);
        ((GlobalVariables) this.getApplication()).setOxygen(zero);
        ((GlobalVariables) this.getApplication()).setTemp(zero);

        ((GlobalVariables) this.getApplication()).setRisk(zero);

        if (checkReset()==true) {
            return true;
        }
        else return false;

    }

    public boolean checkReset() {
        int PID = ((GlobalVariables) this.getApplication()).getPatientID();

        String first = ((GlobalVariables) this.getApplication()).getFirstName();

        String last = ((GlobalVariables) this.getApplication()).getLastName();

        double oxy = ((GlobalVariables) this.getApplication()).getOxygen();

        double temp = ((GlobalVariables) this.getApplication()).getTemp();

        double risk = ((GlobalVariables) this.getApplication()).getRisk();

        if (PID==0 && first.equals("") && last.equals("") && temp==0 && oxy==0 && risk==0) {
            return true;
        }
        else return false;
    }
}
