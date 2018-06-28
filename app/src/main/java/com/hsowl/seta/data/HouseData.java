package com.hsowl.seta.data;

import com.hsowl.seta.logic.PvPrognosis;

import java.util.ArrayList;
import java.util.Date;

public class HouseData {

    private SmartMeter smartMeter;

    private ArrayList<Device> devices;

    private double pvNominalPower;

    private double annualPowerConsumption;

    private WeatherStation weatherStation;

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

    public WeatherStation getWeatherStation() {
        return weatherStation;
    }

    public double getActivePowPlus() throws Exception{
        double activePowPlus;
        //check if user has smart meter
        if (smartMeter != null) {
            //try to get current active pow pos from smart meter
            if(smartMeter.checkAuthentication()){
                activePowPlus = smartMeter.requestData().getActivePowerPos();
            } else {
                throw new Exception("Smart Meter Authentication failed");
            }
        } else {
                //if the user has no smart meter, use the average power consumption instead
                activePowPlus = annualPowerConsumption / 8760.0;
            }

        return activePowPlus;
    }

    public void getActivePowMinusPredict(double activePowMinusPredict[]) throws Exception {
        double activePowMinus = 1.0;
        //check if user has smart meter
        if (smartMeter != null) {
            //try to get current active pow pos from smart meter
            if(smartMeter.checkAuthentication()){
                activePowMinus = smartMeter.requestData().getActivePowerNeg();
            } else {
                throw new Exception("Smart Meter Authentication failed");
            }
        }

        double[] weatherFactor = new double[activePowMinusPredict.length];


        if(weatherStation == null){
            throw new Exception("No Weather Station");
        } else{
            if(weatherStation.checkForUpdates()){
                weatherStation.updateWeatherData();
            }
            weatherStation.getWeatherFactor(weatherFactor);
        }

        PvPrognosis pvp = new PvPrognosis(pvNominalPower, weatherStation.getLat(), weatherStation.getLon(), weatherStation.getAzimuth(), weatherStation.getSlope());

        pvp.calculatePvPrognosis(activePowMinusPredict,weatherFactor, new Date());

        if(activePowMinus <=0 ){
            activePowMinusPredict[0] = activePowMinus;
        }

    }

}
