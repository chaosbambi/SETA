package com.hsowl.seta.ui;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.hsowl.seta.R;
import com.hsowl.seta.data.HouseData;
import com.hsowl.seta.logic.EnergySuggestion;
import com.hsowl.seta.logic.TrafficLightColor;

import java.util.List;

public class MainFragment extends Fragment {

    //Storage storage;
    HouseData houseDate;
    EnergySuggestion energySuggestion;
    List<TrafficLightColor> trafficLightColorsList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //houseDate = Storage.getHouseData();
        //energySuggestion = new EnergySuggestion(houseDate);
        //trafficLightColorsList = energySuggestion.getTrafficLightColors();

        setHasOptionsMenu(true);
    }

    @Override
    public void onPause() {
        super.onPause();

        //this.storage.storeHouseData();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        Intent menuIntent;

        switch (id)
        {
            case R.id.device_activity_menu_item:
                menuIntent = new Intent(getActivity(), DeviceActivity.class);
                startActivity(menuIntent);
                return true;
            case R.id.custom_settings_activity_menu_item:
                menuIntent = new Intent(getActivity(), CustomSettingsActivity.class);
                startActivity(menuIntent);
                return true;
            case R.id.about_activity_menu_item:
                menuIntent = new Intent(getActivity(), AboutActivity.class);
                startActivity(menuIntent);
                return true;
            default: break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        return rootView;
    }

}
