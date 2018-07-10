package com.hsowl.seta.ui;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hsowl.seta.R;
import com.hsowl.seta.data.HouseData;
import com.hsowl.seta.data.OwmWeatherStation;
import com.hsowl.seta.data.SmartMeter;
import com.hsowl.seta.data.Storage;
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

    Storage storage;
    HouseData houseData;
    WeatherStation weatherStation;
    SmartMeter smartMeter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = this.getActivity().getIntent();

        try{
            houseData = new Gson().fromJson(intent.getStringExtra("houseData"), HouseData.class);
        }catch (Exception e){
            Toast.makeText(getActivity(), R.string.exception_first_login, Toast.LENGTH_LONG).show();
        }

        if(houseData == null){
            houseData = new HouseData();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_custom_settings, container, false);

        etAnnualPowerConsumption = (EditText)rootView.findViewById(R.id.etAnnualPowerConsumption);
        etPVPeakPower = (EditText)rootView.findViewById(R.id.etPVPeakPower);
        etIPAddress = (EditText)rootView.findViewById(R.id.etIPAddress);
        etZIPCode = (EditText)rootView.findViewById(R.id.etZIPCode);
        etAzimuth = (EditText)rootView.findViewById(R.id.etAzimuth);
        etSlope = (EditText)rootView.findViewById(R.id.etSlope);
        btnApplyCustomSettings1 = (Button)rootView.findViewById(R.id.btnApplyCustomSettings1);
        btnApplyCustomSettings2 = (Button)rootView.findViewById(R.id.btnApplyCustomSettings2);

        smartMeter = houseData.getSmartMeter();
        weatherStation = houseData.getWeatherStation();

        try {
            etIPAddress.setText(smartMeter.getHost());
            etAnnualPowerConsumption.setText(String.valueOf(houseData.getAnnualPowerConsumption()));
            etZIPCode.setText(weatherStation.getZip());
            etPVPeakPower.setText(String.valueOf(houseData.getPvPeakPower()));
            etAzimuth.setText(String.valueOf(houseData.getAzimuth()));
            etSlope.setText(String.valueOf(houseData.getSlope()));
        }catch (Exception e){
            Toast.makeText(getActivity(), R.string.exception_no_houseData, Toast.LENGTH_SHORT).show();
        }

        btnApplyCustomSettings1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                smartMeter = new SmartMeter(etIPAddress.getText().toString());
                houseData.setSmartMeter(smartMeter);
            }
        });

        btnApplyCustomSettings2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                houseData.setAnnualPowerConsumption(Double.valueOf(etAnnualPowerConsumption.getText().toString()));
                weatherStation = new OwmWeatherStation();
                weatherStation.setZip(Integer.valueOf(etZIPCode.getText().toString()));
                weatherStation.updateWeatherData();
                weatherStation.updateCoordinates();
                weatherStation.createPvPrognosis(Double.valueOf(etPVPeakPower.getText().toString()), Double.valueOf(etAzimuth.getText().toString()), Double.valueOf(etSlope.getText().toString()));
                houseData.setAzimuth(Double.valueOf(etAzimuth.getText().toString()));
                houseData.setSlope(Double.valueOf(etSlope.getText().toString()));

                houseData.setWeatherStation(weatherStation);

                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("houseData", new Gson().toJson(houseData));
                startActivity(intent);
                getActivity().finish();

                Toast.makeText(getActivity(), R.string.data_saved, Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //this.storage.setHouseData(houseData);
        //this.storage.storeHouseData();
    }
}
