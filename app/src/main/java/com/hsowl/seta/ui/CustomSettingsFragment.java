package com.hsowl.seta.ui;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
    Button btnAzimuthSlopeHelp;

    Storage storage;
    HouseData houseData;
    WeatherStation weatherStation;
    SmartMeter smartMeter;
    GsonBuilder gsonBuilder;
    Gson gson;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);

        storage = new Storage(getActivity());

        gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(WeatherStation.class, new InterfaceAdapter<WeatherStation>());
        gson = gsonBuilder.create();

        Intent intent = this.getActivity().getIntent();

        try{
            houseData = gson.fromJson(intent.getStringExtra("houseData"), HouseData.class);
        }catch (Exception e){
            Toast.makeText(getActivity(), R.string.exception_first_login, Toast.LENGTH_SHORT).show();
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
        btnAzimuthSlopeHelp = (Button)rootView.findViewById(R.id.btnAzimuthSlopeHelp) ;

        smartMeter = houseData.getSmartMeter();
        weatherStation = houseData.getWeatherStation();

        try {
            if (smartMeter == null)
            {
                etIPAddress.setText("");
            }else{
                etIPAddress.setText(smartMeter.getHost());
            }
        }catch (Exception e){
            Log.d("Settings_Activity", e.getMessage().toString());
        }

        try{
            if (houseData.getAnnualPowerConsumption() == 0.0){
                etAnnualPowerConsumption.setText("");
            }else{
                etAnnualPowerConsumption.setText(String.valueOf(houseData.getAnnualPowerConsumption()));
            }
        }catch (Exception e){
            Log.d("Settings_Activity", e.getMessage().toString());
        }

        try{
            if (weatherStation.getZip() == 0){
                etZIPCode.setText("");
            }else{
                etZIPCode.setText(String.valueOf(weatherStation.getZip()));
            }
        }catch (Exception e){
            Log.d("Settings_Activity", e.getMessage().toString());
        }

        try{
            if (houseData.getPvPeakPower() == 0.0){
                etPVPeakPower.setText("");
            } else {
                etPVPeakPower.setText(String.valueOf(houseData.getPvPeakPower()));
            }
        }catch (Exception e){
            Log.d("Settings_Activity", e.getMessage().toString());
        }

        try{
            if (houseData.getAzimuth() == 0.0){
                etAzimuth.setText("");
            }else{
                etAzimuth.setText(String.valueOf(houseData.getAzimuth()));
            }
        }catch (Exception e){
            Log.d("Settings_Activity", e.getMessage().toString());
        }

        try{
            if(houseData.getSlope() == 0.0){
                etSlope.setText("");
            }else{
                etSlope.setText(String.valueOf(houseData.getSlope()));
            }
        }catch (Exception e){
            Log.d("Settings_Activity", e.getMessage().toString());
        }

        btnApplyCustomSettings1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                smartMeter = new SmartMeter(etIPAddress.getText().toString());
                houseData.setSmartMeter(smartMeter);
            }
        });

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

        btnApplyCustomSettings2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                houseData.setAnnualPowerConsumption(Double.valueOf(etAnnualPowerConsumption.getText().toString()));
                weatherStation = new OwmWeatherStation();

                boolean correctZip;

                correctZip = weatherStation.setZip(Integer.valueOf(etZIPCode.getText().toString()));

                if(correctZip == false){
                    Toast.makeText(getActivity(), "Bitte richtige Postleitzahl eingeben!", Toast.LENGTH_SHORT).show();
                    etZIPCode.setTextColor(Color.RED);
                    return;
                }

                weatherStation.updateWeatherData();
                weatherStation.updateCoordinates();
                weatherStation.createPvPrognosis(Double.valueOf(etPVPeakPower.getText().toString()), Double.valueOf(etAzimuth.getText().toString()), Double.valueOf(etSlope.getText().toString()));
                houseData.setPvPeakPower(Double.valueOf(etPVPeakPower.getText().toString()));
                houseData.setAzimuth(Double.valueOf(etAzimuth.getText().toString()));
                houseData.setSlope(Double.valueOf(etSlope.getText().toString()));

                houseData.setWeatherStation(weatherStation);

                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("houseData", gson.toJson(houseData));
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getActivity().finish();

                Toast.makeText(getActivity(), R.string.data_saved, Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                Toast.makeText(getActivity(),"Back button clicked", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("houseData", gson.toJson(houseData));
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getActivity().finish();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        super.onPause();

        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.putExtra("houseData", gson.toJson(houseData));
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        try{
            this.storage.setHouseData(houseData);
            this.storage.storeHouseData();
        }catch (Exception e){
            Toast.makeText(getActivity(), "Speichern fehlgeschlagen!", Toast.LENGTH_SHORT).show();
        }

    }
}
