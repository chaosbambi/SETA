package com.hsowl.seta.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.hsowl.seta.R;

public class CustomSettingsFragment extends Fragment {

    // Variables
    TextView tvYearlyPowerConsumption;
    EditText etPowerConsumptionInput;
    TextView tvPVPeak;
    EditText etPeakPower;
    TextView tvConnectionEM300;
    EditText etIPAddress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_custom_settings, container, false);

        return rootView;
    }

}
