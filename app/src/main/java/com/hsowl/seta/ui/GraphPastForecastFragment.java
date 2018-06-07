package com.hsowl.seta.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hsowl.seta.R;

public class GraphPastForecastFragment extends Fragment {

    // Variables
    TextView tvPeriod1;
    TextView tvPeriod2;
    TextView tvPeriod3;
    // TODO Graph 1-3 nachtragen

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_graph_past_forecast, container, false);

        return rootView;
    }

}
