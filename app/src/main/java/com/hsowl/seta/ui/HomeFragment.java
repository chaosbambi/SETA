package com.hsowl.seta.ui;

import android.app.Activity;
import android.os.AsyncTask;
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
import android.widget.Toast;

import com.hsowl.seta.R;
import com.hsowl.seta.data.NoWeatherStationException;
import com.hsowl.seta.data.SmartMeterAuthenticationException;
import com.hsowl.seta.data.SmartMeterDataRetrievalException;
import com.hsowl.seta.data.WeatherStationDataRetrievalException;
import com.hsowl.seta.logic.EnergySuggestion;
import com.hsowl.seta.logic.TrafficLightColor;

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";

    // Variables
    Activity mainActivity;
    TrafficLightColor[] trafficLightColorsList;
    String[] trafficLightsForecastIntervalls;

    // Widgets
    LinearLayout llTrafficLightsForecastView;
    ImageView ivCurrentTrafficLight;
    TextView tvRecommendation;
    SwipeRefreshLayout srlSwipeRefresh;
    View view;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mainActivity = activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: Starting");

        GetDataAsyncTask getDataAsyncTask = new GetDataAsyncTask();
        getDataAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: Starting");

        view = inflater.inflate(R.layout.fragment_home, container, false);

        // Current traffic light image view and recommendation text view
        ivCurrentTrafficLight = view.findViewById(R.id.ivCurrentTrafficLight);
        tvRecommendation = view.findViewById(R.id.tvRecommendation);

        // TODO check if needed
        if (trafficLightColorsList != null)
        {
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

        //Add Listener for swipe gesture
        srlSwipeRefresh = view.findViewById(R.id.srlMainFragment);
        srlSwipeRefresh.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.d(TAG, "onRefresh: Starting");

                        // This methods performs the actual data-refresh operation.
                        GetDataAsyncTask getDataAsyncTask = new GetDataAsyncTask();
                        getDataAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    }
                }
        );

        return view;
    }

    private void updateTrafficLightColors(){
        if (((MainActivity)mainActivity).energySuggestion == null){
            ((MainActivity)mainActivity).energySuggestion = new EnergySuggestion(((MainActivity)mainActivity).houseData);
        }

        try {
            trafficLightColorsList = ((MainActivity)mainActivity).energySuggestion.getTrafficLightColors(TrafficLightColor.Yellow);
        }catch(NoWeatherStationException nwse){
            Log.e(TAG, nwse.getMessage());
            Toast.makeText(getActivity(), "Leider ist keine Wetterstation.", Toast.LENGTH_SHORT).show();
        }catch(SmartMeterDataRetrievalException smde){
            Log.e(TAG, smde.getMessage());
            Toast.makeText(getActivity(), "Beim laden der Daten von Ihrem SmartMeter ist leider ein Fehler aufgetreten.", Toast.LENGTH_SHORT).show();
        }catch(SmartMeterAuthenticationException smae){
            Log.e(TAG, smae.getMessage());
            Toast.makeText(getActivity(), "Bei der Verbindung mit ihrem SmartMeter ist leider ein Fehler aufgetreten.", Toast.LENGTH_SHORT).show();
        }catch(WeatherStationDataRetrievalException wde){
            Log.e(TAG, wde.getMessage());
            Toast.makeText(getActivity(), "Bei der Abfrage der Wetterdaten ist leider ein Fehler aufgetreten.", Toast.LENGTH_SHORT).show();
        }catch (Exception e) {
            Log.e(TAG, e.getMessage());
            Toast.makeText(getActivity(), "Leider ist ein Fehler aufgetreten. Bitte Prüfen Sie ob alle Eingaben richtig sind.", Toast.LENGTH_SHORT).show();
            /*ErrorFragment errorFragment = new ErrorFragment();

            FragmentTransaction transaction = mainActivity.getFragmentManager().beginTransaction();
            transaction.replace(R.id.container, errorFragment, "ErrorFragment");

            transaction.addToBackStack(null);
            transaction.commit();*/

        }
    }

    private void updateTrafficLightViews(View view) {

        // TODO check if needed
        /*if (trafficLightColorsList == null)
        {
            ErrorFragment errorFragment = new ErrorFragment();

            FragmentTransaction transaction = mainActivity.getFragmentManager().beginTransaction();
            transaction.replace(R.id.container, errorFragment, "ErrorFragment");

            transaction.addToBackStack(null);
            transaction.commit();
        }else {*/

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
//        }

        view.invalidate();

        srlSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d(TAG, "onResume: starting");

        // TODO check if needed
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
     * @param interval  time between 2 forecast elements
     * @param unit      unit of the time given in <code>interval</code>
     * @return String[] array of timestempswith
     */
    private String[] buildIntervallsForTrafficLightForecast(int interval, String unit) {
        String [] intervalStrings = new String[23];
        int intervalTarget = interval;

        for(int i=0; i<intervalStrings.length; i++) {
            intervalStrings[i] = "+" + String.valueOf(intervalTarget) + unit;
            intervalTarget += interval;
        }

        return intervalStrings;
    }

    /**
     * This method creates a view object that displays a linear layout filled with the traffic lights of the forecast and the forecast interval time.
     * It is set into a HorizontalScrollView in the MainFragment
     * @param rootView                          the parent of ListView which is inflated
     * @param trafficLightsForecastIntervalls   string array with timestamps for the ListView elements
     */
    private void createCustomTrafficLightForecastView(View rootView, String[] trafficLightsForecastIntervalls) {
        llTrafficLightsForecastView = rootView.findViewById(R.id.trafficLightsForecast);
        llTrafficLightsForecastView.removeAllViews();
        LayoutInflater inflaterCustomView = LayoutInflater.from(getActivity());
        for(int i=0; i<trafficLightColorsList.length-1; i++){
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

    public class GetDataAsyncTask extends AsyncTask<String, Integer, String[]> {

        private final String TAG = GetDataAsyncTask.class.getSimpleName();

        @Override
        protected String[] doInBackground(String... strings) {
            Log.d(TAG, "doInBackground: Starting");

            if (((MainActivity) mainActivity).energySuggestion == null) {
                ((MainActivity) mainActivity).energySuggestion = new EnergySuggestion(((MainActivity) mainActivity).houseData);
            }

            try {
                trafficLightColorsList = ((MainActivity) mainActivity).energySuggestion.getTrafficLightColors(TrafficLightColor.Yellow);
            } catch (NoWeatherStationException nwse) {
                Log.e(TAG, nwse.getMessage());
                //Toast.makeText(getActivity(), "Leider ist keine Wetterstation.", Toast.LENGTH_SHORT).show();
            } catch (SmartMeterDataRetrievalException smde) {
                Log.e(TAG, smde.getMessage());
                //Toast.makeText(getActivity(), "Beim laden der Daten von Ihrem SmartMeter ist leider ein Fehler aufgetreten.", Toast.LENGTH_SHORT).show();
            } catch (SmartMeterAuthenticationException smae) {
                Log.e(TAG, smae.getMessage());
                //Toast.makeText(getActivity(), "Bei der Verbindung mit ihrem SmartMeter ist leider ein Fehler aufgetreten.", Toast.LENGTH_SHORT).show();
            } catch (WeatherStationDataRetrievalException wde) {
                Log.e(TAG, wde.getMessage());
                //Toast.makeText(getActivity(), "Bei der Abfrage der Wetterdaten ist leider ein Fehler aufgetreten.", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
                //Toast.makeText(getActivity(), "Leider ist ein Fehler aufgetreten. Bitte Prüfen Sie ob alle Eingaben richtig sind.", Toast.LENGTH_SHORT).show();
            /*ErrorFragment errorFragment = new ErrorFragment();

            FragmentTransaction transaction = mainActivity.getFragmentManager().beginTransaction();
            transaction.replace(R.id.container, errorFragment, "ErrorFragment");

            transaction.addToBackStack(null);
            transaction.commit();*/
            }
            return new String[0];
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

        }

        @Override
        protected void onPostExecute(String[] strings) {

            Log.d(TAG, "onPostExecute: Starting");
            // TODO check if needed
        /*if (trafficLightColorsList == null)
        {
            ErrorFragment errorFragment = new ErrorFragment();

            FragmentTransaction transaction = mainActivity.getFragmentManager().beginTransaction();
            transaction.replace(R.id.container, errorFragment, "ErrorFragment");

            transaction.addToBackStack(null);
            transaction.commit();
        }else {*/
            if (trafficLightColorsList != null) {
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
//        }

            view.invalidate();

            srlSwipeRefresh.setRefreshing(false);
        }
    }
}
