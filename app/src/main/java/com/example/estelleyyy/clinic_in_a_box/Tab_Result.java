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
        /*


        // globally
        String output = "hehe";
        TextView textView1;
        //in your OnCreate() method
        textView1 = getView().findViewById(R.id.textView10);
        textView1.setText(output);
*/
        int[] Qresults = new int[5];
        ((GlobalVariables) this.getActivity().getApplication()).getQresult(Qresults);


        for (int i =0; i<5; i++) {
            System.out.println("Qresult is: " + Qresults[i]);
        }

        String output = CurrentTestResult(Qresults);    //have problems

        TextView textView1 = rootView.findViewById(R.id.textView10);
        textView1.setText(output);

        return rootView;
    }



    // function to display (current) test result
    String CurrentTestResult(int[] Qresults) {

        double result;
        int age = ((GlobalVariables) this.getActivity().getApplication()).getAge();
        //Creating function calling class object.
        DiagnosisAlgorithm FN = new DiagnosisAlgorithm(DiagnosisAlgorithm.context);

        //Calling function from another class.
        result = FN.RiskPercentAlg(Qresults, age);


        //set risk percentage to global variable
        ((GlobalVariables) this.getActivity().getApplication()).setRisk(result);

        String output="The risk percentage is: " + result + "%. \n";
        if (result<25) {
            output+="The patient has a relatively low chance of getting Sepsis.\n";
        }
        else if (result>70) {
            output+="The patient has a relatively high chance of getting Sepsis.\n";
            output+="This is urgent. Please go to the local hospital as soon as possible.\n";
        }
        else {
            output+="Moderate risk of Sepsis.\n";
            output+="The patient is strongly recommended to take a thorough examination.\n";
        }

        //display the physical test measurements:
        double[] BloodPressure = ((GlobalVariables) this.getActivity().getApplication()).getBloodPressure();
        double BloodOxy = ((GlobalVariables) this.getActivity().getApplication()).getOxygen();
        double temp = ((GlobalVariables) this.getActivity().getApplication()).getTemp();
        output+="Below shows the physical test results for the current patient: .\n";
        output+="Systolic Pressure: " + BloodPressure[0];
        output+="\nDiastolic Pressure: " + BloodPressure[1];
        output+="\nBlood Oxygen: " + BloodOxy;
        output+="\nBody Temperature: " + temp;

        return output;

    }






}
