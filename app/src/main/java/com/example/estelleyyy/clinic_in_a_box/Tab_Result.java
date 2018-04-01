package com.example.estelleyyy.clinic_in_a_box;

/**
 * Created by estelleyyy on 2018-03-11.
 */

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Bundle;

public class Tab_Result extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.result, container, false);

        // connect this to the XML!!!
        // use the below 2 lines for results display!!!!!!

        int[] Qresults = new int[5];
        ((GlobalVariables) this.getActivity().getApplication()).getQresult(Qresults);


        for (int i =0; i<5; i++) {
            System.out.println("Qresult is: " + Qresults[i]);
        }

        double Result = CurrentTestResult(Qresults);

        String output1 =Result + "%";
        String output2="";

        if (Result<25) {
            output2+="Low Risk Percentage";
        }
        else if (Result>70) {
            output2+="High Risk Percentage";
        }
        else {
            output2+="Moderate Risk Percentage";
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

        TextView textView3 = rootView.findViewById(R.id.textView13);
        textView3.setText(test1);

        TextView textView4 = rootView.findViewById(R.id.textView14);
        textView4.setText(test2);

        TextView textView5 = rootView.findViewById(R.id.textView15);
        textView5.setText(test3);

        return rootView;
    }



    // function to display (current) test result
    //分开variables来return,,,还没做完
    double CurrentTestResult(int[] Qresults) {

        double result;
        int age = ((GlobalVariables) this.getActivity().getApplication()).getAge();
        //Creating function calling class object.
        DiagnosisAlgorithm FN = new DiagnosisAlgorithm(DiagnosisAlgorithm.context);

        //Calling function from another class.
        result = FN.RiskPercentAlg(Qresults, age);


        //set risk percentage to global variable
        ((GlobalVariables) this.getActivity().getApplication()).setRisk(result);

        return result;


        //return output; //todo: output1, output2, test1, test2, test3;  //need to change

    }






}
