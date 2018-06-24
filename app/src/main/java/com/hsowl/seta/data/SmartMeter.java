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


    private String buildDataGetURL(){
        return baseURL.concat(host + "/mum-webservice/data.php");
    }
    private String buildStartGetURL(){
        return baseURL.concat(host + "/start.php");
    }

    /**
     * This method delegates a task to send an http request to the host address to check whether the
     * user is authenticated.
     * @return  true if the JSON paramter in the response equals true. Otherwise returns false.
     */
    public boolean checkAuthentication(){
        SmartMeterData smartMeterData = null;

        String startUrl = buildStartGetURL();

        JSONSmartMeterTask task = new JSONSmartMeterTask();
        try{
            smartMeterData = task.execute(startUrl).get();
        }catch(Throwable t){
            Log.getStackTraceString(t);
        }

        if (smartMeterData != null) {
            return (smartMeterData.getAuthentication().equals("true"));
        }

        //TODO: Check if an exception should be implemented that will be thown if the smartMeterData object is null

        return false;
    }

    /**
     * Not implemented yet.
     * @return  false
     */
    public boolean authenticate(){
        //TODO: In further releases a method for the Login of the SmartMeter with pw is needed
        return false;
    }

    /**
     * This Method delegates a Task to send an http request to the host address and returns the
     * parsed SmartMeterData object from the response.
     * @return  A SmartMeterData object with recent data from the host address or null if the
     * request failed
     */
    public SmartMeterData requestData(){
        SmartMeterData smartMeterData = null;

        String dataUrl = buildDataGetURL();

        JSONSmartMeterTask task = new JSONSmartMeterTask();
        try{
            smartMeterData = task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,dataUrl).get();
        }catch(Throwable t){
            Log.getStackTraceString(t);
        }

        return smartMeterData;
    }

    /**
     * This inner class is used to execute the API request on a different Thread and get the
     * SmartMeterData object from the JSON response
     */
    private static class JSONSmartMeterTask extends AsyncTask<String, Void, SmartMeterData> {

        @Override
        protected SmartMeterData doInBackground(String... strings) {
            SmartMeterData smd = null;
            HttpClient httpClient = new HttpClient();
            String data;

            if (strings[0].contains("start")){
                httpClient.initializeConnection(strings[0]);
                data = ((httpClient.getData()));
                httpClient.storeCookiesFromConnection();
            }else{
                httpClient.initializeConnection(strings[0]);
                httpClient.addCookiesToConnection();
                data = ((httpClient.getData()));
            }

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
