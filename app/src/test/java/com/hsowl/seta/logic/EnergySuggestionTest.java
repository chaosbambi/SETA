package com.hsowl.seta.logic;

import com.hsowl.seta.data.Device;
import com.hsowl.seta.data.DeviceType;
import com.hsowl.seta.data.HouseData;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EnergySuggestionTest {

    // Attributes
    private EnergySuggestion es;

    // Methods
    @Before
    public void setup() {
        HouseData hs = new HouseData();
        //erstelle Device List
        hs.getDevices().add(new Device("Buegeleisen",150, DeviceType.flatIron));
        hs.getDevices().add(new Device("Waschmaschine",1000, DeviceType.flatIron));
        hs.getDevices().add(new Device("Spülmaschine", 500, DeviceType.flatIron));

        double[] forecast={-300,-200,-600,200,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

        es = new EnergySuggestion(hs,forecast);

    }
    @Test
    public void getDeviceSuggestions() throws Exception {
        Suggestion[] suggestions = es.getDeviceSuggestions();
        Suggestion[] expected = {Suggestion.Now,Suggestion.Later,Suggestion.Two};

        Assert.assertArrayEquals(expected,suggestions);
    }
}