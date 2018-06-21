package com.hsowl.seta.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hsowl.seta.R;

public class MainActivity extends Activity { //TODO ggf. andere Activites auch von Activity erben lassen

    // Variables
    ScrollView svTrafficLights;
    ImageView ivTrafficLight0; //TODO Adapter/Liste nutzen
    TextView tvHeader;
    TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }




}
