package com.hsowl.seta.data;

import java.util.ArrayList;

public class HouseData {

    private SmartMeter smartMeter;

    private ArrayList<Device> devices;

    private double pvNominalPower;

    private double annualPowerConsumption;

    private WeatherStation weatherStaion;

    public void setPvNominalPower(double pvNominalPower) {
        this.pvNominalPower = pvNominalPower;
    }

    public void setAnnualPowerConsumption(double annualPowerConsumption) {
        this.annualPowerConsumption = annualPowerConsumption;
    }

    public SmartMeter getSmartMeter() {
        return smartMeter;
    }

    public ArrayList<Device> getDevices() {
        return devices;
    }

    public double getPvNominalPower() {
        return pvNominalPower;
    }

    public double getAnnualPowerConsumption() {
        return annualPowerConsumption;
    }

    public WeatherStation getWeatherStaion() {
        return weatherStaion;
    }

    public double getActivePowPlus() {
        double activePowPlus;
        //check if user has smart meter
        if (smartMeter != null) {
            //try to get current active pow pos from smart meter
            try {
                activePowPlus = smartMeter.requestData().getActivePowerPos();
            } catch (Exception e) {
                //if an Exception occurs, use the average power consumption instead
                activePowPlus = annualPowerConsumption / 8760.0;
                //TODO exception eingrenzen und mehr handling?
            }
        } else {
                //if the user has no smart meter, use the average power consumption instead
                activePowPlus = annualPowerConsumption / 8760.0;
            }

        return activePowPlus;
    }

    public double [] getActivePowMinusPredict(){
        double activePowMinusPredict[] = new double [24];
        double activePowMinus = 1.0;
        //check if user has smart meter
        if (smartMeter != null) {
            //try to get current active pow pos from smart meter
            try {
                activePowMinus = smartMeter.requestData().getActivePowerNeg();
            } catch (Exception e) {
                //handle exception
                //TODO exception eingrenzen und handling
            }
        }

        //TODO weather data and prediction sequence


        return activePowMinusPredict;
    }

}
