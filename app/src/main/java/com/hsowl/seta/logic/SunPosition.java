package com.hsowl.seta.logic;

import java.util.Date;

import static java.lang.Math.abs;
import static java.lang.Math.acos;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.tan;
import static java.lang.Math.toRadians;

class SunPosition {
    private double lat;
    private double lon;
    private double azimuth;
    private double slope;


    public SunPosition(double lat, double lon, double azimuth, double slope) {
        this.lat = lat;
        this.lon = lon;
        this.azimuth = azimuth;
        this.slope = slope;
    }

    // returns the day in the tropical calender
    private static double calcDayNum(Date day) {
        return 30.3 * day.getMonth() - 1 + day.getDay();
    }

    // calculates the declination of the sun by wagner approximations
    private static double calcDeclination(double dayNum) {
        return 23.45 * sin(toRadians(360 * (284 + dayNum) / 365));
    }

    private static double calcTimeDeviation(double dayNum) {
        return 0.123 * cos(toRadians(360 * (88 + dayNum) / 365)) - 0.167 * sin(toRadians(720 * (10 + dayNum) / 365));
    }

    private static double calcHourAngle(double trueLocalTime) {
        double hourAngle = (12 - trueLocalTime) * 15;
        return hourAngle;
    }

    private static double convertTrueLocalTime(double hourAngle) {
        return 12-hourAngle /15;
    }

    private double calcTrueLocalTime(Date date) {
        double dayNum = SunPosition.calcDayNum(date);
        double timeDeviation = SunPosition.calcTimeDeviation(dayNum);
        double timeInHours = date.getHours() + date.getMinutes()/60.0 + date.getSeconds()/(60.0*60.0);
        double referencedMeridian = date.getTimezoneOffset() / 60.0 * 15;
        double trueLocalTime = timeInHours + (timeDeviation + (lon - referencedMeridian) / 15);
        return trueLocalTime;
    }

    private double calcAirmass(Date date) {
        double trueLocalTime = calcTrueLocalTime(date);
        double hourAngle = SunPosition.calcHourAngle(trueLocalTime);
        double dayNum = SunPosition.calcDayNum(date);
        double declination = SunPosition.calcDeclination(dayNum);
        //calculate airMass
        double airmass = 1 / sin(toRadians(declination)) * sin(toRadians(lat)) +
                cos(toRadians(declination)) * cos(toRadians(lat)) * cos(toRadians(hourAngle));
        return airmass;
    }

    public void calcIncidenceAngles(Date date, double angles[]) {
        //creates array that divides day of date into as many parts as the length of angles array
        Date []dates= new Date[angles.length];
        int stepWidth = 60*60*24/dates.length;
        int timeInSeconds;
        for(int i=0; i<dates.length; i++){
            dates[i] = new Date(date.getYear(),date.getMonth(),date.getDay());
            timeInSeconds = i*stepWidth;
            dates[i].setSeconds(timeInSeconds%60);
            dates[i].setMinutes((timeInSeconds/60)%60);
            dates[i].setHours(timeInSeconds/(60*60));
        }

        // Berechnung der Einfallswinkel
        for (int i = 0; i < dates.length; i++) {
            angles[i] = getIncideceAngle(dates[i]);
        }

    }

    public double calcRadiationIntensity(Date date) {
        double airmass = calcAirmass(date);
        if (airmass < 0) {
            airmass = 100000;
        }
        double intensity = 1.1 * pow(0.7, (pow(airmass, 0.678)));
        return intensity;
    }

    public Date getSunRiseTime(Date day) {
        // Berechnen der notwendigen Variablen
        double dayNum = SunPosition.calcDayNum(day);
        double declination = SunPosition.calcDeclination(dayNum);
        // Berechnung des Stundenwinkels
        double hourAngle = acos(-tan(toRadians(declination)) * tan(toRadians(lat)));

        double hourAngleSunrise = abs(hourAngle);
        // Umrechnung in gesetzliche Zeit
        Date sunrise = calcLegalTime(hourAngleSunrise, day);

        return sunrise;
    }

    public Date getSunSetTime(Date day) {
        // Berechnen der notwendigen Variablen
        double dayNum = SunPosition.calcDayNum(day);
        double declination = SunPosition.calcDeclination(dayNum);
        // Berechnung des Stundenwinkels
        double hourAngle = Math.acos(-tan(toRadians(declination)) * tan(toRadians(lat)));

        double hourAngleSunSet = -abs(hourAngle);
        // Umrechnung in gesetzliche Zeit
        Date sunset = calcLegalTime(hourAngleSunSet, day);

        return sunset;
    }

    private Date calcLegalTime(double hourAngle, Date day) {
        //Berechne notwendige Variablen
        double dayNum = SunPosition.calcDayNum(day);
        double timeDeviation = SunPosition.calcTimeDeviation(dayNum);
        double timezone = day.getTimezoneOffset() / 60.0;
        double referencedMeridian = timezone * 15;
        double trueLocalTime = SunPosition.convertTrueLocalTime(hourAngle);
        // Berechnung der gesetzlichen Zeit
        double dateTimeInHours = (trueLocalTime - (timeDeviation + (lon - referencedMeridian) / 15));
        day.setHours((int)dateTimeInHours);
        day.setMinutes((int)(dateTimeInHours*60)-(int)dateTimeInHours);
        day.setSeconds((int)(dateTimeInHours*60*60)-(int)(dateTimeInHours*60));
        return day;
    }

    private double getIncideceAngle(Date date) {
        // Berechnet notwendige Variablen
        double dayNum = SunPosition.calcDayNum(date);
        double trueLocalTime = calcTrueLocalTime(date);
        double hourAngle = SunPosition.calcHourAngle(trueLocalTime);
        double declination = SunPosition.calcDeclination(dayNum);
        // Berechnung des Einfallswinkels
        double incidenceAngle = acos(sin(toRadians(declination)) * sin(toRadians(lat)) * cos(toRadians(slope)) + sin(toRadians(declination)) * cos(toRadians(lat)) * sin(toRadians(slope)) * cos(toRadians(azimuth)) + cos(toRadians(declination)) * cos(toRadians(lat)) * cos(toRadians(slope)) * cos(toRadians(hourAngle)) - cos(toRadians(declination)) * sin(toRadians(lat)) * sin(toRadians(slope)) * cos(toRadians(azimuth)) * cos(toRadians(hourAngle)) + cos(toRadians(declination)) * sin(toRadians(slope)) * sin(toRadians(azimuth)) * sin(toRadians(hourAngle)));
        return incidenceAngle;
    }


}
