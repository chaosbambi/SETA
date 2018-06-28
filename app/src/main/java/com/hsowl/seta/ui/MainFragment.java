package com.hsowl.seta.ui;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hsowl.seta.R;
import com.hsowl.seta.data.HouseData;
import com.hsowl.seta.logic.EnergySuggestion;
import com.hsowl.seta.logic.TrafficLightColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainFragment extends Fragment {

    //Storage storage;
    HouseData houseDate;
    EnergySuggestion energySuggestion;
    List<TrafficLightColor> trafficLightColorsList;
    String[] trafficLightsForecastIntervalls;
    Map<ImageView, String> trafficLightsForecastData;
    LinearLayout llTrafficLightsForecastView;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onPause() {
        super.onPause();

        //this.storage.storeHouseData();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        Intent menuIntent;

        switch (id)
        {
            case R.id.device_activity_menu_item:
                menuIntent = new Intent(getActivity(), DeviceActivity.class);
                startActivity(menuIntent);
                return true;
            case R.id.custom_settings_activity_menu_item:
                menuIntent = new Intent(getActivity(), CustomSettingsActivity.class);
                startActivity(menuIntent);
                return true;
            case R.id.about_activity_menu_item:
                menuIntent = new Intent(getActivity(), AboutActivity.class);
                startActivity(menuIntent);
                return true;
            default: break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        //houseDate = Storage.getHouseData();
        //energySuggestion = new EnergySuggestion(houseDate);
        //trafficLightColorsList = energySuggestion.getTrafficLightColors();

        trafficLightColorsList = new ArrayList<>();
        trafficLightColorsList.add(TrafficLightColor.Red);

        trafficLightsForecastIntervalls = buildIntervallsForTrafficLightForecast(1, "h");

        createCustomTrafficLightForecastView(rootView, trafficLightsForecastIntervalls);

        return rootView;
    }

    private String[] buildIntervallsForTrafficLightForecast(int intervall, String unit) {
        String [] intervallStrings = new String[23];

        for(String item : intervallStrings) {
            item = "+" + String.valueOf(intervall) + " min" + unit;
            intervall += intervall;
        }

        return intervallStrings;
    }

    private void createCustomTrafficLightForecastView(View rootView, String[] trafficLightsForecastIntervalls) {
        llTrafficLightsForecastView = (LinearLayout) rootView.findViewById(R.id.trafficLightsForecast);
        LayoutInflater inflaterCustomView = LayoutInflater.from(getActivity());
        for(int i=0; i<trafficLightColorsList.size(); i++){
            View view = inflaterCustomView.inflate(R.layout.traffic_light_item, llTrafficLightsForecastView, false);

            switch (trafficLightColorsList.get(i)) {
                case Green:
                    ((ImageView)view.findViewById(R.id.trafficLightImage)).setImageResource(R.drawable.traffic_light_green_v3);
                    ((TextView)view.findViewById(R.id.trafficLightTextView)).setText(trafficLightsForecastIntervalls[i]);
                    break;
                case Yellow:
                    ((ImageView)view.findViewById(R.id.trafficLightImage)).setImageResource(R.drawable.traffic_light_yellow_v3);
                    ((TextView)view.findViewById(R.id.trafficLightTextView)).setText(trafficLightsForecastIntervalls[i]);
                    break;
                case Red:
                    ((ImageView)view.findViewById(R.id.trafficLightImage)).setImageResource(R.drawable.traffic_light_red_v3);
                    ((TextView)view.findViewById(R.id.trafficLightTextView)).setText(trafficLightsForecastIntervalls[i]);
                    break;
            }

            llTrafficLightsForecastView.addView(view);
        }
    }

}
