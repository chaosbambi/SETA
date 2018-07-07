package com.hsowl.seta.data;

import com.hsowl.seta.logic.PvPrognosis;

public abstract class WeatherStation {

    protected double lat;

    protected double lon;

    protected int zip;

    protected PvPrognosis pvPrognosis;

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public PvPrognosis getPvPrognosis() {return pvPrognosis; }

    public abstract void getWeatherFactor(double [] weatherFactor);

    public abstract boolean setZip(int zip);

    public abstract boolean checkForUpdates();

    public abstract boolean updateWeatherData();

    public abstract boolean updateCoordinates();

    public abstract void createPvPrognosis(double pvPeakPower, double azimuth, double slope);
}
