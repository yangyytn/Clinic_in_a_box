package com.example.estelleyyy.clinic_in_a_box;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.Adapter;

/**
 * Created by wenjiazhang on 2018-03-11.
 */

public class SignUp extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    Spinner Agesets = (Spinner) findViewById(R.id.spinner);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHelper = new DatabaseHelper(this);
        setContentView(R.layout.activity_signup);

        //set variables//

        String[] ages = new String[]{"<1", "1", "2", "3", "4", "5"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, ages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Agesets.setAdapter(adapter);
    }

    public void onSignUpClick(View v){
        if (v.getId() == R.id.buttonSignUp){
            EditText firstName = (EditText) findViewById(R.id.firstName);
            EditText lastName = (EditText) findViewById(R.id.userID);
            EditText age = (EditText) findViewById(R.id.age);
            EditText pw = (EditText) findViewById(R.id.password);
            EditText pw2 = (EditText) findViewById(R.id.password2);

            String firstNameStr = firstName.getText().toString();
            String lastNameStr = lastName.getText().toString();
            String ageStr = age.getText().toString();
            String pwStr = pw.getText().toString();
            String pw2Str = pw2.getText().toString();


            if (!pwStr.equals(pw2Str))
            {
                //popup msg
                Toast password = Toast.makeText(SignUp.this, "Passwords don't match", Toast.LENGTH_SHORT);
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

                    a_builder.setMessage("You have been signed up. \n Your Login Number is " + signUpId + ".")
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


                }
                else
                    Toast.makeText(SignUp.this,"Data Not Inserted", Toast.LENGTH_LONG).show();


            }

        }

    }
}
