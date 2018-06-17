package com.hsowl.seta.logic;

import android.util.Log;

import java.util.Calendar;
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

        //Preparations
        //Create an array with the incidence angles of the sun
        double[] incidenceAngles = new double[prognosis.length];
        sunPosition.calcIncidenceAngles(incidenceAngles);

        //Calculate the specific times for sunrise and sunset
        Date sunRiseTime = sunPosition.getSunRiseTime(day);
        Date sunSetTime = sunPosition.getSunSetTime(day);

        //Calculate the intensity of the suns radiation
        double radiationIntensity = sunPosition.calcRadiationIntensity(day);

        //Calculate the factor to multiply with the peak power
        double[] factor = new double[prognosis.length];
        for (int i = 0; i < factor.length; i++){
            factor[i] = Math.cos(Math.toRadians(incidenceAngles[i]));

            if (factor[i] < 0){
                factor[i] = 0;
            }

            int hourOffDay =  (int)(24 * ((double)i/factor.length));
            int minuteOffHour = (int) (24 * 60 * ((double)i/factor.length)) % 60;
            Calendar partOffTheDay = Calendar.getInstance();
            partOffTheDay.set(day.getYear(),day.getMonth(),day.getDate(),hourOffDay,minuteOffHour);

            if (sunRiseTime.getTime() > partOffTheDay.getTimeInMillis())
                factor[i] = 0;

            if (sunSetTime.getTime() < partOffTheDay.getTimeInMillis())
                factor[i] = 0;
        }

        try{
            for (int j = 0; j < prognosis.length; j++){
                //Calculate optimal outcome
                double optOutcome = pvGrossPower * radiationIntensity * factor [j];

                if (weatherFactor[j] > 100){
                    weatherFactor[j] = 100;
                }else if(weatherFactor[j] < 0){
                    weatherFactor[j] = 0;
                }

                //Outcome with usage of weather information
                double totalOutcome = optOutcome * ((11.0/1000.0 * Math.pow(weatherFactor[j], 2) - 41.0/20.0 * weatherFactor[j] + 200.0) /100.0);

                if (totalOutcome > pvPeakPower){
                    prognosis[j] = pvPeakPower;
                }else {
                    prognosis[j] = totalOutcome;
                }
            }
        }catch (Exception e){
            Log.getStackTraceString(e);
            throw e;
        }
    }

    private double calculateGrossPower(double pvPeakPower) {
        return pvPeakPower * 1367/1000;
    }
}