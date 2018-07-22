package com.hsowl.seta.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hsowl.seta.R;
import com.hsowl.seta.data.HouseData;
import com.hsowl.seta.data.OwmWeatherStation;
import com.hsowl.seta.data.SmartMeter;
import com.hsowl.seta.data.WeatherStation;

public class CustomSettingsFragment extends Fragment {
    private static final String TAG = "CustomSettingsFragment";

    // Variables
    Activity mainActivity;
    HouseData houseData;
    WeatherStation weatherStation;
    SmartMeter smartMeter;

    // Widgets
    EditText etAnnualPowerConsumption;
    EditText etPVPeakPower;
    EditText etIPAddress;
    EditText etZIPCode;
    EditText etAzimuth;
    EditText etSlope;
    Button btnApplyCustomSettings;
    Button btnAzimuthSlopeHelp;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(TAG, "onAttach: get activity");

        mainActivity = activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: starting");

        try{
            houseData = ((MainActivity)mainActivity).houseData;
            if (houseData == null){
                houseData = new HouseData();
            }
        }catch (Exception e){
            Log.e(TAG, e.getMessage());
            Toast.makeText(getActivity(), R.string.exception_no_houseData, Toast.LENGTH_SHORT).show();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_custom_settings, container, false);

        etAnnualPowerConsumption = (EditText)view.findViewById(R.id.etAnnualPowerConsumption);
        etPVPeakPower = (EditText)view.findViewById(R.id.etPVPeakPower);
        etIPAddress = (EditText)view.findViewById(R.id.etIPAddress);
        etZIPCode = (EditText)view.findViewById(R.id.etZIPCode);
        etAzimuth = (EditText)view.findViewById(R.id.etAzimuth);
        etSlope = (EditText)view.findViewById(R.id.etSlope);
        btnApplyCustomSettings = (Button)view.findViewById(R.id.btnApplyCustomSettings);
        btnAzimuthSlopeHelp = (Button)view.findViewById(R.id.btnAzimuthSlopeHelp);

        // Fill text fields with current values
        try {
            smartMeter = houseData.getSmartMeter();
        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }

        try{
            weatherStation = houseData.getWeatherStation();
        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }


        try {
            if (smartMeter == null)
            {
                etIPAddress.setText("");
            }else{
                etIPAddress.setText(smartMeter.getHost());
            }
        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }

        try{
            if (houseData.getAnnualPowerConsumption() == 0.0){
                etAnnualPowerConsumption.setText("");
            }else{
                etAnnualPowerConsumption.setText(String.valueOf(houseData.getAnnualPowerConsumption()));
            }
        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }

        try{
            if (weatherStation.getZip() == 0){
                etZIPCode.setText("");
            }else{
                etZIPCode.setText(String.valueOf(weatherStation.getZip()));
            }
        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }

        try{
            if (houseData.getPvPeakPower() == 0.0){
                etPVPeakPower.setText("");
            } else {
                etPVPeakPower.setText(String.valueOf(houseData.getPvPeakPower()));
            }
        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }

        try{
            if (houseData.getAzimuth() == 0.0){
                etAzimuth.setText("");
            }else{
                etAzimuth.setText(String.valueOf(houseData.getAzimuth()));
            }
        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }

        try{
            if(houseData.getSlope() == 0.0){
                etSlope.setText("");
            }else{
                etSlope.setText(String.valueOf(houseData.getSlope()));
            }
        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }

        btnAzimuthSlopeHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertAzimuth = new AlertDialog.Builder(getActivity());
                alertAzimuth.setMessage(R.string.string_azimuth_slope_info)
                        .setPositiveButton("Verstanden", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setTitle("Azimut & Neigungswinkel")
                        .create();
                alertAzimuth.show();
            }
        });

        btnApplyCustomSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                smartMeter = new SmartMeter(etIPAddress.getText().toString());
                houseData.setSmartMeter(smartMeter);

                // Prove and set annual power consumption
                if(etAnnualPowerConsumption.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "Bitte geben Sie einen Wert ein!", Toast.LENGTH_SHORT).show();
                    etAnnualPowerConsumption.setHintTextColor(Color.RED);
                    return;
                }
                else{
                    etAnnualPowerConsumption.setHintTextColor(Color.WHITE);
                }

                houseData.setAnnualPowerConsumption(Double.valueOf(etAnnualPowerConsumption.getText().toString()));


                weatherStation = new OwmWeatherStation();

                // Prove and set zip code
                if (etZIPCode.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Bitte geben Sie eine Postleitzahl ein!", Toast.LENGTH_SHORT).show();
                    etZIPCode.setHintTextColor(Color.RED);
                    return;
                } else{
                    etZIPCode.setHintTextColor(Color.WHITE);
                }

                boolean correctZip;

                correctZip = weatherStation.setZip(Integer.valueOf(etZIPCode.getText().toString()));

                if (correctZip == false || etZIPCode.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Bitte richtige Postleitzahl eingeben!", Toast.LENGTH_SHORT).show();
                    etZIPCode.setTextColor(Color.RED);
                    return;
                } else{
                    etZIPCode.setTextColor(Color.WHITE);
                }

                weatherStation.updateWeatherData();
                weatherStation.updateCoordinates();

                // Prove and set pv peak power
                if(etPVPeakPower.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "Bitte geben Sie einen Wert ein!", Toast.LENGTH_SHORT).show();
                    etPVPeakPower.setHintTextColor(Color.RED);
                    return;
                }
                else{
                    etPVPeakPower.setHintTextColor(Color.WHITE);
                }

                // Prove and set azimuth
                if(etAzimuth.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "Bitte geben Sie einen Wert ein!", Toast.LENGTH_SHORT).show();
                    etAzimuth.setHintTextColor(Color.RED);
                    return;
                }
                else{
                    etAzimuth.setHintTextColor(Color.WHITE);
                }

                // Prove and set slope
                if(etSlope.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "Bitte geben Sie einen Wert ein!", Toast.LENGTH_SHORT).show();
                    etSlope.setHintTextColor(Color.RED);
                    return;
                }
                else{
                    etSlope.setHintTextColor(Color.WHITE);
                }

                weatherStation.createPvPrognosis(Double.valueOf(etPVPeakPower.getText().toString()), Double.valueOf(etAzimuth.getText().toString()), Double.valueOf(etSlope.getText().toString()));
                houseData.setPvPeakPower(Double.valueOf(etPVPeakPower.getText().toString()));
                houseData.setAzimuth(Double.valueOf(etAzimuth.getText().toString()));
                houseData.setSlope(Double.valueOf(etSlope.getText().toString()));

                houseData.setWeatherStation(weatherStation);

                try{
                    ((OnHouseDataSaveListener)mainActivity).save(houseData);
                    Toast.makeText(getActivity(), R.string.data_saved, Toast.LENGTH_SHORT).show();
                }catch (ClassCastException e){
                    Toast.makeText(getActivity(), R.string.exception_class_cast, Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}
