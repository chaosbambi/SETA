package com.hsowl.seta.logic;

import com.hsowl.seta.data.Device;
import com.hsowl.seta.data.HouseData;

import java.util.ArrayList;
import java.util.Map;

public class EnergySuggestion {

    private ArrayList<TrafficLightColor> trafficLightColors;

    private HouseData houseData;

    public ArrayList<TrafficLightColor> getTrafficLightColors() {
        return trafficLightColors;
    }

    public Map<Device,Suggestion> getDeviceSuggestions() {
        Map<Device,Suggestion> deviceSuggestions = null;

        return deviceSuggestions;
    }
}
