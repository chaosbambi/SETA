package com.hsowl.seta.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hsowl.seta.R;
import com.hsowl.seta.data.SmartMeter;
import com.hsowl.seta.data.SmartMeterData;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SmartMeter sm = new SmartMeter("sem100");
        SmartMeterData smd = null;
        if(sm.checkAuthentication()){
            smd = sm.requestData();
        }

        smd.getStatus();
    }
}
