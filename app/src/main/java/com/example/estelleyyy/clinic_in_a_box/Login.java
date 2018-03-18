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
    EditText editName;
    Button btnAdd;
    Button btnViewAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        databaseHelper = new DatabaseHelper(this);
        editName = (EditText) findViewById(R.id.userID);
        btnAdd = (Button) findViewById(R.id.buttonAdd);
        btnViewAll = (Button) findViewById(R.id.buttonViewAll);
        AddPatient();
        viewAllPatients();
    }

    public void AddPatient(){
        btnAdd.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean isInserted = databaseHelper.insertPatient(editName.getText().toString());
                    if (isInserted)
                        Toast.makeText(Login.this,"Data Inserted", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(Login.this,"Data Not Inserted", Toast.LENGTH_LONG).show();
                }
            }
        );
    }

    public void viewAllPatients(){
        btnViewAll.setOnClickListener(
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

    public void goToNext(View v){
        EditText userId = (EditText)findViewById(R.id.userID);
        String userIdStr = userId.getText().toString();
        EditText password = (EditText)findViewById(R.id.password);
        String passwordStr = password.getText().toString();

        String passwordValid = databaseHelper.searchPassword(userIdStr);

        if (passwordStr.equals(passwordValid)){
            Intent startNewActivity = new Intent(this, Questionnaire.class);
            startActivity(startNewActivity);
        }
        else{
            Toast pw = Toast.makeText(Login.this, "Incorrect Password" + passwordValid, Toast.LENGTH_SHORT);
            pw.show();
        }

    }

    public void goToSignUp(View v){
        Intent startNewActivity = new Intent(this, SignUp.class);
        startActivity(startNewActivity);
    }
}
