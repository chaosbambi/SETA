package com.hsowl.seta.data;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Storage {

    private Context context;

    private HouseData houseData;

    private final String filename = "houseData.json";

    public Storage(Context context){
        this.context = context;
    }

    public void storeHouseData() {
        FileOutputStream outputStream;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(WeatherStation.class, new InterfaceAdapter<WeatherStation>());
        Gson gson = gsonBuilder.create();
        try {
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(gson.toJson(houseData).getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readHouseData(){
        String fileContent;
        try {
            FileInputStream inputStream = context.openFileInput(filename);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String receiveString;
            StringBuilder stringBuilder = new StringBuilder();

            while ( (receiveString = bufferedReader.readLine()) != null ) {
                stringBuilder.append(receiveString);
            }

            inputStream.close();
            fileContent = stringBuilder.toString();

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(WeatherStation.class, new InterfaceAdapter<WeatherStation>());
            Gson gson =gsonBuilder.create();
            houseData = gson.fromJson(fileContent, HouseData.class);
        } catch (FileNotFoundException e) {
            //TODO handle Exception
            //erste Eingabe von Daten des Users starten
            e.printStackTrace();
        } catch (IOException e) {
            //TODO handle Exception
            e.printStackTrace();
        }

    }

    public HouseData getHouseData() {
        return houseData;
    }

    public void setHouseData(HouseData houseData) {
        this.houseData = houseData;
    }
}
