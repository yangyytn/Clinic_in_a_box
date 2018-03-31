package com.example.estelleyyy.clinic_in_a_box;

/**
 * Created by estelleyyy on 2018-03-11.
 */

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Tab_Suggestion extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.suggestion, container, false);
        return rootView;
    }


    // todo: connect this to the XML!!!
    // todo: use the below 2 lines to print suggestions!
    //double risk = ((GlobalVariables) this.getActivity().getApplication()).getTemp();
    //Suggestions(risk);


    // function to display suggestions
    void Suggestions(double risk) {

        if (risk < 25) {
            System.out.println("Currently, the patient has a low risk percentage of Sepsis: " + risk + "%. ");
            System.out.println("No further instructions needed to be taken.");
        }

        else if (risk > 70) {
            System.out.println("Currently, the patient has a relatively high risk percentage of Sepsis: " + risk + "%. ");
            System.out.println("The patient must go to the local hospital as soon as possible.");
            System.out.println("Here are some mandatory treatments he/she needs to take: ");
            System.out.println("1. Antibiotics – if the sepsis is detected early enough, this may be a course of tablets you can finish taking at home\n" +
                    "2. Fluids Intravenously\n" +
                    "3. Giving oxygen if levels are low");
            System.out.println("Please consult medical professionals for more information.");
        }


        else {
            System.out.println("Currently, the patient has a moderate risk percentage of Sepsis: " + risk + "%. ");
            System.out.println("If possible, the patient should go for a thorough examination.");

            System.out.println("There are 2 important factors that he/she needs to be aware of: ");

            System.out.println("1. Please make sure the blood oxygen is maintained at a normal level: ");
            System.out.println("Blood Oxygen > 92% for children older than 1 year old.");
            System.out.println("Blood Oxygen > 90% for infants less than 1 year old.");

            System.out.println("2. Please make sure the patient does not have skin problems.");
            System.out.println("If the patient is having aggravated symptoms with worse health, please go to the local hospital.");
        }

    }

}