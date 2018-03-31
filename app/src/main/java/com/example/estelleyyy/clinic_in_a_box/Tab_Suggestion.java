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

            System.out.println("Please take good care of the patient to avoid aggravation, as a weakened immune system will likely get infected by Sepsis.");

            System.out.println("1. Get vaccinated: Thirty-five percent of sepsis cases in the CDC study stemmed from pneumonia. ");
            System.out.println("Annual flu shots can also prevent respiratory infections that can sometimes turn septic. ");

            System.out.println("2. Act fast: If the contact a healthcare provider if the infection is gotten worse.");

            System.out.println("3. Please make sure the patient does not have skin problems.");

            System.out.println("If needed, go to the local hospital for more detailed tests.");
        }

    }

}