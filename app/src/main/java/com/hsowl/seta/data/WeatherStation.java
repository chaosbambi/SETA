package com.hsowl.seta.data;

public abstract class WeatherStation {

    protected double lat;

    protected double lon;

    protected double slope;

    protected double azimuth;

    protected int zip;

    public double getSlope() {
        return slope;
    }

    public double getAzimuth() {
        return azimuth;
    }

    public int getZip() {
        return zip;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public abstract void getWeatherFactor(double [] weatherFactor);

    public abstract boolean checkForUpdates();

    public abstract boolean updateWeatherData();
}
