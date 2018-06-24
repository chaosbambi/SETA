package com.hsowl.seta.ui;

import com.hsowl.seta.logic.PvPrognosis;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class PvPrognosisTest {

    @Test
    public void testCalculatePvPrognosis(){
        double [] prog = new double[24];
        double [] wfac = new double[24];
        PvPrognosis pp = new PvPrognosis(5000.0,52.026673,8.901862, 180,45);

        //Values were extracted from the Matlab-Script that was used to design the tested method
        double[] expected = {0,0,0,0,0,0,0,741.538406950969,1844.94137071594,2944.06107028363,
                3882.66887665735,4550.99252090546,4878.17117563033,4831.07146465736,4414.40922449884,
                3670.71027714868,2680.29357490889,1563.32862936981,490.779500531811,0,0,0,0,0};

        Date d = new Date();
        d.setDate(28);
        d.setMonth(4);
        d.setYear(118);
        d.setSeconds(0);
        d.setMinutes(0);
        d.setHours(0);

        pp.calculatePvPrognosis(prog,wfac,d);

        Assert.assertArrayEquals(expected, prog,20.0);
    }
}
