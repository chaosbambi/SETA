package com.hsowl.seta.data;

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

        return smartMeterData;
    }

}
