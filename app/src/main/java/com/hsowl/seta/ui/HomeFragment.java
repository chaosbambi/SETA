package com.hsowl.seta.ui;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hsowl.seta.R;
import com.hsowl.seta.logic.EnergySuggestion;
import com.hsowl.seta.logic.TrafficLightColor;

import java.util.Map;

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";

    // Variables
    Activity mainActivity;
    EnergySuggestion energySuggestion;
    TrafficLightColor[] trafficLightColorsList;
    String[] trafficLightsForecastIntervalls;
    Map<ImageView, String> trafficLightsForecastData;

    // Widgets
    LinearLayout llTrafficLightsForecastView;
    ImageView ivCurrentTrafficLight;
    TextView tvRecommendation;
    SwipeRefreshLayout srlSwipeRefresh;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mainActivity = activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: Starting");

        energySuggestion = new EnergySuggestion(((MainActivity)mainActivity).houseData);
        try {
            trafficLightColorsList = energySuggestion.getTrafficLightColors(TrafficLightColor.Red);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());

        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // TODO just for testing, delete in further steps
//        trafficLightColorsList = new ArrayList<>();
//        trafficLightColorsList.add(TrafficLightColor.Red);

        // Current traffic light image view and recommendation text view
        ivCurrentTrafficLight = (ImageView) view.findViewById(R.id.ivCurrentTrafficLight);
        tvRecommendation = (TextView) view.findViewById(R.id.tvRecommendation);

        if (trafficLightColorsList == null)
        {
            ErrorFragment errorFragment = new ErrorFragment();

            FragmentTransaction transaction = mainActivity.getFragmentManager().beginTransaction();
            transaction.replace(R.id.container, errorFragment, "ErrorFragment");

            transaction.addToBackStack(null);
            transaction.commit();
        }else {

            switch (trafficLightColorsList[0]) {
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

            createCustomTrafficLightForecastView(view, trafficLightsForecastIntervalls);
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d(TAG, "onResume: starting");

        /*if (trafficLightColorsList == null)
        {
            ErrorFragment errorFragment = new ErrorFragment();

            FragmentTransaction transaction = mainActivity.getFragmentManager().beginTransaction();
            transaction.replace(R.id.main_content, errorFragment, "ErrorFragment");

            transaction.addToBackStack(null);
            transaction.commit();
        }*/
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
        for(int i=0; i<trafficLightColorsList.length; i++){
            View view = inflaterCustomView.inflate(R.layout.traffic_light_item, llTrafficLightsForecastView, false);

            switch (trafficLightColorsList[i]) {
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
