package com.hsowl.seta.data;

import java.util.ArrayList;

public class HouseData {

    private double pvPeakPower;

    private double annualPowerConsumption;

    private WeatherStation weatherStaion;

    private SmartMeter smartMeter;

    private ArrayList<Device> devices;

    public HouseData(){
        this.devices = new ArrayList<>();
    }

    public SmartMeter getSmartMeter() {
        return smartMeter;
    }

    public ArrayList<Device> getDevices() {
        return devices;
    }

    public double getPvPeakPower() {
        return pvPeakPower;
    }

    public double getAnnualPowerConsumption() {
        return annualPowerConsumption;
    }

    public WeatherStation getWeatherStaion() {
        return weatherStaion;
    }

    public void setPvPeakPower(double pvPeakPower) {
        this.pvPeakPower = pvPeakPower;
    }

    public void setAnnualPowerConsumption(double annualPowerConsumption) {
        this.annualPowerConsumption = annualPowerConsumption;
    }

}
