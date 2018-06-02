package com.hsowl.seta.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hsowl.seta.R;
import com.hsowl.seta.data.WeatherStation;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WeatherStation ws = new WeatherStation(52.016859, 8.904493);
        ws.getWeatherData();

    }
}
