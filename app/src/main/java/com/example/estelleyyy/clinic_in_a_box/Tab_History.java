package com.example.estelleyyy.clinic_in_a_box;

/**
 * Created by estelleyyy on 2018-03-11.
 */

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class Tab_History extends Fragment {
    DatabaseHelper databaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.history, container, false);
        databaseHelper = new DatabaseHelper(this.getActivity());
        viewHistoryTestData(rootView);
        return rootView;
    }

    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    public void viewHistoryTestData(View rootView){
        int pid = ((GlobalVariables) this.getActivity().getApplication()).getPatientID();

        Cursor result = databaseHelper.getTestData(pid);

        if (result.getCount() == 0){
            showMessage("Error", "No Test Data in Database");
            return;
        }
        StringBuffer buffer = new StringBuffer();

        StringBuffer P1_buffer = new StringBuffer();

        while (result.moveToNext()) {
            buffer.append("Test Id : " + result.getString(0) + "\n");
            buffer.append("Patient ID : " + result.getString(1) + "\n");
            buffer.append("Test Date : " + result.getString(2) + "\n");
            buffer.append("Test Age : " + result.getString(3) + "\n");
            buffer.append("P1 : " + result.getString(9) + "\n");
            buffer.append("P2 : " + result.getString(10) + "\n");
            buffer.append("P3 : " + result.getString(11) + "\n");
            buffer.append("Risk : " + result.getString(12) + "\n");

            P1_buffer.append("P1 : " + result.getString(9) + "\n");
        }

        String output = buffer.toString();

        String P1_output = P1_buffer.toString();

        TextView textView1 = rootView.findViewById(R.id.textView11);
        textView1.setText(output);

        TextView P1_textView = rootView.findViewById(R.id.textView17);
        P1_textView.setText(P1_output);
    }

}
