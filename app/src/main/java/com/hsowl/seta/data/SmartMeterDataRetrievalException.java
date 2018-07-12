package com.hsowl.seta.data;

public class SmartMeterDataRetrievalException extends Exception {

    public String getMessage(){
        return "Smart Meter Authentication failed. Check your login data.";
    }

}
