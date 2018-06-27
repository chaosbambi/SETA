package com.hsowl.seta.data;

import java.util.ArrayList;

public class HouseData {

    private double pvPeakPower;

    private double annualPowerConsumption;

    private OpenWeatherStation weatherStaion;

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

    public OpenWeatherStation getWeatherStaion() {
        return weatherStaion;
    }

    public void setPvPeakPower(double pvPeakPower) {
        this.pvPeakPower = pvPeakPower;
    }

    public void setAnnualPowerConsumption(double annualPowerConsumption) {
        this.annualPowerConsumption = annualPowerConsumption;
    }

}
