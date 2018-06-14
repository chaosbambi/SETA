package com.hsowl.seta.logic;

import com.hsowl.seta.data.Device;
import com.hsowl.seta.data.HouseData;

import java.util.ArrayList;
import java.util.Map;

public class EnergySuggestion {

    private ArrayList<TrafficLightColor> trafficLightColors;

    private HouseData houseData;

    private final double yellow_max = 4000.0;

    private final double yellow_min = 200.0;

    public Map<Device,Suggestion> getDeviceSuggestions() {
        Map<Device,Suggestion> deviceSuggestions = null;

        return deviceSuggestions;
    }

    public ArrayList<TrafficLightColor> getTrafficLightColors() {
        return trafficLightColors;
    }


    private TrafficLightColor trafficLightState(TrafficLightColor curColor, double power){
        //hysteresis for the given boundaries
        final double hyst = 200.0;
        //if the Color won't change within the if statements it will stay the current color
        TrafficLightColor nextColor = curColor;
        //case 1 : the boundary red to yellow is undercut by the hysteresis -> traffic light turns yellow
        if(curColor == TrafficLightColor.Red && power - hyst < yellow_max ){
            nextColor = TrafficLightColor.Yellow;
        //case 2 : the boundary green to yellow is exceeded by the hysteresis -> traffic light turns yellow
        } else if( curColor == TrafficLightColor.Green && power + hyst > yellow_min){
            nextColor = TrafficLightColor.Yellow;
        //case 3 : traffic light is yellow
        } else if(curColor == TrafficLightColor.Yellow){
            //case 3a : the boundary yellow to green is undercut by the hysteresis -> traffic light turns yellow
            if(power - hyst < yellow_min){
                nextColor = TrafficLightColor.Green;
            //case 3b : the boundary yellow to red is exceeded by the hysteresis -> traffic light turns yellow
            } else if (power + hyst > yellow_max){
                nextColor = TrafficLightColor.Red;
            }
        }
        return nextColor;
    }


}
