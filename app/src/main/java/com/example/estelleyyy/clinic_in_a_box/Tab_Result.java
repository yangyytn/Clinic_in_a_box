package com.example.estelleyyy.clinic_in_a_box;

/**
 * Created by estelleyyy on 2018-03-11.
 */

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Tab_Result extends Fragment {

    DatabaseHelper databaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.result, container, false);

        // connect this to the XML!!!
        // use the below 2 lines for results display!!!!!!

        int[] Qresults;
        Qresults = ((GlobalVariables) this.getActivity().getApplication()).getQresult();


        for (int i =0; i<5; i++) {
            System.out.println("Qresult is: " + Qresults[i]);
        }

        double Result = CurrentTestResult(Qresults);

        String output1 = (int) Result + "%";
        String output2="";

        if (Result<25) {
            output2+="Low Risk";

        }
        else if (Result>70) {
            output2+="High Risk";
        }
        else {
            output2+="Moderate Risk";
        }

        //display the physical test measurements:
        String test1="";
        String test2="";
        String test3="";
        double[] BloodPressure = ((GlobalVariables) this.getActivity().getApplication()).getBloodPressure();
        double BloodOxy = ((GlobalVariables) this.getActivity().getApplication()).getOxygen();
        double temp = ((GlobalVariables) this.getActivity().getApplication()).getTemp();
        //output+="Below shows the physical test results for the current patient: .\n";
        test1+="Systolic Pressure: " + BloodPressure[0];
        test1+="\nDiastolic Pressure: " + BloodPressure[1];
        test2+=BloodOxy + "%";
        test3+=temp + "°C";

        TextView textView1 = rootView.findViewById(R.id.textView11);
        textView1.setText(output1);

        TextView textView2 = rootView.findViewById(R.id.textView12);
        textView2.setText(output2);

        if (Result<25) {
            textView1.setTextColor(0xff006600);     // green
            textView2.setTextColor(0xff006600);
        }
        else if (Result>70) {
            textView1.setTextColor(0xffcc0000);     // red
            textView2.setTextColor(0xffcc0000);
        }
        else {
            textView1.setTextColor(0xffe65c00);     //orange
            textView2.setTextColor(0xffe65c00);
        }



        TextView textView3 = rootView.findViewById(R.id.textView13);
        textView3.setText(test1);

        TextView textView4 = rootView.findViewById(R.id.textView14);
        textView4.setText(test2);

        TextView textView5 = rootView.findViewById(R.id.textView15);
        textView5.setText(test3);


        return rootView;
    }



    // function to display (current) test result
    double CurrentTestResult(int[] Qresults) {

        double result;
        int age = ((GlobalVariables) this.getActivity().getApplication()).getAge();
        //Creating function calling class object.
        DiagnosisAlgorithm FN = new DiagnosisAlgorithm(DiagnosisAlgorithm.context);

        //Calling function from another class.
        result = FN.RiskPercentAlg(Qresults, age);

        //set risk percentage to global variable
        ((GlobalVariables) this.getActivity().getApplication()).setRisk(result);

        //push risk to database
        PushToDatabase(Qresults);

        return result;


        //return output;

    }


    // connect to database
    public void PushToDatabase(int[] Qresult) {
        databaseHelper = new DatabaseHelper(this.getActivity());
        TestData t = new TestData();

        // push the questionnaire & test results to the database[]

        t.setQ1(Qresult[0]);
        t.setQ2(Qresult[1]);
        t.setQ3(Qresult[2]);
        t.setQ4(Qresult[3]);
        t.setQ5(Qresult[4]);

        // push age
        int age = ((GlobalVariables) this.getActivity().getApplication()).getAge();
        t.setTestAge(age);

        // push test Pid
        int Pid = ((GlobalVariables) this.getActivity().getApplication()).getPatientID();
        t.setPatientID(Pid);

        // push risk
        double risk = ((GlobalVariables) this.getActivity().getApplication()).getRisk();
        t.setRiskPercentage(risk);
        System.out.println("看看risk："+risk);

        // push physical
        double[] P1 = ((GlobalVariables) this.getActivity().getApplication()).getBloodPressure();
        double P2 = ((GlobalVariables) this.getActivity().getApplication()).getOxygen();
        double P3 = ((GlobalVariables) this.getActivity().getApplication()).getTemp();
        t.setP1_1(P1[0]);
        t.setP1_2(P1[1]);
        t.setP2(P2);
        t.setP3(P3);


        boolean isInserted = databaseHelper.insertTest(t);
        System.out.println("Check if data is inserted: " +isInserted);

    }



}
