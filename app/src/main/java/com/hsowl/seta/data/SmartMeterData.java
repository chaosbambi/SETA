package com.hsowl.seta.data;

/**
 * This class supplies the attributes in Java which are filled by the SmartMeter Json-Requests
 */
public class SmartMeterData {

    private String serial;
    private String authentication;

    private double activePowerNeg;
    private double activePowerPos;

    private int status;

    public String getSerial() {
        return serial;
    }

    public String getAuthentication() {
        return authentication;
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
