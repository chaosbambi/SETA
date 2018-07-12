package com.hsowl.seta.ui;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hsowl.seta.R;
import com.hsowl.seta.data.HouseData;
import com.hsowl.seta.data.Storage;
import com.hsowl.seta.data.WeatherStation;
import com.hsowl.seta.logic.EnergySuggestion;
import com.hsowl.seta.logic.TrafficLightColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainFragment extends Fragment {

    LinearLayout llTrafficLightsForecastView;
    ImageView ivCurrentTrafficLight;
    TextView tvRecommendation;
    SwipeRefreshLayout srlSwipeRefresh;

    Storage storage;
    HouseData houseData;
    EnergySuggestion energySuggestion;
    List<TrafficLightColor> trafficLightColorsList;
    String[] trafficLightsForecastIntervalls;
    Map<ImageView, String> trafficLightsForecastData;
    GsonBuilder gsonBuilder;
    Gson gson;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        Intent data = this.getActivity().getIntent();
        gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(WeatherStation.class, new InterfaceAdapter<WeatherStation>());
        gson = gsonBuilder.create();

        try {
            houseData = gson.fromJson(data.getStringExtra("houseData"), HouseData.class);
        }catch (Exception e){
            Toast.makeText(getActivity(), "Error!", Toast.LENGTH_SHORT).show();
        }

        if(houseData == null || houseData.getAnnualPowerConsumption() == 0.0 || houseData.getWeatherStation().getZip() == 0 || houseData.getPvPeakPower() == 0.0 || houseData.getAzimuth() == 0.0 || houseData.getSlope() == 0.0){
            Toast.makeText(getActivity(), R.string.exception_first_login, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getActivity(), CustomSettingsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            getActivity().finish();
        }

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
                menuIntent.putExtra("houseData", gson.toJson(houseData));
                menuIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(menuIntent);
                getActivity().finish();
                return true;
            case R.id.custom_settings_activity_menu_item:
                menuIntent = new Intent(getActivity(), CustomSettingsActivity.class);
                menuIntent.putExtra("houseData", gson.toJson(houseData));
                menuIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(menuIntent);
                getActivity().finish();
                return true;
            case R.id.about_activity_menu_item:
                menuIntent = new Intent(getActivity(), AboutActivity.class);
                menuIntent.putExtra("houseData", gson.toJson(houseData));
                menuIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(menuIntent);
                getActivity().finish();
                return true;
            default: break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // TODO just for testing, delete in further steps
        trafficLightColorsList = new ArrayList<>();
        trafficLightColorsList.add(TrafficLightColor.Red);

        // Current traffic light image view and recommendation text view
        ivCurrentTrafficLight = (ImageView)rootView.findViewById(R.id.ivCurrentTrafficLight);
        tvRecommendation = (TextView)rootView.findViewById(R.id.tvRecommendation);
        switch (trafficLightColorsList.get(0)){
            case Green:
                ivCurrentTrafficLight.setImageResource(R.drawable.traffic_light_green_v3);
                tvRecommendation.setText(R.string.recommendationGreen);
                break;
            case Yellow:
                ivCurrentTrafficLight.setImageResource(R.drawable.traffic_light_yellow_v3);
                tvRecommendation.setText(R.string.recommendationYellow);
                break;
            case Red:
                ivCurrentTrafficLight.setImageResource(R.drawable.traffic_light_red_v3);
                tvRecommendation.setText(R.string.recommendationRed);
                break;
        }

        // Build Forecast-Scrollbar
        trafficLightsForecastIntervalls = buildIntervallsForTrafficLightForecast(1, "h");

        createCustomTrafficLightForecastView(rootView, trafficLightsForecastIntervalls);

        srlSwipeRefresh = (SwipeRefreshLayout)rootView.findViewById(R.id.srlMainFragment);
        srlSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getActivity(), "Refresh fragment!!!!!", Toast.LENGTH_LONG).show();
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        //energySuggestion = new EnergySuggestion(houseData);
        //trafficLightColorsList = energySuggestion.getTrafficLightColors();
    }

    @Override
    public void onPause() {
        super.onPause();

        //this.storage.setHouseData(houseData);
        //this.storage.storeHouseData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        try{
            this.storage.setHouseData(houseData);
            this.storage.storeHouseData();
        }catch (Exception e){
            Toast.makeText(getActivity(), "Speichern nicht möglich", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * This method builds an array of strings that contain the intervall time of the forecast and the unit that is used.
     * @param interval
     * @param unit
     * @return String[]
     */
    private String[] buildIntervallsForTrafficLightForecast(int interval, String unit) {
        String [] intervalStrings = new String[23];

        for(int i=0; i<intervalStrings.length; i++) {
            intervalStrings[i] = "+" + String.valueOf(interval) + unit;
            interval += interval;
        }

        return intervalStrings;
    }

    /**
     * This method creates a view object that displays a linear layout filled with the traffic lights of the forecast and the forecast interval time.
     * It is set into a HorizontalScrollView in the MainFragment
     * @param rootView
     * @param trafficLightsForecastIntervalls
     */
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
