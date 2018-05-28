package com.hsowl.seta.data;

public class Device {

    private String name;

    private double powerConsumption;

    private DeviceType type;


    public Device(String name, double powerConsumption, DeviceType type){
        this.name = name;
        this.powerConsumption = powerConsumption;
        this.type = type;
    }


    public String getName() {
        return name;
    }

    public double getPowerConsumption() {
        return powerConsumption;
    }

    public DeviceType getType() {
        return type;
    }

    public void setPowerConsumption(double powerConsumption) {
        this.powerConsumption = powerConsumption;
    }
}
