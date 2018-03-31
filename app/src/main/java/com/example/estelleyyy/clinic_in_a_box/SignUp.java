package com.example.estelleyyy.clinic_in_a_box;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ArrayAdapter;

/**
 * Created by wenjiazhang on 2018-03-11.
 */

public class SignUp extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    Spinner Agesets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHelper = new DatabaseHelper(this);
        setContentView(R.layout.activity_signup);

        //set variables//
        Agesets = (Spinner) findViewById(R.id.spinner);
        String[] ages = new String[]{"Less than 12 months", "1", "2", "3", "4", "5"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, ages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Agesets.setAdapter(adapter);
    }

    public void onSignUpClick(View v){
        if (v.getId() == R.id.buttonSignUp){
            EditText firstName = (EditText) findViewById(R.id.firstName);
            EditText lastName = (EditText) findViewById(R.id.userID);
            Spinner age = (Spinner) findViewById(R.id.spinner);
            EditText pw = (EditText) findViewById(R.id.password);
            EditText pw2 = (EditText) findViewById(R.id.password2);

            String firstNameStr = firstName.getText().toString();
            String lastNameStr = lastName.getText().toString();
            String ageStr = age.getSelectedItem().toString();
            String pwStr = pw.getText().toString();
            String pw2Str = pw2.getText().toString();


            if ((firstNameStr.trim().length() == 0) ||
                    (lastNameStr.trim().length() == 0) ||
                    (ageStr.trim().length() == 0) ||
                    (pwStr.trim().length() == 0) ||
                    (pw2Str.trim().length() == 0) ){
                Toast password = Toast.makeText(SignUp.this, "Please fill in all fields", Toast.LENGTH_SHORT);
                password.show();
            }
            else if (!pwStr.equals(pw2Str))
            {
                //popup msg
                Toast password = Toast.makeText(SignUp.this, "Passwords do not match", Toast.LENGTH_SHORT);
                password.show();
            }
            else
            {
                //insert the patient data into the database
                Patient p = new Patient();
                p.setFirstName(firstNameStr);
                p.setLastName(lastNameStr);
                p.setAge(ageStr);
                p.setPassword(pwStr);

                boolean isInserted = databaseHelper.insertPatient(p);
                if (isInserted) {
                    Toast.makeText(SignUp.this, "Data Inserted", Toast.LENGTH_LONG).show();

                    AlertDialog.Builder a_builder = new AlertDialog.Builder(SignUp.this);
                    String signUpId = databaseHelper.searchID(firstNameStr);

                    // Set PatientID and FirstName globally
                    ((GlobalVariables) this.getApplication()).setPatientID(Integer.parseInt(signUpId));
                    ((GlobalVariables) this.getApplication()).setFirstName(firstNameStr);
                    ((GlobalVariables) this.getApplication()).setLastName(lastNameStr);

                    a_builder.setMessage("You have been signed up. \n Your Login Number is " + signUpId + ". \n Click OK to login in.")
                           // .setCancelable(false)
                            .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();
                                    Intent startNewActivity = new Intent(SignUp.this, Questionnaire.class);
                                    startActivity(startNewActivity);
                                }
                            });
                    AlertDialog alert = a_builder.create();
                    alert.setTitle("Confirmation");
                    alert.show();
                    alert.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(Color.parseColor("#ffffff"));
                    alert.getButton(AlertDialog.BUTTON_NEUTRAL).setBackgroundColor(Color.parseColor("#bf0913"));
                    int height = getResources().getDimensionPixelSize(R.dimen.dialog_height);
                    int width  = getResources().getDimensionPixelSize(R.dimen.dialog_width);
                    alert.getButton(AlertDialog.BUTTON_NEUTRAL).setWidth(width);
                    alert.getButton(AlertDialog.BUTTON_NEUTRAL).setHeight(height);

                }
                else
                    Toast.makeText(SignUp.this,"Data Not Inserted", Toast.LENGTH_LONG).show();


            }

        }

    }
}
