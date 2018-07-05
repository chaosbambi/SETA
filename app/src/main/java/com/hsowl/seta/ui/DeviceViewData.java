package com.hsowl.seta.ui;

import android.widget.ImageView;

public class DeviceViewData {

    private ImageView ivDevice;
    private String deviceType;
    private String deviceName;
    private String turnOnTime;

    public DeviceViewData(String deviceType, String deviceName, String turnOnTime){
        this.deviceType = deviceType;
        this.deviceName = deviceName;
        this.turnOnTime = turnOnTime;
    }

    public DeviceViewData(ImageView ivDevice, String deviceType, String deviceName, String turnOnTime){
        this.deviceType = deviceType;
        this.ivDevice = ivDevice;
        this.deviceName = deviceName;
        this.turnOnTime = turnOnTime;
    }

    public void setDeviceImage(ImageView ivDevice){
        this.ivDevice = ivDevice;
    }

    public void setDeviceName(String deviceName){
        this.deviceName = deviceName;
    }

    public void setTurnOnTime(String turnOnTime){
        this.turnOnTime = turnOnTime;
    }

    public ImageView getDeviceImage(){
        return ivDevice;
    }

    public String getDeviceName(){
        return deviceName;
    }

    public String getTurnOnTime(){
        return turnOnTime;
    }

}
