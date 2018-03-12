package com.example.estelleyyy.clinic_in_a_box;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }
    public void goToNext(View v){
        Intent startNewActivity = new Intent(this, Login.class);
        startActivity(startNewActivity);
    }

    public void gotoreport(View v){
        Intent startNewActivity = new Intent(this, TabReportActivity.class);
        startActivity(startNewActivity);
    }

}
