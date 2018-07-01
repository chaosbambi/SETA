package com.hsowl.seta.data;

import com.hsowl.seta.ui.MainActivity;

import org.junit.Before;
import org.junit.Test;

public class StorageTest {

    Storage storage = new Storage(new MainActivity());

    @Before
    public void setUp() {

    }

    @Test
    public void storeHouseData() {
    }

    @Test
    public void readHouseData() {
    }

    public void codeForManuelTesting(){
        Storage s = new Storage(new MainActivity());
        s.readHouseData();

        HouseData h = s.getHouseData();

        if(h==null){
            h = new HouseData();
            h.setAnnualPowerConsumption(13.37);
            h.setSmartMeter(new SmartMeter());
        }

        s.setHouseData(h);
        s.storeHouseData();

        s.readHouseData();
        h = s.getHouseData();

        double test = h.getAnnualPowerConsumption();
    }
}