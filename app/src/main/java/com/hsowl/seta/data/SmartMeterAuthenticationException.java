package com.hsowl.seta.data;

public class SmartMeterAuthenticationException extends Exception {

    public String getMessage(){
        return "Smart Meter Authentication failed. Check your login data.";
    }

}
