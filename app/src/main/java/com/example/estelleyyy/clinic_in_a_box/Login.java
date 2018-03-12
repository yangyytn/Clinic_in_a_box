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
    DatabaseHelper myDatatbase;
    EditText editName;
    Button btnAdd;
    Button btnViewAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        myDatatbase = new DatabaseHelper(this);
        editName = (EditText) findViewById(R.id.lastName);
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
                    boolean isInserted = myDatatbase.insertPatient(editName.getText().toString());
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
                    Cursor result = myDatatbase.getAllPatients();
                    if (result.getCount() == 0){
                        showMessage("Error", "No Data in Database");
                        return;
                    }
                    StringBuffer buffer = new StringBuffer();
                    while (result.moveToNext()) {
                        buffer.append("Id :" + result.getString(0) + "\n");
                        buffer.append("Name :" + result.getString(1) + "\n");
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
        Intent startNewActivity = new Intent(this, Questionnaire.class);
        startActivity(startNewActivity);
    }

    public void goToSignUp(View v){
        Intent startNewActivity = new Intent(this, SignUp.class);
        startActivity(startNewActivity);
    }
}
