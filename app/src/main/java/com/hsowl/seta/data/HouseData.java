package com.hsowl.seta.data;

import com.hsowl.seta.logic.PvPrognosis;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

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

    public void getActivePowMinusPredict(double activePowMinusPredict[]) throws Exception {
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


        double[] weatherFactor = new double[activePowMinusPredict.length];


        if(weatherStaion == null){
            throw new Exception("No Weather Station");
        } else{
            weatherStaion.getWeatherFactor(weatherFactor);
        }
        //TODO fill with right values
        PvPrognosis pvp = new PvPrognosis(pvNominalPower,0,0,0,0);

        pvp.calculatePvPrognosis(activePowMinusPredict,weatherFactor, new Date());

        if(activePowMinus <=0 ){
            activePowMinusPredict[0] = activePowMinus;
        }

    }

}
