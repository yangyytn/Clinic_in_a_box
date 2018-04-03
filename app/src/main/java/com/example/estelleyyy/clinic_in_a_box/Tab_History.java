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

    public static void reverse(double[] input) {
    // handling null, empty and one element array
        if(input == null || input.length <= 1)
        { return; }

        for (int i = 0; i < input.length / 2; i++) {
            double temp = input[i];
            // swap numbers
            input[i] = input[input.length - 1 -i];
            input[input.length - 1 - i] = temp;
        }
    }


    public void viewHistoryTestData(View rootView){

        // Get Test History from Database
        int pid = ((GlobalVariables) this.getActivity().getApplication()).getPatientID();
        Cursor result = databaseHelper.getTestData(pid);

        // If No Data in database, return
        if (result.getCount() == 0){
            showMessage("Error", "No Test Data in Database");
            return;
        }

        // Define Risk, Pressure, Oxygen and Temperature data
        double[] RiskData, PressureData_1, PressureData_2, OxygenData, TemperatureData;
        RiskData = new double[result.getCount()];
        PressureData_1 = new double[result.getCount()];
        PressureData_2 = new double[result.getCount()];
        OxygenData = new double[result.getCount()];
        TemperatureData = new double[result.getCount()];

        // Parse Queries to Datasets
        int i = 0 ;
        while (result.moveToNext()) {
            /*
            buffer.append("Test Id : " + result.getString(0) + "\n");
            buffer.append("Patient ID : " + result.getString(1) + "\n");
            buffer.append("Test Date : " + result.getString(2) + "\n");
            buffer.append("Test Age : " + result.getString(3) + "\n");
            buffer.append("P1_1 : " + result.getString(9) + "\n");
            buffer.append("P1_2 : " + result.getString(10) + "\n");
            buffer.append("P2 : " + result.getString(11) + "\n");
            buffer.append("P3 : " + result.getString(12) + "\n");
            buffer.append("Risk : " + result.getString(13) + "\n");
            */

            PressureData_1[i] = Double.parseDouble(result.getString(9));
            PressureData_2[i] = Double.parseDouble(result.getString(10));
            OxygenData[i] = Double.parseDouble(result.getString(11));
            TemperatureData[i] = Double.parseDouble(result.getString(12));
            RiskData[i] = Double.parseDouble(result.getString(13));

            i++;
        }

        // todo: delete testing data
        for (i = 0; i < result.getCount(); i++){
            PressureData_1[i] = i;
            PressureData_2[i] = i + 10;
            OxygenData[i] = i;
            TemperatureData[i] = i;
        }

        reverse(PressureData_1);
        reverse(PressureData_2);
        reverse(OxygenData);
        reverse(TemperatureData);
        reverse(RiskData);

        // Find Graph in UI
        GraphView graph_risk = (GraphView) rootView.findViewById(R.id.graph_risk);
        LineGraphSeries<DataPoint> series_risk = new LineGraphSeries<>(new DataPoint[] {});

        GraphView graph_pressure = (GraphView) rootView.findViewById(R.id.graph_pressure);
        LineGraphSeries<DataPoint> series_pressure_1 = new LineGraphSeries<>(new DataPoint[] {});
        LineGraphSeries<DataPoint> series_pressure_2 = new LineGraphSeries<>(new DataPoint[] {});

        GraphView graph_oxygen = (GraphView) rootView.findViewById(R.id.graph_oxygen);
        LineGraphSeries<DataPoint> series_oxygen = new LineGraphSeries<>(new DataPoint[] {});

        GraphView graph_temperature = (GraphView) rootView.findViewById(R.id.graph_temperature);
        LineGraphSeries<DataPoint> series_temperature = new LineGraphSeries<>(new DataPoint[] {});

        // Append data to series
        for (i = 0; i < 5 - result.getCount(); i++){
            series_risk.appendData(new DataPoint(i + 1, 0), true, 5);
            series_pressure_1.appendData(new DataPoint(i + 1, 0), true, 5);
            series_pressure_2.appendData(new DataPoint(i + 1, 0), true, 5);
            series_oxygen.appendData(new DataPoint(i + 1, 0), true, 5);
            series_temperature.appendData(new DataPoint(i + 1, 0), true, 5);
        }


        for (i = 5 - result.getCount(); i < 5; i++){
            int j = i - (5 - result.getCount());
            series_risk.appendData(new DataPoint(i + 1, RiskData[j]), true, 5);
            series_pressure_1.appendData(new DataPoint(i + 1, PressureData_1[j]), true, 5);
            series_pressure_2.appendData(new DataPoint(i + 1, PressureData_2[j]), true, 5);
            series_oxygen.appendData(new DataPoint(i + 1, OxygenData[j]), true, 5);
            series_temperature.appendData(new DataPoint(i + 1, TemperatureData[j]), true, 5);
        }


        // Draw Graph
        series_risk.setColor(Color.parseColor("#bf0913"));
        series_risk.setDrawDataPoints(true);
        series_risk.setDataPointsRadius(5);
        series_risk.setThickness(2);

        series_pressure_1.setColor(Color.parseColor("#bf0913"));
        series_pressure_1.setDrawDataPoints(true);
        series_pressure_1.setDataPointsRadius(3);
        series_pressure_1.setThickness(1);

        series_pressure_2.setColor(Color.parseColor("#bf0913"));
        series_pressure_2.setDrawDataPoints(true);
        series_pressure_2.setDataPointsRadius(5);
        series_pressure_2.setThickness(2);

        series_oxygen.setColor(Color.parseColor("#bf0913"));
        series_oxygen.setDrawDataPoints(true);
        series_oxygen.setDataPointsRadius(5);
        series_oxygen.setThickness(2);

        series_temperature.setColor(Color.parseColor("#bf0913"));
        series_temperature.setDrawDataPoints(true);
        series_temperature.setDataPointsRadius(5);
        series_temperature.setThickness(2);

        graph_risk.getGridLabelRenderer().setHorizontalLabelsColor(Color.parseColor("#bf0913"));
        graph_risk.getGridLabelRenderer().setVerticalLabelsColor(Color.parseColor("#bf0913"));
        graph_risk.getGridLabelRenderer().setVerticalAxisTitle("Risk Percentage");
        graph_risk.getGridLabelRenderer().setVerticalAxisTitleColor(Color.parseColor("#bf0913"));
        //graph_risk.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.HORIZONTAL);
        graph_risk.getGridLabelRenderer().setGridColor(Color.parseColor("#f1b2b5"));

        graph_pressure.getGridLabelRenderer().setHorizontalLabelsColor(Color.parseColor("#bf0913"));
        graph_pressure.getGridLabelRenderer().setVerticalLabelsColor(Color.parseColor("#bf0913"));
        graph_pressure.getGridLabelRenderer().setVerticalAxisTitle("Risk Percentage");
        graph_pressure.getGridLabelRenderer().setVerticalAxisTitleColor(Color.parseColor("#bf0913"));
        graph_pressure.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.HORIZONTAL);
        graph_pressure.getGridLabelRenderer().setGridColor(Color.parseColor("#f1b2b5"));


        // todo: change data series
        graph_risk.addSeries(series_risk);
        graph_pressure.addSeries(series_pressure_1);
        graph_pressure.addSeries(series_pressure_2);
        graph_oxygen.addSeries(series_oxygen);
        graph_temperature.addSeries(series_temperature);


    }


}
