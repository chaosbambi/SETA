package com.hsowl.seta.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SmartMeter {

    private String host;

    private String login;

    private String password;

    private String cookie;

    private final String startURL = "/start.php";

    private final String dataURL = "/mum-webservice/data.php";


    public SmartMeter(String host, String login, String password){
        this.host = host;
        this.login = login;
        this.password = password;
    }


    public SmartMeterData requestData(){
        SmartMeterData smartMeterData = null;
        String response = null;

        //TODO http request to Smartmeter

        //reformat json response
        response = response.replace("1-0:2.4.0*255","activePowerPos");
        response = response.replace("1-0:2.8.0*255", "activePowerNeg");
        response = response.replace("serial", "\"serial");
        response = response.replace(", ", ", \"");
        response = response.replace(": ", "\": ");
        //parse valid json into gson and get smartMeterData
        Gson gson = new GsonBuilder().create();
        smartMeterData = gson.fromJson(response, SmartMeterData.class);

        return smartMeterData;
    }

}
