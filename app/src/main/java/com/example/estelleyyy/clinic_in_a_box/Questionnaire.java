package com.example.estelleyyy.clinic_in_a_box;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class Questionnaire extends AppCompatActivity {

    private RadioGroup RadioGroup1, RadioGroup2, RadioGroup3, RadioGroup4, RadioGroup5;
    private RadioButton RadioButton1, RadioButton2, RadioButton3, RadioButton4, RadioButton5;
    private Button btnDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);

        addListenerOnButton();
    }


    // method to process all input data from the questionnaire
    public void addListenerOnButton() {

        RadioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
        RadioGroup2 = (RadioGroup) findViewById(R.id.radioGroup2);
        RadioGroup3 = (RadioGroup) findViewById(R.id.radioGroup3);
        RadioGroup4 = (RadioGroup) findViewById(R.id.radioGroup4);
        RadioGroup5 = (RadioGroup) findViewById(R.id.radioGroup5);

        btnDisplay = (Button) findViewById(R.id.Submit);
        // store questionnaire results
        final int[] Qresults;
        Qresults = new int[5];


        btnDisplay.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                // get selected radio button from radioGroup1
                int selectedId = RadioGroup1.getCheckedRadioButtonId();
                switch(selectedId){
                    case R.id.q1Yes:
                        // do operations specific to this selection
                        Qresults[0] = 1;
                        break;

                    case R.id.q1No:
                        // do operations specific to this selection
                        Qresults[0] = 0;
                        break;

                    default:
                        //error: should not happen
                        System.out.println("Error in selection.");
                        break;
                }


                // get selected radio button from radioGroup2
                int selectedId2 = RadioGroup2.getCheckedRadioButtonId();
                switch(selectedId2){
                    case R.id.q2Yes:
                        // do operations specific to this selection
                        Qresults[1] = 1;
                        break;

                    case R.id.q2No:
                        // do operations specific to this selection
                        Qresults[1] = 0;
                        break;

                    default:
                        //error: should not happen
                        System.out.println("Error in selection.");
                        break;
                }

                // get selected radio button from radioGroup3
                selectedId = RadioGroup3.getCheckedRadioButtonId();
                switch(selectedId){
                    case R.id.q3Yes:
                        // do operations specific to this selection
                        Qresults[2] = 1;
                        break;

                    case R.id.q3No:
                        // do operations specific to this selection
                        Qresults[2] = 0;
                        break;

                    default:
                        //error: should not happen
                        System.out.println("Error in selection.");
                        break;
                }


                // get selected radio button from radioGroup4
                selectedId = RadioGroup4.getCheckedRadioButtonId();
                switch(selectedId){
                    case R.id.q4Yes:
                        // do operations specific to this selection
                        Qresults[3] = 1;
                        break;

                    case R.id.q4No:
                        // do operations specific to this selection
                        Qresults[3] = 0;
                        break;

                    default:
                        //error: should not happen
                        System.out.println("Error in selection.");
                        break;
                }


                // get selected radio button from radioGroup5
                selectedId = RadioGroup5.getCheckedRadioButtonId();
                switch(selectedId){
                    case R.id.q5yes1:
                        // do operations specific to this selection
                        Qresults[4] = 1;
                        break;

                    case R.id.q5yes2:
                        // do operations specific to this selection
                        Qresults[4] = 2;
                        break;

                    case R.id.q5No:
                        // do operations specific to this selection
                        Qresults[4] = 0;
                        break;

                    default:
                        //error: should not happen
                        System.out.println("Error in selection.");
                        break;
                }


                System.out.println("print out the answer array: ");
                for (int i = 0; i<5; i++) {
                    System.out.println(Qresults[i]);
                }

                // store the answers
                PushToDatabase(Qresults);

                // when "Submit" clicked, last step -> switch to the next page
                goToNext(v);


            }

        });

    }


    public void goToNext(View v){
        Intent startNewActivity = new Intent(this, Diagnosis_BP.class);
        startActivity(startNewActivity);
    }


    public void PushToDatabase(int[] Qresult) {

        // first analyze the results, see what tests are needed:


        // get test results

        // push the questionnaire & test results to the database[]


    }

}
