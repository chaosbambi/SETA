package com.hsowl.seta.data;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SmartMeter {
    private String host;

    private String login;

    private String password;

    private String cookie;

    private final String baseURL = "http://";

    public SmartMeter(String host){
        this. host = host;
    }

    public SmartMeterData requestData(){
        SmartMeterData smartMeterData = null;
        String response = null;

        String url = buildDataGetURL();

        //TODO http request to Smartmeter
        JSONSmartMeterTask task = new JSONSmartMeterTask();
        try{
            smartMeterData = task.execute(url).get();
        }catch(Throwable t){
            Log.getStackTraceString(t);
        }

        return smartMeterData;
    }

    private String buildDataGetURL(){
        return baseURL.concat(host + "/mum-webservice/data.php");
    }
    private String buildStartGetURL(){
        return baseURL.concat(host + "/start.php");
    }


    /**
     * This inner class is used to execute the API request on a different Thread and get the
     * SmartMeterData object from the JSON response
     */
    private static class JSONSmartMeterTask extends AsyncTask<String, Void, SmartMeterData> {

        @Override
        protected SmartMeterData doInBackground(String... strings) {
            SmartMeterData smd = null;
            String data = ((new HttpClient().getData(strings[0])));

            //reformat json response
            data = data.replace("1-0:2.4.0*255","activePowerPos");
            data = data.replace("1-0:2.8.0*255", "activePowerNeg");
            data = data.replace("serial", "\"serial");
            data = data.replace(", ", ", \"");
            data = data.replace(": ", "\": ");

            //Parse the JSON response in a class of the WheaterData type
            try {
                Gson gson = new GsonBuilder().create();

                smd = gson.fromJson(data, SmartMeterData.class);
            }catch (Exception e){
                Log.getStackTraceString(e);
            }

            return smd;
        }
    }

}
