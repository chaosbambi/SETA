package com.hsowl.seta.ui;

import com.hsowl.seta.data.SmartMeter;
import com.hsowl.seta.data.SmartMeterData;

public class SmartMeterUnitTest {
    public void codeForManuelTestInMainActivity(){
        SmartMeter sm = new SmartMeter("sem100");
        SmartMeterData smd = null;
        if(sm.checkAuthentication()){
            smd = sm.requestData();
        }

        if(smd != null){
            int s = smd.getStatus();
        }
    }
}
