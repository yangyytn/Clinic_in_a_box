package com.example.estelleyyy.clinic_in_a_box;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by wenjiazhang on 2018-03-11.
 */

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    public void onSignUpClick(View v){
        if (v.getId() == R.id.buttonSignUp){
            EditText firstName = (EditText) findViewById(R.id.firstName);
            EditText lastName = (EditText) findViewById(R.id.lastName);
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
        }

    }
}
