package com.hsowl.seta.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.hsowl.seta.R;
import com.hsowl.seta.data.HouseData;
import com.hsowl.seta.data.OwmWeatherStation;
import com.hsowl.seta.data.SmartMeter;
import com.hsowl.seta.data.WeatherStation;

public class CustomSettingsFragment extends Fragment {

    // Variables
    EditText etAnnualPowerConsumption;
    EditText etPVPeakPower;
    EditText etIPAddress;
    EditText etZIPCode;
    EditText etAzimuth;
    EditText etSlope;
    Button btnApplyCustomSettings1;
    Button btnApplyCustomSettings2;

    HouseData houseData;
    WeatherStation weatherStation;
    SmartMeter smartMeter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_custom_settings, container, false);

        etAnnualPowerConsumption = rootView.findViewById(R.id.etAnnualPowerConsumption);
        etPVPeakPower = rootView.findViewById(R.id.etPVPeakPower);
        etIPAddress = rootView.findViewById(R.id.etIPAddress);
        etZIPCode = rootView.findViewById(R.id.etZIPCode);
        etAzimuth = rootView.findViewById(R.id.etAzimuth);
        etSlope = rootView.findViewById(R.id.etSlope);
        btnApplyCustomSettings1 = rootView.findViewById(R.id.btnApplyCustomSettings1);
        btnApplyCustomSettings2 = rootView.findViewById(R.id.btnApplyCustomSettings2);

        houseData = new HouseData();

        btnApplyCustomSettings1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                smartMeter = new SmartMeter(etIPAddress.getText().toString());
                houseData.setAnnualPowerConsumption(Double.valueOf(etAnnualPowerConsumption.getText().toString()));
                // TODO Funktion für PVPeakPower
                // TODO Funktion für addSmartMeter to HouseData
            }
        });

        btnApplyCustomSettings2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weatherStation = new OwmWeatherStation();
                weatherStation.setZip(Integer.valueOf(etZIPCode.getText().toString()));
                // TODO Funktion für setAzimuth
                // TODO Funktion für setSlope
                weatherStation.updateWeatherData();
                // TODO Funktion für updateCoordinates
                // TODO Funktion für addWeatherStation to HouseData
            }
        });

        return rootView;
    }

}
