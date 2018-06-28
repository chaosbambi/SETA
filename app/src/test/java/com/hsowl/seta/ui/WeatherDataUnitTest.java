package com.hsowl.seta.ui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hsowl.seta.data.weatherData.WeatherData;

import org.junit.Assert;
import org.junit.Test;

public class WeatherDataUnitTest {
    @Test
    public void testGsonParsability(){
        String testJsonString = "{\"cod\":\"200\",\"message\":0.0027,\"cnt\":40,\"list\":[{\"dt\":1527930000,\"main\":{\"temp\":291.58,\"temp_min\":291.58,\"temp_max\":291.737,\"pressure\":1016.38,\"sea_level\":1030.59,\"grnd_level\":1016.38,\"humidity\":88,\"temp_kf\":-0.16},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":80},\"wind\":{\"speed\":4.77,\"deg\":256.5},\"rain\":{\"3h\":0.055},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2018-06-02 09:00:00\"},{\"dt\":1527940800,\"main\":{\"temp\":293.39,\"temp_min\":293.39,\"temp_max\":293.508,\"pressure\":1016.48,\"sea_level\":1030.64,\"grnd_level\":1016.48,\"humidity\":79,\"temp_kf\":-0.12},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":76},\"wind\":{\"speed\":4.66,\"deg\":253.001},\"rain\":{\"3h\":0.02},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2018-06-02 12:00:00\"},{\"dt\":1527951600,\"main\":{\"temp\":294.71,\"temp_min\":294.71,\"temp_max\":294.785,\"pressure\":1015.98,\"sea_level\":1030.03,\"grnd_level\":1015.98,\"humidity\":76,\"temp_kf\":-0.08},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":64},\"wind\":{\"speed\":4.26,\"deg\":259.504},\"rain\":{\"3h\":0.0025000000000001},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2018-06-02 15:00:00\"},{\"dt\":1527962400,\"main\":{\"temp\":294.33,\"temp_min\":294.33,\"temp_max\":294.365,\"pressure\":1015.86,\"sea_level\":1030,\"grnd_level\":1015.86,\"humidity\":72,\"temp_kf\":-0.04},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":80},\"wind\":{\"speed\":2.93,\"deg\":263.003},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2018-06-02 18:00:00\"},{\"dt\":1527973200,\"main\":{\"temp\":293.389,\"temp_min\":293.389,\"temp_max\":293.389,\"pressure\":1016.78,\"sea_level\":1031.02,\"grnd_level\":1016.78,\"humidity\":70,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04n\"}],\"clouds\":{\"all\":92},\"wind\":{\"speed\":2.41,\"deg\":244.501},\"rain\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2018-06-02 21:00:00\"},{\"dt\":1527984000,\"main\":{\"temp\":292.1,\"temp_min\":292.1,\"temp_max\":292.1,\"pressure\":1017.16,\"sea_level\":1031.44,\"grnd_level\":1017.16,\"humidity\":78,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":48},\"wind\":{\"speed\":2.61,\"deg\":250.004},\"rain\":{\"3h\":0.03},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2018-06-03 00:00:00\"},{\"dt\":1527994800,\"main\":{\"temp\":289.957,\"temp_min\":289.957,\"temp_max\":289.957,\"pressure\":1017.05,\"sea_level\":1031.35,\"grnd_level\":1017.05,\"humidity\":89,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":32},\"wind\":{\"speed\":1.82,\"deg\":242.5},\"rain\":{\"3h\":0.005},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2018-06-03 03:00:00\"},{\"dt\":1528005600,\"main\":{\"temp\":291.223,\"temp_min\":291.223,\"temp_max\":291.223,\"pressure\":1017.69,\"sea_level\":1031.9,\"grnd_level\":1017.69,\"humidity\":90,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":80},\"wind\":{\"speed\":1.86,\"deg\":256.004},\"rain\":{\"3h\":0.155},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2018-06-03 06:00:00\"},{\"dt\":1528016400,\"main\":{\"temp\":293.366,\"temp_min\":293.366,\"temp_max\":293.366,\"pressure\":1017.95,\"sea_level\":1032.14,\"grnd_level\":1017.95,\"humidity\":89,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":56},\"wind\":{\"speed\":1.96,\"deg\":267.501},\"rain\":{\"3h\":0.135},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2018-06-03 09:00:00\"},{\"dt\":1528027200,\"main\":{\"temp\":295.314,\"temp_min\":295.314,\"temp_max\":295.314,\"pressure\":1017.31,\"sea_level\":1031.4,\"grnd_level\":1017.31,\"humidity\":84,\"temp_kf\":0},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03d\"}],\"clouds\":{\"all\":44},\"wind\":{\"speed\":1.96,\"deg\":259.001},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2018-06-03 12:00:00\"},{\"dt\":1528038000,\"main\":{\"temp\":296.211,\"temp_min\":296.211,\"temp_max\":296.211,\"pressure\":1015.97,\"sea_level\":1030.02,\"grnd_level\":1015.97,\"humidity\":76,\"temp_kf\":0},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03d\"}],\"clouds\":{\"all\":44},\"wind\":{\"speed\":1.96,\"deg\":282.002},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2018-06-03 15:00:00\"},{\"dt\":1528048800,\"main\":{\"temp\":295.594,\"temp_min\":295.594,\"temp_max\":295.594,\"pressure\":1014.82,\"sea_level\":1028.9,\"grnd_level\":1014.82,\"humidity\":72,\"temp_kf\":0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":76},\"wind\":{\"speed\":1.62,\"deg\":310.009},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2018-06-03 18:00:00\"},{\"dt\":1528059600,\"main\":{\"temp\":293.901,\"temp_min\":293.901,\"temp_max\":293.901,\"pressure\":1014.88,\"sea_level\":1029.04,\"grnd_level\":1014.88,\"humidity\":71,\"temp_kf\":0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04n\"}],\"clouds\":{\"all\":80},\"wind\":{\"speed\":1.12,\"deg\":264.5},\"rain\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2018-06-03 21:00:00\"},{\"dt\":1528070400,\"main\":{\"temp\":292.458,\"temp_min\":292.458,\"temp_max\":292.458,\"pressure\":1014.03,\"sea_level\":1028.17,\"grnd_level\":1014.03,\"humidity\":76,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":48},\"wind\":{\"speed\":0.85,\"deg\":280.501},\"rain\":{\"3h\":0.005},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2018-06-04 00:00:00\"},{\"dt\":1528081200,\"main\":{\"temp\":291.807,\"temp_min\":291.807,\"temp_max\":291.807,\"pressure\":1012.89,\"sea_level\":1027.01,\"grnd_level\":1012.89,\"humidity\":78,\"temp_kf\":0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04n\"}],\"clouds\":{\"all\":80},\"wind\":{\"speed\":0.36,\"deg\":305.502},\"rain\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2018-06-04 03:00:00\"},{\"dt\":1528092000,\"main\":{\"temp\":293.948,\"temp_min\":293.948,\"temp_max\":293.948,\"pressure\":1012.57,\"sea_level\":1026.75,\"grnd_level\":1012.57,\"humidity\":80,\"temp_kf\":0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":56},\"wind\":{\"speed\":0.96,\"deg\":88.5008},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2018-06-04 06:00:00\"},{\"dt\":1528102800,\"main\":{\"temp\":295.719,\"temp_min\":295.719,\"temp_max\":295.719,\"pressure\":1012.03,\"sea_level\":1026.15,\"grnd_level\":1012.03,\"humidity\":79,\"temp_kf\":0},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03d\"}],\"clouds\":{\"all\":32},\"wind\":{\"speed\":1.88,\"deg\":304.502},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2018-06-04 09:00:00\"},{\"dt\":1528113600,\"main\":{\"temp\":296.67,\"temp_min\":296.67,\"temp_max\":296.67,\"pressure\":1011.19,\"sea_level\":1025.18,\"grnd_level\":1011.19,\"humidity\":77,\"temp_kf\":0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":64},\"wind\":{\"speed\":1.96,\"deg\":314.501},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2018-06-04 12:00:00\"},{\"dt\":1528124400,\"main\":{\"temp\":297.116,\"temp_min\":297.116,\"temp_max\":297.116,\"pressure\":1009.7,\"sea_level\":1023.76,\"grnd_level\":1009.7,\"humidity\":73,\"temp_kf\":0},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03d\"}],\"clouds\":{\"all\":32},\"wind\":{\"speed\":2.56,\"deg\":338.01},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2018-06-04 15:00:00\"},{\"dt\":1528135200,\"main\":{\"temp\":296.067,\"temp_min\":296.067,\"temp_max\":296.067,\"pressure\":1008.94,\"sea_level\":1023.03,\"grnd_level\":1008.94,\"humidity\":71,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"02d\"}],\"clouds\":{\"all\":8},\"wind\":{\"speed\":2.37,\"deg\":350.501},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2018-06-04 18:00:00\"},{\"dt\":1528146000,\"main\":{\"temp\":292.478,\"temp_min\":292.478,\"temp_max\":292.478,\"pressure\":1009.92,\"sea_level\":1024.06,\"grnd_level\":1009.92,\"humidity\":68,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"02n\"}],\"clouds\":{\"all\":8},\"wind\":{\"speed\":3.17,\"deg\":4.00031},\"rain\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2018-06-04 21:00:00\"},{\"dt\":1528156800,\"main\":{\"temp\":289.068,\"temp_min\":289.068,\"temp_max\":289.068,\"pressure\":1010.58,\"sea_level\":1024.71,\"grnd_level\":1010.58,\"humidity\":72,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":2.26,\"deg\":353.003},\"rain\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2018-06-05 00:00:00\"},{\"dt\":1528167600,\"main\":{\"temp\":286.497,\"temp_min\":286.497,\"temp_max\":286.497,\"pressure\":1010.76,\"sea_level\":1025.04,\"grnd_level\":1010.76,\"humidity\":81,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":1.97,\"deg\":345.003},\"rain\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2018-06-05 03:00:00\"},{\"dt\":1528178400,\"main\":{\"temp\":287.818,\"temp_min\":287.818,\"temp_max\":287.818,\"pressure\":1011.29,\"sea_level\":1025.6,\"grnd_level\":1011.29,\"humidity\":86,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":1.86,\"deg\":330.001},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2018-06-05 06:00:00\"},{\"dt\":1528189200,\"main\":{\"temp\":291.641,\"temp_min\":291.641,\"temp_max\":291.641,\"pressure\":1011.65,\"sea_level\":1025.77,\"grnd_level\":1011.65,\"humidity\":83,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":2.06,\"deg\":304.502},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2018-06-05 09:00:00\"},{\"dt\":1528200000,\"main\":{\"temp\":294.748,\"temp_min\":294.748,\"temp_max\":294.748,\"pressure\":1011.58,\"sea_level\":1025.73,\"grnd_level\":1011.58,\"humidity\":80,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":2.42,\"deg\":332.013},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2018-06-05 12:00:00\"},{\"dt\":1528210800,\"main\":{\"temp\":296.398,\"temp_min\":296.398,\"temp_max\":296.398,\"pressure\":1011.39,\"sea_level\":1025.44,\"grnd_level\":1011.39,\"humidity\":76,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":2.01,\"deg\":24.5008},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2018-06-05 15:00:00\"},{\"dt\":1528221600,\"main\":{\"temp\":295.994,\"temp_min\":295.994,\"temp_max\":295.994,\"pressure\":1011.72,\"sea_level\":1025.73,\"grnd_level\":1011.72,\"humidity\":70,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":1.52,\"deg\":70.5008},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2018-06-05 18:00:00\"},{\"dt\":1528232400,\"main\":{\"temp\":291.549,\"temp_min\":291.549,\"temp_max\":291.549,\"pressure\":1012.75,\"sea_level\":1027.01,\"grnd_level\":1012.75,\"humidity\":76,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":1.57,\"deg\":97.0002},\"rain\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2018-06-05 21:00:00\"},{\"dt\":1528243200,\"main\":{\"temp\":289.199,\"temp_min\":289.199,\"temp_max\":289.199,\"pressure\":1013.82,\"sea_level\":1028.03,\"grnd_level\":1013.82,\"humidity\":84,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":1.76,\"deg\":89.5},\"rain\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2018-06-06 00:00:00\"},{\"dt\":1528254000,\"main\":{\"temp\":288.036,\"temp_min\":288.036,\"temp_max\":288.036,\"pressure\":1014.27,\"sea_level\":1028.56,\"grnd_level\":1014.27,\"humidity\":85,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":2.57,\"deg\":78.0007},\"rain\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2018-06-06 03:00:00\"},{\"dt\":1528264800,\"main\":{\"temp\":291.927,\"temp_min\":291.927,\"temp_max\":291.927,\"pressure\":1014.82,\"sea_level\":1029.11,\"grnd_level\":1014.82,\"humidity\":76,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":3.01,\"deg\":90.5001},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2018-06-06 06:00:00\"},{\"dt\":1528275600,\"main\":{\"temp\":297.096,\"temp_min\":297.096,\"temp_max\":297.096,\"pressure\":1015.41,\"sea_level\":1029.61,\"grnd_level\":1015.41,\"humidity\":75,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":3.36,\"deg\":111.504},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2018-06-06 09:00:00\"},{\"dt\":1528286400,\"main\":{\"temp\":298.31,\"temp_min\":298.31,\"temp_max\":298.31,\"pressure\":1015.67,\"sea_level\":1029.81,\"grnd_level\":1015.67,\"humidity\":72,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":32},\"wind\":{\"speed\":4.31,\"deg\":119.001},\"rain\":{\"3h\":0.145},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2018-06-06 12:00:00\"},{\"dt\":1528297200,\"main\":{\"temp\":297.116,\"temp_min\":297.116,\"temp_max\":297.116,\"pressure\":1015.59,\"sea_level\":1029.76,\"grnd_level\":1015.59,\"humidity\":68,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":56},\"wind\":{\"speed\":4.82,\"deg\":120.51},\"rain\":{\"3h\":0.725},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2018-06-06 15:00:00\"},{\"dt\":1528308000,\"main\":{\"temp\":296.137,\"temp_min\":296.137,\"temp_max\":296.137,\"pressure\":1015.4,\"sea_level\":1029.47,\"grnd_level\":1015.4,\"humidity\":66,\"temp_kf\":0},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03d\"}],\"clouds\":{\"all\":32},\"wind\":{\"speed\":4.25,\"deg\":112.001},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2018-06-06 18:00:00\"},{\"dt\":1528318800,\"main\":{\"temp\":293.234,\"temp_min\":293.234,\"temp_max\":293.234,\"pressure\":1015.96,\"sea_level\":1030.1,\"grnd_level\":1015.96,\"humidity\":64,\"temp_kf\":0},\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02n\"}],\"clouds\":{\"all\":12},\"wind\":{\"speed\":4.02,\"deg\":111},\"rain\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2018-06-06 21:00:00\"},{\"dt\":1528329600,\"main\":{\"temp\":290.856,\"temp_min\":290.856,\"temp_max\":290.856,\"pressure\":1015.81,\"sea_level\":1030.05,\"grnd_level\":1015.81,\"humidity\":65,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"02n\"}],\"clouds\":{\"all\":8},\"wind\":{\"speed\":3.47,\"deg\":108.5},\"rain\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2018-06-07 00:00:00\"},{\"dt\":1528340400,\"main\":{\"temp\":288.856,\"temp_min\":288.856,\"temp_max\":288.856,\"pressure\":1014.92,\"sea_level\":1029.22,\"grnd_level\":1014.92,\"humidity\":66,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":2.77,\"deg\":101.501},\"rain\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2018-06-07 03:00:00\"},{\"dt\":1528351200,\"main\":{\"temp\":291.736,\"temp_min\":291.736,\"temp_max\":291.736,\"pressure\":1014.74,\"sea_level\":1028.96,\"grnd_level\":1014.74,\"humidity\":72,\"temp_kf\":0},\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02d\"}],\"clouds\":{\"all\":12},\"wind\":{\"speed\":2.26,\"deg\":101.512},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2018-06-07 06:00:00\"}],\"city\":{\"id\":2878943,\"name\":\"Lemgo\",\"coord\":{\"lat\":52.0333,\"lon\":8.9},\"country\":\"DE\",\"population\":41943}}";
        Gson gson = new GsonBuilder().create();

        WeatherData wd = gson.fromJson(testJsonString, WeatherData.class);

        Assert.assertNotNull(wd);
    }
}
