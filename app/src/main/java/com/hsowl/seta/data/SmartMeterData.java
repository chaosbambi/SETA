package com.hsowl.seta.data;

public class SmartMeterData {

    private String serial;

    private double activePowerNeg;
    private double activePowerPos;

    private int status;

    public String getSerial() {
        return serial;
    }

    public double getActivePowerNeg() {
        return activePowerNeg;
    }

    public double getActivePowerPos() {
        return activePowerPos;
    }

    public int getStatus() {
        return status;
    }
}
