package com.hsowl.seta.data.weatherData;

public class List {


    private Integer dt;

    private Main main;

    private java.util.List<Weather> weather = null;

    private Clouds clouds;

    private Wind wind;

    public Integer getDt() {
        return dt;
    }

    public Main getMain() {
        return main;
    }

    public java.util.List<Weather> getWeather() {
        return weather;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public Wind getWind() {
        return wind;
    }

    public Sys getSys() {
        return sys;
    }

    public String getDtTxt() {
        return dtTxt;
    }

    private Sys sys;

    private String dtTxt;

}
