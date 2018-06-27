package com.hsowl.seta.ui;

import com.hsowl.seta.logic.SunPosition;

import org.junit.Test;

import java.util.Date;

public class SunPositionTest {
    @Test
    public void calcRadiationIntensityTest(){
        SunPosition sunPosition = new SunPosition(52.026673,8.901862, 180,45);
        double[] intensities = new double[24];

        Date d = new Date();
        d.setDate(28);
        d.setMonth(4);
        d.setYear(118);
        d.setSeconds(0);
        d.setMinutes(0);
        d.setHours(0);

        for (int i = 0; i < 24; i++){
            intensities[i] = sunPosition.calcRadiationIntensity(d);
            int timeInSeconds = d.getSeconds() + 3600;
            d.setSeconds(timeInSeconds);
        }
        intensities.toString();
    }

    @Test
    public void calcAirmassTest(){
        SunPosition sunPosition = new SunPosition(52.026673,8.901862, 180,45);
        double[] airmass = new double[24];

        Date d = new Date();
        d.setDate(28);
        d.setMonth(4);
        d.setYear(118);
        d.setSeconds(0);
        d.setMinutes(0);
        d.setHours(0);

        for (int i = 0; i < 24; i++){
            airmass[i] = sunPosition.calcAirmass(d);
            int timeInSeconds = d.getSeconds() + 3600;
            d.setSeconds(timeInSeconds);
        }
        airmass.toString();
    }
}
