package com.example.estelleyyy.clinic_in_a_box;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Diagnosis_BP extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis__bp);
    }

    public void goToNext(View v){
        Intent startNewActivity = new Intent(this, Diagnosis_BO.class);
        startActivity(startNewActivity);
    }

    
}
