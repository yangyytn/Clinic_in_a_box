package com.example.estelleyyy.clinic_in_a_box;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class Questionnaire extends AppCompatActivity {

    DatabaseHelper databaseHelper;

    private RadioGroup RadioGroup1, RadioGroup2, RadioGroup3, RadioGroup4, RadioGroup5;
    private RadioButton RadioButton1, RadioButton2, RadioButton3, RadioButton4, RadioButton5;
    private Button btnDisplay;
    boolean selectionAll = true;  //flag to check if user answers to all problem
    boolean doTest = true;

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
        Qresults = new int[6];



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

                    case R.id.q1Mid:
                        Qresults[0] = 2;
                        break;

                    default:
                        //error: should not happen
                        System.out.println("Error in selection.");
                        Qresults[0] = -1;
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
                        Qresults[1] = -1;
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
                        Qresults[2] = -1;
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
                        Qresults[3] = -1;
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
                        Qresults[4] = -1;
                        break;
                }


                EditText age = (EditText) findViewById(R.id.age);
                String ageStr = age.getText().toString();
                if(ageStr != null && !ageStr.isEmpty()) {
                    int ageInt = Integer.parseInt(ageStr);
                    if (ageInt<=5 && ageInt>=0) {
                        Qresults[5] = ageInt;
                    }
                    else {
                        System.out.println("Error in selection.");
                        Qresults[5] = -2;
                        Toast showUp = Toast.makeText(Questionnaire.this, "The remote clinic is only applicable for infants and children under 5 years of age.", Toast.LENGTH_SHORT);
                        showUp.show();
                    }
                }
                // empty input
                else {
                    Qresults[5] = -1;
                }



                System.out.println("print out the answer array: ");
                for (int i = 0; i<6; i++) {
                    System.out.println(Qresults[i]);
                }

                for (int i = 0; i<Qresults.length; i++) {
                    if (Qresults[i] == -1) {
                        selectionAll = false;
                        System.out.println("selection all flag: " + selectionAll);
                    }
                    if (Qresults[0] != -1 && Qresults[1] != -1 && Qresults[2] != -1 && Qresults[3] != -1 && Qresults[4] != -1 && Qresults[5] != -1) {
                        selectionAll = true;
                        System.out.println("selection all flag: " + selectionAll);
                    }
                }



                // only when all questions are answered, shall we proceed to the next step
                if ((selectionAll == true) && (Qresults[5] != -2)) {

                    // store the answers
                    PushToDatabase(Qresults);

                    // analysis of the results, determine what physical tests needed to perform
                   /* if (!AnalyzeQresults(Qresults)) {
                        // do not need tests...
                        Toast NoTest = Toast.makeText(Questionnaire.this, "The patient is healthy! No physical tests needed.", Toast.LENGTH_SHORT);
                        NoTest.show();
                        goToFinish(v);
                    } else {*/
                        // when "Submit" clicked, last step -> switch to the next page
                    AlgorithmTest(Qresults);
                    goToNext(v);
                    //}

                } else if (Qresults[5] != -2) {
                    Toast showUp = Toast.makeText(Questionnaire.this, "Please answer to all questions", Toast.LENGTH_SHORT);
                    showUp.show();
                }


            }

        });

    }

    // go to the next page
    public void goToNext(View v) {
        Intent startNewActivity = new Intent(this, Diagnosis_BP.class);
        startActivity(startNewActivity);
    }

    // go to the last page (no tests needed)
    public void goToFinish(View v) {
        Intent startNewActivity = new Intent(this, Finish.class);
        startActivity(startNewActivity);
    }

    public boolean AnalyzeQresults(int[] Qresults) {

        // if answer all NO: no need to do test
        // if all YES: do all tests
        int[] QuestionAnswers;
        QuestionAnswers = new int[5];
        for (int i = 0; i<QuestionAnswers.length; i++) {
            QuestionAnswers[i] = Qresults[i];
        }
        doTest = contains(QuestionAnswers, 1);
        System.out.println("The doTest flag is: " + doTest);

        // print to UI

        if (doTest)
            return true;
        else return false;

    }

    // function to check if a value is in an array
    public static boolean contains(final int[] array, final int v) {

        boolean result = false;

        for(int i : array){
            if(i == v){
                result = true;
                break;
            }
        }

        return result;
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

        //todo: push age to database
        // set age
        // t.setage(Qresult[5])

        boolean isInserted = databaseHelper.insertTest(t);
        if (isInserted) {
            Toast.makeText(Questionnaire.this, "Test Data Inserted", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(Questionnaire.this, "Test Data Not Inserted", Toast.LENGTH_LONG).show();
        }


    }

    // test: need to move to after physical tests
    void AlgorithmTest(int[] Qresult) {

        double result;
        //Creating function calling class object.
        DiagnosisAlgorithm FN = new DiagnosisAlgorithm(DiagnosisAlgorithm.context);

        //Calling function from another class .
        result = FN.RiskPercentAlg(Qresult);
        System.out.println("The risk percentage is: " + result + "%. ");
        if (result<30) {
            System.out.println("虽然测出来的数据显示你没病，但你其实可能很有病");
        }
        else if (result>70) {
            System.out.println("病入膏肓，无药可救");
        }
        else {
            System.out.println("Moderate risk of Sepsis. ");
            System.out.println("We strongly recommend the patient to take a thorough examination.");
        }


    }


}
