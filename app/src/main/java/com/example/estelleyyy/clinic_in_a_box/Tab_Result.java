package com.example.estelleyyy.clinic_in_a_box;

/**
 * Created by estelleyyy on 2018-03-11.
 */

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Tab_Result extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.result, container, false);
        return rootView;
    }


    // todo: connect this to the XML!!!
    // todo: use the below 2 lines for results display!!!!!!
    //int[] Qresults = ((GlobalVariables) this.getActivity().getApplication()).getQresult();
    //CurrentTestResult(Qresults);


}
