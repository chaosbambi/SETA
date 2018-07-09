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

    public void setSmartMeter(SmartMeter smartMeter) {
        this.smartMeter = smartMeter;
    }

    public void setWeatherStation(WeatherStation weatherStation) {
        this.weatherStation = weatherStation;
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

    public void getPowerConsumptionPrediction(double activePowPlusPredict[]) throws SmartMeterAuthenticationException, SmartMeterDataRetrievalException, NoWeatherStationException{
        double activePow;
        //check if user has smart meter
        if (smartMeter != null) {

            //try to get current active pow pos from smart meter
            if(smartMeter.checkAuthentication()){
                SmartMeterData smd = smartMeter.requestData();
                if (smd == null) {
                    throw new SmartMeterDataRetrievalException();
                }

                double consumption[] = new double[activePowPlusPredict.length];

                getPowerProductionPrediction(consumption);

                activePow = smd.getActivePowerPos();
                if (activePow == 0){
                    activePow = smd.getActivePowerNeg();
                    activePow = Math.abs(activePow-consumption[0]);
                }else {
                    activePow = activePow + consumption[0];
                }
            } else {
                throw new SmartMeterAuthenticationException();
            }
        } else {
                //if the user has no smart meter, use the average power consumption instead
                activePow = annualPowerConsumption / (365 * activePowPlusPredict.length);
        }

        for (int i = 0 ; i < activePowPlusPredict.length;i++){
            activePowPlusPredict[i] = activePow;
        }
    }

    public void getPowerProductionPrediction(double activePowMinusPredict[]) throws NoWeatherStationException {

        double[] weatherFactor = new double[activePowMinusPredict.length];

        if(weatherStation == null){
            throw new NoWeatherStationException();
        } else{
            if(weatherStation.checkForUpdates()){
                weatherStation.updateWeatherData();
            }
            weatherStation.getWeatherFactor(weatherFactor);
        }

        PvPrognosis pvp = weatherStation.getPvPrognosis();

        pvp.calculatePvPrognosis(activePowMinusPredict,weatherFactor, new Date());
    }
}