package com.hsowl.seta.data;

import java.util.ArrayList;

public class HouseData {

    private SmartMeter smartMeter;

    private ArrayList<Device> devices;

    private double pvNominalPower;

    private double annualPowerConsumption;

    private WeatherStation weatherStaion;


    public void setSmartMeter(SmartMeter smartMeter) {
        this.smartMeter = smartMeter;
    }

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
}
