package com.example.estelleyyy.clinic_in_a_box;

/**
 * Created by estelleyyy on 2018-03-11.
 */

import android.graphics.Color;
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

        double Result = ((GlobalVariables) this.getActivity().getApplication()).getRisk();

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
        //textView1.setText(output1);
        tvreset(textView1, output1);

        TextView textView2 = rootView.findViewById(R.id.textView12);
        //textView2.setText(output2);
        tvreset(textView2, output2);

        if (Result<25) {
            //textView1.setTextColor("#336600");     // green
            //textView2.setTextColor("#336600");
            tvsetcolor (textView1, "#336600");
            tvsetcolor (textView2, "#336600");
        }
        else if (Result>70) {
            //textView1.setTextColor("#cc0000");     // red
            //textView2.setTextColor("#cc0000");
            tvsetcolor (textView1, "#cc0000");
            tvsetcolor (textView2, "#cc0000");
        }
        else {
            //textView1.setTextColor("#e65c00");     //orange
            //textView2.setTextColor("#e65c00");
            tvsetcolor (textView1, "#e65c00");
            tvsetcolor (textView2, "#e65c00");
        }



        TextView textView3 = rootView.findViewById(R.id.textView13);
        //textView3.setText(test1);
        tvreset(textView3, test1);

        TextView textView4 = rootView.findViewById(R.id.textView14);
        //textView4.setText(test2);
        tvreset(textView4, test2);

        TextView textView5 = rootView.findViewById(R.id.textView15);
        //textView5.setText(test3);
        tvreset(textView5, test3);

        String FirstName = ((GlobalVariables) this.getActivity().getApplication()).getFirstName();
        FirstName = FirstName.substring(0, 1).toUpperCase() + FirstName.substring(1);
        String LastName = ((GlobalVariables) this.getActivity().getApplication()).getLastName();
        LastName = LastName.substring(0, 1).toUpperCase() + LastName.substring(1);
        String Name = FirstName + " " + LastName;

        if (Name.length()>12) {
            if (FirstName.length()<12) {
                Name = FirstName;
            }
            else {
                Name.substring(0,11);
                System.out.println(Name);
            }
        }

        Name = "Name: " + Name;
        TextView textName = rootView.findViewById(R.id.textView19);
        //textName.setText(Name);
        tvreset(textName, Name);

        int Age = ((GlobalVariables) this.getActivity().getApplication()).getAge();
        TextView textAge = rootView.findViewById(R.id.textView20);

        String AgeStr = "Age: " + Age;
        //textAge.setText(AgeStr);
        tvreset(textAge, AgeStr);

        return rootView;
    }



    private void tvreset(TextView tv, CharSequence text) {
        final TextView ftv = tv;
        final CharSequence ftext = text;

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ftv.setText(ftext);
            }
        });
    }

    private void tvsetcolor(TextView tv, final String color) {
        final TextView ftv = tv;
        final String color1 = color;

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ftv.setTextColor(Color.parseColor(color1)); //"#bf0913"
            }
        });
    }


}
