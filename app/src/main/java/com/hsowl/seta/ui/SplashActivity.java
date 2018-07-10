package com.hsowl.seta.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.hsowl.seta.data.HouseData;
import com.hsowl.seta.data.Storage;

public class SplashActivity extends Activity {

    Storage storage;
    HouseData houseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        storage = new Storage(this);

        /*try{
//            storage.readHouseData();
            houseData = storage.getHouseData();
        }catch (Exception e){
            Toast.makeText(this, R.string.exception_first_login, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, CustomSettingsActivity.class);
            startActivityForResult(intent, 1);
        }*/

        startActivity(new Intent(this, MainActivity.class));

        finish();
    }
}
