package com.example.estelleyyy.clinic_in_a_box;

/**
 * Created by estelleyyy on 2018-03-11.
 */

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.graphics.Color;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.GridLabelRenderer;



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

        final double[] TemperatureData;
        TemperatureData = new double[result.getCount()];

        StringBuffer buffer = new StringBuffer();

        StringBuffer P1_buffer = new StringBuffer();

        int i = 0 ;
        while (result.moveToNext()) {
            buffer.append("Test Id : " + result.getString(0) + "\n");
            buffer.append("Patient ID : " + result.getString(1) + "\n");
            buffer.append("Test Date : " + result.getString(2) + "\n");
            buffer.append("Test Age : " + result.getString(3) + "\n");
            buffer.append("P1 : " + result.getString(9) + "\n");
            buffer.append("P2 : " + result.getString(10) + "\n");
            buffer.append("P3 : " + result.getString(11) + "\n");
            buffer.append("Risk : " + result.getString(12) + "\n");

            TemperatureData[i] = Double.parseDouble(result.getString(9));
            i++;
        }

        String output = buffer.toString();

        String P1_output = P1_buffer.toString();

        for (i = 0; i < TemperatureData.length; i++){
           TemperatureData[i]=i;
        }
        /*TextView textView1 = rootView.findViewById(R.id.textView11);
        textView1.setText(output);

        TextView P1_textView = rootView.findViewById(R.id.textView17);
        P1_textView.setText(P1_output);
*/
        GraphView graph = (GraphView) rootView.findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
        });

        for (i = 0; i < TemperatureData.length; i++){
            series.appendData(new DataPoint(i + 1, TemperatureData[i]), true, 5);
        }

        series.setColor(Color.parseColor("#bf0913"));
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(3);
        series.setThickness(2);
        series.setBackgroundColor(Color.parseColor("#ea8383"));

        GridLabelRenderer gridRender = graph.getGridLabelRenderer();
        gridRender.setGridColor(Color.parseColor("#ea8383"));

        graph.addSeries(series);
        graph.setTitle("Armpit Temperature"); //Blood oxygen saturation;  Blood pressure
        graph.setTitleColor(Color.parseColor("#bf0913"));

    }


}
