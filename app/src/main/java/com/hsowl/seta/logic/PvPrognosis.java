package com.hsowl.seta.logic;

import android.util.Log;

import java.util.Date;

public class PvPrognosis {
    private SunPosition sunPosition;
    private double pvPeakPower;
    private double pvGrossPower;
    private double lat;
    private double lon;
    private double azimuth;
    private double slope;

    public PvPrognosis(double pvPeakPower, double lat, double lon, double azimuth, double slope){
        this.sunPosition = new SunPosition(lat, lon, azimuth, slope);
        this.pvPeakPower = pvPeakPower;
        this.pvGrossPower = calculateGrossPower(pvPeakPower);
        this.lat = lat;
        this.lon = lon;
        this.azimuth = azimuth;
        this.slope = slope;
    }

    /**
     * This Method fills the given prognosis Array with estimated values of the pv output depending
     * on the day of the year, coordinates, azimuth, slope an the weatherFactor
     * @param prognosis         Array to fill with prognosis data
     * @param weatherFactor     Factor between 0 and 100
     * @param day               Day of the Year for prognosis
     */
    public void calculatePvPrognosis(double[] prognosis, double[] weatherFactor, Date day){

        //Calculate the specific times for sunrise and sunset
        Date sunRiseTime = sunPosition.getSunRiseTime(day);
        Date sunSetTime = sunPosition.getSunSetTime(day);

        //Calculate the intensity of the suns radiation
        double radiationIntensity;

        double factor;
        Date partOffTheDay = new Date(day.getYear(),day.getMonth(),day.getDate(),day.getHours(),day.getMinutes(),day.getSeconds());
        int stepWidth = 60*60*24/prognosis.length;
        int timeInSeconds;
        for (int i = 0; i < prognosis.length; i++) {

            //Calculate the factor to multiply with the peak power
            factor = Math.cos(Math.toRadians(sunPosition.getIncideceAngle(partOffTheDay)));

            if (factor < 0) {
                factor = 0;
            }


            if (sunRiseTime.getTime() > partOffTheDay.getTime()) {
                factor = 0;
            }

            if (sunSetTime.getTime() < partOffTheDay.getTime()) {
                factor = 0;
            }

            radiationIntensity = sunPosition.calcRadiationIntensity(partOffTheDay);


            try {

                //Calculate optimal outcome
                double optOutcome = pvGrossPower * radiationIntensity * factor;

                if (weatherFactor[i] > 100) {
                    weatherFactor[i] = 100;
                } else if (weatherFactor[i] < 0) {
                    weatherFactor[i] = 0;
                }

                //Outcome with usage of weather information
                double totalOutcome = optOutcome * ((11.0 / 1000.0 * Math.pow(weatherFactor[i], 2) - 41.0 / 20.0 * weatherFactor[i] + 100.0) / 100.0);

                if (totalOutcome > pvPeakPower) {
                    prognosis[i] = pvPeakPower;
                } else {
                    prognosis[i] = totalOutcome;
                }

            } catch (Exception e) {
                Log.getStackTraceString(e);
                throw e;
            }

            timeInSeconds = partOffTheDay.getSeconds() + stepWidth;
            partOffTheDay.setSeconds(timeInSeconds);

            if(partOffTheDay.getDate() != sunRiseTime.getDate()){
                sunRiseTime = sunPosition.getSunRiseTime(partOffTheDay);
                sunSetTime = sunPosition.getSunSetTime(partOffTheDay);
            }
        }
    }

    private double calculateGrossPower(double pvPeakPower) {
        return pvPeakPower * 1367/1000;
    }
}