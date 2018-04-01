package com.example.estelleyyy.clinic_in_a_box;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.database.Cursor;

public class Login extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    EditText userId;
    EditText password;
    Button btnViewTest;
    Button btnViewPatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        databaseHelper = new DatabaseHelper(this);

        userId = (EditText)findViewById(R.id.userID);
        password = (EditText)findViewById(R.id.password);
        btnViewTest = (Button) findViewById(R.id.buttonTest);
        btnViewPatient = (Button) findViewById(R.id.buttonPatient);

        viewAllTestData();
        viewAllPatients();
    }

    public void viewAllTestData(){

        btnViewTest.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Cursor result = databaseHelper.getAllTestData();

                        if (result.getCount() == 0){
                            showMessage("Error", "No Test Data in Database");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();

                        while (result.moveToNext()) {
                            buffer.append("Test Id :" + result.getString(0) + "\n");
                            buffer.append("Patient ID :" + result.getString(1) + "\n");
                            buffer.append("Test Date :" + result.getString(2) + "\n");
                            buffer.append("Test Age :" + result.getString(3) + "\n");
                            buffer.append("Q1 :" + result.getString(4) + "\n");
                            buffer.append("Q2 :" + result.getString(5) + "\n");
                            buffer.append("Q3 :" + result.getString(6) + "\n");
                            buffer.append("Q4 :" + result.getString(7) + "\n");
                            buffer.append("Q5 :" + result.getString(8) + "\n");
                            buffer.append("P1 :" + result.getString(9) + "\n");
                            buffer.append("P2 :" + result.getString(10) + "\n");
                            buffer.append("P3 :" + result.getString(11) + "\n");
                            buffer.append("Risk :" + result.getString(12) + "\n");

                        }

                        //Show Data in Database
                        showMessage("Test Data", buffer.toString());
                    }
                }
        );
    }

    public void viewAllPatients(){
        btnViewPatient.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Cursor result = databaseHelper.getAllPatients();
                    if (result.getCount() == 0){
                        showMessage("Error", "No Data in Database");
                        return;
                    }
                    StringBuffer buffer = new StringBuffer();

                    while (result.moveToNext()) {
                        buffer.append("Id :" + result.getString(0) + "\n");
                        buffer.append("First Name :" + result.getString(1) + "\n");
                        buffer.append("Last Name :" + result.getString(2) + "\n");
                        buffer.append("Age :" + result.getString(3) + "\n");
                        buffer.append("Password :" + result.getString(4) + "\n");
                    }

                    //Show Data in Database
                    showMessage("Patients", buffer.toString());
                }
            }
        );
    }

    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    static boolean isInt(String s)
    {
        try
        { int i = Integer.parseInt(s); return true; }

        catch(NumberFormatException er)
        { return false; }
    }

    public void goToNext(View v){
        String userIdStr = userId.getText().toString();
        String passwordStr = password.getText().toString();

        if ((userIdStr.trim().length() == 0) ||
                (passwordStr.trim().length() == 0)){
            Toast password = Toast.makeText(Login.this, "Please fill in all fields", Toast.LENGTH_SHORT);
            password.show();
        }
        else if (!isInt(userIdStr)){
            Toast notInt = Toast.makeText(Login.this, "UserID should be numbers", Toast.LENGTH_SHORT);
            notInt.show();
        }
        else {
            String passwordValid = databaseHelper.searchPassword(Integer.parseInt(userIdStr));

            if (passwordStr.equals(passwordValid)){
                // Set PatientID globally
                ((GlobalVariables) this.getApplication()).setPatientID(Integer.parseInt(userIdStr));

                // Set First Name globally
                String firstName = databaseHelper.searchFirstName(Integer.parseInt(userIdStr));
                ((GlobalVariables) this.getApplication()).setFirstName(firstName);

                // Set Last Name globally
                String lastName = databaseHelper.searchLastName(Integer.parseInt(userIdStr));
                ((GlobalVariables) this.getApplication()).setLastName(lastName);

                // Turn To Next Page
                Intent startNewActivity = new Intent(this, Questionnaire.class);
                startActivity(startNewActivity);
            }
            else{
                Toast pw = Toast.makeText(Login.this, "Incorrect Password ", Toast.LENGTH_SHORT);
                pw.show();
            }
        }

    }

    public void goToSignUp(View v){
        Intent startNewActivity = new Intent(this, SignUp.class);
        startActivity(startNewActivity);
    }

    public void goToTemp(View v){
        Intent startNewActivity = new Intent(this, Diagnosis.class);
        startActivity(startNewActivity);
    }
}
