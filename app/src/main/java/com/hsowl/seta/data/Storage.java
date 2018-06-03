package com.hsowl.seta.data;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Storage {

    private Context context;

    private HouseData houseData;

    private final String filename = "houseData.json";

    public void storeHouseData() {
        //TODO check if file exists
        File file = new File(context.getFilesDir(), filename);
        FileOutputStream outputStream;
        Gson gson = new GsonBuilder().create();
        try {
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(gson.toJson(houseData).getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readHouseData(){
        byte [] fileContent = null;
        try {
            //TODO check if file exists
            FileInputStream inputStream = context.openFileInput(filename);
            fileContent = new byte[inputStream.available()];
            inputStream.read(fileContent);
            Gson gson = new GsonBuilder().create();
            houseData = gson.fromJson(fileContent.toString(), HouseData.class);
        } catch (FileNotFoundException e) {
            //TODO handle Exception
            e.printStackTrace();
        } catch (IOException e) {
            //TODO handle Exception
            e.printStackTrace();
        }

    }
}
